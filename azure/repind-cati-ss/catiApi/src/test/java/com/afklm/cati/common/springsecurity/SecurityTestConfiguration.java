package com.afklm.cati.common.springsecurity;

import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@Configuration
@EnableWebSecurity()
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityTestConfiguration {

    private final String profile = "{\n" +
            "  \"accessKeyConfiguration\": [\n" +
            "    {\n" +
            "      \"profile\": \"P_CATI_ADMIN\",\n" +
            "      \"accessKeyLst\": [\n" +
            "        {\n" +
            "          \"accessKey\": \"ROLE_ADMIN\",\n" +
            "          \"idProfil\": \"P_CATI_ADMIN\",\n" +
            "          \"type\": \"NONE/READ/WRITE\",\n" +
            "          \"value\": \"READWRITE\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"accessKey\": \"ROLE_ADMIN_COMMPREF\",\n" +
            "          \"idProfil\": \"P_CATI_ADMIN_COMMPREF\",\n" +
            "          \"type\": \"NONE/READ/WRITE\",\n" +
            "          \"value\": \"READWRITE\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"profile\": \"P_CATI_ADMIN_COMMPREF\",\n" +
            "      \"accessKeyLst\": [\n" +
            "        {\n" +
            "          \"accessKey\": \"ROLE_ADMIN_COMMPREF\",\n" +
            "          \"idProfil\": \"P_CATI_ADMIN_COMMPREF\",\n" +
            "          \"type\": \"NONE/READ/WRITE\",\n" +
            "          \"value\": \"READWRITE\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();
        return http.build();


    }

    @Bean
    public UserProfilesAccessKey userProfilesAccessKeyBean() {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.readValue(
                    profile,
                    new TypeReference<UserProfilesAccessKey>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new UserProfilesAccessKey();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()

                .withUser("user")
                .password("password")
                .roles("AK_USER")
                .and()
                .withUser("admin")
                .password("password")
                .roles("ADMIN", "ADMIN_COMMPREF");
    }

}
