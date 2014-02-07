package de.uulm.qtbiquitousui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

class ServerOld extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String value = params[0];
		if (value.equals("m")){
			value = "m#" + params[1];
		}
		System.out.println("Send: " + value);
		Socket client;
		PrintWriter out;

		try {
			client = new Socket("134.60.70.63", 8888);
			out = new PrintWriter(client.getOutputStream(), true);
			out.append(value);
			out.flush();
			out.close();
			client.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Executed";
	}

	@Override
	protected void onPostExecute(String result) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}
}