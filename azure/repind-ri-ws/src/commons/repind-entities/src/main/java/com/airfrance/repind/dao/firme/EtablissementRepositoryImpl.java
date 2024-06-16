package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.zone.PmZoneRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.zone.PmZone;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.scope.FirmScope;
import com.airfrance.repind.util.OffsetBasedPageRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class EtablissementRepositoryImpl implements EtablissementRepositoryCustom {

	private static final Log log = LogFactory.getLog(EtablissementRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	/** postalAddressDAO **/
    @Autowired
    private PostalAddressRepository postalAddressRepository;

    /** telecomsDAO **/
    @Autowired
    private TelecomsRepository telecomsRepository;

    /** emailDAO **/
    @Autowired
    private EmailRepository emailRepository;

    /** synonymeDAO **/
    @Autowired
    private SynonymeRepository synonymeRepository;

    /** numeroIdentDAO **/
    @Autowired
    private NumeroIdentRepository numeroIdentRepository;

    /** chiffreDAO **/
    @Autowired
    private ChiffreRepository chiffreRepository;

    /** segmentationDAO **/
    @Autowired
    private SegmentationRepository segmentationRepository;

    /** gestionPMDAO **/
    @Autowired
    private GestionPMRepository gestionPMRepository;

    /** pmZoneDAO **/
    @Autowired
    private PmZoneRepository pmZoneRepository;

    /** selfBookingToolDAO **/
    @Autowired
    private SelfBookingToolRepository selfBookingToolRepository;

    /** personneMoraleDAOBean **/
    @Autowired
    private PersonneMoraleRepository personneMoraleRepository;

    /** businessRoleDAO **/
    @Autowired
    private BusinessRoleRepository businessRoleRepository;
	
	public Etablissement findByGinWithAllCollectionsFusion(final String pGin, List<String> scopeToProvide) throws JrafDaoException {
		log.debug("BEGIN findByGinWithAllCollectionsFusion(" + pGin + ") at " + System.currentTimeMillis());

        Etablissement finalResult = findByGinWithAllCollectionsExceptAgencies(pGin, scopeToProvide);
        Etablissement onlyAgencies = findByGinWithOnlyAgencies(pGin, scopeToProvide);
        
        // REPIND-1398 : Test SONAR NPE
        if (finalResult != null && onlyAgencies != null) {        
        	finalResult.setPersonnesMoralesGerantes(onlyAgencies.getPersonnesMoralesGerantes());
        }
        
		log.debug("END findByGinWithAllCollectionsFusion at " + System.currentTimeMillis());
        
        return finalResult;
    }
	
	public Etablissement findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException {
		log.debug("BEGIN findByGinWithAllCollections(" + pGin + ") at " + System.currentTimeMillis());

        // le gin est requis
        Assert.hasText(pGin, "'pGin' must not be empty");

        Etablissement result = null;

        StringBuilder hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
        hql.append(" LEFT JOIN FETCH etab.parent p1 ");
        hql.append(" LEFT JOIN FETCH p1.parent ");       
        
        if(scopeToProvide!=null && (scopeToProvide.contains("POSTAL_ADDRESSES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.postalAddresses ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("TELECOMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.telecoms ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("EMAILS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.emails ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("CONTRACTS") || scopeToProvide.contains("ALL"))){
            hql.append(" LEFT JOIN FETCH etab.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleAgence ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SYNONYMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.synonymes");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.numerosIdent");
        }
        
        if(scopeToProvide != null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))) {
            hql.append(" LEFT JOIN FETCH etab.pmZones pmz LEFT JOIN FETCH pmz.zoneDecoup ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.chiffres");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.segmentations");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("AGENCIES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.personnesMoralesGerantes ");
        }
        
        // NE JAMAIS RAMENER LES MEMBRES CAR SVT TROP NOMBREUX
        /*if (scopeToProvide != null && (scopeToProvide.contains("MEMBERS") || scopeToProvide.contains("ALL"))) {
            hql.append(" LEFT JOIN FETCH etab.membres ");
        }*/     
        
        hql.append(" WHERE etab.gin = :param ");  

        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("param", pGin);

        @SuppressWarnings("unchecked")
        List<Etablissement> results = (List<Etablissement>) myquery.getResultList();
        
        // FIXME Dangereux mais seul moyen trouvé pour que des updates ne soient pas exécutés 
        // sur les PM gérantes remontées
        // entityManager.clear();
        
        if (!results.isEmpty()) {
            
            // ignores multiple results
            result = results.get(0);
              
        }			

		log.debug("END findByGinWithAllCollections at " + System.currentTimeMillis());
        
        return result;
    }

	private Etablissement findByGinWithAllCollectionsExceptAgencies(final String pGin, List<String> scopeToProvide) throws JrafDaoException {
		log.debug("BEGIN findByGinWithAllCollectionsExceptAgencies(" + pGin + ") at " + System.currentTimeMillis());

        // le gin est requis
        Assert.hasText(pGin, "'pGin' must not be empty");

        Etablissement result = null;

        StringBuilder hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
        hql.append(" LEFT JOIN FETCH etab.parent p1 ");
        hql.append(" LEFT JOIN FETCH p1.parent ");       
        
        if(scopeToProvide!=null && (scopeToProvide.contains("POSTAL_ADDRESSES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.postalAddresses ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("TELECOMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.telecoms ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("EMAILS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.emails ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("CONTRACTS") || scopeToProvide.contains("ALL"))){
            hql.append(" LEFT JOIN FETCH etab.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleAgence ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SYNONYMS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.synonymes");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("KEY_NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.numerosIdent");
        }
        
        if(scopeToProvide != null && (scopeToProvide.contains("COMMERCIAL_ZONES") || scopeToProvide.contains("SALES_ZONES") || scopeToProvide.contains("FINANCIAL_ZONES") || scopeToProvide.contains("ALL"))) {
            hql.append(" LEFT JOIN FETCH etab.pmZones pmz LEFT JOIN FETCH pmz.zoneDecoup ");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("NUMBERS") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.chiffres");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("MARKET_CHOICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.segmentations");
        }
        
        if(scopeToProvide!=null && (scopeToProvide.contains("SERVICES") || scopeToProvide.contains("ALL"))){
        	hql.append(" LEFT JOIN FETCH etab.enfants");
        }

        hql.append(" WHERE etab.gin = :param ");  

        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("param", pGin);

        @SuppressWarnings("unchecked")
        List<Etablissement> results = (List<Etablissement>) myquery.getResultList();

        if (!results.isEmpty()) {
            
            // ignores multiple results
            result = results.get(0);
        }			

		log.debug("END findByGinWithAllCollectionsExceptAgencies at " + System.currentTimeMillis());
        
        return result;
    }
	
	private Etablissement findByGinWithOnlyAgencies(final String pGin, List<String> scopeToProvide) throws JrafDaoException {
		log.debug("BEGIN findByGinWithOnlyAgencies(" + pGin + ") at " + System.currentTimeMillis());

	    // le gin est requis
	    Assert.hasText(pGin, "'pGin' must not be empty");
	
	    Etablissement result = null;
	
	    StringBuilder hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
	  
	    
	    if(scopeToProvide!=null && (scopeToProvide.contains("AGENCIES") || scopeToProvide.contains("ALL"))){
	    	hql.append(" LEFT JOIN FETCH etab.personnesMoralesGerantes");
	    }
	
	    hql.append(" WHERE etab.gin = :param ");  
	
	    
	    Query myquery = entityManager.createQuery(hql.toString());
	    myquery.setParameter("param", pGin);
	
	    @SuppressWarnings("unchecked")
	    List<Etablissement> results = (List<Etablissement>) myquery.getResultList();
	
	    if (!results.isEmpty()) {
	        
	        // ignores multiple results
	        result = results.get(0);
	    }			
	
			log.debug("END findByGinWithOnlyAgencies at " + System.currentTimeMillis());
	    
	    return result;
	}
	
	public List<Etablissement> findByVille(final String pVille) throws JrafDaoException {
		log.debug("BEGIN findByVille with " + pVille);
        
        StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab INNER JOIN etab.postalAddresses AS adresses WITH adresses.sville LIKE :paramValue ");
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("paramValue", pVille);
        
        myquery.setFirstResult(0);
        myquery.setMaxResults(100);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements1 = (List<Etablissement>) myquery.getResultList();
        
        hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
        hql.append(" LEFT JOIN FETCH etab.postalAddresses ");
        hql.append(" LEFT JOIN FETCH etab.telecoms ");
        hql.append(" LEFT JOIN FETCH etab.emails ");
        hql.append(" LEFT JOIN FETCH etab.synonymes ");
        hql.append(" LEFT JOIN FETCH etab.pmZones");
        hql.append(" WHERE etab.gin = :ginValue ");
        
        List<Etablissement> etablissements2 = new ArrayList<Etablissement>();
        for (Etablissement etablissement : etablissements1) {            
            
            myquery = entityManager.createQuery(hql.toString());
            myquery.setParameter("ginValue", etablissement.getGin());
            etablissements2.add((Etablissement) myquery.getSingleResult());
        }
        
		log.debug("END findByVille : " + etablissements2.size());
        return etablissements2;
    }
	
	public List<Etablissement> findByNumeroIdent(final String pTypeIdent, final String pNumeroIdent) throws JrafDaoException {
		log.debug("BEGIN findByNumeroIdent with " + pNumeroIdent);
        
        StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab, NumeroIdent ni LEFT JOIN FETCH etab.numerosIdent WHERE ni.type LIKE :type AND ni.numero LIKE :numero AND ni.personneMorale.gin = etab.gin");
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("type", pTypeIdent);
        myquery.setParameter("numero", pNumeroIdent);
        
        myquery.setFirstResult(0);
        myquery.setMaxResults(100);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) myquery.getResultList();
        
		log.debug("END findByNumeroIdent : " + etablissements.size());
        return etablissements;
    }
	
	public List<Etablissement> findByNumeroIdentBis(final String pTypeIdent, final String pNumeroIdent) throws JrafDaoException {
		log.debug("BEGIN findByNumeroIdentBis with " + pNumeroIdent);
        
        StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab INNER JOIN etab.numerosIdent ni WITH ni.type LIKE :type AND ni.numero LIKE :numero ");
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("type", pTypeIdent);
        myquery.setParameter("numero", pNumeroIdent);
        
        myquery.setFirstResult(0);
        myquery.setMaxResults(100);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) myquery.getResultList();
        
		log.debug("END findByNumeroIdentBis : " + etablissements.size());
        return etablissements;
    }
	
	public List<Etablissement> findByNumeroContrat(String pNumeroContrat) throws JrafDaoException {
		log.debug("BEGIN findByNumeroContrat with " + pNumeroContrat);
        
        StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab INNER JOIN etab.businessRoles AS br INNER JOIN br.roleFirme rf WITH rf.numero LIKE :paramValue ");
        
        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("paramValue", pNumeroContrat);
        
        myquery.setFirstResult(0);
        myquery.setMaxResults(100);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) myquery.getResultList();
        
		log.debug("END findByNumeroContrat : " + etablissements.size());
        return etablissements;
    }
	
	public List<Etablissement> findBySiret(String pSiret) throws JrafDaoException {
        Query myquery = entityManager.createQuery(" SELECT etab FROM Etablissement etab WHERE etab.siret = :paramValue ");
        myquery.setParameter("paramValue", pSiret);
        myquery.setMaxResults(2);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> results = (List<Etablissement>) myquery.getResultList();
        
        
		log.debug("END findBySiret : " + results.size());
        return results;
    }
	
	@SuppressWarnings("deprecation")
	public boolean existWithSameSiret(Etablissement pEtablissement) throws JrafDaoException {
        Assert.notNull(pEtablissement);
        Assert.hasText(pEtablissement.getSiret());
        
        Query myquery = null;
        
        StringBuilder jpql = new StringBuilder(" SELECT count(etab) FROM Etablissement etab WHERE etab.siret = :paramSiret ");
        if (pEtablissement.getGin() != null) {
            
            jpql.append(" AND etab.gin != :paramGin ");
            myquery = entityManager.createQuery(jpql.toString());
            myquery.setParameter("paramGin", pEtablissement.getGin());
            
        } else {
            
            myquery = entityManager.createQuery(jpql.toString());
        }
        myquery.setParameter("paramSiret", pEtablissement.getSiret());
                
        Long cnt = (Long) myquery.getSingleResult();
        
        return cnt != 0;
    }
	
	public Etablissement findUniqueBySiret(String pSiret) throws JrafDaoException {
        Etablissement result = null;
        
        Query myquery = entityManager.createQuery(" SELECT etab FROM Etablissement etab WHERE etab.siret = :paramValue ");
        myquery.setParameter("paramValue", pSiret);
        myquery.setMaxResults(2);
        
        @SuppressWarnings("unchecked")
        List<Etablissement> results = (List<Etablissement>) myquery.getResultList();
        
        switch (results.size()) {
        case 0:
            break;
        case 1:
            result = results.get(0);
            break;
        default:
            throw new JrafDaoException("Several firms are sharing the siret number " + pSiret);
        }
        
        return result;
    }
	
	public List<String> findMerged() throws JrafDaoException {
		log.debug("START findMerged : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e " +
        		"WHERE e.statut = 'FU'");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findMerged : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findMergedNotSginPereOnAgency() throws JrafDaoException {
		log.debug("START findMergedNotSginPereOnAgency : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e WHERE NOT EXISTS (SELECT a FROM Agence a WHERE a.parent.gin = e.gin) AND e.statut = 'FU' ");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findMergedNotSginPereOnAgency : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findClosed() throws JrafDaoException {
		log.debug("START findClosed : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e " +
        		"WHERE e.statut = 'X'");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosed : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findClosedNotSginPereOnAgency() throws JrafDaoException {
		log.debug("START findClosedNotSginPereOnAgency : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e WHERE NOT EXISTS (SELECT a.gin FROM Agence a WHERE a.parent.gin = e.gin) AND e.statut='X' ");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosedNotSginPereOnAgency : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findActPend() throws JrafDaoException {
		log.debug("START findActPend : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e " +
        		"WHERE e.statut = 'A' OR e.statut = 'P'");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findActPend : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findActPendNotSginPereOnAgency() throws JrafDaoException {
		log.debug("START findActPendNotSginPereOnAgency : " + System.currentTimeMillis());

       Query myquery = entityManager.createQuery(" SELECT DISTINCT e.gin FROM Etablissement e WHERE NOT EXISTS (SELECT a FROM Agence a WHERE a.parent.gin = e.gin) AND (e.statut = 'A' OR e.statut = 'P') ");
       
       @SuppressWarnings("unchecked")
       List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findActPendNotSginPereOnAgency : " + System.currentTimeMillis());

       return results;
   }
	
	public List<String> findClosedOrMergedInCancellationZone() throws JrafDaoException {
		log.debug("START findClosedOrMergedInCancellationZone : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery("SELECT etab.gin " +
        		"FROM Etablissement etab, PmZone pmz " +
				"WHERE etab.statut in ('X', 'FU') " +
				"AND etab.gin = pmz.personneMorale.gin " +
				"AND (pmz.dateFermeture is null OR pmz.dateFermeture > sysdate) " +
				"AND pmz.lienPrivilegie = 'O' " +
				"AND pmz.zoneDecoup.nature = 'ANN' " +
				"AND pmz.zoneDecoup.class = ZoneComm " +
				"GROUP BY etab.gin " +
				"HAVING count(*) = 1");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosedOrMergedInCancellationZone : " + System.currentTimeMillis());

        return results;
    }
	
	public List<String> findClosedOrMergedInCancellationZoneNotSginPereOnAgency() throws JrafDaoException {
		log.debug("START findClosedOrMergedInCancellationZoneNotSginPereOnAgency : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery("SELECT etab.gin FROM Etablissement etab, PmZone pmz WHERE NOT EXISTS (SELECT a FROM Agence a WHERE a.parent.gin = etab.gin) AND etab.statut in ('X', 'FU') AND etab.gin = pmz.personneMorale.gin AND (pmz.dateFermeture is null OR pmz.dateFermeture > sysdate) AND pmz.lienPrivilegie = 'O' AND pmz.zoneDecoup.nature = 'ANN' AND pmz.zoneDecoup.class = ZoneComm GROUP BY etab.gin HAVING count(*) = 1");
        
        @SuppressWarnings("unchecked")
        List<String> results = (List<String>) myquery.getResultList();

		log.debug("END findClosedOrMergedInCancellationZoneNotSginPereOnAgency : " + System.currentTimeMillis());

        return results;
    }
	
	public Etablissement findByGinWithPmZoneAndMembre(String ginValue) throws JrafDaoException {
		log.debug("START findByGinWithPmZoneAndMembre with gin = " + ginValue + " : " + System.currentTimeMillis());

        Etablissement result = null;
	      StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
	      
	      hql.append("LEFT JOIN FETCH etab.pmZones ");
	      hql.append("LEFT JOIN FETCH etab.membres ");
	      hql.append("WHERE etab.gin = :paramValue ");  
       
        Query myquery = entityManager.createQuery(hql.toString());
        
        myquery.setParameter("paramValue", ginValue);

        result = (Etablissement) myquery.getSingleResult();
        
		log.debug("END findByGinWithPmZoneAndMembre : " + System.currentTimeMillis());
        
        return result;
    }
	
	public Etablissement findByGinWithPmZone(String ginValue) throws JrafDaoException {
		log.debug("START findByGinWithPmZone with gin = " + ginValue + " : " + System.currentTimeMillis());

        Etablissement result = null;
	      StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
	      hql.append("LEFT JOIN FETCH etab.pmZones ");
	      hql.append("WHERE etab.gin = :paramValue ");  
        
        Query myquery = entityManager.createQuery(hql.toString());
        
        myquery.setParameter("paramValue", ginValue);

        result = (Etablissement) myquery.getSingleResult();
        
		log.debug("END findByGinWithPmZone : " + System.currentTimeMillis());
        
        return result;
    } 
	
	public Etablissement findByGinWithBusinessRole(String ginValue) throws JrafDaoException {
		log.debug("START findByGinWithBusinessRole with gin = " + ginValue + " : " + System.currentTimeMillis());

        Etablissement result = null;
		StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
		  
		hql.append("LEFT JOIN FETCH etab.businessRoles br ");
		hql.append("LEFT JOIN FETCH br.roleFirme ");
		hql.append("LEFT JOIN FETCH br.roleRcs ");
		  
		hql.append("WHERE etab.gin = :paramValue ");  
		
		Query myquery = entityManager.createQuery(hql.toString());
        
        myquery.setParameter("paramValue", ginValue);

        result = (Etablissement) myquery.getSingleResult();
        
		log.debug("END findByGinWithBusinessRole : " + System.currentTimeMillis());
        
        return result;
    }
	
	public Etablissement findByGinWithEnfant(String ginValue) throws JrafDaoException {
		log.debug("START findByGinWithEnfant with gin = " + ginValue + " : " + System.currentTimeMillis());

        Etablissement result = null;
	    StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
	      
	    hql.append("LEFT JOIN FETCH etab.enfants ");
	      
	    hql.append("WHERE etab.gin = :paramValue ");  
       
        Query myquery = entityManager.createQuery(hql.toString());
        
        myquery.setParameter("paramValue", ginValue);

        result = (Etablissement) myquery.getSingleResult();
        
		log.debug("END findByGinWithEnfant : " + System.currentTimeMillis());
        
        return result;
    }    
    
    public Etablissement findByGinWithPmZoneMembreEnfant(String ginValue) throws JrafDaoException {
		log.debug("START findByGinWithPmZoneMembreEnfant with gin = " + ginValue + " : " + System.currentTimeMillis());

        Etablissement result = null;
	    StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
	      
	    hql.append("LEFT JOIN FETCH etab.pmZones ");
	    hql.append("LEFT JOIN FETCH etab.membres ");
	      
	    hql.append("LEFT JOIN FETCH etab.businessRoles br ");
	    hql.append("LEFT JOIN FETCH br.roleFirme ");
	    hql.append("LEFT JOIN FETCH br.roleRcs ");
	      
	    hql.append("LEFT JOIN FETCH etab.enfants ");
	    hql.append("WHERE etab.gin = :paramValue ");  
       
        Query myquery = entityManager.createQuery(hql.toString());
        
        myquery.setParameter("paramValue", ginValue);

        result = (Etablissement) myquery.getSingleResult();
        
		log.debug("END findByGinWithPmZoneMembreEnfant : " + System.currentTimeMillis());
        
        return result;
    }        
    
    public List<Etablissement> findByNameWithAddressesUsingJpqlBadly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException {
		log.debug("BEGIN findByNameUsingHqlQuery(" + pName + "," + pIndex + "," + pMaxLines + ")");
        
        Assert.isTrue(pIndex >= 0 && pMaxLines >= 0, "pIndex and pMaxLines must be >= 0 ");
        
        // select distnct
        StringBuilder hql = new StringBuilder(" SELECT DISTINCT etab FROM Etablissement etab ");
        
        // join
        hql.append(" LEFT JOIN FETCH etab.postalAddresses addresses ");
        
        // where
        hql.append(" WHERE UPPER(etab.nom) LIKE :name ");
        
        // order
        hql.append(" ORDER BY etab.gin ");
        
        // query
        Query hqlQuery = entityManager.createQuery(hql.toString());
        
        // valorisation des parametres
        hqlQuery.setParameter("name", pName);
        
        // pagination
        hqlQuery.setFirstResult(pIndex);
        hqlQuery.setMaxResults(pMaxLines);
        
        // execution de la requete
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) hqlQuery.getResultList();
        
        // Analyse des logs :
        // Détectant le fetch, Hibernate avertit qu'il éxecute la requete en entier puis réalise la pagination en mémoire
        // "... WARN  [main] ast.QueryTranslatorImpl (QueryTranslatorImpl.java:353) - firstResult/maxResults specified with collection fetch; applying in memory!"
        // => Plus le nombre d'enregistrements est élevé, plus les performances en patissent
        
		log.debug("END findByName : " + etablissements.size());
        return etablissements;
    }
    
    public List<Etablissement> findByNameWithAddressesUsingJpqlProperly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException {
		log.debug("BEGIN findByNameWithAddressesUsingJpqlProperly(" + pName + "," + pIndex + "," + pMaxLines + ")");
        
        Assert.isTrue(pIndex >= 0 && pMaxLines >= 0, "pIndex and pMaxLines must be >= 0 ");
        
        // select distnct
        StringBuilder hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
        
        // where
        hql.append(" WHERE UPPER(etab.nom) LIKE :name ");
        
        // order
        hql.append(" ORDER BY etab.gin ");
        
        // query
        Query hqlQuery = entityManager.createQuery(hql.toString());
        
        // valorisation des parametres
        hqlQuery.setParameter("name", pName);
        
        // pagination
        hqlQuery.setFirstResult(pIndex);
        hqlQuery.setMaxResults(pMaxLines);
        
        // execution de la requete
        @SuppressWarnings("unchecked")
        List<Etablissement> lightEtablissements = (List<Etablissement>) hqlQuery.getResultList();
        
        // populate all collections
        //--------------------------
        
        hql = new StringBuilder(" SELECT etab FROM Etablissement etab ");
        hql.append(" LEFT JOIN FETCH etab.postalAddresses ");
        hql.append(" WHERE etab.gin = :gin ");
        
        List<Etablissement> fullEtablissements = new ArrayList<Etablissement>();
        for (Etablissement etab : lightEtablissements) {
           
            hqlQuery = entityManager.createQuery(hql.toString());
            hqlQuery.setParameter("gin", etab.getGin());
            fullEtablissements.add((Etablissement) hqlQuery.getSingleResult());
        }
        
		log.debug("END findByNameWithAddressesUsingJpqlProperly : " + fullEtablissements.size());
        return fullEtablissements;
    }
    
    @SuppressWarnings("deprecation")
	public List<Etablissement> findByNameWithAddressesUsingCriteriaBadly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException {
		log.debug("BEGIN findByNameUsingCriteria(" + pName + "," + pIndex + "," + pMaxLines + ")");
        
        Assert.isTrue(pIndex >= 0 && pMaxLines >= 0, " pIndex and pMaxLines must be >= 0 ");
        
        // recuperation de la session hibernate
        Session hSession = ((Session) entityManager.getDelegate());
        
        // select
        Criteria crit = hSession.createCriteria(Etablissement.class);
        
        // join
        crit.createAlias("postalAddresses", "addresses", JoinFragment.LEFT_OUTER_JOIN);
        
        // where
        crit.add(Restrictions.ilike("nom",pName));
        
        // order
        crit.addOrder(Order.asc("gin"));
        
        // distinct
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        
        // pagination
        crit.setFirstResult(pIndex);
        crit.setMaxResults(pMaxLines);
        
        // execution de la requete
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) crit.list();
        
        // Analyse des logs :
        // Criteria éxecute la requete en appliquant la pagination : select * from (select ...) where rownum < 20
        // Problème n°1 : les 20 1er enregistrements donnent 16 entités => La 1ere page est consituée de 16 etablissements
        // Problème n°2 : si les enregistrements d'une entité sont à cheval, on aura un etab en page 1 de manière incomplete et on aura ce même etab en page 2 de manière incomplete
        
		log.debug("END findByNameWithAddressesUsingCriteriaBadly : " + etablissements.size());
        return etablissements;
    }
    
    public List<Etablissement> findByNameWithAddressesUsingCriteriaProperly(final String pName, final int pIndex, final int pMaxLines) throws JrafDaoException {
		log.debug("BEGIN findByNameWithAddressesUsingCriteriaProperly(" + pName + "," + pIndex + "," + pMaxLines + ")");
        
        Assert.isTrue(pIndex >= 0 && pMaxLines >= 0, " pIndex and pMaxLines must be >= 0 ");
        
        // recuperation de la session hibernate
        Session hSession = ((Session) entityManager.getDelegate());
        
        // select
        Criteria crit = hSession.createCriteria(Etablissement.class);
        
        // where
        crit.add(Restrictions.ilike("nom",pName));
        
        // order
        crit.addOrder(Order.asc("gin"));
        
        // pagination
        crit.setFirstResult(pIndex);
        crit.setMaxResults(pMaxLines);
        
        // execution de la requete
        @SuppressWarnings("unchecked")
        List<Etablissement> etablissements = (List<Etablissement>) crit.list();
        
        for (Etablissement etablissement : etablissements) {
            
            if (etablissement.getPostalAddresses() != null) {
                Hibernate.initialize(etablissement.getPostalAddresses());
            }
        }
        
		log.debug("END findByNameWithAddressesUsingCriteriaProperly : " + etablissements.size());
        return etablissements;
    }
    
    public boolean addZoneDecoupToEtablissement(Etablissement pEtablissement, ZoneComm pZoneComm) {
    	StringBuilder hqlUpdate = new StringBuilder("insert into pm_zone (icle_pmz, igin_zone, sgin, slien_privilegie, ddate_ouverture, ddate_modif) values (iseq_pm_zone.nextval,");
    	
    	// values to insert
    	hqlUpdate.append(":ginZone, :ginEtablissement, 'O', sysdate, sysdate)");
        
        // query
        Query hqlQuery = entityManager.createNativeQuery(hqlUpdate.toString());
        
        // valorisation des parametres
        hqlQuery.setParameter("ginZone", pZoneComm.getGin());
        hqlQuery.setParameter("ginEtablissement", pEtablissement.getGin());
        
        // execution de la requete
        try {
        	hqlQuery.executeUpdate();
        } catch (Exception e) {
        	log.error("addZoneDecoupToEtablissement "+ e);
        	return false;
        }
    	return true;
    }

	public Etablissement findByGinUsingScopes(String gin, List<FirmScope> scopes) throws JrafDaoException {
		log.debug("BEGIN findByGinWithScopes(" + gin + ") at " + System.currentTimeMillis());

		// Check that GIN is not empty
		if (StringUtils.isEmpty(gin)) {
			throw new JrafDaoException("GIN cannot be empty!");
		}

		// Initialize return
        Etablissement result = null;

		// Get hibernate session
        Session hSession = ((Session) entityManager.getDelegate());
        
        // Create criteria
        Criteria criteria = null;
        try {
            criteria = hSession.createCriteria(Etablissement.class);
            criteria.setFetchMode("parent", FetchMode.JOIN);
            criteria.setFetchMode("parent.parent", FetchMode.JOIN);
            criteria.add(Restrictions.eq("gin", gin));
        } catch (Exception e) {
        	throw new JrafDaoException("Technical error!", e);
        }

        // Get result
		try {
			result = (Etablissement) criteria.uniqueResult();
		} catch(NoResultException e) {
			log.warn("No Etablissement found having GIN=" + gin);
		} catch(Exception e) {
			throw new JrafDaoException(e);
		}

        // Check retrieved firm
        if (result == null) {
        	throw new JrafDaoException("Firm with GIN=" + gin + " was not found in database!");
        }
        
        // Initialize variable to avoid searching zones more than once
        boolean zonesAlreadyRead = false;
        
        // Add additional information to the result by looping over the scopes
        for (FirmScope firmScope : scopes) {
        	// Get name of the scope
        	ScopeToProvideFirmEnum scopeName = firmScope.getFirmProvideScope();
        	
    		Integer firstResultIndex = firmScope.getFirstResultIndex();
    		if (firstResultIndex == null) {
    			firstResultIndex = 0;
    		}
    		
    		Integer maxResults = firmScope.getMaxResults();
    		if (maxResults == null) {
    			maxResults = Integer.MAX_VALUE;
    		}

        	if (scopeName == ScopeToProvideFirmEnum.POSTAL_ADDRESSES) {
        		// Add addresses to result
				List<PostalAddress> postalAddressesList = postalAddressRepository.findByPMGin(gin, new OffsetBasedPageRequest(firstResultIndex, maxResults));

				if (postalAddressesList != null) {
					result.setPostalAddresses(new HashSet<PostalAddress>(postalAddressesList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.TELECOMS) {
        		// Add telecoms to result
				List<Telecoms> telecomsList = telecomsRepository.findByPMGin(gin, new OffsetBasedPageRequest(firstResultIndex, maxResults));

				if (telecomsList != null) {
					result.setTelecoms(new HashSet<Telecoms>(telecomsList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.EMAILS) {
        		// Add telecoms to result
				List<Email> emailsList = emailRepository.findByPMGin(gin, new OffsetBasedPageRequest(firstResultIndex, maxResults));

				if (emailsList != null) {
					result.setEmails(new HashSet<Email>(emailsList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.CONTRACTS) {
        		// Add telecoms to result
				List<BusinessRole> businessRolesList = businessRoleRepository.findByGinPm(gin);

				if (businessRolesList != null) {
					result.setBusinessRoles(new HashSet<BusinessRole>(businessRolesList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.SYNONYMS) {
				// Add telecoms to result
				List<Synonyme> synonymsList = synonymeRepository.findByPMGin(gin, new OffsetBasedPageRequest(firmScope.getFirstResultIndex(), firmScope.getMaxResults()));

				if (synonymsList != null) {
					result.setSynonymes(new HashSet<Synonyme>(synonymsList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.KEY_NUMBERS) {
				// Add key numbers to result
				List<NumeroIdent> numIdentList = numeroIdentRepository.findByPMGin(gin, new OffsetBasedPageRequest(firmScope.getFirstResultIndex(), firmScope.getMaxResults()));

				if (numIdentList != null) {
					result.setNumerosIdent(new HashSet<NumeroIdent>(numIdentList));
				}
        	} else if (!zonesAlreadyRead && (scopeName == ScopeToProvideFirmEnum.COMMERCIAL_ZONES
        			|| scopeName == ScopeToProvideFirmEnum.FINANCIAL_ZONES
        			|| scopeName == ScopeToProvideFirmEnum.SALES_ZONES)) {

        		// Add zones to result
        		List<PmZone> pmZonesList = pmZoneRepository.findByPMGin(gin);

				if (pmZonesList != null) {
					result.setPmZones(new HashSet<PmZone>(pmZonesList));
				}
				
				zonesAlreadyRead = true;
        	} else if (scopeName == ScopeToProvideFirmEnum.NUMBERS) {
				// Add numbers to result
				List<Chiffre> chiffreList = chiffreRepository.findByPMGin(gin, firmScope.getFirstResultIndex(),
																firmScope.getMaxResults());

				if (chiffreList != null) {
					result.setChiffres(new HashSet<Chiffre>(chiffreList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.MARKET_CHOICES) {
				// Add numbers to result
				List<Segmentation> segmentationList = segmentationRepository.findByPMGin(gin, new OffsetBasedPageRequest(firmScope.getFirstResultIndex(), firmScope.getMaxResults()));

				if (segmentationList != null) {
					result.setSegmentations(new HashSet<Segmentation>(segmentationList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.SBT) {
        		// Add SBT to result
				SelfBookingTool sbt = selfBookingToolRepository.findByPMGin(gin);

				if (sbt != null) {
					result.setSelfBookingTool(sbt);
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.AGENCIES) {
				// Add numbers to result
				List<GestionPM> gestionPMList = gestionPMRepository.findByPMGereeGin(gin, firmScope.getFirstResultIndex(),
																firmScope.getMaxResults());

				if (gestionPMList != null) {
					result.setPersonnesMoralesGerantes(new HashSet<GestionPM>(gestionPMList));
				}
        	} else if (scopeName == ScopeToProvideFirmEnum.SERVICES) {
        		// Add services to result
				List<PersonneMorale> servicesList = personneMoraleRepository.findByParentGin(gin, firmScope.getFirstResultIndex(),
																firmScope.getMaxResults());

				if (servicesList != null) {
					result.setEnfants(new HashSet<PersonneMorale>(servicesList));
				}
        	}
        }


//        if(scopeToProvide!=null && (scopeToProvide.contains("CONTRACTS") || scopeToProvide.contains("ALL"))){
//            hql.append(" LEFT JOIN FETCH etab.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleAgence ");
//        }

		log.debug("END findByGinWithScopes(" + gin + ") at " + System.currentTimeMillis());

        return result;
	}
	
	public void refresh(Etablissement etablissement) {
		entityManager.refresh(etablissement);
	}

	@Override
	public List<Etablissement> findEtablissementByExample(Etablissement etablissement, int i, int j) {

		// Get hibernate session
        Session session = ((Session) entityManager.getDelegate());
        
        Example addressExample = Example.create(etablissement);
        Criteria criteria = session.createCriteria(Etablissement.class).add(addressExample);
        // pagination
        criteria.setFirstResult(i);
        criteria.setMaxResults(j);
        
		return criteria.list();
	}
}
