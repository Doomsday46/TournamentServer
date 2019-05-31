package com.doomsday.tournamentserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class TournamentserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentserverApplication.class, args);
	}


	@Bean
	public ObjectMapper jsonObjectMapper() {
		return Jackson2ObjectMapperBuilder.json()
				.modules(new JavaTimeModule())
				.build();
	}
}
