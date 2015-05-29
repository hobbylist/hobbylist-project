package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
 

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
 

import edu.upc.eetac.dsa.rubenpg.hobbylist.api.MediaType;
import edu.upc.eetac.dsa.rubenpg.hobbylist.api.HobbyResource;
 
public class Hobby {
	@InjectLinks({
			@InjectLink(resource = HobbyResource.class, style = Style.ABSOLUTE, rel = "hobbies", title = "Latest hobbies", type = MediaType.HOBBYLIST_API_HOBBY_COLLECTION),
			@InjectLink(resource = HobbyResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Hobby", type = MediaType.HOBBYLIST_API_HOBBY, method = "getHobby", bindings = @Binding(name = "hobbyid", value = "${instance.hobbyid}")) })
	private List<Link> links;
	private int hobbyid;
	private String classification;
	private String title;
	private String genre;	
	private String synopsis;
	private String director;
	private String author;
	private String company;
	private String year;	
	private long creationTimestamp;
 
	public int getHobbyid() {
		return hobbyid;
	}
 
	public void setHobbyid(int hobbyid) {
		this.hobbyid = hobbyid;
	}
 
	public String getClassification() {
		return classification;
	}
 
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	public String getTitle() {
		return title;
	}
 
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getSynopsis() {
		return synopsis;
	}
 
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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