package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.PaysRepository;
import com.airfrance.repind.entity.reference.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefCountryDS {

	/** reference sur le repository principal */
	@Autowired
	private PaysRepository paysRepository;

	@Transactional(readOnly = true)
	public Pays get(Pays bo) throws JrafDomainException {
		return get(bo.getCodePays());
	}

	/**
	 * Retrieves the country for the specified country code
	 * 
	 * @param countryCode the country code
	 * @return {@code Pays} for the specified code, if not found {@code null}
	 */
	public Pays get(String countryCode) {
		Optional<Pays> country = paysRepository.findById(countryCode);
		if (country.isPresent()) {
			return country.get();
		}
		return null;
	}
	
	/**
	 * getCountryCode
	 * @param countryLabel in String
	 * @return The getCountryCode as <code>String</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly = true)
	public String getCountryCode(String countryLabel) throws JrafDomainException {
		Pays pays = new Pays();
		pays.setLibellePaysEn(countryLabel);
		List<Pays> paysList = paysRepository.findByLibellePaysEn(countryLabel);

		if (!paysList.isEmpty()) {
			return paysList.get(0).getCodePays();
		} else {

			throw new JrafDomainException("Country not found");
		}
	}
}
