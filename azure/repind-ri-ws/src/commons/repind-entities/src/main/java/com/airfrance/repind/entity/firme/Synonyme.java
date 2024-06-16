package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_jfGAQLbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : Synonyme.java
 * </p>
 * BO Synonyme
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "SYNONYME")
public class Synonyme implements Serializable {

	/* PROTECTED REGION ID(serialUID _jfGAQLbCEeCrCZp8iGNNVw) ENABLED START */
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version of this
	 * class is not compatible with old versions. See Sun docs for <a
	 * href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
	 * /serialization/spec/class.html#4100> details. </a>
	 *
	 * Not necessary to include in first version of the class, but included here as
	 * a reminder of its importance.
	 */
	private static final long serialVersionUID = 1L;
	/* PROTECTED REGION END */

	/**
	 * cle
	 */
	@Id
	@Column(name = "ICLE", length = 12, nullable = false)
	@SequenceGenerator(name = "ISEQ_SYNONYME", sequenceName = "ISEQ_SYNONYME",
		allocationSize = 1)
	@GeneratedValue(generator = "ISEQ_SYNONYME")
	private Long cle;

	/**
	 * statut
	 */
	@Column(name = "SSTATUT", length = 1, nullable = false)
	private String statut;

	/**
	 * nom
	 */
	@Column(name = "SNOM", length = 45, nullable = false)
	private String nom;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 2, nullable = false)
	private String type;

	/**
	 * dateModificationSnom
	 */
	@Column(name = "DDATE_MODIFICATION_SNOM")
	private Date dateModificationSnom;

	/**
	 * personneMorale
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "SGIN", nullable = false)
	@ForeignKey(name = "SY_PM_FK")
	private PersonneMorale personneMorale;

	/* PROTECTED REGION ID(_jfGAQLbCEeCrCZp8iGNNVw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public Synonyme() {
	}

	/**
	 * full constructor
	 * 
	 * @param pCle                  cle
	 * @param pStatut               statut
	 * @param pNom                  nom
	 * @param pType                 type
	 * @param pDateModificationSnom dateModificationSnom
	 */
	public Synonyme(Long pCle, String pStatut, String pNom, String pType, Date pDateModificationSnom) {
		this.cle = pCle;
		this.statut = pStatut;
		this.nom = pNom;
		this.type = pType;
		this.dateModificationSnom = pDateModificationSnom;
	}

	/**
	 *
	 * @return cle
	 */
	public Long getCle() {
		return this.cle;
	}

	/**
	 *
	 * @param pCle cle value
	 */
	public void setCle(Long pCle) {
		this.cle = pCle;
	}

	/**
	 *
	 * @return dateModificationSnom
	 */
	public Date getDateModificationSnom() {
		return this.dateModificationSnom;
	}

	/**
	 *
	 * @param pDateModificationSnom dateModificationSnom value
	 */
	public void setDateModificationSnom(Date pDateModificationSnom) {
		this.dateModificationSnom = pDateModificationSnom;
	}

	/**
	 *
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 *
	 * @param pNom nom value
	 */
	public void setNom(String pNom) {
		this.nom = pNom;
	}

	/**
	 *
	 * @return personneMorale
	 */
	public PersonneMorale getPersonneMorale() {
		return this.personneMorale;
	}

	/**
	 *
	 * @param pPersonneMorale personneMorale value
	 */
	public void setPersonneMorale(PersonneMorale pPersonneMorale) {
		this.personneMorale = pPersonneMorale;
	}

	/**
	 *
	 * @return statut
	 */
	public String getStatut() {
		return this.statut;
	}

	/**
	 *
	 * @param pStatut statut value
	 */
	public void setStatut(String pStatut) {
		this.statut = pStatut;
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
	 * @param pType type value
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
		/* PROTECTED REGION ID(toString_jfGAQLbCEeCrCZp8iGNNVw) ENABLED START */
		result = toStringImpl();
		/* PROTECTED REGION END */
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
		buffer.append("cle=").append(getCle());
		buffer.append(",");
		buffer.append("statut=").append(getStatut());
		buffer.append(",");
		buffer.append("nom=").append(getNom());
		buffer.append(",");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("dateModificationSnom=").append(getDateModificationSnom());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _jfGAQLbCEeCrCZp8iGNNVw) ENABLED START */

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
		final Synonyme other = (Synonyme) obj;

		// TODO: writes or generates equals method

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

		// TODO: writes or generates hashcode method

		return result;
	}

	/* PROTECTED REGION END */

	/**
	 * Generated implementation method for hashCode You can stop calling it in the
	 * hashCode() generated in protected region if necessary
	 * 
	 * @return hashcode
	 */
	private int hashCodeImpl() {
		return super.hashCode();
	}

	/**
	 * Generated implementation method for equals You can stop calling it in the
	 * equals() generated in protected region if necessary
	 * 
	 * @return if param equals the current object
	 * @param obj Object to compare with current
	 */
	private boolean equalsImpl(Object obj) {
		return super.equals(obj);
	}

	/* PROTECTED REGION ID(_jfGAQLbCEeCrCZp8iGNNVw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
