package com.example.server_manager;

import com.example.server_manager.enums.Status;
import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ServerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(
					Server.builder()
							.id(null)
							.ipAddress("192.166.1.111")
							.name("Ubuntu linux")
							.memory("16 GB")
							.type("Personal computer")
							.imageUrl("http://localhost:8080/server/image/server1.png")
							.status(Status.SERVER_UP)
							.build());

			serverRepository.save(
					Server.builder()
							.id(null)
							.ipAddress("192.166.1.55")
							.name("Fedora linux")
							.memory("16 GB")
							.type("Dell tower")
							.imageUrl("http://localhost:8080/server/image/server2.png")
							.status(Status.SERVER_DOWN)
							.build());

			serverRepository.save(
					Server.builder()
							.id(null)
							.ipAddress("192.166.1.21")
							.name("MS 2008")
							.memory("32 GB")
							.type("Web server")
							.imageUrl("http://localhost:8080/server/image/server3.png")
							.status(Status.SERVER_UP)
							.build());

			serverRepository.save(
					Server.builder()
							.id(null)
							.ipAddress("192.166.1.66")
							.name("Red Hat Enterprise Linux")
							.memory("64 GB")
							.type("Mail server")
							.imageUrl("http://localhost:8080/server/image/server4.png")
							.status(Status.SERVER_DOWN)
							.build());
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(List.of(
				"Origin", "Content-Type", "Accept", "Authorization",
				"X-Requested-With", "Access-Control-Request-Method",
				"Access-Control-Request-Headers", "Access-Control-Allow-Origin"));
		corsConfiguration.setExposedHeaders(List.of(
				"Origin", "Content-Type", "Accept", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
		));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}