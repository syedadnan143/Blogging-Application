package com.cwd.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration

public class SwaggerConfig {
	/*
	 * @SecurityScheme( name = "bearerAuth", description = "JWT auth description",
	 * scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in =
	 * SecuritySchemeIn.HEADER )
	 */
	
	
 	@Bean
	public OpenAPI springShopOpenAPI() {
 		
//		.SecurityContexts()
//		.SecuritySchemas(Arrays.asList(apiKeys()))
		return new OpenAPI()
				
				.info(new Info().title("Blogging Application Api Documentation").description("Spring boot backend").version("1.1")
						.contact(new Contact().name("Adnan").email("addy51@Gmail.com"))
					
						.license(new License().name("Apache 2.0").url("http.com.cwd.blog")))
				.externalDocs(new ExternalDocumentation().description("SpringShop Documentation")
						.url("http://localhost:8083/swagger-ui/index.html")); // Corrected URL here
	}
}
