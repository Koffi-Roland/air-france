/*-------------------------------------------------------
/  Fichier Common.h
/
/--------------------------------------------------------
/     29/01/01   : chris : common structures
/ 2003/07/07 : mise a la norme de Pelora (H.Maders)
/ VERSION :
/ - VISTA-DM2007060151-Add origin of the channel in the
/   bookings an in reports -> __LgOriginChannel=1
/ - VISTA-DEM-2007050619-DAI screen change in CPL :
/   + Add connecting flight (CNFL).
/   + Add frequency, origin and destination stations
/ - pelora v01.14-OT353-PENDING LIST for E-bookings
/   Add option "W" -> is a DUB:A restricted to bookings
/   which origin of the channel is :
/   FFR Traxon, JV, Interline, GFX and CPS
/   S24552 -> S24553 :
/   - struct WContexteResa_Type:
/     + IndicInterline
/     + IndicSplit
/   - struct WDUB_CPS_Type is renamed in WDUB_Type to
/   make the source clear:
/     + interline
/     + other
// - TC08.01 cgoisa v02.00. Loose in ULDs add ons.
//   Pelican DUB:E and DUB:J
//   -> cgoisa DUB option "J" Sort option "2"
//   S24553 -> S24554 :
//   - struct WContexteResa_Type:
//     ! IndicJV receives "Indic_reservation_autre"
//       For DUB option J, it's used by Pelican to
//       select booking segments from Ebi only
//     + IndicEbs -> bool  _indicEbs
//     + IndicClu -> bool  _indicClu
//   !!! WARNING : struct WDUB_Type is not modified !!!
//       no "WIndic_Type ebs" and "no WIndic_Type clu"
//       in out
/------------------------------------------------------*/

#ifndef _COMMON_H
#define _COMMON_H


#define __LgService     5   // Nom du service 


// ADHESION STANDARD STRUCTURES


/* --------------------------------------------------- */
/* Habilitation header                                 */
/* --------------------------------------------------- */

#define __Uid		8   // User id.
#define __Pwd		8   // Password
#define __Qualif	3   // Qualifier
#define __Key		16  // Cripted Key
#define __NewPwd	8   // New Password
#define __UserRole  	20  // Role


#ifndef _WUSER_TYPE
#define _WUSER_TYPE
typedef struct {
  char UserId[__Uid + 1];             /*Utilisateur*/
  char PassWord[__Pwd + 1];           /*Mot de Passe*/
  char Qualif[__Qualif + 1];          /*Qualifier*/
  char Key[__Key + 1];                /*cle criptee*/
  char NewPassword[__NewPwd + 1];     /*Nouveau Psswd*/
  char UserRole[__UserRole + 1];      /*Role*/
} WUser_Type;
#endif


/* --------------------------------------------------- */
/* Security header                                     */
/* --------------------------------------------------- */

#define __Agnt          8   // Agent number
#define __HCie          3   // Cie Hote
#define __Lang          2   // langue
#define __SIEsc         5   // escale de signature 
#define __Ugrp          2   // User Group
#define __maxUlvl       36  // User level  
#define __Ulvl          1


#ifndef _WSECULEVEL_TYPE
#define _WSECULEVEL_TYPE
typedef struct {
  char UserLevel[__Ulvl + 1];         /*User level*/   		       
} WSecuLevel_Type;
#endif


#ifndef _WSECU_TYPE
#define _WSECU_TYPE
typedef struct {
  char AgentId[__Agnt +1];            /*Agent ID*/
  char HtCie[__HCie + 1];             /*code Cie Hote*/
  char Language[__Lang + 1];          /*language*/
  char SIStation[__SIEsc + 1];        /*escale de signature*/
  char UserGroup[__Ugrp + 1];         /*User group*/
  WSecuLevel_Type Level[__maxUlvl ];  /*User level*/   	       
} WSecu_Type;
#endif


/* --------------------------------------------------- */
/* Error header                                        */
/* --------------------------------------------------- */

// Error code structure
#define __ErrCode	5   // Error code
#define __ErrTxt	64  // Error text


#ifndef _WERR_TYPE
#define _WERR_TYPE
typedef struct {
  char ErrorCode[__ErrCode + 1];              /*Code retour*/
  char ErrorText[__ErrTxt + 1];               /*Libelle Erreur*/
} WErr_Type;
#endif


#ifndef _WCODERR_TYPE
#define _WCODERR_TYPE 
typedef struct {
  char ErrorCode[ __ErrCode + 1];
} WCodErr_Type;
#endif


// OTHER COMMON STRUCTURES

/* --------------------------------------------------- */
/* Date                                                */
/* --------------------------------------------------- */

#define __LgDate	8   // Date "YYYYMMDD"
#define __LgDateLong	12  // Date "YYYYMMDDHHMM"
#define __LgDateHour    14  // Date "YYYYMMDDHHMMSS" (Pelora)


#ifndef _WDATE_TYPE
#define _WDATE_TYPE
typedef struct {
  char Date[__LgDate + 1];
} WDate_Type;
#endif


// for this real example S18350
#define __LgRefNote     	4   // Reference Note length
#define __LgVersionNote 	3   // Version Note length
#define __LgLigneTexte  	67  // Ligne de texte d'une Note length
#define __LgStatutNote  	1   // Statut Notelength
#define __LgIataResolution 	10  // Iata Resolution length

#ifndef _WLIGNETEXTE_TYPE
#define _WLIGNETEXTE_TYPE 
typedef struct {
  char LigneTexte[ __LgLigneTexte + 1];
} WLigneTexte_Type;
#endif


/* --------------------------------------------------- */
/* Cie                                                 */
/* --------------------------------------------------- */

#define __LgCie   	        3   // Code Cie


#ifndef _WCODECIE_TYPE
#define _WCODECIE_TYPE
typedef struct {
  char             CodeCie[__LgCie + 1];
} WCodeCie_Type;
#endif


// SPECIFIC STRUCTURES : Pelora

// Maximum number of structures in Input/Output
const int __maxSplDUB = 5;       // Input/Output Spl maximum number
const int __maxSplDRF = 5;       // Input/Output Spl maximum number DRF
const int __maxAgentDUB = 8;     // Input Agent maximum number
const int __maxDUBRes = 45;      // Output DUB Reference maximum number
const int __maxDRFRes = 80;      // Output DRF Reference maximum number

// Maximum length of data in I/O and I/O structures

/* Nombre de references DUB trouvees */
const int __nbRefTrouveeDUB = 4;

/* nombre de ref retournées sur 1 appel */
const int __nbRefRenvoyees = 2;
#ifndef _WNBREFRENVOYEES_TYPE
#define _WNBREFRENVOYEES_TYPE
typedef struct{
  char NbRefRenvoyees[__nbRefRenvoyees + 1];
}WNbRefRenvoyees_Type;
#endif

/* nombre total de ref trouvées */
const int __nbRefTrouvees = 4;
#ifndef _WNBREFTROUVEES_TYPE
#define _WNBREFTROUVEES_TYPE
typedef struct{
  char NbRefTrouvees[__nbRefTrouvees + 1];
}WNbRefTrouvees_Type;
#endif

/* INDICATEUR */
const int __indic = 1 ;
#ifndef _WINDIC_TYPE
#define _WINDIC_TYPE
typedef struct{
  char Indic[__indic + 1];
}WIndic_Type;
#endif 


/* CONTINUITE I/O */
const int __indicateurContinuite = 1;

#ifndef _WCONTINUITE_TYPE
#define _WCONTINUITE_TYPE
typedef struct{
        char IndicateurContinuite[__indicateurContinuite + 1];
}WContinuite_Type;
#endif

/* ref pour continuite */
const int __refContiDUB = 9;
#ifndef _WREFCONTINUITEDUB_TYPE
#define _WREFCONTINUITEDUB_TYPE
typedef struct{
  char IndicateurContinuiteDUB[__refContiDUB + 1];
}WContinuiteDUB_Type;
#endif

/* ref pour continuite DRF */
const int __refContiDRF = 11;
#ifndef _WREFCONTINUITEDRF_TYPE
#define _WREFCONTINUITEDRF_TYPE
typedef struct{
  char IndicateurContinuiteDRF[__refContiDRF + 1];
}WContinuiteDRF_Type;
#endif

/* FLAG YES OR NOT */
const int __flagYesOrNot = 1;
#ifndef _WFLAGEYESORNOT_TYPE
#define _WFLAGEYESORNOT_TYPE
typedef struct{
  char FlagYesOrNot[ __flagYesOrNot + 1];
}WFlagYesOrNot_Type;
#endif

/* CODE ACTION BCTL FCTLT */
const int __codeAction = 2; /*Code_action_de_la_BCTL_(OA)*/
#ifndef _WCODEACTION_TYPE   
#define _WCODEACTION_TYPE
typedef struct{
  char CodeAction[ __codeAction + 1];
}WCodeAction_Type;
#endif

const int __codeStatut = 2; /*Code_statut_FCTL_(CA)*/
#ifndef _WCODESTATUT_TYPE
#define _WCODESTATUT_TYPE
typedef struct{
  char CodeStatut[ __codeStatut + 1];
}WCodeStatut_Type;
#endif

const int __codeRefus = 3;
#ifndef _WCODEREFUS_TYPE
#define _WCODEREFUS_TYPE
typedef struct{
  char CodeRefus[ __codeRefus + 1];
}WCodeRefus_Type;
#endif

/* DUB */

const int __codeOptionDub = 1;  /*Code_option_DUB_(ancien_Type_de_reservation)*/
#ifndef _WDUBCODEOPTION_TYPE
#define _WDUBCODEOPTION_TYPE
typedef struct{
  char CodeOptionDub[ __codeOptionDub + 1];
}WCodeOptionDub_Type;
#endif

const int __codeOptionDrf = 1;  /*Code_option_DRF*/
#ifndef _WDRFCODEOPTION_TYPE
#define _WDRFCODEOPTION_TYPE
typedef struct{
  char CodeOptionDrf[ __codeOptionDrf + 1];
}WCodeOptionDrf_Type;
#endif

const int __codeOptionTri = 1; /*Code_option_du_tri*/
#ifndef _WTRICODEOPTION_TYPE
#define _WTRICODEOPTION_TYPE
typedef struct{
  char CodeOptionTri[ __codeOptionTri + 1];
}WCodeOptionTri_Type;
#endif

/* ESCALE */
const int __codeEscale = 5;
#ifndef _WCODEESCALE_TYPE
#define _WCODEESCALE_TYPE
typedef struct{
  char CodeEscale[__codeEscale + 1];
}WCodeEscale_Type;
#endif

/* SECTEUR ET SOUS SECTEUR */
const int __codeSecteur = 5;
#ifndef _WCODESECTEUR_TYPE
#define _WCODESECTEUR_TYPE
typedef struct{
  char CodeSecteur[__codeSecteur + 1];
}WCodeSecteur_Type;
#endif


/* ACN */
const int __codeAcn = 3;
#ifndef _WCODEACN_TYPE
#define _WCODEACN_TYPE
typedef struct{
  char CodeAcn[__codeAcn + 1];
}WCodeAcn_Type;
#endif

/* COMPAGNIE */
const int __codeCompagnie = 3;
#ifndef _WCODECOMPAGNIE_TYPE
#define _WCODECOMPAGNIE_TYPE
typedef struct{
  char CodeCompagnie[__codeCompagnie + 1];
}WCodeCompagnie_Type;
#endif

/* FLIGHT */
const int __flightNum = 4;
const int __flightSuffix = 1;
#ifndef _WFLIGHT_TYPE
#define _WFLIGHT_TYPE
typedef struct{
  WCodeCompagnie_Type Cie;
  WCodeCompagnie_Type CieShared;
  char FlightNum[__flightNum + 1];
  char FlightSuffix[__flightSuffix + 1];
}WFlight_Type;
#endif

/* DATE */
const int __date = 8 ;
#ifndef _WDATE_TYPE
#define _WDATE_TYPE
typedef struct{
  char Date[__date + 1];
}WDate_Type;
#endif


/* FLIGHT DATE */
#ifndef _WFLIGHTDATE_TYPE
#define _WFLIGHTDATE_TYPE
typedef struct{
  WFlight_Type Flight;
  WDate_Type FlightDate;
}WFlightDate_Type;
#endif

/* TIME */
const int __waitingTime = 3 ;
#ifndef _WWAITINGTIME_TYPE
#define _WWAITINGTIME_TYPE
typedef struct{
  char WaitingTime[__waitingTime + 1];
}WWaitingTime_Type;
#endif

/* EXPRESS */
const int __express = 1 ;
#ifndef _WEXPRESS_TYPE
#define _WEXPRESS_TYPE
typedef struct{
  char Express[__express + 1];
}WExpresse_Type;
#endif

/* REF AGENT */
const int __refAgent = 6 ;
#ifndef _WREFAGENT_TYPE
#define _WREFAGENT_TYPE
typedef struct{
  char RefAgent[__refAgent + 1];
}WAgentRef_Type;
#endif

/* PLAGE HORAIRE */
const int __hhmm = 4 ; 
#ifndef _WTIMERANGE_TYPE
#define _WTIEMRANGE_TYPE
typedef struct{
  char TimeRange[__hhmm + 1];
}WTimeRange_Type;
#endif

/* SEGMENT */
const int __codeSegment = 3 ; /*Code_segment_commercial*/
#ifndef _WCODESEGMENT_TYPE
#define _WCODESEGMENT_TYPE
typedef struct{
  char CodeSegment[__codeSegment + 1];
}WCodeSegment_Type;
#endif

/* PRODUIT */
const int __codeProduit = 3 ; /*Code_produit_commercial*/
#ifndef _WCODEPRODUIT_TYPE
#define _WCODEPRODUIT_TYPE
typedef struct{
  char CodeProduit[__codeProduit + 1];
}WCodeProduit_Type;
#endif

/* RESA */
const int __bookingType = 3 ;  /*Type_resa_(DBI_DBC_DBS_DCH_DAI)*/
#ifndef _WBOOKINGTYPE_TYPE
#define _WBOOKINGTYPE_TYPE
typedef struct{
  char BookingType[__bookingType + 1];
}WBookingType_Type;
#endif

const int __bookingClass = 1 ;
#ifndef _WBOOKINGCLASS_TYPE /*Classe_de_reservation_(1_ou_2_ou__A_)*/
#define _WBOOKINGCLASS_TYPE
typedef struct{
  char BookingClass[__bookingClass + 1];
}WBookingClass_Type;
#endif 

const int __nbColis = 3 ;
#ifndef _WNBCOLIS_TYPE
#define _WNBCOLIS_TYPE
typedef struct{
  char NbColis[__nbColis + 1];
}WNbColis_Type;
#endif 

const int __weight = 6 ;
#ifndef _WWEIGHT_TYPE
#define _WWEIGHT_TYPE
typedef struct{
  char Weight[__weight + 1];
}WWeight_Type;
#endif 

const int __nbPontSouteCtr = 2 ;
#ifndef _WNBPONTSOUTECTR_TYPE
#define _WNBPONTSOUTECTR_TYPE
typedef struct{
  char NbPontSouteCtr[__nbPontSouteCtr + 1];
}WNbPontSouteCtr_Type;
#endif 

const int __recette = 9 ;
#ifndef _WRECETTE_TYPE
#define _WRECETTE_TYPE
typedef struct{
  char Recette[__recette + 1];
}WRecette_Type;
#endif 

const int __contexte = 1 ;
#ifndef _WCONTEXTERESA_TYPE
#define _WCONTEXTERESA_TYPE
typedef struct{
  char IndicTraxon[__contexte + 1];
  char IndicGfx[__contexte + 1];
  char IndicCps[__contexte + 1];
  char IndicInterline[__contexte + 1];
  char IndicSplit[__contexte + 1];
  char IndicJV[__contexte + 1];
  char IndicEbs[__contexte + 1];
  char IndicClu[__contexte + 1];
}WContexteResa_Type;
#endif 

#ifndef _WCONTEXTERESA_CPS_TYPE
#define _WCONTEXTERESA_CPS_TYPE
typedef struct{
  char IndicTraxon[__contexte + 1];
  char IndicGfx[__contexte + 1];
  char IndicCps[__contexte + 1];
  char IndicJV[__contexte + 1];
}WContexteResa_CPS_Type;
#endif 


/* SPL */
const int __codeParticulariteSPL = 3 ;
#ifndef _WCODEPARTICULARITESPL_TYPE
#define _WCODEPARTICULARITESPL_TYPE
typedef struct{
  char CodeParticulariteSPL[__codeParticulariteSPL + 1];
}WCodeParticulariteSPL_Type;
#endif 

/* REFERENCE */
const int __refLTA = 12 ;
#ifndef _WREFLTA_TYPE
#define _WREFLTA_TYPE
typedef struct{
  char RefLta[__refLTA + 1];
}WRefLta_Type;
#endif 

const int __refCLU = 8 ;
#ifndef _WREFCLU_TYPE
#define _WREFCLU_TYPE
typedef struct{
  char RefClu[__refCLU + 1];
}WRefClu_Type;
#endif 

const int __refALLO = 14 ;
#ifndef _WREFALLO_TYPE
#define _WREFALLO_TYPE
typedef struct{
  char RefAllo[__refALLO + 1];
}WRefAllo_Type;
#endif 

const int __refECH = 19 ; //echeancier
#ifndef _WREFECH_TYPE
#define _WREFECH_TYPE
typedef struct{
  char RefEch[__refECH + 1];
}WRefEch_Type;
#endif  

const int __numVersion = 3 ; 
#ifndef _WNUMVERSION_TYPE
#define _WNUMVERSION_TYPE
typedef struct{
  char NumVersion[__numVersion + 1];
}WNumVersion_Type;
#endif 

const int __statutMagasin = 3 ;
#ifndef _WSTATUTMAG_TYPE
#define _WSTATUTMAG_TYPE
typedef struct{
  char StatutMagasin[__statutMagasin + 1];
}WStatutMagasin_Type;
#endif 

const int __numLigResa = 1 ; //numero ligne resa
#ifndef _WNUMLIGRESA_TYPE
#define _WNUMLIGRESA_TYPE
typedef struct{
  char NumLigResa[__numLigResa + 1];
}WNumLigResa_Type;
#endif

/* PARTICIPANT */
const int __agentName = 35 ; //nom de l'agent
#ifndef _WAGENTNAME_TYPE
#define _WAGENTNAME_TYPE
typedef struct{
  char AgentName[__agentName + 1];
}WAgentName_Type;
#endif


#define __LgIataGroup                         9
const int __agentCass = 14 ; //agent CASS IATA/CASS
#ifndef _WAGENTCASS_TYPE
#define _WAGENTCASS_TYPE
typedef struct{
  char AgentCass[__agentCass + 1];
}WAgentCass_Type;
#endif

#define __LgTypeParticipant 1
#ifndef _WPARTICIPANT_TYPE
#define _WPARTICIPANT_TYPE
typedef struct{
  WAgentRef_Type ref;
  WAgentName_Type name;
  WCodeEscale_Type station;
  WAgentCass_Type cass;
}WParticipant_Type;
#endif

#ifndef _WDUB_Type
#define _WDUB_Type
typedef struct {
  WBookingType_Type bookingType;
  WBookingClass_Type bookingClass;
  WWaitingTime_Type waitingTime;  /*Temps_d_attente_en_heures*/
  WCodeEscale_Type escBctl ;  /*Escale_de_controle_de_la_reservation_(BCTL)*/
  WCodeEscale_Type escOrigSeg ; /*Escale_origine_du_segment_de_reservation*/
  WCodeEscale_Type escDestSeg ; /*Escale_destination_du_segment_de_reservation*/
  WCodeEscale_Type escDestXp ;   /*Escale_destination_de_l_expedition*/
  WNbColis_Type nbColis;
  WWeight_Type weight;
  WNbPontSouteCtr_Type pont;  /*Nbre_de_palettes_Pont*/
  WNbPontSouteCtr_Type  soute; /*Nbre_de_palettes_Soute*/
  WNbPontSouteCtr_Type ctr; /*Nbre_de_containers*/
  WIndic_Type split; /*Indicateur_de_reservation_splittee*/
  WIndic_Type traxon; /*Indicateur_de_reservation_Traxon*/
  WIndic_Type gfx; /*Indicateur_de_reservation_GF_X*/
  WIndic_Type cps; /* Indicateur de reservation CPS */
  WIndic_Type interline; /*Indicateur_de_reservation_INTERLINE*/
  WIndic_Type jv; /*Indicateur de reservation JV*/
//!!! S24554 WARNING : struct WDUB_Type is not modified !!!
//WIndic_Type ebs; /*Indicateur de reservation Ebs*/
//WIndic_Type clu; /*Indicateur de reservation Clu*/
//!!! S24554 WARNING : struct WDUB_Type is not modified !!!
  WCodeAction_Type actionCode;
  WCodeStatut_Type statutCode;
  WRecette_Type annonceOri;  /*Recette_Annoncee_Origine*/
  WRecette_Type moyenneKiloOri;  /*Recette_Moyenne_au_Kilo_Origine*/
  WCodeRefus_Type codeRefus;  /*Code_refus_de_reservation_(numero_d_erreur_Yield)_XBERRY*/
  WIndic_Type nonPaletisable ;  /*Indicateur_vrac_non_palettisable_(FBIMAN_=_L)*/
  WCodeParticulariteSPL_Type  spl[__maxSplDUB ] ;
  WRefLta_Type refLta;
  WRefClu_Type refClu;
  WRefAllo_Type refAllo;
  WRefEch_Type refEch;
  WNumVersion_Type numVersion;
  WStatutMagasin_Type magStatut;
  WNumLigResa_Type numLigResa;
  WFlightDate_Type resaFlight; /* Vol_de_reservation*/
  WFlightDate_Type arrivingFlight; /*Vol_d_apport*/
  WParticipant_Type participant;
  WCodeSegment_Type comSeg ;/*Code_segment_commercial*/
  WCodeProduit_Type comProduct ;/*Code_produit_commercial*/
  WIndic_Type altFlight ; /* Indicatuer_de_vol_alternative  */
} WDUB_Type;                                     /*Data*/
#endif


const int __poids = 9 ;
const int __poidsUnit = 2 ;
const int __volume = 12 ;
const int __volumeUnit = 2 ;
const int __nbSplsDrf = 5 ;
const int __dimsUnit = 2 ;
const int __dimsLong = 4 ;
const int __dimsLarg = 4 ;
const int __dimsHaut = 4 ;
#ifndef _WDRF_Type
#define _WDRF_Type
typedef struct {
  WCodeEscale_Type            EscOrig ;             /*Escale d'origine*/
  WCodeEscale_Type            EscConso ;            /*Escale du hub de consolidation*/
  WCodeEscale_Type            EscRegroup ;          /*Escale du hub de regroupement*/
  WCodeEscale_Type            EscOffl ;             /*Escale offload*/
  WCodeEscale_Type            EscDest ;             /*Escale destination*/
  WRefLta_Type                RefLta ;              /*Reference Lta*/
  WNbColis_Type               NbreColis ;             /*Nombre de colis*/
  char                        Poids[__poids + 1] ;  /*Poids*/
  char                        UnitePoids[__poidsUnit + 1] ;  /*Unite de poids*/
  char                        Volume[__volume + 1] ;  /*Volume*/
  char                        UniteVolume[__volumeUnit + 1] ;  /*Unite de volume*/
  WCodeParticulariteSPL_Type  Spls[__nbSplsDrf] ;   /*Spls*/
  WStatutMagasin_Type         StatutMag;
  WFlightDate_Type            VolDateSeg ;          /*Vol date segment*/
  WFlightDate_Type            VolDatePreach ;       /*Vol date preach*/
  char                        DimsUnit[__dimsUnit + 1] ;  /*Unite Dims*/
  char                        DimsLong[__dimsLong + 1] ;  /*Longueur*/
  char                        DimsLarg[__dimsLarg + 1] ;  /*Largeur*/
  char                        DimsHaut[__dimsHaut + 1] ;  /*Hauteur*/
} WDRF_Type;                                     /*Data*/
#endif

/* FREE TEXT */
const int __refhisunique = 9;
#ifndef _WContinuiteHIS_Type
#define _WContinuiteHIS_Type
typedef struct {
     char RefHisUnique[__refhisunique + 1];                        /*TypeLigne*/
} WContinuiteHIS_Type;                                     /*Texte_Libre*/
#endif

const int __txt = 1000 ;
const int __typLigne = 1;
#ifndef _WTxtLibre_Type
#define _WTxtLibre_Type
typedef struct {
     char TypLigne[__typLigne + 1];                        /*TypeLigne*/
     char TxtLibre[__txt + 1];                                  /*TxtLibre*/
} WTxtLibre_Type;                                     /*Texte_Libre*/
#endif

const int __nbreferencehis = 4;
#ifndef _WNbReferenceHis_Type
#define _WNbReferenceHis_Type
typedef struct {
     char NbReferenceHis[__nbreferencehis + 1];             /*Code_Escale*/
} WNbReferenceHis_Type;                                     /*Transit*/
#endif

const int __esctra = 5;
const int __cietra = 3;
#ifndef _WTransit_Type
#define _WTransit_Type
typedef struct {
     char EscTra[__esctra + 1];                      /*Code_Escale*/
     char CieTra[__cietra + 1];                      /*Code_compagnie*/
} WTransit_Type;                                     /*Transit*/
#endif

const int __escorgsurf = 5;
const int __escorg = 5;
const int __max_transit = 5;
const int __escdstsurf = 5;
#ifndef _WRouting_Type
#define _WRouting_Type
typedef struct {
     char EscOrgSurf[__escorgsurf + 1];         /*Code_Escale_Origine_de_surface*/
     char EscOrg[__escorg + 1];                 /*Code_Escale_Origine*/
     WTransit_Type Transit[__max_transit];      /*Transit*/
     char EscDstSurf[__escdstsurf + 1];         /*Code_Escale_Destination_de_surface*/
} WRouting_Type;                                /*Routing*/
#endif

const int __refunique = 6;
const int __nom = 35;
const int __esc = 5;
#ifndef _WPers_Type
#define _WPers_Type
typedef struct {
     char RefUnique[__refunique + 1];            /*Reference_unique_de_l_agent*/
     char Nom[__nom + 1];                        /*Nom_de_l_Agent*/
     char Esc[__esc + 1];                        /*Escale_de_l_Agent*/
} WPers_Type;                                    /*Agent*/
#endif

const int __desc = 50;
const int __escctl = 5;
// SC - 22/11/05 - Change Field size to 12 as previous Common.h version as VCargo is not ready to do the mesa now
const int __recetteFT = 12;
//const int __recetteFT = 16;
const int __codemonnaie = 3;
#ifndef _WEntete_Type
#define _WEntete_Type
typedef struct {
     WRefLta_Type RefLta;                          /*Reference_LTA*/
     char Desc[__desc + 1];                        /*Description*/
     WRouting_Type Routing;                        /*Routing*/
     WCodeParticulariteSPL_Type Spls[__maxSplDUB]; /*Codes_Particularites*/
     char EscCtl[__escctl + 1];                    /*Code_Escale_de_controle*/
     WPers_Type Expd;                              /*Expediteur*/
     WPers_Type Agt;                               /*Agent*/
     char Recette[__recetteFT + 1];                  /*Recette*/
     char CodeMonnaie[__codemonnaie + 1];          /*Code_monnaie*/
} WEntete_Type;                                    /*Entete*/
#endif

// FLIGHT DATE WIEGHT AND PIECES BOOKED
const int __FlightDateWeight = 10 ;
#ifndef _WFLIGHTDATEWEIGHT_TYPE
#define _WFLIGHTDATEWEIGHT_TYPE
typedef struct{
  char FlightDateWeight[__FlightDateWeight + 1];
}WFlightDateWeight_Type;
#endif 

const int __FlightDatePieces = 4 ;
#ifndef _WFLIGHTDATEPIECES_TYPE
#define _WFLIGHTDATEPIECES_TYPE
typedef struct{
  char FlightDatePieces[__FlightDatePieces + 1];
}WFlightDatePieces_Type;
#endif 

const int __FFMPieces = 5 ;
#ifndef _WFFMPIECES_TYPE
#define _WFFMPIECES_TYPE
typedef struct{
  char FFMPieces[__FFMPieces + 1];
}WFFMPieces_Type;
#endif 



#ifndef _WFFMFSUBOOKINGRESULT_Type
#define _WFFMFSUBOOKINGRESULT_Type
typedef struct {
  WRefLta_Type AWBNumber;                     /*AirWayBill_Number*/
  WFlightDateWeight_Type BKDWT;                             /*Weight_Booked_for_flight_date*/
  WFlightDatePieces_Type BKDPCS;                           /*Pieces_booked_for_flight_date*/
  WAgentName_Type AgtName;                         /*Agent_Name*/
  WAgentRef_Type RefUniqueAgt;               /*Reference_unique_de_l_agent*/
  WFlightDate_Type FFMFlightDate;             /*FFM_Flight_Date*/
  WFFMPieces_Type FFMPCs;                           /*FFM_Pieces*/
  WIndic_Type GAC;                                 /*GAC*/
  WFlightDate_Type BKDFlight ;        /* BKD_Flight_Date */
} WFFMFSUBookingResult_Type;                                     /*DataOut*/
#endif

const int __NbMaxLinesReturn = 3;
#ifndef _WMAXLINESRETURN_Type
#define _WMAXLINESRETURN_Type
typedef struct{
  char NbMaxLines[__NbMaxLinesReturn + 1]; 
} WNbMaxLinesReturn_Type;
#endif



// Maximum length of Oracle data

#define __LSeqIndex 9 // Numero de sequence
#define __LSplCode 3 // Code Spl
#define __LgTxnCode 4 // Code transaction ou batch Pelican

// Standard length
#define __LgStation 5 // Escale
#define __LgImmat 5 // Immatriculation de l'avion
#define __LgCountryCode 3 // Code pays

#define __LgReceivedStatus 3 // Statut magasin
#define __LgRemarq 75 // Remarque
#define __LgAcnCode 3 // Code Acn
#define __LgLtaRef 9 // Reference Lta
#define __LgLtaType 1 // Type de Lta
#define __LgLtaPriorityRow 1 // Rang de priorite de la Lta
#define __LgLtaReservationType 1 // Type de reservation de la Lta
#define __LgSourceType 1 // Source de la reservation (Gfx,Traxon,dbI,dbS,dbC,dAi)
#define __LgAloRef 14 // Reference de l'allotement
#define __LgAllotmentType 3 // Type de l'allotement
#define __LgSalesManagerName 30 // Nom du Sales Manager de l'allotement
#define __LgCluRef 8 // Reference de la Clu
#define __LgIDCodeUld 11 // ID Code de l'Uld liee
#define __LgCluReservationStatus 3 // Statut de reservation de la Clu
#define __LgBillBookRef 19 // Reference de l'echeancier
#define __LgQuotationRef 12 // Reference cotation MB 04/09/12 Passage de 11 à 12 caracteres
#define __LgNatureCode 4  // Code de nature des marchandises
#define __LgDescription 40 // Description de l'expedition
#define __LgIndicatorCo2 1  // Indicateur CO2
#define __LgComSegmentCode 3 // Code de segment commercial
#define __LgComProductCode 3 // Code de produit commercial
#define __LgBctlConfirmationIndic 1 // Indicateur de confirmation de la BCTL (A,Y,N)
#define __LgOriginChannel 1 // Origin of the channel (for a Awb only)
#define __LgFreeText 1000 // Remarque
#define __LgSPLCodeList 45 // Special Handling Code list
#define __LgArrivingOrConnectionFlightType 1 // A:Arriving flight or C:Connection flight (for Allotment only)
#define __LgFlightFrequency 7 // String frequency. For ex., daily is 1234567
#define __LgProductLabel 3 // product label

#define __LgCie 3 // Code compagnie
#define __LgFlightNumber 4 // Numero de vol
#define __LgFlightNumberFFM 5 // Numero de vol
#define __LgFlightSuffix 1 // Suffixe du numero de vol

#define __LgStaPel 6 // Statut Pelican du vol
#define __LgStaGae 6 // Statut Gaetan du vol

#define __LgReceipt 9 // Recette
#define __LgRmk 5 // Rmk

#define __LgCurrencyCode 3 // Code monnaie
#define __LgWeightUnitCode 2 // Code des unites de poids
#define __LgVolumeUnitCode 2 // Code des unites de volume
#define __LgDimensionUnitCode 2 // Code des unites de dimension

#define __LgYmfError 45 // Libelle de l'erreur Yield

#define __LgCustomerType 1 // Type de client
#define __LgCustomerName 35 // Nom
#define __LgCustomerAddress 35 // Adresse
#define __LgCustomerCity 17 // Ville
#define __LgCustomerPostCode 9 // Code postal
#define __LgCustomerCountry 52 // Pays
#define __LgCustomerState 24 // Etat

#define __LgPlaneType 5 // Type avion
#define __LgBctlActionCode 2 // Code d'action (CA)
#define __LgFctlStatusCode 2 // Code du statut (OA)
#define __LgTtyAddress 7 // Adresse Teletype

// Contacts
#define __LgContactName 30
#define __LgContactFirstName 15
#define __LgContactPhone 14
#define __LgContactFax 14
#define __LgContactEMail 50

// Add these parameters to all clients
#define __LgAccountNumber 14
#define __LgAccountID 1

//Radioactivity Index
#define __LgRadioactivityIndex 12

// Erreur Yield au segment
#define __LgYieldSegError 50

// Type Booking (LTA/CLU)
#define __LgTypeBooking 1

// Maximum FFM infos return for Service 3421
#define __MaxFFMBooking 500
#define __NbBookingFFMMaxRef  250
#endif // _COMMON_H
