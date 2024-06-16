package com.afklm.repind.msv.manage.external.identifier.transformer;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ExternalIdentifierDataEntity;
import com.afklm.repind.msv.manage.external.identifier.models.SignatureElement;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierData;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.Signature;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.SignatureData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
/*
 * A transformer for the get request of this microservice
 */
public class ExternalIdentifierTransformer {
    /**
     * A mapper to create an ExternalIdentifier with an ExternalIdentifier and his list of ExternalIdentifierDataEntity
     * @param externalIdentifierEntity Some data to build the new object
     * @param externalIdentifierDataEntityList Some data about the complementary data of the object
     * @return The mapped object
     */
    public com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier buildExternalIdentifier(ExternalIdentifier externalIdentifierEntity, List<ExternalIdentifierDataEntity> externalIdentifierDataEntityList) {
        com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier res = new com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier();

        res.setIdentifier(externalIdentifierEntity.getIdentifier());
        res.setType(externalIdentifierEntity.getType());
        res.setExternalIdentifierData(this.buildExternalIdentifierDataList(externalIdentifierDataEntityList));

        SignatureElement sig = new SignatureElement(externalIdentifierEntity.getSignatureCreation(), externalIdentifierEntity.getSiteCreation(),
                externalIdentifierEntity.getDateCreation(), externalIdentifierEntity.getSignatureModification(),
                externalIdentifierEntity.getSiteModification(), externalIdentifierEntity.getDateModification());
        res.setSignature(this.createSignature(sig));

        return res;
    }

    /**
     * A method to map a signature object
     * @param sig The data used to create the signature
     * @return The mapped signature
     */
    public Signature createSignature(SignatureElement sig) {
        Signature res = new Signature();

        SignatureData creation = new SignatureData();
        creation.setSite(sig.getSiteCreation());
        creation.setSignature(sig.getSignatureCreation());
        creation.setDate(sig.getDateCreation());

        SignatureData modification = new SignatureData();
        modification.setSite(sig.getSiteModification());
        modification.setSignature(sig.getSignatureModification());
        modification.setDate(sig.getDateModification());

        res.setCreation(creation);
        res.setModification(modification);
        return res;
    }

    /**
     * A mapper for the list of ExternalIdentifierData of an ExternalIdentifier
     * @param externalIdentifierDataEntityList Some data used to create the list of ExternalIdentifierData
     * @return The list of mapped ExternalIdentifierData
     */
    public List<ExternalIdentifierData> buildExternalIdentifierDataList(List<ExternalIdentifierDataEntity> externalIdentifierDataEntityList) {
        List<ExternalIdentifierData> res = new ArrayList<>();

        for (ExternalIdentifierDataEntity externalIdentifierDataEntity : externalIdentifierDataEntityList) {
            ExternalIdentifierData tmp = new ExternalIdentifierData();

            tmp.setKey(externalIdentifierDataEntity.getKey());
            tmp.setValue(externalIdentifierDataEntity.getValue());

            SignatureElement sig = new SignatureElement(externalIdentifierDataEntity.getSignatureCreation(), externalIdentifierDataEntity.getSiteCreation(),
                    externalIdentifierDataEntity.getDateCreation(), externalIdentifierDataEntity.getSignatureModification(),
                    externalIdentifierDataEntity.getSiteModification(), externalIdentifierDataEntity.getDateModification());
            tmp.setSignature(this.createSignature(sig));

            res.add(tmp);
        }

        return res;
    }
}
