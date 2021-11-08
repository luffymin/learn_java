package com.example.echoserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class EchoServerTest {

	class StartServer implements Runnable {
		@Override
		public void run() {
			try {
				final EchoServer server = new EchoServer(9999);
				server.start();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@BeforeEach
	public void before() throws Exception {
		Thread t = new Thread(new StartServer());
		t.start();
		Thread.sleep(2000);
	}

    @Test
	public void test() throws Exception {
		Socket s = new Socket("127.0.0.1", 9999);

		InputStream is = s.getInputStream();
		OutputStream os = s.getOutputStream();

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		bw.write("Netty rocks!");
		bw.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Assertions.assertEquals(br.readLine(), "Netty rocks!");
	}

}
