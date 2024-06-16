package com.airfrance.repind.entity.refTable;

public class RefTableREF_NIV_SEG_A extends RefTableMere {


	private static RefTableREF_NIV_SEG_A m_RefTableREF_NIV_SEG_A = null;

	public static final String _REF_A = "A";
	public static final String _REF_B = "B";
	public static final String _REF_C = "C";
	public static final String _REF_D = "D";
	public static final String _REF_N = "N";


	public static final String AGV_PARTENAIRE = "AGV PARTENAIRE";
	public static final String PARTNER_AGENCY = "PARTNER AGENCY";
	public static final String AGV_ASSOCIEE = "AGV ASSOCIEE";
	public static final String ASSOCIATED_AGEN = "ASSOCIATED AGEN";
	public static final String PRIVILEGED_AGEN = "PRIVILEGED AGEN";
	public static final String AGV_PRIVILEGIEE = "AGV PRIVILEGIEE";
	public static final String AGV_BASIQUE = "AGV BASIQUE";
	public static final String BASIC_AGENCY = "BASIC AGENCY";
	public static final String NEW_AGENCY = "NEW AGENCY";
	public static final String NOUVELLE_AGENCE = "NOUVELLE AGENCE";


	private RefTableREF_NIV_SEG_A() {

		m_lstChamps.add(new RefTableChamp(_REF_A, AGV_PARTENAIRE, PARTNER_AGENCY, "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_A, AGV_PARTENAIRE, PARTNER_AGENCY, "SGC"));
		m_lstChamps.add(new RefTableChamp(_REF_A, AGV_PARTENAIRE, PARTNER_AGENCY, "SGM"));
		m_lstChamps.add(new RefTableChamp(_REF_A, AGV_PARTENAIRE, PARTNER_AGENCY, "SGR"));
		m_lstChamps.add(new RefTableChamp(_REF_A, AGV_PARTENAIRE, PARTNER_AGENCY, "STO"));
		m_lstChamps.add(new RefTableChamp(_REF_B, AGV_ASSOCIEE, ASSOCIATED_AGEN, "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_B, AGV_ASSOCIEE, ASSOCIATED_AGEN, "SGC"));
		m_lstChamps.add(new RefTableChamp(_REF_B, AGV_ASSOCIEE, ASSOCIATED_AGEN, "SGM"));
		m_lstChamps.add(new RefTableChamp(_REF_B, AGV_ASSOCIEE, ASSOCIATED_AGEN, "SGR"));
		m_lstChamps.add(new RefTableChamp(_REF_B, AGV_ASSOCIEE, ASSOCIATED_AGEN, "STO"));
		m_lstChamps.add(new RefTableChamp(_REF_C, AGV_PRIVILEGIEE, PRIVILEGED_AGEN, "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_C, AGV_PRIVILEGIEE, PRIVILEGED_AGEN, "SGC"));
		m_lstChamps.add(new RefTableChamp(_REF_C, AGV_PRIVILEGIEE, PRIVILEGED_AGEN, "SGM"));
		m_lstChamps.add(new RefTableChamp(_REF_C, AGV_PRIVILEGIEE, PRIVILEGED_AGEN, "SGR"));
		m_lstChamps.add(new RefTableChamp(_REF_C, AGV_PRIVILEGIEE, PRIVILEGED_AGEN, "STO"));
		m_lstChamps.add(new RefTableChamp(_REF_D, AGV_BASIQUE, BASIC_AGENCY, "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_D, AGV_BASIQUE, BASIC_AGENCY, "SGC"));
		m_lstChamps.add(new RefTableChamp(_REF_D, AGV_BASIQUE, BASIC_AGENCY, "SGM"));
		m_lstChamps.add(new RefTableChamp(_REF_D, AGV_BASIQUE, BASIC_AGENCY, "SGR"));
		m_lstChamps.add(new RefTableChamp(_REF_D, AGV_BASIQUE, BASIC_AGENCY, "STO"));
		m_lstChamps.add(new RefTableChamp(_REF_N, NOUVELLE_AGENCE, NEW_AGENCY, "SGA"));
		m_lstChamps.add(new RefTableChamp(_REF_N, NOUVELLE_AGENCE, NEW_AGENCY, "SGC"));
		m_lstChamps.add(new RefTableChamp(_REF_N, NOUVELLE_AGENCE, NEW_AGENCY, "SGM"));
		m_lstChamps.add(new RefTableChamp(_REF_N, NOUVELLE_AGENCE, NEW_AGENCY, "SGR"));
		m_lstChamps.add(new RefTableChamp(_REF_N, NOUVELLE_AGENCE, NEW_AGENCY, "STO"));
	}

	public static RefTableREF_NIV_SEG_A instance() {
		if (null == m_RefTableREF_NIV_SEG_A) {
			m_RefTableREF_NIV_SEG_A = new RefTableREF_NIV_SEG_A();
		}
		return m_RefTableREF_NIV_SEG_A;
	}


}
