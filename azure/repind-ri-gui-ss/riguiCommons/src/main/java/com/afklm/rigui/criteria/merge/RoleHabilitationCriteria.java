package com.afklm.rigui.criteria.merge;

/**
 * Class to get Habilitations of the user who call a request. For now it's use
 * only for Merge, must be refactor in generic way if needed.
 * 
 * @author m421262
 *
 */
public class RoleHabilitationCriteria {

	/**
	 * The user that call the method has FBRole
	 */
	private boolean hasFBRole;

	/**
	 * The user that call the method has AdminRole
	 */
	private boolean hasAdminRole;

	/**
	 * The user that call the method has GPRole
	 */
	private boolean hasGPRole;

	/**
	 * The user that call the method has FBRoleMinor
	 */
	private boolean hasFBRoleMinor;

	/**
	 * Constructor by default that init all with FALSE
	 */
	public RoleHabilitationCriteria() {
		this.hasFBRole = false;
		this.hasAdminRole = false;
		this.hasGPRole = false;
		this.hasFBRoleMinor = false;
	}

	public RoleHabilitationCriteria(boolean hasFBRole, boolean hasAdminRole, boolean hasGPRole,
			boolean hasFBRoleMinor) {
		this.hasFBRole = hasFBRole;
		this.hasAdminRole = hasAdminRole;
		this.hasGPRole = hasGPRole;
		this.hasFBRoleMinor = hasFBRoleMinor;
	}

	/**
	 * @return the hasFBRole
	 */
	public boolean isHasFBRole() {
		return hasFBRole;
	}

	/**
	 * @param hasFBRole
	 *            the hasFBRole to set
	 */
	public void setHasFBRole(boolean hasFBRole) {
		this.hasFBRole = hasFBRole;
	}

	/**
	 * @return the hasAdminRole
	 */
	public boolean isHasAdminRole() {
		return hasAdminRole;
	}

	/**
	 * @param hasAdminRole
	 *            the hasAdminRole to set
	 */
	public void setHasAdminRole(boolean hasAdminRole) {
		this.hasAdminRole = hasAdminRole;
	}

	/**
	 * @return the hasGPRole
	 */
	public boolean isHasGPRole() {
		return hasGPRole;
	}

	/**
	 * @param hasGPRole
	 *            the hasGPRole to set
	 */
	public void setHasGPRole(boolean hasGPRole) {
		this.hasGPRole = hasGPRole;
	}

	/**
	 * @return the hasFBRoleMinor
	 */
	public boolean isHasFBRoleMinor() {
		return hasFBRoleMinor;
	}

	/**
	 * @param hasFBRoleMinor
	 *            the hasFBRoleMinor to set
	 */
	public void setHasFBRoleMinor(boolean hasFBRoleMinor) {
		this.hasFBRoleMinor = hasFBRoleMinor;
	}

}
