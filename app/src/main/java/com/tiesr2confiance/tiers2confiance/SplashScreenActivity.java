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

		Log.i(TAG, "SplashScreenActivity onCreate: ");


		GlobalClass globalVariables = (GlobalClass) getApplicationContext();
		String userId       = globalVariables.getUserId();
		String userEmail    = globalVariables.getUserEmail();
		try {
			globalVariables.LoadUserDataFromFirestore();
//			globalVariables.LoadGendersDataFromFirestore();
//			globalVariables.LoadHobbiesDataFromFirestore();
		}
		catch (Exception e) {
			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----" );
			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----userEmail "  + userEmail);        };

		globalVariables.DisplayAttributes();

		Log.d(TAG, "MainActivity onCreate: USERROLE" + globalVariables.getUserRole() + globalVariables.getUserEmail());



		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
				startActivity(i);
				globalVariables.DisplayAttributes();
				// close this activity
				finish();
			}
		}, 3000);
	}
}