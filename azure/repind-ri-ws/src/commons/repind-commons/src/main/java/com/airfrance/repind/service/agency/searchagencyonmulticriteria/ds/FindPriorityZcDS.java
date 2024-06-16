package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyInformationsDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.CommercialZonesDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.DecoupZoneDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("AgencyFindPriorityZcDS")
public class FindPriorityZcDS extends AbstractDS {


	/* =============================================== */
	/* LOGGER */
	/* =============================================== */
	private static Log LOGGER = LogFactory.getLog(FindPriorityZcDS.class);

	/* =============================================== */
	/* PUBLIC METHODS */
	/* =============================================== */

	/**
	 * Calculates the priorityZC Loop through ACTIVE and PRIVILEGED pmZones
	 * if((pmZone.getLienPrivilegie() != null) &&
	 * (pmZone.getLienPrivilegie().equalsIgnoreCase("o")) &&
	 * (zc.getDateFermeture() != null) && (zc.getDateFermeture().after())
	 * 
	 * 
	 * => We return the most recent ZoneComm zc =
	 * (ZoneComm)pmZone.getZoneDecoup()
	 * 
	 * @throws InvalidParameterException
	 * 
	 */
	public void findPriorityZc(Agence agence, AgencyInformationsDTO agencyInformationsDTO)
			throws InvalidParameterException {

		if (agencyInformationsDTO == null)
			throw new InvalidParameterException("agencyInformationsDTO can't be null !");

		// Returned value
		ZoneComm priorityZc = null;

		if (agence != null) {
			priorityZc = returnPriorityZc(agence);

			// Version 1 of SearchAgencyOnMultiCriteria
			agencyInformationsDTO.setCommercialZone(fromZcToString(priorityZc));

			if (priorityZc != null) {
				
				/* Version 2 of SearchAgencyOnMultiCriteria */
				DecoupZoneDTO priorityZd = new DecoupZoneDTO();
				priorityZd.setGin(priorityZc.getGin().toString());
				priorityZd.setCreationDate(priorityZc.getDateOuverture());
				priorityZd.setStatus(priorityZc.getStatut());
				priorityZd.setType("ZC");
				priorityZd.setSubType(priorityZc.getSousType());
				priorityZd.setNature(priorityZc.getNature());

				CommercialZonesDTO _priorityZc = new CommercialZonesDTO();
				_priorityZc.setZc1(priorityZc.getZc1());
				_priorityZc.setZc2(priorityZc.getZc2());
				_priorityZc.setZc3(priorityZc.getZc3());
				_priorityZc.setZc4(priorityZc.getZc4());
				_priorityZc.setZc5(priorityZc.getZc5());
				// TODO: Maybe add more info if needed ?

				// Link with priorityZd ...
				priorityZd.setCommercialZones(_priorityZc);

				// Link with agencyInformations ...
				agencyInformationsDTO.addDecoupZone(priorityZd);

				/* End - Version 2 */
			}
		}
	}

	public ZoneComm returnPriorityZc(Agence agence) {

		ZoneComm priorityZc = null;

		// Current date (priorityZC.getDateFermeture()) has to be after the
		// currentDate
		Date currentDate = new Date();

		if (agence.getPmZones() != null && !agence.getPmZones().isEmpty()) {

			for (PmZone pmZone : agence.getPmZones()) {

				if (pmZone.getZoneDecoup() != null && pmZone.getZoneDecoup().getClass().equals(ZoneComm.class)) {

					ZoneComm zc = (ZoneComm) pmZone.getZoneDecoup();

					// Treating ACTIVE (AND) PRIVILEGED ZC...
					if (pmZone.getLienPrivilegie() != null
							&& (zc.getDateFermeture() == null || zc.getDateFermeture().after(currentDate))
							&& pmZone.getLienPrivilegie().equalsIgnoreCase("o")) {

						if (priorityZc == null) {
							priorityZc = zc;
						} else {
							// If ZC has been updated most recently than
							// previous "priorityZc"...
							if (zc.getDateMaj() != null) {

								if (priorityZc.getDateMaj() != null && zc.getDateMaj().after(priorityZc.getDateMaj())) {
									priorityZc = zc;
								}

								if (priorityZc.getDateMaj() == null) {
									priorityZc = zc;
								}
							}
						}
					}
				}
			}
		}
		if (priorityZc == null) {
			LOGGER.info("No priority ZC found");
		}
		if (priorityZc != null && priorityZc.getDateMaj() == null) {
			LOGGER.info("priorityZc.getDateMaj() == null");
		}
		return priorityZc;
	}

	public List<PmZone> returnActiveZc(Agence agence) {
		/*
		 * Returned value
		 */
		List<PmZone> activeZcList = null;
		/*
		 * Current date (priorityZC.getDateFermeture() has to be after the
		 * currentDate
		 */
		Date currentDate = new Date();

		if ((agence.getPmZones() != null) && (!agence.getPmZones().isEmpty())) {
			for (PmZone pmZone : agence.getPmZones()) {
				if ((pmZone.getZoneDecoup() != null && pmZone.getZoneDecoup().getClass().equals(ZoneComm.class))
						&& (pmZone.getDateFermeture() == null || pmZone.getDateFermeture().after(currentDate))) {
					/*
					 * Treating ACTIVE ZC
					 */
					ZoneComm zc = (ZoneComm) pmZone.getZoneDecoup();

					if (zc.getDateFermeture() == null
							|| (zc.getDateFermeture() != null && zc.getDateFermeture().after(currentDate))) {
						if (activeZcList == null) {
							activeZcList = new ArrayList<PmZone>();
						}
						activeZcList.add(pmZone);
					}
				}
			}
		}
		if (activeZcList == null) {
			LOGGER.info("No active ZC found");
		}
		return activeZcList;
	}

	/* =============================================== */
	/* PRVATE METHODS */
	/* =============================================== */

	/**
	 * Concatenates zoneCommerciale.ZC1, zoneCommerciale.ZC2,
	 * zoneCommerciale.ZC3, zoneCommerciale.ZC4, zoneCommerciale.ZC5
	 * 
	 * @param zoneCommerciale
	 * @return
	 */
	private String fromZcToString(ZoneComm zoneCommerciale) {
		StringBuffer zcBuffer = new StringBuffer("");
		if (zoneCommerciale != null) {
			if (zoneCommerciale.getZc1() != null) {
				zcBuffer.append(zoneCommerciale.getZc1());
			}
			if (zoneCommerciale.getZc2() != null) {
				zcBuffer.append(zoneCommerciale.getZc2());
			}
			if (zoneCommerciale.getZc3() != null) {
				zcBuffer.append(zoneCommerciale.getZc3());
			}
			if (zoneCommerciale.getZc4() != null) {
				zcBuffer.append(zoneCommerciale.getZc4());
			}
			if (zoneCommerciale.getZc5() != null) {
				zcBuffer.append(zoneCommerciale.getZc5());
			}
		}
		return zcBuffer.toString();
	}

}
