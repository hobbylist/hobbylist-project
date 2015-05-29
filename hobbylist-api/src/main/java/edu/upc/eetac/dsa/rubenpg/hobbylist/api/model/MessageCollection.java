package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class MessageCollection {
	
	private List<Link> links;
	private List<Message> messages;
	 
	public MessageCollection() {
		super();
		messages = new ArrayList<>();
	}
 
	public List<Message> getMessages() {
		return messages;
	}
 
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
 
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
