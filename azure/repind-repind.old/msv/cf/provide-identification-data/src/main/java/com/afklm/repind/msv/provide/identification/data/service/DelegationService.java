package com.afklm.repind.msv.provide.identification.data.service;

import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.individual.ComplementaryInformationRepository;
import com.afklm.repind.common.repository.individual.DelegationDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.models.DelegationTransformObject;
import com.afklm.repind.msv.provide.identification.data.transform.DelegationTransform;
import com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation;
import com.afklm.soa.stubs.r000378.v1.model.Delegate;
import com.afklm.soa.stubs.r000378.v1.model.DelegationDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.Delegator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
/*
 * A service used to map the response of the delegation part of the request of this MS
 */
public class DelegationService {
    private DelegationDataRepository delegationDataRepository;
    private ComplementaryInformationRepository complementaryInformationRepository;
    private IndividuRepository individuRepository;
    private AccountIdentifierRepository accountIdentifierRepository;
    private TelecomService telecomsService;
    private ProvideHelper provideHelper;
    private DelegationTransform delegationTransform;

    /**
     * Getter for delegation data by Gin of delegator
     * @param gin 12 character String used to identify an individual
     * @return List of all Delegation data where the provided Gin is the Delegator gin, it can be empty
     */
    public List<DelegationData> getDelegationDataByDelegatorGin(String gin) {
        return this.delegationDataRepository.getDelegationDataEntitiesByDelegateGin(gin);
    }

    /**
     * Getter for delegation data by Gin of delegate
     * @param gin 12 character String used to identify an individual
     * @return List of all Delegation data where the provided Gin is the Delegate gin, it can be empty
     */
    public List<DelegationData> getDelegationDataByDelegateGin(String gin) {
        return this.delegationDataRepository.getDelegationDataEntitiesByDelegateGin(gin);
    }

    /**
     * Getter for ComplementaryInformation By DelegationDataId
     * @param delegationDataId An id used to distinguish which complementaryInformation correspond to which delegate.
     * @return A list of ComplementaryInformationEntity sharing the same DelegationDataId.
     */
    public List<ComplementaryInformationEntity> getComplementaryInformationEntitiesByDelegationDataId(String delegationDataId) {
        return this.complementaryInformationRepository.findAllByDelegationDataId(delegationDataId);
    }

    /**
     * Setter for delegateResponse
     * @param delegationDataResponse The response object where we store the data created here
     * @param delegateDataEntities The base used to create data about delegate
     */
    public void setDelegateResponse(DelegationDataResponse delegationDataResponse, List<DelegationData> delegateDataEntities) {
        List<Delegate> delegates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(delegateDataEntities)) {
            for (DelegationData delegateData : delegateDataEntities) {
                Individu individuDelegate = delegateData.getDelegate();
                String ginDelegate = individuDelegate != null ? individuDelegate.getGin() : "";
                DelegationTransformObject dto = mapReponseToDTO(ginDelegate, delegateData);
                List<ComplementaryInformation> tmpComplementaryInformation = delegationTransform.setComplementaryInformations(getComplementaryInformationEntitiesByDelegationDataId(delegateData.getDelegationDataId()));
                Delegate delegate = delegationTransform.setDelegate(dto.getDelegationStatusData(), dto.getDelegationIndividualData(), dto.getTelecomList(), tmpComplementaryInformation, dto.getSignature());
                delegates.add(delegate);
            }
        }
        delegationDataResponse.setDelegates(delegates);
    }

    /**
     * Setter for DelegatorResponse
     * @param delegationDataResponse The response object where we store the data created here
     * @param delegatorDataEntities The base used to create data about delegator
     */
    public void setDelegatorResponse(DelegationDataResponse delegationDataResponse, List<DelegationData> delegatorDataEntities) {
        List<Delegator> delegators = new ArrayList<>();
        if (!CollectionUtils.isEmpty(delegatorDataEntities)) {
            for (DelegationData delegatorData : delegatorDataEntities) {
                Individu individuDelegator = delegatorData.getDelegator();
                String ginDelegator = individuDelegator != null ? individuDelegator.getGin() : "";
                DelegationTransformObject dto = mapReponseToDTO(ginDelegator, delegatorData);
                Delegator delegator = delegationTransform.setDelegator(dto.getDelegationStatusData(), dto.getDelegationIndividualData(), dto.getTelecomList(), dto.getSignature());
                delegators.add(delegator);
            }
        }
        delegationDataResponse.setDelegators(delegators);
    }

    /**
     * Method to map some data into a DTO to refactor code and improve data manipulation
     * @param gin The gin of the individual related to the delegateData or to the delegatorData in parameter to fetch more data
     * @param delegateData The data of the delegation used in this method
     * @return A delegationTransformObject containing all the data we need for a delegate or a delegator
     */
    public DelegationTransformObject mapReponseToDTO(String gin, DelegationData delegateData) {
        DelegationTransformObject dto = new DelegationTransformObject();

        dto.setIndividu(individuRepository.getIndividuByGin(gin));
        dto.setAccountIdentifier(accountIdentifierRepository.findBySgin(gin));
        dto.setTelecomList(telecomsService.convertTelecomsListToTelecomList(telecomsService.getTelecomsByGin(gin)));
        String delegateGin = (delegateData != null && delegateData.getDelegate() != null)
                ? delegateData.getDelegate().getGin() : "";
        if(gin.equals(delegateGin)){
            dto.setDelegationStatusData(delegationTransform.setDelegationStatusDataForDelegate(delegateData));
        }
        else {
            dto.setDelegationStatusData(delegationTransform.setDelegationStatusDataForDelegator(delegateData));
        }
        String lastNameWithoutSpecialCharacter = "";
        if(dto.getIndividu().getNomTypo1() != null){
            lastNameWithoutSpecialCharacter = provideHelper.removeSpecialCharacter(dto.getIndividu().getNomTypo1());
        }
        String firstNameWithoutSpecialCharacter = "";
        if(dto.getIndividu().getPrenomTypo1() != null){
            firstNameWithoutSpecialCharacter = provideHelper.removeSpecialCharacter(dto.getIndividu().getPrenomTypo1());
        }
        dto.setDelegationIndividualData(delegationTransform.setDelegationIndividualData(dto.getIndividu(), dto.getAccountIdentifier(), lastNameWithoutSpecialCharacter, firstNameWithoutSpecialCharacter));
        dto.setSignature(delegationTransform.setSignature(delegateData));

        return dto;
    }


}
