package com.afklm.repind.msv.provide.last.activity.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.helper.CheckerUtil;
import com.afklm.repind.msv.provide.last.activity.model.LastActivityModel;
import com.afklm.repind.msv.provide.last.activity.repository.LastActivityRepository;
import com.afklm.repind.msv.provide.last.activity.seed.InitLastActivityData;
import com.afklm.repind.msv.provide.last.activity.service.impl.LastActivityServiceImpl;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapper;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

/**
 * Unit testing service layer - Last activity service
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={LastActivityMapperImpl.class})
public class LastActivityServiceTest {

    @Mock
    private LastActivityRepository lastActivityRepository;
    @InjectMocks
    private LastActivityServiceImpl lastActivityService;

    @Autowired
    private LastActivityMapper lastActivityMapper;

    @Mock
    private CheckerUtil checkerUtil;

    @InjectMocks
    private InitLastActivityData initLastActivityDataService;

    private LastActivityModel lastActivityModel;

    @BeforeEach
    public void setup()
    {
       this.lastActivityModel = this.initLastActivityDataService.initLastActivityData();
    }

    @DisplayName("JUnit test for get last activity method")
    @Test
    public void testGetLastActivityByGin() throws BusinessException
    {
       // given
        given(lastActivityRepository.findByGin(this.lastActivityModel.getGin())).willReturn(Optional.of(this.lastActivityMapper.mapToLastActivity(this.lastActivityModel)));
        //When
       LastActivity   lastActivity = this.lastActivityService.getLastActivityByGin(this.lastActivityModel.getGin());

        // then - verify the output
        assertThat(lastActivity).isNotNull();

    }

}
