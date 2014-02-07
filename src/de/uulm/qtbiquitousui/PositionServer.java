package de.uulm.qtbiquitousui;

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
		ArrayList<String> surfaces = Server.getInstanceOf().getPositions();
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
