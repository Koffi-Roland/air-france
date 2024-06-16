package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.agence.OfficeID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class OfficeIDRepositoryImpl implements OfficeIDRepositoryCustom {

	private static final Log log = LogFactory.getLog(OfficeIDRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


	public List<OfficeID> findByAgenceGinOrderByRowNum(String gin) throws JrafDaoException 
	{
		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select oid ");
		strQuery.append(" from OfficeID oid ");
		strQuery.append(" where oid.agence.gin = :param ");
		strQuery.append(" order by rownum desc");
	
		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter("param", gin);
		
		List<OfficeID> result;
		
		try {
			result = (List<OfficeID>)myquery.getResultList();

		} 
		catch(Exception e) 
		{
			throw new JrafDaoException(e);
		}
		
		return result;
		
	}

}
