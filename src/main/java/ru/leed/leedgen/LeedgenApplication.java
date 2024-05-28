package ru.leed.leedgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaRepositories
public class LeedgenApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeedgenApplication.class, args);
  }

}
