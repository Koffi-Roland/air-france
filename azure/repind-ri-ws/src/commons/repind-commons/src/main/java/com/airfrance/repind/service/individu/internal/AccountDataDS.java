package com.airfrance.repind.service.individu.internal;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.AccountDataStatusEnum;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.AccountDataTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AccountDataDS {

    /**
     * logger
     */
    private static final Log log = LogFactory.getLog(AccountDataDS.class);

    /**
     * the Entity Manager
     */
    @PersistenceContext(unitName = "entityManagerFactoryRepind")
    private EntityManager entityManager;


    /**
     * main dao
     */
    @Autowired
    private AccountDataRepository accountDataRepository;

    /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ u var) ENABLED START*/

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Autowired
    @Qualifier("emailDS")
    private EmailDS emailDS;

    /*PROTECTED REGION END*/

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Integer countWhere(AccountDataDTO dto) throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM countWhere) ENABLED START*/
        Integer count = null;
        AccountData accountData = null;
        // conversion light de dto -> bo
        accountData = AccountDataTransform.dto2Bo(dto);

        // execution du denombrement
        count = (int) accountDataRepository.count(Example.of(accountData));
        return count;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(AccountDataDTO accountDataDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM create) ENABLED START*/
        AccountData accountData = null;

        // transformation light dto -> bo
        accountData = AccountDataTransform.dto2Bo(accountDataDTO);

        if (accountData.getNbFailureAuthentification() == null) {
            accountData.setNbFailureAuthentification(0);
        }
        if (accountData.getNbFailureSecretQuestionAns() == null) {
            accountData.setNbFailureSecretQuestionAns(0);
        }
        if (accountData.getPasswordToChange() == null) {
            accountData.setPasswordToChange(null);
        }
        if (accountData.getEmailIdentifier() != null) {
            accountData.setEmailIdentifier(accountData.getEmailIdentifier().toLowerCase());
        }

        String gin = null;
        if (accountDataDTO.getIndividudto() != null
                && accountDataDTO.getIndividudto().getSgin() != null) {
            gin = accountDataDTO.getIndividudto().getSgin();
        } else if (accountDataDTO.getSgin() != null) {
            gin = accountDataDTO.getSgin();
        }
        if (gin == null) {
            throw new MissingParameterException(
                    "Individual is mandatory for an account data");
        } else {
            Individu individu = individuRepository.findBySgin(gin);
            if (individu == null) {
                throw new IndivudualNotFoundException("Individual not found for an account data");
            } else {
                accountData.setIndividu(individu);
            }
        }

        if (!isUniqueIdentifier(accountDataDTO)) {
            throw new IdentifierAlreadyUsedException("Identifier already used "
                    + accountDataDTO);
        }

        // creation en base
        // Appel create de l'Abstract
        accountDataRepository.saveAndFlush(accountData);

        // Version update and Id update if needed
        AccountDataTransform.bo2Dto(accountData, accountDataDTO);
        // AccountDataTransform.bo2DtoLink(accountDataDTO, accountData);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(AccountDataDTO dto) throws JrafDomainException {

        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM remove) ENABLED START*/
        AccountData accountData = null;
        // chargement du bo
        if (dto.getId() != null) {
            accountData = accountDataRepository.getOne(dto.getId());
        } else if (dto.getSgin() != null) {
            AccountData whereClause = new AccountData();
            whereClause.setSgin(dto.getSgin());
            List<AccountData> findList = accountDataRepository.findAll(Example.of(whereClause));
            if (findList.size() > 1) {
                throw new SeveralAccountDataException("several AccountData found for gin " + dto.getSgin());
            } else {
                accountData = findList.get(0);
            }
            dto.setId(accountData.getId());
        } else {
            throw new MissingParameterException("An ID is needed for remove an AccountData" + accountData);
        }


        // Checking the optimistic strategy
//            if (!(accountData.getVersion().equals(dto.getVersion()))) {
//                throw new SimultaneousUpdateException("Simultaneous update on following accountData: "+ accountData.getId());
//            }

        // transformation light dto -> bo
        AccountDataTransform.dto2Bo(dto, accountData);

        // suppression en base
        accountDataRepository.delete(accountData);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<AccountDataDTO> findOnlyAccountByGin(String gin) throws JrafDomainException {

        List<AccountData> findList = accountDataRepository.findAllByGin(gin);

        // transformation light bo -> dto
        return AccountDataTransform.bo2DtoLightWithIndividual(findList);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public boolean isAccountDeleteByGin(String gin) throws JrafDomainException {

        // transformation light bo -> dto
        return accountDataRepository.findBySginAndStatus(gin, AccountDataStatusEnum.ACCOUNT_DELETED.code()) != null;
    }
    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<AccountDataDTO> findByExample(AccountDataDTO dto) throws JrafDomainException {

        AccountData accountData = new AccountData();

        AccountDataTransform.dto2Bo(dto, accountData);
        List<AccountData> findList = accountDataRepository.findAll(Example.of(accountData));

        // transformation light bo -> dto
        return AccountDataTransform.bo2Dto(findList);
    }

    @Transactional(readOnly = true)
    public List<AccountDataDTO> findAllByGin(String gin) throws JrafDomainException {

        List<AccountData> findList = accountDataRepository.findAllByGin(gin);

        // transformation light bo -> dto
        return AccountDataTransform.bo2Dto(findList);
    }


    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(Integer oid) throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM remove2) ENABLED START*/
        accountDataRepository.deleteById(oid);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(AccountDataDTO accountDataDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM update) ENABLED START*/
        AccountData accountData = null;
        if (accountDataDTO.getEmailIdentifier() != null) {
            accountDataDTO.setEmailIdentifier(accountDataDTO.getEmailIdentifier().toLowerCase());
        }

        // chargement du bo
        if (accountDataDTO.getId() != null) {
            accountData = accountDataRepository.getOne(accountDataDTO.getId());
        } else if (accountDataDTO.getSgin() != null) {
            AccountData whereClause = new AccountData();
            whereClause.setSgin(accountDataDTO.getSgin());
            List<AccountData> findList = accountDataRepository.findAll(Example.of(whereClause));
            if (findList.size() > 1) {
                throw new SeveralAccountDataException("several AccountData found for gin " + accountDataDTO.getSgin());
            } else {
                accountData = findList.get(0);
            }
            accountDataDTO.setId(accountData.getId());
        } else {
            throw new MissingParameterException("An ID is needed for update on AccountData " + accountDataDTO);
        }

        if (accountData.getEmailIdentifier() != null && accountDataDTO.getSgin() != null
                && !emailDS.emailExist(accountDataDTO.getSgin(), accountDataDTO.getEmailIdentifier()) && !isUniqueIdentifier(accountDataDTO)) {
            throw new IdentifierAlreadyUsedException("Identifier already used " + accountDataDTO);
        }


        // Checking the optimistic strategy
        if (!(accountData.getVersion().equals(accountDataDTO.getVersion()))) {
            throw new SimultaneousUpdateException("Simultaneous update on following accountData: " + accountData.getId());
        }

        // transformation light dto -> bo        
        AccountDataTransform.dto2Bo(accountDataDTO, accountData);
        String gin = null;
        if (accountDataDTO.getIndividudto() != null && accountDataDTO.getIndividudto().getSgin() != null) {
            gin = accountDataDTO.getIndividudto().getSgin();
        } else if (accountDataDTO.getSgin() != null) {
            gin = accountDataDTO.getSgin();
        }
        if (gin == null) {
            throw new MissingParameterException("Individual is mandatory for an account data");
        } else {
            Individu individu = individuRepository.findBySgin(gin);
            if (individu == null) {
                throw new MissingParameterException("Individual not found for an account data");
            }
        }
        AccountDataTransform.dto2BoLink(getEntityManager(), accountDataDTO, accountData);

        /*PROTECTED REGION END*/

    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updateLoginEmail(String gin, List<EmailDTO> emails, SignatureDTO signatureAPP, String requestStatus) throws JrafDaoException, JrafDomainException, AlreadyExistException, NotFoundException {
        if (emails == null || emails.size() != 1) {
            return;
        }
        EmailDTO email = emails.get(0);
        if (!StringUtils.equalsIgnoreCase(MediumStatusEnum.VALID.toString(), email.getStatutMedium())) {
            return;
        }
        if (StringUtils.isEmpty(gin)) {
            throw new InvalidParameterException("Gin must not be empty");
        }

        //REPIND-1596 - Not update the email of connection if the email in request is type P
        if (MediumCodeEnum.BUSINESS.toString().equalsIgnoreCase(email.getCodeMedium())) {
            return;
        }

        AccountData account = accountDataRepository.findBySgin(gin);

        if (account == null) {
            return;
        }
        boolean isFlyingBlue = StringUtils.isNotEmpty(account.getFbIdentifier());
        String emailString = email.getEmail();
        String newEmail = null;
        int accountFound = accountDataRepository.countWhereEmailIdentifierAndNotGin(gin, emailString.toLowerCase());

        /*
         *  If Individual is Flying blue member, clean MyAccount + save individual modification
         * - User "A" have the login email "toto@test.com"
         * - User "B" have the login email "machin@tata.com"
         * - We try to update the login email of User "B" with "toto@test.com"
         * - The login email of User "B" will be blanked because user "A" already use this login email
         */
        if (accountFound > 0 && isFlyingBlue) {
            newEmail = "";
        }
        // Else update MyAccount datas
        if (accountFound == 0) {
            newEmail = emailString;
        }
        if (newEmail == null) {
            return;
        }
        account.setEmailIdentifier(newEmail);
        account.setDateModification(new Date());
        account.setSignatureModification(signatureAPP.getSignature());
        account.setSiteModification(signatureAPP.getSite());

        if (StringUtils.isNotEmpty(requestStatus)) {
            // MAJ status
            // I = enroll offline / C++ FB creation / Migration
            // V = enrollement / create Connexion
            // U = upgrade status to flying blue
            // D = delete status ( no newletter / expired since 18 month)
            // C = close / keep newsletter
            // E = expired (18 month stand by)
            account.setStatus(requestStatus);

            // if update is an Upgrade : MyAcount to FlyingBlue
            if ("U".equals(requestStatus.trim())) {
                account.setAccountUpgradeDate(new Date());
            }
        }
        accountDataRepository.saveAndFlush(account);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public List<AccountDataDTO> findAll() throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM findAll) ENABLED START*/
        List boFounds = null;
        List<AccountDataDTO> dtoFounds = null;
        AccountDataDTO dto = null;
        AccountData accountData = null;

        // execution du find
        boFounds = accountDataRepository.findAll();
        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<AccountDataDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                accountData = (AccountData) i.next();
                dto = AccountDataTransform.bo2Dto(accountData);
                dtoFounds.add(dto);
            }
        }
        return dtoFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Integer count() throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM count) ENABLED START*/
        return (int) accountDataRepository.count();
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public AccountDataDTO get(AccountDataDTO dto) throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM get) ENABLED START*/
        AccountData accountData = null;
        AccountDataDTO accountDataDTO = null;
        // get en base
        accountData = accountDataRepository.getOne(dto.getId());


        // transformation light bo -> dto
        if (accountData != null) {
            accountDataDTO = AccountDataTransform.bo2Dto(accountData);
        }
        return accountDataDTO;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public AccountDataDTO get(Integer oid) throws JrafDomainException {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQ DS-CM getOid) ENABLED START*/
        AccountData accountData = null;
        AccountDataDTO accountDataDTO = null;
        // get en base
        accountData = accountDataRepository.getOne(oid);

        // transformation light bo -> dto
        if (accountData != null) {
            accountDataDTO = AccountDataTransform.bo2Dto(accountData);
        }
        return accountDataDTO;
        /*PROTECTED REGION END*/
    }

    public AccountDataRepository getAccountDataRepository() {
        return accountDataRepository;
    }

    public void setAccountDataRepository(AccountDataRepository accountDataRepository) {
        this.accountDataRepository = accountDataRepository;
    }

    /**
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_3UElUDRaEeCSWs-9m-UttQgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     * @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * isUniqueIdentifier
     *
     * @param accountData in AccountDataDTO
     * @return The isUniqueIdentifier as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly = true)
    public Boolean isUniqueIdentifier(AccountDataDTO accountData) throws JrafDomainException {
        /*PROTECTED REGION ID(_Ib--wE8FEeCFM4AYw8gmuw) ENABLED START*/
        if (accountData.getFbIdentifier() == null &&
                accountData.getEmailIdentifier() == null &&
                accountData.getPersonnalizedIdentifier() == null) {
            return true;
        }
        boolean result = true;
        StringBuffer whereClause = new StringBuffer("select count(*) from AccountData where sgin <> :sgin and (");
        int lg = whereClause.length();
        if (accountData.getFbIdentifier() != null) {
            if (lg < whereClause.length()) {
                whereClause.append(" or ");
            }
            whereClause.append("fbIdentifier = :fbIdent");
        }

        if (accountData.getEmailIdentifier() != null) {
            if (lg < whereClause.length()) {
                whereClause.append(" or ");
            }
            whereClause.append("emailIdentifier = :emailIdent");
        }

        if (accountData.getPersonnalizedIdentifier() != null) {
            if (lg < whereClause.length()) {
                whereClause.append(" or ");
            }
            whereClause.append("personnalizedIdentifier = :personIdent");
        }
        whereClause.append(")");
        whereClause.append(" and status <> 'D'");

        Query query = getEntityManager().createQuery(whereClause.toString());
        query.setParameter("sgin", accountData.getSgin());
        if (accountData.getFbIdentifier() != null) {
            query.setParameter("fbIdent", accountData.getFbIdentifier());
        }

        if (accountData.getEmailIdentifier() != null) {
            query.setParameter("emailIdent", accountData.getEmailIdentifier());
        }

        if (accountData.getPersonnalizedIdentifier() != null) {
            query.setParameter("personIdent", accountData.getPersonnalizedIdentifier());
        }

        Number value = (Number) query.getSingleResult();
        log.info("AccountDataDS:isUniqueIdentifier : accountData trouvés :" + value);
        if (value.intValue() > 0) {
            result = false;
        }
        return result;
        /*PROTECTED REGION END*/
    }


    /**
     * getMyAccountIdentifier
     *
     * @return The getMyAccountIdentifier as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly = true)
    public String getMyAccountIdentifier() throws JrafDomainException {
        /*PROTECTED REGION ID(_00_7cFYzEeCtP4lyoW3NpA) ENABLED START*/
        return accountDataRepository.getMyAccountIdentifier();
        /*PROTECTED REGION END*/
    }


    public IndividuRepository getIndividuRepository() {
        return individuRepository;
    }

    public void setIndividuRepository(IndividuRepository individuRepository) {
        this.individuRepository = individuRepository;
    }

    public CommunicationPreferencesRepository getCommunicationPreferencesRepository() {
        return communicationPreferencesRepository;
    }

    public void setCommunicationPreferencesRepository(
            CommunicationPreferencesRepository communicationPreferencesRepository) {
        this.communicationPreferencesRepository = communicationPreferencesRepository;
    }

    /**
     * deleteCommPreferences
     *
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void deleteCommPreferences(String Sgin) throws JrafDomainException {
        if (Sgin != null && !"".equals(Sgin)) {
            getCommunicationPreferencesRepository().deleteByGin(Sgin);
        }
    }

    /**
     * getAccountBySocialId
     *
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly = true)
    public List<AccountDataDTO> getAccountBySocialId(String social_id) throws JrafDomainException {

        List<AccountData> accs = null;
        if (social_id != null) {
            accs = accountDataRepository.findBySocialNetworkId(social_id);
        }
        if (accs != null && !accs.isEmpty()) {
            return AccountDataTransform.bo2DtoLight(accs);
        }

        return null;
    }

    public long getNbAccountsToExpire() {
        return accountDataRepository.getNbAccountsToExpire(-18, 19);
    }

    public long getNbAccountsToDelete() {
        return accountDataRepository.getNbAccountsToDelete(-19);
    }

    public long getNbAccountsToErase() {
        return accountDataRepository.getNbAccountsToErase();
    }

    public long getNbValidAccounts() {
        return accountDataRepository.getNbValidAccounts();
    }

    public List<AccountDataDTO> getAccountsToExpire(long limit) throws JrafDomainException {
        List<AccountData> accountDataList = accountDataRepository.getAccountsToExpire(-18, -19, limit);
        return AccountDataTransform.bo2DtoLight(accountDataList);
    }

    public List<AccountDataDTO> getAccountsToDelete(long limit) throws JrafDomainException {
        List<AccountData> accountDataList = accountDataRepository.getAccountsToDelete(-19, limit);
        return AccountDataTransform.bo2DtoLight(accountDataList);
    }

    public List<AccountDataDTO> getAccountsToErase(long limit) throws JrafDomainException {
        List<AccountData> accountDataList = accountDataRepository.getAccountsToErase(limit);
        return AccountDataTransform.bo2DtoLight(accountDataList);
    }

    @Transactional(readOnly = true)
    public AccountDataDTO getByGin(String gin) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        AccountData accountData = accountDataRepository.findBySgin(gin);

        if (accountData == null) {
            return null;
        }

        AccountDataDTO accountDataDTO = AccountDataTransform.bo2Dto(accountData);

        return accountDataDTO;
    }

    @Transactional(readOnly = true)
    public AccountDataDTO getOnlyAccountByGin(String gin) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        AccountData accountData = accountDataRepository.findBySgin(gin);

        if (accountData == null) {
            return null;
        }

        return AccountDataTransform.bo2DtoLight(accountData);
    }

    public String getLastValidEmailForGin(String gin) {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        String email = accountDataRepository.getLastEmailByGin(gin);

        if (email == null) {
            return null;
        }

        return email;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updateSocialNetworkId(String gin, String socialNetworkId, SignatureDTO signatureFromAPP) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("GIN is required for update social network id");
        }

        // get account data in database
        AccountData accountDataFromDB = accountDataRepository.findBySgin(gin);

        // get today's date
        Date today = new Date();

        // keep in mind old social network id
        String lastSocialNetworkId = accountDataFromDB.getSocialNetworkId();

        // update social network id
        accountDataFromDB.setSocialNetworkId(socialNetworkId);
        accountDataFromDB.setLastSocialNetworkId(lastSocialNetworkId);
        accountDataFromDB.setLastSocialNetworkLogonDate(today);
        accountDataFromDB.setDateModification(today);
        accountDataFromDB.setSiteModification(signatureFromAPP.getSite());
        accountDataFromDB.setSignatureModification(signatureFromAPP.getSite());

    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void removeSocialNetworkId(String gin, SignatureDTO signatureFromAPP) throws JrafDomainException {
        updateSocialNetworkId(gin, null, signatureFromAPP);
    }

    @Transactional(readOnly = true)
    public AccountDataDTO getByGinWithSocialNetworkData(String gin) throws JrafDomainException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        AccountData accountData = accountDataRepository.findBySgin(gin);

        if (accountData == null) {
            return null;
        }

        AccountDataDTO accountDataDTO = AccountDataTransform.bo2DtoLight(accountData);

        return accountDataDTO;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void delete(Integer id) {
        accountDataRepository.deleteById(id);
    }

    @Transactional
    public AccountDataDTO findByFbIdentifier(String fbIdentifier) throws JrafDomainException {
        AccountData accountData = accountDataRepository.findByFbIdentifier(fbIdentifier);
        if (accountData == null) {
            return null;
        }
        return AccountDataTransform.bo2Dto(accountData);
    }

    @Transactional
    public void deleteById(Integer id) {
        accountDataRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public int countByGin(String gin) throws JrafDomainException {
    	return accountDataRepository.countByGin(gin);
    }
    /*PROTECTED REGION END*/
}
