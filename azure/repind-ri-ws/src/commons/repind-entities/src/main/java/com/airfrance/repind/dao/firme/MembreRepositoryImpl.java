package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Membre;
import com.airfrance.repind.entity.firme.MembreLight;
import com.airfrance.repind.entity.firme.enums.LegalPersonStatusEnum;
import com.airfrance.repind.exception.firme.InvalidParametersException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MembreRepositoryImpl implements MembreRepositoryCustom {

	private static final Log log = LogFactory.getLog(MembreRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	//Nombre de résultats max renvoyés pour le nombre de membres
	private static final int maxMembers = 100 ;

	public void checkRequest(String linkType, String jobTitle, List<String> fbTier) throws InvalidParametersException{
		if ("T".equals(linkType)){
			if (jobTitle!=null && !"".equals(jobTitle)){
				String msg = "The link type is 'T' and a filter on a job title is specified";
				throw new InvalidParametersException(msg);
			}
		}
		if ("C".equals(linkType)){
			if (fbTier!=null && !fbTier.isEmpty()){
				String msg = "The link type is 'C' and a filter on the fb tier(s) is specified";
				throw new InvalidParametersException(msg);
			}
		}
	}
	
	public List<Membre> findByPersonneMorale(String pGinPm, String pGinIndividu, String linkType, String sortType, String lastName, String jobTitle, List<String> fbTier, Boolean memberStatusFilter, Boolean jobStatusFilter, Integer index) throws JrafDomainException {

		log.info(String.format("Call findByPersonneMorale(%s,%s,%s,%s,%s,%s,%s,%s,%d)", pGinPm, linkType,
				sortType, lastName, jobTitle, fbTier, memberStatusFilter, jobStatusFilter, index));

		if (fbTier!=null){
			fbTier.removeAll(Collections.singleton(null));
		}

		checkRequest(linkType, jobTitle, fbTier);

		// light members
		//---------------

		StringBuilder hql = new StringBuilder(" SELECT m FROM Membre m ");

		if ("C".equals(linkType.toUpperCase().trim()) && jobTitle != null && !"".equals(jobTitle)
				|| jobStatusFilter == null
				|| jobStatusFilter) {

			hql.append(" JOIN m.fonctions fonction ");
		}

		if ("T".equals(linkType.toUpperCase().trim()) && fbTier != null && !fbTier.isEmpty()) {

			hql.append(" JOIN m.individu.rolecontrats contrat ");
		}

		hql.append(" WHERE m.personneMorale.gin = :param ");

		if (!StringUtils.isEmpty(pGinIndividu)){
			hql.append(" AND m.individu.sgin = :param2 ");
		}

		if (linkType != null) {

			if ("T".equals(linkType.toUpperCase().trim())) {
				hql.append(" AND m.client = 'O' ");
			} else if ("C".equals(linkType.toUpperCase().trim())) {
				hql.append(" AND m.contact = 'O' ");
			} else if ("A".equals(linkType.toUpperCase().trim())) {
				hql.append(" AND ( m.contact = 'O' OR m.client = 'O' ) ");
			}
		}

		// Filtre sur le statut des membres :
		// - si il est � false, on ne filtre pas
		// - sinon par d�faut on ne garde que ceux dont la date de fin de validit� est nulle ou sup�rieure � la date actuelle
		if (memberStatusFilter == null || memberStatusFilter) {
			hql.append(" AND ( m.dateFinValidite is null OR m.dateFinValidite > current_date ) ");
		}

		if (lastName != null && !"".equals(lastName.trim())) {
			hql.append(" AND m.individu.nom LIKE :lastname ");
		}

		if ("C".equals(linkType.toUpperCase().trim()) && jobTitle != null && !"".equals(jobTitle)) {
			hql.append(" AND fonction.fonction = :jobtitle ");
		}

		if ("T".equals(linkType.toUpperCase().trim()) && fbTier != null && !fbTier.isEmpty() && !"".equals(fbTier.get(0))) {

			hql.append(" AND contrat.tier IN ( :fbtiers ) ");
		}

		// Filtre sur le statut des fonctions :
		// Si il est � false, on ne filtre pas sinon
		if (jobStatusFilter == null || jobStatusFilter) {
			hql.append(" AND ( fonction.dateFinValidite is null OR fonction.dateFinValidite > current_date ) ");
		}

		// order by (ATTENTION : en cas de recherche pagin�e sous Oracle, il faut tjrs une propri�t� unique)
		if (sortType != null && "NAME".equals(sortType.toUpperCase().trim())) {
			hql.append(" ORDER BY m.individu.nom, m.key ");
		} else {
			hql.append(" ORDER BY m.key ");
		}
		// query
		Query myquery = entityManager.createQuery(hql.toString());

		// parameters
		myquery.setParameter("param", pGinPm);
		if (!StringUtils.isEmpty(pGinIndividu)){
			myquery.setParameter("param2", pGinIndividu);
		}
		if (lastName != null && !"".equals(lastName.trim())) {
			myquery.setParameter("lastname", lastName+"%");
		}
		if ("C".equals(linkType.toUpperCase().trim()) && jobTitle != null && !"".equals(jobTitle)) {
			myquery.setParameter("jobtitle", jobTitle);
		}
		if ("T".equals(linkType.toUpperCase().trim()) && fbTier != null && !fbTier.isEmpty() && !"".equals(fbTier.get(0))) {
			List<String> fbtiersUpperCase = new ArrayList<>();
			for(String fbtierElement: fbTier){
				fbtiersUpperCase.add(fbtierElement.toUpperCase());
			}
			myquery.setParameter("fbtiers", fbtiersUpperCase);

		}
		// pagination
		if (index == null){
			index = 0;
		}
		myquery.setFirstResult(index);
		myquery.setMaxResults(maxMembers);

		// result
		@SuppressWarnings("unchecked")
		List<Membre> lightMembers = myquery.getResultList();

		// populate all collections
		//--------------------------

		hql = new StringBuilder(" SELECT m FROM Membre m ");
		hql.append(" LEFT JOIN FETCH m.fonctions f ");
		hql.append(" LEFT JOIN FETCH m.individu ind ");
		hql.append(" LEFT JOIN FETCH ind.accountData "); // oneToOne
		hql.append(" LEFT JOIN FETCH ind.profils "); // oneToOne
		hql.append(" LEFT JOIN FETCH ind.rolecontrats ");
		hql.append(" LEFT JOIN FETCH f.telecoms ");
		hql.append(" LEFT JOIN FETCH f.emails ");
		hql.append(" LEFT JOIN FETCH f.postalAddresses ");
		hql.append(" WHERE m.key = :param ");

		List<Membre> fullMembers = new ArrayList<>();
		for (Membre lightMember : lightMembers) {

			myquery = entityManager.createQuery(hql.toString());
			myquery.setParameter("param", lightMember.getKey());

			@SuppressWarnings("unchecked")
			List<Membre> results = myquery.getResultList();
			if (!results.isEmpty()) {

				// ignores multiple results
				fullMembers.add(results.get(0));
			}
		}

		log.info(String.format("findByPersonneMorale return %d rows", fullMembers.size()));

		return fullMembers;
	}
	
	public long countByPersonneMorale(String pGinPm, String pGinIndividu, String linkType, String sortType, String lastName, String jobTitle, List<String> fbTier, Boolean memberStatusFilter, Boolean jobStatusFilter) throws JrafDomainException{

		log.debug("BEGIN countByPersonneMorale with " + pGinPm);

		if (fbTier!=null){
			fbTier.removeAll(Collections.singleton(null));
		}

		checkRequest(linkType, jobTitle, fbTier);

		StringBuilder hql = new StringBuilder(" SELECT COUNT(DISTINCT m.key) FROM Membre m ");
		// joins effectu�s seulement sur les tables n�cessaires d'apr�s les arguments en entr�e
		if (lastName!=null && !"".equals(lastName.trim())){
			hql.append(" JOIN m.individu ind ");
		}else if (sortType!=null && "NAME".equals(sortType.toUpperCase().trim())){
			hql.append(" JOIN m.individu ind ");
		}
		if (("A".equals(linkType.toUpperCase().trim()) || "C".equals(linkType.toUpperCase().trim())) && jobTitle!=null && !"".equals(jobTitle)){
			hql.append(" JOIN m.fonctions fonct ");
		} else if (jobStatusFilter==null || jobStatusFilter){
			hql.append(" JOIN m.fonctions fonct ");
		}
		if (( "A".equals(linkType.toUpperCase().trim()) || "T".equals(linkType.toUpperCase().trim())) && fbTier!=null && !fbTier.isEmpty()){
			hql.append(" JOIN m.individu.rolecontrats roleContrats ");
		}

		hql.append(" WHERE m.personneMorale.gin = :param1 ");

		if (!StringUtils.isEmpty(pGinIndividu)){
			hql.append(" AND m.individu.sgin = :param2 ");
		}

		if(linkType!=null){
			if("T".equals(linkType.toUpperCase().trim())){
				hql.append(" AND m.client='O' ");
			}
			else if("C".equals(linkType.toUpperCase().trim())){
				hql.append(" AND m.contact='O' ");

			}else if("A".equals(linkType.toUpperCase().trim())){
				hql.append(" AND (m.contact='O' OR m.client='O') ");
			}
		}


		// Filtre sur le statut des membres :
		// Si il est � false, on ne filtre pas
		// sinon par d�faut on ne garde que ceux dont la date de fin de validit� est nulle ou sup�rieure � la date actuelle
		if (memberStatusFilter==null || memberStatusFilter){
			hql.append(" AND (m.dateFinValidite>current_date OR m.dateFinValidite is null ) ");
		}


		if (lastName!=null && !"".equals(lastName.trim())){
			hql.append(" AND ind.nom LIKE :lastname ");
		}

		if (("A".equals(linkType.toUpperCase().trim()) || "C".equals(linkType.toUpperCase().trim())) && jobTitle!=null && !"".equals(jobTitle)){
			hql.append(" AND fonct.fonction = :jobtitle ");
		}

		if (("A".equals(linkType.toUpperCase().trim()) || "T".equals(linkType.toUpperCase().trim())) && fbTier!=null && !fbTier.isEmpty() && !"".equals(fbTier.get(0))){
			hql.append(" AND roleContrats.tier IN ( :fbtiers ) ");
		}

		// Filtre sur le statut des fonctions :
		// Si il est � false, on ne filtre pas sinon
		if (jobStatusFilter==null || jobStatusFilter){
			hql.append(" AND (fonct.dateFinValidite>current_date OR fonct.dateFinValidite is null ) ");
		}

		// order by (ATTENTION : en cas de recherche pagin�e sous Oracle, il faut tjrs une propri�t� unique)
		hql.append(" ORDER BY m.key ");

		// query
		Query myquery = entityManager.createQuery(hql.toString());

		// parameters
		myquery.setParameter("param1", pGinPm);
		if (!StringUtils.isEmpty(pGinIndividu)){
			myquery.setParameter("param2", pGinIndividu);
		}

		if (lastName!=null && !"".equals(lastName.trim())){
			myquery.setParameter("lastname", lastName+"%");
		}
		if (("A".equals(linkType.toUpperCase().trim()) || "C".equals(linkType.toUpperCase().trim())) && jobTitle!=null && !"".equals(jobTitle)){
			myquery.setParameter("jobtitle", jobTitle);
		}
		if (("A".equals(linkType.toUpperCase().trim()) || "T".equals(linkType.toUpperCase().trim())) && fbTier!=null && !fbTier.isEmpty() && !"".equals(fbTier.get(0))){
			List<String> fbTiersUpperCase = new ArrayList<>();
			for(String fbtierElement : fbTier){
				fbTiersUpperCase.add(fbtierElement.toUpperCase());
			}
			myquery.setParameter("fbtiers", fbTiersUpperCase);
		}


		// result
		//@SuppressWarnings("unchecked")
		Long lightMembers = (Long) myquery.getSingleResult();
		long res=lightMembers;

		log.debug("END countByPersonneMorale");

		return res;
	}

	public List<Membre> findMember(Integer ain, Boolean jobStatusFilter)throws JrafDomainException {

		log.debug("BEGIN findMember with " + ain);
		log.info("BEGIN findMember with " + ain);


		// light member
		//---------------

		StringBuilder hql = new StringBuilder(" SELECT DISTINCT m FROM Membre m ");
		hql.append(" LEFT JOIN FETCH m.individu as ind ");
		hql.append(" LEFT JOIN FETCH m.fonctions fonct ");

		hql.append(" WHERE m.key = :param1 ");

		// Filtre sur le statut des fonctions :
		// Si il est à false, on ne filtre pas sinon
		if (jobStatusFilter==null || jobStatusFilter){
			hql.append(" AND (fonct.dateFinValidite>current_date OR fonct.dateFinValidite is null ) ");
		}

		hql.append(" ORDER BY m.key ");

		// query
		Query myquery = entityManager.createQuery(hql.toString());

		// parameters
		myquery.setParameter("param1", ain);

		// result
		@SuppressWarnings("unchecked")
		List<Membre> lightMembers = myquery.getResultList();

		log.debug("END Query of light members ");

		// populate all collections
		//--------------------------

		hql = new StringBuilder(" SELECT DISTINCT m FROM Membre m ");
		hql.append(" LEFT JOIN FETCH m.fonctions f ");
		hql.append(" LEFT JOIN FETCH m.individu ind ");
		hql.append(" LEFT JOIN FETCH ind.rolecontrats ");
		hql.append(" LEFT JOIN FETCH f.telecoms ");
		hql.append(" LEFT JOIN FETCH f.emails ");
		hql.append(" LEFT JOIN FETCH f.postalAddresses ");
		hql.append(" WHERE m.personneMorale.gin = :ginPm AND m.individu.sgin = :ginInd ");

		List<Membre> fullMembers = new ArrayList<>();
		for (Membre membre : lightMembers) {

			myquery = entityManager.createQuery(hql.toString());
			myquery.setParameter("ginPm", membre.getPersonneMorale().getGin());
			myquery.setParameter("ginInd", membre.getIndividu().getSgin());
			fullMembers.add((Membre) myquery.getSingleResult());
		}

		log.debug("END findMember : " + fullMembers.size());

		return fullMembers;
	}
	
	@SuppressWarnings("unchecked")
	public List<MembreLight> findByMultiCriteria(String individualGin, String legalPersonGin, List<String> legalPersonTypes,
			String jobTitle, String client, String contact, Boolean isClientOrContact, Date memberEndDate,
			Integer membershipFirstResultIndex, Integer membershipMaxResult) throws JrafDaoException {

		log.debug("START findByMultiCriteria(...): " + System.currentTimeMillis());

		// Check input
		if (individualGin == null && legalPersonGin == null) {
			throw new JrafDaoException("An individual GIN or a legal person GIN must be given at least!");
		}

		// Prepare return
		List<MembreLight> result = null;

		// Get hibernate session
		Session hSession = ((Session) entityManager.getDelegate());

		// Create criteria
		Criteria criteria = null;
		try {
			criteria = hSession.createCriteria(MembreLight.class)//.createAlias("individuLight", "ind")
					.createAlias("personneMoraleLight", "pm");

			// Add individual GIN restriction
			if (StringUtils.isNotEmpty(individualGin)) {
				criteria.add(Restrictions.eq("individuLight.sgin", individualGin));
			}

			// Add legal person GIN restriction
			if (StringUtils.isNotEmpty(legalPersonGin)) {
				criteria.add(Restrictions.eq("pm.gin", legalPersonGin));
			}

			// Add legal person type restriction
			if (legalPersonTypes != null && !legalPersonTypes.isEmpty()) {
				criteria.add(Restrictions.in("pm.class", legalPersonTypes));
			}

			// Add legal person status restriction
			Criterion noFUStatus = Restrictions.ne("pm.statut", LegalPersonStatusEnum.MERGED.toLiteral());
			Criterion noXStatus = Restrictions.ne("pm.statut", LegalPersonStatusEnum.CLOSED.toLiteral());
			criteria.add(Restrictions.and(noFUStatus, noXStatus));

			// Add job restriction
			if (StringUtils.isNotEmpty(jobTitle)) {
				criteria.createAlias("fonctions", "fct").add(Restrictions.eq("fct.fonction", jobTitle));
			}

			// Process contact restriction
			Criterion contactCriterion = null;
			if (StringUtils.isNotEmpty(contact)) {
				contactCriterion = Restrictions.eq("contact", contact);
			}

			// Process client restriction
			Criterion clientCriterion = null;
			if (StringUtils.isNotEmpty(client)) {
				clientCriterion = Restrictions.eq("client", client);
			}

			// Process client and contact restriction
			if (isClientOrContact != null && isClientOrContact.equals(true)
					&& clientCriterion != null && contactCriterion != null) {
				criteria.add(Restrictions.or(clientCriterion, contactCriterion));
			} else {
				if (clientCriterion != null) {
					criteria.add(clientCriterion);
				}
				if (contactCriterion != null) {
					criteria.add(contactCriterion);
				}
			}

			// End date restriction
			if (memberEndDate != null) {
				criteria.add(Restrictions.or(Restrictions.ge("dateFinValidite", memberEndDate),
						Restrictions.isNull("dateFinValidite")));
			}

			// Add order
			criteria.addOrder(Order.desc("dateFinValidite"));

			// Force fetch type on all parameters to avoid useless data getting fetched
			criteria.setFetchMode("ind", FetchMode.SELECT);
			criteria.setFetchMode("fonctions", FetchMode.SELECT);
			criteria.setFetchMode("pm", FetchMode.JOIN);

			// Get only distinct rows
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			// Set first result index
			if (membershipFirstResultIndex != null) {
				criteria.setFirstResult(membershipFirstResultIndex);
			}

			// Set max results
			if (membershipMaxResult != null) {
				criteria.setMaxResults(membershipMaxResult);
			}
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		// Get results
		try {
			result = criteria.list();
		} catch (HibernateException e) {
			throw new JrafDaoException(e);
		}

		log.debug("END findByMultiCriteria(...): " + System.currentTimeMillis());

		return result;
	}
	
	public int getNumberFirmsOrAgenciesLinkedByGin(String gin, boolean firms) throws JrafDaoException {
        log.debug("START getNumberFirmsAgenciesLinkedByGin : " + System.currentTimeMillis());
		StringBuffer buffer = new StringBuffer("select count(1) from Membre m, PersonneMorale pm ");
		buffer.append("where m.individu.sgin = :pGin ");
		buffer.append("and m member of pm.membres ");
		if (firms) {
			buffer.append("and STYPE != 'A'");
		} else {
			buffer.append("and STYPE = 'A'");
		}

		Query query = entityManager.createQuery(buffer.toString());
		query.setParameter("pGin", gin);
		
		Long temp = (Long) query.getSingleResult();
		int result = temp.intValue();
		log.debug("END getNumberFirmsAgenciesLinkedByGin : " + System.currentTimeMillis());
        return result;
	}

	public void refresh(Membre membre) {
		entityManager.merge(membre);
		entityManager.refresh(membre);
	}

	public static int getMaxMembers() {
		return MembreRepositoryImpl.maxMembers;
	}
}
