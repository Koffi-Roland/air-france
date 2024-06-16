package com.airfrance.repind.dao.zone;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.enums.NatureZoneEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


public class ZoneCommRepositoryImpl implements ZoneCommRepositoryCustom {

	private static final Log log = LogFactory.getLog(ZoneCommRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	@SuppressWarnings("deprecation")
	public List<ZoneComm> findByCountryCodeAndProvinceCodeAndNature(String pCountryCode, String pProvinceCode, NatureZoneEnum pNature) throws JrafDaoException {
        
		log.info(String.format("Call findByCountryCodeAndProvinceCodeAndNature(%s,%s,%s)", pCountryCode, pProvinceCode,
				pNature));
        Assert.notNull(pCountryCode);
        Assert.notNull(pProvinceCode);
        Assert.notNull(pNature);

        StringBuilder jpql = new StringBuilder(" SELECT l.zoneDecoup FROM LienIntCpZd l ");
        jpql.append(" WHERE l.zoneDecoup.class = ZoneComm AND l.zoneDecoup.nature = :param1 ");
        jpql.append(" AND l.codePays = :param2 ");
        jpql.append(" AND l.codeProvince = :param3 ");
        
        Query myquery = entityManager.createQuery(jpql.toString());
        
        myquery.setParameter("param1", pNature.toLiteral());
        myquery.setParameter("param2", pCountryCode);
        myquery.setParameter("param3", pProvinceCode);        
        
        @SuppressWarnings("unchecked")
        List<ZoneComm> results = (List<ZoneComm>) myquery.getResultList();

            log.info(String.format("findByCountryCodeAndProvinceCodeAndNature return %d items", results.size()));
        return results; 
    }
	
	@SuppressWarnings("deprecation")
	public List<ZoneComm> findByCountryCodeAndPostalCodeAndNature(String pCountryCode, String pPostalCode, NatureZoneEnum pNature) throws JrafDaoException {
        
		log.info(String.format("Call findByCountryCodeAndPostalCodeAndNature(%s,%s,%s)", pCountryCode, pPostalCode,
				pNature));
        Assert.notNull(pCountryCode);
        Assert.notNull(pPostalCode);
        Assert.notNull(pNature);

        StringBuilder jpql = new StringBuilder(" SELECT l.zoneDecoup FROM LienIntCpZd l ");
        jpql.append(" WHERE l.zoneDecoup.class = ZoneComm AND l.zoneDecoup.nature = :param1 ");
        jpql.append(" AND l.codePays = :param2 ");
        jpql.append(" AND l.intervalleCodesPostaux.codePostalDebut <= :param3 AND l.intervalleCodesPostaux.codePostalFin >= :param3 ");
        Query myquery = entityManager.createQuery(jpql.toString());
        
        myquery.setParameter("param1", pNature.toLiteral());
        myquery.setParameter("param2", pCountryCode);
        myquery.setParameter("param3", pPostalCode);        
        
        @SuppressWarnings("unchecked")
        List<ZoneComm> results = (List<ZoneComm>) myquery.getResultList();

            log.info(String.format("findByCountryCodeAndPostalCodeAndNature return %d items", results.size()));

        return results;  
    }
	
	@SuppressWarnings("deprecation")
	public List<ZoneComm> findByCountryCodeAndNature(String pCountryCode, NatureZoneEnum pNature) throws JrafDaoException {

        log.info(String.format("Call findByCountryCodeAndNature(%s,%s)", pCountryCode, pNature));
	    Assert.notNull(pCountryCode);
	    Assert.notNull(pNature);
	
	    StringBuilder jpql = new StringBuilder(" SELECT l.zoneDecoup FROM LienIntCpZd l ");
	    jpql.append(" WHERE l.zoneDecoup.class = ZoneComm AND l.zoneDecoup.nature = :param1 ");
	    jpql.append(" AND l.codePays = :param2 ");
	    Query myquery = entityManager.createQuery(jpql.toString());
	    
	    myquery.setParameter("param1", pNature.toLiteral());
	    myquery.setParameter("param2", pCountryCode);        
	    
	    @SuppressWarnings("unchecked")
	    List<ZoneComm> results = (List<ZoneComm>) myquery.getResultList();
	
	        log.info(String.format("findByCountryCodeAndNature return %d items", results.size()));
	    return results;  
	}
	
	public List<ZoneComm> findActivePvValidZc(PersonneMorale pm) throws JrafDaoException {
	    	
	     log.info(String.format("Call findActivePvValidZc(%s)", pm.getGin()));
	
		StringBuilder hql = new StringBuilder(" SELECT pmz.zoneDecoup FROM PmZone pmz ");
		 hql.append(" WHERE ( pmz.dateFermeture is null OR pmz.dateFermeture >= sysdate ) ");
		 hql.append(" AND pmz.personneMorale.gin = :param1 ");
		 hql.append(" AND pmz.zoneDecoup.class = ZoneComm AND pmz.lienPrivilegie = :param2 ");
		 
		 Query myquery = entityManager.createQuery(hql.toString());
		
		 myquery.setParameter("param1", pm.getGin());
		 myquery.setParameter("param2", "O");
		 
		 @SuppressWarnings("unchecked")
		 List<ZoneComm> results = (List<ZoneComm>) myquery.getResultList();
		
		 log.info(String.format("findActivePvValidZc return %d items", results.size()));
		 return results; 
	}

}
