package com.eazybytes.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API documentation",
				description = "My Bank Accounts microservice REST API documentation",
				version = "v1",
				contact = @Contact(
						name = "Sandeep Pandey",
						email = "sandip.cimb@gmail.com", // team group mail  id
						url = "https://mywebsite.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://mywebsite.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "My Bank Accounts microservice REST API external documentation",
				url = "https://mywebsite.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
