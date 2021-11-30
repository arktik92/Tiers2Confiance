package com.tiesr2confiance.tiers2confiance.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Util {

    /** Méthode pour la verif internet **/
    public static boolean connectionAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null ){
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        } else {
            return false;
        }
    }

    public static void showSnackBar(View baseView, String message) {
        Snackbar.make(baseView, message, Snackbar.LENGTH_SHORT).show();
//        Log.i(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
    }


    public static void waitfor(long delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
