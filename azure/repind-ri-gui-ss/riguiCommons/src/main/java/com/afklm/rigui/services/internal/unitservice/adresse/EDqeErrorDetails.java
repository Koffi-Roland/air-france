package com.afklm.rigui.services.internal.unitservice.adresse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enum corresponding to error return by DQE Service
 */
@Getter
@AllArgsConstructor
public enum EDqeErrorDetails {
    ADDRESS_OK(10 , 1),
    ADDRESS_OK_PATH_NO_RECOGNIZE(20,2),
    SMALL_TOWN_NUMBER_OUT_OF_RANGE(21,2),
    SMALL_TOWN_NO_NUMBER(22,2),
    BIG_TOWN_NUMBER_OUT_OF_RANGE(23,2),
    BIG_TOWN_NO_NUMBER(24,2),
    SMALL_TOWN_PATH_UNKNOWN(30,3),
    SMALL_TOWN_PATH_MISSING(41,4),
    BIG_TOWN_PATH_UNKNOWN(50,5),
    BIG_TOWN_PATH_MISSING(61,6),
    CP_TOWN_NOT_CORRECTABLE_PATH_PRESENT(70,7),
    CP_TOWN_NOT_CORRECTABLE_PATH_MISSING(80,8),
    INTERNATIONAL_ADDRESS(90,9),
    UNKNOWN_CODE(100 , 10);

    private final Integer code;
    private final Integer level;

    /**
     * Return DqeErrorDetails corresponding to input level
     * If no match return the higher level in this enum
     * @param iCode
     * @return matched EDqeErrorDetails
     */
    public static EDqeErrorDetails valueFromLevel(Integer iCode){
        return Arrays.stream(values()).filter(d -> d.getCode().equals(iCode)).findFirst().orElse(UNKNOWN_CODE);
    }
}
