package com.afklm.rigui.services;

import java.util.Date;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.AccountDataStatusEnum;
import com.afklm.rigui.dto.role.RoleContratsDTO;
import com.afklm.rigui.dto.role.RoleContratsTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.role.RoleContrats;

@Service
public class AdministratorToolsService {

	@Autowired
	private IndividuRepository individualRepository;
	
	@Autowired 
	private AccountDataRepository accountDataRepository;
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorToolsService.class);

	/**
	 * Unmerge individual, check if the individual is in status 'T' to allow the
	 * unmerge.
	 * 
	 * @param unmergeCriteria
	 * @throws ServiceException
	 */
	public void unmergeIndividual(AdministratorToolsCriteria unmergeCriteria) throws ServiceException {
		Individu individualToUnmerge = individualRepository.findBySgin(unmergeCriteria.getGin());
		if (!"T".equals(individualToUnmerge.getStatutIndividu())) {
			LOGGER.error(BusinessErrorList.API_BUSINESS_UNMERGE_WRONG_STATUS_ERROR.getError() + ": called by "
					+ unmergeCriteria.getMatricule() + "for GIN: " + unmergeCriteria.getGin());
			throw new ServiceException(BusinessErrorList.API_BUSINESS_UNMERGE_WRONG_STATUS_ERROR.getError(),
					HttpStatus.BAD_REQUEST);
		}
		individualRepository.unmergeIndividualByGin(unmergeCriteria.getGin(), new Date(), "Unmerge - RIGUI",
				unmergeCriteria.getMatricule());
	}
	
	/**
	 * Reactivate individual account and MYA contract, check if the individual account is in status 'D' to allow the
	 * reactivation.
	 * 
	 * @param reactivateAccountCriteria
	 * @throws ServiceException
	 */
	public void reactivateAccountGin(AdministratorToolsCriteria reactivateAccountCriteria) throws ServiceException {
		AccountData accountToReactivate = accountDataRepository.findBySgin(reactivateAccountCriteria.getGin());
		if (accountToReactivate == null || !AccountDataStatusEnum.ACCOUNT_DELETED.code().equals(accountToReactivate.getStatus())) {
			LOGGER.error(BusinessErrorList.API_BUSINESS_REACTIVATE_WRONG_STATUS_ERROR.getError() + ": called by "
					+ reactivateAccountCriteria.getMatricule() + "for GIN: " + reactivateAccountCriteria.getGin());
			throw new ServiceException(BusinessErrorList.API_BUSINESS_REACTIVATE_WRONG_STATUS_ERROR.getError(),
					HttpStatus.BAD_REQUEST);
		}

		accountDataRepository.reactivateAccountData(reactivateAccountCriteria.getGin(), new Date(), "RIGUI - admin",
				reactivateAccountCriteria.getMatricule());
		roleContratsRepository.reactivateContractByNumeroContract(accountToReactivate.getAccountIdentifier(),new Date(), "RIGUI - admin", reactivateAccountCriteria.getMatricule());
	}
	
}
