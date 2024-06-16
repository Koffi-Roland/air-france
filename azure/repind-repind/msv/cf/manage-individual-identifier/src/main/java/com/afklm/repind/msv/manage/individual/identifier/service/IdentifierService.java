package com.afklm.repind.msv.manage.individual.identifier.service;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.model.error.FormatError;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.service.format.FormatService;
import com.afklm.repind.msv.manage.individual.identifier.model.error.BusinessError;
import com.afklm.repind.msv.manage.individual.identifier.response.WrapperFindGinByIdentifierResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IdentifierService {

    private FormatService formatService;
    private RoleContractService roleContractService;
    private AccountIdentifierRepository accountIdentifierRepository;

    public ResponseEntity<WrapperFindGinByIdentifierResponse> findGinByEmail(String email) throws BusinessException {
        if (!formatService.isValidMail(email)) {
            throw new BusinessException(FormatError.API_EMAIL_MISMATCH);
        }

        String mailNormalized = formatService.normalizeEmail(email);

        AccountIdentifier accountIdentifierFound = accountIdentifierRepository.findByEmailIdentifier(mailNormalized);

        // No GIN found
        if (accountIdentifierFound == null) {
            if (roleContractService.existFrequencePlusContractSharingSameValidEmail(mailNormalized)){
                throw new BusinessException(BusinessError.EMAIL_ALREADY_USED_BY_FB_MEMBERS);
            }
            throw new BusinessException(BusinessError.GIN_NOT_FOUND);
        }

        // GIN found and have fb contract identifier
        if (accountIdentifierFound.getFbIdentifier() != null
                && roleContractService.existFrequencePlusContractSharingSameValidEmail(mailNormalized, accountIdentifierFound.getSgin())) {
            throw new BusinessException(BusinessError.EMAIL_ALREADY_USED_BY_FB_MEMBERS);
        }

        WrapperFindGinByIdentifierResponse response = new WrapperFindGinByIdentifierResponse(accountIdentifierFound.getSgin());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<WrapperFindGinByIdentifierResponse> findGinByContract(String cin) throws BusinessException {
        if (!formatService.isValidCIN(cin)) {
            throw new BusinessException(FormatError.API_CIN_MISMATCH);
        }

        AccountIdentifier accountIdentifierFound = accountIdentifierRepository.findByFbIdentifier(formatService.formatCIN(cin));

        if (accountIdentifierFound == null) {
            throw new BusinessException(BusinessError.GIN_NOT_FOUND);
        }

        WrapperFindGinByIdentifierResponse response = new WrapperFindGinByIdentifierResponse(accountIdentifierFound.getSgin());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
