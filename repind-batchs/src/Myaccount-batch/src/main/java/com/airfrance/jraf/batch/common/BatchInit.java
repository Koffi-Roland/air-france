package com.airfrance.jraf.batch.common;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BatchInit {
    
    private ApplicationContext context;
	
    @PostConstruct
    public void init() {
    	BatchArgs.setContext(context);
    }
    
}
