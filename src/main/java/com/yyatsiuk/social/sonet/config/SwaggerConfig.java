package com.yyatsiuk.social.sonet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
@Profile("prod")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPs")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.softserve.ita.sonet"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SONET social network RESTful Web Service documentation",
                "This page document SONET RESTful Web Service endpoints",
                "1.0",
                "https://sonet-social.net/terms-of-service",
                new Contact("Sonet Team", "https://sonet-social.net", "sonet.social@gmail.com"),
                "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
