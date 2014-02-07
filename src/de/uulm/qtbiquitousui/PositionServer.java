package de.uulm.qtbiquitousui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public class PositionServer extends AsyncTask<String, Void, ArrayList<String>> {

	private View v;

	public PositionServer(View v) {
		this.v = v;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
		System.out.println("Talk to the server!!!!");
		String value = params[0];
		Socket client;
		PrintWriter out;
		BufferedReader in;
		ArrayList<String> surfaces = new ArrayList<String>();

		try {
			client = new Socket("134.60.70.63", 8888);
			out = new PrintWriter(client.getOutputStream(), true);
			out.append("p");
			out.flush();

			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));

			String result;
			while ((result = in.readLine()) != null) {
				System.out.println("<" + result + ">");
				surfaces.add(result);
			}

			in.close();
			out.close();
			client.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return surfaces;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		System.out.println("JOB DONE");

		final CharSequence[] items = result.toArray(new CharSequence[result
				.size()]);

		AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("Choose Surface");
		// builder.setIcon(R.drawable.icon);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(v.getContext().getApplicationContext(),
						items[item], Toast.LENGTH_SHORT).show();
				String t = items[item].toString();
				new ServerOld().execute("m",t);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Void... values) {
	}

}
