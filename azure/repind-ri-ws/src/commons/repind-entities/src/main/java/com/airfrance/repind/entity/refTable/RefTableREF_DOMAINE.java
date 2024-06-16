package com.airfrance.repind.entity.refTable;

public class RefTableREF_DOMAINE extends RefTableMere {


	
	private static RefTableREF_DOMAINE m_RefTableREF_DOMAINE = null;

	public static final String _REF_A = "A";
	public static final String _REF_B = "B";
	public static final String _REF_D = "DC";
	public static final String _REF_E = "E";
	public static final String _REF_F = "EM";
	public static final String _REF_G = "G";
	public static final String _REF_I = "I";
	public static final String _REF_L = "L";
	public static final String _REF_N = "N";
	public static final String _REF_P = "P";
	public static final String _REF_Q = "Q";
	public static final String _REF_R = "R";
	public static final String _REF_S = "S";
	public static final String _REF_T = "T";
	public static final String _REF_V = "V";
	public static final String _REF_W = "W";
    public static final String _REF_X = "X";
	public static final String _REF_Y = "Y";
	public static final String _REF_Z = "Z";
 

	private RefTableREF_DOMAINE() {

		m_lstChamps.add(new RefTableChamp(_REF_A, "AFFAIRES", "BUSINESS", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_B, "SPECIALISTES", "SPECIALTY SALES", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_D, "DIRECT CONNECT", "DIRECT CONNECT", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_E, "CELLULE EMISSION", "TKT ISSUED CENTER", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_F, "PAIEMENT EMV", "PAYEMENT EMV", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_G, "GENERALISTE", "GENERALIST", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_I, "LOISIRS ONLINE", "ONLINE LEISURE", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_L, "LOISIRS OFFLINE", "OFFLINE LEISURE", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_N, "E COMMERCE", "E BUSINESS", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_P, "AGAF VILLES", "CITY TICKET OFFICE", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_Q, "AGAF COMPTOIRS", "AIRPORT TICKET OFFICE", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_R, "AUTRES- GP-FB", "OTHERS- GP-FB", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "SWIPE", "SWIPE", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_T, "TPE", "CHIP AND PIN", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_V, "VAD", "CALL CENTER", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_W, "PLAT AFF", "CORPORATE DESK", "VD"));
        m_lstChamps.add(new RefTableChamp(_REF_X, "BLS", "KIOSK", "VD"));
		m_lstChamps.add(new RefTableChamp(_REF_Y, "BUREAU CE - GSA CE", "OTHER AIRLINES N GSA OFFI", "VT"));
		m_lstChamps.add(new RefTableChamp(_REF_Z, "AUCUN", "AUCUN", "VT"));
	}

	public static RefTableREF_DOMAINE instance() {
		if (null == m_RefTableREF_DOMAINE) {
			m_RefTableREF_DOMAINE = new RefTableREF_DOMAINE();
		}
		return m_RefTableREF_DOMAINE;
	}


}
