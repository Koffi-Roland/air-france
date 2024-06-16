package com.afklm.rigui.services.adresse.internal;

import com.afklm.rigui.enums.SiteEnum;
import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.IsNormalizedEnum;
import com.afklm.rigui.enums.MediumCodeEnum;
import com.afklm.rigui.enums.MediumStatusEnum;
import com.afklm.rigui.enums.TerminalTypeEnum;
import com.afklm.rigui.dao.adresse.TelecomsRepository;
import com.afklm.rigui.dto.adresse.TelecomsDTO;
import com.afklm.rigui.dto.adresse.TelecomsTransform;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.IndividuTransform;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.dto.telecom.NormalizePhoneNumberDTO;
import com.afklm.rigui.entity.adresse.Telecoms;
import com.afklm.rigui.services.individu.internal.IndividuDS;
import com.afklm.rigui.services.telecom.internal.NormalizePhoneNumberDS;
import com.afklm.rigui.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class TelecomDS {

	private static final String SIGNATURE_INVALID_NORM = "StockInvNorm";

	private static final String VIDE = "";

	private static final String ESPACE = " ";

	private static final int NUMERO_MAX_SIZE = 15;

	/** logger */
	private static final Log log = LogFactory.getLog(TelecomDS.class);

	private static final String COUNTRY_CODE_RE = "262";

	@Autowired
	protected TelecomsRepository telecomsRepository;

	/* PROTECTED REGION ID(_mg1rMGyPEeK1MNJVk84-BQ u var) ENABLED START */

	@Autowired
	protected NormalizePhoneNumberDS normalizePhoneNumberDS;

	@Autowired
	protected IndividuDS individuDS;


	private final int MAX_NB_FIXED_PHONE = 1;
	private final int MAX_NB_MOBILE_PHONE = 1;
	private final int MAX_NB_FAX = 1;

	private final String _REF_M = "M";
	private final String _REF_F = "F";
	private final String _REF_L = "L";
	private final String _REF_P = "P";
	private final String _REF_G = "G";
	private final String _REF_V = "V";
	private final String _REF_I = "I";
	private final String _REF_S = "S";

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(TelecomsDTO telecomsDTO) throws JrafDomainException {
		Telecoms telecoms = TelecomsTransform.dto2BoLight(telecomsDTO);
		telecomsRepository.saveAndFlush(telecoms);
		TelecomsTransform.bo2DtoLight(telecoms, telecomsDTO);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(TelecomsDTO dto) throws JrafDomainException {
		remove(dto.getSain());
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(String sain) throws JrafDomainException {
		telecomsRepository.deleteById(sain);
	}

    @Transactional(readOnly=true)
	public void update(TelecomsDTO telecomsDTO) throws JrafDomainException {
		Telecoms telecoms = telecomsRepository.findById(telecomsDTO.getSain()).get();

		// Case Telecom with version field empty
		if (telecoms.getVersion() == null) {
			telecoms.setVersion(1);
		}



		// Update version
		if (telecomsDTO.getVersion() != null) {
			telecomsDTO.nextVersion();
		}
		else {
			telecomsDTO.setVersion(telecoms.getVersion());
		}
		
		// transformation light dto -> bo
		TelecomsTransform.dto2BoLight(telecomsDTO, telecoms);
	}

    @Transactional(readOnly=true)
	public List<TelecomsDTO> findByExample(TelecomsDTO dto) throws JrafDomainException {
		Telecoms telecoms = TelecomsTransform.dto2BoLight(dto);
		List<TelecomsDTO> results = new ArrayList<>();
		for(Telecoms found : telecomsRepository.findAll(Example.of(telecoms))) {
			results.add(TelecomsTransform.bo2DtoLight(found));
		}
		return results;
	}

    @Transactional(readOnly=true)
	public TelecomsDTO get(TelecomsDTO dto) throws JrafDomainException {
		return get(dto.getSain());
	}

    @Transactional(readOnly=true)
	public TelecomsDTO get(String sain) throws JrafDomainException {
		Optional<Telecoms> telecoms = telecomsRepository.findById(sain);
		if (!telecoms.isPresent()) {
			return null;
		}
		return TelecomsTransform.bo2DtoLight(telecoms.get());
	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>0..1 mobile phone</li>
	 * <li>0..1 fixed-line phone</li>
	 * <li>status VALID or INVALID</li>
	 * <li>priority to VALID status</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
    @Transactional(readOnly=true)
	public List<TelecomsDTO> findLatest(String gin) throws JrafDomainException {

		List<TelecomsDTO> telecomsDTOList = new ArrayList<TelecomsDTO>();

		// get latest fixed-line phone
		TelecomsDTO fixedLine = findLatest(gin, TerminalTypeEnum.FIX);

		// get latest mobile phone
		TelecomsDTO mobile = findLatest(gin, TerminalTypeEnum.MOBILE);

		// add fixed-line phone to result
		if (fixedLine != null) {
			telecomsDTOList.add(fixedLine);
		}

		// add mobile-line phone to result
		if (mobile != null) {
			telecomsDTOList.add(mobile);
		}

		return telecomsDTOList;
	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>same terminal type</li>
	 * <li>status VALID or INVALID</li>
	 * <li>priority to VALID status</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
    @Transactional(readOnly=true)
	public TelecomsDTO findLatest(String gin, TerminalTypeEnum terminalType) throws JrafDomainException {

		TelecomsDTO telecomsDTO = null;

		try {
			Telecoms telecom = telecomsRepository.findLatest(gin, terminalType.toString());
			telecomsDTO = TelecomsTransform.bo2Dto(telecom);

			// if normalized telecom -> compute iso country code
			if (telecomsDTO != null && telecomsDTO.isNormalized()) {
				telecomsDTO = computeIsoCountryCode(telecomsDTO);
			}

		} catch (JrafDaoException e) {
			log.error(e);
			throw new JrafDomainException("Unable to find latest telecom referenced by the following gin: " + gin);
		}
		return telecomsDTO;
	}

    @Transactional(readOnly=true)
	public TelecomsDTO findLatestByUsageCode(String gin, MediumCodeEnum mediumCode, TerminalTypeEnum terminalType)
			throws JrafDomainException {

		TelecomsDTO telecomsDTO = null;

		try {
			Telecoms telecom = telecomsRepository.findLatestByUsageCode(gin, mediumCode.toString(), terminalType.toString());
			telecomsDTO = TelecomsTransform.bo2Dto(telecom);

			// if normalized telecom -> compute iso country code
			if (telecomsDTO != null && telecomsDTO.isNormalized()) {
				telecomsDTO = computeIsoCountryCode(telecomsDTO);
			}

		} catch (JrafDaoException e) {
			log.error(e);
			throw new JrafDomainException("Unable to find latest telecom referenced by the following gin: " + gin);
		}
		return telecomsDTO;
	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>valid status</li>
	 * <li>same terminal type</li>
	 * <li>same country code</li>
	 * <li>same phone number</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
    @Transactional(readOnly=true)
	public TelecomsDTO findLatest(TelecomsDTO telecomDTO) throws JrafDomainException {

		TelecomsDTO telecomsDTO = null;

		try {
			Telecoms telecom = TelecomsTransform.dto2BoLight(telecomDTO);
			telecom = telecomsRepository.findLatest(telecom.getSgin(), telecom.getSterminal().toString(), 
					telecom.getSnorm_inter_country_code(), telecom.getSnorm_nat_phone_number());
			telecomsDTO = TelecomsTransform.bo2Dto(telecom);
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to find latest telecom referenced by the following gin: " + telecomDTO.getSgin());
		}

		return telecomsDTO;

	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>no constraint on status</li>
	 * <li>same usage code</li>
	 * <li>same terminal type</li>
	 * <li>same country code</li>
	 * <li>same phone number (non-normalized)</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
    @Transactional(readOnly=true)
	public TelecomsDTO findLatestByUsageCodeValidOrNot(TelecomsDTO telecomDTO) throws JrafDomainException {

		TelecomsDTO telecomsDTO = null;

		try {
			Telecoms telecom = TelecomsTransform.dto2BoLight(telecomDTO);
			telecom = telecomsRepository.findLatestByUsageCodeValidOrNot(telecom.getSgin(), telecom.getScode_medium().toString(),
					telecom.getSterminal().toString(), telecom.getSnumero());
			telecomsDTO = TelecomsTransform.bo2Dto(telecom);
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to find latest telecom referenced by the following gin: " + telecomDTO.getSgin());
		}

		return telecomsDTO;

	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>valid status</li>
	 * <li>same usage code</li>
	 * <li>same terminal type</li>
	 * <li>same country code</li>
	 * <li>same phone number</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
    @Transactional(readOnly=true)
	public TelecomsDTO findLatestByUsageCode(TelecomsDTO telecomDTO) throws JrafDomainException {

		TelecomsDTO telecomsDTO = null;

		try {
			Telecoms telecom = TelecomsTransform.dto2BoLight(telecomDTO);
			telecom = telecomsRepository.findLatestByUsageCode(telecom.getSgin(), telecom.getSterminal(), telecom.getScode_medium(), telecom.getSnumero());
			telecomsDTO = TelecomsTransform.bo2Dto(telecom);
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to find latest telecom referenced by the following gin: " + telecomDTO.getSgin());
		}

		return telecomsDTO;

	}

	/**
	 * Remove all telecoms (status='X') but provided telecom according following
	 * criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>VALID/INVALID status</li>
	 * <li>same terminal type</li>
	 * </ul>
	 * 
	 * @param telecomDTO
	 * @param signatureDTO
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void removeAllButThis(TelecomsDTO telecomDTO, SignatureDTO signatureDTO) throws JrafDomainException {

		try {
			telecomDTO.prepareForUpdate(signatureDTO, telecomDTO);
			Telecoms telecom = TelecomsTransform.dto2BoLight(telecomDTO);
			telecomsRepository.removeAllButThis(telecom.getSgin(), telecom.getSterminal().toString(), telecom.getSain(),
					telecom.getSsignature_modification(), telecom.getSsite_modification(), new Date());
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to remove all telecoms for the following gin: " + telecomDTO.getSgin());
		}
	}

	/**
	 * Remove all telecoms (status='X') but provided telecom according following
	 * criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>VALID/INVALID status</li>
	 * <li>same terminal type</li>
	 * <li>same usage code</li>
	 * </ul>
	 * 
	 * @param telecomDTO
	 * @param signatureDTO
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void removeAllButThisByUsageCode(TelecomsDTO telecomDTO, SignatureDTO signatureDTO)
			throws JrafDomainException {

		try {
			telecomDTO.prepareForUpdateWithUsageCode(signatureDTO, telecomDTO);
			Telecoms telecom = TelecomsTransform.dto2BoLight(telecomDTO);
			telecomsRepository.removeAllButThisByUsageCode(telecom.getSgin(), telecom.getSterminal().toString(), telecom.getSain(),
					telecom.getScode_medium().toString(), telecom.getSsignature_modification(), telecom.getSsite_modification(), new Date());
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to remove all telecoms for the following gin: " + telecomDTO.getSgin());
		}

	}

	/**
	 * Create telecom
	 * 
	 * @param telecomDTO
	 * @param gin
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createTelecom(TelecomsDTO telecomDTO) throws JrafDomainException {

		IndividuDTO individuDTO = individuDS.getByGin(telecomDTO.getSgin());
		if (individuDTO == null) {
			throw new JrafDomainException("Unable to get individual referenced by the following GIN : " + telecomDTO.getSgin());
		}
		
		telecomDTO.setIndividudto(individuDTO);

		createTelecomOnly(telecomDTO);
	}

	/**
	 * Create telecom only, with association to a gin and verification to
	 * existing gin
	 * 
	 * @param telecomDTO
	 * @param gin
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createTelecomOnly(TelecomsDTO telecomDTO) throws JrafDomainException {

		try {
			Telecoms telecom = TelecomsTransform.dto2Bo(telecomDTO);
			telecom = telecomsRepository.saveAndFlush(telecom);
			TelecomsTransform.bo2DtoLight(telecom, telecomDTO);
		} catch (JrafDaoException e) {
			log.error(e);
		}
	}
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateNormalizedTelecom(String gin, List<TelecomsDTO> telecomDTOFromWSList, SignatureDTO signatureAPP,
			SignatureDTO signatureWS) throws JrafDomainException {

		// Nothing to do
		if (telecomDTOFromWSList == null || telecomDTOFromWSList.isEmpty()) {
			return;
		}

		// Signature is mandatory
		if (signatureAPP == null) {
			throw new InvalidParameterException("Signature data is mandatory");
		}

		// loop on telecoms provided in input
		for (TelecomsDTO telecomDTOFromWS : telecomDTOFromWSList) {

			// set default values as medium/status code
			telecomDTOFromWS.setDefaultValues();

			// set gin
			telecomDTOFromWS.setSgin(gin);

			// find latest matching VALID telecom in DB
			TelecomsDTO foundTelecomFromDB = findLatest(telecomDTOFromWS);

			// no telecom found in DB -> this is a creation
			if (foundTelecomFromDB == null) {

				// error : only VALID status allowed for creation
				if (!MediumStatusEnum.VALID.toString().equals(telecomDTOFromWS.getSstatut_medium())) {
					throw new InvalidParameterException("Only VALID status is allowed for telecom creation");
				}

				// set mandatory values for creation
				telecomDTOFromWS.prepareForCreation(signatureAPP);

				// create telecom
				createTelecom(telecomDTOFromWS);

				// remove other telecoms with same terminal
				removeAllButThis(telecomDTOFromWS, signatureWS);

			}
			// a VALID telecom was found in DB -> this is an update
			else {

				// - update is restricted to VALID telecoms
				// - the only thing that an update can do is to change the
				// telecom status
				// -> a telecom with a VALID status in input = nothing to do
				if (MediumStatusEnum.VALID.toString().equals(telecomDTOFromWS.getSstatut_medium())) {
					continue;
				}

				// set mandatory values for update
				foundTelecomFromDB.prepareForUpdate(signatureAPP, telecomDTOFromWS);

				// update telecom
				update(foundTelecomFromDB);

			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateNormalizedTelecomWithUsageCode(String gin, List<TelecomsDTO> telecomDTOFromWSList,
			SignatureDTO signatureAPP, SignatureDTO signatureWS) throws JrafDomainException {

		// Nothing to do
		if (telecomDTOFromWSList == null || telecomDTOFromWSList.isEmpty()) {
			return;
		}

		// Signature is mandatory
		if (signatureAPP == null) {
			throw new InvalidParameterException("Signature data is mandatory");
		}

		// loop on telecoms provided in input
		for (TelecomsDTO telecomDTOFromWS : telecomDTOFromWSList) {

			// set default values as status code. Medium code is mandatory (v6)
			if (StringUtils.isEmpty(telecomDTOFromWS.getSstatut_medium())) {
				telecomDTOFromWS.setSstatut_medium(MediumStatusEnum.VALID.toString());
			}

			// set gin
			telecomDTOFromWS.setSgin(gin);

			// REPIND-643 if this is not a deletion, find latest matching VALID
			// telecom in DB
			TelecomsDTO foundTelecomFromDB;
			if (MediumStatusEnum.REMOVED.toString().equals(telecomDTOFromWS.getSstatut_medium())) {
				foundTelecomFromDB = findLatestByUsageCodeValidOrNot(telecomDTOFromWS);
				if (foundTelecomFromDB == null) {
					foundTelecomFromDB = findTelecomToDelete(telecomDTOFromWS);
				}
			} else {
				foundTelecomFromDB = findLatestByUsageCode(telecomDTOFromWS);
			}

			// no telecom found in DB -> this is a creation
			if (foundTelecomFromDB == null) {

				// error : only VALID status allowed for creation
				if (!MediumStatusEnum.VALID.toString().equals(telecomDTOFromWS.getSstatut_medium())) {
					throw new InvalidParameterException("Telecom to update not found");
				}

				// set mandatory values for creation
				telecomDTOFromWS.prepareForCreation(signatureAPP);

				// create telecom
				createTelecom(telecomDTOFromWS);

				// remove other telecoms with same terminal and same usage code
				removeAllButThisByUsageCode(telecomDTOFromWS, signatureWS);

			}
			// a VALID telecom was found in DB -> this is an update
			else {

				// - update is restricted to VALID telecoms
				// - the only thing that an update can do is to change the
				// telecom status
				// -> a telecom with a VALID status in input = nothing to do
				if (MediumStatusEnum.VALID.toString().equals(telecomDTOFromWS.getSstatut_medium())) {
					continue;
				}

				// set mandatory values for update
				foundTelecomFromDB.prepareForUpdateWithUsageCode(signatureAPP, telecomDTOFromWS);

				// update telecom
				update(foundTelecomFromDB);

			}
		}
	}

    @Transactional(readOnly=true)
	public TelecomsDTO findTelecomToDelete(TelecomsDTO telecomDTOFromWS) throws JrafDomainException {
		TelecomsDTO telecomsDTO = null;

		try {
			String gin = telecomDTOFromWS.getSgin();
			List<Telecoms> resultList = telecomsRepository.findTelecomsToRemoveByGIN(gin);
			
			if (resultList != null && !resultList.isEmpty()) {
				String countryCode = telecomDTOFromWS.getCountryCode();
				String number = telecomDTOFromWS.getSnumero();
				String medium = telecomDTOFromWS.getScode_medium();
				String terminal = telecomDTOFromWS.getSterminal();
				
				for (Telecoms result : resultList) {
					// Compare medium code
					if (!medium.equalsIgnoreCase(result.getScode_medium())) {
						continue;   // different medium code
					}
					// Compare terminal
					if (!terminal.equalsIgnoreCase(result.getSterminal())) {
						continue;   // different terminal
					}
					// Compare country code
					if (StringUtils.isNotEmpty(countryCode)) {
						if (StringUtils.isNotEmpty(result.getSnorm_inter_country_code())) {
							if (!countryCode.equalsIgnoreCase(result.getSnorm_inter_country_code())) {
								continue;   // different country code
							}
						}
						else if (StringUtils.isNotEmpty(result.getSindicatif())) {
							if (!countryCode.equalsIgnoreCase(result.getSindicatif())) {
								continue;    // different country code
							}
							
						}
					}
					// Compare number with normalized number
					if (StringUtils.isNotEmpty(result.getSnorm_nat_phone_number_clean())) {
						if (number.equalsIgnoreCase(result.getSnorm_nat_phone_number_clean())) {
							// Same telecom
							telecomsDTO = TelecomsTransform.bo2Dto(result);
							break;
						}
					}
					else if (StringUtils.isNotEmpty(result.getSnumero())) {
						if (number.equalsIgnoreCase(result.getSnumero())) {
							// Same telecom
							telecomsDTO = TelecomsTransform.bo2Dto(result);
							break;
						}
						else if (StringUtils.isNotEmpty(result.getScode_region())) {
							String completeNumberFromDB = result.getScode_region() + result.getSnumero();
							if (number.equalsIgnoreCase(completeNumberFromDB)) {
								// Same telecom
								telecomsDTO = TelecomsTransform.bo2Dto(result);
								break;
							}
						}
					}
				}
			}
		} catch (JrafDomainException e) {
			log.error(e);
			throw new JrafDomainException(
					"Unable to find latest telecom referenced by the following gin: " + telecomDTOFromWS.getSgin());
		}

		return telecomsDTO;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public TelecomsDTO normalizePhoneNumber(TelecomsDTO telecomsDTO, boolean bForce) throws JrafDomainException {

		if (telecomsDTO == null) {
			return null;
		}
		
		// REPIND-1661 : Do not normalize a telecom to be deleted
		if (StringUtils.isNotEmpty(telecomsDTO.getSstatut_medium()) && MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(telecomsDTO.getSstatut_medium())) {
			return telecomsDTO;
		}

		String countryCode = telecomsDTO.getCountryCode();
		String phoneNumber = telecomsDTO.getSnumero();

		NormalizePhoneNumberDTO normalizePhoneNumberDTO = normalizePhoneNumberDS.normalizePhoneNumber(countryCode,
				phoneNumber, bForce);
		TelecomsTransform.transform(telecomsDTO, normalizePhoneNumberDTO);

		return telecomsDTO;
	}
	
	public TelecomsDTO computeIsoCountryCode(TelecomsDTO normalizedTelecom) throws JrafDomainException {

		// nothing to do
		if (normalizedTelecom == null) {
			return null;
		}

		// get params
		String countryCode = normalizedTelecom.getSnorm_inter_country_code();
		String phoneNumber = normalizedTelecom.getSnorm_nat_phone_number_clean();
		String isoCountryCode = "";

		try {
			// compute iso country code
			isoCountryCode = normalizePhoneNumberDS.computeIsoCountryCode(countryCode, phoneNumber);
		} catch (JrafDomainException e) {
			// Cannot normalize, return null, as if the telecom did't existed
			log.error(e);
			return null;
		}

		// workaround (see REPIND-218)
		if (countryCode != null && countryCode.equals(COUNTRY_CODE_RE)) {
			isoCountryCode = "RE";
		}

		// set iso country code
		normalizedTelecom.setIsoCountryCode(isoCountryCode);

		return normalizedTelecom;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)

	public void invalidateTelecoms(TelecomsDTO telecomsDTO) throws JrafDomainException {
		telecomsDTO.setSstatut_medium(MediumStatusEnum.INVALID.toString());

		update(telecomsDTO);
	}

	// public pour pouvoir �tre appel� depuis TelecomUS
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void checkMandatoryAndValidity(Telecoms telecomToCheck, boolean telForFunction, boolean bForce)
			throws JrafDomainRollbackException, JrafDomainNoRollbackException {
		if (StringUtils.isEmpty(telecomToCheck.getSstatut_medium())) {
			throw new JrafDomainRollbackException("137"); // REF_ERREUR : MEDIUM
															// STATUS MANDATORY
		}

		if (StringUtils.isEmpty(telecomToCheck.getScode_medium())) {
			throw new JrafDomainRollbackException("117"); // REF_ERREUR : MEDIUM
															// CODE MANDATORY
		}

		if (StringUtils.isEmpty(telecomToCheck.getSterminal())) {
			throw new JrafDomainRollbackException("122"); // REF_ERREUR :
															// INVALID TERMINAL
															// TYPE
		}

		if (StringUtils.isEmpty(telecomToCheck.getSnumero())) {
			throw new JrafDomainRollbackException("240"); // REF_ERREUR :
															// INVALID PHONE
															// NUMBER DATA
		}

		// Check pour une fonction diff�rent du check pour une personne morale
		if (!telForFunction) {
			if (!_REF_M.equals(telecomToCheck.getScode_medium())
					&& !_REF_F.equals(telecomToCheck.getScode_medium())
					&& !_REF_L.equals(telecomToCheck.getScode_medium())
					&& !_REF_P.equals(telecomToCheck.getScode_medium())
					&& !_REF_G.equals(telecomToCheck.getScode_medium()))
				throw new JrafDomainRollbackException("116"); // REF_ERREUR :
																// INVALID
																// MEDIUM CODE
		} else {
			if (!_REF_L.equals(telecomToCheck.getScode_medium()))
				throw new JrafDomainRollbackException("116"); // REF_ERREUR :
																// INVALID
																// MEDIUM CODE
		}

		if (!"T".equals(telecomToCheck.getSterminal()) && !"F".equals(telecomToCheck.getSterminal())
				&& !"M".equals(telecomToCheck.getSterminal()))
			throw new JrafDomainRollbackException("122"); // REF_ERREUR :
															// INVALID TERMINAL
															// TYPE

		// Only V status allowed in creation
		if (!_REF_V.equals(telecomToCheck.getSstatut_medium())
				&& StringUtils.isEmpty(telecomToCheck.getSain()))
			throw new JrafDomainRollbackException("136"); // REF_ERREUR :
															// INVALID MEDIUM
															// STATUS
		// status V,I or S allowed in modification
		if (!_REF_I.equals(telecomToCheck.getSstatut_medium())
				&& !_REF_V.equals(telecomToCheck.getSstatut_medium())
				&& !_REF_S.equals(telecomToCheck.getSstatut_medium()))
			throw new JrafDomainRollbackException("136"); // REF_ERREUR :
															// INVALID MEDIUM
															// STATUS

		// Mise en commentaire du code : The updated phone number must be
		// normalized too!
		// Normalize phone number in creation only => NON
		// if (StringUtils.isEmpty(telecomToCheck.getSain())) {
		// Normalize
		telecomToCheck = normalize(telecomToCheck, bForce);
		// }
		// else
		// telecomToCheck.setIsnormalized("N");
	}

	public boolean checkTelecomIsEmptyData(Telecoms telecomToCheck) {
		boolean res = false;
		if (StringUtils.isEmpty(telecomToCheck.getSstatut_medium())
				&& StringUtils.isEmpty(telecomToCheck.getScode_medium())
				&& StringUtils.isEmpty(telecomToCheck.getSterminal())
				&& StringUtils.isEmpty(telecomToCheck.getSnumero())) {
			res = true;
		}
		return res;
	}


	/**
	 * Call normalization of the BO phone number
	 * 
	 * @param telecomToCheck
	 * @return
	 * @throws JrafDomainRollbackException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Telecoms normalize(Telecoms telecomToCheck, boolean bForce) throws JrafDomainRollbackException, JrafDomainNoRollbackException {

		TelecomsDTO telDTO = new TelecomsDTO();

		telDTO.setCountryCode(telecomToCheck.getCountryCode());
		telDTO.setSnumero(telecomToCheck.getSnumero());

		TelecomsDTO telDTONormalized = null;

		try {
			telDTONormalized = normalizePhoneNumber(telDTO, bForce);
			// Anciens champs snumero et sindicatif � renseigner pour
			// compatibilit�
			telecomToCheck.setSindicatif(telDTONormalized.getSnorm_inter_country_code());
			telecomToCheck.setSnumero(telDTONormalized.getSnorm_nat_phone_number_clean());

			telecomToCheck.setSnorm_inter_country_code(telDTONormalized.getSnorm_inter_country_code());
			telecomToCheck.setSnorm_inter_phone_number(telDTONormalized.getSnorm_inter_phone_number());
			telecomToCheck.setSnorm_nat_phone_number(telDTONormalized.getSnorm_nat_phone_number());
			telecomToCheck.setSnorm_nat_phone_number_clean(telDTONormalized.getSnorm_nat_phone_number_clean());
			telecomToCheck.setSnorm_terminal_type_detail(telDTONormalized.getSnorm_terminal_type_detail());
			telecomToCheck.setSnormalized_country(telDTONormalized.getSnormalized_country());
			telecomToCheck.setSnormalized_numero(telDTONormalized.getSnormalized_numero());

			telecomToCheck.setIsnormalized("Y");
		} catch (JrafDomainException e) {			
			if (bForce)
			{
				//A JrafDomainNoRollbackException("WRONG NORMALIZED TELECOM");
				telecomToCheck.setIsnormalized("N");							
			}
			else
				throw new JrafDomainRollbackException("WRONG NORMALIZED TELECOM");
		}

		return telecomToCheck;
	}





	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateProspectNormalizedTelecom(Long gin, List<TelecomsDTO> prospectTelecomFromWS,
			SignatureDTO signatureAPP, SignatureDTO signatureWS) throws JrafDomainException {

		if (prospectTelecomFromWS == null || prospectTelecomFromWS.isEmpty()) {
			return;
		}

		// Signature is mandatory
		if (signatureAPP == null) {
			throw new InvalidParameterException("Signature data is mandatory");
		}

		// set default values as medium/status code
		prospectTelecomFromWS.get(0).setDefaultValues();

		// set gin
		prospectTelecomFromWS.get(0).setSgin(String.valueOf(gin));

		// find latest matching VALID telecom in DB
		TelecomsDTO foundTelecomFromDB = findLatestProspectTelecom(prospectTelecomFromWS.get(0));

		// no telecom found in DB --> create a new one
		if (foundTelecomFromDB == null) {
			// error : only VALID status allowed for creation
			if (!MediumStatusEnum.VALID.toString().equals(prospectTelecomFromWS.get(0).getSstatut_medium())) {
				throw new InvalidParameterException("Only VALID status is allowed for telecom creation");
			}

			// set mandatory values for creation
			prospectTelecomFromWS.get(0).prepareForCreation(signatureAPP);

			// create telecom
			createProspectTelecoms(prospectTelecomFromWS);

			// remove other telecoms with same terminal
			removeAllButThis(prospectTelecomFromWS.get(0), signatureWS);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createProspectTelecoms(List<TelecomsDTO> prospectTelecomFromWS) throws JrafDomainException {
		IndividuDTO findProspect = null;

		findProspect = individuDS.getByGin(prospectTelecomFromWS.get(0).getSgin());

		if (findProspect == null) {
			throw new JrafDomainException("Unable to get prospect referenced by the following gin: "
					+ prospectTelecomFromWS.get(0).getSgin());
		}

		prospectTelecomFromWS.get(0).setIndividudto(findProspect);
		try {
			Telecoms telecom = IndividuTransform.prospectTelecomDto2Bo(prospectTelecomFromWS.get(0));
			telecomsRepository.saveAndFlush(telecom);
			IndividuTransform.prospectTelecomBo2DtoLight(telecom, prospectTelecomFromWS.get(0));
		} catch (JrafDomainException e) {
			throw new JrafDomainException("Unable to create telecom for prospect ", e);
		}
	}

    @Transactional(readOnly=true)
	public TelecomsDTO findLatestProspectTelecom(TelecomsDTO prospectTelecomDTO) throws JrafDomainException{
		
    	Telecoms telecom = IndividuTransform.prospectTelecomDto2BoLight(prospectTelecomDTO);
    	telecom = telecomsRepository.findLatest(telecom.getSgin(), telecom.getSterminal(), 
    			telecom.getSnorm_inter_country_code(), telecom.getSnorm_nat_phone_number());
    	//telecom = prospectTelecomDao.findLatest(telecom);
		// no result
		if (telecom == null) {
			return null;
		}

		TelecomsDTO telecomDto = IndividuTransform.prospectTelecomBo2DtoLight(telecom);

		return telecomDto;
	}

	public void checkProspectTelecom(List<TelecomsDTO> prospectTelecomFromWS) throws JrafDomainException {
		if (prospectTelecomFromWS == null || prospectTelecomFromWS.isEmpty() || prospectTelecomFromWS.size() > 1) {
			return;
		}

		if (StringUtils.isEmpty(prospectTelecomFromWS.get(0).getSterminal())) {
			throw new MissingParameterException("Terminal type is mandatory for following phone number: "
					+ prospectTelecomFromWS.get(0).getSnumero());
		}

		if (StringUtils.isEmpty(prospectTelecomFromWS.get(0).getCountryCode())) {
			throw new MissingParameterException("Country code is mandatory for following phone number: "
					+ prospectTelecomFromWS.get(0).getSnumero());
		}

		if (StringUtils.isEmpty(prospectTelecomFromWS.get(0).getSnumero())) {
			throw new MissingParameterException("Phone number is mandatory for the telecom structure");
		}
	}

	public List<TelecomsDTO> normalizeProspectPhoneNumber(List<TelecomsDTO> prospectTelecomsFromWS)
			throws JrafDomainException {
		if (prospectTelecomsFromWS == null || prospectTelecomsFromWS.isEmpty()) {
			return null;
		}

		String countryCode = prospectTelecomsFromWS.get(0).getCountryCode();
		String phoneNumber = prospectTelecomsFromWS.get(0).getSnumero();

		NormalizePhoneNumberDTO normalizePhoneNumberDTO = normalizePhoneNumberDS.normalizePhoneNumber(countryCode,
				phoneNumber, false);
		TelecomsTransform.prospectTelecomTransform(prospectTelecomsFromWS.get(0), normalizePhoneNumberDTO);

		return prospectTelecomsFromWS;
	}

	public int getNumberTelecomsPro(String gin) throws JrafDomainException {
		return telecomsRepository.getNumberTelecomsProOrPersoByGinAndCodeMedium(gin, "P");
	}
	
	public int getNumberTelecomsPerso(String gin) throws JrafDomainException {
		return telecomsRepository.getNumberTelecomsProOrPersoByGinAndCodeMedium(gin, "D");
	}




}
