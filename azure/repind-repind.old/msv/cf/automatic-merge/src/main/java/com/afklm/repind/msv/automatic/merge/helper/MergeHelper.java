package com.afklm.repind.msv.automatic.merge.helper;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.entity.individual.AccountData;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleGP;
import com.afklm.repind.common.enums.MediumCodeEnum;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.enums.RoleAndContractTypeEnum;
import com.afklm.repind.common.enums.TerminalTypeEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.automatic.merge.model.error.BusinessError;
import com.afklm.repind.msv.automatic.merge.model.individual.*;
import com.afklm.repind.msv.automatic.merge.service.BeanMapper;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperAccountDataMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperAddressMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperContractMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperEmailMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperMergeRequestBloc;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperRoleGPMerge;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperTelecomMerge;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceBusinessExceptionBusinessException;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;
import com.afklm.soa.stubs.w002122.v1.xsd0.RequestorInformation;
import com.afklm.soa.stubs.w002122.v1.xsd1.BusinessErrorSet;
import com.afklm.soa.stubs.w002122.v1.xsd1.IndividualInformation;
import com.afklm.soa.stubs.w002122.v1.xsd1.ProvideFBContractMergePreferenceRequest;
import com.afklm.soa.stubs.w002122.v1.xsd1.ProvideFBContractMergePreferenceResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Component
@Slf4j
@AllArgsConstructor
public class MergeHelper {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeHelper.class) ;


    private final ProvideFBContractMergePreferenceV1 provideFBContractMergePreferenceV1;


    private final BeanMapper beanMapper;

    private final BusinessRoleRepository businessRoleRepository;

    private final RoleContractRepository roleContratsRepository;

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final List<String> CONTRACT_STATUS_MERGEABLE = new ArrayList(Arrays.asList("C", "P"));


    /**
     * @param identifiant1
     * @param identifiant2
     * @throws BusinessException
     */
    public void checkInputGins(String identifiant1, String identifiant2) throws BusinessException {

        if (identifiant1 == null || "".equals(identifiant1)
                || identifiant2 == null || "".equals(identifiant2)) {
            LOGGER.error(BusinessError.API_CUSTOMER_EMPTY.getRestError().getCode());
            throw new BusinessException(BusinessError.API_CUSTOMER_EMPTY);
        }

        if (identifiant1.equalsIgnoreCase(identifiant2)) {
            LOGGER.error(BusinessError.MERGE_GIN_EQUALS.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_GIN_EQUALS);
        }

    }

    /**
     * @param rcs
     * @return
     */
    public boolean checkHasFBContract(List<RoleContract> rcs) {
        if (CollectionUtils.isEmpty(rcs)) {
            return false;
        } else {
            for (RoleContract role : rcs) {
                if (RoleAndContractTypeEnum.FLYING_BLUE.getType().equals(role.getTypeContrat())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param rcs
     * @return
     */

    public boolean checkHasSHContract(List<RoleContract> rcs) {
        if (CollectionUtils.isEmpty(rcs)) {
            return false;
        } else {
            for (RoleContract role : rcs) {
                if (RoleAndContractTypeEnum.SAPHIR.getType().equals(role.getTypeContrat())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Contract SAPHIR not allowed for Merge
     *
     * @param source
     * @param target
     * @throws BusinessException
     */
    public void checkHaveSHContractForBothGins(String source, String target) throws BusinessException {
        List<RoleContract> listRCSource = roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(source, CONTRACT_STATUS_MERGEABLE);
        List<RoleContract> listRTarget = roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(target, CONTRACT_STATUS_MERGEABLE);
        boolean isSHSource = checkHasSHContract(listRCSource);
        boolean isSHTarget = checkHasSHContract(listRTarget);

        if (isSHSource || isSHTarget) {
            LOGGER.error(BusinessError.MERGE_INVALID_RIGHTS.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_INVALID_RIGHTS);
        }
    }

    /**
     * MA - MA (Managed by Talend)
     *
     * @param source
     * @param target
     * @throws BusinessException
     */
    public void checkHaveMAContractForBothGins(String source, String target) throws BusinessException {
        List<BusinessRole> listBRSource = businessRoleRepository.findBusinessRolesByGinInd(source);
        List<BusinessRole> listBRTarget = businessRoleRepository.findBusinessRolesByGinInd(target);

        List<RoleContract> listRCSource = roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(source, CONTRACT_STATUS_MERGEABLE);
        List<RoleContract> listRTarget = roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(target, CONTRACT_STATUS_MERGEABLE);

        boolean isGPSource = checkHasGPRole(listBRSource);
        boolean isGPTarget = checkHasGPRole(listBRTarget);

        boolean isFBSource = checkHasFBContract(listRCSource);
        boolean isFBTarget = checkHasFBContract(listRTarget);

        boolean isMASource = checkHasMAContract(listRCSource);
        boolean isMATarget = checkHasMAContract(listRTarget);

        if ((!isGPSource && !isFBSource && isMASource) && (!isGPTarget && !isFBTarget && isMATarget)) {
            LOGGER.error(BusinessError.MERGE_INVALID_RIGHTS.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_INVALID_RIGHTS);
        }
    }

    /**
     * @param identifiant1
     * @param identifiant2
     * @return
     * @throws BusinessException
     */
    public ProvideFBContractMergePreferenceResponse provideFBContractMergeRecommandation(String identifiant1, String identifiant2) throws BusinessException {

        ProvideFBContractMergePreferenceRequest request = new ProvideFBContractMergePreferenceRequest();

        IndividualInformation individualInformation = new IndividualInformation();
        individualInformation.setFirstGIN(Long.valueOf(identifiant1));
        individualInformation.setSecondGIN(Long.valueOf(identifiant2));

        RequestorInformation requestorInformation = new RequestorInformation();
        requestorInformation.setChannel("SIC");
        requestorInformation.setApplicationCode("SIC");

        request.setIndividualData(individualInformation);
        request.setRequestor(requestorInformation);

        ProvideFBContractMergePreferenceResponse response = null;

        try {
            response = provideFBContractMergePreferenceV1.provideFBContractMergeWay(request);

        } catch (ProvideFBContractMergePreferenceBusinessExceptionBusinessException hachikoBusinessError) {

            BusinessErrorSet error = hachikoBusinessError.getFaultInfo().getErrorCode();

            switch (error) {
                case MERGE_FORBIDDEN_FOR_FB_ON_GIN_1:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_FIRST_GIN_FORBIDDEN_MERGE.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_BUSINESS_HACHIKO_FIRST_GIN_FORBIDDEN_MERGE);
                case MERGE_FORBIDDEN_FOR_FB_ON_GIN_2:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_SECOND_GIN_FORBIDDEN_MERGE.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_BUSINESS_HACHIKO_SECOND_GIN_FORBIDDEN_MERGE);
                case MERGE_FORBIDDEN_FOR_FB_ON_GIN_1_2:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_GINS_FORBIDDEN_MERGE.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_BUSINESS_HACHIKO_GINS_FORBIDDEN_MERGE);
                case TWO_GIN_WITHOUT_FB:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_GINS_WITHOUT_FB.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_BUSINESS_HACHIKO_GINS_WITHOUT_FB);
                case MISSING_PARAMETER:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_GINS_WITHOUT_FB.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_BUSINESS_HACHIKO_MISSING_PARAMETER);
                case FB_EXPERT_VALIDATION_REQUESTED:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_GINS_WITHOUT_FB.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_FB_EXPERT_VALIDATION_REQUESTED);
                case TECHNICAL_ERROR:
                    LOGGER.error(BusinessError.API_BUSINESS_HACHIKO_GINS_WITHOUT_FB.getRestError().getCode());
                    throw new BusinessException(BusinessError.API_UNKNOWN_BUSINESS_ERROR);
            }
        } catch (SystemException technicalError) {
            log.error("Hachiko merge technical error : " + technicalError.getMessage());
            throw new BusinessException(BusinessError.API_UNKNOWN_TECHNICAL_ERROR);
        }

        return response;
    }

    /**
     * @param brs
     * @return
     */
    public boolean checkHasGPRole(List<BusinessRole> brs) {

        if (CollectionUtils.isEmpty(brs)) {
            return false;
        } else {
            for (BusinessRole br : brs) {
                if (RoleAndContractTypeEnum.STAFF.getType().equals(br.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param rcs
     * @return
     */
    public boolean checkHasMAContract(List<RoleContract> rcs) {
        if (CollectionUtils.isEmpty(rcs)) {
            return false;
        } else {
            for (RoleContract role : rcs) {
                if (RoleAndContractTypeEnum.MY_ACCOUNT.getType().equals(role.getTypeContrat())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param ind
     * @throws BusinessException
     */
    public void checkIfIndividualStatusIsAllowedForMerge(ModelIndividual ind) throws BusinessException {

        if (!MediumStatusEnum.VALID.toString().equalsIgnoreCase(ind.getStatus()) &&
                !MediumStatusEnum.PENDING.toString().equalsIgnoreCase(ind.getStatus())) {
            LOGGER.error(BusinessError.MERGE_INVALID_STATUS.getRestError().getCode());
            throw new BusinessException(BusinessError.MERGE_INVALID_STATUS);
        }
    }

    /**
     * @param telecoms
     * @return
     */
    public List<WrapperTelecomMerge> buildWrapperTelecomMerge(List<Telecoms> telecoms) {

        List<WrapperTelecomMerge> wrapperTelecoms = new ArrayList<>();

        if (telecoms != null) {
            for (int i = 0; i < telecoms.size(); i++) {
                Telecoms telecom = telecoms.get(i);
                WrapperTelecomMerge wrapperTelecomMerge = new WrapperTelecomMerge();
                wrapperTelecomMerge.telecom = beanMapper.telecomsToModelTelecom(telecom);
                cleanTelecomForDisplay(telecom, wrapperTelecomMerge.telecom);
                wrapperTelecoms.add(wrapperTelecomMerge);
            }
        }

        return wrapperTelecoms;

    }

    /**
     * @param telecoms
     * @return
     */
    public WrapperTelecomMerge buildWrapperTelecomMerge(Telecoms telecoms) {

        WrapperTelecomMerge wrapperTelecomMerge = new WrapperTelecomMerge();

        wrapperTelecomMerge.telecom = beanMapper.telecomsToModelTelecom(telecoms);
        cleanTelecomForDisplay(telecoms, wrapperTelecomMerge.telecom);

        return wrapperTelecomMerge;
    }

    /**
     * @param telecom
     * @param modelTelecom
     */
    private void cleanTelecomForDisplay(Telecoms telecom, ModelTelecom modelTelecom) {

        if (StringUtils.isEmpty(modelTelecom.getPhoneNumber())) {
            if (!StringUtils.isEmpty(telecom.getNormNatPhoneNumber())) {
                modelTelecom.setPhoneNumber(telecom.getNormNatPhoneNumber());
            } else if (!StringUtils.isEmpty(telecom.getNormNatPhoneNumberClean())) {
                modelTelecom.setPhoneNumber(telecom.getNormNatPhoneNumberClean());
            } else if (!StringUtils.isEmpty(telecom.getNumero())) {
                modelTelecom.setPhoneNumber(telecom.getNumero());
            }

        }
    }

    /**
     * @param telecomsForIndividualSource
     * @param telecomsForIndividualTarget
     */
    public void compareAndSelectTelecomsByDefault(List<WrapperTelecomMerge> telecomsForIndividualSource, List<WrapperTelecomMerge> telecomsForIndividualTarget) {

        selectTelecom(MediumCodeEnum.DIRECT.getCode(), TerminalTypeEnum.MOBILE.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);
        selectTelecom(MediumCodeEnum.PRO.getCode(), TerminalTypeEnum.MOBILE.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);
        selectTelecom(MediumCodeEnum.DIRECT.getCode(), TerminalTypeEnum.FIX.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);
        selectTelecom(MediumCodeEnum.PRO.getCode(), TerminalTypeEnum.FIX.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);
        selectTelecom(MediumCodeEnum.DIRECT.getCode(), TerminalTypeEnum.FAX.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);
        selectTelecom(MediumCodeEnum.PRO.getCode(), TerminalTypeEnum.FAX.toString(), telecomsForIndividualSource, telecomsForIndividualTarget);

    }

    /**
     * @param type
     * @param terminal
     * @param telecomsForIndividualSource
     * @param telecomsForIndividualTarget
     */
    private void selectTelecom(String type, String terminal, List<WrapperTelecomMerge> telecomsForIndividualSource, List<WrapperTelecomMerge> telecomsForIndividualTarget) {

        WrapperTelecomMerge telecomToSelect = findTelecomByTypeAndTerminal(type, terminal, telecomsForIndividualTarget);
        if (telecomToSelect == null) {
            telecomToSelect = findTelecomByTypeAndTerminal(type, terminal, telecomsForIndividualSource);
        } else if (MediumStatusEnum.INVALID.toString().equals(telecomToSelect.telecom.getStatus())) {
            WrapperTelecomMerge telecomToCompare = findTelecomByTypeAndTerminal(type, terminal, telecomsForIndividualSource);
            if (telecomToCompare != null && MediumStatusEnum.VALID.toString().equals(telecomToCompare.telecom.getStatus())) {
                telecomToSelect = telecomToCompare;
            }
        }

        if (telecomToSelect != null) {
            telecomToSelect.selected = true;
        }
    }

    /**
     * @param type
     * @param terminal
     * @param telecoms
     * @return
     */
    private WrapperTelecomMerge findTelecomByTypeAndTerminal(String type, String terminal, List<WrapperTelecomMerge> telecoms) {

        for (Iterator<WrapperTelecomMerge> iterator = telecoms.iterator(); iterator.hasNext(); ) {

            WrapperTelecomMerge wrapperTelecomMerge = iterator.next();
            ModelTelecom telecom = wrapperTelecomMerge.telecom;

            if (type.equals(telecom.getType()) && terminal.equals(telecom.getTerminal())) {
                return wrapperTelecomMerge;
            }
        }

        return null;

    }

    public List<WrapperEmailMerge> buildWrapperEmailMerge(List<EmailEntity> emails) {

        List<WrapperEmailMerge> wrapperEmails = new ArrayList<>();

        if (emails != null) {
            for (int i = 0; i < emails.size(); i++) {
                EmailEntity email = emails.get(i);
                WrapperEmailMerge wrapperEmailMerge = new WrapperEmailMerge();
                wrapperEmailMerge.email = beanMapper.emailToModelEmail(email);
                wrapperEmails.add(wrapperEmailMerge);
            }
        }

        return wrapperEmails;

    }

    public void compareAndSelectEmailsByDefault(List<WrapperEmailMerge> emailsForIndividualSource, List<WrapperEmailMerge> emailsForIndividualTarget) {

        selectEmail(MediumCodeEnum.DIRECT.getCode(), emailsForIndividualSource, emailsForIndividualTarget);
        selectEmail(MediumCodeEnum.PRO.getCode(), emailsForIndividualSource, emailsForIndividualTarget);
        selectEmail(MediumCodeEnum.LOCALIZATION.getCode(), emailsForIndividualSource, emailsForIndividualTarget);

    }

    private void selectEmail(String type, List<WrapperEmailMerge> emailsForIndividualSource, List<WrapperEmailMerge> emailsForIndividualTarget) {

        WrapperEmailMerge emailToSelect = findEmailByType(type, emailsForIndividualTarget);
        if (emailToSelect == null) {
            emailToSelect = findEmailByType(type, emailsForIndividualSource);
        } else if (MediumStatusEnum.INVALID.toString().equals(emailToSelect.email.getStatus())) {
            WrapperEmailMerge emailToCompare = findEmailByType(type, emailsForIndividualSource);
            if (emailToCompare != null && MediumStatusEnum.VALID.toString().equals(emailToCompare.email.getStatus())) {
                emailToSelect = emailToCompare;
            }
        }

        if (emailToSelect != null) {
            emailToSelect.selected = true;
        }
    }

    private WrapperEmailMerge findEmailByType(String type, List<WrapperEmailMerge> emails) {

        for (Iterator<WrapperEmailMerge> iterator = emails.iterator(); iterator.hasNext(); ) {

            WrapperEmailMerge wrapperEmailMerge = iterator.next();
            ModelEmail email = wrapperEmailMerge.email;

            if (type.equals(email.getType())) {
                return wrapperEmailMerge;
            }
        }

        return null;

    }

    public List<WrapperContractMerge> buildWrapperContractMerge(List<RoleContract> rcs) {

        List<WrapperContractMerge> wrapperContracts = new ArrayList<>();
        if (rcs != null) {
            for (int i = 0; i < rcs.size(); i++) {
                RoleContract rc = rcs.get(i);
                WrapperContractMerge wrapperContractMerge = new WrapperContractMerge();
                wrapperContractMerge.contract = beanMapper.roleContratsToModelContract(rc);
                wrapperContracts.add(wrapperContractMerge);
            }
        }

        return wrapperContracts;

    }

    public void detectNotMergeableAddresses(List<WrapperAddressMerge> allAddresses, List<WrapperAddressMerge> addressesMergeable, List<WrapperAddressMerge> addressesNotMergeable, List<String> notMergeable) {
        for (WrapperAddressMerge addresse : allAddresses) {
            if (!isMergeable(addresse, notMergeable)) {
                WrapperAddressMerge addressMergeable = new WrapperAddressMerge();
                addressMergeable.address = addresse.address;

                cleanMergeableUsages(addresse, notMergeable);
                addressesNotMergeable.add(addresse);

                //If Address has no usages like ISI, WEB...we don't need to display it in the block of mergeable addresses
                cleanNotMergeableUsages(addressMergeable, notMergeable);
                if (! addressMergeable.address.getUsages().isEmpty()) {
                    addressesMergeable.add(addressMergeable);
                }
            } else {
                addressesMergeable.add(addresse);
            }
        }
    }

    public boolean isMergeable(WrapperAddressMerge addresse, List<String> notMergeable) {
        for (ModelUsageMedium usage : addresse.address.getUsages()) {
            if (usage.getApplicationCode() != null
                    && notMergeable.contains(usage.getApplicationCode().toUpperCase())) {
                return false;
            }
        }

        return true;
    }

    private void cleanMergeableUsages(WrapperAddressMerge addresse, List<String> notMergeable) {
        for (Iterator<ModelUsageMedium> iterator = addresse.address.getUsages().iterator(); iterator.hasNext(); ) {

            ModelUsageMedium usage = iterator.next();
            if (!notMergeable.contains(usage.getApplicationCode().toUpperCase())) {
                iterator.remove();
            }
        }
    }

    private void cleanNotMergeableUsages(WrapperAddressMerge addresse, List<String> notMergeable) {
        for (Iterator<ModelUsageMedium> iterator = addresse.address.getUsages().iterator(); iterator.hasNext(); ) {

            ModelUsageMedium usage = iterator.next();
            if (notMergeable.contains(usage.getApplicationCode().toUpperCase())) {
                iterator.remove();
            }
        }
    }

    public List<WrapperAddressMerge> buildWrapperAddressMerge(List<PostalAddress> addresses) {

        List<WrapperAddressMerge> wrapperAddress = new ArrayList<>();

        if (addresses != null) {
            for (int i = 0; i < addresses.size(); i++) {
                PostalAddress address = addresses.get(i);
                WrapperAddressMerge wrapperAddressMerge = new WrapperAddressMerge();
                wrapperAddressMerge.address = beanMapper.postalAddressToModelAddress(address);
                wrapperAddress.add(wrapperAddressMerge);
            }
        }
        return wrapperAddress;

    }

    public void compareAndSelectAddressesByDefault(List<WrapperAddressMerge> addressesForIndividualSource, List<WrapperAddressMerge> addressesForIndividualTarget) {

        selectAddress(MediumCodeEnum.DIRECT.getCode(), addressesForIndividualSource, addressesForIndividualTarget);
        selectAddress(MediumCodeEnum.PRO.getCode(), addressesForIndividualSource, addressesForIndividualTarget);

    }

    private void selectedToTrue(List<WrapperAddressMerge> addressList){
        for(WrapperAddressMerge address : addressList){
            address.selected = true;
        }
    }

    private void selectAddress(String type, List<WrapperAddressMerge> addressesForIndividualSource, List<WrapperAddressMerge> addressesForIndividualTarget) {

        List<WrapperAddressMerge> addressesTarget = findAddressesByType(type, addressesForIndividualTarget);
        List<WrapperAddressMerge> addressesSource = findAddressesByType(type, addressesForIndividualSource);

        if (addressesSource.isEmpty()) {
            selectedToTrue(addressesTarget);
        }

        if (addressesTarget.isEmpty()) {
            selectedToTrue(addressesSource);
        }

        if (addressesSource.size() > addressesTarget.size()) {
            for (int i = 0; i < addressesSource.size(); i++) {
                if (i < addressesTarget.size()) {
                    addressesTarget.get(i).selected = true;
                } else {
                    addressesSource.get(i).selected = true;
                }
            }
        } else {
            selectedToTrue(addressesTarget);
        }
    }

    private List<WrapperAddressMerge> findAddressesByType(String type, List<WrapperAddressMerge> addresses) {

        List<WrapperAddressMerge> listResult = new ArrayList<>();

        for (Iterator<WrapperAddressMerge> iterator = addresses.iterator(); iterator.hasNext(); ) {
            WrapperAddressMerge wrapperAddressMerge = iterator.next();
            ModelAddress address = wrapperAddressMerge.address;

            if (type.equals(address.getType())) {
                listResult.add(wrapperAddressMerge);
            }
        }

        return listResult;
    }

    public List<WrapperAccountDataMerge> buildWrapperAccountDataMerge(AccountData accountData) {

        List<WrapperAccountDataMerge> wrapperAccountData = new ArrayList<>();

        if (accountData != null) {
            WrapperAccountDataMerge wrapperAccountDataMerge = new WrapperAccountDataMerge();

            wrapperAccountDataMerge.account = beanMapper.accountDataToModelAccountData(accountData);

            wrapperAccountData.add(wrapperAccountDataMerge);
        }

        return wrapperAccountData;
    }

    public List<WrapperRoleGPMerge> buildWrapperRoleGPMerge(List<RoleGP> rolesGP) {

        List<WrapperRoleGPMerge> wrapperRoleGP = new ArrayList<>();

        for (RoleGP roleGP : rolesGP) {
            WrapperRoleGPMerge wrapperRoleGPMerge = new WrapperRoleGPMerge();
            wrapperRoleGPMerge.roleGP = beanMapper.toModelRoleGP(roleGP);
            wrapperRoleGP.add(wrapperRoleGPMerge);
        }

        return wrapperRoleGP;
    }

    public WrapperEmailMerge buildWrapperEmailMerge(EmailEntity emails) {

        WrapperEmailMerge wrapperEmailMerge = new WrapperEmailMerge();

        wrapperEmailMerge.email = beanMapper.emailToModelEmail(emails);

        return wrapperEmailMerge;

    }

    /**
     * Check if an address has only usages that cannot be merged, so only usages like BDC, GP, AMF (ADDRESSES_NOT_MERGEABLE)
     *
     * @param addresse
     * @param notMergeable
     * @return
     */
    public boolean isNotMergeablePure(PostalAddress addresse, List<String> notMergeable) {
        boolean bUsage = false;
        boolean bUsageNotMergeable = false;
        boolean result;

        for (UsageMedium usage : addresse.getUsageMedium()) {
            if (usage.getCodeApplication() != null
                    && notMergeable.contains(usage.getCodeApplication().toUpperCase())) {
                bUsageNotMergeable = true;
            }else{
                bUsage = true;
            }
        }

        if (!bUsage && bUsageNotMergeable) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Create a new PostalAddress with migrated values
     *
     * @param postalAddress
     * @return
     */
    public PostalAddress getNewPostalAddress(PostalAddress postalAddress) {
        if (postalAddress != null) {
            return beanMapper.toPostalAddress(postalAddress);
        }
        return null;
    }

    /**
     * Create a new UsageMedium with migrated values
     *
     * @param usageMedium
     * @return
     */
    public UsageMedium getNewUsageMedium(UsageMedium usageMedium) {
        if (usageMedium != null) {
            return beanMapper.toUsageMedium(usageMedium);
        }
        return null;
    }

    public WrapperAddressMerge buildWrapperAddressMerge(PostalAddress addresses) {

        WrapperAddressMerge wrapperAddressMerge = new WrapperAddressMerge();

        wrapperAddressMerge.address = beanMapper.postalAddressToModelAddress(addresses);

        return wrapperAddressMerge;
    }

    public WrapperRoleGPMerge buildWrapperRoleGPMerge(RoleGP roleGP) {

        WrapperRoleGPMerge wrapperRoleGPMerge = new WrapperRoleGPMerge();

        wrapperRoleGPMerge.roleGP = beanMapper.toModelRoleGP(roleGP);

        return wrapperRoleGPMerge;
    }

    /**
     * Retrieve identifier with type in an list of identifiers
     * @param blocs WrapperMergeRequestBloc instance
     * @param type Type of identifier
     * @return A list of identifiers
     */
    public List<String> retrieveIdentifiers(List<WrapperMergeRequestBloc> blocs, String type){
        List<String> listAddressIdentifiants = new ArrayList<>();
        for (WrapperMergeRequestBloc dataBloc : blocs) {
            if (dataBloc.getType().equalsIgnoreCase(type)) {
                listAddressIdentifiants = dataBloc.getIdentifiants();
            }
        }
        return listAddressIdentifiants;
    }
}
