package com.afklm.batch.mergeduplicatescore.service;

import com.afklm.batch.mergeduplicatescore.model.IndividualScore;
import com.airfrance.repind.util.service.RestTemplateExtended;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.COMMA;

@Service
@PropertySource("classpath:/app/merge-duplicates-score.properties")
@Slf4j
public class ScoresService implements IScoreService {

    @Qualifier("provideScore")
    @Autowired
    private RestTemplateExtended provideScore;

    @Override
    public List<IndividualScore> fetchScoreByGin(List<String> gins) {
        log.info("Fetch scores for gins: " + gins);
        HttpHeaders headers = provideScore.createHeaders();
        IndividualScore[] wrapperPcs = provideScore.exchange(
                String.join(COMMA, gins) + "?api_key=" + provideScore.getApiKey() + "&sig=" + provideScore.getSig(),
                HttpMethod.GET, new HttpEntity<Object>(headers), IndividualScore[].class).getBody();
        if(wrapperPcs == null){
            throw new IllegalArgumentException("[-] Api response is null");
        }
        return Arrays.stream(wrapperPcs).toList();
    }

}
