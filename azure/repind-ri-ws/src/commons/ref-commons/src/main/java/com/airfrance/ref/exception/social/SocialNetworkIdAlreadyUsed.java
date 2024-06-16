package com.airfrance.ref.exception.social;

public class SocialNetworkIdAlreadyUsed extends SocialNetworkDataException {

	private static final long serialVersionUID = 5500110210408271158L;

	public SocialNetworkIdAlreadyUsed(String socialNetworkId) {
		super("Social network ID already used: "+socialNetworkId);
	}
	
}
