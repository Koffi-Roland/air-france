package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_AGRE extends RefTableMere {


	private static RefTableREF_TYP_AGRE m_RefTableREF_TYP_AGRE = null;

	public static final String _REF_A = "A";
	public static final String _REF_B = "B";
	public static final String _REF_D = "D";
	public static final String _REF_F = "F";
	public static final String _REF_I = "I";
	public static final String _REF_N = "N";
	public static final String _REF_R = "R";
	public static final String _REF_T = "T";


	public static final String DIVERS = "DIVERS";


	private RefTableREF_TYP_AGRE() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "AF", "AF"));
		m_lstChamps.add(new RefTableChamp(_REF_B, "BANDE IATA", "IATA BAND"));
		m_lstChamps.add(new RefTableChamp(_REF_D, DIVERS, DIVERS));
		m_lstChamps.add(new RefTableChamp(_REF_F, "FICTIF", "FICTIVE"));
		m_lstChamps.add(new RefTableChamp(_REF_I, "IATA", "IATA"));
		m_lstChamps.add(new RefTableChamp(_REF_N, DIVERS, "OTHERS"));
		m_lstChamps.add(new RefTableChamp(_REF_R, "ARC", "ARC"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "ATAF", "ATAF"));
	}

	public static RefTableREF_TYP_AGRE instance() {
		if (null == m_RefTableREF_TYP_AGRE) {
			m_RefTableREF_TYP_AGRE = new RefTableREF_TYP_AGRE();
		}
		return m_RefTableREF_TYP_AGRE;
	}


}
