package com.airfrance.repind.util;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class DozerDateToAdhConverter implements CustomConverter {

	Log _log = LogFactory.getLog(DozerDateToAdhConverter.class);

	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {		
		if (source == null) {
			return null;
		}
		if (source instanceof String && destClass == String.class) {	
			if (((String)source).length() == 0)
				return null;
			try {
				String result = (String)source;
				if (!SicStringUtils.isDDMMYYYFormatDate(result)) {
					result = SicStringUtils.formatDateToAdh(result); 
				}
				return result;
			} catch (JrafApplicativeException e) {
				throw new MappingException(	"Converter DozerDateToAdhConverter exception ( "+e.getMessage()+" ). Arguments passed in were:"
								+ destination + " and " + source);
			}
		} else {
			throw new MappingException(
					"Converter DozerDateToAdhConverter used incorrectly. Arguments passed in were:"
							+ destination + " and " + source);
		}
	}
}
