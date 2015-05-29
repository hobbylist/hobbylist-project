package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
 




import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.HobbylistRootAPIResource;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.HobbyResource;
 
public class HobbylistRootAPI {
	@InjectLinks({
            @InjectLink(resource = HobbylistRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Hobbylist Root API"),
            @InjectLink(resource = HobbyResource.class, style = Style.ABSOLUTE, rel = "collection", title = "Latest hobbies", type = MediaType.HOBBYLIST_API_HOBBY_COLLECTION),
            @InjectLink(resource = HobbyResource.class, style = Style.ABSOLUTE, rel = "create-hobby", title = "Create new movie", type = MediaType.HOBBYLIST_API_HOBBY)})
          
    	private List<Link> links;
 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}