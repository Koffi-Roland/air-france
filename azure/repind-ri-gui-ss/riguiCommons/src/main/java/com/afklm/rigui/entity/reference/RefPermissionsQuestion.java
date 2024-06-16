package com.afklm.rigui.entity.reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REF_PERMISSIONS_QUESTION")
public class RefPermissionsQuestion implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	@Id
	@Column(name = "REF_PERMISSIONS_QUESTION_ID", length = 12, nullable = false)
	@GeneratedValue(generator = "SEQ_REF_PERMISSIONS_QUESTION")
	@SequenceGenerator(name="SEQ_REF_PERMISSIONS_QUESTION", sequenceName = "SEQ_REF_PERMISSIONS_QUESTION",
			allocationSize = 1)
	private Integer id;

	/**
	 * name
	 */
	@Column(name = "SNAME", length = 30, nullable = false)
	private String name;

	/**
	 * question
	 */
	@Column(name = "SQUESTION", length = 255, nullable = false)
	private String question;

	/**
	 * questionEN
	 */
	@Column(name = "SQUESTION_EN", length = 255, nullable = false)
	private String questionEN;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION", nullable = false)
	private Date dateCreation;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", nullable = false)
	private String siteCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", nullable = false)
	private String signatureCreation;

	/**
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * siteModification
	 */
	@Column(name = "SSITE_MODIFICATION")
	private String siteModification;

	/**
	 * signatureModification
	 */
	@Column(name = "SSIGNATURE_MODIFICATION")
	private String signatureModification;

	public RefPermissionsQuestion() {
		super();
	}

	public RefPermissionsQuestion(Integer id, String name, String question, String questionEN, Date dateCreation,
								  String siteCreation, String signatureCreation, Date dateModification, String siteModification,
								  String signatureModification) {
		super();
		this.id = id;
		this.name = name;
		this.question = question;
		this.questionEN = questionEN;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "RefPermissionsQuestion [id=" + id + ", name=" + name + ", question=" + question + ", questionEN="
				+ questionEN + ", dateCreation=" + dateCreation + ", siteCreation=" + siteCreation
				+ ", signatureCreation=" + signatureCreation + ", dateModification=" + dateModification
				+ ", siteModification=" + siteModification + ", signatureModification=" + signatureModification + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreation == null) ? 0 : dateCreation.hashCode());
		result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((questionEN == null) ? 0 : questionEN.hashCode());
		result = prime * result + ((signatureCreation == null) ? 0 : signatureCreation.hashCode());
		result = prime * result + ((signatureModification == null) ? 0 : signatureModification.hashCode());
		result = prime * result + ((siteCreation == null) ? 0 : siteCreation.hashCode());
		result = prime * result + ((siteModification == null) ? 0 : siteModification.hashCode());
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
		RefPermissionsQuestion other = (RefPermissionsQuestion) obj;
		if (dateCreation == null) {
			if (other.dateCreation != null)
				return false;
		} else if (!dateCreation.equals(other.dateCreation))
			return false;
		if (dateModification == null) {
			if (other.dateModification != null)
				return false;
		} else if (!dateModification.equals(other.dateModification))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (questionEN == null) {
			if (other.questionEN != null)
				return false;
		} else if (!questionEN.equals(other.questionEN))
			return false;
		if (signatureCreation == null) {
			if (other.signatureCreation != null)
				return false;
		} else if (!signatureCreation.equals(other.signatureCreation))
			return false;
		if (signatureModification == null) {
			if (other.signatureModification != null)
				return false;
		} else if (!signatureModification.equals(other.signatureModification))
			return false;
		if (siteCreation == null) {
			if (other.siteCreation != null)
				return false;
		} else if (!siteCreation.equals(other.siteCreation))
			return false;
		if (siteModification == null) {
			if (other.siteModification != null)
				return false;
		} else if (!siteModification.equals(other.siteModification))
			return false;
		return true;
	}
}
