package com.afklm.repind.msv.provide.contract.data.transform;

import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleTravelers;
import com.afklm.repind.common.entity.role.RoleUCCR;
import com.afklm.repind.common.enums.ContractType;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.models.stubs.SignatureElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Component
/*
  This transformer is responsible for the mapping of object inside the response
 */
public class ContractTransform {

    /**
     * This method is responsible for the mapping of RoleContract to Contract
     *
     * @param res          The Contract object
     * @param roleContract the RoleContract object
     * @return The contract newly created or null if the roleContrat is null
     */
    public Contract mapRoleContractToContract(Contract res, RoleContract roleContract) {
        if(roleContract == null){
            return null;
        }

        if (roleContract.getDateDebutValidite() != null) {
            res.setValidityStartDate(roleContract.getDateDebutValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (roleContract.getDateFinValidite() != null) {
            res.setValidityEndDate(roleContract.getDateFinValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (roleContract.getIata() != null && !roleContract.getIata().isEmpty()) {
            res.setIataCode(roleContract.getIata());
        }
        res.setContractStatus(roleContract.getEtat());
        res.setContractType(roleContract.getTypeContrat());
        if (roleContract.getCodeCompagnie() != null && !roleContract.getCodeCompagnie().isEmpty()) {
            res.setCompanyCode(roleContract.getCodeCompagnie());
        }
        if (roleContract.getSousType() != null && !roleContract.getSousType().isEmpty()) {
            res.setContractSubType(roleContract.getSousType());
        }
        mapSignatureToContract(res, roleContract.getDateCreation(), roleContract.getSiteCreation(), roleContract.getSignatureCreation(),
                roleContract.getDateModification(), roleContract.getSiteModification(), roleContract.getSignatureModification());

        return res;
    }

    /**
     * This method is responsible for the mapping of RoleUCCR to Contract
     *
     * @param res      The Contract object
     * @param roleUCCR the RoleUCCR object
     * @return The contract newly created or null if the roleUCCR is null
     */
    public Contract mapRoleUCCRToContract(Contract res, RoleUCCR roleUCCR) {
        if(roleUCCR == null){
            return null;
        }

        res.setContractNumber(roleUCCR.getUccrId());
        res.setContractType(ContractType.ROLE_UCCR.toString());
        if (roleUCCR.getCorporateEnvironmentID() != null && !roleUCCR.getCorporateEnvironmentID().isEmpty()) {
            res.setCorporateEnvironmentID(roleUCCR.getCorporateEnvironmentID());
        }
        res.setContractStatus(roleUCCR.getEtat());
        if (roleUCCR.getDebutValidite() != null) {
            res.setValidityStartDate(roleUCCR.getDebutValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (roleUCCR.getFinValidite() != null) {
            res.setValidityEndDate(roleUCCR.getFinValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        mapSignatureToContract(res, roleUCCR.getDateCreation(), roleUCCR.getSiteCreation(), roleUCCR.getSignatureCreation(),
                roleUCCR.getDateModification(), roleUCCR.getSiteModification(), roleUCCR.getSignatureModification());

        return res;
    }

    /**
     * This method is responsible for the mapping of RoleTravelers to Contract
     *
     * @param res          The Contract object
     * @param roleTraveler the RoleTravelers object
     * @return The contract newly created or null if the roleTraveler is null
     */
    public Contract mapRoleTravelerToContract(Contract res, RoleTravelers roleTraveler) {
        if (roleTraveler == null) {
            return null;
        }
        res.setContractType(ContractType.ROLE_TRAVELERS.toString());
        if (roleTraveler.getMatchingRecognitionCode() != null && !roleTraveler.getMatchingRecognitionCode().isEmpty()) {
            res.setMatchingRecognition(roleTraveler.getMatchingRecognitionCode());
        }
        res.setLastRecognitionDate(roleTraveler.getLastRecognitionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mapSignatureToContract(res, roleTraveler.getDateCreation(), roleTraveler.getSiteCreation(), roleTraveler.getSignatureCreation(),
                roleTraveler.getDateModification(), roleTraveler.getSiteModification(), roleTraveler.getSignatureModification());

        return res;
    }

    /**
     * This is a generic method to map a signature inside a Contract object
     *
     * @param res                   The contract object
     * @param dateCreation          the date of creation
     * @param siteCreation          the site of creation
     * @param signatureCreation     the signature of creation
     * @param dateModification      the date of modification
     * @param siteModification      the site of modification
     * @param signatureModification the signature of modification
     */
    public void mapSignatureToContract(Contract res, Date dateCreation, String siteCreation, String signatureCreation, Date dateModification, String siteModification, String signatureModification) {
        if (dateCreation != null && signatureCreation != null && !signatureCreation.isEmpty() && siteCreation != null && !siteCreation.isEmpty()) {
            res.setSignatureCreation(new SignatureElement(
                    dateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), signatureCreation, siteCreation
            ));
        }

        if (dateModification != null && signatureModification != null && !signatureModification.isEmpty() && siteModification != null && !siteModification.isEmpty()) {
            res.setSignatureModification(new SignatureElement(
                    dateModification.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), signatureModification, siteModification
            ));
        }
    }
}
