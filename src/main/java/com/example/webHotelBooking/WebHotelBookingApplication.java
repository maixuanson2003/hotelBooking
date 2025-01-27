package com.example.webHotelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.webHotelBooking.Repository")
@EnableScheduling
@EnableAsync
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebHotelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHotelBookingApplication.class, args);
	}

}
