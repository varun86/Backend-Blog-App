package com.spring.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        
        String schemename="bearerScheme";
        
        
        return new OpenAPI()   
                            .addSecurityItem(new SecurityRequirement().addList(schemename))
                            .components(new Components()
                                                
                                               .addSecuritySchemes(schemename, new SecurityScheme()
                                                                   
                                                                   .name(schemename)
                                                                   .type(SecurityScheme.Type.HTTP)
                                                                   .bearerFormat("JWT")
                                                                   .scheme("bearer")
                                                                   
                                                                   )
                                        )
                            .info(new Info()
                                       .title("Blog Application APIs")
                                       .description("this is Blog Application API developed by Varun")                                    
                                       .version("1.0")
                                       .contact(new Contact().name("VARUN SONI").email("vrn_mark@otulook.com")
                                                           .url("www.jinxTech.com"))
                                                           .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                            .externalDocs(new ExternalDocumentation()
                                                .description("Blog Application API-this is External URL")
                                                .url("https://VarunSoni.wiki.github.org/docs"));
                                       
                                   
                                           
    }
}
