package com.afklm.rigui.fonetik;

public class Analyse{
	String contenu;		// Le contenu du champ  tel qu'il est lu    
	String idPart;		// Le meme phonetise
	String consIdPart;	// Le meme phonetise sans voyelle
	char tempax;		// Type de terme \0, 'i' ou 'P'
	char cId;			// Flag indicatif
	char cNP;			// Compteur de particule
	public char getCId() {
		return cId;
	}
	public void setCId(char id) {
		cId = id;
	}
	public char getCNP() {
		return cNP;
	}
	public void setCNP(char cnp) {
		cNP = cnp;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getIdPart() {
		return idPart;
	}
	public void setIdPart(String idPart) {
		this.idPart = idPart;
	}
	public char getTempax() {
		return tempax;
	}
	public void setTempax(char tempax) {
		this.tempax = tempax;
	}
	public String getConsIdPart() {
		return consIdPart;
	}
	public void setConsIdPart(String consIdPart) {
		this.consIdPart = consIdPart;
	}
	
}
