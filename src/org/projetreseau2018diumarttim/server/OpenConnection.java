package org.projetreseau2018diumarttim.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class OpenConnection implements Runnable
{

	Socket					socket	= null;
	String					login	= null;
	private BufferedReader	in		= null;
	private PrintWriter		out		= null;
	private Scanner			sc		= null;

	OpenConnection(Socket socket, String login) {
		this.socket = socket;
		this.login = login;
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		Thread tRec = new Thread(new Receive(in, login));
		tRec.run();
		Thread tTrans = new Thread(new Transmit(out));
		tTrans.run();
	}

}
