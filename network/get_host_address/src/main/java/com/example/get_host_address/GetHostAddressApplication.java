package com.example.get_host_address;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Optional;

@SpringBootApplication
public class GetHostAddressApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GetHostAddressApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		getHostAddress();
	}

	public void getHostAddress() throws Exception {
		Enumeration<NetworkInterface> interfaces  = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface anInterface = (NetworkInterface)interfaces.nextElement();
			if (anInterface.isUp() && !anInterface.isLoopback() && !anInterface.isPointToPoint() && !anInterface.isVirtual()) {
				Enumeration<InetAddress> addresses = anInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress address = addresses.nextElement();
					if (address != null && address instanceof Inet4Address) {
						System.out.println(address.getHostAddress());
					}
				}
			}
		}
	}

}
