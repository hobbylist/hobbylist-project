package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MessageResource;

public class Message {
	@InjectLinks({
		@InjectLink(resource = MessageResource.class, style = Style.ABSOLUTE, rel = "messages", title = "Latest messages", type = MediaType.HOBBYLIST_API_MESSAGE_COLLECTION),
		@InjectLink(resource = MessageResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Message", type = MediaType.HOBBYLIST_API_MESSAGE, method = "getMessage", bindings = @Binding(name = "messageid", value = "${instance.messageid}")) })

	private List<Link> links;
	private int messageid;
	private String sender;
	private String receiver;
	private String subject;
	private String content;	
	private long creationTimestamp;
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

}
