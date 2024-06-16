package com.airfrance.batch.cleanTravelDoc.writer;

import com.airfrance.batch.cleanTravelDoc.model.CleanTravelDocModel;
import com.airfrance.repind.dao.preference.PreferenceDataRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class CleanTravelDocWriter implements ItemWriter<CleanTravelDocModel> {

    @Autowired
    protected PreferenceRepository preferenceRepository;

    @Autowired
    protected PreferenceDataRepository preferenceDataRepository;

    @Override
    @Transactional
    public void write(List<? extends CleanTravelDocModel> list) throws Exception {
        List<Long> prefToDelete = list.stream().map(CleanTravelDocModel::getPreferenceId).toList();
        //TODO: Reactivate logs once the first big cleaning is done
        /*log.info("Deleting {} preferences with the following ids : {}",
                prefToDelete.size(),
                String.join(",", prefToDelete.stream().map(Object::toString).toList()));*/
        try {
            preferenceDataRepository.deleteByPreferenceIdIn(prefToDelete);
            preferenceRepository.deleteByPreferenceIdIn(prefToDelete);
            //log.info("The preferences was successfully deleted");
        } catch (Exception e) {
            log.error("An error occurred during the deletion of the preferences");
            throw e;
        }
    }
}
