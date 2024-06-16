package com.afklm.repind.msv.preferences.criteria;

import com.afklm.repind.msv.preferences.model.UltimatePreferencesModel;
import com.afklm.repind.msv.preferences.model.error.BusinessErrorList;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Exceptional event criteria POJO
 *
 * @author m312812
 *
 */
public class UltimatePreferencesCriteria {

	private String gin;

    private String type;
    
    private List<UltimatePreferencesModel> data;
    
    private RequestorCriteria requestor;

    private String actionCode;

	/**
	 * @return the gin
	 */
	public String getGin() {
		return gin;
	}
	
	/**
	 * @param gin
	 *            the gin to set
	 *            Gin can't be null and its length must be 12
	 * @throws ServiceException
	 */
	public void setGin(final String gin) throws ServiceException {
		if (gin == null) {
			throw new ServiceException(BusinessErrorList.API_GIN_IS_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		if (gin.length() != 12) {
			throw new ServiceException(BusinessErrorList.API_INVALID_GIN.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.gin = gin;
	}

	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 *            Type can't be null and its length must be 3
	 * @throws ServiceException
	 */
	public void setType(String type) throws ServiceException {
		if (type == null) {
			throw new ServiceException(BusinessErrorList.API_TYPE_IS_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.type = StringUtils.upperCase(type);
	}

	public List<UltimatePreferencesModel> getData() {
		return data;
	}

	public void setData(List<UltimatePreferencesModel> data){
		this.data = data;
	}

	public RequestorCriteria getRequestor() {
		return requestor;
	}

	public void setRequestor(RequestorCriteria requestor) {
		this.requestor = requestor;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	
	
}
