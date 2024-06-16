package com.airfrance.batch.compref.migklsub.enums;

import com.airfrance.batch.utils.SicUtils;
import com.airfrance.repind.entity.individu.CommunicationPreferences;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.airfrance.batch.utils.IConstants.DATETIME_FORMATTER;

public enum ComPrefFieldEnum {
    SUBSCRIPTION_TYPE("Subscription_Type"),
    STATUS("Status"),
    GIN("GIN"),
    SIGNUP_SOURCE("Signup_Source"),
    UPDATE_DATE("Update_Date"),
    UPDATE_SOURCE("Update_Source"),
    ADHOC_SUBSCRIPTION_CODE("Adhoc_Subscriptioncode");

    private String value;

    ComPrefFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ComPrefFieldEnum fromString(String iValue) {
        return Arrays.stream(ComPrefFieldEnum.values()).filter(x -> x.getValue().equals(iValue)).findFirst().orElse(null);
    }

    public static Consumer<Object> mapConsumer(ComPrefFieldEnum iComPrefFieldEnum, CommunicationPreferences ioCompPref) {
        if (ioCompPref != null && iComPrefFieldEnum != null) {
            switch (iComPrefFieldEnum) {
                case GIN:
                    return (x) -> ioCompPref.setGin((String) x);
                case SUBSCRIPTION_TYPE:
                    return (x) -> ioCompPref.setComType((String) x);
                case STATUS:
                    return (x) -> ioCompPref.setSubscribe(SicUtils.encodeStatus((String) x));
                case UPDATE_DATE:
                    return (x) -> ioCompPref.setDateOptin(SicUtils.encodeDate(DATETIME_FORMATTER, (String) x));
                case ADHOC_SUBSCRIPTION_CODE:
                    return (x) -> ioCompPref.setCreationSite(SicUtils.encodeCreationSiteDate((String) x));
                case UPDATE_SOURCE:
                    return (x) -> ioCompPref.setModificationSignature(SicUtils.subString((String) x, 16));
                default:
                    return null;
            }
        }
        return null;
    }
}
