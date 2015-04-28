package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

public interface MediaType {
	public final static String HOBBYLIST_API_USER = "application/vnd.hobbylist.api.user+json";
	public final static String HOBBYLIST_API_USER_COLLECTION = "application/vnd.hobbylist.api.user.collection+json";
	public final static String HOBBYLIST_API_MOVIE = "application/vnd.hobbylist.api.movie+json";
	public final static String HOBBYLIST_API_MOVIE_COLLECTION = "application/vnd.hobbylist.api.movie.collection+json";
	public final static String HOBBYLIST_API_ERROR = "application/vnd.dsa.hobbylist.error+json";
}