package org.projetreseau2018diumarttim.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{
	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("127.0.0.1", 33334)) {
			// System.out.println("Client connected to server.");
			Thread t = new Thread(new Login(socket));
			t.run();
		}
	}
}
