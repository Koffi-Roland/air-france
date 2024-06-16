package com.afklm.repind.msv.search.gin.by.email.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.msv.search.gin.by.email.repository.IEmailRepository;
import com.afklm.repind.msv.search.gin.by.email.service.encoder.SearchGinByEmailEncoder;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<WrapperSearchGinByEmailResponse> search(String iEmail) {
        Collection<EmailEntity> emails = emailRepository.findByEmailAndStatutMediumIn(iEmail , Arrays.asList("V" , "I")).stream().filter(e -> e.getIndividu().getGin() != null).collect(Collectors.toSet());
        return new ResponseEntity<>(searchGinByEmailEncoder.decode(emails), HttpStatus.OK);
    }

}
