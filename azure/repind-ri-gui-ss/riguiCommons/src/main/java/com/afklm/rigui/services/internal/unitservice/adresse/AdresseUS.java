package com.afklm.rigui.services.internal.unitservice.adresse;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.PaysRepository;
import com.afklm.rigui.dto.adresse.PostalAddressDTO;
import com.afklm.rigui.dto.adresse.Usage_mediumDTO;
import com.afklm.rigui.entity.reference.Pays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdresseUS {

    /** logger */
    private static final Log log = LogFactory.getLog(AdresseUS.class);

    /*PROTECTED REGION ID(_B9NvMHvhEeCAmbGwtfTi3Q u var) ENABLED START*/
    private static final String BATCH           = "B";                       // Batch
    private static final String REALTIME        = "R";                       // Temps réel

    private static final String FORCAGE_O       = "O";                       // Phonetisation without Normalisation
    private static final String FORCAGE_N       = "N";                       // Phonetisation and Normalisation
    private static final String FORCAGE_C       = "C";                       // Phonetisation, Normalisation, and no database update
    private static final String FORCAGE_B       = "B";                       // Phonetisation, Normalisation and no error management
    private static final String FORCAGE_EMPTY   = "";                        // Like FORCAGE_B

    private static final String VALID           = "V";  // Adresse Valide
    private static final String INVALID         = "I";  // Adresse Invalide

    /** references on associated Repository */
    @Autowired
    private PaysRepository paysRepository;

    /**
     * Constructeur vide
     */
    public AdresseUS() {
    }


    /** 
     * isPaysNormalisable
     * @param codePays in String
     * @return The isPaysNormalisable as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public Boolean isPaysNormalisable(String codePays) throws JrafDomainException {
        /*PROTECTED REGION ID(_xAghAHvhEeCAmbGwtfTi3Q) ENABLED START*/
    	Boolean result = false;
    	if (codePays != null && codePays.length()>0) {
    		Optional<Pays> findPays = paysRepository.findById(codePays);
        	if (findPays.isPresent()) {
    			if (findPays.get().isPaysNormalisable(codePays)) {
    				result = true;
    			}
        	}    		
    	}
    	return result;
        /*PROTECTED REGION END*/
    }

    /** 
     * addNotEmptyElement
     * @param list in List
     * @param value in String
     * @throws JrafDomainException en cas d'exception
     */
    public void addNotEmptyElement(List list, String value) throws JrafDomainException {
        /*PROTECTED REGION ID(_nveUAH1hEeCAmbGwtfTi3Q) ENABLED START*/
        if (list != null && value != null)
        {
            value = value.trim();   // Delete blank character
            if (value.length()>0)
                list.add(value);
        }
        /*PROTECTED REGION END*/
    }
    

    /** 
     * getAFErrorCode
     * @param errCodeDetailled in String
     * @throws JrafDomainException en cas d'exception
     */
    public void getAFErrorCode(String errCodeDetailled) throws JrafDomainException {
        /*PROTECTED REGION ID(_ATb-kH--EeCV0v4ujvP1cA) ENABLED START*/
        List<String> listeErrCodeDetailled = new ArrayList<String>();
        String sErrCode;
        int nPos = 0;
        do
        {
            nPos = errCodeDetailled.indexOf('-');
            if (nPos>=0)
            {
                sErrCode = errCodeDetailled.substring(0,nPos);
                errCodeDetailled = errCodeDetailled.substring(nPos+1,errCodeDetailled.length());
            }
            else
                sErrCode = errCodeDetailled;
            if (sErrCode.length()>0)
                listeErrCodeDetailled.add(sErrCode);
        }
        while (nPos>=0);

        ///////////////////////////////////////////////////////////////////////////////////////
        // En v1 :
        // - Seulement le premier code d'erreur detaillé est traité
        // - Le mapping se fait uniquement sur les erreurs throwées actuellement avec Normail
        ///////////////////////////////////////////////////////////////////////////////////////


        if (listeErrCodeDetailled.size()>0)
        {
            // Recuperation du premier code d'erreur detaillés
            sErrCode = listeErrCodeDetailled.get(0).trim();

            // Mapping Soft-AF

            if ("2".equals(sErrCode))                        // City or Zip code or CP error
                throw new JrafDomainException("VILLE OU CP INCONNU");     // Todo : modifier le message _REF_200 en database car "ville non trouvée" uniquement

            if ("7".equals(sErrCode))                        // Unknow street
            	throw new JrafDomainException("202");
        }

        throw new JrafDomainException("120");
        /*PROTECTED REGION END*/
    }
    

    /** 
     * hasMailingFlagInParamList
     * @param listAdrPost in PostalAddressDTO
     * @param ain in long
     * @return The hasMailingFlagInParamList as <code>boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public boolean hasMailingFlagInParamList(List<PostalAddressDTO> listAdrPost, long ain) throws JrafDomainException {
        /*PROTECTED REGION ID(_9NF9sIH3EeCtut40RvtPWA) ENABLED START*/
    	boolean result = false;
    	for (PostalAddressDTO addressDTO : listAdrPost) {
	      if ((Long.parseLong(addressDTO.getSain()) != ain) && (isISISMailling(addressDTO))) {
		result = true;;
	      }			
		}
    	  return result;
        /*PROTECTED REGION END*/
    }
    

    /** 
     * isISISMailling
     * @param address in PostalAddressDTO
     * @return The isISISMailling as <code>boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public boolean isISISMailling(PostalAddressDTO address) throws JrafDomainException {
        /*PROTECTED REGION ID(_ka8AwIH5EeCtut40RvtPWA) ENABLED START*/
        boolean bRet = false;
        for (Usage_mediumDTO usage : address.getUsage_mediumdto()) {
            if (usage.getScode_application().equalsIgnoreCase("ISI"))
            {
            	if ("M".equalsIgnoreCase(usage.getSrole1())) {
            		bRet = true;
            	}
            	if ("M".equalsIgnoreCase(usage.getSrole2())) {
            		bRet = true;
            	}
            	if ("M".equalsIgnoreCase(usage.getSrole3())) {
            		bRet = true;
            	}
            	if ("M".equalsIgnoreCase(usage.getSrole4())) {
            		bRet = true;
            	}
            	if ("M".equalsIgnoreCase(usage.getSrole5())) {
            		bRet = true;
            	}

            }			
		}
        return bRet;
        /*PROTECTED REGION END*/
    }
}
