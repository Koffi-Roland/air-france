package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.model.AcPhysicalDelete;
import com.airfrance.batch.purgemyaccount.service.AccountDataService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class AcPhysicalDeleteWriter implements ItemWriter<AcPhysicalDelete> {

    @Autowired
    private AccountDataService accountDataService;


    /**
     * Write process for delete physically account data
     *
     * @param acPhysicalDeleteItems communication preference model
     * @throws Exception exception
     */
    @Override
    public void write(@NotNull List<? extends AcPhysicalDelete> acPhysicalDeleteItems) throws Exception {
        if (!CollectionUtils.isEmpty(acPhysicalDeleteItems)) {
            acPhysicalDeleteItems.forEach(item -> this.accountDataService.physicalDeleteAccountData(item.getId()));
        }
        else{
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }
}