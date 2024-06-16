package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CatchmentAreaRepository {
	
	private static final Log log = LogFactory.getLog(CatchmentAreaRepository.class);
	
	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public String findCatchmentAreaByPostalCode(String sPostalCode) throws JrafDaoException {
    			
		log.debug("START findCatchmentAreaByPostalCode : " + System.currentTimeMillis());
		
		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select scode from sic2.ref_chalandise where scode_post = '" + sPostalCode + "' ");
		
		Query myquery = entityManager.createNativeQuery(strQuery.toString());
	
		List<String> results;
		String result = null;
		try 
		{
			results = (List<String>)myquery.getResultList();

		} 
		catch(Exception e) 
		{
			throw new JrafDaoException(e);
		}
		
		log.debug("END findCatchmentAreaByPostalCode : " + System.currentTimeMillis());
		if (results.size() > 0) 
		{
			result = results.get(0);			
		}
		
		return result;
	
    }

	public String findCatchmentAreaByDeptCode(String sDeptCode) throws JrafDaoException {
		
		log.debug("START findCatchmentAreaByDeptCode : " + System.currentTimeMillis());
		
		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select scode from sic2.ref_chalandise where scode_dept = '" + sDeptCode + "' ");
		
		Query myquery = entityManager.createNativeQuery(strQuery.toString());
		
		List<String> results;
		String result = null;
		try 
		{
			results = (List<String>)myquery.getResultList();
		
		} 
		catch(Exception e) 
		{
			throw new JrafDaoException(e);
		}
		
		log.debug("END findCatchmentAreaByDeptCode : " + System.currentTimeMillis());
		if (results.size() > 0) 
		{
			result = results.get(0);			
		}
		
		return result;
				
		}
}
