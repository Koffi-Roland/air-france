
package com.airfrance.repind.service.adresse.internal;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.Usage_mediumRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressPropertiesDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Usage_medium;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.exception.AddressNormalizationCustomException;
import com.airfrance.repind.service.internal.unitservice.adresse.DqeUS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class PostalAddressDS {

	private static final Log log = LogFactory.getLog(PostalAddressDS.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private Usage_mediumRepository usageMediumRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;

	
	@Autowired
	private DqeUS dqeUS;

	private final static String BDC_APP_CODE = "BDC";
	private final static String GP_APP_CODE = "GP";
	private final static String ISI_APP_CODE = "ISI";
	

	public Integer countWhere(PostalAddressDTO dto) throws JrafDomainException {
		 PostalAddress postalAddress = PostalAddressTransform.dto2BoLight(dto);
		return (int) postalAddressRepository.count(Example.of(postalAddress));
	}

	public void create(PostalAddressDTO postalAddressDTO) throws JrafDomainException {
		// light transformation dto -> bo
		PostalAddress postalAddress = PostalAddressTransform.dto2Bo(postalAddressDTO);
		
		if (!UList.isNullOrEmpty(postalAddress.getUsage_medium())) {
			for (Usage_medium usage : postalAddress.getUsage_medium()) {
				usageMediumRepository.saveAndFlush(usage);
			}
		}
		
		// create in database (call the abstract class)
		postalAddressRepository.saveAndFlush(postalAddress);
		
		// Version update and Id update if needed
		PostalAddressTransform.bo2Dto(postalAddress, postalAddressDTO);
	}
	

	

	public void remove(PostalAddressDTO dto) throws JrafDomainException {
		remove(dto.getSain());
	}

	public void remove(String sain) throws JrafDomainException {
		postalAddressRepository.deleteById(sain);
	}

	public void update(PostalAddressDTO postalAddressDTO) throws JrafDomainException {
		PostalAddress postalAddress = postalAddressRepository.getOne(postalAddressDTO.getSain());
		PostalAddressTransform.dto2BoLight(postalAddressDTO, postalAddress);
	}

	public List<PostalAddressDTO> findByExample(PostalAddressDTO dto) throws JrafDomainException {	
		PostalAddress postalAddress = PostalAddressTransform.dto2BoLight(dto);
		List<PostalAddressDTO> result = new ArrayList<>();
		for(PostalAddress postAdd : postalAddressRepository.findAll(Example.of(postalAddress))) {
			result.add(PostalAddressTransform.bo2DtoLight(postAdd));
		}
		return result;
	}

	public PostalAddressDTO get(PostalAddressDTO dto) throws JrafDomainException {
		return get(dto.getSain());
	}

	public PostalAddressDTO get(String sain) throws JrafDomainException {
		Optional<PostalAddress> postalAddress = postalAddressRepository.findById(sain);
		if (!postalAddress.isPresent()) {
			return null;
		}
		return PostalAddressTransform.bo2DtoLight(postalAddress.get());
	}
	
	public PostalAddressRepository getPostalAddressRepository() {
		return postalAddressRepository;
	}

	public void setPostalAddressRepository(PostalAddressRepository postalAddressRepository) {
		this.postalAddressRepository = postalAddressRepository;
	}

	/**
	 * @return EntityManager
	 */ 
	public EntityManager getEntityManager() {
		/*PROTECTED REGION ID(_bvS3ELA6EeONKbL6LK2pLAgem ) ENABLED START*/
		return entityManager;
		/*PROTECTED REGION END*/
	}

	/**
	 *  @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/*PROTECTED REGION ID(_bvS3ELA6EeONKbL6LK2pLA u m) ENABLED START*/

	public PostalAddressDTO findPostalAddress(String gin, MediumCodeEnum mediumCode, String applicationCode,
			Integer version) throws JrafDomainException {

		PostalAddress postalAddress = null;
		PostalAddressDTO postalAddressDTO = null;

		try {
			postalAddress = postalAddressRepository.findPostalAddressByGinAndMediumCodeAndApplicationCodeAndVersion(gin, mediumCode.toString(), applicationCode, version);
			postalAddressDTO = PostalAddressTransform.bo2Dto(postalAddress);
		} catch (DataAccessException e) {
			throw new JrafDomainException(e);
		}

		return postalAddressDTO;

	}

    @Transactional(readOnly=true)
	public List<PostalAddressDTO> findPostalAddress(String gin) throws JrafDomainException {

		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to find postal address without GIN");
		}

		List<PostalAddress> postalAddressList = postalAddressRepository.findPostalAddress(gin);

		if(postalAddressList==null) {
			return null;
		}

		return PostalAddressTransform.bo2Dto(postalAddressList);
	}

    @Transactional(readOnly=true)
	public List<PostalAddressDTO> getNotDeletedPostalAddress(String gin) throws JrafDomainException {
		if(StringUtils.isEmpty(gin)) {
			log.error("GIN is null");
			throw new IllegalArgumentException("Unable to find postal address without GIN");
		}

		List<PostalAddress> postalAddressList = postalAddressRepository.findNotDeletedPostalAddress(gin);

		if(postalAddressList==null) {
			return null;
		}

		return PostalAddressTransform.bo2Dto(postalAddressList);
	}



	public int getNumberProAddressesByGin(String gin) throws JrafDomainException {
		return postalAddressRepository.getNumberProOrPersoAddressesByGinAndCodeMedium(gin, "P");
	}

	public int getNumberPersoAddressesByGin(String gin) throws JrafDomainException {
		return postalAddressRepository.getNumberProOrPersoAddressesByGinAndCodeMedium(gin, "D");
	}

	public List<PostalAddress> getAddressByGin(String sgin) throws JrafDomainException {
		return postalAddressRepository.findPostalAddress(sgin);
	}

	public List<PostalAddressDTO> getAllAddressesByGin(String sgin) throws JrafDomainException {
		List<PostalAddress> listAddresses = postalAddressRepository.findBySgin(sgin);
		
		if (!UList.isNullOrEmpty(listAddresses)) {
			for (PostalAddress address : listAddresses) {
				List<Usage_medium> listUsageMedium = usageMediumRepository.getAllUsageMediumByAddress(address.getSain());
					Set<Usage_medium> setUsageMedium = new HashSet<Usage_medium>(listUsageMedium);
					address.setUsage_medium(setUsageMedium);
			}
		}
		
		return PostalAddressTransform.bo2Dto(listAddresses);
	}

	
	// Loop on all usages of an address and detect if BDC usage exists on it or GP usage exists on it 
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean defaultAddressDeletion(PostalAddressDTO paFromDB) throws JrafDomainException {
		boolean techUsageFound = false;
		boolean success = true;
		
		if (paFromDB.getUsage_mediumdto() != null) {
			List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
			String scodeApplication = "";
			
			// Check if no USAGE from BDC, GP or ISI
			for (Usage_mediumDTO usage : paFromDB.getUsage_mediumdto()) {
				scodeApplication = usage.getScode_application();
				if (BDC_APP_CODE.equalsIgnoreCase(scodeApplication)) {
					techUsageFound = true;
				}else if(GP_APP_CODE.equalsIgnoreCase(scodeApplication)){
					techUsageFound = true;
				}
//				else if(ISI_APP_CODE.equalsIgnoreCase(scodeApplication)){
//					techUsageFound = true;
//				}
				else {
					usageToRemove.add(usage);
				}
			}
			
			if (!usageToRemove.isEmpty()) {
				for (Usage_mediumDTO delete : usageToRemove) {
					paFromDB.getUsage_mediumdto().remove(delete);
				}
			}
		}
		
		// Manage version
		if (paFromDB.getVersion() == null) {
			paFromDB.setVersion(1);
		}

		// NO BDC and NO GP Usages => Remove address
		if (!techUsageFound) {
			paFromDB.setSstatut_medium(MediumStatusEnum.REMOVED.toString());
		}
		else {
			log.error("Postal address cannot be deleted: Used by another application");
			success = false;
		}
		
		postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(paFromDB));
		
		return success;
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean addressDeletion(PostalAddressDTO adrFromDB, String updatingApp) throws JrafDomainException {
		boolean success= true;
		boolean protectedUsageFound = false;
		
		if (adrFromDB.getUsage_mediumdto() != null) {
			List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
			String scodeApplication = "";
			
			// Check if no USAGE from BDC, GP or ISI
			for (Usage_mediumDTO usage : adrFromDB.getUsage_mediumdto()) {
				scodeApplication = usage.getScode_application();
				if (BDC_APP_CODE.equalsIgnoreCase(scodeApplication) && !BDC_APP_CODE.equalsIgnoreCase(updatingApp)) {
					protectedUsageFound = true;
				} 
				else if (GP_APP_CODE.equalsIgnoreCase(scodeApplication) && !GP_APP_CODE.equalsIgnoreCase(updatingApp)) {
					protectedUsageFound = true;
				}
				else {
					usageToRemove.add(usage);
				}
			}
			
			if (!usageToRemove.isEmpty()) {
				for (Usage_mediumDTO delete : usageToRemove) {
					adrFromDB.getUsage_mediumdto().remove(delete);
				}
			}
		}
		
		// Manage version
		if (adrFromDB.getVersion() == null) {
			adrFromDB.setVersion(1);
		}
		if (!protectedUsageFound) {
			if (adrFromDB.getUsage_mediumdto() != null) {
				adrFromDB.getUsage_mediumdto().clear();
			}

			adrFromDB.setSstatut_medium(MediumStatusEnum.REMOVED.toString());
		}
		else {
			log.error("Cannot delete postal address: usage found for other application");
			success = false;
		}
		postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(adrFromDB));
		
		return success;
	}
	
	public List<PostalAddressDTO> getAllIndividualPostalAddress(String gin) throws JrafDomainException {
		if(StringUtils.isEmpty(gin)) {
			log.error("GIN is null");
			throw new IllegalArgumentException("Unable to find postal address without GIN");
		}

		List<PostalAddress> postalAddressList = postalAddressRepository.findAllPostalAddress(gin);

		if(postalAddressList==null) {
			return null;
		}

		return PostalAddressTransform.bo2Dto(postalAddressList);
	}

    @Transactional(readOnly=true)
	public PostalAddressDTO getIsiComplementaryAddress(String gin) throws JrafDomainException {
		PostalAddressDTO response = null;
		
		List<PostalAddress> adrListFromDB = postalAddressRepository.getIsiComplementaryAddress(gin);
		
		if (adrListFromDB != null && !adrListFromDB.isEmpty()) {
			response = PostalAddressTransform.bo2Dto(adrListFromDB.get(0)); 
		}
		
		return response;
	}

	/**
	 * Creates or update an address and associates it with an Agency
	 *
	 * @param postalAddressDTO the new address to be created
	 * @param agenceBO concerned Agency
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createUpdateAgencyPostalAddress(PostalAddressDTO postalAddressDTO, Agence agenceBO) throws JrafDomainException {
		if (postalAddressDTO != null && agenceBO != null) {
			List<String> medium = Arrays.asList(postalAddressDTO.getScode_medium());
			List<PostalAddressDTO> dbList = findByGinPmCodeMedium(agenceBO.getGin(), medium);

			if (CollectionUtils.isEmpty(dbList)) {
				// No adr in DB
				createPmAddress(postalAddressDTO, agenceBO);
			}
			else {
				for (PostalAddressDTO dbLoop : dbList) {
					if (MediumStatusEnum.VALID.toString().equals(dbLoop.getSstatut_medium())) {
						postalAddressDTO.setSain(dbLoop.getSain());
						postalAddressDTO.setVersion(dbLoop.getVersion());
						postalAddressDTO.setPersonneMorale(dbLoop.getPersonneMorale());
						postalAddressDTO.setDdate_creation(dbLoop.getDdate_creation());
						postalAddressDTO.setSignature_creation(dbLoop.getSignature_creation());
						postalAddressDTO.setSsite_creation(dbLoop.getSsite_creation());
						postalAddressDTO.setIcod_err(dbLoop.getIcod_err());

						PostalAddress adrToSave = PostalAddressTransform.dto2BoPM(postalAddressDTO);
						adrToSave.setPersonneMorale(agenceBO);
						postalAddressRepository.saveAndFlush(adrToSave);
					}
				}
			}
		}
	}

	/**
	 * Find the address for a PersonneMorale for the specified GIN and CodeMedium
	 *
	 * @param gin      the GIN of PersonneMorale
	 * @param codeList list of CodeMedium
	 * @return the list of address found
	 */
	public List<PostalAddressDTO> findByGinPmCodeMedium(@NotNull String gin, @NotNull List<String> codeList) {
		List<PostalAddress> result = postalAddressRepository.findByGinPmCodeMedium(gin, codeList);
		if (result != null && !result.isEmpty()) {
			return PostalAddressTransform.bo2Dto(result);
		}
		return Collections.emptyList();
	}


	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createPmAddress(PostalAddressDTO postalAddressDTO, PersonneMorale pm) throws MissingParameterException {
		if (pm == null || StringUtils.isEmpty(pm.getGin())) {
			throw new MissingParameterException("PM data is missing");
		}

		PostalAddress toBeCreated = PostalAddressTransform.dto2Bo(postalAddressDTO);
		toBeCreated.setPersonneMorale(pm);
		PostalAddress created = postalAddressRepository.saveAndFlush(toBeCreated);
		return created.getSain();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createPmAddressWithNormalization(PostalAddressDTO postalAddressDTO, PersonneMorale pm) throws JrafDomainRollbackException {
		if (pm == null || StringUtils.isEmpty(pm.getGin())) {
			throw new MissingParameterException("PM data is missing");
		}

		if (postalAddressDTO.getDdate_creation() == null) {
			postalAddressDTO.setDdate_creation(pm.getDateCreation());
		}
		if (StringUtils.isBlank(postalAddressDTO.getSignature_creation())) {
			postalAddressDTO.setSignature_creation(pm.getSignatureCreation());
		}
		if (StringUtils.isBlank(postalAddressDTO.getSsite_creation())) {
			if (StringUtils.isBlank(postalAddressDTO.getSsite_modification())) {
				postalAddressDTO.setSsite_creation(pm.getSiteCreation());
			} else {
				postalAddressDTO.setSsite_creation(postalAddressDTO.getSsite_modification());
			}
		}
		postalAddressDTO.setIcod_err(0);
		PostalAddress toBeCreated = PostalAddressTransform.dto2Bo(postalAddressDTO);
		toBeCreated.setPersonneMorale(pm);

		if (YesNoFlagEnum.NO.toString().equalsIgnoreCase(postalAddressDTO.getSforcage())) {
			PostalAddressResponseDTO normalizedAddressResponse = dqeUS.normalizeAddress(toBeCreated, false, false);
			if (normalizedAddressResponse != null
					&& normalizedAddressResponse.getSoftComputingResponse() != null) {
				normalizedAddressResponse.setPostalAddressProperties(new PostalAddressPropertiesDTO());
				String errorCode = normalizedAddressResponse.getSoftComputingResponse().getErrorNumber();
				throw new AddressNormalizationCustomException("Unable to validate postal address with error " + errorCode, normalizedAddressResponse);
			}
		}

		PostalAddress created = postalAddressRepository.saveAndFlush(toBeCreated);
		return created.getSain();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createUpdateDeleteWithNormalization(PostalAddressDTO postalAddressDTO, PersonneMorale persMoraleBO) throws JrafDomainException {
		if (postalAddressDTO != null && persMoraleBO != null) {
			List<String> medium = Arrays.asList(postalAddressDTO.getScode_medium());
			List<PostalAddressDTO> dbList = findByGinPmCodeMedium(persMoraleBO.getGin(), medium);
			if (postalAddressDTO.getSain()!= null && postalAddressRepository.findById(postalAddressDTO.getSain()).isPresent()){
				PostalAddressDTO existingAdressDT0= PostalAddressTransform.bo2Dto(postalAddressRepository.findById(postalAddressDTO.getSain()).get());
				dbList = Arrays.asList(existingAdressDT0);
			}


			if (StringUtils.isBlank(postalAddressDTO.getSain())) {
				// Create
				createPmAddressWithNormalization(postalAddressDTO, persMoraleBO);
			}
			else {
				// Update or Delete (X medium status)
				for (PostalAddressDTO dbLoop : dbList) {
					if (dbLoop.getSain().equals(postalAddressDTO.getSain())) {
						// Copy important data from DB
						postalAddressDTO.setSain(dbLoop.getSain());
						postalAddressDTO.setVersion(dbLoop.getVersion());
						postalAddressDTO.setPersonneMorale(dbLoop.getPersonneMorale());
						postalAddressDTO.setDdate_creation(dbLoop.getDdate_creation());
						postalAddressDTO.setSignature_creation(dbLoop.getSignature_creation());
						postalAddressDTO.setSsite_creation(dbLoop.getSsite_creation());
						postalAddressDTO.setIcod_err(dbLoop.getIcod_err());

						// Copy complementary data from DB
						postalAddressDTO.setSlocalite(dbLoop.getSlocalite());
						postalAddressDTO.setSraison_sociale(dbLoop.getSraison_sociale());
						postalAddressDTO.setSdescriptif_complementaire(dbLoop.getSdescriptif_complementaire());
						postalAddressDTO.setSindadr(dbLoop.getSindadr());
						postalAddressDTO.setIcod_warning(dbLoop.getIcod_warning());
						postalAddressDTO.setIcle_role(dbLoop.getIcle_role());
						postalAddressDTO.setIkey_temp(dbLoop.getIkey_temp());
						postalAddressDTO.setScod_err_simple(dbLoop.getScod_err_simple());
						postalAddressDTO.setScod_err_detaille(dbLoop.getScod_err_detaille());
						postalAddressDTO.setStype_invalidite(dbLoop.getStype_invalidite());
						postalAddressDTO.setSenvoi_postal(dbLoop.getSenvoi_postal());
						postalAddressDTO.setDenvoi_postal(dbLoop.getDenvoi_postal());
						postalAddressDTO.setScod_app_send(dbLoop.getScod_app_send());

						PostalAddress adrToSave = PostalAddressTransform.dto2BoPM(postalAddressDTO);
						adrToSave.setPersonneMorale(persMoraleBO);
						adrToSave.setScode_medium(postalAddressDTO.getScode_medium());

						if (YesNoFlagEnum.NO.toString().equalsIgnoreCase(postalAddressDTO.getSforcage())) {
							PostalAddressResponseDTO normalizedAddressResponse = dqeUS.normalizeAddress(adrToSave, false, false);
							if (normalizedAddressResponse != null
									&& normalizedAddressResponse.getSoftComputingResponse() != null) {
								normalizedAddressResponse.setPostalAddressProperties(new PostalAddressPropertiesDTO());
								String errorCode = normalizedAddressResponse.getSoftComputingResponse().getErrorNumber();
								throw new AddressNormalizationCustomException("Unable to validate postal address with error " + errorCode, normalizedAddressResponse);
							}
						}
						postalAddressRepository.saveAndFlush(adrToSave);
					}
				}
			}
		}
	}
}
