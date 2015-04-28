package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
 

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
 

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MovieResource;
 
public class Movie {
	@InjectLinks({
			@InjectLink(resource = MovieResource.class, style = Style.ABSOLUTE, rel = "movies", title = "Movies Collection", type = MediaType.HOBBYLIST_API_MOVIE_COLLECTION),
			@InjectLink(resource = MovieResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Movie", type = MediaType.HOBBYLIST_API_MOVIE, method = "getMovie", bindings = @Binding(name = "movieid", value = "${instance.movieid}")) })
	private List<Link> links;
	private int movieid;
	private String title;
	private String tag;
	private String username;	
	private long lastModified;
	private long creationTimestamp;
 
	public int getMovieid() {
		return movieid;
	}
 
	public void setMovieid(int movieid) {
		this.movieid = movieid;
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