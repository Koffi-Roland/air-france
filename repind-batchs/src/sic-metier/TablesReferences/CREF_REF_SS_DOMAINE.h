#ifndef CREF_REF_SS_DOMAINE_h
#define CREF_REF_SS_DOMAINE_h 1

#include "CREF_Mere.h"

class CREF_REF_SS_DOMAINE : public CREF_Mere
{

	public :

		CREF_REF_SS_DOMAINE();

		~CREF_REF_SS_DOMAINE();
		static CREF_REF_SS_DOMAINE * Instance ();
		static const CREF_REF_SS_DOMAINE * const& get_pCREF_REF_SS_DOMAINE ();
		static void set_pCREF_REF_SS_DOMAINE (CREF_REF_SS_DOMAINE *& value);

		static RWCString _REF_AB;

		static RWCString _REF_AC;

		static RWCString _REF_AD;

		static RWCString _REF_BA;

		static RWCString _REF_BB;

		static RWCString _REF_BC;

		static RWCString _REF_BD;

		static RWCString _REF_BE;

		static RWCString _REF_BF;

		static RWCString _REF_BG;

		static RWCString _REF_BH;

		static RWCString _REF_BI;

		static RWCString _REF_BJ;
		
		static RWCString _REF_BK;

		static RWCString _REF_EB;

		static RWCString _REF_EK;

		static RWCString _REF_EO;

		static RWCString _REF_GC;

		static RWCString _REF_IC;

		static RWCString _REF_LA;

		static RWCString _REF_LB;

		static RWCString _REF_LC;

		static RWCString _REF_NB;

		static RWCString _REF_NC;

		static RWCString _REF_NK;

		static RWCString _REF_NM;

		static RWCString _REF_NP;

		static RWCString _REF_PA;

		static RWCString _REF_PG;

		static RWCString _REF_PK;

		static RWCString _REF_PO;

		static RWCString _REF_PP;

		static RWCString _REF_PR;

		static RWCString _REF_QF;

		static RWCString _REF_QG;

		static RWCString _REF_QK;

		static RWCString _REF_QO;

		static RWCString _REF_QP;

		static RWCString _REF_QR;

		static RWCString _REF_RK;

		static RWCString _REF_RO;

		static RWCString _REF_VA;

		static RWCString _REF_VI;

		static RWCString _REF_VK;

		static RWCString _REF_VO;

		static RWCString _REF_VP;

		static RWCString _REF_VR;

		static RWCString _REF_VS;

		static RWCString _REF_WA;

		static RWCString _REF_WK;

		static RWCString _REF_WP;

		static RWCString _REF_WR;

		static RWCString _REF_WS;

		static RWCString _REF_YS;

	protected :

		static CREF_REF_SS_DOMAINE *m_pCREF_REF_SS_DOMAINE;

};
inline const CREF_REF_SS_DOMAINE * const& CREF_REF_SS_DOMAINE::get_pCREF_REF_SS_DOMAINE ()
{
	return m_pCREF_REF_SS_DOMAINE;
}

inline void CREF_REF_SS_DOMAINE::set_pCREF_REF_SS_DOMAINE (CREF_REF_SS_DOMAINE *& value)
{
	m_pCREF_REF_SS_DOMAINE = value;
}

#endif
