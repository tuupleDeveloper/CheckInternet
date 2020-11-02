package com.tuuple.checkinternet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String BroadcastStringForAction = "checkInternet";
    private static final String TAG = "===============>";
    IntentFilter mInterFilter;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtStatus = findViewById(R.id.txtStatus);

        mInterFilter = new IntentFilter();
        mInterFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        if (new MyService().isOnline(getApplicationContext()))
            online();
        else
            offline();
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(BroadcastStringForAction)){
                if (intent.getStringExtra("online_status").equalsIgnoreCase("true")){
                    online();
                }else {
                    offline();
                }
            }
        }
    };

    private void online(){
        txtStatus.setText(("ONLINE"));
        txtStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
    }

    private void offline(){
        txtStatus.setText(("OFFLINE"));
        txtStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver, mInterFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, mInterFilter);
    }
}