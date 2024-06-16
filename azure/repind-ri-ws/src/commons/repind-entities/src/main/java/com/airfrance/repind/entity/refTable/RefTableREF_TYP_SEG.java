package com.airfrance.repind.entity.refTable;

public class RefTableREF_TYP_SEG extends RefTableMere {


	public RefTableREF_TYP_SEG m_RefTableREF_TYP_SEG = null;

	public static final String _REF_FVF = "FVF";
	public static final String _REF_SGA = "SGA";
	public static final String _REF_SGC = "SGC";
	public static final String _REF_SGM = "SGM";
	public static final String _REF_SGR = "SGR";
	public static final String _REF_STO = "STO";


	public RefTableREF_TYP_SEG() {

		m_lstChamps.add(new RefTableChamp(_REF_FVF, "FORCES DE VENTE", "SALES DIRECTION"));
		m_lstChamps.add(new RefTableChamp(_REF_SGA, "ATTACHES COMMERCIAUX", "SALES FORCES"));
		m_lstChamps.add(new RefTableChamp(_REF_SGC, "GRANDS COMPTES", "MAIN COMPANY"));
		m_lstChamps.add(new RefTableChamp(_REF_SGM, "MULTI MARCHE", "MULTI MARKETS"));
		m_lstChamps.add(new RefTableChamp(_REF_SGR, "GROUPES", "GROUP SALES"));
		m_lstChamps.add(new RefTableChamp(_REF_STO, "TOUR OPERATORS", "TOUR OPERATORS"));
	}

	public RefTableREF_TYP_SEG instance() {
		if (null != m_RefTableREF_TYP_SEG) {
			m_RefTableREF_TYP_SEG = new RefTableREF_TYP_SEG();
		}
		return m_RefTableREF_TYP_SEG;
	}


}
