package com.umdhacks.automatedring;

import java.util.Calendar;

import com.google.android.maps.GeoPoint;
import com.umdhacks.youbetterworkbitch.R;

import android.media.AudioManager;
import android.content.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.text.format.Time;

public class VolumeSetter extends Activity implements OnClickListener {
	private static AudioManager audio;
	private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
	public String criteria;
	public static String defaultHigh;
	public static String defaultLow;
	public boolean toggle;
	public Calendar cal;
//	GeoPoint point;
	Button nextScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		toggle = false;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volume_setter);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
		radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
		radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
		Toast.makeText(getApplicationContext(), "Welcome!",
				Toast.LENGTH_SHORT).show();
		nextScreen = (Button) findViewById(R.id.button1);

		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				boolean late = Time.HOUR < 5 || Time.HOUR > 1;
				// checkedId is the RadioButton selected
				System.out.println(checkedId);

				switch (checkedId) {
				case R.id.radioButton1:
					criteria = "location";
					Toast.makeText(getApplicationContext(),
							"CRITERIA set to LOCATION", Toast.LENGTH_SHORT)
							.show();
					
					
					
					break;
				case R.id.radioButton2:
					criteria = "time";
					Toast.makeText(getApplicationContext(),
							"CRITERIA set to TIME", Toast.LENGTH_SHORT).show();
					//
					AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//					Intent intent = new Intent(this, MyAlarmReceiver.class);
//					PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
					Calendar time = Calendar.getInstance();
					time.setTimeInMillis(System.currentTimeMillis());
					time.add(Calendar.SECOND, 30);
//					alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
					//
					break;
				default:
					throw new IllegalArgumentException(
							"Choose a valid radio button condition.");
				}
			}
		});
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// checkedId is the RadioButton selected

				System.out.println(checkedId);
				switch (checkedId) {
				case R.id.radioButton3:
					defaultLow = "silence";
					Toast.makeText(getApplicationContext(),
							"LOW MODE set to SILENCE", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.radioButton4:
					defaultLow = "vibrate";
					Toast.makeText(getApplicationContext(),
							"LOW MODE set to VIBRATE", Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					throw new IllegalArgumentException(
							"Choose a valid radio button condition.");
				}
			}
		});
		radioGroup3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// checkedId is the RadioButton selected

				System.out.println(checkedId);

				switch (checkedId) {
				case R.id.radioButton5:
					defaultHigh = "vibrate";
					Toast.makeText(getApplicationContext(),
							"HIGH MODE set to VIBRATE", Toast.LENGTH_SHORT)
							.show();
					break;
				case R.id.radioButton6:
					defaultHigh = "volume_on";
					Toast.makeText(getApplicationContext(),
							"HIGH MODE set to DEFAULT RINGER",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					throw new IllegalArgumentException(
							"Choose a valid radio button condition.");
				}
			}
		});
		radioGroup4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// checkedId is the RadioButton selected
				System.out.println(checkedId);

				switch (checkedId) {
				case R.id.radioButton7:
					Toast.makeText(getApplicationContext(), "SMARTTONE ON",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.radioButton8:
					Toast.makeText(getApplicationContext(), "SMARTTONE OFF",
							Toast.LENGTH_SHORT).show();
					audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					Toast.makeText(getApplicationContext(), "RINGER NORMAL",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					throw new IllegalArgumentException(
							"Choose a valid radio button condition.");
				}
			}
		});
		
		nextScreen.setOnClickListener(this);

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.volume_setter, menu);
		return true;
	}

	// NEW STUFF BELOW
	/*
	 * credit to: http://stackoverflow.com/questions/4593552/how-do-you
	 * -get-set-media-volume-not-ringtone-volume-in-android
	 */

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { switch
	 * (keyCode) { case KeyEvent.KEYCODE_VOLUME_UP:
	 * audio.adjustStreamVolume(AudioManager.STREAM_RING,
	 * AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI); // DO WE WANT THE
	 * FLAG_SHOW_UI TO BE ON? return true; case KeyEvent.KEYCODE_VOLUME_DOWN:
	 * audio.adjustStreamVolume(AudioManager.STREAM_RING,
	 * AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI); return true;
	 * default: return false; } }
	 */

	// These are mine below, bitch

	public static int setDefaultLow() {
		if (defaultLow.equals("silence")) {
			audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		} else if (defaultLow.equals("vibrate")) {
			audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		return 0;
	}

	public static int setDefaultHigh() {
		if (defaultHigh.equals("volume_on")) {
			audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		} else if (defaultHigh.equals("vibrate")) {
			audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		return 0;
	}
	
	public class MyAlarmReceiver extends BroadcastReceiver { 
	     @Override
	     public void onReceive(Context context, Intent intent) {
	         Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
	     }
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(VolumeSetter.this, CriteriaActivity.class);
		startActivity(i);
		
	}

}
