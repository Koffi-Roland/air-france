package com.airfrance.batch.subscriptiondata.config;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.config.vault.VaultConfiguration;
import com.airfrance.batch.config.vault.VaultProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.env.VaultPropertySource;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ComponentScan(basePackages = {"com.airfrance.batch.subscriptiondata"
        ,"com.airfrance.repind.service.environnement.internal"
        ,"com.airfrance.repind.service.ws.internal.helpers"})
@Configuration
@EnableConfigurationProperties
@Import({CollectSharepointSubscriptionDataProperties.class,
        OAuth2ClientProperties.class,
        VaultConfiguration.class,
        VaultProperties.class,
        ConfigBatch.class ,
        JpaRepindConfig.class,
        JpaRepindUtf8Config.class})
public class BatchCollectSharepointSubscriptionDataConfig {

    @Inject
    private VaultTemplate vaultTemplate;

    @Inject
    VaultProperties vaultProperties;
    @Inject
    private ConfigurableEnvironment environment;

    public static final String SHAREPOINT = "sharepoint";

    @Bean
    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction filter = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                authorizedClientManager);
        filter.setDefaultClientRegistrationId(SHAREPOINT);

        //size 10MB
        final int size = 10 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder().apply(filter.oauth2Configuration()).exchangeStrategies(strategies).build();
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .password()
                .refreshToken()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        authorizedClientManager.setContextAttributesMapper(contextAttributesMapper(
                environment.getProperty("cdm_user_name"), environment.getProperty("cdm_user_password")
        ));
        return authorizedClientManager;
    }

    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    InMemoryClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
        List<ClientRegistration> registrations = new ArrayList<>(
                OAuth2ClientPropertiesRegistrationAdapter.getClientRegistrations(properties).values());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    @ConditionalOnMissingBean
    OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper(final String username, final String password) {
        return authorizeRequest -> {
            final Map<String, Object> contextAttributes = new HashMap<>();
            contextAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username);
            contextAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password);
            return contextAttributes;
        };
    }

    @PostConstruct
    public void init() {
        environment.getPropertySources().addFirst(
                new VaultPropertySource(vaultTemplate, "secrets/" + vaultProperties.getVaultEnv() + "/sharepoint")
        );
    }
}
