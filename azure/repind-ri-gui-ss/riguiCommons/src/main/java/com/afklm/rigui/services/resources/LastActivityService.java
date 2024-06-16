package com.afklm.rigui.services.resources;

import com.afklm.rigui.dao.lastactivity.LastActivityRepository;
import com.afklm.rigui.dto.lastactivity.LastActivityDTO;
import com.afklm.rigui.entity.lastactivity.LastActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Last activity service
 */
@Service
@RequiredArgsConstructor
public class LastActivityService {
    /**
     * Last activity repository - inject by spring
     */
    private final LastActivityRepository lastActivityRepository;

    /**
     * Get last activity by gin
     * @param gin individual number
     * @return LastActivityDTO last activity data transfer objetct
     */
    public LastActivityDTO getLastActivityByGin(String gin)
    {
        LastActivityDTO lastActivityDTO = null;
        Optional<LastActivity> lastActivityOpt = this.lastActivityRepository.findByGin(gin);
        if(lastActivityOpt.isPresent())
        {
            lastActivityDTO = this.mapToLastActivityDto(lastActivityOpt.get());
        }
        return lastActivityDTO;

    }

    /**
     * Map last activity to dto
     * @param lastActivity last activity
     * @return LastActivityDTO
     */
    private LastActivityDTO mapToLastActivityDto(LastActivity lastActivity)
    {
        LastActivityDTO lastActivityDTO =  new LastActivityDTO();
        lastActivityDTO.setGin(lastActivity.getGin());
        lastActivityDTO.setSignatureModification(lastActivity.getSignatureModification());
        lastActivityDTO.setSourceModification(lastActivity.getSourceModification());
        lastActivityDTO.setSiteModification(lastActivity.getSiteModification());
        lastActivityDTO.setDateModification(lastActivity.getDateModification());
        return lastActivityDTO;
    }
}
