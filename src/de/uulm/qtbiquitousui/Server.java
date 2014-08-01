package de.uulm.qtbiquitousui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class Server {

	private static Server server = null;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private static InetSocketAddress ip = null;
	
	
	private void connect() {
		try {
			server.client = new Socket();
			server.client.setSoTimeout(30000);
			server.client.setKeepAlive(true);
			server.client.connect(ip);
			Log.d("TAG","connect to:" + ip);
			server.out = new PrintWriter(server.client.getOutputStream(), true);
			server.in = new BufferedReader(new InputStreamReader(server.client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	protected void setIp(String address){
	
		
		String PATTERN = 
		        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		          
		      Pattern pattern = Pattern.compile(PATTERN);
		      Matcher matcher = pattern.matcher(address);
		      
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      
		     
		      if(matcher.matches()){
		    	  //valid ip
		  		ip = new InetSocketAddress(address, 8888);
		  		Log.d("TAG", "IP is valid");
		      }else{
		  		ip = new InetSocketAddress("127.0.0.1", 8888);
		  		Log.d("TAG", "IP is NOT valid");
		      }}
	
	
	private void init(){
		server.client = new Socket();
		try {
			server.client.setSoTimeout(30000);
			server.client.setKeepAlive(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Server getInstanceOf() {	
		

		
		if (server == null) {
			server = new Server();
			server.init();
			if(ip!=null){
				server.connect();}
			else 
				return server;
		}

		if (ip!=null & !server.client.isConnected()) {
			System.out.println("Client was not Connected");
			server.connect();
		}
		return server;
	}

	protected void sendData(String data) {
		if(!server.client.isConnected()){
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
	
	protected void close(){
		try {
			out.close();
			in.close();
			client.close();
			server = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
