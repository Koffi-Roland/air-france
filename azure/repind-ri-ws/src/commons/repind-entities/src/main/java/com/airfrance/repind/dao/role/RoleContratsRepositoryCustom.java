package com.airfrance.repind.dao.role;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface RoleContratsRepositoryCustom {
	
	public int getNumberFPContractsOrOthersByGin(String gin, String contract) throws JrafDaoException;
	
	public boolean isFlyingBlueOrMyAccountByGin(String gin, String contract) throws JrafDaoException;
	
	public boolean isGinAndCinFbMaExist(String gin, String cin);

	boolean isUniqueGinAndCinAndEmailExist(String gin, String cin, String email);
	
	List<Tuple> findRoleContratsByTypeWithoutAccountData(@Param("contractType") String contractType);
}
