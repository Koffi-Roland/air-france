package com.afklm.spring.security.habile.properties;

import java.util.List;

/**
 *  Custom Url/Http Verbs/Authorities properties.<br/>
 *  To grant access to a set of Habile profiles to specific URL patterns for specific HTTP verbs.
 */
public class UrlsHttpVerbsAuthorities {
    /**
     * List of URLs covered by this configuration. Must end with <b>&#47;&#42;&#42;</b>
     */
    private List<String> urls;
    /**
     * For each list of url patterns, we can define a list of http verbs for which users with 
     * only specific permissions will have access
     */
    private List<HttpVerbsAuthorities> httpVerbsAuthorities;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<HttpVerbsAuthorities> getHttpVerbsAuthorities() {
        return httpVerbsAuthorities;
    }

    public void setHttpVerbsAuthorities(List<HttpVerbsAuthorities> httpVerbsAuthorities) {
        this.httpVerbsAuthorities = httpVerbsAuthorities;
    }
}
