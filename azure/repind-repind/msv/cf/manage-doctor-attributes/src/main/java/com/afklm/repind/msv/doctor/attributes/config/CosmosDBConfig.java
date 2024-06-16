package com.afklm.repind.msv.doctor.attributes.config;


import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosClientTelemetryConfig;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.core.ResponseDiagnostics;
import com.azure.spring.data.cosmos.core.ResponseDiagnosticsProcessor;
import com.azure.spring.data.cosmos.exception.ConfigurationException;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


/**
 * Cosmos Db configuration
 * Note: Setting the value to -1 means  the system automatically decides the number used by default
 */
@Configuration
@EnableCosmosRepositories(basePackages ="com.afklm.repind.msv.doctor.attributes.repository")
@EntityScan("com.afklm.repind.msv.doctor.attributes.entity")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Slf4j
public class CosmosDBConfig extends AbstractCosmosConfiguration {

	/**
	 * Database url
	 */
	@Value("${azure.cosmos.uri}")
	private String uri;
	/**
	 * Key vault namespace
	 */
	@Value("${az-vault.namespace}")
	private String keyVaultName;
	/**
	 * Database Primary key
	 */
	@Value("${az-vault.secrets.cosmosDbKey.secretName}")
	private String keyName;

	/**
	 *  Database Secondary key
	 */
	@Value("${az-vault.secrets.cosmosDbSecondaryKey.secretName}")
	private String secondaryKeyName;
	/**
	 * Database name
	 */
	@Value("${azure.cosmos.database}")
	private String database;
	/**
	 * 	Set queryMetricsEnabled flag to true in application.properties to enable query metrics
	 */
	@Value("${azure.cosmos.queryMetricsEnabled}")
	private boolean queryMetricsEnabled;
	/**
	 * Set maxDegreeOfParallelism flag to an integer in application.properties to allow parallel processing
	 */
	@Value("${azure.cosmos.maxDegreeOfParallelism}")
	private int maxDegreeOfParallelism;
	/**
	 * Set maxBufferedItemCount flag to an integer in application.properties to allow the user to set the max number of items that can be buffered during parallel query execution
	 */
	@Value("${azure.cosmos.maxBufferedItemCount}")
	private int maxBufferedItemCount;
	/**
	 * Set responseContinuationTokenLimitInKb flag to an integer in application.properties to allow the user to limit the length of the continuation token in the query response
	 */
	@Value("${azure.cosmos.responseContinuationTokenLimitInKb}")
	private int responseContinuationTokenLimitInKb;

	/**
	 * Set pointOperationLatencyThresholdInMS  to enable diagnostics at the client level when these thresholds are exceeded
	 */

	@Value("${azure.cosmos.diagnosticsThresholds.pointOperationLatencyThresholdInMS}")
	private int pointOperationLatencyThresholdInMS;
	/**
	 * Set nonPointOperationLatencyThresholdInMS to enable diagnostics at the client level when these thresholds are exceeded
	 */
	@Value("${azure.cosmos.diagnosticsThresholds.nonPointOperationLatencyThresholdInMS}")
	private int nonPointOperationLatencyThresholdInMS;
	/**
	 * Set requestChargeThresholdInRU to enable diagnostics at the client level when these thresholds are exceeded
	 */
	@Value("${azure.cosmos.diagnosticsThresholds.requestChargeThresholdInRU}")
	private int requestChargeThresholdInRU;

	/**
	 * payloadSizeThresholdInBytes to enable diagnostics at the client level when these thresholds are exceeded
	 */
	@Value("${azure.cosmos.diagnosticsThresholds.payloadSizeThresholdInBytes}")
	private int payloadSizeThresholdInBytes;

	/**
	 * Azure Key credential
	 */
	private AzureKeyCredential azureKeyCredential;

	/**
	 * Path protocol
	 */
	 private static final String PATH_PROTOCOL  = "https://";
	/**
	 * Key Vault URI
	 */
	private static final String KEY_URI  = ".vault.azure.net";

	/**
	 * Get database name
	 *
	 * @return database name
	 */
	@Override
	protected String getDatabaseName() {
		return database;
	}

	/**
	 * Get Cosmos client builder
	 *
	 * @return CosmosClientBuilder
	 */
	@Bean
	public CosmosClientBuilder getCosmosClientBuilder() {
		String key = this.getKeyVaultSecretValue(keyName);
		if(StringUtils.isNotEmpty(key)){
			this.azureKeyCredential = new AzureKeyCredential(key);
		}
		else {
			throw new ConfigurationException("can't not access database with nullable key");
		}

		return new CosmosClientBuilder()
				.endpoint(uri)
				.credential(azureKeyCredential)
				.gatewayMode()
				.clientTelemetryConfig(
						new CosmosClientTelemetryConfig()
								.diagnosticsThresholds(
										new CosmosDiagnosticsThresholds()
												.setNonPointOperationLatencyThreshold(Duration.ofMillis(nonPointOperationLatencyThresholdInMS))
												.setPointOperationLatencyThreshold(Duration.ofMillis(pointOperationLatencyThresholdInMS))
												.setPayloadSizeThreshold(payloadSizeThresholdInBytes)
												.setRequestChargeThreshold(requestChargeThresholdInRU)
								)
								.diagnosticsHandler(CosmosDiagnosticsHandler.DEFAULT_LOGGING_HANDLER));
	}

	/**
	 * Get cosmos database configuration
	 *
	 * @return CosmosConfig
	 */
	@Override
	public CosmosConfig cosmosConfig() {
		return CosmosConfig.builder()
				.enableQueryMetrics(queryMetricsEnabled)
				.maxDegreeOfParallelism(maxDegreeOfParallelism)
				.maxBufferedItemCount(maxBufferedItemCount)
				.responseContinuationTokenLimitInKb(responseContinuationTokenLimitInKb)
				.responseDiagnosticsProcessor(new ResponseDiagnosticsProcessorImplementation())
				.build();
	}


	/**
	 * Get key vault secret value
	 *
	 * @param secretName secret name
	 * @return secret name
	 * @throws IllegalArgumentException exception
	 */
	public String getKeyVaultSecretValue(String secretName) throws IllegalArgumentException {

		String keyVaultUri = PATH_PROTOCOL + keyVaultName + KEY_URI;

		SecretClient secretClient = new SecretClientBuilder()
				.vaultUrl(keyVaultUri)
				.credential(new DefaultAzureCredentialBuilder().build())
				.buildClient();

		KeyVaultSecret retrievedSecret = secretClient.getSecret(secretName);

		String secretValue = retrievedSecret.getValue();

		log.info("Your secret's value is '{}'.", secretValue);
		return secretValue;
	}
	public void switchToSecondaryKey()
	{
		String secondaryKey = this.getKeyVaultSecretValue(secondaryKeyName);
		if(StringUtils.isNotEmpty(secondaryKey)){
			this.azureKeyCredential.update(secondaryKey);
		}
		else {
			throw new ConfigurationException("can't not access database with nullable key");
		}
	}


	/**
	 * Static class for the response diagnostic
	 */
	private static class ResponseDiagnosticsProcessorImplementation implements ResponseDiagnosticsProcessor {

		@Override
		public void processResponseDiagnostics(@Nullable ResponseDiagnostics responseDiagnostics) {
			log.info("Response Diagnostics {}", responseDiagnostics);
		}
	}
}
