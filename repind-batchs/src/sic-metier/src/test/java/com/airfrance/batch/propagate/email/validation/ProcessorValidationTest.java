package com.airfrance.batch.propagate.email.validation;

import com.airfrance.batch.propagate.email.utils.GenerateData;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProcessorValidationTest {

    @InjectMocks
    private ProcessorValidation processorValidation;

    @Mock
    private IndividuRepository individuRepository;

    @Mock
    private RoleContratsRepository roleContratsRepository;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private AccountDataRepository accountDataRepository;

    @Test
    public void testIsIndividuValid() {

        Individu individu = new Individu();
        individu.setStatutIndividu("V");

        Mockito.when(individuRepository.findBySgin(Mockito.anyString())).thenReturn(individu);

        // Test the function
        boolean result = processorValidation.isIndividuValid.apply(GenerateData.buildAccount());

        assertTrue(result);
    }

    @Test
    public void testIsIndividuFB() {

        RoleContrats roleContrat = new RoleContrats();
        roleContrat.setEtat("C");

        Mockito.when(individuRepository.isFlyingBlue(Mockito.anyString())).thenReturn(1L);
        Mockito.when(roleContratsRepository.findByNumeroContrat(Mockito.anyString())).thenReturn(roleContrat);

        // Test the function
        boolean result = processorValidation.isIndividuFB.apply(GenerateData.buildAccount());

        assertTrue(result);
    }

    @Test
    public void testIsIndividuMyAccount() {

        RoleContrats roleContrat = new RoleContrats();
        roleContrat.setEtat("C");

        Mockito.when(individuRepository.isMyAccount(Mockito.anyString())).thenReturn(1L);
        Mockito.when(roleContratsRepository.findByNumeroContrat(Mockito.anyString())).thenReturn(roleContrat);

        // Test the function
        boolean result = processorValidation.isIndividuMyAccount.apply(GenerateData.buildAccount());

        assertTrue(result);
    }

    @Test
    void testIsEmailUnique() {

        Mockito.when(emailRepository.findDirectEmail(Mockito.anyString())).thenReturn(GenerateData.buildListEmails());

        AccountData accountData = new AccountData();
        accountData.setSgin("test_sgin");
        Mockito.when(accountDataRepository.findByEmailIdentifier(Mockito.anyString())).thenReturn(accountData);

        // Test the function
        AccountData account = new AccountData();
        account.setSgin("test_sgin");
        boolean result = processorValidation.isEmailUnique.apply(account);

        assertFalse(result);
    }

    @Test
    void testIsEmailDirectUnique() {

        Mockito.when(emailRepository.findByEmail(Mockito.anyString())).thenReturn(GenerateData.buildListEmails());

        // Test the function
        boolean result1 = processorValidation.isEmailDirectUnique("test1@example.com", "test_sgin");
        boolean result2 = processorValidation.isEmailDirectUnique("test2@example.com", "test_sgin");

        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    void testGetDirectEmailFunction() {
        List<Email> emails = new ArrayList<>();
        emails.add(GenerateData.buildEmail());
        Mockito.when(emailRepository.findDirectEmail(Mockito.anyString())).thenReturn(emails);

        // Test the function
        String result = processorValidation.getDirectEmailFunction.apply("test_gin");

        assertEquals("test@example.com", result);
    }
}
