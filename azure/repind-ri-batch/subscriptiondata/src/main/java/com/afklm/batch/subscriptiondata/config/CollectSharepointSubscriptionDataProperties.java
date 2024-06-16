package com.afklm.batch.subscriptiondata.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "sharepoint-client")
@PropertySource({"classpath:/collect-sharepoint-subscription-data.properties", "classpath:/local.properties"})
public class CollectSharepointSubscriptionDataProperties {

    private String sharePointUrl;

    public String getSharePointUrl() {
        return sharePointUrl;
    }
    public void setSharePointUrl(String sharePointUrl) {
        this.sharePointUrl = sharePointUrl;
    }
}
