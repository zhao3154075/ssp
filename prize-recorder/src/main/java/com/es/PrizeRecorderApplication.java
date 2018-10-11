package com.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:rabbitmq.properties")
public class PrizeRecorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrizeRecorderApplication.class, args);
	}
}
