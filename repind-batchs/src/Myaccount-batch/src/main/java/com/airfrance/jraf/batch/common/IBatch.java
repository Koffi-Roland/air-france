package com.airfrance.jraf.batch.common;

import com.airfrance.ref.exception.jraf.JrafDomainException;

import java.io.IOException;

public interface IBatch {
	/** marque le nom du projet pour le build ant */	
	public static final  String TYPE_EXT = "type.ext";

	/**
	 * Lance l'execution du batch
	 * @throws JrafEnterpriseException
	 */
	public abstract void execute() throws JrafDomainException, IOException;
	
}
