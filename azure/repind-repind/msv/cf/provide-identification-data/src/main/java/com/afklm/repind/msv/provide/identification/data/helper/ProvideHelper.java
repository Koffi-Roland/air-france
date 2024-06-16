package com.afklm.repind.msv.provide.identification.data.helper;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.identification.data.models.error.BusinessError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


@Service
/*
 * A helper used to remove special character in a string to return a good format in all our request
 */
public class ProvideHelper {
    /**
     * A method called to delete special character in a string.
     * @param word The string where we want to remove our special character
     * @return The base string with normal letter instead of special character
     */
    public String removeSpecialCharacter(String word) {
        String chars = "àáâãäåÀÁÂÃÄÅèéêëÈÉÊËìíîïÌÍÎÏòóôõöÒÓÔÕùúûüÙÚÛÜỳýŷÿỲÝŶŸçÇ";
        String normalizedChars = "aaaaaaAAAAAAeeeeEEEEiiiiIIIIoooooOOOOuuuuUUUUyyyyYYYYcC";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            Character tmp = word.charAt(i);
            if (chars.indexOf(tmp) != -1) {
                res.append(normalizedChars.charAt(chars.indexOf(tmp)));
            } else {
                res.append(tmp);
            }
        }
        return res.toString();
    }

    public static String ginChecker(String ginToCheck) throws BusinessException{
        if(ginToCheck.length() > 12){
            throw new BusinessException(BusinessError.PARAMETER_GIN_INVALID);
        }
        return StringUtils.leftPad(ginToCheck,12,"0");
    }
}
