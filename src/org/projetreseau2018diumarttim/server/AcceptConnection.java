package org.projetreseau2018diumarttim.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptConnection implements Runnable
{
	private ServerSocket	ss			= null;
	private Socket			socket;
	private boolean			listening	= true;

	public AcceptConnection(ServerSocket ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		while (listening) {
			try {
				System.out.println("Waiting for connections...");
				socket = ss.accept();
				System.out.println("Incoming connection from " + socket.getInetAddress());
				Thread t = new Thread(new Identification(socket));
				t.run();

			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
