package com.afklm.rigui.entity.refTable;

public class RefTableREF_STA_MED extends RefTableMere {


	private static RefTableREF_STA_MED m_RefTableREF_STA_MED = null;

	public static final String _REF_H = "H";
	public static final String _REF_I = "I";
	public static final String _REF_S = "S";
	public static final String _REF_T = "T";
	public static final String _REF_V = "V";
	public static final String _REF_X = "X";


	private RefTableREF_STA_MED() {

		m_lstChamps.add(new RefTableChamp(_REF_H, "HISTORIQUE", "HISTORIC"));
		m_lstChamps.add(new RefTableChamp(_REF_I, "INVALIDE", "INVALID"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "SUSPENDU", "PENDING"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "TEMPORAIRE", "TEMPORARY"));
		m_lstChamps.add(new RefTableChamp(_REF_V, "VALIDE", "VALID"));
		m_lstChamps.add(new RefTableChamp(_REF_X, "SUPPRIME", "SUPPRESSED"));
	}

	public static RefTableREF_STA_MED instance() {
		if (null == m_RefTableREF_STA_MED) {
			m_RefTableREF_STA_MED = new RefTableREF_STA_MED();
		}
		return m_RefTableREF_STA_MED;
	}


}
