#include "CREF_REF_SS_DOMAINE.h"

CREF_REF_SS_DOMAINE *CREF_REF_SS_DOMAINE::m_pCREF_REF_SS_DOMAINE = 0;

RWCString CREF_REF_SS_DOMAINE::_REF_AB = "AB";
RWCString CREF_REF_SS_DOMAINE::_REF_AC = "AC";
RWCString CREF_REF_SS_DOMAINE::_REF_AD = "AD";
RWCString CREF_REF_SS_DOMAINE::_REF_BA = "BA";
RWCString CREF_REF_SS_DOMAINE::_REF_BB = "BB";
RWCString CREF_REF_SS_DOMAINE::_REF_BC = "BC";
RWCString CREF_REF_SS_DOMAINE::_REF_BD = "BD";
RWCString CREF_REF_SS_DOMAINE::_REF_BE = "BE";
RWCString CREF_REF_SS_DOMAINE::_REF_BF = "BF";
RWCString CREF_REF_SS_DOMAINE::_REF_BG = "BG";
RWCString CREF_REF_SS_DOMAINE::_REF_BH = "BH";
RWCString CREF_REF_SS_DOMAINE::_REF_BI = "BI";
RWCString CREF_REF_SS_DOMAINE::_REF_BJ = "BJ";
RWCString CREF_REF_SS_DOMAINE::_REF_BK = "BK";
RWCString CREF_REF_SS_DOMAINE::_REF_EB = "EB";
RWCString CREF_REF_SS_DOMAINE::_REF_EK = "EK";
RWCString CREF_REF_SS_DOMAINE::_REF_EO = "EO";
RWCString CREF_REF_SS_DOMAINE::_REF_GC = "GC";
RWCString CREF_REF_SS_DOMAINE::_REF_IC = "IC";
RWCString CREF_REF_SS_DOMAINE::_REF_LA = "LA";
RWCString CREF_REF_SS_DOMAINE::_REF_LB = "LB";
RWCString CREF_REF_SS_DOMAINE::_REF_LC = "LC";
RWCString CREF_REF_SS_DOMAINE::_REF_NB = "NB";
RWCString CREF_REF_SS_DOMAINE::_REF_NC = "NC";
RWCString CREF_REF_SS_DOMAINE::_REF_NK = "NK";
RWCString CREF_REF_SS_DOMAINE::_REF_NM = "NM";
RWCString CREF_REF_SS_DOMAINE::_REF_NP = "NP";
RWCString CREF_REF_SS_DOMAINE::_REF_PA = "PA";
RWCString CREF_REF_SS_DOMAINE::_REF_PG = "PG";
RWCString CREF_REF_SS_DOMAINE::_REF_PK = "PK";
RWCString CREF_REF_SS_DOMAINE::_REF_PO = "PO";
RWCString CREF_REF_SS_DOMAINE::_REF_PP = "PP";
RWCString CREF_REF_SS_DOMAINE::_REF_PR = "PR";
RWCString CREF_REF_SS_DOMAINE::_REF_QF = "QF";
RWCString CREF_REF_SS_DOMAINE::_REF_QG = "QG";
RWCString CREF_REF_SS_DOMAINE::_REF_QK = "QK";
RWCString CREF_REF_SS_DOMAINE::_REF_QO = "QO";
RWCString CREF_REF_SS_DOMAINE::_REF_QP = "QP";
RWCString CREF_REF_SS_DOMAINE::_REF_QR = "QR";
RWCString CREF_REF_SS_DOMAINE::_REF_RK = "RK";
RWCString CREF_REF_SS_DOMAINE::_REF_RO = "RO";
RWCString CREF_REF_SS_DOMAINE::_REF_VA = "VA";
RWCString CREF_REF_SS_DOMAINE::_REF_VI = "VI";
RWCString CREF_REF_SS_DOMAINE::_REF_VK = "VK";
RWCString CREF_REF_SS_DOMAINE::_REF_VO = "VO";
RWCString CREF_REF_SS_DOMAINE::_REF_VP = "VP";
RWCString CREF_REF_SS_DOMAINE::_REF_VR = "VR";
RWCString CREF_REF_SS_DOMAINE::_REF_VS = "VS";
RWCString CREF_REF_SS_DOMAINE::_REF_WA = "WA";
RWCString CREF_REF_SS_DOMAINE::_REF_WK = "WK";
RWCString CREF_REF_SS_DOMAINE::_REF_WP = "WP";
RWCString CREF_REF_SS_DOMAINE::_REF_WR = "WR";
RWCString CREF_REF_SS_DOMAINE::_REF_WS = "WS";
RWCString CREF_REF_SS_DOMAINE::_REF_YS = "YS";

CREF_REF_SS_DOMAINE::CREF_REF_SS_DOMAINE()
{
	try {
		this->lstChamps.append(new CREF_ChampTable(_REF_AB, "AFFAIRES/OFFLINE", "B2B OFFLINE/ONLINE", "32"));
		this->lstChamps.append(new CREF_ChampTable(_REF_AC, "AFFAIRES/INTERNET B2B", "B2B 100% ONLINE (OBT)", "60"));
		this->lstChamps.append(new CREF_ChampTable(_REF_AD, "AFFAIRES CONSOLIDATEUR", "BUSINESS CONSOLIDATOR", "98"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BA, "ETUDIANTS", "STUDENTS", "65"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BB, "CROISIERES", "CRUISES", "88"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BC, "LUXE/LA PREMIERE", "LUXURY/LA PREMIERE", "66"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BD, "MARINS/PETROLIERS", "SEAMEN/OFFSHORE", "89"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BE, "MISSIONNAIRES/ONG", "NGO/MISSIONARY", "90"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BF, "MILITAIRES", "MILITARY (MCG)", "91"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BG, "ETHNIQUE", "ETHNIC", "92"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BH, "MICE", "MICE", "94"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BI, "GROUP", "GROUP", "95"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BJ, "MEDICAL", "MEDICAL", "96"));
		this->lstChamps.append(new CREF_ChampTable(_REF_BK, "HYBRIDE", "HYBRID", "97"));
		this->lstChamps.append(new CREF_ChampTable(_REF_EB, "PRIME BLUEBIZ", "BLUEBIZ AWARD", "93"));
		this->lstChamps.append(new CREF_ChampTable(_REF_EK, "KLM", "KLM", "69"));
		this->lstChamps.append(new CREF_ChampTable(_REF_EO, "OUTSOURCE", "OUTSOURCE", "70"));
		this->lstChamps.append(new CREF_ChampTable(_REF_GC, "GENERALISTE CONSOLIDATEUR", "GENERALIST CONSOLIDATOR", "99"));
		this->lstChamps.append(new CREF_ChampTable(_REF_IC, "LOISIRS ONLINE CONSO", "ONLINE LEISURE CONSO", "100"));
		this->lstChamps.append(new CREF_ChampTable(_REF_LA, "LOISIRS/LOISIRS OFFLINE", "LEISURE/OFFLINE LEISURE", "62"));
		this->lstChamps.append(new CREF_ChampTable(_REF_LB, "TOUR OPERATEUR", "TOUR OPERATOR", "37"));
		this->lstChamps.append(new CREF_ChampTable(_REF_LC, "LOISIRS OFFLINE CONSO", "OFFLINE LEISURE CONSO", "63"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NB, "INTERNET AF B2B", "INTERNET AF B2B", "38"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NC, "INTERNET AF B2C", "INTERNET AF B2C", "39"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NK, "KLM", "KLM", "71"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NM, "CITYJET", "CITYJET", "40"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NP, "ASSISTANCE INTERNET", "WEB ASSISTANCE", "72"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PA, "CODE LOCAL", "CODE LOCAL", "53"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PG, "GSA CTO", "GSA CTO", "85"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PK, "KLM", "KLM", "73"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PO, "OUTSOURCE", "OUTSOURCE", "74"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PP, "AGAF PARIS", "MARCHE FRANCE", "41"));
		this->lstChamps.append(new CREF_ChampTable(_REF_PR, "AGAF PROVINCE", "MARCHE FRANCE", "42"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QF, "FRANCHISED ATO", "FRANCHISED ATO", "87"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QG, "GSA ATO", "GSA ATO", "86"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QK, "KLM", "KLM", "75"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QO, "OUTSOURCE", "OUTSOURCE", "76"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QP, "COMPTOIR APT PAR", "MARCHE FRANCE", "43"));
		this->lstChamps.append(new CREF_ChampTable(_REF_QR, "COMPTOIR APT PROV", "MARCHE FRANCE", "44"));
		this->lstChamps.append(new CREF_ChampTable(_REF_RK, "KLM", "KLM", "77"));
		this->lstChamps.append(new CREF_ChampTable(_REF_RO, "OUTSOURCE", "OUTSOURCE", "78"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VA, "CODE LOCAL", "CODE LOCAL", "51"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VI, "INTL RES CENTERS", "INTL RES CENTERS", "84"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VK, "KLM", "KLM", "79"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VO, "OUTSOURCE", "OUTSOURCE", "80"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VP, "VAD PARIS", "MARCHE FRANCE", "45"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VR, "VAD PROVINCE", "MARCHE FRANCE", "46"));
		this->lstChamps.append(new CREF_ChampTable(_REF_VS, "INTL CALL CENTERS", "INTL CALL CENTERS", "83"));
		this->lstChamps.append(new CREF_ChampTable(_REF_WA, "CODE LOCAL", "CODE LOCAL", "55"));
		this->lstChamps.append(new CREF_ChampTable(_REF_WK, "PLAT AFF KLM", "PLAT AFF KLM", "81"));
		this->lstChamps.append(new CREF_ChampTable(_REF_WP, "PLAT AFF PAR", "MARCHE FRANCE", "47"));
		this->lstChamps.append(new CREF_ChampTable(_REF_WR, "PLAT AFF PROV", "MARCHE FRANCE", "48"));
		this->lstChamps.append(new CREF_ChampTable(_REF_WS, "SBT", "SBT", "82"));
		this->lstChamps.append(new CREF_ChampTable(_REF_YS, "CIE SKYTEAM", "SKYTEAM AIRLINE", "50"));
		this->lstChamps.append(new CREF_ChampTable(_REF_NP, "VENTE PACKAGE", "PACKAGE SELL", "N"));
	}
	catch(...) {
		throw;
	}
}

CREF_REF_SS_DOMAINE::~CREF_REF_SS_DOMAINE()
{
}

CREF_REF_SS_DOMAINE * CREF_REF_SS_DOMAINE::Instance()
{
	try {
		if (!m_pCREF_REF_SS_DOMAINE) {
			m_pCREF_REF_SS_DOMAINE = new CREF_REF_SS_DOMAINE;
		}
		return m_pCREF_REF_SS_DOMAINE;
	}
	catch(...) {
		throw;
	}
}

