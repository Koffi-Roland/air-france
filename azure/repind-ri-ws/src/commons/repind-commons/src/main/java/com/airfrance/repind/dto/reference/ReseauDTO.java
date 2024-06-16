package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/*PROTECTED REGION END*/

/**
 * <p>Title : ReseauDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ReseauDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 4750314042470458720L;


	/**
     * code
     */
    private String code;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * dateFermeture
     */
    private Date dateFermeture;
        
        
    /**
     * nature
     */
    private String nature;
        
        
    /**
     * nom
     */
    private String nom;
        
        
    /**
     * pays
     */
    private String pays;
        
        
    /**
     * type
     */
    private String type;
        

        
        
    /**
     * enfants
     */
    private Set<ReseauDTO> enfants;
        
        
    /**
     * parent
     */
    private ReseauDTO parent;
        

    /*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ReseauDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pDateCreation dateCreation
     * @param pDateFermeture dateFermeture
     * @param pCode code
     * @param pNature nature
     * @param pNom nom
     * @param pPays pays
     * @param pType type
     */
    public ReseauDTO(Date pDateCreation, Date pDateFermeture, String pCode, String pNature, String pNom, String pPays, String pType) {
        this.dateCreation = pDateCreation;
        this.dateFermeture = pDateFermeture;
        this.code = pCode;
        this.nature = pNature;
        this.nom = pNom;
        this.pays = pPays;
        this.type = pType;
    }



    /**
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param pCode code value
     */
    public void setCode(String pCode) {
        this.code = pCode;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
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
     * @return enfants
     */
    public Set<ReseauDTO> getEnfants() {
        return this.enfants;
    }

    /**
     *
     * @param pEnfants enfants value
     */
    public void setEnfants(Set<ReseauDTO> pEnfants) {
        this.enfants = pEnfants;
    }

    /**
     *
     * @return nature
     */
    public String getNature() {
        return this.nature;
    }

    /**
     *
     * @param pNature nature value
     */
    public void setNature(String pNature) {
        this.nature = pNature;
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
     * @return parent
     */
    public ReseauDTO getParent() {
        return this.parent;
    }

    /**
     *
     * @param pParent parent value
     */
    public void setParent(ReseauDTO pParent) {
        this.parent = pParent;
    }

    /**
     *
     * @return pays
     */
    public String getPays() {
        return this.pays;
    }

    /**
     *
     * @param pPays pays value
     */
    public void setPays(String pPays) {
        this.pays = pPays;
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
        /*PROTECTED REGION ID(toString_RkSYBWkxEeGhB9497mGnHw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
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
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("dateFermeture=").append(getDateFermeture());
        buffer.append(",");
        buffer.append("code=").append(getCode());
        buffer.append(",");
        buffer.append("nature=").append(getNature());
        buffer.append(",");
        buffer.append("nom=").append(getNom());
        buffer.append(",");
        buffer.append("pays=").append(getPays());
        buffer.append(",");
        buffer.append("type=").append(getType());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

    /**
     * @return true if all fields are null or empty
     */
    public boolean isEmpty() {
    	if ( (code == null || code.length() == 0)
    			&& dateCreation == null
    			&& dateFermeture == null
    			&& (nature == null || nature.length() == 0)
    			&& (nom == null || nom.length() == 0)
    			&& (pays == null || pays.length() == 0)
    			&& (type == null || type.length() == 0)
    			&& (enfants == null || enfants.size() == 0)
    			&& parent == null) {
    		return true;
    	} else {
        	return false;    		
    	}
    }
    
}
