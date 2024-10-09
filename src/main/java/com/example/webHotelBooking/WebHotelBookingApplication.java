package com.example.webHotelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.webHotelBooking.Repository")
@EnableScheduling
@EnableAsync
public class WebHotelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHotelBookingApplication.class, args);
	}

}
