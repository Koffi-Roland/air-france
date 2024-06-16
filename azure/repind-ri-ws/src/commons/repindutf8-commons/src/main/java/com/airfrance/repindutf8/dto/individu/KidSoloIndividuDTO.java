package com.airfrance.repindutf8.dto.individu;

/*PROTECTED REGION ID(_5KX30I_eEeKSvNsslAib8A i) ENABLED START*/

// add not generated imports here
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProspectIndividuDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class KidSoloIndividuDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = 1548261781147298140L;

	
	/**
     * gin
     */
    private String gin;
        
        
    /**
     * birthday
     */
    private Date birthday;
        
        
    /**
     * lastname
     */
    private String lastname;
     
    /**
     * firstname
     */
    private String firstname;
     

    /*PROTECTED REGION ID(_5KX30I_eEeKSvNsslAib8A u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    /** 
     * default constructor 
     */
    public KidSoloIndividuDTO() {
    
    }


	public KidSoloIndividuDTO(String gin, Date birthday, String lastname, String firstname) {
		super();
		this.gin = gin;
		this.birthday = birthday;
		this.lastname = lastname;
		this.firstname = firstname;
	}


	public String getGin() {
		return gin;
	}


	public void setGin(String gin) {
		this.gin = gin;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
