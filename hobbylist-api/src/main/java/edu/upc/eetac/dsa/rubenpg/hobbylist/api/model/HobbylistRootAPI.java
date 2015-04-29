package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
 




import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.HobbylistRootAPIResource;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MovieResource;
 
public class HobbylistRootAPI {
	@InjectLinks({
            @InjectLink(resource = HobbylistRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Hobbylist Root API"),
            @InjectLink(resource = MovieResource.class, style = Style.ABSOLUTE, rel = "collection", title = "Latest movies", type = MediaType.HOBBYLIST_API_MOVIE_COLLECTION),
            @InjectLink(resource = MovieResource.class, style = Style.ABSOLUTE, rel = "create-movie", title = "Create new movie", type = MediaType.HOBBYLIST_API_MOVIE)})
    	private List<Link> links;
 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}