package com.airfrance.repind.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SingletonSpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;
    
    public SingletonSpringApplicationContext(){
    	
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT = context;
 	}
        
 

    public static Object getBean(String beanName) {
        return (CONTEXT != null) ? CONTEXT.getBean(beanName) : null;
    }
    
}
