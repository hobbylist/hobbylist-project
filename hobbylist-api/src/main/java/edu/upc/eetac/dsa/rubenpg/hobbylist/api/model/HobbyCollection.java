package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class HobbyCollection {
	
	private List<Link> links;
	private List<Hobby> hobbies;
	 
	public HobbyCollection() {
		super();
		hobbies = new ArrayList<>();
	}
 
	public List<Hobby> getHobbies() {
		return hobbies;
	}
 
	public void setHobbies(List<Hobby> hobbies) {
		this.hobbies = hobbies;
	}
 
	public void addHobby(Hobby hobby) {
		hobbies.add(hobby);
	}
	
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
