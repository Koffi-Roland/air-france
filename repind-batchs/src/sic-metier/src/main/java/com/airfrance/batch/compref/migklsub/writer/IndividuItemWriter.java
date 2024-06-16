package com.airfrance.batch.compref.migklsub.writer;

import com.airfrance.batch.compref.migklsub.service.MigrationKLSubCounterService;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.ref.exception.MaximumSubscriptionsException;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("individuItemWriter")
public class IndividuItemWriter implements ItemWriter<Individu> {

    private final static Log log = LogFactory.getLog(IndividuItemWriter.class);

    @Autowired
    private CreateOrUpdateIndividualDS createOrUpdateIndividualDs;

    @Autowired
    private MigrationKLSubCounterService migrationKLSubCounterService;

    private final String FB_SUCESS_CODE = "2";
    private final String MA_SUCESS_CODE = "3";

    private final String CREATION_TYPE = "C";
    private final String MODIFICATION_TYPE = "M";

    @Override
    public void write(List<? extends Individu> iIndividus) {
        log.info("IndividuItemWriter");
        iIndividus.stream().forEach(i -> {
            try {
                CreateModifyIndividualResponseDTO response = createOrUpdateIndividualDs.createOrUpdateV8(encodeIndividuDTO(i), new CreateModifyIndividualResponseDTO());
                if(response.getSuccess()){
                    for (InformationResponseDTO informationResponseDTO : response.getInformationResponse()) {
                        String informationCode = informationResponseDTO.getInformation().getInformationCode();
                        if(FB_SUCESS_CODE.equals(informationCode)){
                            migrationKLSubCounterService.addFbSuccessCount();
                        }
                        if(MA_SUCESS_CODE.equals(informationCode)){
                            migrationKLSubCounterService.addMaSuccessCount();
                        }
                    }
                }
            } catch (Exception e) {
                if(e instanceof MaximumSubscriptionsException){
                    throw new RuntimeException();
                }
            }
        });
    }

    public CreateUpdateIndividualRequestDTO encodeIndividuDTO(Individu iIndividu){
        CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
        requestDTO.setIndividualRequestDTO(encodeIndividualRequestDTO(iIndividu));
        requestDTO.setRequestorDTO(encodeRequestorDTO(iIndividu));
        requestDTO.setCommunicationPreferencesRequestDTO(encodeCommunicationPreferencesRequestDTOs(iIndividu));
        requestDTO.setProcess(ProcessEnum.I.getCode());
        return requestDTO;
    }

    private RequestorDTO encodeRequestorDTO(Individu iIndividu) {
        RequestorDTO requestorDTO = new RequestorDTO();
        requestorDTO.setChannel("B2C");
        requestorDTO.setContext("BATCH_SCM_QVI");
        requestorDTO.setSignature(((CommunicationPreferences)iIndividu.getCommunicationpreferences().toArray()[0]).getModificationSignature());
        requestorDTO.setSite(IConstants.BATCH_QVI);
        return requestorDTO;
    }

    private List<CommunicationPreferencesRequestDTO> encodeCommunicationPreferencesRequestDTOs(Individu iIndividu) {
        return iIndividu.getCommunicationpreferences().stream().map(this::encodeCommunicationPreferencesRequestDTO).collect(Collectors.toList());
    }

    private CommunicationPreferencesRequestDTO encodeCommunicationPreferencesRequestDTO(CommunicationPreferences iCommunicationPreferences){
        CommunicationPreferencesRequestDTO communicationPreferencesRequestDTO = new CommunicationPreferencesRequestDTO();
        communicationPreferencesRequestDTO.setCommunicationPreferencesDTO(encodeCommunicationPreferencesDTO(iCommunicationPreferences));
        return communicationPreferencesRequestDTO;
    }

    private CommunicationPreferencesDTO encodeCommunicationPreferencesDTO(CommunicationPreferences iCommunicationPreferences) {
        CommunicationPreferencesDTO communicationPreferencesDTO = new CommunicationPreferencesDTO();
        communicationPreferencesDTO.setCommunicationType(iCommunicationPreferences.getComType());
        communicationPreferencesDTO.setMarketLanguageDTO(encodeMarketLanguageDTOs(iCommunicationPreferences));
        communicationPreferencesDTO.setDomain(iCommunicationPreferences.getDomain());
        communicationPreferencesDTO.setCommunicationGroupeType(iCommunicationPreferences.getComGroupType());
        communicationPreferencesDTO.setOptIn(iCommunicationPreferences.getSubscribe());
        return communicationPreferencesDTO;
    }

    private List<MarketLanguageDTO> encodeMarketLanguageDTOs(CommunicationPreferences iCommunicationPreferences) {
        return iCommunicationPreferences.getMarketLanguage().stream().map(this::encodeMarketLanguageDTO).collect(Collectors.toList());
    }

    private MarketLanguageDTO encodeMarketLanguageDTO(MarketLanguage iMarketLanguage){
        MarketLanguageDTO marketLanguageDTO = new MarketLanguageDTO();
        marketLanguageDTO.setDateOfConsent(iMarketLanguage.getDateOfConsent());
        marketLanguageDTO.setOptIn(iMarketLanguage.getOptIn());
        marketLanguageDTO.setLanguage(iMarketLanguage.getLanguage());
        marketLanguageDTO.setMarket(iMarketLanguage.getMarket());

        List<SignatureDTO> signatureList = encodeMarketLanguageSignature(iMarketLanguage);
        if (signatureList != null) {
            marketLanguageDTO.setSignatureDTOList(signatureList);
        }

        return marketLanguageDTO;
    }

    private List<SignatureDTO> encodeMarketLanguageSignature(MarketLanguage iMarketLanguage) {
        List<SignatureDTO> signatureList = new ArrayList<>();

        if (iMarketLanguage.getCreationSignature() != null
                || iMarketLanguage.getCreationSite() != null) {

            SignatureDTO creation = new SignatureDTO();
            creation.setSignatureType(CREATION_TYPE);
            creation.setSignature(iMarketLanguage.getCreationSignature());
            creation.setSignatureSite(iMarketLanguage.getCreationSite());
            creation.setDate(iMarketLanguage.getCreationDate());

            signatureList.add(creation);
        }

        if (iMarketLanguage.getModificationSignature() != null
                || iMarketLanguage.getModificationSite() != null) {

            SignatureDTO modification = new SignatureDTO();
            modification.setSignatureType(MODIFICATION_TYPE);
            modification.setSignature(iMarketLanguage.getModificationSignature());
            modification.setSignatureSite(iMarketLanguage.getModificationSite());
            modification.setDate(iMarketLanguage.getModificationDate());

            signatureList.add(modification);
        }

        return signatureList.isEmpty() ? null : signatureList;
    }

    private IndividualRequestDTO encodeIndividualRequestDTO(Individu iIndividu){
        IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
        individualRequestDTO.setIndividualInformationsDTO(encodeIndividualInformationsDTO(iIndividu));
        return individualRequestDTO;
    }

    private IndividualInformationsDTO encodeIndividualInformationsDTO(Individu iIndividu){
        IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
        RoleContrats roleContrats = (RoleContrats) iIndividu.getRolecontrats().toArray()[0];
        individualInformationsDTO.setIdentifier(roleContrats.getGin());
        return individualInformationsDTO;
    }
}
