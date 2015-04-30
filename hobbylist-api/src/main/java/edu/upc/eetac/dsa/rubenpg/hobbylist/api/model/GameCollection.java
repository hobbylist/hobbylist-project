package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.GameResource;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;

public class GameCollection {
	@InjectLinks({
		@InjectLink(resource = GameResource.class, style = Style.ABSOLUTE, rel = "create-game", title = "Create game", type = MediaType.HOBBYLIST_API_GAME),
		@InjectLink(value = "/games?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous games", type = MediaType.HOBBYLIST_API_GAME_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
		@InjectLink(value = "/games?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest games", type = MediaType.HOBBYLIST_API_GAME_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
	private List<Link> links;
	private List<Game> games;
	private long newestTimestamp;
	private long oldestTimestamp;
	 
	public GameCollection() {
		super();
		games = new ArrayList<>();
	}
 
	public List<Game> getGames() {
		return games;
	}
 
	public void setGames(List<Game> games) {
		this.games = games;
	}
 
	public void addGame(Game game) {
		games.add(game);
	}
	
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
 
	public long getNewestTimestamp() {
		return newestTimestamp;
	}
 
	public void setNewestTimestamp(long newestTimestamp) {
		this.newestTimestamp = newestTimestamp;
	}
 
	public long getOldestTimestamp() {
		return oldestTimestamp;
	}
 
	public void setOldestTimestamp(long oldestTimestamp) {
		this.oldestTimestamp = oldestTimestamp;
	}
}
