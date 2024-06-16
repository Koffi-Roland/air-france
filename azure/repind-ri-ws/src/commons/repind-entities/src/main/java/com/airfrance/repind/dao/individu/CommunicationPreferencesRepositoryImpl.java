package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.CommunicationPreferences;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

public class CommunicationPreferencesRepositoryImpl implements CommunicationPreferencesRepositoryCustom {


	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int unsubscribeCommPref(CommunicationPreferences communicationPreferences) {

		Date today = new Date();

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update SIC2.COMMUNICATION_PREFERENCES ");
		strQuery.append("set");
		strQuery.append(" SUBSCRIBE=:optout, ");
		strQuery.append(" MODIFICATION_SIGNATURE=:signatureModification, ");
		strQuery.append(" MODIFICATION_DATE=:modificationDate, ");
		strQuery.append(" MODIFICATION_SITE=:modificationSite ");
		strQuery.append(" where COM_PREF_ID=:comPrefId ");

		final Query myQuery = entityManager.createNativeQuery(strQuery.toString(), CommunicationPreferences.class);
		myQuery.setParameter("optout", "N");
		myQuery.setParameter("signatureModification", communicationPreferences.getModificationSignature());
		myQuery.setParameter("modificationDate", today);
		myQuery.setParameter("modificationSite", communicationPreferences.getModificationSite());
		myQuery.setParameter("comPrefId", communicationPreferences.getComPrefId());

		communicationPreferences.setSubscribe("N");
		communicationPreferences.setModificationSignature(communicationPreferences.getModificationSignature());
		communicationPreferences.setModificationDate(today);
		communicationPreferences.setModificationSite(communicationPreferences.getModificationSite());

		return myQuery.executeUpdate();
	}

	@Override
	@Transactional
	public void updateComPref(CommunicationPreferences comPref) {

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("update SIC2.COMMUNICATION_PREFERENCES ");
		strQuery.append("set");
		strQuery.append(" SUBSCRIBE=:optin, ");
		strQuery.append(" MODIFICATION_SIGNATURE=:signatureModification, ");
		strQuery.append(" MODIFICATION_DATE=:modificationDate, ");
		strQuery.append(" MODIFICATION_SITE=:modificationSite ");
		strQuery.append(" where COM_PREF_ID=:comPrefId ");

		final Query myQuery = entityManager.createNativeQuery(strQuery.toString(), CommunicationPreferences.class);
		myQuery.setParameter("optin", comPref.getSubscribe());
		myQuery.setParameter("signatureModification", comPref.getModificationSignature());
		myQuery.setParameter("modificationDate", new Date());
		myQuery.setParameter("modificationSite", comPref.getModificationSite());
		myQuery.setParameter("comPrefId", comPref.getComPrefId());

		myQuery.executeUpdate();
		
	}
	
}
