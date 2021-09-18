package com.example.echo_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EchoServerApplication {

	private final EchoServer echoServer;

	public static void main(String[] args) {
		SpringApplication.run(EchoServerApplication.class, args);
	}

}
