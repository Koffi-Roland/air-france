package com.airfrance.repind.entity.refTable;

public class RefTableREF_CODE_TITRE extends RefTableMere {


	private static RefTableREF_CODE_TITRE m_RefTableREF_CODE_TITRE = null;

	public static final String _REF_ADM = "ADM";
	public static final String _REF_AMB = "AMB";
	public static final String _REF_ARC = "ARC";
	public static final String _REF_AVO = "AVO";
	public static final String _REF_BAR = "BAR";
	public static final String _REF_BRE = "BRE";
	public static final String _REF_CAP = "CAP";
	public static final String _REF_CAV = "CAV";
	public static final String _REF_CHA = "CHA";
	public static final String _REF_CJU = "CJU";
	public static final String _REF_CNT = "CNT";
	public static final String _REF_COL = "COL";
	public static final String _REF_COM = "COM";
	public static final String _REF_CON = "CON";
	public static final String _REF_COT = "COT";
	public static final String _REF_COW = "COW";
	public static final String _REF_DAM = "DAM";
	public static final String _REF_DEA = "DEA";
	public static final String _REF_DIP = "DIP";
	public static final String _REF_DIR = "DIR";
	public static final String _REF_DOC = "DOC";
	public static final String _REF_DOE = "DOE";
	public static final String _REF_DOT = "DOT";
	public static final String _REF_DUC = "DUC";
	public static final String _REF_EVE = "EVE";
	public static final String _REF_FRE = "FRE";
	public static final String _REF_GEN = "GEN";
	public static final String _REF_GOU = "GOU";
	public static final String _REF_HAD = "HAD";
	public static final String _REF_HIH = "HIH";
	public static final String _REF_HIN = "HIN";
	public static final String _REF_HJR = "HJR";
	public static final String _REF_HKF = "HKF";
	public static final String _REF_HNR = "HNR";
	public static final String _REF_HON = "HON";
	public static final String _REF_HRH = "HRH";
	public static final String _REF_ING = "ING";
	public static final String _REF_JUG = "JUG";
	public static final String _REF_LAD = "LAD";
	public static final String _REF_LIE = "LIE";
	public static final String _REF_LOR = "LOR";
	public static final String _REF_MAI = "MAI";
	public static final String _REF_MAJ = "MAJ";
	public static final String _REF_MAQ = "MAQ";
	public static final String _REF_MAR = "MAR";
	public static final String _REF_MIN = "MIN";
	public static final String _REF_MIS = "MIS";
	public static final String _REF_MMM = "MMM";
	public static final String _REF_MOG = "MOG";
	public static final String _REF_MRR = "MRR";
	public static final String _REF_MRS = "MRS";
	public static final String _REF_MSS = "MSS";
	public static final String _REF_NDA = "NDA";
	public static final String _REF_NUO = "NUO";
	public static final String _REF_PDG = "PDG";
	public static final String _REF_PER = "PER";
	public static final String _REF_PRE = "PRE";
	public static final String _REF_PRI = "PRI";
	public static final String _REF_PRL = "PRL";
	public static final String _REF_PRO = "PRO";
	public static final String _REF_PRS = "PRS";
	public static final String _REF_PRX = "PRX";
	public static final String _REF_RAB = "RAB";
	public static final String _REF_RAG = "RAG";
	public static final String _REF_REM = "REM";
	public static final String _REF_REP = "REP";
	public static final String _REF_REV = "REV";
	public static final String _REF_SAE = "SAE";
	public static final String _REF_SAL = "SAL";
	public static final String _REF_SAP = "SAP";
	public static final String _REF_SEN = "SEN";
	public static final String _REF_SEX = "SEX";
	public static final String _REF_SHE = "SHE";
	public static final String _REF_SID = "SID";
	public static final String _REF_SIR = "SIR";
	public static final String _REF_SOE = "SOE";
	public static final String _REF_VCP = "VCP";
	public static final String _REF_VIC = "VIC";
	public static final String _REF_VIT = "VIT";


	public static final String Baron = "Baron";
	public static final String PRESIDENT = "PRESIDENT";
	public static final String PROFESSEUR = "PROFESSEUR";
	public static final String SON_ALTESSE = "SON ALTESSE";

	private RefTableREF_CODE_TITRE() {

		m_lstChamps.add(new RefTableChamp(_REF_ADM, "Amiral", "Amiral"));
		m_lstChamps.add(new RefTableChamp(_REF_AMB, "Ambassadeur", "Ambassador"));
		m_lstChamps.add(new RefTableChamp(_REF_ARC, "Architecte", "Architect"));
		m_lstChamps.add(new RefTableChamp(_REF_AVO, "Avocat", "Lawyer"));
		m_lstChamps.add(new RefTableChamp(_REF_BAR, Baron, Baron));
		m_lstChamps.add(new RefTableChamp(_REF_BRE, "Baronne", Baron));
		m_lstChamps.add(new RefTableChamp(_REF_CAP, "Capitaine", "Captain"));
		m_lstChamps.add(new RefTableChamp(_REF_CAV, "Cavaliere", "Riding"));
		m_lstChamps.add(new RefTableChamp(_REF_CHA, "President", "President"));
		m_lstChamps.add(new RefTableChamp(_REF_CJU, "Chief Justice", "Chief Justice"));
		m_lstChamps.add(new RefTableChamp(_REF_CNT, "Comtesse", "Countess"));
		m_lstChamps.add(new RefTableChamp(_REF_COL, "Colonel", "Colonel"));
		m_lstChamps.add(new RefTableChamp(_REF_COM, "Commandant", "Commandant"));
		m_lstChamps.add(new RefTableChamp(_REF_CON, "Consul", "Consul"));
		m_lstChamps.add(new RefTableChamp(_REF_COT, "Comte", "Count"));
		m_lstChamps.add(new RefTableChamp(_REF_COW, "Commander", "Commander"));
		m_lstChamps.add(new RefTableChamp(_REF_DAM, "Dame", "Ram"));
		m_lstChamps.add(new RefTableChamp(_REF_DEA, "Dean", "Dean"));
		m_lstChamps.add(new RefTableChamp(_REF_DIP, "Herr Dipl-Ing", "Herr Dipl-Ing"));
		m_lstChamps.add(new RefTableChamp(_REF_DIR, "Directeur", "Director"));
		m_lstChamps.add(new RefTableChamp(_REF_DOC, "Docteur", "Doctor"));
		m_lstChamps.add(new RefTableChamp(_REF_DOE, "Doctoresse", "Lady Doctor"));
		m_lstChamps.add(new RefTableChamp(_REF_DOT, "Dottore Ing", "Dottore Ing"));
		m_lstChamps.add(new RefTableChamp(_REF_DUC, "Duc", "Duke"));
		m_lstChamps.add(new RefTableChamp(_REF_EVE, "Eveque", "bishop"));
		m_lstChamps.add(new RefTableChamp(_REF_FRE, "Frere", "Brother"));
		m_lstChamps.add(new RefTableChamp(_REF_GEN, "General", "General"));
		m_lstChamps.add(new RefTableChamp(_REF_GOU, "Gouverneur", "Governor"));
		m_lstChamps.add(new RefTableChamp(_REF_HAD, "Hadj", "Hadj"));
		m_lstChamps.add(new RefTableChamp(_REF_HIH, "HER HIGHNESS", "HER HIGHNESS"));
		m_lstChamps.add(new RefTableChamp(_REF_HIN, "Herr Dr Ing", "Herr Dr Ing"));
		m_lstChamps.add(new RefTableChamp(_REF_HJR, "Herr Dr Jur", "Herr Dr Jur"));
		m_lstChamps.add(new RefTableChamp(_REF_HKF, "HERR DIPL-KFM", "HERR DIPL-KFM"));
		m_lstChamps.add(new RefTableChamp(_REF_HNR, "THE HON MR", "THE HON MR"));
		m_lstChamps.add(new RefTableChamp(_REF_HON, "THE HON MRS", "THE HON MRS"));
		m_lstChamps.add(new RefTableChamp(_REF_HRH, "HIS HIGHNESS", "HIS HIGHNESS"));
		m_lstChamps.add(new RefTableChamp(_REF_ING, "INGENIEUR", "INGENIEUR"));
		m_lstChamps.add(new RefTableChamp(_REF_JUG, "JUGE", "JUGE"));
		m_lstChamps.add(new RefTableChamp(_REF_LAD, "LADY", "LADY"));
		m_lstChamps.add(new RefTableChamp(_REF_LIE, "LIEUTENANT", "LIEUTENANT"));
		m_lstChamps.add(new RefTableChamp(_REF_LOR, "LORD", "LORD"));
		m_lstChamps.add(new RefTableChamp(_REF_MAI, "MAITRE", "MAITRE"));
		m_lstChamps.add(new RefTableChamp(_REF_MAJ, "MAJOR", "MAJOR"));
		m_lstChamps.add(new RefTableChamp(_REF_MAQ, "MARQUIS", "MARQUIS"));
		m_lstChamps.add(new RefTableChamp(_REF_MAR, "MARQUISE", "MARQUISE"));
		m_lstChamps.add(new RefTableChamp(_REF_MIN, "MIN. AFF. ETR.", "MIN. AFF. ETR."));
		m_lstChamps.add(new RefTableChamp(_REF_MIS, "MADEMOISELLE", "MADEMOISELLE"));
		m_lstChamps.add(new RefTableChamp(_REF_MMM, "INDEFINIU", "INDEFINIU"));
		m_lstChamps.add(new RefTableChamp(_REF_MOG, "MONSEIGNEUR", "MONSEIGNEUR"));
		m_lstChamps.add(new RefTableChamp(_REF_MRR, "MONSIEUR", "MONSIEUR"));
		m_lstChamps.add(new RefTableChamp(_REF_MRS, "MADAME", "MADAME"));
		m_lstChamps.add(new RefTableChamp(_REF_MSS, "MS", "MS"));
		m_lstChamps.add(new RefTableChamp(_REF_NDA, "NOBIL DONA", "NOBIL DONA"));
		m_lstChamps.add(new RefTableChamp(_REF_NUO, "NOBIL UOMO", "NOBIL UOMO"));
		m_lstChamps.add(new RefTableChamp(_REF_PDG, PRESIDENT, PRESIDENT));
		m_lstChamps.add(new RefTableChamp(_REF_PER, "PERE", "PERE"));
		m_lstChamps.add(new RefTableChamp(_REF_PRE, PRESIDENT, PRESIDENT));
		m_lstChamps.add(new RefTableChamp(_REF_PRI, "MR LE PRINCE", "MR LE PRINCE"));
		m_lstChamps.add(new RefTableChamp(_REF_PRL, PRESIDENT, PRESIDENT));
		m_lstChamps.add(new RefTableChamp(_REF_PRO, PROFESSEUR, PROFESSEUR));
		m_lstChamps.add(new RefTableChamp(_REF_PRS, "PRINCESSE", "PRINCESSE"));
		m_lstChamps.add(new RefTableChamp(_REF_PRX, PROFESSEUR, PROFESSEUR));
		m_lstChamps.add(new RefTableChamp(_REF_RAB, "RABBI", "RABBI"));
		m_lstChamps.add(new RefTableChamp(_REF_RAG, "RAGIONIERE", "RAGIONIERE"));
		m_lstChamps.add(new RefTableChamp(_REF_REM, "REVERENDE MERE", "REVERENDE MERE"));
		m_lstChamps.add(new RefTableChamp(_REF_REP, "REVEREND PERE", "REVEREND PERE"));
		m_lstChamps.add(new RefTableChamp(_REF_REV, "REVEREND", "REVEREND"));
		m_lstChamps.add(new RefTableChamp(_REF_SAE, SON_ALTESSE, SON_ALTESSE));
		m_lstChamps.add(new RefTableChamp(_REF_SAL, SON_ALTESSE, SON_ALTESSE));
		m_lstChamps.add(new RefTableChamp(_REF_SAP, "SA LA PRINCESSE", "SA LA PRINCESSE"));
		m_lstChamps.add(new RefTableChamp(_REF_SEN, "SENATEUR", "SENATEUR"));
		m_lstChamps.add(new RefTableChamp(_REF_SEX, "SON EXCELLENCE", "SON EXCELLENCE"));
		m_lstChamps.add(new RefTableChamp(_REF_SHE, "SHEIKH", "SHEIKH"));
		m_lstChamps.add(new RefTableChamp(_REF_SID, "THE SIRDAR", "THE SIRDAR"));
		m_lstChamps.add(new RefTableChamp(_REF_SIR, "SIR", "SIR"));
		m_lstChamps.add(new RefTableChamp(_REF_SOE, "SOEUR", "SOEUR"));
		m_lstChamps.add(new RefTableChamp(_REF_VCP, "VICE PRESIDENT", "VICE PRESIDENT"));
		m_lstChamps.add(new RefTableChamp(_REF_VIC, "VICOMTE", "VICOMTE"));
		m_lstChamps.add(new RefTableChamp(_REF_VIT, "VICOMTESSE", "VICOMTESSE"));
	}

	public static RefTableREF_CODE_TITRE instance() {
		if (null == m_RefTableREF_CODE_TITRE) {
			m_RefTableREF_CODE_TITRE = new RefTableREF_CODE_TITRE();
		}
		return m_RefTableREF_CODE_TITRE;
	}


}
