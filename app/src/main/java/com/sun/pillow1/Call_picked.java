package com.sun.pillow1;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class Call_picked extends AppCompatActivity {
    String state,text;
    TextView t;
    FloatingActionButton conferencebtn;


    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.action.close")){
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
        setContentView(R.layout.activity_call_picked);

        conferencebtn=(FloatingActionButton)findViewById(R.id.conferenceBtn);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.action.close");
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, mIntentFilter);
        text=getIntent().getStringExtra("text");
        state=getIntent().getStringExtra("state");
        t= (TextView) findViewById(R.id.textcall);
        t.setText(text);
    }

    @Override
    public void onBackPressed() {

    }

    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    public void end(View v)
    {
        try {
            if(state.equals("call"))
            {
                Log.i("kyachalrahahai","call");
                Streamer.getInstance().getOutputStreamer().write(("call:abruptEnd").getBytes());
            }

            else {
                Streamer.getInstance().getOutputStreamer().write(("call:disconnect").getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }


    public  void  addNew(View view)
    {

    }
    public void conference(View view){
        Intent i=new Intent(getApplicationContext(),Call.class);
        startActivity(i);
    }
}
