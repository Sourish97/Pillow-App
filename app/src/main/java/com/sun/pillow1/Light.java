package com.sun.pillow1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import me.tankery.lib.circularseekbar.CircularSeekBar;

public class Light extends AppCompatActivity  {
    Boolean state=false;
    CircularSeekBar seek;
    ImageView image;
    OutputStream out;
    InputStream in;

    String light_value="light:";
    String light_on="light:on";
    String light_off="light:off";


    LocalBroadcastManager nLocalBroadcastManager;
    BroadcastReceiver nBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.action.light")){

                Log.i("incoming","light");
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        nLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter  = new IntentFilter();
        mIntentFilter.addAction("com.action.light");
        nLocalBroadcastManager.registerReceiver(nBroadcastReceiver, mIntentFilter);


        seek = (CircularSeekBar) findViewById(R.id.seekBar);
        image= (ImageView) findViewById(R.id.light);
         out=Streamer.getInstance().getOutputStreamer();
        in=Streamer.getInstance().getInputStreamer();
        /*try {
            out.write("light:check".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        seek.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                Log.i("Progress", seekBar.getProgress() + "");
                try {

                    int value=(int)seekBar.getProgress();
                    String a=light_value +value ;
                    out.write(a.getBytes());
                } catch (IOException e) {

                    Log.i("bluetoothconn","lost");
                }
                ;
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
    }

            public void toggle(View v) {
                state=!state;
                if(state==true)
                    lightOn();

                if(state==false)
                    lightOff();

            }




    void lightOn()
    {
        seek.setEnabled(true);
        int myColor = Color.parseColor("#14faf616");
        seek.setCircleFillColor(myColor);
        image.setImageResource(R.drawable.light_on);
        Log.i("Progress","on");
        try {
            out.write(light_on.getBytes());
            seek.setProgress(100);
        } catch (Exception e) {
            Log.i("bluetoothconn", "lost");
            Toast.makeText(getApplicationContext(),"failed to connect to bluetooth device",Toast.LENGTH_LONG).show();
        }


    }


    void lightOff()
    {
        seek.setProgress(0);
        seek.setEnabled(false);
        int myColor = Color.parseColor("#ffffff");
        seek.setCircleFillColor(myColor);
        image.setImageResource(R.drawable.light_off);
        Log.i("Progress", "off");


        try {
            out.write(light_off.getBytes());
        } catch (Exception e) {
            Log.i("bluetoothconn","lost");
        }

    }

}
