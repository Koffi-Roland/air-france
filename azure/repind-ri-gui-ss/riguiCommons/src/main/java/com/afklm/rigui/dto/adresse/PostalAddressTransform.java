package com.afklm.rigui.dto.adresse;

/*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.util.NormalizedStringUtilsV2;
import com.afklm.rigui.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/*PROTECTED REGION END*/

/**
 * <p>Title : PostalAddressTransform.java</p>
 * transformation bo <-> dto pour un(e) PostalAddress
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PostalAddressTransform {

    /*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(PostalAddressTransform.class);
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PostalAddressTransform() {
    }
    /**
     * dto -> bo for a PostalAddress
     * @param postalAddressDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PostalAddress dto2BoLight(PostalAddressDTO postalAddressDTO) throws JrafDomainException {
        // instanciation du BO
        PostalAddress postalAddress = new PostalAddress();
        dto2BoLight(postalAddressDTO, postalAddress);

        // on retourne le BO
        return postalAddress;
    }

    /**
     * dto -> bo for a postalAddress
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param postalAddressDTO dto
     * @param postalAddress bo
     */
    public static void dto2BoLight(PostalAddressDTO postalAddressDTO, PostalAddress postalAddress) {
    
        /*PROTECTED REGION ID(dto2BoLight_RpoWEDRWEeCGEoB0vWAi2A) ENABLED START*/
        
        dto2BoLightImpl(postalAddressDTO,postalAddress);
        
        postalAddress.setScode_pays(SicStringUtils.toUpperCaseWithoutAccents(postalAddressDTO.getScode_pays()));

        // REPIND-1767 : Store only ASCII char 
       	postalAddress.setScode_province(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getScode_province()));	
        postalAddress.setScomplement_adresse(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getScomplement_adresse()));
        postalAddress.setSlocalite(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getSlocalite()));
        postalAddress.setSno_et_rue(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getSno_et_rue()));
        postalAddress.setSraison_sociale(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getSraison_sociale()));
        postalAddress.setScode_postal(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getScode_postal()));
        postalAddress.setSville(NormalizedStringUtilsV2.normalizeString(postalAddressDTO.getSville()));
        
        /*PROTECTED REGION END*/
    }

    /**
     * dto -> bo for a postalAddress from Prospect
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param postalAddressDTO dto
     * @param postalAddress bo
     */
//    public static void dto2BoLight(ProspectLocalizationDTO prospectLocalizationDTO, PostalAddress postalAddress) {
    
        /*PROTECTED REGION ID(dto2BoLight_RpoWEDRWEeCGEoB0vWAi2A) ENABLED START*/
/*        
        dto2BoLightImpl(prospectLocalizationDTO,postalAddress);
        
        postalAddress.setScode_pays(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getCountryCode()));
        postalAddress.setScode_province(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getStateCode()));
//        postalAddress.setScomplement_adresse(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getScomplement_adresse()));
//        postalAddress.setSlocalite(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getSlocalite()));
        postalAddress.setSno_et_rue(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getPostalAddress()));
//        postalAddress.setSraison_sociale(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getSraison_sociale()));
        postalAddress.setSville(SicStringUtils.toUpperCaseWithoutAccents(prospectLocalizationDTO.getCity()));
*/        
        /*PROTECTED REGION END*/
//    }
    
    /**
     * dto -> bo implementation for a postalAddress
     * @param postalAddressDTO dto
     * @param postalAddress bo
     */
    private static void dto2BoLightImpl(PostalAddressDTO postalAddressDTO, PostalAddress postalAddress){
    
        // property of PostalAddressDTO
        postalAddress.setSain(postalAddressDTO.getSain());
        postalAddress.setSgin(postalAddressDTO.getSgin());
        postalAddress.setVersion(postalAddressDTO.getVersion());
        postalAddress.setSraison_sociale(postalAddressDTO.getSraison_sociale());
        postalAddress.setScomplement_adresse(postalAddressDTO.getScomplement_adresse());
        postalAddress.setSno_et_rue(postalAddressDTO.getSno_et_rue());
        postalAddress.setSlocalite(postalAddressDTO.getSlocalite());
        postalAddress.setScode_postal(postalAddressDTO.getScode_postal());
        postalAddress.setSville(postalAddressDTO.getSville());
        postalAddress.setScode_pays(postalAddressDTO.getScode_pays());
        postalAddress.setScode_province(postalAddressDTO.getScode_province());
        postalAddress.setScode_medium(postalAddressDTO.getScode_medium());
        postalAddress.setSstatut_medium(postalAddressDTO.getSstatut_medium());
        postalAddress.setSsite_modification(postalAddressDTO.getSsite_modification());
        postalAddress.setSsignature_modification(postalAddressDTO.getSsignature_modification());
        postalAddress.setDdate_modification(postalAddressDTO.getDdate_modification());
        postalAddress.setSsite_creation(postalAddressDTO.getSsite_creation());
        postalAddress.setSignature_creation(postalAddressDTO.getSignature_creation());
        postalAddress.setDdate_creation(postalAddressDTO.getDdate_creation());
        postalAddress.setSforcage(postalAddressDTO.getSforcage());
        postalAddress.setSdescriptif_complementaire(postalAddressDTO.getSdescriptif_complementaire());
        postalAddress.setSindadr(postalAddressDTO.getSindadr());
        postalAddress.setIcod_err(postalAddressDTO.getIcod_err());
        postalAddress.setIcod_warning(postalAddressDTO.getIcod_warning());
        postalAddress.setIcle_role(postalAddressDTO.getIcle_role());
        postalAddress.setIkey_temp(postalAddressDTO.getIkey_temp());
        postalAddress.setDdate_fonctionnel(postalAddressDTO.getDdate_fonctionnel());
        postalAddress.setSsite_fonctionnel(postalAddressDTO.getSsite_fonctionnel());
        postalAddress.setSsignature_fonctionnel(postalAddressDTO.getSsignature_fonctionnel());
        postalAddress.setScod_err_simple(postalAddressDTO.getScod_err_simple());
        postalAddress.setScod_err_detaille(postalAddressDTO.getScod_err_detaille());
        postalAddress.setStype_invalidite(postalAddressDTO.getStype_invalidite());
        postalAddress.setSenvoi_postal(postalAddressDTO.getSenvoi_postal());
        postalAddress.setDenvoi_postal(postalAddressDTO.getDenvoi_postal());
        postalAddress.setScod_app_send(postalAddressDTO.getScod_app_send());
    }
  
    /**
     * bo -> dto for a postalAddress
     * @param pPostalAddress bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PostalAddressDTO bo2DtoLight(PostalAddress pPostalAddress) throws JrafDomainException {
        // instanciation du DTO
        PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
        bo2DtoLight(pPostalAddress, postalAddressDTO);
        // on retourne le dto
        return postalAddressDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param postalAddress bo
     * @param postalAddressDTO dto
     */
    public static void bo2DtoLight(
        PostalAddress postalAddress,
        PostalAddressDTO postalAddressDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_RpoWEDRWEeCGEoB0vWAi2A) ENABLED START*/

        bo2DtoLightImpl(postalAddress, postalAddressDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param postalAddress bo
     * @param postalAddressDTO dto
     */
    private static void bo2DtoLightImpl(PostalAddress postalAddress,
        PostalAddressDTO postalAddressDTO){
    
        // simple properties
        postalAddressDTO.setSain(postalAddress.getSain());
        postalAddressDTO.setSgin(postalAddress.getSgin());
        postalAddressDTO.setVersion(postalAddress.getVersion());
        postalAddressDTO.setSraison_sociale(postalAddress.getSraison_sociale());
        postalAddressDTO.setScomplement_adresse(postalAddress.getScomplement_adresse());
        postalAddressDTO.setSno_et_rue(postalAddress.getSno_et_rue());
        postalAddressDTO.setSlocalite(postalAddress.getSlocalite());
        postalAddressDTO.setScode_postal(postalAddress.getScode_postal());
        postalAddressDTO.setSville(postalAddress.getSville());
        postalAddressDTO.setScode_pays(postalAddress.getScode_pays());
        postalAddressDTO.setScode_province(postalAddress.getScode_province());
        postalAddressDTO.setScode_medium(postalAddress.getScode_medium());
        postalAddressDTO.setSstatut_medium(postalAddress.getSstatut_medium());
        postalAddressDTO.setSsite_modification(postalAddress.getSsite_modification());
        postalAddressDTO.setSsignature_modification(postalAddress.getSsignature_modification());
        postalAddressDTO.setDdate_modification(postalAddress.getDdate_modification());
        postalAddressDTO.setSsite_creation(postalAddress.getSsite_creation());
        postalAddressDTO.setSignature_creation(postalAddress.getSignature_creation());
        postalAddressDTO.setDdate_creation(postalAddress.getDdate_creation());
        postalAddressDTO.setSforcage(postalAddress.getSforcage());
        postalAddressDTO.setSdescriptif_complementaire(postalAddress.getSdescriptif_complementaire());
        postalAddressDTO.setSindadr(postalAddress.getSindadr());
        postalAddressDTO.setIcod_err(postalAddress.getIcod_err());
        postalAddressDTO.setIcod_warning(postalAddress.getIcod_warning());
        postalAddressDTO.setIcle_role(postalAddress.getIcle_role());
        postalAddressDTO.setIkey_temp(postalAddress.getIkey_temp());
        postalAddressDTO.setDdate_fonctionnel(postalAddress.getDdate_fonctionnel());
        postalAddressDTO.setSsite_fonctionnel(postalAddress.getSsite_fonctionnel());
        postalAddressDTO.setSsignature_fonctionnel(postalAddress.getSsignature_fonctionnel());
        postalAddressDTO.setScod_err_simple(postalAddress.getScod_err_simple());
        postalAddressDTO.setScod_err_detaille(postalAddress.getScod_err_detaille());
        postalAddressDTO.setStype_invalidite(postalAddress.getStype_invalidite());
        postalAddressDTO.setSenvoi_postal(postalAddress.getSenvoi_postal());
        postalAddressDTO.setDenvoi_postal(postalAddress.getDenvoi_postal());
        postalAddressDTO.setScod_app_send(postalAddress.getScod_app_send());
    }
    
    /*PROTECTED REGION ID(_RpoWEDRWEeCGEoB0vWAi2A u m - Tr) ENABLED START*/
    /**
     * Ask both method : dto2BoLight & dto2BoLink
     * @param postalAddressDTO
     * @param postalAddress
     */
    public static void dto2Bo(PostalAddressDTO postalAddressDTO, PostalAddress postalAddress) {
    	dto2BoLight(postalAddressDTO, postalAddress);
    	dto2BoLink(postalAddressDTO, postalAddress);
    }


    

    public static List<PostalAddressDTO> bo2Dto(List<PostalAddress> postalAddressList) {
    	
    	if(postalAddressList==null || postalAddressList.isEmpty()) {
    		return null;
    	}
    	
    	List<PostalAddressDTO> postalAddressListDTO = new ArrayList<PostalAddressDTO>();
    	
    	for(PostalAddress postalAddress : postalAddressList) {
    		PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    		bo2Dto(postalAddress, postalAddressDTO);
    		postalAddressListDTO.add(postalAddressDTO);
    	}
    	
    	return postalAddressListDTO;
    }
    
public static List<PostalAddressDTO> bo2DtoLegalEntity(List<PostalAddress> postalAddressList) {
    	
    	if(postalAddressList==null || postalAddressList.isEmpty()) {
    		return null;
    	}
    	
    	List<PostalAddressDTO> postalAddressListDTO = new ArrayList<PostalAddressDTO>();
    	
    	for(PostalAddress postalAddress : postalAddressList) {
    		PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    		bo2DtoLegalEntity(postalAddress, postalAddressDTO);
    		postalAddressListDTO.add(postalAddressDTO);
    	}
    	
    	return postalAddressListDTO;
    }
    
    public static PostalAddressDTO bo2Dto(PostalAddress postalAddress) {
    	
    	if(postalAddress==null) {
    		return null;
    	}
    	
    	PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    	bo2Dto(postalAddress, postalAddressDTO);
    	return postalAddressDTO;
    }
    
    /**
     * Ask both method : bo2DtoLight & bo2DtoLink
	 * @param postalAddress
	 * @param postalAddressDTO
     */
    public static void bo2Dto(PostalAddress postalAddress, PostalAddressDTO postalAddressDTO) {
    	
    	if(postalAddress==null) {
    		return;
    	}
    	
    	bo2DtoLight(postalAddress, postalAddressDTO);
    	bo2DtoLink(postalAddress, postalAddressDTO);
    }
    
  public static void bo2DtoLegalEntity(PostalAddress postalAddress, PostalAddressDTO postalAddressDTO) {
    	
    	if(postalAddress==null) {
    		return;
    	}
    	
    	bo2DtoLight(postalAddress, postalAddressDTO);    	
    }

    
	/**
	 * @param postalAddress
	 * @param postalAddressDTO
	 */
	public static void bo2DtoLink(PostalAddress postalAddress, PostalAddressDTO postalAddressDTO) {
		if(postalAddress.getUsage_medium() != null) {
			try {
				postalAddressDTO.setUsage_mediumdto(Usage_mediumTransform.bo2Dto(postalAddress.getUsage_medium()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}

    /**
     * @param postalAddressDTO
     * @param postalAddress
     */
    public static void dto2BoLink(PostalAddressDTO postalAddressDTO, PostalAddress postalAddress) {
    	if(postalAddressDTO.getUsage_mediumdto() != null ) {
        	try {
        		postalAddress.setUsage_medium(Usage_mediumTransform.dto2BoSet(postalAddressDTO.getUsage_mediumdto()));
			} catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
        }
	}

    /**
     * @param listPostalAddress
     * @return listPostalAddressesDTO
     * @throws JrafDomainException
     */
    public static List<PostalAddressDTO> bo2Dto(Set<PostalAddress> listPostalAddress) throws JrafDomainException {
    	if(listPostalAddress != null) {
    		List<PostalAddressDTO> listPostalAddressDTO = new ArrayList<PostalAddressDTO>();
    		for(PostalAddress postalAddress : listPostalAddress) {
    	        PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    	        bo2Dto(postalAddress, postalAddressDTO);
    			listPostalAddressDTO.add(postalAddressDTO);
    		}
    		return listPostalAddressDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listPostalAddress
     * @return listPostalAddressesDTO
     * @throws JrafDomainException
     */
    public static List<PostalAddressDTO> bo2DtoLight(Set<PostalAddress> listPostalAddress) throws JrafDomainException {
    	if(listPostalAddress != null) {
    		List<PostalAddressDTO> listPostalAddressDTO = new ArrayList<PostalAddressDTO>();
    		for(PostalAddress postalAddress : listPostalAddress) {
    	        PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    	        bo2DtoLight(postalAddress, postalAddressDTO);
    			listPostalAddressDTO.add(postalAddressDTO);
    		}
    		return listPostalAddressDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listPostalAddress
     * @return
     * @throws JrafDomainException
     */
    public static List<PostalAddressDTO> bo2DtoValid(Set<PostalAddress> listPostalAddress) throws JrafDomainException {
    	if(listPostalAddress != null) {
    		List<PostalAddressDTO> listPostalAddressDTO = new ArrayList<PostalAddressDTO>();
    		for(PostalAddress postalAddress : listPostalAddress) {
    			if (postalAddress.getSstatut_medium().equalsIgnoreCase("V") || postalAddress.getSstatut_medium().equalsIgnoreCase("T")){
    				PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
        	        bo2Dto(postalAddress, postalAddressDTO);
        			listPostalAddressDTO.add(postalAddressDTO);
    			}
    		}
    		return listPostalAddressDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listPostalAddress
     * @return setPostalAddressesDTO
     * @throws JrafDomainException
     */
    public static Set<PostalAddressDTO> bo2DtoSet(Set<PostalAddress> listPostalAddress) throws JrafDomainException {
    	if(listPostalAddress != null) {
    		Set<PostalAddressDTO> setPostalAddressDTO = new LinkedHashSet<PostalAddressDTO>();
    		for(PostalAddress postalAddress : listPostalAddress) {
    	        PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
    	        bo2DtoLight(postalAddress, postalAddressDTO);
    	        setPostalAddressDTO.add(postalAddressDTO);
    		}
    		return setPostalAddressDTO;
    	} else {
    		return null;
    	}
    }
    /**
     * @param listPostalAddressDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<PostalAddress> dto2Bo(List<PostalAddressDTO> listPostalAddressDTO) throws JrafDomainException {
    	if(listPostalAddressDTO != null) {
    		Set<PostalAddress> listPostalAddress = new HashSet<PostalAddress>();
    		for(PostalAddressDTO postalAddressdto : listPostalAddressDTO) {
    	        PostalAddress postalAddress = new PostalAddress();
    	        dto2Bo(postalAddressdto, postalAddress);
    			listPostalAddress.add(postalAddress);
    		}
    		return listPostalAddress;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listPostalAddressDTO
     * @return
     * @throws JrafDomainException
     */
/*    
    public static Set<PostalAddress> dto2Bo(ProspectLocalizationDTO prospectLocalizationDTO) throws JrafDomainException {
    	if(prospectLocalizationDTO != null) {
    		Set<PostalAddress> listPostalAddress = new HashSet<PostalAddress>();
	        PostalAddress postalAddress = new PostalAddress();
	        dto2Bo(prospectLocalizationDTO, postalAddress);
			listPostalAddress.add(postalAddress);
    		return listPostalAddress;
    	} else {
    		return null;
    	}
    }
*/
    /**
     * @param listPostalAddressDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<PostalAddress> dto2BoSet(Set<PostalAddressDTO> listPostalAddressDTO) throws JrafDomainException {
    	if(listPostalAddressDTO != null) {
    		Set<PostalAddress> listPostalAddress = new HashSet<PostalAddress>();
    		for(PostalAddressDTO postalAddressdto : listPostalAddressDTO) {
    	        PostalAddress postalAddress = new PostalAddress();
    	        dto2Bo(postalAddressdto, postalAddress);
    			listPostalAddress.add(postalAddress);
    		}
    		return listPostalAddress;
    	} else {
    		return null;
    	}
    }
    
    public static PostalAddress dto2Bo(PostalAddressDTO dto) {
    	PostalAddress bo = null;
    	
    	if (dto != null) {
    		bo = new PostalAddress();
    		dto2Bo(dto, bo);
    	}
    	
    	return bo;
    }
    


    /*PROTECTED REGION END*/
}

