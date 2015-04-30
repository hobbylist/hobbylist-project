package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.Game;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.GameCollection;

@Path("/games")

public class GameResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();	
	private String GET_GAMES_QUERY = "select g.*, u.name from games g, users u where u.username=g.username and g.creation_timestamp < ifnull(?, now())  order by creation_timestamp desc limit ?";
	private String GET_GAMES_QUERY_FROM_LAST = "select g.*, u.name from games g, users u where u.username=g.username and g.creation_timestamp > ? order by creation_timestamp desc";
	
	@GET
	@Produces(MediaType.HOBBYLIST_API_GAME_COLLECTION)
	public GameCollection getGames(@QueryParam("length") int length,
			@QueryParam("before") long before, @QueryParam("after") long after) {
		GameCollection games = new GameCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {	
			boolean updateFromLast = after > 0;
			stmt = updateFromLast ? conn
					.prepareStatement(GET_GAMES_QUERY_FROM_LAST) : conn
					.prepareStatement(GET_GAMES_QUERY);
			if (updateFromLast) {
				stmt.setTimestamp(1, new Timestamp(after));
			} else {
				if (before > 0)
					stmt.setTimestamp(1, new Timestamp(before));
				else
					stmt.setTimestamp(1, null);
				length = (length <= 0) ? 5 : length;
				stmt.setInt(2, length);
			}
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;
			
			while (rs.next()) {
				Game game = new Game();
				game.setGameid(rs.getInt("gameid"));
				game.setTitle(rs.getString("title"));
				game.setTag(rs.getString("tag"));
				game.setUsername(rs.getString("username"));				
				game.setLastModified(rs.getTimestamp("last_modified").getTime());
				game.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				game.setLastModified(oldestTimestamp);
				if (first) {
					first = false;
					games.setNewestTimestamp(game.getCreationTimestamp());
				}
				games.addGame(game);
			}
			games.setOldestTimestamp(oldestTimestamp);			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return games;
	}
	
	private String GET_GAME_BY_ID_QUERY = "select * from games where gameid=?";
	 
	@GET
	@Path("/{gameid}")
	@Produces(MediaType.HOBBYLIST_API_GAME)
	public Response getGame(@PathParam("gameid") String gameid,
			@Context Request request) {
		// Create CacheControl
		CacheControl cc = new CacheControl();
		Game game = getGameFromDatabase(gameid);
		 
		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(game.getLastModified()));
	 
		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
	 
		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}
	 
		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(game).cacheControl(cc).tag(eTag);
	 
		return rb.build();
	}
	
	private String INSERT_GAME_QUERY = "insert into games (title, tag, username) values (?, ?, ?)";
	 
	@POST
	@Consumes(MediaType.HOBBYLIST_API_GAME)
	@Produces(MediaType.HOBBYLIST_API_GAME)
	public Game createGame(Game game) {
		validateGame(game);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_GAME_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, game.getTitle());
			stmt.setString(2, game.getTag());
			stmt.setString(3, security.getUserPrincipal().getName());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int gameid = rs.getInt(1);
	 
				game = getGameFromDatabase(Integer.toString(gameid));
			} else {
				// Something has failed...
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return game;
	}
	
	private String DELETE_GAME_QUERY = "delete from games where gameid=?";
	 
	@DELETE
	@Path("/{gameid}")
	public void deleteBook(@PathParam("gameid") String gameid) {
		validateUser(gameid);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_GAME_QUERY);
			stmt.setInt(1, Integer.valueOf(gameid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no game with gameid="
						+ gameid);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	private String UPDATE_GAME_QUERY = "update games set title=ifnull(?, title), tag=ifnull(?, tag) where bookid=?";
	 
	@PUT
	@Path("/{gameid}")
	@Consumes(MediaType.HOBBYLIST_API_GAME)
	@Produces(MediaType.HOBBYLIST_API_GAME)
	public Game updateGame(@PathParam("gameid") String gameid, Game game) {
		validateUser(gameid);
		validateUpdateGame(game);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_GAME_QUERY);
			stmt.setString(1, game.getTitle());
			stmt.setString(2, game.getTag());
			stmt.setInt(3, Integer.valueOf(gameid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 1)
				game = getGameFromDatabase(gameid);
			else {
				throw new NotFoundException("There's no game with gameid="
						+ gameid);
			}
	 
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
	return game;
	}
	
	private void validateGame(Game game) {
		if (game.getTitle() == null)
			throw new BadRequestException("Title can't be null.");
		if (game.getTag() == null)
			throw new BadRequestException("Tag can't be null.");
		if (game.getTitle().length() > 100)
			throw new BadRequestException("Title can't be greater than 100 characters.");
		if (game.getTag().length() > 20)
			throw new BadRequestException("Tag can't be greater than 20 characters.");
	}
	
	private void validateUpdateGame(Game game) {
		if (game.getTitle() != null && game.getTitle().length() > 100)
			throw new BadRequestException(
					"Title can't be greater than 100 characters.");
		if (game.getTag() != null && game.getTag().length() > 20)
			throw new BadRequestException(
					"Tag can't be greater than 20 characters.");
	}
	
	private Game getGameFromDatabase(String gameid) {
		Game game = new Game();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_GAME_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(gameid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				game.setGameid(rs.getInt("gameid"));
				game.setTitle(rs.getString("title"));
				game.setTag(rs.getString("tag"));
				game.setUsername(rs.getString("username"));				
				game.setLastModified(rs.getTimestamp("last_modified").getTime());
				game.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
			} else {
				throw new NotFoundException("There's no game with gameid="+ gameid);
				}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return game;
	}
	
	private void validateUser(String gameid) {
	    Game game = getGameFromDatabase(gameid);
	    String username = game.getUsername();
		if (!security.getUserPrincipal().getName().equals(username))
			throw new ForbiddenException(
					"You are not allowed to modify this game.");
	}
	
	@Context
	private SecurityContext security;
}
