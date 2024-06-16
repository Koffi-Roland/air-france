package com.afklm.repind.config;

import com.afklm.repind.v1.createorupdatecomprefbasedonpermws.CreateOrUpdateComPrefBasedOnPermissionImplV1;
import com.afklm.repind.v1.createorupdaterolecontractws.CreateOrUpdateRoleContractImplV1;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.soa.jaxws.spring.SpringBindingBuilder;
import com.afklm.soa.stubs.w000442.v6.CreateUpdateIndividualServiceV6;
import com.afklm.soa.stubs.w000442.v7.CreateUpdateIndividualServiceV7;
import com.afklm.soa.stubs.w000442.v8.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w001567.v1.CreateOrUpdateRoleContractServiceV1;
import com.afklm.soa.stubs.w001950.v1.CreateOrUpdateComPrefBasedOnPermissionV1;
import com.sun.xml.ws.transport.http.servlet.SpringBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WSConfig {
    
	/*
	 *  CreateUpdateIndividualServiceV6
	 * 
	 */
    @Bean
    public SpringBinding wsCreateUpdateIndividualServiceV6() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrUpdateIndividualImplV6()).url("/ws/passenger/marketing/000442v06");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateUpdateIndividualServiceV6 createOrUpdateIndividualImplV6() {

        return new CreateOrUpdateIndividualImplV6();
    }

	/*
	 *  CreateUpdateIndividualServiceV7
	 * 
	 */
    @Bean
    public SpringBinding wsCreateUpdateIndividualServiceV7() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrUpdateIndividualImplV7()).url("/ws/passenger/marketing/000442v07");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateUpdateIndividualServiceV7 createOrUpdateIndividualImplV7() {

        return new CreateOrUpdateIndividualImplV7();
    }

	/*
	 *  CreateUpdateIndividualServiceV8
	 * 
	 */
    @Bean
    public SpringBinding wsCreateUpdateIndividualServiceV8() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrUpdateIndividualImplV8()).url("/ws/passenger/marketing/000442v08");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateUpdateIndividualServiceV8 createOrUpdateIndividualImplV8() {

        return new CreateOrUpdateIndividualImplV8();
    }


    @Bean
    public SpringBinding wsCreateOrUpdateRoleContractServiceV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrUpdateRoleContractImplV1()).url("/ws/passenger/marketing/001567v01");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateOrUpdateRoleContractServiceV1 createOrUpdateRoleContractImplV1() {

        return new CreateOrUpdateRoleContractImplV1();
    }
    

	/*
	 *  CreateOrUpdateComPrefBasedOnPermissionV1
	 * 
	 */
    @Bean
    public SpringBinding wsCreateOrUpdateComPrefBasedOnPermissionV1() throws Exception {

        SpringBindingBuilder springBindingBuilder = new SpringBindingBuilder();
        springBindingBuilder.bean(createOrUpdateComPrefBasedOnPermissionImplV1()).url("/ws/passenger/marketing/001950v01");
        return springBindingBuilder.build();
    }

    @Bean
    public CreateOrUpdateComPrefBasedOnPermissionV1 createOrUpdateComPrefBasedOnPermissionImplV1() {

        return new CreateOrUpdateComPrefBasedOnPermissionImplV1();
    }
}
