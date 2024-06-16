package com.airfrance.repind.dao.tracking;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;

import java.util.List;

public interface TriggerChangeIndividusRepositoryCustom {
	
	List<TriggerChangeIndividus> findTriggerChangeIndividus() throws JrafDaoException;
	
	List<TriggerChangeIndividus> findTriggerChangeIndividus(String nombreMaxParLot) throws JrafDaoException;
	
}
