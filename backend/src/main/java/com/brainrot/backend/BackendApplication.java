package com.brainrot.backend;

import com.brainrot.backend.model.Fact;
import com.brainrot.backend.repository.FactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

@Bean
CommandLineRunner initDatabase(FactRepository repository){
    return args -> {
        repository.save(new Fact("Your brain generates enough electricity to power a small lightbulb."));
        repository.save(new Fact("Information travels in your brain at up to 268 miles per hour."));
        repository.save(new Fact("The human brain is made up of about 75% water."));

        System.out.println("Database seeded with BrainBytes!");
    };
    }
}
