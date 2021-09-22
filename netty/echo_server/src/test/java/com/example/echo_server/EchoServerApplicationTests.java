package com.example.echo_server;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.net.UnknownHostException;
import java.io.IOException;

@SpringBootTest
class EchoServerApplicationTests {

	@Test
	void contextLoads() {
		echoClient();
	}

	void echoClient() {
		try {
			Socket s = new Socket("127.0.0.1", 9999);

			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("Netty rocks!");
			bw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			Assert.assertEquals(br.readLine(), "Netty rocks!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
      	} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
