package com.airfrance.repind.entity.refTable;

public class RefTableREF_STATUTPM extends RefTableMere {


	private static RefTableREF_STATUTPM m_RefTableREF_STATUTPM = null;

	public static final String _REF_A = "A";
	public static final String _REF_AI = "AI";
	public static final String _REF_FU = "FU";
	public static final String _REF_P = "P";
	public static final String _REF_Q = "Q";
	public static final String _REF_R = "R";
	public static final String _REF_X = "X";
	public static final String _REF_D = "D";


	private RefTableREF_STATUTPM() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "ACTIVE", "ACTIVE"));
		m_lstChamps.add(new RefTableChamp(_REF_AI, "ADRESSE INVALIDE", "INVALID ADDRESS"));
		m_lstChamps.add(new RefTableChamp(_REF_FU, "FUSIONNEE", "MERGED"));
		m_lstChamps.add(new RefTableChamp(_REF_P, "PROVISOIRE", "TEMPORARY"));
		m_lstChamps.add(new RefTableChamp(_REF_Q, "FILE D ATTENTE", "QUEUE"));
		m_lstChamps.add(new RefTableChamp(_REF_R, "RADIEE", "STRUCK OFF"));
		m_lstChamps.add(new RefTableChamp(_REF_X, "FERMEE", "CLOSED"));
		m_lstChamps.add(new RefTableChamp(_REF_D, "SUPPRIMEE", "DELETED"));
	}

	public static RefTableREF_STATUTPM instance() {
		if (null == m_RefTableREF_STATUTPM) {
			m_RefTableREF_STATUTPM = new RefTableREF_STATUTPM();
		}
		return m_RefTableREF_STATUTPM;
	}


}
