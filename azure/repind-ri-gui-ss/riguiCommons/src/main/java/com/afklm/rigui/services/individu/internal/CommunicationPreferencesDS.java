package com.afklm.rigui.services.individu.internal;

import com.afklm.rigui.exception.MaximumSubscriptionsException;
import com.afklm.rigui.exception.compref.CommunicationPreferencesNotFoundException;
import com.afklm.rigui.exception.compref.InvalidPermissionIdException;
import com.afklm.rigui.exception.compref.MarketLanguageException;
import com.afklm.rigui.exception.compref.MarketLanguageNotFoundException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.AddressRoleCodeEnum;
import com.afklm.rigui.enums.CommunicationPreferencesConstantValues;
import com.afklm.rigui.util.UList;
import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.individu.CommunicationPreferencesRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.individu.MarketLanguageRepository;
import com.afklm.rigui.dao.profil.ProfilsRepository;
import com.afklm.rigui.dao.reference.RefComPrefCountryMarketRepository;
import com.afklm.rigui.dao.reference.RefComPrefRepository;
import com.afklm.rigui.dto.compref.InformationDTO;
import com.afklm.rigui.dto.compref.InformationsDTO;
import com.afklm.rigui.dto.compref.PermissionDTO;
import com.afklm.rigui.dto.compref.PermissionsDTO;
import com.afklm.rigui.dto.individu.*;
import com.afklm.rigui.dto.reference.RefComPrefDgtDTO;
import com.afklm.rigui.dto.reference.RefComPrefGroupDTO;
import com.afklm.rigui.dto.reference.RefPermissionsQuestionDTO;
import com.afklm.rigui.dto.reference.RefProductDTO;
import com.afklm.rigui.dto.ws.RequestorDTO;
import com.afklm.rigui.entity.individu.CommunicationPreferences;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.individu.MarketLanguage;
import com.afklm.rigui.entity.profil.Profils;
import com.afklm.rigui.entity.reference.RefComPref;
import com.afklm.rigui.services.reference.internal.RefPermissionsDS;
import com.afklm.rigui.services.reference.internal.RefPermissionsQuestionDS;
import com.afklm.rigui.services.reference.internal.RefProductComPrefGroupDS;
import com.afklm.rigui.services.reference.internal.RefProductDS;
import com.afklm.rigui.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class CommunicationPreferencesDS {

    /**
     * Permission information
     */
    private static final String COMPREF_CREATED_UPDATED_CODE = "0";
    private static final String COMPREF_NOT_CREATED_UPDATED_CODE = "1";
    private static final String COMPREF_CREATED_DETAILS = "Communication preference created";
    private static final String COMPREF_UPDATED_DETAILS = "Communication preference updated";
    private static final int MAX_MARKET_FOR_SALES = 5;
    private static final String EMPTY_STRING = "";

    /**
     * logger
     */
    private static final Log log = LogFactory.getLog(CommunicationPreferencesDS.class);

    /**
     * reference sur le dao principal
     */
    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    /*PROTECTED REGION ID(_knuKoNNfEeKqudTad_7FKg u var) ENABLED START*/

    @Autowired
    private MarketLanguageRepository marketLanguageRepository;

    @Autowired
    protected PostalAddressRepository postalAddressRepository;

    @Autowired
    protected RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;

    @Autowired
    private RefComPrefRepository refComPrefRepository;

    @Autowired
    private ProfilsRepository profilsRepository;

    @Autowired
    private RefPermissionsDS refPermissionsDS;

    @Autowired
    private RefPermissionsQuestionDS refPermissionsQuestionDS;

    @Autowired
    private IndividuDS individuDS;

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private RefProductDS refProductDS;

    @Autowired
    private RefProductComPrefGroupDS refProductComPrefGroupDS;

    /*PROTECTED REGION END*/

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(CommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(_knuKoNNfEeKqudTad_7FKg DS-CM create) ENABLED START*/
        CommunicationPreferences communicationPreferences = null;

        // transformation light dto -> bo
        communicationPreferences = CommunicationPreferencesTransform.dto2BoLight(communicationPreferencesDTO);

        // creation en base
        // Appel create de l'Abstract
        communicationPreferencesRepository.saveAndFlush(communicationPreferences);

        // Version update and Id update if needed
        CommunicationPreferencesTransform.bo2DtoLight(communicationPreferences, communicationPreferencesDTO);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(CommunicationPreferencesDTO dto) throws JrafDomainException {

        /*PROTECTED REGION ID(_knuKoNNfEeKqudTad_7FKg DS-CM remove) ENABLED START*/
        CommunicationPreferences communicationPreferences = null;

        // transformation light dto -> bo
        communicationPreferences = CommunicationPreferencesTransform.dto2BoLight(dto);

        // suppression en base
        communicationPreferencesRepository.delete(communicationPreferences);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(Integer id) throws JrafDomainException {
        communicationPreferencesRepository.deleteById(id);
    }


    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(CommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(_knuKoNNfEeKqudTad_7FKg DS-CM update) ENABLED START*/
        CommunicationPreferences communicationPreferences = null;

        // chargement du bo
        communicationPreferences = communicationPreferencesRepository.findById(communicationPreferencesDTO.getComPrefId()).get();


        // transformation light dto -> bo
        CommunicationPreferencesTransform.dto2BoLight(communicationPreferencesDTO, communicationPreferences);

        /*PROTECTED REGION END*/

    }

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateListWithMarketLanguage(List<CommunicationPreferencesDTO> comPrefList) throws JrafDomainException {

		Set<CommunicationPreferences> comPrefBoList = CommunicationPreferencesTransform.dto2Bo(comPrefList);
		for (CommunicationPreferences cp : comPrefBoList) {
			communicationPreferencesRepository.saveAndFlush(cp);
		}
	}

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<CommunicationPreferencesDTO> findAll() throws JrafDomainException {
        /*PROTECTED REGION ID(_knuKoNNfEeKqudTad_7FKg DS-CM findAll) ENABLED START*/
        List boFounds = null;
        List<CommunicationPreferencesDTO> dtoFounds = null;
        CommunicationPreferencesDTO dto = null;
        CommunicationPreferences communicationPreferences = null;

        // execution du find
        boFounds = communicationPreferencesRepository.findAll();
        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<CommunicationPreferencesDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                communicationPreferences = (CommunicationPreferences) i.next();
                dto = CommunicationPreferencesTransform.bo2DtoLight(communicationPreferences);
                dtoFounds.add(dto);
            }
        }
        return dtoFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Integer count() throws JrafDomainException {
        return (int) communicationPreferencesRepository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public CommunicationPreferencesDTO get(CommunicationPreferencesDTO dto) throws JrafDomainException {
        CommunicationPreferencesDTO communicationPreferencesDTO = null;
        // get en base
        Optional<CommunicationPreferences> communicationPreferences = communicationPreferencesRepository.findById(dto.getComPrefId());


        // transformation light bo -> dto
        if (communicationPreferences.isPresent()) {
            communicationPreferencesDTO = CommunicationPreferencesTransform.bo2DtoLight(communicationPreferences.get());
        }
        return communicationPreferencesDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public CommunicationPreferencesDTO get(Integer id) throws JrafDomainException {
        CommunicationPreferencesDTO communicationPreferencesDTO = null;
        // get en base
        Optional<CommunicationPreferences> communicationPreferences = communicationPreferencesRepository.findById(id);

        // transformation light bo -> dto
        if (communicationPreferences.isPresent()) {
            communicationPreferencesDTO = CommunicationPreferencesTransform.bo2DtoLight(communicationPreferences.get());
        }
        return communicationPreferencesDTO;
    }

    public CommunicationPreferencesRepository getCommunicationPreferencesRepository() {
        return communicationPreferencesRepository;
    }

    public void setCommunicationPreferencesRepository(
            CommunicationPreferencesRepository communicationPreferencesRepository) {
        this.communicationPreferencesRepository = communicationPreferencesRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public CommunicationPreferencesDTO findComPrefId(String gin, String domain, String comGroupType, String comType) throws JrafDomainException {

        CommunicationPreferencesDTO communicationPreferencesDTO = null;
        CommunicationPreferences comPref = communicationPreferencesRepository.findComPrefId(gin, domain, comGroupType, comType);

        if (comPref != null) {
            communicationPreferencesDTO = CommunicationPreferencesTransform.bo2Dto2(comPref);
        }

        return communicationPreferencesDTO;

    }

    private CommunicationPreferencesDTO findComPrefId(IndividuDTO individuDTO, String domain, String comGroupType, String comType) throws JrafDomainException {

        if (!UList.isNullOrEmpty(individuDTO.getCommunicationpreferencesdto())) {
            for (CommunicationPreferencesDTO comPrefDTO : individuDTO.getCommunicationpreferencesdto()) {
                if (domain.equalsIgnoreCase(comPrefDTO.getDomain()) && comGroupType.equalsIgnoreCase(comPrefDTO.getComGroupType()) && comType.equalsIgnoreCase(comPrefDTO.getComType())) {
                    return comPrefDTO;
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public MarketLanguageDTO findMarketId(int comPrefId, String market, String language) throws JrafDomainException {

        MarketLanguageDTO marketLanguageDTO = null;
        MarketLanguage marketLanguage = marketLanguageRepository.findMarketId(comPrefId, market, language);

        if (marketLanguage != null) {
            marketLanguageDTO = MarketLanguageTransform.bo2Dto(marketLanguage);
        }

        return marketLanguageDTO;

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public int checkOptinCoherence(CommunicationPreferencesDTO comPrefDTO) throws JrafDomainException {

        int result = 0;

        CommunicationPreferences communicationPreferences = CommunicationPreferencesTransform.dto2Bo2(comPrefDTO);
        result = communicationPreferencesRepository.checkOptinCoherence(communicationPreferences.getComPrefId()).intValue();

        return result;
    }
    /*PROTECTED REGION END*/

    @Transactional(readOnly = true)
    public String getGinWithComPrefSubscribeY() {
        return communicationPreferencesRepository.getGinWithComPrefSubscribeY();
    }

    public int getNumberOptinComPrefByGin(String gin) {
        return communicationPreferencesRepository.getNumberOptoutOrOptinComPrefByGinAndSubscribe(gin, "Y").intValue();
    }

    public int getNumberOptoutComPrefByGin(String gin) {
        return communicationPreferencesRepository.getNumberOptoutOrOptinComPrefByGinAndSubscribe(gin, "N").intValue();
    }


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public List<InformationsDTO> createComPrefBasedOnAPermission(PermissionsDTO permissionsDTO) throws JrafDomainException {
        String gin = permissionsDTO.getGin();
        RequestorDTO requestorDTO = permissionsDTO.getRequestorDTO();
        List<InformationsDTO> result = new ArrayList<InformationsDTO>();
        String errorFB = null;

        //Calcul the Market for this Individu
        String marketFB = null;
        try {
            marketFB = getMarketForComPref(gin);
        } catch (MarketLanguageException e) {
            errorFB = e.getMessage();
        }

        //Get the Profil in order to retrieve the communication language
        Optional<Profils> profils = profilsRepository.findById(gin);

        //Get the FB_ESS ComPref used in case of MarketWorld (*) to define the language
        RefComPref refComPrefFB_ESS = null;
        try {
            refComPrefFB_ESS = getRefComPrefFBESS(CommunicationPreferencesConstantValues.DOMAIN_F, CommunicationPreferencesConstantValues.NEWSLETTER, CommunicationPreferencesConstantValues.FB_ESS, marketFB);
        } catch (MarketLanguageException e) {
            if (StringUtils.isEmpty(errorFB)) errorFB = e.getMessage();
        }

        //Iterate over every Permission
        for (PermissionDTO permissionDTO : permissionsDTO.getPermission()) {
            int permissionId = Integer.parseInt(permissionDTO.getPermissionId());
            String answer = (permissionDTO.getAnswer() == true) ? "Y" : "N";

            //REPIND-1238 : Add the PermissionId on each Signature Creation for all Comprefs
            String signature = modifySignatureForPermission(requestorDTO.getSignature(), permissionDTO.getPermissionId());

            InformationsDTO informations = new InformationsDTO();
            informations.setInformationsId(permissionId);
            informations.setInformation(new ArrayList<InformationDTO>());

            //Check if the Permission is valid
            RefPermissionsQuestionDTO refPermissionsQuestionDTO = refPermissionsQuestionDS.getById(permissionId);
            if (refPermissionsQuestionDTO == null) {
                log.error("### CommunicationPreferencesDS : Permission ID Invalid");
                throw new InvalidPermissionIdException(permissionDTO.getPermissionId());
            }

            //Get all RefComPrefDGT associated to a Permission
            List<RefComPrefDgtDTO> listRefComPrefDGT = refPermissionsDS.getAllComPrefDGTByPermissionsQuestionId(permissionId);

            //For each RefComPrefDgt linked to the Permission
            for (RefComPrefDgtDTO refComPrefDgtDTO : listRefComPrefDGT) {
                String error = errorFB;

                //No error for Domain other than F or S
                if (!refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("F") && !refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("S")) {
                    error = null;
                }

                //Get RefComPref
                RefComPref refComPref = null;
                try {
                    //If Domain = S and Market is provided, we use it to find a refcompref and get the Media (Market Language will be forced without any controls on them (Sales Rules)
                    if ("S".equalsIgnoreCase(refComPrefDgtDTO.getDomain().getCodeDomain()) && StringUtils.isNotEmpty(permissionDTO.getMarket())) {
                        refComPref = getRefComPrefByMarket(refComPrefDgtDTO.getDomain().getCodeDomain(), refComPrefDgtDTO.getGroupType().getCodeGType(), refComPrefDgtDTO.getType().getCodeType(), permissionDTO.getMarket());
                        //Else we use the Market from FlyingBlue Algo
                    } else {
                        refComPref = getRefComPrefByMarket(refComPrefDgtDTO.getDomain().getCodeDomain(), refComPrefDgtDTO.getGroupType().getCodeGType(), refComPrefDgtDTO.getType().getCodeType(), marketFB);
                    }
                } catch (MarketLanguageException e) {
                    //If we don't have the Market Language, we use the Market Languag FB, error must no be propagated in order to create the compref later
                    if (!"S".equalsIgnoreCase(refComPrefDgtDTO.getDomain().getCodeDomain())) {
                        if (StringUtils.isEmpty(error)) error = e.getMessage();
                    }
                }

                String media = null;
                if (refComPref != null && refComPref.getMedia() != null) {
                    media = refComPref.getMedia().getCodeMedia();
                } else {
                    media = "E"; //Default Media
                }

                //Get Language for FB Domain
                String languageFB = getLanguageForComPref(refComPref, refComPrefFB_ESS, profils.orElse(null));

                CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
                error = provideComPrefForPermissionAndGroup(comPrefToProcess, refComPrefDgtDTO, requestorDTO.getChannel(), media, answer, marketFB, languageFB, permissionDTO.getMarket(), permissionDTO.getLanguage(), error);

                //If an error is present, we don't create the compref
                if (StringUtils.isNotEmpty(error)) {
                    InformationDTO informationDTO = new InformationDTO();
                    informationDTO.setCode(COMPREF_NOT_CREATED_UPDATED_CODE);
                    informationDTO.setDetails(error);
                    informationDTO.setName(getComPrefName(refComPrefDgtDTO));
                    informations.getInformation().add(informationDTO);
                } else {
                    Map<InformationDTO, CommunicationPreferencesDTO> mapInformationCompref = createUpdateComPrefFromDGT(gin, comPrefToProcess, requestorDTO.getSite(), signature);
                    if (!mapInformationCompref.isEmpty()) {
                        for (InformationDTO informationDTO : mapInformationCompref.keySet()) {
                            informations.getInformation().add(informationDTO);
                        }
                    }
                }
            }

            result.add(informations);

        }

        return result;
    }


    private String provideComPrefForPermissionAndGroup(CommunicationPreferencesDTO comPrefToProcess, RefComPrefDgtDTO refComPrefDgtDTO, String channel, String media, String answer, String marketFB, String languageFB, String market, String language, String error) throws JrafDomainException {
        String result = null;

        comPrefToProcess.setChannel(channel);
        comPrefToProcess.setComGroupType(refComPrefDgtDTO.getGroupType().getCodeGType());
        comPrefToProcess.setComType(refComPrefDgtDTO.getType().getCodeType());
        comPrefToProcess.setDomain(refComPrefDgtDTO.getDomain().getCodeDomain());
        comPrefToProcess.setDateOptin(new Date());
        comPrefToProcess.setMedia1(media);
        comPrefToProcess.setSubscribe(answer);

        if (comPrefToProcess.getDomain().equalsIgnoreCase(CommunicationPreferencesConstantValues.DOMAIN_F) || comPrefToProcess.getDomain().equalsIgnoreCase(CommunicationPreferencesConstantValues.DOMAIN_S)) {
            comPrefToProcess.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
            MarketLanguageDTO ml = new MarketLanguageDTO();
            ml.setDateOfConsent(new Date());
            ml.setMedia1(media);
            ml.setOptIn(answer);

            if (comPrefToProcess.getDomain().equalsIgnoreCase(CommunicationPreferencesConstantValues.DOMAIN_F)) {
                if (StringUtils.isNotEmpty(error)) {
                    result = error;
                }

                ml.setMarket(marketFB);
                ml.setLanguage(languageFB);
            }

            if (comPrefToProcess.getDomain().equalsIgnoreCase(CommunicationPreferencesConstantValues.DOMAIN_S)) {
                //If Market or Language is missing, error raised
                String notValidSalesComPref = checkMarketLanguageForSalesComPref(market, language, comPrefToProcess);
                if (StringUtils.isNotEmpty(notValidSalesComPref)) {
                    result = notValidSalesComPref;
                }

                //If Market and Language are provided, we use them without any control
                if (StringUtils.isNotEmpty(market) && StringUtils.isNotEmpty(language)) {
                    ml.setMarket(market);
                    ml.setLanguage(language);
                    result = null;
                }

                //If no Market Language are provided, we use Market Language From COR Algo
                if (StringUtils.isEmpty(market) && StringUtils.isEmpty(language)) {
                    ml.setMarket(marketFB);
                    ml.setLanguage(languageFB);
                    if (StringUtils.isNotEmpty(error)) {
                        result = error;
                    }
                }
            }

            comPrefToProcess.getMarketLanguageDTO().add(ml);
        }

        return result;
    }


    /**
     * Create Communications Preferences based on a RefComPrefDGT
     * Optin will be used to Optin or Optout the ComPref. Market Language are optional, only used for Sales domain.
     * <p>
     * If the ComPref is related to Privacy (P) domain, we just create the ComPref without any Market Language.
     * If the ComPref is related to FlyingBlue (F) domain, we ignore the Market Language (even if they are filled in input)
     * - We deduce the Market from the address of the individual:
     * -> We get the Country of Residence from the Address Postal Valid with ISI Mailing
     * -> Else we take the Country of Residence from the Address Postal with ISI Mailing
     * -> Else we take the Country of Residence from the Address Postal Valid
     * (For each case, if there are several addresses, we took the most recent ordered by Modification Date)
     * -> Else we take the Market associated to the Country of Residence found
     * - We deduce the Language for the Communication Preference:
     * -> We look for the ComPref in the RefComPref with the Market calculated
     * -> If found, we compare if one of the languages associated to the RefComPref is matching with the Spoken Language (filled in the Profil of the individu)
     * -> If one language is matching, we keep the Spoken Language
     * -> Else, we keep the first language of the RefComPref
     * -> If there is no RefComPref associated to the Market calculated, we look for the RefComPref with Market World (*)
     * -> Then we do the same algo as just before but we use the FB_ESS ComPref (associated to the Market found)
     * - Once Market and Language are calculated, we create the Communication Preference with a Market Language
     * If the Compref is related to Sales (S) domain
     * - If the Market Language is filled in input, we just create the Communication Preference with the Market Language
     * - Else Market Language are not filled, we do the same algo as for FlyingBlue Communications Preferences
     */

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public Map<InformationDTO, CommunicationPreferencesDTO> createUpdateComPrefFromDGT(String gin, CommunicationPreferencesDTO comPrefToProcess, String site, String signature) throws JrafDomainException {
        Map<InformationDTO, CommunicationPreferencesDTO> result = new HashMap<InformationDTO, CommunicationPreferencesDTO>();

        //Get the Individu
        IndividuDTO individuDTO = individuDS.getByGin(gin);

        //To avoid nullPointerException
        if (individuDTO.getCommunicationpreferencesdto() == null) {
            individuDTO.setCommunicationpreferencesdto(new ArrayList<CommunicationPreferencesDTO>());
        }

        //Set Gin to ComPref to process
        comPrefToProcess.setGin(gin);

        //Get existing ComPref if existing
        CommunicationPreferencesDTO comPrefExisting = findComPrefId(individuDTO, comPrefToProcess.getDomain(), comPrefToProcess.getComGroupType(), comPrefToProcess.getComType());

        applyOptinBusinessRule(comPrefToProcess, comPrefExisting);

        //Switch following the Domain of the Communication Preference
        switch (comPrefToProcess.getDomain()) {
            case CommunicationPreferencesConstantValues.DOMAIN_F:
                result.put(manageComPref_F(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_S:
                result.put(manageComPref_S(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_P:
                result.put(manageComPref_P(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_U:
                result.put(manageComPref_U(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            default:
                result.put(manageComPref_Default(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
        }

        //Check Global Optin
        validateGlobalOptin(individuDTO.getCommunicationpreferencesdto());

        //Update the individu in order to also update the communications preferences
        individuDS.updateComPrefOfIndividual(individuDTO);

        return result;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public Map<InformationDTO, CommunicationPreferencesDTO> createUpdateComPrefFromDGTSharepoint(String gin, CommunicationPreferencesDTO comPrefToProcess, String site, String signature) throws JrafDomainException {
        Map<InformationDTO, CommunicationPreferencesDTO> result = new HashMap<InformationDTO, CommunicationPreferencesDTO>();

        //Get the Individu
        IndividuDTO individuDTO = individuDS.getByGin(gin);

        //To avoid nullPointerException
        if (individuDTO.getCommunicationpreferencesdto() == null) {
            individuDTO.setCommunicationpreferencesdto(new ArrayList<CommunicationPreferencesDTO>());
        }

        //Set Gin to ComPref to process
        comPrefToProcess.setGin(gin);

        //Get existing ComPref if existing
        CommunicationPreferencesDTO comPrefExisting = findComPrefId(individuDTO, comPrefToProcess.getDomain(), comPrefToProcess.getComGroupType(), comPrefToProcess.getComType());

        applyOptinBusinessRule(comPrefToProcess, comPrefExisting);

        //Switch following the Domain of the Communication Preference
        switch (comPrefToProcess.getDomain()) {
            case CommunicationPreferencesConstantValues.DOMAIN_F:
                result.put(manageComPref_F(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_S:
                result.put(manageComPref_S_Sharepoint(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_P:
                result.put(manageComPref_P(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            case CommunicationPreferencesConstantValues.DOMAIN_U:
                result.put(manageComPref_U(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
                break;
            default:
                result.put(manageComPref_Default(individuDTO, comPrefToProcess, comPrefExisting, site, signature), comPrefToProcess);
        }

        //Check Global Optin
        validateGlobalOptin(individuDTO.getCommunicationpreferencesdto());

        //Update the individu in order to also update the communications preferences
        individuDS.updateComPrefOfIndividual(individuDTO);

        return result;
    }


    /**
     * Apply optin business rule to compref or market language
     * change made for REPIND-1930
     *
     * @param comPrefToProcess compref to work on
     * @param comPrefExisting  compref from db
     */
    private void applyOptinBusinessRule(CommunicationPreferencesDTO comPrefToProcess, CommunicationPreferencesDTO comPrefExisting) {
        if (comPrefExisting != null) {
            if ("P".equals(comPrefToProcess.getDomain())) {
                /** put sysdate to dateOptin if dateOptin = null && compref subscribe change */
                if (comPrefToProcess.getDateOptin() == null) {
                    if (!comPrefExisting.getSubscribe().equals(comPrefToProcess.getSubscribe())) {
                        comPrefToProcess.setDateOptin(new Date());
                    }
                }
            } else {
                comPrefToProcess.getMarketLanguageDTO().stream().forEach(ml -> {
                    /** put sysdate to getDateOfConsent if getDateOfConsent = null && ml subscribe change */
                    if (ml.getDateOfConsent() == null) {
                        Optional<MarketLanguageDTO> mlDB = comPrefExisting.getMarketLanguageDTO().stream().filter(marketLanguage -> marketLanguage.getMarket().equals(ml.getMarket()) && marketLanguage.getLanguage().equals(ml.getLanguage())).findFirst();
                        if (mlDB.isPresent()) {
                            MarketLanguageDTO marketLanguage = mlDB.get();
                            if (!marketLanguage.getOptIn().equals(ml.getOptIn())) {
                                ml.setDateOfConsent(new Date());
                            }
                        }
                    }
                });
            }
        }
    }

    private void validateGlobalOptin(List<CommunicationPreferencesDTO> communicationPreferencesDTOs) {
        for (CommunicationPreferencesDTO communicationPreferencesDTO : communicationPreferencesDTOs) {
            validateGlobalOptin(communicationPreferencesDTO);
        }
    }

    private void validateGlobalOptin(CommunicationPreferencesDTO communicationPreferencesDTO) {
        String optinML = "N";

        if (!UList.isNullOrEmpty(communicationPreferencesDTO.getMarketLanguageDTO())) {
            for (MarketLanguageDTO ml : communicationPreferencesDTO.getMarketLanguageDTO()) {
                if ("Y".equalsIgnoreCase(ml.getOptIn())) {
                    optinML = "Y";
                }
            }
            communicationPreferencesDTO.setSubscribe(optinML);
        }
    }

    /**
     * Manage a Communication Preference with Domain S
     * FOLLOWING IS OLD ALGO
     * Case	IN_MARKET	IN_LANGUAGE	Domain	GroupType	Market/Language exist in CATI ?	Apply COR Algorithm?	Create compref? 	warning
     * C01	Not null	Not null	S		N or A		Yes								-						Yes
     * C02	Not null	Not null	S		N or A		No								-						No					Yes
     * C03	Not null	Not null	S		Other		-								-						Yes
     * C04	null		null		S		N or A		-								Yes						Yes
     * C05	null		null		S		Other		-								-						No					Yes
     * C06	Not null	null		S		N or A		-								-						No					Yes
     * C07	Not null	null		S		Other		-								-						No					Yes
     * C08	null		Not null	S		N or A		-								-						No					Yes
     * C09	null		Not null	S		Other		-								-						No					Yes
     *
     * @param individuDTO
     * @param communicationPreferencesDTOProcess
     * @param communicationPreferencesDTOExisting
     * @param site
     * @param signature
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private InformationDTO manageComPref_S(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            for (MarketLanguageDTO mlToKeep : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                boolean isUpdated = false;
                for (MarketLanguageDTO mlExisting : communicationPreferencesDTOExisting.getMarketLanguageDTO()) {
                    if (mlToKeep.getMarket().equalsIgnoreCase(mlExisting.getMarket())) {
                        // Creation signature
                        if (mlToKeep.getCreationSignature() != null && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getCreationSignature())) {
                            mlExisting.setCreationSignature(mlToKeep.getCreationSignature());
                        }
                        if (mlToKeep.getCreationSite() != null && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getCreationSite())) {
                            mlExisting.setCreationSite(mlToKeep.getCreationSite());
                        }
                        // Modification signature
                        mlExisting.setModificationDate(new Date());
                        mlExisting.setModificationSignature(mlToKeep.getModificationSignature() != null
                                && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getModificationSignature()) ? mlToKeep.getModificationSignature() : signature);
                        mlExisting.setModificationSite(mlToKeep.getModificationSite() != null
                                && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getModificationSite()) ? mlToKeep.getModificationSite() : site);
                        if (StringUtils.isNotEmpty(mlToKeep.getLanguage())) {
                            mlExisting.setLanguage(mlToKeep.getLanguage());
                        }
                        if (StringUtils.isNotEmpty(mlToKeep.getOptIn())) {
                            mlExisting.setOptIn(mlToKeep.getOptIn());
                        }
                        if (StringUtils.isNotEmpty(mlToKeep.getMedia1())) {
                            mlExisting.setMedia1(mlToKeep.getMedia1());
                        }
                        if (mlToKeep.getDateOfConsent() != null) {
                            mlExisting.setDateOfConsent(mlToKeep.getDateOfConsent());
                        }
                        isUpdated = true;
                    }
                }
                if (!isUpdated) {
                    if (mlToKeep.getCreationDate() == null) {
                        mlToKeep.setCreationDate(new Date());
                    }
                    if (mlToKeep.getCreationSignature() == null || EMPTY_STRING.equals(mlToKeep.getCreationSignature())) {
                        mlToKeep.setCreationSignature(signature);
                    }
                    if (mlToKeep.getCreationSite() == null || EMPTY_STRING.equals(mlToKeep.getCreationSite())) {
                        mlToKeep.setCreationSite(site);
                    }
                    mlToKeep.setModificationDate(new Date());
                    if (mlToKeep.getModificationSignature() == null || EMPTY_STRING.equals(mlToKeep.getModificationSignature())) {
                        mlToKeep.setModificationSignature(signature);
                    }
                    mlToKeep.setModificationSite(site);
                    if (mlToKeep.getDateOfConsent() == null) {
                        mlToKeep.setDateOfConsent(new Date());
                    }
                    communicationPreferencesDTOExisting.getMarketLanguageDTO().add(mlToKeep);
                }
            }
            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);
        }

        checkNumberMarketLanguage(communicationPreferencesDTOProcess);
        checkNumberMarketLanguage(communicationPreferencesDTOExisting);

        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;
    }

    private InformationDTO manageComPref_S_Sharepoint(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            for (MarketLanguageDTO mlToKeep : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                boolean isUpdated = false;
                for (MarketLanguageDTO mlExisting : communicationPreferencesDTOExisting.getMarketLanguageDTO()) {
                    if (mlToKeep.getMarket().equalsIgnoreCase(mlExisting.getMarket())) {
                        // Modification signature
                        mlExisting.setModificationDate(new Date());
                        mlExisting.setModificationSignature(mlToKeep.getModificationSignature() != null
                                && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getModificationSignature()) ? mlToKeep.getModificationSignature() : signature);
                        mlExisting.setModificationSite(mlToKeep.getModificationSite() != null
                                && !EMPTY_STRING.equalsIgnoreCase(mlToKeep.getModificationSite()) ? mlToKeep.getModificationSite() : site);
                        if (StringUtils.isNotEmpty(mlToKeep.getLanguage())) {
                            mlExisting.setLanguage(mlToKeep.getLanguage());
                        }
                        if (StringUtils.isNotEmpty(mlToKeep.getOptIn())) {
                            mlExisting.setOptIn(mlToKeep.getOptIn());
                        }
                        if (StringUtils.isNotEmpty(mlToKeep.getMedia1())) {
                            mlExisting.setMedia1(mlToKeep.getMedia1());
                        }
                        if (mlToKeep.getDateOfConsent() != null) {
                            mlExisting.setDateOfConsent(mlToKeep.getDateOfConsent());
                        }
                        isUpdated = true;
                    }
                }
                if (!isUpdated) {
                    if (mlToKeep.getCreationDate() == null) {
                        mlToKeep.setCreationDate(new Date());
                    }
                    if (mlToKeep.getCreationSignature() == null || EMPTY_STRING.equals(mlToKeep.getCreationSignature())) {
                        mlToKeep.setCreationSignature(signature);
                    }
                    if (mlToKeep.getCreationSite() == null || EMPTY_STRING.equals(mlToKeep.getCreationSite())) {
                        mlToKeep.setCreationSite(site);
                    }
                    mlToKeep.setModificationDate(new Date());
                    if (mlToKeep.getModificationSignature() == null || EMPTY_STRING.equals(mlToKeep.getModificationSignature())) {
                        mlToKeep.setModificationSignature(signature);
                    }
                    mlToKeep.setModificationSite(site);
                    if (mlToKeep.getDateOfConsent() == null) {
                        mlToKeep.setDateOfConsent(new Date());
                    }
                    communicationPreferencesDTOExisting.getMarketLanguageDTO().add(mlToKeep);
                }
            }
            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);
        }

        checkNumberMarketLanguage(communicationPreferencesDTOProcess);
        checkNumberMarketLanguage(communicationPreferencesDTOExisting);

        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;

    }

    public static void checkNumberMarketLanguage(CommunicationPreferencesDTO cp) throws MaximumSubscriptionsException {
        if (cp != null && !UList.isNullOrEmpty(cp.getMarketLanguageDTO()) && cp.getMarketLanguageDTO().size() > MAX_MARKET_FOR_SALES) {
            throw new MaximumSubscriptionsException("MAXIMUM NUMBER OF SUBSCRIBED NEWSLETER SALES REACHED");
        }
    }

    /**
     * Manage a Communication Preference with Domain P
     *
     * @param individuDTO
     * @param communicationPreferencesDTOProcess
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private InformationDTO manageComPref_P(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);
        }

        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;
    }

    /**
     * Manage a Communication Preference with Domain U
     *
     * @param individuDTO
     * @param communicationPreferencesDTOProcess
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private InformationDTO manageComPref_U(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            MarketLanguageDTO mlToKeep = new MarketLanguageDTO();
            for (MarketLanguageDTO ml : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                mlToKeep = ml;
                break;
            }

            MarketLanguageDTO mlToUpdate = null;
            for (MarketLanguageDTO ml : communicationPreferencesDTOExisting.getMarketLanguageDTO()) {
                if (mlToKeep.getMarket().equalsIgnoreCase(ml.getMarket()) && mlToKeep.getLanguage().equalsIgnoreCase(ml.getLanguage())) {
                    mlToUpdate = ml;
                }
            }

            if (mlToUpdate != null) {
                if (StringUtils.isNotEmpty(mlToKeep.getOptIn())) {
                    mlToUpdate.setOptIn(mlToKeep.getOptIn());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMarket())) {
                    mlToUpdate.setMarket(mlToKeep.getMarket());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getLanguage())) {
                    mlToUpdate.setLanguage(mlToKeep.getLanguage());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMedia1())) {
                    mlToUpdate.setMedia1(mlToKeep.getMedia1());
                }
                if (mlToKeep.getDateOfConsent() != null) {
                    mlToUpdate.setDateOfConsent(mlToKeep.getDateOfConsent());
                }
				updateSignatureForMofification(site, signature, mlToUpdate, mlToKeep);
			} else {
				if (UList.isNullOrEmpty(communicationPreferencesDTOExisting.getMarketLanguageDTO())) {
					communicationPreferencesDTOExisting.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
				}
				for (MarketLanguageDTO newMarketLanguage : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
					updatedSignatureForCreation(site, signature, newMarketLanguage);
				}
				communicationPreferencesDTOExisting.getMarketLanguageDTO()
						.addAll(communicationPreferencesDTOProcess.getMarketLanguageDTO());
			}

            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);

        }

        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;
    }

	private void updatedSignatureForCreation(String site, String signature, MarketLanguageDTO newMarketLanguage) {
		// Create signature
		Date now = new Date();
		if (newMarketLanguage.getCreationDate() == null) {
			newMarketLanguage.setCreationDate(now);
		}
		if (StringUtils.isEmpty(newMarketLanguage.getCreationSignature())) {
			newMarketLanguage.setCreationSignature(signature);
		}
		if (StringUtils.isEmpty(newMarketLanguage.getCreationSite())) {
			newMarketLanguage.setCreationSite(site);
		}

		// Update signature
		if (newMarketLanguage.getModificationDate() == null) {
			newMarketLanguage.setModificationDate(now);
		}
		if (StringUtils.isEmpty(newMarketLanguage.getModificationSignature())) {
			newMarketLanguage.setModificationSignature(signature);
		}
		if (StringUtils.isEmpty(newMarketLanguage.getModificationSite())) {
			newMarketLanguage.setModificationSite(site);
		}

	}

	private void updateSignatureForMofification(String site, String signature, MarketLanguageDTO existingMarketLanguage,
			MarketLanguageDTO newMarketLanguage) {
		// Create signature
		if (newMarketLanguage.getCreationDate() != null) {
			existingMarketLanguage.setCreationDate(newMarketLanguage.getCreationDate());
		}
		if (StringUtils.isNotEmpty(newMarketLanguage.getCreationSignature())) {
			existingMarketLanguage.setCreationSignature(newMarketLanguage.getCreationSignature());
		}
		if (StringUtils.isNotEmpty(newMarketLanguage.getCreationSite())) {
			existingMarketLanguage.setCreationSite(newMarketLanguage.getCreationSite());
		}

		// Update signature
		Date modificationDate = newMarketLanguage.getModificationDate() == null ? new Date()
				: newMarketLanguage.getModificationDate();
		existingMarketLanguage.setModificationDate(modificationDate);

		String modificationSignature = StringUtils.isEmpty(newMarketLanguage.getModificationSignature()) ? signature
				: newMarketLanguage.getModificationSignature();
		existingMarketLanguage.setModificationSignature(modificationSignature);

		String modificationSite = StringUtils.isEmpty(newMarketLanguage.getModificationSite()) ? site
				: newMarketLanguage.getModificationSite();
		existingMarketLanguage.setModificationSite(modificationSite);
	}

    /**
     * Manage a Communication Preference with Domain P
     *
     * @param individuDTO
     * @param communicationPreferencesDTOProcess
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private InformationDTO manageComPref_F(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            MarketLanguageDTO mlToKeep = new MarketLanguageDTO();
            for (MarketLanguageDTO ml : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                mlToKeep = ml;
                break;
            }

            if (!UList.isNullOrEmpty(communicationPreferencesDTOExisting.getMarketLanguageDTO())) {
                MarketLanguageDTO mlToUpdate = new MarketLanguageDTO();
                for (MarketLanguageDTO ml : communicationPreferencesDTOExisting.getMarketLanguageDTO()) {
                    mlToUpdate = ml;
                    break;
                }

                if (StringUtils.isNotEmpty(mlToKeep.getOptIn())) {
                    mlToUpdate.setOptIn(mlToKeep.getOptIn());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMarket())) {
                    mlToUpdate.setMarket(mlToKeep.getMarket());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getLanguage())) {
                    mlToUpdate.setLanguage(mlToKeep.getLanguage());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMedia1())) {
                    mlToUpdate.setMedia1(mlToKeep.getMedia1());
                }
                if (mlToKeep.getDateOfConsent() != null) {
                    mlToUpdate.setDateOfConsent(mlToKeep.getDateOfConsent());
                }
                mlToUpdate.setModificationDate(new Date());
                mlToUpdate.setModificationSignature(signature);
                mlToUpdate.setModificationSite(site);

            } else {
                communicationPreferencesDTOExisting.setMarketLanguageDTO(new HashSet<MarketLanguageDTO>());
                communicationPreferencesDTOExisting.getMarketLanguageDTO().addAll(communicationPreferencesDTOProcess.getMarketLanguageDTO());
            }

            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);
        }

        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;
    }

    private InformationDTO manageComPref_Default(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) throws JrafDaoException, JrafDomainException {
        InformationDTO informationDTO = new InformationDTO();

        if (communicationPreferencesDTOExisting == null) {
            //Creation
            updatedFieldsForCreation(communicationPreferencesDTOProcess, site, signature);

            individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTOProcess);

            informationDTO.setDetails(COMPREF_CREATED_DETAILS);

        } else {
            //Update
            updatedFieldsForUpdate(communicationPreferencesDTOProcess, communicationPreferencesDTOExisting, site, signature);

            MarketLanguageDTO mlToKeep = new MarketLanguageDTO();
            for (MarketLanguageDTO ml : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                mlToKeep = ml;
                break;
            }

            if (!UList.isNullOrEmpty(communicationPreferencesDTOExisting.getMarketLanguageDTO())) {
                MarketLanguageDTO mlToUpdate = new MarketLanguageDTO();
                for (MarketLanguageDTO ml : communicationPreferencesDTOExisting.getMarketLanguageDTO()) {
                    mlToUpdate = ml;
                    break;
                }

                if (StringUtils.isNotEmpty(mlToKeep.getOptIn())) {
                    mlToUpdate.setOptIn(mlToKeep.getOptIn());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMarket())) {
                    mlToUpdate.setMarket(mlToKeep.getMarket());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getLanguage())) {
                    mlToUpdate.setLanguage(mlToKeep.getLanguage());
                }
                if (StringUtils.isNotEmpty(mlToKeep.getMedia1())) {
                    mlToUpdate.setMedia1(mlToKeep.getMedia1());
                }
                if (mlToKeep.getDateOfConsent() != null) {
                    mlToUpdate.setDateOfConsent(mlToKeep.getDateOfConsent());
                }
                mlToUpdate.setModificationDate(new Date());
                mlToUpdate.setModificationSignature(signature);
                mlToUpdate.setModificationSite(site);

            } else {
                communicationPreferencesDTOExisting.setMarketLanguageDTO(new HashSet<>());
                communicationPreferencesDTOExisting.getMarketLanguageDTO().addAll(communicationPreferencesDTOProcess.getMarketLanguageDTO());
            }

            informationDTO.setDetails(COMPREF_UPDATED_DETAILS);
        }
        informationDTO.setCode(COMPREF_CREATED_UPDATED_CODE);
        informationDTO.setName(getComPrefName(communicationPreferencesDTOProcess));

        return informationDTO;
    }

    private void updatedFieldsForCreation(CommunicationPreferencesDTO communicationPreferencesDTOProcess, String site, String signature) {
        communicationPreferencesDTOProcess.setCreationDate(new Date());
        communicationPreferencesDTOProcess.setCreationSignature(signature);
        communicationPreferencesDTOProcess.setCreationSite(site);
        communicationPreferencesDTOProcess.setModificationDate(new Date());
        communicationPreferencesDTOProcess.setModificationSignature(signature);
        communicationPreferencesDTOProcess.setModificationSite(site);
        if (communicationPreferencesDTOProcess.getDateOptin() == null) {
            communicationPreferencesDTOProcess.setDateOptin(new Date());
        }

        if (!UList.isNullOrEmpty(communicationPreferencesDTOProcess.getMarketLanguageDTO())) {
            for (MarketLanguageDTO ml : communicationPreferencesDTOProcess.getMarketLanguageDTO()) {
                ml.setCreationDate(ml.getCreationDate() != null
                        ? ml.getCreationDate() : new Date());
                ml.setCreationSignature(ml.getCreationSignature() != null && !EMPTY_STRING.equalsIgnoreCase(ml.getCreationSignature())
                        ? ml.getCreationSignature() : signature);
                ml.setCreationSite(ml.getCreationSite() != null && !EMPTY_STRING.equalsIgnoreCase(ml.getCreationSite())
                        ? ml.getCreationSite() : site);
                ml.setModificationDate(ml.getModificationDate() != null
                        ? ml.getModificationDate() : new Date());
                ml.setModificationSignature(ml.getModificationSignature() != null && !EMPTY_STRING.equalsIgnoreCase(ml.getModificationSignature())
                        ? ml.getModificationSignature() : signature);
                ml.setModificationSite(ml.getModificationSite() != null && !EMPTY_STRING.equalsIgnoreCase(ml.getModificationSite())
                        ? ml.getModificationSite() : site);
                if (ml.getDateOfConsent() == null) {
                    ml.setDateOfConsent(new Date());
                }
                if (StringUtils.isEmpty(ml.getMedia1()) && StringUtils.isNotEmpty(communicationPreferencesDTOProcess.getMedia1())) {
                    ml.setMedia1(communicationPreferencesDTOProcess.getMedia1());
                }
            }
        }
    }

    private void updatedFieldsForUpdate(CommunicationPreferencesDTO communicationPreferencesDTOProcess, CommunicationPreferencesDTO communicationPreferencesDTOExisting, String site, String signature) {
        if (StringUtils.isNotEmpty(communicationPreferencesDTOProcess.getSubscribe())) {
            communicationPreferencesDTOExisting.setSubscribe(communicationPreferencesDTOProcess.getSubscribe());
        }
        if (communicationPreferencesDTOProcess.getDateOptin() != null) {
            communicationPreferencesDTOExisting.setDateOptin(communicationPreferencesDTOProcess.getDateOptin());
        }
        communicationPreferencesDTOExisting.setModificationDate(new Date());
        communicationPreferencesDTOExisting.setModificationSignature(signature);
        communicationPreferencesDTOExisting.setModificationSite(site);
        if (StringUtils.isNotEmpty(communicationPreferencesDTOProcess.getChannel())) {
            communicationPreferencesDTOExisting.setChannel(communicationPreferencesDTOProcess.getChannel());
        }
        if (StringUtils.isNotEmpty(communicationPreferencesDTOProcess.getMedia1())) {
            communicationPreferencesDTOExisting.setMedia1(communicationPreferencesDTOProcess.getMedia1());
        }
    }

    /**
     * Check for S (Sales) Domain if one of Market or Language parameter in input is missing
     *
     * @param communicationPreferencesDTO
     * @param market
     * @param language
     * @throws MarketLanguageException
     */
    private String checkMarketLanguageForSalesComPref(String market, String language, CommunicationPreferencesDTO communicationPreferencesDTO) throws MarketLanguageException {
        if (communicationPreferencesDTO.getDomain().equalsIgnoreCase(CommunicationPreferencesConstantValues.DOMAIN_S)) {
            if (market == null && language != null) {
                return "Cannot create communication preference. Market is missing in input";
            }
            if (market != null && language == null) {
                return "Cannot create communication preference. Language is missing in input";
            }
        }

        return null;
    }

    /**
     * <<<<<<< HEAD
     * We create or update the ComPref following if we already found a match in the existing ComPrefs
     *
     * @param individuDTO
     * @param communicationPreferencesDTO
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private Map<InformationDTO, CommunicationPreferencesDTO> manageComPref(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDaoException, JrafDomainException {
        return manageComPref(individuDTO, communicationPreferencesDTO, null);
    }

    /**
     * If there is no error, we create or update the ComPref following if we already found a match in the existing ComPrefs
     * Else, we return an Information corresponding to the error
     *
     * @param individuDTO
     * @param communicationPreferencesDTO
     * @param error
     * @return Map of InformationDTO CommunicationPreferencesDTO
     * @throws JrafDaoException
     * @throws JrafDomainException
     */
    private Map<InformationDTO, CommunicationPreferencesDTO> manageComPref(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTO, String error) throws JrafDaoException, JrafDomainException {
        Map<InformationDTO, CommunicationPreferencesDTO> result = new HashMap<InformationDTO, CommunicationPreferencesDTO>();
        InformationDTO information = new InformationDTO();

        if (error != null) {
            information.setCode(COMPREF_NOT_CREATED_UPDATED_CODE);
            information.setDetails(error);
            information.setName(getComPrefName(communicationPreferencesDTO));
            result.put(information, null);
        } else {
            //Case of Creation
            if (!isUpdatingComPref(individuDTO, communicationPreferencesDTO)) {
                if (individuDTO.getCommunicationpreferencesdto() != null) {
                    individuDTO.getCommunicationpreferencesdto().add(communicationPreferencesDTO);
                    information.setCode(COMPREF_CREATED_UPDATED_CODE);
                    information.setDetails(COMPREF_CREATED_DETAILS);
                    information.setName(getComPrefName(communicationPreferencesDTO));
                }
                //Case of Update
            } else {
                information.setCode(COMPREF_CREATED_UPDATED_CODE);
                information.setDetails(COMPREF_UPDATED_DETAILS);
                information.setName(getComPrefName(communicationPreferencesDTO));
            }
            result.put(information, communicationPreferencesDTO);
        }

        return result;
    }

    /**
     * Once the CompRef is initialized (with Market Language), we check if it already existing
     * If it does, we just update it
     * Else we keep it as initialized to create a new one for this individu
     *
     * @param individuDTO
     * @param communicationPreferencesDTO
     * @return true if existing else false
     */
    private boolean isUpdatingComPref(IndividuDTO individuDTO, CommunicationPreferencesDTO communicationPreferencesDTO) {
        String domain = communicationPreferencesDTO.getDomain();
        String groupType = communicationPreferencesDTO.getComGroupType();
        String type = communicationPreferencesDTO.getComType();

        String market = null;
        String language = null;

        if (communicationPreferencesDTO.getMarketLanguageDTO() != null && !communicationPreferencesDTO.getMarketLanguageDTO().isEmpty()) {
            List<MarketLanguageDTO> listMarketLanguageDTO = new ArrayList<MarketLanguageDTO>(communicationPreferencesDTO.getMarketLanguageDTO());
            market = listMarketLanguageDTO.get(0).getMarket();
            language = listMarketLanguageDTO.get(0).getLanguage();
        }

        if (individuDTO.getCommunicationpreferencesdto() != null && !individuDTO.getCommunicationpreferencesdto().isEmpty()) {
            for (CommunicationPreferencesDTO comPrefDTO : individuDTO.getCommunicationpreferencesdto()) {
                if (comPrefDTO.getDomain().equalsIgnoreCase(domain) && comPrefDTO.getComGroupType().equalsIgnoreCase(groupType) && comPrefDTO.getComType().equalsIgnoreCase(type)) {
                    if (market != null && language != null) {
                        for (MarketLanguageDTO marketLanguageDTO : comPrefDTO.getMarketLanguageDTO()) {
                            if (marketLanguageDTO.getMarket().equalsIgnoreCase(market) && marketLanguageDTO.getLanguage().equalsIgnoreCase(language)) {
                                comPrefDTO.setSubscribe(communicationPreferencesDTO.getSubscribe());
                                comPrefDTO.setDateOptin(new Date());
                                comPrefDTO.setModificationDate(new Date());
                                comPrefDTO.setModificationSignature(communicationPreferencesDTO.getModificationSignature());
                                comPrefDTO.setModificationSite(communicationPreferencesDTO.getModificationSite());

                                marketLanguageDTO.setOptIn(communicationPreferencesDTO.getSubscribe());
                                marketLanguageDTO.setDateOfConsent(new Date());
                                marketLanguageDTO.setModificationDate(new Date());
                                marketLanguageDTO.setModificationSignature(communicationPreferencesDTO.getModificationSignature());
                                marketLanguageDTO.setModificationSite(communicationPreferencesDTO.getModificationSite());

                                return true;
                            }
                        }

                        //REPIND-1508: For Sales Compref, its allowed to have many ML on the same Compref
                        //So we need to add this new ML to the existing Compref S
                        if (CommunicationPreferencesConstantValues.DOMAIN_S.equalsIgnoreCase(domain)) {
                            comPrefDTO.setSubscribe(communicationPreferencesDTO.getSubscribe());
                            comPrefDTO.setDateOptin(new Date());
                            comPrefDTO.setModificationDate(new Date());
                            comPrefDTO.setModificationSignature(communicationPreferencesDTO.getModificationSignature());
                            comPrefDTO.setModificationSite(communicationPreferencesDTO.getModificationSite());
                            for (MarketLanguageDTO marketLanguageDTO : communicationPreferencesDTO.getMarketLanguageDTO()) {
                                comPrefDTO.getMarketLanguageDTO().add(marketLanguageDTO);
                            }

                            return true;
                        }
                    } else {
                        comPrefDTO.setSubscribe(communicationPreferencesDTO.getSubscribe());
                        comPrefDTO.setModificationDate(new Date());
                        comPrefDTO.setModificationSignature(communicationPreferencesDTO.getModificationSignature());
                        comPrefDTO.setModificationSite(communicationPreferencesDTO.getModificationSite());

                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * =======
     * >>>>>>> REPIND-1656
     * Get the FB_ESS RefComPref associated to the market in parameter
     *
     * @param domain
     * @param groupType
     * @param type
     * @param market
     * @return
     * @throws MarketLanguageException
     * @throws JrafDaoException
     */
    private RefComPref getRefComPrefFBESS(String domain, String groupType, String type, String market) throws MarketLanguageException, JrafDaoException {
        List<RefComPref> listRefComPref = refComPrefRepository.findComPerfByMarketComTypeComGType(market, domain, type, groupType);

        if (!UList.isNullOrEmpty(listRefComPref)) {
            if (listRefComPref.size() > 1) {
                throw new MarketLanguageException("Too many communications preferences are defined", null, null, null);
            }
        } else {
            throw new MarketLanguageException("No communication preferences is defined", null, null, null);
        }

        return listRefComPref.get(0);
    }

    /**
     * Get the RefComPref associated to the Market (FR for instance)
     * If not found, look for the RefComPref with MarketWorld (*)
     *
     * @param domain
     * @param groupType
     * @param type
     * @param market
     * @return
     * @throws JrafDomainException
     */
    private RefComPref getRefComPrefByMarket(String domain, String groupType, String type, String market) throws JrafDomainException {
        RefComPref refComPref = null;

        List<RefComPref> listRefComPref = refComPrefRepository.findComPerfByMarketComTypeComGType(market, domain, type, groupType);

        if (!UList.isNullOrEmpty(listRefComPref)) {
            refComPref = listRefComPref.get(0);
        } else {
            listRefComPref = refComPrefRepository.findComPerfByMarketComTypeComGType(CommunicationPreferencesConstantValues.MARKET_WORLD, domain, type, groupType);
            if (!UList.isNullOrEmpty(listRefComPref)) {
                refComPref = listRefComPref.get(0);
            } else {
                throw new MarketLanguageException("Communication preference not available for market " + market, null, null, null);
            }
        }

        return refComPref;
    }

    /**
     * Build the name of the ComPref for the output
     *
     * @param communicationPreferencesDTO
     * @return
     */
    private String getComPrefName(CommunicationPreferencesDTO communicationPreferencesDTO) {
        return communicationPreferencesDTO.getDomain() + " - " + communicationPreferencesDTO.getComGroupType() + " - " + communicationPreferencesDTO.getComType();
    }

    /**
     * Build the name of the ComPref for the output
     *
     * @param refComPrefDgtDTO
     * @return
     */
    private String getComPrefName(RefComPrefDgtDTO refComPrefDgtDTO) {
        return refComPrefDgtDTO.getDomain().getCodeDomain() + " - " + refComPrefDgtDTO.getGroupType().getCodeGType() + " - " + refComPrefDgtDTO.getType().getCodeType();
    }


    @Transactional(readOnly = true)
    public String getMarketForComPref(String gin) throws JrafDaoException, MarketLanguageException {

        String market = null;

        // Get the most recent code pays
        //REPIND-934 : Search valid ISI address first. Then ISI address non valid if needed
        List<String> listeCodePays = postalAddressRepository.findISIValidPostalAddressCodePays(gin, AddressRoleCodeEnum.PRINCIPAL.toString(),
                "ISI");
        String codePays;
        if (UList.isNullOrEmpty(listeCodePays)) {
            listeCodePays = postalAddressRepository.findISIPostalAddressCodePays(gin, AddressRoleCodeEnum.PRINCIPAL.toString(),
                    "ISI");
        }
        if (UList.isNullOrEmpty(listeCodePays)) {
            listeCodePays = postalAddressRepository.findValidPostalAddressCodePays(gin);
        }
        if (UList.isNullOrEmpty(listeCodePays)) {
            throw new MarketLanguageException("No ISIS or valid mailing address available", null, null, null);
        } else {
            codePays = listeCodePays.get(0);
        }

        // Get one of the markets
        List<String> listeMarket = refComPrefCountryMarketRepository.findMarketByCodePays(codePays);
        if (UList.isNullOrEmpty(listeMarket)) {
            throw new MarketLanguageException("No Market is associated to country: " + codePays, null, null, null);
        } else {
            market = listeMarket.get(0);
        }

        return market;
    }


    public String getLanguageForComPref(RefComPref refComPref, RefComPref refComPrefFBEss, Profils profils) {
        String language = null;

        if (refComPref == null || CommunicationPreferencesConstantValues.MARKET_WORLD.equals(refComPref.getMarket())) {
            if (refComPrefFBEss == null) {
                return null;
            }
            language = ruleLanguage(refComPrefFBEss, profils);
        } else {
            language = ruleLanguage(refComPref, profils);
        }

        return language;
    }

    /**
     * Business rule code language for initializing Code Langue
     *
     * @param refCompRef
     * @param profils
     * @return
     */
    private String ruleLanguage(RefComPref refCompRef, Profils profils) {

        String codeLangue = null;

        if (profils != null && StringUtils.isNotEmpty(profils.getScode_langue())) {
            codeLangue = profils.getScode_langue();
        }

        return ruleLanguage(refCompRef, codeLangue);
    }

    /**
     * Business rule code language for initializing Code Langue
     *
     * @param refCompRef
     * @param codeLangue
     * @return
     */
    public String ruleLanguage(RefComPref refCompRef, String codeLangue) {

        if (null != codeLangue) {
            if (codeLangue.equals(refCompRef.getDefaultLanguage1())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage2())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage3())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage4())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage5())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage6())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage7())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage8())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage9())) {
                return codeLangue;
            }
            if (codeLangue.equals(refCompRef.getDefaultLanguage10())) {
                return codeLangue;
            }
        }

        return refCompRef.getDefaultLanguage1();
    }


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public Map<InformationDTO, CommunicationPreferencesDTO> createComPrefBasedOnAGroup(String gin, String productType, String productSubType, String market, String language, String signature, String site, String channel) throws JrafDomainException {
        List<RefComPrefDgtDTO> listOptin = new ArrayList<RefComPrefDgtDTO>();
        List<RefComPrefDgtDTO> listOptout = new ArrayList<RefComPrefDgtDTO>();
        Map<InformationDTO, CommunicationPreferencesDTO> mapOptAll = new HashMap<InformationDTO, CommunicationPreferencesDTO>();
        String errorFB = null;

        //Calcul the Market for this Individu
        String marketFB = null;
        try {
            marketFB = getMarketForComPref(gin);
        } catch (MarketLanguageException e) {
            errorFB = e.getMessage();
        }

        //Get the Profil in order to retrieve the communication language
        Optional<Profils> profils = profilsRepository.findById(gin);

        //Get the FB_ESS ComPref used in case of MarketWorld (*) to define the language
        RefComPref refComPrefFB_ESS = null;
        try {
            refComPrefFB_ESS = getRefComPrefFBESS(CommunicationPreferencesConstantValues.DOMAIN_F, CommunicationPreferencesConstantValues.NEWSLETTER, CommunicationPreferencesConstantValues.FB_ESS, marketFB);
        } catch (MarketLanguageException e) {
            if (StringUtils.isEmpty(errorFB)) errorFB = e.getMessage();
        }

        RefProductDTO refProductDTO = refProductDS.getProductByProductType(productType, productSubType);

        if (SicStringUtils.getFrenchBoolean(refProductDTO.getGenerateComPref())) {
            List<RefComPrefGroupDTO> listRefComPrefGroupDTO = refProductComPrefGroupDS.getAllRefComPrefGroupByProductId(refProductDTO);

            for (RefComPrefGroupDTO refComPrefGroupDTO : listRefComPrefGroupDTO) {

                String mandatoryOption = refComPrefGroupDTO.getRefComPrefGroupId().getRefComPrefGroupInfo().getMandatoryOption();
                String defaultOption = refComPrefGroupDTO.getRefComPrefGroupId().getRefComPrefGroupInfo().getDefaultOption();
                RefComPrefDgtDTO refComPrefDgtDTO = refComPrefGroupDTO.getRefComPrefGroupId().getRefComPrefDgt();

                // mandatoryOption = NO And defaultOption = NO
                if (SicStringUtils.getNotFrenchBoolean(mandatoryOption) && SicStringUtils.getNotFrenchBoolean(defaultOption)) {
                    listOptout.add(refComPrefDgtDTO);
                } else {
                    listOptin.add(refComPrefDgtDTO);
                }
            }

            signature = modifySignatureForGroup(signature);

            if (!listOptin.isEmpty()) {
                for (RefComPrefDgtDTO refComPrefDgtDTO : listOptin) {
                    String error = errorFB;

                    //No error for Domain other than F or S
                    if (!refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("F") && !refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("S")) {
                        error = null;
                    }

                    //Get RefComPref
                    RefComPref refComPref = null;
                    try {
                        refComPref = getRefComPrefByMarket(refComPrefDgtDTO.getDomain().getCodeDomain(), refComPrefDgtDTO.getGroupType().getCodeGType(), refComPrefDgtDTO.getType().getCodeType(), marketFB);
                    } catch (MarketLanguageException e) {
                        //If we don't have the Market Language, we use the Market Languag FB, error must no be propagated in order to create the compref later
                        if (!"S".equalsIgnoreCase(refComPrefDgtDTO.getDomain().getCodeDomain())) {
                            if (StringUtils.isEmpty(error)) error = e.getMessage();
                        }
                    }

                    String media = null;
                    if (refComPref != null && refComPref.getMedia() != null) {
                        media = refComPref.getMedia().getCodeMedia();
                    } else {
                        media = "E"; //Default Media
                    }

                    //Get Language for FB Domain
                    String languageFB = getLanguageForComPref(refComPref, refComPrefFB_ESS, profils.orElse(null));

                    CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
                    error = provideComPrefForPermissionAndGroup(comPrefToProcess, refComPrefDgtDTO, channel, media, "Y", marketFB, languageFB, null, null, error);

                    //If an error is present, we don't create the compref
                    if (StringUtils.isNotEmpty(error)) {
                        InformationDTO informationDTO = new InformationDTO();
                        informationDTO.setCode(COMPREF_NOT_CREATED_UPDATED_CODE);
                        informationDTO.setDetails(error);
                        informationDTO.setName(getComPrefName(refComPrefDgtDTO));
                        mapOptAll.put(informationDTO, comPrefToProcess);
                    } else {
                        mapOptAll.putAll(createUpdateComPrefFromDGT(gin, comPrefToProcess, site, signature));
                    }
                }
            }

            if (!listOptout.isEmpty()) {
                for (RefComPrefDgtDTO refComPrefDgtDTO : listOptout) {
                    String error = errorFB;

                    //No error for Domain other than F or S
                    if (!refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("F") && !refComPrefDgtDTO.getDomain().getCodeDomain().equalsIgnoreCase("S")) {
                        error = null;
                    }

                    //Get RefComPref
                    RefComPref refComPref = null;
                    try {
                        refComPref = getRefComPrefByMarket(refComPrefDgtDTO.getDomain().getCodeDomain(), refComPrefDgtDTO.getGroupType().getCodeGType(), refComPrefDgtDTO.getType().getCodeType(), marketFB);
                    } catch (MarketLanguageException e) {
                        //If we don't have the Market Language, we use the Market Languag FB, error must no be propagated in order to create the compref later
                        if (!"S".equalsIgnoreCase(refComPrefDgtDTO.getDomain().getCodeDomain())) {
                            if (StringUtils.isEmpty(error)) error = e.getMessage();
                        }
                    }

                    String media = null;
                    if (refComPref != null && refComPref.getMedia() != null) {
                        media = refComPref.getMedia().getCodeMedia();
                    } else {
                        media = "E"; //Default Media
                    }

                    //Get Language for FB Domain
                    String languageFB = getLanguageForComPref(refComPref, refComPrefFB_ESS, profils.orElse(null));

                    CommunicationPreferencesDTO comPrefToProcess = new CommunicationPreferencesDTO();
                    error = provideComPrefForPermissionAndGroup(comPrefToProcess, refComPrefDgtDTO, channel, media, "N", marketFB, languageFB, null, null, error);

                    //If an error is present, we don't create the compref
                    if (StringUtils.isNotEmpty(error)) {
                        InformationDTO informationDTO = new InformationDTO();
                        informationDTO.setCode(COMPREF_NOT_CREATED_UPDATED_CODE);
                        informationDTO.setDetails(error);
                        informationDTO.setName(getComPrefName(refComPrefDgtDTO));
                        mapOptAll.put(informationDTO, comPrefToProcess);
                    } else {
                        mapOptAll.putAll(createUpdateComPrefFromDGT(gin, comPrefToProcess, site, signature));
                    }
                }
            }
        }

        return mapOptAll;
    }

    /**
     * We want to know from which Permission Id the Compref was created
     *
     * @param pSignature
     * @return
     */
    private String modifySignatureForPermission(String pSignature, String pPermissionId) {

        pPermissionId = "/P" + pPermissionId;
        if (pSignature.length() + pPermissionId.length() > 16) {
            int overLimit = (pSignature.length() + pPermissionId.length()) - 16;
            pSignature = pSignature.substring(0, (pSignature.length() - overLimit)) + pPermissionId;
        } else {
            pSignature = pSignature + pPermissionId;
        }

        return pSignature;
    }

    /**
     * In case of GROUP, we override the Signature in order to track this step
     *
     * @param pSignature
     * @return
     */
    private String modifySignatureForGroup(String pSignature) {

        if (!StringUtils.isEmpty(pSignature) && pSignature.length() > 14) pSignature = pSignature.substring(0, 14);

        return pSignature + "/G";
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void deleteComPrefFromDGT(String gin, List<CommunicationPreferencesDTO> comprefsFromWS) throws JrafDomainException {

        //Get the Individu
        IndividuDTO individuFromDB = individuDS.getByGin(gin);

        if (individuFromDB != null && !UList.isNullOrEmpty(individuFromDB.getCommunicationpreferencesdto())) {

            checkForDeleteMode(comprefsFromWS);

            for (CommunicationPreferencesDTO comprefWS : comprefsFromWS) {

                CommunicationPreferencesDTO compref = null;
                Iterator<CommunicationPreferencesDTO> itCompref = individuFromDB.getCommunicationpreferencesdto().iterator();

                while (itCompref.hasNext()) {
                    CommunicationPreferencesDTO comprefTemp = itCompref.next();
                    if (comprefTemp.getDomain().equalsIgnoreCase(comprefWS.getDomain()) && comprefTemp.getComGroupType().equalsIgnoreCase(comprefWS.getComGroupType()) && comprefTemp.getComType().equalsIgnoreCase(comprefWS.getComType())) {
                        compref = comprefTemp;
                        break;
                    }
                }

                if (compref == null) {
                    throw new JrafDomainException("Communication Preference does not exist. Unable to delete.");
                }

                if (!UList.isNullOrEmpty(comprefWS.getMarketLanguageDTO())) {
                    for (MarketLanguageDTO mlWS : comprefWS.getMarketLanguageDTO()) {
                        boolean markLangDeleted = false;
                        Iterator<MarketLanguageDTO> itMarkLang = compref.getMarketLanguageDTO().iterator();

                        while (itMarkLang.hasNext()) {
                            MarketLanguageDTO markLang = itMarkLang.next();

                            if (markLang.getMarket().equalsIgnoreCase(mlWS.getMarket()) && markLang.getLanguage().equalsIgnoreCase(mlWS.getLanguage())) {
                                itMarkLang.remove();
                                markLangDeleted = true;
                            }
                        }

                        if (!markLangDeleted) {
                            throw new JrafDomainException("Market Language does not exist. Unable to delete.");
                        }

                        if (compref.getMarketLanguageDTO().size() == 0) {
                            itCompref.remove();
                        }

                    }
                } else {
                    itCompref.remove();
                }

            }

            //Update the individu in order to also update the communications preferences
            individuDS.updateComPrefOfIndividual(individuFromDB);
        } else {
            throw new JrafDomainException("Communication Preference does not exist. Unable to delete.");
        }
    }

    private void checkForDeleteMode(List<CommunicationPreferencesDTO> comprefsFromWS) throws JrafDomainException {

        for (CommunicationPreferencesDTO comPref : comprefsFromWS) {
            if (CommunicationPreferencesConstantValues.DOMAIN_S.equalsIgnoreCase(comPref.getDomain())) {
                if (UList.isNullOrEmpty(comPref.getMarketLanguageDTO())) {
                    throw new JrafDomainException("Market Language not provided. Unable to delete.");
                }
            } else {
                throw new JrafDomainException("Only Sales Domain can be deleted.");
            }

        }
    }

    public boolean ifComprefExists(IndividuDTO individu, String domain, String groupType, String type) {

        if (!UList.isNullOrEmpty(individu.getCommunicationpreferencesdto())) {
            for (CommunicationPreferencesDTO compref : individu.getCommunicationpreferencesdto()) {
                if (domain.equalsIgnoreCase(compref.getDomain()) && groupType.equalsIgnoreCase(compref.getComGroupType()) && type.equalsIgnoreCase(compref.getComType())) {
                    return true;
                }
            }
        }

        return false;
    }

    //REPIND-1747: Downgrade Ultimate
    public void deleteUltimateCompref(String gin) {
        deleteUltimateCompref(gin, null);
    }

	public void deleteUltimateCompref(String gin, Individu individu) {
		CommunicationPreferences ultimateCompref = communicationPreferencesRepository.findComPrefId(gin, "U", "S",
				"UL_PS");

		if (individu != null) {
			individu.getCommunicationpreferences().remove(ultimateCompref);
		}

		if (ultimateCompref != null) {
			communicationPreferencesRepository.delete(ultimateCompref);
		}
	}

    //REPIND-1747: Downgrade Ultimate
    public void createUltimateCompref(String gin) {
        CommunicationPreferences ultimateCompref = communicationPreferencesRepository.findComPrefId(gin, "U", "S", "UL_PS");

        if (ultimateCompref == null) {
            ultimateCompref = new CommunicationPreferences();
            ultimateCompref.setGin(gin);
            ultimateCompref.setDomain("U");
            ultimateCompref.setComGroupType("S");
            ultimateCompref.setComType("UL_PS");
            ultimateCompref.setDateOptin(new Date());
            ultimateCompref.setSubscribe("P");
            ultimateCompref.setCreationDate(new Date());
            ultimateCompref.setCreationSignature("Ulti CP init");
            ultimateCompref.setCreationSite("QVI");
            ultimateCompref.setModificationDate(new Date());
            ultimateCompref.setModificationSignature("Ulti CP init");
            ultimateCompref.setModificationSite("QVI");

            communicationPreferencesRepository.saveAndFlush(ultimateCompref);
        }
    }
}
