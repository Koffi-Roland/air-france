package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.repind.dao.preference.PreferenceDataRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private PreferenceDataRepository preferenceDataRepository;

    @Transactional
    public void physicalDeletePreference(Long preferenceId){
        List<Long> preferenceLoneList = new ArrayList<>();
        preferenceLoneList.add(preferenceId); // add one Long element to the list
        try{
            preferenceRepository.deleteByPreferenceIdIn(preferenceLoneList);
            preferenceDataRepository.deleteByPreferenceIdIn(preferenceLoneList);
        }catch (Exception e){
            log.error("Unable to delete physically preference with PREFERENCE_ID= {}: {}", preferenceLoneList, e.getMessage());
        }
    }
}
