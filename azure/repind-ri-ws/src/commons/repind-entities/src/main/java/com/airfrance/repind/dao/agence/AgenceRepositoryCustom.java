package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.agence.Agence;

import java.util.Date;
import java.util.List;

public interface AgenceRepositoryCustom {
	
	public List<Agence> findByNumero(String pNumero);

    public Agence findAgenceByStringRequest(StringBuilder request, String optionValue, Date searchDate, String optionType) throws JrafDaoException;
    
    /**
     *  Find an agency by options
     * @param optionType 
     * @param optionValue
     * @param scopeToProvide
     * @return agence
     */
    public Agence findAgencyByOptions(String optionType, String optionValue, Date searchDate, List<String> scopeToProvide) throws JrafDaoException;
    
    /**
     *  Find an agency by Gin
     * @param gin 
     * @param scopeToProvide
     * @return agence
     */
    public Agence findAgencyByGin(String gin, List<String> scopeToProvide) throws JrafDaoException;

	public Integer getVersionByNumIdent(String pIdentValue) throws JrafDaoException;
	
	public void refresh(Agence agence);
}
