package com.airfrance.repind.dto.reference;




import java.io.Serializable;



public class RefPermissionsIdDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * refPermissionsQuestionDTO
     */
	private RefPermissionsQuestionDTO questionId;
	
	
    /**
     * refComPrefDgt
     */
	private RefComPrefDgtDTO refComPrefDgt;


	public RefPermissionsIdDTO() {
		super();
	}


	public RefPermissionsIdDTO(RefPermissionsQuestionDTO questionId, RefComPrefDgtDTO refComPrefDgt) {
		super();
		this.questionId = questionId;
		this.refComPrefDgt = refComPrefDgt;
	}


	public RefPermissionsQuestionDTO getQuestionId() {
		return questionId;
	}


	public void setQuestionId(RefPermissionsQuestionDTO questionId) {
		this.questionId = questionId;
	}


	public RefComPrefDgtDTO getRefComPrefDgt() {
		return refComPrefDgt;
	}


	public void setRefComPrefDgt(RefComPrefDgtDTO refComPrefDgt) {
		this.refComPrefDgt = refComPrefDgt;
	}


	@Override
	public String toString() {
		return "RefPermissionsIdDTO [questionId=" + questionId + ", refComPrefDgt=" + refComPrefDgt + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((refComPrefDgt == null) ? 0 : refComPrefDgt.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefPermissionsIdDTO other = (RefPermissionsIdDTO) obj;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (refComPrefDgt == null) {
			if (other.refComPrefDgt != null)
				return false;
		} else if (!refComPrefDgt.equals(other.refComPrefDgt))
			return false;
		return true;
	}
}
