package com.afklm.rigui.services.adhoc;

import com.afklm.rigui.enums.AdhocColumnEnum;
import com.afklm.rigui.model.individual.ModelAdhoc;
import com.afklm.rigui.model.individual.ModelAdhocResult;
import com.afklm.rigui.model.individual.requests.ModelAdhocRequest;
import com.afklm.rigui.services.helper.AdhocValidatorHelper;
import com.afklm.rigui.wrapper.adhoc.WrapperAdhoc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AdhocService {

    private final AdhocValidatorHelper adhocValidatorHelper;

    private final String TEMP_PATH = "/tmp/ADHOC_UPLOAD_%s.json";

    @Autowired
    public AdhocService(AdhocValidatorHelper adhocValidatorHelper) {
        this.adhocValidatorHelper = adhocValidatorHelper;
    }

    /**
     * Validate the request and return only the line in the request in error
     * @param adhocRequest
     * @param airlineCode
     * @return
     */
    public WrapperAdhoc validate(ModelAdhocRequest adhocRequest, String airlineCode) {
        List<ModelAdhoc> list = adhocRequest.getList();
        HashMap<Integer, ModelAdhocResult> errorsMap = new HashMap<>();
        List<ModelAdhocResult> result = list.stream().filter((modelAdhoc -> {
                    boolean isValid = true;
                    ModelAdhocResult modelAdhocResult = isModelAdhocValid(modelAdhoc, airlineCode);
                    if (!modelAdhocResult.getErrors().isEmpty()) {
                        isValid = false;
                        errorsMap.put(modelAdhoc.getId(), modelAdhocResult);
                    }
                    return !isValid;
                }))
                .map(modelAdhoc -> errorsMap.get(modelAdhoc.getId()))
                .toList();
        return new WrapperAdhoc(result);
    }

    /**
     * Check if the line is valid and return a item with all the field of the line that are in error
     * @param modelAdhoc
     * @param airlineCode
     * @return
     */
    private ModelAdhocResult isModelAdhocValid(ModelAdhoc modelAdhoc, String airlineCode) {
        ModelAdhocResult modelAdhocResult = new ModelAdhocResult(modelAdhoc);
        modelAdhocResult.setId(modelAdhoc.getId());
        LinkedHashMap<AdhocColumnEnum, Boolean> columnsValid = new LinkedHashMap<>();
        columnsValid.put(AdhocColumnEnum.EMAIL, adhocValidatorHelper.isEmailValid(modelAdhoc.getEmailAddress()));
        columnsValid.put(AdhocColumnEnum.GIN, adhocValidatorHelper.isGINValid(modelAdhoc.getGin()));
        columnsValid.put(AdhocColumnEnum.CIN, adhocValidatorHelper.isCINValid(modelAdhoc.getCin()));
        columnsValid.put(AdhocColumnEnum.FIRSTNAME, adhocValidatorHelper.isFirstnameValid(modelAdhoc.getFirstname()));
        columnsValid.put(AdhocColumnEnum.SURNAME, adhocValidatorHelper.isSurnameValid(modelAdhoc.getSurname()));
        columnsValid.put(AdhocColumnEnum.CIVILITY, adhocValidatorHelper.isCivilityValid(modelAdhoc.getCivility()));
        columnsValid.put(AdhocColumnEnum.BIRTHDATE, adhocValidatorHelper.isBirthDateValid(modelAdhoc.getBirthdate()));
        columnsValid.put(AdhocColumnEnum.COUNTRY_CODE, adhocValidatorHelper.isCountryCodeValid(modelAdhoc.getCountryCode()));
        columnsValid.put(AdhocColumnEnum.LANGUAGE_CODE, adhocValidatorHelper.isLanguageCodeValid(modelAdhoc.getLanguageCode()));
        columnsValid.put(AdhocColumnEnum.SUBSCRIPTION_TYPE, adhocValidatorHelper.isSubscriptionTypeValid(modelAdhoc.getSubscriptionType(), airlineCode));
        columnsValid.put(AdhocColumnEnum.DOMAIN, adhocValidatorHelper.isDomainValid(modelAdhoc.getDomain()));
        columnsValid.put(AdhocColumnEnum.GROUP_TYPE, adhocValidatorHelper.isGroupTypeValid(modelAdhoc.getGroupType()));
        columnsValid.put(AdhocColumnEnum.STATUS, adhocValidatorHelper.isStatusValid(modelAdhoc.getStatus()));
        columnsValid.put(AdhocColumnEnum.SOURCE, adhocValidatorHelper.isSourceValid(modelAdhoc.getSource()));
        columnsValid.put(AdhocColumnEnum.DATE_OF_CONSENT, adhocValidatorHelper.isDateOfConsentValid(modelAdhoc.getDateOfConsent()));
        columnsValid.put(AdhocColumnEnum.PREFERRED_DEPARTURE_AIRPORT, adhocValidatorHelper.isPreferredDepartureAirport(modelAdhoc.getPreferredDepartureAirport()));
        modelAdhocResult.setErrors(
                columnsValid.entrySet().stream()
                        .filter(entry -> !entry.getValue())
                        .map(Map.Entry::getKey).toList());
        return modelAdhocResult;
    }

    /**
     * Upload a json file to Azure container. The json file will be replicated on ONPRIME site automatically
     * The lines in error will be automatically skipped
     * @param adhocRequest
     * @param airlineCode
     * @return
     */
    public boolean upload(ModelAdhocRequest adhocRequest, String airlineCode) {
        boolean ok = true;
        List<ModelAdhoc> list = adhocRequest.getList();
        WrapperAdhoc result = validate(adhocRequest, airlineCode);
        List<Integer> invalidIds = result.getResult().stream().map((ModelAdhocResult::getId)).toList();
        list = list.stream().filter((modelAdhoc -> !invalidIds.contains(modelAdhoc.getId()))).toList();
        if (list.isEmpty()) {
            return false;
        }
        // CREATE TEMP FILE
        File file = new File(String.format(TEMP_PATH, new Date().getTime()));
        try {
            new ObjectMapper().writeValue(file, list);
        } catch (IOException e) {
            ok = false;
        }
        // UPLOAD FILE HERE
        if(ok) {
            ok = uploadAdhocFile(file);
        }
        // DELETE TEMP FILE AT THE END
        ok = ok && file.delete();
        return ok;
    }
    
    private boolean uploadAdhocFile(File file) {
        boolean ok = false;
        //TODO: upload file with StorageAzureService once the config are done in REPIND-3328
        return ok;
    }
}
