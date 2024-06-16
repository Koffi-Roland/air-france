package com.afklm.repind.msv.doctor.role.config;


import com.afklm.repind.common.config.documentation.AbstractWebMvcConfigurer;
import com.afklm.repind.common.config.documentation.CommonConfig;
import com.afklm.repind.msv.doctor.role.client.role.create.CreateDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.delete.DeleteDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.RetrieveDoctorRoleClient;
import com.afklm.repind.msv.doctor.role.client.role.upsert.UpsertDoctorRoleClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/swagger.properties")
@Import( CommonConfig.class)
public class Config extends AbstractWebMvcConfigurer {

	@Value("${ri-manage-doctor-attributes.url}")
	private String manageDoctorAttributesUrl;

	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {

		final List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON);

		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(mediaTypes);

		final ObjectMapper objectMapper = converter.getObjectMapper();
		configureJSonMapper(objectMapper);

		converters.add(converter);
		converters.add(new ResourceHttpMessageConverter());
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder
				.setConnectTimeout(Duration.ofMillis(5000))
				.setReadTimeout(Duration.ofMillis(5000))
				.build();
	}

	@Bean
	public RetrieveDoctorRoleClient retrieveAttributesDoctor(){
		return new RetrieveDoctorRoleClient(manageDoctorAttributesUrl+"/role/{roleId}" , HttpMethod.GET , DoctorRoleResponseModel.class);
	}

	@Bean
	public UpsertDoctorRoleClient upsertDoctorAttributesClient(){
		return new UpsertDoctorRoleClient(manageDoctorAttributesUrl+"/role/{roleId}" , HttpMethod.PUT , DoctorRoleResponseModel.class);
	}

	@Bean
	public CreateDoctorRoleClient createDoctorRoleClient(){
		return new CreateDoctorRoleClient(manageDoctorAttributesUrl+"/role" , HttpMethod.POST , DoctorRoleResponseModel.class);
	}

	@Bean
	public DeleteDoctorRoleClient deleteDoctorRoleClient(){
		return new DeleteDoctorRoleClient(manageDoctorAttributesUrl+"/role/{roleId}" , HttpMethod.DELETE , DoctorRoleResponseModel.class);
	}

}