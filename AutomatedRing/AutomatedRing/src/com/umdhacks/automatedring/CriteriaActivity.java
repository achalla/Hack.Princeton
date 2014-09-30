package com.umdhacks.automatedring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.android.maps.GeoPoint;
import com.umdhacks.youbetterworkbitch.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CriteriaActivity extends Activity implements OnClickListener,
		LocationListener {

	Set<GeoPoint> points;
	LocationManager lm;
	LocationListener l;
	GPSTracker gps;

	TextView top;
	Button addLocation;

	class pointPair {
		double lat, lon;

		pointPair(Double lat, Double lon) {
			this.lat = lat;
			this.lon = lon;
		}
	}

	ArrayList<pointPair> geopts = new ArrayList<pointPair>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.criteria_disp);

		top = (TextView) findViewById(R.id.textView1);
		top.setText("Location");

		points = new HashSet<GeoPoint>();
		gps = new GPSTracker(CriteriaActivity.this, this);

		addLocation = (Button) findViewById(R.id.locButton);
		addLocation.setOnClickListener(this);

	}

	@SuppressLint("UseValueOf")
	public static float distFrom(double d, double e, double lat2, double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - d);
		double dLng = Math.toRadians(lng2 - e);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(d)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return new Float(dist * meterConversion).floatValue();
	}

	@Override
	public void onClick(View v) {
		int e = 1000000;
		
		

		TextView textLatitude = (TextView) findViewById(R.id.text_latitude);
		TextView textLongitude = (TextView) findViewById(R.id.text_longitude);

		gps.getLocation();
		points.add(new GeoPoint((int) gps.getLatitude() * e, (int) gps
				.getLongitude() * e));
		
		if (!geopts.contains(new pointPair(gps.getLatitude(),gps.getLongitude()))) {
			geopts.add(new pointPair(gps.getLatitude(), gps.getLongitude()));
		}

		//
		TextView currLat = (TextView) findViewById(R.id.textView4);
		TextView currLong = (TextView) findViewById(R.id.textView5);
		currLat.setText(Double.toString(gps.getLatitude()));
		currLong.setText(Double.toString(gps.getLongitude()));
		//

		textLatitude.setText(Double.toString(gps.getLatitude()));
		textLongitude.setText(Double.toString(gps.getLongitude()));

		

	}

	@Override
	public void onLocationChanged(Location location) {
		TextView textLatitude = (TextView) findViewById(R.id.text_latitude);
		TextView textLongitude = (TextView) findViewById(R.id.text_longitude);

		LocationManager locationManager = gps.getLocationManager();

		if (locationManager != null) {
			if (gps.isNetworkEnabled) {
				gps.location = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			} else if (gps.isGPSEnabled) {
				gps.location = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if (gps.location != null) {
				gps.latitude = gps.location.getLatitude();
				gps.longitude = gps.location.getLongitude();
			}
		}

		double lat = (double) gps.getLatitude();
		double lon = (double) gps.getLongitude();

		

		System.out.println("lat: " + lat);
		System.out.println("lon: " + lon);
		textLatitude.setText(Double.toString(lat)); // always keep
		textLongitude.setText(Double.toString(lon)); // always keep

		boolean turnOff = false;

		Iterator<GeoPoint> iter = points.iterator();

		/*
		 * while (iter.hasNext()) {
		 * 
		 * GeoPoint point = iter.next(); if (distFrom(point.getLatitudeE6() /
		 * 1E6, point.getLongitudeE6() / 1E6, lat, lon) < 10) {
		 * 
		 * Toast.makeText(getApplicationContext(), "Turn off ringer ",
		 * Toast.LENGTH_SHORT).show(); turnOff = true; break;
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (turnOff == false) { Toast.makeText(getApplicationContext(),
		 * "Turn on ringer", Toast.LENGTH_SHORT).show(); }
		 */

		
		int count2 = 0;
		for (pointPair p : geopts) {
			count2++;
			System.out.println("Begin"+count2);
			System.out.println(Double.toString(p.lat));
			System.out.println(Double.toString(p.lon));
			System.out.println("end");
		}
		
		Integer count = 0;
		for (pointPair p : geopts) {
			
			count++;
			/*TextView onOrOff = (TextView) findViewById(R.id.textView12);
			TextView iteration = (TextView) findViewById(R.id.textView13);
			iteration.setText(Integer.toString(count));
			TextView textlat1 = (TextView) findViewById(R.id.textView6);
			textlat1.setText(Double.toString(p.lat));
			TextView textlat2 = (TextView) findViewById(R.id.textView7);
			textlat2.setText(Double.toString(p.lon));
			TextView textlon1 = (TextView) findViewById(R.id.textView8);
			textlon1.setText(Double.toString(lat));
			TextView textlon2 = (TextView) findViewById(R.id.textView9);
			textlon2.setText(Double.toString(lon));*/

			TextView latdiff = (TextView) findViewById(R.id.textView10);
			latdiff.setText("Lat Diff: "
					+ Double.toString(Math.abs(p.lat - lat)));
			TextView londiff = (TextView) findViewById(R.id.textView11);
			londiff.setText("Long Diff: "
					+ Double.toString(Math.abs(p.lon - lon)));
			if (Math.abs(p.lat - lat) < .00005
					&& Math.abs(p.lon - lon) < .00005) {
				//Toast.makeText(getApplicationContext(), "Turn off ringer ",
					//	Toast.LENGTH_SHORT).show();
				turnOff = true;
				//onOrOff.setText(Boolean.toString(turnOff));
				VolumeSetter.setDefaultLow();
				break;
			} else {
				turnOff = false;
				VolumeSetter.setDefaultHigh();
				//Toast.makeText(getApplicationContext(), "Turn on ringer ",
				//		Toast.LENGTH_SHORT).show();
			}
			//onOrOff.setText(Boolean.toString(turnOff));
			
		}

		/*
		 * while (iter.hasNext()) { TextView textlat1 = (TextView)
		 * findViewById(R.id.textView6);
		 * textlat1.setText(Double.toString(point.getLatitudeE6())); TextView
		 * textlat2 = (TextView) findViewById(R.id.textView7);
		 * textlat2.setText(Double.toString(point.getLongitudeE6())); TextView
		 * textlon1 = (TextView) findViewById(R.id.textView8);
		 * textlon1.setText(Double.toString(lat*1000000)); GeoPoint point =
		 * iter.next();
		 * 
		 * TextView textlat1 = (TextView) findViewById(R.id.textView6);
		 * textlat1.setText(Double.toString(point.getLatitudeE6())); TextView
		 * textlat2 = (TextView) findViewById(R.id.textView7);
		 * textlat2.setText(Double.toString(point.getLongitudeE6())); TextView
		 * textlon1 = (TextView) findViewById(R.id.textView8);
		 * textlon1.setText(Double.toString(lat*1000000)); //TextView textlon2 =
		 * (TextView) findViewById(R.id.textView9);
		 * //textlon2.setText(Double.toString(lon*1000000));
		 * 
		 * if (Math.abs(point.getLatitudeE6() - lat*(1000000)) < 1000 ||
		 * Math.abs(point.getLongitudeE6() - lon*(1000000)) < 1000) {
		 * 
		 * Toast.makeText(getApplicationContext(), "Turn off ringer ",
		 * Toast.LENGTH_SHORT).show();
		 * Toast.makeText(getApplicationContext(),"Lat: "
		 * +Double.toString(point.getLatitudeE6() - lat*(1000000)),
		 * Toast.LENGTH_SHORT).show();
		 * Toast.makeText(getApplicationContext(),"Lon: "
		 * +Double.toString(point.getLongitudeE6() - lon*(1000000)),
		 * Toast.LENGTH_SHORT).show(); turnOff = true; break;
		 * 
		 * }
		 * 
		 * }
		 */

	}

	@Override
	public void onProviderDisabled(String provider) {
		// getting GPS status
		gps.isGPSEnabled = gps.locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// getting network status
		gps.isNetworkEnabled = gps.locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
