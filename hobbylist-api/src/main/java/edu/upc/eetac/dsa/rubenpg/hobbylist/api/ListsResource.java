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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.Lista;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.ListaCollection;

@Path("/hobbies")
public class ListsResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	//private String GET_HOBBY_BY_USER_QUERY = "select * from hobbies where hobbyid = (select hobbyid from lists where username like ?)";
	
	private String GET_HOBBY_BY_USER_AND_CLASSIFICATION_QUERY = "select * from hobbies h, lists l where h.hobbyid = l.hobbyid AND username like ? AND classification like ?";
	
	@GET
	@Path("/users/{username}/{classification}")
	@Produces(MediaType.HOBBYLIST_API_LISTA_COLLECTION)
	public ListaCollection getLists(@PathParam("username") String username, @PathParam("classification") String classification) {
		ListaCollection lists = new ListaCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {	
			stmt = conn.prepareStatement(GET_HOBBY_BY_USER_AND_CLASSIFICATION_QUERY);
			stmt.setString(1, "%" + username + "%");
			stmt.setString(2, "%" + classification + "%");
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Lista lista = new Lista();
				lista.setHobbyid(rs.getInt("hobbyid"));
				lista.setClassification(rs.getString("classification"));
				lista.setTitle(rs.getString("title"));
				lista.setSynopsis(rs.getString("synopsis"));
				lista.setGenre(rs.getString("genre"));
				lista.setDirector(rs.getString("director"));
				lista.setAuthor(rs.getString("author"));
				lista.setCompany(rs.getString("company"));
				lista.setYear(rs.getString("year"));
				lista.setTag(rs.getString("tag"));
				lista.setRank(rs.getString("rank"));				;
				lista.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
				lists.addLista(lista);
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
	 
		return lists;
	}
	
	private String GET_HOBBY_BY_USER_AND_ID_QUERY = "select * from hobbies h, lists l where l.listid=? AND h.hobbyid = l.hobbyid";
	 
	@GET
	@Path("/users/{username}/{listid}")
	@Produces(MediaType.HOBBYLIST_API_LISTA)
	public Lista getHobbyinLista(@PathParam("username") String username, @PathParam("listid") String listid,	@Context Request request) {
		Lista lista = new Lista();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;				
		try {				
			stmt = conn.prepareStatement(GET_HOBBY_BY_USER_AND_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(listid));
			
			ResultSet rs = stmt.executeQuery();			
			while (rs.next()) {
				lista.setHobbyid(rs.getInt("hobbyid"));
				lista.setClassification(rs.getString("classification"));
				lista.setTitle(rs.getString("title"));
				lista.setSynopsis(rs.getString("synopsis"));
				lista.setGenre(rs.getString("genre"));
				lista.setDirector(rs.getString("director"));
				lista.setAuthor(rs.getString("author"));
				lista.setCompany(rs.getString("company"));
				lista.setYear(rs.getString("year"));
				lista.setTag(rs.getString("tag"));
				lista.setRank(rs.getString("rank"));
				lista.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
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
	 
		return lista;
	}

	private String INSERT_HOBBY_IN_LIST_QUERY = "insert into lists (hobbyid, username, tag, rank) values (?, ?, ?, ?)";
	 
	@POST
	@Consumes(MediaType.HOBBYLIST_API_LISTA)
	@Produces(MediaType.HOBBYLIST_API_LISTA)
	public Lista createLista(Lista lista) {
		validateLista(lista);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_HOBBY_IN_LIST_QUERY,
					Statement.RETURN_GENERATED_KEYS);	 
			stmt.setInt(1, lista.getHobbyid());
			stmt.setString(2, lista.getUsername());
			stmt.setString(3, lista.getTag());
			stmt.setString(4, lista.getRank());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int listid = rs.getInt(1);
	 
				lista = getListaFromDatabase(Integer.toString(listid));
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
	 
		return lista;
	}
	
	private String DELETE_LIST_QUERY = "delete from lists where listid=?";
	 
	@DELETE
	@Path("/lists/{listid}")
	public void deleteLista(@PathParam("listid") String listid) {
		//validateUser(listid);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_LIST_QUERY);
			stmt.setInt(1, Integer.valueOf(listid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no list with listid="
						+ listid);
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
	
	private String UPDATE_LIST_QUERY = "update lists set hobbyid=ifnull(?, hobbyid), username=ifnull(?, username), tag=ifnull(?, tag), rank=ifnull(?, rank) where listid=?";
	 
	@PUT
	@Path("/{listid}")
	@Consumes(MediaType.HOBBYLIST_API_LISTA)
	@Produces(MediaType.HOBBYLIST_API_LISTA)
	public Lista updateLista(@PathParam("listid") String listid, Lista lista) {
		validateUpdateLista(lista);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_LIST_QUERY);
			stmt.setInt(1, lista.getHobbyid());
			stmt.setString(2, lista.getUsername());
			stmt.setString(3, lista.getTag());
			stmt.setString(4, lista.getRank());
			stmt.setInt(5, Integer.valueOf(listid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 1)
				lista = getListaFromDatabase(listid);
			else {
				throw new NotFoundException("There's no list with listid=" + listid);
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
	 
		return lista;
	}
	
	private void validateLista(Lista lista) {		
		if (lista.getUsername() == null)
			throw new BadRequestException("Username can't be null.");
		if (lista.getTag() == null)
			throw new BadRequestException("Tag can't be null.");
		if (lista.getRank() == null)
			throw new BadRequestException("Rank can't be null.");		
		if (lista.getUsername().length() > 20)
			throw new BadRequestException("Username can't be greater than 100 characters.");
		if (lista.getTag().length() > 20)
			throw new BadRequestException("Tag can't be greater than 500 characters.");
		if (lista.getRank().length() > 20)
			throw new BadRequestException("Rank can't be greater than 20 characters.");
	}
	
	private void validateUpdateLista(Lista lista) {
		if (lista.getUsername() != null && lista.getUsername().length() > 20)
			throw new BadRequestException(
					"Username can't be greater than 20 characters.");
		if (lista.getTag() != null && lista.getTag().length() > 20)
			throw new BadRequestException(
					"Tag can't be greater than 500 characters.");
		if (lista.getRank() != null && lista.getRank().length() > 20)
			throw new BadRequestException(
					"Rank can't be greater than 20 characters.");
	}
	
	private Lista getListaFromDatabase(String listid) {
		Lista lista = new Lista();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_HOBBY_BY_USER_AND_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(listid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				lista.setListid(rs.getInt("listid"));
				lista.setHobbyid(rs.getInt("hobbyid"));
				lista.setUsername(rs.getString("username"));
				lista.setTag(rs.getString("tag"));
				lista.setRank(rs.getString("rank"));
			} else {
				throw new NotFoundException("There's no list with listid="+ listid);
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
	 
		return lista;
	}
	
	@Context
	private SecurityContext security;

}
