package com.tuuple.checkinternet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class isOnline {
    public static boolean isOnline(Context context){
        if (context == null) return false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm !=null){
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }else {
            return false;
        }
    }
}
