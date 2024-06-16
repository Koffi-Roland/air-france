package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_OZPxEFpwEeK2gpxNhBlpTw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.dto.individu.enums.IdentifierTypeEnum;
import org.apache.commons.lang.builder.ToStringBuilder;

/*PROTECTED REGION END*/

/**
 * <p>Title : MyAccountUpdateConnectionDataV2DTO</p>
 *
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MyAccountUpdateConnectionDataV2DTO {

    /**
     * gin
     */
    private String gin;


    /**
     * updateType
     */
    private String updateType;


    /**
     * connectionIdentifier
     */
    private String connectionIdentifier;


    /**
     * connectionIdentifierType
     */
    private IdentifierTypeEnum connectionIdentifierType;


    /**
     * personalQuestion
     */
    private String personalQuestion;


    /**
     * personalAnswer
     */
    private String personalAnswer;


    /**
     * newPassword
     */
    private String newPassword;


    /**
     * currentPassword
     */
    private String currentPassword;


    /**
     * generatedPasswordFlag
     */
    private Integer generatedPasswordFlag;

    /**
     * status
     */
    private String status;


    /**
     * signature
     */
    private SignatureDTO signature;

    /**
     * default constructor
     */
    public MyAccountUpdateConnectionDataV2DTO() {

    }


    /**
     * full constructor
     *
     * @param pGin                      gin
     * @param pUpdateType               updateType
     * @param pConnectionIdentifier     connectionIdentifier
     * @param pConnectionIdentifierType connectionIdentifierType
     * @param pPersonalQuestion         personalQuestion
     * @param pPersonalAnswer           personalAnswer
     * @param pNewPassword              newPassword
     * @param pCurrentPassword          currentPassword
     * @param pGeneratedPasswordFlag    generatedPasswordFlag
     * @param pStatus                   status
     */
    public MyAccountUpdateConnectionDataV2DTO(String pGin, String pUpdateType, String pConnectionIdentifier, IdentifierTypeEnum pConnectionIdentifierType, String pPersonalQuestion, String pPersonalAnswer, String pNewPassword, String pCurrentPassword, Integer pGeneratedPasswordFlag, Integer pPasswordToChange, String pStatus) {
        this.gin = pGin;
        this.updateType = pUpdateType;
        this.connectionIdentifier = pConnectionIdentifier;
        this.connectionIdentifierType = pConnectionIdentifierType;
        this.personalQuestion = pPersonalQuestion;
        this.personalAnswer = pPersonalAnswer;
        this.newPassword = pNewPassword;
        this.currentPassword = pCurrentPassword;
        this.generatedPasswordFlag = pGeneratedPasswordFlag;
        this.status = pStatus;
    }

    /**
     * @return connectionIdentifier
     */
    public String getConnectionIdentifier() {
        return this.connectionIdentifier;
    }

    /**
     * @param pConnectionIdentifier connectionIdentifier value
     */
    public void setConnectionIdentifier(String pConnectionIdentifier) {
        this.connectionIdentifier = pConnectionIdentifier;
    }

    /**
     * @return connectionIdentifierType
     */
    public IdentifierTypeEnum getConnectionIdentifierType() {
        return this.connectionIdentifierType;
    }

    /**
     * @param pConnectionIdentifierType connectionIdentifierType value
     */
    public void setConnectionIdentifierType(IdentifierTypeEnum pConnectionIdentifierType) {
        this.connectionIdentifierType = pConnectionIdentifierType;
    }

    /**
     * @return currentPassword
     */
    public String getCurrentPassword() {
        return this.currentPassword;
    }

    /**
     * @param pCurrentPassword currentPassword value
     */
    public void setCurrentPassword(String pCurrentPassword) {
        this.currentPassword = pCurrentPassword;
    }

    /**
     * @return generatedPasswordFlag
     */
    public Integer getGeneratedPasswordFlag() {
        return this.generatedPasswordFlag;
    }

    /**
     * @param pGeneratedPasswordFlag generatedPasswordFlag value
     */
    public void setGeneratedPasswordFlag(Integer pGeneratedPasswordFlag) {
        this.generatedPasswordFlag = pGeneratedPasswordFlag;
    }

    /**
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     * @return newPassword
     */
    public String getNewPassword() {
        return this.newPassword;
    }

    /**
     * @param pNewPassword newPassword value
     */
    public void setNewPassword(String pNewPassword) {
        this.newPassword = pNewPassword;
    }

    /**
     * @return personalAnswer
     */
    public String getPersonalAnswer() {
        return this.personalAnswer;
    }

    /**
     * @param pPersonalAnswer personalAnswer value
     */
    public void setPersonalAnswer(String pPersonalAnswer) {
        this.personalAnswer = pPersonalAnswer;
    }

    /**
     * @return personalQuestion
     */
    public String getPersonalQuestion() {
        return this.personalQuestion;
    }

    /**
     * @param pPersonalQuestion personalQuestion value
     */
    public void setPersonalQuestion(String pPersonalQuestion) {
        this.personalQuestion = pPersonalQuestion;
    }

    /**
     * @return signature
     */
    public SignatureDTO getSignature() {
        return this.signature;
    }

    /**
     * @param pSignature signature value
     */
    public void setSignature(SignatureDTO pSignature) {
        this.signature = pSignature;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     * @return updateType
     */
    public String getUpdateType() {
        return this.updateType;
    }

    /**
     * @param pUpdateType updateType value
     */
    public void setUpdateType(String pUpdateType) {
        this.updateType = pUpdateType;
    }

    /**
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_OZPxEFpwEeK2gpxNhBlpTw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
                .append("gin", getGin())
                .append("updateType", getUpdateType())
                .append("connectionIdentifier", getConnectionIdentifier())
                .append("connectionIdentifierType", getConnectionIdentifierType())
                .append("personalQuestion", getPersonalQuestion())
                .append("personalAnswer", getPersonalAnswer())
                .append("newPassword", getNewPassword())
                .append("currentPassword", getCurrentPassword())
                .append("generatedPasswordFlag", getGeneratedPasswordFlag())
                .append("status", getStatus())
                .toString();
    }

    /*PROTECTED REGION ID(_OZPxEFpwEeK2gpxNhBlpTw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}