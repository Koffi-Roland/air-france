package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.NumeroIdent;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NumeroIdentRepositoryCustom {
	
	/**
	 * Find all NumeroIdent between two date
	 * @param key
	 * @param numero
	 * @param type
	 * @param dateOuverture
	 * @param dateFermeture
	 * @return
	 */
	public List<NumeroIdent> findAllByKeyNumberTypeDate(@Param("key") Long key,@Param("numero") String numero, @Param("types") String type, @Param("dateOuverture")String dateOuverture,@Param("dateFermeture")String dateFermeture);


}
