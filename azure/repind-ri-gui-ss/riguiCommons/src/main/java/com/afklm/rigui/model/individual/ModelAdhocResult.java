package com.afklm.rigui.model.individual;

import com.afklm.rigui.enums.AdhocColumnEnum;
import com.afklm.rigui.model.individual.requests.ModelAdhocRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ModelAdhocResult extends ModelAdhoc {

    public ModelAdhocResult(ModelAdhoc modelAdhoc) {
        super(
                modelAdhoc.getId(),
                modelAdhoc.getEmailAddress(),
                modelAdhoc.getGin(),
                modelAdhoc.getCin(),
                modelAdhoc.getFirstname(),
                modelAdhoc.getSurname(),
                modelAdhoc.getCivility(),
                modelAdhoc.getBirthdate(),
                modelAdhoc.getCountryCode(),
                modelAdhoc.getLanguageCode(),
                modelAdhoc.getSubscriptionType(),
                modelAdhoc.getDomain(),
                modelAdhoc.getGroupType(),
                modelAdhoc.getStatus(),
                modelAdhoc.getSource(),
                modelAdhoc.getDateOfConsent(),
                modelAdhoc.getPreferredDepartureAirport()
        );
        errors = new ArrayList<>();
    }

    private List<AdhocColumnEnum> errors;

    public List<String> getErrors() {
        return errors.stream().map(AdhocColumnEnum::column).toList();
    }

    public void setErrors(List<AdhocColumnEnum> errors) {
        this.errors = errors;
    }
}
