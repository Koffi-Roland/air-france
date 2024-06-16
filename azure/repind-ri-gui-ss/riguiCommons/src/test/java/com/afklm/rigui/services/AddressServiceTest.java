package com.afklm.rigui.services;

import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.adresse.Usage_mediumRepository;
import com.afklm.rigui.spring.TestConfiguration;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private PostalAddressRepository addressRepository;

    @Mock
    private Usage_mediumRepository usageMediumRepository;

    @Mock
    public DozerBeanMapper dozerBeanMapper;

    @Test
    public void checkInit() {
        assertNotNull(addressService);
    }
}
