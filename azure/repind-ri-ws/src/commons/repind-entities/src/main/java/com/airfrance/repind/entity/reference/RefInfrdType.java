package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "REF_INFRD_TYPE")
public class RefInfrdType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
	 * code
	 */
	@Id
	@Column(name = "SCODE", length = 3, nullable = false)
	private String code;
        
            
    /**
	 * libelle
	 */
	@Column(name = "SLIBELLE", length = 25)
	private String libelle;
        
            
    /**
	 * libelleEng
	 */
	@Column(name = "SLIBELLE_EN", length = 25)
	private String libelleEng;

	public RefInfrdType() {
		super();
	}

	public RefInfrdType(String code, String libelle, String libelleEng) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.libelleEng = libelleEng;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle
	 *            the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the libelleEng
	 */
	public String getLibelleEng() {
		return libelleEng;
	}

	/**
	 * @param libelleEng
	 *            the libelleEng to set
	 */
	public void setLibelleEng(String libelleEng) {
		this.libelleEng = libelleEng;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((libelleEng == null) ? 0 : libelleEng.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefInfrdType other = (RefInfrdType) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (libelleEng == null) {
			if (other.libelleEng != null)
				return false;
		} else if (!libelleEng.equals(other.libelleEng))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefInfrdType [code=" + code + ", libelle=" + libelle + ", libelleEng=" + libelleEng + "]";
	}
}
