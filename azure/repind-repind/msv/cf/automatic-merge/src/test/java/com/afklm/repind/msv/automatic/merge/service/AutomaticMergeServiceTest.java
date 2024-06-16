package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.common.repository.contact.UsageMediumRepository;
import com.afklm.repind.common.repository.individual.AccountDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.role.BusinessRoleRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.common.repository.role.RoleGPRepository;
import com.afklm.repind.msv.automatic.merge.helper.MergeHelper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AutomaticMergeServiceTest {

    @InjectMocks
    private AutomaticMergeService instanceService;

    @Mock
    private MergeHelper mergeHelper;

    @Mock
    IndividualService individualService;

    @Mock
    RoleContractRepository roleContratsRepository;

    @Mock
    BusinessRoleRepository businessRoleRepository;

    @Mock
    public BeanMapper mapper;

    @Mock
    public TelecomsRepository telecomsRepository;

    @Mock
    public EmailRepository emailRepository;

    @Mock
    public AddressService addressService;

    @Mock
    public AccountDataRepository accountDataRepository;

    @Mock
    public RoleGPRepository roleGPRepository;

    @Mock
    public IndividuRepository individuRepository;

    @Mock
    private UsageMediumRepository usageMediumRepository;

    @Mock
    public EmailService emailService;

    @Test
    void isGPPureSuccess() {
        when(mergeHelper.checkHasGPRole(any())).thenReturn(true);
        when(mergeHelper.checkHasFBContract(any())).thenReturn(false);
        when(mergeHelper.checkHasMAContract(any())).thenReturn(false);

        Assert.assertTrue(instanceService.isGPPure(any()));
    }
}
