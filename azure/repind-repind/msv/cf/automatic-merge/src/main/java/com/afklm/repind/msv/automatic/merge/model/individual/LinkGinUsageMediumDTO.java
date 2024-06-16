package com.afklm.repind.msv.automatic.merge.model.individual;

import com.afklm.repind.common.entity.contact.UsageMedium;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LinkGinUsageMediumDTO {
    private String gin;
    private UsageMedium usage;
}
