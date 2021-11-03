package com.example.http_xml_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpXmlServerApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(HttpXmlServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new HttpXmlServer().run(port);
	}
}
