package com.airfrance.repind.service.internal.unitservice.zone;

import com.airfrance.repind.entity.refTable.RefTablePAYS;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.zone.IntervalleCodesPostauxRepository;
import com.airfrance.repind.dao.zone.LienIntCpZdRepository;
import com.airfrance.repind.dao.zone.ZoneCommRepository;
import com.airfrance.repind.dao.zone.ZoneVenteRepository;
import com.airfrance.repind.dto.zone.*;
import com.airfrance.repind.entity.zone.*;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LienIntCpZdUs {

	/**
	 * Usage used for creating ZC attribution
	 */
	public static final String USAGE_FA = "FA";

	/**
	 * Usage used for creating ZV attribution
	 */
	public static final String USAGE_ZV = "ZV";

	/**
	 * Country and zone link DAO
	 */
	@Autowired
	private LienIntCpZdRepository lienIntCpZdRepository;

	/**
	 * Commercial zone DAO
	 */
	@Autowired
	private ZoneCommRepository zcRepository;

	/**
	 * Sales zone DAO
	 */
	@Autowired
	private ZoneVenteRepository zvRepository;
	
	/**
	 * Intervalle Codes Postaux DAO
	 */
	@Autowired
	private IntervalleCodesPostauxRepository intervalleCodesPostauxRepository;

	/**
	 * Retrieves the links for the specified country and zone numbers. It retrieves
	 * the data for {@link LienIntCpZd#getUsage()} {@literal FA}
	 * 
	 * @param countryCode    link to be retrieved for the specific country code
	 *                       (optional parameter, if not specified data for all
	 *                       countries will be retrieved)
	 * @param commercialZone link to be retrieved for the specified zone numbers
	 *                       (optional parameter, if not specified data for all zone
	 *                       numbers will be retrieved)
	 * @return the links retrieved for the specified parameters
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly=true)
	public List<LienIntCpZdDTO> findByCountryCommercialZone(final String countryCode, final ZoneCommDTO commercialZone)
			throws JrafDomainException {
		ZoneComm zc = commercialZone != null ? ZoneCommTransform.dto2BoLight(commercialZone) : null;
		List<LienIntCpZd> response = lienIntCpZdRepository.findByUsageCountryCommercialZone(countryCode, zc, USAGE_FA);
		List<LienIntCpZdDTO> result = new ArrayList<>();
		for (LienIntCpZd link : response) {
			LienIntCpZdDTO linkPopulated = LienIntCpZdTransform.bo2DtoLight(link);
			LienIntCpZdTransform.bo2DtoWithIntervalleCodesPostaux(link, linkPopulated);
			result.add(linkPopulated);
		}
		return result;
	}

	/**
	 * Validates and creates a new link
	 * 
	 * @param newLink the new link to be created
	 * @return the new link with updated key
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public LienIntCpZdDTO create(@NotNull final LienIntCpZdDTO newLink) throws JrafDomainException {
		ZoneDecoup zd = validateAndGetZone(newLink);
		IntervalleCodesPostaux icp = validateAndGetIntervalleCodesPostaux(newLink);
		if (zd instanceof ZoneComm) {
			newLink.setUsage(USAGE_FA);
		} else if (zd instanceof ZoneVente) {
			newLink.setUsage(USAGE_ZV);
		}
		newLink.setDateMaj(new Date());
		LienIntCpZd newLinkBo = LienIntCpZdTransform.dto2BoLight(newLink);
		newLinkBo.setZoneDecoup(zd);
		newLinkBo.setIntervalleCodesPostaux(icp);
		LienIntCpZd saved = lienIntCpZdRepository.saveAndFlush(newLinkBo);
		LienIntCpZdDTO savedDto = new LienIntCpZdDTO();
		LienIntCpZdTransform.bo2Dto(saved, savedDto);
		return savedDto;
	}

	/**
	 * Validates and updates the zone of the link.
	 * 
	 * @param updateLink the link to be updated
	 * @return the new link with updated key
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public LienIntCpZdDTO update(@NotNull final LienIntCpZdDTO updateLink) throws JrafDomainException {
		Optional<LienIntCpZd> linkBo = lienIntCpZdRepository.findById(updateLink.getCle());
		if (!linkBo.isPresent()) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_242);
		}
		LienIntCpZd toBeUpdatedBo = linkBo.get();
		ZoneDecoup zd = validateAndGetZone(updateLink);
		toBeUpdatedBo.setZoneDecoup(zd);
		toBeUpdatedBo.setDateMaj(new Date());
		toBeUpdatedBo.setSignatureMaj(updateLink.getSignatureMaj());
		LienIntCpZd saved = lienIntCpZdRepository.saveAndFlush(toBeUpdatedBo);
		LienIntCpZdDTO updatedDto = new LienIntCpZdDTO();
		LienIntCpZdTransform.bo2Dto(saved, updatedDto);
		return updatedDto;
	}

	/**
	 * Create a new link and close the current one.
	 *
	 * @param createLink the link to be created
	 * @return the new link
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public LienIntCpZdDTO createZv3(@NotNull final LienIntCpZdDTO createLink) throws JrafDomainException {
		Date closeDate = new DateTime(createLink.getDateDebutLien()).minusDays(1).toDate();
		Optional<LienIntCpZd> currentLink = lienIntCpZdRepository.getCurrentLinkByCity(createLink.getCodeVille());
		List<LienIntCpZd> futureLinks = lienIntCpZdRepository.getFutureLinksByCity(createLink.getCodeVille());

		LienIntCpZd linkToSave = LienIntCpZdTransform.dto2BoLight(createLink);
		linkToSave.setZoneDecoup(ZoneDecoupTransform.dto2BoLight(createLink.getZoneDecoup()));

		LienIntCpZd saved = lienIntCpZdRepository.saveAndFlush(linkToSave);

		currentLink.ifPresent(link -> {
			link.setDateFinLien(closeDate);
			lienIntCpZdRepository.save(link);
		});

		futureLinks.forEach(link -> {
			lienIntCpZdRepository.delete(link);
		});

		LienIntCpZdTransform.bo2Dto(saved, createLink);

		return createLink;
	}

	/**
	 * Deletes an existing link
	 * 
	 * @param id the id to delete
	 * @return true if link are deleted
	 */
	public boolean delete(@NotNull final Long id) {
		try {
			lienIntCpZdRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Retrieves the links for the specified country and zone numbers. It retrieves
	 * the data for {@link LienIntCpZd#getUsage()} {@literal ZV}
	 * 
	 * @param countryCode link to be retrieved for the specific country code
	 *                    (optional parameter, if not specified data for all
	 *                    countries will be retrieved)
	 * @param saleZone    link to be retrieved for the specified zone numbers
	 *                    (optional parameter, if not specified data for all zone
	 *                    numbers will be retrieved)
	 * @return the links retrieved for the specified parameters
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly=true)
	public List<LienIntCpZdDTO> findByCountrySalesZone(final String countryCode, final ZoneVenteDTO saleZone)
			throws JrafDomainException {
		ZoneVente zv = saleZone != null ? ZoneVenteTransform.dto2BoLight(saleZone) : null;
		List<LienIntCpZd> response = lienIntCpZdRepository.findActiveByUsageCountrySalesZone(countryCode, zv, USAGE_ZV);
		List<LienIntCpZdDTO> result = new ArrayList<>();
		for (LienIntCpZd link : response) {
			LienIntCpZdDTO linkPopulated = LienIntCpZdTransform.bo2DtoLight(link);
			LienIntCpZdTransform.bo2DtoWithIntervalleCodesPostaux(link, linkPopulated);
			result.add(linkPopulated);
		}
		return result;
	}

	private ZoneDecoup validateAndGetZone(final LienIntCpZdDTO newLink) throws JrafDaoException {
		String countryCode = newLink.getCodePays();
		ZoneDecoupDTO zoneDecoup = newLink.getZoneDecoup();
		validateCountry(countryCode, (newLink.getCle() == null && USAGE_ZV.equals(newLink.getUsage())));
		if (zoneDecoup == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_173);
		}
		return validateZone(countryCode, zoneDecoup);
	}

	private ZoneDecoup validateAndGetZone3(final LienIntCpZdDTO newLink) throws JrafDaoException {
		String cityCode = newLink.getCodeVille();
		ZoneDecoupDTO zoneDecoup = newLink.getZoneDecoup();
		if (zoneDecoup == null || zoneDecoup.getGin() == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_173);
		}
		return validateZone(cityCode, zoneDecoup);
	}

	private ZoneDecoup validateZone(String countryCode, ZoneDecoupDTO zoneDecoup) throws JrafDaoException {
		ZoneDecoup response = null;
		List<LienIntCpZd> links = null;
		Date nowMidnight = SicDateUtils.midnight(new Date());
		if (zoneDecoup instanceof ZoneCommDTO) {
			// If zone GIN is not provided, find the exact zone
			if (zoneDecoup.getGin() == null) {
				ZoneCommDTO zoneComm = (ZoneCommDTO) zoneDecoup;
				List<ZoneComm> zonesFound = zcRepository.findAllByZc5(zoneComm.getZc1(), zoneComm.getZc2(), zoneComm.getZc3(), zoneComm.getZc4(), zoneComm.getZc5());
				if (CollectionUtils.isEmpty(zonesFound)) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_346);
				}
				if (zonesFound.size() > 1) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_365);
				}
				if (!CollectionUtils.isEmpty(zonesFound) && zonesFound.size() == 1 && zonesFound.get(0) != null) {					
					zoneDecoup.setGin(zonesFound.get(0).getGin());
				}
			}
			ZoneComm zc = zcRepository.findByGin(zoneDecoup.getGin());
			// Only active zones
			if (zc != null && (zc.getDateFermeture() == null || zc.getDateFermeture().after(nowMidnight))) {
				response = zc;
				links = lienIntCpZdRepository.findActiveByUsageCountryCommercialZone(countryCode, zc, USAGE_FA);
			}
		} else if (zoneDecoup instanceof ZoneVenteDTO) {
			if (zoneDecoup.getGin() == null) {
				// If zone GIN is not provided, find the exact zone
				ZoneVenteDTO zoneVente = (ZoneVenteDTO) zoneDecoup;
				List<ZoneVente> zonesFound = zvRepository.findAllByZv3(zoneVente.getZv0(), zoneVente.getZv1(), zoneVente.getZv2(), zoneVente.getZv3());
				if (CollectionUtils.isEmpty(zonesFound)) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_346);
				}
				if (zonesFound.size() > 1) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_365);
				}
				if (!CollectionUtils.isEmpty(zonesFound) && zonesFound.size() == 1 && zonesFound.get(0) != null) {					
					zoneDecoup.setGin(zonesFound.get(0).getGin());
				}
			}
			ZoneVente zv = zvRepository.findByGin(zoneDecoup.getGin());
			// Only active zones
			if (zv != null && (zv.getDateFermeture() == null || zv.getDateFermeture().after(nowMidnight))) {
				response = zv;
				links = lienIntCpZdRepository.findByUsageCountrySalesZone(countryCode, zv, USAGE_ZV);
			}
		}
		if (response == null) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_173);
		}

		if (!CollectionUtils.isEmpty(links)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_244);
		}
		
		return response;
	}

	private void validateCountry(final String countryCode, boolean createZvLink) throws JrafDaoException {
		if (StringUtils.isBlank(countryCode)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_192);
		} else if (!RefTablePAYS.instance().estValide(countryCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_131);
		}
		if (createZvLink) {
			List<LienIntCpZd> links = lienIntCpZdRepository.findByUsageCountrySalesZone(countryCode, null, USAGE_ZV);
			if (!CollectionUtils.isEmpty(links)) {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_001 + ": LINK ZV3-COUNTRY CODE ALREADY USED");
			}
		}
	}
	
	private IntervalleCodesPostaux validateAndGetIntervalleCodesPostaux(final LienIntCpZdDTO newLink) {
		if (newLink.getIntervalleCodesPostaux() == null || (newLink.getIntervalleCodesPostaux().getCodePostalDebut() == null 
				&& newLink.getIntervalleCodesPostaux().getCodePostalFin() == null)) {			
			return null;
		}
		
		String codePays = newLink.getCodePays();
		String codePostalDebut = newLink.getIntervalleCodesPostaux().getCodePostalDebut();
		String codePostalFin = newLink.getIntervalleCodesPostaux().getCodePostalFin();
		
		List<IntervalleCodesPostaux> icp = intervalleCodesPostauxRepository.findByCodePaysAndCodePostalDebutAndCodePostalFin(codePays, codePostalDebut, codePostalFin);
		if (!CollectionUtils.isEmpty(icp)) {
			return icp.get(0);
		}
		
		IntervalleCodesPostaux newIcpBo = new IntervalleCodesPostaux();
		newIcpBo.setCodePays(codePays);
		newIcpBo.setCodePostalDebut(codePostalDebut);
		newIcpBo.setCodePostalFin(codePostalFin);
		return intervalleCodesPostauxRepository.saveAndFlush(newIcpBo);
	}

	/**
	 * Retrieves the links for the specified zv3 closure date. It retrieves the data
	 * for {@link LienIntCpZd#getUsage()} {@literal FA}
	 *
	 * @param closeDate Zv3 closure date
	 *
	 * @return the links retrieved for the specified parameter
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly=true)
	public List<LienIntCpZdDTO> findCityWithClosedZv3ByDate(final LocalDate closeDate) throws JrafDomainException {
		List<LienIntCpZd> response = lienIntCpZdRepository.findCityWithClosedZv3ByDate(closeDate);
		List<LienIntCpZdDTO> result = new ArrayList<>();
		for (LienIntCpZd link : response) {
			LienIntCpZdDTO linkPopulated = LienIntCpZdTransform.bo2DtoLight(link);
			LienIntCpZdTransform.bo2Dto(link, linkPopulated);
			result.add(linkPopulated);
		}
		return result;
	}

	/**
	 * Retrieves all cities linked to the given ZV3 AND the future links of the
	 * returned cities.
	 *
	 * @return the links retrieved
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly=true)
	public List<LienIntCpZdDTO> findAllCityByZV3(int zv0, int zv1, int zv2, int zv3) throws JrafDomainException {
		List<LienIntCpZd> response = lienIntCpZdRepository.findAllCityByZV3(zv0, zv1, zv2, zv3);
		List<LienIntCpZdDTO> result = new ArrayList<>();
		for (LienIntCpZd link : response) {
			LienIntCpZdDTO linkPopulated = LienIntCpZdTransform.bo2DtoLight(link);
			LienIntCpZdTransform.bo2Dto(link, linkPopulated);
			result.add(linkPopulated);
		}
		return result;
	}

	/**
	 * Retrieves all zv3 (current and future) linked to the given cities
	 *
	 * @return the links retrieved
	 * @throws JrafDomainException
	 */
	public List<LienIntCpZdDTO> findAllZv3ByCities(List<String> cities) throws JrafDomainException {
		List<LienIntCpZd> response = lienIntCpZdRepository.findAllZv3ByCities(cities);
		List<LienIntCpZdDTO> result = new ArrayList<>();
		for (LienIntCpZd link : response) {
			LienIntCpZdDTO linkPopulated = LienIntCpZdTransform.bo2DtoLight(link);
			LienIntCpZdTransform.bo2Dto(link, linkPopulated);
			result.add(linkPopulated);
		}
		return result;
	}
}
