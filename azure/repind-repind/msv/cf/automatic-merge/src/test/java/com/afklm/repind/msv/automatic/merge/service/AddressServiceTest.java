package com.afklm.repind.msv.automatic.merge.service;

import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.UsageMedium;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.UsageMediumRepository;
import com.afklm.repind.msv.automatic.merge.model.UsageMediumEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService instance;

    @Mock
    private UsageMediumRepository usageMediumRepository;

    @Mock
    private PostalAddressRepository postalAddressRepository;

    @Test
    void isMergeableSuccessfull() {
        PostalAddress pa = createPostalAddress(UsageMediumEnum.ISI.getName());
        assertTrue(instance.isMergeable(pa));
    }

    @Test
    void isNotMergeable() {
        PostalAddress pa = createPostalAddress("BDC");
        assertFalse(instance.isMergeable(pa));
    }

    @Test
    void findBySainSuccessfull() {
        String codeApp = UsageMediumEnum.ISI.getName();
        PostalAddress pa = createPostalAddress(codeApp);
        Mockito.when(postalAddressRepository.findPostalAddressByAin(any())).thenReturn(pa);
        when(usageMediumRepository.findUsageMediumByAinAdr(any())).thenReturn(new ArrayList<>(pa.getUsageMedium()));
        PostalAddress bySain = instance.findBySain(any());
        assertEquals(codeApp, ((UsageMedium) bySain.getUsageMedium().toArray()[0]).getCodeApplication());
    }

    @Test
    void findBySainAndStatusSuccessfull() {
        String codeApp = UsageMediumEnum.ISI.getName();
        PostalAddress pa = createPostalAddress(codeApp);
        when(postalAddressRepository.findByIndividuGinAndStatutMediumIn(any(), any())).thenReturn(List.of(pa));
        when(usageMediumRepository.findUsageMediumByAinAdr(any())).thenReturn(new ArrayList<>(pa.getUsageMedium()));
        List<PostalAddress> pas = instance.findByGinAndStatus(any(), any());
        assertEquals(1, pas.size());
        assertEquals(codeApp, ((UsageMedium) pas.get(0).getUsageMedium().toArray()[0]).getCodeApplication());
    }

    private PostalAddress createPostalAddress(String iCodeApp) {
        PostalAddress pa = new PostalAddress();
        pa.setUsageMedium(new HashSet<>(List.of(createUsageMedium(iCodeApp))));
        return pa;
    }

    private UsageMedium createUsageMedium(String iCodeApp) {
        UsageMedium um = new UsageMedium();
        um.setCodeApplication(iCodeApp);
        return um;
    }
}
