package com.airfrance.repind.entity.role;

/*PROTECTED REGION ID(_q-trwAz5EeSYv7JdnOTzPw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RoleGP.java</p>
 * BO RoleGP
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity


@Table(name="ROLE_GP")
public class RoleGP implements Serializable {

/*PROTECTED REGION ID(serialUID _q-trwAz5EeSYv7JdnOTzPw) ENABLED START*/
   /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
/*PROTECTED REGION END*/

            
    /**
     * cleRole
     */
    @Id
    @Column(name="ICLE_ROLE", length=10, nullable=false, unique=true)
    @GeneratedValue(generator = "foreignGeneratorGp")
    @GenericGenerator(name = "foreignGeneratorGp", strategy = "foreign", parameters = { @Parameter(name = "property", value = "businessRole") })
    private Integer cleRole;
        
            
    /**
     * matricule
     */
    @Column(name="SMATRICULE", length=12)
    private String matricule;
        
            
    /**
     * version
     */
    @Column(name="SVERSION", length=2)
    private String version;
        
            
    /**
     * etat
     */
    @Column(name="SETAT", length=1)
    private String etat;
        
            
    /**
     * dateDebValidite
     */
    @Column(name="DDEBUT_VALIDITE")
    private Date dateDebValidite;
    
    /**
     * dateFinValidite
     */
    @Column(name="DFIN_VALIDITE")
    private Date dateFinValidite;
    
    /**
     * type
     */
    @Column(name="STYPE", length=2)
    private String type;
        
            
    /**
     * codeOrigin
     */
    @Column(name="SCODE_ORIGINE", length=6)
    private String codeOrigin;
        
            
    /**
     * codeCie
     */
    @Column(name="SCODE_CIE", length=6)
    private String codeCie;
        
            
    /**
     * codePays
     */
    @Column(name="SCODE_PAYS", length=2)
    private String codePays;
        
            
    /**
     * typology
     */
    @Column(name="STRUNC_TYPO", length=4)
    private String typology;
    
    
    /**
     * ordreIdentifiant
     */
    @Column(name="SORDRE_IDENTIFIANT", length=3)
    private String ordreIdentifiant;
            

        
            
    /**
     * businessRole
     */
    // 1 <-> 1
    @OneToOne(mappedBy="roleGP", fetch=FetchType.LAZY)
    private BusinessRole businessRole;
        
    /*PROTECTED REGION ID(_sAz1UDnEEeS2wtWjh0gEaw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RoleGP() {
    }
    

	/**
	 * @param cleRole
	 * @param matricule
	 * @param version
	 * @param etat
	 * @param dateDebValidite
	 * @param dateFinValidite
	 * @param type
	 * @param codeOrigin
	 * @param codeCie
	 * @param codePays
	 * @param typology
	 * @param ordreIdentifiant
	 * @param businessRole
	 */
	public RoleGP(Integer cleRole, String matricule, String version,
			String etat, Date dateDebValidite, Date dateFinValidite,
			String type, String codeOrigin, String codeCie, String codePays,
			String typology, String ordreIdentifiant, BusinessRole businessRole) {
		this.cleRole = cleRole;
		this.matricule = matricule;
		this.version = version;
		this.etat = etat;
		this.dateDebValidite = dateDebValidite;
		this.dateFinValidite = dateFinValidite;
		this.type = type;
		this.codeOrigin = codeOrigin;
		this.codeCie = codeCie;
		this.codePays = codePays;
		this.typology = typology;
		this.ordreIdentifiant = ordreIdentifiant;
		this.businessRole = businessRole;
	}


	 /**
	 * @return the cleRole
	 */
	public Integer getCleRole() {
		return cleRole;
	}


	/**
	 * @param cleRole the cleRole to set
	 */
	public void setCleRole(Integer cleRole) {
		this.cleRole = cleRole;
	}


	/**
	 * @return the matricule
	 */
	public String getMatricule() {
		return matricule;
	}


	/**
	 * @param matricule the matricule to set
	 */
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @return the etat
	 */
	public String getEtat() {
		return etat;
	}


	/**
	 * @param etat the etat to set
	 */
	public void setEtat(String etat) {
		this.etat = etat;
	}


	/**
	 * @return the dateDebValidite
	 */
	public Date getDateDebValidite() {
		return dateDebValidite;
	}


	/**
	 * @param dateDebValidite the dateDebValidite to set
	 */
	public void setDateDebValidite(Date dateDebValidite) {
		this.dateDebValidite = dateDebValidite;
	}


	/**
	 * @return the dateFinValidite
	 */
	public Date getDateFinValidite() {
		return dateFinValidite;
	}


	/**
	 * @param dateFinValidite the dateFinValidite to set
	 */
	public void setDateFinValidite(Date dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the codeOrigin
	 */
	public String getCodeOrigin() {
		return codeOrigin;
	}


	/**
	 * @param codeOrigin the codeOrigin to set
	 */
	public void setCodeOrigin(String codeOrigin) {
		this.codeOrigin = codeOrigin;
	}


	/**
	 * @return the codeCie
	 */
	public String getCodeCie() {
		return codeCie;
	}


	/**
	 * @param codeCie the codeCie to set
	 */
	public void setCodeCie(String codeCie) {
		this.codeCie = codeCie;
	}


	/**
	 * @return the codePays
	 */
	public String getCodePays() {
		return codePays;
	}


	/**
	 * @param codePays the codePays to set
	 */
	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}


	/**
	 * @return the typology
	 */
	public String getTypology() {
		return typology;
	}


	/**
	 * @param typology the typology to set
	 */
	public void setTypology(String typology) {
		this.typology = typology;
	}


	/**
	 * @return the ordreIdentifiant
	 */
	public String getOrdreIdentifiant() {
		return ordreIdentifiant;
	}


	/**
	 * @param ordreIdentifiant the ordreIdentifiant to set
	 */
	public void setOrdreIdentifiant(String ordreIdentifiant) {
		this.ordreIdentifiant = ordreIdentifiant;
	}


	/**
	 * @return the businessRole
	 */
	public BusinessRole getBusinessRole() {
		return businessRole;
	}


	/**
	 * @param businessRole the businessRole to set
	 */
	public void setBusinessRole(BusinessRole businessRole) {
		this.businessRole = businessRole;
	}


	/**
    *
    * @return object as string
    */
   public String toString() {
       String result = null;
       /*PROTECTED REGION ID(toString_sAz1UDnEEeS2wtWjh0gEaw) ENABLED START*/
       result = toStringImpl();
       /*PROTECTED REGION END*/
       return result;
   }
   
   /**
   *
   * @return object as string
   */
	public String toStringImpl() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleGP [cleRole=");
		builder.append(cleRole);
		builder.append(", matricule=");
		builder.append(matricule);
		builder.append(", version=");
		builder.append(version);
		builder.append(", etat=");
		builder.append(etat);
		builder.append(", dateDebValidite=");
		builder.append(dateDebValidite);
		builder.append(", dateFinValidite=");
		builder.append(dateFinValidite);
		builder.append(", type=");
		builder.append(type);
		builder.append(", codeOrigin=");
		builder.append(codeOrigin);
		builder.append(", codeCie=");
		builder.append(codeCie);
		builder.append(", codePays=");
		builder.append(codePays);
		builder.append(", typology=");
		builder.append(typology);
		builder.append(", ordreIdentifiant=");
		builder.append(ordreIdentifiant);
		builder.append(", businessRole=");
		builder.append(businessRole);
		builder.append("]");
		return builder.toString();
	}
	
	 /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_sydB8FMSEeat9YXqXExedw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
