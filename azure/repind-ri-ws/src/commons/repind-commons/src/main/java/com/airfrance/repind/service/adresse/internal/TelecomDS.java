package com.airfrance.repind.service.adresse.internal;

import com.airfrance.repind.entity.refTable.RefTableREF_STA_MED;
import com.airfrance.ref.SiteEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.IsNormalizedEnum;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.TerminalTypeEnum;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.TelecomsTransform;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.telecom.internal.NormalizePhoneNumberDS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
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

	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

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

    @Transactional(readOnly=true)
	public Integer countWhere(TelecomsDTO dto) throws JrafDomainException {
		Telecoms telecoms = TelecomsTransform.dto2BoLight(dto);
		return (int) telecomsRepository.count(Example.of(telecoms));
	}

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
		Telecoms telecoms = telecomsRepository.getOne(telecomsDTO.getSain());

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
	public List<TelecomsDTO> findTelecoms(String gin) throws JrafDomainException {
		List<TelecomsDTO> results = new ArrayList<>();
		for(Telecoms found : telecomsRepository.findBySgin(gin)) {
			results.add(TelecomsTransform.bo2DtoLight(found));
		}
		return results;
	}
    
    @Transactional(readOnly=true)
	public TelecomsDTO get(String sain) throws JrafDomainException {
		Optional<Telecoms> telecoms = telecomsRepository.findById(sain);
		if (!telecoms.isPresent()) {
			return null;
		}
		return TelecomsTransform.bo2DtoLight(telecoms.get());
	}
	
	public TelecomsRepository getTelecomsRepository() {
		return telecomsRepository;
	}

	public void setTelecomsRepository(TelecomsRepository telecomsRepository) {
		this.telecomsRepository = telecomsRepository;
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_mg1rMGyPEeK1MNJVk84-BQgem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager
	 *            EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * getPreferedTelecom
	 * 
	 * @param sgin
	 *            in String
	 * @return The getPreferedTelecom as <code>Telecoms</code>
	 * @throws JrafDomainException
	 *             en cas d'exception
	 */
    @Transactional(readOnly=true)
	public Telecoms getPreferedTelecom(String sgin) throws JrafDomainException {
		return telecomsRepository.getPreferedTelecom(sgin);
	}

	/* PROTECTED REGION ID(_mg1rMGyPEeK1MNJVk84-BQ u m) ENABLED START */

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateTelecom(String gin, List<TelecomsDTO> telecomFromWSList, SignatureDTO signatureAPP,
			SignatureDTO signatureWS) throws JrafDomainException {

		// Nothing to do
		if (telecomFromWSList == null || telecomFromWSList.isEmpty()) {
			return;
		}

		// Check telecoms
		checkTelecoms(telecomFromWSList);

		// Normalize provided telecom
		telecomFromWSList = normalizePhoneNumber(telecomFromWSList);

		// Update normalize telecom
		updateNormalizedTelecom(gin, telecomFromWSList, signatureAPP, signatureWS);
	}

	public void checkTelecoms(List<TelecomsDTO> telecomFromWSList)
			throws JrafDomainException, InvalidParameterException {

		// Nothing to do
		if (telecomFromWSList == null || telecomFromWSList.isEmpty()) {
			return;
		}

		// Check mandatory fields
		checkMandatoryFields(telecomFromWSList);

		// No more than MAX_NB_MOBILE_PHONE mobile phone allowed in input
		if (countFiltered(telecomFromWSList, TerminalTypeEnum.MOBILE.toString()) > MAX_NB_MOBILE_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_MOBILE_PHONE + " mobile phone allowed");
		}

		// No more than MAX_NB_FIXED_PHONE fixed phone allowed in input
		if (countFiltered(telecomFromWSList, TerminalTypeEnum.FIX.toString()) > MAX_NB_FIXED_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_FIXED_PHONE + " fixed-line phone allowed");
		}
	}

	public void checkTelecomsByUsageCode(List<TelecomsDTO> telecomFromWSList)
			throws JrafDomainException, InvalidParameterException {

		// Nothing to do
		if (telecomFromWSList == null || telecomFromWSList.isEmpty()) {
			return;
		}

		// Check mandatory fields
		checkMandatoryFieldsWithUsageCode(telecomFromWSList);

		// MOBILE PHONES
		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.MOBILE.toString(),
				MediumCodeEnum.BUSINESS.toString()) > MAX_NB_MOBILE_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_MOBILE_PHONE
					+ " mobile phone allowed with medium code " + MediumCodeEnum.BUSINESS.toString());
		}

		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.MOBILE.toString(),
				MediumCodeEnum.HOME.toString()) > MAX_NB_MOBILE_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_MOBILE_PHONE
					+ " mobile phone allowed with medium code " + MediumCodeEnum.HOME.toString());
		}

		// FIXED LINES
		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.FIX.toString(),
				MediumCodeEnum.BUSINESS.toString()) > MAX_NB_FIXED_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_FIXED_PHONE
					+ " fixed-line phone allowed with medium code " + MediumCodeEnum.BUSINESS.toString());
		}

		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.FIX.toString(),
				MediumCodeEnum.HOME.toString()) > MAX_NB_FIXED_PHONE) {
			throw new InvalidParameterException("No more than " + MAX_NB_FIXED_PHONE
					+ " fixed-line phone allowed with medium code " + MediumCodeEnum.HOME.toString());
		}

		// FAX
		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.FAX.toString(),
				MediumCodeEnum.BUSINESS.toString()) > MAX_NB_FAX) {
			throw new InvalidParameterException("No more than " + MAX_NB_FAX + " fax allowed with medium code "
					+ MediumCodeEnum.BUSINESS.toString());
		}

		if (countFilteredByUsageCode(telecomFromWSList, TerminalTypeEnum.FAX.toString(),
				MediumCodeEnum.HOME.toString()) > MAX_NB_FAX) {
			throw new InvalidParameterException(
					"No more than " + MAX_NB_FAX + " fax allowed with medium code " + MediumCodeEnum.HOME.toString());
		}
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

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>0..1 mobile phone</li>
	 * <li>0..1 fixed-line phone</li>
	 * <li>status VALID or INVALID</li>
	 * <li>priority to VALID status</li>
	 * <li>Mobile even if Fixed is present</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public TelecomsDTO findLatestAutoByTerminal(String gin) throws JrafDomainException {

		// get latest fixed-line phone
		TelecomsDTO fixedLine = findLatest(gin, TerminalTypeEnum.FIX);

		// get latest mobile phone
		TelecomsDTO mobile = findLatest(gin, TerminalTypeEnum.MOBILE);

		// add fixed-line phone to result
		if (fixedLine != null && mobile == null) {
			return fixedLine;
		}

		// add mobile-line phone to result
		if (mobile != null) {
			return mobile;
		}

		return null;
	}

	/**
	 * Find the latest telecom according following criteria:
	 * <ul>
	 * <li>same gin</li>
	 * <li>0..1 mobile phone per usage code (Pro or Home)</li>
	 * <li>0..1 fixed-line phone per usage code (Pro or Home)</li>
	 * <li>0..1 fax per usage code (Pro or Home)</li>
	 * <li>status VALID or INVALID</li>
	 * <li>priority to VALID status</li>
	 * </ul>
	 * 
	 * @param telecomFromDBSet
	 * @param telecomFromWS
	 * @return max 6 telecoms per individual: 2 fixed-lines (P/D), 2 mobiles
	 *         (P/D) and 2 faxes (P/D)
	 */
    @Transactional(readOnly=true)
	public List<TelecomsDTO> findLatestByUsageCode(String gin) throws JrafDomainException {

		List<TelecomsDTO> telecomsDTOList = new ArrayList<TelecomsDTO>();

		// get latest fixed-line phone for usage 'P' and 'D'
		TelecomsDTO ProFixedLine = findLatestByUsageCode(gin, MediumCodeEnum.BUSINESS, TerminalTypeEnum.FIX);
		TelecomsDTO HomeFixedLine = findLatestByUsageCode(gin, MediumCodeEnum.HOME, TerminalTypeEnum.FIX);

		// get latest mobile phone for usage 'P' and 'D'
		TelecomsDTO ProMobile = findLatestByUsageCode(gin, MediumCodeEnum.BUSINESS, TerminalTypeEnum.MOBILE);
		TelecomsDTO HomeMobile = findLatestByUsageCode(gin, MediumCodeEnum.HOME, TerminalTypeEnum.MOBILE);

		// get latest fax for usage 'P' and 'D'
		TelecomsDTO ProFax = findLatestByUsageCode(gin, MediumCodeEnum.BUSINESS, TerminalTypeEnum.FAX);
		TelecomsDTO HomeFax = findLatestByUsageCode(gin, MediumCodeEnum.HOME, TerminalTypeEnum.FAX);

		// add fixed-line phone to result
		if (ProFixedLine != null) {
			telecomsDTOList.add(ProFixedLine);
		}

		if (HomeFixedLine != null) {
			telecomsDTOList.add(HomeFixedLine);
		}

		if (ProMobile != null) {
			telecomsDTOList.add(ProMobile);
		}

		if (HomeMobile != null) {
			telecomsDTOList.add(HomeMobile);
		}

		if (ProFax != null) {
			telecomsDTOList.add(ProFax);
		}

		if (HomeFax != null) {
			telecomsDTOList.add(HomeFax);
		}

		return telecomsDTOList;
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
	// REPIND-260 : SONAR - Passer la fonction en Public au lieu de Private car
	// Transactional
	public int updatePhoneNumberOnly(TelecomsDTO telecomsDTO) throws JrafDomainException {

		int update = 0;
		Telecoms telecoms = new Telecoms();

		IndividuDTO individuDTO = new IndividuDTO();
		individuDTO.setSgin(telecomsDTO.getSgin());

		telecomsDTO.setIndividudto(individuDTO);

		try {
			Telecoms telecom = TelecomsTransform.dto2Bo(telecomsDTO);
			update = telecomsRepository.updatePhoneNumberOnly(telecom);

			TelecomsTransform.bo2DtoLight(telecom, telecomsDTO);

		} catch (JrafDaoException e) {
			log.error(e);
			throw new JrafDomainException("Unable to create telecom", e);
		}

		TelecomsTransform.dto2BoLight(telecomsDTO, telecoms);

		return update;
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

	/**
	 * Count telecoms in provided list according following criteria:
	 * <ul>
	 * <li>same terminal type></li>
	 * </ul>
	 * 
	 * @param telecomDTOList
	 * @param terminalType
	 * @return
	 */
	protected int countFiltered(List<TelecomsDTO> telecomDTOList, String terminalType) {

		int counter = 0;

		for (TelecomsDTO telecomDTO : telecomDTOList) {

			String terminalTypeFromList = telecomDTO.getSterminal();

			boolean isSameTerminalType = terminalType.equals(terminalTypeFromList);

			if (isSameTerminalType) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Count telecoms in provided list according following criteria:
	 * <ul>
	 * <li>same terminal type</li>
	 * <li>same usage code</li>
	 * </ul>
	 * 
	 * @param telecomDTOList
	 * @param terminalType
	 * @param usageCode
	 * @return
	 */
	protected int countFilteredByUsageCode(List<TelecomsDTO> telecomDTOList, String terminalType, String usageCode) {

		int counter = 0;

		for (TelecomsDTO telecomDTO : telecomDTOList) {
			
			if (StringUtils.isNotEmpty(telecomDTO.getSstatut_medium()) && MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(telecomDTO.getSstatut_medium())) {
				continue;
			}

			String terminalTypeFromList = telecomDTO.getSterminal();
			String usageCodeFromList = telecomDTO.getScode_medium();

			boolean isSameTerminalType = terminalType.equals(terminalTypeFromList);
			boolean isSameUsageCode = usageCode.equals(usageCodeFromList);

			if (isSameTerminalType && isSameUsageCode) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Check following mandatory fields
	 * <ul>
	 * <li>terminal type</li>
	 * <li>medium code</li>
	 * <li>country code</li>
	 * <li>phone number</li>
	 * </ul>
	 * 
	 * @param telecomDTOList
	 * @throws JrafDomainException
	 */
	protected void checkMandatoryFieldsWithUsageCode(List<TelecomsDTO> telecomDTOList) throws JrafDomainException {

		for (TelecomsDTO telecomDTO : telecomDTOList) {

			if (StringUtils.isEmpty(telecomDTO.getSterminal())) {
				throw new MissingParameterException(
						"Terminal type is mandatory for following phone number: " + telecomDTO.getSnumero());
			}

			if (StringUtils.isEmpty(telecomDTO.getScode_medium())) {
				throw new MissingParameterException(
						"Medium code is mandatory for following phone number: " + telecomDTO.getSnumero());
			}

			// REPIND-643 : We do not check input validity if we are getting a
			// DELETE code (status X)
			if (!StringUtils.isEmpty(telecomDTO.getSstatut_medium()) && !"X".equals(telecomDTO.getSstatut_medium())) {
				if (StringUtils.isEmpty(telecomDTO.getCountryCode())) {
					throw new MissingParameterException(
							"Country code is mandatory for following phone number: " + telecomDTO.getSnumero());
				}
			}

			if (StringUtils.isEmpty(telecomDTO.getSnumero())) {
				throw new MissingParameterException("Phone number is mandatory for the telecom structure");
			}
		}
	}

	/**
	 * Check following mandatory fields:
	 * <ul>
	 * <li>terminal type</li>
	 * <li>country code</li>
	 * <li>phone number</li>
	 * </ul>
	 * 
	 * @param telecomDTOList
	 * @throws JrafDomainException
	 */
	protected void checkMandatoryFields(List<TelecomsDTO> telecomDTOList) throws JrafDomainException {

		for (TelecomsDTO telecomDTO : telecomDTOList) {

			if (StringUtils.isEmpty(telecomDTO.getSterminal())) {
				throw new MissingParameterException("Terminal type is mandatory for following phone number: "
						+ SicStringUtils.maskingPCIDSS(telecomDTO.getSnumero()));
			}

			if (StringUtils.isEmpty(telecomDTO.getCountryCode())) {
				throw new MissingParameterException("Country code is mandatory for following phone number: "
						+ SicStringUtils.maskingPCIDSS(telecomDTO.getSnumero()));
			}

			if (StringUtils.isEmpty(telecomDTO.getSnumero())) {
				throw new MissingParameterException("Phone number is mandatory for the telecom structure");
			}
		}
	}

	/**
	 * This method is aimed to check if a telecom (to update) was updated based
	 * on following criteria:
	 * 
	 * <ul>
	 * <li>same status</li>
	 * </ul>
	 * 
	 * @param foundTelecomFromDB
	 * @param telecomDTOFromWS
	 * @return
	 */
	protected boolean isTelecomUpdated(TelecomsDTO foundTelecomFromDB, TelecomsDTO telecomDTOFromWS) {

		String status = foundTelecomFromDB.getSstatut_medium();

		boolean isStatusModified = !status.equals(telecomDTOFromWS.getSstatut_medium());

		return isStatusModified;
	}

	/**
	 * Update {@link Telecoms} apres normalisation du batch. - verifier si le
	 * telecom est normaliser si oui : + si le numero normalise est trop long
	 * pour SNumero (>=15) - trop long : enlever les espaces - encore trop long
	 * : Invalider. + sinon - recupere le telecom existant. - maj les nouveau
	 * champs. - copier les anciens champs. - vider le champs code region. -
	 * positionner IsNormalized � Y. si non : + positionner IsNormalized � N. -
	 * augmente la version. - met � jour.
	 * 
	 * @param ain
	 *            : identifiant du {@link Telecoms}.
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public int updateTelecomNormalizedWithBatch(String ain, String gin, NormalizePhoneNumberDTO normalizePhoneNumberDTO)
			throws JrafDomainException {

		TelecomsDTO telecoms = new TelecomsDTO();
		telecoms.setSain(ain);
		telecoms.setSgin(gin);

		// Verifier si le telecom est normalise
		if (normalizePhoneNumberDTO == null) {
			// Normalisation = ECHEC
			telecoms.setIsnormalized(IsNormalizedEnum.N.toString());

		} else {
			// Normalisation = SUCCES
			// Ajout des nouveaux champs
			// Copier les nouveaux champs dans les anciens champs.
			boolean isNumTooLong = false;
			String natPhoneNumberClean = normalizePhoneNumberDTO.getNormalizedNationalPhoneNumberClean();
			if (natPhoneNumberClean.length() >= NUMERO_MAX_SIZE) {
				natPhoneNumberClean = natPhoneNumberClean.replace(ESPACE, VIDE);
				if (natPhoneNumberClean.length() >= NUMERO_MAX_SIZE) {
					isNumTooLong = true;
				}
			}

			if (isNumTooLong) {
				telecoms.setIsnormalized(IsNormalizedEnum.E.toString());
				telecoms.setSstatut_medium(MediumStatusEnum.INVALID.toString());
				telecoms.setDdate_modification(new Date());
				telecoms.setSsignature_modification(SIGNATURE_INVALID_NORM);
				telecoms.setSsite_modification(SiteEnum.BATCHQVI.toString());
			} else {
				telecoms.setSnorm_inter_country_code(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
				telecoms.setSnorm_inter_phone_number(normalizePhoneNumberDTO.getNormalizedInternationalPhoneNumber());
				telecoms.setSnorm_terminal_type_detail(normalizePhoneNumberDTO.getNormalizedTerminalTypeDetail());
				telecoms.setSnorm_nat_phone_number(normalizePhoneNumberDTO.getNormalizedNationalPhoneNumber());
				telecoms.setSnorm_nat_phone_number_clean(natPhoneNumberClean);

				// ancien champs pour retro compatibilit�.
				telecoms.setSnumero(natPhoneNumberClean);
				telecoms.setSindicatif(normalizePhoneNumberDTO.getNormalizedInternationalCountryCode());
				// RG : RAZ du champs code region
				telecoms.setScode_region(null);

				telecoms.setIsnormalized(IsNormalizedEnum.Y.toString());

			}
		}

		return this.updatePhoneNumberOnly(telecoms);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<TelecomsDTO> normalizePhoneNumber(List<TelecomsDTO> telecomsDTOList) throws JrafDomainException {

		if (telecomsDTOList == null || telecomsDTOList.isEmpty()) {
			return null;
		}

		for (TelecomsDTO telecomsDTO : telecomsDTOList) {
			telecomsDTO = normalizePhoneNumber(telecomsDTO, false);
		}

		return telecomsDTOList;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public TelecomsDTO normalizePhoneNumber(TelecomsDTO telecomsDTO) throws JrafDomainException {

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
				phoneNumber, false);
		TelecomsTransform.transform(telecomsDTO, normalizePhoneNumberDTO);

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

	public Set<TelecomsDTO> computeIsoCountryCode(Set<TelecomsDTO> normalizedTelecom) throws JrafDomainException {

		if (normalizedTelecom == null || normalizedTelecom.isEmpty()) {
			return null;
		}

		for (TelecomsDTO telecomDTO : normalizedTelecom) {
			if (telecomDTO.isNormalized()) {
				telecomDTO = computeIsoCountryCode(telecomDTO);
			}
		}

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

	/**
	 * Creates or update a telecom and associates it with an Agency
	 *  @param telecomsDTO the new address to be created
	 * @param agenceBO concerned Agency
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public void createUpdateAgencyTelecom(TelecomsDTO telecomsDTO, Agence agenceBO) throws JrafDomainException {
		if (telecomsDTO != null && agenceBO != null) {
			List<String> medium = Arrays.asList(telecomsDTO.getScode_medium());
			List<String> terminalType = Arrays.asList(telecomsDTO.getSterminal());
			Set<TelecomsDTO> dbList = findByGinPmCodeMediumAndTerminal(agenceBO.getGin(), medium, terminalType);

			if (CollectionUtils.isEmpty(dbList)) {
				// No telecom in DB
				createPmTelecom(telecomsDTO, agenceBO);
			}
			else {
				for (TelecomsDTO dbLoop : dbList) {
					if (MediumStatusEnum.VALID.equals(dbLoop.getSstatut_medium())) {
						telecomsDTO.setSain(dbLoop.getSain());
						telecomsDTO.setVersion(dbLoop.getVersion());
						telecomsDTO.setPersonneMorale(dbLoop.getPersonneMorale());
						telecomsDTO.setDdate_creation(dbLoop.getDdate_creation());
						telecomsDTO.setSsignature_creation(dbLoop.getSsignature_creation());
						telecomsDTO.setSsite_creation(dbLoop.getSsite_creation());

						Telecoms telToSave = TelecomsTransform.dto2Bo(telecomsDTO);
						telToSave.setPersonneMorale(agenceBO);
						telecomsRepository.saveAndFlush(TelecomsTransform.dto2Bo(telecomsDTO));
					}
				}
			}
		}
	}

	/**
	 * Find the telecom for a PersonneMorale for the specified GIN and CodeMedium and Terminal Type
	 *
	 * @param gin      the GIN of PersonneMorale
	 * @param codeList list of CodeMedium
	 * @param terminalList list of terminal
	 * @return the list of address found
	 */
	public Set<TelecomsDTO> findByGinPmCodeMediumAndTerminal(@NotNull String gin, @NotNull List<String> codeList, @NotNull List<String> terminalList) throws JrafDomainException {
		Set<Telecoms> result = telecomsRepository.findByGinPmCodeMediumAndTerminal(gin, codeList, terminalList);
		if (result != null && !result.isEmpty()) {
			return TelecomsTransform.bo2Dto(result);
		}
		return Collections.emptySet();
	}

	/**
	 * Creates a telecom for a personne morale
	 *
	 * @param telecomPm telecom to be created
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createPmTelecom(TelecomsDTO telecomPm, PersonneMorale pm) throws JrafDomainException {
		if (pm == null || StringUtils.isEmpty(pm.getGin())) {
			throw new MissingParameterException("PM data is missing");
		}

		Telecoms toBeCreated = TelecomsTransform.dto2BoLight(telecomPm);
		toBeCreated.setPersonneMorale(pm);
		Telecoms created = telecomsRepository.saveAndFlush(toBeCreated);
		return created.getSain();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.airfrance.sic.service.internal.unitservice.firm.ITelecomUS#
	 * createOrUpdateOrDelete(List, PersonneMorale)
	 */
	@Transactional(rollbackFor = JrafDomainException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateOrDelete(List<Telecoms> pTelecoms, PersonneMorale pPersonneMorale)
			throws JrafDomainException {

		Assert.notNull(pTelecoms);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// Initialize PersonneMorale Telecoms
		if (pPersonneMorale.getTelecoms() == null) {
			pPersonneMorale.setTelecoms(new HashSet<Telecoms>());
		}

		List<Telecoms> telecomsToCreate = new ArrayList<Telecoms>();
		List<Telecoms> telecomsToUpdate = new ArrayList<Telecoms>();

		// Check Validity of all Telecoms in the list
		for (Telecoms telecom : pTelecoms) {
			Telecoms telecomToSave = check(telecom, pPersonneMorale, false);

			if (telecomToSave.getSain() == null)
				telecomsToCreate.add(telecomToSave);
			else
				telecomsToUpdate.add(telecomToSave);
		}

		// Check Rule for global telecoms
		// List to check the global RM
		List<Telecoms> telecoms = new ArrayList<Telecoms>(pPersonneMorale.getTelecoms());
		telecoms.addAll(telecomsToCreate);

		// REPFIRM-606: Only V status are counted
		// Only 4 telecoms V
		// ERROR 613 "MAXIMUM NUMBER OF TELECOMS REACHED"
		// We must accept an update of telecom even if more than 4 telecoms
		// to be able to update it, so we check only if we have a telecom to
		// create
		if (telecomsToCreate != null && telecomsToCreate.size() > 0) {
			int nbTelecomVMax = 4;
			for (Telecoms telecom : telecoms) {
				if (RefTableREF_STA_MED._REF_V.equals(telecom.getSstatut_medium())) {
					nbTelecomVMax--;
				}

				if (nbTelecomVMax < 0)
					throw new JrafDomainRollbackException("613"); // REF_ERREUR
				// : MAXIMUM
				// NUMBER OF
				// TELECOMS
				// REACHED
			}
		}
		// Enregistrement en base
		for (Telecoms telecom : telecomsToCreate) {
			telecomsRepository.saveAndFlush(telecom);
		}
		for (Telecoms telecom : telecomsToUpdate) {
			telecomsRepository.saveAndFlush(telecom);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.airfrance.sic.service.internal.unitservice.firm.ITelecomUS#
	 * createOrUpdateOrDelete(List, PersonneMorale)
	 */
	@Transactional(rollbackFor = JrafDomainException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateOrDelete(List<Telecoms> pTelecoms, PersonneMorale pPersonneMorale, boolean bForce)
			throws JrafDomainException {

		Assert.notNull(pTelecoms);
		Assert.notNull(pPersonneMorale);
		Assert.notNull(pPersonneMorale.getGin());

		// Initialize PersonneMorale Telecoms
		if (pPersonneMorale.getTelecoms() == null) {
			pPersonneMorale.setTelecoms(new HashSet<Telecoms>());
		}

		List<Telecoms> telecomsToCreate = new ArrayList<Telecoms>();
		List<Telecoms> telecomsToUpdate = new ArrayList<Telecoms>();

		// Check Validity of all Telecoms in the list
		for (Telecoms telecom : pTelecoms) {
			Telecoms telecomToSave = check(telecom, pPersonneMorale, bForce);

			if (telecomToSave.getSain() == null)
				telecomsToCreate.add(telecomToSave);
			else
				telecomsToUpdate.add(telecomToSave);
		}

		// Check Rule for global telecoms
		// List to check the global RM
		List<Telecoms> telecoms = new ArrayList<Telecoms>(pPersonneMorale.getTelecoms());
		telecoms.addAll(telecomsToCreate);

		// REPFIRM-606: Only V status are counted
		// Only 4 telecoms V
		// ERROR 613 "MAXIMUM NUMBER OF TELECOMS REACHED"
		// We must accept an update of telecom even if more than 4 telecoms
		// to be able to update it, so we check only if we have a telecom to
		// create
		if (telecomsToCreate != null && telecomsToCreate.size() > 0) {
			int nbTelecomVMax = 4;
			for (Telecoms telecom : telecoms) {
				if (RefTableREF_STA_MED._REF_V.equals(telecom.getSstatut_medium())) {
					nbTelecomVMax--;
				}

				if (nbTelecomVMax < 0)
					throw new JrafDomainRollbackException("613"); // REF_ERREUR
				// : MAXIMUM
				// NUMBER OF
				// TELECOMS
				// REACHED
			}
		}
		// Enregistrement en base
		for (Telecoms telecom : telecomsToCreate) {
			telecomsRepository.saveAndFlush(telecom);
		}
		for (Telecoms telecom : telecomsToUpdate) {
			telecomsRepository.saveAndFlush(telecom);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Telecoms check(Telecoms telecomToCheck, PersonneMorale pm, boolean bForce) throws JrafDomainRollbackException, JrafDomainNoRollbackException {
		// telecomChecked : bo used with DAO layer
		Telecoms telecomChecked = null;

		Date now = new Date();

		// No key sent with valid data will generate a create of this telecom.
		if (StringUtils.isEmpty(telecomToCheck.getSain())) {

			// Creation
			// ---------

			checkMandatoryAndValidity(telecomToCheck, false, bForce);

			telecomToCheck.setSstatut_medium(MediumStatusEnum.VALID.toString());

			telecomToCheck.setDdate_creation(now); // signature et site ont
			// normalt �t� sett�s par
			// l'appelant

			telecomToCheck.setDdate_modification(now); // doit �tre sett� �
			// cause de la
			// r�plication
			telecomToCheck.setSsignature_modification(telecomToCheck.getSsignature_creation()); // doit
			// �tre
			// sett�
			// �
			// cause
			// de
			// la
			// r�plication
			telecomToCheck.setSsite_modification(telecomToCheck.getSsite_creation()); // doit
			// �tre
			// sett�
			// �
			// cause
			// de
			// la
			// r�plication

			telecomToCheck.setPersonneMorale(pm);

			telecomChecked = telecomToCheck;

		} else if (telecomToCheck.getSain() != null) {

			// modify or delete
			// ------------------

			// Get the existing telecoms
			Telecoms existingTelecom = null;
			List<Telecoms> lstTelecomsExisting = new ArrayList<Telecoms>(pm.getTelecoms());
			for (Telecoms existingT : lstTelecomsExisting) {
				if (existingT.getSain().trim().equals(telecomToCheck.getSain())
						|| existingT.getSain().equals(telecomToCheck.getSain())) {
					existingTelecom = existingT;
					break;
				}
			}

			if (existingTelecom == null) {

				throw new JrafDomainRollbackException("904"); // SAIN DOES NOT
				// EXIST
			} else if (telecomToCheck.getVersion() != null
					&& !existingTelecom.getVersion().equals(telecomToCheck.getVersion())) {
				throw new JrafDomainRollbackException("003");

			} else if (!checkTelecomIsEmptyData(telecomToCheck)) {

				// Modify
				// -------

				// Check filed mandatory and validity
				checkMandatoryAndValidity(telecomToCheck, false, bForce);

				existingTelecom.setScode_medium(telecomToCheck.getScode_medium());
				existingTelecom.setSstatut_medium(telecomToCheck.getSstatut_medium());
				existingTelecom.setSterminal(telecomToCheck.getSterminal());
				existingTelecom.setSnumero(telecomToCheck.getSnumero());
				existingTelecom.setSindicatif(telecomToCheck.getSindicatif());

				existingTelecom.setDdate_modification(now);
				existingTelecom.setSsite_modification(telecomToCheck.getSsite_modification());
				existingTelecom.setSsignature_modification(telecomToCheck.getSsignature_modification());

				existingTelecom.setScode_medium(telecomToCheck.getScode_medium());
				existingTelecom.setSstatut_medium(telecomToCheck.getSstatut_medium());

				existingTelecom.setSnorm_inter_country_code(telecomToCheck.getSnorm_inter_country_code());
				existingTelecom.setSnorm_inter_phone_number(telecomToCheck.getSnorm_inter_phone_number());
				existingTelecom.setSnorm_nat_phone_number(telecomToCheck.getSnorm_nat_phone_number());
				existingTelecom.setSnorm_nat_phone_number_clean(telecomToCheck.getSnorm_nat_phone_number_clean());
				existingTelecom.setSnorm_terminal_type_detail(telecomToCheck.getSnorm_terminal_type_detail());

				telecomChecked = existingTelecom;
			} else {

				// Suppression
				// ------------

				existingTelecom.setSstatut_medium(MediumStatusEnum.REMOVED.toString()); // flag to delete with
				// status = X

				existingTelecom.setDdate_modification(now);
				existingTelecom.setSsite_modification(telecomToCheck.getSsite_modification());
				existingTelecom.setSsignature_modification(telecomToCheck.getSsignature_modification());

				telecomChecked = existingTelecom;
			}
		}

		return telecomChecked;
	}
}
