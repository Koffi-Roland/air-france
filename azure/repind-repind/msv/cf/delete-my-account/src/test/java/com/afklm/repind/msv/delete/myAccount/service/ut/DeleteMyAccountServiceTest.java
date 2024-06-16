package com.afklm.repind.msv.delete.myAccount.service.ut;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import com.afklm.repind.common.entity.preferences.PreferenceEntity;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.commonpp.repository.paymentDetails.PaymentDetailsRepository;
import com.afklm.repind.msv.delete.myAccount.criteria.DeleteMyAccountCriteria;
import com.afklm.repind.msv.delete.myAccount.model.error.ErrorMessage;
import com.afklm.repind.msv.delete.myAccount.service.DeleteMyAccountPaymentDetailsService;
import com.afklm.repind.msv.delete.myAccount.service.DeleteMyAccountService;
import com.afklm.repind.msv.delete.myAccount.service.encoder.DeleteMyAccountEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DeleteMyAccountServiceTest {
    @Mock
    private DeleteMyAccountEncoder deleteMyAccountEncoder;

    @Mock
    private AccountIdentifierRepository accountIdentifierRepository;

    @Mock
    private RoleContractRepository roleContratsRepository;

    @Mock
    private BusinessRoleRepository businessRoleRepository;

    @Mock
    private PreferenceDataRepository preferenceDataRepository;

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private PaymentDetailsRepository paymentDetailsRepository;

    @Mock
    private DeleteMyAccountPaymentDetailsService deleteMyAccountPaymentDetailsService;

    private DeleteMyAccountService deleteMyAccountService;

    String GIN = "123456789101";
    String FBInd = "123456";
    Integer CLEROLE = 12457;
    String MA_CONTRACT = "MA";
    private final String FB_CONTRACT = "FP";

    @BeforeEach
    public void setUp() {
        deleteMyAccountService = new DeleteMyAccountService(
                deleteMyAccountEncoder,
                accountIdentifierRepository,
                roleContratsRepository,
                businessRoleRepository,
                preferenceDataRepository,
                preferenceRepository,
                deleteMyAccountPaymentDetailsService
        );
    }


    @Test
    void deleteMyAccountTest() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();

        populateData(accountData, roleContrats, businessRole);

        roleContrats.setTypeContrat(MA_CONTRACT);


        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);


        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, MA_CONTRACT)).thenReturn(roleContrats);
        when(businessRoleRepository.findById(CLEROLE)).thenReturn(Optional.of(businessRole));

        deleteMyAccountService.deleteMyAccount(criteria);

        //verify
        verify(roleContratsRepository).delete(roleContrats);
        verify(businessRoleRepository).delete(businessRole);
        verify(accountIdentifierRepository).delete(accountData);
    }

    @Test
    void deleteMyAccountTestWithTCDAndTCC() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();

        populateData(accountData, roleContrats, businessRole);
        roleContrats.setTypeContrat(MA_CONTRACT);

        // Create Pref
        PreferenceEntity preferenceTDC = new PreferenceEntity();
        PreferenceDataEntity preferenceDataTCD = new PreferenceDataEntity();
        preferenceTDC.setPreferenceId(1L);
        preferenceTDC.setType("TDC");
        preferenceDataTCD.setPreference(preferenceTDC);
        preferenceDataTCD.setPreferenceDataId(5);

        PreferenceDataEntity preferenceDataTCC = new PreferenceDataEntity();
        PreferenceEntity preferenceTCC = new PreferenceEntity();
        preferenceTCC.setPreferenceId(2L);
        preferenceTCC.setType("TCC");
        preferenceDataTCC.setPreference(preferenceTCC);
        preferenceDataTCC.setPreferenceDataId(6);

        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);

        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, MA_CONTRACT)).thenReturn(roleContrats);
        when(businessRoleRepository.findById(CLEROLE)).thenReturn(Optional.of(businessRole));
        when(preferenceRepository.getPreferenceEntitiesByIndividuGin(GIN)).thenReturn(List.of(preferenceTDC, preferenceTCC));

        deleteMyAccountService.deleteMyAccount(criteria);

        //verify
        verify(roleContratsRepository).delete(roleContrats);
        verify(businessRoleRepository).delete(businessRole);
        verify(accountIdentifierRepository).delete(accountData);
    }

    @Test
    void deleteMyAccountTestWithOtherType() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();

        populateData(accountData, roleContrats, businessRole);
        roleContrats.setTypeContrat(MA_CONTRACT);

        // Create Pref
        PreferenceEntity preferenceTDC = new PreferenceEntity();
        PreferenceDataEntity preferenceDataTCD = new PreferenceDataEntity();
        preferenceTDC.setPreferenceId(1L);
        preferenceTDC.setType("TOTO");
        preferenceDataTCD.setPreference(preferenceTDC);
        preferenceDataTCD.setPreferenceDataId(5);

        PreferenceDataEntity preferenceDataTCC = new PreferenceDataEntity();
        PreferenceEntity preferenceTCC = new PreferenceEntity();
        preferenceTCC.setPreferenceId(2L);
        preferenceTCC.setType("TATA");
        preferenceDataTCC.setPreference(preferenceTCC);
        preferenceDataTCC.setPreferenceDataId(6);

        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);

        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, MA_CONTRACT)).thenReturn(roleContrats);
        when(businessRoleRepository.findById(CLEROLE)).thenReturn(Optional.of(businessRole));
        when(preferenceRepository.getPreferenceEntitiesByIndividuGin(GIN)).thenReturn(List.of(preferenceTDC, preferenceTCC));

        deleteMyAccountService.deleteMyAccount(criteria);

        //verify
        verify(roleContratsRepository).delete(roleContrats);
        verify(businessRoleRepository).delete(businessRole);
        verify(accountIdentifierRepository).delete(accountData);
    }


    @Test
    void deleteMyAccountTestNoRoleContract() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();
        populateData(accountData, roleContrats, businessRole);
        roleContrats.setTypeContrat(MA_CONTRACT);

        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);


        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, MA_CONTRACT)).thenReturn(null);

        deleteMyAccountService.deleteMyAccount(criteria);

        //verify
        verify(accountIdentifierRepository).delete(accountData);
    }

    @Test
    void deleteMyAccountTestAccountNotFound() {
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(null);

        //rule
        Throwable exception = assertThrows(BusinessException.class, () -> deleteMyAccountService.deleteMyAccount(criteria));
        assertEquals(ErrorMessage.ERROR_MESSAGE_404_001.getDescription(), exception.getMessage());
    }

    @Test
    void deleteMyAccountTestFbIdentifierNotExist() {
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();

        populateData(accountData, roleContrats, null);


        accountData.setFbIdentifier(FBInd);
        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);


        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());

        //rule
        Throwable exception = assertThrows(BusinessException.class, () -> deleteMyAccountService.deleteMyAccount(criteria));
        assertEquals(ErrorMessage.ERROR_MESSAGE_403_001.getDescription(), exception.getMessage());
    }

    @Test
    void deleteMyAccountTestFbContractNotFound() {
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();

        populateData(accountData, roleContrats, null);

        roleContrats.setTypeContrat(FB_CONTRACT);

        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);


        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, FB_CONTRACT)).thenReturn(roleContrats);

        //rule
        Throwable exception = assertThrows(BusinessException.class, () -> deleteMyAccountService.deleteMyAccount(criteria));
        assertEquals(ErrorMessage.ERROR_MESSAGE_403_001.getDescription(), exception.getMessage());
    }


    public void populateData(AccountIdentifier accountData, RoleContract roleContrats, BusinessRole businessRole) {
        Individu individu = new Individu();
        individu.setGin(GIN);
        accountData.setSgin(GIN);
        roleContrats.setIndividu(individu);
        roleContrats.setCleRole(CLEROLE);

        if (businessRole != null) {
            businessRole.setCleRole(CLEROLE);
        }
    }
}
