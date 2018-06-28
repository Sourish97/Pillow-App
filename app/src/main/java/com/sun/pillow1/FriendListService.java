package com.sun.pillow1;

import android.app.IntentService;
import android.app.LauncherActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sun.pillow1.Streamer;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class FriendListService extends IntentService {


    public FriendListService() {
        super("Friend");
    }


    public FriendListService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InputStreamReader inputReader;


        Log.i("Service2", "yeah");


/*            InputStream in = Streamer.getInstance().getInputStreamer();
            inputReader = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inputReader);
            Log.i("helllo", "yeee");*/




               /* StringBuilder out = new StringBuilder();
                String line;
                line = buf.readLine();
                out.append(line);


                String data = out.toString();
                Log.i("helllo", data);*/
                String url="http://13.126.45.185:666/get";


                RequestQueue queue = Volley.newRequestQueue(this);

                while(com.sun.pillow1.Call.state) {
                    StringRequest stringRequest = new StringRequest
                            (Request.Method.GET, url, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Intent local = new Intent();
                                    local.putExtra("messenger1", response);
                                    local.setAction("com.hello.action");
                                    Log.i("hello113", response);
                                    FriendListService.this.sendBroadcast(local);
                                }


                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    Log.i("hello113", error.toString());
                                    Intent local = new Intent();
                                    local.putExtra("messenger1", "error");
                                    local.setAction("com.hello.action");
                                    Log.i("hello113","error");
                                    FriendListService.this.sendBroadcast(local);


                                }
                            });

                    queue.add(stringRequest);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

       // Log.i("hello11","done");
/*
        String data=" ";
        Intent local = new Intent();
        local.putExtra("messenger1", data);
        local.setAction("com.hello.action");
        Log.i("hello11",data);
        FriendListService.this.sendBroadcast(local);
*/


    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Service1", "yeah");

    }
}





