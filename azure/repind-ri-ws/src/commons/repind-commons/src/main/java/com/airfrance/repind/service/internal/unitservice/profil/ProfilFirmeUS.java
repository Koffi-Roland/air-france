package com.airfrance.repind.service.internal.unitservice.profil;

import com.airfrance.repind.entity.refTable.RefTableLANGUES;
import com.airfrance.repind.entity.refTable.RefTableREF_TYP_MAIL;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.profil.ProfilFirmeRepository;
import com.airfrance.repind.dto.profil.ProfilFirmeDTO;
import com.airfrance.repind.dto.profil.ProfilFirmeTransform;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.profil.ProfilFirme;
import com.airfrance.repind.util.SicBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/*PROTECTED REGION END*/


/**
 * <p>Title : ProfilFirmeUS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class ProfilFirmeUS {

    /** logger */
    private static final Log log = LogFactory.getLog(ProfilFirmeUS.class);

    /*PROTECTED REGION ID(_925SQOHaEeS79pPzHY2rFw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private ProfilFirmeRepository profilFirmeRepository;

    /**
     * empty constructor
     */
    public ProfilFirmeUS() {
    }

    public ProfilFirmeRepository getProfilFirmeRepository() {
		return profilFirmeRepository;
	}

	public void setProfilFirmeRepository(ProfilFirmeRepository profilFirmeRepository) {
		this.profilFirmeRepository = profilFirmeRepository;
	}

	/*PROTECTED REGION ID(_925SQOHaEeS79pPzHY2rFw u m) ENABLED START*/
    // add your custom methods here if necessary
	
	 @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	    public void create(ProfilFirmeDTO profilFirmeDTO) throws JrafDomainException {

	        ProfilFirme profilFirme = null;

	        // transformation light dto -> bo
	        profilFirme = ProfilFirmeTransform.dto2Bo(profilFirmeDTO);	        	      
	        
	        checkMandatoryAndValidity(profilFirme);
	        
	        // creation en base
	        profilFirme = profilFirmeRepository.saveAndFlush(profilFirme);

	        // Version update and Id update if needed
	        ProfilFirmeTransform.bo2DtoLight(profilFirme, profilFirmeDTO);
	       /*PROTECTED REGION END*/
	    }
	 
    private void checkMandatoryAndValidity(ProfilFirme pf) throws JrafDomainRollbackException {
        // Export activity
        if (!StringUtils.isEmpty(pf.getExporting()) 
                        && !"O".equals(pf.getExporting())
                        && !"Y".equals(pf.getExporting())
                        && !"N".equals(pf.getExporting()))
                throw new JrafDomainRollbackException("161"); // REF_ERREUR : INVALID EXPORT CODE (Y/N)
        
        // Mailing indicator 
        if (!StringUtils.isEmpty(pf.getMailing())
                        && !RefTableREF_TYP_MAIL.instance().estValide(pf.getMailing(), ""))
                throw new JrafDomainRollbackException("165"); // REF_ERREUR : INVALID MAILING CODE (A,T OR N)
        
        // INSEE code
        // TODO Check validity of INSEE Code
        
        // Non-payment
        if (!StringUtils.isEmpty(pf.getDefautPaiement()) 
                        && !"O".equals(pf.getDefautPaiement())
                        && !"Y".equals(pf.getDefautPaiement())
                        && !"N".equals(pf.getDefautPaiement()))
                throw new JrafDomainRollbackException("164"); // REF_ERREUR : INVALID NON PAYMENT CODE (Y/N)
        
        // No sales
        if (!StringUtils.isEmpty(pf.getInterdictionVente()) 
                        && !"O".equals(pf.getInterdictionVente())
                        && !"Y".equals(pf.getInterdictionVente())
                        && !"N".equals(pf.getInterdictionVente()))
                throw new JrafDomainRollbackException("163"); // REF_ERREUR : INVALID SALES RESTRICTION CODE (Y/N)
        
        // Spoken language
        if (!StringUtils.isEmpty(pf.getLangueParlee())
                        && !RefTableLANGUES.instance().estValide(pf.getLangueParlee(), ""))
                throw new JrafDomainRollbackException("135"); // REF_ERREUR : INVALID LANGUAGE CODE
        
        // Written language
        if (!StringUtils.isEmpty(pf.getLangueEcrite())
                        && !RefTableLANGUES.instance().estValide(pf.getLangueEcrite(), ""))
                throw new JrafDomainRollbackException("135"); // REF_ERREUR : INVALID LANGUAGE CODE
        
        // Nationality
        // TODO Check validity of Nationality
    }

    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IProfilFirmUS#check(java.util.List, PersonneMorale)
     */
    public void check(ProfilFirme pProfilFirme, PersonneMorale pPersonneMorale) throws JrafDomainException {

        Assert.notNull(pProfilFirme);
        Assert.notNull(pPersonneMorale);
        
        // Check global rule  
        if (SicBeanUtils.getNotNullPropertyNames(pProfilFirme).length == 0) {
                // Empty block => remove existing pf
                if (pPersonneMorale.getProfilFirme() != null) {
                        ProfilFirme pf = pPersonneMorale.getProfilFirme();
                        pPersonneMorale.setProfilFirme(null);
                        profilFirmeRepository.delete(pf);
                }
        } else {        // creation/modification
            // Check Validity of ProfilFirm
            checkMandatoryAndValidity(pProfilFirme);

            if (pPersonneMorale.getProfilFirme() == null) {
                // create the profilFirme
                ProfilFirme pf = new ProfilFirme();
                pf.setGin(pPersonneMorale.getGin());
                ProfilFirme newPf = profilFirmeRepository.saveAndFlush(pf);
                pPersonneMorale.setProfilFirme(newPf);
            }
            // Modif and creation
            BeanUtils.copyProperties(pProfilFirme, pPersonneMorale.getProfilFirme(), "gin", "personneMorale");
        }
    }
    /*PROTECTED REGION END*/

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(ProfilFirmeDTO profilFirmeDTO) throws JrafDomainException {

        ProfilFirme profilFirme = null;

        // transformation light dto -> bo
        profilFirme = ProfilFirmeTransform.dto2Bo(profilFirmeDTO);

        checkMandatoryAndValidity(profilFirme);

        Optional<ProfilFirme> profilFromDB = profilFirmeRepository.findById(profilFirmeDTO.getGin());

        if (profilFromDB.isPresent()) {
            // Update
            ProfilFirme profil = profilFromDB.get();
            if (StringUtils.isNotBlank(profilFirmeDTO.getDefautPaiement())) {
                profil.setDefautPaiement(profilFirmeDTO.getDefautPaiement());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getExporting())) {
                profil.setExporting(profilFirmeDTO.getExporting());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getMailing())) {
                profil.setMailing(profilFirmeDTO.getMailing());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getCodeInseeEmp())) {
                profil.setCodeInseeEmp(profilFirmeDTO.getCodeInseeEmp());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getDefautPaiement())) {
                profil.setDefautPaiement(profilFirmeDTO.getDefautPaiement());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getInterdictionVente())) {
                profil.setInterdictionVente(profilFirmeDTO.getInterdictionVente());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getLangueEcrite())) {
                profil.setLangueEcrite(profilFirmeDTO.getLangueEcrite());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getNationalite())) {
                profil.setNationalite(profilFirmeDTO.getNationalite());
            }
            if (profilFirmeDTO.getNombreEmploye() != null) {
                profil.setNombreEmploye(profilFirmeDTO.getNombreEmploye());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getImporting())) {
                profil.setImporting(profilFirmeDTO.getImporting());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getLangueParlee())) {
                profil.setLangueParlee(profilFirmeDTO.getLangueParlee());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getNiveauSegmentation())) {
                profil.setNiveauSegmentation(profilFirmeDTO.getNiveauSegmentation());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getTypeClient())) {
                profil.setTypeClient(profilFirmeDTO.getTypeClient());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getTypeSegmentation())) {
                profil.setTypeSegmentation(profilFirmeDTO.getTypeSegmentation());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getCodGrpTie())) {
                profil.setCodGrpTie(profilFirmeDTO.getCodGrpTie());
            }
            if (StringUtils.isNotBlank(profilFirmeDTO.getCodTypTie())) {
                profil.setCodTypTie(profilFirmeDTO.getCodTypTie());
            }
            profilFirme = profil;
        }

        profilFirme = profilFirmeRepository.saveAndFlush(profilFirme);

        // Version update and Id update if needed
        ProfilFirmeTransform.bo2DtoLight(profilFirme, profilFirmeDTO);
        /*PROTECTED REGION END*/
    }
 
}
