package com.afklm.repind.msv.search.gin.by.lastname.firstname.service;

import com.afklm.repind.msv.search.gin.by.lastname.firstname.entity.Individu;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.repository.IndividuRepository;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.service.encoder.SearchGinByLastnameAndFirstnameEncoder;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.util.NormalizedStringUtils;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.util.StatusEnum;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.wrapper.WrapperSearchGinByLastnameAndFirstnameResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class SearchGinByLastnameAndFirstnameService {

    private IndividuRepository individuRepository;
    private SearchGinByLastnameAndFirstnameEncoder searchGinByLastnameAndFirstnameEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByLastnameAndFirstnameResponse> search(String nom, String prenom) {

        Collection<Individu> individus = individuRepository.findByNomAndPrenomAndStatutIndividuIn(normalize(nom), normalize(prenom), Arrays.asList(StatusEnum.VALIDATED.getName(), StatusEnum.PENDING.getName()));
        log.info("Search by lastname and firstname : {} results found",individus.size());

        return new ResponseEntity<>(searchGinByLastnameAndFirstnameEncoder.decode(individus), HttpStatus.OK);
    }

    /**
     * This method is aimed to normalize a given string by:
     * <ul>
     * 	<li>replacing special characters</li>
     * 	<li>transforming to upper case</li>
     * </ul>
     */
    protected String normalize(String word) {

        // If string is empty normalization is useless
        if(StringUtils.isEmpty(word)) {
            return word;
        }

        // Replace special characters
        String cleanedStr = NormalizedStringUtils.normalizeString(word);
        // Transform to upper case
        cleanedStr = cleanedStr.toUpperCase();

        return cleanedStr;
    }

}