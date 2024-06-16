package com.airfrance.repind.entity.refTable;

public class RefTableCODE_INDUS extends RefTableMere {


	private static RefTableCODE_INDUS m_RefTableCODE_INDUS = null;

	public static final String _REF_01 = "01";
	public static final String _REF_02 = "02";
	public static final String _REF_03 = "03";
	public static final String _REF_04 = "04";
	public static final String _REF_05 = "05";
	public static final String _REF_06 = "06";
	public static final String _REF_07 = "07";
	public static final String _REF_08 = "08";
	public static final String _REF_09 = "09";
	public static final String _REF_10 = "10";
	public static final String _REF_11 = "11";
	public static final String _REF_12 = "12";
	public static final String _REF_13 = "13";
	public static final String _REF_14 = "14";
	public static final String _REF_15 = "15";
	public static final String _REF_16 = "16";
	public static final String _REF_17 = "17";
	public static final String _REF_18 = "18";
	public static final String _REF_19 = "19";
	public static final String _REF_20 = "20";
	public static final String _REF_21 = "21";
	public static final String _REF_22 = "22";
	public static final String _REF_23 = "23";
	public static final String _REF_24 = "24";
	public static final String _REF_25 = "25";
	public static final String _REF_26 = "26";
	public static final String _REF_27 = "27";


	private RefTableCODE_INDUS() {

		m_lstChamps.add(new RefTableChamp(_REF_01, "AUTO", "AUTO"));
		m_lstChamps.add(new RefTableChamp(_REF_02, "ENER", "ENER"));
		m_lstChamps.add(new RefTableChamp(_REF_03, "PHAR", "PHAR")); 
		m_lstChamps.add(new RefTableChamp(_REF_04, "CHEM", "CHEM"));
		m_lstChamps.add(new RefTableChamp(_REF_05, "TECH", "TECH"));
		m_lstChamps.add(new RefTableChamp(_REF_06, "CONS", "CONS"));
		m_lstChamps.add(new RefTableChamp(_REF_07, "POWE", "POWE"));
		m_lstChamps.add(new RefTableChamp(_REF_08, "BANK", "BANK"));
		m_lstChamps.add(new RefTableChamp(_REF_09, "GOOD", "GOOD"));
		m_lstChamps.add(new RefTableChamp(_REF_10, "SERV", "SERV"));
		m_lstChamps.add(new RefTableChamp(_REF_11, "TRAN", "TRAN"));
		m_lstChamps.add(new RefTableChamp(_REF_12, "GOVE", "GOVE"));
		m_lstChamps.add(new RefTableChamp(_REF_13, "NICH", "NICH")); 
		m_lstChamps.add(new RefTableChamp(_REF_14, "AERO", "AERO"));
		m_lstChamps.add(new RefTableChamp(_REF_15, "AIRF", "AIRF"));
		m_lstChamps.add(new RefTableChamp(_REF_16, "ENGI", "ENGI"));
		m_lstChamps.add(new RefTableChamp(_REF_17, "FOOD", "FOOD"));
		m_lstChamps.add(new RefTableChamp(_REF_18, "LUXE", "LUXE"));
		m_lstChamps.add(new RefTableChamp(_REF_19, "MEDI", "MEDI"));
		m_lstChamps.add(new RefTableChamp(_REF_20, "MINE", "MINE"));
		m_lstChamps.add(new RefTableChamp(_REF_21, "OILD", "OILD"));
		m_lstChamps.add(new RefTableChamp(_REF_22, "PROP", "PROP"));
		m_lstChamps.add(new RefTableChamp(_REF_23, "NGOS", "NGOS"));
		m_lstChamps.add(new RefTableChamp(_REF_24, "SPOR", "SPOR"));
		m_lstChamps.add(new RefTableChamp(_REF_25, "OTHE", "OTHE"));
		m_lstChamps.add(new RefTableChamp(_REF_26, "EDUC", "EDUC"));
		m_lstChamps.add(new RefTableChamp(_REF_27, "FORM", "FORM"));
	}

	public static RefTableCODE_INDUS instance() {
		if (null == m_RefTableCODE_INDUS) {
			m_RefTableCODE_INDUS = new RefTableCODE_INDUS();
		}
		return m_RefTableCODE_INDUS;
	}


}
