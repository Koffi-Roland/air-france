package com.afklm.repind.msv.provide.identification.data.transform;

import com.afklm.repind.msv.provide.identification.data.models.SignatureElement;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import com.afklm.soa.stubs.r000378.v1.model.SignatureCreation;
import com.afklm.soa.stubs.r000378.v1.model.SignatureModification;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
@NoArgsConstructor
/*
 * A class used to refactor common code between all the transform
 */
public class GenericTransform {

    /**
     * Setter for signature
     * @param creationSig The row base of our response
     * @param modificationSig The row base of our response
     * @return A complete signature object
     */
    public Signature setSignature(SignatureElement creationSig,SignatureElement modificationSig){
        Signature res = new Signature();

        SignatureCreation creation = new SignatureCreation();
        if(creationSig.getDate() != null) {
            creation.setDate(creationSig.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        creation.setSite(creationSig.getSite());
        creation.setSignature(creationSig.getSignature());

        SignatureModification modification = new SignatureModification();
        if(modificationSig.getDate() != null) {
            modification.setDate(modificationSig.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        modification.setSignature(modificationSig.getSignature());
        modification.setSite(modificationSig.getSite());

        res.setSignatureCreation(creation);
        res.setSignatureModification(modification);

        return res;
    }
}
