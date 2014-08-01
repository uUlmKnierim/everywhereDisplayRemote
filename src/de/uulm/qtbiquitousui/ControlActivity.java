package de.uulm.qtbiquitousui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class ControlActivity extends Activity {

	RelativeLayout layout_joystick;
	JoyStickClass js;

	Sender s;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy stPolicy= new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(stPolicy);
		
		
		setContentView(R.layout.activity_control);

        SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
        String ip = settings.getString("PROCAMSIP", "0.0.0.0");
	    Server.getInstanceOf().setIp(ip);      	      

		
		
		
		s = new Sender();
		layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
		js = new JoyStickClass(getApplicationContext(), layout_joystick,
				R.drawable.joystickstick);
		js.setStickSize(250, 250);
		js.setLayoutSize(600, 600);
		js.setLayoutAlpha(255);
		js.setStickAlpha(255);
		js.setOffset(90);
		js.setMinimumDistance(50);
		js.resetStickPos();

		layout_joystick.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				js.drawStick(arg1);
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					s.setValue(0);
				}
				if (arg1.getAction() == MotionEvent.ACTION_DOWN
						|| arg1.getAction() == MotionEvent.ACTION_MOVE) {
					int direction = js.get4Direction();
					System.out.println("Direkction: " + direction);
					s.setValue(direction);
				}
				return true;
			}
		});

		ImageButton load = (ImageButton) findViewById(R.id.load);
		load.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new PositionTask(v).execute("");
			}
		});

		ImageButton save = (ImageButton) findViewById(R.id.save);
		
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(v
						.getContext());

				// 2. Chain together various setter methods to set the dialog
				// characteristics
				builder.setMessage("Enter name of new surface.").setTitle(
						"Set new Surface");

				// Set an EditText view to get user input
				final EditText input = new EditText(v.getContext());
				builder.setView(input);

				builder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK button
								System.out.println("Text Input: "
										+ input.getText());
								new SendTask().execute("save#"
										+ input.getText());
							}
						});
				builder.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

		ImageButton start = (ImageButton) findViewById(R.id.done);
		start.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				new SendTask().execute("start");
			}
		});
		
		ImageButton refresh = (ImageButton) findViewById(R.id.refresh);
		refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SendTask().execute("refresh");				
			}
		});
	}
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
        case R.id.action_calibrate:
        	new SendTask().execute("calibrate");
            return true;
        case R.id.action_setIP:

        	AlertDialog.Builder builder = new AlertDialog.Builder(ControlActivity.this);
        	
            SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
            String ip = settings.getString("PROCAMSIP", "0.0.0.0");

        	builder.setMessage("")
        	       .setTitle("Set IP of PROCAMS");

        	final EditText input = new EditText(this);
        	input.setText(ip);
        	builder.setView(input);
        	
        	
        	builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int whichButton) {
        		  Editable value = input.getText();
        		  Log.d("QT", "mega: "+ value.toString());

        		  SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
        	      SharedPreferences.Editor editor = settings.edit();
        	      editor.putString("PROCAMSIP", value.toString());
        	      editor.commit();  	      
        	      Server.getInstanceOf().setIp(value.toString());      	      
        		}
        		});

        	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  public void onClick(DialogInterface dialog, int whichButton) {
        		    // Canceled.
        		  }
        		});
        	
        	
        	
        	
        	AlertDialog dialog = builder.create();
        	dialog.show();
        	return true;       	
        default:
            return super.onOptionsItemSelected(item);
    }
	}

	@Override
	protected void onPause() {
		Server.getInstanceOf().close();
		Log.d("Main", "Server connection closed");
		super.onPause();
	}
}
