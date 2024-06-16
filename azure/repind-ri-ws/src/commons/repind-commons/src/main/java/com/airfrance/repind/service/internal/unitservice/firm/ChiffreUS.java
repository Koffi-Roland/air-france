package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.RefTableREF_TYP_CHIFFRE;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.ChiffreRepository;
import com.airfrance.repind.entity.firme.Chiffre;
import com.airfrance.repind.entity.firme.PersonneMorale;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class ChiffreUS {

    /** logger */
    private static final Log log = LogFactory.getLog(ChiffreUS.class);

    /*PROTECTED REGION ID(_OE57EKLnEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private ChiffreRepository chiffreRepository;

    /**
     * empty constructor
     */
    public ChiffreUS() {
    }

    /**
     * @return chiffreDAO
     */
    public ChiffreRepository getChiffreRepository() {
        return chiffreRepository;
    }

    /**
     * @param dao the IChiffreDAO
     */
    public void setChiffreRepository(ChiffreRepository chiffreRepository) {
        this.chiffreRepository = chiffreRepository;
    }


    /*PROTECTED REGION ID(_OE57EKLnEeSXNpATSKyi0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    private void checkMandatoryAndValidity(Chiffre ch) throws JrafDomainRollbackException {
    	// If no type at all --> ERROR 199 "NUMBER TYPE MANDATORY"
    	if (StringUtils.isEmpty(ch.getType()))
    		throw new JrafDomainRollbackException("199"); // REF_ERREUR : NUMBER TYPE MANDATORY
    	// --> if not one of these type ERROR 215 "INVALID NUMBER TYPE"
    	else if (!RefTableREF_TYP_CHIFFRE._REF_CA.equals(ch.getType())
    			&& !RefTableREF_TYP_CHIFFRE._REF_CP.equals(ch.getType())
    			&& !RefTableREF_TYP_CHIFFRE._REF_CE.equals(ch.getType())
    			&& !RefTableREF_TYP_CHIFFRE._REF_CI.equals(ch.getType()))
    		throw new JrafDomainRollbackException("215"); // REF_ERREUR : INVALID NUMBER TYPE
    }

    
    private Chiffre check(Chiffre chiffreToCheck, PersonneMorale pm) throws JrafDomainRollbackException {
    	// chiffreSaved : bo used with DAO layer
    	Chiffre chiffreChecked = null;

    	// A Number can have a type only from this list :
		// TYPE_CAPITAL
		// TYPE_CHIFFRE_AFFAIRE
		// TYPE_CA_EXPORT
		// TYPE_CA_IMPORT

		// No key sent with valid data will generate a create of this chiffre.
        if (chiffreToCheck.getKey() == null) { 
        	// Creation
        	
    		// Check filed mandatory and validity 
    		checkMandatoryAndValidity(chiffreToCheck);
        	
        	chiffreToCheck.setStatut("V");
        	chiffreToCheck.setDateDebut(new Date());
        	chiffreToCheck.setPersonneMorale(pm);
	        
	        chiffreChecked = chiffreToCheck;
        } else if (chiffreToCheck.getKey() != null) {
        	// modify or delete
        	
        	// Get the existing chiffre 
        	Chiffre existingChiffre = null;
        	List<Chiffre> lstChiffreExisting = new ArrayList<Chiffre>(pm.getChiffres());
        	for (Chiffre existingC : lstChiffreExisting) {
				if (existingC.getKey().equals(chiffreToCheck.getKey())) {
					existingChiffre = existingC;

					break;
				}
			}
        	
        	if (existingChiffre == null) {
        	    // TODO LBN créer erreur "NUMBER NOT FOUND"
        		throw new JrafDomainRollbackException("NUMBER NOT FOUND"); // KEY NOT CORRESPONDING TO GIN
        	}
        	else if (!StringUtils.isEmpty(chiffreToCheck.getType())){
        		// Modify
        		
        		// Check filed mandatory and validity 
        		checkMandatoryAndValidity(chiffreToCheck);
        		 
        		existingChiffre.setDateMaj(new Date());
        		existingChiffre.setLibelle(chiffreToCheck.getLibelle());
        		existingChiffre.setMonnaie(chiffreToCheck.getMonnaie());
        		existingChiffre.setMontant(chiffreToCheck.getMontant());
        		existingChiffre.setType(chiffreToCheck.getType());
        		existingChiffre.setStatut("V"); 
        		
        		chiffreChecked = existingChiffre;
	        } else {
	            // Suppression : flag to delete with status = X
	        	existingChiffre.setStatut("X"); 
        		
	        	chiffreChecked = existingChiffre;
	        }
        }
        
        return chiffreChecked;
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IChiffreUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDelete(List<Chiffre> pChiffres, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pChiffres);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        // Initialize PersonneMorale Chiffres
        if (pPersonneMorale.getChiffres() == null) {
            pPersonneMorale.setChiffres(new HashSet<Chiffre>());
        }
        
        List<Chiffre> chiffresToCreate = new ArrayList<Chiffre>();
        List<Chiffre> chiffresToRemove = new ArrayList<Chiffre>();
        List<Chiffre> chiffresToUpdate = new ArrayList<Chiffre>();
        
        // Check Validity of all Chiffres in the list
        for (Chiffre chiffre : pChiffres) {
        	Chiffre chiffreToSave = check(chiffre, pPersonneMorale);
        	// REPIND-1398 : Test SONAR NPE
        	if (chiffreToSave != null && chiffreToSave.getKey() == null)
        		chiffresToCreate.add(chiffreToSave);
        	else if (chiffreToSave != null && "X".equals(chiffreToSave.getStatut()))
        		chiffresToRemove.add(chiffreToSave);
        	else
        		chiffresToUpdate.add(chiffreToSave);
        }
    	
        // Check Rule for global postal addresses
        // Type CA and a CP must be unique in database.
        
        // List to check the global RM
        List<Chiffre> chiffres = new ArrayList<Chiffre>(pPersonneMorale.getChiffres());
        chiffres.removeAll(chiffresToRemove);
        chiffres.addAll(chiffresToCreate);

        
        // ERROR 215 "INVALID NUMBER TYPE" adding an information text like ": CA or CP number already exists"
        int nbTypeCA = 0;
        int nbTypeCP = 0;
	    for (Chiffre chiffre : chiffres) {
	    	if (RefTableREF_TYP_CHIFFRE._REF_CA.equals(chiffre.getType()))
	    		nbTypeCA++;
	    	else if (RefTableREF_TYP_CHIFFRE._REF_CP.equals(chiffre.getType())) 
	    		nbTypeCP++;
	    	else
	    		continue;
	    		
	    	if (nbTypeCA > 1 )
	    		throw new JrafDomainRollbackException("215"); // REF_ERREUR : INVALID NUMBER TYPE : CA number already exists"
	    	if (nbTypeCP > 1 )
	    		throw new JrafDomainRollbackException("215"); // REF_ERREUR : INVALID NUMBER TYPE : CP number already exists"
	    }
        
        // Enregistrement en base
        for (Chiffre chiffre : chiffresToCreate) {
            chiffreRepository.saveAndFlush(chiffre);
        }
        for (Chiffre chiffre : chiffresToUpdate) {
            chiffreRepository.saveAndFlush(chiffre);
        }
        for (Chiffre chiffre : chiffresToRemove) {
            chiffreRepository.delete(chiffre);
            chiffreRepository.flush();
        }
    }    
    /*PROTECTED REGION END*/
}
