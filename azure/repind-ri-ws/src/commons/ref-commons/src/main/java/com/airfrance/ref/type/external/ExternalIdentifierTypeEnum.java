package com.airfrance.ref.type.external;

public enum ExternalIdentifierTypeEnum {

	PNM_ID("PNM_ID"),
	GIGYA_ID("GIGYA_ID"),
	FACEBOOK_ID("FACEBOOK_ID"),
	TWITTER_ID("TWITTER_ID"),
	LINKEDIN_ID("LINKEDIN_ID"),
	SINAWEIBO_ID("SINAWEIBO_ID"),
	WECHAT_ID("WECHAT_ID"),
	WHATSAPP_ID("WHATSAPP_ID"),
	INSTAGRAM_ID("INSTAGRAM_ID"),
	KAKAO_ID("KAKAO_ID"),
	KLOOT_ID("KLOOT_ID"),
	HYVES_ID("HYVES_ID"),
	APP_ID("APP_ID"),
	LIN_ID("LIN_ID");


	private String type;
	
	ExternalIdentifierTypeEnum(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
	
	public final String getType() {
		return type;
	}
	
}
