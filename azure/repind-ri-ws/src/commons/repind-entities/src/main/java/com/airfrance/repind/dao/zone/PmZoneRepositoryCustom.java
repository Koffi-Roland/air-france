package com.airfrance.repind.dao.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.zone.PmZone;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : IPmZoneDAO.java
 * </p>
 * BO: PmZone
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Repository
public interface PmZoneRepositoryCustom {

	List<PmZone> findByPMGin(String gin) throws JrafDaoException;

	/**
	 * Retrieves all agencies linked to the given ZV AND the future links of the
	 * returned agencies.
	 * 
	 * @param zv0
	 * @param zv1
	 * @param zv2
	 * @param zv3
	 * @param date
	 * @return the city list
	 */
	List<PmZone> findAgencyZvLinks(int zv0, int zv1, int zv2, int zv3, LocalDate date);
	
	/**
	 * Retrieves all firms linked to the given ZC AND the future links of the
	 * returned firms.
	 * 
	 * @param zc1
	 * @param zc2
	 * @param zc3
	 * @param zc4
	 * @param zc5
	 * @param date
	 * @return
	 */
	List<PmZone> findFirmZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date);
	
	/**
	 * Retrieves all agencies linked to the given ZC AND the future links of the
	 * returned agencies.
	 * 
	 * @param zc1
	 * @param zc2
	 * @param zc3
	 * @param zc4
	 * @param zc5
	 * @param date
	 * @return
	 */
	List<PmZone> findAgencyZcLinks(String zc1, String zc2, String zc3, String zc4, String zc5, LocalDate date);
}
