package com.afklm.repind.msv.provide.identification.data.service;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.msv.provide.identification.data.transform.AccountTransform;
import com.afklm.soa.stubs.r000378.v1.model.AccountDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.IdentifierData;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
/*
 * A service used to map the response of the account part of the request of this MS
 */
public class AccountService {
    private AccountIdentifierRepository accountIdentifierRepository;
    private AccountTransform accountTransform;


    /**
     * Getter for Account identifier
     * @param gin 12 character String used to identify an individual
     * @return AccountIdentifier object corresponding to the provided Gin, it can be null
     */
    public AccountIdentifier getAccountIdentifierbyGin(String gin) {
        return this.accountIdentifierRepository.findBySgin(gin);
    }

    /**
     * Setter for accountDataResponse
     * @param accountIdentifier An AccountIdentifier object used to create the response
     * @return An AccountDataResponse object based on the inputs
     */
    public AccountDataResponse setAccountDataResponse(AccountIdentifier accountIdentifier) {
        List<IdentifierData> identifierData = new ArrayList<>();
        Signature tmp = this.accountTransform.setSignature(accountIdentifier);

        if(accountIdentifier.getAccountId() != null){
            IdentifierData tmp2 = this.accountTransform.setIdentifierData(accountIdentifier.getAccountId(),tmp,"MA");
            identifierData.add(tmp2);
        }

        if(accountIdentifier.getEmailIdentifier() != null){
            IdentifierData tmp2 = this.accountTransform.setIdentifierData(accountIdentifier.getEmailIdentifier(),tmp,"EI");
            identifierData.add(tmp2);
        }

        if(accountIdentifier.getFbIdentifier() != null){
            IdentifierData tmp2 = this.accountTransform.setIdentifierData(accountIdentifier.getFbIdentifier(),tmp,"FP");
            identifierData.add(tmp2);
        }

        return this.accountTransform.setResponse(identifierData);
    }
}
