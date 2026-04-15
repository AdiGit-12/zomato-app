package com.zomato;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.zomato.entity")  // Required for Spring Boot 3.x
@EnableJpaRepositories(basePackages = "com.zomato.repository")
public class ZomatoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoAppApplication.class, args);
		System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   🍕 ZOMATO APPLICATION STARTED! 🍕   ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  API URL: http://localhost:8080       ║");
        System.out.println("║  pgAdmin: http://localhost:5050       ║");
        System.out.println("║  Database: localhost:5432             ║");
        System.out.println("╚═══════════════════════════════════════╝");
	}

}
