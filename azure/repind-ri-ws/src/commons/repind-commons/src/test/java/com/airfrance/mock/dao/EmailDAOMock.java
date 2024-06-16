//package com.airfrance.mock.dao;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.NonUniqueResultException;
//
//import com.airfrance.ref.exception.jraf.JrafDaoException;
//import com.airfrance.ref.exception.InvalidParameterException;
//import com.airfrance.repind.dao.adresse.IEmailDAO;
//import com.airfrance.repind.entity.adresse.Email;
//import com.airfrance.repind.entity.firme.Fonction;
//
///**
// * Test class that represents a mock for the MembreDAO.
// * It returns predefined values that could be used in tests.
// *
// * @author Ghayth AYARI
// *
// */
//public class EmailDAOMock implements IEmailDAO {
//
//	@Override
//	public void invalidOnEmail(Email email) throws JrafDaoException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public List<Email> findEmail(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Email> findPMEmail(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Email> findByPMGin(String gin, Integer firstResultIndex, Integer maxResults) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Email> findByFonction(Fonction fonction, Integer firstIndex, Integer maxResults)
//			throws JrafDaoException {
//
//		// Check parameters
//		if (fonction == null) {
//			throw new JrafDaoException("Parameter fonction cannot be null!");
//		}
//
//		// Prepare return
//		List<Email> result = new ArrayList<>();
//
//		Integer listSize = maxResults == null ? 3 : maxResults;
//
//		// Add the number needed of Telecoms
//		for (int i = 0; i < listSize; i++) {
//			Email telecoms = new Email();
//			telecoms.setFonction(fonction);
//			telecoms.setEmail("test@test.test");
//			result.add(telecoms);
//		}
//
//		return result;
//	}
//
//	@Override
//	public Email findDirectEmail(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Email> findMemberEmailByGin(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> getMbrEmailSainByKey(Integer key) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Email findMemberEmailById(String sain) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Email> findAll(Email entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long countAll(Email entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Email findById(Serializable id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Email findByExample(Email entity) throws InvalidParameterException, NonUniqueResultException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void removeById(Serializable id) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void create(Email entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void update(Email entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void remove(Email id) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Boolean isEmailFbMember(String gin, String email) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getNumberProOrPersoEmailByGinAndCodeMedium(String gin, String codeMedium) throws JrafDaoException {
//		return 0;
//	}
//
//}
