package edu.upc.eetac.dsa.rubenpg.hobbylist.api.model;

import java.util.ArrayList;
import java.util.List;

public class MovieCollection {
	private List<Movie> movies;
	 
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

}
