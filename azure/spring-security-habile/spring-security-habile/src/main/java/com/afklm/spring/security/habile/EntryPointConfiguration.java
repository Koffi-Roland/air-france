package com.afklm.spring.security.habile;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * This is the entry point of the spring-security-habile configuration automatically
 * loaded by spring. It scans all our packages in order to load all
 * the habile necessary configuration without any action on client application (no scan
 * or import needed).
 * 
 * @author TECC
 *
 */
@AutoConfiguration("habileEntryPointConfiguration")
@ComponentScan(basePackages = EntryPointConfiguration.SS4H_BASE_PACKAGE,
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = EntryPointConfiguration.SS4H_PROXY_SUBPACKAGES))
public class EntryPointConfiguration {

    static final String SS4H_BASE_PACKAGE = "com.afklm.spring.security.habile";
    static final String SS4H_PROXY_SUBPACKAGES = SS4H_BASE_PACKAGE + ".proxy.*";

}
