package com.sun.pillow1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 6/7/17.
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "tokenid";


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

    }


    private void sendRegistrationToServer(String token) {

        String user = "raunak";
        String json_string = "{'user':'" + user + "','token':" + token + "'}";
        Log.i("json_string", json_string);
        JsonObjectRequest jsonObjectRequest = null;
        String url = "http://13.126.45.185:666/favourites/insert";
        Intent intent = new Intent(this,Token.class);
        intent.putExtra("token",token);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Token")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(token))
                .setContentText(token)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1111 /* ID of notification */, notificationBuilder.build());
        Log.d(TAG,"Token activity started");
        startActivity(intent);

     /*   try {
            jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json_string), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        catch (JSONException e) {
            Log.i("json_error",e.toString());
        }
        RequestQueue queue=Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }*/
    }
}