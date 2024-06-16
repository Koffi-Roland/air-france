package com.airfrance.repind.entity.refTable;

public class RefTableREF_STYP_ZON extends RefTableMere {


	private static RefTableREF_STYP_ZON m_RefTableREF_STYP_ZON = null;

	public static final String _REF_CM = "CM";
	public static final String _REF_CN = "CN";
	public static final String _REF_FV = "FV";
	public static final String _REF_GR = "GR";
	public static final String _REF_MK = "MK";
	public static final String _REF_MM = "MM";
	public static final String _REF_SM = "SM";
	public static final String _REF_TO = "TO";
	public static final String _REF_VD = "VD";


	private RefTableREF_STYP_ZON() {

		m_lstChamps.add(new RefTableChamp(_REF_CM, "MULTI MARCHE", "MULTI MARKET ZONE", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_CN, "COMPTES NATIONAUX", "COMPTES NATIONAUX", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_FV, "FORCES DE VENTE", "SALES FORCES ZONE", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_GR, "GROUPES", "GROUPS ZONE (BINOM)", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_MK, "MARKETING", "MARKETING", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_MM, "GRANDS COMPTES GLOBAUX", "GLOBAL ACCOUNT", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_SM, "FIRMES SME", "SME", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_TO, "TOUR OPERATOR", "TOUR OPERATOR ZONE", "ZC"));
		m_lstChamps.add(new RefTableChamp(_REF_VD, "VENTE DIRECTE", "DIRECT SALES", "ZC"));
	}

	public static RefTableREF_STYP_ZON instance() {
		if (null == m_RefTableREF_STYP_ZON) {
			m_RefTableREF_STYP_ZON = new RefTableREF_STYP_ZON();
		}
		return m_RefTableREF_STYP_ZON;
	}


}
