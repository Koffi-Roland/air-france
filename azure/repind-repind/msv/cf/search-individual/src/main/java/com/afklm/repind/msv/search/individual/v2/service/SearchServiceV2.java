package com.afklm.repind.msv.search.individual.v2.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.client.search.contract.RiSearchGinByContractClient;
import com.afklm.repind.msv.search.individual.client.search.contract.model.SearchGinByContractRequest;
import com.afklm.repind.msv.search.individual.client.search.email.RiSearchGinByEmailClient;
import com.afklm.repind.msv.search.individual.client.search.email.model.SearchGinByEmailRequest;
import com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.RiSearchGinByLastnameAndFirstnameClient;
import com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.model.SearchGinByLastnameAndFirstnameRequest;
import com.afklm.repind.msv.search.individual.client.search.phone.RiSearchGinByPhoneNumberClient;
import com.afklm.repind.msv.search.individual.client.search.phone.model.SearchGinByPhoneNumberRequest;
import com.afklm.repind.msv.search.individual.client.search.socialMedia.RiSearchGinBySocialMediaClient;
import com.afklm.repind.msv.search.individual.client.search.socialMedia.model.SearchGinBySocialMediaRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import com.afklm.repind.msv.search.individual.model.error.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Search Service
 */
@Service
@Slf4j
public class SearchServiceV2 {

    @Autowired
    private RiSearchGinByEmailClient riSearchGinByEmailClient;

    @Autowired
    private RiSearchGinByContractClient riSearchGinByContractClient;

    @Autowired
    private RiSearchGinByPhoneNumberClient riSearchGinByPhoneNumberClient;

    @Autowired
    private RiSearchGinBySocialMediaClient riSearchGinBySocialMediaClient;

    @Autowired
    private RiSearchGinByLastnameAndFirstnameClient riSearchGinByLastnameAndFirstnameClient;

    /**
     * Search By
     * @param cin
     * @param email
     * @param internationalPhoneNumber
     * @param externalIdentifierId the externalIdentifierId
     * @param externalIdentifierType the externalIdentifiertYPE
     * @param firstname
     * @param lastname
     * @return
     * @throws BusinessException
     */
    public List<String> searchBy(String cin, String email, String internationalPhoneNumber, String externalIdentifierId,
                                 String externalIdentifierType, String firstname, String lastname, boolean merge) throws BusinessException {

        List<String> gins = new ArrayList<>();

        if (cin != null) {
            SearchGinsResponse searchGinsResponse = riSearchGinByContractClient.execute(new SearchGinByContractRequest().withContract(cin).withMerge(merge));
            log.info("Search by cin : {} results found",searchGinsResponse.getGins().size());
            gins.addAll(searchGinsResponse.getGins());
        } else if (email != null) {
            SearchGinsResponse searchGinsResponse = riSearchGinByEmailClient.execute(new SearchGinByEmailRequest().withEmail(email).withMerge(merge));
            log.info("Search by email : {} results found",searchGinsResponse.getGins().size());
            gins.addAll(searchGinsResponse.getGins());
        } else if (internationalPhoneNumber != null) {
            SearchGinsResponse searchGinsResponse = riSearchGinByPhoneNumberClient.execute(new SearchGinByPhoneNumberRequest().withPhone(internationalPhoneNumber).withMerge(merge));
            log.info("Search by phone : {} results found",searchGinsResponse.getGins().size());
            gins.addAll(searchGinsResponse.getGins());
        } else if (externalIdentifierId != null && externalIdentifierType != null) {
            SearchGinsResponse searchGinsResponse = riSearchGinBySocialMediaClient.execute(new SearchGinBySocialMediaRequest().withExternalIdentifierId(externalIdentifierId).withExternalIdentifierType(externalIdentifierType).withMerge(merge));
            log.info("Search by identifierId : {} results found",searchGinsResponse.getGins().size());
            gins.addAll(searchGinsResponse.getGins());
        }else if (lastname != null && firstname != null) {
            SearchGinsResponse searchGinsResponse = riSearchGinByLastnameAndFirstnameClient.execute(new SearchGinByLastnameAndFirstnameRequest().withFirstname(firstname).withLastname(lastname).withMerge(merge));
            log.info("Search by lastname and firstname : {} results found",searchGinsResponse.getGins().size());
            gins.addAll(searchGinsResponse.getGins());
        }else {
            // should not happened because already checked
            throw new BusinessException(BusinessError.API_MISSING_REQUEST_PARAMETER);
        }

        return gins;
    }

}
