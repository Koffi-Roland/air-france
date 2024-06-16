package com.airfrance.config;

import com.airfrance.paymentpreferenceswsv2.DeletePaymentPreferencesImpl;
import com.airfrance.paymentpreferenceswsv2.ProvidePaymentPreferencesImpl;
import com.airfrance.paymentpreferenceswsv2.v2.CreateOrReplacePaymentPreferencesV2Impl;
import com.airfrance.paymentpreferenceswsv2.v2.ProvidePaymentPreferencesV2Impl;
import org.springframework.context.annotation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.net.MalformedURLException;

@Profile("test")

@Configuration
@ComponentScan(basePackages = "com.afklm.repindpp",
excludeFilters={@ComponentScan.Filter(type=FilterType.ASPECTJ, pattern = "com.afklm.repind.config.*")})
@Import(com.airfrance.repind.config.WebTestConfig.class)
public class WebTestConfig {
	
	@Bean(name = "passenger_ProvidePaymentPreferences-v1Bean")
	public ProvidePaymentPreferencesImpl providePaymentPreferencesImpl() throws MalformedURLException{
		return new ProvidePaymentPreferencesImpl();
	}
		
	@Bean(name = "passenger_ProvidePaymentPreferences-v2Bean")
	public ProvidePaymentPreferencesV2Impl providePaymentPreferencesV2Impl() throws MalformedURLException{
		return new ProvidePaymentPreferencesV2Impl();
	}
	
	@Bean(name = "passenger_DeletePaymentPreferences-v1Bean")
	public DeletePaymentPreferencesImpl deletePaymentPreferencesImpl() throws MalformedURLException{
		return new DeletePaymentPreferencesImpl();
	}
	
	@Bean(name = "passenger_CreateOrReplacePaymentPreferences-v2Bean")
	public CreateOrReplacePaymentPreferencesV2Impl createOrReplacePaymentPreferencesV2Impl() throws MalformedURLException{
		return new CreateOrReplacePaymentPreferencesV2Impl();
	}

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(8);
		executor.setMaxPoolSize(12);
		executor.setThreadNamePrefix("test_task_executor_thread");
		executor.initialize();

		return executor;
	}

}
