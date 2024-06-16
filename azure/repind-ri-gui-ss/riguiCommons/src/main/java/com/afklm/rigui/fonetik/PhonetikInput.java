package com.afklm.rigui.fonetik;

public class PhonetikInput {
	protected String ident;		// terme a phonetiser
	protected String indict;	// terme phonetise
	protected String indCons;	// terme phonetise sans les voyelles
	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getIndCons() {
		return indCons;
	}
	public void setIndCons(String indCons) {
		this.indCons = indCons;
	}
	public String getIndict() {
		return indict;
	}
	public void setIndict(String indict) {
		this.indict = indict;
	}
}
