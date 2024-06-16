package com.airfrance.batch.automaticmerge.service;

import com.airfrance.batch.automaticmerge.exception.ServiceException;
import com.airfrance.batch.common.service.ProvideIndividualScoreService;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:/app/merge-duplicates-score.properties")
@Slf4j
public class ScoreService {

    @Autowired
    private ProvideIndividualScoreService provideIndividualScoreService;

    public List<WrapperProvideIndividualScoreResponse> fetchScoreByGin(List<String> gins) throws ServiceException {
        if(gins.isEmpty()){
            throw new IllegalArgumentException("[-] empty input parameters in fetchScoreByGin");
        }

        List<WrapperProvideIndividualScoreResponse> wrapperPcs = null;
        try {
            wrapperPcs = provideIndividualScoreService.calculatePcsScore(gins);
        }catch(Exception e){
            log.error("[-] Error while fetching score for GINS {}", gins);
            throw new ServiceException("[-] ProvideIndividualScoreService response is null", gins);
        }

        return wrapperPcs;
    }

}