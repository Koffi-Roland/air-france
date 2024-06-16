package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_SEG_A extends RefTableMere {


	private static RefTableREF_TYP_SEG_A m_RefTableREF_TYP_SEG_A = null;

	public static final String _REF_SGA = "SGA";
	public static final String _REF_SGC = "SGC";
	public static final String _REF_SGM = "SGM";
	public static final String _REF_SGR = "SGR";
	public static final String _REF_STO = "STO";


	private RefTableREF_TYP_SEG_A() {

		m_lstChamps.add(new RefTableChamp(_REF_SGA, "ATTACHES COMMERCIAUX", "SALES FORCES"));
		m_lstChamps.add(new RefTableChamp(_REF_SGC, "GRANDS COMPTES", "MAIN COMPANY"));
		m_lstChamps.add(new RefTableChamp(_REF_SGM, "MULTI MARCHE", "MULTI MARKETS"));
		m_lstChamps.add(new RefTableChamp(_REF_SGR, "GROUPES", "GROUP SALES"));
		m_lstChamps.add(new RefTableChamp(_REF_STO, "TOUR OPERATORS", "TOUR OPERATORS"));
	}

	public static RefTableREF_TYP_SEG_A instance() {
		if (null == m_RefTableREF_TYP_SEG_A) {
			m_RefTableREF_TYP_SEG_A = new RefTableREF_TYP_SEG_A();
		}
		return m_RefTableREF_TYP_SEG_A;
	}


}
