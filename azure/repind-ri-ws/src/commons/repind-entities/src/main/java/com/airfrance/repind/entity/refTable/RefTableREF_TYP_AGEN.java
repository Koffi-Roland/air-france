package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_AGEN extends RefTableMere {


	private static RefTableREF_TYP_AGEN m_RefTableREF_TYP_AGEN = null;

	public static final String _REF_0 = "0";
	public static final String _REF_1 = "1";
	public static final String _REF_2 = "2";
	public static final String _REF_3 = "3";
	public static final String _REF_4 = "4";
	public static final String _REF_5 = "5";
	public static final String _REF_6 = "6";
	public static final String _REF_7 = "7";
	public static final String _REF_9 = "9";


	private RefTableREF_TYP_AGEN() {

		m_lstChamps.add(new RefTableChamp(_REF_0, "AG FICT", "FICTIVE AG"));
		m_lstChamps.add(new RefTableChamp(_REF_1, "AF KLM AG", "AF KLM OF"));
		m_lstChamps.add(new RefTableChamp(_REF_2, "STP", "STP"));
		m_lstChamps.add(new RefTableChamp(_REF_3, "AG CE", "AIRLINE OF"));
		m_lstChamps.add(new RefTableChamp(_REF_4, "GSA AF", "AF GSA"));
		m_lstChamps.add(new RefTableChamp(_REF_5, "ERSP", "ERSP"));
		m_lstChamps.add(new RefTableChamp(_REF_6, "GSA CE", "AIRLINE GS"));
		m_lstChamps.add(new RefTableChamp(_REF_7, "AG VOY", "TVL AG"));
		m_lstChamps.add(new RefTableChamp(_REF_9, "NA SS CAT", "NA SS CAT"));
	}

	public static RefTableREF_TYP_AGEN instance() {
		if (null == m_RefTableREF_TYP_AGEN) {
			m_RefTableREF_TYP_AGEN = new RefTableREF_TYP_AGEN();
		}
		return m_RefTableREF_TYP_AGEN;
	}


}
