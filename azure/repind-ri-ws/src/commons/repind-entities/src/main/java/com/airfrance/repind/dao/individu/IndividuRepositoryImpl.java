package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleGP;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IndividuRepositoryImpl implements IndividuRepositoryCustom {


	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


	/**
	 * getAnIndividualIdentification
	 * @param email in String
	 * @return The getAnIndividualIdentification as <code>List<IndividuDTO></code>
	 * @throws JrafDaoException en cas d'exception
	 */
	@Override
	public List<String> getAnIndividualIdentification(String email) {
		List<String> retList = new ArrayList<>();
		String request="select u1.semail,u1.sgin , r.snumero_contrat"
				+" from SIC2.role_contrats r, sic2.account_data ad, "
				+"(WITH sel_gin AS (SELECT DISTINCT e.sgin FROM emails e "
				+"WHERE e.semail = ? "
				+"AND SSTATUT_MEDIUM IN ('I', 'V')) "
				+"SELECT m.semail,m.sain,	m.ddate_modification,	m.sgin, "
				+"ROW_NUMBER () "
				+"OVER (PARTITION BY m.sgin ORDER BY m.ddate_modification DESC) "
				+"rang "
				+"FROM emails m "
				+"WHERE m.sgin IN (SELECT sgin FROM sel_gin) and m.SSTATUT_MEDIUM IN ('I', 'V')) u1 "
				+"where u1.rang = 1 and u1.sgin=r.sgin(+) and r.stype_contrat(+)='FP' and u1.sgin=ad.sgin";
		Query query = entityManager.createNativeQuery(request);
		query.setParameter(1, email);
		List<Object[]> result = query.getResultList();
		for (Object[] ligne : result) {
			if(ligne[0]!=null && ligne[0].toString().equals(email)){
				if(ligne[1]!=null) {
					retList.add(new String(ligne[1].toString()));
				}
			}
		}

		return retList;
	}

	@Override
	public Integer countIndividualIdentification(String email) {
		String request="select count(distinct email.sgin) from sic2.emails email inner join sic2.role_contrats rol " + 
				"on email.sgin=rol.sgin where email.semail = :email and email.sstatut_medium in ('I', 'V') " + 
				"and rol.stype_contrat in ('FP', 'MA')";
		Query query = entityManager.createNativeQuery(request);
		query.setParameter("email", email);

		return ((BigDecimal) query.getSingleResult()).intValue();
	}
	
	@Override
	public List<RoleGP> getGPRoleByGIN(String sGIN) {

		List<BusinessRole> businessRoleList = getBusinessRoleByGIN(sGIN);
		List<RoleGP> result = new ArrayList<RoleGP>();
		
		if (businessRoleList != null && !businessRoleList.isEmpty())
		{
			result = new ArrayList<RoleGP>();
					
			for (BusinessRole br : businessRoleList)
			{
				if (br != null)
				{
					StringBuilder strQuery = new StringBuilder();
					strQuery.append("SELECT GP FROM RoleGP GP");
					strQuery.append(" WHERE GP.cleRole = ?1 ");		
					
					Query myquery = entityManager.createQuery(strQuery.toString());
					myquery.setParameter(1, br.getCleRole());
					List<RoleGP> res = (List<RoleGP>)myquery.getResultList();
					
					if (res != null && !res.isEmpty())
					{
						result.addAll(res);				 													
					} 
				}
			}
		}
		
		if (result != null && !result.isEmpty())
			return result;
		else
			return null;
	}
			
	@Override
	public List<BusinessRole> getBusinessRoleByGIN(String sGIN) {

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT BR FROM BusinessRole BR");
		strQuery.append(" WHERE BR.ginInd = ?1 ");		
		
		Query myquery = entityManager.createQuery(strQuery.toString());
		myquery.setParameter(1, sGIN);
		@SuppressWarnings("unchecked")
		List<BusinessRole> result = (List<BusinessRole>)myquery.getResultList();		
	
		if (result != null && !result.isEmpty())
			return result;
		else 
			return null;
	}

	@Override
	public List<Individu> findIndividuByExample(int i, int j) {
		// Get hibernate session
        Session session = ((Session) entityManager.getDelegate());
        
        Individu individu = new Individu();
        
        Example individuExample = Example.create(individu);
        Criteria criteria = session.createCriteria(Individu.class).add(individuExample);
        // pagination
        criteria.setFirstResult(i);
        criteria.setMaxResults(j);
        
		return criteria.list();
	}
	
	@Override
	public List<Telecoms> findTelecomsByGINSortedByDate(String sGin) {
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT * FROM TELECOMS tel");
		strQuery.append(" WHERE tel.SGIN = ? ");
		strQuery.append(" AND tel.SSTATUT_MEDIUM NOT IN ('X','H') ORDER BY NVL(tel.DDATE_MODIFICATION, tel.DDATE_CREATION) DESC ");
		Query myquery = entityManager.createNativeQuery(strQuery.toString());		
		myquery.setParameter(1, sGin);
		List<Telecoms> result = new ArrayList<Telecoms>();
		List<Object[]> resultObj = myquery.getResultList();		
		for (Object[] obj : resultObj) 
		{	
			if (obj != null)
			{
				Telecoms tel = new Telecoms();
				tel.setScode_medium(obj[3].toString());
				if (obj[4] != null)
					tel.setSstatut_medium(obj[4].toString());
				if (obj[5] != null)
					tel.setSnumero(obj[5].toString());
				if (obj[7] != null)
					tel.setSterminal(obj[7].toString());
				if (obj[18] != null)
					tel.setCountryCode(obj[18].toString());
				if (obj[24] != null)
					tel.setSnorm_nat_phone_number(obj[24].toString());
				if (obj[26] != null)
					tel.setSnorm_inter_country_code(obj[26].toString());
				
				result.add(tel);
			}
		}
			
		
		return result;
	}
	
	/*@Override
	public Individu findByGinFetchingRelationships(String pGin, List<String> pRelationships, List<String> pSubRelationships) throws JrafDaoException {
		// gin is required
		Assert.hasText(pGin, "gin must not be empty");

		// result
		Individu result = null;

		// from
		StringBuilder jpql = new StringBuilder(getRequete());

		// join relationships to fetch
		for (String relationship : pRelationships) {
			jpql.append(String.format(" LEFT JOIN FETCH %s.%s ", getAlias(), relationship));
		}

		// join subrelationships to fetch
		for (String subRelationship : pSubRelationships) {
			jpql.append(String.format(" LEFT JOIN FETCH %s ", subRelationship));
		}

		// where
		jpql.append(String.format(" WHERE %s.sgin = :param ", getAlias()));

		Query myquery = entityManager.createQuery(jpql.toString());
		myquery.setParameter("param", pGin);

		@SuppressWarnings("unchecked")
		List<Individu> results = myquery.getResultList();

		if (!results.isEmpty()) {

			// ignores multiple results
			result = results.get(0);
		}

		return result;
	}*/
	
	@SuppressWarnings("unused")
	@Override
	public Integer getVersionOfIndividual(String gin) {

		Integer version = 0;
		
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT I.IVERSION FROM INDIVIDUS_ALL I");
		strQuery.append(" WHERE I.SGIN = ?");

		Query myquery = entityManager.createNativeQuery(strQuery.toString());
		myquery.setParameter(1, gin);
		version = ((BigDecimal) myquery.getSingleResult()).intValue();
		
		if (version != null) {
			return version;
		}

		return version;
	}
}
