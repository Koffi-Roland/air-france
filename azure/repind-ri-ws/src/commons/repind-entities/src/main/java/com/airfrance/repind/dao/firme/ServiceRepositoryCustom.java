package com.airfrance.repind.dao.firme;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.entity.firme.Service;

import java.util.List;

public interface ServiceRepositoryCustom {
	
	public List<Service> findClosedNotModifiedSince(int numberOfDays) throws JrafDaoException;
	
	public List<Service> findClosedNotModifiedSinceNotSginPereOnAgency(int numberOfDays) throws JrafDaoException;
	
	public Service findByGinWithAllCollections(final String pGin) throws JrafDaoException;
	
	public Service findByGinWithAllCollections(final String pGin, List<String> scopeToProvide) throws JrafDaoException;
	
	public void refresh(Service service);
}
