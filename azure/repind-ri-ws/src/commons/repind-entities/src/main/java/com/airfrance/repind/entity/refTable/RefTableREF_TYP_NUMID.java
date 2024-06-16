package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_NUMID extends RefTableMere {


	private static RefTableREF_TYP_NUMID m_RefTableREF_TYP_NUMID = null;

	public static final String _REF_AG = "AG";
	public static final String _REF_AN = "AN";
	public static final String _REF_AR = "AR";
	public static final String _REF_AT = "AT";
	public static final String _REF_BI = "BI";
	public static final String _REF_DU = "DU";
	public static final String _REF_EA = "EA";
	public static final String _REF_EN = "EN";
	public static final String _REF_IA = "IA";
	public static final String _REF_IV = "IV";
	public static final String _REF_KV = "KV";
	public static final String _REF_SI = "SI";
	public static final String _REF_SR = "SR";
	public static final String _REF_TA = "TA";
	public static final String _REF_TV = "TV";
	public static final String _REF_UC = "UC";
	public static final String _REF_TC = "TC";
	public static final String _REF_TI = "TI";


	private RefTableREF_TYP_NUMID() {

		m_lstChamps.add(new RefTableChamp(_REF_AG, "AGENCE NON AGREE", "AGENCY WITHOUT AGREEMENT"));
		m_lstChamps.add(new RefTableChamp(_REF_AN, "AGENCE ANCIEN NUMERO", "FORMER AGENCY NUMBER"));
		m_lstChamps.add(new RefTableChamp(_REF_AR, "AGENCE ARC", "ARC AGENCY"));
		m_lstChamps.add(new RefTableChamp(_REF_AT, "AGENCE ATAF", "ATAF AGENCY"));
		m_lstChamps.add(new RefTableChamp(_REF_BI, "CODE AGENCE KLM", "KLM AGENT CODE"));
		m_lstChamps.add(new RefTableChamp(_REF_DU, "DUNS", "DUNS"));
		m_lstChamps.add(new RefTableChamp(_REF_EA, "EAN", "EAN"));
		m_lstChamps.add(new RefTableChamp(_REF_EN, "ENREGISTREMENT", "REGISTRATION"));
		m_lstChamps.add(new RefTableChamp(_REF_IA, "AGENCE IATA", "IATA AGENCY"));
		m_lstChamps.add(new RefTableChamp(_REF_IV, "PARTITA IVA", "PARTITA IVA"));
		m_lstChamps.add(new RefTableChamp(_REF_KV, "KVK NUMBER", "KVK NUMBER"));
		m_lstChamps.add(new RefTableChamp(_REF_SI, "SIRET", "SIRET"));
		m_lstChamps.add(new RefTableChamp(_REF_SR, "SIREN", "SIREN"));
		m_lstChamps.add(new RefTableChamp(_REF_TA, "TOUS NUMEROS AGENCE", "ALL AGENCY NUMBERS"));
		m_lstChamps.add(new RefTableChamp(_REF_TV, "TVA", "VAT"));
		m_lstChamps.add(new RefTableChamp(_REF_UC, "UCC CODE", "UCC CODE"));
		m_lstChamps.add(new RefTableChamp(_REF_TC, "TVA INTRACOMMUNAUTAIRE", "INTRA COMMUNITY VAT"));
		m_lstChamps.add(new RefTableChamp(_REF_TI, "AGENCE TID", "TID AGENCY"));
	}

	public static RefTableREF_TYP_NUMID instance() {
		if (null == m_RefTableREF_TYP_NUMID) {
			m_RefTableREF_TYP_NUMID = new RefTableREF_TYP_NUMID();
		}
		return m_RefTableREF_TYP_NUMID;
	}


}
