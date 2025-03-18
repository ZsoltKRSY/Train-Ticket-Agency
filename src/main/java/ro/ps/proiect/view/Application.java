package ro.ps.proiect.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "ro.ps.proiect")
@EnableJpaRepositories(basePackages = "ro.ps.proiect.model.repository")
@EntityScan(basePackages = "ro.ps.proiect.model.data_structures")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
