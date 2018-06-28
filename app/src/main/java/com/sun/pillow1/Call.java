package com.sun.pillow1;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Call extends AppCompatActivity {
    List<Friend> list;
    BroadcastReceiver updateUIReciver;
    public static Boolean state;
    RecyclerView recyclerView;
    IntentFilter filter;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        recyclerView = (RecyclerView) findViewById(R.id.calllist);

         alertDialog = new AlertDialog.Builder(getApplicationContext())
                .setTitle("Network error ")
                .setMessage("Please ensure that your Phone is connected to the internet")
                .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);

/***************************no need of seperate FriendListService . Use Volley from this Activity itself********************/


         filter = new IntentFilter();


        filter.addAction("com.hello.action");

        updateUIReciver = new BroadcastReceiver() {
            AdapterFriendList adapter;
            LinearLayoutManager mLinearLayoutManager;

            public void alert_dial()
            {

                if(!alertDialog.isShowing()) {
                    alertDialog.show();
                }
            }

            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("messenger1");

                if (data.equals("error"))
                {
                    alert_dial();
                }
                else
                {
                    list = new ArrayList<>();
                    Log.i("messenger1", data);

                    try {

                        JSONArray friendlist = new JSONArray(data);
                        //JSONArray a = new JSONArray(jsonObject);
                        Log.i("jsontest", "hello");
                        for (int i = 0; i < friendlist.length(); i++) {
                            JSONObject c = friendlist.getJSONObject(i);
                            String id = c.getString("user2");
                            String status_now = c.getString("status_now");
                            String status_req = c.getString("status_req");
                            int fav=c.getInt("fav");
                            Friend f = new Friend(id, status_now,fav);
                            list.add(f);


                        }

                        //create a list from json

                        if (recyclerView.getAdapter() == null) {

                            adapter = new AdapterFriendList(list);
                            mLinearLayoutManager = new LinearLayoutManager(Call.this);
                            recyclerView.setLayoutManager(mLinearLayoutManager);
                            recyclerView.setAdapter(adapter);


                        } else {
                            adapter = new AdapterFriendList(list);
                            recyclerView.swapAdapter(adapter, false);

                        }



                       /*
                        Log.i("jsontest", list.toString() + list.size());
                          if (recyclerView.getAdapter() != null) {
                            //recyclerView.swapAdapter(adapter,true);
                            Log.i("hello113","swap");
                        } else {
                            Log.i("hello113","set");

                            recyclerView.setAdapter(adapter);
                        }*/


                    } catch (Exception e) {
                        Log.i("jsontest", e.toString());
                    }

                }
            }
        };
        registerReceiver(updateUIReciver, filter);

    }

    @Override
    protected void onPause() {
        state=false;
        Intent in=new Intent(this,FriendListService.class);
        stopService(in);
        unregisterReceiver(updateUIReciver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        state=true;
        registerReceiver(updateUIReciver,filter);
        Intent in=new Intent(this,FriendListService.class);
        startService(in);
        super.onResume();
    }
}













