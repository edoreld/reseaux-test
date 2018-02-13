package org.projetreseau2018diumarttim.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Login implements Runnable
{
	private Socket			socket	= null;
	private BufferedReader	in		= null;
	private PrintWriter		out		= null;
	private Scanner			sc		= null;
	private String			user	= null;
	private String			pass	= null;

	public Login(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		boolean auth = false;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			sc = new Scanner(System.in);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		while (!auth) {
			try {
				socket.setSoTimeout(5000);
				System.out.print("Username: ");
				user = sc.nextLine();
				printAndFlush(user);
				System.out.print("Password: ");
				pass = sc.nextLine();
				printAndFlush(pass);
			} catch (SocketException e) {
				throw new IllegalStateException(e);
			}

			try {
				String response = in.readLine();
				// DEBUG - remove in prod
				System.out.println("response: " + response);
				if (response.equals("success")) {
					System.out.println("You are now logged in");
					auth = true;
				} else if (response.equals("too-many-attemps")) {
					System.out.println("Too many failed connection attempts");
					break;
				} else if (response.equals("wrong-login")) {
					System.out.println("Login failed. Try again");
					break;
				} else if (response.equals("robot")) {
					System.out.println("Robots not allowed");
					break;
				}
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		if (auth) {
			Thread t = new Thread(new OpenConnection(out));
			t.run();
		}
	}

	private void printAndFlush(String text) {
		out.println(text);
		out.flush();
	}

}
