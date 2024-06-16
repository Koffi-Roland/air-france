package com.airfrance.repind.dao.agence;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.agence.MembreReseau;

import java.util.Date;
import java.util.List;

public interface MembreReseauRepositoryCustom {
	
	/**
	 * Count subnetworks link for each parent network
	 * 
	 * @param membreReseau
	 * @return
	 */
	int countSubNetworks(MembreReseau membreReseau);

	/**
	 * Get parents links for a subNetwork link 
	 * 
	 * @param pGinValue
	 * @param pCodeChild
	 * @return
	 * @throws JrafDaoException
	 */
	List<MembreReseau> getParentLinkNetwork(String pGinValue, String pCodeChild) throws JrafDaoException;
	
	
	/**
	 * Get networks by type 
	 * 
	 * @param gin
	 * @param networkType
	 * @return
	 * @throws JrafDaoException
	 */
	List<MembreReseau> getLinkNetworksByTypeAndCode(String gin, String networkType) throws JrafDaoException;


	/**
	 * @param gGinValue
	 * @param pCodeValue
	 * @param pDateDebut
	 * @return
	 * @throws JrafDaoException 
	 */
	Integer getCleMembreReseau(String gGinValue, String pCodeValue, Date pDateDebut) throws JrafDaoException;
    
	/**
	 * @param gGinValue
	 * @param pCodeValue
	 * @return
	 * @throws JrafDaoException 
	 */
	Integer getCleMembreReseauWithoutEntryDate(String gGinValue, String pCodeValue) throws JrafDaoException;
}
