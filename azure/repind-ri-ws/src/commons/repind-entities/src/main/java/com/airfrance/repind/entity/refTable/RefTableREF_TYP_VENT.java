package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_VENT extends RefTableMere {


	private static RefTableREF_TYP_VENT m_RefTableREF_TYP_VENT = null;

	public static final String _REF_VD = "VD";
	public static final String _REF_VT = "VT";


	private RefTableREF_TYP_VENT() {

		m_lstChamps.add(new RefTableChamp(_REF_VD, "VENTE DIRECTE", "DIRECT SALES"));
		m_lstChamps.add(new RefTableChamp(_REF_VT, "VENTE TIERCE", "INDIRECT SALES"));
	}

	public static RefTableREF_TYP_VENT instance() {
		if (null == m_RefTableREF_TYP_VENT) {
			m_RefTableREF_TYP_VENT = new RefTableREF_TYP_VENT();
		}
		return m_RefTableREF_TYP_VENT;
	}


}
