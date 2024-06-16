package com.afklm.repind.msv.provide.last.activity.transform;

import com.afklm.repind.msv.provide.last.activity.dto.LastActivityDto;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.model.LastActivityModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Last Activity Mapper Interface. We don't need to create an implementation class because map struct  creates it for us
 */
@Mapper(componentModel = "spring")
public interface LastActivityMapper {
    LastActivityMapper INSTANCE = Mappers.getMapper( LastActivityMapper.class );

    LastActivityDto mapToLastActivityDto(LastActivity lastActivity);
    LastActivity mapToLastActivity(LastActivityModel lastActivityModel);

}
