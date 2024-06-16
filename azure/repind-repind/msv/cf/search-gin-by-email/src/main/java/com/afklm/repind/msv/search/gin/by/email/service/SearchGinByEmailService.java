package com.afklm.repind.msv.search.gin.by.email.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.msv.search.gin.by.email.repository.IEmailRepository;
import com.afklm.repind.msv.search.gin.by.email.service.encoder.SearchGinByEmailEncoder;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SearchGinByEmailService {

    private IEmailRepository emailRepository;

    private SearchGinByEmailEncoder searchGinByEmailEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByEmailResponse> search(String iEmail, boolean merge) {


            Collection<EmailEntity> emails = emailRepository.findByEmailAndStatutMediumInAndIndividuIsNotNull(iEmail,
                            Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString())).
                    stream().filter(item -> this.filterIndividualMerged(item, merge))
                    .collect(Collectors.toSet());

            log.info("Search by email : {} results found", emails.size());
            return new ResponseEntity<>(searchGinByEmailEncoder.decode(emails), HttpStatus.OK);

    }
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean filterIndividualMerged(EmailEntity e, boolean merge) {
        return merge || e.getIndividu() != null && e.getIndividu().getGin() != null && !e.getIndividu().getStatutIndividu().equals(IndividuStatusEnum.MERGED.toString());
    }

}
