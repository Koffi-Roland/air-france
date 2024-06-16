package com.airfrance.repind.dao.external;

import com.airfrance.ref.exception.external.ExternalIdentifierLinkedToDifferentIndividualException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.external.ExternalIdentifier;

import java.util.List;

public interface ExternalIdentifierRepositoryCustom {
	
	public List<ExternalIdentifier> findExternalIdentifier(String gin) throws JrafDaoException;
	public List<ExternalIdentifier> findExternalIdentifierPNMAndGIGYA(String gin) throws JrafDaoException;
	public List<ExternalIdentifier> findExternalIdentifierALL(String gin) throws JrafDaoException;
	public ExternalIdentifier findExternalIdentifier(ExternalIdentifier externalIdentifier) throws JrafDaoException;
	public ExternalIdentifier existExternalIdentifier(String extId, String type) throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException;
	public String existExternalIdentifierByGIGYA(String gigya) throws JrafDaoException, ExternalIdentifierLinkedToDifferentIndividualException;
	public boolean removeExternalIdentifierNotIn(String gin, String externalIdentifierListToKeep, String modificationSignature, String modificationSite) throws JrafDaoException;
	public int getNumberExternalIdentifierByGin(String gin) throws JrafDaoException;
}
