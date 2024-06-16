package com.airfrance.repind.firme.searchfirmonmulticriteria.dao;

import com.airfrance.repind.firme.searchfirmonmulticriteria.ds.builders.FirmBuilder;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessError;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
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
	private FirmBuilder firmBuilder = null;
	

	/*===============================================*/
	/*                   LOGGER                      */
	/*===============================================*/
	private static Log LOGGER  = LogFactory.getLog(AbstractDAO.class);
	
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
		if(code == null)
		{
			code = "";
		}
		if(message == null)
		{
			message = "";
		}
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		LOGGER.info("SearchFirmOnMultiCriteria | DAO EXCEPTION | " + code + message);
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

	public FirmBuilder getFirmBuilder() {
		return firmBuilder;
	}

	public void setFirmBuilder(FirmBuilder firmBuilder) {
		this.firmBuilder = firmBuilder;
	}
    
}
