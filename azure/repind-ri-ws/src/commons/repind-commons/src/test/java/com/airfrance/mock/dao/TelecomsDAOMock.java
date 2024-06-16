//package com.airfrance.mock.dao;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.NonUniqueResultException;
//
//import com.airfrance.ref.exception.jraf.JrafDaoException;
//import com.airfrance.ref.exception.jraf.JrafDomainException;
//import com.airfrance.ref.exception.InvalidParameterException;
//import com.airfrance.ref.type.MediumCodeEnum;
//import com.airfrance.ref.type.TerminalTypeEnum;
//import com.airfrance.repind.dao.adresse.ITelecomsDAO;
//import com.airfrance.repind.entity.adresse.Telecoms;
//import com.airfrance.repind.entity.firme.Fonction;
//
///**
// * Test class that represents a mock for the MembreDAO.
// * It returns predefined values that could be used in tests.
// *
// * @author Ghayth AYARI
// *
// */
//public class TelecomsDAOMock implements ITelecomsDAO {
//
//	@Override
//	public Telecoms getPreferedTelecom(String sgin) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Telecoms findLatest(Telecoms telecom) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Telecoms findLatestByUsageCode(Telecoms telecom) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Telecoms findLatest(String gin, TerminalTypeEnum terminalType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Telecoms findLatestByUsageCode(String gin, MediumCodeEnum mediumCode, TerminalTypeEnum terminalType) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void removeAllButThis(Telecoms telecom) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void removeAllButThisByUsageCode(Telecoms telecom) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public int updatePhoneNumberOnly(Telecoms telecom) throws JrafDomainException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List<Telecoms> findByPMGin(String gin, Integer firstResultIndex, Integer maxResults)
//			throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Telecoms> findPMTelecomsByGin(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Telecoms> findByFonction(Fonction fonction, Integer firstIndex, Integer maxResults)
//			throws JrafDaoException {
//
//		// Check parameters
//		if (fonction == null) {
//			throw new JrafDaoException("Parameter fonction cannot be null!");
//		}
//
//		// Prepare return
//		List<Telecoms> result = new ArrayList<>();
//
//		Integer listSize = maxResults == null ? 3 : maxResults;
//
//		// Add the number needed of Telecoms
//		for (int i = 0; i < listSize; i++) {
//			Telecoms telecoms = new Telecoms();
//			telecoms.setFonction(fonction);
//			telecoms.setSnumero("0611111111");
//			result.add(telecoms);
//		}
//
//		return result;
//	}
//
//	@Override
//	public Telecoms findLatestByUsageCodeValidOrNot(Telecoms telecom) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public int removeBySAIN(String sain) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Telecoms findMemberTelecomById(String sain) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> getMbrTelSainByKey(Integer key) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Telecoms> findTelecomsByGIN(String gin) throws JrafDaoException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getNumberTelecomsProOrPersoByGinAndCodeMedium(String gin, String codeMedium) throws JrafDaoException {
//		return 0;
//	}
//
//	@Override
//	public List<Telecoms> findAll(Telecoms entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long countAll(Telecoms entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public Telecoms findById(Serializable id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Telecoms findByExample(Telecoms entity) throws InvalidParameterException, NonUniqueResultException {
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
//	public void create(Telecoms entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void update(Telecoms entity) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public int removeByDelegationId(int delegationId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//}
