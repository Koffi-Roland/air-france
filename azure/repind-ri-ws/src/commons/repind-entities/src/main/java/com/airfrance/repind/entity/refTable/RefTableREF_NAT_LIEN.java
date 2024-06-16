package com.airfrance.repind.entity.refTable;

public class RefTableREF_NAT_LIEN extends RefTableMere {


	private static RefTableREF_NAT_LIEN m_RefTableREF_NAT_LIEN = null;

	public static final String _REF_AU = "AU";
	public static final String _REF_CL = "CL";
	public static final String _REF_IM = "IM";


	private RefTableREF_NAT_LIEN() {

		m_lstChamps.add(new RefTableChamp(_REF_AU, "AUTRES", "OTHERS"));
		m_lstChamps.add(new RefTableChamp(_REF_CL, "CLIENT", "CUSTOMER"));
		m_lstChamps.add(new RefTableChamp(_REF_IM, "IMPLANT", "IMPLANT"));
	}

	public static RefTableREF_NAT_LIEN instance() {
		if (null == m_RefTableREF_NAT_LIEN) {
			m_RefTableREF_NAT_LIEN = new RefTableREF_NAT_LIEN();
		}
		return m_RefTableREF_NAT_LIEN;
	}


}
