package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_INFRA extends RefTableMere {


	private static RefTableREF_TYP_INFRA m_RefTableREF_TYP_INFRA = null;

	public static final String _REF_B = "B";
	public static final String _REF_E = "E";
	public static final String _REF_M = "M";
	public static final String _REF_Q = "Q";


	private RefTableREF_TYP_INFRA() {

		m_lstChamps.add(new RefTableChamp(_REF_B, "BTC PLAT AFF VENTE TIERCE", "BTC BSS TRAVEL CENTER"));
		m_lstChamps.add(new RefTableChamp(_REF_E, "IMPLANT EXCLUSIF", "EXCLUSIVE IMPLANT"));
		m_lstChamps.add(new RefTableChamp(_REF_M, "IMPLANT MULTIPLE", "MULTI IMPLANT"));
		m_lstChamps.add(new RefTableChamp(_REF_Q, "IMPLANT QUASI EXCLUSIF", "QUASI EXCLUSIVE IMPLANT"));
	}

	public static RefTableREF_TYP_INFRA instance() {
		if (null == m_RefTableREF_TYP_INFRA) {
			m_RefTableREF_TYP_INFRA = new RefTableREF_TYP_INFRA();
		}
		return m_RefTableREF_TYP_INFRA;
	}


}
