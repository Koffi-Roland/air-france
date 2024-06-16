package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_SEG_F extends RefTableMere {


	private static RefTableREF_TYP_SEG_F m_RefTableREF_TYP_SEG_F = null;

	public static final String _REF_FVF = "FVF";
	public static final String _REF_SGA = "SGA";


	private RefTableREF_TYP_SEG_F() {

		m_lstChamps.add(new RefTableChamp(_REF_FVF, "FORCES DE VENTE", "SALES DIRECTION"));
		m_lstChamps.add(new RefTableChamp(_REF_SGA, "ATTACHES COMMERCIAUX", "SALES FORCES"));
	}

	public static RefTableREF_TYP_SEG_F instance() {
		if (null == m_RefTableREF_TYP_SEG_F) {
			m_RefTableREF_TYP_SEG_F = new RefTableREF_TYP_SEG_F();
		}
		return m_RefTableREF_TYP_SEG_F;
	}


}
