package com.airfrance.batch.templatespringbatch.writer;

import com.airfrance.batch.templatespringbatch.model.OutputModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class TemplateSpringBatchWriter implements ItemWriter<OutputModel> {
    @Override
    public void write(List<? extends OutputModel> list) throws Exception {
        if (!CollectionUtils.isEmpty(list)) {
            //do something
        }
    }
}
