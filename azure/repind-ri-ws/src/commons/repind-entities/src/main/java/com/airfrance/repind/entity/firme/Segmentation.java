package com.airfrance.repind.entity.firme;

/*PROTECTED REGION ID(_iXamgLbCEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : Segmentation.java
 * </p>
 * BO Segmentation
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "SEGMENTATION")
public class Segmentation implements Serializable {

	/* PROTECTED REGION ID(serialUID _iXamgLbCEeCrCZp8iGNNVw) ENABLED START */
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
	@Column(name = "ICLE", nullable = false)
	@SequenceGenerator(name = "ISEQ_SEGMENTATION", sequenceName = "ISEQ_SEGMENTATION",
	allocationSize = 1)
	@GeneratedValue(generator = "ISEQ_SEGMENTATION")
	private Integer cle;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 3)
	private String type;

	/**
	 * niveau
	 */
	@Column(name = "SNIVEAU")
	private String niveau;

	/**
	 * dateEntree
	 */
	@Column(name = "DdATE_ENTREE", nullable = false)
	private Date dateEntree;

	/**
	 * dateSortie
	 */
	@Column(name = "DDATE_SORTIE")
	private Date dateSortie;

	/**
	 * potentiel
	 */
	@Column(name = "POTENTIEL", length = 10)
	private String potentiel;

	/**
	 * montant
	 */
	@Column(name = "MONTANT")
	private Integer montant;

	/**
	 * monnaie
	 */
	@Column(name = "MONNAIE", length = 3)
	private String monnaie;

	/**
	 * politiqueVoyage
	 */
	@Column(name = "POLITIQUE_VOYAGE", length = 2)
	private String politiqueVoyage;

	/**
	 * personneMorale
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "SGIN", nullable = false)
	@ForeignKey(name = "FK_PM_SEG")
	private PersonneMorale personneMorale;

	/* PROTECTED REGION ID(_iXamgLbCEeCrCZp8iGNNVw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public Segmentation() {
	}

	/**
	 * full constructor
	 * 
	 * @param pCle             cle
	 * @param pType            type
	 * @param pNiveau          niveau
	 * @param pDateEntree      dateEntree
	 * @param pDateSortie      dateSortie
	 * @param pPotentiel       potentiel
	 * @param pMontant         montant
	 * @param pMonnaie         monnaie
	 * @param pPolitiqueVoyage politiqueVoyage
	 */
	public Segmentation(Integer pCle, String pType, String pNiveau, Date pDateEntree, Date pDateSortie,
			String pPotentiel, Integer pMontant, String pMonnaie, String pPolitiqueVoyage) {
		this.cle = pCle;
		this.type = pType;
		this.niveau = pNiveau;
		this.dateEntree = pDateEntree;
		this.dateSortie = pDateSortie;
		this.potentiel = pPotentiel;
		this.montant = pMontant;
		this.monnaie = pMonnaie;
		this.politiqueVoyage = pPolitiqueVoyage;
	}

	/**
	 *
	 * @return cle
	 */
	public Integer getCle() {
		return this.cle;
	}

	/**
	 *
	 * @param pCle cle value
	 */
	public void setCle(Integer pCle) {
		this.cle = pCle;
	}

	/**
	 *
	 * @return dateEntree
	 */
	public Date getDateEntree() {
		return this.dateEntree;
	}

	/**
	 *
	 * @param pDateEntree dateEntree value
	 */
	public void setDateEntree(Date pDateEntree) {
		this.dateEntree = pDateEntree;
	}

	/**
	 *
	 * @return dateSortie
	 */
	public Date getDateSortie() {
		return this.dateSortie;
	}

	/**
	 *
	 * @param pDateSortie dateSortie value
	 */
	public void setDateSortie(Date pDateSortie) {
		this.dateSortie = pDateSortie;
	}

	/**
	 *
	 * @return monnaie
	 */
	public String getMonnaie() {
		return this.monnaie;
	}

	/**
	 *
	 * @param pMonnaie monnaie value
	 */
	public void setMonnaie(String pMonnaie) {
		this.monnaie = pMonnaie;
	}

	/**
	 *
	 * @return montant
	 */
	public Integer getMontant() {
		return this.montant;
	}

	/**
	 *
	 * @param pMontant montant value
	 */
	public void setMontant(Integer pMontant) {
		this.montant = pMontant;
	}

	/**
	 *
	 * @return niveau
	 */
	public String getNiveau() {
		return this.niveau;
	}

	/**
	 *
	 * @param pNiveau niveau value
	 */
	public void setNiveau(String pNiveau) {
		this.niveau = pNiveau;
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
	 * @return politiqueVoyage
	 */
	public String getPolitiqueVoyage() {
		return this.politiqueVoyage;
	}

	/**
	 *
	 * @param pPolitiqueVoyage politiqueVoyage value
	 */
	public void setPolitiqueVoyage(String pPolitiqueVoyage) {
		this.politiqueVoyage = pPolitiqueVoyage;
	}

	/**
	 *
	 * @return potentiel
	 */
	public String getPotentiel() {
		return this.potentiel;
	}

	/**
	 *
	 * @param pPotentiel potentiel value
	 */
	public void setPotentiel(String pPotentiel) {
		this.potentiel = pPotentiel;
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
		/* PROTECTED REGION ID(toString_iXamgLbCEeCrCZp8iGNNVw) ENABLED START */
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
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("niveau=").append(getNiveau());
		buffer.append(",");
		buffer.append("dateEntree=").append(getDateEntree());
		buffer.append(",");
		buffer.append("dateSortie=").append(getDateSortie());
		buffer.append(",");
		buffer.append("potentiel=").append(getPotentiel());
		buffer.append(",");
		buffer.append("montant=").append(getMontant());
		buffer.append(",");
		buffer.append("monnaie=").append(getMonnaie());
		buffer.append(",");
		buffer.append("politiqueVoyage=").append(getPolitiqueVoyage());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _iXamgLbCEeCrCZp8iGNNVw) ENABLED START */

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
		final Segmentation other = (Segmentation) obj;

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

	/* PROTECTED REGION ID(_iXamgLbCEeCrCZp8iGNNVw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
