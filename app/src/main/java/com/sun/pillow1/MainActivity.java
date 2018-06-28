package com.sun.pillow1;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Intent in;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BluetoothAdapter mBluetoothAdapter;
    ProgressDialog mProgress;
    static Context c;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=MainActivity.this;
        mProgress =new ProgressDialog(this);
        sharedPreferences=getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Boolean b=sharedPreferences.getBoolean("Login",false);
        String username=sharedPreferences.getString("user","");
        boolean notifClicked=false;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.d(TAG,"MainActivity onCreate called!!!!!!!");
        if(bundle!=null) {
            int size=bundle.size();
            Toast.makeText(this, "" + bundle.size(), Toast.LENGTH_LONG).show(); //*
            for (String key: bundle.keySet())
            {
                if(key.equals("double")){
                    notifClicked=true;
                }
                if(key.equals("from")){
                    if((bundle.get("from").toString()).equals(("264757007158"))){
                        notifClicked=true;
                    }
                }
                Log.d (TAG, key + " is a key in the bundle and value is "+bundle.get(key));
            }
            if(notifClicked && (size==1 || size==4)){
                Intent newin=new Intent(this,Audio_file.class);
                startActivity(newin);
            }
        }
//            if(intentAction.equals("android.intent.action.Audio_MAIN")) {
//
//            }
//
        /*Log.i("loginvalue",b+"");
        if(b)
        {
            Intent blue=new Intent(this,BluetoothService.class);
            mProgress.show();
            blue.putExtra("receiver",new DownReceiver(new Handler()));
            blue.putExtra("device",username);
            startService(blue);;

        }
*/
//        Intent myintent=getIntent();
//        if( myintent.getExtras() != null)
//        {
//
//            String status=""+in.getStringExtra("received");
//            if(status.equals("voicemail")){
//                Log.i(TAG,"Pending Intent Received");
//                myintent=new Intent(this,Audio_file.class);
//                startActivity(myintent);
//            }
//
//        }
//        Toast.makeText(this,""+getIntent(),Toast.LENGTH_LONG).show();

        String titleId="Signing in...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

  /*      BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                Log.i("hello111", "disconnected");
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);*/

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.i("error111", "bluetooth not supported");

        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    public class DeleteUser extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.i("tokenidnew",e.toString());
            }
            FirebaseInstanceId.getInstance().getToken();
            return null;
        }
    }



//    public void callAudio(){
//        Log.i(TAG,"Pending Intent Received");
//        Intent myintent=new Intent(this,Audio_file.class);
//        startActivity(myintent);
//
//    }

    public void login(View v)
    {
        DeleteUser d=new DeleteUser();
       // d.execute();
        EditText e=(EditText)findViewById(R.id.username);
        Intent blue=new Intent(this,BluetoothService.class);
        mProgress.show();
        blue.putExtra("receiver", new DownReceiver(new Handler()));
        blue.putExtra("device", e.getText().toString());
        editor.putString("user", e.getText().toString());
        editor.commit();
        startService(blue);
        //BluetoothService bs=new BluetoothService( e.getText().toString());
        //bs.bluetooth();

         in=  new Intent(this,Home.class);
       // startActivity(in);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.i("result", "Success");


            }


            else {
                Log.i("result", resultCode + "");
                Toast.makeText(this, "Please Enable Bluetooth", Toast.LENGTH_SHORT).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }
    private static final int Error = 0;
    private static final int UPDATE_FINISHED = 1;

 /*   public  Handler handler = new Handler(){
        @Override public void handleMessage(Message msg) {
            Log.i("checkstatus","yeahhh");
            if (mProgress.isShowing()) {
                mProgress.dismiss();
            }
            switch (msg.what) {
                case UPDATE_FINISHED:
                    Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_LONG).show();
                    break;
                case Error:
                    Toast.makeText(MainActivity.this,"Failed to login",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };*/



    private class DownReceiver extends ResultReceiver {


        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         *
         *
         */



        public DownReceiver(Handler handler) {
            super(handler);
        }

        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Log.i("checkstatus", "hello");
            mProgress.dismiss();
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 9) {

                int progress = resultData.getInt("progress");
                if(progress==0)
                {
                    Toast.makeText(MainActivity.this, "Failed to login",Toast.LENGTH_LONG).show();

                }

                if(progress==1)
                {

                    editor.putBoolean("Login",true);
                    editor.commit();
                    Intent in=new Intent(MainActivity.this,Home.class);
                    startActivity(in);

                }
                // pd variable represents your ProgressDialog
              /*  mProgress.setProgress(progress);
                mProgress.setMessage(String.valueOf(progress) + "% downloaded sucessfully.");*/
            }
        }
    }


}
