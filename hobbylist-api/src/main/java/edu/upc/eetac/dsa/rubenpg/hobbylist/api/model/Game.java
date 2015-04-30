package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.GameResource;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;

public class Game {
	@InjectLinks({
		@InjectLink(resource = GameResource.class, style = Style.ABSOLUTE, rel = "games", title = "Latest games", type = MediaType.HOBBYLIST_API_GAME_COLLECTION),
		@InjectLink(resource = GameResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Game", type = MediaType.HOBBYLIST_API_GAME, method = "getGame", bindings = @Binding(name = "gameid", value = "${instance.gameid}")) })
	private List<Link> links;
	private int gameid;
	private String title;
	private String tag;
	private String username;	
	private long lastModified;
	private long creationTimestamp;
	
	public int getGameid() {
		return gameid;
	}
	
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
