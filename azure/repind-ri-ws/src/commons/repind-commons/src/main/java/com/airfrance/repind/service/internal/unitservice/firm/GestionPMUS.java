package com.airfrance.repind.service.internal.unitservice.firm;

/*PROTECTED REGION ID(_LhRUYOHXEeS79pPzHY2rFw US i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.refTable.RefTableREF_NAT_LIEN;
import com.airfrance.repind.entity.refTable.RefTableREF_TYP_NUMID;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.agence.AgenceRepository;
import com.airfrance.repind.dao.firme.GestionPMRepository;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.GestionPM;
import com.airfrance.repind.entity.firme.NumeroIdent;
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

/*PROTECTED REGION END*/


/**
 * <p>Title : GestionPMUS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class GestionPMUS {

    /** logger */
    private static final Log log = LogFactory.getLog(GestionPMUS.class);

    /*PROTECTED REGION ID(_LhRUYOHXEeS79pPzHY2rFw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private AgenceRepository agenceRepository;

    /** references on associated DAOs */
    @Autowired
    private GestionPMRepository gestionRepository;

    /**
     * empty constructor
     */
    public GestionPMUS() {
    }

    public AgenceRepository getAgenceRepository() {
		return agenceRepository;
	}
    
	public void setAgenceRepository(AgenceRepository agenceRepository) {
		this.agenceRepository = agenceRepository;
	}

	/*PROTECTED REGION ID(_LhRUYOHXEeS79pPzHY2rFw u m) ENABLED START*/
    // add your custom methods here if necessary
    private void checkMandatoryAndValidity(GestionPM gestionPM) throws JrafDomainRollbackException {

    	// Type lien mandatory
    	if (StringUtils.isEmpty(gestionPM.getTypeLien()) 
    			|| (!RefTableREF_NAT_LIEN.instance().estValide(gestionPM.getTypeLien(), "")))
    		throw new JrafDomainRollbackException("324"); // REF_ERREUR : INVALID LINK TYPE

        // Lien ZC Firme mandatory
        if (StringUtils.isEmpty(gestionPM.getLienZCFirme())
                || !"O".equals(gestionPM.getLienZCFirme()) && !"N".equals(gestionPM.getLienZCFirme())) {
            
            // TODO LBN créer erreur "INVALID ZC LINK FOR AGENCY LINK"
            throw new JrafDomainRollbackException("INVALID ZC LINK FOR AGENCY LINK");
        }
            

        // Lien Privilegie
        if (StringUtils.isEmpty(gestionPM.getPrivilegie())
                || !"O".equals(gestionPM.getPrivilegie()) && !"N".equals(gestionPM.getPrivilegie())) {
            
            // TODO LBN créer erreur "INVALID PRIVILEGED AGENCY LINK"
            throw new JrafDomainRollbackException("INVALID PRIVILEGED AGENCY LINK");
        }      	
    	
    	// Numero Agence = numeroIdent
    	PersonneMorale agence = gestionPM.getPersonneMoraleGerante(); 
    	if (agence == null)
    		throw new JrafDomainRollbackException("184"); // REF_ERREUR : IATA NUMBER MANDATORY
    	List<NumeroIdent> numsAgence = new ArrayList<NumeroIdent>(agence.getNumerosIdent());
    	if (numsAgence == null || numsAgence.size() != 1)
    		throw new JrafDomainRollbackException("184"); // REF_ERREUR : IATA NUMBER MANDATORY
    	NumeroIdent numAgence = numsAgence.get(0);
    	if (numAgence == null || StringUtils.isEmpty(numAgence.getNumero()))
    		throw new JrafDomainRollbackException("184"); // REF_ERREUR : IATA NUMBER MANDATORY
    	try {
    		Agence agenceBO = agenceRepository.findAgencyByOptions(RefTableREF_TYP_NUMID._REF_IA, numAgence.getNumero(), new Date(), new ArrayList<String>());
    		
    		if (agenceBO == null)
    			throw new JrafDomainRollbackException("351"); // REF_ERREUR : INVALID IDENTIFICATION NUMBER
    		else
    			gestionPM.setPersonneMoraleGerante(agenceBO);
    	} catch (JrafDaoException daoe) {
    		throw new JrafDomainRollbackException("351"); // REF_ERREUR : INVALID IDENTIFICATION NUMBER
    	}
    	
    }

    
    private GestionPM check(GestionPM gestionPMToCheck, PersonneMorale pm) throws JrafDomainRollbackException {
        
        GestionPM gestionPMChecked = null;

        Date now = new Date();

        // No key sent with valid data will generate a create of this chiffre.
        if (gestionPMToCheck.getCle() == null) {
            // Creation

            // Check filed mandatory and validity
            checkMandatoryAndValidity(gestionPMToCheck);

            // Check if the same active link already exists
            List<GestionPM> lstGestionPM = new ArrayList<GestionPM>(pm.getPersonnesMoralesGerantes());
            for (GestionPM existingG : lstGestionPM) {
                if (existingG.getDateFinLien() == null) {
                    PersonneMorale agence = existingG.getPersonneMoraleGerante();
                    if (agence.getGin().equals(gestionPMToCheck.getPersonneMoraleGerante().getGin())) {
                        throw new JrafDomainRollbackException("515"); // REF_ERREUR : ID EXISTS
                    }
                }
            }

            // Complete the BO to save
            gestionPMToCheck.setDateDebLien(now);

            gestionPMToCheck.setDateMaj(now);
            gestionPMToCheck.setSignatureMaj(pm.getSignatureCreation());
            gestionPMToCheck.setSiteMaj(pm.getSiteCreation());

            if(!pm.getClass().equals(Agence.class))
            	gestionPMToCheck.setPersonneMoraleGeree(pm);
            else 
            	gestionPMToCheck.setPersonneMoraleGeree(gestionPMToCheck.getPersonneMoraleGeree());

            gestionPMChecked = gestionPMToCheck;
        } else if (gestionPMToCheck.getCle() != null) {
            // modify or delete

            // Get the existing chiffre
            GestionPM existingGestionPM = null;
            List<GestionPM> lstGestionPMExisting = new ArrayList<GestionPM>(pm.getPersonnesMoralesGerantes());
            for (GestionPM existingG : lstGestionPMExisting) {
                if (existingG.getCle().equals(gestionPMToCheck.getCle())) {
                    existingGestionPM = existingG;

                    break;
                }
            }

            if (existingGestionPM == null) {
                // TODO LBN créer erreur "AGENCY LINK NOT FOUND"
                throw new JrafDomainRollbackException("AGENCY LINK NOT FOUND");
            } else if (gestionPMToCheck.getPersonneMoraleGerante() != null && gestionPMToCheck.getPersonneMoraleGerante().getNumerosIdent() != null
                    && gestionPMToCheck.getPersonneMoraleGerante().getNumerosIdent().size() > 0) {
                // Modify

                // Check filed mandatory and validity
                checkMandatoryAndValidity(gestionPMToCheck);

                // Maj
                existingGestionPM.setDateDebLien(gestionPMToCheck.getDateDebLien());
                existingGestionPM.setDateFinLien(gestionPMToCheck.getDateFinLien());
                existingGestionPM.setLienZCFirme(gestionPMToCheck.getLienZCFirme());
                existingGestionPM.setPrivilegie(gestionPMToCheck.getPrivilegie());
                existingGestionPM.setTypeLien(gestionPMToCheck.getTypeLien());

                // Ne peut etre modifie conforme a l'IHM
                // existingGestionPM.setPersonneMoraleGeree(pm);

                existingGestionPM.setDateMaj(now);
                existingGestionPM.setSignatureMaj(gestionPMToCheck.getSignatureMaj());
                existingGestionPM.setSiteMaj(gestionPMToCheck.getSiteMaj());

                gestionPMChecked = existingGestionPM;
            } else {
                // Deactivate : set close date
                existingGestionPM.setDateDebLien(gestionPMToCheck.getDateDebLien());
                existingGestionPM.setDateFinLien(now);
                
                existingGestionPM.setDateMaj(now);
                existingGestionPM.setSignatureMaj(gestionPMToCheck.getSignatureMaj());
                existingGestionPM.setSiteMaj(gestionPMToCheck.getSiteMaj());

                gestionPMChecked = existingGestionPM;
            }
        }

        return gestionPMChecked;
    }
    
    public GestionPM checkForAgency(GestionPM gestionPMToCheck, PersonneMorale pm) throws JrafDomainRollbackException {
    	GestionPM gestionPMChecked = null;

        Date now = new Date();

        // No key sent with valid data will generate a create of this chiffre.
        if (gestionPMToCheck.getCle() == null) {
            // Creation

            // Check filed mandatory and validity
            checkMandatoryAndValidity(gestionPMToCheck);

            // Check if the same active link already exists
            List<GestionPM> lstGestionPM = new ArrayList<GestionPM>(pm.getPersonnesMoralesGerantes());
            for (GestionPM existingG : lstGestionPM) {
                if (existingG.getDateFinLien() == null) {
                    PersonneMorale agence = existingG.getPersonneMoraleGerante();
                    if (agence.getGin().equals(gestionPMToCheck.getPersonneMoraleGerante().getGin())) {
                        throw new JrafDomainRollbackException("515"); // REF_ERREUR : ID EXISTS
                    }
                }
            }

            // Complete the BO to save
            gestionPMToCheck.setDateDebLien(now);

            gestionPMToCheck.setDateMaj(now);
            gestionPMToCheck.setSignatureMaj(pm.getSignatureCreation());
            gestionPMToCheck.setSiteMaj(pm.getSiteCreation());

            if(!pm.getClass().equals(Agence.class))
            	gestionPMToCheck.setPersonneMoraleGeree(pm);
            else 
            	gestionPMToCheck.setPersonneMoraleGeree(gestionPMToCheck.getPersonneMoraleGeree());

            gestionPMChecked = gestionPMToCheck;
        } else if (gestionPMToCheck.getCle() != null) {
            // modify or delete

            // Get the existing chiffre
            GestionPM existingGestionPM = null;
            List<GestionPM> lstGestionPMExisting = new ArrayList<GestionPM>(pm.getPersonnesMoralesGerees());
            for (GestionPM existingG : lstGestionPMExisting) {
                if (existingG.getCle().equals(gestionPMToCheck.getCle())) {
                    existingGestionPM = existingG;
                    break;
                }
            }

            if (existingGestionPM == null) {
                // TODO LBN créer erreur "AGENCY LINK NOT FOUND"
                throw new JrafDomainRollbackException("AGENCY LINK NOT FOUND");
            } else if (gestionPMToCheck.getPersonneMoraleGerante() != null && gestionPMToCheck.getPersonneMoraleGerante().getNumerosIdent() != null
                    && gestionPMToCheck.getPersonneMoraleGerante().getNumerosIdent().size() > 0) {
                // Modify

                // Check filed mandatory and validity
                checkMandatoryAndValidity(gestionPMToCheck);

                // Maj
                existingGestionPM.setDateFinLien(gestionPMToCheck.getDateFinLien());
                existingGestionPM.setLienZCFirme(gestionPMToCheck.getLienZCFirme());
                existingGestionPM.setPrivilegie(gestionPMToCheck.getPrivilegie());
                existingGestionPM.setTypeLien(gestionPMToCheck.getTypeLien());

                // Ne peut etre modifie conforme a l'IHM
                // existingGestionPM.setPersonneMoraleGeree(pm);

                existingGestionPM.setDateMaj(now);
                existingGestionPM.setSignatureMaj(gestionPMToCheck.getSignatureMaj());
                existingGestionPM.setSiteMaj(gestionPMToCheck.getSiteMaj());

                gestionPMChecked = existingGestionPM;
            } else {
                // Deactivate : set close date
                existingGestionPM.setDateFinLien(now);
                
                existingGestionPM.setDateMaj(now);
                existingGestionPM.setSignatureMaj(gestionPMToCheck.getSignatureMaj());
                existingGestionPM.setSiteMaj(gestionPMToCheck.getSiteMaj());

                gestionPMChecked = existingGestionPM;
            }
        }

        return gestionPMChecked;
    }
    
    public void createOrUpdateOrDeleteForAgency(List<GestionPM> pGestionPMs, PersonneMorale pPersonneMorale) throws JrafDomainException {
    	Assert.notNull(pGestionPMs);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        // Initialize PersonneMorale GestionPM
        if (pPersonneMorale.getPersonnesMoralesGerantes() == null) {
            pPersonneMorale.setPersonnesMoralesGerantes(new HashSet<GestionPM>());
        }
        
        List<GestionPM> gestionPMsToCreate = new ArrayList<GestionPM>();
        List<GestionPM> gestionPMsToUpdate = new ArrayList<GestionPM>();
        
        // Check Validity of all GEstionPMs in the list
        for (GestionPM gestion : pGestionPMs) {
        	GestionPM gestionPMToSave = checkForAgency(gestion, pPersonneMorale);

        	// REPIND-1398 : Test SONAR NPE        	
        	if (gestionPMToSave != null && gestionPMToSave.getCle() == null)
        		gestionPMsToCreate.add(gestionPMToSave);
        	else
        		gestionPMsToUpdate.add(gestionPMToSave);
        }
    	
        // Check Rule for global GestionPM
        
        // List to check the global RM
        List<GestionPM> gestionPMs = new ArrayList<GestionPM>(pPersonneMorale.getPersonnesMoralesGerantes());
        gestionPMs.addAll(gestionPMsToCreate);

        // Enregistrement en base
        for (GestionPM gestionPM : gestionPMsToCreate) {
            gestionRepository.saveAndFlush(gestionPM);
        }
        for (GestionPM gestionPM : gestionPMsToUpdate) {
        	gestionRepository.saveAndFlush(gestionPM);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IGestionPMUS#createOrUpdateOrDelete(List, PersonneMorale)
     */
    public void createOrUpdateOrDelete(List<GestionPM> pGestionPMs, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pGestionPMs);
        Assert.notNull(pPersonneMorale);
        Assert.notNull(pPersonneMorale.getGin());
        
        // Initialize PersonneMorale GestionPM
        if (pPersonneMorale.getPersonnesMoralesGerantes() == null) {
            pPersonneMorale.setPersonnesMoralesGerantes(new HashSet<GestionPM>());
        }
        
        List<GestionPM> gestionPMsToCreate = new ArrayList<GestionPM>();
        List<GestionPM> gestionPMsToUpdate = new ArrayList<GestionPM>();
        
        // Check Validity of all GEstionPMs in the list
        for (GestionPM gestion : pGestionPMs) {
        	GestionPM gestionPMToSave = check(gestion, pPersonneMorale);

        	// REPIND-1398 : Test SONAR NPE        	
        	if (gestionPMToSave != null && gestionPMToSave.getCle() == null)
        		gestionPMsToCreate.add(gestionPMToSave);
        	else
        		gestionPMsToUpdate.add(gestionPMToSave);
        }
    	
        // Check Rule for global GestionPM
        
        // List to check the global RM
        List<GestionPM> gestionPMs = new ArrayList<GestionPM>(pPersonneMorale.getPersonnesMoralesGerantes());
        gestionPMs.addAll(gestionPMsToCreate);

        // Enregistrement en base
        for (GestionPM gestionPM : gestionPMsToCreate) {
            gestionRepository.saveAndFlush(gestionPM);
        }
        for (GestionPM gestionPM : gestionPMsToUpdate) {
        	gestionRepository.saveAndFlush(gestionPM);
        }
    }    
    /*PROTECTED REGION END*/
}
