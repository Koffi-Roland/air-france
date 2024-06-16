package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.entity.individual.AccountData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleGP;
import com.afklm.repind.common.enums.MediumCodeEnum;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.common.repository.contact.UsageMediumRepository;
import com.afklm.repind.common.repository.individual.AccountDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.common.repository.role.RoleGPRepository;
import com.afklm.repind.common.service.format.FormatService;
import com.afklm.repind.msv.automatic.merge.model.error.BusinessError;
import com.afklm.repind.msv.automatic.merge.model.individual.LinkGinUsageMediumDTO;
import com.afklm.repind.msv.automatic.merge.model.individual.ModelIndividual;
import com.afklm.repind.msv.automatic.merge.helper.MergeHelper;
import com.afklm.repind.msv.automatic.merge.wrapper.MergeCriteria;
import com.afklm.repind.msv.automatic.merge.wrapper.MergeProvideCriteria;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperAccountDataMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperAddressMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperContractMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperEmailMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperIndividualMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMergeRequestBloc;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperProvideMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperRoleGPMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperTelecomMerge;
import com.afklm.soa.stubs.w002122.v1.xsd1.ProvideFBContractMergePreferenceResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
/**
 * Service to manage the merge of a two individuals
 */
public class AutomaticMergeService {

    private static final List<String> ADDRESSES_NOT_MERGEABLE = Arrays.asList("BDC");

    private static final List<String> CONTRACT_STATUS_MERGEABLE = Arrays.asList("C", "P");

    private static final String SITE = "QVI";

    private static final String SIGNATURE = "MS FUSION";

    private static final String TYPE_EMAILS = "EMAILS";

    private static final String TYPE_TELECOMS = "TELECOMS";

    private static final String TYPE_ADDRESSES = "ADDRESSES";

    private static final int MAX_ADDRESSES_INDIVIDUAL = 5;

    private static final int MAX_GP_ROLES = 6;

    @Autowired
    private MergeHelper mergeHelper;

    @Autowired
    IndividualService individualService;

    @Autowired
    RoleContractRepository roleContractRepository;

    @Autowired
    BusinessRoleRepository businessRoleRepository;

    @Autowired
    public BeanMapper beanMapper;

    @Autowired
    public TelecomsRepository telecomsRepository;

    @Autowired
    public EmailRepository emailRepository;

    @Autowired
    public AddressService addressService;

    @Autowired
    public AccountDataRepository accountDataRepository;

    @Autowired
    public RoleGPRepository roleGPRepository;

    @Autowired
    public IndividuRepository individuRepository;

    @Autowired
    private UsageMediumRepository usageMediumRepository;

    @Autowired
    public EmailService emailService;

    @Autowired
    public FormatService formatService;

    /**
     * Retrieve informations of an pair of individuals
     *
     * @param criteria Information used to retrieve source/target individual information
     * @return an instance of WrapperMerge
     * @throws BusinessException Generic error
     */
    public WrapperMerge getMergeIndividuals(MergeProvideCriteria criteria) throws BusinessException {
        WrapperMerge wrapperMerge = new WrapperMerge();

        mergeHelper.checkInputGins(criteria.getIdentifiant1(), criteria.getIdentifiant2());
        mergeHelper.checkHaveSHContractForBothGins(criteria.getIdentifiant1(), criteria.getIdentifiant2());
        mergeHelper.checkHaveMAContractForBothGins(criteria.getIdentifiant1(), criteria.getIdentifiant2());

        String source = criteria.getIdentifiant1();
        String target = criteria.getIdentifiant2();

        /**
         * We get the gin of the individual using GIN or FB number, if gin sin't
         * find, we search as FB, if not found -> individual not found
         */
        source = individualService.getIndividualByIdentifiant(source).getGin();
        target = individualService.getIndividualByIdentifiant(target).getGin();

        if (!bothHaveNoFBContract(source, target) && !criteria.isForceSwitchIndividual()) {
            ProvideFBContractMergePreferenceResponse recommandation;
            try {
                recommandation = mergeHelper.provideFBContractMergeRecommandation(source, target);

                // we use hachiko's recommandation
                source = formatService.longIdentifierNumberToString(recommandation.getSourceGIN());
                target = formatService.longIdentifierNumberToString(recommandation.getTargetGIN());

            } catch (BusinessException e) {
                log.error("individual not found" ,e);
                wrapperMerge.isExpertRequired = true;
            }

        } else {
            if (isGPPure(target) && hasContract(source)) {
                String inversion = source;
                source = target;
                target = inversion;
            }
        }
        try {
            wrapperMerge.individualSource = getIndividual(source);
            wrapperMerge.individualTarget = getIndividual(target);

            if (isSamePerson(wrapperMerge.individualSource.getIdentification().individual,
                    wrapperMerge.individualTarget.getIdentification().individual)) {
                wrapperMerge.isSamePerson = true;
            } else {
                wrapperMerge.isSamePerson = false;
            }

            setTelecoms(wrapperMerge);
            setEmails(wrapperMerge);
            setContrats(wrapperMerge);
            setAddresses(wrapperMerge);
            setAccountData(wrapperMerge);
            setRolesGP(wrapperMerge);

        } catch (DataAccessException e) {
            log.error(BusinessError.API_CANT_ACCESS_DB.getRestError().getCode() ,e);
            throw new BusinessException(BusinessError.API_CANT_ACCESS_DB);
        }

        return wrapperMerge;
    }

    /**
     * Retrieve individual's information
     *
     * @param identifiant Individual ID
     * @return an instance of WrapperProvideMerge
     * @throws BusinessException Generic error
     */
    private WrapperProvideMerge getIndividual(String identifiant) throws BusinessException {

        ModelIndividual ind = individualService.getIndividualByIdentifiant(identifiant);

        mergeHelper.checkIfIndividualStatusIsAllowedForMerge(ind);

        WrapperProvideMerge wrapperProvideMerge = new WrapperProvideMerge();

        WrapperIndividualMerge wrapperIndividualMerge = new WrapperIndividualMerge();
        wrapperIndividualMerge.individual = ind;
        wrapperProvideMerge.setIdentification(wrapperIndividualMerge);

        return wrapperProvideMerge;
    }

    /**
     * Check if an individual has just the GP role
     *
     * @param gin Individual ID
     * @return true: has just the GP ROLE / false: has a contract (MA or FB)
     */
    public boolean isGPPure(String gin) {

        List<BusinessRole> listBR = businessRoleRepository.findBusinessRolesByGinInd(gin);
        List<RoleContract> listRC = roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(gin, CONTRACT_STATUS_MERGEABLE);

        boolean isGP = mergeHelper.checkHasGPRole(listBR);
        boolean isFB = mergeHelper.checkHasFBContract(listRC);
        boolean isMA = mergeHelper.checkHasMAContract(listRC);

        return isGP && !isFB && !isMA;
    }

    /**
     * Check if the source and the target individual have no FB contract
     *
     * @param source Individual ID
     * @param target Individual ID
     * @return true: the both individual have FB contract / false:
     */
    private boolean bothHaveNoFBContract(String source, String target) {
        List<RoleContract> listRCSource = roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(source, CONTRACT_STATUS_MERGEABLE);
        List<RoleContract> listRTarget = roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(target, CONTRACT_STATUS_MERGEABLE);

        boolean isFBSource = mergeHelper.checkHasFBContract(listRCSource);
        boolean isFBTarget = mergeHelper.checkHasFBContract(listRTarget);

        return !isFBSource && !isFBTarget;
    }

    /**
     * Check if an individual has contract
     *
     * @param gin Individual ID
     * @return true: he has contract / false: he has no contract
     */
    private boolean hasContract(String gin) {
        List<RoleContract> listRC = roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(gin, CONTRACT_STATUS_MERGEABLE);

        return !CollectionUtils.isEmpty(listRC);
    }

    /**
     * Check if two individual are the same person
     *
     * @param individualSource Individual DTO
     * @param individualTarget Individual DTO
     * @return true: the both individual are the same first name, lastname and birthdate
     */
    private boolean isSamePerson(ModelIndividual individualSource, ModelIndividual individualTarget) {
        return (individualSource.getFirstName().equalsIgnoreCase(individualTarget.getFirstName())
                && individualSource.getLastName().equalsIgnoreCase(individualTarget.getLastName())
                && individualSource.getBirthDate().equalsIgnoreCase(individualTarget.getBirthDate()));
    }

    /**
     * Retrieve telecoms for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setTelecoms(WrapperMerge wrapperMerge) {
        List<String> status = new ArrayList<>();
        status.add(MediumStatusEnum.VALID.toString());
        status.add(MediumStatusEnum.INVALID.toString());

        List<String> codesMedium = new ArrayList<>();
        codesMedium.add(MediumCodeEnum.PRO.toString());
        codesMedium.add(MediumCodeEnum.DIRECT.toString());

        List<WrapperTelecomMerge> telecomsForIndividualSource = mergeHelper.buildWrapperTelecomMerge(telecomsRepository.findTelecomsByIndividuGinAndStatutMediumInAndCodeMediumIn(wrapperMerge.individualSource.getIdentification().individual.getGin(), status, codesMedium));
        List<WrapperTelecomMerge> telecomsForIndividualTarget = mergeHelper.buildWrapperTelecomMerge(telecomsRepository.findTelecomsByIndividuGinAndStatutMediumInAndCodeMediumIn(wrapperMerge.individualTarget.getIdentification().individual.getGin(), status, codesMedium));

        mergeHelper.compareAndSelectTelecomsByDefault(telecomsForIndividualSource, telecomsForIndividualTarget);

        wrapperMerge.individualSource.setTelecoms(telecomsForIndividualSource);
        wrapperMerge.individualTarget.setTelecoms(telecomsForIndividualTarget);
    }

    /**
     * Retrieve emails for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setEmails(WrapperMerge wrapperMerge) {
        List<String> status = new ArrayList<>();
        status.add(MediumStatusEnum.VALID.toString());
        status.add(MediumStatusEnum.INVALID.toString());

        List<WrapperEmailMerge> emailsForIndividualSource = mergeHelper.buildWrapperEmailMerge(emailRepository.findEmailEntitiesByIndividuGinAndStatutMediumIn(wrapperMerge.individualSource.getIdentification().individual.getGin(), status));
        List<WrapperEmailMerge> emailsForIndividualTarget = mergeHelper.buildWrapperEmailMerge(emailRepository.findEmailEntitiesByIndividuGinAndStatutMediumIn(wrapperMerge.individualTarget.getIdentification().individual.getGin(), status));

        mergeHelper.compareAndSelectEmailsByDefault(emailsForIndividualSource, emailsForIndividualTarget);

        wrapperMerge.individualSource.setEmails(emailsForIndividualSource);
        wrapperMerge.individualTarget.setEmails(emailsForIndividualTarget);
    }

    /**
     * Retrieve contracts for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setContrats(WrapperMerge wrapperMerge) {
        List<WrapperContractMerge> contratsForIndividualSource = mergeHelper
                .buildWrapperContractMerge(roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(
                        wrapperMerge.individualSource.getIdentification().individual.getGin(), CONTRACT_STATUS_MERGEABLE));
        List<WrapperContractMerge> contratsForIndividualTarget = mergeHelper
                .buildWrapperContractMerge(roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(
                        wrapperMerge.individualTarget.getIdentification().individual.getGin(), CONTRACT_STATUS_MERGEABLE));

        wrapperMerge.individualSource.setContracts(contratsForIndividualSource);
        wrapperMerge.individualTarget.setContracts(contratsForIndividualTarget);
    }

    /**
     * Retrieve postal addresses for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setAddresses(WrapperMerge wrapperMerge) {
        List<String> status = new ArrayList<>();
        status.add(MediumStatusEnum.VALID.toString());

        List<WrapperAddressMerge> addressesForIndividualSourceMergeable = new ArrayList<>();
        List<WrapperAddressMerge> addressesForIndividualSourceNotMergeable = new ArrayList<>();
        List<WrapperAddressMerge> addressesForIndividualTargetMergeable = new ArrayList<>();
        List<WrapperAddressMerge> addressesForIndividualTargetNotMergeable = new ArrayList<>();

        mergeHelper.detectNotMergeableAddresses(mergeHelper.buildWrapperAddressMerge(addressService.findByGinAndStatus(wrapperMerge.individualSource.getIdentification().individual.getGin(), status)), addressesForIndividualSourceMergeable, addressesForIndividualSourceNotMergeable, ADDRESSES_NOT_MERGEABLE);
        mergeHelper.detectNotMergeableAddresses(mergeHelper.buildWrapperAddressMerge(addressService.findByGinAndStatus(wrapperMerge.individualTarget.getIdentification().individual.getGin(), status)), addressesForIndividualTargetMergeable, addressesForIndividualTargetNotMergeable, ADDRESSES_NOT_MERGEABLE);

        mergeHelper.compareAndSelectAddressesByDefault(addressesForIndividualSourceMergeable, addressesForIndividualTargetMergeable);

        wrapperMerge.individualSource.setAddresses(addressesForIndividualSourceMergeable);
        wrapperMerge.individualSource.setAddressesNotMergeable(addressesForIndividualSourceNotMergeable);
        wrapperMerge.individualTarget.setAddresses(addressesForIndividualTargetMergeable);
        wrapperMerge.individualTarget.setAddressesNotMergeable(addressesForIndividualTargetNotMergeable);
    }

    /**
     * Retrieve account data for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setAccountData(WrapperMerge wrapperMerge) {
        List<WrapperAccountDataMerge> accountDataForIndividualSource = mergeHelper.buildWrapperAccountDataMerge(accountDataRepository.findAccountDataByGin(wrapperMerge.individualSource.getIdentification().individual.getGin()));
        List<WrapperAccountDataMerge> accountDataForIndividualTarget = mergeHelper.buildWrapperAccountDataMerge(accountDataRepository.findAccountDataByGin(wrapperMerge.individualTarget.getIdentification().individual.getGin()));

        wrapperMerge.individualSource.setAccounts(accountDataForIndividualSource);
        wrapperMerge.individualTarget.setAccounts(accountDataForIndividualTarget);
    }

    /**
     * Retrieve GP roles for source/target individual
     *
     * @param wrapperMerge an Instance of WrapperMerge that contains information of source/target individual
     */
    private void setRolesGP(WrapperMerge wrapperMerge) {
        List<WrapperRoleGPMerge> roleGPForIndividualSource = mergeHelper.buildWrapperRoleGPMerge(roleGPRepository.findRoleGPSByGin(wrapperMerge.individualSource.getIdentification().individual.getGin()));
        List<WrapperRoleGPMerge> roleGPForIndividualTarget = mergeHelper.buildWrapperRoleGPMerge(roleGPRepository.findRoleGPSByGin(wrapperMerge.individualTarget.getIdentification().individual.getGin()));

        wrapperMerge.individualSource.setRolesGP(roleGPForIndividualSource);
        wrapperMerge.individualTarget.setRolesGP(roleGPForIndividualTarget);
    }

    /**
     * Compute the resume of an individual merge
     *
     * @param criteria Information used by the merge (source, target, list of elements with their type)
     * @return an instance of WrapperMerge
     * @throws BusinessException Generic error
     */
    @Transactional(rollbackFor = BusinessException.class, propagation = Propagation.SUPPORTS)
    public WrapperMerge individualMergeResume(MergeCriteria criteria) throws BusinessException {

        Date dateModification = new Date();
        WrapperMerge wrapperMerge = new WrapperMerge();

        String ginCible = criteria.getGinCible();
        String ginSource = criteria.getGinSource();

        List<WrapperMergeRequestBloc> blocs = criteria.getBlocs();
        boolean commit = criteria.isCommit();

        try {
            wrapperMerge.individualTarget = getIndividual(ginCible);
            wrapperMerge.individualSource = getIndividual(ginSource);

            //PROCESS DATA FOR GIN SOURCE
            Individu individuSource = individuRepository.findById(ginSource).get();
            individuSource.setStatutIndividu("T");
            individuSource.setGinFusion(ginCible);
            individuSource.setDateFusion(dateModification);
            individuSource.setDateModification(dateModification);
            individuSource.setSiteModification(SITE);
            individuSource.setSignatureModification(SIGNATURE);

            if (commit) {
                individuRepository.save(individuSource);
            }

            //PROCESS TELECOMS
            List<WrapperTelecomMerge> listWrapperTelecomMerge = individualMergeTelecoms(ginCible, blocs, dateModification, commit);
            if (!listWrapperTelecomMerge.isEmpty()) {
                wrapperMerge.individualTarget.setTelecoms(listWrapperTelecomMerge);
            }

            //PROCESS EMAILS
            List<WrapperEmailMerge> listWrapperEmailMerge = individualMergeEmails(ginCible, blocs, dateModification, commit);
            if (!listWrapperEmailMerge.isEmpty()) {
                wrapperMerge.individualTarget.setEmails(listWrapperEmailMerge);
            }

            //PROCESS ADDRESS
            List<WrapperAddressMerge> listWrapperAddressMerge = individualMergeAddress(ginCible, blocs, dateModification, commit);
            if (!listWrapperAddressMerge.isEmpty()) {
                wrapperMerge.individualTarget.setAddresses(listWrapperAddressMerge);
            }

            //PROCESS ACCOUNTDATA
            List<WrapperAccountDataMerge> listWrapperAccountDataMerge = individualMergeAccountData(ginCible);
            if (!listWrapperAccountDataMerge.isEmpty()) {
                wrapperMerge.individualTarget.setAccounts(listWrapperAccountDataMerge);
            }

            //PROCESS CONTRACTS
            List<WrapperContractMerge> listWrapperContractMerge = individualMergeContract(ginCible);
            if (!listWrapperContractMerge.isEmpty()) {
                wrapperMerge.individualTarget.setContracts(listWrapperContractMerge);
            }

            //PROCESS GP ROLES
            boolean acknowledgementGP = isAcknowledgementGP(ginSource);
            List<WrapperRoleGPMerge> listWrapperRoleGP = individualMergeRoleGP(ginSource, ginCible, dateModification, commit);
            if (!listWrapperRoleGP.isEmpty()) {
                wrapperMerge.individualTarget.setRolesGP(listWrapperRoleGP);
            }

        } catch (BusinessException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("An exception occured :" +e.getMessage(),e);
            log.error(BusinessError.API_UNKNOWN_TECHNICAL_ERROR.getRestError().getCode());
            throw new BusinessException(BusinessError.API_UNKNOWN_TECHNICAL_ERROR);
        }

        // If the isCommit is false, the resume is call, we remove the historical data
        if (!commit) {
            this.removeHistoricalStatusDatas(wrapperMerge);
        }

        return wrapperMerge;
    }

    /**
     * Remove data with status H (for Address, Telecom and Emails)
     *
     * @param datas an Instance of WrapperMerge that contains information of source/target individual
     */
    private void removeHistoricalStatusDatas(WrapperMerge datas) {
        if (datas != null && datas.individualTarget != null) {
            if (datas.individualTarget.getAddresses() != null && !datas.individualTarget.getAddresses().isEmpty()) {
                datas.individualTarget.getAddresses().removeIf(address -> MediumStatusEnum.HISTORIZED.toString().equals(address.address.getStatus()));
            }
            if (datas.individualTarget.getEmails() != null && !datas.individualTarget.getEmails().isEmpty()) {
                datas.individualTarget.getEmails().removeIf(email -> MediumStatusEnum.HISTORIZED.toString().equals(email.email.getStatus()));
            }
            if (datas.individualTarget.getTelecoms() != null && !datas.individualTarget.getTelecoms().isEmpty()) {
                datas.individualTarget.getTelecoms().removeIf(telecom -> MediumStatusEnum.HISTORIZED.toString().equals(telecom.telecom.getStatus()));
            }
        }
    }

    /**
     * Create new telecoms from telecoms of the source individual for the target individual
     *
     * @param ginCible         Individual ID
     * @param blocs            List of elements with their type that will be transferred to the target individual
     * @param dateModification Date of modification
     * @param commit           Boolean to know if source code need to commit in database
     * @return The list that contain the final telecoms linked to the target individual
     */
    private List<WrapperTelecomMerge> individualMergeTelecoms(String ginCible, List<WrapperMergeRequestBloc> blocs, Date dateModification, boolean commit) {

        List<WrapperTelecomMerge> wrapperTelecomMerge = new ArrayList<>();
        Map<String, String> identifiantMap;

        List<String> inputIdentifiants = blocs.stream().filter(b -> TYPE_TELECOMS.equalsIgnoreCase(b.getType()))
                .flatMap(l -> l.getIdentifiants().stream())
                .collect(Collectors.toList());
        if (!inputIdentifiants.isEmpty()) {
            List<Telecoms> existingTargetTelecoms = telecomsRepository.findByIndividuGinAndStatutMediumIn(ginCible, Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString()));
            List<String> existingTelecomsSain = existingTargetTelecoms.stream().map(Telecoms::getAin).collect(Collectors.toList());

            identifiantMap = existingTargetTelecoms.stream().collect(Collectors.toMap(this::retrieveTelecomUnicityKey, Telecoms::getAin));

            //Create new Telecoms
            for (String inputIdentifiant : inputIdentifiants) {
                if (!existingTelecomsSain.contains(inputIdentifiant)) {
                    Telecoms newTelecom = telecomsRepository.findById(inputIdentifiant).get();
                    newTelecom.setIndividu(individuRepository.getReferenceById(ginCible));
                    newTelecom.setDateModification(dateModification);
                    newTelecom.setSiteModification(SITE);
                    newTelecom.setSignatureModification(SIGNATURE);
                    newTelecom.setSignatureCreation(SIGNATURE);
                    newTelecom.setDateCreation(dateModification);
                    existingTargetTelecoms.add(newTelecom);

                    // Check if an email already exist with the unique key
                    String uniqueKey = retrieveTelecomUnicityKey(newTelecom);
                    //If we replace an existing telecom we have to historize it before
                    if (identifiantMap.containsKey(uniqueKey)) {
                        Telecoms oldTelecom = telecomsRepository.findById(identifiantMap.get(uniqueKey)).get();
                        oldTelecom.setStatutMedium(MediumStatusEnum.HISTORIZED.toString());
                        oldTelecom.setDateModification(dateModification);
                        oldTelecom.setSignatureModification(SIGNATURE);
                        oldTelecom.setSiteModification(SITE);
                        this.telecomsRepository.save(oldTelecom);
                    }
                }
            }

            for (Telecoms telecom : existingTargetTelecoms) {
                wrapperTelecomMerge.add(mergeHelper.buildWrapperTelecomMerge(telecom));
                if (commit) {
                    telecomsRepository.save(telecom);
                }
            }
        }

        return wrapperTelecomMerge;
    }

    private String retrieveTelecomUnicityKey(Telecoms iTelecoms) {
        return new StringBuilder().append(iTelecoms.getCodeMedium()).append(iTelecoms.getTerminal()).append(iTelecoms.getStatutMedium()).toString();
    }

    private String retrieveEmailUnicityKey(EmailEntity iEmail) {
        return new StringBuilder().append(iEmail.getCodeMedium()).append(iEmail.getStatutMedium()).toString();
    }

    /**
     * Create new emails from emails of the source individual for the target individual
     *
     * @param ginCible         Individual ID
     * @param blocs            List of elements with their type that will be transferred to the target individual
     * @param dateModification Date of modification
     * @param commit           Boolean to know if source code need to commit in database
     * @return The list that contain the final emails linked to the target individual
     */
    private List<WrapperEmailMerge> individualMergeEmails(String ginCible, List<WrapperMergeRequestBloc> blocs, Date dateModification, boolean commit) {

        List<WrapperEmailMerge> wrapperEmailMerge = new ArrayList<>();
        Map<String, String> identifiantMap;
        List<String> inputIdentifiants = blocs.stream().filter(b -> TYPE_EMAILS.equalsIgnoreCase(b.getType()))
                .flatMap(l -> l.getIdentifiants().stream())
                .collect(Collectors.toList());


        if (!inputIdentifiants.isEmpty()) {
            List<EmailEntity> existingEmails = emailRepository.findByIndividuGinAndStatutMediumIn(ginCible, Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString()));
            List<String> existingEmailsSain = existingEmails.stream().map(EmailEntity::getAin).collect(Collectors.toList());

            identifiantMap = existingEmails.stream().collect(Collectors.toMap(this::retrieveEmailUnicityKey, EmailEntity::getAin));

            //Create new Emails
            for (String intputIdentifiant : inputIdentifiants) {
                if (!existingEmailsSain.contains(intputIdentifiant)) {
                    // Retrieve source email
                    EmailEntity newEmail = emailRepository.findById(intputIdentifiant).get();
                    newEmail.setIndividu(individuRepository.getReferenceById(ginCible));
                    newEmail.setDateModification(dateModification);
                    newEmail.setSiteModification(SITE);
                    newEmail.setSignatureModification(SIGNATURE);
                    newEmail.setSignatureCreation(SIGNATURE);
                    newEmail.setDateCreation(dateModification);
                    existingEmails.add(newEmail);

                    // Check if an email already exist with the unique key
                    String uniqueKey = retrieveEmailUnicityKey(newEmail);
                    //If we replace an existing Email we have to historize it before
                    if (identifiantMap.containsKey(uniqueKey)) {
                        EmailEntity oldEmail = emailRepository.findById(identifiantMap.get(uniqueKey)).get();
                        emailService.softDeleteEmail(oldEmail, dateModification, SIGNATURE, SITE);
                        this.emailRepository.save(oldEmail);
                    }
                }
            }

            for (EmailEntity email : existingEmails) {
                wrapperEmailMerge.add(mergeHelper.buildWrapperEmailMerge(email));
                if (commit) {
                    emailRepository.save(email);
                }
            }
        }

        return wrapperEmailMerge;
    }

    /**
     * Create new telecoms from postal addresses of the source individual for the target individual
     *
     * @param ginCible         Individual ID
     * @param blocs            List of elements with their type that will be transferred to the target individual
     * @param dateModification Date of modification
     * @param commit           Boolean to know if source code need to commit in database
     * @return The list that contain the final postal addresses linked to the target individual
     */
    private List<WrapperAddressMerge> individualMergeAddress(String ginCible, List<WrapperMergeRequestBloc> blocs, Date dateModification, boolean commit) throws BusinessException {
        // Init variables
        int totalAddressCounter = 0;

        // Retrieve selected ID in blocs
        List<String> listAddressIdentifiants = mergeHelper.retrieveIdentifiers(blocs, TYPE_ADDRESSES);

        List<WrapperAddressMerge> wrapperAddressMerge = processAddressMerge(ginCible, listAddressIdentifiants, totalAddressCounter, dateModification, commit);

        if (totalAddressCounter > MAX_ADDRESSES_INDIVIDUAL) {
            log.error(BusinessError.MERGE_TOO_MANY_ADDRESSES.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_TOO_MANY_ADDRESSES);
        }

        return wrapperAddressMerge;
    }

    private List<WrapperAddressMerge> processAddressMerge(String ginCible, List<String> listAddressIdentifiants, int addressCounter, Date dateModification, boolean commit) {
        List<WrapperAddressMerge> wrapperAddressMerge = new ArrayList<>();

        if (listAddressIdentifiants.isEmpty()) {
            return wrapperAddressMerge;
        }

        // Retrieve target existings address and identify/remove BDC addresses
        List<PostalAddress> notMergeablePureAddress = new ArrayList<>();

        List<String> status = new ArrayList<>();
        status.add(MediumStatusEnum.VALID.toString());

        List<PostalAddress> existingAddress = addressService.findByGinAndStatus(ginCible, status);
        List<String> existingAddressSain = new ArrayList<>();
        Map<String, String> identifiantMap = new HashMap<>();

        //Remove Addresses Not Mergeable Pure (with only BDC...usages) for treatment
        //But we save this address as it will still present on the account, we add these addresses at the end to show them
        for (PostalAddress address : existingAddress) {
            existingAddressSain.add(address.getAin());
            populateAdrList(address, notMergeablePureAddress, identifiantMap);
        }

        //Create new Postal Address + Usage Mediums
        for (String newAddressSain : listAddressIdentifiants) {
            createNewpostalAddressAndUsage(ginCible, dateModification, newAddressSain, existingAddressSain, existingAddress, identifiantMap, commit);
        }

        //Add those addresses in order to show them
        existingAddress.addAll(notMergeablePureAddress
                .stream()
                .filter(adr -> !existingAddressSain.contains(adr.getAin()))
                .collect(Collectors.toList()));

        for (PostalAddress postalAddress : existingAddress) {
            wrapperAddressMerge.add(mergeHelper.buildWrapperAddressMerge(postalAddress));
            if ("V".equalsIgnoreCase(postalAddress.getStatutMedium())){
                addressCounter++;
            }
        }

        return wrapperAddressMerge;
    }

    private void createNewpostalAddressAndUsage(String ginCible, Date dateModification, String newAddressSain, List<String> existingAddressSain, List<PostalAddress> existingAddress, Map<String, String> identifiantMap, boolean commit) {
        if (!existingAddressSain.contains(newAddressSain)) {
            PostalAddress address = addressService.findBySain(newAddressSain);
            if (address != null) {
                // Create new address on target will all usage / change signature,date...
                address.setIndividu(individuRepository.getReferenceById(ginCible));
                address.setSignatureModification(SIGNATURE);
                address.setSignatureCreation(SIGNATURE);
                address.setDateCreation(dateModification);
                address.setSiteModification(SITE);
                address.setDateModification(dateModification);

                if (addressService.isMergeable(address)) {
                    Set<LinkGinUsageMediumDTO> usagesAlreadyExist = new HashSet<>();
                    PostalAddress tempAddress = address;
                    List<PostalAddress> sameAdresses = existingAddress.stream().filter(el -> addressService.areEquals(tempAddress, el)).collect(Collectors.toList());
                    manageUsageOnDifferentAddresses(sameAdresses, usagesAlreadyExist);

                    address = addressService.save(address, commit);

                    if (address.getUsageMedium() != null && !address.getUsageMedium().isEmpty()) {
                        PostalAddress finalAddress = address;
                        Set<LinkGinUsageMediumDTO> usagesToCopy = new HashSet<>(
                                address.getUsageMedium().stream()
                                        .filter(el -> !ADDRESSES_NOT_MERGEABLE.contains(el.getCodeApplication()))
                                        .map(el -> new LinkGinUsageMediumDTO(finalAddress.getIndividu().getGin(), el))
                                        .collect(Collectors.toList())
                        );
                        usagesToCopy.addAll(usagesAlreadyExist);
                        usagesToCopy = addressService.consistencyOfUsages(ginCible, usagesToCopy);

                        Set<UsageMedium> newUsages = new HashSet<>();

                        saveNewUsage(usagesToCopy, address.getAin(), newUsages);

                        address.setUsageMedium(newUsages);

                        address = addressService.save(address, commit);
                    }

                    historizePostalAddresses(address, existingAddress, identifiantMap, dateModification, commit);

                    existingAddress.add(address);
                }
            }
        }
    }

    private void saveNewUsage(Set<LinkGinUsageMediumDTO> usagesToCopy, String sain, Set<UsageMedium> newUsages) {
        for (LinkGinUsageMediumDTO link : usagesToCopy) {
            UsageMedium newUsage = mergeHelper.getNewUsageMedium(link.getUsage());
            newUsage.setAinAdr(sain);
            newUsages.add(usageMediumRepository.save(newUsage));
        }
    }

    private void manageUsageOnDifferentAddresses(List<PostalAddress> sameAdresses, Set<LinkGinUsageMediumDTO> usagesAlreadyExist) {
        if (!sameAdresses.isEmpty()) {
            for (PostalAddress sameAddr : sameAdresses) {
                usagesAlreadyExist.addAll(
                        sameAddr.getUsageMedium()
                                .stream()
                                .map(el -> new LinkGinUsageMediumDTO(sameAddr.getIndividu().getGin(), mergeHelper.getNewUsageMedium(el)))
                                .collect(Collectors.toList()));
            }
        }
    }

    private void populateAdrList(PostalAddress address, List<PostalAddress> notMergeablePureAddress, Map<String, String> identifiantMap) {
        if (mergeHelper.isNotMergeablePure(address, ADDRESSES_NOT_MERGEABLE)) {
            notMergeablePureAddress.add(address);
        }

        // Create map with uniqueKey => sain
        for (String uniqueKey : addressService.retrieveUnicityKey(address)) {
            identifiantMap.put(uniqueKey, address.getAin());
        }
    }

    private void historizePostalAddresses(PostalAddress address, List<PostalAddress> existingAddresses, Map<String, String> identifiantMap, Date dateModification, boolean commit) {
        // If an address with unique key exist on target, this addres will be historized
        List<String> uniqueKeys = addressService.retrieveUnicityKey(address);
        for (String uniqueKey : uniqueKeys) {
            //If we replace an existing postal address we have to historize it before
            if (identifiantMap.containsKey(uniqueKey)) {
                PostalAddress adr = addressService.findBySain(identifiantMap.get(uniqueKey));
                adr.setDateModification(dateModification);
                adr.setSignatureModification(SIGNATURE);
                adr.setSiteModification(SITE);
                // If this address contain one BDC usage, we need to keep this address with BDC usage
                if (!addressService.isBDCPostalAddress(adr)) {
                    adr.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
                }
                this.addressService.save(adr, commit);
            }
        }

        List<PostalAddress> sameAddresses = existingAddresses.stream().filter(el -> addressService.areEquals(address, el)).collect(Collectors.toList());
        for (PostalAddress sameAdr : sameAddresses) {
            PostalAddress adr = addressService.findBySain(sameAdr.getAin());
            adr.setStatutMedium(MediumStatusEnum.TEMPORARY.toString());
            adr.setDateModification(dateModification);
            adr.setSignatureModification(SIGNATURE);
            adr.setSiteModification(SITE);
            this.addressService.save(adr, commit);
        }
    }

    /**
     * Create new emails from account data of the source individual for the target individual
     *
     * @param ginTarget Individual ID
     * @return The list that contain the final account data linked to the target individual
     */
    private List<WrapperAccountDataMerge> individualMergeAccountData(String ginTarget) {

        List<WrapperAccountDataMerge> wrapperAccountDataMerge = new ArrayList<>();

        AccountData accountTarget = accountDataRepository.findAccountDataByGin(ginTarget);

        if (accountTarget != null) {
            wrapperAccountDataMerge.addAll(mergeHelper.buildWrapperAccountDataMerge(accountTarget));
        }

        return wrapperAccountDataMerge;
    }

    /**
     * Create new emails from contracts of the source individual for the target individual
     *
     * @param ginTarget Individual ID
     * @return The list that contain the final contracts linked to the target individual
     */
    private List<WrapperContractMerge> individualMergeContract(String ginTarget) {

        List<WrapperContractMerge> wrapperContractMerge = new ArrayList<>();

        List<RoleContract> rcs = roleContractRepository.findRoleContractsByIndividuGinAndEtatIn(ginTarget, CONTRACT_STATUS_MERGEABLE);

        if (rcs != null) {
            wrapperContractMerge.addAll(mergeHelper.buildWrapperContractMerge(rcs));
        }

        return wrapperContractMerge;
    }

    /**
     * Check if an individual has a GP role
     *
     * @param gin Individual ID
     * @return true: he has a GP role
     */
    public boolean isAcknowledgementGP(String gin) {
        return roleGPRepository.countRoleGPSByGin(gin) > 0;
    }

    /**
     * Merge GP role from source individual to target individual
     *
     * @param ginSource        Individual ID
     * @param ginCible         Individual ID
     * @param dateModification Date of modification
     * @param commit           Boolean to know if source code need to commit in database
     * @return The list that contain the final GP role linked to the target individual
     */
    private List<WrapperRoleGPMerge> individualMergeRoleGP(String ginSource, String ginCible, Date dateModification, boolean commit) throws BusinessException {

        List<WrapperRoleGPMerge> wrapperRoleGPMerge = new ArrayList<>();
        List<RoleGP> listAllRoleGP = new ArrayList<>();

        List<RoleGP> listRoleGPSource = roleGPRepository.findRoleGPSByGin(ginSource);
        List<RoleGP> listRoleGPCible = roleGPRepository.findRoleGPSByGin(ginCible);

        if (listRoleGPCible.size() + listRoleGPSource.size() > MAX_GP_ROLES) {
            log.error(BusinessError.MERGE_TOO_MANY_GP_ROLES.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_TOO_MANY_GP_ROLES);
        }

        for (RoleGP roleGPToMerge : listRoleGPSource) {
            BusinessRole businessRoleGP = businessRoleRepository.findById(roleGPToMerge.getCleRole()).get();
            businessRoleGP.setGinInd(ginCible);
            businessRoleGP.setDateModification(dateModification);
            businessRoleGP.setSiteModification(SITE);
            businessRoleGP.setSignatureModification(SIGNATURE);

            if (commit) {
                businessRoleRepository.saveAndFlush(businessRoleGP);
            }

            listAllRoleGP.add(roleGPToMerge);
        }


        listAllRoleGP.addAll(listRoleGPCible);

        for (RoleGP roleGP : listAllRoleGP) {
            wrapperRoleGPMerge.add(mergeHelper.buildWrapperRoleGPMerge(roleGP));
        }

        return wrapperRoleGPMerge;
    }

    /**
     * This method can commit modifications into DB if commit parameter is true.
     * If commit parameter is false (for getting the resume of merge), nothing
     * will be committed. Moreover, in the return of data, datas with historical
     * status will be remove
     * <p>
     * To avoid such problem with lazy collections, we use Propagation SUPPORTS
     * (Use or not the exising transaction started before).
     *
     * @param criteria Information used by the merge (source, target, list of elements with their type)
     * @return an instance of WrapperMerge
     * @throws BusinessException Generic error
     */
    @Transactional(rollbackFor = BusinessException.class)
    public WrapperMerge individualMerge(MergeCriteria criteria) throws BusinessException {
        return individualMergeResume(criteria);
    }
}
