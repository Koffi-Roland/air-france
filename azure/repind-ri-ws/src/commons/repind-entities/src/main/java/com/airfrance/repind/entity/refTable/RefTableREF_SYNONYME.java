package com.airfrance.repind.entity.refTable;

public class RefTableREF_SYNONYME extends RefTableMere {


	private static RefTableREF_SYNONYME m_RefTableREF_SYNONYME = null;

	public static final String _REF_D = "D";
	public static final String _REF_M = "M";
	public static final String _REF_S = "S";
	public static final String _REF_U = "U";


	private RefTableREF_SYNONYME() {

		m_lstChamps.add(new RefTableChamp(_REF_D, "Mot directeur", "Director word"));
		m_lstChamps.add(new RefTableChamp(_REF_M, "Marque commerciale", "Trade name"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "Sigle", "Logo"));
		m_lstChamps.add(new RefTableChamp(_REF_U, "Nom usuel", "Usual name"));
	}

	public static RefTableREF_SYNONYME instance() {
		if (null == m_RefTableREF_SYNONYME) {
			m_RefTableREF_SYNONYME = new RefTableREF_SYNONYME();
		}
		return m_RefTableREF_SYNONYME;
	}


}
