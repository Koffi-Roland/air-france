package com.airfrance.repind.entity.refTable;

public class RefTableREF_STATUT_IATA extends RefTableMere {

	private static RefTableREF_STATUT_IATA m_RefTableREF_STATUT_IATA = null;

	public static final String _REF_AA = "AA";
	public static final String _REF_AE = "AE";
	public static final String _REF_AI = "AI";
	public static final String _REF_AO = "AO";
	public static final String _REF_AR = "AR";
	public static final String _REF_AT = "AT";
	public static final String _REF_BB = "BB";
	public static final String _REF_BI = "BI";
	public static final String _REF_BR = "BR";
	public static final String _REF_BT = "BT";
	public static final String _REF_EB = "EB";
	public static final String _REF_EP = "EP";
	public static final String _REF_ES = "ES";
	public static final String _REF_ET = "ET";
	public static final String _REF_HE = "HE";
	public static final String _REF_HH = "HH";
	public static final String _REF_HI = "HI";
	public static final String _REF_HO = "HO";
	public static final String _REF_HR = "HR";
	public static final String _REF_HT = "HT";
	public static final String _REF_SA = "SA";
	public static final String _REF_SE = "SE";
	public static final String _REF_SP = "SP";
	public static final String _REF_SS = "SS";
	public static final String _REF_ST = "ST";
	public static final String _REF_TD = "TD";

	public static final String US_ONLY = "US ONLY";
	public static final String INUSITE = "INUSITE";

	private RefTableREF_STATUT_IATA() {

		m_lstChamps.add(new RefTableChamp(_REF_AA, "ARC-INDEPENDANT", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_AE, "IATA - ASSOCIATE ENTITY", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_AI, "ARC-INDEPENDANT IMPLANT", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_AO, "IATA - BUREAU ADMINIST", "IATA - BUREAU ADMINIST"));
		m_lstChamps.add(new RefTableChamp(_REF_AR, "ARC - INDEP RESTRICT ACCE", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_AT, "ARC - INDEPENDANT HOST", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_BB, "ARC- BRANCH", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_BI, "ARC- BRANCH IMPLANT", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_BR, "IATA - FILIALE D AGENCE", "US AND ROW"));
		m_lstChamps.add(new RefTableChamp(_REF_BT, "ARC - BRANCH HOST", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_EB, "IATA - FILIALE ERSP", "IATA - FILIALE ERSP"));
		m_lstChamps.add(new RefTableChamp(_REF_EP, "IATA - ERSP PARENT", "IATA - ERSP PARENT"));
		m_lstChamps.add(new RefTableChamp(_REF_ES, "IATA-IMP. DEPORTEE EUR", "IATA-IMP. DEPORTEE EUR"));
		m_lstChamps.add(new RefTableChamp(_REF_ET, INUSITE, INUSITE));
		m_lstChamps.add(new RefTableChamp(_REF_HE, "IATA - HEAD ENTITY", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_HH, "ARC - HOME", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_HI, "ARC - HOME IMPLANT", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_HO, "IATA - MAISON MERE", "IATA - MAISON MERE"));
		m_lstChamps.add(new RefTableChamp(_REF_HR, "ARC - HOME RESTRICT AREA", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_HT, "ARC - HOME HOST", "ARC - HOME HOST"));
		m_lstChamps.add(new RefTableChamp(_REF_SA, "VENTES SPECIALES", "SALES ACTIVITY"));
		m_lstChamps.add(new RefTableChamp(_REF_SE, "IATA-EVENEMENT PARTICULIE", "IATA-EVENEMENT PARTICULIE"));
		m_lstChamps.add(new RefTableChamp(_REF_SP, INUSITE, INUSITE));
		m_lstChamps.add(new RefTableChamp(_REF_SS, "ARCSATELLITE TKT PRINTER", US_ONLY));
		m_lstChamps.add(new RefTableChamp(_REF_ST, "IATA - IMPR DEPORTEE HORS", "IATA - IMPR DEPORTEE HORS"));
		m_lstChamps.add(new RefTableChamp(_REF_TD, INUSITE, INUSITE));
	}

	public static RefTableREF_STATUT_IATA instance() {
		if (null == m_RefTableREF_STATUT_IATA) {
			m_RefTableREF_STATUT_IATA = new RefTableREF_STATUT_IATA();
		}
		return m_RefTableREF_STATUT_IATA;
	}

}
