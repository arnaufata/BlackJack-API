package cat.itacademy.s05.t01.blackjackapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Blackjack API", version = "1.0", description = "API per gestionar partides de Blackjack"))
public class BlackjackApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApiApplication.class, args);
	}
}