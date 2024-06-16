package com.airfrance.repind.service.internal.unitservice.zone;

import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.entity.refTable.RefTableREF_NAT_ZONE;
import com.airfrance.repind.entity.refTable.RefTableREF_STYP_ZON;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.zone.LienZvZcRepository;
import com.airfrance.repind.dao.zone.PmZoneRepository;
import com.airfrance.repind.dao.zone.ZoneCommRepository;
import com.airfrance.repind.dao.zone.ZoneVenteRepository;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.dto.zone.ZoneCommTransform;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.dto.zone.ZoneVenteTransform;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.zone.LienZvZc;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.enums.NatureZoneEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ZoneCommUS {

	/** logger */
	private static final Log log = LogFactory.getLog(ZoneCommUS.class);

	/* PROTECTED REGION ID(_-2UN0KLiEeSXNpATSKyi0Q u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/** references on associated DAOs */
	@Autowired
	private ZoneCommRepository zoneCommRepository;

	@Autowired
	private ZoneVenteRepository zoneVenteRepository;

	@Autowired
	private PmZoneRepository pmZoneRepository;

	@Autowired
	private LienZvZcRepository lienZvZcRepository;

	/**
	 * empty constructor
	 */
	public ZoneCommUS() {
	}

	/* PROTECTED REGION ID(_-2UN0KLiEeSXNpATSKyi0Q u m) ENABLED START */
	// add your custom methods here if necessary

	public ZoneComm findCorrespondingCancellationZone(ZoneComm zcLink) throws JrafDomainException {

		ZoneComm result = null;

		// Liste des liens
		List<ZoneComm> zcs = new ArrayList<ZoneComm>();

		// On recherche le lien d'annulation dont le ZC4 = ZC1 du lien zcLink (et avec
		// ZC5 non null)
		zcs.addAll(zoneCommRepository.findCancellation(zcLink.getZc1()));

		// Si la liste des liens n'est pas vide, on prend le 1er
		if (!zcs.isEmpty()) {

			result = zcs.get(0);
		}

		return result;

	}

	/**
	 * Get a ZC attached to a Person Morale.
	 * @param ginPM gin of the {@link com.airfrance.repind.entity.firme.PersonneMorale}
	 * @return One ZC or null if no ZC found
	 * @throws JrafDomainException
	 */
	public List<ZoneCommDTO> findZcsByGinPM(String ginPM) throws JrafDomainException {
		List<ZoneComm> zc = zoneCommRepository.getZoneCommByGinOfPM(ginPM);
		return ZoneCommTransform.bo2DtoLight(zc);
	}

	/**
	 * Get a ZC Attached to a Fonction
	 * @param cle_fonction Cle of the {@link com.airfrance.repind.entity.firme.Fonction}
	 * @return One ZC or null if no ZC found
	 * @throws JrafDomainException
	 */
	public List<ZoneCommDTO> findZcsByGinFonction(Integer cle_fonction) throws JrafDomainException {
		List<ZoneComm> zc = zoneCommRepository.getZoneCommByGinOfFonction(cle_fonction);
		return ZoneCommTransform.bo2DtoLight(zc);
	}

	public ZoneComm findZone(PostalAddress pPostalAddress, NatureZoneEnum pNature) throws JrafDomainException {

		Assert.notNull(pPostalAddress);

		ZoneComm result = null;

		String codePays = pPostalAddress.getScode_pays();
		String codeProvince = pPostalAddress.getScode_province();
		String codePostal = pPostalAddress.getScode_postal();

		if (codePays != null) {

			// Liste des liens
			List<ZoneComm> zcs = new ArrayList<ZoneComm>();

			if (codeProvince != null) {

				// On recherche les liens dans LIEN_INT_CP_ZD avec SCODE_PAYS et SCODE_PROVINCE
				zcs.addAll(
						zoneCommRepository.findByCountryCodeAndProvinceCodeAndNature(codePays, codeProvince, pNature));
			} else {

				log.warn("code province non renseigné");

				if (codePostal != null) {

					if (StringUtils.isNumeric(codePostal)) {

						// On recherche les liens dans LIEN_INT_CP_ZD avec SCODE_PAYS et ICLE_INT_CP
						zcs.addAll(zoneCommRepository.findByCountryCodeAndPostalCodeAndNature(codePays, codePostal,
								pNature));

					} else {
						log.warn("code postal non numérique");
					}

				} else {
					log.warn("code postal non renseigné");
				}
			}

			// Si aucun lien, on recherche les liens dans LIEN_INT_CP_ZD avec SCODE_PAYS
			if (zcs.isEmpty()) {

				zcs.addAll(zoneCommRepository.findByCountryCodeAndNature(codePays, pNature));
			}

			// Si la liste des liens n'est pas vide, on prend le 1er
			if (!zcs.isEmpty()) {

				result = zcs.get(0);
			}

		} else {

			log.warn("Call findStandbyZone without country");
		}

		return result;
	}

	public ZoneComm findTrashZone() throws JrafDomainException {

		ZoneComm result = null;

		ZoneComm zc = new ZoneComm();
		zc.setZc1("DIV");
		zc.setZc2("ZIT");
		zc.setZc3("DIVIT");
		zc.setZc4("RRDIV");
		zc.setZc5("ZZ");
		List<ZoneComm> zcs = zoneCommRepository.findAll(Example.of(zc));

		if (!zcs.isEmpty()) {
			result = zcs.get(0);
		}

		return result;
	}

	public boolean checkZc1ToZc5(ZoneComm pCommercialZone) {

		return StringUtils.isNotEmpty(pCommercialZone.getZc1()) && StringUtils.isNotEmpty(pCommercialZone.getZc2())
				&& StringUtils.isNotEmpty(pCommercialZone.getZc3()) && StringUtils.isNotEmpty(pCommercialZone.getZc4())
				&& StringUtils.isNotEmpty(pCommercialZone.getZc5());
	}

	public void checkSubtype(ZoneComm pCommercialZone) throws JrafDomainException {
	}

	/**
	 * Retrieves the active commercial zone matching all the parameters
	 * 
	 * @param zc1 zc1 value
	 * @param zc2 zc2 value
	 * @param zc3 zc3 value
	 * @param zc4 zc4 value
	 * @param zc5 zc5 value
	 * @return the active zone matching all the parameters
	 * @throws JrafDomainException
	 */
	public ZoneCommDTO findActiveByZc1Zc2Zc3Zc4Zc5(@NotNull String zc1, String zc2, String zc3,
			String zc4, String zc5) throws JrafDomainException {
		ZoneComm zoneComm = zoneCommRepository.findActiveByZc1Zc2Zc3Zc4Zc5(zc1, zc2, zc3, zc4, zc5);
		ZoneCommDTO zoneCommDTO = null;
		if (zoneComm != null) {
			zoneCommDTO = ZoneCommTransform.bo2DtoLight(zoneComm);
		}
		return zoneCommDTO;
	}

	/**
	 * Retrieves all the zone numbers by levels. e.g. To retrieve zc1, leave zc1 as
	 * ({@code null} To retrieve zc2 associated with zc1, specify the zc1 value and
	 * leave zc2 as {@code null}
	 * 
	 * @param zc1 level 1
	 * @param zc2 level 2
	 * @param zc3 level 3
	 * @param zc4 level 4
	 * @return the zone numbers by level
	 */
	public List<String> getZoneNumbers(String zc1, String zc2, String zc3, String zc4, Boolean active, String subtype) {
		if (zc1 == null) {
			return zoneCommRepository.getAllZc1(active, subtype);
		} else {
			if (zc2 == null) {
				return zoneCommRepository.getAllZc2(zc1, active, subtype);
			}
			if (zc3 == null) {
				return zoneCommRepository.getAllZc3(zc1, zc2, active, subtype);
			} else {
				if (zc4 == null) {
					return zoneCommRepository.getAllZc4(zc1, zc2, zc3, active, subtype);
				} else {
					return zoneCommRepository.getAllZc5(zc1, zc2, zc3, zc4, active, subtype);
				}
			}
		}
	}

	/**
	 * Retrieves the hierarchical numbers by level. e.g. For the specified zc1
	 * level, zc2 level data will be retrieved, such that zc3 is {@code null}
	 * 
	 * The data of all the precedent levels must be specified to retrieve the data
	 * of a level. e.g. to retrieve data for zc4, data for zc1 to zc4 must be
	 * specified
	 * 
	 * @param zc1 level 1
	 * @param zc2 level 2
	 * @param zc3 level 3
	 * @param zc4 level 4
	 * @return the hierarchical zone numbers by level
	 * @throws JrafDomainException
	 */
	public List<ZoneCommDTO> findLightHierarchicalByNumber(@NotNull String zc1, String zc2, String zc3, String zc4)
			throws JrafDomainException {
		List<ZoneCommDTO> result = new ArrayList<>();
		for (ZoneComm zc : findHierarchicalByNumber(zc1, zc2, zc3, zc4)) {
			result.add(ZoneCommTransform.bo2DtoLight(zc));
		}
		return result;
	}

	private List<ZoneComm> findHierarchicalByNumber(String zc1, String zc2, String zc3, String zc4) {
		if (zc4 != null) {
			return zoneCommRepository.findHierarchicalByZc4(zc1, zc2, zc3, zc4);
		} else {
			if (zc3 != null) {
				return zoneCommRepository.findHierarchicalByZc3(zc1, zc2, zc3);
			} else {
				if (zc2 != null) {
					return zoneCommRepository.findHierarchicalByZc2(zc1, zc2);
				} else {
					return zoneCommRepository.findHierarchicalByZc1(zc1);
				}
			}
		}
	}

	/**
	 * Retrieves the zone by level specified. e.g. If zc1 is specified, the zone
	 * matching zc1 will be retrieved irrespective of other levels
	 * 
	 * @param zc1 level 1
	 * @param zc2 level 2
	 * @param zc3 level 3
	 * @param zc4 level 4
	 * @param zc5 level 5
	 * @return the zone by level
	 * @throws JrafDomainException
	 */
	public List<ZoneCommDTO> findLightByNumber(@NotNull String zc1, String zc2, String zc3, String zc4, String zc5)
			throws JrafDomainException {
		List<ZoneCommDTO> result = new ArrayList<>();

		for (ZoneComm zc : this.findByNumber(zc1, zc2, zc3, zc4, zc5)) {
			result.add(ZoneCommTransform.bo2DtoLight(zc));
		}

		return result;
	}

	private List<ZoneComm> findByNumber(String zc1, String zc2, String zc3, String zc4, String zc5) {
		if (!StringUtils.isBlank(zc5)) {
			return zoneCommRepository.findAllByZc5(zc1, zc2, zc3, zc4, zc5);
		} else {
			if (!StringUtils.isBlank(zc4)) {
				return zoneCommRepository.findAllByZc4(zc1, zc2, zc3, zc4);
			} else {
				if (!StringUtils.isBlank(zc3)) {
					return zoneCommRepository.findAllByZc3(zc1, zc2, zc3);
				} else {
					if (!StringUtils.isBlank(zc2)) {
						return zoneCommRepository.findAllByZc2(zc1, zc2);
					} else {
						return zoneCommRepository.findAllByZc1(zc1);
					}
				}
			}
		}
	}

	private ZoneComm findSuperiorZone(String zc1, String zc2, String zc3, String zc4, String zc5)
			throws JrafDaoException {
		if (!StringUtils.isBlank(zc5)) {
			return zoneCommRepository.findActiveByZc4(zc1, zc2, zc3, zc4);
		} else {
			if (!StringUtils.isBlank(zc4)) {
				return zoneCommRepository.findActiveByZc3(zc1, zc2, zc3);
			} else {
				if (!StringUtils.isBlank(zc3)) {
					return zoneCommRepository.findActiveByZc2(zc1, zc2);
				} else {
					if (!StringUtils.isBlank(zc2)) {
						return zoneCommRepository.findActiveByZc1(zc1);
					} else {
						throw new JrafDaoException(RefTableREF_ERREUR._REF_000);
					}
				}
			}
		}
	}

	/**
	 * Retrieves the zone by the GIN
	 * 
	 * @param gin GIN of the zone to be retrieved
	 * @return zone for specified GIN
	 * @throws JrafDomainException
	 */
	public ZoneCommDTO findLightByGin(@NotNull Long gin) throws JrafDomainException {
		ZoneComm zc = zoneCommRepository.findByGin(gin);
		if (zc != null) {
			return ZoneCommTransform.bo2DtoLight(zc);
		}
		return null;
	}

	/**
	 * Retrieves the commercial zone by sales zone
	 * 
	 * @param zoneVenteDto sales zone associated with the commercial zone
	 * @return commercial zone for the sales zone specified
	 * @throws JrafDomainException
	 */
	public ZoneCommDTO getByZoneVente(@NotNull final ZoneVenteDTO zoneVenteDto) throws JrafDomainException {
		List<Long> zvGinList = zoneVenteRepository.getHeirarchyGins(ZoneVenteTransform.dto2BoLight(zoneVenteDto));
		if (!zvGinList.isEmpty()) {
			List<LienZvZc> response = lienZvZcRepository.findActiveByZoneVente(zvGinList);
			if (!response.isEmpty()) {
				return ZoneCommTransform.bo2DtoLight(response.get(0).getZoneComm());
			}
		}
		return null;
	}

	/**
	 * Validates and creates the commercial zone
	 * 
	 * @param newZone commercial zone to be created
	 * @return the new zone created with GIN populated
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public ZoneCommDTO createLight(ZoneCommDTO newZone) throws JrafDomainException {
		validateCreate(newZone);
		ZoneComm zoneTobeCreated = ZoneCommTransform.dto2BoLight(newZone);
		zoneTobeCreated.setDateMaj(new Date());
		zoneTobeCreated.setStatut(ZoneServiceHelper.calculateStatus(zoneTobeCreated.getDateFermeture()));
		ZoneComm zoneComm = zoneCommRepository.saveAndFlush(zoneTobeCreated);
		return ZoneCommTransform.bo2DtoLight(zoneComm);
	}

	/**
	 * Updated the commercial zone:
	 * <ul>
	 * The following fields can be updated
	 * <li>Nature</li>
	 * <li>Closing date</li>
	 * </ul>
	 * If the zone is being closed i.e. Start date is after the Closing date,
	 * validation needs to be done that there are no links or (lower) hierarchy
	 * existing for the modified zone
	 * 
	 * @param modifiedZone the zone to be updated
	 * @return the updated zone
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public ZoneCommDTO updateLight(ZoneCommDTO modifiedZone) throws JrafDomainException {
		ZoneComm existing = zoneCommRepository.findByGin(modifiedZone.getGin());
		if (existing == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_001);
		}
		Date today = new Date();
		existing.setDateMaj(today);
		existing.setSignatureMaj(modifiedZone.getSignatureMaj());

		// Validate nature
		String updatedNature = modifiedZone.getNature();
		if (StringUtils.isNotBlank(updatedNature)) {
			// Validate nature
			if (StringUtils.isBlank(updatedNature)
					|| !RefTableREF_NAT_ZONE.instance().estValide(updatedNature, StringUtils.EMPTY)) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_261);
			}
			existing.setNature(updatedNature);
		}

		// Validate sous type
		String updatedSousType = modifiedZone.getSousType();
		if (StringUtils.isNotBlank(updatedSousType)) {
			if (!RefTableREF_STYP_ZON.instance().estValide(updatedSousType, StringUtils.EMPTY)) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_328);
			}
			existing.setSousType(updatedSousType);
		}

		Date existingStartDate = existing.getDateOuverture();
		Date existingEndDate = existing.getDateFermeture();
		Date modifiedEndDate = modifiedZone.getDateFermeture();
		Date modifiedStartDate = modifiedZone.getDateOuverture();


		// Check if End date or Start date are modified
		if (ZoneServiceHelper.isDateModified(existingEndDate, modifiedEndDate) || ZoneServiceHelper.isDateModified(existingStartDate, modifiedStartDate) ) {
			if (modifiedEndDate != null && modifiedStartDate != null
					&& ZoneServiceHelper.dateTimeComparator.compare(modifiedEndDate, modifiedStartDate) < 0) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_119);
			}
			// Check if end date is valid w.r.t.superior zone
			if(ZoneServiceHelper.isDateModified(existingEndDate, modifiedEndDate)){
				validateSuperiorZoneEndDate(existing, modifiedEndDate);
			}
			ZoneServiceHelper.updateStartDate(existing,modifiedStartDate,false);
			if (modifiedEndDate != null) {
				// Check if active links with PM is present and close the inferior zones as well
				return ZoneCommTransform.bo2DtoLight(closeZone(existing, modifiedEndDate, false));
			}
			ZoneServiceHelper.updateEndDate(existing, modifiedEndDate, false);
		}

		ZoneComm updated = zoneCommRepository.saveAndFlush(existing);
		return ZoneCommTransform.bo2DtoLight(updated);
	}

	private ZoneComm closeZone(ZoneComm existing, Date modifiedEndDate, boolean isInferiorZone)
			throws JrafDaoException {
		// Check if the zone is associated with PM
		if (pmZoneRepository.findAllActiveByIginZone(existing.getGin()) > 0) {
			log.error("Active link with PM present for zone gin : " + existing.getGin());
			throw new JrafDaoException(RefTableREF_ERREUR._REF_377 + ": LINKS(S) PRESENT");
		}

		// Update all the inferior zones
		if (StringUtils.isBlank(existing.getZc5())) {
			List<ZoneComm> hierarchicalZones = this.findHierarchicalByNumber(existing.getZc1(), existing.getZc2(),
					existing.getZc3(), existing.getZc4());
			for (ZoneComm subZone : hierarchicalZones) {
				this.closeZone(subZone, modifiedEndDate, true);
			}
		}

		ZoneServiceHelper.updateEndDate(existing, modifiedEndDate, isInferiorZone);
		return zoneCommRepository.saveAndFlush(existing);
	}

	private void validateCreate(ZoneCommDTO newZone) throws JrafDaoException {
		String incomingZc1 = newZone.getZc1();

		if (StringUtils.isBlank(incomingZc1)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_176);
		}

		String zc1 = StringUtils.upperCase(incomingZc1);
		String zc2 = StringUtils.upperCase(newZone.getZc2());
		String zc3 = StringUtils.upperCase(newZone.getZc3());
		String zc4 = StringUtils.upperCase(newZone.getZc4());
		String zc5 = StringUtils.upperCase(newZone.getZc5());

		// Validate zone number
		List<ZoneComm> existingZones = this.findByNumber(zc1, zc2, zc3, zc4, zc5);
		if (!CollectionUtils.isEmpty(existingZones)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_375);
		}

		// Validate hierarchy
		ZoneComm superiorZone = null;
		if (StringUtils.isNotBlank(zc2)) {
			superiorZone = this.findSuperiorZone(zc1, zc2, zc3, zc4, zc5);
			if (superiorZone == null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}

		// Validate sub-type
		String subType = newZone.getSousType();
		if (StringUtils.isBlank(subType) || !RefTableREF_STYP_ZON.instance().estValide(subType, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_328);
		}

		// Validate nature
		String nature = newZone.getNature();
		if (StringUtils.isBlank(nature) || !RefTableREF_NAT_ZONE.instance().estValide(nature, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_261);
		}

		// Validate dates
		ZoneServiceHelper.validateDates(newZone, superiorZone);

		// Update zone case
		newZone.setZc1(zc1);
		newZone.setZc2(zc2);
		newZone.setZc3(zc3);
		newZone.setZc4(zc4);
		newZone.setZc5(zc5);
	}
	
	private void validateSuperiorZoneEndDate(ZoneComm existing, Date modifiedEndDate) throws JrafDaoException {
		String zc2 = StringUtils.upperCase(existing.getZc2());
		ZoneComm superiorZone = null;
		if (StringUtils.isNotBlank(zc2)) {
			String zc1 = StringUtils.upperCase(existing.getZc1());
			String zc3 = StringUtils.upperCase(existing.getZc3());
			String zc4 = StringUtils.upperCase(existing.getZc4());
			String zc5 = StringUtils.upperCase(existing.getZc5());
			superiorZone = this.findSuperiorZone(zc1, zc2, zc3, zc4, zc5);
			if (superiorZone != null) {
				Date superiorZoneEndDate = superiorZone.getDateFermeture();
				ZoneServiceHelper.validateSuperiorEndDate(modifiedEndDate, superiorZoneEndDate);
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}
	}
	private void validateSuperiorZoneStartDate(ZoneComm existing, Date modifiedEndDate) throws JrafDaoException {
		String zc2 = StringUtils.upperCase(existing.getZc2());
		ZoneComm superiorZone = null;
		if (StringUtils.isNotBlank(zc2)) {
			String zc1 = StringUtils.upperCase(existing.getZc1());
			String zc3 = StringUtils.upperCase(existing.getZc3());
			String zc4 = StringUtils.upperCase(existing.getZc4());
			String zc5 = StringUtils.upperCase(existing.getZc5());
			superiorZone = this.findSuperiorZone(zc1, zc2, zc3, zc4, zc5);
			if (superiorZone != null) {
				Date superiorZoneStartDate = superiorZone.getDateOuverture();
				ZoneServiceHelper.validateSuperiorStartDate(modifiedEndDate, superiorZoneStartDate);
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}
	}


	/* PROTECTED REGION END */
}
