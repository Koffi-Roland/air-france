package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;

import com.afklm.cati.common.entity.RefPermissionsQuestion;

public class RefPermissionsIdResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;

    private RefPermissionsQuestion questionId;
    
    private RefComPrefDgtResource refComPrefDgt;

	/**
	 * @return the questionId
	 */
	public RefPermissionsQuestion getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(RefPermissionsQuestion questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the refComPrefDgt
	 */
	public RefComPrefDgtResource getRefComPrefDgt() {
		return refComPrefDgt;
	}

	/**
	 * @param refComPrefDgt the refComPrefDgt to set
	 */
	public void setRefComPrefDgt(RefComPrefDgtResource refComPrefDgt) {
		this.refComPrefDgt = refComPrefDgt;
	}
  
}
