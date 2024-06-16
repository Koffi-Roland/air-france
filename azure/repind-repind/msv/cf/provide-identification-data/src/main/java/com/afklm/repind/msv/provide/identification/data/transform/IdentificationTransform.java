package com.afklm.repind.msv.provide.identification.data.transform;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.UsageClientEntity;
import com.afklm.repind.common.enums.YesNoEnum;
import com.afklm.repind.msv.provide.identification.data.models.SignatureElement;
import com.afklm.soa.stubs.r000378.v1.model.IdentificationDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.IndividualInformations;
import com.afklm.soa.stubs.r000378.v1.model.UsageClient;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
/*
 * A transform used to map Response of the request of the MS for the identification part
 */
public class IdentificationTransform {
    private final GenericTransform genericTransform = new GenericTransform();

    /**
     * Setter for identificationDataResponse
     * @param individu The row base of our response
     * @param usagesClient The row base of our response
     * @param languageCode The row base of our response
     * @return The mapped response
     */
    public IdentificationDataResponse setIdentificationDataResponse(Individu individu, List<UsageClientEntity> usagesClient, String languageCode) {
        IdentificationDataResponse identificationDataResponse = new IdentificationDataResponse();

        this.setUsagesClient(identificationDataResponse, usagesClient);
        this.setIndividualInformations(identificationDataResponse, individu, languageCode);
        this.setSignature(identificationDataResponse,individu);

        return identificationDataResponse;
    }

    /**
     * Setter for signature
     * @param identificationDataResponse The object where we store our signature
     * @param individu The row base of our response
     */
    public void setSignature(IdentificationDataResponse identificationDataResponse, Individu individu) {
        SignatureElement creation = new SignatureElement(individu.getSiteCreation(),individu.getSignatureCreation(),individu.getDateCreation());
        SignatureElement modification = new SignatureElement(individu.getSiteModification(),individu.getSignatureModification(),individu.getDateModification());
        identificationDataResponse.setSignature(this.genericTransform.setSignature(creation,modification));
    }

    /**
     * Setter for usageClient
     * @param identificationDataResponse The object where we store our usage client
     * @param usageClientEntities The row base of our response
     */
    public void setUsagesClient(IdentificationDataResponse identificationDataResponse, List<UsageClientEntity> usageClientEntities) {
        List<UsageClient> tmp = new ArrayList<>();

        for (UsageClientEntity usageClientEntity : usageClientEntities) {
            if(usageClientEntity != null) {
                UsageClient tmp2 = new UsageClient();
                tmp2.setApplicationCode(usageClientEntity.getCode());
                tmp2.setSrin(usageClientEntity.getSrin());
                tmp2.setAuthorizedModification(usageClientEntity.getAuthorizedModification());
                if(usageClientEntity.getDateModification() != null) {
                    tmp2.setLastModificationDate(usageClientEntity.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                tmp.add(tmp2);
            }
        }

        identificationDataResponse.setUsagesClient(tmp);
    }

    /**
     * Setter for individualInformation
     * @param identificationDataResponse The object where we store our individual information
     * @param individu The row base of our response
     * @param languageCode The row base of our response
     */
    public void setIndividualInformations(IdentificationDataResponse identificationDataResponse, Individu individu, String languageCode) {
        IndividualInformations individualInformations = new IndividualInformations();

        if(individu.getDateNaissance() != null) {
            individualInformations.setBirthdate(individu.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        individualInformations.setCivility(individu.getCivilite());
        this.setIndividualFlags(individualInformations, individu);
        individualInformations.setFirstNameSC(individu.getPrenomTypo1());
        individualInformations.setLastNameSC(individu.getNomTypo1());
        individualInformations.setFirstNameNormalized(individu.getPrenom());
        individualInformations.setLastNameNormalized(individu.getNom());
        individualInformations.setFirstNamePseudonym(individu.getAliasPrenom());
        individualInformations.setSecondFirstName(individu.getSecondPrenom());
        individualInformations.setLastNamePseudonym(individu.getAliasNom1());
        individualInformations.setGender(individu.getSexe());
        individualInformations.setIdentifier(individu.getGin());
        individualInformations.setPopulationType(individu.getType());
        individualInformations.setStatus(individu.getStatutIndividu());
        individualInformations.setTitleCode(individu.getCodeTitre());
        individualInformations.setVersion(individu.getVersion().toString());
        individualInformations.setPersonalIdentifier(individu.getIdentifiantPersonnel());
        individualInformations.setLanguageCode(languageCode);

        identificationDataResponse.setIndividualInformations(individualInformations);
    }

    /**
     * Setter for individualFlag
     * @param individualInformations The object where we store our individual flags
     * @param individu The row base of our response
     */
    public void setIndividualFlags(IndividualInformations individualInformations, Individu individu) {
        individualInformations.setFlagNoFusion(individu.getNonFusionnable() != null && YesNoEnum.getValue(individu.getNonFusionnable()) == YesNoEnum.YES);
        individualInformations.setFlagThirdTrap(individu.getTierUtiliseCommePiege() != null && YesNoEnum.getValue(individu.getTierUtiliseCommePiege()) == YesNoEnum.YES);
    }
}
