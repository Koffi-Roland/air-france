package com.afklm.repind.msv.search.individual.v2.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.BusinessError;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * Utility class to validate
 */
@Service
public class SearchIndividualCheckerV2 {

    /**
     * Check search individual
     * @param cin the cin
     * @param email the email
     * @param internationalPhoneNumber the phone
     * @param socialIdentifier the socialIdentifer
     * @param socialType the socialType
     * @param firstname the firstname
     * @param lastname the lastname
     * @throws BusinessException
     */
    public void checkSearchIndividual(String cin, String email, String internationalPhoneNumber, String socialIdentifier, String socialType, String firstname, String lastname) throws BusinessException {

        boolean isCinEmpty = ObjectUtils.isEmpty(cin);
        boolean isEmailEmpty = ObjectUtils.isEmpty(email);
        boolean isPhoneEmpty = ObjectUtils.isEmpty(internationalPhoneNumber);
        boolean isSocialIdentifierEmpty = ObjectUtils.isEmpty(socialIdentifier);
        boolean isSocialTypeEmpty = ObjectUtils.isEmpty(socialType);
        boolean isLastnameEmpty = ObjectUtils.isEmpty(lastname);
        boolean isFisrtnameEmpty = ObjectUtils.isEmpty(firstname);

        // at least one parameter must be provided
        if (isCinEmpty && isEmailEmpty && isPhoneEmpty && (isSocialIdentifierEmpty || isSocialTypeEmpty) && (isLastnameEmpty || isFisrtnameEmpty)) {
            throw new BusinessException(BusinessError.API_MISSING_REQUEST_PARAMETER);
        }

        CheckerUtils.checkCin(cin);
        CheckerUtils.checkEmail(email);
        CheckerUtils.checkPhone(internationalPhoneNumber);
        CheckerUtils.checkSocialIdentifier(socialIdentifier);
        CheckerUtils.checkSocialType(socialType);
        CheckerUtils.checkLastname(lastname);
        CheckerUtils.checkFirstname(firstname);
    }
}
