package com.example.echo_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class EchoServerApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(EchoServerApplication.class, args);
	}

    public void run(String... args) throws Exception {
		
    	new EchoServer(port).start();
    }
}
