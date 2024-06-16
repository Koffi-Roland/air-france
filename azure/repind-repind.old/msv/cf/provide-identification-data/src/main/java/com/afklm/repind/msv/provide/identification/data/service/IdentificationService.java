package com.afklm.repind.msv.provide.identification.data.service;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.individual.UsageClientEntity;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.individual.UsageClientRepository;
import com.afklm.repind.msv.provide.identification.data.transform.IdentificationTransform;
import com.afklm.soa.stubs.r000378.v1.model.IdentificationDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/*
 * A service used to map the response of the identification part of the request of this MS
 */
public class IdentificationService {

    private IndividuRepository individuRepository;

    private UsageClientRepository usageClientRepository;

    private ProfilsRepository profilsRepository;
    private IdentificationTransform identificationTransform;

    /**
     * Getter For individual by his gin
     * @param gin 12 character String used to identify an individual
     * @return Individu object corresponding to the provided GIN, it can be null
     */
    public Individu getIndividuByGin(String gin) {
        return this.individuRepository.getIndividuByGin(gin);
    }

    /**
     * Getter For usage Client by gin
     * @param gin 12 character String used to identify an individual
     * @return List of all Usage Client for a provided Gin, it can be empty
     */
    public List<UsageClientEntity> getAllUsageClientByGin(String gin) {
        return this.usageClientRepository.getUsageClientEntitiesByGin(gin);
    }

    /**
     * Getter for Profile by gin
     * @param gin 12 character String used to identify an individual
     * @return Profil object corresponding to provided Gin, it can be null
     */
    public ProfilsEntity getProfilByGin(String gin) {
        return this.profilsRepository.getProfilsEntityByGin(gin);
    }

    /**
     * Setter for IdentificationDataResponse
     * @param individu     The Individu object used to create the response
     * @param usagesClient The UsageClient list object used to create the response
     * @param languageCode A string corresponding to one of the parameter of the reponse
     * @return An IdentificationDataResponse object based on the inputs
     */
    public IdentificationDataResponse setIdentificationDataResponse(Individu individu, List<UsageClientEntity> usagesClient, String languageCode) {
        return this.identificationTransform.setIdentificationDataResponse(individu, usagesClient, languageCode);
    }
}
