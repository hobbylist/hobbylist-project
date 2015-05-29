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
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.Message;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.MessageCollection;

@Path("/messages")
public class MessageResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_MESSAGE_BY_USER_QUERY = "select * from messages where sender like ? OR receiver like ?";

	@GET
	@Path("/user/{username}")
	@Produces(MediaType.HOBBYLIST_API_MESSAGE_COLLECTION)
	public MessageCollection getMessages(@QueryParam("messageid") String messageid, 
			@PathParam("username") String username) {
		MessageCollection messages = new MessageCollection();
	 
		Connection conn = null;	
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {				
			stmt = conn.prepareStatement(GET_MESSAGE_BY_USER_QUERY);
			stmt.setString(1, "%" + username + "%");
			stmt.setString(2, "%" + username + "%");
			stmt.executeQuery();
						
			ResultSet rs = stmt.executeQuery();			
			while (rs.next()) {
				Message message = new Message();
				message.setMessageid(rs.getInt("messageid"));
				message.setSender(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setSubject(rs.getString("subject"));
				message.setContent(rs.getString("content"));
				message.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());								
				messages.addMessage(message);
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
	 
		return messages;
	}
	
	private String GET_MESSAGE_BY_ID_QUERY = "select * from messages where messageid=?";
	 
	@GET
	@Path("/{messageid}")
	@Produces(MediaType.HOBBYLIST_API_MESSAGE)
	public Message getMessage(@PathParam("messageid") String messageid,	@Context Request request) {
		Message message = new Message();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;				
		try {				
			stmt = conn.prepareStatement(GET_MESSAGE_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(messageid));
			
			ResultSet rs = stmt.executeQuery();			
			while (rs.next()) {
				message.setMessageid(rs.getInt("messageid"));
				message.setSender(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setSubject(rs.getString("subject"));
				message.setContent(rs.getString("content"));
				message.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());					
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
	 
		return message;
	}
	
	private String INSERT_MESSAGE_QUERY = "insert into messages (sender, receiver, subject, content) values (?, ?, ?, ?)";
	 
	@POST
	@Consumes(MediaType.HOBBYLIST_API_MESSAGE)
	@Produces(MediaType.HOBBYLIST_API_MESSAGE)
	public Message createMessage(Message message) {
		validateMessage(message);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_MESSAGE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, message.getSender());
			stmt.setString(2, message.getReceiver());
			stmt.setString(3, message.getSubject());
			stmt.setString(4, message.getContent());			
			//stmt.setString(6, security.getUserPrincipal().getName());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int messageid = rs.getInt(1);
	 
				message = getMessageFromDatabase(Integer.toString(messageid));
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
	 
		return message;
	}
	
	private void validateMessage(Message message) {
		if (message.getSender() == null)
			throw new BadRequestException("Sender can't be null.");
		if (message.getReceiver() == null)
			throw new BadRequestException("Receiver can't be null.");
		if (message.getSubject() == null)
			throw new BadRequestException("Subject can't be null.");
		if (message.getContent() == null)
			throw new BadRequestException("Content can't be null.");
		
		if (message.getSender().length() > 20)
			throw new BadRequestException("Sender can't be greater than 20 characters.");
		if (message.getReceiver().length() > 20)
			throw new BadRequestException("Receiver can't be greater than 20 characters.");
		if (message.getSubject().length() > 100)
			throw new BadRequestException("Subject can't be greater than 100 characters.");
		if (message.getContent().length() > 500)
			throw new BadRequestException("Content can't be greater than 500 characters.");
	}
	
	private Message getMessageFromDatabase(String messageid) {
		Message message = new Message();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_MESSAGE_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(messageid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				message.setSender(rs.getString("sender"));
				message.setReceiver(rs.getString("receiver"));
				message.setSubject(rs.getString("subject"));
				message.setContent(rs.getString("content"));								
				message.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
			} else {
				throw new NotFoundException("There's no message with messageid="+ messageid);
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
	 
		return message;
	}
	
	private String DELETE_MESSAGE_QUERY = "delete from messages where messageid=?";
	 
	@DELETE
	@Path("/{messageid}")
	public void deleteMessage(@PathParam("messageid") String messageid) {
		validateUser(messageid);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_MESSAGE_QUERY);
			stmt.setInt(1, Integer.valueOf(messageid));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no message with messageid=" + messageid);
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
	
	private void validateUser(String messageid) {
	    Message message = getMessageFromDatabase(messageid);
	    String sender = message.getSender();
		if (!security.getUserPrincipal().getName().equals(sender))
			throw new ForbiddenException(
					"You are not allowed to modify this message.");
	}
	
	@Context
	private SecurityContext security;


}
