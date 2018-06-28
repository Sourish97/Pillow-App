package com.sun.pillow1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm extends AppCompatActivity {
    TimePicker time;
    Dialog d;
    String selectedtime;
    private FloatingActionButton mFAB;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);



    }




    public void alarmdialog(View v)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        d = adb.setView(new View(this)).create();
        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)

        /*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;*/
        d.getWindow().setLayout(275,350);
        d.show();



        d.setContentView(R.layout.alarmdialog);
        aSwitch= (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    OutputStream outputStream = Streamer.getInstance().getOutputStreamer();
                    try {
                        Log.i("AlarmTime1","cancel");
                          outputStream.write(("alarm:cancel,1".getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        mFAB = (FloatingActionButton) d.findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                time = (TimePicker) d.findViewById(R.id.timePicker);
                int hr = time.getHour();
                int min = time.getMinute();
                Log.i("hr,min", hr + " " + min);
                String state = "AM";

                if (hr == 12)
                {
                    state="PM";
                }

                if (hr == 0) {
                    hr = 12;
                }

                if(hr > 12) {
                    hr -=12;
                    state = "PM";
                }
                selectedtime = hr+ ":" + ((min < 10) ? 0 : "") + min + " ";
                selectedtime += state;
                Log.i("AlarmTime1", selectedtime);
                String minu=min+"";
                if(min<10)
                {
                    minu="0"+min;
                }
                String a=("alarm:set,1," + (hr ) + "," + minu + "," + state);
                Log.i("alarm",a);
                OutputStream outputStream = Streamer.getInstance().getOutputStreamer();
                try {
                    outputStream.write(a.getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView t = (TextView) findViewById(R.id.textClock);
                t.setText(selectedtime);
                Switch s = (Switch) findViewById(R.id.switch1);
                s.setVisibility(View.VISIBLE);
                s.setChecked(true);
                d.dismiss();

            }
        });


    }

}
