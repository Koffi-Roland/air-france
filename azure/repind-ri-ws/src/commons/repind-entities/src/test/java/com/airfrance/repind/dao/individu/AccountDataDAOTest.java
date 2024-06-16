package com.airfrance.repind.dao.individu;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import com.airfrance.repind.entity.individu.AccountData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class AccountDataDAOTest {

    /**
     * AccountData DAO
     */
    @Autowired
    private AccountDataRepository accountDataRepository;

    @Test(expected = Test.None.class /* no exception expected */)
    public void testFindFbIdentifierAndGinReturnNull() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();
        acc.setAccountIdentifier("sandra.cavallo#gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
        
        acc = new AccountData();
        acc.setEmailIdentifier("sandra.cavallo#gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
        
        acc = new AccountData();
        acc.setFbIdentifier("sandra.cavallo#gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
        
        acc = new AccountData();
        acc.setPersonnalizedIdentifier("sandra.cavallo#gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
        
        acc = new AccountData();
        acc.setSocialNetworkId("sandra.cavallo#gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void testFindFbIdentifierAndGinReturnFbAndGin() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();
        acc.setAccountIdentifier("090040AA");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.notNull(result[1]);
        
        acc = new AccountData();
        acc.setEmailIdentifier("sandra.cavallo@gmail.com");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.notNull(result[1]);
        
        acc = new AccountData();
        acc.setFbIdentifier("002033904400");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.notNull(result[1]);
        
        acc = new AccountData();
        acc.setPersonnalizedIdentifier("2033904400");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.notNull(result[1]);
        
        acc = new AccountData();
        acc.setSocialNetworkId("AFKL_201412011654__guid_S2NL6YOQyXqHKaMSoCm3KAeAwS7ZzrltxQNX");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.notNull(result[1]);
    }

@Test(expected = Test.None.class /* no exception expected */)
    @Transactional
    public void testFindByGin() throws JrafDaoException {
        
        String gin = "000000023331";
        
        AccountData account = accountDataRepository.findBySgin(gin);
        
        Assert.notNull(account, "Please, choose another gin");
        
        account.setLastConnexionDate(new Date());
        accountDataRepository.saveAndFlush(account);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    @Transactional
    public void testGetByGin() throws JrafDaoException {
        
        String gin = "000000023331";
        
        AccountData account = accountDataRepository.findBySgin(gin);
        
        Assert.notNull(account, "Please, choose another gin");
        
        account.setLastConnexionDate(new Date());
        accountDataRepository.saveAndFlush(account);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    @Transactional
    public void testFindByExample() throws JrafDaoException {
        
        String gin = "000000023331";
        
        AccountData accountExample = new AccountData();
        accountExample.setSgin(gin);
        List<AccountData> accounts = accountDataRepository.findAll(Example.of(accountExample));
        
        Assert.notEmpty(accounts, "Please, choose another gin");
        
        AccountData account = accounts.get(0);
        
        account.setLastConnexionDate(new Date());
        accountDataRepository.saveAndFlush(account);
    }
    

    // REPIND-433 : Recherche d'un SOCIAL_NETWORK_DATA ou alors d'un EXTERNAL_IDENTIFIER

    @Test(expected = Test.None.class /* no exception expected */)	// Cas de test de Base - Non regression
    public void testFindFbIdentifierAndGin_SocialNetworkData() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();

        acc.setSocialNetworkId("AFKL_201412121924__guid__oe0nCWkhneGRMzex3jeTvWAEf0w0HHxyZfq");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.isTrue("001100462855".equals(result[0]));
        Assert.notNull(result[1]);
        Assert.isTrue("400447543936".equals(result[1]));
    }


    @Test(expected = Test.None.class /* no exception expected */)		// Cas de test External Identifier ONLY non lié a un Account_Data
    public void testFindFbIdentifierAndGin_ExternalIdentifier_NOT_FOUND() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();

        acc.setSocialNetworkId("NumeroIDGIGYA_AFKL_1234567_1_0455428657");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void testFindFbIdentifierAndGin_SocialNetworkData_And_ExternalIdentifier() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();

        acc.setSocialNetworkId("AFKL_201412280211__guid_UtW54JLHzvjHZ37JhLGZPqm10Imab7qwKpNp");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.isTrue("001100721811".equals(result[0]));
        Assert.notNull(result[1]);
        Assert.isTrue("400448549954".equals(result[1]));
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void testFindFbIdentifierAndGin_SocialNetworkData_And_ExternalIdentifier_2() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();

        acc.setSocialNetworkId("AFKL_201504250118__guid_bKZUHEhlvJNJG4mzElEnb1OSdYs1i-ES3bgZ");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.notNull(result);
        Assert.notNull(result[0]);
        Assert.isTrue("002096820713".equals(result[0]));
        Assert.notNull(result[1]);
        Assert.isTrue("400400030644".equals(result[1]));
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void testFindFbIdentifierAndGin_ExternalIdentifier_NOT_EXISTS() throws JrafDaoException {

        AccountData acc = null;
        Object[] result = null;
        
        acc = new AccountData();

        acc.setSocialNetworkId("NumeroIDGIGYA_AFKL_12qdsqsd34567_1_04554sqdsqd28657");
        result = accountDataRepository.findFbIdentifierAndGin(acc);
        Assert.isNull(result);
    }
    
}
