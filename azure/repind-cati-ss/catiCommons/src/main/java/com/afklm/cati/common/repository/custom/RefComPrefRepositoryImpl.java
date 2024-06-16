package com.afklm.cati.common.repository.custom;

import com.afklm.cati.common.entity.RefComPref;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class RefComPrefRepositoryImpl implements RefComPrefRepositoryCustom {

    @PersistenceContext(unitName = "entityManagerFactorySic")
    private EntityManager entityManager;

    public Long countRefComPref(String domain, String language, String market) {
        StringBuilder strQueryTotalResults = new StringBuilder();
        strQueryTotalResults.append("select count(refComPref) from RefComPref refComPref ");
        strQueryTotalResults.append("where refComPref.domain.codeDomain = ");

        if (domain == null || domain.isEmpty()) {
            strQueryTotalResults.append(" some(select refComPrefDomain.codeDomain from RefComPrefDomain refComPrefDomain)");
        } else {
            strQueryTotalResults.append(" :domain ");
        }

        if (market != null && !market.isEmpty()) {
            strQueryTotalResults.append(" and (refComPref.market = :market or refComPref.market = '*')");
        }

        if (language != null && !language.isEmpty()) {
            strQueryTotalResults.append(" and (:language IN (refComPref.defaultLanguage1, refComPref.defaultLanguage2, refComPref.defaultLanguage3, refComPref.defaultLanguage4, refComPref.defaultLanguage5, refComPref.defaultLanguage6, refComPref.defaultLanguage7, refComPref.defaultLanguage8, refComPref.defaultLanguage9, refComPref.defaultLanguage10) or refComPref.defaultLanguage1 = '*' )");
        }

        Query myQuery = entityManager.createQuery(strQueryTotalResults.toString());

        if (domain != null && !domain.isEmpty()) {
            myQuery.setParameter("domain", domain);
        }

        if (market != null && !market.isEmpty()) {
            myQuery.setParameter("market", market);
        }

        if (language != null && !language.isEmpty()) {
            myQuery.setParameter("language", language);
        }

        Object singleResult = myQuery.getSingleResult();
        return (Long) singleResult;
    }

    @SuppressWarnings("unchecked")
    public List<RefComPref> provideRefComPrefWithPagination(String domain, String language, String market, int index, int maxResults) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append("select refComPref from RefComPref refComPref ");
        strQuery.append("where refComPref.domain.codeDomain = ");
        if (domain == null || domain.isEmpty()) {
            strQuery.append(" some(select refComPrefDomain.codeDomain from RefComPrefDomain refComPrefDomain)");
        } else {
            strQuery.append(" :domain ");
        }

        if (market != null && !market.isEmpty()) {
            strQuery.append(" and (refComPref.market =: market or refComPref.market = '*')");
        }

        if (language != null && !language.isEmpty()) {
            strQuery.append(" and (:language IN (refComPref.defaultLanguage1, refComPref.defaultLanguage2, refComPref.defaultLanguage3, refComPref.defaultLanguage4, refComPref.defaultLanguage5, refComPref.defaultLanguage6, refComPref.defaultLanguage7, refComPref.defaultLanguage8, refComPref.defaultLanguage9, refComPref.defaultLanguage10) or refComPref.defaultLanguage1 = '*' )");
        }

        strQuery.append(" order by refComPref.refComprefId");

        Query myQuery = entityManager.createQuery(strQuery.toString());

        if (domain != null && !domain.isEmpty()) {
            myQuery.setParameter("domain", domain);
        }

        if (market != null && !market.isEmpty()) {
            myQuery.setParameter("market", market);
        }

        if (language != null && !language.isEmpty()) {
            myQuery.setParameter("language", language);
        }

        myQuery.setFirstResult(index);
        myQuery.setMaxResults(maxResults);

        List<RefComPref> resultList;

        try {
            resultList = (List<RefComPref>) myQuery.getResultList();
        } catch (NoResultException e) {
            resultList = null;
        }

        return resultList;
    }
}
