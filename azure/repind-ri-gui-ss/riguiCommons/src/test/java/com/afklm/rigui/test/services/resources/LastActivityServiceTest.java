package com.afklm.rigui.test.services.resources;

import com.afklm.rigui.dao.lastactivity.LastActivityRepository;
import com.afklm.rigui.dto.lastactivity.LastActivityDTO;
import com.afklm.rigui.entity.lastactivity.LastActivity;
import com.afklm.rigui.services.resources.LastActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Unit testing service layer - Last activity service
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class LastActivityServiceTest {

    /**
     * Gin : Individual number
     */
    private static final String GIN = "110000038701";
    /**
     * Table source where modification originated
     */
    private static final String TABLE_MODIFICATION = "INDIVIDUS";
    /**
     * Signature modification
     */
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    /**
     * Signature site
     */
    private static final String MODIFICATION_SITE = "KLM";

    @Mock
    private LastActivityRepository lastActivityRepository;
    @InjectMocks
    private LastActivityService lastActivityService;
    private LastActivity lastActivity;
    @BeforeEach
    public void setup()
    {
        LastActivity initlastActivity = new LastActivity();
        Date date = Date.from(LocalDate.of(2013, 12, 6).atStartOfDay(ZoneId.systemDefault()).toInstant());
        initlastActivity.setGin(GIN);
        initlastActivity.setDateModification(date);
        initlastActivity.setSourceModification(TABLE_MODIFICATION);
        initlastActivity.setSiteModification(MODIFICATION_SITE);
        initlastActivity.setSignatureModification(SIGNATURE_MODIFICATION);
        this.lastActivity = initlastActivity;
    }

    @DisplayName("JUnit test for get last activity method")
    @Test
    public void testGetLastActivityByGin()
    {
        // given
        given(lastActivityRepository.findByGin(GIN)).willReturn(Optional.of(this.lastActivity));
        //When
        LastActivityDTO lastActivityDto = this.lastActivityService.getLastActivityByGin(GIN);
        // then - verify the output
        assertThat(lastActivityDto).isNotNull();
        //Then
        assertAll(
                () -> assertEquals(lastActivityDto.getGin(), GIN),
                () -> assertEquals(lastActivityDto.getSourceModification(), TABLE_MODIFICATION),
                () -> assertEquals(lastActivityDto.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(lastActivityDto.getSiteModification(), MODIFICATION_SITE)
        );

    }

}
