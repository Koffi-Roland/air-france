package com.afklm.spring.security.habile.proxy.service;

import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.fasterxml.jackson.core.JsonParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService authoritiesConfigurationService;

    @Mock
    private ApplicationArguments args;

    @Test
    public void testNoOptionProvided() throws Exception {
        Mockito.when(args.getOptionValues(ConfigurationService.CONFIG_SIMUL_OPTION)).thenReturn(null);

        authoritiesConfigurationService.afterPropertiesSet();

        assertThat(authoritiesConfigurationService.getUserInformationList()).isEmpty();
    }

    @Test
    public void testNominalConfigurationFile() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("src/test/resources/singleConfig.json");
        Mockito.when(args.getOptionValues(ConfigurationService.CONFIG_SIMUL_OPTION)).thenReturn(list);

        authoritiesConfigurationService.afterPropertiesSet();

        assertThat(authoritiesConfigurationService.getUserInformationList()).hasSize(1);
        UserInformation userInfo = authoritiesConfigurationService.getUserInformation("junit-userId");
        assertThat(userInfo).isNotNull();
        assertThat(userInfo.getUserId()).isEqualTo("junit-userId");
        assertThat(userInfo.getPassword()).isEqualTo("junit-password");
        assertThat(userInfo.getFirstName()).isEqualTo("junit-firstName");
        assertThat(userInfo.getLastName()).isEqualTo("junit-lastName");
        assertThat(userInfo.getEmail()).isEqualTo("junit-email");
        assertThat(userInfo.getProfiles()).containsExactly("junit-profile");
    }

    @Test
    public void testBadConfigurationFile() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("src/test/resources/badConfig.json");
        Mockito.when(args.getOptionValues(ConfigurationService.CONFIG_SIMUL_OPTION)).thenReturn(list);

        Assertions.assertThrows(JsonParseException.class, () -> {
            authoritiesConfigurationService.afterPropertiesSet();
        });
    }

    @Test
    public void testUnknownConfigurationFile() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("junit-unknownfile");
        Mockito.when(args.getOptionValues(ConfigurationService.CONFIG_SIMUL_OPTION)).thenReturn(list);

        Assertions.assertThrows(FileNotFoundException.class, () -> {
            authoritiesConfigurationService.afterPropertiesSet();
        });
    }
}
