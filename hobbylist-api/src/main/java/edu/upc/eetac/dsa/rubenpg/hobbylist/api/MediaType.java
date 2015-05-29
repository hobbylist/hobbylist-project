package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

public interface MediaType {
	public final static String HOBBYLIST_API_USER = "application/vnd.hobbylist.api.user+json";
	public final static String HOBBYLIST_API_USER_COLLECTION = "application/vnd.hobbylist.api.user.collection+json";
	public final static String HOBBYLIST_API_HOBBY = "application/vnd.hobbylist.api.hobby+json";
	public final static String HOBBYLIST_API_HOBBY_COLLECTION = "application/vnd.hobbylist.api.hobby.collection+json";
	public final static String HOBBYLIST_API_MESSAGE = "application/vnd.hobbylist.api.message+json";
	public final static String HOBBYLIST_API_MESSAGE_COLLECTION = "application/vnd.hobbylist.api.message.collection+json";
	public final static String HOBBYLIST_API_LISTA = "application/vnd.hobbylist.api.lista+json";
	public final static String HOBBYLIST_API_LISTA_COLLECTION = "application/vnd.hobbylist.api.lista.collection+json";
	public final static String HOBBYLIST_API_ERROR = "application/vnd.dsa.hobbylist.error+json";
}