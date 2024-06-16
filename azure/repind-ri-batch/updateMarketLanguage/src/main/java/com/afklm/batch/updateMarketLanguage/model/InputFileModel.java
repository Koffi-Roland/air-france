package com.afklm.batch.updateMarketLanguage.model;

public class InputFileModel {

    private String gin;
    private String currentCountryCode;
    private String newMarketCode;
    private String newLanguageCode;

    public InputFileModel(String gin, String currentCountryCode, String newMarketCode, String newLanguageCode) {
        this.gin = gin;
        this.currentCountryCode = currentCountryCode;
        this.newLanguageCode = newLanguageCode;
        this.newMarketCode = newMarketCode;
    }

    public String getGin() {
        return gin;
    }

    public void setGin(String gin) {
        this.gin = gin;
    }

    public String getCurrentCountryCode() {
        return currentCountryCode;
    }

    public void setCurrentCountryCode(String currentCountryCode) {
        this.currentCountryCode = currentCountryCode;
    }

    public String getNewMarketCode() {
        return newMarketCode;
    }

    public void setNewMarketCode(String newMarketCode) {
        this.newMarketCode = newMarketCode;
    }

    public String getNewLanguageCode() {
        return newLanguageCode;
    }

    public void setNewLanguageCode(String newLanguageCode) {
        this.newLanguageCode = newLanguageCode;
    }
}
