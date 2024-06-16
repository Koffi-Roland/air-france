package com.airfrance.repind.util.ut.comparators;

import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.util.comparators.TelecomsDTOComparator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class TelecomsDTOComparatorTest extends TelecomsDTOComparator {

	@Test
	public void testCompare() {
		
		Date today = new Date();
		Date yesterday = getYesterday();
		
		String lower = "10000";
		String greater = "30000";
		
		Assert.assertSame(OLDER, compare(getTelecomsDTO(yesterday, lower), getTelecomsDTO(today, greater)));
		Assert.assertSame(MORE_RECENT, compare(getTelecomsDTO(today, lower), getTelecomsDTO(yesterday, greater)));
		// Assert.assertSame(OLDER, compare(getTelecomsDTO(today, lower), getTelecomsDTO(today, greater)));
		// Assert.assertSame(MORE_RECENT, compare(getTelecomsDTO(today, greater), getTelecomsDTO(today, lower)));
		Assert.assertSame(SAME, compare(getTelecomsDTO(today, lower), getTelecomsDTO(today, lower)));
		
	}
	
	@Test
	public void testCompareDate() {
		
		Date today = new Date();
		Date yesterday = getYesterday();
		
		Assert.assertSame(SAME, compareDate(null, null));
		Assert.assertSame(OLDER, compareDate(yesterday, today));
		Assert.assertSame(MORE_RECENT, compareDate(today, yesterday));
		Assert.assertSame(SAME, compareDate(today, today));
	}
	
	@Test
	public void testCompareSain() {
		
		String lower = "10000";
		String greater = "30000";
		
		Assert.assertSame(SAME, compareSain(null, null));
		Assert.assertSame(GREATER, compareSain(lower, greater));
		Assert.assertSame(LOWER, compareSain(greater, lower));
		Assert.assertSame(SAME, compareSain(lower, lower));
	}
	
	@Test
	public void testCheckDate() {
		
		Date today = new Date();
		
		Assert.assertSame(SAME, checkDate(null, null));
		Assert.assertSame(MORE_RECENT, checkDate(null, today));
		Assert.assertSame(OLDER, checkDate(today, null));
		Assert.assertNull(checkDate(today, today));
	}
	
	@Test
	public void testCheckSain() {
		
		String sample = "012345";
		
		Assert.assertSame(SAME, checkSain(null, null));
		Assert.assertSame(LOWER, checkSain(null, sample));
		Assert.assertSame(GREATER, checkSain(sample, null));
		Assert.assertNull(checkSain(sample, sample));
	}
	
	private TelecomsDTO getTelecomsDTO(Date modificationDate, String sain) {
		TelecomsDTO telecomDTO = new TelecomsDTO();
		telecomDTO.setSain(sain);
		telecomDTO.setDdate_modification(modificationDate);
		return telecomDTO;
	}
	
	private Date getYesterday() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		return yesterday.getTime();
	}
	
}
