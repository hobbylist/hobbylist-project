package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MovieResource;

public class MovieCollection {
	@InjectLinks({
		@InjectLink(resource = MovieResource.class, style = Style.ABSOLUTE, rel = "create-movie", title = "Create movie", type = MediaType.HOBBYLIST_API_MOVIE),
		@InjectLink(value = "/movies?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous movies", type = MediaType.HOBBYLIST_API_MOVIE_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
		@InjectLink(value = "/movies?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest movies", type = MediaType.HOBBYLIST_API_MOVIE_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
	private List<Link> links;
	private List<Movie> movies;
	private long newestTimestamp;
	private long oldestTimestamp;
	 
	public MovieCollection() {
		super();
		movies = new ArrayList<>();
	}
 
	public List<Movie> getMovies() {
		return movies;
	}
 
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
 
	public void addMovie(Movie movie) {
		movies.add(movie);
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
