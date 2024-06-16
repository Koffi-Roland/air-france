package com.afklm.cati.common.repository.custom;

import javax.validation.constraints.NotNull;

public interface RefComPrefDgtRepositoryCustom {

    /**
     * Gets the count of communication preferences available for the specified
     * combination
     *
     * @param domain    the domain
     * @param groupType the group type
     * @param type      the type
     * @return the count of communication preferences available for the specified
     * combination
     */
    int countByDomainGroupAndType(@NotNull String domain, @NotNull String groupType, @NotNull String type);
}
