package com.airfrance.repind.service.firm.internal;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.firme.providefirmdata.ProvideFirmDataRequestDTO;
import com.airfrance.repind.dto.firme.providefirmdata.ProvideFirmDataResponseDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneFinanciereDTO;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.exception.firme.InvalidFirmTypeException;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.exception.TooManyResultsDomainException;
import com.airfrance.repind.scope.FirmScope;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repind.util.transformer.ScopeTransformer;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@org.springframework.stereotype.Service
public class ProvideFirmDataDS {

	/** logger */
	private static final Log log = LogFactory.getLog(ProvideFirmDataDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** main dao */
	@Autowired
	private PersonneMoraleRepository personneMoraleRepository;

	/** references on associated DAOs */
	@Autowired
	private EntrepriseRepository entrepriseRepository;

	/** references on associated DAOs */
	@Autowired
	private EtablissementRepository etablissementRepository;

	/** references on associated DAOs */
	@Autowired
	private GroupeRepository groupeRepository;

	/** references on associated DAOs */
	@Autowired
	private ServiceRepository serviceRepository;

	/** references on associated DAOs */
	@Autowired

	private MembreRepository membreRepository;

    @Transactional(readOnly=true)
	public Integer countWhere(PersonneMoraleDTO dto) throws JrafDomainException {
		PersonneMorale personneMorale = PersonneMoraleTransform.dto2BoLight(dto);
		return (int) personneMoraleRepository.count(Example.of(personneMorale));
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {

		/*
		 * PROTECTED REGION ID(_OL19YDNFEeS007mi-9xpRQ DS-CM create) ENABLED
		 * START
		 */
		PersonneMorale personneMorale = null;

		// light transformation dto -> bo
		personneMorale = PersonneMoraleTransform.dto2BoLight(personneMoraleDTO);

		// create in database (call the abstract class)
		personneMoraleRepository.saveAndFlush(personneMorale);

		// Version update and Id update if needed
		PersonneMoraleTransform.bo2DtoLight(personneMorale, personneMoraleDTO);
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(PersonneMoraleDTO dto) throws JrafDomainException {

		/*
		 * PROTECTED REGION ID(_OL19YDNFEeS007mi-9xpRQ DS-CM remove) ENABLED
		 * START
		 */
		PersonneMorale personneMorale = null;
		// chargement du bo
		personneMorale = personneMoraleRepository.getOne(dto.getGin());

		// Checking the optimistic strategy
		if (!(personneMorale.getVersion().equals(dto.getVersion()))) {
			throw new JrafDomainRollbackException("Optimistic failure on PersonneMorale : " + personneMorale);
		}

		// light transform dto -> bo
		PersonneMoraleTransform.dto2BoLight(dto, personneMorale);

		// delete (database)
		personneMoraleRepository.delete(personneMorale);
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(String oid) throws JrafDomainException {
		personneMoraleRepository.deleteById(oid);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(PersonneMoraleDTO personneMoraleDTO) throws JrafDomainException {
		PersonneMorale personneMorale = null;

		// chargement du bo
		personneMorale = personneMoraleRepository.getOne(personneMoraleDTO.getGin());

		// Checking the optimistic strategy
		if (!(personneMorale.getVersion().equals(personneMoraleDTO.getVersion()))) {
			throw new JrafDomainRollbackException("Optimistic failure on PersonneMorale" + personneMorale);
		}
		// transformation light dto -> bo
		PersonneMoraleTransform.dto2BoLight(personneMoraleDTO, personneMorale);
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public List<PersonneMoraleDTO> findAll() throws JrafDomainException {
		List<PersonneMoraleDTO> results = new ArrayList<>();
		for(PersonneMorale found : personneMoraleRepository.findAll()) {
			results.add(PersonneMoraleTransform.bo2DtoLight(found));
		}
		return results;
	}

    @Transactional(readOnly=true)
	public Integer count() throws JrafDomainException {
		return (int) personneMoraleRepository.count();
	}

    @Transactional(readOnly=true)
    public List<PersonneMoraleDTO> findByExample(PersonneMoraleDTO dto) throws JrafDomainException {
		PersonneMorale personneMorale = PersonneMoraleTransform.dto2BoLight(dto);
        List<PersonneMoraleDTO> result = new ArrayList<>();
        for (PersonneMorale found : personneMoraleRepository.findAll(Example.of(personneMorale))) {
			result.add(PersonneMoraleTransform.bo2DtoLight(found));
		}
		return result;
    }

    @Transactional(readOnly=true)
	public PersonneMoraleDTO get(PersonneMoraleDTO dto) throws JrafDomainException {
		/*
		 * PROTECTED REGION ID(_OL19YDNFEeS007mi-9xpRQ DS-CM get) ENABLED START
		 */
		PersonneMorale personneMorale = null;
		PersonneMoraleDTO personneMoraleDTO = null;
		// get en base
		personneMorale = personneMoraleRepository.getOne(dto.getGin());

		// transformation light bo -> dto
		if (personneMorale != null) {
			personneMoraleDTO = PersonneMoraleTransform.bo2DtoLight(personneMorale);
		}
		return personneMoraleDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public PersonneMoraleDTO get(String oid) throws JrafDomainException {
		/*
		 * PROTECTED REGION ID(_OL19YDNFEeS007mi-9xpRQ DS-CM getOid) ENABLED
		 * START
		 */
		PersonneMorale personneMorale = null;
		PersonneMoraleDTO personneMoraleDTO = null;
		// get en base
		personneMorale = personneMoraleRepository.getOne(oid);

		// transformation light bo -> dto
		if (personneMorale != null) {
			personneMoraleDTO = PersonneMoraleTransform.bo2DtoLight(personneMorale);
		}
		return personneMoraleDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * getFirm
	 * 
	 * @param request
	 *            in ProvideFirmDataRequestDTO
	 * @return The getFirm as <code>ProvideFirmDataResponseDTO</code>
	 * @throws JrafDomainException
	 *             en cas d'exception
	 */
    @Transactional(readOnly=true)
	public ProvideFirmDataResponseDTO getFirm(ProvideFirmDataRequestDTO request) throws JrafDomainException {
		/* PROTECTED REGION ID(_KtxCEDNHEeS007mi-9xpRQ) ENABLED START */
		/*
		 * PersonneMorale personneMorale = null; PersonneMoraleDTO
		 * personneMoraleDTO = null;
		 * 
		 * ProvideFirmDataResponseDTO provideFirmDataResponseDTO = new
		 * ProvideFirmDataResponseDTO(request.getIdentificationNumber());
		 * 
		 * // Search by GIN personneMorale =
		 * getAbstractDSPersonneMorale().get(request.getIdentificationNumber());
		 * 
		 * // transformation light bo -> dto if (personneMorale != null) {
		 * 
		 * log.debug("personneMorale.getActiviteLocal()="+personneMorale.
		 * getActiviteLocal());
		 * log.debug("PersonneMoraleTransform.bo2Dto(personneMorale)");
		 * personneMoraleDTO = PersonneMoraleTransform.bo2Dto(personneMorale);
		 * provideFirmDataResponseDTO.setIdentificationNumber(personneMoraleDTO.
		 * getGin()); ArrayList<PersonneMoraleDTO> listPersonneMoraleDTO = new
		 * ArrayList<PersonneMoraleDTO>();
		 * listPersonneMoraleDTO.add(personneMoraleDTO);
		 * provideFirmDataResponseDTO.setPersonneMoraleDTO(listPersonneMoraleDTO
		 * ); } else { log.debug("personneMorale is null"); }
		 */

		ProvideFirmDataResponseDTO provideFirmDataResponseDTO = new ProvideFirmDataResponseDTO(
				request.getIdentificationNumber());

		PersonneMoraleDTO personneMoraleDTO = findByEnumSearchWithAllCollections("GIN",
				request.getIdentificationNumber(), "", Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE, Arrays.asList("ALL"));
		if (personneMoraleDTO != null) {

			List<Membre> membres = membreRepository.findByGinPm(personneMoraleDTO.getGin());
			personneMoraleDTO.setMembres(MembreTransform.bo2DtoLight(membres));

			provideFirmDataResponseDTO.setPersonneMoraleDTO(Arrays.asList(personneMoraleDTO));
		} else {
			log.debug("personneMorale is null");
		}

		return provideFirmDataResponseDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * getFirmList
	 * 
	 * @param ginList
	 *            in String
	 * @return The getFirmList as <code>List<ProvideFirmDataResponseDTO></code>
	 * @throws JrafDomainException
	 *             en cas d'exception
	 */
    @Transactional(readOnly=true)
	public List<ProvideFirmDataResponseDTO> getFirmList(List<String> ginList) throws JrafDomainException {
		/* PROTECTED REGION ID(_MLEtkDNHEeS007mi-9xpRQ) ENABLED START */
		List<ProvideFirmDataResponseDTO> firmList = new ArrayList<ProvideFirmDataResponseDTO>();

		for (String gin : ginList) {
			ProvideFirmDataResponseDTO resp = getFirm(new ProvideFirmDataRequestDTO(gin, null, null, null));
			if (resp.getPersonneMoraleDTO() != null && !resp.getPersonneMoraleDTO().isEmpty()) {
				firmList.add(resp);
			}
		}

		return firmList;
		/* PROTECTED REGION END */
	}

	/* PROTECTED REGION ID(_OL19YDNFEeS007mi-9xpRQ u m) ENABLED START */
	// add your custom methods here if necessary

    @Transactional(readOnly=true)
	public PersonneMoraleDTO findByGinWithAllCollections(String optionValue) throws JrafDomainException {
		return findByEnumSearchWithAllCollections("gin", optionValue, "", true, true, true, true, true, true, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.airfrance.repind.service.firm.IProvideFirmDataDS#
	 * findByGinWithAllCollections(java.lang.String)
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public PersonneMoraleDTO findByEnumSearchWithAllCollections(String optionType, String optionValue, String azFilter,
			Boolean filterAddress, Boolean filterTelecom, Boolean filterEmail, Boolean filterMarketChoice,
			Boolean filterContract, Boolean filterZones, List<String> scope) throws JrafDomainException {

		log.debug("START findByEnumSearchWithAllCollections in DS with " + optionType + " = " + optionValue + " : "
				+ System.currentTimeMillis());

		// Prepare result
		PersonneMoraleDTO personneMoraleDTO = null;

		// Get main information about the firm
		PersonneMorale personneMoraleLight = getPMLight(optionType, optionValue);

		// Complete needed information about the firm depending on its nature
		PersonneMorale personneMorale = null;
		if (personneMoraleLight != null) {
			try {
				if (personneMoraleLight.getClass().equals(Groupe.class)) {
					personneMorale = groupeRepository.findByGinWithAllCollections(personneMoraleLight.getGin(), scope);
				} else if (personneMoraleLight.getClass().equals(Entreprise.class)) {
					personneMorale = entrepriseRepository.findByGinWithAllCollections(personneMoraleLight.getGin(), scope);
				} else if (personneMoraleLight.getClass().equals(Etablissement.class)) {
					personneMorale = etablissementRepository.findByGinWithAllCollectionsFusion(personneMoraleLight.getGin(),
							scope);
				} else if (personneMoraleLight.getClass().equals(Service.class)) {
					personneMorale = serviceRepository.findByGinWithAllCollections(personneMoraleLight.getGin(), scope);
				} else {
					throw new InvalidFirmTypeException("INVALID FIRM TYPE - AGENCY FOUND");
				}
			} catch (TooManyResultsDaoException e) {
				throw new TooManyResultsDomainException(e.getMessage());
			}

			// Transformation bo -> dto
			personneMoraleDTO = processReadPersonneMorale(personneMorale, azFilter, filterAddress, filterTelecom,
					filterEmail, filterMarketChoice, filterContract, filterZones, scope);
		}

		log.debug("END findByEnumSearchWithAllCollections in DS : " + System.currentTimeMillis());

		return personneMoraleDTO;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public PersonneMoraleDTO findWithFiltersAndScopes(String optionType, String optionValue, String azFilter,
			Boolean filterAddress, Boolean filterTelecom, Boolean filterEmail, Boolean filterMarketChoice,
			Boolean filterContract, Boolean filterZones, List<FirmScope> scopes) throws JrafDomainException {

		log.debug("START findWithFiltersAndScopes in DS with " + optionType + " = " + optionValue + " : "
				+ System.currentTimeMillis());

		// Prepare result
		PersonneMoraleDTO personneMoraleDTO = null;

		// Get main information about the firm
		PersonneMorale personneMoraleLight = getPMLight(optionType, optionValue);

		// Complete needed information about the firm depending on its nature
		PersonneMorale personneMorale = null;
		if (personneMoraleLight != null) {
			// Get String scopes list
			List<String> stringScopesList = ScopeTransformer.scopesList2StringList(scopes);

			try {
				if (personneMoraleLight.getClass().equals(Groupe.class)) {
					personneMorale = groupeRepository.findByGinWithAllCollections(personneMoraleLight.getGin(),
							stringScopesList);

				} else if (personneMoraleLight.getClass().equals(Entreprise.class)) {
					personneMorale = entrepriseRepository.findByGinWithAllCollections(personneMoraleLight.getGin(),
							stringScopesList);

				} else if (personneMoraleLight.getClass().equals(Etablissement.class)) {
					personneMorale = etablissementRepository.findByGinUsingScopes(personneMoraleLight.getGin(), scopes);

				} else if (personneMoraleLight.getClass().equals(Service.class)) {
					personneMorale = serviceRepository.findByGinWithAllCollections(personneMoraleLight.getGin(),
							stringScopesList);

				} else {
					throw new InvalidFirmTypeException("INVALID FIRM TYPE - AGENCY FOUND");
				}
			} catch (TooManyResultsDaoException e) {
				throw new TooManyResultsDomainException(e.getMessage());
			}

			// Transformation bo -> dto
			personneMoraleDTO = processReadPersonneMorale(personneMorale, azFilter, filterAddress, filterTelecom,
					filterEmail, filterMarketChoice, filterContract, filterZones, stringScopesList);

			// Clean characters
			cleanAndReplaceCharacters(personneMoraleDTO);
		}

		log.debug("END findWithFiltersAndScopes in DS : " + System.currentTimeMillis());

		return personneMoraleDTO;
	}

	private void cleanAndReplaceCharacters(PersonneMoraleDTO pmDTOToClean) {

		// Clean Name
		pmDTOToClean.setNom(SicStringUtils.toUpperCaseWithoutAccentsForFirms(pmDTOToClean.getNom()));

		// Clean Address (Address Name, Address complement, No and Rue, City)
		if (pmDTOToClean.getPostalAddresses() != null) {
			Iterator<PostalAddressDTO> iteratorPostalAddressDTO = pmDTOToClean.getPostalAddresses().iterator();
			while (iteratorPostalAddressDTO.hasNext()) {
				PostalAddressDTO postalAddressDTO = iteratorPostalAddressDTO.next();
				postalAddressDTO.setSraison_sociale(
						SicStringUtils.toUpperCaseWithoutAccentsForFirms(postalAddressDTO.getSraison_sociale()));
				postalAddressDTO.setScomplement_adresse(
						SicStringUtils.toUpperCaseWithoutAccentsForFirms(postalAddressDTO.getScomplement_adresse()));
				postalAddressDTO.setSno_et_rue(
						SicStringUtils.toUpperCaseWithoutAccentsForFirms(postalAddressDTO.getSno_et_rue()));
				postalAddressDTO
						.setSville(SicStringUtils.toUpperCaseWithoutAccentsForFirms(postalAddressDTO.getSville()));
			}
		}

		// Clean Synonymes
		if (pmDTOToClean.getSynonymes() != null) {
			Iterator<SynonymeDTO> iteratorSynonymeDTO = pmDTOToClean.getSynonymes().iterator();
			while (iteratorSynonymeDTO.hasNext()) {
				SynonymeDTO synonymeDTO = iteratorSynonymeDTO.next();
				synonymeDTO.setNom(SicStringUtils.toUpperCaseWithoutAccentsForFirms(synonymeDTO.getNom()));
			}
		}

		// Clean Parent Name
		if (pmDTOToClean.getParent() != null) {
			pmDTOToClean.getParent()
					.setNom(SicStringUtils.toUpperCaseWithoutAccentsForFirms(pmDTOToClean.getParent().getNom()));
		}
	}

	/**
	 * Generates a PersonneMoraleDTO based on the data and filters given.
	 * 
	 * @param personneMorale
	 * @param azFilter
	 * @param filterAddress
	 * @param filterTelecom
	 * @param filterEmail
	 * @param filterMarketChoice
	 * @param filterContract
	 * @param filterZones
	 * @param scope
	 * 
	 * @return
	 * 
	 * @throws JrafDomainException
	 */
	private PersonneMoraleDTO processReadPersonneMorale(PersonneMorale personneMorale, String azFilter,
			Boolean filterAddress, Boolean filterTelecom, Boolean filterEmail, Boolean filterMarketChoice,
			Boolean filterContract, Boolean filterZones, List<String> scope) throws JrafDomainException {

		// FIXME : C'est complètement FAUX de récupérer toutes les données et
		// d'appliquer le filtre après.
		// Il FAUT récupérer les données en appliquant les filtres en amont !!!

		// Convert to DTO
		PersonneMoraleDTO personneMoraleDTO = PersonneMoraleTransform
				.bo2DtoWithoutUnitializedProxiesCollections(personneMorale);

		if (azFilter != null) {
			azFilter = azFilter.toUpperCase();
		}
		// Par defaut on ne renvoie que les valeurs valides
		if (filterAddress == null) {
			filterAddress = true;
		}
		if (filterTelecom == null) {
			filterTelecom = true;
		}
		if (filterEmail == null) {
			filterEmail = true;
		}
		if (filterMarketChoice == null) {
			filterMarketChoice = true;
		}
		if (filterContract == null) {
			filterContract = true;
		}
		if (filterZones == null) {
			filterZones = true;
		}

		// affectations des liens au DTO
		// inutile car appelé dans bo2Dto
		// PersonneMoraleTransform.bo2DtoLink(personneMorale,
		// personneMoraleDTO);

		// Date actuelle, utilisée pour les filtres
		Date currentDate = new Date();

		// Filtre sur les requested airlines : si au moins une des zc4 de la
		// firme est de type AZ (se termine par AZ), on renvoie la firme
		// sinon on la met à null
		if ("AZ".equals(azFilter)) {
			boolean azFirm = false;
			if (personneMoraleDTO.getPmZones() != null) {
				Iterator<PmZoneDTO> iterator = personneMoraleDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();

					if (pmZoneDTO.getZoneDecoup() != null
							&& pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
						ZoneCommDTO zc = (ZoneCommDTO) pmZoneDTO.getZoneDecoup();
						if (zc.getZc4() != null && "V".equals(zc.getStatut()) && zc.getZc4().endsWith("AZ")) {
							azFirm = true;
							break;
						}
					}
				}
			}
			// La firme de possède pas de ZC possédant un ZC 4 AZ, on ne
			// renvoie rien
			if (!azFirm) {
				return null;
			}
		}

		// Filtre sur les adresses -> seulement les status 'S', 'I' ou 'V'
		// sont gardés :
		if (filterAddress != null && filterAddress) {
			if (personneMoraleDTO.getPostalAddresses() != null) {
				Iterator<PostalAddressDTO> iterator = personneMoraleDTO.getPostalAddresses().iterator();
				while (iterator.hasNext()) {
					PostalAddressDTO postalAddressDTO = iterator.next();
					if (!"S".equals(postalAddressDTO.getSstatut_medium())
							&& !"I".equals(postalAddressDTO.getSstatut_medium())
							&& !"V".equals(postalAddressDTO.getSstatut_medium())) {
						iterator.remove();
					}
				}
			}
		}

		// Filtre sur les emails -> seulement les status 'S', 'I' ou 'V'
		// sont gardés :
		if (filterEmail != null && filterEmail) {
			if (personneMoraleDTO.getEmails() != null) {
				Iterator<EmailDTO> iterator = personneMoraleDTO.getEmails().iterator();
				while (iterator.hasNext()) {
					EmailDTO emailDTO = iterator.next();
					if (!"S".equals(emailDTO.getStatutMedium()) && !"I".equals(emailDTO.getStatutMedium())
							&& !"V".equals(emailDTO.getStatutMedium())) {
						iterator.remove();
					}
				}
			}
		}

		// Filtre sur les telecoms -> seulement les status 'S', 'I' ou 'V'
		// sont gardés :
		if (filterTelecom != null && filterTelecom) {
			if (personneMoraleDTO.getTelecoms() != null) {
				Iterator<TelecomsDTO> iterator = personneMoraleDTO.getTelecoms().iterator();
				while (iterator.hasNext()) {
					TelecomsDTO telecomDTO = iterator.next();
					if (!"S".equals(telecomDTO.getSstatut_medium()) && !"I".equals(telecomDTO.getSstatut_medium())
							&& !"V".equals(telecomDTO.getSstatut_medium())) {
						iterator.remove();
					}
				}
			}
		}

		// Filtre sur les market choices (segmentations) -> seulement ceux
		// avec une date de fin sup�rieure à la date actuelle sont gardés :
		if (filterMarketChoice != null && filterMarketChoice) {
			if (personneMoraleDTO.getSegmentations() != null) {
				Iterator<SegmentationDTO> iterator = personneMoraleDTO.getSegmentations().iterator();
				while (iterator.hasNext()) {
					SegmentationDTO segmentationDTO = iterator.next();
					if (segmentationDTO.getDateSortie() != null
							&& segmentationDTO.getDateSortie().compareTo(currentDate) < 0) {
						iterator.remove();
					}
				}
			}
		}

		// Filtre sur les contrats -> seulement ceux avec une date de fin
		// sup�rieure à la date actuelle sont gardés :
		if (filterContract != null && filterContract) {
			if (personneMoraleDTO.getBusinessRoles() != null) {
				Iterator<BusinessRoleDTO> iterator = personneMoraleDTO.getBusinessRoles().iterator();
				while (iterator.hasNext()) {
					BusinessRoleDTO businessRoleDTO = iterator.next();
					if (businessRoleDTO.getRoleFirme() != null
							&& businessRoleDTO.getRoleFirme().getFinValidite() != null
							&& (businessRoleDTO.getRoleFirme().getFinValidite().compareTo(currentDate) < 0 
									&& !DateUtils.isSameDay(businessRoleDTO.getRoleFirme().getFinValidite(), currentDate))) {
						iterator.remove();
						
					}
				}
			}
		}

		// Filtre sur les zones, selon la valeur du scope
		if (scope != null && !scope.contains("ALL")) {
			if (personneMoraleDTO.getPmZones() != null) {
				boolean zoneCommRequested = (scope.contains("COMMERCIAL_ZONES")) ? true : false;
				boolean zoneFinRequested = (scope.contains("FINANCIAL_ZONES")) ? true : false;
				boolean zoneVenteRequested = (scope.contains("SALES_ZONES")) ? true : false;
				Iterator<PmZoneDTO> iterator = personneMoraleDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					if (!zoneCommRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneFinRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneFinanciereDTO.class)) {
						iterator.remove();
						continue;
					}
					if (!zoneVenteRequested && pmZoneDTO.getZoneDecoup().getClass().equals(ZoneVenteDTO.class)) {
						iterator.remove();
						continue;
					}
				}

			}
		}

		// Filtre sur les zones -> seulement celles avec une date de fin
		// supérieure à la date actuelle sont gardés :
		if (filterZones != null && filterZones) {
			if (personneMoraleDTO.getPmZones() != null) {
				int nbZCpvActives = 0;
				Date dateDebutLienPlusRecente = null;
				Date dateModificationPlusRecente = null;
				Iterator<PmZoneDTO> iterator = personneMoraleDTO.getPmZones().iterator();
				while (iterator.hasNext()) {
					PmZoneDTO pmZoneDTO = iterator.next();
					// Si la ZC n'est plus active ou le lien n'est plus
					// valide on la supprime
					if ((pmZoneDTO.getZoneDecoup() != null && (pmZoneDTO.getZoneDecoup().getDateFermeture() != null
							&& pmZoneDTO.getZoneDecoup().getDateFermeture().compareTo(currentDate) < 0))
							|| ((pmZoneDTO.getDateFermeture() != null
									&& pmZoneDTO.getDateFermeture().compareTo(currentDate) < 0
									&& !DateUtils.isSameDay(pmZoneDTO.getDateFermeture(), currentDate))
									|| pmZoneDTO.getDateOuverture().compareTo(currentDate) > 0)) {
						iterator.remove();
					}

					// Business rules sur les ZC si pls liens non bornés :
					// la ZC privilegiee retournee est celle qui a la date
					// de debut de lien la plus recente
					// on vérifie aussi que la date d'ouverture est dans le passé (pour ne pas sélectionner une ZC future)
					else if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)) {
						if ("O".equals(pmZoneDTO.getLienPrivilegie())
								&& pmZoneDTO.getDateOuverture().compareTo(currentDate) < 0) {
							if (dateDebutLienPlusRecente == null) {
								dateDebutLienPlusRecente = pmZoneDTO.getDateOuverture();
								// si la date d'ouverture de cette pmZone
								// est plus récente on l'assigne
							} else if (pmZoneDTO.getDateOuverture().compareTo(dateDebutLienPlusRecente) > 0) {
								dateDebutLienPlusRecente = pmZoneDTO.getDateOuverture();
							}
							if (dateModificationPlusRecente == null) {
								dateModificationPlusRecente = pmZoneDTO.getDateModif();
								// si la date d'ouverture de cette pmZone
								// est plus récente on l'assigne
							} else if (pmZoneDTO.getDateModif().compareTo(dateModificationPlusRecente) > 0) {
								dateModificationPlusRecente = pmZoneDTO.getDateModif();
							}
							nbZCpvActives++;
						}
					}
				}
				// Si nbZCpvActives>1, il y a pls zc actives privilégiées,
				// on ne remonte que celle qui a la date de lien d'ouverture
				// le plus récent
				if (nbZCpvActives > 1) {
					Iterator<PmZoneDTO> iterator2 = personneMoraleDTO.getPmZones().iterator();
					while (iterator2.hasNext()) {
						PmZoneDTO pmZoneDTO = iterator2.next();
						if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)
								&& "O".equals(pmZoneDTO.getLienPrivilegie())) {
							// On supprime les ZC actives pv qui n'ont pas
							// la date d'ouverture la plus récente
							// mais on garde les autres (future ZC)
							if (pmZoneDTO.getDateOuverture() == null
									|| pmZoneDTO.getDateOuverture().compareTo(dateDebutLienPlusRecente) < 0) {
								iterator2.remove();
								nbZCpvActives--;
							}
						}
					}
				}
				// Si nbZCpvActives>1, il y a encore pls zc actives
				// privilégiées,
				// on ne remonte que celle qui a la date de modification la
				// plus récente
				// mais on fait une exception en cas de ZC future
				if (nbZCpvActives > 1) {
					Iterator<PmZoneDTO> iterator3 = personneMoraleDTO.getPmZones().iterator();
					while (iterator3.hasNext()) {
						PmZoneDTO pmZoneDTO = iterator3.next();
						// On cherche la date de modification la plus
						// récente
						if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)
								&& "O".equals(pmZoneDTO.getLienPrivilegie())
								&& pmZoneDTO.getDateOuverture().compareTo(currentDate) < 0) {
							if (pmZoneDTO.getDateModif() == null
									|| pmZoneDTO.getDateModif().compareTo(dateModificationPlusRecente) != 0) {
								iterator3.remove();
								nbZCpvActives--;
							}
						}
					}
				}
				// Si nbZCpvActives>1, il y a encore pls zc actives
				// privilégiées,
				// on n'en remonte qu'une aléatoire
//				if (nbZCpvActives > 1) {
//					int onlyOneZCPvActive = 0;
//					Iterator<PmZoneDTO> iterator4 = personneMoraleDTO.getPmZones().iterator();
//					while (iterator4.hasNext()) {
//						PmZoneDTO pmZoneDTO = iterator4.next();
//						if (pmZoneDTO.getZoneDecoup().getClass().equals(ZoneCommDTO.class)
//								&& "O".equals(pmZoneDTO.getLienPrivilegie())) {
//							if (onlyOneZCPvActive > 0) {
//								iterator4.remove();
//								nbZCpvActives--;
//							} else {
//								onlyOneZCPvActive++;
//							}
//						}
//					}
//				}
			}
		}

		return personneMoraleDTO;
	}

	/**
	 * Launches a light firm search in order to get main information about the
	 * firm and its category.
	 * 
	 * @param optionType
	 *            GIN, ...
	 * @param optionValue
	 *            number
	 * 
	 * @return null if firm was not found
	 * 
	 * @throws TooManyResultsDomainException
	 * @throws JrafDaoException
	 */
	private PersonneMorale getPMLight(String optionType, String optionValue)
			throws TooManyResultsDomainException, JrafDaoException {

		// Correct input if needed
		if ("".equals(optionType)) {
			optionType = "GIN";
		} else {
			optionType = optionType.toUpperCase();
		}

		// Prepare result
		PersonneMorale personneMoraleLight = null;

		// Launch search
		try {
			personneMoraleLight = personneMoraleRepository.findPMByOptions(optionType, optionValue);
		} catch (TooManyResultsDaoException e) {
			throw new TooManyResultsDomainException(e.getMessage());
		}

		return personneMoraleLight;
	}

	public PersonneMoraleRepository getPersonneMoraleRepository() {
		return personneMoraleRepository;
	}

	public void setPersonneMoraleRepository(PersonneMoraleRepository personneMoraleRepository) {
		this.personneMoraleRepository = personneMoraleRepository;
	}

	public EtablissementRepository getEtablissementRepository() {
		return etablissementRepository;
	}

	public void setEtablissementRepository(EtablissementRepository etablissementRepository) {
		this.etablissementRepository = etablissementRepository;
	}
	
	public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	public GroupeRepository getGroupeRepository() {
		return groupeRepository;
	}

	public void setGroupeRepository(GroupeRepository groupeRepository) {
		this.groupeRepository = groupeRepository;
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager
	 *            EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
