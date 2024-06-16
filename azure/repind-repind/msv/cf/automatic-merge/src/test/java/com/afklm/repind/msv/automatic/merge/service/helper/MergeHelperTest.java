package com.afklm.repind.msv.automatic.merge.service.helper;

import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.automatic.merge.helper.MergeHelper;
import com.afklm.repind.msv.automatic.merge.model.error.BusinessErorMessageList;
import com.afklm.repind.msv.automatic.merge.model.individual.ModelAddress;
import com.afklm.repind.msv.automatic.merge.model.individual.ModelUsageMedium;
import com.afklm.repind.msv.automatic.merge.service.BeanMapper;
import com.afklm.repind.msv.automatic.merge.wrapper.WrapperAddressMerge;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceBusinessExceptionBusinessException;
import com.afklm.soa.stubs.w002122.v1.ProvideFBContractMergePreferenceV1;
import com.afklm.soa.stubs.w002122.v1.xsd1.BusinessErrorSet;
import com.afklm.soa.stubs.w002122.v1.xsd1.ProvideFBContractMergePreferenceBusinessFault;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class MergeHelperTest {

    @InjectMocks
    public MergeHelper mergeHelper;

    @Mock
    public ProvideFBContractMergePreferenceV1 hachikoService;

    @Mock
    public BeanMapper mapper;

    @Mock
    private BusinessRoleRepository businessRoleRepository;

    @Mock
    private RoleContractRepository roleContratsRepository;

    @Test
    void checkInputGins() {
        Throwable exceptionEmptyId1 = assertThrows(BusinessException.class, () -> mergeHelper.checkInputGins("", "test"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_003, exceptionEmptyId1.getMessage());

        Throwable exceptionEmptyId2 = assertThrows(BusinessException.class, () -> mergeHelper.checkInputGins("test", ""));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_003, exceptionEmptyId2.getMessage());

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.checkInputGins("test", "test"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_005, exceptionGinEquals.getMessage());
    }

    @Test
    void checkHasFBContract() {
        // One have type FP
        RoleContract roleContrats = new RoleContract();
        roleContrats.setTypeContrat("FP");
        List<RoleContract> roles = new ArrayList<>();
        roles.add(roleContrats);
        assertTrue(mergeHelper.checkHasFBContract(roles));

        // One have type MA
        RoleContract roleContratsMA = new RoleContract();
        roleContratsMA.setTypeContrat("MA");
        List<RoleContract> rolesNoFP = new ArrayList<>();
        rolesNoFP.add(roleContratsMA);
        assertFalse(mergeHelper.checkHasFBContract(rolesNoFP));

        // Empty
        List<RoleContract> empty = new ArrayList<>();
        assertFalse(mergeHelper.checkHasFBContract(empty));
    }

    @Test
    void checkHasSHContract() {
        // One have type FP
        RoleContract roleContrats = new RoleContract();
        roleContrats.setTypeContrat("S");
        List<RoleContract> roles = new ArrayList<>();
        roles.add(roleContrats);
        assertTrue(mergeHelper.checkHasSHContract(roles));

        // One have type MA
        RoleContract roleContratsMA = new RoleContract();
        roleContratsMA.setTypeContrat("MA");
        List<RoleContract> rolesNoFP = new ArrayList<>();
        rolesNoFP.add(roleContratsMA);
        assertFalse(mergeHelper.checkHasSHContract(rolesNoFP));

        // Empty
        List<RoleContract> empty = new ArrayList<>();
        assertFalse(mergeHelper.checkHasSHContract(empty));
    }

    @Test
    void errorCheckHaveSHContractForBothGins_SourceHaveContract() {
        RoleContract roleContratsSrc = new RoleContract();
        roleContratsSrc.setTypeContrat("S");
        RoleContract roleContratsDest = new RoleContract();

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(roleContratsSrc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(roleContratsDest));

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.checkHaveSHContractForBothGins("src", "dest"));
        assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_015, exceptionGinEquals.getMessage());
    }

    @Test
    void errorCheckHaveSHContractForBothGins_DestaveContract() {
        RoleContract roleContratsSrc = new RoleContract();
        RoleContract roleContratsDest = new RoleContract();
        roleContratsDest.setTypeContrat("S");

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(roleContratsSrc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(roleContratsDest));

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.checkHaveSHContractForBothGins("src", "dest"));
        assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_015, exceptionGinEquals.getMessage());
    }

    @Test
    void checkHaveSHContractForBothGins_BothNoShContract() throws BusinessException {
        RoleContract rc = new RoleContract();

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(rc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(rc));

        mergeHelper.checkHaveSHContractForBothGins("src", "dest");
    }

    @Test
    void checkHaveMAContractForBothGins() throws BusinessException {
        RoleContract roleContratsSrc = new RoleContract();
        RoleContract roleContratsDest = new RoleContract();

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(roleContratsSrc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(roleContratsDest));

        mergeHelper.checkHaveMAContractForBothGins("src", "dest");
    }

    @Test
    void checkHaveMAContractForBothGins_sourceHaveMAContract() throws BusinessException {
        RoleContract roleContratsSrc = new RoleContract();
        roleContratsSrc.setTypeContrat("MA");
        RoleContract roleContratsDest = new RoleContract();

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(roleContratsSrc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(roleContratsDest));

        mergeHelper.checkHaveMAContractForBothGins("src", "dest");
    }


    @Test
    void errorCheckHaveMAContractForBothGins_srcDestHaveMAContracts() {
        RoleContract roleContratsSrc = new RoleContract();
        roleContratsSrc.setTypeContrat("MA");
        RoleContract roleContratsDest = new RoleContract();
        roleContratsDest.setTypeContrat("MA");

        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("src"), any())).thenReturn(Arrays.asList(roleContratsSrc));
        when(roleContratsRepository.findRoleContractsByIndividuGinAndEtatIn(eq("dest"), any())).thenReturn(Arrays.asList(roleContratsDest));

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.checkHaveMAContractForBothGins("src", "dest"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_015, exceptionGinEquals.getMessage());
    }

    @Test
    void errorProvideFBContractMergeRecommandation_forbiddenForFbGin1() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.MERGE_FORBIDDEN_FOR_FB_ON_GIN_1;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_018, exceptionGinEquals.getMessage());
    }

    @Test
    void errorProvideFBContractMergeRecommandation_forbiddenForFbGin2() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.MERGE_FORBIDDEN_FOR_FB_ON_GIN_2;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_019, exceptionGinEquals.getMessage());
    }

    @Test
    void errorProvideFBContractMergeRecommandation_forbiddenForFbGin1And2() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.MERGE_FORBIDDEN_FOR_FB_ON_GIN_1_2;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_020, exceptionGinEquals.getMessage());
    }


    @Test
    void errorProvideFBContractMergeRecommandation_ginWithoutFb() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.TWO_GIN_WITHOUT_FB;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_021, exceptionGinEquals.getMessage());
    }


    @Test
    void errorProvideFBContractMergeRecommandation_missingParams() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.MISSING_PARAMETER;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_022, exceptionGinEquals.getMessage());
    }


    @Test
    void errorProvideFBContractMergeRecommandation_expertValidationRequested() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.FB_EXPERT_VALIDATION_REQUESTED;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_017, exceptionGinEquals.getMessage());
    }


    @Test
    void errorProvideFBContractMergeRecommandation_technicalError() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        BusinessErrorSet errCode = BusinessErrorSet.TECHNICAL_ERROR;
        ProvideFBContractMergePreferenceBusinessFault fault = new ProvideFBContractMergePreferenceBusinessFault();
        fault.setErrorCode(errCode);
        ProvideFBContractMergePreferenceBusinessExceptionBusinessException ex = new ProvideFBContractMergePreferenceBusinessExceptionBusinessException(errCode.toString(), fault);
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_412_010, exceptionGinEquals.getMessage());
    }

    @Test
    void errorProvideFBContractMergeRecommandation_systemException() throws ProvideFBContractMergePreferenceBusinessExceptionBusinessException, SystemException {
        SystemException ex = new SystemException("test", new SystemFault());
        when(hachikoService.provideFBContractMergeWay(any())).thenThrow(ex);

        Throwable exceptionGinEquals = assertThrows(BusinessException.class, () -> mergeHelper.provideFBContractMergeRecommandation("123", "124"));
        Assertions.assertEquals(BusinessErorMessageList.ERROR_MESSAGE_500_003, exceptionGinEquals.getMessage());
    }

    @Test
    void checkHasGPRole() {
        // One have type GP
        BusinessRole br = new BusinessRole();
        br.setType("G");
        List<BusinessRole> roles = new ArrayList<>();
        roles.add(br);
        assertTrue(mergeHelper.checkHasGPRole(roles));

        // One have type MA
        BusinessRole brMA = new BusinessRole();
        br.setType("MA");
        List<BusinessRole> rolesNoG = new ArrayList<>();
        rolesNoG.add(brMA);
        assertFalse(mergeHelper.checkHasGPRole(rolesNoG));

        // Empty
        List<BusinessRole> empty = new ArrayList<>();
        assertFalse(mergeHelper.checkHasGPRole(empty));
    }

    @Test
    void detectNotMergeableAddresses(){
        WrapperAddressMerge adrNotMergeable = new WrapperAddressMerge();
        ModelUsageMedium modelUsageMedium = new ModelUsageMedium();
        modelUsageMedium.setApplicationCode("BDC");
        Set<ModelUsageMedium> usages = new HashSet<>();
        usages.add(modelUsageMedium);
        ModelAddress model = new ModelAddress();
        model.setUsages(usages);
        adrNotMergeable.address = model;

        List<WrapperAddressMerge> allAddresses = new ArrayList<>();
        allAddresses.add(adrNotMergeable);
        List<WrapperAddressMerge> addressesMergeable = new ArrayList<>();
        List<WrapperAddressMerge> addressesNotMergeable = new ArrayList<>();
        List<String> notMergeable = new ArrayList<>();
        notMergeable.add("BDC");

        mergeHelper.detectNotMergeableAddresses(allAddresses, addressesMergeable, addressesNotMergeable, notMergeable);
        assertEquals(1, allAddresses.size());
        assertEquals(1, addressesNotMergeable.size());
        assertEquals(0, addressesMergeable.size());
    }

    @Test
    void detectNotMergeableAddressesMergeableAdr(){
        WrapperAddressMerge adrNotMergeable = new WrapperAddressMerge();
        ModelUsageMedium modelUsageMedium = new ModelUsageMedium();
        modelUsageMedium.setApplicationCode("ISI");
        Set<ModelUsageMedium> usages = new HashSet<>();
        usages.add(modelUsageMedium);
        ModelAddress model = new ModelAddress();
        model.setUsages(usages);
        adrNotMergeable.address = model;

        List<WrapperAddressMerge> allAddresses = new ArrayList<>();
        allAddresses.add(adrNotMergeable);
        List<WrapperAddressMerge> addressesMergeable = new ArrayList<>();
        List<WrapperAddressMerge> addressesNotMergeable = new ArrayList<>();
        List<String> notMergeable = new ArrayList<>();
        notMergeable.add("BDC");

        mergeHelper.detectNotMergeableAddresses(allAddresses, addressesMergeable, addressesNotMergeable, notMergeable);
        assertEquals(1, allAddresses.size());
        assertEquals(0, addressesNotMergeable.size());
        assertEquals(1, addressesMergeable.size());
    }
}