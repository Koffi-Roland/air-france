package com.airfrance.batch.compref.migklsub.enums;

import com.airfrance.batch.utils.SicUtils;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.airfrance.batch.utils.IConstants.DATETIME_FORMATTER;

public enum MarketLanguageFieldEnum {
    STATUS ( "Status" ),
    SIGNUP_DATE ( "SignUpDate" ),
    COUNTRY_CODE ( "Country_Code" ),
    LANGUAGE_CODE ( "Language_Code" ),
    UPDATE_SOURCE ( "Update_Source" ),
    UPDATE_DATE ( "Update_Date" ),
    ADHOC_SUBSCRIPTION_CODE ( "Adhoc_Subscriptioncode" );

    private String value;

    MarketLanguageFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MarketLanguageFieldEnum fromString(String iValue){
        return Arrays.stream(MarketLanguageFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(MarketLanguageFieldEnum iMarketLanguageFieldEnum , MarketLanguage ioMarketLanguage){
        if(ioMarketLanguage != null && iMarketLanguageFieldEnum != null){
            switch (iMarketLanguageFieldEnum){
                case COUNTRY_CODE:
                    return (x) -> ioMarketLanguage.setMarket((String)x);
                case LANGUAGE_CODE:
                    return (x) -> ioMarketLanguage.setLanguage((String)x);
                case UPDATE_SOURCE:
                    return (x) -> ioMarketLanguage.setModificationSignature(SicUtils.subString((String)x , 16));
                case UPDATE_DATE:
                    return (x) -> ioMarketLanguage.setDateOfConsent(SicUtils.encodeDate(DATETIME_FORMATTER , (String)x));
                case ADHOC_SUBSCRIPTION_CODE:
                    return (x) -> ioMarketLanguage.setCreationSite(SicUtils.encodeCreationSiteDate((String)x));
                case STATUS:
                    return (x) -> ioMarketLanguage.setOptIn(SicUtils.encodeStatus((String)x));
                default:
                    return null;
            }
        }
        return null;
    }
}
