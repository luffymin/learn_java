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
	}
}
