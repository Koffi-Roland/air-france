package com.afklm.spring.security.habile.oidc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInfoTest {
    
    @Test
    @DisplayName("test the unmarshalling with a list of profiles")
    void testTheUnmarshallingWithASListOfProfile() throws JsonProcessingException {
        String userInfoAsJson = "{\n"
                + "  \"sub\" : \"mXX\",\n"
                + "  \"logoutUrl\" : null,\n"
                + "  \"profile\" : [\"P1\",\"P2\"],\n"
                + "  \"name\" : \"john\",\n"
                + "  \"given_name\" : \"john doe\",\n"
                + "  \"family_name\" : \"doe\",\n"
                + "  \"email\" : null\n"
                + "}";
        
        TypeReference<UserInfo> typeRef = new TypeReference<UserInfo>() {};
        ObjectMapper mapper = new ObjectMapper();
        UserInfo ui = mapper.readValue(userInfoAsJson, typeRef);
        assertThat(ui.getProfile())
            .hasSize(2)
            .containsExactly("P1", "P2");
    }
    
    @ParameterizedTest
    @DisplayName("test the unmarshalling with a single profile")
    @MethodSource("userAsJsonWithOneProfile")
    void testParameterized(String json) throws JsonProcessingException {
        TypeReference<UserInfo> typeRef = new TypeReference<UserInfo>() {};
        ObjectMapper mapper = new ObjectMapper();
        UserInfo ui = mapper.readValue(json, typeRef);
        assertThat(ui.getProfile())
            .hasSize(1)
            .containsExactly("P1");
    }
    
    static Stream<String> userAsJsonWithOneProfile() {
        String json1 = "{\n"
                + "  \"sub\" : \"mXX\",\n"
                + "  \"logoutUrl\" : null,\n"
                + "  \"profile\" : [\"P1\"],\n"
                + "  \"name\" : \"john\",\n"
                + "  \"given_name\" : \"john doe\",\n"
                + "  \"family_name\" : \"doe\",\n"
                + "  \"email\" : null\n"
                + "}";
        
        String json2 = "{\n"
                + "  \"sub\" : \"mXX\",\n"
                + "  \"logoutUrl\" : null,\n"
                + "  \"profile\" : \"P1\",\n"
                + "  \"name\" : \"john\",\n"
                + "  \"given_name\" : \"john doe\",\n"
                + "  \"family_name\" : \"doe\",\n"
                + "  \"email\" : null\n"
                + "}";
        
        return Stream.of(json1, json2);
    }

}
