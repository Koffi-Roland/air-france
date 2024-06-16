package com.airfrance.repind.entity.firme;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.io.Serializable;

/**
 * <p>
 * Title : Etablissement.java
 * </p>
 * BO Etablissement
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@DiscriminatorValue("T")
@SecondaryTable(name = "ETABLISSEMENT")
public class EtablissementLight extends PersonneMoraleLight implements Serializable {

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here
	 * as a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * type
	 */
	@Column(table = "ETABLISSEMENT", name = "STYPE", length = 2, nullable = false)
	private String type;

	/**
	 * sginAgence
	 */
	@Column(table = "ETABLISSEMENT", name = "SGIN_AGENCE", length = 12)
	private String ginAgence;

	/**
	 * siret
	 */
	@Column(table = "ETABLISSEMENT", name = "SSIRET", length = 14)
	private String siret;

	/**
	 * siegeSocial
	 */
	@Column(table = "ETABLISSEMENT", name = "SSIEGE_SOCIAL", length = 1, nullable = false)
	private String siegeSocial;

	/**
	 * sce
	 */
	@Column(table = "ETABLISSEMENT", name = "SCE", length = 1)
	private String ce;
	/**
	 * srem
	 */
	@Column(table = "ETABLISSEMENT", name = "SREM", length = 225)
	private String rem;

	/**
	 * default constructor
	 */
	public EtablissementLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pType
	 *            type
	 * @param pGinAgence
	 *            ginAgence
	 * @param pSiret
	 *            siret
	 * @param pSiegeSocial
	 *            siegeSocial
	 * @param pCe
	 *            ce
	 * @param pRem
	 *            rem
	 */
	public EtablissementLight(String pType, String pGinAgence, String pSiret, String pSiegeSocial, String pCe,
                              String pRem) {
		this.type = pType;
		this.ginAgence = pGinAgence;
		this.siret = pSiret;
		this.siegeSocial = pSiegeSocial;
		this.ce = pCe;
		this.rem = pRem;
	}

	/**
	 *
	 * @return ce
	 */
	public String getCe() {
		return this.ce;
	}

	/**
	 *
	 * @param pCe
	 *            ce value
	 */
	public void setCe(String pCe) {
		this.ce = pCe;
	}

	/**
	 *
	 * @return ginAgence
	 */
	public String getGinAgence() {
		return this.ginAgence;
	}

	/**
	 *
	 * @param pGinAgence
	 *            ginAgence value
	 */
	public void setGinAgence(String pGinAgence) {
		this.ginAgence = pGinAgence;
	}

	/**
	 *
	 * @return rem
	 */
	public String getRem() {
		return this.rem;
	}

	/**
	 *
	 * @param pRem
	 *            rem value
	 */
	public void setRem(String pRem) {
		this.rem = pRem;
	}

	/**
	 *
	 * @return siegeSocial
	 */
	public String getSiegeSocial() {
		return this.siegeSocial;
	}

	/**
	 *
	 * @param pSiegeSocial
	 *            siegeSocial value
	 */
	public void setSiegeSocial(String pSiegeSocial) {
		this.siegeSocial = pSiegeSocial;
	}

	/**
	 *
	 * @return siret
	 */
	public String getSiret() {
		return this.siret;
	}

	/**
	 *
	 * @param pSiret
	 *            siret value
	 */
	public void setSiret(String pSiret) {
		this.siret = pSiret;
	}

	/**
	 *
	 * @return type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 *
	 * @param pType
	 *            type value
	 */
	public void setType(String pType) {
		this.type = pType;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		result = toStringImpl();
		return result;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toStringImpl() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
		buffer.append("[");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("ginAgence=").append(getGinAgence());
		buffer.append(",");
		buffer.append("siret=").append(getSiret());
		buffer.append(",");
		buffer.append("siegeSocial=").append(getSiegeSocial());
		buffer.append(",");
		buffer.append("ce=").append(getCe());
		buffer.append(",");
		buffer.append("rem=").append(getRem());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final EtablissementLight other = (EtablissementLight) obj;

		return super.equals(other);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

}
