package com.afklm.batch.mergeduplicatescore.service;

import com.afklm.batch.mergeduplicatescore.model.IndividualScore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IScoreService {
    List<IndividualScore> fetchScoreByGin(List<String> gins);
}
