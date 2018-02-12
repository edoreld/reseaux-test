package org.projetreseau2018diumarttim.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class Transmit implements Runnable
{
	private PrintWriter out;

	public Transmit(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		String output = null;

		while (true) {
			System.out.print("> ");
			output = sc.nextLine();
			out.println(output);
			out.flush();
		}

	}

}
