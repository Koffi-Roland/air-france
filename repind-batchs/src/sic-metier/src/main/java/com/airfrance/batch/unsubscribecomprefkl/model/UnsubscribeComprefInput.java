package com.airfrance.batch.unsubscribecomprefkl.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class UnsubscribeComprefInput {
    /**
     * ACTION_INDEX
     * mandatory = Yes
     */
    private String actionIndex;
    /**
     * DOMAIN_COMPREF_INDEX
     * mandatory = Yes
     */
    private String domainComprefIndex;
    /**
     * COMGROUPTYPE_COMPREF_INDEX
     * mandatory = Yes
     */
    private String comGroupTypeComprefIndex;
    /**
     * COMTYPE_COMPREF_INDEX
     * mandatory = Yes
     */
    private String comTypeComprefIndex;
    /**
     * GIN_INDEX
     * mandatory = Yes
     */
    private String ginIndex;
    /**
     * MARKET_COMPREF_INDEX
     * mandatory = No
     */
    private String marketComprefIndex;
    /**
     * LANGUAGE_COMPREF_INDEX
     * mandatory = No
     */
    private String languageComprefIndex;
    /**
     * CAUSE_INDEX
     * mandatory = Yes
     */
    private String causeIndex;

}