package com.example.echo_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EchoServerApplication implements CommandLineRunner {

	@Value("${server.port}")
 	private int port;

	public static void main(String[] args) {
		SpringApplication.run(EchoServerApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        final EchoServer server = new EchoServer(port);
		server.start();
    }
}
