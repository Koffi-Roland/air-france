package com.afklm.batch.templatespringbatch.processor;

import com.afklm.batch.templatespringbatch.model.InputModel;
import com.afklm.batch.templatespringbatch.model.OutputModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TemplateSpringBatchProcessor implements ItemProcessor<InputModel, OutputModel> {
    @Override
    public OutputModel process(InputModel inputModel) throws Exception {
        return null;
    }
}
