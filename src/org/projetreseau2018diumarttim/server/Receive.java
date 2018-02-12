package org.projetreseau2018diumarttim.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class Receive implements Runnable
{
	private String			login	= null;
	private BufferedReader	in		= null;
	CharBuffer				buffer	= CharBuffer.allocate(512);

	public Receive(BufferedReader in, String login) {
		this.in = in;
		this.login = login;
	}

	@Override
	public void run() {
		int input;

		String inputLine;

		try {
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
