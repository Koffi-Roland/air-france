package com.airfrance.repind.entity.refTable;

public class RefTableREF_PORT extends RefTableMere {


	private static RefTableREF_PORT m_RefTableREF_PORT = null;

	public static final String _REF_AB = "AB";
	public static final String _REF_AM = "AM";
	public static final String _REF_AX = "AX";
	public static final String _REF_GA = "GA";
	public static final String _REF_IN = "IN";
	public static final String _REF_ND = "ND";
	public static final String _REF_SA = "SA";
	public static final String _REF_TR = "TR";
	public static final String _REF_WO = "WO";


	private RefTableREF_PORT() {

		m_lstChamps.add(new RefTableChamp(_REF_AB, "Abacus GDS", "Abacus GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_AM, "Amadeus GDS", "Amadeus GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_AX, "Axess GDS", "Axess GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_GA, "Galileo GDS", "Galileo GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_IN, "Infini GDS", "Infini GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_ND, "NDC", "NDC"));
		m_lstChamps.add(new RefTableChamp(_REF_SA, "Sabre GDS", "Sabre GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_TR, "TravelSky GDS", "TravelSky GDS"));
		m_lstChamps.add(new RefTableChamp(_REF_WO, "Worldspan GDS", "Worldspan GDS"));
	}

	public static RefTableREF_PORT instance() {
		if (null == m_RefTableREF_PORT) {
			m_RefTableREF_PORT = new RefTableREF_PORT();
		}
		return m_RefTableREF_PORT;
	}


}
