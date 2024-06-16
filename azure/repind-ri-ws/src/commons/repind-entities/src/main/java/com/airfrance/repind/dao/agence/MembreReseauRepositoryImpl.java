package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.agence.MembreReseau;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MembreReseauRepositoryImpl implements MembreReseauRepositoryCustom {

	private static final Log log = LogFactory.getLog(MembreReseauRepositoryImpl.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Override
	public int countSubNetworks(MembreReseau membreReseau) {

		log.debug("START countSubNetworks : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select count(1) ");
		strQuery.append(" from MEMBRE_RESEAU rl, reseau rs , PERS_MORALE pers ");
		strQuery.append(" where rl.SCODE = rs.SCODE ");
		strQuery.append(" and pers.SGIN = rl.SGIN ");
		strQuery.append(" and pers.SGIN = :gin ");
		strQuery.append(" and rs.SCODE_PERE = :codePere ");

		Query myQuery = entityManager.createNativeQuery(strQuery.toString());
		myQuery.setParameter("gin", membreReseau.getAgence().getGin());
		myQuery.setParameter("codePere", membreReseau.getReseau().getCode());

		BigDecimal result = (BigDecimal) myQuery.getSingleResult();

		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MembreReseau> getParentLinkNetwork(String pGinValue, String pCodeChild) throws JrafDaoException {

		log.debug("START getParentLinkNetwork : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append(" select rl ");
		strQuery.append(" from MembreReseau rl,  PersonneMorale pers , Reseau rs");
		strQuery.append(" where  rl.agence = pers");
		strQuery.append(" and rl.reseau = rs ");
		strQuery.append(" and  pers.gin = ?1 ");
		strQuery.append(" and rs.code in ");
		
		strQuery.append(" ( select rs.parent.code ");
		strQuery.append(" from  Reseau rs");
		strQuery.append(" where rs.code = ?2 )");
		
		
		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter(1, pGinValue);
		myquery.setParameter(2, pCodeChild);
		
		List<MembreReseau> result;
		
		try {
			result = (List<MembreReseau>)myquery.getResultList();

		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END getParentLinkNetwork : " + System.currentTimeMillis());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MembreReseau> getLinkNetworksByTypeAndCode(String gin, String networkType) throws JrafDaoException {

		log.debug("START getLinkNetworksByTypeAndCode : " + System.currentTimeMillis());

		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select rl ");
		strQuery.append(" from MembreReseau rl, Reseau rs , PersonneMorale pers  ");
		strQuery.append(" where rl.reseau = rs ");
		strQuery.append(" and pers = rl.agence");
		strQuery.append(" and pers.gin = ?1 ");
		strQuery.append(" and rl.reseau.type = ?2 ");

		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter(1, gin);
		myquery.setParameter(2, networkType);
		
		List<MembreReseau> result;
		
		try {
			result = (List<MembreReseau>)myquery.getResultList();

		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END getLinkNetworksByTypeAndCode : " + System.currentTimeMillis());
		return result;
	}



	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getCleMembreReseau(String gNumeroValue, String pCodeValue, Date pDateDebut) throws JrafDaoException {
		Integer result = null;
				
		log.debug("START getMembreReseauWithoutDate : " + System.currentTimeMillis());
		
		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select icle_mbr from sic2.membre_reseau where sgin in ");
		strQuery.append(" (select sgin from sic2.numero_ident where snumero like '" + gNumeroValue +"%' and stype = 'IA')");
		strQuery.append(" and scode = '" + pCodeValue + "' ");
		strQuery.append(" and DDATE_DEBUT = :paramDate ");

		Query myquery = entityManager.createNativeQuery(strQuery.toString());
		
		myquery.setParameter("paramDate", pDateDebut);
		
		List<BigDecimal> results;
		try {
			results = (List<BigDecimal>)myquery.getResultList();

		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END getMembreReseauWithoutDate : " + System.currentTimeMillis());
		if (results.size() > 0) {
			result = results.get(0).intValue();			
		}
	
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getCleMembreReseauWithoutEntryDate(String gNumeroValue, String pCodeValue) throws JrafDaoException {
		Integer result = null;
				
		log.debug("START getMembreReseauWithoutDate : " + System.currentTimeMillis());
		
		StringBuilder strQuery = new StringBuilder();
		
		strQuery.append("select icle_mbr from sic2.membre_reseau where sgin in ");
		strQuery.append(" (select sgin from sic2.numero_ident where snumero like '" + gNumeroValue +"%' and stype = 'IA')");
		strQuery.append(" and scode = '" + pCodeValue + "' ");

		Query myquery = entityManager.createNativeQuery(strQuery.toString());
		
		List<BigDecimal> results;
		try {
			results = (List<BigDecimal>)myquery.getResultList();

		} catch(Exception e) {
			throw new JrafDaoException(e);
		}
		
		log.debug("END getMembreReseauWithoutDate : " + System.currentTimeMillis());
		if (results.size() > 0) {
			result = results.get(0).intValue();			
		}
	
		return result;
	}
}
