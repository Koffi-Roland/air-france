package com.afklm.rigui.criteria.administrator;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

public class AdministratorToolsCriteria {

	/**
	 * GIN of the individual
	 */
	@NotNull
	private String gin;

	/**
	 * Matricule of current user
	 */
	private String matricule;

	/**
	 * Constructor to create the critaria with all datas
	 *
	 * @param gin,
	 *            Gin from the individual
	 * @param matricule,
	 *            Matricule of the user
	 */
	public AdministratorToolsCriteria(String gin, String matricule) {
		this.setGin(gin);
		this.setMatricule(matricule);
	}


	/**
	 * @return the gin
	 */
	public String getGin() {
		return gin;
	}


	/**
	 * @param gin
	 *            the gin to set
	 */
	public void setGin(String gin) {
		this.gin = gin;
	}


	/**
	 * @return the matricule
	 */
	public String getMatricule() {
		return matricule;
	}


	/**
	 * @param matricule
	 *            (max 10 char length for DB) the matricule to set
	 */
	public void setMatricule(String matricule) {
		if (StringUtils.isNoneBlank(matricule) && matricule.length() > 10) {
			this.matricule = matricule.substring(0, 10);
		}
		this.matricule = matricule;
	}


}
