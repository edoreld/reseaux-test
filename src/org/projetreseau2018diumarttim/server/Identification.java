package org.projetreseau2018diumarttim.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Identification implements Runnable
{
	Socket					socket	= null;
	private BufferedReader	in		= null;
	private PrintWriter		out		= null;
	private Scanner			sc		= null;
	private String			login	= null;
	private String			pass	= null;

	public Identification(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String[] addressBookLines;
		boolean auth = false;
		int loginAttempts = 1;

		System.out.println("Identification Phase");
		System.out.println("####################");

		while (!auth && loginAttempts < 3) {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				sc = new Scanner(System.in);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}

			try {
				long t0 = System.currentTimeMillis();
				login = in.readLine();
				// socket.setSoTimeout(5000);
				pass = in.readLine();
				long tDelta = System.currentTimeMillis() - t0;
				if (tDelta < 1000) {
					out.println("robot");
					out.flush();
					break;
				}
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}

			System.out.println("Login is: " + login);
			System.out.println("Pass is: " + pass);

			try (Scanner ab = new Scanner(new File("addressbook.txt"))) {

				while (ab.hasNextLine()) {
					addressBookLines = ab.nextLine().split(";");
					if (login.equals(addressBookLines[0]) && pass.equals(addressBookLines[1])) {
						System.out.println("Client " + login + " is now connected");
						out.println("success");
						out.flush();
						auth = true;
					}
				}
				if (!auth && loginAttempts < 3) {
					out.println("wrong-login");
					out.flush();
				}
				ab.close();
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			}
			loginAttempts++;
		}

		if (loginAttempts == 3 && auth == false) {
			out.println("too-many-attemps");
			out.flush();
		}

		if (auth == true) {
			Thread t = new Thread(new OpenConnection(socket, login));
			t.run();
		} else {
			System.exit(-1);
		}

	}
}
