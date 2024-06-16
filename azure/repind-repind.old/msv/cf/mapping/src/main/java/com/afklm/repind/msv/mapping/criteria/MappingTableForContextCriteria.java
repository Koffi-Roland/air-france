package com.afklm.repind.msv.mapping.criteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.afklm.repind.msv.mapping.model.error.BusinessErrorList;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;

public class MappingTableForContextCriteria {

	private String context;

	public MappingTableForContextCriteria(String context) throws ServiceException {
		setContext(context);
	}
	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 * @throws ServiceException
	 */
	public void setContext(String context) throws ServiceException {
		if (CriteriaHelper.ALL_CONTEXT_REF_TABLE_VALUE.equals(context)
				|| (StringUtils.isAlphanumeric(context) && context.length() > CriteriaHelper.MIN_LENGTH
						&& context.length() < CriteriaHelper.CONTEXT_MAX_LENGTH)) {
			this.context = context;
		} else {
			throw new ServiceException(BusinessErrorList.API_CONTEXT_VIOLATION.getError(),
					HttpStatus.PRECONDITION_FAILED);
		}
	}

	public boolean isAllContexts() {
		return CriteriaHelper.ALL_CONTEXT_REF_TABLE_VALUE.equals(this.getContext());
	}

}
