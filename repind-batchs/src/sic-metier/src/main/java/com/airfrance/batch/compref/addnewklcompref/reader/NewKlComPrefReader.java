package com.airfrance.batch.compref.addnewklcompref.reader;

import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NewKlComPrefReader implements ItemReader<CommunicationPreferences> {

    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    private ItemReader<CommunicationPreferences> itemReader;

    private List<CommunicationPreferences> communicationPreferencesList;

    private final static String DOMAIN = "S";
    private final static String COM_GROUP_TYPE = "N";
    private final static String COM_TYPE = "KL";
    private final static String NEW_COM_TYPE = "KL_PART";

    @Override
    public CommunicationPreferences read() throws Exception {
        if (itemReader == null) {
            itemReader = new IteratorItemReader<>(getCommunicationPreferencesList());
        }
        return itemReader.read();
    }

    @Transactional(readOnly = true)
    List<CommunicationPreferences> getCommunicationPreferencesList() {

        communicationPreferencesList = communicationPreferencesRepository.findByDomainAndComTypeAndComGroupType(DOMAIN, COM_GROUP_TYPE, COM_TYPE, NEW_COM_TYPE);
        log.info("Total of comPref that will be updated: " + communicationPreferencesList.size());

        return communicationPreferencesList;
    }



}
