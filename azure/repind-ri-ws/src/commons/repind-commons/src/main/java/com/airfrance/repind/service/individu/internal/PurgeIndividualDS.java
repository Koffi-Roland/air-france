package com.airfrance.repind.service.individu.internal;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewBusinessException;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewV3;
import com.afklm.soa.stubs.w001580.v3.cbs.Customer;
import com.afklm.soa.stubs.w001580.v3.cbs.Pnr;
import com.afklm.soa.stubs.w001580.v3.cbs.ProvideCustomer360ViewRequest;
import com.afklm.soa.stubs.w001580.v3.cbs.ProvideCustomer360ViewResponse;
import com.afklm.soa.stubs.w001580.v3.commons.CustomerCase;
import com.afklm.soa.stubs.w001580.v3.commons.ProfilingData;
import com.afklm.soa.stubs.w001580.v3.commons.Requestor;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.util.AspectLogger.Loggable;
import com.airfrance.repind.util.AspectLogger.LoggableNoParams;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.neo4j.driver.Values.parameters;

/*
 * Documentation available on confluence/jira:
 * https://confluence.devnet.klm.com/display/repind/Select+Implementation
 * https://confluence.devnet.klm.com/display/repind/Physical+deletion+of+an+individual
 * https://confluence.devnet.klm.com/display/repind/Purge+RI+v2
 * https://jira.devnet.klm.com/browse/REPIND-821
 * When I first implemented this batch, we had 5 millions individual to purge.
 * So the batch is quite complex in order to properly handle (and quickly) more than 5 millions individual
 */
@Service
@Slf4j
public class PurgeIndividualDS {

	@Autowired
	private ForgottenIndividualDS forgottenIndividualDS;

	/*
	 * If someone create new table or delete old table in the database, this list
	 * NEED to be updated.
	 */
	private final static LinkedHashMap<String, DatabaseType> DELETE_REQUESTS = new LinkedHashMap<String, DatabaseType>() {
		private static final long serialVersionUID = 3054461864864427718L;

		{
			put("SIC2.ACCOUNT_DATA origin INNER JOIN SIC2.:TPD tpd ON origin.sgin", DatabaseType.INDIVIDUS);
			//put("SIC2.TRACKING origin INNER JOIN SIC2.:TPD tpd ON origin.IDENTIFICATIONNUMBER ",
			//		DatabaseType.INDIVIDUS);
			put("SIC2.TRACKING_PERMISSIONS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ADR_TO_NORMALIZE origin INNER JOIN SIC2.ADR_POST ap ON ap.sain=origin.sain INNER JOIN  SIC2.:TPD tpd ON ap.sgin",
					DatabaseType.INDIVIDUS);
			put("SIC2.FORMALIZED_ADR origin INNER JOIN SIC2.ADR_POST ap ON origin.sain_adr=ap.sain INNER JOIN SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.USAGE_MEDIUMS origin INNER JOIN SIC2.ADR_POST ap ON ap.sain = origin.sain_adr INNER JOIN  SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.USAGE_MEDIUMS origin INNER JOIN SIC2.EMAILS ap ON ap.sain = origin.sain_email INNER JOIN  SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.USAGE_MEDIUMS origin INNER JOIN SIC2.TELECOMS ap ON ap.sain = origin.sain_tel INNER JOIN  SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.BLOCAGE_ACCES origin INNER JOIN  SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.BSP_DATA origin INNER JOIN  SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.CHIFFRE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.CIE_ALLIEE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.MARKET_LANGUAGE origin INNER JOIN SIC2.COMMUNICATION_PREFERENCES cp ON cp.com_pref_id=origin.com_pref_id INNER JOIN SIC2.:TPD tpd ON cp.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.COMMUNICATION_PREFERENCES origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.CONSENT_DATA origin INNER JOIN SIC2.CONSENT cst ON cst.consent_id=origin.consent_id INNER JOIN SIC2.:TPD tpd ON cst.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.CONSENT origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DELEGATION_DATA_INFO origin INNER JOIN SIC2.DELEGATION_DATA dd ON dd.DELEGATION_DATA_ID=origin.DELEGATION_DATA_ID INNER JOIN SIC2.:TPD tpd ON dd.sgin_delegator ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DELEGATION_DATA_INFO origin INNER JOIN SIC2.DELEGATION_DATA dd ON dd.DELEGATION_DATA_ID=origin.DELEGATION_DATA_ID INNER JOIN SIC2.:TPD tpd ON dd.sgin_delegate ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DELEGATION_DATA origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_delegator ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DELEGATION_DATA origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_delegate ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DOCUMENTATION origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.MBR_ADR origin INNER JOIN SIC2.ADR_POST ap ON origin.sain=ap.sain INNER JOIN SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_ADR origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MBR INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_ADR origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MBR INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_EMAIL origin INNER JOIN SIC2.EMAILS emails on emails.sain=origin.sain INNER JOIN SIC2.:TPD tpd ON emails.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_EMAIL origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MBR INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_EMAIL origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MBR INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_TEL origin INNER JOIN SIC2.TELECOMS t ON t.sain=origin.sain INNER JOIN SIC2.:TPD tpd ON t.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_TEL origin INNER JOIN SIC2.MEMBRE m ON origin.IKEY_MBR=m.IKEY INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MBR_TEL origin INNER JOIN SIC2.MEMBRE m ON origin.IKEY_MBR=m.IKEY INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FONCTION origin INNER JOIN SIC2.EMAILS emails ON emails.ICLE_FONCTION=origin.ICLE INNER JOIN SIC2.:TPD tpd ON emails.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.EMAILS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ENTREPRISE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ETABLISSEMENT origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.EXTERNAL_IDENTIFIER_DATA origin INNER JOIN SIC2.EXTERNAL_IDENTIFIER ei ON ei.identifier_id=origin.identifier_id INNER JOIN SIC2.:TPD tpd ON ei.sgin  ",
					DatabaseType.INDIVIDUS);
			put("SIC2.EXTERNAL_IDENTIFIER origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.GESTION_PM origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_gerante ", DatabaseType.INDIVIDUS);
			put("SIC2.GESTION_PM origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_geree ", DatabaseType.INDIVIDUS);
			put("SIC2.GROUPE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.HANDICAP_DATA origin INNER JOIN SIC2.HANDICAP h on h.HANDICAP_ID=origin.HANDICAP_ID INNER JOIN SIC2.:TPD tpd ON h.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.HANDICAP origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.INDIVIDU_NOTIFICATION origin INNER JOIN SIC2.:TPD tpd ON origin.sid  ", DatabaseType.INDIVIDUS);
			put("SIC2.INDIVIDUS_ERREUR origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.INFERRED_DATA origin INNER JOIN SIC2.INFERRED i on i.INFERRED_ID=origin.INFERRED_ID INNER JOIN SIC2.:TPD tpd ON i.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.INFERRED origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.LETTRE_COMPT origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.LIEN_GP_TRC origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.LIEN_IND_RI origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.LIEN_IND_RI origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_donnant_droit ",
					DatabaseType.INDIVIDUS);
			put("SIC2.MEMBRE_RESEAU origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.NUMERO_IDENT origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.OFFICE_ID origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PERS_MORALE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PM_SBT origin INNER JOIN SIC2.:TPD tpd ON origin.ps_sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.OBJ_SUPP origin INNER JOIN PM_ZONE pz ON pz.ICLE_PMZ=origin.SID INNER JOIN SIC2.:TPD tpd ON pz.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PM_ZONE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.JOUR_OUV origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.JOUR_OUV origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_CONTENTIEUX origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_CONTENTIEUX origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_DEMARCHAGE origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_DEMARCHAGE origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_QUALITATIF origin  INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PR_QUALITATIF origin  INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_AF origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_AF origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.:TPD tpd ON pm.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_BANQ origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_BANQ origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_FACT origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_FACT origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_FINANC origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_IND ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_FINANC origin INNER JOIN SIC2.PROFIL_MERE pm ON origin.ICLE_PRF=pm.ICLE_PRF INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=pm.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.SGIN_PM ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PREFILLED_NUMBERS_DATA origin INNER JOIN SIC2.PREFILLED_NUMBERS pn ON pn.PREFILLED_NUMBERS_ID=origin.PREFILLED_NUMBERS_ID INNER JOIN SIC2.:TPD tpd ON pn.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PREFILLED_NUMBERS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PREFERENCE_DATA origin INNER JOIN SIC2.PREFERENCE p on p.PREFERENCE_ID=origin.PREFERENCE_ID INNER JOIN SIC2.:TPD tpd ON p.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.PREFERENCE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PROFILS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_FIRME origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_MERE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_ind ", DatabaseType.INDIVIDUS);
			put("SIC2.PROFIL_MERE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_pm ", DatabaseType.INDIVIDUS);
			put("SIC2.QUALIFLYER origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.RJT_PROBABLE origin INNER JOIN SIC2.RJT_PM pm ON origin.ICLE_RJT_PM=pm.ICLE INNER JOIN SIC2.:TPD tpd ON pm.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.RJT_PM origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_pm ", DatabaseType.INDIVIDUS);
			put("SIC2.SEGMENTATION origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.SONDE_NEWTEST origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);

			put("SIC2.SYNONYME origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.FONCTION origin INNER JOIN SIC2.TELECOMS t ON origin.ICLE=t.ICLE_FONCTION INNER JOIN SIC2.:TPD tpd ON t.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.TELECOMS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.USAGE_CLIENTS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_CONTRATS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_CONTRATS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_CONTRATS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_CONTRATS2 origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_AGENCE origin INNER JOIN SIC2.BUSINESS_ROLE br ON origin.ICLE_ROLE=br.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_AGENCE origin INNER JOIN SIC2.BUSINESS_ROLE br ON origin.ICLE_ROLE=br.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_AGENCE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_FIRME origin INNER JOIN SIC2.BUSINESS_ROLE br on origin.ICLE_ROLE=br.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_FIRME origin INNER JOIN SIC2.BUSINESS_ROLE br on origin.ICLE_ROLE=br.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_FIRME origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_GP origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_GP origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_RCS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_RCS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_TRAVELERS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_TRAVELERS origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.ICLE_ROLE=origin.ICLE_ROLE INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_TRAVELERS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ROLE_UCCR origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.APPLI_FUSION origin INNER JOIN SIC2.DOUBLONS d ON d.ICLE=origin.ICLE_DOUBLON INNER JOIN SIC2.:TPD tpd ON d.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FONCTION origin INNER JOIN SIC2.ADR_POST ap ON ap.ICLE_FONCTION=origin.ICLE INNER JOIN SIC2.:TPD tpd ON ap.sgin ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FORMALIZED_ADR origin INNER JOIN SIC2.ADR_POST ap ON origin.sain_adr=ap.sain INNER JOIN SIC2.BUSINESS_ROLE br ON br.icle_role=ap.icle_role INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ADR_POST origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.ADR_POST origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.icle_role=origin.icle_role INNER JOIN SIC2.:TPD tpd ON br.sgin_ind ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ADR_POST origin INNER JOIN SIC2.BUSINESS_ROLE br ON br.icle_role=origin.icle_role INNER JOIN SIC2.:TPD tpd ON br.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.BUSINESS_ROLE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_pm ", DatabaseType.INDIVIDUS);
			put("SIC2.BUSINESS_ROLE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_ind ", DatabaseType.INDIVIDUS);
			put("SIC2.FORMALIZED_ADR origin INNER JOIN SIC2.ADR_POST ap ON origin.SAIN_ADR=ap.sain INNER JOIN SIC2.FONCTION f ON f.ICLE=ap.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FORMALIZED_ADR origin INNER JOIN SIC2.ADR_POST ap ON origin.SAIN_ADR=ap.sain INNER JOIN SIC2.FONCTION f ON f.ICLE=ap.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ADR_POST origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.ADR_POST origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.TELECOMS origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.TELECOMS origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.EMAILS origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.EMAILS origin INNER JOIN SIC2.FONCTION f ON f.ICLE=origin.ICLE_FONCTION INNER JOIN SIC2.MEMBRE m ON m.IKEY=f.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FONCTION origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_individus ",
					DatabaseType.INDIVIDUS);
			put("SIC2.FONCTION origin INNER JOIN SIC2.MEMBRE m ON m.IKEY=origin.IKEY_MEMBRE INNER JOIN SIC2.:TPD tpd ON m.sgin_pm ",
					DatabaseType.INDIVIDUS);
			put("SIC2.DOUBLONS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.DOUBLONS origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_pm ", DatabaseType.INDIVIDUS);
			put("SIC2.AGENCE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.MEMBRE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_individus ", DatabaseType.INDIVIDUS);
			put("SIC2.MEMBRE origin INNER JOIN SIC2.:TPD tpd ON origin.sgin_pm ", DatabaseType.INDIVIDUS);
			put("SIC_UTF8.UTF origin INNER JOIN SIC_UTF8.:TPD tpd ON origin.sgin", DatabaseType.UTF8);
			put("REPIND_PP.FIELDS origin INNER JOIN REPIND_PP.PAYMENTDETAILS pd ON pd.PAYMENTID = origin.PAYMENTDETAILS_PAYMENTID INNER JOIN REPIND_PP.:TPD tpd ON pd.gin",
					DatabaseType.PAYMENT);
			put("REPIND_PP.PAYMENTDETAILS origin INNER JOIN REPIND_PP.:TPD tpd ON origin.gin", DatabaseType.PAYMENT);
			put("SIC2.INDIVIDUS_ALL origin INNER JOIN SIC2.:TPD tpd ON origin.sgin ", DatabaseType.INDIVIDUS);
			put("SIC2.IND_TO_PURGE origin INNER JOIN SIC2.:TPD tpd ON origin.gin ", DatabaseType.INDIVIDUS);
		}
	};

	public interface BackupWriteFileCallback {
		void writeBackupFile(final String filename, final Map<String, String> content) throws Exception;
	}
	@Autowired
	private ApplicationContext context;

	/**
	 * Contract Type = A was deleted from the clause as we don't want anymore to protect Fidelio Contract.
	 * Business Role = G (GP) was deleted from the clause, we don't need anymore
	 * They will be checked later with the call to CBS (Provide360Customer)
	 */
	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeBusinessRole(final Session customSession) {
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select sgin_ind, :rule from SIC2.BUSINESS_ROLE where stype in ('C', 'U')")
				.setParameter("rule", SubBusinessRule.EXCLUDE_BUSINESS_ROLE.businessRule).executeUpdate();
	}

	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeCommpref(final Session customSession) {
		final Calendar calendarCommPrefLastModified = Calendar.getInstance();
		calendarCommPrefLastModified.setTime(_currentDate);
		calendarCommPrefLastModified.add(Calendar.DATE, _tempo_optout_compref_sales_rule);
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) "
						+ "select c.SGIN, :rule FROM SIC2.COMMUNICATION_PREFERENCES c WHERE c.DOMAIN = 'S' AND ( c.SUBSCRIBE = 'Y' OR ( c.SUBSCRIBE = 'N' AND c.MODIFICATION_DATE > :date)) ")
				.setParameter("date", calendarCommPrefLastModified.getTime())
				.setParameter("rule", SubBusinessRule.EXCLUDE_COMPREF.businessRule).executeUpdate();
	}

	// Get all individu that got modified in the last 12 months AND with all their
	// com pref sales "S" opt-out for at least 6 months
	@PurgeIndividualBusinessRule
	@Loggable
	private void _includeIndividu(final Session customSession) {
		final Calendar calendarIndividuLastModified = Calendar.getInstance();
		calendarIndividuLastModified.setTime(_currentDate);
		calendarIndividuLastModified.add(Calendar.DATE, _tempo_inactive_rule);
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_INCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) "
						+ " select i.sgin, :rule from SIC2.INDIVIDUS_ALL i WHERE (i.STYPE = 'W' OR i.STYPE = 'I' OR i.STYPE = 'T') AND ( i.DDATE_MODIFICATION < :date OR i.DDATE_MODIFICATION IS NULL)")
				.setParameter("date", calendarIndividuLastModified.getTime())
				.setParameter("rule", SubBusinessRule.INDIVIDUAL_OR_PROSPECT.businessRule).executeUpdate();
	}

	/*
	 * Rule 2 -- Logically deleted / deceased
	 * --------------------------------------
	 * Individual status is X (Deleted) or D (Deceased)
	 */
	@Loggable
	@PurgeIndividualBusinessRule
	private void _includeObsoleteIndividu(final Session customSession) throws InvalidParameterException {
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_INCLUDE_BR" + BusinessRule.OBSOLETE_INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select sgin, :rule from SIC2.INDIVIDUS_ALL where SSTATUT_INDIVIDU = 'X' OR SSTATUT_INDIVIDU = 'D'")
				.setParameter("rule", SubBusinessRule.OBSOLETE_INDIVIDUAL.businessRule).executeUpdate();
	}

	@Loggable
	@PurgeIndividualInsertDetectedInd
	private void _insertNewlyDetectedInd(final Session customSession) {
		customSession.createNativeQuery("insert /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ into SIC2.TEMPORARY_IND_TO_PURGE (gin) select gin from SIC2.TEMPORARY_PURGE_DISTINCT_IND MINUS select gin from SIC2.IND_TO_PURGE ")
				.executeUpdate();
	}

	private void addIndividuToNoPurge(List<String> individuToNotPurge) {
		for (String gin : individuToNotPurge) {
			entityManager.createNativeQuery("insert into SIC2.IND_NOT_TO_PURGE (GIN, SIGNATURE) values ('" + gin + "', 'PROT_OCP')").executeUpdate();
		}
	}

	@Loggable
	private PhysicalPurgeResult _purge() throws InterruptedException, MissingParameterException, NoSuchMethodException,
			SecurityException, InvalidParameterException, ExecutionException {
		List<String> individuToPurge = _selectIndividualToPurge();

		final PhysicalPurgeResult result = new PhysicalPurgeResult();

		if (!UList.isNullOrEmpty(individuToPurge)) {
			if (_maximumNumberDeletion != 0) {
				individuToPurge = Lists.partition(individuToPurge, _maximumNumberDeletion).get(0);
			}

			//REPIND-1078 : Automatization of RI Purge
			//If the GIN is NOT eligible to the Purge according to the response of CBS, we remove it from the list of GINs to be purged, it will be take again next time
			List<String> individuToNotPurge = callToCBS(individuToPurge);
			for (String ginToNotPurge : individuToNotPurge) {
				if (individuToPurge.contains(ginToNotPurge)) {
					individuToPurge.remove(ginToNotPurge);
				}
			}
			if (_period_in_days_ocp > 0) {
				addIndividuToNoPurge(individuToNotPurge);
			}

			result.errors = physicalDeletion(individuToPurge, false);
			result.ginPurged = new ArrayList<>(individuToPurge);
			result.cbsReport = _reportCBS;
			result.cbsReportDetails = _reportCBSDetails;
			for (final Map.Entry<List<String>, String> entry : result.errors.entrySet()) {
				for (final String gin : entry.getKey()) {
					result.ginPurged.remove(gin);
				}
			}

		} else {
			result.errors = null;
			result.ginPurged = null;
			result.cbsReport = null;
			result.cbsReportDetails = null;
		}

		return result;
	}

	/*
	 * DELETE FROM the IND_TO_PURGE every gin that have not been found in the
	 * TEMPORARY_PURGE tables
	 * then
	 * Insert the content of the TEMPORARY_PURGE tables into IND_TO_PURGE table.
	 * That way, we keep correct "detected_date" in IND_TO_PURGE and without
	 * duplicate
	 */
	@Loggable
	private void _storeIndToPurge() throws InterruptedException, ExecutionException {

		/*
		 * the deletion of all gins but those matching the business rules is too
		 * slow/resource consuming,
		 * we have to go step by step with the use of a temporary table
		 */

		final EntityManager customEntityManager = entityManagerFactory.createEntityManager();
		final Session session = ((Session) customEntityManager.getDelegate()).getSessionFactory().openSession();
		session.beginTransaction();
		session.createNativeQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();

		session.createNativeQuery("insert /*+ parallel(" + _numberDatabaseCpuCore
				+ ") */ into SIC2.TEMPORARY_PURGE_DISTINCT_IND (gin) select distinct gin from ( "
				+ " ( select gin from SIC2.TEMPORARY_PURGE_INCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
				+ " MINUS select gin from SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
				+ " ) " + " UNION ( select gin from SIC2.TEMPORARY_PURGE_INCLUDE_BR"
				+ BusinessRule.OBSOLETE_INDIVIDUAL.getBusinessRule()
				+ " MINUS select gin from SIC2.TEMPORARY_PURGE_EXCLUDE_BR"
				+ BusinessRule.OBSOLETE_INDIVIDUAL.getBusinessRule() + " ) "
				+ ") MINUS select gin from SIC2.IND_NOT_TO_PURGE").executeUpdate();
		session.getTransaction().commit();
		session.close();
		for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
			thread.get();
		}
	}

	public void set_cpu_thread_cbs(int _cpu_thread_cbs) {
		int maxAvailableProcessors = Runtime.getRuntime().availableProcessors();

		if (_cpu_thread_cbs <= 0) {
			_cpu_thread_cbs = 4;
		}
		this._cpu_thread_cbs = _cpu_thread_cbs > maxAvailableProcessors
				? maxAvailableProcessors
				: _cpu_thread_cbs;
	}

	@Loggable
	@Transactional
	public PurgeIndividualResult process() throws InterruptedException, MissingParameterException,
			InvalidParameterException, NoSuchMethodException, SecurityException, ExecutionException {

		log.info("Inside purge processing ...");

		//REPIND-1078: We get the periodInDays (for ProvideCustomer360) from the DB
		//Double check to be sure to have a right value
		if (_period_in_days < 0)
			throw new InvalidParameterException("Period In Days (ProvideCustomer360) cannot not be negative.");

		//REPINd-1648: We reuse the same parameter as we use for the Events (Fidelio, Icare, PNR) to have the same period for all controls
		_limit_date_clickers = Calendar.getInstance();
		_limit_date_clickers.setTime(new Date());
		_limit_date_clickers.add(Calendar.DAY_OF_YEAR, -(_period_in_days));

		final PurgeIndividualResult result = new PurgeIndividualResult();
		log.info("Start deleting temporary tables");
		_deleteTemporaryTables();
		log.info("End deleting temporary tables");
		log.info("Start emptying IND_NOT_TO_PURGE table");
		_deleteOldExcludedIndividualFromPurge();
		log.info("End emptying IND_NOT_TO_PURGE table");

		if (_period_in_days_ocp > 0) {
			log.info("Start emptying protect by ocp data");
			_deleteOldExcludedProtectedByOCPIndividualFromPurge();
			log.info("End emptying protect by ocp");
		}

		log.info("Start running Business rules");
		for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualBusinessRule.class)) {
			thread.get();
		}
		log.info("End running Business rules");
		// mode selection des individu
		log.info("Start copy of individual to purge");
		_storeIndToPurge();
		log.info("End copy of individual to purge");
		// mode physical purge des individu
		final List<Future<?>> threads = _runAllAnnotatedWith(PurgeIndividualCopyData.class);
		if (_execMode == 1) {
			log.info("Start of physical purge");
			final PhysicalPurgeResult physicalPurgeResult = _purge();
			result.setGinsPurged(physicalPurgeResult.ginPurged);
			result.setErrorsPhysicalPurge(physicalPurgeResult.errors);
			result.set_cbsReport(physicalPurgeResult.cbsReport);
			result.set_cbsReportDetails(physicalPurgeResult.cbsReportDetails);
			log.info("End of physical purge");
		}
		log.info("Start selecting all individual that are planned to be deleted");
		result.setGinsToPurgeLater(_selectIndividualToPurgeLater());
		log.info("End selecting all individual that are planned to be deleted");
		log.info("Start copy data from temp table to main table");
		for (final Future<?> thread : threads) {
			thread.get();
		}
		log.info("End copy date from temp table to main table");
		return result;
	}

	private List<String> _reportCBS = new ArrayList<String>();

	private List<String> _reportCBSDetails = new ArrayList<String>();

	private final SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS");
	private final SimpleDateFormat dateFormaterClickers = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	//Numbers of days before current date to search for anything, for the days after current date they are taken by default
	private int _period_in_days = 365;

	// REPIND-1533 : Delay in Ms before call to CBS 360 WS
	private long _delay_in_ms = 50;		// 50 ms by default
	

	/**
	 * We call the CBS WebService ProvideCustomer360View V3 in order to know if the gin has still some activities in AFKLM.
	 * If no activities are found, we consider that we can purge this gin,
	 * Otherwise we don't purge it.
	 *
	 * @param gins
	 * @return List<String>
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@LoggableNoParams
	private List<String> callToCBS(List<String> gins) throws InterruptedException, ExecutionException {
		String startCallCBS = dateFormater.format(new Date());

		List<String> TO_NOT_PURGE = new ArrayList<String>();

		final ExecutorService exec = Executors.newFixedThreadPool(_cpu_thread_cbs);
		final List<Future<List<CBSThreadResult>>> threads = new ArrayList<>();
		try {
			List<List<String>> individuToPurgeGrouped = Lists.partition(gins, 100);

			for (List<String> individuToPurge : individuToPurgeGrouped) {
				final CBSThread thread = new CBSThread(individuToPurge);
				final Future<List<CBSThreadResult>> future = exec.submit(thread);
				threads.add(future);
			}
		} finally {
			exec.shutdown();
		}

		for (Future<List<CBSThreadResult>> thread : threads) {
			for (CBSThreadResult threadResult : thread.get()) {
				if (threadResult.purgeable == false) TO_NOT_PURGE.add(threadResult.gin);
			}
		}

		String endCallCBS = dateFormater.format(new Date());

		try {
			_reportCBS = generateCBSReport(startCallCBS, endCallCBS, gins.size(), threads);
		} catch (InterruptedException | ExecutionException e) {
			PurgeIndividualDS.logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		try {
			_reportCBSDetails = generateCBSReportDetails(threads);
		} catch (InterruptedException | ExecutionException e) {
			PurgeIndividualDS.logger.error(ExceptionUtils.getFullStackTrace(e));
		}

		return TO_NOT_PURGE;
	}

	//Cannot be a negative value, if 0 == unlimited, else we limit amount of records according to the parameter
	private int _max_records_cbs_report = 0;

	private int _cpu_thread_cbs = 8;

	//REPIND-1648: Protect a gin if a click is detected in the last 3 years
	private Calendar _limit_date_clickers = null;

	private final static String CLICKERS_PROFILING_DATA = "DAT_LST_CLK";
	private final static String FIDELIO_COMPLAINT = "FIDELIO";
	private final static String ICARE_COMPLAINT = "ICARE";
	private final static String SEPARATOR_CSV = ";";

	private final static List<String> CBS_ERROR = new ArrayList<String>(Arrays.asList("NOT_FOUND", "INDIVIDUAL_MERGED", "INDIVIDUAL_CANCELLED"));

	private static final String[] USAGES_TO_PURGE = new String[] { "FID" };

	private int _tempo_inactive_rule = -365;

	// Only for _includeExternalIdentifier rule
	private int _tempo_inactive_rule_external = -365;


	private int _tempo_inactive_rule_merged = -365;

	private int _tempo_optout_compref_sales_rule = -180;

	private int _tempo_delay = -30;

	private int _tempo_cycle_life = 400;

	// if value 0 then the protection by OCP is disable
	private int _period_in_days_ocp = 0;

	private int _execMode = 0;

	private final static String TEMPORARY_DELETE_TABLE = "TMP_DEL_";

	private static final Log logger = LogFactory.getLog(PurgeIndividualDS.class);

	// The '12' have been chosen arbitrary, need to adapt it depending on the
	// database server
	private final static int MAX_DATABASE_POOL = 12;

	// The '8' have been chosen arbitrary, need to adapt it depending on the
	// database server
	private final static int MAX_NUMBER_DATABASE_CPU_CORE = 8;

	//Dedicated CBS businessScope for the RI PURGE
	private final static String BUSINESS_SCOPE_CBS = "RI_PURGE";

	private PurgeMode _mode = PurgeMode.SAFE_DELETE;

	private BackupWriteFileCallback _batchPurge;

	private final AtomicInteger _uniqueThreadId = new AtomicInteger();

	private int _numberDatabasePool = PurgeIndividualDS.MAX_DATABASE_POOL;

	private int _userDefinedBatchSize = 0;

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("entityManagerFactoryRepind")
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	@Qualifier("entityManagerFactoryRepindPP")
	private EntityManagerFactory entityManagerFactoryPayment;

	@Autowired
	@Qualifier("entityManagerFactoryRepindUtf8")
	private EntityManagerFactory entityManagerFactoryUtf;
	private final Date _currentDate = new Date();
	private int _numberDatabaseCpuCore = PurgeIndividualDS.MAX_NUMBER_DATABASE_CPU_CORE;

	@PurgeIndividualCopyData
	@Loggable
	private void _copyDataToIndToPurge(final Session customSession) {
		// truncate the main table
		customSession.createNativeQuery("truncate table SIC2.IND_TO_PURGE").executeUpdate();

		// insert all data from the temp table into the main table
		customSession.createNativeQuery("insert /*+ parallel(" + _numberDatabaseCpuCore
				+ ") */ into SIC2.IND_TO_PURGE select * from SIC2.TEMPORARY_IND_TO_PURGE").executeUpdate();
	}

	/*
	 * remove old item in the IND_NOT_TO_PURGE table.
	 * In the process, clients will send us a list a gin that must not be purged (
	 * test account for their application, etc )
	 * But after some times, test account stop to be used, so need the purge to
	 * handle that case.
	 */
	@Loggable
	private void _deleteOldExcludedIndividualFromPurge() {
		entityManager
				.createNativeQuery("DELETE FROM SIC2.IND_NOT_TO_PURGE i where i.updated_date < SYSDATE - :duration"
						+ " and i.signature != 'PROT_OCP' and i.signature != 'PROFIL_AF' and i.signature != 'AGENT_CONNECT' and i.signature != 'PROT_GP'")
				.setParameter("duration", _tempo_cycle_life).executeUpdate();
	}

	@Loggable
	private void _deleteOldExcludedProtectedByOCPIndividualFromPurge() {
		entityManager
				.createNativeQuery("DELETE FROM SIC2.IND_NOT_TO_PURGE i where i.updated_date < SYSDATE - :duration and i.signature = 'PROT_OCP'")
				.setParameter("duration", _period_in_days_ocp).executeUpdate();
	}

	private void _deleteTemporaryTables() {
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_IND_TO_PURGE").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_DISTINCT_IND").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_INCLUDE_BR1").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_INCLUDE_BR2").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_INCLUDE_BR3").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_EXCLUDE_BR1").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_EXCLUDE_BR2").executeUpdate();
		entityManager.createNativeQuery("truncate table SIC2.TEMPORARY_PURGE_EXCLUDE_BR3").executeUpdate();
	}

	private List<String> generateCBSReport(String start, String end, int ginTotal, List<Future<List<CBSThreadResult>>> threads) throws InterruptedException, ExecutionException {
		List<String> result = new ArrayList<String>();
		int ginOK = 0;
		int ginKO = 0;
		int ginERROR = 0;
		long seconds = 0;
		int callsPerSecond = 0;

		try {
			Date endDate = dateFormater.parse(end);
			Date startDate = dateFormater.parse(start);

			seconds = (endDate.getTime() - startDate.getTime()) / 1000;
			if (seconds > 0 && ginTotal > 0) {
				callsPerSecond = ginTotal / (int) seconds;
			}
		} catch (ParseException e) {
			PurgeIndividualDS.logger.error("Cannot calculate number of CBS calls per second.");
		}

		for (Future<List<CBSThreadResult>> thread : threads) {
			for (CBSThreadResult threadResult : thread.get()) {
				if (threadResult.error == true) {
					ginERROR++;
				} else if (threadResult.error == false && threadResult.purgeable == false) {
					ginKO++;
				} else if (threadResult.purgeable == true) {
					ginOK++;
				}
			}
		}

		result.add("Start calling ProvideCustomer360View CBS (" + _cpu_thread_cbs + " Threads): " + start);
		result.add("\t- Total of gins to process: " + ginTotal);
		result.add("\t- Amount of gins purgeable: " + ginOK);
		result.add("\t- Amount of gins protected: " + (ginTotal - ginOK));
		result.add("\t- Amount of gins non purgeable: " + ginKO);
		result.add("\t- Amount of gins not processed (error): " + ginERROR);
		result.add("End calling ProvideCustomer360View CBS (" + seconds + " seconds - " + callsPerSecond + " calls/second): " + end);

		return result;
	}

	private List<String> generateCBSReportDetails(List<Future<List<CBSThreadResult>>> listThreadsResult) throws InterruptedException, ExecutionException {
		List<String> result = new ArrayList<String>();
		String headersReport = "CALL DATE;GIN;PURGEABLE;CUSTOMER CARE FIDELIO (" + _period_in_days + " Days);CUSTOMER CARE FIDELIO DETAILS;CUSTOMER CARE ICARE (" + _period_in_days + " Days);CUSTOMER CARE ICARE DETAILS;PNR (" + _period_in_days + " Days);PNR DETAILS;CLICKERS (" + _period_in_days + " Days);CLICKERS DETAILS;ERROR";
		result.add(headersReport);
		for (Future<List<CBSThreadResult>> thread : listThreadsResult) {
			for (CBSThreadResult threadResult : thread.get()) {
				String reportLine = "";

				//Report Call Date
				reportLine += threadResult.callDate + SEPARATOR_CSV;

				//Report Gin
				reportLine += threadResult.gin + SEPARATOR_CSV;

				//Report Purgeable
				String purgeable = "";
				if (threadResult.purgeable == true) {
					purgeable = "YES";
				} else {
					purgeable = "NO";
				}
				reportLine += purgeable + SEPARATOR_CSV;

				//Report Fidelio Complaint
				String fidelioReport = threadResult.error == true ? "" : String.valueOf(threadResult.complaintFidelioDetails.size());
				reportLine += fidelioReport + SEPARATOR_CSV;

				//Report Fidelio Complaint Details
				String fidelioReportDetails = threadResult.complaintFidelioDetails == null ? "" : StringUtils.join(threadResult.complaintFidelioDetails, " - ");
				reportLine += fidelioReportDetails + SEPARATOR_CSV;

				//Report Icare Complaint
				String icareReport = threadResult.error == true ? "" : String.valueOf(threadResult.complaintIcareDetails.size());
				reportLine += icareReport + SEPARATOR_CSV;

				//Report Icare Complaint Details
				String icareReportDetails = threadResult.complaintIcareDetails == null ? "" : StringUtils.join(threadResult.complaintIcareDetails, " - ");
				reportLine += icareReportDetails + SEPARATOR_CSV;

				//Report PNR
				String pnrReport = threadResult.error == true ? "" : String.valueOf(threadResult.pnrActivityDetails.size());
				reportLine += pnrReport + SEPARATOR_CSV;

				//Report PNR Details
				String pnrReportDetails = threadResult.pnrActivityDetails == null ? "" : StringUtils.join(threadResult.pnrActivityDetails, " - ");
				reportLine += pnrReportDetails + SEPARATOR_CSV;

				//Report Clickers
				String clickersReport = threadResult.error == true ? "" : String.valueOf(threadResult.clickersDetails.size());
				reportLine += clickersReport + SEPARATOR_CSV;

				//Report Clickers Details
				String clickersReportDetails = threadResult.clickersDetails == null ? "" : StringUtils.join(threadResult.clickersDetails, " - ");
				reportLine += clickersReportDetails + SEPARATOR_CSV;

				//Report Error
				String errorDetails = threadResult.errorDetails == null ? "" : threadResult.errorDetails;
				reportLine += errorDetails;

				result.add(reportLine);
			}
		}

        /*for (String line : result) {
            System.out.println(line);
        }*/

		return result;
	}

	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeMembre(final Session customSession) {
		customSession
				.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select sgin_individus, :rule from SIC2.MEMBRE")
				.setParameter("rule", SubBusinessRule.EXCLUDE_MEMBRE.businessRule).executeUpdate();
	}

	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeProfilAF(final Session customSession) {
		customSession
				.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select SGIN_IND, :rule from SIC2.PROFIL_MERE where stype = 'AF'")
				.setParameter("rule", SubBusinessRule.EXCLUDE_PROFIL_AF.businessRule).executeUpdate();
	}

	//Exclude from purge caller with status 'V'
	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeValidCaller(final Session customSession) {
		customSession
				.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select SGIN, :rule from SIC2.INDIVIDUS_ALL where stype = 'C' and sstatut_individu = 'V'")
				.setParameter("rule", SubBusinessRule.EXCLUDE_VALID_CALLER.businessRule).executeUpdate();
	}

	@Loggable
	@PurgeIndividualBusinessRule
	private void _excludeSonde(final Session customSession) {
		customSession
				.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_EXCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) select SGIN, :rule from SIC2.SONDE_NEWTEST")
				.setParameter("rule", SubBusinessRule.EXCLUDE_SONDE.businessRule).executeUpdate();
	}

	public ApplicationContext getContext() {
		return context;
	}

	// Get all external identifier that got modified in the last 12 months
	@PurgeIndividualBusinessRule
	@Loggable
	private void _includeExternalIdentifier(final Session customSession) {
		final Calendar calendarIndividuLastModified = Calendar.getInstance();
		calendarIndividuLastModified.setTime(_currentDate);
		calendarIndividuLastModified.add(Calendar.DATE, _tempo_inactive_rule_external);
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_INCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) "
						+ " select i.sgin, :rule from SIC2.INDIVIDUS_ALL i WHERE (i.STYPE = 'E') AND ( i.DDATE_MODIFICATION < :date OR i.DDATE_MODIFICATION IS NULL)")
				.setParameter("date", calendarIndividuLastModified.getTime())
				.setParameter("rule", SubBusinessRule.EXTERNAL_IDENTIFIER.businessRule).executeUpdate();
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	// Retrieve Individual type == "C not "V"
	@Loggable
	@PurgeIndividualBusinessRule
	private void _includeCallerNotValid(final Session customSession) {
		customSession
				.createNativeQuery(
						"INSERT /*+ parallel(" + _numberDatabaseCpuCore + ") */ INTO SIC2.TEMPORARY_PURGE_INCLUDE_BR"
								+ BusinessRule.OBSOLETE_INDIVIDUAL.getBusinessRule() + " (gin, business_rule) "
								+ " select i.sgin, :rule from SIC2.INDIVIDUS_ALL i WHERE i.STYPE = 'C' and sstatut_individu != 'V'")
				.setParameter("rule", SubBusinessRule.OBSOLETE_INDIVIDUAL.businessRule).executeUpdate();
	}

	// Retrieve Individual Merged (sstatut_individu == "T")
	@Loggable
	@PurgeIndividualBusinessRule
	private void _includeMergedIndividu(final Session customSession) {
		final Calendar calendarIndividuLastModified = Calendar.getInstance();
		calendarIndividuLastModified.setTime(_currentDate);
		calendarIndividuLastModified.add(Calendar.DATE, _tempo_inactive_rule_merged);
		customSession.createNativeQuery("INSERT /*+ parallel(" + _numberDatabaseCpuCore
						+ ") */ INTO SIC2.TEMPORARY_PURGE_INCLUDE_BR" + BusinessRule.INDIVIDUAL.getBusinessRule()
						+ " (gin, business_rule) "
						+ " select i.sgin, :rule from SIC2.INDIVIDUS_ALL i WHERE ( i.sstatut_individu = 'T') AND ( i.DDATE_MODIFICATION < :date OR i.DDATE_MODIFICATION IS NULL)")
				.setParameter("date", calendarIndividuLastModified.getTime())
				.setParameter("rule", SubBusinessRule.INCLUDE_MERGED_INDIVIDUAL.businessRule).executeUpdate();
	}

	@Loggable
	@PurgeIndividualInsertDetectedInd
	private void _insertAlreadyDetectedInd(final Session customSession) {
		customSession.createNativeQuery("insert /*+ parallel(" + _numberDatabaseCpuCore
				+ ") */ into SIC2.TEMPORARY_IND_TO_PURGE (gin, detected_date) select ind2.gin, ind2.detected_date from ("
				+ " select gin from IND_TO_PURGE " + " INTERSECT select gin from TEMPORARY_PURGE_DISTINCT_IND ) ind1 "
				+ " INNER JOIN SIC2.IND_TO_PURGE ind2 ON ind2.gin = ind1.gin ").executeUpdate();
	}

	public void deleteForgottenGininNeo4j(Driver neo4jDriver, String gin) {
		if (!checkIfIndExistInNeo4j(neo4jDriver, gin))
			return;
		try (Transaction transaction = neo4jDriver.session().beginTransaction()) {
			transaction.run("MATCH (r:Role {gin: $gin}) DETACH DELETE r",
					parameters("gin", gin));
			transaction.commit();
		}
	}

	public boolean checkIfIndExistInNeo4j(Driver neo4jDriver, String gin) {
		try (Transaction transaction = neo4jDriver.session().beginTransaction()) {
			Result result = transaction.run("MATCH (u:Role {gin: $gin})\n" +
							"WITH COUNT(u) > 0  as node_exists\n" +
							"RETURN node_exists",
					parameters("gin", gin));
			return result.single().get(0).asBoolean();
		}
	}

	private enum BusinessRule {
		OBSOLETE_INDIVIDUAL(2), INDIVIDUAL(1);
		private final int _businessRule;

		BusinessRule(final int br) {
			_businessRule = br;
		}

		public int getBusinessRule() {
			return _businessRule;
		}
	}

	public List<Future<?>> _runAllAnnotatedWith(final Class<? extends Annotation> annotationTarget)
			throws InterruptedException {
		final List<Future<?>> threads = new ArrayList<>();
		final ExecutorService exec = Executors.newFixedThreadPool(_numberDatabasePool);
		final Method[] methods = this.getClass().getDeclaredMethods();
		for (final Method method : methods) {
			final Annotation[] annotations = method.getDeclaredAnnotations();
			for (final Annotation annotation : annotations) {
				if (annotationTarget.isInstance(annotation)) {
					method.setAccessible(true);
					final InvokeThread invokeThread = new InvokeThread(method, this);
					threads.add(exec.submit(invokeThread));
				}
			}
		}
		exec.shutdown();
		return threads;
	}

	/*
	 * Select all individual that must be deleted
	 * ( same method as getSelectIndToPurge, but we add a constraint on
	 * "date_detected" to avoid deleting individual too soon
	 */
	@Loggable
	@SuppressWarnings("unchecked")
	private List<String> _selectIndividualToPurge() {
		final Calendar calendarDetectedDate = Calendar.getInstance();
		calendarDetectedDate.setTime(_currentDate);
		calendarDetectedDate.add(Calendar.DATE, _tempo_delay);
		return entityManager
				.createNativeQuery("select gin from SIC2.TEMPORARY_IND_TO_PURGE where detected_date < :date_detected ")
				.setParameter("date_detected", calendarDetectedDate).getResultList();
	}

	/*
	 * Select all individual that are planned to be deleted
	 */
	@Loggable
	@SuppressWarnings("unchecked")
	private List<String> _selectIndividualToPurgeLater() {
		return entityManager.createNativeQuery("select gin from SIC2.TEMPORARY_IND_TO_PURGE ").getResultList();
	}

	public enum PurgeMode {
		/*
		 * "BRUTAL_BULK_DELETE": no rollback available.
		 * In case of error in the middle on a batch, no rollback so
		 * these individual will have an inconsistent business state in the database
		 * until their are correctly deleted.
		 * No database backup before the delete (aka: no CSV export of the lines to be
		 * deleted.)
		 * The interest is that this mode is able to properly handle millions of
		 * individual delete in few hours.
		 * I suggest to do a database snapshot before using this mode (DBA request,
		 * deploy the snapshot in RC4 and SANDBOX). So in case of pb,
		 * easy recovery is possible.
		 * REQUIREMENTS: None
		 */
		BRUTAL_BULK_DELETE(1),

		/*
		 * "SAFE_DELETE": rollback available.
		 * In case of error in the middle of a batch, a rollback will
		 * be done, and the business state of the individual will remains consistent and
		 * untouched in the database.
		 * A backup is done before the delete (aka: CSV export of the lines to be
		 * deleted.)
		 * REQUIREMENTS: All foreign key used must be indexed. If not, the batch and
		 * others process will crash randomly with error ORA-00060
		 * The issue is: In the database, we have some foreign key that are not indexed.
		 * Because of that, if multiple session (aka: for this batch = threads) are
		 * doing delete operation on the same table, the oracle database crash with a
		 * deadlock ORA-00060.
		 * The only way to resolve this issue is either: Add index on every foreign key
		 * of the database OR use only 1 session ( = only 1 thread, and pray that no
		 * other process is doing delete operation on one of the table in the scope of
		 * the purge.).
		 * Additional references:
		 * https://stackoverflow.com/questions/31202352/ora-00060-while-running-multiple
		 * -delete-queries-on-one-table
		 * https://stackoverflow.com/questions/20474959/how-to-find-out-the-cause-of-an-
		 * oracle-deadlock
		 * https://stackoverflow.com/questions/10577000/how-can-i-identify-the-rows-
		 * involved-in-an-oracle-deadlock
		 * http://www.dba-oracle.com/
		 * t_ora_00060_deadlock_detected_while_waiting_for_resource.htm
		 * You can find all foreign key without index with this:
		 * http://www.dba-oracle.com/t_find_foreign_keys_with_no_index.htm
		 * If case the page got 404, here is the copy paste of the request, encoded in
		 * base 64
		 * DQpzZWxlY3QNCmNhc2UNCiAgIHdoZW4gYi50YWJsZV9uYW1lIGlzIG51bGwgdGhlbg0KICAgICAgJ3VuaW5kZXhlZCcNCiAgIGVsc2UNCiAgICAgICdpbmRleGVkJw0KZW5kIGFzIHN0YXR1cywNCiAgIGEudGFibGVfbmFtZSAgICAgIGFzIHRhYmxlX25hbWUsDQogICBhLmNvbnN0cmFpbnRfbmFtZSBhcyBma19uYW1lLA0KICBhLmZrX2NvbHVtbnMgICAgICBhcyBma19jb2x1bW5zLA0KICBiLmluZGV4X25hbWUgICAgICBhcyBpbmRleF9uYW1lLA0KICBiLmluZGV4X2NvbHVtbnMgICBhcyBpbmRleF9jb2x1bW5zDQpmcm9tDQooDQogICBzZWxlY3QgDQogICAgYS50YWJsZV9uYW1lLA0KICAgYS5jb25zdHJhaW50X25hbWUsDQogICBsaXN0YWdnKGEuY29sdW1uX25hbWUsICcsJykgd2l0aGluDQpncm91cCAob3JkZXIgYnkgYS5wb3NpdGlvbikgZmtfY29sdW1ucw0KZnJvbQ0KICAgZGJhX2NvbnNfY29sdW1ucyBhLA0KICAgZGJhX2NvbnN0cmFpbnRzIGINCndoZXJlDQogICBhLmNvbnN0cmFpbnRfbmFtZSA9IGIuY29uc3RyYWludF9uYW1lDQphbmQgDQogICBiLmNvbnN0cmFpbnRfdHlwZSA9ICdSJw0KYW5kIA0KICAgYS5vd25lciA9ICcmJnNjaGVtYV9vd25lcicNCmFuZCANCiAgIGEub3duZXIgPSBiLm93bmVyDQpncm91cCBieSANCiAgIGEudGFibGVfbmFtZSwgDQogICBhLmNvbnN0cmFpbnRfbmFtZQ0KKSBhDQosKA0Kc2VsZWN0IA0KICAgdGFibGVfbmFtZSwNCiAgIGluZGV4X25hbWUsDQogICBsaXN0YWdnKGMuY29sdW1uX25hbWUsICcsJykgd2l0aGluDQpncm91cCAob3JkZXIgYnkgYy5jb2x1bW5fcG9zaXRpb24pIGluZGV4X2NvbHVtbnMNCmZyb20NCiAgIGRiYV9pbmRfY29sdW1ucyBjDQp3aGVyZSANCiAgIGMuaW5kZXhfb3duZXIgPSAnJiZzY2hlbWFfb3duZXInDQpncm91cCBieQ0KICAgdGFibGVfbmFtZSwgDQogICBpbmRleF9uYW1lDQopIGINCndoZXJlDQogICBhLnRhYmxlX25hbWUgPSBiLnRhYmxlX25hbWUoKykNCmFuZCANCiAgIGIuaW5kZXhfY29sdW1ucygrKSBsaWtlIGEuZmtfY29sdW1ucyB8fCAnJScNCm9yZGVyIGJ5IA0KICAgMSBkZXNjLCAyOw
		 * ==
		 * 29/01/2018: we have 94 non indexed foreign key. I indexed the minimal
		 * required foreign key for the purge.
		 * So everything is OK now. But when someone create new tables and update this
		 * process, he need to ensure that the new foreign key are indexed correctly
		 */
		SAFE_DELETE(2);

		// Rule of dumb: Use the SAFE_DELETE unless you understand what you are doing
		private final int _mode;

		PurgeMode(final int mode) {
			_mode = mode;
		}

		public static PurgeMode parse(final int mode) throws InvalidParameterException {
			for (final PurgeMode m : PurgeMode.values()) {
				if (m.getMode() == mode) {
					return m;
				}
			}
			throw new InvalidParameterException("The mode " + mode + " doesn't exist");
		}

		public int getMode() {
			return _mode;
		}
	}

	public int getNumberDatabaseCPUCore() {
		return _numberDatabaseCpuCore;
	}

	public int getNumberDatabasePool() {
		return _numberDatabasePool;
	}

	private int _maximumNumberDeletion = 0;

	public int getMaximumNumberDeletion() {
		return _maximumNumberDeletion;
	}

	public void setMaximumNumberDeletion(int maximumNumberDeletion) {
		if(maximumNumberDeletion < 0) {
			maximumNumberDeletion = 0;
		}
		_maximumNumberDeletion = maximumNumberDeletion;
	}

	public int get_tempo_inactive_rule() {
		return _tempo_inactive_rule;
	}

	public void set_tempo_inactive_rule(int _tempo_inactive_rule) {
		this._tempo_inactive_rule = _tempo_inactive_rule;
	}

	public int get_tempo_inactive_rule_external() {
		return _tempo_inactive_rule_external;
	}

	public void set_tempo_inactive_rule_external(int _tempo_inactive_rule_external) {
		this._tempo_inactive_rule_external = _tempo_inactive_rule_external;
	}

	public int get_tempo_inactive_rule_merged() {
		return _tempo_inactive_rule_merged;
	}

	public void set_tempo_inactive_rule_merged(int _tempo_inactive_rule_merged) {
		this._tempo_inactive_rule_merged = _tempo_inactive_rule_merged;
	}

	public int get_tempo_optout_compref_sales_rule() {
		return _tempo_optout_compref_sales_rule;
	}

	public void set_tempo_optout_compref_sales_rule(int _tempo_optout_compref_sales_rule) {
		this._tempo_optout_compref_sales_rule = _tempo_optout_compref_sales_rule;
	}

	public int get_tempo_delay() {
		return _tempo_delay;
	}

	public void set_tempo_delay(int _tempo_delay) {
		this._tempo_delay = _tempo_delay;
	}

	public int get_tempo_cycle_life() {
		return _tempo_cycle_life;
	}

	public void set_tempo_cycle_life(int _tempo_cycle_life) {
		this._tempo_cycle_life = _tempo_cycle_life;
	}

	public int get_execMode() {
		return _execMode;
	}

	public void set_execMode(int _execMode) {
		this._execMode = _execMode;
	}

	public int get_period_in_days() {
		return _period_in_days;
	}

	public void set_period_in_days(int _period_in_days) {
		this._period_in_days = _period_in_days;
	}

	public Calendar get_limit_date_clickers() {
		return _limit_date_clickers;
	}

	public void set_limit_date_clickers(Calendar _limit_date_clickers) {
		this._limit_date_clickers = _limit_date_clickers;
	}

	public int get_period_in_days_ocp() {
		return _period_in_days_ocp;
	}

	public void set_period_in_days_ocp(int _period_in_days_ocp) {
		this._period_in_days_ocp = _period_in_days_ocp;
	}

	public long get_delay_in_ms() {
		return _delay_in_ms;
	}

	public void set_delay_in_ms(long _delay_in_ms) {
		this._delay_in_ms = _delay_in_ms;
	}


	public int get_max_records_cbs_report() {
		return _max_records_cbs_report;
	}

	public void set_max_records_cbs_report(int _max_records_cbs_report) {
		this._max_records_cbs_report = _max_records_cbs_report;
	}

	public int get_cpu_thread_cbs() {
		return _cpu_thread_cbs;
	}

	private enum SubBusinessRule {
		INDIVIDUAL_OR_PROSPECT(4), EXTERNAL_IDENTIFIER(10), EXCLUDE_COMPREF(2), EXCLUDE_SONDE(3), EXCLUDE_PROFIL_AF(1), EXCLUDE_BUSINESS_ROLE(6), EXCLUDE_MEMBRE(
				7), EXCLUDE_ROLE_CONTRACT(8), OBSOLETE_INDIVIDUAL(
				9), EXCLUDE_VALID_CALLER(13),
		INCLUDE_MERGED_INDIVIDUAL(14);
		private final int businessRule;

		SubBusinessRule(final int br) {
			businessRule = br;
		}
	}

	@Transactional
	public Map<List<String>, String> physicalDeletion(final List<String> gins, final boolean isGDPR)
			throws InvalidParameterException, MissingParameterException, ExecutionException, InterruptedException {
		Collections.sort(gins);
		int MAX_DELETE_BEFORE_COMMIT = 1000;
		if (_userDefinedBatchSize > 0) {
			MAX_DELETE_BEFORE_COMMIT = _userDefinedBatchSize;
		} else if (_userDefinedBatchSize <= 0 && PurgeMode.BRUTAL_BULK_DELETE.getMode() == _mode.getMode()) {
			MAX_DELETE_BEFORE_COMMIT = 4000;
		}
		final int nbPool = Runtime.getRuntime().availableProcessors() > _numberDatabasePool ? _numberDatabasePool
				: Runtime.getRuntime().availableProcessors();

		final ExecutorService exec = Executors.newFixedThreadPool(nbPool);
		final List<Future<DeleteThreadResult>> threads = new ArrayList<>();
		try {
			for (final List<String> partition : Lists.partition(gins, MAX_DELETE_BEFORE_COMMIT)) {
				final DeleteThread thread = new DeleteThread(partition, isGDPR, _batchPurge);
				final Future<DeleteThreadResult> future = exec.submit(thread);
				threads.add(future);
			}
		} finally {
			exec.shutdown();
		}

		final Map<List<String>, String> errors = new HashMap<>();
		for (final Future<DeleteThreadResult> thread : threads) {
			if (StringUtils.isBlank(thread.get()._error)) {
				continue;
			}
			errors.put(thread.get()._gins, thread.get()._error);
		}
		return errors;
	}

	public enum DatabaseType {
		INDIVIDUS("SIC2"), UTF8("SIC_UTF8"), PAYMENT("REPIND_PP");
		private final String user;

		DatabaseType(final String DBuser) {
			user = DBuser;
		}
	}

	public void setBackupWriteFileCallback(final BackupWriteFileCallback batch) {
		_batchPurge = batch;
	}

	public void setBatchSize(final int batchSize) {
		_userDefinedBatchSize = batchSize;
	}

	public void setMode(final int mode) throws InvalidParameterException {
		_mode = PurgeMode.parse(mode);
	}

	public void setNumberDatabaseCPUCore(int numberDatabaseCPUCore) {
		if (numberDatabaseCPUCore <= 0) {
			numberDatabaseCPUCore = 1;
		}
		_numberDatabaseCpuCore = numberDatabaseCPUCore > PurgeIndividualDS.MAX_NUMBER_DATABASE_CPU_CORE
				? PurgeIndividualDS.MAX_NUMBER_DATABASE_CPU_CORE
				: numberDatabaseCPUCore;
	}

	public void setNumberDatabasePool(int number_database_pool) {
		if (number_database_pool <= 0) {
			number_database_pool = 1;
		}
		_numberDatabasePool = number_database_pool > PurgeIndividualDS.MAX_DATABASE_POOL
				? PurgeIndividualDS.MAX_DATABASE_POOL
				: number_database_pool;
	}

	//REPIND-1324: We can only check Fidelio, Icare and PNR (Baggages and Caiman cases are linked to a PNR, we already checked on PNR)
	private class CBSThread implements Callable<List<CBSThreadResult>> {

		private ProvideCustomer360ViewRequest request;
		private List<String> gins;

		public CBSThread(List<String> gins) {
			this.gins = gins;
		}

		@Override
		public List<CBSThreadResult> call() throws Exception {

			Requestor requestorProvideCustomer360View = new Requestor();
			requestorProvideCustomer360View.setSite("QVI");
			requestorProvideCustomer360View.setSignature("RI_PURGE");
			requestorProvideCustomer360View.setChannel("CRM");

			ProvideCustomer360ViewRequest provideCustomer360Request = new ProvideCustomer360ViewRequest();
			provideCustomer360Request.setRequestor(requestorProvideCustomer360View);
			provideCustomer360Request.setBusinessScope(BUSINESS_SCOPE_CBS);
			provideCustomer360Request.setPeriodInDays(_period_in_days);

			this.request = provideCustomer360Request;

			List<CBSThreadResult> results = new ArrayList<CBSThreadResult>();

			for (String gin : gins) {
				String callDate = dateFormater.format(new Date());

				request.setGin(gin);

				List<String> complaintFidelioDetails = new ArrayList<String>();
				List<String> complaintIcareDetails = new ArrayList<String>();
				List<String> pnrActivityDetails = new ArrayList<String>();
				List<String> clickersDetails = new ArrayList<String>();

				//Create the Response
				ProvideCustomer360ViewResponse _provideCustomer360Response = null;

				try {
					// R3EPIND-1533 : Put a delay before calling CBS
					Thread.sleep(_delay_in_ms);

					//Call CBS
					ProvideCustomer360ViewV3 provideCustomer360ViewV3 = (ProvideCustomer360ViewV3) context.getBean("provideCustomer360ViewV3");
					_provideCustomer360Response = provideCustomer360ViewV3.provideCustomerView(request);

					if (_provideCustomer360Response != null) {

						Customer customer = _provideCustomer360Response.getCustomer().get(0);
						if (customer.getCustomerExperienceResponse() != null) {
							if (customer.getCustomerExperienceResponse().getCustomerCaseData() != null) {
								for (CustomerCase customerCase : customer.getCustomerExperienceResponse().getCustomerCaseData().getCustomerCases()) {
									if (customerCase != null && !StringUtils.isEmpty(customerCase.getProviderId())) {

										/*LOOKING FOR FIDELIO COMPLAINT*/
										if (customerCase.getProviderId().equalsIgnoreCase(FIDELIO_COMPLAINT)) {
											if (complaintFidelioDetails.size() < _max_records_cbs_report || _max_records_cbs_report == 0) {
												List<String> complaintFidelio = new ArrayList<String>();
												if (!StringUtils.isEmpty(customerCase.getCaseId())) {
													complaintFidelio.add(customerCase.getCaseId() + " ");
												}
												if (customerCase.getCreationDate() != null || customerCase.getLastModificationDate() != null) {
													complaintFidelio.add("(");
													if (customerCase.getCreationDate() != null) {
														complaintFidelio.add("creationDate: " + customerCase.getCreationDate().toString() + ", ");
													}
													if (customerCase.getLastModificationDate() != null) {
														complaintFidelio.add("lastModificationDate: " + customerCase.getLastModificationDate().toString() + ")");
													} else {
														complaintFidelio.add(")");
													}
												}
												complaintFidelioDetails.add(StringUtils.join(complaintFidelio, ""));
											}
										}

										/*LOOKING FOR ICARE COMPLAINT*/
										if (customerCase.getProviderId().equalsIgnoreCase(ICARE_COMPLAINT)) {
											if (complaintIcareDetails.size() < _max_records_cbs_report || _max_records_cbs_report == 0) {
												List<String> complaintIcare = new ArrayList<String>();
												if (!StringUtils.isEmpty(customerCase.getCaseId())) {
													complaintIcare.add(customerCase.getCaseId() + " ");
												}
												if (customerCase.getReceptionDate() != null || customerCase.getLastModificationDate() != null) {
													complaintIcare.add("(");
													if (customerCase.getReceptionDate() != null) {
														complaintIcare.add("receptionDate: " + customerCase.getReceptionDate().toString() + ", ");
													}
													if (customerCase.getLastModificationDate() != null) {
														complaintIcare.add("lastModificationDate: " + customerCase.getLastModificationDate().toString() + ")");
													} else {
														complaintIcare.add(")");
													}
												}
												complaintIcareDetails.add(StringUtils.join(complaintIcare, ""));
											}
										}
									}
								}
							}

							/*LOOKING FOR PNR ACTIVITY WITHIN +/- 1 YEAR*/
							if (customer.getCustomerExperienceResponse().getPnrData() != null) {
								for (Pnr pnr : customer.getCustomerExperienceResponse().getPnrData().getPnr()) {
									if (pnr.getPnrId() != null && pnr.getFlight() != null) {
										if (pnrActivityDetails.size() < _max_records_cbs_report || _max_records_cbs_report == 0) {
											List<String> pnrDetails = new ArrayList<String>();
											if (!StringUtils.isEmpty(pnr.getPnrId().getRecordLocator())) {
												pnrDetails.add(pnr.getPnrId().getRecordLocator() + " ");
												pnrDetails.add("(");
											}
											if (pnr.getPnrId().getPnrDate() != null) {
												pnrDetails.add("date: " + pnr.getPnrId().getPnrDate().toString() + ")");
											} else {
												pnrDetails.add(")");
											}
											pnrActivityDetails.add(StringUtils.join(pnrDetails, ""));
										}
									}
								}
							}
						}

						/*LOOKING FOR DATE CLICKERS*/
						if (customer.getCustomerProfileResponse() != null) {
							if (customer.getCustomerProfileResponse().getCrmProfileData() != null && customer.getCustomerProfileResponse().getCrmProfileData().getCrmProfiling() != null) {
								for (ProfilingData profilingData : customer.getCustomerProfileResponse().getCrmProfileData().getCrmProfiling()) {
									if (CLICKERS_PROFILING_DATA.equalsIgnoreCase(profilingData.getKey())) {
										if (StringUtils.isNotEmpty(profilingData.getKey())) {
											Calendar clickerDate = Calendar.getInstance();
											clickerDate.setTime(dateFormaterClickers.parse(profilingData.getValue()));
											if (clickerDate.after(_limit_date_clickers)) {
												clickersDetails.add(profilingData.getValue());
											}
										}
									}
								}
							}
						}

						if (complaintFidelioDetails.size() == 0 && complaintIcareDetails.size() == 0 && pnrActivityDetails.size() == 0 && clickersDetails.size() == 0) {
							results.add(new CBSThreadResult(request.getGin(), callDate, true, false, complaintFidelioDetails, complaintIcareDetails, pnrActivityDetails, clickersDetails, null));
						} else {
							results.add(new CBSThreadResult(request.getGin(), callDate, false, false, complaintFidelioDetails, complaintIcareDetails, pnrActivityDetails, clickersDetails, null));
						}
					} else {
						results.add(new CBSThreadResult(request.getGin(), callDate, false, true, null, null, null, null, "Response from ProvideCustomer360View was empty"));
					}
				} catch (ProvideCustomer360ViewBusinessException eProvide) {
					if (CBS_ERROR.contains(eProvide.getFaultInfo().getErrorCode().value())) {
						results.add(new CBSThreadResult(request.getGin(), callDate, true, true, null, null, null, null, eProvide.getFaultInfo().getFaultDescription()));
					} else {
						results.add(new CBSThreadResult(request.getGin(), callDate, false, true, null, null, null, null, eProvide.getFaultInfo().getFaultDescription()));
					}
				} catch (SystemException eSystem) {
					results.add(new CBSThreadResult(request.getGin(), callDate, false, true, null, null, null, null, eSystem.getFaultInfo().getFaultDescription()));
					// Add a general fault exception catch not to cut the process if something is getting wrong
				} catch (Exception e) {
					results.add(new CBSThreadResult(request.getGin(), callDate, false, true, null, null, null, null, e.getMessage()));
				}
			}
			return results;
		}
	}

	private class CBSThreadResult {

		private final String gin;
		private final String callDate;
		private final boolean purgeable;
		private final boolean error;

		private final List<String> complaintFidelioDetails;
		private final List<String> complaintIcareDetails;
		private final List<String> pnrActivityDetails;
		private final List<String> clickersDetails;

		private final String errorDetails;

		public CBSThreadResult(final String gin, final String callDate, final boolean purgeable, final boolean error, final List<String> complaintFidelioDetails,
							   final List<String> complaintIcareDetails, final List<String> pnrActivityDetails, final List<String> clickersDetails, final String errorDetails) {
			this.gin = gin;
			this.callDate = callDate;
			this.purgeable = purgeable;
			this.error = error;
			this.complaintFidelioDetails = complaintFidelioDetails;
			this.complaintIcareDetails = complaintIcareDetails;
			this.pnrActivityDetails = pnrActivityDetails;
			this.clickersDetails = clickersDetails;
			this.errorDetails = errorDetails;
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface PurgeIndividualBusinessRule {
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface PurgeIndividualCopyData {
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface PurgeIndividualInsertDetectedInd {
	}

	private class DeleteThread implements Callable<DeleteThreadResult> {
		private final List<String> _gins;
		private final boolean _isGDPR;
		private final Map<String, String> _backup = new HashMap<>();
		private final BackupWriteFileCallback _backupCallback;
		private final String _temporary_delete_table;
		private Map<DatabaseType, Session> _sessions = new HashMap<>();

		public DeleteThread(final List<String> gins, final boolean isGDPR,
							final BackupWriteFileCallback backupCallback) {
			_gins = gins;
			_isGDPR = isGDPR;
			_backupCallback = backupCallback;
			_temporary_delete_table = PurgeIndividualDS.TEMPORARY_DELETE_TABLE + _currentDate.getTime() + "_"
					+ _uniqueThreadId.getAndIncrement();
		}

		@Loggable
		private void _backup() {
			for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
				session.getValue().beginTransaction();
			}
			for (final Map.Entry<String, DatabaseType> request : PurgeIndividualDS.DELETE_REQUESTS.entrySet()) {
				final Session session = _sessions.get(request.getValue());
				final String requestSQL = request.getKey().replace(":TPD", _temporary_delete_table);
				final String requestString = "SELECT /*+ parallel(" + _numberDatabaseCpuCore + ") */ origin.* FROM "
						+ requestSQL + "= tpd.gin ";
				PurgeIndividualDS.logger.info(requestString);
				@SuppressWarnings("unchecked") final List<Object> results = session.createNativeQuery(requestString).list();
				StringBuilder data = new StringBuilder();
				for (final Object result : results) {
					if (result.getClass().isArray()) {
						for (final Object element : (Object[]) result) {
							data = data.append(element);
							data = data.append(";");
						}
					} else {
						data = data.append(result);
						data = data.append(";");
					}
					data = data.append("\n");
				}
				if (StringUtils.isBlank(data.toString())) {
					continue;
				}
				_backup.put(requestString, data.toString());
			}
			for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
				session.getValue().getTransaction().commit();
			}
		}

		@Loggable
		private void _deleteAllRequests() {
			/*
			 * Set global variable to disable the NEI / NSEI etc kind of trigger on delete.
			 * It's a manual work, if someone add a new trigger, he need to keep the use of
			 * this variable.
			 *
			 * The request should be
			 * _customSession.createSQLQuery("BEGIN
			 * SIC2.globals_variables.enable_triggers_events\\:=FALSE;END;")
			 * .executeUpdate();
			 * but because of a bug with hibernate < 4.1.5 SP1 (bad intepretation of PL/SQL
			 * parameter assignment ":="), we need to have a workaround
			 * by creating and calling a dedicated SQL stored procedure to set the variable.
			 * https://hibernate.atlassian.net/browse/HHH-2697
			 */

			if (PurgeMode.SAFE_DELETE.getMode() == _mode.getMode()) {
				for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
					session.getValue().beginTransaction();
				}
			}
			for (final Map.Entry<String, DatabaseType> request : PurgeIndividualDS.DELETE_REQUESTS.entrySet()) {
				_deleteSingleRequest(request);
			}
			if (PurgeMode.SAFE_DELETE.getMode() == _mode.getMode()) {
				for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
					session.getValue().getTransaction().commit();
				}
			}
		}

		@Loggable
		private void _deleteSingleRequest(Map.Entry<String, DatabaseType> request) {
			String parallel = "";
			if (PurgeMode.BRUTAL_BULK_DELETE.getMode() == _mode.getMode()) {
				for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
					session.getValue().beginTransaction();
				}
				parallel = " /*+ parallel(" + _numberDatabaseCpuCore + ") */ ";
			}
			try {
				final Session session = _sessions.get(request.getValue());
				final String requestSQL = request.getKey().replace(":TPD", _temporary_delete_table);
				session.createNativeQuery("DELETE " + parallel + " ( SELECT * FROM " + requestSQL + "= tpd.gin )")
						.executeUpdate();
			} catch (final Exception e) {
				PurgeIndividualDS.logger.error(request.getKey());
				throw e;
			}
			if (PurgeMode.BRUTAL_BULK_DELETE.getMode() == _mode.getMode()) {
				for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
					session.getValue().getTransaction().commit();
				}
			}
		}

		private void _deleteTemporaryTable() {
			for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
				session.getValue().beginTransaction();
				session.getValue()
						.createNativeQuery("truncate table " + session.getKey().user + "." + _temporary_delete_table)
						.executeUpdate();
				// REPIND-1493: Add purge keyword
				session.getValue().createNativeQuery("drop table " + session.getKey().user + "." + _temporary_delete_table + " purge ")
						.executeUpdate();
				session.getValue().getTransaction().commit();
			}
		}

		/*
		 * Store the gin to be immediately used into temporary table for easier
		 * delete SQL request (aka: INNER JOIN instead of IN )
		 */

		private void _insertGinsIntoTemporaryTable() {
			for (DatabaseType dbType : DatabaseType.values()) {
				Session session = _sessions.get(dbType);
				session.beginTransaction();
				String tablespace = "";
				if (StringUtils.equals(dbType.user, DatabaseType.INDIVIDUS.user)) {
					tablespace = " TABLESPACE SIC_TEMP ";
				}
				final String request = "CREATE TABLE " + dbType.user + "." + _temporary_delete_table
						+ " (gin VARCHAR2(12) not null) NOLOGGING " + tablespace + " STORAGE (INITIAL 100M next 100M)";
				logger.info(request);
				session.createNativeQuery(request).executeUpdate();
			}

			// Why the '100'? Small insert are faster than big insert. SQL request way
			// smaller so less likely to crash the database
			for (final List<String> subList : Lists.partition(_gins, 100)) {
				Map<DatabaseType, StringBuilder> requests = new HashMap<>();
				for (DatabaseType dbType : DatabaseType.values()) {
					requests.put(dbType, new StringBuilder());
				}
				final Map<String, String> parameters = new HashMap<>();
				for (int i = 0; i < subList.size(); i++) {
					final String parameter = "gin" + i;
					parameters.put(parameter, subList.get(i));
					for (DatabaseType dbType : DatabaseType.values()) {
						StringBuilder str = requests.get(dbType);
						str.append(" INTO ").append(dbType.user).append(".").append(_temporary_delete_table)
								.append(" (gin) VALUES ( :").append(parameter).append("  ) ");
						requests.put(dbType, str);
					}
				}
				for (DatabaseType dbType : DatabaseType.values()) {
					NativeQuery sqlquery = _sessions.get(dbType).createNativeQuery("INSERT /*+ parallel("
							+ _numberDatabaseCpuCore + ") */ ALL " + requests.get(dbType) + " SELECT 1 FROM DUAL");
					for (final Map.Entry<String, String> entry : parameters.entrySet()) {
						sqlquery = (NativeQuery) sqlquery.setParameter(entry.getKey(), entry.getValue());
					}
					sqlquery.executeUpdate();
				}
			}

			for (DatabaseType dbType : DatabaseType.values()) {
				Session session = _sessions.get(dbType);
				session.createNativeQuery("ALTER TABLE " + dbType.user + "." + _temporary_delete_table
								+ " ADD CONSTRAINT PK_" + _temporary_delete_table + " PRIMARY KEY (gin) nologging ")
						.executeUpdate();
				// commit temporary table creation
				session.getTransaction().commit();
			}
		}

		@Override
		/*
		 * - If DB var PURGE_EMERGENCY_STOP is true: STOP
		 * - Insert gin into temporary table
		 * - If backup option have been enabled: Extract the lines to be deleted as a
		 * CSV string stored in memory
		 * - Do the physical purge
		 * - If the backup option have been enabled: Call the callback with the CSV
		 * string stored in memory
		 * - Delete temporary table
		 * - If process is GPDR & no errors detected: do specific GDPR action
		 */
		@Loggable
		public DeleteThreadResult call() {
			try {
				final EntityManager customEntityManager = entityManagerFactory.createEntityManager();
				final EntityManager customEntityManagerUtf = entityManagerFactoryUtf.createEntityManager();
				final EntityManager customEntityManagerPayment = entityManagerFactoryPayment.createEntityManager();
				final Date debut = new Date();
				final boolean emergencyStop = Boolean.parseBoolean((String) customEntityManager
						.createNativeQuery("SELECT ENVVALUE FROM SIC2.ENV_VAR WHERE ENVKEY = 'PURGE_EMERGENCY_STOP'")
						.getSingleResult());
				if (emergencyStop) {
					final String error = "Emergency stop from 'ENV_VAR'->'PURGE_EMERGENCY_STOP'";
					PurgeIndividualDS.logger.error(error);
					return new DeleteThreadResult(error, _gins);
				}
				try {
					_sessions.put(DatabaseType.INDIVIDUS,
							((Session) customEntityManager.getDelegate()).getSessionFactory().openSession());
					_sessions.put(DatabaseType.UTF8,
							((Session) customEntityManagerUtf.getDelegate()).getSessionFactory().openSession());
					_sessions.put(DatabaseType.PAYMENT,
							((Session) customEntityManagerPayment.getDelegate()).getSessionFactory().openSession());

					for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
						session.getValue().beginTransaction();
						session.getValue().createNativeQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
						session.getValue().getTransaction().commit();
					}
					_insertGinsIntoTemporaryTable();
					if (PurgeMode.SAFE_DELETE.getMode() == _mode.getMode()) {
						if (_backupCallback != null) {
							_backup();
						}
						for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
							session.getValue().close();
						}
						_sessions.put(DatabaseType.INDIVIDUS,
								((Session) customEntityManager.getDelegate()).getSessionFactory().openSession());
						_sessions.put(DatabaseType.UTF8,
								((Session) customEntityManagerUtf.getDelegate()).getSessionFactory().openSession());
						_sessions.put(DatabaseType.PAYMENT,
								((Session) customEntityManagerPayment.getDelegate()).getSessionFactory().openSession());
					}
					_deleteAllRequests();
					if (_backupCallback != null) {
						_backupCallback.writeBackupFile(
								"backup_" + _currentDate.getTime() + "_" + _uniqueThreadId.getAndIncrement() + ".gz",
								_backup);
					}
					final Date fin = new Date();
					PurgeIndividualDS.logger.warn(
							_gins.size() + " deletions in " + (fin.getTime() - debut.getTime()) / 1000 + " seconds");
				} catch (final Exception e) {
					try {
						// We try to rollback any active transaction
						for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
							session.getValue().getTransaction().rollback();
						}
					} catch (final Exception e2) {
						// ignore
					}
					PurgeIndividualDS.logger.error(ExceptionUtils.getFullStackTrace(e));
					throw e;
				} finally {
					_deleteTemporaryTable();
					for (final Map.Entry<DatabaseType, Session> session : _sessions.entrySet()) {
						session.getValue().close();
					}
				}
				if (_isGDPR) {
					for (String gin : _gins) {
						forgottenIndividualDS.updateIndividualForPhysicalPurge(gin);
					}
				}
			} catch (final Exception e) {
				PurgeIndividualDS.logger.error(ExceptionUtils.getFullStackTrace(e));
				return new DeleteThreadResult(ExceptionUtils.getFullStackTrace(e) + "\n" + e.getMessage(), _gins);
			}
			return new DeleteThreadResult("", _gins);
		}
	}

	private class DeleteThreadResult {
		private final String _error;
		private final List<String> _gins;

		public DeleteThreadResult(final String error, final List<String> gins) {
			_error = error;
			_gins = gins;
		}
	}

	private class InvokeThread implements Runnable {
		private final Method _method;
		private final PurgeIndividualDS _instance;

		public InvokeThread(final Method method, final PurgeIndividualDS instance) {
			_method = method;
			_instance = instance;
		}

		@Override
		public void run() {
			try {
				final EntityManager customEntityManager = entityManagerFactory.createEntityManager();
				final Session session = ((Session) customEntityManager.getDelegate()).getSessionFactory().openSession();
				session.beginTransaction();
				session.createNativeQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
				_method.invoke(_instance, session);
				session.getTransaction().commit();
				session.close();
			} catch (IllegalAccessException | IllegalArgumentException e) {
				PurgeIndividualDS.logger.fatal(_method);
				PurgeIndividualDS.logger.fatal(ExceptionUtils.getStackTrace(e));
				System.exit(1);
			} catch (final InvocationTargetException e) {
				PurgeIndividualDS.logger.fatal(_method);
				PurgeIndividualDS.logger.fatal(ExceptionUtils.getStackTrace(e.getTargetException()));
				System.exit(1);
			}
		}
	}

	private final class PhysicalPurgeResult {
		private List<String> ginPurged;
		private Map<List<String>, String> errors;
		private List<String> cbsReport;
		private List<String> cbsReportDetails;

		private PhysicalPurgeResult() {
		}
	}

}
