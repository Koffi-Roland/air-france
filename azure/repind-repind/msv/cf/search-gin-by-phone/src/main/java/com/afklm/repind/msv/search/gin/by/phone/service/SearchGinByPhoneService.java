package com.afklm.repind.msv.search.gin.by.phone.service;

import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.msv.search.gin.by.phone.service.encoder.SearchGinByPhoneEncoder;
import com.afklm.repind.msv.search.gin.by.phone.wrapper.WrapperSearchGinByPhoneResponse;
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
public class SearchGinByPhoneService {

    private TelecomsRepository telecomsRepository;
    private SearchGinByPhoneEncoder searchGinByPhoneEncoder;

    public static final Collection<String> I_STATUS = Arrays.asList("V", "I");
    public static final Collection<String> I_SCODES = Arrays.asList("P", "D");

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByPhoneResponse> search(String tel, boolean merge) throws BusinessException {

        Collection<Telecoms> telecoms = telecomsRepository.findByNormInterPhoneNumberAndStatutMediumInAndCodeMediumInAndIndividuIsNotNull(tel, I_STATUS, I_SCODES)
                .stream().filter(item -> this.filterIndividualMerged(item, merge))
                .collect(Collectors.toSet());

        log.info("Search by phone : {} results found", telecoms.size());
        return new ResponseEntity<>(searchGinByPhoneEncoder.decode(telecoms), HttpStatus.OK);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean filterIndividualMerged(Telecoms t, boolean merge) {
        return merge || t.getIndividu() != null && t.getIndividu().getGin() != null && !t.getIndividu().getStatutIndividu().equals(IndividuStatusEnum.MERGED.toString());
    }

}
