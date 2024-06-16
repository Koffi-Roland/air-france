package com.afklm.repind.msv.manage.external.identifier.service;

import com.afklm.repind.common.entity.identifier.ExternalIdentifier;
import com.afklm.repind.common.entity.identifier.ExternalIdentifierDataEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierDataRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.msv.manage.external.identifier.models.error.BusinessError;
import com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifierResponse;
import com.afklm.repind.msv.manage.external.identifier.transformer.ExternalIdentifierTransformer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
/*
 * A service used to map the response of the request for this MS
 */
public class ExternalIdentifierService {

    private ExternalIdentifierRepository externalIdentifierRepository;
    private ExternalIdentifierDataRepository externalIdentifierDataRepository;
    private ExternalIdentifierTransformer externalIdentifierTransformer;

    /**
     * A getter for all the external identifier entity of the database related to a specific gin
     * @param gin The gin of all the external identifier entity we want
     * @return The list of external identifier entity who match the gin in parameter
     */
    public List<ExternalIdentifier> getExternalIdentifierListByGin(String gin) {
        return this.externalIdentifierRepository.findAllByGin(gin);
    }

    /**
     * A getter for all the external identifier data entity of the database related to the external identifier entity in parameter
     * @param externalIdentifierEntity The external identifier entity related to the list of external identifier data entity we want
     * @return The list of external identifier data entity related to the external identifier entity in parameter
     */
    public List<ExternalIdentifierDataEntity> getExternalIdentifierDataListByExternalIdentifierEntity(ExternalIdentifier externalIdentifierEntity) {
        return this.externalIdentifierDataRepository.findAllByIdentifierId(externalIdentifierEntity.getIdentifierId());
    }

    /**
     * A getter for a list of external identifier entity
     * @param identifier The identifier of the external identifier entity we want
     * @param type The Type of the external identifier entity we want
     * @return A list that of external identifier entity
     */
    public List<ExternalIdentifier> getAllExternalIdentifierByIdentifierIdAndType(String identifier, String type) {
        return this.externalIdentifierRepository.findAllByIdentifierAndType(identifier, type);
    }

    /**
     * A getter for a specific external identifier entity, it'll be return in an optional if it exists, else the optional will be empty
     * @param identifier The identifier of the external identifier entity we want
     * @param type The Type of the external identifier entity we want
     * @param gin The gin of the external identifier entity we want
     * @return An optional that contain an external identifier entity if it exists on the database
     */
    public Optional<ExternalIdentifier> getExternalIdentifierByIdentifierIdAndTypeAndGin(String identifier, String type, String gin) {
        ExternalIdentifier tmp = this.externalIdentifierRepository.findByIdentifierAndTypeAndGin(identifier, type,gin);
        if (tmp != null) {
            return Optional.of(tmp);
        }
        return Optional.empty();
    }

    /**
     * A mapper for the Get request of the microservice, it fetches all the ExternalIdentifierDataEntity of the database for all
     * externalIdentifierEntity of the list in parameter and map them into the response
     * @param externalIdentifierEntityList The List of ExternalIdentifier to map into the response
     * @return The mapped response
     */
    public ExternalIdentifierResponse setExternalIdentifierDataResponse(List<ExternalIdentifier> externalIdentifierEntityList) {
        ExternalIdentifierResponse externalIdentifierResponse = new ExternalIdentifierResponse();
        List<com.afklm.repind.msv.manage.external.identifier.models.tmp.stubs.ExternalIdentifier> externalIdentifierList = new ArrayList<>();

        for (ExternalIdentifier externalIdentifierEntity : externalIdentifierEntityList) {
            List<ExternalIdentifierDataEntity> externalIdentifierDataEntityList = this.getExternalIdentifierDataListByExternalIdentifierEntity(externalIdentifierEntity);
            externalIdentifierList.add(externalIdentifierTransformer.buildExternalIdentifier(externalIdentifierEntity, externalIdentifierDataEntityList));
        }

        externalIdentifierResponse.setExternalIdentifierList(externalIdentifierList);
        return externalIdentifierResponse;
    }

    /**
     * A method to delete a specific external identifier if it exists in the database, if the gin isn't null it will check if the gin
     * is the same before doing the deletion
     * @param identifier The identifierId of the externalIdentifierEntity we want to delete
     * @param type The type of the externalIdentifierEntity we want to delete
     * @param gin The gin of the externalIdentifierEntity we want to delete, it can be null
     * @throws BusinessException This exception is thrown if the ExternalIdentifier corresponding to all the entry parameter
     * is not found in the database
     */
    public void deleteExternalIdentifier(String identifier, String type, String gin) throws BusinessException {
        if(gin != null){
            Optional<ExternalIdentifier> tmp = this.getExternalIdentifierByIdentifierIdAndTypeAndGin(identifier,type,gin);
            if(!tmp.isPresent()){
                throw new BusinessException(BusinessError.ALL_DATA_NOT_FOUND);
            }
            this.deleteAllDataForOneExternalIdentifierEntity(tmp.get());
            return;
        }
        List<ExternalIdentifier> tmp = this.getAllExternalIdentifierByIdentifierIdAndType(identifier,type);
        if(tmp.isEmpty()){
            throw new BusinessException(BusinessError.ALL_DATA_NOT_FOUND);
        }
        for(ExternalIdentifier externalIdentifierEntity : tmp){
            this.deleteAllDataForOneExternalIdentifierEntity(externalIdentifierEntity);
        }
    }

    /**
     * This method delete all external identifier data entity related to the external identifier entity in parameter and
     * the external identifier entity itself
     * @param externalIdentifierEntity the external identifier entity to delete
     */
    public void deleteAllDataForOneExternalIdentifierEntity(ExternalIdentifier externalIdentifierEntity) {
        externalIdentifierDataRepository.deleteAllByIdentifierId(externalIdentifierEntity.getIdentifierId());
        externalIdentifierRepository.delete(externalIdentifierEntity);
    }
}
