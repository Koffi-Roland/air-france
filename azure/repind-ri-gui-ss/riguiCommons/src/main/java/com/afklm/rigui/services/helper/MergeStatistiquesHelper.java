package com.afklm.rigui.services.helper;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MergeStatistiquesHelper {

	String[] TABLE_HEADER = { "Gin", "Signature Modification", "Date Modification", "Site Modification", "Gin Merged",
			"Lastname", "Firstname" };
	/**
	 * Generate an InputStream from a list of TrackingDTO in order to provide a
	 * CSV File
	 * 
	 * @param trackingDTO
	 * @return
	 */
	public InputStream generateCSV(List<Object[]> listTracking) {
		return GenerateCSVHelper.generateCSVObject(listTracking, TABLE_HEADER);
	}
}
