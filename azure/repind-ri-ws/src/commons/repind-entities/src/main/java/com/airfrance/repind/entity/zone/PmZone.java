package com.airfrance.repind.entity.zone;

/*PROTECTED REGION ID(_5oPAoLbNEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.firme.PersonneMorale;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : PmZone.java
 * </p>
 * BO PmZone
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Entity

@Table(name = "PM_ZONE")
public class PmZone implements Serializable {

	/* PROTECTED REGION ID(serialUID _5oPAoLbNEeCrCZp8iGNNVw) ENABLED START */
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
	@Column(name = "ICLE_PMZ", length = 10, nullable = false)
    @SequenceGenerator(name = "ISEQ_PM_ZONE", sequenceName = "ISEQ_PM_ZONE",
    		allocationSize = 1)
	@GeneratedValue(generator = "ISEQ_PM_ZONE")
	private Long cle;

	/**
	 * lienPrivilegie
	 */
	@Column(name = "SLIEN_PRIVILEGIE", length = 1)
	private String lienPrivilegie;

	/**
	 * dateOuverture
	 */
	@Column(name = "DDATE_OUVERTURE")
	private Date dateOuverture;

	/**
	 * dateFermeture
	 */
	@Column(name = "DDATE_FERMETURE")
	private Date dateFermeture;

	/**
	 * origine
	 */
	@Column(name = "SORIGINE", length = 2)
	private String origine;

	/**
	 * dateModif
	 */
	@Column(name = "DDATE_MODIF")
	private Date dateModif;

	/**
	 * signature
	 */
	@Column(name = "SSIGNATURE", length = 16)
	private String signature;

	/**
	 * usage
	 */
	@Column(name = "SUSAGE", length = 2)
	private String usage;

	/**
	 * personneMorale
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "SGIN", nullable = false)
	@ForeignKey(name = "FK_PMZ_PM")
	private PersonneMorale personneMorale;

	/**
	 * zoneDecoup
	 */
	// * <-> 1
	@ManyToOne()
	@JoinColumn(name = "IGIN_ZONE", nullable = false)
	@ForeignKey(name = "FK_PM_ZONE_IGIN_ZONE")
	private ZoneDecoup zoneDecoup;

	/* PROTECTED REGION ID(_5oPAoLbNEeCrCZp8iGNNVw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * default constructor
	 */
	public PmZone() {
	}

	/**
	 * full constructor
	 * 
	 * @param pCle            cle
	 * @param pLienPrivilegie lienPrivilegie
	 * @param pDateOuverture  dateOuverture
	 * @param pDateFermeture  dateFermeture
	 * @param pOrigine        origine
	 * @param pDateModif      dateModif
	 * @param pSignature      signature
	 * @param pUsage          usage
	 */
	public PmZone(Long pCle, String pLienPrivilegie, Date pDateOuverture, Date pDateFermeture, String pOrigine,
			Date pDateModif, String pSignature, String pUsage) {
		this.cle = pCle;
		this.lienPrivilegie = pLienPrivilegie;
		this.dateOuverture = pDateOuverture;
		this.dateFermeture = pDateFermeture;
		this.origine = pOrigine;
		this.dateModif = pDateModif;
		this.signature = pSignature;
		this.usage = pUsage;
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
	 * @return dateFermeture
	 */
	public Date getDateFermeture() {
		return this.dateFermeture;
	}

	/**
	 *
	 * @param pDateFermeture dateFermeture value
	 */
	public void setDateFermeture(Date pDateFermeture) {
		this.dateFermeture = pDateFermeture;
	}

	/**
	 *
	 * @return dateModif
	 */
	public Date getDateModif() {
		return this.dateModif;
	}

	/**
	 *
	 * @param pDateModif dateModif value
	 */
	public void setDateModif(Date pDateModif) {
		this.dateModif = pDateModif;
	}

	/**
	 *
	 * @return dateOuverture
	 */
	public Date getDateOuverture() {
		return this.dateOuverture;
	}

	/**
	 *
	 * @param pDateOuverture dateOuverture value
	 */
	public void setDateOuverture(Date pDateOuverture) {
		this.dateOuverture = pDateOuverture;
	}

	/**
	 *
	 * @return lienPrivilegie
	 */
	public String getLienPrivilegie() {
		return this.lienPrivilegie;
	}

	/**
	 *
	 * @param pLienPrivilegie lienPrivilegie value
	 */
	public void setLienPrivilegie(String pLienPrivilegie) {
		this.lienPrivilegie = pLienPrivilegie;
	}

	/**
	 *
	 * @return origine
	 */
	public String getOrigine() {
		return this.origine;
	}

	/**
	 *
	 * @param pOrigine origine value
	 */
	public void setOrigine(String pOrigine) {
		this.origine = pOrigine;
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
	 * @return signature
	 */
	public String getSignature() {
		return this.signature;
	}

	/**
	 *
	 * @param pSignature signature value
	 */
	public void setSignature(String pSignature) {
		this.signature = pSignature;
	}

	/**
	 *
	 * @return usage
	 */
	public String getUsage() {
		return this.usage;
	}

	/**
	 *
	 * @param pUsage usage value
	 */
	public void setUsage(String pUsage) {
		this.usage = pUsage;
	}

	/**
	 *
	 * @return zoneDecoup
	 */
	public ZoneDecoup getZoneDecoup() {
		return this.zoneDecoup;
	}

	/**
	 *
	 * @param pZoneDecoup zoneDecoup value
	 */
	public void setZoneDecoup(ZoneDecoup pZoneDecoup) {
		this.zoneDecoup = pZoneDecoup;
	}

	/**
	 *
	 * @return object as string
	 */
	public String toString() {
		String result = null;
		/* PROTECTED REGION ID(toString_5oPAoLbNEeCrCZp8iGNNVw) ENABLED START */
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
		buffer.append("lienPrivilegie=").append(getLienPrivilegie());
		buffer.append(",");
		buffer.append("dateOuverture=").append(getDateOuverture());
		buffer.append(",");
		buffer.append("dateFermeture=").append(getDateFermeture());
		buffer.append(",");
		buffer.append("origine=").append(getOrigine());
		buffer.append(",");
		buffer.append("dateModif=").append(getDateModif());
		buffer.append(",");
		buffer.append("signature=").append(getSignature());
		buffer.append(",");
		buffer.append("usage=").append(getUsage());
		buffer.append("]");
		return buffer.toString();
	}

	/* PROTECTED REGION ID(equals hash _5oPAoLbNEeCrCZp8iGNNVw) ENABLED START */

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
		final PmZone other = (PmZone) obj;

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

	/* PROTECTED REGION ID(_5oPAoLbNEeCrCZp8iGNNVw u m) ENABLED START */
	// add your custom methods here if necessary
	/* PROTECTED REGION END */

}
