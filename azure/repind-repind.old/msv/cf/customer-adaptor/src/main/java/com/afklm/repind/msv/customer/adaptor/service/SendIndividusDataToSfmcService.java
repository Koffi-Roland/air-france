package com.afklm.repind.msv.customer.adaptor.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.upsert.UpsertIndividusClient;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.service.encoder.individus.UpsertIndividusEncoder;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendIndividusDataToSfmcService {

    @Autowired
    private UpsertIndividusClient upsertIndividusClient;

    @Autowired
    private UpsertIndividusEncoder upsertIndividusEncoder;



    public ResponseEntity<WrapperUpsertIndividusResponse> sendIndividusDataToSfmc(UpsertIndividusRequestCriteria upsertIndividusCriteria, String uuid) throws BusinessException {

        log.info("[sendIndividusDataToSfmc] - uuid={} - Begin upsert of individus service", uuid);

        WrapperUpsertIndividusResponse wrapperUpsertIndividusResponse =  upsertIndividusClient.execute(upsertIndividusEncoder.encodeUpdateIndividusRequest(upsertIndividusCriteria));

        return new ResponseEntity<>(wrapperUpsertIndividusResponse, wrapperUpsertIndividusResponse.getHttpStatusCode());

    }
}
