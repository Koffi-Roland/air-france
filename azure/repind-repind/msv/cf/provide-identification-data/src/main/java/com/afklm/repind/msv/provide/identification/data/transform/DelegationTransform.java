package com.afklm.repind.msv.provide.identification.data.transform;


import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.msv.provide.identification.data.models.SignatureElement;
import com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation;
import com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformationData;
import com.afklm.soa.stubs.r000378.v1.model.Delegate;
import com.afklm.soa.stubs.r000378.v1.model.DelegationIndividualData;
import com.afklm.soa.stubs.r000378.v1.model.DelegationStatusData;
import com.afklm.soa.stubs.r000378.v1.model.Delegator;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import com.afklm.soa.stubs.r000378.v1.model.Telecom;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
/*
 * A transform used to map Response of the request of the MS for the delegation part
 */
public class DelegationTransform {
    private final GenericTransform genericTransform = new GenericTransform();

    /**
     * Setter for delegate
     *
     * @param delegationStatusData     The row base of our response
     * @param delegationIndividualData The row base of our response
     * @param telecoms                 The row base of our response
     * @param complementaryInformation The row base of our response
     * @param signature                The row base of our response
     * @return The mapped object
     */
    public Delegate setDelegate(DelegationStatusData delegationStatusData, DelegationIndividualData delegationIndividualData, List<Telecom> telecoms,
                                List<ComplementaryInformation> complementaryInformation, Signature signature) {
        Delegate res = new Delegate();

        res.setDelegationStatusData(delegationStatusData);
        res.setDelegationIndividualData(delegationIndividualData);
        res.setTelecoms(telecoms);
        res.setComplementaryInformation(complementaryInformation);
        res.setSignature(signature);

        return res;
    }

    /**
     * Setter for delegator
     *
     * @param delegationStatusData     The row base of our response
     * @param delegationIndividualData The row base of our response
     * @param telecoms                 The row base of our response
     * @param signature                The row base of our response
     * @return The mapped object
     */
    public Delegator setDelegator(DelegationStatusData delegationStatusData, DelegationIndividualData delegationIndividualData, List<Telecom> telecoms, Signature signature) {
        Delegator res = new Delegator();

        res.setDelegationStatusData(delegationStatusData);
        res.setDelegationIndividualData(delegationIndividualData);
        res.setTelecoms(telecoms);
        res.setSignature(signature);

        return res;
    }

    /**
     * Setter for signature
     *
     * @param delegationData The row base of our response
     * @return the signature object
     */
    public Signature setSignature(DelegationData delegationData) {
        SignatureElement creation = new SignatureElement(delegationData.getSiteCreation(), delegationData.getSignatureCreation(), delegationData.getDateCreation());
        SignatureElement modification = new SignatureElement(delegationData.getSiteModification(), delegationData.getSignatureModification(), delegationData.getDateModification());
        return this.genericTransform.setSignature(creation, modification);

    }

    /**
     * Setter for DelegationStatusData
     *
     * @param delegationData The row base of our response
     * @return The delegationStatusData object
     */
    public DelegationStatusData setDelegationStatusDataForDelegator(DelegationData delegationData) {

        DelegationStatusData delegationStatusData = new DelegationStatusData();

        delegationStatusData.setDelegationStatus(delegationData.getStatus());
        delegationStatusData.setDelegationType(delegationData.getType());
        delegationStatusData.setGin(delegationData.getDelegator() != null ? delegationData.getDelegator().getGin() : "");

        return delegationStatusData;
    }
    /**
     * Setter for DelegationStatusData
     *
     * @param delegationData The row base of our response
     * @return The delegationStatusData object
     */
    public DelegationStatusData setDelegationStatusDataForDelegate(DelegationData delegationData) {

        DelegationStatusData delegationStatusData = new DelegationStatusData();

        delegationStatusData.setDelegationStatus(delegationData.getStatus());
        delegationStatusData.setDelegationType(delegationData.getType());
        delegationStatusData.setGin(delegationData.getDelegate() != null ? delegationData.getDelegate().getGin() : "");

        return delegationStatusData;
    }

    /**
     * Setter for DelegationIndividualData
     *
     * @param individu                    The row base of our response
     * @param accountIdentifier           The row base of our response
     * @param lastNameWithoutSpecialChar  The row base of our response
     * @param firstNameWithoutSpecialChar The row base of our response
     * @return The delegationIndividualData object
     */
    public DelegationIndividualData setDelegationIndividualData(Individu individu, AccountIdentifier accountIdentifier, String lastNameWithoutSpecialChar, String firstNameWithoutSpecialChar) {
        DelegationIndividualData res = new DelegationIndividualData();

        res.setCivility(individu.getCivilite());
        res.setLastNameSC(individu.getNomTypo1());
        res.setLastName(lastNameWithoutSpecialChar);
        res.setFirstNameSC(individu.getPrenomTypo1());
        res.setFirstName(firstNameWithoutSpecialChar);
        if (accountIdentifier != null) {
            res.setAccountIdentifier(accountIdentifier.getAccountId());
            res.setFbIdentifier(accountIdentifier.getFbIdentifier());
            res.setEmailIdentifier(accountIdentifier.getEmailIdentifier());
        }

        return res;
    }

    /**
     * Create complementaryInformation based on row data
     *
     * @param complementaryInformationEntity The row base of our response
     * @return The complementaryInformation object
     */
    public ComplementaryInformation createComplementaryInformation(ComplementaryInformationEntity complementaryInformationEntity) {
        ComplementaryInformation res = new ComplementaryInformation();
        List<ComplementaryInformationData> complementaryInformationData = new ArrayList<>();
        ComplementaryInformationData tmp = new ComplementaryInformationData();

        tmp.setKey(complementaryInformationEntity.getKey());
        tmp.setValue(complementaryInformationEntity.getValue());
        complementaryInformationData.add(tmp);

        res.setType(complementaryInformationEntity.getType());
        res.setComplementaryInformationDatas(complementaryInformationData);

        return res;
    }

    /**
     * Create complementaryInformationData based on row data
     *
     * @param complementaryInformationEntity The row base of our response
     * @return Some complementaryInformationData used to create complementaryInformation
     */
    public ComplementaryInformationData createComplementaryInformationData(ComplementaryInformationEntity complementaryInformationEntity) {
        ComplementaryInformationData res = new ComplementaryInformationData();

        res.setValue(complementaryInformationEntity.getValue());
        res.setKey(complementaryInformationEntity.getKey());

        return res;
    }

    /**
     * Setter for complementary Information
     *
     * @param complementaryInformationEntities The row base of our response
     * @return The mapped list of complementary Information
     */
    public List<ComplementaryInformation> setComplementaryInformations(List<ComplementaryInformationEntity> complementaryInformationEntities) {
        List<ComplementaryInformation> complementaryInformations = new ArrayList<>();
        List<String> tmp = new ArrayList<>();

        for (ComplementaryInformationEntity complementaryInformationEntity : complementaryInformationEntities) {
            if (!tmp.contains(complementaryInformationEntity.getType())) {
                tmp.add(complementaryInformationEntity.getType());
                complementaryInformations.add(this.createComplementaryInformation(complementaryInformationEntity));
            } else {
                int index = tmp.indexOf(complementaryInformationEntity.getType());
                ComplementaryInformation tmp2 = complementaryInformations.get(index);
                tmp2.getComplementaryInformationDatas().add(this.createComplementaryInformationData(complementaryInformationEntity));
            }
        }

        return complementaryInformations;
    }
}
