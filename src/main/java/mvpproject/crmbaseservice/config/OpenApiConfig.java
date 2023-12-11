package mvpproject.crmbaseservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Crm base service",
                description = "Crm", version = "1.0.0",
                contact = @Contact(
                        name = "Komikov Sergey",
                        email = "komikovsergey@gmail.com"
                )
        )
)
public class OpenApiConfig {
}