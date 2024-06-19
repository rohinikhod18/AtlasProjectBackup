package com.currenciesdirect.gtg.compliance.compliancesrv.restdocs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class ComplianceSwaggerConfig {
	@Bean
	public Docket customDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Compliance")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Compliance Services Documentation")
                .description("Compliance Services Documentation for API developers")
                .termsOfServiceUrl("http://www.currenciesdirect.com")
                .license("Currencies Direct")
                .licenseUrl("http://www.currenciesdirect.com/LICENSE")
                .version("1.0")
                .build();
    }
}	