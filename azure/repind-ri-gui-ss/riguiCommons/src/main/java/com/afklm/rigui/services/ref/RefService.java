package com.afklm.rigui.services.ref;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.afklm.rigui.services.helper.ProvideRefTableHelper;
import com.afklm.rigui.wrapper.ref.WrapperRefTable;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001588.v1_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001588.v1_0_1.ProvideIndividualReferenceTableServiceV1;
import com.afklm.soa.stubs.w001588.v1_0_1.data.ProvideIndividualReferenceTableResponse;
import com.airfrance.reef.reftable.RefTableChamp;
import com.airfrance.reef.reftable.sic.RefTableDOM_PRO;
import com.airfrance.reef.reftable.sic.RefTableLANGUES;
import com.airfrance.reef.reftable.sic.RefTablePAYS;
import com.airfrance.reef.reftable.sic.RefTableREF_AUTORIS_MAIL;
import com.airfrance.reef.reftable.sic.RefTableREF_CIVILITE;
import com.airfrance.reef.reftable.sic.RefTableREF_CODE_TITRE;
import com.airfrance.reef.reftable.sic.RefTableREF_ETAT_ROLE_CTR;
import com.airfrance.reef.reftable.sic.RefTableREF_NIV_TIER_FB;
import com.airfrance.reef.reftable.sic.RefTableREF_SEXE;
import com.airfrance.reef.reftable.sic.RefTableREF_STATUT_INDIVIDU;

@Service
public class RefService {
	
	@Autowired
	@Qualifier("consumerW001588v01")
	private ProvideIndividualReferenceTableServiceV1 provideRefTable;

	@Autowired
	private ProvideRefTableHelper provideRefTableHelper;

	/**
	 * Fetch all the references of civilities in RefTable
	 */
	public List<RefTableChamp> findRefCivilities() {
		List<RefTableChamp> fields = RefTableREF_CIVILITE.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of titles in RefTable
	 */
	public List<RefTableChamp> findRefTitles() {
		List<RefTableChamp> fields = RefTableREF_CODE_TITRE.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of genders in RefTable
	 */
	public List<RefTableChamp> findRefGenders() {
		List<RefTableChamp> fields = RefTableREF_SEXE.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of individual status in RefTable
	 */
	public List<RefTableChamp> findRefStatus() {
		List<RefTableChamp> fields = RefTableREF_STATUT_INDIVIDU.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of individual authorization mailing in RefTable
	 */
	public List<RefTableChamp> findRefNat() {
		List<RefTableChamp> fields = RefTableREF_AUTORIS_MAIL.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of individual branches in RefTable
	 */
	public List<RefTableChamp> findRefBranches() {
		List<RefTableChamp> fields = RefTableDOM_PRO.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of individual languages in RefTable
	 */
	public List<RefTableChamp> findRefLanguages() {
		List<RefTableChamp> fields = RefTableLANGUES.instance().getLstChamps();
		return fields;
	}

	/**
	 * Fetch all the references of country codes in RefTable
	 */
	public List<RefTableChamp> findRefCountryCodes() {
		List<RefTableChamp> fields = RefTablePAYS.instance().getLstChamps();
		return fields;
	}
	
	/**
	 * Fetch all the references of FB tier levels in RefTable
	 */
	public List<RefTableChamp> findRefFBTierLevels() {
		List<RefTableChamp> fields = RefTableREF_NIV_TIER_FB.instance().getLstChamps();
		return fields;
	}

	public List<RefTableChamp> findRefStatesRoleContract() {
		List<RefTableChamp> fields = RefTableREF_ETAT_ROLE_CTR.instance().getLstChamps();
		return fields;
	}

	/**
	 * Cal WS RefTableV1 to get data
	 * 
	 * @return
	 */
	public WrapperRefTable findRefTable(String table) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		try {
			response = provideRefTable
					.provideIndividualReferenceTable(provideRefTableHelper.createProvideRequestForTable(table));
		} catch (BusinessErrorBlocBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return provideRefTableHelper.transformDataFromWsToWrapper(response);
	}

}
