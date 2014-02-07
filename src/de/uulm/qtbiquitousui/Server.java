package de.uulm.qtbiquitousui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

	private static Server server = null;
	private Socket client;
	private PrintWriter out;

	protected Server() {
		try {
			client = new Socket();
			client.setSoTimeout(30000);
			client.setKeepAlive(true);
			client.connect(new InetSocketAddress("134.60.70.63", 8888));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Server getInstanceOf() {
		if (server == null) {
			server = new Server();
		}
		return server;
	}

	protected void sendData(String data) {
		out.append(data);
		out.flush();
	}
}
