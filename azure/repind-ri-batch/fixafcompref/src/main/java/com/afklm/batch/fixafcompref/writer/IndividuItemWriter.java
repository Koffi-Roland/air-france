package com.afklm.batch.fixafcompref.writer;

import com.afklm.batch.fixafcompref.enums.FileNameEnum;
import com.afklm.batch.fixafcompref.helper.ValidationHelper;
import com.afklm.batch.fixafcompref.logger.FixAfComPrefLogger;
import com.afklm.batch.fixafcompref.service.FixAfComPrefCounterService;
import com.airfrance.batch.common.utils.IConstants;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("individuItemWriter")
@Slf4j
public class IndividuItemWriter implements ItemWriter<Individu> {

    @Autowired
    private CreateOrUpdateIndividualDS createOrUpdateIndividualDs;

    @Autowired
    private FixAfComPrefCounterService fixAfComPrefCounterService;

    @Autowired
    private Map<String , FieldSet> mapContext;

    @Autowired
    private ValidationHelper validationHelper;

    @Autowired
    private FixAfComPrefLogger fixAfComPrefLogger;

    private final String FB_SUCESS_CODE = "2";
    private final String MA_SUCESS_CODE = "3";

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
                            fixAfComPrefCounterService.addFbSuccessCounter();
                        }
                        if(MA_SUCESS_CODE.equals(informationCode)){
                            fixAfComPrefCounterService.addMaSuccessCounter();
                        }
                    }
                }
            } catch (Exception e) {
                //TODO: handle exception if needed
                log.info(e.toString());
                fixAfComPrefLogger.write(FileNameEnum.ERROR , validationHelper.getFieldSetData(validationHelper.returnUnicityCode(i)));
            }
            mapContext.remove(validationHelper.returnUnicityCode(i));
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
        return marketLanguageDTO;
    }

    private IndividualRequestDTO encodeIndividualRequestDTO(Individu iIndividu){
        IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
        individualRequestDTO.setIndividualInformationsDTO(encodeIndividualInformationsDTO(iIndividu));
        return individualRequestDTO;
    }

    private IndividualInformationsDTO encodeIndividualInformationsDTO(Individu iIndividu){
        IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
        CommunicationPreferences communicationPreferences = (CommunicationPreferences) iIndividu.getCommunicationpreferences().toArray()[0];
        individualInformationsDTO.setIdentifier(communicationPreferences.getGin());
        return individualInformationsDTO;
    }
}
