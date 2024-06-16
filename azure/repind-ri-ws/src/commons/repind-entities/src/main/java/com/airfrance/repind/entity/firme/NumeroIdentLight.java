package com.airfrance.repind.entity.firme;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title : NumeroIdent.java</p>
 * BO NumeroIdent
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity

@Table(name = "NUMERO_IDENT")
public class NumeroIdentLight implements Serializable {

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
	 * key
	 */
	@Id
	@Column(name = "IKEY", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_NUMERO_IDENT")
    @SequenceGenerator(name="ISEQ_NUMERO_IDENT", sequenceName = "ISEQ_NUMERO_IDENT", allocationSize = 1)
	private Long key;

	/**
	 * statut
	 */
	@Column(name = "SSTATUT", length = 1, nullable = false)
	private String statut;

	/**
	 * numero
	 */
	@Column(name = "SNUMERO", length = 20)
	private String numero;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 2)
	private String type;

	/**
	 * libelle
	 */
	@Column(name = "SLIBELLE", length = 40)
	private String libelle;

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
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * personneMorale
	 */
	// * <-> 1
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SGIN", nullable = false)
	@ForeignKey(name = "FK_PM_NUM")
	private PersonneMoraleLight personneMoraleLight;

	/**
	 * default constructor
	 */
	public NumeroIdentLight() {
	}

	/**
	 * full constructor
	 * 
	 * @param pKey key
	 * @param pStatut statut
	 * @param pNumero numero
	 * @param pType type
	 * @param pLibelle libelle
	 * @param pDateOuverture dateOuverture
	 * @param pDateFermeture dateFermeture
	 * @param pDateModification dateModification
	 */
	public NumeroIdentLight(Long pKey, String pStatut, String pNumero, String pType, String pLibelle,
                            Date pDateOuverture, Date pDateFermeture, Date pDateModification) {
		this.key = pKey;
		this.statut = pStatut;
		this.numero = pNumero;
		this.type = pType;
		this.libelle = pLibelle;
		this.dateOuverture = pDateOuverture;
		this.dateFermeture = pDateFermeture;
		this.dateModification = pDateModification;
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
	 * @param pDateFermeture
	 *            dateFermeture value
	 */
	public void setDateFermeture(Date pDateFermeture) {
		this.dateFermeture = pDateFermeture;
	}

	/**
	 *
	 * @return dateModification
	 */
	public Date getDateModification() {
		return this.dateModification;
	}

	/**
	 *
	 * @param pDateModification
	 *            dateModification value
	 */
	public void setDateModification(Date pDateModification) {
		this.dateModification = pDateModification;
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
	 * @param pDateOuverture
	 *            dateOuverture value
	 */
	public void setDateOuverture(Date pDateOuverture) {
		this.dateOuverture = pDateOuverture;
	}

	/**
	 *
	 * @return key
	 */
	public Long getKey() {
		return this.key;
	}

	/**
	 *
	 * @param pKey
	 *            key value
	 */
	public void setKey(Long pKey) {
		this.key = pKey;
	}

	/**
	 *
	 * @return libelle
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 *
	 * @param pLibelle
	 *            libelle value
	 */
	public void setLibelle(String pLibelle) {
		this.libelle = pLibelle;
	}

	/**
	 *
	 * @return numero
	 */
	public String getNumero() {
		return this.numero;
	}

	/**
	 *
	 * @param pNumero
	 *            numero value
	 */
	public void setNumero(String pNumero) {
		this.numero = pNumero;
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
	 * @param pStatut
	 *            statut value
	 */
	public void setStatut(String pStatut) {
		this.statut = pStatut;
	}

    /**
     *
     * @return personneMorale
     */
    public PersonneMoraleLight getPersonneMoraleLight() {
        return this.personneMoraleLight;
    }

    /**
     *
     * @param personneMoraleLight personneMorale value
     */
    public void setPersonneMoraleLight(PersonneMoraleLight personneMoraleLight) {
        this.personneMoraleLight = personneMoraleLight;
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
		buffer.append("key=").append(getKey());
		buffer.append(",");
		buffer.append("statut=").append(getStatut());
		buffer.append(",");
		buffer.append("numero=").append(getNumero());
		buffer.append(",");
		buffer.append("type=").append(getType());
		buffer.append(",");
		buffer.append("libelle=").append(getLibelle());
		buffer.append(",");
		buffer.append("dateOuverture=").append(getDateOuverture());
		buffer.append(",");
		buffer.append("dateFermeture=").append(getDateFermeture());
		buffer.append(",");
		buffer.append("dateModification=").append(getDateModification());
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
		final NumeroIdentLight other = (NumeroIdentLight) obj;

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
