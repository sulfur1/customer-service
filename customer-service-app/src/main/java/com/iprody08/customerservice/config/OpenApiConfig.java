package com.iprody08.customerservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.iprody08.customerservice.util.Settings.API_DESCRIPTION;
import static com.iprody08.customerservice.util.Settings.API_GROUP;
import static com.iprody08.customerservice.util.Settings.API_PATHS;
import static com.iprody08.customerservice.util.Settings.API_TITLE;
import static com.iprody08.customerservice.util.Settings.API_VERSION;
import static com.iprody08.customerservice.util.Settings.CONTACT_EMAIL;
import static com.iprody08.customerservice.util.Settings.CONTACT_NAME;
import static com.iprody08.customerservice.util.Settings.CONTACT_URL;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = API_TITLE,
                version = API_VERSION,
                description = API_DESCRIPTION,
                contact = @Contact(
                        url = CONTACT_URL,
                        name = CONTACT_NAME,
                        email = CONTACT_EMAIL
                )
        )
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group(API_GROUP)
                .pathsToMatch(API_PATHS)
                .build();
    }
}
