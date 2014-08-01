package de.uulm.qtbiquitousui;

import android.os.Handler;

public class Sender {

	protected Handler handler;
	int value = 0;

	public Sender() {
		handler = new Handler();
		handler.postDelayed(runnable, 0);
	}


	public void setValue(int _value) {
		value = _value;
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			String result;
			switch (value) {
			case 1:
			 new SendTask().execute("up");
				break;
			case 3:
				new SendTask().execute("right");
				break;
			case 5:
				new SendTask().execute("down");
				break;
			case 7:
				new SendTask().execute("left");
				break;
			}
			handler.postDelayed(this, 250);
		}
	};

}