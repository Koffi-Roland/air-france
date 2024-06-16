package com.afklm.cati.common.model;

import lombok.Builder;

@Builder
public class ModelPays {

	private String codePays;
	private String normalisable;
	private String libellePays;
	private Integer codeIata; 
	private String libellePaysEn;
	private String codeGestionCP;
	private String codeCapitale;
	private String formatAdr;
	private Integer iformatAdr;
	private String forcage;
	private String iso3code;
	
	public ModelPays() {}
	
	public ModelPays(String codePays, String normalisable, String libellePays, Integer codeIata,
			String libellePaysEn, String codeGestionCP, String codeCapitale, String formatAdr, Integer iformatAdr,
			String forcage, String iso3code) {
		this.codePays = codePays;
		this.normalisable = normalisable;
		this.libellePays = libellePays;
		this.codeIata = codeIata;
		this.libellePaysEn = libellePaysEn;
		this.codeGestionCP = codeGestionCP;
		this.codeCapitale = codeCapitale;
		this.formatAdr = formatAdr;
		this.iformatAdr = iformatAdr;
		this.forcage = forcage;
		this.iso3code = iso3code;
	}
	
	public String getLibellePays() {
		return libellePays;
	}
	public void setLibellePays(String libellePays) {
		this.libellePays = libellePays;
	}
	public Integer getCodeIata() {
		return codeIata;
	}
	public void setCodeIata(Integer codeIata) {
		this.codeIata = codeIata;
	}
	public String getLibellePaysEn() {
		return libellePaysEn;
	}
	public void setLibellePaysEn(String libellePaysEn) {
		this.libellePaysEn = libellePaysEn;
	}
	public String getCodeGestionCP() {
		return codeGestionCP;
	}
	public void setCodeGestionCP(String codeGestionCP) {
		this.codeGestionCP = codeGestionCP;
	}
	public String getCodeCapitale() {
		return codeCapitale;
	}
	public void setCodeCapitale(String codeCapitale) {
		this.codeCapitale = codeCapitale;
	}
	public String getFormatAdr() {
		return formatAdr;
	}
	public void setFormatAdr(String formatAdr) {
		this.formatAdr = formatAdr;
	}
	public Integer getIformatAdr() {
		return iformatAdr;
	}
	public void setIformatAdr(Integer iformatAdr) {
		this.iformatAdr = iformatAdr;
	}
	public String getForcage() {
		return forcage;
	}
	public void setForcage(String forcage) {
		this.forcage = forcage;
	}
	public String getIso3code() {
		return iso3code;
	}
	public void setIso3code(String iso3code) {
		this.iso3code = iso3code;
	}
	public String getCodePays() {
		return codePays;
	}
	public void setCodePays(String codePays) {
		this.codePays = codePays;
	}
	public String getNormalisable() {
		return normalisable;
	}
	public void setNormalisable(String normalisable) {
		this.normalisable = normalisable;
	}
	
}
