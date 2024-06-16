package com.afklm.repind.msv.delete.myAccount.service;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.commonpp.entity.paymentDetails.PaymentDetailsEntity;
import com.afklm.repind.commonpp.repository.paymentDetails.PaymentDetailsRepository;
import com.afklm.repind.msv.delete.myAccount.criteria.DeleteMyAccountCriteria;
import com.afklm.repind.msv.delete.myAccount.model.error.BusinessError;
import com.afklm.repind.msv.delete.myAccount.service.encoder.DeleteMyAccountEncoder;
import com.afklm.repind.msv.delete.myAccount.wrapper.WrapperDeleteMyAccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(transactionManager = "transactionManagerSic")
public class DeleteMyAccountService {

    private final DeleteMyAccountEncoder deleteMyAccountEncoder;

    private final AccountIdentifierRepository accountIdentifierRepository;

    private final RoleContractRepository roleContractRepository;

    private final BusinessRoleRepository businessRoleRepository;

    private final PreferenceDataRepository preferenceDataRepository;

    private final PreferenceRepository preferenceRepository;

    private final DeleteMyAccountPaymentDetailsService deleteMyAccountPaymentDetailsService;

    private static final String FB_CONTRACT = "FP";
    private static final String MA_CONTRACT = "MA";


    public DeleteMyAccountService(
            DeleteMyAccountEncoder deleteMyAccountEncoder,
            AccountIdentifierRepository accountIdentifierRepository,
            RoleContractRepository roleContractRepository,
            BusinessRoleRepository businessRoleRepository,
            PreferenceDataRepository preferenceDataRepository,
            PreferenceRepository preferenceRepository,
            DeleteMyAccountPaymentDetailsService deleteMyAccountPaymentDetailsService
    ) {
        this.deleteMyAccountEncoder = deleteMyAccountEncoder;
        this.accountIdentifierRepository = accountIdentifierRepository;
        this.roleContractRepository = roleContractRepository;
        this.businessRoleRepository = businessRoleRepository;
        this.preferenceDataRepository = preferenceDataRepository;
        this.preferenceRepository = preferenceRepository;
        this.deleteMyAccountPaymentDetailsService = deleteMyAccountPaymentDetailsService;
    }


    public ResponseEntity<WrapperDeleteMyAccountResponse> deleteMyAccount(DeleteMyAccountCriteria deleteMyAccountCriteria) throws BusinessException {

        log.info("delete myAccount contract check for: {}", deleteMyAccountCriteria.getGin());
        AccountIdentifier accountData = checkAccountData(deleteMyAccountCriteria.getGin());

        //check if other contracts exist
        checkIfOtherContractExist(accountData, FB_CONTRACT);

        deleteTravelerDocumentsAndTravelerCompanions(accountData);
        // Create another service to change Transactional. Transaction only worked at classes level
        deleteMyAccountPaymentDetailsService.deletePaymentPreferences(accountData);

        //delete MA_Contract, BusinessRole and AccountData
        deleteData(accountData, MA_CONTRACT);
        log.info("delete myAccount contract done for: {}", deleteMyAccountCriteria.getGin());
        return new ResponseEntity<>(deleteMyAccountEncoder.decode(deleteMyAccountCriteria.getGin()), HttpStatus.OK);
    }


    public AccountIdentifier checkAccountData(String gin) throws BusinessException {
        AccountIdentifier accoutDataOpt = accountIdentifierRepository.findBySgin(gin);

        if (accoutDataOpt == null) {
            throw new BusinessException(BusinessError.ACCOUNT_DATA_NOT_FOUND);
        }
        return accoutDataOpt;
    }

    public void checkIfOtherContractExist(AccountIdentifier accountData, String fbContract) throws BusinessException {

        log.info("check if other contract exist on accountData: {}", accountData.getSgin());
        RoleContract contratFB = roleContractRepository.findByIndividuGinAndTypeContrat(accountData.getSgin(), fbContract);

        if (accountData.getFbIdentifier() != null || contratFB != null) {
            throw new BusinessException(BusinessError.CANNOT_DELETE_AS_OTHER_CONTRACT_EXIST);
        }
    }

    public void deleteTravelerDocumentsAndTravelerCompanions(AccountIdentifier accountData) {
        List<PreferenceEntity> allPreferences = preferenceRepository.getPreferenceEntitiesByIndividuGin(accountData.getSgin());
        List<Long> prefToDelete = allPreferences.stream().filter(pref -> pref.getType().equals("TDC") || pref.getType().equals("TCC")).map(PreferenceEntity::getPreferenceId).toList();
        log.info("Found {} TCC AND TDC prefs to delete. Their preferences data will also be deleted", prefToDelete.size());
        preferenceDataRepository.deleteByPreferenceIdIn(prefToDelete);
        preferenceRepository.deleteByPreferenceIdIn(prefToDelete);
    }

    public void deleteData(AccountIdentifier accountData, String maContract) {
        // get contract and role
        RoleContract contratMA = roleContractRepository.findByIndividuGinAndTypeContrat(accountData.getSgin(), maContract);

        if (contratMA != null) {
            Optional<BusinessRole> businessRole = businessRoleRepository.findById(contratMA.getCleRole());
            roleContractRepository.delete(contratMA);
            businessRole.ifPresent(businessRoleRepository::delete);
        }

        // delete contrat and role
        accountIdentifierRepository.delete(accountData);
    }
}
