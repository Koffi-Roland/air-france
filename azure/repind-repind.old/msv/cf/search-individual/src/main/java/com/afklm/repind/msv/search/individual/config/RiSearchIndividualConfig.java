package com.afklm.repind.msv.search.individual.config;

import com.afklm.repind.msv.search.individual.client.search.contract.RiSearchGinByContractClient;
import com.afklm.repind.msv.search.individual.client.search.email.RiSearchGinByEmailClient;
import com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.RiSearchGinByLastnameAndFirstnameClient;
import com.afklm.repind.msv.search.individual.client.search.phone.RiSearchGinByPhoneNumberClient;
import com.afklm.repind.msv.search.individual.client.search.socialMedia.RiSearchGinBySocialMediaClient;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.HttpMethod;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Config for RI Search Individual MS
 */
@Configuration
@PropertySource("classpath:/swagger.properties")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RiSearchIndividualConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;

    @Value("${ri-search-gin-by-email.url}")
    private String searchByIndividualEmailUrl;

    @Value("${ri-search-gin-by-phone.url}")
    private String searchByIndividualPhoneUrl;

    @Value("${ri-search-gin-by-contract.url}")
    private String searchByIndividualContractUrl;

    @Value("${ri-search-gin-by-social-media.url}")
    private String searchByIndividualSocialMediaUrl;

    @Value("${ri-search-gin-by-lastname-firstname.url}")
    private String searchByIndividualLastnameFirstnameUrl;



    /**
     * Bean for RestTemplate
     * @param builder the restTemplateBuilder
     * @return the restTemplate bean
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.of(plugins));
    }


    @Bean
    public RiSearchGinByEmailClient riSearchGinByEmail(){
        return new RiSearchGinByEmailClient(searchByIndividualEmailUrl+"/{email}", HttpMethod.GET, SearchGinsResponse.class);
    }

    @Bean
    public RiSearchGinByContractClient riSearchGinByContract(){
        return new RiSearchGinByContractClient(searchByIndividualContractUrl+"/{contract}", HttpMethod.GET, SearchGinsResponse.class);
    }

    @Bean
    public RiSearchGinByPhoneNumberClient riSearchGinByPhoneNumber(){
        return new RiSearchGinByPhoneNumberClient(searchByIndividualPhoneUrl+"/{phone}", HttpMethod.GET, SearchGinsResponse.class);
    }


    @Bean
    public RiSearchGinBySocialMediaClient riSearchGinBySocialMedia(){
        return new RiSearchGinBySocialMediaClient(searchByIndividualSocialMediaUrl+"/?externalIdentifierId={externalIdentifierId}&externalIdentifierType={externalIdentifierType}", HttpMethod.GET, SearchGinsResponse.class);
    }

    @Bean
    public RiSearchGinByLastnameAndFirstnameClient riSearchGinByLastnameAndFirstnameClient(){
        return new RiSearchGinByLastnameAndFirstnameClient(searchByIndividualLastnameFirstnameUrl+"/?lastname={lastname}&firstname={firstname}", HttpMethod.GET, SearchGinsResponse.class);
    }


}
