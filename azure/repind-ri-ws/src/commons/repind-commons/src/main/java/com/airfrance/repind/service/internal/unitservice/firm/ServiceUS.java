package com.airfrance.repind.service.internal.unitservice.firm;

import com.airfrance.repind.entity.refTable.RefTableCODE_INDUS;
import com.airfrance.repind.entity.refTable.RefTableREF_DEMARCH;
import com.airfrance.repind.entity.refTable.RefTableREF_STA_JURI;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.ServiceRepository;
import com.airfrance.repind.dao.reference.ActiviteRepository;
import com.airfrance.repind.entity.firme.Service;
import com.airfrance.repind.entity.firme.enums.LegalPersonStatusEnum;
import com.airfrance.repind.entity.firme.enums.SourceCodeEnum;
import com.airfrance.repind.entity.firme.enums.SupportCodeEnum;
import com.airfrance.repind.entity.reference.Activite;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceUS {

    /** logger */
    private static final Log log = LogFactory.getLog(ServiceUS.class);
    
    /** references on associated DAOs */
    @Autowired
    private ActiviteRepository activiteRepository;

    /*PROTECTED REGION ID(_Kx_DsKLlEeSXNpATSKyi0Q u var) ENABLED START*/
    /*
     * (non-Javadoc)
     * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkStatut(Service)
     */
     public void checkStatutWhenCreating(final Service pService) throws JrafDomainException {
     	
         log.info("call checkStatut with statut " + pService.getStatut());
         if (StringUtils.isEmpty(pService.getStatut())) {
             
             // Erreur 154 - STATUS MANDATORY
             throw new JrafDomainRollbackException("154");
             
         } else if (LegalPersonStatusEnum.fromLiteral(pService.getStatut()) == null) {
             
             // Erreur 177 - INVALID STATUS
             throw new JrafDomainRollbackException("177");
             
         } else if (!LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pService.getStatut())) && !LegalPersonStatusEnum.TEMPORARY.equals(LegalPersonStatusEnum.fromLiteral(pService.getStatut()))) {
             
             throw new JrafDomainRollbackException("STATUS NOT SUPPORTED");
         }
     }
     
     /*
      * (non-Javadoc)
      * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkStatut(Service)
      */
      public void checkStatutWhenUpdating(final Service pService) throws JrafDomainException {
      	
          log.info("call checkStatut with statut " + pService.getStatut());
          if (StringUtils.isEmpty(pService.getStatut())) {
              
              // Erreur 154 - STATUS MANDATORY
              throw new JrafDomainRollbackException("154");
              
          } else if (LegalPersonStatusEnum.fromLiteral(pService.getStatut()) == null) {
              
              // Erreur 177 - INVALID STATUS
              throw new JrafDomainRollbackException("177");
              
          } else if (!LegalPersonStatusEnum.ACTIVE.equals(LegalPersonStatusEnum.fromLiteral(pService.getStatut())) && !LegalPersonStatusEnum.TEMPORARY.equals(LegalPersonStatusEnum.fromLiteral(pService.getStatut())) && !LegalPersonStatusEnum.CLOSED.equals(LegalPersonStatusEnum.fromLiteral(pService.getStatut()))) {
              
              throw new JrafDomainRollbackException("STATUS NOT SUPPORTED");
          }
      }
      
      /*
       * (non-Javadoc)
       * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkNom(Service)
       */
       public void checkNom(final Service pService) throws JrafDomainException {
           
           log.info("call checkNom with nom " + pService.getNom());
           if (StringUtils.isEmpty(pService.getNom())) {
               
               throw new JrafDomainRollbackException("BUSINESS NAME MANDATORY");
           }
       }
       
       /*
        * (non-Javadoc)
        * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkCodeSource(Service)
        */    
        public void checkCodeSource(final Service pService) throws JrafDomainException {
            
            log.info("call checkCodeSource with codeSource " + pService.getCodeSource());
            if (pService.getCodeSource() != null && SourceCodeEnum.fromLiteral(pService.getCodeSource()) == null){
                
                // Erreur 147 - INVALID SOURCE CODE
                throw new JrafDomainRollbackException("147");
            }	
        }
        
        /*
         * (non-Javadoc)
         * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkCodeSupport(Service)
         */    
         public void checkCodeSupport(final Service pService) throws JrafDomainException {
             
             log.info("call checkCodeSupport with codeSupport " + pService.getCodeSupport());
             if (pService.getCodeSupport() != null && SupportCodeEnum.fromLiteral(pService.getCodeSupport()) == null) {
                 
                 // Erreur 148 - INVALID SUPPORT CODE
                 throw new JrafDomainRollbackException("148");
             }        
         }
         
         /*
          * (non-Javadoc)
          * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkActivitetLocale(Service)
          */      
          public void checkActivitetLocale(final Service pService) throws JrafDomainException {
              
              log.info("call checkActivitetLocale with activitetLocal " + pService.getActiviteLocal());
          	if (!StringUtils.isEmpty(pService.getActiviteLocal())) {
          	    
          	    Optional<Activite> activite = activiteRepository.findByActivite(pService.getActiviteLocal());
          	    if (!activite.isPresent()) {
          	        // Erreur 160 - INVALID ACTIVITY CODE
          	        throw new JrafDomainRollbackException("160");
          	    }
          	}
          }
          
          /*
           * (non-Javadoc)
           * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkCodeIndus(Service)
           */    
           public void checkCodeIndus(final Service pService) throws JrafDomainException {
               
               log.info("call checkCodeIndus with codeIndustrie " + pService.getCodeIndustrie());
               if (!StringUtils.isEmpty(pService.getCodeIndustrie())
               	&& !RefTableCODE_INDUS.instance().estValide(pService.getCodeIndustrie(), "")) {
                   
                   throw new JrafDomainRollbackException("159"); // Erreur 159 - INVALID INDUSTRY CODE	
               }
           }
           
           /*
            * (non-Javadoc)
            * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkStatutJuridique(Service)
            */        
            public void checkStatutJuridique(final Service pService) throws JrafDomainException {
                
                log.info("call checkStatutJuridique with statutJuridique " + pService.getStatutJuridique());
                if (StringUtils.isEmpty(pService.getStatutJuridique())
                	&& RefTableREF_STA_JURI.instance().estValide(pService.getStatutJuridique(), "")) {
                    
                    throw new JrafDomainRollbackException("146"); // Erreur 146 - INVALID LEGAL STATUS
                }
            } 
            
            /*
             * (non-Javadoc)
             * @see com.airfrance.sic.service.internal.unitservice.firm.IServiceUS#checkTypeDemarchage(Service)
             */      
             public void checkTypeDemarchage(final Service pService) throws JrafDomainException {
                 
                 log.info("call checkTypeDemarchage with typeDemarchage " + pService.getTypeDemarchage());
                 if (StringUtils.isEmpty(pService.getTypeDemarchage())
                         && RefTableREF_DEMARCH.instance().estValide(pService.getTypeDemarchage(), "")) {
                     
                     throw new JrafDomainRollbackException("149"); // Erreur 149 - INVALID DOOR TO DOOR SELLING
                 }
             }
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * empty constructor
     */
    public ServiceUS() {
    }

	public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
}
