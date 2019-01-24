package com.jmgits.sample.audit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.jmgits.sample.audit.util.Constant.HEADER_AUTH_TOKEN;
import static java.util.Arrays.asList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(@Value("${application.version}") String applicationVersion) {

        return new Docket(SWAGGER_2)
                .select()
                .paths(regex("/api/.*"))
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .apiInfo(apiInfo("sample-audit", "sample-audit", applicationVersion))
                .globalOperationParameters(asList(authHeader()))
        ;
    }

    //
    //  private

    private ApiInfo apiInfo(String title, String description, String version) {

        return new ApiInfoBuilder().title(title)
                .description(description)
                .version(version)
                .build();
    }

    private Parameter authHeader() {

        return new ParameterBuilder()
                .name(HEADER_AUTH_TOKEN)
                .description("Auth token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
    }


}
