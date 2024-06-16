package com.afklm.rigui.criteria.merge;

import javax.validation.constraints.NotNull;

public class MergeStatistiquesCriteria {

	public static final int ONE_YEARS_BEFORE = 365;
	public static final int ONE_WEEK_BEFORE = 7;
	/**
	 * Index to get next result
	 */
	@NotNull
	private int dayInPast;

	public MergeStatistiquesCriteria(Integer dayInPast) {
		super();
		this.setDayInPast(dayInPast);
	}

	/**
	 * @return the index
	 */
	public int getDayInPast() {
		return dayInPast;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setDayInPast(Integer dayInPast) {
		if (dayInPast == null || dayInPast < 0) {
			this.dayInPast = 0;
		} else {
			this.dayInPast = dayInPast.intValue();
		}
	}

}
