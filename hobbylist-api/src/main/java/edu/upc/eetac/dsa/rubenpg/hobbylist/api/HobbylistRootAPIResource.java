package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.model.HobbylistRootAPI;
 
 
@Path("/")
public class HobbylistRootAPIResource {
	@GET
	public HobbylistRootAPI getRootAPI() {
		HobbylistRootAPI api = new HobbylistRootAPI();
		return api;
	}
}