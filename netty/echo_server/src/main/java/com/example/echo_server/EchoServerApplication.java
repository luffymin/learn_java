package com.example.echo_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EchoServerApplication {

	@Value("${server.port}")
 	private int port;

	public static void main(String[] args) {
		SpringApplication.run(EchoServerApplication.class, args);
	}

	@Override
    public void run(String... args) {
        logger.info(String.format("%s", String.valueOf(port)));
    }
}
