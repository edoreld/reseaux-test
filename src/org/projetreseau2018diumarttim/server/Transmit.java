package org.projetreseau2018diumarttim.server;

import java.io.PrintWriter;

class Transmit implements Runnable
{

	private PrintWriter out = null;

	public Transmit(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void run() {
		//
	}

}
