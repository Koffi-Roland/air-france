package com.airfrance.repind.dao.individu;

import com.airfrance.repind.entity.individu.AccountData;

import java.util.List;
import java.util.Map;

public interface AccountDataRepositoryCustom {

	String getMyAccountIdentifier();

	/**
     * Tableau contenant fbIdentifier et gin
     * @param pAccountData
     * @return Object[] if found, null otherwise
     */
    Object[] findFbIdentifierAndGin(AccountData pAccountData);

	Object[] findFbIdentifierAndGinV2(AccountData searchAccount);
    
	/**
	 * Map contenant les noms de propriété et leurs valeurs respectives
	 * @param pGin
	 * @return Map if found, null otherwise
	 */
	List<Map<String,?>> findSimplePropertiesByGin(String pGin);
	
}
