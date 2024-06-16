package com.airfrance.repind.service.internal.unitservice.adresse;


import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelecomUS {

    /** logger */
    private static final Log log = LogFactory.getLog(TelecomUS.class);

    /*PROTECTED REGION ID(_cpqeoKLfEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private TelecomsRepository telecomsRepository;

    /** domain service : TelecomDS **/
    @Autowired
    private TelecomDS telecomDS;
    
    /**
     * empty constructor
     */
    public TelecomUS() {
    }


    /*PROTECTED REGION ID(_cpqeoKLfEeSXNpATSKyi0Q u m) ENABLED START*/

    public TelecomsRepository getTelecomsRepository() {
		return telecomsRepository;
	}


	public void setTelecomsRepository(TelecomsRepository telecomsRepository) {
		this.telecomsRepository = telecomsRepository;
	}



    
    /*PROTECTED REGION END*/
}
