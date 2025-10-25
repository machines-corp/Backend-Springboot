package com.mchscorp.integrajob.datapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatapiApplication.class, args);
	}

}
