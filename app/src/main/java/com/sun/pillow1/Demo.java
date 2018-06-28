package com.sun.pillow1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;

public class Demo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }


    public void call1(View v)
    {
       OutputStream out=Streamer.getInstance().getOutputStreamer();
        try {
            Log.i("calll","hello");
            out.write("call:shubham".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
