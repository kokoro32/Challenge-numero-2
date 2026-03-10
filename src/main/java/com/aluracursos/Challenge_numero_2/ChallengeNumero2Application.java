package com.aluracursos.Challenge_numero_2;

import com.aluracursos.Challenge_numero_2.principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChallengeNumero2Application {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ChallengeNumero2Application.class, args);
        Principal principal = context.getBean(Principal.class);
        principal.principal();
	}
}
