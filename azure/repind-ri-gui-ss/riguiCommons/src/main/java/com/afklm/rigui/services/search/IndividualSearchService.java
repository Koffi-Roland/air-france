package com.afklm.rigui.services.search;

import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.model.individual.ModelBasicIndividualData;
import com.afklm.rigui.model.individual.requests.*;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.ConsentService;
import com.afklm.rigui.util.service.RestTemplateExtended;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearchMS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndividualSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsentService.class);

    private final static Integer MAX_GINS_ALLOWED = 255;

    @Autowired
    @Qualifier("searchIndividual")
    private RestTemplateExtended searchIndividual;

    @Autowired
    @Qualifier("manageIndividualIdentifier")
    private RestTemplateExtended manageIndividualIdentifier;

    @Autowired
    private IndividuRepository individuRepository;

    private final static String ENDPOINT_SEARCH = "/v2/search-individual";
    private final static String ENDPOINT_ACCOUNT_CIN = "/contract/%s";
    private final static String ENDPOINT_ACCOUNT_EMAIL = "/email/%s";

    /**
     * Given a model representing the search by multi criteria request, returns a wrapper with all
     * the information needed (number of individuals found and basic data for each of them)
     *
     * @param searchRequest search request
     * @param merge true if merge status to be included otherwise false, false by default
     * @return a WrapperIndividualSearch object
     * @throws ServiceException
     * @throws SystemException
     */
    @Transactional(readOnly = true)
    public WrapperIndividualSearch searchIndividualByMulticriteria(ModelSearchByMulticriteriaRequest searchRequest,boolean merge) throws ServiceException, SystemException {


        ResponseEntity<WrapperIndividualSearchMS[]> response = null;

        LOGGER.warn("Call SearchIndividual MS by Multicriteria: Firstname={} Lastname={} merge = {}", searchRequest.getFirstname(), searchRequest.getLastname(),merge);
        try {
            HttpHeaders headers = this.getHeaders(searchIndividual);
            UriComponentsBuilder builder = this.getBuilder(ENDPOINT_SEARCH);
            Map<String, String> params = this.getParams(searchIndividual);
            params.put("merge",String.valueOf(merge));
            builder.queryParam("merge", "{merge}");
            if(searchRequest.getFirstname() != null) {
                builder.queryParam("firstname", "{firstname}");
                params.put("firstname", searchRequest.getFirstname());
            }
            if(searchRequest.getLastname() != null) {
                builder.queryParam("lastname", "{lastname}");
                params.put("lastname", searchRequest.getLastname());
            }

            response = searchIndividual.exchange(builder.encode().toUriString(),
                    HttpMethod.GET, new HttpEntity<>(headers), WrapperIndividualSearchMS[].class, params);

        } catch (HttpClientErrorException e) {
            handleExceptionFromMS(e);
        }
        LOGGER.info("MS respond with search");
        return toWrapperIndividualSearch(Arrays.asList(response.getBody()));
    }

    /**
     * Given a search by email request model, returns a wrapper that contains all the information needed about the individual(s)
     * that have been found (count, basic info)
     *
     * @param searchRequest
     * @return a WrapperIndividualSearch object
     * @throws ServiceException
     * @throws SystemException
     */
    @Transactional(readOnly = true)
    public WrapperIndividualSearch searchIndividualByEmail(ModelSearchByEmailRequest searchRequest,boolean merge)
            throws ServiceException {

        ResponseEntity<WrapperIndividualSearchMS[]> response = null;

        LOGGER.warn("Call SearchIndividual MS by : Email={} merge = {} " , searchRequest.getEmail(),merge);
        try {
            //Create Headers for request
            HttpHeaders headers = this.getHeaders(searchIndividual);
            UriComponentsBuilder builder = this.getBuilder(ENDPOINT_SEARCH);
            Map<String, String> params = this.getParams(searchIndividual);
            params.put("merge",String.valueOf(merge));
            builder.queryParam("merge", "{merge}");
            if(searchRequest.getEmail() != null) {
                builder.queryParam("email", "{email}");
                params.put("email", searchRequest.getEmail());
            }

            response = searchIndividual.exchange(builder.encode().toUriString(),
                    HttpMethod.GET, new HttpEntity<>(headers), WrapperIndividualSearchMS[].class, params);

        } catch (HttpClientErrorException e) {
            handleExceptionFromMS(e);
        }
        LOGGER.info("MS respond with search");
        return toWrapperIndividualSearch(Arrays.asList(response.getBody()));
    }

    /**
     * Given a search by telecom request model, returns a wrapper that contains all the information needed about the individual(s)
     * that have been found (count, basic info)
     *
     * @param searchRequest
     * @return
     * @throws ServiceException
     * @throws SystemException
     */
    @Transactional(readOnly = true)
    public WrapperIndividualSearch searchIndividualByTelecom(ModelSearchByTelecomRequest searchRequest,boolean merge) throws ServiceException, SystemException {

        ResponseEntity<WrapperIndividualSearchMS[]> response = null;

        LOGGER.warn("Call SearchIndividual MS by : Telecom={} merge = {} " ,searchRequest.getPhoneNumber(),merge);
        try {
            HttpHeaders headers = this.getHeaders(searchIndividual);
            UriComponentsBuilder builder = this.getBuilder(ENDPOINT_SEARCH);
            Map<String, String> params = this.getParams(searchIndividual);
            params.put("merge",String.valueOf(merge));
            builder.queryParam("merge", "{merge}");
            if(searchRequest.getPhoneNumber() != null) {
                builder.queryParam("internationalPhoneNumber", "{internationalPhoneNumber}");
                params.put("internationalPhoneNumber", searchRequest.getPhoneNumber());
            }
            response = searchIndividual.exchange(builder.encode().toUriString(),
                    HttpMethod.GET, new HttpEntity<>(headers), WrapperIndividualSearchMS[].class, params);
        } catch (HttpClientErrorException e) {
            handleExceptionFromMS(e);
        }
        LOGGER.info("MS respond with search");
        return toWrapperIndividualSearch(Arrays.asList(response.getBody()));
    }

    /**
     * Given a search by social ID request model, returns a wrapper that contains all the information needed about the individual(s)
     * that have been found (count, basic info)
     *
     * @param searchRequest model search by social identifier
     * @return WrapperIndividualSearch Individual
     * @throws ServiceException Service exception
     * @throws SystemException Syatem exception
     */
    @Transactional(readOnly = true)
    public WrapperIndividualSearch searchIndividualBySocialID(ModelSearchBySocialIDRequest searchRequest, boolean merge) throws ServiceException, SystemException {

        ResponseEntity<WrapperIndividualSearchMS[]> response = null;
        LOGGER.warn("Call SearchIndividual MS by social ID: Social ID={} Social Type={} merge = {} " , searchRequest.getSocialID(), searchRequest.getSocialType(),merge);
        try {
            HttpHeaders headers = this.getHeaders(searchIndividual);
            UriComponentsBuilder builder = this.getBuilder(ENDPOINT_SEARCH);
            Map<String, String> params = this.getParams(searchIndividual);
            params.put("merge",String.valueOf(merge));
            builder.queryParam("merge", "{merge}");
            if(searchRequest.getSocialID() != null) {
                builder.queryParam("externalIdentifierId", "{externalIdentifierId}");
                params.put("externalIdentifierId", searchRequest.getSocialID());
            }
            if(searchRequest.getSocialType() != null) {
                builder.queryParam("externalIdentifierType", "{externalIdentifierType}");
                params.put("externalIdentifierType", searchRequest.getSocialType());
            }
            response = searchIndividual.exchange(builder.encode().toUriString(),
                    HttpMethod.GET, new HttpEntity<>(headers), WrapperIndividualSearchMS[].class, params);
        } catch (HttpClientErrorException e) {
            handleExceptionFromMS(e);
        }
        LOGGER.info("MS respond with search");
        return toWrapperIndividualSearch(Arrays.asList(response.getBody()));
    }

    private Map<String, String> getParams(RestTemplateExtended rest) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", rest.getApiKey());
        params.put("sig", rest.getSig());
        return params;
    }

    private void handleExceptionFromMS(final HttpClientErrorException e) throws ServiceException {
        if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            LOGGER.info("Individu not found");
            throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND_MULTI_CRIT.getError(),
                    HttpStatus.NOT_FOUND);
        } else if (e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            LOGGER.info("Can't access to MS Search individual");
            throw new ServiceException(BusinessErrorList.API_FORBIDDEN_MS_ACCESS.getError(), HttpStatus.FORBIDDEN);
        } else {
            LOGGER.info("ERROR: When calling MS Search individual: " + e.getLocalizedMessage() + ", "
                    + e.getResponseBodyAsString());
            throw new ServiceException(BusinessErrorList.API_MS_TECHNICAL_ERROR.getError(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UriComponentsBuilder getBuilder(String endpoint) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endpoint);
        builder.queryParam("api_key", "{api_key}");
        builder.queryParam("sig", "{sig}");
        return builder;
    }

    private HttpHeaders getHeaders(RestTemplateExtended rest) {
        HttpHeaders headers = rest.createHeaders();
        return headers;
    }

    private WrapperIndividualSearch toWrapperIndividualSearch(List<WrapperIndividualSearchMS> gins) throws ServiceException {
        if(gins.isEmpty()) {
            LOGGER.info("gins not found");
            throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND_MULTI_CRIT.getError(),
                    HttpStatus.NOT_FOUND);
        }
        if(gins.size() > MAX_GINS_ALLOWED) {
            LOGGER.info("Too many gins !");
            throw new ServiceException(BusinessErrorList.API_BUSINESS_TOO_MANY_INDIVIDUS_FOUND.getError(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        WrapperIndividualSearch result = new WrapperIndividualSearch();
        List<Individu> individus = individuRepository.findIndividuBySginIn(gins.stream().map(WrapperIndividualSearchMS::getGin).toList());
        if(gins.size() != individus.size()) {
            LOGGER.info("Not all individual info found");
            throw new ServiceException(BusinessErrorList.API_BUSINESS_NOT_ALL_INDIVIDUS_FOUND.getError(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        result.count = individus.size();
        result.data = individus.stream().map((this::toModelBasicIndividualData)).toList();
        return result;
    }

    private ModelBasicIndividualData toModelBasicIndividualData(Individu ind) {
        ModelBasicIndividualData modelBasicIndividualData = new ModelBasicIndividualData();
        modelBasicIndividualData.setFirstname(ind.getPrenom());
        modelBasicIndividualData.setLastname(ind.getNom());
        modelBasicIndividualData.setBirthdate(ind.getDateNaissance());
        modelBasicIndividualData.setGin(ind.getSgin());
        modelBasicIndividualData.setCivility(ind.getCivilite());
        modelBasicIndividualData.setStatus(ind.getStatutIndividu());
        if(!ind.getPostaladdress().isEmpty()) {
            // Take first postal address result
            PostalAddress address = ind.getPostaladdress().iterator().next();
            modelBasicIndividualData.setPostalAddress(String.format("%s %s %s", address.getSno_et_rue(), address.getScode_postal(), address.getSville()));
        }
        return modelBasicIndividualData;
    }
    @Transactional(readOnly = true)
    public WrapperIndividualSearch searchIndividualByAccountIdentifier(ModelSearchByAccountRequest searchRequest) throws ServiceException {
        ResponseEntity<WrapperIndividualSearchMS> response = null;

        LOGGER.warn("Call manageIndividualIdentifier MS by request: " + searchRequest.toString());
        try {
            if(searchRequest.getCin() != null && searchRequest.getEmail() != null) {
                throw new ServiceException(BusinessErrorList.API_BUSINESS_INVALID_PARAMETERS_ACCOUNT_REQUEST.getError(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            HttpHeaders headers = this.getHeaders(manageIndividualIdentifier);
            Map<String, String> params = this.getParams(manageIndividualIdentifier);
            UriComponentsBuilder builder = null;
            if(searchRequest.getCin() != null) {
                builder = this.getBuilder( String.format(ENDPOINT_ACCOUNT_CIN, searchRequest.getCin()));
            }
            if(searchRequest.getEmail() != null) {
                builder = this.getBuilder( String.format(ENDPOINT_ACCOUNT_EMAIL, searchRequest.getEmail()));
            }
            if(builder == null) {
                throw new ServiceException(BusinessErrorList.API_MISSING_REQUEST_PARAMETER.getError(),
                        HttpStatus.BAD_REQUEST);
            }
            response = manageIndividualIdentifier.exchange(builder.encode().toUriString(),
                    HttpMethod.GET, new HttpEntity<>(headers), WrapperIndividualSearchMS.class, params);
        } catch (HttpClientErrorException e) {
            handleExceptionFromMS(e);
        }
        LOGGER.info("MS respond with manage individual identifier");
        return toWrapperIndividualSearch(List.of(response.getBody()));
    }
}
