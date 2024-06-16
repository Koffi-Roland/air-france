package com.afklm.repind.msv.handicap.criteria;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;

/**
 * Exceptional event criteria POJO
 *
 * @author m312812
 *
 */
public class HandicapCriteria {

	private Long id;
    
	private String gin;

    private String type;
	
    private String code;
    
    private String application;
    
    private List<CreateHandicapModel> handicap;
    
    private List<HandicapDataCreateModel> handicapData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) throws ServiceException {
		if (id == null) {
			throw new ServiceException(BusinessErrorList.API_ID_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
		}
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            code status to set
	 *            Code can't be null and its length must be 1
	 * @throws ServiceException
	 */
	public void setCode(String code) throws ServiceException {
		if (code == null) {
			throw new ServiceException(BusinessErrorList.API_CODE_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
		}
		if (code.length() > 10) {
			throw new ServiceException(BusinessErrorList.API_INVALID_CODE.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.code = StringUtils.upperCase(code);
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
		if (StringUtils.isBlank(gin)) {
			throw new ServiceException(BusinessErrorList.API_GIN_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
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
			throw new ServiceException(BusinessErrorList.API_TYPE_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
		}
		if (type.length() > 10) {
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
		if (StringUtils.isBlank(application)) {
			throw new ServiceException(BusinessErrorList.API_APPLICATION_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
		}
		if (application.length() > 16) {
			throw new ServiceException(BusinessErrorList.API_INVALID_APPLICATION.getError(), HttpStatus.PRECONDITION_FAILED);
		}
		this.application = application;
	}

	public List<CreateHandicapModel> getHandicap() {
		return handicap;
	}

	public void setHandicap(List<CreateHandicapModel> handicap) {
		this.handicap = handicap;
	}

	public List<HandicapDataCreateModel> getHandicapData() {
		return handicapData;
	}

	public void setHandicapData(List<HandicapDataCreateModel> handicapData) {
		this.handicapData = handicapData;
	}
	
}
