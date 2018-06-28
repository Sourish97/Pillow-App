package com.sun.pillow1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sa90.materialarcmenu.ArcMenu;
import com.sa90.materialarcmenu.StateChangeListener;

import java.io.IOException;

public class
        Home extends AppCompatActivity {
    ArcMenu arcMenuAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent loginIntent=getIntent();

        arcMenuAndroid = (ArcMenu) findViewById(R.id.arcMenu);
        arcMenuAndroid.setStateChangeListener(new StateChangeListener() {
            @Override
            public void onMenuOpened() {
                //TODO something when menu is opened
            }

            @Override
            public void onMenuClosed() {
                //TODO something when menu is closed
                }
        });







    }





    public void wifi(View v)
    {

        Log.i("hello", "wifi");
        WifiManager wifi= (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        final String a=wifi.getConnectionInfo().getSSID();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.wifi, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.password);

        dialogBuilder.setTitle("WIFI");
        dialogBuilder.setMessage("Enter password for " + a);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String pass = edt.getText().toString();
                Log.i("wifi_conf", a + pass);
                try {
                    Streamer.getInstance().getOutputStreamer().write(("wifi:" + a + "," + pass).getBytes());
                } catch (IOException e) {
                    Log.i("wifi_conf", e.toString());
                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }



    public void light(View v)
    {

        Log.i("hello","light");
        Intent in=new Intent(this,Light.class);
        startActivity(in);
    }




    public void alarm(View v)
    {

        Log.i("hello","alarm");
        Intent in=new Intent(this,Alarm.class);
        startActivity(in);
    }



    public void call(View v)
    {

        Log.i("hello","call");
        Intent in=new Intent(this,Call.class);
        startActivity(in);
    }



    public void volume(View v)
    {

        Log.i("hello","voice");
        Intent in=new Intent(this,Voice_control.class);
        startActivity(in);
    }


    public void logout(View v)
    {

        Log.i("hello", "logout");
        SharedPreferences sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("Login", false);
        stopService(new Intent(this, BluetoothService.class));
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logging Out...")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i("hello", "logout");
                        SharedPreferences sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean("Login", false);
                        stopService(new Intent(getApplicationContext(), BluetoothService.class));
                        Intent in=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(in);
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }
}
