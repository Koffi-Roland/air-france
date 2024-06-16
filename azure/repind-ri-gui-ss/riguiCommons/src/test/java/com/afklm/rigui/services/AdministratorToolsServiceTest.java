package com.afklm.rigui.services;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.enums.AccountDataStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AdministratorToolsServiceTest {

    @InjectMocks
    private AdministratorToolsService administratorToolsService;

    @Mock
    private IndividuRepository individualRepository;

    @Mock
    private AccountDataRepository accountDataRepository;

    @Mock
    private RoleContratsRepository roleContratsRepository;

    private final static String GIN = "123456789";

    @Test
    public void unmergeIndividual() throws ServiceException {
        AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(GIN, "M");
        Individu individu = new Individu();
        individu.setStatutIndividu("T");
        when(individualRepository.findBySgin(GIN)).thenReturn(individu);
        administratorToolsService.unmergeIndividual(criteria);
        verify(individualRepository, times(1)).unmergeIndividualByGin(eq(criteria.getGin()), any(), any(), eq(criteria.getMatricule()));
    }

    @Test
    public void unmergeIndividualError() throws ServiceException {
        AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(GIN, "M");
        Individu individu = new Individu();
        individu.setStatutIndividu("V");
        when(individualRepository.findBySgin(GIN)).thenReturn(individu);
        assertThrows(Exception.class, () -> {
            administratorToolsService.unmergeIndividual(criteria);
        });
    }

    @Test
    public void reactivateAccountGin() throws ServiceException {
        AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(GIN, "M");
        AccountData accountData = new AccountData();
        accountData.setAccountIdentifier("ID");
        accountData.setStatus(AccountDataStatusEnum.ACCOUNT_DELETED.code());
        when(accountDataRepository.findBySgin(criteria.getGin())).thenReturn(accountData);
        administratorToolsService.reactivateAccountGin(criteria);
        verify(accountDataRepository, times(1)).reactivateAccountData(eq(criteria.getGin()), any(), any(), eq(criteria.getMatricule()));
        verify(roleContratsRepository, times(1)).reactivateContractByNumeroContract(eq(accountData.getAccountIdentifier()), any(), any(), eq(criteria.getMatricule()));
    }

    @Test
    public void reactivateAccountGinError() throws ServiceException {
        AdministratorToolsCriteria criteria = new AdministratorToolsCriteria(GIN, "M");
        AccountData accountData = new AccountData();
        accountData.setAccountIdentifier("ID");
        accountData.setStatus(AccountDataStatusEnum.ACCOUNT_CLOSED.code());
        when(accountDataRepository.findBySgin(criteria.getGin())).thenReturn(accountData);

        assertThrows(Exception.class, () -> {
            administratorToolsService.reactivateAccountGin(criteria);
        });

    }


}
