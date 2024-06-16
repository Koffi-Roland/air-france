package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dao;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.builder.AgencyBuilder;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessError;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Abstract DAO layer
 * 		Contains EntityManager reference
 * 		Contains Logger
 * 
 * @author t950700
 *
 */
public abstract class AbstractDAO 
{
	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	@Autowired
	private AgencyBuilder agencyBuilder = null;
	
	/*===============================================*/
	/*         LOGGER AND LOGGING METHODS            */
	/*===============================================*/
	private static final Log log = LogFactory.getLog(AbstractDAO.class);
	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Throws a business exception 
	 * @param code
	 * @param message
	 * @throws BusinessException
	 */
	public void throwsException(String code, String message) throws BusinessException
	{
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		getLog().info(message);
		throw new BusinessException(message, businessError);
	}
	
	
	/*===============================================*/
	/*                  ACCESSORS                    */
	/*===============================================*/
	
	public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	public AgencyBuilder getAgencyBuilder() {
		return agencyBuilder;
	}

	public void setAgencyBuilder(AgencyBuilder agencyBuilder) {
		this.agencyBuilder = agencyBuilder;
	}

	public static Log getLog() {
		return log;
	}
    
    
}

