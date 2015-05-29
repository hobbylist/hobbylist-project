package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class ListaCollection {
	
	private List<Link> links;
	private List<Lista> lists;
	 
	public ListaCollection() {
		super();
		lists = new ArrayList<>();
	}
	

	public List<Lista> getLists() {
		return lists;
	}

	public void setLists(List<Lista> lists) {
		this.lists = lists;
	}
	
	public void addLista(Lista lista) {
		lists.add(lista);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
}
