package com.afklm.rigui.services.resources;

import com.afklm.rigui.criteria.merge.MergeCriteria;
import com.afklm.rigui.criteria.merge.MergeProvideCriteria;
import com.afklm.rigui.model.error.BusinessErrorList;
import com.afklm.rigui.model.error.RestError;
import com.afklm.rigui.model.error.ServerRestError;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.MergeHelper;
import com.afklm.rigui.wrapper.merge.WrapperMerge;
import com.afklm.rigui.wrapper.merge.WrapperMergeRequestBloc;
import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.role.BusinessRoleRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;
import com.afklm.rigui.util.service.RestTemplateExtended;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class MergeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeService.class);

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static final List<String> CONTRACT_STATUS_MERGEABLE = new ArrayList(Arrays.asList("C", "P"));

    @Qualifier("manageMerge")
    @Autowired
    private RestTemplateExtended manageMerge;

    @Autowired
    private MergeHelper mergeHelper;

    @Autowired
    private IndividuRepository individuRepository;

    @Autowired
    private BusinessRoleRepository businessRoleRepository;

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    private static final Pattern VALID_GIN_REGEX =
            Pattern.compile("^\\d{12}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_PATH_REGEX =
            Pattern.compile("^\\d{12}/\\d{12}$", Pattern.CASE_INSENSITIVE);
    public WrapperMerge getMergeIndividuals(MergeProvideCriteria criteria) throws ServiceException, IOException {

        String source = criteria.getIdentifiant1();
        String target = criteria.getIdentifiant2();

        source = verifyMergeInput(source);
        target = verifyMergeInput(target);

        checkValidityOfRightsToMerge(source, target, criteria.getRoleHabilitations().isHasFBRole(),
                criteria.getRoleHabilitations().isHasAdminRole(), criteria.getRoleHabilitations().isHasGPRole(),
                criteria.getRoleHabilitations().isHasFBRoleMinor());

        try {
            if (!VALID_GIN_REGEX.matcher(source).find() || !VALID_GIN_REGEX.matcher(target).find()) {
                throw new ServiceException(BusinessErrorList.API_INVALID_GIN.getError(), HttpStatus.PRECONDITION_FAILED);
            }

            //Create Headers for request
            HttpHeaders headers = manageMerge.createHeaders();

            // Construct the URL using UriComponentsBuilder
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(source + "/" + target)
                    .queryParam("forceSwitchIndividual", criteria.isForceSwitchIndividual())
                    .queryParam("api_key", manageMerge.getApiKey())
                    .queryParam("sig", manageMerge.getSig());

            // Encode the URL to ensure it's safe and properly formatted
            URI uri = builder.build().encode().toUri();
            if(uri.getPath().matches("^\\d{12}/\\d{12}$") && uri.getQuery().contains("api_key")){
                return manageMerge.exchange(
                        uri,
                        HttpMethod.GET, new HttpEntity<Object>(headers), WrapperMerge.class).getBody();
            } else {
            LOGGER.warn("Potential SSRF attempt with URI: " + uri);
        }


        } catch (HttpClientErrorException e) {
            RestError restError = mapReceivedObject(e);
            LOGGER.error(restError.getDescription());
            HttpStatus httpStatus = Arrays.stream(HttpStatus.values())
                    .filter(status -> status.name().equals(restError))
                    .findAny()
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
            int errorIntCode = httpStatus.value();
            throw new ServiceException(restError, httpStatus);

        }
        return null;
    }

    public String verifyMergeInput(String input) throws ServiceException{
        if(individuRepository.findBySgin(input) == null){
            if(roleContratsRepository.findByNumeroContrat(input) == null)
                throw new ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND_MULTI_CRIT.getError(), HttpStatus.NOT_FOUND);
            else
                input = roleContratsRepository.findByNumeroContrat(input).getGin();
        }
        return input;
    }

    public WrapperMerge individualMergeResume(MergeCriteria criteria) throws IOException, ServiceException {
        String gin1 = criteria.getGinSource();
        String gin2 = criteria.getGinCible();
        List<WrapperMergeRequestBloc> blocs = criteria.getBlocs();
        try {
            //Create Headers for request
            HttpHeaders headers = manageMerge.createHeaders();

            // Construct the URL using UriComponentsBuilder
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("resume/" + gin1 + "/" + gin2)
                    .queryParam("api_key", manageMerge.getApiKey())
                    .queryParam("sig", manageMerge.getSig());

            // Encode the URL to ensure it's safe and properly formatted
            URI uri = builder.build().encode().toUri();
            HttpEntity<List<WrapperMergeRequestBloc>> requestEntity = new HttpEntity<>(blocs, headers);
            if(VALID_PATH_REGEX.matcher(uri.getPath()).find()){
                return manageMerge.exchange(
                    "resume/" + gin1 + "/" + gin2 + "?api_key=" + manageMerge.getApiKey() + "&sig=" + manageMerge.getSig(),
                    HttpMethod.POST, requestEntity, WrapperMerge.class).getBody();
            }
        } catch (HttpClientErrorException e) {
            RestError restError = mapReceivedObject(e);
            LOGGER.error(restError.getDescription());
            HttpStatus httpStatus = Arrays.stream(HttpStatus.values())
                    .filter(status -> status.name().equals(restError))
                    .findAny()
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new ServiceException(restError, httpStatus);
        }
    return null;
    }

    public WrapperMerge individualMerge(MergeCriteria criteria) throws ServiceException, IOException {
        String gin1 = criteria.getGinSource();
        String gin2 = criteria.getGinCible();
        List<WrapperMergeRequestBloc> blocs = criteria.getBlocs();

        try {
            //Create Headers for request
            HttpHeaders headers = manageMerge.createHeaders();

            // Construct the URL using UriComponentsBuilder
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(gin1 + "/" + gin2)
                    .queryParam("api_key", manageMerge.getApiKey())
                    .queryParam("sig", manageMerge.getSig());

            // Encode the URL to ensure it's safe and properly formatted
            URI uri = builder.build().encode().toUri();

            // Create the HttpEntity with the URI
            HttpEntity<List<WrapperMergeRequestBloc>> requestEntity = new HttpEntity<>(blocs, headers);

            // Make the HTTP request using the URI
            return manageMerge.exchange(uri, HttpMethod.GET, requestEntity, WrapperMerge.class).getBody();

            /*return manageMerge.exchange(
                    gin1 + "/" + gin2 + "?api_key=" + manageMerge.getApiKey() + "&sig=" + manageMerge.getSig(),
                    HttpMethod.POST, requestEntity, WrapperMerge.class).getBody();
*/
        } catch (HttpClientErrorException e) {
            RestError restError = mapReceivedObject(e);
            LOGGER.error(restError.getDescription());
            HttpStatus httpStatus = Arrays.stream(HttpStatus.values())
                    .filter(status -> status.name().equals(restError))
                    .findAny()
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new ServiceException(restError, httpStatus);
        }
    }

    private void checkValidityOfRightsToMerge(String source, String target, boolean hasFBRole, boolean hasAdminRole,
                                              boolean hasGPRole, boolean hasFBRoleMinor) throws ServiceException {

        Individu individuSource = individuRepository.findBySgin(source);
        Individu individuTarget = individuRepository.findBySgin(target);

        if (!isSamePerson(individuSource, individuTarget) && hasFBRoleMinor) {
            throw new ServiceException(BusinessErrorList.MERGE_INVALID_RIGHTS.getError(), HttpStatus.FORBIDDEN);
        }

        List<BusinessRole> listBRSource = businessRoleRepository.findByGinInd(source);
        List<BusinessRole> listBRTarget = businessRoleRepository.findByGinInd(target);

        List<RoleContrats> listRCSource = roleContratsRepository.findByGinAndEtat(source, CONTRACT_STATUS_MERGEABLE);
        List<RoleContrats> listRTarget = roleContratsRepository.findByGinAndEtat(target, CONTRACT_STATUS_MERGEABLE);

        boolean isGPSource = mergeHelper.checkHasGPRole(listBRSource);
        boolean isGPTarget = mergeHelper.checkHasGPRole(listBRTarget);

        boolean isFBSource = mergeHelper.checkHasFBContract(listRCSource);
        boolean isFBTarget = mergeHelper.checkHasFBContract(listRTarget);

        boolean isMASource = mergeHelper.checkHasMAContract(listRCSource);
        boolean isMATarget = mergeHelper.checkHasMAContract(listRTarget);

        //GP - GP
        if ((isGPSource && !isFBSource && !isMASource) && (isGPTarget && !isFBTarget && !isMATarget)) {
            if (!hasGPRole && !hasAdminRole) {
                throw new ServiceException(BusinessErrorList.MERGE_INVALID_RIGHTS.getError(), HttpStatus.FORBIDDEN);
            }
        }

        //FB - FB
        if ((!isGPSource && isFBSource && !isMASource) && (!isGPTarget && isFBTarget && !isMATarget)) {
            if (!(hasFBRole || hasFBRoleMinor) && !hasAdminRole) {
                throw new ServiceException(BusinessErrorList.MERGE_INVALID_RIGHTS.getError(), HttpStatus.FORBIDDEN);
            }
        }
    }

    private boolean isSamePerson(Individu individualSource, Individu individualTarget) {
        boolean isBirthdaySame = false;
        if (individualSource.getDateNaissance() == null || individualTarget.getDateNaissance() == null) {
            isBirthdaySame = true;
        } else {
            isBirthdaySame = individualSource.getDateNaissance().compareTo(individualTarget.getDateNaissance()) == 0;
        }
        if (!StringUtils.equals(individualSource.getNom(), individualTarget.getNom())
                || !StringUtils.equals(individualSource.getPrenom(), individualTarget.getPrenom())
                || !isBirthdaySame) {
            return false;
        }
        return true;
    }


    private RestError mapReceivedObject(HttpClientErrorException e) throws IOException {
        RestError restErrorTmp = null;
        ServerRestError serverRestError = new ObjectMapper().readValue(e.getResponseBodyAsString(), ServerRestError.class);
        if(serverRestError != null && serverRestError.getRestError() != null){
            restErrorTmp = serverRestError.getRestError();
        }

        return restErrorTmp;
    }
}
