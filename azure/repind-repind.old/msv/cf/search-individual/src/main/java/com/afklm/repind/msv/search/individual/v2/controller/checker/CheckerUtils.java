package com.afklm.repind.msv.search.individual.v2.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.BusinessError;
import org.apache.commons.lang3.ObjectUtils;

import java.util.regex.Pattern;

/**
 * Utility class to validate
 */
public final class CheckerUtils {

    private static final
            Pattern VALID_CIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE);
    private static final
            Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$", Pattern.CASE_INSENSITIVE);
    private static final
            Pattern VALID_SOCIAL_IDENTIFIER_REGEX = Pattern.compile("^.{1,500}$", Pattern.CASE_INSENSITIVE);
    private static final
            Pattern VALID_LASTNAME_REGEX = Pattern.compile("^[\\D]+$", Pattern.CASE_INSENSITIVE);
    private static final
            Pattern VALID_FIRSTNAME_REGEX = Pattern.compile("^[\\D]+$", Pattern.CASE_INSENSITIVE);

    /**
     * Check cin
     * @param cin the cin
     * @return the cin
     * @throws BusinessException
     */
    public static String checkCin(String cin) throws BusinessException {
        if (ObjectUtils.isEmpty(cin)) {
            return null;
        }
        if (!VALID_CIN_PATTERN.matcher(cin).find()) {
            throw new BusinessException(BusinessError.API_CIN_MISMATCH);
        }
        return cin;
    }

    /**
     * Check email
     * @param email the email
     * @return the email
     * @throws BusinessException
     */
    public static String checkEmail(String email) throws BusinessException {
        if (ObjectUtils.isEmpty(email)) {
            return null;
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            throw new BusinessException(BusinessError.API_EMAIL_MISMATCH);
        }
        return email;
    }

    /**
     * Check phone
     * @param phone the phone
     * @return the phone
     * @throws BusinessException
     */
    public static String checkPhone(String phone){
        if (ObjectUtils.isEmpty(phone)) {
            return null;
        }
        return phone;
    }

    /**
     * Check socialIdentifer
     * @param socialIdentifier the socialIdentifer
     * @return the socialIdentifer
     * @throws BusinessException
     */
    public static String checkSocialIdentifier(String socialIdentifier) throws BusinessException {
        if (ObjectUtils.isEmpty(socialIdentifier)) {
            return null;
        }
        if(!VALID_SOCIAL_IDENTIFIER_REGEX.matcher(socialIdentifier).find()){
            //API_SOCIAL_IDENTIFIER_MISMATCH
            throw new BusinessException(BusinessError.API_SOCIAL_IDENTIFIER_MISMATCH);
        }
        return socialIdentifier;
    }

    /**
     * Check socialType
     * @param socialType the socialType
     * @return the socialType
     * @throws BusinessException
     */
    public static String checkSocialType(String socialType){
        if (ObjectUtils.isEmpty(socialType)) {
            return null;
        }
        return socialType;
    }

    /**
     * Check lastname
     * @param lastname the lastname
     * @return the lastname
     * @throws BusinessException
     */
    public static String checkLastname(String lastname) throws BusinessException {
        if(ObjectUtils.isEmpty(lastname)){
            return null;
        }
        if(!VALID_LASTNAME_REGEX.matcher(lastname).find()){
            throw new BusinessException(BusinessError.API_LASTNAME_MISMATCH);
        }
        return lastname;
    }

    /**
     * Check firstname
     * @param firstname the firstname
     * @return the firstname
     * @throws BusinessException
     */
    public static String checkFirstname(String firstname) throws BusinessException {
        if(ObjectUtils.isEmpty(firstname)){
            return null;
        }
        if(!VALID_FIRSTNAME_REGEX.matcher(firstname).find()){
            throw new BusinessException(BusinessError.API_FIRSTNAME_MISMATCH);
        }
        return firstname;
    }

    private CheckerUtils() {
        throw new IllegalStateException("Utility class");
    }
}
