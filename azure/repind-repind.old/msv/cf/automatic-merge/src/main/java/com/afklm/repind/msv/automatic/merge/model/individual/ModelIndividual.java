package com.afklm.repind.msv.automatic.merge.model.individual;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelIndividual {

    private String gin;
    private String type;
    private String civility;
    private String status;
    private String lastName;
    private String firstName;
    private String lastNameAlias;
    private String firstNameAlias;
    private String secondFirstName;
    private String sexe;
    private String birthDate;
    private String title;
    private String nationality;
    private String secondNationality;
    private String ginMerged;
    private Date dateMerged;
    private ModelSignature signature;
    private ModelProfile profilsdto;

    public String getGin() {
        return gin;
    }

    public void setGin(String gin) {
        this.gin = gin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNameAlias() {
        return lastNameAlias;
    }

    public void setLastNameAlias(String lastNameAlias) {
        this.lastNameAlias = lastNameAlias;
    }

    public String getFirstNameAlias() {
        return firstNameAlias;
    }

    public void setFirstNameAlias(String firstNameAlias) {
        this.firstNameAlias = firstNameAlias;
    }

    public String getSecondFirstName() {
        return secondFirstName;
    }

    public void setSecondFirstName(String secondFirstName) {
        this.secondFirstName = secondFirstName;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSecondNationality() {
        return secondNationality;
    }

    public void setSecondNationality(String secondNationality) {
        this.secondNationality = secondNationality;
    }

    public String getGinMerged() {
        return ginMerged;
    }

    public void setGinMerged(String ginMerged) {
        this.ginMerged = ginMerged;
    }

    public Date getDateMerged() {
        return dateMerged;
    }

    public void setDateMerged(Date dateMerged) {
        this.dateMerged = dateMerged;
    }

    public ModelSignature getSignature() {
        return signature;
    }

    public void setSignature(ModelSignature signature) {
        this.signature = signature;
    }

    public void convertBirthDateToString(Date birthDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.setBirthDate(format.format(birthDate));
    }

    public ModelProfile getProfilsdto() {
        return profilsdto;
    }

    public void setProfilsdto(ModelProfile profilsdto) {
        this.profilsdto = profilsdto;
    }

}
