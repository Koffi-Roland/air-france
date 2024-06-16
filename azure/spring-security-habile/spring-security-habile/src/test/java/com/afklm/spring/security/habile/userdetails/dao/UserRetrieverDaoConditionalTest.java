package com.afklm.spring.security.habile.userdetails.dao;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v2.ProvideUserRightsAccessV20;
import com.afklm.spring.security.habile.EntryPointConfiguration;
import com.afklm.spring.security.habile.userdetails.HabileAuthenticationUserDetailsService;

@ExtendWith(MockitoExtension.class)
public class UserRetrieverDaoConditionalTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
        .withUserConfiguration(EntryPointConfiguration.class)
        .withInitializer(new ConfigDataApplicationContextInitializer());

    @Test
    public void testW000479V1UsedByDefault() {
        this.runner
            .withPropertyValues("v1v2")
            .run((context) -> {
                assertThat(context).hasSingleBean(UserRetrieverDaoImplPing.class);
                assertThat(context).hasSingleBean(UserRetrieverDaoImplV1.class);
                assertThat(context).doesNotHaveBean(UserRetrieverDaoImplV2.class);
            });
    }

    @Test
    public void testNoErrorWhenNoW000479ButStubsPresent() {
        this.runner
            .run((context) -> {
                assertThat(context).hasSingleBean(UserRetrieverDaoImplPing.class);
                assertThat(context).hasSingleBean(UserRetrieverDaoImplV1.class);
                assertThat(context).doesNotHaveBean(UserRetrieverDaoImplV2.class);
                assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {
                    context.getBean(HabileAuthenticationUserDetailsService.class).loadUserDetails(new PreAuthenticatedAuthenticationToken("toto", null));
                })
                    .withMessage(SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479.format());
            });
    }

    @Test
    public void testNoErrorWhenNoW000479NorStubs() {
        this.runner
            .withClassLoader(new FilteredClassLoader(ProvideUserRightsAccessV10.class, ProvideUserRightsAccessV20.class))
            .run((context) -> {
                assertThat(context).hasSingleBean(UserRetrieverDaoImplPing.class);
                assertThat(context).doesNotHaveBean(UserRetrieverDaoImplV1.class);
                assertThat(context).doesNotHaveBean(UserRetrieverDaoImplV2.class);
                assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {
                    context.getBean(HabileAuthenticationUserDetailsService.class).loadUserDetails(new PreAuthenticatedAuthenticationToken("toto", null));
                })
                    .withMessage(SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479.format());
            });
    }

    @Test
    public void testW000479V2UsedIfV1IsMissing() {
        this.runner
            .withClassLoader(new FilteredClassLoader(ProvideUserRightsAccessV10.class))
            .withPropertyValues("v2Only")
            .run((context) -> {
                assertThat(context).hasSingleBean(UserRetrieverDaoImplPing.class);
                assertThat(context).doesNotHaveBean(UserRetrieverDaoImplV1.class);
                assertThat(context).hasSingleBean(UserRetrieverDaoImplV2.class);
            });
    }

    @Configuration
    @ConditionalOnProperty("v2Only")
    static class OnlyBeanV2Configuration {

        @Bean
        public ProvideUserRightsAccessV20 beanV2() {
            ProvideUserRightsAccessV20 mock = mock(ProvideUserRightsAccessV20.class);
            return mock;
        }

    }

    @Configuration
    @ConditionalOnProperty("v1v2")
    static class BothBeanVersionsConfiguration {

        @Bean
        public ProvideUserRightsAccessV10 beanV1() {
            ProvideUserRightsAccessV10 mock = mock(ProvideUserRightsAccessV10.class);
            return mock;
        }

        @Bean
        public ProvideUserRightsAccessV20 beanV2() {
            ProvideUserRightsAccessV20 mock = mock(ProvideUserRightsAccessV20.class);
            return mock;
        }

    }
}
