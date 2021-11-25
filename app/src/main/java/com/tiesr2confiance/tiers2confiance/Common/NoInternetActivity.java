package com.tiesr2confiance.tiers2confiance.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.R;

public class NoInternetActivity extends AppCompatActivity {

    /** Variables globales **/
    private TextView tvNoInternet;

    // Callback pour connaitre l'état de connection
    private ConnectivityManager.NetworkCallback networkCallback;

    /** Méthode init **/
    public void init() {
        tvNoInternet = findViewById(R.id.tvNoInternet);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        init();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    finish();
                }
                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    tvNoInternet.setText("Verifier la connection internet");
                }
            };
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            connectivityManager.registerNetworkCallback(new NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), networkCallback);
        }
    }

    public void btnRetryClick(View view) {
        if(Util.connectionAvailable(this)){
            finish();
        } else {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO changer tv en progressBar
                    tvNoInternet.setText("azerty");
                }
            },1000);

        }
    }
    public void btnCloseClick(View view) {
        finishAffinity();
    }
}