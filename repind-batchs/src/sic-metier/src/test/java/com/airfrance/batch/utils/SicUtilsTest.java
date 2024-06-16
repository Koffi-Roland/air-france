package com.airfrance.batch.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SicUtilsTest {

    @Test
    public void encodeDateSuccessTest(){
        Date date = SicUtils.encodeDate(IConstants.DATE_FORMATTER, "02/02/2020");
        assertNotNull(date);
    }

    @Test
    public void encodeDateTimeSuccessTest(){
        Date date = SicUtils.encodeDate(IConstants.DATETIME_FORMATTER, "02/02/2020 00:00");
        assertNotNull(date);
        assertEquals(1580598000000L , date.getTime());

    }

    @Test
    public void encodeDateNullTest(){
        Date date = SicUtils.encodeDate(IConstants.DATE_FORMATTER, null);
        assertNull(date);
    }

    @Test
    public void encodeDateEmptyTest(){
        Date date = SicUtils.encodeDate(IConstants.DATE_FORMATTER, "");
        assertNull(date);
    }

    @Test
    public void encodeStatusSuccessTest(){
        String status = SicUtils.encodeStatus("active");
        assertEquals("Y" , status);
        status = SicUtils.encodeStatus("ACTIVE");
        assertEquals("Y" , status);
        status = SicUtils.encodeStatus("AcTiVE");
        assertEquals("Y" , status);
        status = SicUtils.encodeStatus("unsubscribed");
        assertEquals("N" , status);
    }

    @Test
    public void encodeStatusNullTest(){
        String status = SicUtils.encodeStatus(null);
        assertEquals("N" , status);
    }

    @Test
    public void encodeCinSuccessTest(){
        String cin = "0000000000";
        String cinEncoded = SicUtils.encodeCin(cin);
        assertEquals("00"+cin, cinEncoded);

        cin = "120000000000";
        cinEncoded = SicUtils.encodeCin(cin);
        assertEquals(cin, cinEncoded);
    }

    @Test
    public void encodeCinNullTest(){
        String cinEncoded = SicUtils.encodeCin(null);
        assertEquals(null, cinEncoded);
    }
}
