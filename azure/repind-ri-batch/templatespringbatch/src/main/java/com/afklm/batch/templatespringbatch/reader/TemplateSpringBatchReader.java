package com.afklm.batch.templatespringbatch.reader;

import com.afklm.batch.templatespringbatch.model.InputModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TemplateSpringBatchReader implements ItemReader<InputModel> {
    @Override
    public InputModel read() throws Exception{
        return null;
    }
}
