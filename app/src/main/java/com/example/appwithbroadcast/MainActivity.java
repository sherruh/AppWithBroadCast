package com.example.appwithbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.UnicodeSetSpanner;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_RECIEVER="my_action";

    NetworkReciever networkReciever;
    MyReciever myReciever;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcast();
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void registerBroadcast() {
        networkReciever=new NetworkReciever();
        registerReceiver(networkReciever,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        myReciever=new MyReciever();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReciever,
                new IntentFilter(ACTION_RECIEVER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReciever);
    }

    public void onClick(View view) {
        startService(new Intent(this,MyService.class));
        progressBar.setVisibility(View.VISIBLE);
    }

    private class MyReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_RECIEVER)){
                Toast.makeText(MainActivity.this,"Done", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

}
