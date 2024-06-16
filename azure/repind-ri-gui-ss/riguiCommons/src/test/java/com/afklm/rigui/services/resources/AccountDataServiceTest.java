package com.afklm.rigui.services.resources;

import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.model.individual.ModelAccountData;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.AccountDataHelper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AccountDataServiceTest {

    @InjectMocks
    private AccountDataService accountDataService;

    @Mock
    private AccountDataRepository accountDataRepository;

    @Mock
    private AccountDataHelper accountDataHelper;

    private static final String GIN = "1234567890";

    @Test
    void getAll() throws ServiceException {
        AccountData accountData = new AccountData();
        accountData.setEmailIdentifier("email");
        accountData.setFbIdentifier("fb");
        when(accountDataRepository.findBySgin(GIN)).thenReturn(accountData);

        when(accountDataHelper.buildAccountModel(any()))
                .thenReturn(new ModelAccountData())
                .thenReturn(new ModelAccountData());

        List<ModelAccountData> models = accountDataService.getAll(GIN);
        Assertions.assertEquals(2, models.size());
        Assertions.assertEquals("email", models.get(0).getEmailFbIdentifier());
        Assertions.assertEquals("fb", models.get(1).getEmailFbIdentifier());
    }

    @Test
    void getAll_onlyOne() throws ServiceException {
        AccountData accountData = new AccountData();
        accountData.setEmailIdentifier("email");
        when(accountDataRepository.findBySgin(GIN)).thenReturn(accountData);

        when(accountDataHelper.buildAccountModel(any()))
                .thenReturn(new ModelAccountData());

        List<ModelAccountData> models = accountDataService.getAll(GIN);
        Assertions.assertEquals(1, models.size());
        Assertions.assertEquals("email", models.get(0).getEmailFbIdentifier());
    }
}
