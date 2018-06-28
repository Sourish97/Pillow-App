package com.sun.pillow1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Admin on 04/10/2017.
 */

public class Token extends AppCompatActivity {
    Intent newIn;
    private static final String TAG="Token class";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        Log.d(TAG,"Token class created");
        String token = getIntent().getStringExtra("token").toString();
        EditText e=(EditText) findViewById(R.id.editText);
        e.setText(token);
    }

}
