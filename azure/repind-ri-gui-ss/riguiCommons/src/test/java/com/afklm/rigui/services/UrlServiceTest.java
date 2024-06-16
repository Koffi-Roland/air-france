package com.afklm.rigui.services;

import com.afklm.rigui.services.helper.UrlHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlHelper urlHelper;

    @Test
    public void extractResourceId() {
        when(urlHelper.removeSpacesOfUrl(" 12345")).thenReturn("12345");
        assertEquals("12345", urlService.extractResourceId("A/B/ 12345"));
    }


}
