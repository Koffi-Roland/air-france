package com.airfrance.repind.entity.refTable;

public class RefTableREF_SS_DOMAINE extends RefTableMere {


	private static RefTableREF_SS_DOMAINE m_RefTableREF_SS_DOMAINE = null;

	public static final String _REF_AB = "AB";
	public static final String _REF_AC = "AC";
	public static final String _REF_AD = "AD";
	public static final String _REF_AL = "AL";
	public static final String _REF_BA = "BA";
	public static final String _REF_BB = "BB";
	public static final String _REF_BC = "BC";
	public static final String _REF_BD = "BD";
	public static final String _REF_BE = "BE";
	public static final String _REF_BF = "BF";
	public static final String _REF_BG = "BG";
	public static final String _REF_BH = "BH";
	public static final String _REF_BI = "BI";
	public static final String _REF_BJ = "BJ";
	public static final String _REF_BK = "BK";
	public static final String _REF_EB = "EB";
	public static final String _REF_EK = "EK";
	public static final String _REF_EO = "EO";
	public static final String _REF_GA = "GA";
	public static final String _REF_GB = "GB";
	public static final String _REF_GC = "GC";
	public static final String _REF_GL = "GL";
	public static final String _REF_IA = "IA";
	public static final String _REF_IB = "IB";
	public static final String _REF_IC = "IC";
	public static final String _REF_LA = "LA";
	public static final String _REF_LB = "LB";
	public static final String _REF_LC = "LC";
	public static final String _REF_NB = "NB";
	public static final String _REF_NC = "NC";
	public static final String _REF_NK = "NK";
	public static final String _REF_NM = "NM";
	public static final String _REF_NP = "NP";
	public static final String _REF_PA = "PA";
	public static final String _REF_PB = "PB";
	public static final String _REF_PG = "PG";
	public static final String _REF_PK = "PK";
	public static final String _REF_PO = "PO";
	public static final String _REF_PP = "PP";
	public static final String _REF_PR = "PR";
	public static final String _REF_QF = "QF";
	public static final String _REF_QG = "QG";
	public static final String _REF_QK = "QK"; 
	public static final String _REF_QO = "QO";
	public static final String _REF_QP = "QP";
	public static final String _REF_QR = "QR";
	public static final String _REF_RK = "RK";
	public static final String _REF_RO = "RO";
	public static final String _REF_VA = "VA";
	public static final String _REF_VB = "VB";
	public static final String _REF_VI = "VI";
	public static final String _REF_VK = "VK";
	public static final String _REF_VO = "VO";
	public static final String _REF_VP = "VP";
	public static final String _REF_VR = "VR";
	public static final String _REF_VS = "VS";
	public static final String _REF_WA = "WA";
	public static final String _REF_WB = "WB";
	public static final String _REF_WK = "WK";
	public static final String _REF_WP = "WP";
	public static final String _REF_WR = "WR";
	public static final String _REF_WS = "WS";
	public static final String _REF_YS = "YS";


	public static final String OUTSOURCE = "OUTSOURCE";
	public static final String CODE_LOCAL = "CODE LOCAL";
	public static final String MARCHE_FRANCE = "MARCHE FRANCE";


	private RefTableREF_SS_DOMAINE() {

		m_lstChamps.add(new RefTableChamp(_REF_AB, "AFFAIRES/OFFLINE", "B2B OFFLINE/ONLINE", "A"));
		m_lstChamps.add(new RefTableChamp(_REF_AC, "AFFAIRES/INTERNET B2B", "B2B 100% ONLINE (OBT)", "A"));
		m_lstChamps.add(new RefTableChamp(_REF_AD, "AFFAIRES CONSOLIDATEUR", "BUSINESS CONSOLIDATOR", "A"));
		m_lstChamps.add(new RefTableChamp(_REF_BA, "ETUDIANTS", "STUDENTS", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BB, "CROISIERES", "CRUISES", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BC, "LUXE/LA PREMIERE", "LUXURY/LA PREMIERE", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BD, "MARINS/PETROLIERS", "SEAMEN/OFFSHORE", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BE, "MISSIONNAIRES/ONG", "NGO/MISSIONARY", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BF, "MILITAIRES", "MILITARY (MCG)", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BG, "ETHNIQUE", "ETHNIC", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BH, "MICE", "MICE", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BI, "GROUPE", "GROUP", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BJ, "MEDICAL", "MEDICAL", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_BK, "HYBRIDE", "HYBRID", "B"));
		m_lstChamps.add(new RefTableChamp(_REF_EB, "PRIME BLUEBIZ", "BLUEBIZ AWARD", "E"));
		m_lstChamps.add(new RefTableChamp(_REF_EK, "KLM", "KLM", "E"));
		m_lstChamps.add(new RefTableChamp(_REF_EO, OUTSOURCE, OUTSOURCE, "E"));
		m_lstChamps.add(new RefTableChamp(_REF_GC, "GENERALISTE CONSOLIDATEUR", "GENERALIST CONSOLIDATOR", "G"));
		m_lstChamps.add(new RefTableChamp(_REF_IC, "LOISIRS ONLINE CONSO", "ONLINE LEISURE CONSO", "I"));
		m_lstChamps.add(new RefTableChamp(_REF_LA, "LOISIRS/LOISIRS OFFLINE", "LEISURE/OFFLINE LEISURE", "L"));
		m_lstChamps.add(new RefTableChamp(_REF_LB, "TOUR OPERATEUR", "TOUR OPERATOR", "L"));
		m_lstChamps.add(new RefTableChamp(_REF_LC, "LOISIRS OFFLINE CONSO", "OFFLINE LEISURE CONSO", "L"));
		m_lstChamps.add(new RefTableChamp(_REF_NB, "INTERNET AF B2B", "INTERNET AF B2B", "N"));
		m_lstChamps.add(new RefTableChamp(_REF_NC, "INTERNET AF B2C", "INTERNET AF B2C", "N"));
		m_lstChamps.add(new RefTableChamp(_REF_NK, "KLM", "KLM", "N"));
		m_lstChamps.add(new RefTableChamp(_REF_NM, "CITYJET", "CITYJET", "N"));
		m_lstChamps.add(new RefTableChamp(_REF_NP, "ASSISTANCE INTERNET", "WEB ASSISTANCE", "N"));
		m_lstChamps.add(new RefTableChamp(_REF_PA, CODE_LOCAL, CODE_LOCAL, "P"));
		m_lstChamps.add(new RefTableChamp(_REF_PG, "GSA CTO", "GSA CTO", "P"));
		m_lstChamps.add(new RefTableChamp(_REF_PK, "KLM", "KLM", "P"));
		m_lstChamps.add(new RefTableChamp(_REF_PO, OUTSOURCE, OUTSOURCE, "P"));
		m_lstChamps.add(new RefTableChamp(_REF_PP, "AGAF PARIS", MARCHE_FRANCE, "P"));
		m_lstChamps.add(new RefTableChamp(_REF_PR, "AGAF PROVINCE", MARCHE_FRANCE, "P"));
		m_lstChamps.add(new RefTableChamp(_REF_QF, "FRANCHISED ATO", "FRANCHISED ATO", "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_QG, "GSA ATO", "GSA ATO", "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_QK, "KLM", "KLM", "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_QO, OUTSOURCE, OUTSOURCE, "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_QP, "COMPTOIR APT PAR", MARCHE_FRANCE, "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_QR, "COMPTOIR APT PROV", MARCHE_FRANCE, "Q"));
		m_lstChamps.add(new RefTableChamp(_REF_RK, "KLM", "KLM", "R"));
		m_lstChamps.add(new RefTableChamp(_REF_RO, OUTSOURCE, OUTSOURCE, "R"));
		m_lstChamps.add(new RefTableChamp(_REF_VA, CODE_LOCAL, CODE_LOCAL, "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VI, "INTL RES CENTERS", "INTL RES CENTERS", "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VK, "KLM", "KLM", "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VO, OUTSOURCE, OUTSOURCE, "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VP, "VAD PARIS", MARCHE_FRANCE, "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VR, "VAD PROVINCE", MARCHE_FRANCE, "V"));
		m_lstChamps.add(new RefTableChamp(_REF_VS, "INTL CALL CENTERS", "INTL CALL CENTERS", "V"));
		m_lstChamps.add(new RefTableChamp(_REF_WA, CODE_LOCAL, CODE_LOCAL, "W"));
		m_lstChamps.add(new RefTableChamp(_REF_WK, "PLAT AFF KLM", "PLAT AFF KLM", "W"));
		m_lstChamps.add(new RefTableChamp(_REF_WP, "PLAT AFF PAR", MARCHE_FRANCE, "W"));
		m_lstChamps.add(new RefTableChamp(_REF_WR, "PLAT AFF PROV", MARCHE_FRANCE, "W"));
		m_lstChamps.add(new RefTableChamp(_REF_WS, "SBT", "SBT", "W"));
		m_lstChamps.add(new RefTableChamp(_REF_YS, "CIE SKYTEAM", "SKYTEAM AIRLINE", "Y"));
	}

	public static RefTableREF_SS_DOMAINE instance() {
		if (null == m_RefTableREF_SS_DOMAINE) {
			m_RefTableREF_SS_DOMAINE = new RefTableREF_SS_DOMAINE();
		}
		return m_RefTableREF_SS_DOMAINE;
	}


}
