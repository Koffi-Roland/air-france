package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.individu.CommunicationPreferences;

public interface CommunicationPreferencesRepositoryCustom {


	/**
	 * unsubscribeCommPref : set global opt-in to 'N' (subscribe field)
	 * @param bo communicationPreferences CommunicationPreferences
	 * @throws JrafDaoException
	 * @return Nb of unsubscriptions CP
	 * @see com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS#unsubscribeCommPref(CommunicationPreferences communicationPreferences)
	 */
	int unsubscribeCommPref(CommunicationPreferences communicationPreferences);

	/**
	 * For SGIN, COM_GROUP_TYPE, COM_TYPE, MEDIA='E', DOMAIN Update communication preferences
	 *
	 * @param gin
	 * @param domain
	 * @param comGroupType
	 * @param comType
	 * @throws JrafDaoException
	 */
	void updateComPref(CommunicationPreferences compPref);


}
