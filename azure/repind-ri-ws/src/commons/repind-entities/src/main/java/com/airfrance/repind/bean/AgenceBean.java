package com.airfrance.repind.bean;

/**
 * The type Agence bean.
 */
public class AgenceBean {

    private String gin;
    private String agenceRA2;
    private String nom;
    private String bsp;
    private String localisation;
    private String codeVilleIso;

    /**
     * Constructor
     *
     * @param gin       the gin
     * @param agenceRA2 the agence ra 2
     */
    public AgenceBean(String gin, String agenceRA2, String nom, String localisation, String bsp, String codeVilleIso) {
        this.gin = gin;
        this.agenceRA2 = agenceRA2;
        this.nom = nom;
        this.bsp = bsp;
        this.localisation = localisation;
        this.codeVilleIso = codeVilleIso;
    }

    /**
     * Get Gin
     *
     * @return gin
     */
    public String getGin() {
        return gin;
    }

    /**
     * Sets gin.
     *
     * @param gin the gin
     */
    public void setGin(String gin) {
        this.gin = gin;
    }

    /**
     * Gets agence ra 2.
     *
     * @return the agence ra 2
     */
    public String getAgenceRA2() {
        return agenceRA2;
    }

    /**
     * Sets agence ra 2.
     *
     * @param agenceRA2 the agence ra 2
     */
    public void setAgenceRA2(String agenceRA2) {
        this.agenceRA2 = agenceRA2;
    }

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the bsp
	 */
	public String getBsp() {
		return bsp;
	}

	/**
	 * @param bsp the bsp to set
	 */
	public void setBsp(String bsp) {
		this.bsp = bsp;
	}

	/**
	 * @return the localisation
	 */
	public String getLocalisation() {
		return localisation;
	}

	/**
	 * @param localisation the localisation to set
	 */
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	/**
	 * @return the codeVilleIso
	 */
	public String getCodeVilleIso() {
		return codeVilleIso;
	}

	/**
	 * @param codeVilleIso the codeVilleIso to set
	 */
	public void setCodeVilleIso(String codeVilleIso) {
		this.codeVilleIso = codeVilleIso;
	}

    
    
}
