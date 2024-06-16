package com.afklm.repind.msv.mapping.services.it;


import com.afklm.repind.msv.mapping.services.builder.DateHelper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DateHelperTest {

    @Test
    void convertDateToUTCTest() throws ParseException {

        final DateHelper dateHelper = new DateHelper();

        String dateString = "15/10/2022 14:44";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        Date dateToConvert = formatter.parse(dateString);

        Date resultDate = dateHelper.convertDateToUTC(dateToConvert);

        // Dates in LocalDateTime to have the characteristics
        LocalDateTime localDateToConvert = dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localDateResult = resultDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


        Assert.assertEquals(15, localDateToConvert.getDayOfMonth());
        Assert.assertEquals(15, localDateResult.getDayOfMonth());
        Assert.assertEquals("OCTOBER", localDateToConvert.getMonth().toString());
        Assert.assertEquals("OCTOBER", localDateResult.getMonth().toString());
        Assert.assertEquals(2022, localDateToConvert.getYear());
        Assert.assertEquals(2022, localDateResult.getYear());
    }
}
