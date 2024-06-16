package com.airfrance.batch.compref.injestadhocdata.property;

import com.airfrance.batch.compref.injestadhocdata.enums.SiteEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.inject.adhoc.data")
public class InjestAdhocDataPropoerty {

	private Map<SiteEnum, String> site;
	private String creationContext;
	private String channel;
	private String outputFileExtension;
}
