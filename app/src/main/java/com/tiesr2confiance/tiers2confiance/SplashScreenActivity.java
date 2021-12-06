package com.tiesr2confiance.tiers2confiance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;

public class SplashScreenActivity extends AppCompatActivity {
	private static final String TAG = "CreationProfilActivity";
	private static final String TAGAPP = "LOGAPP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		Log.i(TAGAPP, "SplashScreenActivity onCreate: ");


		GlobalClass globalVariables = (GlobalClass) getApplicationContext();
//		String userId       = globalVariables.getUserId();
//		String userEmail    = globalVariables.getUserEmail();

		Log.i(TAGAPP, "SplashScreenActivity onCreate: ");

		try
		{
			globalVariables.LoadUserDataFromFirestore();
		}
		catch (Exception e) {
			Log.e(TAGAPP, "----- SplashScreen : onCreate error on Loading data in SPLASH: -----");
		};


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				globalVariables.LoadArraysDataFromFirestore();
				Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
				startActivity(myIntent);
				globalVariables.DisplayAttributes();
				// close this activity
				finish();
			}
		}, 3000);
	}
}