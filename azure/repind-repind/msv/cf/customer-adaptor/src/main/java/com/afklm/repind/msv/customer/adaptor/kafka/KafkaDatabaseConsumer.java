package com.afklm.repind.msv.customer.adaptor.kafka;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.metric.ProcessTypeEnum;
import com.afklm.repind.common.metric.ProcessingMetricDTO;
import com.afklm.repind.common.metric.ProcessingMetricLogger;
import com.afklm.repind.common.metric.StatusEnum;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.service.SendIndividusDataToSfmcService;
import com.afklm.repind.msv.customer.adaptor.service.filters.FilterAndProcessIndividusService;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaDatabaseConsumer {

    @Autowired
    FilterAndProcessIndividusService filterAndProcessService;
    @Autowired
    SendIndividusDataToSfmcService sendDataToSfmcService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
    @Getter
    private UpsertIndividusRequestCriteria upsertedIndividu;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupid;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("topic is {}, groupid is {}", topic, groupid);

        filterAndProcessKafkaMsg(consumerRecord);

    }

    public ResponseEntity<WrapperUpsertIndividusResponse> filterAndProcessKafkaMsg(ConsumerRecord<String, String> consumerRecord) {
        String uuid = UUID.randomUUID().toString();
        ResponseEntity<WrapperUpsertIndividusResponse> response = null;

        try{
            LOGGER.info("[Start] - UUID={} - Sending Event Message received Consumer record -> {}", uuid, consumerRecord);

            //Processing kafka data && filter and prepare the request to be sent to SFMC
            UpsertIndividusRequestCriteria upsertIndividusCriteria = filterAndProcessService.filterAndProcessIndividusKafkaData(consumerRecord, uuid);
            this.upsertedIndividu = upsertIndividusCriteria;

            //Sending the request to SFMC
            if(upsertIndividusCriteria.isEligible()){
                response = sendDataToSfmcService.sendIndividusDataToSfmc(upsertIndividusCriteria, uuid);
                if(response != null && response.getStatusCode().is2xxSuccessful()){
                    logComo(consumerRecord.value(), StatusEnum.SUCCESS, upsertIndividusCriteria.getGin(),"");
                }
            }else{
                LOGGER.error("[-] Individu is not Eligible - uuid={}", uuid);
                logComo( consumerRecord.value(), StatusEnum.NOT_ELIGIBLE, upsertIndividusCriteria.getGin(), upsertIndividusCriteria.getMessage());
            }

        }catch (BusinessException e){
            LOGGER.error("[-] Business Exception - uuid={} , Message={}", uuid, e.getMessage());
            logComo(consumerRecord.value(), StatusEnum.FAILURE, "", e.getMessage());

        }catch(Exception e){
            LOGGER.error("[-] Exception - uuid={} , Message={}", uuid, e.getMessage());
        }

        LOGGER.info("[END] - uuid={} - Sending Event Message", uuid);
        return response;
    }

    private static void logComo(String consumerRecord, StatusEnum status, String gin,  String message) {
        ProcessingMetricLogger.log(ProcessingMetricDTO.builder()
                        .withProcessType(ProcessTypeEnum.CUSTOMER_ADAPTOR)
                        .withConsumerRecord(consumerRecord)
                        .withStatus(status)
                        .withGin(gin)
                        .withCustomMsg(message)
                        .build()
                );
    }

}
