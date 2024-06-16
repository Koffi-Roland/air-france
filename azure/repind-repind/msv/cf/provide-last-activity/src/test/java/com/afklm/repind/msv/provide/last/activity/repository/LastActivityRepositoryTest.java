package com.afklm.repind.msv.provide.last.activity.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.model.LastActivityModel;
import com.afklm.repind.msv.provide.last.activity.seed.InitLastActivityData;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapper;
import com.afklm.repind.msv.provide.last.activity.transform.LastActivityMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

/**
 * Unit testing repository layer - Last activity repository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes={LastActivityMapperImpl.class,InitLastActivityData.class,LastActivity.class})
@EntityScan(basePackageClasses = {
        LastActivity.class,
})
@EnableJpaRepositories(basePackageClasses = {
        LastActivityRepository.class,
})
public class LastActivityRepositoryTest {

    @Autowired
    private LastActivityRepository lastActivityRepository;


    @Autowired
    private LastActivityMapper lastActivityMapper;

    @Autowired
    private InitLastActivityData initLastActivityDataService;
    private LastActivityModel lastActivityModel;


    @BeforeEach
    public void setup() {
        //given - precondition or setup
        this.lastActivityModel = this.initLastActivityDataService.initLastActivityData();
    }

    @DisplayName("JUnit test for save activity")
    @Test
    public void testSaveLastActivity() {
        // when - action or the behaviour that we are going test
        LastActivity lastActivity = this.lastActivityRepository.save(this.lastActivityMapper.mapToLastActivity(this.lastActivityModel));
        // when - action or the behaviour that we are going test
        assertThat(lastActivity).isNotNull();
        assertThat(lastActivity.getId()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for get last activity according to gin")
    @Test
    public void testGetLastActivityByGin() {
        this.lastActivityRepository.save(this.lastActivityMapper.mapToLastActivity(this.lastActivityModel));
        LastActivity lastActivity = null;
        Optional<LastActivity> optionalLastActivity = this.lastActivityRepository.findByGin(this.lastActivityModel.getGin());
        //check if value is present
        if (optionalLastActivity.isPresent())
        {
            lastActivity = optionalLastActivity.get();
        }
        assertThat(lastActivity).isNotNull();
    }

}
