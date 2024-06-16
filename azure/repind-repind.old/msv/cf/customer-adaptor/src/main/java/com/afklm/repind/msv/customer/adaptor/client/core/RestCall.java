package com.afklm.repind.msv.customer.adaptor.client.core;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model.RetrieveTokenRequest;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model.TokenResponseModel;
import com.afklm.repind.msv.customer.adaptor.model.error.BusinessError;
import com.afklm.repind.msv.customer.adaptor.service.encoder.individus.UpsertIndividusEncoder;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
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
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RestCall<T extends HttpRequestModel,V extends HttpResponseModel> {

    private final String url;
    private final HttpMethod httpMethod;
    private final Class<V> returnObject;
    private String token = null;
    private Boolean isRefreshToken = true;

    @Value("${az-vault.namespace}")
    private String keyVaultName;

    @Value("${az-vault.secrets.clientid.secretName}")
    private String clientId;

    @Value("${az-vault.secrets.secretid.secretName}")
    private String clientSecret;

    @Value("${services.request.token.url}")
    private String tokenUrl;

    @Value("${services.request.token.grant-type}")
    private String grant_type;

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
        ignoreCertificates();
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

        if(!response.getStatusCode().is2xxSuccessful() && response.getBody() != null ){
            LOGGER.error("Salesforce API Business exception - {}", response.getBody());
            throw new BusinessException(BusinessError.SFMC_API_BUSINESS_EXCEPTION);
        }

        return upsertIndividusEncoder.decode(response);
    }


    private void ignoreCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            LOGGER.error("[-] exception with ignore certificates.");
        }
    }


    private String getToken()  {
        LOGGER.info("Call salesforce get Token API");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        RetrieveTokenRequest retrieveTokenRequest =  new RetrieveTokenRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);

        HashMap<String, String> requestdata = new HashMap<>();
        requestdata.put("grant_type", grant_type);
        requestdata.put("client_id", clientId);
        requestdata.put("client_secret", clientSecret);

        retrieveTokenRequest.setBody(requestdata);

        HttpEntity<?> entity = new HttpEntity<>(retrieveTokenRequest.getBody(), headers);
        try{

            ResponseEntity<TokenResponseModel> response  = restTemplate.exchange(tokenUrl, httpMethod, entity, TokenResponseModel.class );

            LOGGER.info("response getToken : {}", response);
            return Objects.requireNonNull(response.getBody()).getAccess_token();

        } catch (RestClientException e) {
            LOGGER.error("Salesforce getToken Business exception - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
