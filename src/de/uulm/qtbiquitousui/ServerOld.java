package de.uulm.qtbiquitousui;

import android.os.AsyncTask;

class ServerOld extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		String value = params[0];
		if (value.equals("m")){
			value = "m#" + params[1];
		}
		System.out.println("Send: " + value);
		Server.getInstanceOf().sendData(value);
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