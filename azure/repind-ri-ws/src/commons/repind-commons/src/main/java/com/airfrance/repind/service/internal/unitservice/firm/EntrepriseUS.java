package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.firme.EntrepriseRepository;
import com.airfrance.repind.entity.firme.Entreprise;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.profil.ProfilFirme;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class EntrepriseUS {

    /** logger */
    private static final Log log = LogFactory.getLog(EntrepriseUS.class);

    /*PROTECTED REGION ID(_rbYl8FRQEeSfCs1mvZekEQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    /**
     * empty constructor
     */
    public EntrepriseUS() {
    }

    /*PROTECTED REGION ID(_rbYl8FRQEeSfCs1mvZekEQ u m) ENABLED START*/
    // add your custom methods here if necessary
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEntrepriseUS#newEntreprise(Etablissement)
     */
    public Entreprise newParent(Etablissement pEtablissement) throws JrafDomainException {

        // l'établissement ne doit pas avoir d'entreprise père
        Assert.isNull(pEtablissement.getParent(), "L'établissement ne doit pas avoir d'entreprise père");
        
        Entreprise entrepriseParente = new Entreprise();       
        
        entrepriseParente.setActiviteLocal(pEtablissement.getActiviteLocal());
        entrepriseParente.setCodeIndustrie(pEtablissement.getCodeIndustrie());
        entrepriseParente.setCodeSource(pEtablissement.getCodeSource());
        entrepriseParente.setCodeSupport(pEtablissement.getCodeSupport());
        entrepriseParente.setDateCreation(pEtablissement.getDateCreation());
        entrepriseParente.setDateModification(pEtablissement.getDateCreation());// On renseigne les xxxModification car non nullable en bdd => TODO les rendre nullable
        entrepriseParente.setNom(pEtablissement.getNom());
        
        if(!StringUtils.isEmpty(pEtablissement.getSiret())) {
            entrepriseParente.setSiren(pEtablissement.getSiret().substring(0, 9));
        } else {
            entrepriseParente.setSiren(""); // afin de forcer la création d'un enregistrement (certes vide) dans la table ENTREPRISE car l'IHM l'exige (defect 42).
        }
        
        entrepriseParente.setSignatureCreation(pEtablissement.getSignatureCreation());
        entrepriseParente.setSignatureModification(pEtablissement.getSignatureCreation());// On renseigne les xxxModification car non nullable en bdd => TODO les rendre nullable
        entrepriseParente.setSiteCreation(pEtablissement.getSiteCreation());
        entrepriseParente.setSiteInternet(pEtablissement.getSiteInternet());
        entrepriseParente.setSiteModification(pEtablissement.getSiteCreation());// On renseigne les xxxModification car non nullable en bdd => TODO les rendre nullable
        entrepriseParente.setStatut(pEtablissement.getStatut());
        entrepriseParente.setStatutJuridique(pEtablissement.getStatutJuridique());
        entrepriseParente.setTypeDemarchage(pEtablissement.getTypeDemarchage());
        
        // on crée un profil firme identique à celui de l'établissement
        if (pEtablissement.getProfilFirme() != null) {
            
            ProfilFirme pf = new ProfilFirme();
            BeanUtils.copyProperties(pEtablissement.getProfilFirme(), pf);
            pf.setGin(entrepriseParente.getGin());
            entrepriseParente.setProfilFirme(pf);
        }            
        
        return entrepriseParente;
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEntrepriseUS#checkParent(Etablissement)
     */
    public void checkParent(Etablissement pEtablissement) throws JrafDomainException {

        Optional<Entreprise> parent = entrepriseRepository.findById(pEtablissement.getParent().getGin());
        if (!parent.isPresent()) {
            // TODO lever exception : aucune entreprise n'a pour gin pEtablissement.getParent().getGin()
            throw new UnsupportedOperationException();
        }
        pEtablissement.setParent(parent.get());
        
        // RG - type de parent
        if (!pEtablissement.getParent().getClass().equals(Entreprise.class)) {
            // TODO lever exception : le parent doit être une entreprise
            throw new UnsupportedOperationException();

        } 
//        else {
//
//            // le parent est bien une entreprise
//            Entreprise entrepriseParente = (Entreprise) pEtablissement.getParent();
//
//            // le siret est fourni
//            if (!StringUtils.isEmpty(pEtablissement.getSiret())) {
//
//                String sirenDeduit = pEtablissement.getSiret().substring(0, 9);
//                if (!StringUtils.isEmpty(entrepriseParente.getSiren())) {
//
//                    if (!entrepriseParente.getSiren().equals(sirenDeduit)) {
//                        // TODO lever exception : le siren du parent ne correspond pas aux 9 premiers chiffres du siret fourni
//                        throw new UnsupportedOperationException();
//                    }
//                } else {
//
//                    // TODO LBN : doit-on renseigner le siren du parent (avec les 9 premiers chiffres du siret fourni)
//                    // entrepriseParente.setSiren(sirenDeduit);
//                    throw new UnsupportedOperationException();
//                }
//            }
//        }
    }
    /*PROTECTED REGION END*/
}
