package com.airfrance.repind.entity.refTable;

public class RefTableREF_CODE_GDS extends RefTableMere {


	private static RefTableREF_CODE_GDS m_RefTableREF_CODE_GDS = null;

	public static final String _REF_1A = "1A";
	public static final String _REF_1B = "1B";
	public static final String _REF_1E = "1E";
	public static final String _REF_1F = "1F";
	public static final String _REF_1G = "1G";
	public static final String _REF_1J = "1J";
	public static final String _REF_1N = "1N";
	public static final String _REF_1P = "1P";
	public static final String _REF_1S = "1S";
	public static final String _REF_1U = "1U";
	public static final String _REF_1V = "1V";
	public static final String _REF_1Y = "1Y";
	public static final String _REF_A1 = "A1";
	public static final String _REF_AS = "AS";
	public static final String _REF_CE = "CE";
	public static final String _REF_CO = "CO";
	public static final String _REF_FA = "FA";
	public static final String _REF_GE = "GE";
	public static final String _REF_JT = "JT";
	public static final String _REF_KE = "KE";
	public static final String _REF_KM = "KM";
	public static final String _REF_NH = "NH";
	public static final String _REF_PA = "PA";
	public static final String _REF_RZ = "RZ";
	public static final String _REF_SD = "SD";
	public static final String _REF_SO = "SO";
	public static final String _REF_TQ = "TQ";
	public static final String _REF_TT = "TT";


	private RefTableREF_CODE_GDS() {

		m_lstChamps.add(new RefTableChamp(_REF_1A, "AMADEUS", "AMADEUS"));
		m_lstChamps.add(new RefTableChamp(_REF_1B, "ABACUS", "ABACUS"));
		m_lstChamps.add(new RefTableChamp(_REF_1E, "TRAVELSKY", "TRAVELSKY"));
		m_lstChamps.add(new RefTableChamp(_REF_1F, "INFINI", "INFINI"));
		m_lstChamps.add(new RefTableChamp(_REF_1G, "GALILEO", "GALILEO"));
		m_lstChamps.add(new RefTableChamp(_REF_1J, "AXESS", "AXESS"));
		m_lstChamps.add(new RefTableChamp(_REF_1N, "NAVITAIRE", "NAVITAIRE"));
		m_lstChamps.add(new RefTableChamp(_REF_1P, "WORLDSPAN", "WORLDSPAN"));
		m_lstChamps.add(new RefTableChamp(_REF_1S, "SABRE", "SABRE"));
		m_lstChamps.add(new RefTableChamp(_REF_1U, "ITA", "ITA"));
		m_lstChamps.add(new RefTableChamp(_REF_1V, "APOLLO", "APOLLO"));
		m_lstChamps.add(new RefTableChamp(_REF_1Y, "EDS", "EDS"));
		m_lstChamps.add(new RefTableChamp(_REF_A1, "G2 SWITCHWORKS", "G2 SWITCHWORKS"));
		m_lstChamps.add(new RefTableChamp(_REF_AS, "ATLA", "ATLA"));
		m_lstChamps.add(new RefTableChamp(_REF_CE, "CONCORDE", "CONCORDE"));
		m_lstChamps.add(new RefTableChamp(_REF_CO, "COVIA", "COVIA"));
		m_lstChamps.add(new RefTableChamp(_REF_FA, "FANTASIA", "FANTASIA"));
		m_lstChamps.add(new RefTableChamp(_REF_GE, "GEMINI", "GEMINI"));
		m_lstChamps.add(new RefTableChamp(_REF_JT, "JETSET", "JETSET"));
		m_lstChamps.add(new RefTableChamp(_REF_KE, "TOPAS", "TOPAS"));
		m_lstChamps.add(new RefTableChamp(_REF_KM, "KOMMAS", "KOMMAS"));
		m_lstChamps.add(new RefTableChamp(_REF_NH, "NEW HORIZONS", "NEW HORIZONS"));
		m_lstChamps.add(new RefTableChamp(_REF_PA, "PARS", "PARS"));
		m_lstChamps.add(new RefTableChamp(_REF_RZ, "RETZ", "RETZ"));
		m_lstChamps.add(new RefTableChamp(_REF_SD, "SODA", "SODA"));
		m_lstChamps.add(new RefTableChamp(_REF_SO, "SYSTEMONE", "SYSTEMONE"));
		m_lstChamps.add(new RefTableChamp(_REF_TQ, "TARMAQ", "TARMAQ"));
		m_lstChamps.add(new RefTableChamp(_REF_TT, "TOURISTS TECHNOLOGY", "TOURISTS TECHNOLOGY"));
	}

	public static RefTableREF_CODE_GDS instance() {
		if (null == m_RefTableREF_CODE_GDS) {
			m_RefTableREF_CODE_GDS = new RefTableREF_CODE_GDS();
		}
		return m_RefTableREF_CODE_GDS;
	}


}
