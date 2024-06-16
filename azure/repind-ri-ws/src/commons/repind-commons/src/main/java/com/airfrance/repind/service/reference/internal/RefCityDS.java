package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.dao.reference.RefVilleIataRepository;
import com.airfrance.repind.dao.reference.RefVillesAnnexesRepository;
import com.airfrance.repind.dto.reference.RefVilleIataDTO;
import com.airfrance.repind.dto.reference.RefVilleIataTransform;
import com.airfrance.repind.entity.reference.Pays;
import com.airfrance.repind.entity.reference.RefVilleIata;
import com.airfrance.repind.entity.reference.RefVillesAnnexes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefCityDS.java
 * </p>
 * 
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Service
public class RefCityDS {

	/** logger */
	private static final Log log = LogFactory.getLog(RefCityDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** reference sur le dao principal */
	@Autowired
	private RefVilleIataRepository refVilleIataRepository;

	/** references on associated Repository */
	@Autowired
	private PaysRepository paysRepository;

	/** references on associated DAOs */
	@Autowired
	private RefVillesAnnexesRepository refVillesAnnexesRepository;

	@Transactional(readOnly = true)
	public RefVilleIataDTO get(RefVilleIataDTO dto) throws JrafDomainException {
		return get(dto.getScodeVille());
	}

	@Transactional(readOnly = true)
	public RefVilleIataDTO get(String id) throws JrafDomainException {
		Optional<RefVilleIata> refVilleIata = refVilleIataRepository.findById(id);

		// transformation light bo -> dto
		if (!refVilleIata.isPresent()) {
			return null;
		}
		return RefVilleIataTransform.bo2DtoLight(refVilleIata.get());
	}

	public RefVilleIataRepository getRefVilleIataRepository() {
		return refVilleIataRepository;
	}

	public void setRefVilleIataRepository(RefVilleIataRepository refVilleIataRepository) {
		this.refVilleIataRepository = refVilleIataRepository;
	}

	public void setPaysRepository(PaysRepository paysRepository) {
		this.paysRepository = paysRepository;
	}

	public RefVillesAnnexesRepository getRefVillesAnnexesRepository() {
		return refVillesAnnexesRepository;
	}

	public void setRefVillesAnnexesRepository(RefVillesAnnexesRepository refVillesAnnexesRepository) {
		this.refVillesAnnexesRepository = refVillesAnnexesRepository;
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_UqXrzxeJEeKJFbgRY_ODIggem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

    
	/**
	 * Retrieves the unique city code specified for the city name and country code
	 * 
	 * @param cityLabel   city name
	 * @param countryCode country code
	 * @return the unique city code {@code String} specified for the city name and
	 *         country code, else {@code null}
	 */
	@Transactional(readOnly = true)
	public String getUniqueCityCode(@NotNull final String cityLabel, @NotNull final String countryCode) {
		// Exact match
		List<RefVilleIata> responseVille = refVilleIataRepository.findByCityCountry(cityLabel, countryCode);
		if (responseVille != null && responseVille.size() == 1) {
			return responseVille.get(0).getScodeVille();
		} else {
			List<RefVillesAnnexes> responseAnnex = refVillesAnnexesRepository.findByCityCountry(cityLabel, countryCode);
			if (responseAnnex != null && responseAnnex.size() == 1) {
				return responseAnnex.get(0).getCodeVillePrincipal();
			}
			// Similar Match
			else {
				findSimilarMatch(cityLabel, countryCode);
			}
		}
		return null;
	}

	private String findSimilarMatch(final String cityLabel, final String countryCode) {
		List<RefVilleIata> responseVille = refVilleIataRepository.findSimilarByCityCountry(cityLabel, countryCode);
		if (responseVille != null && responseVille.size() == 1) {
			return responseVille.get(0).getScodeVille();
		} else {
			// Annex
			List<RefVillesAnnexes> responseAnnex = refVillesAnnexesRepository.findSimilarByCityCountry(cityLabel,
					countryCode);
			if (responseAnnex != null && responseAnnex.size() == 1) {
				return responseAnnex.get(0).getCodeVillePrincipal();
			}
		}
		return null;
	}
	
	/**
	 * getCityCode
	 * 
	 * @param cityLabel   in String
	 * @param countryCode in String
	 * @return The getCityCode as <code>String</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public String getCityCode(String cityLabel, String countryCode) throws JrafDomainException {
		/* PROTECTED REGION ID(_UqXr0ReJEeKJFbgRY_ODIg) ENABLED START */

		String cityCode;
		Optional<Pays> p = Optional.empty();
		RefVilleIata vi = new RefVilleIata();
		vi.setLibelle(cityLabel);
		if (countryCode != null) {
			p = paysRepository.findById(countryCode);
			vi.setPays(p.get());
		}

		List<RefVilleIata> dtos = refVilleIataRepository.findAll(Example.of(vi));

		if (dtos != null && !dtos.isEmpty()) {
			cityCode = dtos.get(0).getScodeVille();
		} else {

			RefVillesAnnexes va = new RefVillesAnnexes();
			va.setLibelle(cityLabel);
			if (countryCode != null) {
				va.setPays(p.get());
			}

			// Try on villes annexes
			List<RefVillesAnnexes> listVa = refVillesAnnexesRepository.findAll(Example.of(va));

			if (listVa == null || listVa.isEmpty()) {

				throw new JrafDomainException("City not found");

			} else {
				cityCode = listVa.get(0).getCodeVillePrincipal();
			}
		}
		return cityCode;
	}

	/**
	 * getCountryCapitol
	 * 
	 * @param countryCode in String
	 * @return The getCountryCapitol as <code>String</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public String getCountryCapitol(String countryCode) throws JrafDomainException {
		return paysRepository.findById(countryCode).get().getCodeCapitale();
	}
}
