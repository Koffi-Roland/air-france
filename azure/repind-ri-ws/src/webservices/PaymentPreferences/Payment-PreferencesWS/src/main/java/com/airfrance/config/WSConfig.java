package com.airfrance.config;

import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w000469.v1.ProvidePaymentPreferencesV1;
import com.afklm.soa.stubs.w000469.v2_0_1.ProvidePaymentPreferencesV2;
import com.afklm.soa.stubs.w000470.v1.CreateOrReplacePaymentPreferencesV1;
import com.afklm.soa.stubs.w000470.v2_0_1.CreateOrReplacePaymentPreferencesV2;
import com.afklm.soa.stubs.w000471.v1.DeletePaymentPreferencesV1;
import com.afklm.soa.stubs.w000471.v2.DeletePaymentPreferencesV2;
import com.airfrance.paymentpreferenceswsv2.CreateOrReaplacePaymentPreferencesImpl;
import com.airfrance.paymentpreferenceswsv2.DeletePaymentPreferencesImpl;
import com.airfrance.paymentpreferenceswsv2.ProvidePaymentPreferencesImpl;
import com.airfrance.paymentpreferenceswsv2.v2.CreateOrReplacePaymentPreferencesV2Impl;
import com.airfrance.paymentpreferenceswsv2.v2.DeletePaymentPreferencesV2Impl;
import com.airfrance.paymentpreferenceswsv2.v2.ProvidePaymentPreferencesV2Impl;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  CreateOrReplacePaymentPreferencesV1
	 * 
	 */
    @Bean
    public SpringBinding wsCreateOrReplacePaymentPreferencesV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrReaplacePaymentPreferencesImpl()).url("/ws/passenger/marketing/000470v01");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateOrReplacePaymentPreferencesV1 createOrReaplacePaymentPreferencesImpl() {

        return new CreateOrReaplacePaymentPreferencesImpl();
    }

    /*
	 *  DeletePaymentPreferencesV1
	 * 
	 */
    @Bean
    public SpringBinding wsDeletePaymentPreferencesV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(deletePaymentPreferencesImpl()).url("/ws/passenger/marketing/000471v01");
        return springBindingBuilder.build();
    }

    @Bean
    public DeletePaymentPreferencesV1 deletePaymentPreferencesImpl() {

        return new DeletePaymentPreferencesImpl();
    }
    
    /*
	 *  ProvidePaymentPreferencesV1
	 * 
	 */
    @Bean
    public SpringBinding wsProvidePaymentPreferencesV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(providePaymentPreferencesImpl()).url("/ws/passenger/marketing/000469v01");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvidePaymentPreferencesV1 providePaymentPreferencesImpl() {

        return new ProvidePaymentPreferencesImpl();
    }
    
    /*
	 *  CreateOrReplacePaymentPreferencesV2
	 * 
	 */
    @Bean
    public SpringBinding wsCreateOrReplacePaymentPreferencesV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrReplacePaymentPreferencesV2Impl()).url("/ws/passenger/marketing/000470v02");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateOrReplacePaymentPreferencesV2 createOrReplacePaymentPreferencesV2Impl() {

        return new CreateOrReplacePaymentPreferencesV2Impl();
    }
    
    /*
	 *  DeletePaymentPreferencesV2
	 * 
	 */
    @Bean
    public SpringBinding wsDeletePaymentPreferencesV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(deletePaymentPreferencesV2Impl()).url("/ws/passenger/marketing/000471v02");
        return springBindingBuilder.build();
    }

    @Bean
    public DeletePaymentPreferencesV2 deletePaymentPreferencesV2Impl() {

        return new DeletePaymentPreferencesV2Impl();
    }
    
    /*
	 *  ProvidePaymentPreferencesV2
	 * 
	 */
    @Bean
    public SpringBinding wsProvidePaymentPreferencesV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(providePaymentPreferencesV2Impl()).url("/ws/passenger/marketing/000469v02");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvidePaymentPreferencesV2 providePaymentPreferencesV2Impl() {

        return new ProvidePaymentPreferencesV2Impl();
    }    
}
