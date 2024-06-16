package com.afklm.repind.msv.provide.last.activity.controller.ut;

import com.afklm.repind.msv.provide.last.activity.controller.LastActivityController;
import com.afklm.repind.msv.provide.last.activity.dto.LastActivityDto;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.helper.CheckerUtil;
import com.afklm.repind.msv.provide.last.activity.model.LastActivityModel;
import com.afklm.repind.msv.provide.last.activity.seed.InitLastActivityData;
import com.afklm.repind.msv.provide.last.activity.service.LastActivityService;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapper;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Unit testing presentation layer - Last activity controller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LastActivityMapperImpl.class, InitLastActivityData.class})
public class LastActivityControllerTest {

    @InjectMocks
    private LastActivityController lastActivityController;
    @Mock
    private LastActivityService lastActivityService;
    @Mock
    private LastActivityMapper lastActivityMapper;
    @Mock
    private CheckerUtil checkerUtil;

    @Autowired
    private InitLastActivityData initLastActivityDataService;

    @Autowired
    private LastActivityMapper lastActivityMapperImpl;

    @Test
    public void testGetLastActivityByGinUrl() throws Exception
    {
        // Init data
        LastActivityModel lastActivityModel = this.initLastActivityDataService.initLastActivityData();
        //Map activity model to activity
        LastActivity lastActivity = this.lastActivityMapperImpl.mapToLastActivity(lastActivityModel);
        when(lastActivityService.getLastActivityByGin(lastActivityModel.getGin())).thenReturn(lastActivity);
        // when -  action or the behaviour that we are going test
        ResponseEntity<LastActivityDto> response = lastActivityController.provideLastActivityByGin("110000038701");

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
