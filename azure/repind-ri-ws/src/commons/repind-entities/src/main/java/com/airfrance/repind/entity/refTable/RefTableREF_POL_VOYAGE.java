package com.airfrance.repind.entity.refTable;

public class RefTableREF_POL_VOYAGE extends RefTableMere {


	private static RefTableREF_POL_VOYAGE m_RefTableREF_POL_VOYAGE = null;

	public static final String _REF_D = "D";
	public static final String _REF_F = "F";
	public static final String _REF_I = "I";


	private RefTableREF_POL_VOYAGE() {

		m_lstChamps.add(new RefTableChamp(_REF_D, "Directive", "Directive"));
		m_lstChamps.add(new RefTableChamp(_REF_F, "Formalisee", "Formalized"));
		m_lstChamps.add(new RefTableChamp(_REF_I, "Existante", "Existing"));
	}

	public static RefTableREF_POL_VOYAGE instance() {
		if (null == m_RefTableREF_POL_VOYAGE) {
			m_RefTableREF_POL_VOYAGE = new RefTableREF_POL_VOYAGE();
		}
		return m_RefTableREF_POL_VOYAGE;
	}


}
