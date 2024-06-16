package com.afklm.repind.v6.provideindividualdata.type;

import java.util.ArrayList;
import java.util.List;

public class MaskedPaymentPreferences {
	
// Class copied from ProvideMaskedPaymentPreferencesResponse in order to be used by PaymentPreferencesDataHelper 

    protected String pointOfSale;
    protected String airlinePaymentPref;
    protected String paymentGroupType;
    protected String paymentMethod;
    protected List<MaskedFields> provideMaskedFields;
        
    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String value) {
        this.pointOfSale = value;
    }

    public String getAirlinePaymentPref() {
        return airlinePaymentPref;
    }

    public void setAirlinePaymentPref(String value) {
        this.airlinePaymentPref = value;
    }

    public String getPaymentGroupType() {
        return paymentGroupType;
    }

    public void setPaymentGroupType(String value) {
        this.paymentGroupType = value;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

    public List<MaskedFields> getMaskedFields() {
        if (provideMaskedFields == null) {
            provideMaskedFields = new ArrayList<MaskedFields>();
        }
        return this.provideMaskedFields;
    }
}
