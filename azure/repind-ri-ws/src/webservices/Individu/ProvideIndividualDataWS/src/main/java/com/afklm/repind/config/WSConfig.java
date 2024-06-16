package com.afklm.repind.config;

import com.afklm.repind.provideindividualreferencetable.v1.ProvideIndividualReferenceTableV1Impl;
import com.afklm.repind.provideindividualreferencetable.v2.ProvideIndividualReferenceTableV2Impl;
import com.afklm.repind.v4.provideindividualdata.ProvideIndividualDataV4Impl;
import com.afklm.repind.v5.provideindividualdata.ProvideIndividualDataV5Impl;
import com.afklm.repind.v6.provideindividualdata.ProvideIndividualDataV6Impl;
import com.afklm.repind.v7.provideindividualdata.ProvideIndividualDataV7Impl;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w000418.v4.ProvideIndividualDataServiceV4;
import com.afklm.soa.stubs.w000418.v5.ProvideIndividualDataServiceV5;
import com.afklm.soa.stubs.w000418.v6.ProvideIndividualDataServiceV6;
import com.afklm.soa.stubs.w000418.v7.ProvideIndividualDataServiceV7;
import com.afklm.soa.stubs.w001588.v1.ProvideIndividualReferenceTableServiceV1;
import com.afklm.soa.stubs.w001588.v2.ProvideIndividualReferenceTableServiceV2;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {

	/*
	 *  ProvideIndividualDataV4
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualDataServiceV4() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualDataV4Impl()).url("/ws/passenger/marketing/000418v04");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualDataServiceV4 provideIndividualDataV4Impl() {

        return new ProvideIndividualDataV4Impl();
    }

	/*
	 *  ProvideIndividualDataV5
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualDataServiceV5() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualDataV5Impl()).url("/ws/passenger/marketing/000418v05");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualDataServiceV5 provideIndividualDataV5Impl() {

        return new ProvideIndividualDataV5Impl();
    }

	/*
	 *  ProvideIndividualDataV6
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualDataServiceV6() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualDataV6Impl()).url("/ws/passenger/marketing/000418v06");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualDataServiceV6 provideIndividualDataV6Impl() {

        return new ProvideIndividualDataV6Impl();
    }

	/*
	 *  ProvideIndividualDataV7
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualDataServiceV7() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualDataV7Impl()).url("/ws/passenger/marketing/000418v07");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualDataServiceV7 provideIndividualDataV7Impl() {

        return new ProvideIndividualDataV7Impl();
    }

	/*
	 *  ProvideIndividualReferenceTableV1
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualReferenceTableV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualReferenceTableV1Impl()).url("/ws/passenger/marketing/001588v01");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualReferenceTableServiceV1 provideIndividualReferenceTableV1Impl() {

        return new ProvideIndividualReferenceTableV1Impl();
    }

	/*
	 *  ProvideIndividualReferenceTableV2
	 * 
	 */
    @Bean
    public SpringBinding wsProvideIndividualReferenceTableV2() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(provideIndividualReferenceTableV2Impl()).url("/ws/passenger/marketing/001588v02");
        return springBindingBuilder.build();
    }

    @Bean
    public ProvideIndividualReferenceTableServiceV2 provideIndividualReferenceTableV2Impl() {

        return new ProvideIndividualReferenceTableV2Impl();
    }
}
