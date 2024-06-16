package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.RefTableREF_PORT;
import com.airfrance.repind.entity.refTable.RefTableREF_SBT;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.SelfBookingToolRepository;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.SelfBookingTool;
import com.airfrance.repind.util.SicBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SelfBookingToolUS {

    /** logger */
    private static final Log log = LogFactory.getLog(SelfBookingToolUS.class);

    /*PROTECTED REGION ID(_hH-aAOJ6EeS4tOUM_Y9lGQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private SelfBookingToolRepository selfBookingToolRepository;

    /**
     * empty constructor
     */
    public SelfBookingToolUS() {
    }




    public SelfBookingToolRepository getSelfBookingToolRepository() {
		return selfBookingToolRepository;
	}




	public void setSelfBookingToolRepository(SelfBookingToolRepository selfBookingToolRepository) {
		this.selfBookingToolRepository = selfBookingToolRepository;
	}




	/*PROTECTED REGION ID(_hH-aAOJ6EeS4tOUM_Y9lGQ u m) ENABLED START*/
    // add your custom methods here if necessary
    private void checkMandatoryAndValidity(SelfBookingTool sbt) throws JrafDomainRollbackException {
    	// If no type at all --> ERROR 199 "NUMBER TYPE MANDATORY"
    	// --> if not one of these type ERROR 215 "INVALID NUMBER TYPE"
    	if (!StringUtils.isEmpty(sbt.getPortalGdsCode()) 
    			&& !RefTableREF_PORT.instance().estValide(sbt.getPortalGdsCode(), ""))
    		throw new JrafDomainRollbackException("358"); // REF_ERREUR : INVALID GDS CODE
    	// TODO LBN : ERREUR XXX INVALID SBT CODE 
    	if (!StringUtils.isEmpty(sbt.getSbtCode())
    			&& !RefTableREF_SBT.instance().estValide(sbt.getSbtCode(), ""))
    		throw new JrafDomainRollbackException("INVALID SBT CODE"); // REF_ERREUR : INVALID SBT CODE
    }

    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.ISelfBookingToolUS#check(java.util.List, PersonneMorale)
     */
    public void check(SelfBookingTool pSelfBookingTool, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pSelfBookingTool);
        Assert.notNull(pPersonneMorale);
        
        // Check global rule  
        if (SicBeanUtils.getNotNullPropertyNames(pSelfBookingTool).length == 0) {        	
        	// Empty block => remove existing sbt
        	if (pPersonneMorale.getSelfBookingTool() != null) {
        		SelfBookingTool sbt = pPersonneMorale.getSelfBookingTool();
        		pPersonneMorale.setSelfBookingTool(null);
        		selfBookingToolRepository.delete(sbt);
        	}
        } else {	// creation/modification
            // Check Validity of SelfBookingTool
            checkMandatoryAndValidity(pSelfBookingTool);

            if (pPersonneMorale.getSelfBookingTool() == null) {
            	// create the sbt
                SelfBookingTool sbt = new SelfBookingTool();
                sbt.setPersonneMorale(pPersonneMorale);
                pPersonneMorale.setSelfBookingTool(sbt);
                selfBookingToolRepository.saveAndFlush(sbt);
            }
            // Modif and creation
            BeanUtils.copyProperties(pSelfBookingTool, pPersonneMorale.getSelfBookingTool(), "gin", "personneMorale");
        }
    }    
    /*PROTECTED REGION END*/
}
