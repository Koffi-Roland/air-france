package com.airfrance.repind.transversal.identifycustomercrossreferential.dao;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessErrorDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	/*==========================================*/
	/*                                          */
	/*                LOGGER                    */
	/*                                          */
	/*==========================================*/
	private static Log LOGGER  = LogFactory.getLog(AbstractDAO.class);
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Throws a business exception 
	 * @param code
	 * @param message
	 * @throws BusinessException
	 */
	public void throwException(String code, String message) throws BusinessExceptionDTO
	{
		if(code == null)
		{
			code = "";
		}
		if(message == null)
		{
			message = "";
		}

		BusinessErrorDTO businessError = new BusinessErrorDTO();
		businessError.setErrorCode(code);
		businessError.setFaultDescription(message);
		businessError.setMissingParameter(message);
		LOGGER.info("IdentifyCustomerCrossRefrential | DAO EXCEPTION | " + code + message);
		throw new BusinessExceptionDTO(message, businessError);
	}
	
	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
