package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_CHIFFRE extends RefTableMere {


	private static RefTableREF_TYP_CHIFFRE m_RefTableREF_TYP_CHIFFRE = null;

	public static final String _REF_C = "C";
	public static final String _REF_CA = "CA";
	public static final String _REF_CE = "CE";
	public static final String _REF_CI = "CI";
	public static final String _REF_CP = "CP";

	public static final String CAPITAL = "CAPITAL";



	private RefTableREF_TYP_CHIFFRE() {

		m_lstChamps.add(new RefTableChamp(_REF_C, CAPITAL, CAPITAL));
		m_lstChamps.add(new RefTableChamp(_REF_CA, "CHIFFRE D AFFAIRE", "SALES TURNOVER"));
		m_lstChamps.add(new RefTableChamp(_REF_CE, "CHIFFRE D AFFAIRE EXPORT", "EXPORT SALES TURNOVER"));
		m_lstChamps.add(new RefTableChamp(_REF_CI, "CHIFFRE D AFFAIRE IMPORT", "IMPORT SALES TURNOVER"));
		m_lstChamps.add(new RefTableChamp(_REF_CP, CAPITAL, CAPITAL));
	}

	public static RefTableREF_TYP_CHIFFRE instance() {
		if (null == m_RefTableREF_TYP_CHIFFRE) {
			m_RefTableREF_TYP_CHIFFRE = new RefTableREF_TYP_CHIFFRE();
		}
		return m_RefTableREF_TYP_CHIFFRE;
	}


}
