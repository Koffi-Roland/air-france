package com.afklm.repind.msv.provide.identification.data.transform;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.msv.provide.identification.data.models.SignatureElement;
import com.afklm.soa.stubs.r000378.v1.model.AccountDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.IdentifierData;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
/*
 * A transform used to map Response of the request of the MS for the account part
 */
public class AccountTransform {

    private final GenericTransform genericTransform = new GenericTransform();

    /**
     * Setter of AccountDataResponse
     * @param identifierData A parameter containing row data
     * @return Link the mapped data to our response
     */
    public AccountDataResponse setResponse(List<IdentifierData> identifierData) {
        AccountDataResponse accountDataResponse = new AccountDataResponse();
        accountDataResponse.setIdentifierDatas(identifierData);
        return accountDataResponse;
    }

    /**
     * Setter for identifierData
     * @param identifier The base of our response
     * @param signature The base of our response
     * @param type The base of our response
     * @return Transform and map a part of our data
     */
    public IdentifierData setIdentifierData(String identifier, Signature signature, String type){
        IdentifierData res = new IdentifierData();

        res.setIdentifier(identifier);
        res.setSignature(signature);
        res.setType(type);

        return res;
    }

    /**
     * Setter for signature
     * @param accountIdentifier Some account data where we find data about signature
     * @return The signature object
     */
    public Signature setSignature(AccountIdentifier accountIdentifier){
        SignatureElement creation = new SignatureElement(accountIdentifier.getSiteCreation(),accountIdentifier.getSignatureCreation(),accountIdentifier.getDateCreation());
        SignatureElement modification = new SignatureElement(accountIdentifier.getSiteModification(),accountIdentifier.getSignatureModification(),accountIdentifier.getDateModification());
        return this.genericTransform.setSignature(creation,modification);
    }
}
