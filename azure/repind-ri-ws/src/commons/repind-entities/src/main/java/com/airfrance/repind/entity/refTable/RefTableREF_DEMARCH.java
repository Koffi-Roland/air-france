package com.airfrance.repind.entity.refTable;

public class RefTableREF_DEMARCH extends RefTableMere {


	private static RefTableREF_DEMARCH m_RefTableREF_DEMARCH = null;

	public static final String _REF_A = "A";
	public static final String _REF_B = "B";
	public static final String _REF_C = "C";
	public static final String _REF_D = "D";
	public static final String _REF_E = "E";
	public static final String _REF_F = "F";
	public static final String _REF_G = "G";
	public static final String _REF_H = "H";
	public static final String _REF_I = "I";
	public static final String _REF_J = "J";
	public static final String _REF_K = "K";
	public static final String _REF_L = "L";
	public static final String _REF_M = "M";
	public static final String _REF_N = "N";
	public static final String _REF_O = "O";
	public static final String _REF_P = "P";
	public static final String _REF_Q = "Q";
	public static final String _REF_R = "R";
	public static final String _REF_S = "S";
	public static final String _REF_T = "T";
	public static final String _REF_U = "U";
	public static final String _REF_V = "V";
	public static final String _REF_W = "W";


	private RefTableREF_DEMARCH() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "Autre", "Others"));
		m_lstChamps.add(new RefTableChamp(_REF_B, "ABB Abonnement", "ABB Subscription"));
		m_lstChamps.add(new RefTableChamp(_REF_C, "CWC Firme sans contrat", "CWC Firm without contract"));
		m_lstChamps.add(new RefTableChamp(_REF_D, "MKT Marketing", "MKT Marketing"));
		m_lstChamps.add(new RefTableChamp(_REF_E, "SME Petites moyennes Entr", "SME Small Medium corp"));
		m_lstChamps.add(new RefTableChamp(_REF_F, "PFE Contrat Amex", "PFE Amex contract"));
		m_lstChamps.add(new RefTableChamp(_REF_G, "GCL FCE Grand compte loca", "GCL FCE Local key account"));
		m_lstChamps.add(new RefTableChamp(_REF_H, "PRM Prospect marketing", "PRM Marketing prospect"));
		m_lstChamps.add(new RefTableChamp(_REF_I, "HIS Sans potentiel", "HIS Without potential"));
		m_lstChamps.add(new RefTableChamp(_REF_J, "FID Fidelio", "FID Fidelio"));
		m_lstChamps.add(new RefTableChamp(_REF_K, "LKA CI Local key account", "LKA CI Local key account"));
		m_lstChamps.add(new RefTableChamp(_REF_L, "LA CI Local account", "LA CI Local account"));
		m_lstChamps.add(new RefTableChamp(_REF_M, "Mailing", "Mailing"));
		m_lstChamps.add(new RefTableChamp(_REF_N, "Non Prospectee", "Not prospected"));
		m_lstChamps.add(new RefTableChamp(_REF_O, "GA  Comptes globaux", "GA Global accounts"));
		m_lstChamps.add(new RefTableChamp(_REF_P, "Phone marketing (AG)", "Phone marketing (AG)"));
		m_lstChamps.add(new RefTableChamp(_REF_Q, "CNL Firmes annulees", "CNL Cancelled corporates"));
		m_lstChamps.add(new RefTableChamp(_REF_R, "PRS Prospect ventes", "PRS Prospect sales"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "STD FCE Comptes locaux", "STD FCE Local account"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "Phone marketing  (FIRME)", "Phone marketing  (FIRM)"));
		m_lstChamps.add(new RefTableChamp(_REF_U, "PME FCE Petite moy firme", "PME FCE Small medium firm"));
		m_lstChamps.add(new RefTableChamp(_REF_V, "Visite", "Visit"));
		m_lstChamps.add(new RefTableChamp(_REF_W, "NQL Firmes non qualifiees", "NQL Non qualified corp"));
	}

	public static RefTableREF_DEMARCH instance() {
		if (null == m_RefTableREF_DEMARCH) {
			m_RefTableREF_DEMARCH = new RefTableREF_DEMARCH();
		}
		return m_RefTableREF_DEMARCH;
	}


}
