package de.uulm.qtbiquitousui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ControlActivity extends Activity {

	RelativeLayout layout_joystick;
	JoyStickClass js;

	Sender s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);

		s = new Sender();
		layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
		js = new JoyStickClass(getApplicationContext(), layout_joystick,
				R.drawable.image_button);

		js.setStickSize(120, 120);
		js.setLayoutSize(600, 600);
		js.setLayoutAlpha(150);
		js.setStickAlpha(100);
		js.setOffset(90);
		js.setMinimumDistance(50);

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
		
		
		
		
		
		Button load = (Button) findViewById(R.id.load);
		load.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 new PositionServer(v).execute("");


	
			}
		});
		
		
		Button save = (Button) findViewById(R.id.save);
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
								new ServerOld().execute("save#"+input.getText());
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

}
