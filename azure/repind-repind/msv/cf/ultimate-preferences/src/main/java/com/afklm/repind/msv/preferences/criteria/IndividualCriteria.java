package com.afklm.repind.msv.preferences.criteria;

import com.afklm.repind.msv.preferences.model.error.BusinessErrorList;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import org.springframework.http.HttpStatus;


/**
 * Exceptional event criteria POJO
 *
 * @author m312812
 *
 */
public class IndividualCriteria {

	private String gin;

	/**
	 * @return the gin
	 */
	public String getGin() {
		return gin;
	}
	
	/**
	 * @param gin
	 *            the gin to set
	 * @throws ServiceException
	 */
	public void setGin(final String gin) throws ServiceException {
		if (gin.length() != 12) {
			throw new ServiceException(BusinessErrorList.API_INVALID_GIN.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.gin = gin;
	}


}
