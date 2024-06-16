package com.afklm.rigui.services.builder.w000442;

import java.util.stream.Collectors;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.Alert;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.AlertRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.requests.ModelAlertRequest;
import com.afklm.rigui.services.builder.RequestType;

@Component
public class AlertRequestBuilder extends W000442RequestBuilder {

    @Override
    public CreateUpdateIndividualRequest buildCreateRequest(final String id, final Object model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CreateUpdateIndividualRequest buildUpdateRequest(final String id, final Object model) {
        CreateUpdateIndividualRequest request = null;
        request = mergeCommonRequestElements(id);
        AlertRequest alertRequest = getAlertRequest(RequestType.UPDATE, (ModelAlertRequest) model);
        request.setProcess("A");
        request.setAlertRequest(alertRequest);

        //Communication preference is mandatory to create an alert
        ComunicationPreferencesRequest comunicationPreferencesRequest = new ComunicationPreferencesRequest();
        CommunicationPreferences comPref = new CommunicationPreferences();
        comPref.setDomain("S");
        comPref.setCommunicationGroupeType("P");
        comPref.setCommunicationType("AF");
        comunicationPreferencesRequest.setCommunicationPreferences(comPref);
        request.getComunicationPreferencesRequest().add(comunicationPreferencesRequest);

        return request;
    }

    @Override
    public CreateUpdateIndividualRequest buildDeleteRequest(final String id, final Object model) {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the alert request according to the parameters (the type of request and the alert's model)
     * @param type (RequestType)
     * @param modelAlert (ModelAlertRequest)
     * @return a AlertRequest object
     */
    private AlertRequest getAlertRequest(RequestType type, ModelAlertRequest modelAlert) {
        AlertRequest alertRequest = new AlertRequest();
        Alert alert = new Alert();

        if(type == RequestType.UPDATE) {
            alert.setAlertId(Integer.toString(modelAlert.getAlertId()));
            alert.setOptIn(modelAlert.getOptIn());
            alert.setType(modelAlert.getType());
        }

        alertRequest.getAlert().add(alert);

        return alertRequest;
    }

}
