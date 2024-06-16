package com.airfrance.repind.service.internal.unitservice.zone;

import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.entity.refTable.RefTableREF_STATUTPM;
import com.airfrance.repind.util.comparators.DateComparatorEnum;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dto.zone.ZoneDecoupDTO;
import com.airfrance.repind.entity.zone.ZoneDecoup;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeComparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

public class ZoneServiceHelper {

	protected static final DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

	public static final String ERROR_START_DATE = ": START DATE";
	public static final String ERROR_END_DATE = ": END DATE";

	public static String calculateStatus(Date endDate) {
		if (endDate != null && dateTimeComparator.compare(endDate, new Date()) < 0) {
			return RefTableREF_STATUTPM._REF_X;
		} else {
			return RefTableREF_STATUTPM._REF_A;
		}
	}

	public static void validateDates(ZoneDecoupDTO newZone, ZoneDecoup superiorZone) throws JrafDaoException {
		validateStartDate(newZone, superiorZone);
		validateEndDate(newZone, superiorZone);
	}

	public static void validateStartDate(ZoneDecoupDTO newZone, ZoneDecoup superiorZone) throws JrafDaoException {
		if (superiorZone != null) {
			Date superiorStartDate = superiorZone.getDateOuverture();
			if (newZone.getDateOuverture() == null) {
				newZone.setDateOuverture(superiorStartDate);
			} else {
				Date startDate = newZone.getDateOuverture();
				if (dateTimeComparator.compare(superiorStartDate, startDate) > 0) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + ERROR_START_DATE);
				}
			}
		} else if (newZone.getDateOuverture() == null) {
			newZone.setDateOuverture(new Date());
		}
	}

	public static void validateEndDate(ZoneDecoupDTO newZone, ZoneDecoup superiorZone) throws JrafDaoException {
		Date startDate = newZone.getDateOuverture();
		Date endDate = newZone.getDateFermeture();
		if (endDate != null && dateTimeComparator.compare(endDate, startDate) < 0) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_266);
		}

		Date superiorEndDate = superiorZone == null ? null : superiorZone.getDateFermeture();
		if (endDate == null) {
			newZone.setDateFermeture(superiorEndDate);
		} else if (superiorEndDate != null && dateTimeComparator.compare(superiorEndDate, endDate) < 0) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + ERROR_END_DATE);
		}
	}

	public static void updateEndDate(ZoneDecoup zone, Date modifiedEndDate, boolean isInferiorZone) {
		Date existingEndDate = zone.getDateFermeture();
		if (isInferiorZone && existingEndDate != null
				&& dateTimeComparator.compare(existingEndDate, modifiedEndDate) < 0) {
			modifiedEndDate = existingEndDate;
		}
		zone.setDateFermeture(modifiedEndDate);
		zone.setStatut(ZoneServiceHelper.calculateStatus(modifiedEndDate));
	}
	public static void updateStartDate(ZoneDecoup zone, Date modifiedStartDate, boolean isInferiorZone) {
		Date existingStartDate = zone.getDateOuverture();
		if (isInferiorZone && existingStartDate != null
				&& dateTimeComparator.compare(existingStartDate, modifiedStartDate) > 0 || modifiedStartDate == null) {
			modifiedStartDate = existingStartDate;
		}

		zone.setDateOuverture(modifiedStartDate);
	}

	public static void validateSuperiorEndDate(Date modifiedEndDate, Date superiorZoneEndDate) throws JrafDaoException {
		if (superiorZoneEndDate != null && (modifiedEndDate == null
				|| ZoneServiceHelper.dateTimeComparator.compare(superiorZoneEndDate, modifiedEndDate) < 0)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + ERROR_END_DATE);
		}
	}
	public static void validateSuperiorStartDate(Date modifiedStartDate, Date superiorZoneStartDate) throws JrafDaoException {
		if (superiorZoneStartDate != null && (modifiedStartDate == null
				|| ZoneServiceHelper.dateTimeComparator.compare(superiorZoneStartDate, modifiedStartDate) > 0)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_119 + ERROR_START_DATE);
		}
	}

	public static boolean isDateModified(Date oldDate, Date newDate) {
		if (oldDate == null) {
			return newDate != null;
		} else {
			return (newDate == null || !DateUtils.isSameDay(oldDate, newDate));
		}
	}

	public static DateComparatorEnum comparePeriod(Date dDateDebut1, Date dDateDebut2, Date dDateFin1, Date dDateFin2)
			throws ParseException {

		// Permet de comparer la periode 2 avec la periode 1 (c'est la periode 1 la periode de reference)
		// Ca traite les cas suivant :
		// Si la periode 2 est incluse dans la periode 1.
		// Si la periode 2 recouvre la periode 1.
		// Si la periode 2 chevauche par devant ou par derriere la periode 1.
		// Si la periode 2 est inferieure ou superieure � la periode 1.

		String datemax = "05022037";

		boolean inferior = false;
		boolean superior = false;
		boolean beginIncluded = false;
		boolean endIncluded = false;

		if (dDateFin1 == null) {
			dDateFin1 = new SimpleDateFormat("ddMMyyyy").parse(datemax);
		}
		if (dDateFin2 == null) {
			dDateFin2 = new SimpleDateFormat("ddMMyyyy").parse(datemax);
		}

		if (dDateDebut1.after(dDateFin1) || dDateDebut2.after(dDateFin2)) {
			throw new DateTimeException("DATE INVALIDE");
		}

		if (dDateDebut2.before(dDateDebut1) && dDateFin2.before(dDateDebut1)) {
			inferior = true;
		}
		if (dDateFin2.after(dDateFin1) && dDateDebut2.after(dDateFin1) ) {
			superior = true;
		}

		if (dDateDebut2.compareTo(dDateDebut1) >=0 && dDateDebut2.compareTo(dDateFin1) <=0 ) {
			beginIncluded = true;
		}

		if (dDateFin2.compareTo(dDateDebut1) >=0 && dDateFin2.compareTo(dDateFin1)<=0) {
			endIncluded = true;
		}

		if (beginIncluded && endIncluded) {
			return DateComparatorEnum.INCLUSE;
		}
		if (beginIncluded && !endIncluded) {
			return DateComparatorEnum.CHEVAUCHE_SUP;
		}
		if (!beginIncluded && endIncluded) {
			return DateComparatorEnum.CHEVAUCHE_INF;
		}
		if (inferior) {
			return DateComparatorEnum.INFERIEURE;
		}
		if (superior) {
			return DateComparatorEnum.SUPERIEURE;
		}

		return DateComparatorEnum.RECOUVRE;

	}

	private ZoneServiceHelper() {
		// Utility class
	}
}
