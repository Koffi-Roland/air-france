package com.afklm.batch.injestadhocdata.property;

import com.afklm.batch.injestadhocdata.enums.SiteEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
