package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.model.RcPhysicalDelete;
import com.airfrance.batch.purgemyaccount.service.RoleContratService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class RcPhysicalDeleteWriter implements ItemWriter<RcPhysicalDelete> {

    @Autowired
    private RoleContratService roleContratService;


    /**
     * Write process for delete physically rile_contrat and associated business_role
     *
     * @param rcPhysicalDeleteItems roleContrat model
     * @throws Exception exception
     */
    @Override
    public void write(@NotNull List<? extends RcPhysicalDelete> rcPhysicalDeleteItems) throws Exception {
        if (!CollectionUtils.isEmpty(rcPhysicalDeleteItems)) {
            rcPhysicalDeleteItems.forEach(item -> {
                this.roleContratService.physicalDeleteRoleContrat(item.getIcleRole());
            });
        }
        else
        {
            log.error("The list of data is empty.");
            throw new IllegalArgumentException("The list of data is empty...");
        }
    }
}