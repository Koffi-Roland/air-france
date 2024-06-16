package com.airfrance.batch.prospect.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Reader for the Alimentation Prospect property configurations
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.alimentation.prospect")
public class AlimentationProspectProperty {

    /**
     * Signature
     */
    private String signature;

    /**
     * Site
     */
    private String site;

    /**
     * Report filename
     */
    private String reportFilename;

    /**
     * Output data Folder
     */
    private String reportPath;

    /**
     * Input FTP dir
     */
    private String ftpDir;

    /**
     * File extension
     */
    private String fileExt;

}
