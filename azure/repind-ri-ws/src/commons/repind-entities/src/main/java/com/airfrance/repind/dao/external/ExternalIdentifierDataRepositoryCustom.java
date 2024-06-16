package com.airfrance.repind.dao.external;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.external.ExternalIdentifierData;

import java.util.List;

public interface ExternalIdentifierDataRepositoryCustom {
	
	public List<ExternalIdentifierData> findExternalIdentifierData(long identifierId) throws JrafDaoException;
}
