package com.afklm.repind.msv.customer.adaptor.client.core;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model.RetrieveTokenRequest;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model.TokenResponseModel;
import com.afklm.repind.msv.customer.adaptor.model.error.BusinessError;
import com.afklm.repind.msv.customer.adaptor.service.encoder.individus.UpsertIndividusEncoder;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RestCall<T extends HttpRequestModel,V extends HttpResponseModel> {

    private final String url;
    private final HttpMethod httpMethod;
    private final Class<V> returnObject;
    private String token = null;
    private Boolean isRefreshToken = true;

    @Value("${services.request.token.url}")
    private String tokenUrl;
    @Value("${services.request.token.grant-type}")
    private String grantType;


    @Value("${services.request.token.private-key}")
    private String privateKey;


    @Autowired
    private UpsertIndividusEncoder upsertIndividusEncoder;


    private static final Logger LOGGER = LoggerFactory.getLogger(RestCall.class);

    public RestCall(String url, HttpMethod httpMethod , Class<V> returnObject) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.returnObject = returnObject;
    }

    @Autowired
    private ClientResponseErrorHandler responseErrorHandler;

    @Autowired
    private RestTemplate restTemplate;


    public WrapperUpsertIndividusResponse execute(T data) throws BusinessException {
        restTemplate.setErrorHandler(responseErrorHandler);
        HttpEntity<?> entity;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        if(token == null){
            token = getToken();
        }

        headers.set("Authorization", "Bearer " + token);

        if(data instanceof  HttpRequestBodyModel httpRequestBodyModel){
            entity = new HttpEntity<>( httpRequestBodyModel.getBody() , headers);
        }
        else {
            entity = new HttpEntity<>(data.getHttpHeaders());
        }
        ResponseEntity<?> response = null;

        try{
            LOGGER.info("Call salesforce API with url={} and requestBody={}", url, entity);
            response = restTemplate.exchange(url, httpMethod, entity, Object.class, data.returnUriVariables());
            LOGGER.info("[+] response_status={}", response.getStatusCode().value());
            isRefreshToken = true;

        } catch (RestClientException e) {
            if (isRefreshToken) {
                LOGGER.error("Token is refresh");
                token = getToken();
                isRefreshToken = false;
                execute(data);
            }
        }

        if(response == null || (!response.getStatusCode().is2xxSuccessful() && response.getBody() != null)){
            LOGGER.error("Salesforce API Business exception - {}", response);
            throw new BusinessException(BusinessError.SFMC_API_BUSINESS_EXCEPTION);
        }

        return upsertIndividusEncoder.decode(response);
    }



    /**
     * Allows to read the private key shared with the Salesforce Marketing Cloud
     * to request and create a JWT to authenticate the user request.
     *
     * @return a JSON Web Token to authenticate the user request.
     */
    private String generateJwt() throws Exception {
        try {
            LOGGER.info("Begin generation jwt");

            PrivateKey generatedKey = getPrivateKey();

            LOGGER.info("response generatedKey : {}", generatedKey);

            //define header map
            Map<String, Object> headers = new HashMap<>();
            headers.put("alg", "RS256");
            headers.put("typ", "JWT");
            headers.put("kid", "25119676-313f-4732-9583-9cb0cb32f0f8");

            // define payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", "https://auth.marketingcloudapis.com/id/1061329");
            payload.put("iss", "8st5kbpeegx8bg4qfven4m0f");
            payload.put("exp", new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
            payload.put("iat", new Date(System.currentTimeMillis()));
            payload.put("nbf", new Date(System.currentTimeMillis()));

            LOGGER.info("response generatedKey : {}", generatedKey);

            return Jwts.builder().header().add(headers)
                    .and()
                    .claims(payload).claims()
                    .and()
                    .audience().single("https://mc.login.exacttarget.com/")
                    .signWith(SignatureAlgorithm.RS256, generatedKey)
                    .compact();

        }catch (Exception e) {
            LOGGER.error("Error while generating jwt token");
            throw new Exception("Unable to build the JWT bearer",e);

        }
    }


    private PrivateKey getPrivateKey() throws IOException, GeneralSecurityException {
        LOGGER.info("Begin method getPrivateKey()");
        InputStream inputStream = new ByteArrayInputStream(privateKey.getBytes());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder privateKeyPEM = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignore lines starting with "-----BEGIN" or "-----END"
                if (!line.startsWith("-----")) {
                    privateKeyPEM.append(line.trim());
                }
            }
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM.toString());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

            LOGGER.info("End method getPrivateKey");
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        }

    }


    private String getToken()  {
        LOGGER.info("Call salesforce get Token API");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        HttpEntity<?> entity = getHttpEntity();
        try{

            ResponseEntity<TokenResponseModel> response  = restTemplate.exchange(tokenUrl, httpMethod, entity, TokenResponseModel.class );

            LOGGER.info("response getToken : {}", response);
            return Objects.requireNonNull(response.getBody()).getAccess_token();

        } catch (RestClientException e) {
            LOGGER.error("Salesforce getToken Business exception - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<?> getHttpEntity() {
        RetrieveTokenRequest retrieveTokenRequest =  new RetrieveTokenRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);

        HashMap<String, String> requestData = new HashMap<>();
        requestData.put("grant_type", grantType);
        requestData.put("Content-type", "application/x-www-form-urlencoded");
        try {
            requestData.put("assertion", generateJwt());

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        retrieveTokenRequest.setBody(requestData);

        return new HttpEntity<>(retrieveTokenRequest.getBody(), headers);
    }


}
