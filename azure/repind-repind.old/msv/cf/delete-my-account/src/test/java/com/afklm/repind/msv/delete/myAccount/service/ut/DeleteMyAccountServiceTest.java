package com.afklm.repind.msv.delete.myAccount.service.ut;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.delete.myAccount.criteria.DeleteMyAccountCriteria;
import com.afklm.repind.msv.delete.myAccount.model.error.ErrorMessage;
import com.afklm.repind.msv.delete.myAccount.service.DeleteMyAccountService;
import com.afklm.repind.msv.delete.myAccount.service.encoder.DeleteMyAccountEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    private DeleteMyAccountService deleteMyAccountService;

    String GIN = "123456789101";
    String FBInd = "123456";
    Integer CLEROLE = 12457;
    String MA_CONTRACT = "MA";
    private final String FB_CONTRACT = "FP";

    @BeforeEach
    public void setUp() {
        deleteMyAccountService = new DeleteMyAccountService(deleteMyAccountEncoder, accountIdentifierRepository, roleContratsRepository, businessRoleRepository);
    }


    @Test
    void deleteMyAccountTest() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();

        createData(accountData, roleContrats, businessRole);

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
    void deleteMyAccountTestNoRoleContract() throws BusinessException {
        //prepare
        DeleteMyAccountCriteria criteria = new DeleteMyAccountCriteria().withGin(GIN);

        AccountIdentifier accountData = new AccountIdentifier();
        RoleContract roleContrats = new RoleContract();
        BusinessRole businessRole = new BusinessRole();

        createData(accountData, roleContrats, businessRole);

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

        createData(accountData, roleContrats, null);


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

        createData(accountData, roleContrats, null);

        roleContrats.setTypeContrat(FB_CONTRACT);

        Optional<AccountIdentifier> accoutDataOpt = Optional.of(accountData);


        when(accountIdentifierRepository.findBySgin(GIN)).thenReturn(accoutDataOpt.get());
        when(roleContratsRepository.findByIndividuGinAndTypeContrat(GIN, FB_CONTRACT)).thenReturn(roleContrats);

        //rule
        Throwable exception = assertThrows(BusinessException.class, () -> deleteMyAccountService.deleteMyAccount(criteria));
        assertEquals(ErrorMessage.ERROR_MESSAGE_403_001.getDescription(), exception.getMessage());
    }


    public void createData(AccountIdentifier accountData, RoleContract roleContrats, BusinessRole businessRole) {
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