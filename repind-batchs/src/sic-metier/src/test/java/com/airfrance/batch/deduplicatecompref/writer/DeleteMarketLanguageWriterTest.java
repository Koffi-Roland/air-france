package com.airfrance.batch.deduplicatecompref.writer;

import com.airfrance.batch.deduplicatecompref.config.DeduplicateComprefConfig;
import com.airfrance.batch.deduplicatecompref.model.MarketLanguageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit testing batch- Deduplicate communication preference writer
 */
@DataJpaTest
@ContextConfiguration(classes = DeduplicateComprefConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeleteMarketLanguageWriterTest {

    /**
     * Delete market language  writer - inject by spring
     */
    @Autowired
    private DeleteMarketLanguageWriter deleteMarketLanguageWriter;

    /**
     * Market language model
     */
    private MarketLanguageModel marketLanguageModel;

    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.marketLanguageModel = MarketLanguageModel.builder()
                .comPrefId(18200112)
                .marketLanguageId(123906643)
                .build();
    }

    @Test
    @Transactional
    @DisplayName("JUnit test for testing batch  delete market language communication preferences writer")
    //Here the Transactional annotation is to revert the transaction to prevent putting test data in the db
    public void testWriter(){
        List<MarketLanguageModel> marketLanguageModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> deleteMarketLanguageWriter.write(marketLanguageModels));
        marketLanguageModels.add(this.marketLanguageModel);
        assertNotNull(marketLanguageModels);
        assertThat(marketLanguageModels.size()).isEqualTo(1);
        assertDoesNotThrow(() -> this.deleteMarketLanguageWriter.write(marketLanguageModels));
    }

}
