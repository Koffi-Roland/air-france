package com.airfrance.repind.util;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class DozerDateToWsdlConverter implements CustomConverter {

	Log _log = LogFactory.getLog(DozerDateToWsdlConverter.class);

	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		if (source instanceof String && destClass == String.class) {
			if (((String)source).length() == 0)
				return null;			
			try {
				return SicStringUtils.formatDateToWsdl((String)source);
			} catch (JrafApplicativeException e) {
				throw new MappingException(	"Converter DozerDateToWsdlConverter exception ( "+e.getMessage()+" ). Arguments passed in were:"
								+ destination + " and " + source);
			}			
		} else {
			throw new MappingException(
					"Converter DozerDateToWsdlConverter used incorrectly. Arguments passed in were:"
							+ destination + " and " + source);
		}
	}
}
