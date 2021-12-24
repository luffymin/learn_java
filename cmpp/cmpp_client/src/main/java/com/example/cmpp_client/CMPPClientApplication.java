package com.example.cmpp_client;

import com.example.cmpp_client.connect.manager.CustomCMPPClientEndpointEntity;
import com.zx.sms.connect.manager.EndpointEntity.SupportLongMessage;
import com.zx.sms.connect.manager.EndpointManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.charset.Charset;
import java.util.concurrent.locks.LockSupport;

@SpringBootApplication
public class CMPPClientApplication implements CommandLineRunner {

	@Value("${server.host}")
	private String host;

	@Value("${server.port}")
	private int port;

	@Value("${client.id}")
	private String id;

	@Value("${client.groupName}")
	private String groupName;

	@Value("${client.userName}")
	private String userName;

	@Value("${client.password}")
	private String password;

	@Value("${client.spCode}")
	private String spCode;

	@Value("${client.serviceId}")
	private String serviceId;

	@Value("${client.msgSrc}")
	private String msgSrc;

	public static void main(String[] args) {
		SpringApplication.run(CMPPClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		CustomCMPPClientEndpointEntity client = new CustomCMPPClientEndpointEntity();
		client.setId(id);
		client.setServiceId(serviceId);
		client.setHost(host);
		client.setPort(port);
		client.setVersion((short) 0x20);
		client.setChartset(Charset.forName("utf-8"));
		client.setSpCode(spCode);
		client.setGroupName(groupName);
		client.setUserName(userName);
		client.setPassword(password);
		client.setMsgSrc(msgSrc);
		client.setMaxChannels((short) 1);
		client.setUseSSL(false);
		client.setWriteLimit(120);
		client.setLiftTime(45 * 1000);
		client.setSupportLongmsg(SupportLongMessage.SEND);
		client.setReSendFailMsg(false);

		final EndpointManager manager = EndpointManager.INS;
		manager.addEndpointEntity(client);
		manager.startConnectionCheckTask();
		LockSupport.park();

	}
}
