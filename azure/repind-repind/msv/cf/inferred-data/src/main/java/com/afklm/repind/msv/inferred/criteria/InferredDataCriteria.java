package com.afklm.repind.msv.inferred.criteria;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

import com.afklm.repind.msv.inferred.model.InferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.services.exception.ServiceException;

/**
 * Exceptional event criteria POJO
 *
 * @author m312812
 *
 */
public class InferredDataCriteria {

	private Long id;
	
    private String status;
    
	private String gin;

    private String type;
    
    private String application;
    
    private List<InferredDataModel> data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) throws ServiceException {
		if (id == null) {
			throw new ServiceException(BusinessErrorList.API_ID_IS_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 *            Status can't be null and its length must be 1
	 * @throws ServiceException
	 */
	public void setStatus(String status) throws ServiceException {
		if (status == null) {
			throw new ServiceException(BusinessErrorList.API_STATUS_IS_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		if (status.length() != 1) {
			throw new ServiceException(BusinessErrorList.API_INVALID_STATUS.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.status = StringUtils.upperCase(status);
	}

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
		if (type.length() != 3) {
			throw new ServiceException(BusinessErrorList.API_INVALID_TYPE.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.type = StringUtils.upperCase(type);
	}

	public String getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 *            Application can't be null and its length must under 16
	 * @throws ServiceException
	 */
	public void setApplication(String application) throws ServiceException {
		if (application == null || application.length() == 0) {
			throw new ServiceException(BusinessErrorList.API_APPLICATION_IS_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		if (application.length() > 16) {
			throw new ServiceException(BusinessErrorList.API_INVALID_APPLICATION.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.application = application;
	}

	public List<InferredDataModel> getData() {
		return data;
	}

	public void setData(List<InferredDataModel> data) throws ServiceException {
		if (data == null) {
			throw new ServiceException(BusinessErrorList.API_DATA_ARE_MISSING.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		// Check if the list is empty. Inferred not allowed without inferred data
		if(data.isEmpty()) {
			throw new ServiceException(BusinessErrorList.API_DATA_NOT_FOUND.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.data = data;
	}
	
}
