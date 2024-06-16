package com.airfrance.batch.common;

public interface IBatch {
	/** marque le nom du projet pour le build ant */	
	public final static String TYPE_EXT = "type.ext";

	public abstract void execute() throws Exception;

}