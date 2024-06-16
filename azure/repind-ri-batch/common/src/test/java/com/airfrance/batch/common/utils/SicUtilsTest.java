package com.airfrance.batch.common.utils;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;

import static com.airfrance.batch.common.utils.IConstants.DATE_FORMATTER_DDMMYY;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class SicUtilsTest {

    @Test
    public void encodeDate() {
        Date date = SicUtils.encodeDate(DATE_FORMATTER_DDMMYY, "01/01/1999");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(1999, cal.get(Calendar.YEAR));
    }

    @Test
    public void encodeCin() {
        assertEquals("000123456789", SicUtils.encodeCin("0123456789"));
    }

    @Test
    public void encodeStatus() {
        assertEquals("Y", SicUtils.encodeStatus("ACTIVE"));
        assertEquals("N", SicUtils.encodeStatus("INACTIVE"));
    }

    @Test
    public void encodeSubscription() {
        assertEquals("Y", SicUtils.encodeSubscription("1"));
        assertEquals("N", SicUtils.encodeSubscription("0"));
    }

    @Test
    public void subString() {
        assertEquals("My", SicUtils.subString("MySubstring", 2));
        assertEquals("MySubstring", SicUtils.subString("MySubstring", 50));
    }

    @Test
    public void encodeCreationSiteDate() {
        assertEquals("BATCH_QVI", SicUtils.encodeCreationSiteDate(""));
        assertEquals("ALONGSTRIN", SicUtils.encodeCreationSiteDate("ALONGSTRINGOFCHARACTERS"));
    }


}
