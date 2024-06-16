package com.airfrance.batch.common.enums;

import java.util.Calendar;

/*
 * A WorkingDaysEnum is composed of a name and a day 
 */
public enum WorkingDaysEnum { MONDAY(Calendar.MONDAY),
		TUESDAY(Calendar.TUESDAY),
		WEDNESDAY(Calendar.WEDNESDAY),
		THURSDAY(Calendar.THURSDAY), 
		FRIDAY(Calendar.FRIDAY);

		private int value;
		
		private WorkingDaysEnum(int value) {
			this.value = value;
		}
		
		public static boolean isWorkingDay(int day)
		{
			boolean workingDay = false;
			
			for (WorkingDaysEnum b : WorkingDaysEnum.values()) {
				if (day == b.value) {
					workingDay = true;
					break;
				}
		}
		
		return workingDay;
		}
};    
