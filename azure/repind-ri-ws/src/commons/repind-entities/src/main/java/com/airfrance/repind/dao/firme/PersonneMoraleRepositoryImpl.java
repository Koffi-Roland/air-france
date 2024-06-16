package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.enums.SearchByIdEnum;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.util.ConstantValues;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;


public class PersonneMoraleRepositoryImpl implements PersonneMoraleRepositoryCustom {

    private static final Log log = LogFactory.getLog(PersonneMoraleRepositoryImpl.class);

    @PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<PersonneMorale> findClosedServicesNotModifiedSince(int numberOfDays) {
        log.debug("START findClosedServicesNotModifiedSince : " + System.currentTimeMillis());

        Query myquery = entityManager.createQuery(" SELECT DISTINCT pm FROM PersonneMorale pm LEFT JOIN FETCH pm.businessRoles br LEFT JOIN FETCH br.roleFirme LEFT JOIN FETCH br.roleRcs WHERE pm.type = 'S' AND pm.statut = 'X' AND pm.dateModification <= :sinceDate ");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -numberOfDays);
        myquery.setParameter("sinceDate", cal.getTime());

        List<PersonneMorale> results = (List<PersonneMorale>) myquery.getResultList();

        log.debug("END findClosedServicesNotModifiedSince : " + System.currentTimeMillis());

        return results;
    }

    @SuppressWarnings("rawtypes")
    public PersonneMorale findPMByOptions(String optionType, String optionValue) throws JrafDaoException {
        log.debug("START findPMByOptions : " + System.currentTimeMillis());

        PersonneMorale result = null;
        StringBuilder hql = new StringBuilder("");
        try {
            switch (SearchByIdEnum.valueOf(optionType)) {
                // GIN non appelé, cas géré dans ProvideFirmDataDS
                case GIN:
                    hql.append(" SELECT pm FROM PersonneMorale pm WHERE pm.gin = :paramValue ");
                    break;

                case SR:
                    hql.append(" SELECT DISTINCT entreprise FROM Entreprise entreprise WHERE entreprise.siren = :paramValue ");
                    break;

                case ST:
                    hql.append(" SELECT DISTINCT etab FROM Etablissement etab WHERE etab.siret = :paramValue ");
                    break;

                case NCSC:

                    hql.append(" SELECT DISTINCT pm FROM PersonneMorale pm LEFT JOIN pm.businessRoles AS br JOIN br.roleFirme AS role WITH role.numero = :paramValue");
                    break;

                case NC:

                    hql.append(" " +
                            "SELECT DISTINCT pm " +
                            "FROM PersonneMorale pm JOIN pm.businessRoles AS br " +
                            "WITH br.type = 'N' AND br.numeroContrat = :paramValue");
                    break;

                default:
                    hql.append(" SELECT DISTINCT pm FROM PersonneMorale pm, NumeroIdent noIdent WHERE noIdent.type = :paramType AND noIdent.numero = :paramValue AND noIdent.personneMorale.gin = pm.gin AND (noIdent.dateFermeture IS NULL OR noIdent.dateFermeture >= sysdate) ");
                    break;

            }
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage(), e);
            throw new JrafDaoException(ConstantValues.NO_ENUM_CONSTANT_EXCEPTION, e);
        }

        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("paramValue", optionValue);
        if (!StringUtils.equalsAny(optionType, "GIN", "SR", "ST", "NCSC", "NC")) {
            myquery.setParameter("paramType", optionType);
        }

        List listResults = myquery.getResultList();
        if (!listResults.isEmpty()) {
            if (listResults.size() == 1) {
                result = (PersonneMorale) listResults.get(0);
            } else {
                String msg = "Many objects match with " + optionType + " = " + optionValue;
                throw new TooManyResultsDaoException(msg);
            }

        }

        log.debug("END findPMByOptions : " + System.currentTimeMillis());

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<PersonneMorale> findByNumeroContrat(String pNumeroContrat) throws JrafDaoException {
        log.debug("BEGIN findByNumeroContrat with " + pNumeroContrat);

        StringBuilder hql = new StringBuilder(" SELECT DISTINCT pm FROM PersonneMorale pm INNER JOIN pm.businessRoles AS br INNER JOIN br.roleFirme rf WITH rf.numero LIKE :paramValue ");

        Query myquery = entityManager.createQuery(hql.toString());
        myquery.setParameter("paramValue", pNumeroContrat);

        myquery.setFirstResult(0);
        myquery.setMaxResults(100);

        List<PersonneMorale> personnesMorales = (List<PersonneMorale>) myquery.getResultList();

        log.debug("END findByNumeroContrat : " + personnesMorales.size());
        return personnesMorales;
    }

    public void deleteByPMList(List<String> pLstGinPersonneMorale) throws JrafDaoException {
        log.debug("BEGIN deleteByPMList with " + pLstGinPersonneMorale);

        // construct where clause from pLstPersonneMorale list
        StringBuilder sWhere = new StringBuilder("SGIN IN (");
        int nbPM = pLstGinPersonneMorale.size();
        for (int i = 0; i < nbPM; i++) {
            String ginPersonneMorale = pLstGinPersonneMorale.get(i);

            sWhere.append(ginPersonneMorale);
            if (i < nbPM - 1)
                sWhere.append(", ");
        }
        sWhere.append(")");

        // Construct the query to call the stored procedure DELETE_CASCADE_NO_COMMENT
        Query query = entityManager.createNativeQuery("CALL DELETE_CASCADE_NO_COMMENT(:sTableName, :sWhere)");
        query.setParameter("sTableName", "PERS_MORALE");
        query.setParameter("sWhere", sWhere.toString());

        // run the query
        query.executeUpdate();

        log.debug("END deleteByPMList : " + pLstGinPersonneMorale.size());
    }
    
    public void deleteCascadeByGin(String gin) throws JrafDaoException {
        log.debug("BEGIN deleteByGin with " + gin);

        StringBuilder sWhere = new StringBuilder("SGIN=").append(gin);

        // Construct the query to call the stored procedure DELETE_CASCADE_NO_COMMENT
        Query query = entityManager.createNativeQuery("CALL DELETE_CASCADE_NO_COMMENT(:sTableName, :sWhere)");
        query.setParameter("sTableName", "PERS_MORALE");
        query.setParameter("sWhere", sWhere.toString());

        // Run the query
        query.executeUpdate();

        log.debug("END deleteCascadeByGin : " + gin);
    }

    @SuppressWarnings("unchecked")
    public List<PersonneMorale> findByParentGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException {
        log.debug("START findByParentGin: " + System.currentTimeMillis());

        // Prepare return
        List<PersonneMorale> result = null;

        // Get hibernate session
        Session hSession = ((Session) entityManager.getDelegate());

        // Create criteria
        Criteria criteria = null;
        try {
            criteria = hSession.createCriteria(PersonneMorale.class);
            criteria.add(Restrictions.eq("parent.gin", gin));
            criteria.addOrder(Order.desc("dateModification"));
            criteria.setFetchMode("businessRoles", FetchMode.SELECT);
            criteria.setFetchMode("chiffres", FetchMode.SELECT);
            criteria.setFetchMode("compagniesAlliees", FetchMode.SELECT);
            criteria.setFetchMode("emails", FetchMode.SELECT);
            criteria.setFetchMode("enfants", FetchMode.SELECT);
            criteria.setFetchMode("membres", FetchMode.SELECT);
            criteria.setFetchMode("numerosIdent", FetchMode.SELECT);
            criteria.setFetchMode("parent", FetchMode.SELECT);
            criteria.setFetchMode("personnesMoralesGerantes", FetchMode.SELECT);
            criteria.setFetchMode("personnesMoralesGerees", FetchMode.SELECT);
            criteria.setFetchMode("pmZones", FetchMode.SELECT);
            criteria.setFetchMode("postalAddresses", FetchMode.SELECT);
            criteria.setFetchMode("profilFirme", FetchMode.SELECT);
            criteria.setFetchMode("profils", FetchMode.SELECT);
            criteria.setFetchMode("segmentations", FetchMode.SELECT);
            criteria.setFetchMode("selfBookingTool", FetchMode.SELECT);
            criteria.setFetchMode("synonymes", FetchMode.SELECT);
            criteria.setFetchMode("telecoms", FetchMode.SELECT);
            if (firstResultIndex != null) {
                criteria.setFirstResult(firstResultIndex);
            }
            if (maxResults != null) {
                criteria.setMaxResults(maxResults);
            }
        } catch (Exception e) {
            throw new JrafDaoException("Technical error!", e);
        }

        // Get results
        try {
            result = (List<PersonneMorale>) criteria.list();
        } catch (NoResultException e) {
            log.warn("No PersonneMorale found with parent GIN=" + gin);
        } catch (Exception e) {
            throw new JrafDaoException(e);
        }

        log.debug("END findByParentGin: " + System.currentTimeMillis());

        return result;
    }

    public void refresh(PersonneMorale personneMorale) {
        entityManager.refresh(personneMorale);
    }
}
