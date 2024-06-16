package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ExternalIdentifierTypeEnumTest {

    @Test
    void testToString() {
        assertEquals("APP_ID", ExternalIdentifierTypeEnum.APP_ID.toString());
        assertEquals("GIGYA_ID", ExternalIdentifierTypeEnum.GIGYA_ID.toString());
        assertEquals("FACEBOOK_ID", ExternalIdentifierTypeEnum.FACEBOOK_ID.toString());
        assertEquals("HYVES_ID", ExternalIdentifierTypeEnum.HYVES_ID.toString());
        assertEquals("INSTAGRAM_ID", ExternalIdentifierTypeEnum.INSTAGRAM_ID.toString());
        assertEquals("KAKAO_ID", ExternalIdentifierTypeEnum.KAKAO_ID.toString());
        assertEquals("KLOOT_ID", ExternalIdentifierTypeEnum.KLOOT_ID.toString());
        assertEquals("LIN_ID", ExternalIdentifierTypeEnum.LIN_ID.toString());
        assertEquals("LINKEDIN_ID", ExternalIdentifierTypeEnum.LINKEDIN_ID.toString());
        assertEquals("PNM_ID", ExternalIdentifierTypeEnum.PNM_ID.toString());
        assertEquals("SINAWEIBO_ID", ExternalIdentifierTypeEnum.SINAWEIBO_ID.toString());
        assertEquals("TWITTER_ID", ExternalIdentifierTypeEnum.TWITTER_ID.toString());
        assertEquals("WECHAT_ID", ExternalIdentifierTypeEnum.WECHAT_ID.toString());
        assertEquals("WHATSAPP_ID", ExternalIdentifierTypeEnum.WHATSAPP_ID.toString());
    }
}
