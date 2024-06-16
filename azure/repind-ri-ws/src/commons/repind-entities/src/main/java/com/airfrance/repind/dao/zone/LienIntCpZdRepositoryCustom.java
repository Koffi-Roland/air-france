package com.airfrance.repind.dao.zone;

import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.ZoneVente;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LienIntCpZdRepositoryCustom {

	/**
	 * Retrieves all the links (Active and Inactive) for the specified restrictions
	 *
	 * @param countryCode    link to be retrieved for the specific country code
	 *                       (optional parameter, if not specified data for all
	 *                       countries will be retrieved)
	 * @param commercialZone link to be retrieved for the specified zone numbers
	 *                       (optional parameter, if not specified data for all zone
	 *                       numbers will be retrieved)
	 * @param usage          the usage
	 * @return the links retrieved for the specified parameters
	 */
	List<LienIntCpZd> findByUsageCountryCommercialZone(final String countryCode, final ZoneComm commercialZone,
													   @NotNull final String usage);

	/**
	 * Retrieves the active links for the specified restrictions
	 *
	 * @param countryCode    link to be retrieved for the specific country code
	 *                       (optional parameter, if not specified data for all
	 *                       countries will be retrieved)
	 * @param commercialZone link to be retrieved for the specified zone numbers
	 *                       (optional parameter, if not specified data for all zone
	 *                       numbers will be retrieved)
	 * @param usage          the usage
	 * @return the links retrieved for the specified parameters
	 */
	List<LienIntCpZd> findActiveByUsageCountryCommercialZone(final String countryCode, final ZoneComm commercialZone,
															 @NotNull final String usage);

	/**
	 * Retrieves all the links (Active and Inactive) for the specified restrictions
	 *
	 * @param countryCode link to be retrieved for the specific country code
	 *                    (optional parameter, if not specified data for all
	 *                    countries will be retrieved)
	 * @param salesZone   link to be retrieved for the specified zone numbers
	 *                    (optional parameter, if not specified data for all zone
	 *                    numbers will be retrieved)
	 * @param usage       the usage
	 * @return the links retrieved for the specified parameters
	 */
	List<LienIntCpZd> findByUsageCountrySalesZone(final String countryCode, final ZoneVente salesZone,
												  @NotNull final String usage);

	/**
	 * Retrieves the active links for the specified restrictions
	 *
	 * @param countryCode link to be retrieved for the specific country code
	 *                    (optional parameter, if not specified data for all
	 *                    countries will be retrieved)
	 * @param salesZone   link to be retrieved for the specified zone numbers
	 *                    (optional parameter, if not specified data for all zone
	 *                    numbers will be retrieved)
	 * @param usage       the usage
	 * @return the links retrieved for the specified parameters
	 */
	List<LienIntCpZd> findActiveByUsageCountrySalesZone(final String countryCode, final ZoneVente salesZone,
														@NotNull final String usage);

    /**
	 * Retrieves the data for the specified restrictions
	 *
	 * @param cityCode    	link to be retrieved for the specific city code
	 *
	 * @param usage 		the usage
	 *
	 * @return the links retrieved for the specified parameters
	 */
	List<LienIntCpZd> findByCityCode(final String cityCode, final String usage);

	/**
	 * Retrieves list of city with close ZV3 by date
	 *
	 * @param closeDate Zv3 closure date
	 *
	 * @return the city list
	 */
	List<LienIntCpZd> findCityWithClosedZv3ByDate(final LocalDate closeDate);
	
	/**
	 * Retrieves all cities linked to the given ZV3 AND the future links of the
	 * returned cities.
	 * @param zv0
	 * @param zv1
	 * @param zv2
	 * @param zv3
	 * @return the city list
	 */
	List<LienIntCpZd> findAllCityByZV3(int zv0, int zv1, int zv2, int zv3);
	
	/**
	 * Retrieves all zv3 (current and future) linked to the given cities
	 * @param cities the cities codes
	 * @return the city list
	 */
	List<LienIntCpZd> findAllZv3ByCities(List<String> cities);

}
