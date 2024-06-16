package com.afklm.rigui.entity.refTable;

public class RefTableCAT_MED extends RefTableMere {


	private static RefTableCAT_MED m_RefTableCAT_MED = null;
	private static final String LOCALISATION = "LOCALISATION";
	private static final String MAILING = "MAILING";

	public static final String _REF_C = "C";
	public static final String _REF_D = "D";
	public static final String _REF_E = "E";
	public static final String _REF_F = "F";
	public static final String _REF_G = "G";
	public static final String _REF_L = "L";
	public static final String _REF_M = "M";
	public static final String _REF_N = "N";
	public static final String _REF_P = "P";
	public static final String _REF_R = "R";
	public static final String _REF_T = "T";
	public static final String _REF_V = "V";


	private RefTableCAT_MED() {

		m_lstChamps.add(new RefTableChamp(_REF_C, "COMPTABLE", "ACCOUNTING", "ACCOUNTING"));
		m_lstChamps.add(new RefTableChamp(_REF_D, "DOMICILE", "HOME", "HOME"));
		m_lstChamps.add(new RefTableChamp(_REF_E, "ENVOI FACTURE", "BILLING ADRESS", "BILLING ADRESS"));
		m_lstChamps.add(new RefTableChamp(_REF_F, "FACTURATION", "BILLING", "BILLING"));
		m_lstChamps.add(new RefTableChamp(_REF_G, "GROUPE", "GROUP", "GROUP"));
		m_lstChamps.add(new RefTableChamp(_REF_L, LOCALISATION, LOCALISATION, LOCALISATION));
		m_lstChamps.add(new RefTableChamp(_REF_M, MAILING, MAILING, MAILING));
		m_lstChamps.add(new RefTableChamp(_REF_N, "NEGOCIATEUR VOYAGES", "TRAVEL NEGOCIATOR", "TRAVEL NEGOCIATOR"));
		m_lstChamps.add(new RefTableChamp(_REF_P, "PROFESSIONNEL(LE)", "BUSINESS", "BUSINESS"));
		m_lstChamps.add(new RefTableChamp(_REF_R, "RELATION CLIENTELE", "HOTLINE", "HOTLINE"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "DECIDEUR", "DECISION MAKER", "DECISION MAKER"));
		m_lstChamps.add(new RefTableChamp(_REF_V, "PAIEMENT", "PAYMENT", "PAYMENT"));
	}

	public static RefTableCAT_MED instance() {
		if (null == m_RefTableCAT_MED) {
			m_RefTableCAT_MED = new RefTableCAT_MED();
		}
		return m_RefTableCAT_MED;
	}


}
