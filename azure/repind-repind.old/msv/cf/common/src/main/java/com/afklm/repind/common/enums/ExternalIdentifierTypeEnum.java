package com.afklm.repind.common.enums;

public enum ExternalIdentifierTypeEnum {
    KLOOT_ID,
    PNM_ID,
    WHATSAPP_ID,
    SINAWEIBO_ID,
    GIGYA_ID,
    APP_ID,
    WECHAT_ID,
    TWITTER_ID,
    LIN_ID,
    FACEBOOK_ID,
    INSTAGRAM_ID,
    LINKEDIN_ID,
    HYVES_ID,
    KAKAO_ID;

    @Override
    public String toString() {
        return this.name();
    }
}
