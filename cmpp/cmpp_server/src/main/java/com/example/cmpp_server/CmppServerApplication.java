package com.example.cmpp_server;

import com.example.cmpp_server.connect.manager.CustomCMPPServerEndpointEntity;
import com.zx.sms.connect.manager.EndpointManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.LockSupport;

@SpringBootApplication
public class CMPPServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CMPPServerApplication.class, args);
	}

	@Value("${server.port}")
	private int serverPort;

	@Override
	public void run(String... args) {
		final EndpointManager manager = EndpointManager.INS;

		CustomCMPPServerEndpointEntity server = new CustomCMPPServerEndpointEntity();
		server.setId("server");
		server.setHost("0.0.0.0");
		server.setPort(serverPort);
		server.setValid(true);
		server.setUseSSL(false);

		manager.openEndpoint(server);
		LockSupport.park();
	}
}
