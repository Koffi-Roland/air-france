package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_MAIL extends RefTableMere {


	private static RefTableREF_TYP_MAIL m_RefTableREF_TYP_MAIL = null;

	public static final String _REF_A = "A";
	public static final String _REF_N = "N";
	public static final String _REF_T = "T";


	private RefTableREF_TYP_MAIL() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "AIR FRANCE KLM", "AIR FRANCE KLM"));
		m_lstChamps.add(new RefTableChamp(_REF_N, "AUCUN", "NO"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "AIR FRANCE KLM + PARTNERS", "AIR FRANCE KLM + PARTNERS"));
	}

	public static RefTableREF_TYP_MAIL instance() {
		if (null == m_RefTableREF_TYP_MAIL) {
			m_RefTableREF_TYP_MAIL = new RefTableREF_TYP_MAIL();
		}
		return m_RefTableREF_TYP_MAIL;
	}


}
