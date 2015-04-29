package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.Movie;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.MovieCollection;
 
@Path("/movies")
public class MovieResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_MOVIES_QUERY = "select * from movies";
	
	@GET
	@Produces(MediaType.HOBBYLIST_API_MOVIE_COLLECTION)
	public MovieCollection getMovies() {
		MovieCollection movies = new MovieCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {	
			stmt = conn.prepareStatement(GET_MOVIES_QUERY);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieid(rs.getInt("movieid"));
				movie.setTitle(rs.getString("title"));
				movie.setTag(rs.getString("tag"));
				movie.setUsername(rs.getString("username"));				
				movie.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
				movie.setCreationTimestamp(rs
						.getTimestamp("creation_timestamp").getTime());					
				movies.addMovie(movie);
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
	 
		return movies;
	}
	
	private String GET_MOVIE_BY_ID_QUERY = "select * from movies where movieid=?";
	 
	@GET
	@Path("/{movieid}")
	@Produces(MediaType.HOBBYLIST_API_MOVIE)
	public Movie getMovie(@PathParam("movieid") String movieid) {
		Movie movie = new Movie();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_MOVIE_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(movieid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				movie.setMovieid(rs.getInt("movieid"));
				movie.setTitle(rs.getString("title"));
				movie.setTag(rs.getString("tag"));
				movie.setUsername(rs.getString("username"));				
				movie.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
				movie.setCreationTimestamp(rs
						.getTimestamp("creation_timestamp").getTime());
			} else {
				throw new NotFoundException("There's no movie with movieid="
				+ movieid);
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
	 
		return movie;
	}
	
	
	private String INSERT_MOVIE_QUERY = "insert into movies (title, tag, username) values (?, ?, ?)";
	 
	@POST
	@Consumes(MediaType.HOBBYLIST_API_MOVIE)
	@Produces(MediaType.HOBBYLIST_API_MOVIE)
	public Movie createMovie(Movie movie) {
		validateMovie(movie);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_MOVIE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, movie.getTitle());
			stmt.setString(2, movie.getTag());
			stmt.setString(3, movie.getUsername());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int movieid = rs.getInt(1);
	 
				movie = getMovie(Integer.toString(movieid));
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
	 
		return movie;
	}
	
	private String DELETE_MOVIE_QUERY = "delete from movies where movieid=?";
	 
	@DELETE
	@Path("/{movieid}")
	public void deleteMovie(@PathParam("movieid") String movieid) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_MOVIE_QUERY);
			stmt.setInt(1, Integer.valueOf(movieid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no movie with movieid="
						+ movieid);
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
	
	private String UPDATE_MOVIE_QUERY = "update movies set title=ifnull(?, title), tag=ifnull(?, tag) where movieid=?";
	 
	@PUT
	@Path("/{movieid}")
	@Consumes(MediaType.HOBBYLIST_API_MOVIE)
	@Produces(MediaType.HOBBYLIST_API_MOVIE)
	public Movie updateMovie(@PathParam("movieid") String movieid, Movie movie) {
		validateUpdateMovie(movie);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_MOVIE_QUERY);
			stmt.setString(1, movie.getTitle());
			stmt.setString(2, movie.getTag());
			stmt.setInt(3, Integer.valueOf(movieid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 1)
				movie = getMovie(movieid);
			else {
				throw new NotFoundException("There's no movie with movieid="
						+ movieid);
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
	 
		return movie;
		}
	
	private void validateMovie(Movie movie) {
		if (movie.getTitle() == null)
			throw new BadRequestException("Title can't be null.");
		if (movie.getTag() == null)
			throw new BadRequestException("Tag can't be null.");
		if (movie.getTitle().length() > 100)
			throw new BadRequestException("Title can't be greater than 100 characters.");
		if (movie.getTag().length() > 20)
			throw new BadRequestException("Tag can't be greater than 20 characters.");
	}
	
	private void validateUpdateMovie(Movie movie) {
		if (movie.getTitle() != null && movie.getTitle().length() > 100)
			throw new BadRequestException(
					"Title can't be greater than 100 characters.");
		if (movie.getTag() != null && movie.getTag().length() > 20)
			throw new BadRequestException(
					"Tag can't be greater than 20 characters.");
	}
}
