package com.airfrance.repind.dao.tracking;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class TriggerChangeIndividusRepositoryImpl implements TriggerChangeIndividusRepositoryCustom {
	
	private static final String MAX_NEI_BLOCK_SIZE = "200000";

    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

	public List<TriggerChangeIndividus> findTriggerChangeIndividus() throws JrafDaoException {
		// REPIND-1170 : Get in a first time a 1 000 000 and no more in order to not get a GC Oversize Limit
		// "AND ROWNUM < 50000 " +					// DEV
		// REPIND-1353 : 500 000 is two hight, because we need 3Gb on LIVE env. So 200 000 or 100 000 seems better...
		// "AND ROWNUM < 200000 " +				// PROD
    	return findTriggerChangeIndividus(MAX_NEI_BLOCK_SIZE);
    }

    @SuppressWarnings("unchecked")
	public List<TriggerChangeIndividus> findTriggerChangeIndividus(String nombreMaxParLot) throws JrafDaoException {
    	List<TriggerChangeIndividus> result = null;
    	if (nombreMaxParLot == null || "".equals(nombreMaxParLot)) {
    		nombreMaxParLot = MAX_NEI_BLOCK_SIZE;
    	}
    	StringBuilder request = new StringBuilder("" +
    			"SELECT tci " +
    			"FROM TriggerChangeIndividus tci " +
    			"WHERE tci.changeStatus = 'N' AND (tci.siteModification <> 'BATCH_QVI' or tci.siteModification is null) AND tci.gin is not null " +
    			// REPIND-1170 : Get in a first time a 1 000 000 and no more in order to not get a GC Oversize Limit
    			// "AND ROWNUM < 50000 " +					// DEV
    			// REPIND-1353 : 500 000 is two hight, because we need 3Gb on LIVE env. So 200 000 or 100 000 seems better...
    			"AND ROWNUM < " + nombreMaxParLot + " " +	// PROD
    			"ORDER BY tci.gin");  
    	    	
    	Query myquery = entityManager.createQuery(request.toString());
    	List<TriggerChangeIndividus> listResults = myquery.getResultList();
		if(!listResults.isEmpty()){
			result = listResults;
		}
    	
    	return result;
    }
}
