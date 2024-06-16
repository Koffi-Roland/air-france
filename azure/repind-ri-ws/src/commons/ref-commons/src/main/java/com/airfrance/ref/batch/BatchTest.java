package com.airfrance.ref.batch;

import org.springframework.context.ApplicationContext;


/**
 * Classe mere des classes de test pour Batch.
 * @author t251684
 *
 */
public class BatchTest {
	private String currentBean;
    private static ApplicationContext context;

    /**
     * Recuperer le repertoire de la classe de test.
     * @param classTest : Classe du test.
     * @return repertoire de classe de test.
     */
    public String getDir(Class<?> classTest){
    	return classTest.getResource("").getPath().toString().replaceAll("file:/", "");
    }
    
	public void setCurrentBean(String currentBean) {
		this.currentBean = currentBean;
	}

	public String getCurrentBean() {
		return currentBean;
	}
	
    protected static ApplicationContext getContext() {
    return context;
    }

    protected static void setContext(ApplicationContext applicationContext) {
    context = applicationContext;
    }
}
