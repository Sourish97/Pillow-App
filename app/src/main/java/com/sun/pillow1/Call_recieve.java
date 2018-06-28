package com.sun.pillow1;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class Call_recieve extends AppCompatActivity {
    TextView t;
    String caller;

    LocalBroadcastManager nLocalBroadcastManager;
    BroadcastReceiver nBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.action.end")){

                Log.i("incoming","ended");
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_call_recieve);

        nLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter  = new IntentFilter();
        mIntentFilter.addAction("com.action.end");
        nLocalBroadcastManager.registerReceiver(nBroadcastReceiver, mIntentFilter);
        caller=getIntent().getStringExtra("caller");
        t= (TextView) findViewById(R.id.caller);
        t.setText("Incoming call from "+caller);
    }


    public void pickup(View v)
    {
        Log.d("onclick", "pickup");
        try {

            Streamer.getInstance().getOutputStreamer().write(("call:received".getBytes()));
            Intent in=new Intent(this,Call_picked.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            in.putExtra("text","call from "+caller+" in progress");
            in.putExtra("state","picked");
            startActivity(in);
            finish();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void call_end(View v)
    {
        Log.d("onclick","end");

        try {
            Streamer.getInstance().getOutputStreamer().write(("call:reject".getBytes()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }


    protected void onDestroy() {
        super.onDestroy();
        nLocalBroadcastManager.unregisterReceiver(nBroadcastReceiver);
    }
}

