package com.sun.pillow1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class Voice_control extends AppCompatActivity {
    SeekBar call;
    CheckBox check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        call= (SeekBar) findViewById(R.id.call_seek);
        call.setMax(100);

        check= (CheckBox) findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                OutputStream out=Streamer.getInstance().getOutputStreamer();
                try {
                    out.write(("vibrate:"+ isChecked).getBytes());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        call.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("seekbarchange_call",progress+"" );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("seekbarchange_call", seekBar.getProgress() + "");
                OutputStream out=Streamer.getInstance().getOutputStreamer();
                try {
                    out.write(("volume:"+ seekBar.getProgress()).getBytes());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Connect to pillow lost. Trying to reconnect",Toast.LENGTH_LONG).show();
                    Intent in=new Intent(getApplicationContext(),BluetoothService.class);
                    startService(in);
                }


            }
        });

    }
}
