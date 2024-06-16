package com.airfrance.repind.service.adresse.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefProvinceRepository;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftRequestDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.refTable.RefTablePAYS;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.entity.refTable.RefTableREF_PROVINCE;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.reference.RefProvince;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.internal.unitservice.adresse.AdresseUS;
import com.airfrance.repind.service.internal.unitservice.adresse.DqeUS;
import com.airfrance.repind.service.internal.unitservice.adresse.PostalAddressUS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class AdresseDS {

	private static Log log  = LogFactory.getLog(AdresseDS.class);
	
    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    /** Reference sur le unit service AdresseUS **/
    @Autowired
    private AdresseUS adresseUS;

	/** unit service : PostalAddressUS **/
    @Autowired
    private PostalAddressUS postalAddressUS;
    
    /** reference sur le repository principal */
    @Autowired
    private PaysRepository paysRepository;
    
    /** references on associated Repository */
    @Autowired
    private VariablesRepository variablesRepository;
    
    @Autowired
	@Qualifier("variablesDS")
	private VariablesDS variablesDS;
    
    @Autowired
    private RefProvinceRepository refProvinceRepository;

    @Autowired
	private DqeUS dqeUS;
 
    @Transactional(readOnly=true)
    public Pays get(Pays bo) throws JrafDomainException {
        return paysRepository.findById(bo.getCodePays()).get();
    }
   
    /**
     * Getter
     * @return IAdresseUS
     */
    public AdresseUS getAdresseUS() {
        return adresseUS;
    }

    /**
     * Setter
     * @param adresseUS the IAdresseUS 
     */
    public void setAdresseUS(AdresseUS adresseUS) {
        this.adresseUS = adresseUS;
    }
    
    /**
     * Getter
     * @return IPostalAddressUS
     */
    public PostalAddressUS getPostalAddressUS() {
		return postalAddressUS;
	}

    /**
     * Setter
     * @param postalAddressUS the IPostalAddressUS
     */
	public void setPostalAddressUS(PostalAddressUS postalAddressUS) {
		this.postalAddressUS = postalAddressUS;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_zekw0HvfEeCAmbGwtfTi3Qgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
 
    /** 
     * checkNormalizedAdress
     * @param adrPost in NormalisationSoftRequestDTO
     * @param codePays in String
     * @return The checkNormalizedAdress as <code>NormalisationSoftResponseDTO</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public NormalisationSoftResponseDTO checkNormalizedAddress(NormalisationSoftRequestDTO adrPost, String codePays) throws JrafDomainException {
        /*PROTECTED REGION ID(_A1nywBa-EeK8rbfCW6etbQ) ENABLED START*/
    	NormalisationSoftResponseDTO response = new NormalisationSoftResponseDTO();
    	// convert the DTO to Entity used by PostalAddressUS
    	PostalAddress pa = new PostalAddress();
    	dtoToEntity(adrPost, pa);
    	
    	boolean missMandatory = false;


		// CHECKS
		//	 This logic for errors with no exceptions is slightly different from the one used in PostalAddressUS.checkMandatoryAndValidity()
	        if(adrPost.getStreet()==null || ("").equals(adrPost.getStreet())){
	        	response.setNumError("279");
	        	//response.setLibError(getErrorDS().getErrorDTO("279").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_279, "EN"));
	        	missMandatory=true;
	        }
	        
	        if(!missMandatory && (adrPost.getCity()==null || ("").equals(adrPost.getCity()) || adrPost.getCity().length()<2)){
	        	response.setNumError("278");
	        	//response.setLibError(getErrorDS().getErrorDTO("278").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_278, "EN"));
	        	missMandatory=true;
	        }
	        
	        if(!missMandatory && (adrPost.getCountry()==null || ("").equals(adrPost.getCountry()))){
	        	response.setNumError("192");
	        	//response.setLibError(getErrorDS().getErrorDTO("192").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_192, "EN"));
	        	missMandatory=true;
	        }
	        
	        
	        if(!missMandatory && adrPost.getState()!=null && !("").equalsIgnoreCase(adrPost.getState()) && !RefTableREF_PROVINCE.instance().estValide(adrPost.getState(), "")){
	        	response.setNumError("280");
	        	//response.setLibError(getErrorDS().getErrorDTO("131").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_280, "EN"));
	        	missMandatory=true;
	        }
	       
	        if(!missMandatory && (!RefTablePAYS.instance().estValide(codePays,""))){
	        	response.setNumError("131");
	        	//response.setLibError(getErrorDS().getErrorDTO("131").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_131, "EN"));
	        	missMandatory=true;
	        }
	        
	        if(!missMandatory && (adrPost.getZip()==null || ("").equals(adrPost.getZip()))){
	        	response.setNumError("281");
	        	//response.setLibError(getErrorDS().getErrorDTO("281").getLabelUK());
	        	response.setLibError(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_281, "EN"));
	        	missMandatory=true;
	        }
	        
	        // This transformation is present in PostalAddressUS.normalizeAddress()
	        if(!missMandatory) {//if nothing is missing :
	        	if(!(("AT").equals(adrPost.getCountry())) && !(("AU").equals(adrPost.getCountry())) && !(("BR").equals(adrPost.getCountry())) && !(("CA").equals(adrPost.getCountry())) 
	        	&& !(("CI").equals(adrPost.getCountry())) && !(("CM").equals(adrPost.getCountry())) && !(("IT").equals(adrPost.getCountry())) && !(("US").equals(adrPost.getCountry()))){
	        		if(adrPost.getState() != null && !(("").equals(adrPost.getState()))){
	        			transformStateCodeIntoLabelCode(adrPost);
	        		}
	        	}
//	        	response = getAdresseUS().checkSoft(adrPost);
				dqeUS.normalizeAddress(pa,false,false);
		    	entityToDto(pa, response);
			}
//		}
		return response;
        /*PROTECTED REGION END*/
    }
            
    private void entityToDto(PostalAddress pa, NormalisationSoftResponseDTO response) {
    	response.setAdrComplement(pa.getScomplement_adresse());
		response.setCityR(pa.getSville());
		response.setCountry(pa.getScode_pays());
		response.setLocality(pa.getSlocalite());
		if(pa.getScode_pays().equalsIgnoreCase("AR")
				|| pa.getScode_pays().equalsIgnoreCase("AU") || pa.getScode_pays().equalsIgnoreCase("BR")
				|| pa.getScode_pays().equalsIgnoreCase("CA") || pa.getScode_pays().equalsIgnoreCase("CN")
				|| pa.getScode_pays().equalsIgnoreCase("ES") || pa.getScode_pays().equalsIgnoreCase("US")
				|| pa.getScode_pays().equalsIgnoreCase("IT") || pa.getScode_pays().equalsIgnoreCase("KZ")
				|| pa.getScode_pays().equalsIgnoreCase("MX") || pa.getScode_pays().equalsIgnoreCase("UZ")
				|| pa.getScode_pays().equalsIgnoreCase("PG") || pa.getScode_pays().equalsIgnoreCase("DO")
				|| pa.getScode_pays().equalsIgnoreCase("CH") || pa.getScode_pays().equalsIgnoreCase("VE"))
			response.setState(pa.getScode_province().length()>2 ? "" : pa.getScode_province());
		else
			response.setState("");
		response.setNumAndStreet(pa.getSno_et_rue());
		response.setZipCode(pa.getScode_postal());

		int i=0;
        
		String[] formattedLines = new String[6];
        // initialize the Array
        
		for (int j=0; j<6; j++){
        	formattedLines[j] = ""; 
        }
    	
		if (pa.getScomplement_adresse()!=null && !pa.getScomplement_adresse().equals("")){
    		formattedLines[i]=pa.getScomplement_adresse();
    		i++;
    	}
    	if (pa.getSno_et_rue()!=null && !pa.getSno_et_rue().equals("")){
    		formattedLines[i]=pa.getSno_et_rue();
    		i++;
    	}
    	if ((pa.getScode_postal()!=null && !pa.getScode_postal().equals("")) || (pa.getSville()!=null && !pa.getSville().equals(""))){
    		// we need to build a line with postal code + city, or at least one of them
    		if ((pa.getScode_postal()!=null && !pa.getScode_postal().equals("")) && (pa.getSville()!=null && !pa.getSville().equals(""))){
    			// both are present
    			formattedLines[i]=pa.getScode_postal() + " " + pa.getSville();
        		i++;
    		} else if (pa.getScode_postal() == null || pa.getScode_postal().equals("")){
    			// only city is present
        			formattedLines[i]=pa.getSville();
        			i++;
    		} else {
    			// only postalCode is present
    			formattedLines[i]=pa.getScode_postal();
    			i++;
    		}
    	}
    	if (pa.getSlocalite()!=null && !pa.getSlocalite().equals("")){
    		formattedLines[i]=pa.getSlocalite();
    		i++;
    	}
    	if (pa.getScode_province()!=null && !pa.getScode_province().equals("")){
    		formattedLines[i]=pa.getScode_province();
    		i++;
    	}
    	if (pa.getScode_pays()!=null && !pa.getScode_pays().equals("")){
    		formattedLines[i]=pa.getScode_pays();
    	}
		response.setMailingAdrLine1(formattedLines[0]);
		response.setMailingAdrLine2(formattedLines[1]);
		response.setMailingAdrLine3(formattedLines[2]);
		response.setMailingAdrLine4(formattedLines[3]);
		response.setMailingAdrLine5(formattedLines[4]);
		response.setMailingAdrLine6(formattedLines[5]);
//		response.setMailingAdrLine7(pa.);
//		response.setMailingAdrLine8(pa.);
//		response.setMailingAdrLine9(pa.);
		// already set before calling this method
//		response.setLibError(pa.);
//		if (pa.getScod_err_simple() == null)
		response.setNumError(null);
//		else 
//			response.setNumError(!pa.getScod_err_simple().equalsIgnoreCase("0") ? pa.getScod_err_simple() : null);
		response.setReturnCode1(pa.getScod_err_simple() == null? null: new Integer (pa.getScod_err_simple()));
		response.setReturnCode2(pa.getScod_err_detaille());
		response.setRowId(pa.getSain());
		response.setWsErr(0);
	}

	private void dtoToEntity(NormalisationSoftRequestDTO adrPost,
			PostalAddress pa) {
    	pa.setSville(adrPost.getCity() == null? "" : adrPost.getCity());
    	pa.setScomplement_adresse(adrPost.getComp() == null? "" : adrPost.getComp());
    	pa.setScode_pays(adrPost.getCountry() == null? "" : adrPost.getCountry());
    	pa.setSlocalite(adrPost.getLocal() == null? "" : adrPost.getLocal());
    	pa.setScode_province(adrPost.getState() == null? "" : adrPost.getState());
    	pa.setSno_et_rue(adrPost.getStreet() == null? "" : adrPost.getStreet());
    	pa.setScode_postal(adrPost.getZip() == null? "" : adrPost.getZip());
    	pa.setSain(adrPost.getRowid());
	}

	/** 
     * suppressionAdressePostale
     * @param adresse in PostalAddress
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional
    public void suppressionAdressePostale(PostalAddress adresse) throws JrafDomainException {
        /*PROTECTED REGION ID(_VXO_wIHzEeCtut40RvtPWA) ENABLED START*/
        // TODO method suppressionAdressePostale(PostalAddress adresse) to implement
        throw new UnsupportedOperationException();
        /*PROTECTED REGION END*/
    }
    

    /** 
     * isPaysNormalisable
     * @param codePays in String
     * @return The isPaysNormalisable as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public Boolean isPaysNormalisable(String codePays) throws JrafDomainException {
        /*PROTECTED REGION ID(_cNTugJVjEeKM3LLgWXwnmg) ENABLED START*/
        // TODO method isPaysNormalisable(String codePays) to implement
        return getAdresseUS().isPaysNormalisable(codePays);
        /*PROTECTED REGION END*/
    }
    


    /*PROTECTED REGION ID(_zekw0HvfEeCAmbGwtfTi3Q u m) ENABLED START*/
    @Transactional(readOnly=true)
	// REPIND-260 : SONAR - Passer la fonction en Public au lieu de Private car Transactional
    public void transformStateCodeIntoLabelCode(NormalisationSoftRequestDTO adrPost) throws JrafDomainException {
    	RefProvince refProvince = new RefProvince();
    	refProvince.setCodePays(adrPost.getCountry());
    	refProvince.setCode(adrPost.getState());
    	
    	List<RefProvince> lrefProvince = refProvinceRepository.findAll(Example.of(refProvince));
    	
    	if(lrefProvince.size() > 1){
    		throw new JrafDomainException("WARNING: found more than 1 province label from " + refProvince.getCodePays()+ " and " + refProvince.getCode());
    	}    	
    	
    	else if(lrefProvince.size() == 0){
    		throw new JrafDomainException("WARNING: No province label found from " + refProvince.getCodePays()+ " and " + refProvince.getCode());
    	}
    	
    	else{
    	adrPost.setState(lrefProvince.get(0).getLibelleEn());
    	}
    }
    /*PROTECTED REGION END*/


	public boolean isValidDistrictCode(String districtCode, String countryCode) throws JrafDomainException {
		if (districtCode != null && countryCode != null) {
			List<RefProvince> result = refProvinceRepository.isValidProvinceCode(districtCode.toUpperCase(), countryCode.toUpperCase());
			
			if (UList.isNullOrEmpty(result)) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
}
