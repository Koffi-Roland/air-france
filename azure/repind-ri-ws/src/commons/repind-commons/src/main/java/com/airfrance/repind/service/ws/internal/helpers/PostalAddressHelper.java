package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidMediumStatusException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MaximumAddressNumberException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ApplicationCodeEnum;
import com.airfrance.ref.type.IndividualStatusEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.Usage_mediumRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.PostalAddressTransform;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressPropertiesDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import com.airfrance.repind.dto.ws.PostalAddressRequestDTO;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Usage_medium;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.exception.AddressNormalizationCustomException;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.internal.unitservice.adresse.DqeUS;
import com.airfrance.repind.service.internal.unitservice.adresse.EDqeErrorDetails;
import com.airfrance.repind.service.internal.unitservice.adresse.PostalAddressUS;
import com.airfrance.repind.service.reference.internal.RefPaysDS;
import com.airfrance.repind.util.PostalAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PostalAddressHelper {

	private static final Log log = LogFactory.getLog(PostalAddressHelper.class);

	@Autowired
	private AdresseDS addressDS;

	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	private PostalAddressRepository postalAddressRepository;
	
	@Autowired
	private Usage_mediumRepository usage_mediumRepository;

	@Autowired
	private PostalAddressDS postalAddressDS;

	@Autowired
	private PostalAddressUS postalAddressUS;

	@Autowired
	private RefPaysDS refPaysDS;

	@Autowired
	private VariablesDS variablesDS;
	
    @Autowired
    protected CommunicationPreferencesHelper communicationPreferencesHelper;

    @Autowired
	private DqeUS dqeUS;

	private final static String ISI_APP_CODE = ApplicationCodeEnum.ISI.getCode();
	private final static String BDC_APP_CODE = ApplicationCodeEnum.BDC.getCode();
	private final static String GP_APP_CODE = ApplicationCodeEnum.GP.getCode();
	private final static String RPD_APP_CODE = ApplicationCodeEnum.RPD.getCode();

	private final static int DEFAULT_MAX_ADR = 5;

	public void upcastPostalAddressField(PostalAddressRequestDTO parDTO) {
		if (parDTO != null && parDTO.getPostalAddressContentDTO() != null) {
			if (parDTO.getPostalAddressContentDTO().getNumberAndStreet() != null) {
				parDTO.getPostalAddressContentDTO().setNumberAndStreet(parDTO.getPostalAddressContentDTO().getNumberAndStreet().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getAdditionalInformation() != null) {
				parDTO.getPostalAddressContentDTO().setAdditionalInformation(parDTO.getPostalAddressContentDTO().getAdditionalInformation().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getZipCode() != null) {
				parDTO.getPostalAddressContentDTO().setZipCode(parDTO.getPostalAddressContentDTO().getZipCode().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getCity() != null) {
				parDTO.getPostalAddressContentDTO().setCity(parDTO.getPostalAddressContentDTO().getCity().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getDistrict() != null) {
				parDTO.getPostalAddressContentDTO().setDistrict(parDTO.getPostalAddressContentDTO().getDistrict().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getStateCode() != null) {
				parDTO.getPostalAddressContentDTO().setStateCode(parDTO.getPostalAddressContentDTO().getStateCode().toUpperCase());
			}
			if (parDTO.getPostalAddressContentDTO().getCountryCode() != null) {
				parDTO.getPostalAddressContentDTO().setCountryCode(parDTO.getPostalAddressContentDTO().getCountryCode().toUpperCase());
			}
		}

		if (parDTO != null && parDTO.getPostalAddressPropertiesDTO() != null) {
			if (parDTO.getPostalAddressPropertiesDTO().getMediumCode() != null) {
				parDTO.getPostalAddressPropertiesDTO().setMediumCode(parDTO.getPostalAddressPropertiesDTO().getMediumCode().toUpperCase());
			}
			if (parDTO.getPostalAddressPropertiesDTO().getMediumStatus() != null) {
				parDTO.getPostalAddressPropertiesDTO().setMediumStatus(parDTO.getPostalAddressPropertiesDTO().getMediumStatus().toUpperCase());
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Boolean createOrUpdate(String gin, List<PostalAddressDTO> postalAddressList, String codeAppliMetier, SignatureDTO signatureAPP) throws JrafDomainException {
		boolean success = true;


		if (postalAddressList != null && !postalAddressList.isEmpty()) {
			List<PostalAddressDTO> adrFromDBList = postalAddressDS.getNotDeletedPostalAddress(gin);

			boolean validAddressStatus = false;

			for (PostalAddressDTO requestDTO : postalAddressList) {
				checkValidity(requestDTO);
				prepareAddress(requestDTO, codeAppliMetier);

				// No active address found in DB ==> create from scratch
				if (adrFromDBList == null || adrFromDBList.isEmpty()) {
					prepareAddressUsage(requestDTO, codeAppliMetier);

					requestDTO.setSgin(gin);
					requestDTO.setIcod_err(0);
					prepareSignature(requestDTO, signatureAPP);
					PostalAddress boToSave = PostalAddressTransform.dto2Bo(requestDTO);

					// Call normalization
					if (YesNoFlagEnum.NO.toString().equalsIgnoreCase(requestDTO.getSforcage())) {
						PostalAddressResponseDTO normalizedAddressResponse = dqeUS.normalizeAddress(boToSave,false,false);
						if (normalizedAddressResponse != null
								&& normalizedAddressResponse.getSoftComputingResponse() != null) {
							normalizedAddressResponse.setPostalAddressProperties(new PostalAddressPropertiesDTO());
							throw new AddressNormalizationCustomException("Error to validate postal address", normalizedAddressResponse);
						}
					}
					if (!UList.isNullOrEmpty(boToSave.getUsage_medium())) {
						for (Usage_medium um : boToSave.getUsage_medium()) {
							usage_mediumRepository.saveAndFlush(um);
						}
					}

					postalAddressRepository.saveAndFlush(boToSave);
					adrFromDBList = postalAddressDS.getNotDeletedPostalAddress(gin);

				}
				else {

					requestDTO.setSgin(gin);
					requestDTO.setIcod_err(0);
					PostalAddress bo = PostalAddressTransform.dto2Bo(requestDTO);

					// Call normalization
					if (YesNoFlagEnum.NO.toString().equalsIgnoreCase(requestDTO.getSforcage())) {
						PostalAddressResponseDTO normalizedAddressResponse = dqeUS.normalizeAddress(bo,false,false);
						if (normalizedAddressResponse != null
								&& normalizedAddressResponse.getSoftComputingResponse() != null) {
							normalizedAddressResponse.setPostalAddressProperties(new PostalAddressPropertiesDTO());
							throw new AddressNormalizationCustomException("Error to validate postal address", normalizedAddressResponse);
						}
					}
					// BO -> DTO
					PostalAddressDTO dtoToSave = PostalAddressTransform.bo2Dto(bo);

					// Compare adr from input with DB adr and apply necessary update
					// Process depend on application code
					// ISI
					if (ISI_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						success = processIsiAdrUpdate(dtoToSave, adrFromDBList, signatureAPP);
					}
					// BDC
					else if (BDC_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						success = processBdcAdrUpdate(dtoToSave, adrFromDBList, signatureAPP);
					}
					else if(GP_APP_CODE.equalsIgnoreCase(codeAppliMetier)){						
						success = processGpAdrUpdate(dtoToSave, adrFromDBList, signatureAPP);
					}
					// Other application code
					else if(RPD_APP_CODE.equalsIgnoreCase(codeAppliMetier) && usageExists(dtoToSave, null, ISI_APP_CODE)) {
						success = processIsiAdrUpdate(dtoToSave, adrFromDBList, signatureAPP);
					}
					else {
						success = processDefaultAdrUpdate(codeAppliMetier, dtoToSave, adrFromDBList, signatureAPP);
					}
				}

				if (MediumStatusEnum.VALID.toString().equalsIgnoreCase(requestDTO.getSstatut_medium())) {
					validAddressStatus = true;
				}

				// Control max number of valid address
				int maxAdrPost = Integer.valueOf(variablesDS.getEnv("MAX_POSTAL_ADDRESS", DEFAULT_MAX_ADR));


				int validAdrPostFromDB = countValidPostalAddress(adrFromDBList);
				boolean maxSizeReached = (validAdrPostFromDB >= maxAdrPost);

				// Analyse address creation to detect overtaking of max postal address
				if (maxSizeReached) {
					adrFromDBList = postalAddressDS.getNotDeletedPostalAddress(gin);
					validAdrPostFromDB = countValidPostalAddress(adrFromDBList);

					if (validAdrPostFromDB > maxAdrPost) {
						throw new MaximumAddressNumberException("Number of valid address found for this individual: " + validAdrPostFromDB);
					}
				}
			}

			if (validAddressStatus) {
				// Update individual Status to 'V' if current status is temporary ('P')
				Individu ind = individuRepository.findBySgin(gin);
				if (ind != null && IndividualStatusEnum.TEMPORARY.toString().equalsIgnoreCase(ind.getStatutIndividu())) {
					ind.setStatutIndividu(MediumStatusEnum.VALID.toString());
					individuRepository.saveAndFlush(ind);
				}
			}
		}
		
		return success;
	}
	
	/**
	 * 
	 * @param dtoToSave
	 * @param adrFromDBList
	 * @param usage
	 * @return True if the country address has changed and the DB updated postal
	 *         address has an ISI mailing usage. False otherwise
	 * @throws MissingParameterException 
	 */
	private boolean mustUpdateMarketLanguage(PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList,
			Usage_mediumDTO usage) throws MissingParameterException {
		boolean mustUpdateLanguage = false;

		// DB Postal address to be updated
		List<PostalAddressDTO> updatedDbPostalAdressList = getUpdatedDbPostalAdressList(dtoToSave, adrFromDBList, usage);

		if (updatedDbPostalAdressList.isEmpty()) {
			// Check if the updated address has an ISI mailing usage
			mustUpdateLanguage = isiMailingUsageOnAdr(dtoToSave);

		} else {
			// Check if the country has changed
			boolean countryChanged = updatedDbPostalAdressList.stream()
					.anyMatch(upa -> !dtoToSave.getScode_pays().equalsIgnoreCase(upa.getScode_pays()));

			if (countryChanged) {
				// Check if the updated address or input address has an ISI mailing usage
				mustUpdateLanguage = updatedDbPostalAdressList.stream().anyMatch(upa -> isiMailingUsageOnAdr(upa))
						|| isiMailingUsageOnAdr(dtoToSave);
			}
		}

		return mustUpdateLanguage;
	}
	
	/**
	 * 
	 * @param dtoToSave
	 * @param adrFromDBList
	 * @param usage
	 * @return DB Postal address to be updated:
	 * <ul>
	 * 		<li>1. Is valid</li>
	 * 		<li>2. Has the same medium code that the  input</li>
	 * 		<li>3. Has the same code application that the input</li>
	 * </ul>
	 */
	private List<PostalAddressDTO> getUpdatedDbPostalAdressList(PostalAddressDTO dtoToSave,
			List<PostalAddressDTO> adrFromDBList, Usage_mediumDTO usage) {
		List<PostalAddressDTO> updatedDbPostalAdressList = new ArrayList<PostalAddressDTO>();

		String mediumCode = dtoToSave.getScode_medium();
		String codeApplication = usage.getScode_application();

		for (PostalAddressDTO adrFromDB : adrFromDBList) {
			if (MediumStatusEnum.VALID.toString().equals(adrFromDB.getSstatut_medium())
					&& mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())
					&& (usageExists(adrFromDB, usage.getInum(), codeApplication)
							|| (!PostalAddressUtil.sameAddress(dtoToSave, adrFromDB)
									&& eligibleForTransfer(adrFromDB, codeApplication)))) {

				updatedDbPostalAdressList.add(adrFromDB);
			}
		}

		return updatedDbPostalAdressList;
	}

	private int countValidPostalAddress(List<PostalAddressDTO> adrFromDBList) {
		int count = 0;
		for (PostalAddressDTO padto : adrFromDBList) {
			if ("V".equalsIgnoreCase(padto.getSstatut_medium())) {
				count++;
			}
		}

		return count;
	}


	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean processDefaultAdrUpdate(String codeAppliMetier, PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		boolean success = true;
		
		if (dtoToSave == null) {
			throw new MissingParameterException("Input address is missing");
		}

		String mediumCode = dtoToSave.getScode_medium();
		String status = dtoToSave.getSstatut_medium();

		if (mediumCode == null || "".equalsIgnoreCase(mediumCode)) {
			throw new MissingParameterException("Missing medium code");
		}

		if (status == null || "".equalsIgnoreCase(status)) {
			throw new MissingParameterException("Missing medium status");
		}

		// Address deletion 
		if (status.equalsIgnoreCase(MediumStatusEnum.REMOVED.toString())) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB) && mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					prepareSignature(adrFromDB, signatureAPP);
					success = postalAddressDS.defaultAddressDeletion(adrFromDB);
				}
			}
		}
		else {
			Set<Usage_mediumDTO> usageToSaveList = new HashSet<Usage_mediumDTO>();
			Set<Usage_mediumDTO> usageFromInput = dtoToSave.getUsage_mediumdto();

			// Get usage set by the user
			Usage_mediumDTO usage = null;
			if (usageFromInput != null) {
				usage = usageFromInput.iterator().next();
				if (usage == null || usage.getScode_application() == null || "".equalsIgnoreCase(usage.getScode_application())) {
					// Init new usage for this application
					setDefaultUsage(codeAppliMetier, dtoToSave, usageToSaveList);
					Set<Usage_mediumDTO> usageUpdated = dtoToSave.getUsage_mediumdto();
					usage = usageUpdated.iterator().next();
				}
				
				if (usage.getInum() == null) {
					usage.setInum(01);
				}
			}
			else {
				setDefaultUsage(codeAppliMetier, dtoToSave, usageToSaveList);
				Set<Usage_mediumDTO> usageUpdated = dtoToSave.getUsage_mediumdto();
				usage = usageUpdated.iterator().next();
			}

			// Check if the country address has changed and the DB updated postal address
			// has an ISI mailing usage
			boolean mustUpdateMarketLanguage = mustUpdateMarketLanguage(dtoToSave, adrFromDBList, usage);

			commonHistoricalAddressCleaning(dtoToSave, usage, adrFromDBList, usageToSaveList, signatureAPP);

			// Control postal address with different medium code
			// Remove them if exist
			controlUsageOnOtherAddress(dtoToSave, mediumCode, adrFromDBList, signatureAPP);

			// Remove duplicates usage
			List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();

			for (Usage_mediumDTO usageLoop: usageToSaveList) {
				if (usageLoop.getScode_application() != null && usageLoop.getScode_application().equalsIgnoreCase(usage.getScode_application())) {
					if (usageLoop.getInum() != null && usageLoop.getInum().equals(usage.getInum())) {
						usageToRemove.add(usageLoop);
					}
				}

			}
			if (!usageToRemove.isEmpty()) {
				for (Usage_mediumDTO delete: usageToRemove) {
					usageToSaveList.remove(delete);
				}
			}

			cleanUsageList(usageToSaveList);
			if (dtoToSave.getUsage_mediumdto() != null) {
				dtoToSave.getUsage_mediumdto().addAll(usageToSaveList);
			}
			else {
				dtoToSave.setUsage_mediumdto(usageToSaveList);
			}
			prepareSignature(dtoToSave, signatureAPP);
			postalAddressDS.create(dtoToSave);

			// Update markets language
			communicationPreferencesHelper.updateMarketLanguage(dtoToSave.getScode_pays(), dtoToSave.getSgin(), signatureAPP, mustUpdateMarketLanguage);
		}
		
		return success;
	}

	private void commonHistoricalAddressCleaning(PostalAddressDTO dtoToSave, Usage_mediumDTO usage, List<PostalAddressDTO> adrFromDBList, Set<Usage_mediumDTO> usageToSaveList, SignatureDTO signatureAPP) throws MissingParameterException, InvalidParameterException {
		if (signatureAPP == null) {
			throw new MissingParameterException("Signature data is missing ");
		}
		
		String mediumCode = dtoToSave.getScode_medium();
		String callingApp = "RPD";
		
		if(StringUtils.isNotEmpty(signatureAPP.getApplicationCode())){
			callingApp = signatureAPP.getApplicationCode();
		}
		
		
		// Get existing address with this usage and update if found
		for (PostalAddressDTO adrFromDB : adrFromDBList) {
			// Same medium code and no usage from user
			if (mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
				// Look for address with same usage
				if (usageExists(adrFromDB, usage.getInum(), usage.getScode_application()) || (!PostalAddressUtil.sameAddress(dtoToSave, adrFromDB) && eligibleForTransfer(adrFromDB, callingApp))) {
					// Historize this address to log update if there is no other BDC usage or GP
					boolean canBeHistorized = true;

					// Check other BDC or GP usage
					if (adrFromDB.getUsage_mediumdto() != null) {
						for (Usage_mediumDTO umdto: adrFromDB.getUsage_mediumdto()) {
							if (BDC_APP_CODE.equalsIgnoreCase(umdto.getScode_application()) && !callingApp.equalsIgnoreCase(BDC_APP_CODE)) {
								canBeHistorized = false;
								break;
							}
							if (GP_APP_CODE.equalsIgnoreCase(umdto.getScode_application()) && !callingApp.equalsIgnoreCase(GP_APP_CODE)) {
								canBeHistorized = false;
								break;
							}
						}
					}

					if (canBeHistorized) {
						if (MediumStatusEnum.HISTORIZED.toString().equalsIgnoreCase(adrFromDB.getSstatut_medium())) {
							computeAddressUpdateByAction(MediumStatusEnum.REMOVED.toString(), adrFromDB, usageToSaveList, signatureAPP);
						}
						else if (!MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(adrFromDB.getSstatut_medium())) {
							// Keep initial creation signature
							if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB)) {
								dtoToSave.setSignature_creation(adrFromDB.getSignature_creation());
								dtoToSave.setSsite_creation(adrFromDB.getSsite_creation());
								dtoToSave.setDdate_creation(adrFromDB.getDdate_creation());
							}
							computeAddressUpdateByAction(MediumStatusEnum.HISTORIZED.toString(), adrFromDB, usageToSaveList, signatureAPP);
						}
					}
					else {
						List<Usage_mediumDTO> usageToKeep = new ArrayList<Usage_mediumDTO>();
						for (Usage_mediumDTO umdto: adrFromDB.getUsage_mediumdto()) {
							if (BDC_APP_CODE.equalsIgnoreCase(umdto.getScode_application()) && !callingApp.equalsIgnoreCase(BDC_APP_CODE)
									|| GP_APP_CODE.equalsIgnoreCase(umdto.getScode_application())  && !callingApp.equalsIgnoreCase(GP_APP_CODE) 
									|| ISI_APP_CODE.equalsIgnoreCase(umdto.getScode_application())  && !callingApp.equalsIgnoreCase(ISI_APP_CODE) && !callingApp.equalsIgnoreCase(RPD_APP_CODE)) {
								usageToKeep.add(umdto);
							}
							else {
								usageToSaveList.add(umdto);
							}
						}
						adrFromDB.getUsage_mediumdto().clear();
						adrFromDB.getUsage_mediumdto().addAll(usageToKeep);
						prepareSignature(adrFromDB, signatureAPP);
						postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(adrFromDB));
					}
				}
				else {
					// same address 
					if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB)) {
						// Historize  this address or delete historized to log update
						if (MediumStatusEnum.HISTORIZED.toString().equalsIgnoreCase(adrFromDB.getSstatut_medium())) {
							computeAddressUpdateByAction(MediumStatusEnum.REMOVED.toString(), adrFromDB, usageToSaveList, signatureAPP);
						}
						else if (!MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(adrFromDB.getSstatut_medium())) {
							dtoToSave.setSignature_creation(adrFromDB.getSignature_creation());
							dtoToSave.setSsite_creation(adrFromDB.getSsite_creation());
							dtoToSave.setDdate_creation(adrFromDB.getDdate_creation());
							computeAddressUpdateByAction(MediumStatusEnum.HISTORIZED.toString(), adrFromDB, usageToSaveList, signatureAPP);
						}
					}
					else {
						if (MediumStatusEnum.HISTORIZED.toString().equalsIgnoreCase(adrFromDB.getSstatut_medium())) {
							computeAddressUpdateByAction(MediumStatusEnum.REMOVED.toString(), adrFromDB, usageToSaveList, signatureAPP);
						}
					}
				}
			}
		}
	}


	private boolean eligibleForTransfer(PostalAddressDTO adrFromDB, String callingApp) {
		boolean eligible = false;
		if (adrFromDB != null && adrFromDB.getUsage_mediumdto() != null && !adrFromDB.getUsage_mediumdto().isEmpty()) {
			if ("RPD".equalsIgnoreCase(callingApp) || ISI_APP_CODE.equalsIgnoreCase(callingApp)) {
				for (Usage_mediumDTO umdto : adrFromDB.getUsage_mediumdto()) {
					if (BDC_APP_CODE.equalsIgnoreCase(umdto.getScode_application()) || GP_APP_CODE.equalsIgnoreCase(umdto.getScode_application())) {
						return eligible;
					}
					else {
						eligible = true;
					}
				}
			}
		}
		else {
			eligible = true;
		}
		
		return eligible;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean processBdcAdrUpdate(PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		boolean success = true;
		
		if (dtoToSave == null) {
			throw new MissingParameterException("Input address is missing");
		}

		String mediumCode = dtoToSave.getScode_medium();
		String status = dtoToSave.getSstatut_medium();
		Set<Usage_mediumDTO> usageFromInput = dtoToSave.getUsage_mediumdto();

		if (mediumCode == null || "".equalsIgnoreCase(mediumCode)) {
			throw new MissingParameterException("Missing medium code");
		}

		if (status == null || "".equalsIgnoreCase(status)) {
			throw new MissingParameterException("Missing medium status");
		}

		// Address deletion 
		if (status.equalsIgnoreCase(MediumStatusEnum.REMOVED.toString())) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB) && mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					prepareSignature(adrFromDB, signatureAPP);
					success = postalAddressDS.addressDeletion(adrFromDB, BDC_APP_CODE);
				}
			}
		}
		else {
			// Creation of BDC usage depends on existing usage in DB
			// By default creation with usage number 01 
			// Replace existing usage with new address on input

			Set<Usage_mediumDTO> usageToSaveList = new HashSet<Usage_mediumDTO>();
			Usage_mediumDTO usage = null;
			if (usageFromInput != null) {
				usage = usageFromInput.iterator().next();
				
				if (usage.getInum() == null) {
					usage.setInum(01);
				}
			}

			if (usage == null) {
				usage = new Usage_mediumDTO();

				usage.setInum(01);
				usage.setScode_application(BDC_APP_CODE);
			}

			// Check if the country address has changed and the DB updated postal address
			// has an ISI mailing usage
			boolean mustUpdateMarketLanguage = mustUpdateMarketLanguage(dtoToSave, adrFromDBList, usage);

			commonHistoricalAddressCleaning(dtoToSave, usage, adrFromDBList, usageToSaveList, signatureAPP);

			// Control usage set on address to create
			if (usageFromInput == null || usageFromInput.isEmpty()) {
				// Init new BDC usage
				setDefaultUsage(BDC_APP_CODE, dtoToSave, usageToSaveList);
			}
			else {
				List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
				// Usage set by the user ==> replace old usage
				for (Usage_mediumDTO usageLoop: usageToSaveList) {
					if (usageLoop.getScode_application() != null && usageLoop.getScode_application().equalsIgnoreCase(usage.getScode_application())) {
						if (usageLoop.getInum() != null && usageLoop.getInum().equals(usage.getInum())) {
							usageToRemove.add(usageLoop);
						}
					}
				}
				if (!usageToRemove.isEmpty()) {
					for (Usage_mediumDTO delete: usageToRemove) {
						usageToSaveList.remove(delete);
					}
				}
			}

			// Control postal address with different medium code
			// Remove them if exist
			controlUsageOnOtherAddress(dtoToSave, mediumCode, adrFromDBList, signatureAPP);

			cleanUsageList(usageToSaveList);
			if (dtoToSave.getUsage_mediumdto() != null) {
				dtoToSave.getUsage_mediumdto().addAll(usageToSaveList);
			}
			else {
				dtoToSave.setUsage_mediumdto(usageToSaveList);
			}
			prepareSignature(dtoToSave, signatureAPP);
			postalAddressDS.create(dtoToSave);

			// Update markets language
			communicationPreferencesHelper.updateMarketLanguage(dtoToSave.getScode_pays(), dtoToSave.getSgin(), signatureAPP, mustUpdateMarketLanguage);
		}

		return success;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean processGpAdrUpdate(PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		boolean success = true;
		
		if (dtoToSave == null) {
			throw new MissingParameterException("Input address is missing");
		}

		String mediumCode = dtoToSave.getScode_medium();
		String status = dtoToSave.getSstatut_medium();
		Set<Usage_mediumDTO> usageFromInput = dtoToSave.getUsage_mediumdto();

		if (mediumCode == null || "".equalsIgnoreCase(mediumCode)) {
			throw new MissingParameterException("Missing medium code");
		}

		if (status == null || "".equalsIgnoreCase(status)) {
			throw new MissingParameterException("Missing medium status");
		}

		// Address deletion 
		if (status.equalsIgnoreCase(MediumStatusEnum.REMOVED.toString())) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB) && mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					prepareSignature(adrFromDB, signatureAPP);
					success = postalAddressDS.addressDeletion(adrFromDB, GP_APP_CODE);
				}
			}
		}
		else {
			// Creation of GP usage depending on existing usage in DB
			// By default creation with usage number 01 
			// Replace existing usage with new address on input

			Set<Usage_mediumDTO> usageToSaveList = new HashSet<>();
			Usage_mediumDTO usage = null;
			if (usageFromInput != null) {
				usage = usageFromInput.iterator().next();
				
				if (usage.getInum() == null) {
					usage.setInum(01);
				}
			}

			if (usage == null) {
				usage = new Usage_mediumDTO();

				usage.setInum(01);
				usage.setScode_application(GP_APP_CODE);
			}

			// Check if the country address has changed and the DB updated postal address
			// has an ISI mailing usage
			boolean mustUpdateMarketLanguage = mustUpdateMarketLanguage(dtoToSave, adrFromDBList, usage);

			commonHistoricalAddressCleaning(dtoToSave, usage, adrFromDBList, usageToSaveList, signatureAPP);

			// Control usage set on address to create
			if (usageFromInput == null || usageFromInput.isEmpty()) {
				// Init new GP usage
				setDefaultUsage(GP_APP_CODE, dtoToSave, usageToSaveList);
			}
			else {
				List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
				// Usage set by the user ==> replace old usage
				for (Usage_mediumDTO usageLoop: usageToSaveList) {
					if (usageLoop.getScode_application() != null && usageLoop.getScode_application().equalsIgnoreCase(usage.getScode_application())) {
						if (usageLoop.getInum() != null && usageLoop.getInum().equals(usage.getInum())) {
							usageToRemove.add(usageLoop);
						}
					}
				}
				if (!usageToRemove.isEmpty()) {
					for (Usage_mediumDTO delete: usageToRemove) {
						usageToSaveList.remove(delete);
					}
				}
			}

			// Control postal address with different medium code
			// Remove them if exist
			controlUsageOnOtherAddress(dtoToSave, mediumCode, adrFromDBList, signatureAPP);

			cleanUsageList(usageToSaveList);
			if (dtoToSave.getUsage_mediumdto() != null) {
				dtoToSave.getUsage_mediumdto().addAll(usageToSaveList);
			}
			else {
				dtoToSave.setUsage_mediumdto(usageToSaveList);
			}
			prepareSignature(dtoToSave, signatureAPP);
			postalAddressDS.create(dtoToSave);

			// Update markets language
			communicationPreferencesHelper.updateMarketLanguage(dtoToSave.getScode_pays(), dtoToSave.getSgin(), signatureAPP, mustUpdateMarketLanguage);
		}
		
		return success;
	}

	private void computeAddressUpdateByAction(String status, PostalAddressDTO adrFromDB, Set<Usage_mediumDTO> usageToSaveList, SignatureDTO signatureAPP) throws MissingParameterException, InvalidParameterException {
		adrFromDB.setSstatut_medium(status);
		prepareSignature(adrFromDB, signatureAPP);
		if (adrFromDB.getUsage_mediumdto() != null) {
			usageToSaveList.addAll(adrFromDB.getUsage_mediumdto());
			adrFromDB.getUsage_mediumdto().clear();
		}
		postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(adrFromDB));
	}

	private boolean processIsiAdrUpdate(PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		Boolean success = true;
		String mediumCode = dtoToSave.getScode_medium();
		String status = dtoToSave.getSstatut_medium();
		Set<Usage_mediumDTO> usageFromInput = dtoToSave.getUsage_mediumdto();

		if (mediumCode == null || "".equalsIgnoreCase(mediumCode)) {
			throw new MissingParameterException("Missing medium code");
		}

		if (status == null || "".equalsIgnoreCase(status)) {
			throw new MissingParameterException("Missing medium status");
		}

		// Address deletion 
		if (status.equalsIgnoreCase(MediumStatusEnum.REMOVED.toString())) {
			boolean isiMailingUsageRemoved = false;
			
			// Remove all usage on matching address except BDC and GP adr
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				if (PostalAddressUtil.sameAddress(dtoToSave, adrFromDB) && mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					prepareSignature(adrFromDB, signatureAPP);
					if (!isiMailingUsageRemoved) {
						isiMailingUsageRemoved = isiMailingUsageOnAdr(adrFromDB);
					}
					success = postalAddressDS.addressDeletion(adrFromDB, ISI_APP_CODE);
				}
			}
			
			if (isiMailingUsageRemoved) {
				PostalAddressDTO isiComplementaryAddress = postalAddressDS.getIsiComplementaryAddress(dtoToSave.getSgin());
				
				if (isiComplementaryAddress != null && isiComplementaryAddress.getUsage_mediumdto() != null && !isiComplementaryAddress.getUsage_mediumdto().isEmpty()) {
					for (Usage_mediumDTO usage : isiComplementaryAddress.getUsage_mediumdto()) {
						if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application()) && "C".equalsIgnoreCase(usage.getSrole1())) {
							usage.setSrole1("M");
							postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(isiComplementaryAddress));
							break;
						}
					}
				}
			}
		}
		else {  
			// Creation or update of ISI adr
			// Loop on adr from database for adr with same medium code
			// Adr with same medium code and Status 'V', 'I', 'T' or 'S' must be historized if different
			// Historized adr must be deleted ('X')
			// Usage on previous address must be transfered to new adr if different adr except for BDC and GP
			// Add or update ISI usage if same adr

			Set<Usage_mediumDTO> usageToSaveList = new HashSet<>();
			Usage_mediumDTO usage = null;
			if (usageFromInput != null) {
				usage = usageFromInput.iterator().next();
				
				if (usage.getInum() == null) {
					usage.setInum(01);
				}
			}
			else {
				usage = setDefaultIsiUsage(dtoToSave, adrFromDBList);
			}

			// Check if the country address has changed and the DB updated postal address
			// has an ISI mailing usage
			boolean mustUpdateMarketLanguage = mustUpdateMarketLanguage(dtoToSave, adrFromDBList, usage);

			commonHistoricalAddressCleaning(dtoToSave, usage, adrFromDBList, usageToSaveList, signatureAPP);

			// Prepare usage address
			if (usage == null) {
				usage = setDefaultIsiUsage(dtoToSave, adrFromDBList);
			}
			else {

				List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
				// Usage set by the user ==> replace old ISI usage
				for (Usage_mediumDTO usageLoop: usageToSaveList) {
					if (ISI_APP_CODE.equalsIgnoreCase(usageLoop.getScode_application())) {
						usageToRemove.add(usageLoop);
					}
				}

				if (!usageToRemove.isEmpty()) {
					for (Usage_mediumDTO delete: usageToRemove) {
						usageToSaveList.remove(delete);
					}
				}
			}

			cleanUsageList(usageToSaveList);
			if (dtoToSave.getUsage_mediumdto() != null) {
				dtoToSave.getUsage_mediumdto().addAll(usageToSaveList);
			}
			else {
				dtoToSave.setUsage_mediumdto(usageToSaveList);
			}

			// Control postal address with different medium code
			// If current adr to save is an ISI mailing, we must change old mailing adr to C
			controlIsiUsageOnAddress(dtoToSave, mediumCode, adrFromDBList, signatureAPP);

			prepareSignature(dtoToSave, signatureAPP);
			postalAddressDS.create(dtoToSave);

			// Update markets language
			communicationPreferencesHelper.updateMarketLanguage(dtoToSave.getScode_pays(), dtoToSave.getSgin(), signatureAPP, mustUpdateMarketLanguage);
		}

		return success;
	}

	// Check on existing postal address ISI usage to define default usage 
	private Usage_mediumDTO setDefaultIsiUsage(PostalAddressDTO dtoToSave, List<PostalAddressDTO> adrFromDBList) {
		Usage_mediumDTO usage = new Usage_mediumDTO();
		usage.setInum(01);
		usage.setScode_application(ISI_APP_CODE);

		if (adrFromDBList != null && !adrFromDBList.isEmpty()) {
			boolean isiMailingOtherCode = false;

			for (PostalAddressDTO padto : adrFromDBList) {
				if (isiMailingUsageOnAdr(padto)) {
					if (!padto.getScode_medium().equalsIgnoreCase(dtoToSave.getScode_medium())) {
						isiMailingOtherCode = true;
						break;
					}
				}
			}

			if (isiMailingOtherCode) {
				usage.setSrole1("C");
			}
			else {
				usage.setSrole1("M");
			}
		}
		else {
			// No adrPost for this customer
			usage.setSrole1("M");
		}

		if (dtoToSave.getUsage_mediumdto() == null || dtoToSave.getUsage_mediumdto().isEmpty()) {
			Set<Usage_mediumDTO> listUsage = new HashSet<>();
			listUsage.add(usage);
			dtoToSave.setUsage_mediumdto(listUsage);
		}

		return usage;
	}


	/*
	 * Set a default usage to a postal address (expect for ISI) if no usage set by user
	 * BDC app is set with no role and with usage number 01
	 * Other application is set with usage nnumber 00 
	 */
	private void setDefaultUsage(String appCode, PostalAddressDTO dtoToSave, Set<Usage_mediumDTO> usageToSaveList) throws JrafDomainException {
		Set<Usage_mediumDTO> newUsageList = new HashSet<>();
		Usage_mediumDTO newusage = new Usage_mediumDTO();
		boolean duplicate = false;

		newusage.setScode_application(appCode);
		if (BDC_APP_CODE.equalsIgnoreCase(appCode) || ISI_APP_CODE.equalsIgnoreCase(appCode)
				|| GP_APP_CODE.equalsIgnoreCase(appCode)) {
			newusage.setInum(01);
		}
		else {
			newusage.setInum(00);
		}

		// Check if above usage already exists in usage list to be saved
		if (usageToSaveList != null && !usageToSaveList.isEmpty()) {
			for (Usage_mediumDTO umdto : usageToSaveList) {
				if (appCode.equalsIgnoreCase(umdto.getScode_application())) {
					if (newusage.getInum().equals(umdto.getInum())) {
						duplicate = true;
					}
				}
			}
		}

		// Avoid duplicate usage on adr
		if (!duplicate) {
			newUsageList.add(newusage);
			dtoToSave.setUsage_mediumdto(newUsageList);
		}
	}

	// REPIND-1546 : CRITICAL SONAR because Transactional could not be private so change to Public or delete Transactional
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void controlIsiUsageOnAddress(PostalAddressDTO dtoToSave, String mediumCode, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		if (isiMailingUsageOnAdr(dtoToSave)) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				// Different medium code
				if (!mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					if (adrFromDB.getUsage_mediumdto() != null) {
						for (Usage_mediumDTO usage : adrFromDB.getUsage_mediumdto()) {
							if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application()) && "M".equalsIgnoreCase(usage.getSrole1())) {
								usage.setSrole1("C");

								usage_mediumRepository.saveAndFlush(Usage_mediumTransform.dto2bo(usage));
							}
						}
					}
				}
			}
		}

		if (isiComplementaryUsageOnAdr(dtoToSave)) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				// Different medium code
				if (!mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					if (adrFromDB.getUsage_mediumdto() != null) {
						for (Usage_mediumDTO usage : adrFromDB.getUsage_mediumdto()) {
							if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application()) && "C".equalsIgnoreCase(usage.getSrole1())) {
								throw new InvalidParameterException("Usage address already exists");
							}
						}
					}
				}
			}
		}
	}

	// REPIND-1546 : CRITICAL SONAR because Transactional could not be private so change to Public or delete Transactional
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void controlUsageOnOtherAddress(PostalAddressDTO dtoToSave, String mediumCode, List<PostalAddressDTO> adrFromDBList, SignatureDTO signatureAPP) throws MissingParameterException, InvalidParameterException {
		String appCode = null;
		Integer usageNum = null;
		String usageRole = null;

		if (dtoToSave.getUsage_mediumdto() != null) {
			Usage_mediumDTO usage = dtoToSave.getUsage_mediumdto().iterator().next();
			appCode = usage.getScode_application();
			usageNum = usage.getInum();
			usageRole = usage.getSrole1();
		}

		// No check for null application code
		if (appCode != null) {
			for (PostalAddressDTO adrFromDB : adrFromDBList) {
				// Different medium code
				if (!mediumCode.equalsIgnoreCase(adrFromDB.getScode_medium())) {
					List<Usage_mediumDTO> usageToRemove = new ArrayList<Usage_mediumDTO>();
					if (adrFromDB.getUsage_mediumdto() != null) {
						for (Usage_mediumDTO usageLoop : adrFromDB.getUsage_mediumdto()) {
							if (sameUsageWithRole(usageLoop, appCode, usageNum, usageRole)) {
								usageToRemove.add(usageLoop);
							}
						}
					}
					if (!usageToRemove.isEmpty()) {
						for (Usage_mediumDTO delete : usageToRemove) {
							adrFromDB.getUsage_mediumdto().remove(delete);
						}
						// Save update on usage role
						prepareSignature(adrFromDB, signatureAPP);
						// TODO remove if empty
						postalAddressRepository.saveAndFlush(PostalAddressTransform.dto2Bo(adrFromDB));
					}
				}
			}
		}
	}
	
	private boolean sameUsageWithRole(Usage_mediumDTO usageLoop, String appCode, Integer usageNum, String usageRole) {
		boolean result = false;
		
		if (appCode.equalsIgnoreCase(usageLoop.getScode_application()) && usageNum != null && usageNum.equals(usageLoop.getInum())) {
			if (StringUtils.isNotEmpty(usageRole)) {
				if(usageRole.equalsIgnoreCase(usageLoop.getSrole1())) {
					result = true;
				}
			}
			else {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * Detect presence of requested usage on postal address 
	 * @param postalAddress Address to check
	 * @param usageNumber
	 * @param applicationCode
	 * @return usage presence
	 */
	protected boolean usageExists(PostalAddressDTO postalAddress, Integer usageNumber, String applicationCode) {
		if (StringUtils.isEmpty(applicationCode)) {
			return false;
		}

		boolean usageExist = false;

		if (postalAddress.getUsage_mediumdto() != null) {
			for (Usage_mediumDTO usage : postalAddress.getUsage_mediumdto()) {
				boolean equal = false;
				if (usage.getScode_application() != null && applicationCode.equalsIgnoreCase(usage.getScode_application())) {
					equal = true;
				}

				if (equal) {
					if (usageNumber == null || usageNumber.equals(usage.getInum())) {
						return true;
					}
				}
			}
		}

		return usageExist;
	}

	// Control mandatory field
	protected void checkValidity(PostalAddressDTO paDTO) throws JrafDomainException {

		if (paDTO == null) {
			log.error("Postal Address is null");
			throw new MissingParameterException("Missing postal address data ");
		}

		// Reject Address if empty
		if (paDTO.getSville() == null && paDTO.getScode_postal() == null && paDTO.getSno_et_rue() == null && paDTO.getScode_pays() == null) {
			log.error("All Postal Address Content fields are null");
			throw new MissingParameterException("Missing postal address data ");
		}

		String countryCode = "";
		if (paDTO.getScode_pays() != null) {
			countryCode = paDTO.getScode_pays();
		}

		// Validate country code
		if (!refPaysDS.codePaysIsValide(countryCode.toUpperCase())) {
			log.error("Country code not found in REF TABLE");
			throw new InvalidParameterException("Invalid Country code ");
		}

		// Reject Historical Address creation
		if (paDTO.getSstatut_medium() != null && MediumStatusEnum.HISTORIZED.toString().equalsIgnoreCase(paDTO.getSstatut_medium())) {
			log.error("Historical address creation rejected ");
			throw new InvalidMediumStatusException("Historical address creation is not allowed ");
		}
		
		// Check fields
		if (paDTO.getScode_province() != null && paDTO.getScode_province().length() > 2) {
			throw new InvalidParameterException("Invalid state code length (Expected : 2, Current : " + paDTO.getScode_province().length() + ")");
		}
	}

	protected void prepareAddress(PostalAddressDTO parDTO, String codeAppliMetier) throws JrafDomainException {
		if (parDTO == null) {
			log.error("Postal Address is null");
			throw new MissingParameterException("Missing postal address data ");
		}
		
		// Check link between calling application and usage on postal address
		if (parDTO.getUsage_mediumdto() != null && !parDTO.getUsage_mediumdto().isEmpty()) {
			for (Usage_mediumDTO umd : parDTO.getUsage_mediumdto()) {
				if (ISI_APP_CODE.equalsIgnoreCase(umd.getScode_application())) {
					// ISI can be managed by RPD or ISI only
					if (!RPD_APP_CODE.equalsIgnoreCase(codeAppliMetier) && !ISI_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						throw new InvalidParameterException(codeAppliMetier + " applicationCode cannot manage ISI postal address");
					}
				}
				else if (BDC_APP_CODE.equalsIgnoreCase(umd.getScode_application())) {
					// BDC can be managed by BDC only
					if (!BDC_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						throw new InvalidParameterException(codeAppliMetier + " applicationCode cannot manage BDC postal address");
					}
				}
				else if (GP_APP_CODE.equalsIgnoreCase(umd.getScode_application())) {
					// BDC can be managed by BDC only
					if (!GP_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						throw new InvalidParameterException(codeAppliMetier + " applicationCode cannot manage GP postal address");
					}
				}
			}
		}

		String countryCode = null;
		String districtCode = null;
		String stateCode = null;

		// ------ Upcast all atributes ------
		if (parDTO.getSno_et_rue() != null) {
			parDTO.setSno_et_rue(parDTO.getSno_et_rue().toUpperCase());
		}
		if (parDTO.getSdescriptif_complementaire() != null) {
			parDTO.setSdescriptif_complementaire(parDTO.getSdescriptif_complementaire().toUpperCase());
		}
		if (parDTO.getSraison_sociale() != null) {
			parDTO.setSraison_sociale(parDTO.getSraison_sociale().toUpperCase());
		}
		if (parDTO.getScode_province() != null) {
			stateCode = parDTO.getScode_province().toUpperCase();
			parDTO.setScode_province(stateCode);
		}
		if (parDTO.getSlocalite() != null) {
			districtCode = parDTO.getSlocalite().toUpperCase();
			parDTO.setSlocalite(districtCode);
		}
		if (parDTO.getScode_postal() != null) {
			parDTO.setScode_postal(parDTO.getScode_postal().toUpperCase());
		}
		if (parDTO.getSville() != null) {
			parDTO.setSville(parDTO.getSville().toUpperCase());
		}
		if (parDTO.getScode_pays() != null) {
			countryCode = parDTO.getScode_pays().toUpperCase();
			parDTO.setScode_pays(countryCode);
		}
		// -------- END ------------

		if (parDTO.getSforcage() == null) {
			parDTO.setSforcage(YesNoFlagEnum.NO.toString());
		}

		if (!addressDS.isPaysNormalisable(countryCode) || "O".equalsIgnoreCase(parDTO.getSforcage()) || "Y".equalsIgnoreCase(parDTO.getSforcage())) {
			if (districtCode != null && !addressDS.isValidDistrictCode(districtCode, countryCode)) {
				parDTO.setSlocalite("");
			}
		}
		
		
	}

	protected void prepareAddressUsage(PostalAddressDTO addressToCheck, String codeAppliMetier) throws MissingParameterException, InvalidParameterException {
		// ------ Usage management -------
		if (addressToCheck != null && PostalAddressUtil.isNotHistorizedStatus(addressToCheck)) {
			Usage_mediumDTO usageToCheck = null;

			if (addressToCheck.getUsage_mediumdto() != null && !addressToCheck.getUsage_mediumdto().isEmpty()) {
				Iterator<Usage_mediumDTO> it = addressToCheck.getUsage_mediumdto().iterator(); 
				usageToCheck = it.next();
			}

			if (usageToCheck != null) {

				usageToCheck.setScode_application(codeAppliMetier);

				if (usageToCheck.getInum() == null) {
					if (ISI_APP_CODE.equalsIgnoreCase(codeAppliMetier) || BDC_APP_CODE.equalsIgnoreCase(codeAppliMetier) || GP_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
						usageToCheck.setInum(01);
					}
					else {
						usageToCheck.setInum(00);
					}
				}
				if (ISI_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
					if (usageToCheck.getSrole1() == null) {
						usageToCheck.setSrole1("M");
					}
				}

			}
			else {   // create new usage 
				Usage_mediumDTO newUsage = new Usage_mediumDTO();
				newUsage.setScode_application(codeAppliMetier);
				if (ISI_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
					newUsage.setInum(01);
					newUsage.setSrole1("M");
				}
				else if (BDC_APP_CODE.equalsIgnoreCase(codeAppliMetier) || GP_APP_CODE.equalsIgnoreCase(codeAppliMetier)) {
					newUsage.setInum(01);
				}
				else {
					newUsage.setInum(00);
				}
				Set<Usage_mediumDTO> usageList = new HashSet<Usage_mediumDTO>();
				usageList.add(newUsage);
				addressToCheck.setUsage_mediumdto(usageList);
			}
		}
	}

	private void prepareSignature(PostalAddressDTO dtoToSave, SignatureDTO signatureAPP) throws MissingParameterException {
		if (signatureAPP == null) {
			throw new MissingParameterException("Signature is null ");
		}

		if (dtoToSave.getSignature_creation() == null) {
			dtoToSave.setSignature_creation(signatureAPP.getSignature());
		}
		if (dtoToSave.getDdate_creation() == null) {
			dtoToSave.setDdate_creation(signatureAPP.getDate());
		}
		if (dtoToSave.getSsite_creation() == null) {
			dtoToSave.setSsite_creation(signatureAPP.getSite());
		}

		dtoToSave.setSsignature_modification(signatureAPP.getSignature());
		dtoToSave.setDdate_modification(signatureAPP.getDate());
		dtoToSave.setSsite_modification(signatureAPP.getSite());
	}

	private void cleanUsageList(Set<Usage_mediumDTO> usageToSaveList) {
		if (usageToSaveList != null) {
			for (Usage_mediumDTO usage : usageToSaveList) {
				usage.setSain_adr(null);
				usage.setSrin(null);
			}
		}
	}


	// Read all usage on postal address and identify a ISI mailing usage on it
	private boolean isiMailingUsageOnAdr(PostalAddressDTO adrFromDB) {
		boolean result = false;

		if (adrFromDB.getUsage_mediumdto() != null) {
			for (Usage_mediumDTO usage : adrFromDB.getUsage_mediumdto()) {
				if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application()) && "M".equalsIgnoreCase(usage.getSrole1())) {
					result = true;
				}
			}
		}

		return result;
	}

	// Read all usage on postal address and identify a ISI complementary usage on it
	private boolean isiComplementaryUsageOnAdr(PostalAddressDTO adrFromDB) {
		boolean result = false;

		if (adrFromDB.getUsage_mediumdto() != null) {
			for (Usage_mediumDTO usage : adrFromDB.getUsage_mediumdto()) {
				if (ISI_APP_CODE.equalsIgnoreCase(usage.getScode_application()) && "C".equalsIgnoreCase(usage.getSrole1())) {
					result = true;
				}
			}
		}

		return result;
	}

	public int mapDqeResponse(PostalAddressResponseDTO dqeResponse) {
		if (dqeResponse == null || dqeResponse.getSoftComputingResponse() == null) {
			return 0;
		}

		int errorNumber = 0;

		try {
			int levelError = EDqeErrorDetails.valueFromLevel(Integer.parseInt(dqeResponse.getSoftComputingResponse().getErrorNumber())).getLevel();

			if (levelError > EDqeErrorDetails.ADDRESS_OK.getLevel() && levelError <= EDqeErrorDetails.BIG_TOWN_PATH_UNKNOWN.getLevel()) {
				errorNumber = 2;
			}
			else if (levelError > EDqeErrorDetails.BIG_TOWN_PATH_UNKNOWN.getLevel()) {
				errorNumber = 3;
			}

		} catch (NumberFormatException e) {
			log.error("Unable to parse errorNumberNormail");
		}

		dqeResponse.getSoftComputingResponse().setErrorNumberNormail(Integer.toString(errorNumber));
		return errorNumber;
	}
}
