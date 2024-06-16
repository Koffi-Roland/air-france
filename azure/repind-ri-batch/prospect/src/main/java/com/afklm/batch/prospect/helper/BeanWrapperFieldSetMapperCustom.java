package com.afklm.batch.prospect.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class BeanWrapperFieldSetMapperCustom <T> extends BeanWrapperFieldSetMapper<T> {

    private static final String DATE_DDMMYYYY = "dd/MM/yyyy";

    @Override
    protected void initBinder(DataBinder binder) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(DATE_DDMMYYYY)
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);

        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String input) throws IllegalArgumentException {
                if (StringUtils.isNotEmpty(input)) {
                    setValue(LocalDate.parse(input, formatter));
                } else {
                    setValue(null);
                }
            }

            @Override
            public String getAsText() throws IllegalArgumentException {
                Object date = getValue();
                if (date != null) {
                    return formatter.format((LocalDate) date);
                } else {
                    return "";
                }
            }
        });
    }
}
