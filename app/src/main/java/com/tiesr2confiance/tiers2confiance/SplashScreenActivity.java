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
				// Ce code s'execute après 1000ms
				// les data de ArrayLists se charge quand les Data User, notament le Role, sont déjà chargés dans la classe GlobalClass
				globalVariables.LoadArraysDataFromFirestore();
			}
		}, 1000);


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Ce code s'execute après 2000ms, le temps que les Arrays soient chargés dans la clase GlobalClass

				globalVariables.DisplayAttributes();
				globalVariables.DisplayArraysCount();

				// Start MainActivity
				Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
				startActivity(myIntent);

				// close this activity
				finish();
			}
		}, 2000);
	}


}