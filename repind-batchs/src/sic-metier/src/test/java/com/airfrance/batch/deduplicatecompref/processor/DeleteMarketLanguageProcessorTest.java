package com.airfrance.batch.deduplicatecompref.processor;

import com.airfrance.batch.deduplicatecompref.config.DeduplicateComprefConfig;
import com.airfrance.batch.deduplicatecompref.model.MarketLanguageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing batch - Deduplicate communication preferences processor
 */
@DataJpaTest
@ContextConfiguration(classes = DeduplicateComprefConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeleteMarketLanguageProcessorTest {

    /**
     * Delete market language processor - inject by spring
     */
    @Autowired
    private DeleteMarketLanguageProcessor deleteMarketLanguageProcessor;

    /**
     * Market language model
     */
    private MarketLanguageModel marketLanguageModel;

    /**
     * Communication preference identifier
     */
    public final static Integer COM_PREF_ID = 18200112;

    /**
     * Market language identifier
     */
    public final static Integer MARKET_LANGUAGE_ID = 123906643;

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
    @DisplayName("JUnit test for testing batch processor delete market language SNAF or SNKL")
    public void testProcessor() throws Exception {

        MarketLanguageModel marketLanguageModelTest = deleteMarketLanguageProcessor.process(this.marketLanguageModel);
        Objects.requireNonNull(marketLanguageModelTest);
        assertAll(
                () -> assertNotNull(marketLanguageModelTest.getComPrefId()),
                () -> assertNotNull(marketLanguageModelTest.getMarketLanguageId()),
                () -> assertEquals(COM_PREF_ID, marketLanguageModelTest.getComPrefId()),
                () -> assertEquals(MARKET_LANGUAGE_ID, marketLanguageModelTest.getMarketLanguageId())
        );
    }


}
