package com.example.udp.proto.client;

import com.example.udp.proto.common.msg.Message;
import com.example.udp.proto.common.protobuf.UserNameProto;
import com.example.udp.proto.common.util.SequenceNumberUtil;


import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;


@Slf4j
@SpringBootApplication
public class UdpProtoClientApplication implements CommandLineRunner {

	@Value("${server.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(UdpProtoClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DatagramSocket socket = new DatagramSocket();
		InetAddress address = InetAddress.getByName("localhost");

		Message req = new Message(Message.USERNAME_REQUEST, SequenceNumberUtil.getSequenceNo());
		UserNameProto.UserName.Builder builder = UserNameProto.UserName.newBuilder();
		builder.setUsername("Bruce").build();
		UserNameProto.UserName userName = builder.build();
		req.setBodyBuffer(userName.toByteArray());
		log.info("req command id: {}", req.getCommandId());

		byte[] bytes = req.getBuffer();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
		socket.send(packet);

		bytes = new byte[1024];
		packet = new DatagramPacket(bytes, bytes.length);
		socket.receive(packet);
		Message resp = new Message(packet.getData());
		if(resp.getCommandId() == Message.USERNAME_RESPONSE) {
			byte[] bodyBuffer = req.getBodyBuffer();
			try {
				UserNameProto.UserNameResp userNameResp = UserNameProto.UserNameResp.parseFrom(bodyBuffer);
				log.info("code: {}", userNameResp.getCode());
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
			}
		}
		socket.close();
	}

}
