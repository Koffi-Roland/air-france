package com.airfrance.repind.entity.refTable;

public class RefTableREF_AUTORIS_MAIL extends RefTableMere {


	private static RefTableREF_AUTORIS_MAIL m_RefTableREF_AUTORIS_MAIL = null;

	public static final String _REF_A = "A";
	public static final String _REF_N = "N";
	public static final String _REF_T = "T";


	private RefTableREF_AUTORIS_MAIL() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "Air France", "Air France"));
		m_lstChamps.add(new RefTableChamp(_REF_N, "Rien", "No"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "Tout", "All"));
	}

	public static RefTableREF_AUTORIS_MAIL instance() {
		if (null == m_RefTableREF_AUTORIS_MAIL) {
			m_RefTableREF_AUTORIS_MAIL = new RefTableREF_AUTORIS_MAIL();
		}
		return m_RefTableREF_AUTORIS_MAIL;
	}


}
