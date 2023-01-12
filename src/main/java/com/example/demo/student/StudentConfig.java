package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * This is a Configuration class, I assume @Configuration again is a Component from Spring
 *
 *
 *
 */
@Configuration
public class StudentConfig {

    @Bean //Needed such that CommandLineRunner runs
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 5)
            );

            /*
            Invoke repository, uses Hibernate (extension of ORM)
             */
            repository.saveAll(
                    List.of(mariam, alex)
            );
        };
    }
}
