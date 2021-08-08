package com.dynonuggets.refonteimplicaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RefonteImplicactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefonteImplicactionApplication.class, args);
	}

}
