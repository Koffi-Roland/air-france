package com.airfrance.repind.entity.refTable;

public class RefTableREF_LOCALISA extends RefTableMere {


	private static RefTableREF_LOCALISA m_RefTableREF_LOCALISA = null;

	public static final String _REF_A = "A";
	public static final String _REF_V = "V";


	private RefTableREF_LOCALISA() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "AEROPORT", "AIRPORT"));
		m_lstChamps.add(new RefTableChamp(_REF_V, "VILLE", "TOWN"));
	}

	public static RefTableREF_LOCALISA instance() {
		if (null == m_RefTableREF_LOCALISA) {
			m_RefTableREF_LOCALISA = new RefTableREF_LOCALISA();
		}
		return m_RefTableREF_LOCALISA;
	}


}
