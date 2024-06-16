package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.RefTableCODE_INDUS;
import com.airfrance.repind.entity.refTable.RefTableREF_DEMARCH;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_JURI;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.EtablissementRepository;
import com.airfrance.repind.dao.reference.ActiviteRepository;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.Service;
import com.airfrance.repind.entity.firme.enums.*;
import com.airfrance.repind.entity.reference.Activite;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class EtablissementUS {

    /** logger */
    private static final Log log = LogFactory.getLog(EtablissementUS.class);

    /*PROTECTED REGION ID(_oCcfUKLgEeSXNpATSKyi0Q u var) ENABLED START*/
    // add your custom variables here if necessary
    protected static final String SIRET_REGEXP = "[0-9]{14}";

    protected static final Pattern p = Pattern.compile(SIRET_REGEXP);
    
    /** references on associated DAOs */
    @Autowired
    private ActiviteRepository activiteRepository;
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private EtablissementRepository etablissementRepository;

    /**
     * empty constructor
     */
    public EtablissementUS() {
    }
    
    public EtablissementRepository getEtablissementRepository() {
		return etablissementRepository;
	}

	public void setEtablissementRepository(EtablissementRepository etablissementRepository) {
		this.etablissementRepository = etablissementRepository;
	}

	/**
     * Converti un char en un entier '0' => 0 et '9' => 9, et 'A' => 10 a 'Z' => 36,
     * les autres caractere sont aussi convertis pour que 'a' = 10 et 'z' = 36.
     * Pour les autres c'est un indedermine
     *
     * @param c le caractere qui doit etre converti
     * @return le chiffre
     */
    private int getDigit(char c) {
        
        int result = 0;
        if (c >= '0' && c <= '9') {
            result = c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            result = c - 'A' + 10;
        } else {
            result = c - 'a' + 10;
        }
        return result;
    }
    
    /**
     * Verifie la validite d'un numero en suivant l'algorithme Luhn tel que d'ecrit
     * dans <a href="http://fr.wikipedia.org/wiki/Luhn">wikipedia</a>
     * <p/>
     * Algo:
     * en fonction de la position du numero dans la sequence,
     * on multiplie pas 1 (pour les impaires) ou par 2 pour les paires
     * (1 etant le numero le plus a droite)
     * On fait la somme de tous les chiffres qui resulte de ces multiplications
     * (si un resultat etait 14, on ne fait pas +14 mais +1+4)
     * <p/>
     * Si le resultat de cette somme donne un reste de 0 une fois divise par 10
     * le numero est valide.
     *
     * @param siret une chaine composer que de chiffre
     * @return vrai si on a reussi a valider le numero
     */
    public boolean luhnChecksum(String siret) {

        char[] tab = siret.toCharArray();
        int sum = 0;
        for (int i = tab.length - 1; i >= 0; i--) {
            // recuperation de la valeur
            int n = getDigit(tab[i]);

            // 1ere phase il faut faire la multiplication par 1 ou 2

            // il faut faire x1 pour les paires et x2 sur les impaires.
            // en prenant en compte que le numero siret le plus a droite est le
            // 1 et le plus a gauche le 14.
            // mais comme en informatique on commence a 0 :D
            // il faut faire +1 sur l'indice puis un simple module 2 + 1
            // nous convient
            n *= (i + 1) % 2 + 1;

            // 2eme phase il faut faire l'addition

            // si une fois multiplie il est superieur a 9, il faut additionner
            // toutes ces constituante, mais comme il ne peut pas etre superieur
            // a 18, cela revient a retrancher 9
            if (n > 9) {
                n -= 9;
            }

            // on peut directement faire la somme
            sum += n;
        }

        // 3eme phase on verifie que c'est bien un multiple de 10
        boolean result = sum % 10 == 0;

        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkSiret(Etablissement)
     */
    public void checkSiret(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkSiret with siret " + pEtablissement.getSiret());
        
        // le statut est requis
        Assert.hasText(pEtablissement.getStatut(), "Le statut est requis");
        
        // le pays de domiciliation est requis
        PostalAddress adresseLocalisationValide = pEtablissement.fetchValidLocalisationPostalAddress();
        Assert.notNull(adresseLocalisationValide, "L'adresse de domiciliation est requise");
        String paysDomiciliation = adresseLocalisationValide.getScode_pays();
        Assert.notNull(paysDomiciliation, "le pays de domiciliation est requis");
        
        // DOM-TOM
        List<String> territoiresOutreMer = Arrays.asList(new String[]{"NC","PF","TF","WF"});
        List<String> departementsOutreMer = Arrays.asList(new String[]{"GP","MQ","RE","MF","BL","GF"});
        
        // RG Siret à Monaco : si on a un SIRET pour un etablissement X --> ERREUR 169 "SIRET NUMBER NOT ALLOWED"
        if ("MC".equals(paysDomiciliation) && LegalPersonStatusEnum.CLOSED.toLiteral().equals(pEtablissement.getStatut()) && StringUtils.isNotEmpty(pEtablissement.getSiret())) {
            
            throw new JrafDomainRollbackException("169");
        
        // RG Siret en FR et DOM
        } else if ("FR".equals(paysDomiciliation) || departementsOutreMer.contains(paysDomiciliation)) {
            
            // si l'etablissement est actif et que le type d'etablissement n'est pas A (assoc) ni AD (administration), si le siret est null --> ERREUR 168 "SIRET NUMBER MANDATORY"
            if (LegalPersonStatusEnum.ACTIVE.toLiteral().equals(pEtablissement.getStatut()) 
            		&& !FirmTypeEnum.ASSOCIATION.toLiteral().equals(pEtablissement.getType()) 
            		&& !FirmTypeEnum.ADMINISTRATION.toLiteral().equals(pEtablissement.getType()) 
            		&& StringUtils.isEmpty(pEtablissement.getSiret())) {
                
                throw new JrafDomainRollbackException("168");
            
            // si l'etablissement est X et que le SIRET n'est pas null --> ERREUR 169 "SIRET NUMBER NOT ALLOWED"
            } else if (LegalPersonStatusEnum.CLOSED.toLiteral().equals(pEtablissement.getStatut()) && !StringUtils.isEmpty(pEtablissement.getSiret())) {
                
                throw new JrafDomainRollbackException("169");
            }
      
        // RG Siret en TOM        
        } else if (territoiresOutreMer.contains(paysDomiciliation)) {
            
            // si l'etablissement est X et que le SIRET n'est pas null --> ERREUR 169 "SIRET NUMBER NOT ALLOWED"
            if (LegalPersonStatusEnum.CLOSED.toLiteral().equals(pEtablissement.getStatut()) && !StringUtils.isEmpty(pEtablissement.getSiret())) {
                
                throw new JrafDomainRollbackException("169");
            }
            
        // RG Siret hors MC et FR et DOM et TOM : si il y a un SIRET --> ERREUR 169  "SIRET NUMBER NOT ALLOWED"
        } else if (!StringUtils.isEmpty(pEtablissement.getSiret())) {
            
            throw new JrafDomainRollbackException("169");
        }
        
        // Validité du siret
        if (!StringUtils.isEmpty(pEtablissement.getSiret())) {
           
            // RG - le siret doit être unique
            if (etablissementRepository.existWithSameSiret(pEtablissement)) {                
                throw new JrafDomainRollbackException("166");
            }
            
            // RG - le siret doit respecter un certain format
            Matcher m = p.matcher(pEtablissement.getSiret());
            if (!m.matches() || !luhnChecksum(pEtablissement.getSiret())) {
            	throw new JrafDomainRollbackException("170");
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkStatut(Etablissement)
     */
     public void checkStatutWhenCreating(final Etablissement pEtablissement) throws JrafDomainException {
     	
         log.info("call checkStatut with statut " + pEtablissement.getStatut());
         if (StringUtils.isEmpty(pEtablissement.getStatut())) {
             
             // Erreur 154 - STATUS MANDATORY
             throw new JrafDomainRollbackException("154");
             
         } else if (LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut()) == null) {
             
             // Erreur 177 - INVALID STATUS
             throw new JrafDomainRollbackException("177");
             
         } else if (!LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut())) && !LegalPersonStatusEnum.TEMPORARY.equals(LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut()))) {
             
             // TODO LBN créer nouvelle erreur : STATUS NOT SUPPORTED
             throw new JrafDomainRollbackException("STATUS NOT SUPPORTED");
         }
     }
     
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkStatut(Etablissement)
    */
    public void checkStatutWhenUpdating(final Etablissement pEtablissement) throws JrafDomainException {
    	
        log.info("call checkStatut with statut " + pEtablissement.getStatut());
        if (StringUtils.isEmpty(pEtablissement.getStatut())) {
            
            // Erreur 154 - STATUS MANDATORY
            throw new JrafDomainRollbackException("154");
            
        } else if (LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut()) == null) {
            
            // Erreur 177 - INVALID STATUS
            throw new JrafDomainRollbackException("177");
            
        } else if (!LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut())) && !LegalPersonStatusEnum.TEMPORARY.equals(LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut())) && !LegalPersonStatusEnum.CLOSED.equals(LegalPersonStatusEnum.fromLiteral(pEtablissement.getStatut()))) {
            
            // TODO LBN créer nouvelle erreur : STATUS NOT SUPPORTED
            throw new JrafDomainRollbackException("STATUS NOT SUPPORTED");
        }
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkNom(Etablissement)
    */
    public void checkNom(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkNom with nom " + pEtablissement.getNom());
        if (StringUtils.isEmpty(pEtablissement.getNom())) {
            
            // TODO LBN créer nouvelle erreur : BUSINESS NAME MANDATORY
            throw new JrafDomainRollbackException("BUSINESS NAME MANDATORY");
        }
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkType(Etablissement)
    */
    public void checkType(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkType with type " + pEtablissement.getType());
        if (StringUtils.isEmpty(pEtablissement.getType())) {
            
            // Erreur 144 - FIRM TYPE MANDATORY
            throw new JrafDomainRollbackException("144");
            
        } else if (FirmTypeEnum.fromLiteral(pEtablissement.getType()) == null){
            
            // Erreur 156 - INVALID FIRM TYPE
            throw new JrafDomainRollbackException("156");
        }
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkCodeSource(Etablissement)
    */    
    public void checkCodeSource(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkCodeSource with codeSource " + pEtablissement.getCodeSource());
        if (pEtablissement.getCodeSource() != null && SourceCodeEnum.fromLiteral(pEtablissement.getCodeSource()) == null){
            
            // Erreur 147 - INVALID SOURCE CODE
            throw new JrafDomainRollbackException("147");
        }	
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkCodeSupport(Etablissement)
    */    
    public void checkCodeSupport(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkCodeSupport with codeSupport " + pEtablissement.getCodeSupport());
        if (pEtablissement.getCodeSupport() != null && SupportCodeEnum.fromLiteral(pEtablissement.getCodeSupport()) == null) {
            
            // Erreur 148 - INVALID SUPPORT CODE
            throw new JrafDomainRollbackException("148");
        }        
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkSiegeSocial(Etablissement)
    */     
    public void checkSiegeSocial(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkSiegeSocial with siegeSocial " + pEtablissement.getSiegeSocial());        
        if (StringUtils.isEmpty(pEtablissement.getSiegeSocial())) {
            
            if (pEtablissement.getGin() == null) {
                
                // valeur par défaut en création
                pEtablissement.setSiegeSocial(HeadOfficeEnum.NOT_HEAD_OFFICE.toLiteral());
            
            } else {
                
                // TODO LBN : créer nouvelle erreur HEAD OFFICE MANDATORY
                throw new JrafDomainRollbackException("HEAD OFFICE MANDATORY");
            }
            
        } else if (HeadOfficeEnum.fromLiteral(pEtablissement.getSiegeSocial()) == null) {
            
            // Erreur 158 - INVALID HEAD OFFICE
            throw new JrafDomainRollbackException("158");
        }
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkActivitetLocale(Etablissement)
    */      
    public void checkActivitetLocale(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkActivitetLocale with activitetLocal " + pEtablissement.getActiviteLocal());
    	if (!StringUtils.isEmpty(pEtablissement.getActiviteLocal())) {
    	    
    	    Optional<Activite> activite = activiteRepository.findByActivite(pEtablissement.getActiviteLocal());
    	    if (!activite.isPresent()) {
    	        // Erreur 160 - INVALID ACTIVITY CODE
    	        throw new JrafDomainRollbackException("160");
    	    }
    	}
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkCodeIndus(Etablissement)
    */    
    public void checkCodeIndus(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkCodeIndus with codeIndustrie " + pEtablissement.getCodeIndustrie());
        if (!StringUtils.isEmpty(pEtablissement.getCodeIndustrie())
        	&& !RefTableCODE_INDUS.instance().estValide(pEtablissement.getCodeIndustrie(), "")) {
            
            throw new JrafDomainRollbackException("159"); // Erreur 159 - INVALID INDUSTRY CODE	
        }
    }
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkStatutJuridique(Etablissement)
    */        
    public void checkStatutJuridique(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkStatutJuridique with statutJuridique " + pEtablissement.getStatutJuridique());
        if (StringUtils.isEmpty(pEtablissement.getStatutJuridique())
        	&& RefTableREF_STA_JURI.instance().estValide(pEtablissement.getStatutJuridique(), "")) {
            
            throw new JrafDomainRollbackException("146"); // Erreur 146 - INVALID LEGAL STATUS
        }
    }    
    
    /*
    * (non-Javadoc)
    * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkTypeDemarchage(Etablissement)
    */      
    public void checkTypeDemarchage(final Etablissement pEtablissement) throws JrafDomainException {
        
        log.info("call checkTypeDemarchage with typeDemarchage " + pEtablissement.getTypeDemarchage());
        if (StringUtils.isEmpty(pEtablissement.getTypeDemarchage())
                && RefTableREF_DEMARCH.instance().estValide(pEtablissement.getTypeDemarchage(), "")) {
            
            throw new JrafDomainRollbackException("149"); // Erreur 149 - INVALID DOOR TO DOOR SELLING
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkParent(Service)
     */
    public void checkParent(Service pService) throws JrafDomainException {

    	// REPFIRM-610: Fix service creation 
        Optional<Etablissement> parent = etablissementRepository.findById(pService.getParent().getGin());
        
        if (!parent.isPresent()) {
            // TODO lever exception : aucune entreprise n'a pour gin pEtablissement.getParent().getGin()
        	throw new JrafDomainRollbackException("PARENT FIRM NOT FOUND");
        }
        pService.setParent(parent.get());
        
        // RG - type de parent
        if (!pService.getParent().getClass().equals(Etablissement.class)) {
            // TODO lever exception : le parent doit être un etablissement
            //throw new UnsupportedOperationException();
        	throw new JrafDomainRollbackException("INVALID PARENT FIRM TYPE");
        }

    }
    
    /*
     * (non-Javadoc)
     * @see com.airfrance.repind.service.internal.unitservice.firm.IEtablissementUS#checkServices(Etablissement)
     */
    public void checkServices(final Etablissement pEtablissement) throws JrafDomainException {

    	for (PersonneMorale pm : pEtablissement.getEnfants()) {
    		if (pm instanceof Service && pm.getStatut().equals("A")) {
    			throw new JrafDomainRollbackException("CLOSING NOT ALLOWED CHECK OPENED SERVICES");
    		}
    	}

    }
    /*PROTECTED REGION END*/
}
