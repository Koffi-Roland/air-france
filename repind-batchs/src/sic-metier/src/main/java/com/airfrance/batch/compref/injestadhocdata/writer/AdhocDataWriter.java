package com.airfrance.batch.compref.injestadhocdata.writer;

import com.airfrance.batch.compref.injestadhocdata.bean.AdhocDataItem;
import com.airfrance.batch.compref.injestadhocdata.service.InjestAdhocDataSummaryService;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.*;

@Service("adhocDataWriter")
@Slf4j
public class AdhocDataWriter implements ItemWriter<AdhocDataItem> {

	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateIndividualDs;

	@Autowired
	private InjestAdhocDataSummaryService summaryService;

	@Override
	public void write(List<? extends AdhocDataItem> items) {
		log.info("adhocDataWriter");
		if (!CollectionUtils.isEmpty(items)) {
			items.forEach(this::manageItemCreationOrUpdate);
		}
	}

	private void manageItemCreationOrUpdate(@NotNull AdhocDataItem item) {
		String email = item.getEmail();
		try {
			String outputGin = createOrUpdateIndividualDs.createOrUpdateSubscriptionSharepoint(item.getIndividual(), email,
					item.getGin(), item.getMarket());
			log.debug("GIN : " + outputGin);
			summaryService.incrementProcessedCounter();
		} catch (Exception e) {
			StringBuilder msg = new StringBuilder(PROCESSING_FAILED_FOR_EMAIL).append(email).append(OPEN_SQUARE_BRACKET)
					.append(MARKET).append(item.getMarket()).append(COMMA).append(LANGUAGE).append(item.getLanguage())
					.append(CLOSING_SQUARE_BRACKET).append(ERROR).append(e.toString());
			log.error(msg.toString());
			summaryService.incrementFailedCounter();
			summaryService.addErrorMessage(msg.toString());
		}
	}
}
