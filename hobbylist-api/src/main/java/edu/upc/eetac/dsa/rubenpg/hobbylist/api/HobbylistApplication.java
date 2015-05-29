package edu.upc.eetac.dsa.rubenpg.hobbylist.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class HobbylistApplication extends ResourceConfig {
	public HobbylistApplication() {
		super();
		register(DeclarativeLinkingFeature.class);
	}
}
