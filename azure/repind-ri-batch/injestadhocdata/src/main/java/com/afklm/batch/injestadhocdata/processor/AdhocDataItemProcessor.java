package com.afklm.batch.injestadhocdata.processor;

import com.afklm.batch.injestadhocdata.bean.AdhocDataItem;
import com.afklm.batch.injestadhocdata.bean.InputRecord;
import com.afklm.batch.injestadhocdata.helper.FileParserHelper;
import com.afklm.batch.injestadhocdata.property.InjestAdhocDataPropoerty;
import com.afklm.batch.injestadhocdata.service.InjestAdhocDataSummaryService;
import com.airfrance.repind.dao.reference.RefComPrefDgtRepository;
import com.airfrance.repind.dao.reference.RefMarketCountryLanguageRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.afklm.batch.injestadhocdata.helper.Constant.*;
import static com.airfrance.batch.common.utils.IConstants.SCODE_CONTEXT;

@Service("adhocDataItemProcessor")
@Slf4j
public class AdhocDataItemProcessor implements ItemProcessor<InputRecord, AdhocDataItem> {

	@Autowired
	InjestAdhocDataSummaryService summaryService;

	@Autowired
	private RefMarketCountryLanguageRepositoryCustom refMarketCountryLanguageRepository;

	@Autowired
	private RefComPrefDgtRepository refComPrefDgtRepository;

	/**
	 * helper to read configuration properties
	 */
	@Autowired
	private InjestAdhocDataPropoerty property;

	@Transactional(readOnly = true)
	@Override
	public AdhocDataItem process(@NotNull InputRecord input) {
		summaryService.incrementTotalCounter();
		AdhocDataItem item = null;
		List<String> missingFields = FileParserHelper.getMissingFields(input);
		if (!CollectionUtils.isEmpty(missingFields)) {
			log.debug("Fields missing.");
			summaryService.incrementValidationCounter();
			StringBuilder message = new StringBuilder(MANDATORY_INFORMATION_MISSING).append(input.getEmailAddress())
					.append(INPUT_MISSING).append(missingFields.toString());
			summaryService.addErrorMessage(message.toString());
			return item;
		}
		upperCaseInputData(input);

		if (!validateInput(input)) {
			log.debug("Validation failed.");
			summaryService.incrementValidationCounter();
			StringBuilder message = new StringBuilder(INCORRECT_DATA).append(input.getEmailAddress())
					.append(OPEN_SQUARE_BRACKET).append(SUBSCRIPTION_TYPE).append(input.getSubscriptionType())
					.append(COMMA).append(DOMAIN).append(input.getDomain()).append(COMMA).append(GROUP_TYPE)
					.append(input.getGroupType()).append(CLOSING_SQUARE_BRACKET);
			summaryService.addErrorMessage(message.toString());
			return item;
		} else {
			log.debug("Validation successful.");
			String isoLanguage = getIsoMarketLanguage(input.getCountryCode(), input.getLanguageCode());
			item = FileParserHelper.populateIndividual(input, isoLanguage, property);
		}
		return item;
	}

	private boolean validateInput(InputRecord input) {
		int count = refComPrefDgtRepository.countByDomainGroupAndType(input.getDomain(), input.getGroupType(),
				input.getSubscriptionType());
		if (!input.getSubscriptionType().equals("AF") && !input.getSubscriptionType().equals("KL")){
			return false;
		}
		return (count > 0);
	}

	private String getIsoMarketLanguage(String market, String language) {
		String isoLanguage = refMarketCountryLanguageRepository.findCodeIsoByMarketLangNotIsoContext(market, language,
				SCODE_CONTEXT);
		return StringUtils.isBlank(isoLanguage) ? language : isoLanguage;
	}

	private void upperCaseInputData(InputRecord input) {

		if (StringUtils.isNotEmpty(input.getCivility())) {
			input.setCivility(input.getCivility().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getCountryCode())) {
			input.setCountryCode(input.getCountryCode().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getLanguageCode())) {
			input.setLanguageCode(input.getLanguageCode().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getSubscriptionType())) {
			input.setSubscriptionType(input.getSubscriptionType().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getDomain())) {
			input.setDomain(input.getDomain().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getGroupType())) {
			input.setGroupType(input.getGroupType().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getStatus())) {
			input.setStatus(input.getStatus().toUpperCase());
		}
		if (StringUtils.isNotEmpty(input.getPreferredDepartureAirport())) {
			input.setPreferredDepartureAirport(input.getPreferredDepartureAirport().toUpperCase());
		}
	}

}
