package com.tiesr2confiance.tiers2confiance.Common;

import android.content.Context;
import android.net.ConnectivityManager;

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
}
