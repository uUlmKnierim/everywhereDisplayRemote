package de.uulm.qtbiquitousui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.util.Log;

public class Server {

	private static Server server = null;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;

	
	private void connect() {
		try {
			server.client = new Socket();
			server.client.setSoTimeout(30000);
			server.client.setKeepAlive(true);
			server.client.connect(new InetSocketAddress("134.60.70.63", 8888));
			server.out = new PrintWriter(server.client.getOutputStream(), true);
			server.in = new BufferedReader(new InputStreamReader(server.client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static Server getInstanceOf() {
		if (server == null) {
			server = new Server();
			server.connect();
		}

		if (!server.client.isConnected()) {
			System.out.println("Client not Connected");
			server.connect();
		}
		return server;
	}

	protected void sendData(String data) {
		if(!this.client.isConnected()){
			Log.e("SERVER", "NOT Connected");
			return;
		}
		out.append(data);
		out.flush();
	}

	protected ArrayList<String> getPositions() {
		if(!this.client.isConnected()){
			Log.e("SERVER", "NOT Connected");
			return null;
		}
		sendData("p");
		ArrayList<String> surfaces = new ArrayList<String>();
		System.out.println("send Prosition request");
		try {
			String result;
			while ((result = in.readLine()) != null) {
				if(result.equals("")) break;
				System.out.println("<" + result + ">");
				surfaces.add(result);
			}
			System.out.println("Done READ Positions");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return surfaces;
	}
}
