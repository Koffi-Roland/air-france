package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.RefTableREF_TYP_NUMID;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.NumeroIdentRepository;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NumeroIdentUS {

    /** logger */
    private static final Log log = LogFactory.getLog(NumeroIdentUS.class);
    
    /** references on associated DAOs */
    @Autowired
    private NumeroIdentRepository numeroIdentRepository;

    /**
     * empty constructor
     */
    public NumeroIdentUS() {
    }

    public NumeroIdentRepository getNumeroIdentRepository() {
		return numeroIdentRepository;
	}

	public void setNumeroIdentRepository(NumeroIdentRepository numeroIdentRepository) {
		this.numeroIdentRepository = numeroIdentRepository;
	}

    private boolean isValidKeyNumberKVIVTC(String numero, String type){
    	boolean isValid = false;
    	
    	String regex = null;
    	
    	switch(type) {
	    	case "KV":
	    		regex = "^[a-zA-Z0-9]{0,13}$";
	    		break;
	    	case "IV":
	    		regex = "^[a-zA-Z0-9]{0,13}$";
	    		break;
	    	case "TC":
	    		regex = "^[a-zA-Z0-9]{13,14}$";
	    		break;
	    		default:
    	}
    	   	
    	isValid = numero.matches(regex);
    	
    	return isValid;
    }
    
    private void checkMandatoryAndValidity(NumeroIdent num) throws JrafDomainRollbackException {
    	
    	// PersonneMorale
    	PersonneMorale pm = num.getPersonneMorale();
    	
    	// If no type at all --> ERROR 199 "NUMBER TYPE MANDATORY"
    	if (StringUtils.isEmpty(num.getType()))
    		throw new JrafDomainRollbackException("199"); // REF_ERREUR : NUMBER TYPE MANDATORY
    	else if (!RefTableREF_TYP_NUMID._REF_AG.equals(num.getType()) 	 
    			&& !RefTableREF_TYP_NUMID._REF_AN.equals(num.getType()) 
    			&& !RefTableREF_TYP_NUMID._REF_AR.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_AT.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_BI.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_DU.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_EA.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_EN.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_IA.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_IV.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_KV.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_SI.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_SR.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_TA.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_TV.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_TC.equals(num.getType())) // Firm
    		throw new JrafDomainRollbackException("512"); // REF_ERREUR : INVALID NUMBER TYPE
    	else if (!(pm instanceof Agence) // For other personneMorale than Agency => type = KV or IV else ERROR_512
    			&& !RefTableREF_TYP_NUMID._REF_IV.equals(num.getType()) // Firm
    			&& !RefTableREF_TYP_NUMID._REF_KV.equals(num.getType())
    			&& !RefTableREF_TYP_NUMID._REF_TC.equals(num.getType()))
    		throw new JrafDomainRollbackException("512"); // REF_ERREUR : IDENTIFIANT NON VALIDE
    	else if ((RefTableREF_TYP_NUMID._REF_KV.equals(num.getType()) 
    			|| RefTableREF_TYP_NUMID._REF_IV.equals(num.getType())
    			|| RefTableREF_TYP_NUMID._REF_TC.equals(num.getType()))
    			&& !isValidKeyNumberKVIVTC(num.getNumero(), num.getType()))
    		throw new JrafDomainRollbackException("512"); // REF_ERREUR : IDENTIFIANT NON VALIDE
    	else if (num.getDateOuverture() == null)
    		throw new JrafDomainRollbackException("NUMBER START DATE MANDATORY"); // REF_ERREUR : OPENING DATE NOT FILLED    	
    }
    
    /**
     * Compare period overlap of the numeroIdent with other with same type and different number
     * @param numeroIdentToCheck
     * @return true if numeroIdent overlap period with another numeroIdent of the same type 
     * @throws JrafApplicativeException 
     */
    private boolean isPeriodOverlap(NumeroIdent numeroIdentToCheck) throws JrafDomainException {
    	boolean isOverlap = false;;
    	
    	try {
	    		
	    	PersonneMorale pers = numeroIdentToCheck.getPersonneMorale();
	    	
	    	for (NumeroIdent numero : pers.getNumerosIdent()) {
	    		// same type and different number => compare period 
				if (!numeroIdentToCheck.getKey().equals(numero.getKey())
						&& numeroIdentToCheck.getType().equals(numero.getType())
						&& !numeroIdentToCheck.getNumero().equals(numero.getNumero()))
				{
					int retourCompare = SicDateUtils.comparePeriods(numero.getDateOuverture(),
							numero.getDateFermeture(),
							numeroIdentToCheck.getDateOuverture(),
							numeroIdentToCheck.getDateFermeture());
					if (retourCompare == SicDateUtils.PERIOD_INCLUDE
							|| retourCompare == SicDateUtils.PERIOD_OVERLAP_INF
							|| retourCompare == SicDateUtils.PERIOD_OVERLAP_SUP) {
						isOverlap = true;
						break;
					}
				}
			}
    	} catch (JrafApplicativeException e) {
    		throw new JrafDomainException("Compare periods : date invalid");
		}
    	
    	return isOverlap;
    }
    
    private boolean isUniqueNumber(NumeroIdent numeroIdentToCheck) throws JrafDomainException {
    	boolean isUnique = true;
    	
    	try {
	    	PersonneMorale pers = numeroIdentToCheck.getPersonneMorale();
	    	
	    	for (NumeroIdent numero : pers.getNumerosIdent()) {
	    		// same type and number but different key => compare period 
				if (!numero.getKey().equals(numeroIdentToCheck.getKey())
						&&numeroIdentToCheck.getType().equals(numero.getType())
						&& numeroIdentToCheck.getNumero().equals(numero.getNumero()))
				{
					int retourCompare = SicDateUtils.comparePeriods(numero.getDateOuverture(),
							numero.getDateFermeture(),
							numeroIdentToCheck.getDateOuverture(),
							numeroIdentToCheck.getDateFermeture());
					if (retourCompare == SicDateUtils.PERIOD_INCLUDE
							|| retourCompare == SicDateUtils.PERIOD_OVERLAP_INF
							|| retourCompare == SicDateUtils.PERIOD_OVERLAP_SUP
							|| retourCompare == SicDateUtils.PERIOD_RECOVER) {
						isUnique = false;
						break;
					}
				}
			}
    	} catch (JrafApplicativeException e) {
    		throw new JrafDomainException("Compare periods : date invalid");
		}
    	
    	return isUnique;
    }
    
    private List<NumeroIdent> check(NumeroIdent numeroIdentToCheck, PersonneMorale pm) throws JrafDomainException {
    	// numeroIdentSaved : bo used with DAO layer
    	List<NumeroIdent> numeroIdentChecked = new ArrayList<NumeroIdent>();
    	
    	// Actual date
    	Date now = new Date();
    	
		// Affect numeroIdent to pm
		numeroIdentToCheck.setPersonneMorale(pm);

		// No key sent with valid data will generate a create of this numeroIdent.
        if (numeroIdentToCheck.getKey() == null) { 
        	// Creation
        	
    		// Check filed mandatory and validity 
    		checkMandatoryAndValidity(numeroIdentToCheck);
        	
    		// Specific Rule Management in case of creation
    		if ((RefTableREF_TYP_NUMID._REF_KV.equals(numeroIdentToCheck.getType()) 
        			|| RefTableREF_TYP_NUMID._REF_IV.equals(numeroIdentToCheck.getType())))
    		{
    			if (!isUniqueNumber(numeroIdentToCheck)) 
					throw new JrafDomainRollbackException("388"); // REF_ERREUR : (CHECK EXISTING KEY NUMBERS)
    		} 
    			
    		// Specific treatment for existing same type or type in group AT or IA or AG  
    		for (NumeroIdent numero : pm.getNumerosIdent()) {
				if ((!numeroIdentToCheck.getType().equals(RefTableREF_TYP_NUMID._REF_TC) && numero.getType().equals(numeroIdentToCheck.getType())) // Same type already exist
						|| ((RefTableREF_TYP_NUMID._REF_AT.equals(numeroIdentToCheck.getType())
								|| RefTableREF_TYP_NUMID._REF_IA.equals(numeroIdentToCheck.getType())
								|| RefTableREF_TYP_NUMID._REF_AG.equals(numeroIdentToCheck.getType()))
						&& (RefTableREF_TYP_NUMID._REF_AT.equals(numero.getType())
								|| RefTableREF_TYP_NUMID._REF_IA.equals(numero.getType())
								|| RefTableREF_TYP_NUMID._REF_AG.equals(numero.getType())))) { // or new numIdent and existing numIdent have type in group AT/IA/AG
					
					if (numero.getDateFermeture() != null) { // New NumIdent begin before end of the existing NumIdent
						if (numeroIdentToCheck.getDateOuverture().getTime() < numero.getDateFermeture().getTime()) {
							throw new JrafDomainRollbackException("119"); // REF_ERREUR : (CHEVAUCHEMENT PERIODES)
						}
					} else {
						// Existing similar NumIdent already valid 
						if (numeroIdentToCheck.getDateOuverture().getTime() < numero.getDateOuverture().getTime()) {
							// New NumIdent begin before old one 
							throw new JrafDomainRollbackException("119"); // REF_ERREUR : (CHEVAUCHEMENT PERIODES)
						} else { // Creation but closing the old one
							if (numeroIdentToCheck.getDateOuverture().getTime() == numero.getDateOuverture().getTime())
								// Close the NumIdent with DateOuv
								numero.setDateFermeture(numeroIdentToCheck.getDateOuverture());
							else {
								// Else close the NumIdent with the day before the DateOuv
								Calendar cal = Calendar.getInstance();
								cal.setTime(numeroIdentToCheck.getDateOuverture());
								cal.add(Calendar.DAY_OF_WEEK, -1);
								numero.setDateFermeture(cal.getTime());									
							}
							
							// numero has to be updated
							numeroIdentChecked.add(numero);
						}
					}
				}
			}
    			
        	numeroIdentToCheck.setStatut("V");
        	
        	// If no dateOuv passed
        	Date date;
        	if (numeroIdentToCheck.getDateOuverture() == null) {
        		date = new Date();
        	} else date = numeroIdentToCheck.getDateOuverture(); 
        	
        	// Format dateOuv with no hour
        	try {
        		numeroIdentToCheck.setDateOuverture(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()));
        	} catch (ParseException e) {
				
			}
	        
	        numeroIdentChecked.add(numeroIdentToCheck);
        } else if (numeroIdentToCheck.getKey() != null) {
        	// modify or delete
        	
        	// Get the existing numeroIdent
        	NumeroIdent existingNumeroIdent = null;
        	List<NumeroIdent> lstNumeroIdentExisting = new ArrayList<NumeroIdent>(pm.getNumerosIdent());
        	for (NumeroIdent existingN : lstNumeroIdentExisting) {
				if (existingN.getKey().equals(numeroIdentToCheck.getKey())) {	
					existingNumeroIdent = existingN;
					break;
				}
			}
        	
        	if (existingNumeroIdent == null) {
        		// TODO LBN créer erreur "KEY NUMBER NOT FOUND"
        		throw new JrafDomainRollbackException("KEY NUMBER NOT FOUND");
        	}
        	else if (!StringUtils.isEmpty(numeroIdentToCheck.getNumero())){
        		// Modify
        		
        		// Check filed mandatory and validity 
        		checkMandatoryAndValidity(numeroIdentToCheck);
        		
        		// Specific Rule Management in case of modification
        		if ((RefTableREF_TYP_NUMID._REF_KV.equals(numeroIdentToCheck.getType()) 
            			|| RefTableREF_TYP_NUMID._REF_IV.equals(numeroIdentToCheck.getType())))
        		{
        			// If DateFermeture exist, is in the future 
        			if (existingNumeroIdent.getDateFermeture() != null
                			&& existingNumeroIdent.getDateFermeture().before(now))
        				throw new JrafDomainRollbackException("119"); // REF_ERREUR : CHANGE DATE PROHIBITED
					else if (isPeriodOverlap(numeroIdentToCheck))
								throw new JrafDomainRollbackException("119"); // REF_ERREUR : (OVERLAP:CHECK KEY NUMBER)
					else if (!isUniqueNumber(numeroIdentToCheck)) {
						throw new JrafDomainRollbackException("388"); // REF_ERREUR : (CHECK EXISTING KEY NUMBERS)
					}
        		}
            			 
        		existingNumeroIdent.setLibelle(numeroIdentToCheck.getLibelle());
        		existingNumeroIdent.setNumero(numeroIdentToCheck.getNumero());
        		existingNumeroIdent.setType(numeroIdentToCheck.getType());
        		existingNumeroIdent.setDateModification(new Date());
        		existingNumeroIdent.setStatut("V");
        		
        		 
        		numeroIdentChecked.add(existingNumeroIdent);
	        } else {
	            // Suppression : flag to delete with status = X
	        	existingNumeroIdent.setStatut("X"); 
        		
	        	numeroIdentChecked.add(existingNumeroIdent);
	        }
        }
			
		return numeroIdentChecked;
    }    
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.INumeroIdentUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDelete(List<NumeroIdent> pNumerosIdent, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pNumerosIdent);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        // Initialize PersonneMorale NumeroIdents
        if (pPersonneMorale.getNumerosIdent() == null) {
            pPersonneMorale.setNumerosIdent(new HashSet<NumeroIdent>());
        }
        
        List<NumeroIdent> numerosIdentToCreate = new ArrayList<NumeroIdent>();
        List<NumeroIdent> numerosIdentToRemove = new ArrayList<NumeroIdent>();
        List<NumeroIdent> numerosIdentToUpdate = new ArrayList<NumeroIdent>();
        
        // Check Validity of all numeros ident in the list
        for (NumeroIdent numeroIdent : pNumerosIdent) {
        	List<NumeroIdent> numerosIdentToSave = check(numeroIdent, pPersonneMorale);
        	
        	for (NumeroIdent numeroIdentToSave : numerosIdentToSave) {
        		if (numeroIdentToSave.getKey() == null)
            		numerosIdentToCreate.add(numeroIdentToSave);
            	else if ("X".equals(numeroIdentToSave.getStatut()))
            		numerosIdentToRemove.add(numeroIdentToSave);
            	else
            		numerosIdentToUpdate.add(numeroIdentToSave);	
			}
        }
    
        // Enregistrement en base
        for (NumeroIdent numeroIdent : numerosIdentToCreate) {
        	numeroIdentRepository.saveAndFlush(numeroIdent);
        }
        for (NumeroIdent numeroIdent : numerosIdentToUpdate) {
        	numeroIdentRepository.saveAndFlush(numeroIdent);
        }
        for (NumeroIdent numeroIdent : numerosIdentToRemove) {
        	numeroIdentRepository.delete(numeroIdent);
        }
    }        
}
