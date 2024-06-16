package com.airfrance.batch.compref.fixafcompref.enums;

import com.airfrance.batch.utils.SicUtils;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.airfrance.batch.utils.IConstants.*;

public enum MarketLanguageFieldEnum {
    LANGUAGE_CODE ( "COD_LGE" ),
    MARKET_CODE ("COD_MKT"),
    CONSCENT_DATE ("DAT_OPTIN");


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
                case MARKET_CODE:
                    return (x) -> ioMarketLanguage.setMarket((String)x);
                case LANGUAGE_CODE:
                    return (x) -> ioMarketLanguage.setLanguage((String)x);
                case CONSCENT_DATE:
                    return (x) -> ioMarketLanguage.setDateOfConsent(SicUtils.encodeDate(DATE_FORMATTER_DDMMYY , (String)x));
                default:
                    return null;
            }
        }
        return null;
    }
}
