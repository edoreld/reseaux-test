package org.projetreseau2018diumarttim.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{
	public static void main(String[] args) throws IOException {

		ServerSocket ss = new ServerSocket(33334);
		System.out.println("Server running...");

		Thread t;

		t = new Thread(new AcceptConnection(ss));

		t.start();
	}

}
