package com.example.server_manager;

import com.example.server_manager.enums.Status;
import com.example.server_manager.model.Server;
import com.example.server_manager.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}