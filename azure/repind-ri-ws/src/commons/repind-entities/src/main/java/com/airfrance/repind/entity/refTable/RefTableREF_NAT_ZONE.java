package com.airfrance.repind.entity.refTable;

public class RefTableREF_NAT_ZONE extends RefTableMere {


	private static RefTableREF_NAT_ZONE m_RefTableREF_NAT_ZONE = null;

	public static final String _REF_ANN = "ANN";
	public static final String _REF_ATT = "ATT";
	public static final String _REF_SEC = "SEC";


	private RefTableREF_NAT_ZONE() {

		m_lstChamps.add(new RefTableChamp(_REF_ANN, "ANNULATION", "CANCELLATION"));
		m_lstChamps.add(new RefTableChamp(_REF_ATT, "ATTENTE", "STAND BY"));
		m_lstChamps.add(new RefTableChamp(_REF_SEC, "SECTORISE", "SECTORISED"));
	}

	public static RefTableREF_NAT_ZONE instance() {
		if (null == m_RefTableREF_NAT_ZONE) {
			m_RefTableREF_NAT_ZONE = new RefTableREF_NAT_ZONE();
		}
		return m_RefTableREF_NAT_ZONE;
	}


}
