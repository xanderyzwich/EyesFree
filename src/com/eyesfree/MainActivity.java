package com.eyesfree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.eyesfree.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	
	// Variable Declaration
	// The following are used for the shake detection
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	private long lastUpdate;
	private boolean color = false;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// view = findViewById(R.id.textView);
	    // view.setBackgroundColor(Color.GREEN);
	    
	    
		// ShakeDetector initialization
				mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
				mAccelerometer = mSensorManager
						.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				mShakeDetector = new ShakeDetector();
				mShakeDetector.setOnShakeListener(new OnShakeListener() {

					@Override
					public void onShake(int count) {
						/*
						 * The following method, "handleShakeEvent(count):" is a stub //
						 * method you would use to setup whatever you want done once the
						 !* device has been shook.
						 */
						handleShakeEvent(count);
					}
					
					private void handleShakeEvent(int count) {
						setContentView(R.layout.activity_main);
						long actualTime = System.currentTimeMillis();
						if (actualTime - lastUpdate < 200) {
					        return;
					      }
					      lastUpdate = actualTime;
					      
					      TextView tv = (TextView) findViewById(R.id.shakeCount);
				    	  if (count!=0){
					    	  tv.setText("Current Shake Count"+'\n'+count);
					      }
					      else{
					    	  tv.setText("Shake to get starte.d");
					      }
					      
					      // Placeholder Notification of Activation
					      //Toast.makeText(MainActivity.this, "Shake Detected" +'\n' +count, Toast.LENGTH_SHORT)
					      //.show();
					      
					      if (count>=3){
					    	  
						      mShakeDetector.zero();
						      // shake to be in Ouray, CO
					    	  String uri = "geo:38.0233,-107.6722";
						      startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
						      
						      
					      }
					}
				});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener onResume
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
