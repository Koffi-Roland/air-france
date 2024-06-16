package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefPermissionsQuestionResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
    
    @Mapping("id")
    @JsonProperty("id")
    private Integer refPermissionsQuestionId;
    
    private String name;
               
    private String question;
            
    private String questionEN;
    
    private Date dateCreation;
    
    private String siteCreation;
    
    private String signatureCreation;
    
    private Date dateModification;
    
    private String siteModification;
    
    private String signatureModification;

	private Long nbCompref;

	public Integer getRefPermissionsQuestionId() {
		return refPermissionsQuestionId;
	}

	public void setRefPermissionsQuestionId(Integer refPermissionsQuestionId) {
		this.refPermissionsQuestionId = refPermissionsQuestionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionEN() {
		return questionEN;
	}

	public void setQuestionEN(String questionEN) {
		this.questionEN = questionEN;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getSiteCreation() {
		return siteCreation;
	}

	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}

	public String getSignatureCreation() {
		return signatureCreation;
	}

	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getSiteModification() {
		return siteModification;
	}

	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}

	public String getSignatureModification() {
		return signatureModification;
	}

	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}

	public Long getNbCompref() {
		return nbCompref;
	}

	public void setNbCompref(Long nbCompref) {
		this.nbCompref = nbCompref;
	}
}
