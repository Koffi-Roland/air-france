package com.airfrance.jraf.batch.common;

public class BatchCommon {

    private final static String TYPE_EXT = "type.ext";

    /**
     * Recuperer le repertoire de la classe de test.
     * @param classTest : Classe du test.
     * @return repertoire de classe de test.
     */
    public static String getDir(Class<?> classTest){
    	return classTest.getResource("").getPath().toString().replaceAll("file:/", "");
    }
    
}
