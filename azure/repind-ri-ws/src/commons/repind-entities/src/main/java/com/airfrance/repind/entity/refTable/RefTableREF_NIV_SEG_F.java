package com.airfrance.repind.entity.refTable;

public class RefTableREF_NIV_SEG_F extends RefTableMere {


	private static RefTableREF_NIV_SEG_F m_RefTableREF_NIV_SEG_F = null;

	public static final String _REF_L = "L";
	public static final String _REF_LAP = "LAP";
	public static final String _REF_LAT = "LAT";
	public static final String _REF_LKP = "LKP";
	public static final String _REF_LKT = "LKT";
	public static final String _REF_M = "M";
	public static final String _REF_MAP = "MAP";
	public static final String _REF_NS = "NS";
	public static final String _REF_S = "S";
	public static final String _REF_SME = "SME";
	public static final String _REF_SMP = "SMP";
	public static final String _REF_SMT = "SMT";
	public static final String _REF_XL = "XL";
	public static final String _REF_XS = "XS";
	public static final String _REF_XXL = "XXL";


	private RefTableREF_NIV_SEG_F() {

		m_lstChamps.add(new RefTableChamp(_REF_L, "FIRME L", "COMPANY L", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_L, "FIRME L", "COMPANY L", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_LAP, "PTE MOY ENT POT", "LOCAL ACC POT", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_LAP, "PTE MOY ENT POT", "LOCAL ACC POT", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_LAT, "GRANDE ENT", "LOCAL ACC TOP", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_LAT, "GRANDE ENT", "LOCAL ACC TOP", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_LKP, "GRANDE ENT POT", "LOCAL KEY POT", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_LKP, "GRANDE ENT POT", "LOCAL KEY POT", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_LKT, "GRAND GROUPE", "LOCAL KEY TOP", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_LKT, "GRAND GROUPE", "LOCAL KEY TOP", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_M, "FIRME M", "COMPANY M", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_M, "FIRME M", "COMPANY M", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_MAP, "MOY ENT POT", "MED ACC POT", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_MAP, "MOY ENT POT", "MED ACC POT", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_NS, "NON SEGMENTE", "NOT SEGMENTED", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_NS, "NON SEGMENTE", "NOT SEGMENTED", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "FIRME S", "COMPANY S", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_S, "FIRME S", "COMPANY S", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_SME, "PTE MOY ENT VR", "SM MED BUS VR", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_SME, "PTE MOY ENT VR", "SM MED BUS VR", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_SMP, "PTE ENT POT", "SM MED BUS POT", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_SMP, "PTE ENT POT", "SM MED BUS POT", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_SMT, "PTE MOY ENT", "SM MED BUSI TOP", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_SMT, "PTE MOY ENT", "SM MED BUSI TOP", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_XL, "FIRME XL", "COMPANY XL", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_XL, "FIRME XL", "COMPANY XL", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_XS, "FIRME XS", "COMPANY XS", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_XS, "FIRME XS", "COMPANY XS", "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_XXL, "FIRME XXL", "COMPANY XXL", "FVF"));
		m_lstChamps.add(new RefTableChamp(_REF_XXL, "FIRME XXL", "COMPANY XXL", "SGA"));
	}

	public static RefTableREF_NIV_SEG_F instance() {
		if (null == m_RefTableREF_NIV_SEG_F) {
			m_RefTableREF_NIV_SEG_F = new RefTableREF_NIV_SEG_F();
		}
		return m_RefTableREF_NIV_SEG_F;
	}


}
