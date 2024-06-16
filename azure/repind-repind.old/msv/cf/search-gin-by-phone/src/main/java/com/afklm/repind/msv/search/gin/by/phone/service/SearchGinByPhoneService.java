package com.afklm.repind.msv.search.gin.by.phone.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.phone.entity.Telecoms;
import com.afklm.repind.msv.search.gin.by.phone.repository.ITelecomsRepository;
import com.afklm.repind.msv.search.gin.by.phone.service.encoder.SearchGinByPhoneEncoder;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
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
public class SearchGinByPhoneService {

    private ITelecomsRepository telecomsRepository;
    private SearchGinByPhoneEncoder searchGinByPhoneEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByPhoneResponse> search(String iEmail) throws BusinessException {
        Collection<Telecoms> telecoms = telecomsRepository.findBySnormInterPhoneNumberAndSstatutMediumInAndScodeMediumIn(iEmail , Arrays.asList("V" , "I") , Arrays.asList("P" , "D")).stream().filter(t -> t.getSgin() != null).collect(Collectors.toSet());

        return new ResponseEntity<>(searchGinByPhoneEncoder.decode(telecoms), HttpStatus.OK);
    }

}
