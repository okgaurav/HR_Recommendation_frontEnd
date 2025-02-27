package com.sbs.hrRecommendation.swaggerconfig;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
//import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sbs.hrRecommendation"))
                .paths(PathSelectors.any())
                .build();
    }
    private Predicate<RequestHandler> customRequestHandlers(){
        return new Predicate<RequestHandler>(){
            @Override
            public boolean apply(RequestHandler input){
                Set<RequestMethod> methods= input.supportedMethods();
                return methods.contains(RequestMethod.GET)
                        || methods.contains(RequestMethod.POST)
                        || methods.contains(RequestMethod.PUT)
                        || methods.contains(RequestMethod.DELETE);
            }
        };
    }
}