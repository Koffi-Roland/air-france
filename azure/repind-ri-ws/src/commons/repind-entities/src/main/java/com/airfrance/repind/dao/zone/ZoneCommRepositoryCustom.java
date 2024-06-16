package com.airfrance.repind.dao.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.enums.NatureZoneEnum;

import java.util.List;

public interface ZoneCommRepositoryCustom {

	public List<ZoneComm> findByCountryCodeAndProvinceCodeAndNature(String pCountryCode, String pProvinceCode, NatureZoneEnum pNature) throws JrafDaoException;
	
	public List<ZoneComm> findByCountryCodeAndPostalCodeAndNature(String pCountryCode, String pPostalCode, NatureZoneEnum pNature) throws JrafDaoException;
	
	public List<ZoneComm> findByCountryCodeAndNature(String pCountryCode, NatureZoneEnum pNature) throws JrafDaoException;
	
	public List<ZoneComm> findActivePvValidZc(PersonneMorale pm) throws JrafDaoException;
	
}
