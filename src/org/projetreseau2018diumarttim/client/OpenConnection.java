package org.projetreseau2018diumarttim.client;

import java.io.PrintWriter;

public class OpenConnection implements Runnable
{
	private PrintWriter out = null;

	public OpenConnection(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void run() {
		Thread tTrans = new Thread(new Transmit(out));
		tTrans.run();
	}

}
