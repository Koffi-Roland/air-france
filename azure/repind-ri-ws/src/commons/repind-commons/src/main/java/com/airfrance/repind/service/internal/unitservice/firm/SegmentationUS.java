package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.SegmentationRepository;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.AgenceTransform;
import com.airfrance.repind.dto.firme.SegmentationDTO;
import com.airfrance.repind.dto.firme.SegmentationTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.Segmentation;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SegmentationUS {

    /** logger */
    private static final Log log = LogFactory.getLog(SegmentationUS.class);

    /*PROTECTED REGION ID(_uh-OEKMNEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private SegmentationRepository segmentationRepository;
    
    /**
     * empty constructor
     */
    public SegmentationUS() {
    }

    public SegmentationRepository getSegmentationRepository() {
		return segmentationRepository;
	}

	public void setSegmentationRepository(SegmentationRepository segmentationRepository) {
		this.segmentationRepository = segmentationRepository;
	}

	/*PROTECTED REGION ID(_uh-OEKMNEeSXNpATSKyi0Q u m) ENABLED START*/
    // add your custom methods here if necessary
    public void checkMandatoryAndValidity(Segmentation seg) throws JrafDomainRollbackException {

    	if (StringUtils.isEmpty(seg.getType()))
    		throw new JrafDomainRollbackException("255"); // REF_ERREUR : MARKET CHOICE TYPE MANDATORY
    	if (StringUtils.isEmpty(seg.getNiveau()))
    		throw new JrafDomainRollbackException("MARKET CHOICE LEVEL MANDATORY"); // TODO LBN créer erreur "MARKET CHOICE LEVEL MANDATORY"
    	if (//!RefTableREF_NIV_SEG_F._REF_L.equals(seg.getNiveau())
    			!RefTableREF_NIV_SEG_F._REF_LAP.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_LAT.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_LKP.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_LKT.equals(seg.getNiveau())
    			// && !RefTableREF_NIV_SEG_F._REF_M.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_MAP.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_NS.equals(seg.getNiveau())
//    			&& !RefTableREF_NIV_SEG_F._REF_S.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_SME.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_SMP.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_F._REF_SMT.equals(seg.getNiveau())
//    			&& !RefTableREF_NIV_SEG_F._REF_XL.equals(seg.getNiveau())
//    			&& !RefTableREF_NIV_SEG_F._REF_XS.equals(seg.getNiveau())
//    			&& !RefTableREF_NIV_SEG_F._REF_XXL.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_A.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_B.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_C.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_D.equals(seg.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_N.equals(seg.getNiveau())
    			)
    		throw new JrafDomainRollbackException("153"); // REF_ERREUR : INVALID MARKET CHOICE LEVEL
    	if (!RefTableREF_TYP_SEG_F._REF_FVF.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_F._REF_SGA.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_A._REF_SGA.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_A._REF_SGC.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_A._REF_SGM.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_A._REF_SGR.equals(seg.getType())
    			&& !RefTableREF_TYP_SEG_A._REF_STO.equals(seg.getType())
    			)
    		throw new JrafDomainRollbackException("234"); // REF_ERREUR : INVALID MARKET CHOICE TYPE
    	if (!StringUtils.isEmpty(seg.getPolitiqueVoyage()) 
    			&& !RefTableREF_POL_VOYAGE.instance().estValide(seg.getPolitiqueVoyage(), ""))
    		throw new JrafDomainRollbackException("268"); // REF_ERREUR : INVALID TRAVEL POLICY CODE
    	if (seg.getDateEntree() == null)
    		throw new JrafDomainRollbackException("211"); // REF_ERREUR : VALIDITY START DATE MANDATORY
    	if (!StringUtils.isEmpty(seg.getMonnaie()) 
    			&& !RefTableREF_MONNAIE.instance().estValide(seg.getMonnaie(), ""))
    		throw new JrafDomainRollbackException("325"); // REF_ERREUR : INVALID CURRENCY CODE
    }    
    
    private List<Segmentation> check(Segmentation segmentationToCheck, PersonneMorale pm) throws JrafDomainRollbackException {
    	// segmentationSaved : bo used with DAO layer
    	List<Segmentation> segmentationChecked = new ArrayList<Segmentation>();

    	// Get the existing valid segmentation of the pm
    	Segmentation existingSegValid = null;
    	for (Segmentation segmentation : pm.getSegmentations()) {
			if (segmentation.getDateSortie() == null)
				existingSegValid = segmentation;
		}
    	
    	if (existingSegValid != null 
    			&& !RefTableREF_NIV_SEG_F._REF_NS.equals(existingSegValid.getNiveau()))
    		throw new JrafDomainRollbackException("348"); // REF_ERREUR : MARKET CHOICE ALREADY EXISTING  		
    	
		// No key sent with valid data will generate a create of this segmentation
        if (segmentationToCheck.getCle() == null) { 
        	// Creation

    		// Check filed mandatory and validity 
    		checkMandatoryAndValidity(segmentationToCheck);

        	// Close the existing valid market choice => set end date = start date newone - 1 day
        	if (existingSegValid != null) {
        		Calendar calDateSortie = Calendar.getInstance();
        		calDateSortie.setTime(segmentationToCheck.getDateEntree());
        		calDateSortie.add(Calendar.DAY_OF_WEEK, -1);
        		
        		existingSegValid.setDateSortie(calDateSortie.getTime());
        		
        		segmentationChecked.add(existingSegValid);
        	}
    		
        	segmentationToCheck.setPersonneMorale(pm);
        	
        	segmentationChecked.add(segmentationToCheck);
        } else if (segmentationToCheck.getCle() != null) {
        	// modify or delete
        	
        	if (existingSegValid == null || !existingSegValid.getCle().equals(segmentationToCheck.getCle())) {
        		
        	    // TODO LBN créer erreur "MARKET CHOICE NOT FOUND"
        	    throw new JrafDomainRollbackException("MARKET CHOICE NOT FOUND");
        	}
        	else {
        		// Modify
        		
        		// Check filed mandatory and validity 
        		checkMandatoryAndValidity(segmentationToCheck);
        		
        		// Type/Level/Date start and end date cannot be modified
        		if (!existingSegValid.getType().equals(segmentationToCheck.getType())) {
        		    
        		    // TODO LBN créer erreur "MARKET CHOICE TYPE UPDATE NOT ALLOWED"
                            throw new JrafDomainRollbackException("MARKET CHOICE TYPE UPDATE NOT ALLOWED");
        		}
        		
        		if (!RefTableREF_NIV_SEG_F._REF_NS.equals(existingSegValid.getNiveau())
        		        && !existingSegValid.getNiveau().equals(segmentationToCheck.getNiveau())) {
        		    
        		    // TODO LBN créer erreur "MARKET CHOICE LEVEL UPDATE NOT ALLOWED"
                            throw new JrafDomainRollbackException("MARKET CHOICE LEVEL UPDATE NOT ALLOWED");
        		} else {
        		    
        		    existingSegValid.setNiveau(segmentationToCheck.getNiveau());
        		}
        		
        		if (existingSegValid.getDateEntree().compareTo(segmentationToCheck.getDateEntree()) != 0) {
        		    
        		    // TODO LBN créer erreur "MARKET CHOICE START DATE UPDATE NOT ALLOWED"
                            throw new JrafDomainRollbackException("MARKET CHOICE START DATE UPDATE NOT ALLOWED");
        		}
        		
        		if ((existingSegValid.getDateSortie() != null ^ segmentationToCheck.getDateSortie() != null)
        		        || (existingSegValid.getDateSortie() != null && existingSegValid.getDateSortie().compareTo(segmentationToCheck.getDateSortie()) != 0)) {
        		    
        		    // TODO LBN créer erreur "MARKET CHOICE END DATE UPDATE NOT ALLOWED"
        		    throw new JrafDomainRollbackException("MARKET CHOICE END DATE UPDATE NOT ALLOWED");
        		}            		
        		
        		existingSegValid.setMonnaie(segmentationToCheck.getMonnaie());
        		existingSegValid.setMontant(segmentationToCheck.getMontant());
        		existingSegValid.setPolitiqueVoyage(segmentationToCheck.getPolitiqueVoyage());
        		existingSegValid.setPotentiel(segmentationToCheck.getPotentiel());
        		
        		segmentationChecked.add(existingSegValid);
	        } 
        }
        
        return segmentationChecked;
    }    
    
    private List<Segmentation> check(Segmentation segmentationToCheck, Agence agence) throws JrafDomainRollbackException {
    	// segmentationSaved : bo used with DAO layer
    	List<Segmentation> segmentationChecked = new ArrayList<Segmentation>();

    	// Get the existing valid segmentation of the pm
    	Segmentation existingSegValid = null;
    	for (Segmentation segmentation : agence.getSegmentations()) {
			if (segmentation.getDateSortie() == null)
				existingSegValid = segmentation;
		}
    	
		// No key sent with valid data will generate a create of this segmentation
        if (segmentationToCheck.getCle() == null) { 
        	// Creation

    		if (!RefTableREF_TYP_SEG_A._REF_SGA.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGC.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGM.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGR.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_STO.equals(segmentationToCheck.getType())
        	   )
        		throw new JrafDomainRollbackException("234"); 
    		
        	if (!RefTableREF_NIV_SEG_A._REF_A.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_B.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_C.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_D.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_N.equals(segmentationToCheck.getNiveau())        	)
        		throw new JrafDomainRollbackException("153"); // REF_ERREUR : INVALID MARKET CHOICE LEVEL
        	
        	// Close the existing valid market choice => set end date = start date newone - 1 day
        	if (existingSegValid != null) {
        		Calendar calDateSortie = Calendar.getInstance();
        		calDateSortie.setTime(segmentationToCheck.getDateEntree());
        		calDateSortie.add(Calendar.DAY_OF_WEEK, -1);
        		
        		existingSegValid.setDateSortie(calDateSortie.getTime());
        		
        		segmentationChecked.add(existingSegValid);
        	}
    		
        	segmentationToCheck.setPersonneMorale(agence);
        	
        	segmentationChecked.add(segmentationToCheck);
        } else if (segmentationToCheck.getCle() != null) {
        	// modify or delete
        	
        	if (existingSegValid == null || !existingSegValid.getCle().equals(segmentationToCheck.getCle())) {
        		
        	    throw new JrafDomainRollbackException("MARKET CHOICE NOT FOUND");
        	}
        	else {
        		// Modify
        		
        		// Check filed mandatory and validity 
        		checkMandatoryAndValidity(segmentationToCheck);
        		
        		// Type/Level/Date start and end date cannot be modified
        		if (!existingSegValid.getType().equals(segmentationToCheck.getType())) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE TYPE UPDATE NOT ALLOWED");
        		}
        		
        		if (!RefTableREF_NIV_SEG_A._REF_N.equals(existingSegValid.getNiveau())
        		        && !existingSegValid.getNiveau().equals(segmentationToCheck.getNiveau())) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE LEVEL UPDATE NOT ALLOWED");
        		} else {
        		    
        		    existingSegValid.setNiveau(segmentationToCheck.getNiveau());
        		}
        		
        		if (existingSegValid.getDateEntree().compareTo(segmentationToCheck.getDateEntree()) != 0) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE START DATE UPDATE NOT ALLOWED");
        		}
        		
        		if ((existingSegValid.getDateSortie() != null ^ segmentationToCheck.getDateSortie() != null)
        		        || (existingSegValid.getDateSortie() != null && existingSegValid.getDateSortie().compareTo(segmentationToCheck.getDateSortie()) != 0)) {
        		    
        		    throw new JrafDomainRollbackException("MARKET CHOICE END DATE UPDATE NOT ALLOWED");
        		}            		
        		
        		existingSegValid.setMonnaie(segmentationToCheck.getMonnaie());
        		existingSegValid.setMontant(segmentationToCheck.getMontant());
        		existingSegValid.setPolitiqueVoyage(segmentationToCheck.getPolitiqueVoyage());
        		existingSegValid.setPotentiel(segmentationToCheck.getPotentiel());
        		
        		segmentationChecked.add(existingSegValid);
	        } 
        }
        
        return segmentationChecked;
    }
    
    private List<Segmentation> check(Segmentation segmentationToCheck, Agence agence, boolean allowMultiple, boolean allowUpdate) throws JrafDomainRollbackException {
    	// segmentationSaved : bo used with DAO layer
    	List<Segmentation> segmentationChecked = new ArrayList<Segmentation>();

    	// Get the existing valid segmentation of the pm
    	Segmentation existingSegValid = null;
    	if (allowMultiple) {
    		for (Segmentation segmentation : agence.getSegmentations()) {
    			if (segmentation.getCle().equals(segmentationToCheck.getCle())) {    				
    				existingSegValid = segmentation;
    			}
    		}
    	} else {    		
    		for (Segmentation segmentation : agence.getSegmentations()) {
    			if (segmentation.getDateSortie() == null) {    				
    				existingSegValid = segmentation;
    			}
    		}
    	}
    	
		// No key sent with valid data will generate a create of this segmentation
        if (segmentationToCheck.getCle() == null) { 
        	// Creation

    		if (!RefTableREF_TYP_SEG_A._REF_SGA.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGC.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGM.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_SGR.equals(segmentationToCheck.getType())
        			&& !RefTableREF_TYP_SEG_A._REF_STO.equals(segmentationToCheck.getType())
        	   )
        		throw new JrafDomainRollbackException("234"); 
    		
        	if (!RefTableREF_NIV_SEG_A._REF_A.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_B.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_C.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_D.equals(segmentationToCheck.getNiveau())
        		&& !RefTableREF_NIV_SEG_A._REF_N.equals(segmentationToCheck.getNiveau())        	)
        		throw new JrafDomainRollbackException("153"); // REF_ERREUR : INVALID MARKET CHOICE LEVEL
        	
        	// Close the existing valid market choice => set end date = start date newone - 1 day
        	if (!allowMultiple && existingSegValid != null) {
        		Calendar calDateSortie = Calendar.getInstance();
        		calDateSortie.setTime(segmentationToCheck.getDateEntree());
        		calDateSortie.add(Calendar.DAY_OF_WEEK, -1);
        		
        		existingSegValid.setDateSortie(calDateSortie.getTime());
        		
        		segmentationChecked.add(existingSegValid);
        	}
    		
        	segmentationToCheck.setPersonneMorale(agence);
        	
        	segmentationChecked.add(segmentationToCheck);
        } else if (segmentationToCheck.getCle() != null) {
        	// modify or delete
        	
        	if (existingSegValid == null || !existingSegValid.getCle().equals(segmentationToCheck.getCle())) {
        		
        	    throw new JrafDomainRollbackException("MARKET CHOICE NOT FOUND");
        	}
        	else {
        		// Modify
        		
        		// Check filed mandatory and validity 
        		checkMandatoryAndValidity(segmentationToCheck);
        		
        		// Type/Level/Date start and end date cannot be modified
        		if (!allowUpdate && !existingSegValid.getType().equals(segmentationToCheck.getType())) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE TYPE UPDATE NOT ALLOWED");
        		} else {
        			existingSegValid.setType(segmentationToCheck.getType());
        		}
        		
        		if (!allowUpdate && !RefTableREF_NIV_SEG_A._REF_N.equals(existingSegValid.getNiveau())
        		        && !existingSegValid.getNiveau().equals(segmentationToCheck.getNiveau())) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE LEVEL UPDATE NOT ALLOWED");
        		} else {
        		    existingSegValid.setNiveau(segmentationToCheck.getNiveau());
        		}
        		
        		if (!allowUpdate && existingSegValid.getDateEntree().compareTo(segmentationToCheck.getDateEntree()) != 0) {
        		    
                            throw new JrafDomainRollbackException("MARKET CHOICE START DATE UPDATE NOT ALLOWED");
        		} else {
        			existingSegValid.setDateEntree(segmentationToCheck.getDateEntree());
        		}
        		
        		if (!allowUpdate && ((existingSegValid.getDateSortie() != null ^ segmentationToCheck.getDateSortie() != null)
        		        || (existingSegValid.getDateSortie() != null && existingSegValid.getDateSortie().compareTo(segmentationToCheck.getDateSortie()) != 0))) {
        		    
        		    throw new JrafDomainRollbackException("MARKET CHOICE END DATE UPDATE NOT ALLOWED");
        		} else {
        			existingSegValid.setDateSortie(segmentationToCheck.getDateSortie());
        		}            		
        		
        		existingSegValid.setMonnaie(segmentationToCheck.getMonnaie());
        		existingSegValid.setMontant(segmentationToCheck.getMontant());
        		existingSegValid.setPolitiqueVoyage(segmentationToCheck.getPolitiqueVoyage());
        		existingSegValid.setPotentiel(segmentationToCheck.getPotentiel());
        		
        		segmentationChecked.add(existingSegValid);
	        } 
        }
        
        return segmentationChecked;
    }
    
    /**
     * 
     * @param pSegmentations
     * @param pPersonneMorale
     * @throws JrafDomainRollbackException 
     * @throws JrafDomainException
     */
    public void createUpdateOrDeleteForAgency(List<Segmentation> segmentations, Agence agence) throws JrafDomainRollbackException {
    	Assert.notNull(segmentations);
    	Assert.notNull(agence);
    	Assert.notNull(agence.getGin());
    	
    	if(agence.getSegmentations() == null) {
    		agence.setSegmentations(new HashSet<Segmentation>());
    	}
    	
    	List<Segmentation> segmentationsToCreate = new ArrayList<Segmentation>();
        List<Segmentation> segmentationsToRemove = new ArrayList<Segmentation>();
        List<Segmentation> segmentationsToUpdate = new ArrayList<Segmentation>();
    	
    	for(Segmentation segmentation : segmentations) {
    		List<Segmentation> segmentationsToSave = check(segmentation, agence);
    		
    		for (Segmentation segmentationToSave : segmentationsToSave) {
            	if (segmentationToSave.getCle() == null)
            		segmentationsToCreate.add(segmentationToSave);
            	else
            		segmentationsToUpdate.add(segmentationToSave);				
			}
    	}
    	
    	List<Segmentation> segmentationsList = new ArrayList<Segmentation>(agence.getSegmentations());
    	segmentationsList.removeAll(segmentationsToRemove);
        
    	segmentationsList.addAll(segmentationsToCreate);
        
        // Only one segmentation valid with date sortie null
        int nbSegmentationMax = 1;
	    for (Segmentation segmentation : segmentationsList) {
	    	if (segmentation.getDateSortie() == null)
	    		nbSegmentationMax--;
	    		
	    	if (nbSegmentationMax < 0 )
	    		throw new JrafDomainRollbackException("MAXIMUM NUMBER OF MARKET CHOICE REACHED");
	    }
        
        // Enregistrement en base
        for (Segmentation segmentation : segmentationsToCreate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToUpdate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToRemove) {
        	segmentationRepository.delete(segmentation);
        }
    }
    
    public void createUpdateOrDeleteForAgency(List<Segmentation> segmentations, Agence agence, boolean allowMultiple, boolean allowUpdate) throws JrafDomainRollbackException {
    	Assert.notNull(segmentations);
    	Assert.notNull(agence);
    	Assert.notNull(agence.getGin());
    	
    	if(agence.getSegmentations() == null) {
    		agence.setSegmentations(new HashSet<Segmentation>());
    	}

		List<Segmentation> segmentationsToCreate = new ArrayList<Segmentation>();
		List<Segmentation> segmentationsToRemove = new ArrayList<Segmentation>();
		List<Segmentation> segmentationsToUpdate = new ArrayList<Segmentation>();

		List<Segmentation> segmentationsToSave = new ArrayList<Segmentation>();
    	for(Segmentation segmentation : segmentations) {
    		segmentationsToSave.addAll(check(segmentation, agence, allowMultiple, allowUpdate));
    	}
    	
    	for (Segmentation segmentationToSave : segmentationsToSave) {
    		if (segmentationToSave.getCle() == null) {
    			closeSameLevelActiveSegmentationsNOTDTO(agence, segmentationToSave);
    			segmentationsToCreate.add(segmentationToSave);
    		}
    		else
    			segmentationsToUpdate.add(segmentationToSave);
    	}

		List<Segmentation> segmentationsList = new ArrayList<Segmentation>(agence.getSegmentations());
    	segmentationsList.removeAll(segmentationsToRemove);
        
    	segmentationsList.addAll(segmentationsToCreate);
        
    	if (!allowMultiple) {    		
    		// Only one segmentation valid with date sortie null
    		int nbSegmentationMax = 1;
    		for (Segmentation segmentation : segmentationsList) {
    			if (segmentation.getDateSortie() == null)
    				nbSegmentationMax--;
    			
    			if (nbSegmentationMax < 0 )
    				throw new JrafDomainRollbackException("MAXIMUM NUMBER OF MARKET CHOICE REACHED");
    		}
    	}
        
        // Enregistrement en base
        for (Segmentation segmentation : segmentationsToCreate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToUpdate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToRemove) {
        	segmentationRepository.delete(segmentation);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.ISegmentationUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDeleteFromBatch(List<Segmentation> pSegmentations, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pSegmentations);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        pPersonneMorale.setSegmentations(new HashSet<Segmentation>());
        
        // Check Validity of all Segmentations in the list
        for (Segmentation segmentation : pSegmentations) {
        	checkMandatoryAndValidity(segmentation);
        	segmentation.setPersonneMorale(pPersonneMorale);
        	pPersonneMorale.getSegmentations().add(segmentation);
        }
    	
        // Only one segmentation valid with date sortie null
        int nbSegmentationMax = 1;
	    for (Segmentation segmentation : pPersonneMorale.getSegmentations()) {
	    	if (segmentation.getDateSortie() == null)
	    		nbSegmentationMax--;
	    		
	    	if (nbSegmentationMax < 0 )
	    		throw new JrafDomainRollbackException("MAXIMUM NUMBER OF MARKET CHOICE REACHED");
	    }

        // Enregistrement en base
        for (Segmentation segmentation : pPersonneMorale.getSegmentations()) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
    }

    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.ISegmentationUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDelete(List<Segmentation> pSegmentations, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pSegmentations);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        // Initialize PersonneMorale Segmentations
        if (pPersonneMorale.getSegmentations() == null) {
            pPersonneMorale.setSegmentations(new HashSet<Segmentation>());
        }
        
        List<Segmentation> segmentationsToCreate = new ArrayList<Segmentation>();
        List<Segmentation> segmentationsToRemove = new ArrayList<Segmentation>();
        List<Segmentation> segmentationsToUpdate = new ArrayList<Segmentation>();
        
        // Check Validity of all Segmentations in the list
        for (Segmentation segmentation : pSegmentations) {
        	List<Segmentation> segmentationsToSave = check(segmentation, pPersonneMorale);
        	
        	for (Segmentation segmentationToSave : segmentationsToSave) {
            	if (segmentationToSave.getCle() == null)
            		segmentationsToCreate.add(segmentationToSave);
            	else
            		segmentationsToUpdate.add(segmentationToSave);				
			}
        }
    	
        // Check Rule for global segmentation
        // List to check the global RM
        List<Segmentation> segmentations = new ArrayList<Segmentation>(pPersonneMorale.getSegmentations());
        segmentations.removeAll(segmentationsToRemove);
        
        segmentations.addAll(segmentationsToCreate);
        
        // Only one segmentation valid with date sortie null
        int nbSegmentationMax = 1;
	    for (Segmentation segmentation : segmentations) {
	    	if (segmentation.getDateSortie() == null)
	    		nbSegmentationMax--;
	    		
	    	if (nbSegmentationMax < 0 )
	    		// TODO LBN créer erreur "MAXIMUM NUMBER OF MARKET CHOICE REACHED"
	    		throw new JrafDomainRollbackException("MAXIMUM NUMBER OF MARKET CHOICE REACHED");
	    }
        
        // Enregistrement en base
        for (Segmentation segmentation : segmentationsToCreate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToUpdate) {
        	segmentationRepository.saveAndFlush(segmentation);
        }
        for (Segmentation segmentation : segmentationsToRemove) {
        	segmentationRepository.delete(segmentation);
        }
    }

    @Transactional
    public void create(SegmentationDTO segmentationDTO, Agence agency) throws JrafDomainException {
    	if (segmentationDTO.getType() == null || segmentationDTO.getType().isEmpty() ||
    			segmentationDTO.getNiveau() == null || segmentationDTO.getNiveau().isEmpty() ||
    				segmentationDTO.getDateEntree() == null)
    		throw new JrafDomainException("133 - Mandatory (Niveau, Type, DateEntree)");
    	
    	if (!RefTableREF_NIV_SEG_A._REF_A.equals(segmentationDTO.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_B.equals(segmentationDTO.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_C.equals(segmentationDTO.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_D.equals(segmentationDTO.getNiveau())
    			&& !RefTableREF_NIV_SEG_A._REF_N.equals(segmentationDTO.getNiveau()))
    	{
    		throw new JrafDomainException("153 - Invalid Market Choice Level");
    	}
		
    	Segmentation segmentation = SegmentationTransform.dto2BoLight(segmentationDTO);
		// Link with PersonneMorale...
    	segmentation.setPersonneMorale(agency);
		
		// Persist...
    	segmentationRepository.saveAndFlush(segmentation);
		
		// Update DTO...
		Set<SegmentationDTO> segmentationDTOs = SegmentationTransform.bo2Dto(new HashSet<Segmentation>(Arrays.asList(segmentation)));
		if (segmentationDTOs != null && segmentationDTOs.size() > 0)
			segmentationDTO = (SegmentationDTO) segmentationDTOs.toArray()[0];
    }

	public void create(SegmentationDTO segmentationDTO, AgenceDTO agenceDTO)
			throws JrafDomainException {
		if (segmentationDTO.getType() == null || segmentationDTO.getType().isEmpty() ||
				segmentationDTO.getNiveau() == null || segmentationDTO.getNiveau().isEmpty() ||
				segmentationDTO.getDateEntree() == null)
			throw new JrafDomainException("133 - Mandatory (Niveau, Type, DateEntree)");

		if (!RefTableREF_NIV_SEG_A._REF_A.equals(segmentationDTO.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_B.equals(segmentationDTO.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_C.equals(segmentationDTO.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_D.equals(segmentationDTO.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_N.equals(segmentationDTO.getNiveau())) {
			throw new JrafDomainException("153 - Invalid Market Choice Level");
		}

		Segmentation segmentation = SegmentationTransform.dto2BoLight(segmentationDTO);
		Agence agency = AgenceTransform.dto2BoLight(agenceDTO);
		if (segmentation != null)
			segmentation.setPersonneMorale(agency);

		// Persist...
		segmentationRepository.saveAndFlush(segmentation);

		// Update DTO...
		Set<SegmentationDTO> segmentationDTOs = SegmentationTransform.bo2Dto(new HashSet<Segmentation>(Arrays.asList(segmentation)));
		if (segmentationDTOs != null && segmentationDTOs.size() > 0)
			segmentationDTO = (SegmentationDTO) segmentationDTOs.toArray()[0];

	}

	public Set<Integer> create(Set<Segmentation> segmentations, PersonneMorale personneMorale) throws JrafDomainException {
		if (segmentations == null || segmentations.isEmpty() || personneMorale == null) {
			throw new JrafDomainException("Segmentations and personne morale are mandatory");
		}
		segmentations.forEach(segmentation -> segmentation.setPersonneMorale(personneMorale));
		List<Segmentation> createdSegmentations = segmentationRepository.saveAll(segmentations);
		return createdSegmentations.stream().map(Segmentation::getCle).collect(Collectors.toSet());
	}

	@Transactional(readOnly = true)
	public List<SegmentationDTO> findByPm(String gin) throws JrafDomainException {
		List<SegmentationDTO> result = new ArrayList<>();
		List<Segmentation> response = segmentationRepository.findByPMGin(gin);
		for (Segmentation segmentation : response) {
			result.add(SegmentationTransform.bo2DtoLight(segmentation));
		}
		return result;
	}

	@Transactional
	public void createUpdateOrDeleteFromBatch(List<Segmentation> segmentations, Agence agencyFromDB) throws JrafDomainException {
    	// Check Agency status first : no change if struck off or close
		if (RefTableREF_STATUTPM._REF_X.equalsIgnoreCase(agencyFromDB.getStatut())) {
			throw new JrafDomainException("237 - Agency Closed");
		}
		if (RefTableREF_STATUTPM._REF_R.equalsIgnoreCase(agencyFromDB.getStatut())) {
			throw new JrafDomainException("936 - Strucked off agency");
		}

		// Segmentation from batch to save
		Segmentation segmentation = segmentations.get(0);
		Date entryDate = segmentation.getDateEntree();

		// Active segmentation from DB to update
		Set<Segmentation> segmentationsFromDB = agencyFromDB.getSegmentations();

		if (CollectionUtils.isEmpty(segmentationsFromDB)) {
			createFromBatch(segmentation, agencyFromDB);
		}

		else {
			// Close all active segmentations of the same type as the new one
			Set<Segmentation> activeSegmentations = segmentationsFromDB
					.stream()
					.filter(seg -> {
						return (seg.getDateSortie() == null || seg.getDateSortie().after(entryDate)) 
								&& seg.getType().equals(segmentation.getType());
					})
					.collect(Collectors.toSet());

			if (CollectionUtils.isEmpty(activeSegmentations)) {
				createFromBatch(segmentation, agencyFromDB);
			}
			else {
				updateFromBatch(segmentation, activeSegmentations, agencyFromDB);
			}
		}
	}

	@Transactional
	public void updateFromBatch(Segmentation segmentation, Set<Segmentation> activeSegmentations, Agence agencyFromDB) {
    	String level = segmentation.getNiveau();
    	String type = segmentation.getType();
    	Date entryDate = segmentation.getDateEntree();

    	// Calculate exit date
		Calendar cal = Calendar.getInstance();
		cal.setTime(entryDate);
		cal.add(Calendar.DATE, -1);
		Date exitDate = cal.getTime();

    	boolean sameSegmentation = false;

    	activeSegmentations.stream()
				.forEach(seg -> {
					seg.setDateSortie(exitDate);
					segmentationRepository.saveAndFlush(seg);
				});

    	segmentation.setPersonneMorale(agencyFromDB);
    	segmentationRepository.saveAndFlush(segmentation);
	}

	@Transactional
	public void createFromBatch(Segmentation segmentation, Agence agencyFromDB) throws JrafDomainException {
		// Nothing in DB
		if (!validMarketChoiceLevel(segmentation)) {
			throw new JrafDomainException("153 - Invalid Market Choice Level");
		}

		// Link with PersonneMorale...
		segmentation.setPersonneMorale(agencyFromDB);

		// Persist...
		segmentationRepository.saveAndFlush(segmentation);
	}

	private boolean validMarketChoiceLevel(Segmentation segmentation) {
    	boolean valid = true;
		if (!RefTableREF_NIV_SEG_A._REF_A.equals(segmentation.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_B.equals(segmentation.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_C.equals(segmentation.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_D.equals(segmentation.getNiveau())
				&& !RefTableREF_NIV_SEG_A._REF_N.equals(segmentation.getNiveau()))
		{
			valid = false;
		}

		return valid;
	}
	
	/**
	 * Close active segmentations of the same type as the one to be created
	 * @param agenceDTO Agency DTO
	 * @param segmentationDTO Segmentation DTO to be created
	 */
	public void closeSameLevelActiveSegmentations(AgenceDTO agenceDTO, SegmentationDTO segmentationDTO) {
		if (agenceDTO != null && StringUtils.isNotBlank(agenceDTO.getGin())) {			
			List<Segmentation> segmentationsFromDB = segmentationRepository.findByPMGin(agenceDTO.getGin());
			Date segmentationStartDate = SicDateUtils.localDateToDate(LocalDate.now());
			Date segmentationEndDate = SicDateUtils.localDateToDate(LocalDate.now().minusDays(1));
			Set<Segmentation> activeSegmentations = null;
			
			if (!CollectionUtils.isEmpty(segmentationsFromDB) && segmentationDTO != null) {			
				activeSegmentations = segmentationsFromDB.stream().filter(seg -> {
					return (seg.getDateSortie() == null || seg.getDateSortie().after(segmentationStartDate)) 
							&& seg.getType().equals(segmentationDTO.getType());
				})
						.collect(Collectors.toSet());
			}
			
			if (!CollectionUtils.isEmpty(activeSegmentations)) {			
				activeSegmentations.stream().forEach(seg -> {
					seg.setDateSortie(segmentationEndDate);
					segmentationRepository.saveAndFlush(seg);
				});
			}
		}
	}

	public void closeSameLevelActiveSegmentationsNOTDTO(Agence agence, Segmentation segmentation) {
		if (agence != null && StringUtils.isNotBlank(agence.getGin())) {
			List<Segmentation> segmentationsFromDB = segmentationRepository.findByPMGin(agence.getGin());
			Date segmentationStartDate = segmentation.getDateEntree();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(segmentationStartDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
//			Retrieving the day before the new startDate
			Date segmentationEndDate = calendar.getTime();
			Set<Segmentation> activeSegmentations = null;

			if (!CollectionUtils.isEmpty(segmentationsFromDB) && segmentation != null) {
				activeSegmentations = segmentationsFromDB.stream().filter(seg -> {
							return (seg.getDateSortie() == null || seg.getDateSortie().after(segmentationStartDate))
									&& seg.getType().equals(segmentation.getType());
						})
						.collect(Collectors.toSet());
			}

			if (!CollectionUtils.isEmpty(activeSegmentations)) {
				activeSegmentations.stream().forEach(seg -> {
					seg.setDateSortie(segmentationEndDate);
					segmentationRepository.saveAndFlush(seg);
				});
			}
		}
	}
	/*PROTECTED REGION END*/
}
