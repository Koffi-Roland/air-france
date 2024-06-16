package com.airfrance.repind.entity.refTable;



public class RefTableBOOLEAN extends RefTableMere {


	private static RefTableBOOLEAN m_RefTableBOOLEAN = null;

	public static final String _REF_O = "O";
	public static final String _REF_N = "N";


	private RefTableBOOLEAN() {

		m_lstChamps.add(new RefTableChamp(_REF_N, "non", "no"));
      m_lstChamps.add(new RefTableChamp(_REF_O, "oui", "yes"));
	}

	public static RefTableBOOLEAN instance() {
		if (null == m_RefTableBOOLEAN) {
         m_RefTableBOOLEAN = new RefTableBOOLEAN();
		}
		return m_RefTableBOOLEAN;
	}


}
