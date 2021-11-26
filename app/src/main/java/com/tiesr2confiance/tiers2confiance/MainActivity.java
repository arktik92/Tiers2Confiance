package com.tiesr2confiance.tiers2confiance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.LierParrainFilleulActivity;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsActivity;

public class MainActivity extends AppCompatActivity {


    /**  Var globales **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchLierParrainFilleulActivity(View v){
        Intent intent = new Intent(MainActivity.this, LierParrainFilleulActivity.class);
//        intent.putExtra("IdUser", snapshot.getId());
        startActivity(intent);


    }

    public void launchPendingRequestsActivity(View v){
        Intent intent = new Intent(MainActivity.this, PendingRequestsActivity.class);
//        intent.putExtra("IdUser", snapshot.getId());
        startActivity(intent);


    }
}