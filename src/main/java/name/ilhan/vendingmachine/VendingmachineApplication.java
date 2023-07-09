package name.ilhan.vendingmachine;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendingmachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingmachineApplication.class, args);
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Vending Machine API")
                        .description("Vending machine API")
                        .version("v0.0.1")
                        .license(new License().name("All rights reserved").url("https://ilhan.name")))
                .externalDocs(new ExternalDocumentation()
                        .description("Vending Machine User Manual")
                        .url("https://github.com/ilhanyumer/vendingmachine"));
    }

}
