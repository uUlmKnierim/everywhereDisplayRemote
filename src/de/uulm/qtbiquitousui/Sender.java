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
			switch (value) {
			case 1:
				new ServerOld().execute("up");
				break;
			case 3:
				new ServerOld().execute("right");
				break;
			case 5:
				new ServerOld().execute("down");
				break;
			case 7:
				new ServerOld().execute("left");
				break;
			}
			handler.postDelayed(this, 500);
		}
	};

}