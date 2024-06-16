package com.afklm.rigui.criteria.merge;

import javax.validation.constraints.NotNull;

/**
 * Criteria used when a provide merge is called. Contains all information needed
 * for the service. A trim is done on each identifier when created.
 *
 * @author m421262
 *
 */
public class MergeProvideCriteria {

	/**
	 * First identifier (GIN or CIN) of the Individual for merge
	 */
	@NotNull
	private String identifiant1;

	/**
	 * Second identifier (GIN or CIN) of the Individual for merge
	 */
	@NotNull
	private String identifiant2;

	/**
	 * Habilitations of the user who call the API
	 */
	@NotNull
	private RoleHabilitationCriteria roleHabilitations;

	@NotNull
	private boolean forceSwitchIndividual = false;

	/**
	 * Constructor by default, a trim is done on each identifiant
	 *
	 * @param identifiant1,
	 *            GIN or CIN 1
	 * @param identifiant2,
	 *            GIN or CIN 2 The boolean has by default False ! (no
	 *            habilitation)
	 */
	public MergeProvideCriteria(String identifiant1, String identifiant2) {
		this.setIdentifiant1(identifiant1);
		this.setIdentifiant2(identifiant2);
		this.setRoleHabilitations(new RoleHabilitationCriteria());
	}

	/**
	 * Constructor to create the complete
	 *
	 * @param identifiant1,
	 *            GIN or CIN 1
	 * @param identifiant2,
	 *            GIN or CIN 2
	 * @param roleHabilitations,
	 *            Habilitations of the user who call the API
	 */
	public MergeProvideCriteria(String identifiant1, String identifiant2, RoleHabilitationCriteria roleHabilitations) {
		this.setIdentifiant1(identifiant1);
		this.setIdentifiant2(identifiant2);
		this.setRoleHabilitations(roleHabilitations);
	}

	/**
	 * Constructor to create the complete
	 *
	 * @param identifiant1,
	 *            GIN or CIN 1
	 * @param identifiant2,
	 *            GIN or CIN 2
	 * @param roleHabilitations,
	 *            Habilitations of the user who call the API
	 * @param forceSwitchIndividual,
	 *            Bollean that indicate if the switch between individual must be
	 *            forced
	 */
	public MergeProvideCriteria(String identifiant1, String identifiant2, RoleHabilitationCriteria roleHabilitations,
								boolean forceSwitchIndividual) {
		this.setIdentifiant1(identifiant1);
		this.setIdentifiant2(identifiant2);
		this.setRoleHabilitations(roleHabilitations);
		this.setForceSwitchIndividual(forceSwitchIndividual);
	}

	/**
	 * @return the identifiant1
	 */
	public String getIdentifiant1() {
		return identifiant1;
	}

	/**
	 * @param identifiant1
	 *            the identifiant1 to set, do a trim
	 */
	public void setIdentifiant1(String identifiant1) {
		this.identifiant1 = identifiant1.trim();
	}

	/**
	 * @return the identifiant2
	 */
	public String getIdentifiant2() {
		return identifiant2;
	}

	/**
	 * @param identifiant2
	 *            the identifiant2 to set, do a trim
	 */
	public void setIdentifiant2(String identifiant2) {
		this.identifiant2 = identifiant2.trim();
	}

	/**
	 * @return the roleHabilitations
	 */
	public RoleHabilitationCriteria getRoleHabilitations() {
		return roleHabilitations;
	}

	/**
	 * @param roleHabilitations
	 *            the roleHabilitations to set
	 */
	public void setRoleHabilitations(RoleHabilitationCriteria roleHabilitations) {
		this.roleHabilitations = roleHabilitations;
	}

	/**
	 * @return the forceSwitchIndividual
	 */
	public boolean isForceSwitchIndividual() {
		return forceSwitchIndividual;
	}

	/**
	 * @param forceSwitchIndividual
	 *            the forceSwitchIndividual to set
	 */
	public void setForceSwitchIndividual(boolean forceSwitchIndividual) {
		this.forceSwitchIndividual = forceSwitchIndividual;
	}

}
