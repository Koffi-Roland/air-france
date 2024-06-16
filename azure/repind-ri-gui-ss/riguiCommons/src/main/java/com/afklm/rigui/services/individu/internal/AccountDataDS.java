package com.afklm.rigui.services.individu.internal;

import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dao.individu.CommunicationPreferencesRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dto.individu.AccountDataDTO;
import com.afklm.rigui.dto.individu.AccountDataTransform;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.enums.AccountDataStatusEnum;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.services.adresse.internal.EmailDS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AccountDataDS {
    /**
     * main dao
     */
    @Autowired
    private AccountDataRepository accountDataRepository;

    /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ u var) ENABLED START*/

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Autowired
    @Qualifier("emailDS")
    private EmailDS emailDS;

    /*PROTECTED REGION END*/

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public boolean isAccountDeleteByGin(String gin) {

        // transformation light bo -> dto
        return accountDataRepository.findBySginAndStatus(gin, AccountDataStatusEnum.ACCOUNT_DELETED.code()) != null;
    }

    @Transactional(readOnly = true)
    public AccountDataDTO getByGin(String gin) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        AccountData accountData = accountDataRepository.findBySgin(gin);

        if (accountData == null) {
            return null;
        }

        AccountDataDTO accountDataDTO = AccountDataTransform.bo2Dto(accountData);

        return accountDataDTO;
    }

    public String getLastValidEmailForGin(String gin) {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        String email = accountDataRepository.getLastEmailByGin(gin);

        if (email == null) {
            return null;
        }

        return email;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updateSocialNetworkId(String gin, String socialNetworkId, SignatureDTO signatureFromAPP) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("GIN is required for update social network id");
        }

        // get account data in database
        AccountData accountDataFromDB = accountDataRepository.findBySgin(gin);

        // get today's date
        Date today = new Date();

        // keep in mind old social network id
        String lastSocialNetworkId = accountDataFromDB.getSocialNetworkId();

        // update social network id
        accountDataFromDB.setSocialNetworkId(socialNetworkId);
        accountDataFromDB.setLastSocialNetworkId(lastSocialNetworkId);
        accountDataFromDB.setLastSocialNetworkLogonDate(today);
        accountDataFromDB.setDateModification(today);
        accountDataFromDB.setSiteModification(signatureFromAPP.getSite());
        accountDataFromDB.setSignatureModification(signatureFromAPP.getSite());

    }

    /*PROTECTED REGION END*/
}
