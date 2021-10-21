package com.example.cmpp_server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.locks.LockSupport;
import com.zx.sms.connect.manager.EndpointManager;
import com.example.cmpp_server.connect.manager.CustomCmppServerEndpointEntity;

@SpringBootApplication
public class CmppServerApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(CmppServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		final EndpointManager manager = EndpointManager.INS;
		CustomCmppServerEndpointEntity server = new CustomCmppServerEndpointEntity();
		server.setId("server");
		server.setHost("0.0.0.0");
		server.setPort(port);
		server.setValid(true);
		server.setUseSSL(false);

		manager.openEndpoint(server);
		LockSupport.park();
	}
}
