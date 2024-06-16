package com.airfrance.batch.common;

import com.airfrance.ref.exception.jraf.JrafDomainException;

import java.io.IOException;
import java.sql.SQLException;

public interface IBatch {
	/** marque le nom du projet pour le build ant */	
	public static final  String TYPE_EXT = "type.ext";

	/**
	 * Lance l'execution du batch
	 *
	 * @throws JrafDomainException jraf domain exception
	 * @throws IOException Input/output exception,
	 * @throws SQLException SqlException
	 * @throws Exception Exception
	 */
	public abstract void execute() throws JrafDomainException, IOException, SQLException, Exception;

}
