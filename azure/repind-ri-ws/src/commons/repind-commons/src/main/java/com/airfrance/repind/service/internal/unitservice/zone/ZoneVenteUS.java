package com.airfrance.repind.service.internal.unitservice.zone;

import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.entity.refTable.RefTableREF_MONNAIE;
import com.airfrance.repind.entity.refTable.RefTableREF_NAT_ZONE;
import com.airfrance.repind.entity.refTable.RefTableREF_STYP_ZON;
import com.airfrance.repind.util.comparators.DateComparatorEnum;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.zone.LienIntCpZdRepository;
import com.airfrance.repind.dao.zone.PmZoneRepository;
import com.airfrance.repind.dao.zone.ZoneVenteRepository;
import com.airfrance.repind.dto.zone.ZoneVenteDTO;
import com.airfrance.repind.dto.zone.ZoneVenteTransform;
import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.ZoneVente;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.*;

@Service
public class ZoneVenteUS {
	private static final Log log = LogFactory.getLog(ZoneVenteUS.class);


	@Autowired
	private ZoneVenteRepository zoneVenteRepository;

	@Autowired
	private PmZoneRepository pmZoneRepository;

	@Autowired
	private LienIntCpZdRepository lienIntCpZdRepository;

	public ZoneVenteUS() {
	}

	public boolean checkZv0ToZv3(ZoneVente pSaleZone) {

		return pSaleZone.getZv0() != null && pSaleZone.getZv1() != null && pSaleZone.getZv2() != null
				&& pSaleZone.getZv3() != null;
	}

	/**
	 * Retrieves the active sales zone matching all the parameters
	 *
	 * @param zv0 zv0 value
	 * @param zv1 zv1 value
	 * @param zv2 zv2 value
	 * @param zv3 zv3 value
	 * @return the active sales zone matching all the parameters
	 * @throws JrafDomainException
	 */
	public ZoneVenteDTO findActiveByZv0Zv1Zv2Zv3(@NotNull Integer zv0, @NotNull Integer zv1, @NotNull Integer zv2,
												 @NotNull Integer zv3) throws JrafDomainException {
		ZoneVente zoneVente = zoneVenteRepository.findActiveByZv0Zv1Zv2Zv3(zv0, zv1, zv2, zv3);
		ZoneVenteDTO zoneVenteDTO = null;
		if (zoneVente != null) {
			zoneVenteDTO = ZoneVenteTransform.bo2DtoLight(zoneVente);
		}
		return zoneVenteDTO;
	}

	/**
	 * Retrieves all the zone numbers by levels. e.g. To retrieve zv0, leave zv0 as
	 * ({@code null} To retrieve zv1 associated with zv0, specify the zv0 value and
	 * leave zv1 as {@code null}
	 *
	 * @param zv0 level 0
	 * @param zv1 level 1
	 * @param zv2 level 2
	 * @return the zone numbers by level
	 */
	public List<Integer> getZoneNumbers(Integer zv0, Integer zv1, Integer zv2, Boolean active) {
		List<Integer> zoneNumbers = new ArrayList<>();
		if (zv0 == null) {
			for (ZoneVente zv : zoneVenteRepository.getAllZv0(active)) {
				zoneNumbers.add(zv.getZv0());
			}
		} else {
			if (zv1 == null) {
				for (ZoneVente zv : zoneVenteRepository.getAllZv1(zv0, active)) {
					zoneNumbers.add(zv.getZv1());
				}
			} else {
				if (zv2 == null) {
					for (ZoneVente zv : zoneVenteRepository.getAllZv2(zv0, zv1, active)) {
						zoneNumbers.add(zv.getZv2());
					}
				} else {
					for (ZoneVente zv : zoneVenteRepository.getAllZv3(zv0, zv1, zv2, active)) {
						zoneNumbers.add(zv.getZv3());
					}
				}
			}
		}
		return zoneNumbers;
	}

	/**
	 * Retrieves the hierarchical numbers by level. e.g. For the specified zv0
	 * level, zv1 level data will be retrieved, such that zv2 is {@code null}
	 *
	 * The data of all the precedent levels must be specified to retrieve the data
	 * of a level. e.g. to retrieve data for zv3, data for zv0 and zv1 must be
	 * specified
	 *
	 * @param zv0 level 0
	 * @param zv1 level 1
	 * @param zv2 level 2
	 * @return the hierarchical zone numbers by level
	 * @throws JrafDomainException
	 */
	public List<ZoneVenteDTO> findLightHierarchicalByNumber(@NotNull Integer zv0, Integer zv1, Integer zv2)
			throws JrafDomainException {
		List<ZoneVenteDTO> result = new ArrayList<>();
		for (ZoneVente zv : this.findHierarchicalByNumber(zv0, zv1, zv2)) {
			result.add(ZoneVenteTransform.bo2DtoLight(zv));
		}
		return result;
	}

	private List<ZoneVente> findHierarchicalByNumber(@NotNull Integer zv0, Integer zv1, Integer zv2) {
		if (zv2 != null) {
			return zoneVenteRepository.findHierarchicalByZv2(zv0, zv1, zv2);
		} else {
			if (zv1 != null) {
				return zoneVenteRepository.findHierarchicalByZv1(zv0, zv1);
			} else {
				return zoneVenteRepository.findHierarchicalByZv0(zv0);
			}
		}
	}

	/**
	 * Retrieves the zone by level specified. e.g. If zc1 is specified, the zone
	 * matching zc1 will be retrieved irrespective of other levels
	 *
	 * @param zv0 level 0
	 * @param zv1 level 1
	 * @param zv2 level 2
	 * @param zv3 level 3
	 * @return the zone by level
	 * @throws JrafDomainException
	 */
	public List<ZoneVenteDTO> findLightByNumber(@NotNull Integer zv0, Integer zv1, Integer zv2, Integer zv3)
			throws JrafDomainException {
		List<ZoneVenteDTO> result = new ArrayList<>();

		for (ZoneVente zv : this.findByNumber(zv0, zv1, zv2, zv3)) {
			result.add(ZoneVenteTransform.bo2DtoLight(zv));
		}
		return result;
	}

	private List<ZoneVente> findByNumber(Integer zv0, Integer zv1, Integer zv2, Integer zv3) {
		if (zv3 != null) {
			return zoneVenteRepository.findAllByZv3(zv0, zv1, zv2, zv3);
		} else {
			if (zv2 != null) {
				return zoneVenteRepository.findAllByZv2(zv0, zv1, zv2);
			} else {
				if (zv1 != null) {
					return zoneVenteRepository.findAllByZv1(zv0, zv1);
				} else {
					return zoneVenteRepository.findAllByZv0(zv0);
				}
			}
		}
	}

	private ZoneVente findSuperiorZone(Integer zv0, Integer zv1, Integer zv2, Integer zv3) throws JrafDaoException {
		if (zv3 != null) {
			return zoneVenteRepository.findActiveByZv2(zv0, zv1, zv2);
		} else {
			if (zv2 != null) {
				return zoneVenteRepository.findActiveByZv1(zv0, zv1);
			} else {
				if (zv1 != null) {
					return zoneVenteRepository.findActiveByZv0(zv0);
				} else {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_000);
				}
			}
		}
	}


	/**
	 * Retrieves all the zone alpha by levels.
	 * <ul>
	 * <li>zv0Aplha -> zvAlpha where zv0 is not {@code null}  and rest levels are null<li>
	 * <li>zv1Aplha -> zvAlpha for specific zv0 where zv1 is not {@code null}  and rest levels are null<li>
	 * <li>zv2Aplha -> sub-string of zvAlpha for specific zv0 and zv1 where zv2 is not {@code null}  and rest levels are null<li>
	 * <li>zv3Aplha -> sub-string of zvAlpha for specific zv0, zv1 and zv2 where zv3 is not {@code null}<li>
	 * </ul>
	 * @param zv0 level 0
	 * @param zv1 level 1
	 * @param zv2 level 2
	 * @param zv1Alpha the zv1Aplha of the zone to be retrieved (used to get the sub-string)
	 * @return the zone alpha by level
	 */
	public Map<Integer, String> getZoneAlphas(Integer zv0, Integer zv1, Integer zv2, String zv1Alpha) {
		Map<Integer, String> zoneNumbers = new HashMap<>();
		if (zv0 == null) {
			List<ZoneVente> result = zoneVenteRepository.getAllZv0();
			for (ZoneVente zv : result) {
				zoneNumbers.put(zv.getZv0(), zv.getZvAlpha());
			}
		} else {
			if (zv1 == null) {
				for (ZoneVente zv : zoneVenteRepository.getAllZv1(zv0)) {
					zoneNumbers.put(zv.getZv1(), zv.getZvAlpha());
				}
			} else {
				if (zv2 == null) {
					for (ZoneVente zv : zoneVenteRepository.getAllZv2(zv0, zv1)) {
						zoneNumbers.put(zv.getZv2(),
								StringUtils.replaceFirst(zv.getZvAlpha(), zv1Alpha, StringUtils.EMPTY));
					}
				} else {
					for (ZoneVente zv : zoneVenteRepository.getAllZv3(zv0, zv1, zv2)) {
						zoneNumbers.put(zv.getZv3(),
								StringUtils.replaceFirst(zv.getZvAlpha(), zv1Alpha, StringUtils.EMPTY));
					}
				}
			}
		}
		return zoneNumbers;
	}

	/**
	 * Retrieves the zone by the GIN
	 *
	 * @param gin GIN of the zone to be retrieved
	 * @return zone for specified GIN
	 * @throws JrafDomainException
	 */
	public ZoneVenteDTO findLightByGin(@NotNull Long gin) throws JrafDomainException {
		ZoneVente zv = zoneVenteRepository.findByGin(gin);
		if (zv != null) {
			return ZoneVenteTransform.bo2DtoLight(zv);
		}
		return null;
	}

	/**
	 * Retrieves the active zone for the specified alpha
	 *
	 * @param zvAlpha the alpha zone
	 * @return active zone for the specified alpha zone
	 * @throws JrafDomainException
	 */
	public ZoneVenteDTO findActiveByZvAlpha(@NotNull final String zvAlpha) throws JrafDomainException {
		ZoneVenteDTO zoneVenteDTO = null;
		ZoneVente zoneVente = zoneVenteRepository.findActiveByZvAlpha(zvAlpha);
		if (zoneVente != null) {
			zoneVenteDTO = ZoneVenteTransform.bo2DtoLight(zoneVente);
		}

		return zoneVenteDTO;
	}

	/**
	 * Retrieves the Sales zone Alpha for the specified city and country. It first
	 * tries to search for exact match with city specified, if not found, it looks
	 * for similar city names.
	 *
	 * @param city        the city name
	 * @param countryCode the country code
	 * @return sales zone alpha value
	 */
	public String getZvAlphaForCity(@NotNull final String city, @NotNull final String countryCode) {
		return zoneVenteRepository.getZvAlphaForCity(city, countryCode);
	}

	/**
	 * Validates and creates the sales zone
	 *
	 * @param newZone sales zone to be created
	 * @return the new zone created with GIN populated
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public ZoneVenteDTO createLight(ZoneVenteDTO newZone) throws JrafDomainException {
		validateCreate(newZone);
		ZoneVente zoneTobeCreated = ZoneVenteTransform.dto2BoLight(newZone);
		setDefaultValues(zoneTobeCreated);
		ZoneVente zone = zoneVenteRepository.saveAndFlush(zoneTobeCreated);
		return ZoneVenteTransform.bo2DtoLight(zone);
	}

	/**
	 * Updated the sales zone:
	 * <ul>
	 * The following fields can be updated
	 * <li>Alpha zone label</li>
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
	public ZoneVenteDTO updateLight(ZoneVenteDTO modifiedZone) throws JrafDomainException {
		ZoneVente existing = zoneVenteRepository.findByGin(modifiedZone.getGin());
		if (existing == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_001);
		}
		Date today = new Date();
		existing.setDateMaj(today);
		existing.setSignatureMaj(modifiedZone.getSignatureMaj());

		// Validate alpha label
		String alphaLabel = modifiedZone.getLibZvAlpha();
		if (StringUtils.isNotBlank(alphaLabel)) {
			existing.setLibZvAlpha(alphaLabel);
		}

		// Check if Currency is valid
		String currencyCode = StringUtils.upperCase(modifiedZone.getCodeMonnaie());
		if (StringUtils.isBlank(currencyCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_376);
		} else if (!RefTableREF_MONNAIE.instance().estValide(currencyCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_325);
		}
		existing.setCodeMonnaie(modifiedZone.getCodeMonnaie());


		// Check if subType is valid
		String typeCode = StringUtils.upperCase(modifiedZone.getSousType());
		if (!RefTableREF_STYP_ZON.instance().estValide(typeCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_328);
		}
		existing.setSousType(modifiedZone.getSousType());


		// Check if Nature is valid
		String natureCode = StringUtils.upperCase(modifiedZone.getNature());
		if (StringUtils.isBlank(natureCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_261);
		} else if (!RefTableREF_NAT_ZONE.instance().estValide(natureCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_327);
		}
		existing.setNature(modifiedZone.getNature());
		existing.setZvAlpha(modifiedZone.getZvAlpha());

		Date existingStartDate = existing.getDateOuverture();
		Date modifiedStartDate = modifiedZone.getDateOuverture();
		Date existingEndDate = existing.getDateFermeture();
		Date modifiedEndDate = modifiedZone.getDateFermeture();

		// Check if End date or Start date are modified
		if (ZoneServiceHelper.isDateModified(existingEndDate, modifiedEndDate) || ZoneServiceHelper.isDateModified(existingStartDate, modifiedStartDate) ) {
			if (modifiedEndDate != null && modifiedStartDate != null
					&& ZoneServiceHelper.dateTimeComparator.compare(modifiedEndDate, modifiedStartDate) < 0) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_119);
			}
			if (modifiedEndDate != null
					&& ZoneServiceHelper.dateTimeComparator.compare(modifiedEndDate, existingStartDate) < 0) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_119);
			}
			if (modifiedStartDate != null
					&& ZoneServiceHelper.dateTimeComparator.compare(existingEndDate, modifiedStartDate) < 0) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_119);
			}


			// Check if end date and start date are valid w.r.t.superior zone
			if (ZoneServiceHelper.isDateModified(existingEndDate, modifiedEndDate)){
				validateSuperiorZoneEndDate(existing, modifiedEndDate);
			}


			ZoneServiceHelper.updateStartDate(existing,modifiedStartDate,false);
			// Check if Zone should be closed
			if (modifiedEndDate != null) {
				return ZoneVenteTransform.bo2DtoLight(closeZone(existing, modifiedEndDate, false));
			}


		}







		ZoneVente updated = zoneVenteRepository.saveAndFlush(existing);
		return ZoneVenteTransform.bo2DtoLight(updated);
	}

	private void validateSuperiorZoneEndDate(ZoneVente existing, Date modifiedEndDate) throws JrafDaoException {
		Integer zv1 = existing.getZv1();
		ZoneVente superiorZone = null;
		if (zv1 != null) {
			Integer zv0 = existing.getZv0();
			Integer zv2 = existing.getZv2();
			Integer zv3 = existing.getZv3();
			superiorZone = this.findSuperiorZone(zv0, zv1, zv2, zv3);
			if (superiorZone != null) {
				Date superiorZoneEndDate = superiorZone.getDateFermeture();
				ZoneServiceHelper.validateSuperiorEndDate(modifiedEndDate, superiorZoneEndDate);
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}
	}

	private void validateSuperiorZoneStartDate(ZoneVente existing, Date modifiedStartDate) throws JrafDaoException {
		Integer zv1 = existing.getZv1();
		ZoneVente superiorZone = null;
		if (zv1 != null) {
			Integer zv0 = existing.getZv0();
			Integer zv2 = existing.getZv2();
			Integer zv3 = existing.getZv3();
			superiorZone = this.findSuperiorZone(zv0, zv1, zv2, zv3);
			if (superiorZone != null) {
				Date superiorZoneStartDate = superiorZone.getDateOuverture();
				ZoneServiceHelper.validateSuperiorStartDate(modifiedStartDate,superiorZoneStartDate);
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}
	}

	private ZoneVente closeZone(ZoneVente existing, Date modifiedEndDate, boolean isInferiorZone)
			throws JrafDaoException {
		// Check if the zone is associated with PM
		if (pmZoneRepository.findAllActiveByIginZone(existing.getGin()) > 0) {
			log.error("Active link with PM present for zone gin : " + existing.getGin());
			throw new JrafDaoException(RefTableREF_ERREUR._REF_377 + ": LINKS(S) PRESENT");
		}

		// Update all the inferior zones
		if (existing.getZv3() == null) {
			List<ZoneVente> hierarchicalZones = this.findHierarchicalByNumber(existing.getZv0(), existing.getZv1(),
					existing.getZv2());
			for (ZoneVente subZone : hierarchicalZones) {
				this.closeZone(subZone, modifiedEndDate, true);
			}
		}

		ZoneServiceHelper.updateEndDate(existing, modifiedEndDate, isInferiorZone);
		return zoneVenteRepository.saveAndFlush(existing);
	}

	private void validateCreate(ZoneVenteDTO newZone) throws JrafDaoException {
		Integer zv0 = newZone.getZv0();
		String zvAlpha = newZone.getZvAlpha();

		// Validate Zv0 and ZvAlpha
		if (zv0 == null || zv0 <= 0 || StringUtils.isBlank(zvAlpha)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_258);
		}

		Integer zv1 = newZone.getZv1();
		Integer zv2 = newZone.getZv2();
		Integer zv3 = newZone.getZv3();

		// Validate zone number
		List<ZoneVente> existingZones = this.findByNumber(zv0, zv1, zv2, zv3);
		if (!CollectionUtils.isEmpty(existingZones)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_375);
		}

		// Validate hierarchy
		ZoneVente superiorZone = null;
		if (zv1 != null) {
			superiorZone = this.findSuperiorZone(zv0, zv1, zv2, zv3);
			if (superiorZone == null) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_373);
			}
		}

		// Validate dates
		ZoneServiceHelper.validateDates(newZone, superiorZone);

		// Validate currency code
		String currencyCode = StringUtils.upperCase(newZone.getCodeMonnaie());
		if (StringUtils.isBlank(currencyCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_376);
		} else if (!RefTableREF_MONNAIE.instance().estValide(currencyCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_325);
		}
		newZone.setCodeMonnaie(currencyCode);
	}

	private void setDefaultValues(ZoneVente zoneTobeCreated) {
		zoneTobeCreated.setDateMaj(new Date());
		zoneTobeCreated.setStatut(ZoneServiceHelper.calculateStatus(zoneTobeCreated.getDateFermeture()));
		zoneTobeCreated.setNature(RefTableREF_NAT_ZONE._REF_SEC);
		zoneTobeCreated.setSousType(RefTableREF_STYP_ZON._REF_FV);
	}

	@Transactional(readOnly = true)
	public ZoneVenteDTO findByLocalityWithoutLight(String codeVille, Date dateDebut) throws ParseException, JrafDomainException {

		List<LienIntCpZd>listLienIntCpZd = lienIntCpZdRepository.findByCityCode(codeVille, "RA");
		for (LienIntCpZd lienIntCpZd : listLienIntCpZd) {
			if (ZoneServiceHelper.comparePeriod(lienIntCpZd.getDateDebutLien(),dateDebut,lienIntCpZd.getDateFinLien(),dateDebut).equals(DateComparatorEnum.INCLUSE) && lienIntCpZd.getZoneDecoup() instanceof ZoneVente) {
				return ZoneVenteTransform.bo2Dto((ZoneVente)lienIntCpZd.getZoneDecoup());
			}
		}
		return null;
	}
}
