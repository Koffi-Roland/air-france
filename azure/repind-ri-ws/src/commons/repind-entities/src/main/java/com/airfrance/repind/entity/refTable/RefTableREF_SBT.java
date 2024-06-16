package com.airfrance.repind.entity.refTable;

public class RefTableREF_SBT extends RefTableMere {

	private static RefTableREF_SBT m_RefTableREF_SBT = null;

	public static final String _REF_A = "A";
	public static final String _REF_B = "B";
	public static final String _REF_C = "C";
	public static final String _REF_CL = "CL";
	public static final String _REF_CO = "CO";
	public static final String _REF_EK = "EK";
	public static final String _REF_F = "F";
	public static final String _REF_G = "G";
	public static final String _REF_H = "H";
	public static final String _REF_I = "I";
	public static final String _REF_K = "K";
	public static final String _REF_L = "L";
	public static final String _REF_LO = "LO";
	public static final String _REF_M = "M";
	public static final String _REF_N = "N";
	public static final String _REF_O = "O";
	public static final String _REF_ON = "ON";
	public static final String _REF_R = "R";
	public static final String _REF_RO = "RO";
	public static final String _REF_S = "S";
	public static final String _REF_SA = "SA";
	public static final String _REF_SU = "SU";
	public static final String _REF_SW = "SW";
	public static final String _REF_T = "T";
	public static final String _REF_TH = "TH";
	public static final String _REF_TP = "TP";
	public static final String _REF_TR = "TR";

	public static final String _REF_D = "D";
	public static final String _REF_TX = "TX";

	public static final String _REF_AT = "AT";

	public static final String BIZ_TRAVEL = "BIZ TRAVEL";
	public static final String EGENCIA = "EGENCIA";
	public static final String ClickTravel = "ClickTravel";
	public static final String Corpovia = "Corpovia";
	public static final String Ekotrip = "Ekotrip";
	public static final String ARGO_IT_TMS = "ARGO IT - TMS";
	public static final String GETTHERE = "GETTHERE";
	public static final String HRG_ONLINE = "HRG ONLINE";
	public static final String Cytric = "Cytric";
	public static final String Logic = "Logic";
	public static final String Goelett = "Goelett";
	public static final String CONCUR = "CONCUR";
	public static final String ONESTO = "ONESTO";
	public static final String OneClickTravel = "OneClickTravel";
	public static final String Rocketrip = "Rocketrip";
	public static final String SERKO = "SERKO";
	public static final String NeoKDS = "Neo/KDS";

	public static final String Deem = "Deem";

	public static final String OTHER = "OTHER";

	public static final String AUTRE = "AUTRE";
	public static final String ATRIIS = "ATRIIS";
	public static final String THOMALEX = "THOMALEX";
	public static final String SalesTrip = "SalesTrip";
	public static final String Supertripper = "Supertripper";
	public static final String Swift = "Swift";
	public static final String TRAVELDOO = "TRAVELDOO";
	public static final String theTreep = "theTreep";
	public static final String TravelPerk = "TravelPerk";
	public static final String Tripactions = "Tripactions";


	private RefTableREF_SBT() {

		m_lstChamps.add(new RefTableChamp(_REF_B, BIZ_TRAVEL, BIZ_TRAVEL, BIZ_TRAVEL));
		m_lstChamps.add(new RefTableChamp(_REF_C, EGENCIA, EGENCIA, EGENCIA));
		m_lstChamps.add(new RefTableChamp(_REF_CL, ClickTravel, ClickTravel, ClickTravel));
		m_lstChamps.add(new RefTableChamp(_REF_CO, Corpovia, Corpovia, Corpovia));
		m_lstChamps.add(new RefTableChamp(_REF_EK, Ekotrip, Ekotrip, Ekotrip));
		m_lstChamps.add(new RefTableChamp(_REF_F, ARGO_IT_TMS, ARGO_IT_TMS, ARGO_IT_TMS));
		m_lstChamps.add(new RefTableChamp(_REF_G, GETTHERE, GETTHERE, GETTHERE));
		m_lstChamps.add(new RefTableChamp(_REF_H, HRG_ONLINE, HRG_ONLINE, HRG_ONLINE));
		m_lstChamps.add(new RefTableChamp(_REF_I, Cytric, Cytric, Cytric));
		m_lstChamps.add(new RefTableChamp(_REF_K, NeoKDS, NeoKDS, NeoKDS));
		m_lstChamps.add(new RefTableChamp(_REF_LO, Logic, Logic, Logic));
		m_lstChamps.add(new RefTableChamp(_REF_M, Goelett, Goelett, Goelett));
		m_lstChamps.add(new RefTableChamp(_REF_N, CONCUR, CONCUR, CONCUR));
		m_lstChamps.add(new RefTableChamp(_REF_O, ONESTO, ONESTO, ONESTO));
		m_lstChamps.add(new RefTableChamp(_REF_ON, OneClickTravel, OneClickTravel, OneClickTravel));
		m_lstChamps.add(new RefTableChamp(_REF_R, Deem, Deem, Deem));
		m_lstChamps.add(new RefTableChamp(_REF_RO, Rocketrip, Rocketrip, Rocketrip));
		m_lstChamps.add(new RefTableChamp(_REF_S, SERKO, SERKO, SERKO));
		m_lstChamps.add(new RefTableChamp(_REF_SA, SalesTrip, SalesTrip, SalesTrip));
		m_lstChamps.add(new RefTableChamp(_REF_SU, Supertripper, Supertripper, Supertripper));
		m_lstChamps.add(new RefTableChamp(_REF_SW, Swift, Swift, Swift));
		m_lstChamps.add(new RefTableChamp(_REF_T, TRAVELDOO, TRAVELDOO, TRAVELDOO));
		m_lstChamps.add(new RefTableChamp(_REF_TH, theTreep, theTreep, theTreep));
		m_lstChamps.add(new RefTableChamp(_REF_TP, TravelPerk, TravelPerk, TravelPerk));
		m_lstChamps.add(new RefTableChamp(_REF_TR, Tripactions, Tripactions, Tripactions));
		m_lstChamps.add(new RefTableChamp(_REF_D, AUTRE, OTHER, OTHER));
		m_lstChamps.add(new RefTableChamp(_REF_AT, ATRIIS, ATRIIS, ATRIIS));
		m_lstChamps.add(new RefTableChamp(_REF_TX, THOMALEX, THOMALEX, THOMALEX));
	}

	public static RefTableREF_SBT instance() {
		if (null == m_RefTableREF_SBT) {
			m_RefTableREF_SBT = new RefTableREF_SBT();
		}
		return m_RefTableREF_SBT;
	}

}
