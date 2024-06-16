package com.airfrance.repind.entity.refTable;

public class RefTableREF_STA_JURI extends RefTableMere {


	private static RefTableREF_STA_JURI m_RefTableREF_STA_JURI = null;

	public static final String _REF_CO = "CO";
	public static final String _REF_INC = "INC";
	public static final String _REF_LTD = "LTD";
	public static final String _REF_PVT = "PVT";
	public static final String _REF_SA = "SA";
	public static final String _REF_SARL = "SARL";
	public static final String _REF_SC = "SC";


	private RefTableREF_STA_JURI() {

		m_lstChamps.add(new RefTableChamp(_REF_CO, "COMPANY", "COMPANY"));
		m_lstChamps.add(new RefTableChamp(_REF_INC, "INCORPORETED", "INCORPORETED"));
		m_lstChamps.add(new RefTableChamp(_REF_LTD, "LIMITED", "LIMITED"));
		m_lstChamps.add(new RefTableChamp(_REF_PVT, "PRIVATE", "PRIVATE"));
		m_lstChamps.add(new RefTableChamp(_REF_SA, "SOCIETE ANONYME", "SOCIETE ANONYME"));
		m_lstChamps.add(new RefTableChamp(_REF_SARL, "SOCIETE A RESPONSABILITE LIMITEE", "SOCIETE A RESPONSABILITE LIMITEE"));
		m_lstChamps.add(new RefTableChamp(_REF_SC, "SOCIETE EN COMMANDITE", "SOCIETE EN COMMANDITE"));
	}

	public static RefTableREF_STA_JURI instance() {
		if (null == m_RefTableREF_STA_JURI) {
			m_RefTableREF_STA_JURI = new RefTableREF_STA_JURI();
		}
		return m_RefTableREF_STA_JURI;
	}


}
