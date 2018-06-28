package com.sun.pillow1;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Handler;


public class BluetoothService extends Service {

    BluetoothAdapter mBluetoothAdapter;
    Timer timer;
    OutputStream outputStream;
    private static final int Error = 0;
    private static final int UPDATE_FINISHED = 1;
    String MACAddress;
    MainActivity main;
    String device_name;
    Context context;
    InputStream inputStream;
    private BluetoothSocket mmSocket;

    private BluetoothDevice mmDevice;
    Message m;
    android.os.Handler handler;
    ResultReceiver receiver;



   /* BluetoothService(String device_name )
    {
        this.device_name=device_name;
        Log.i("hello","in blue service");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }
    Intent notificationIntent=new Intent(this,Home.class);
*/
    @Override
    public void onCreate() {
        /*PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Pillow")
                .setContentText("Bluetooth Service Running")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);*/
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        receiver = intent.getParcelableExtra("receiver");
        Log.i("checkstatus", receiver.toString());



        handler = new android.os.Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                alarm_ring();
            }
        };
        // data that will be send into ResultReceiver
       /* Bundle data = new Bundle();
        data.putInt("progress", 333);
        receiver.send(9,data);
        Log.i("conncheck","aaya");*/


        device_name = intent.getStringExtra("device");
        Log.i("bluetoothdevice", device_name);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothSocket tmp = null;
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice devic = null;
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                Log.i("getdevicesname", deviceName);

                if (deviceName.equals(device_name)) {
                    devic = device;
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    Log.i("devices0", deviceHardwareAddress);
                    MACAddress = deviceHardwareAddress;

                }
            }
            if (devic == null) {
                Bundle data = new Bundle();
                data.putInt("progress", 0);
                receiver.send(9,data);
                Log.i("MAC", "no device");

            } else {
                Log.i("MAC", MACAddress);
/*
                    c=new ConnectThread(devic);
                    c.run();*/

                tmp = null;
                Log.i("checkflow", "connn");
                mmDevice = devic;

                try {
                    Method method;
                    method = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    tmp = (BluetoothSocket) method.invoke(mmDevice, 1);

                }
                catch (Exception e) {

                    Bundle data = new Bundle();
                    data.putInt("progress", 0);
                    receiver.send(9,data);
                    Log.i("exception_error", e.toString());
                }

                mmSocket = tmp;


                try {
                    // Connect to the remote device through the socket. This call blocks
                    // until it succeeds or throws an exception.
                    mmSocket.connect();
                    Log.i("Connectionstatus", "connected");
                    Log.i("Connectionstatus1","manage socket");
                    outputStream = mmSocket.getOutputStream();
                    inputStream=mmSocket.getInputStream();
                    Streamer.getInstance().setOutputStreamer(outputStream);
                    Streamer.getInstance().setInputStreamer(inputStream);
                    Log.i("Connectionstatus1", "message sent");
                    Bundle data = new Bundle();
                    data.putInt("progress", 1);
                    receiver.send(9, data);

                   /* Intent  call_service = new Intent(this.getBaseContext(), Call_service.class);
                    call_service.putExtra("blue_dev", devic);
                    call_service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startService(call_service);*/
                } catch (Exception connectException) {
                    // Unable to connect; close the socket and return.


                    Bundle data = new Bundle();
                    data.putInt("progress", 0);
                    receiver.send(9,data);
                    Log.i(  "Connectionstatus", connectException.toString());

                    try {
                        mmSocket.close();
                    } catch (Exception closeException) {
                        //main.mProgress.dismiss();

                        Bundle d = new Bundle();
                        data.putInt("progress", 0);
                        receiver.send(9, d);
                        Log.i("socketerror", "Could not close the client socket" + closeException);
                    }
                }

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.


                // manageMyConnectedSocket(mmSocket);




                    //main.mProgress.dismiss();
                    //Toast.makeText(main.getApplicationContext(),"Failed to connect",Toast.LENGTH_LONG).show();

                }


            }



         else {

            Bundle data = new Bundle();

            Bundle d = new Bundle();
            data.putInt("progress", 0);
            receiver.send(9, d);
            Log.i("devices0", "no devices");


        }


        Log.d("inservice","hello1");
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 1000, 5000);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    public  void incoming(String caller)
    {
        Intent in = new Intent(this.getBaseContext(), Call_recieve.class);
        in.putExtra("caller", caller);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(in);
    }


    public  void call_rec()
    {
        Intent in = new Intent(this.getBaseContext(), Call_picked.class);
        in.putExtra("text", "call in progress");
        in.putExtra("state","recv_call");
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(in);
    }


    public void alarm_ring()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Alarm")
                .setMessage("Alarm Ringing on pillow")
                .setPositiveButton("Snooze", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Alarm_state", "snooze");
                        try {
                            outputStream.write(("snooze:1").getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Alarm_state","dismiss");

                        try {
                            outputStream.write(("dismiss:1").getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create();

        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        alertDialog.show();
    }

    public void endcall()
    {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(BluetoothService.this);
        localBroadcastManager.sendBroadcast(new Intent(
                "com.action.close"));

    }

    public void call_end()
    {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(BluetoothService.this);
        localBroadcastManager.sendBroadcast(new Intent(
                "com.action.end"));
    }

    public void speed(String a)
    {
        Intent in = new Intent(this.getBaseContext(), Call_picked.class);
        in.putExtra("text", "calling "+a+"...");
        in.putExtra("state","call");
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(in);

    }

    private class MyTimerTask extends TimerTask
    {
        @Override
        public void run() {
            //alarm_ring();
            Log.d("inservice", "chal_gaya");
            String res="";

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                res=reader.readLine();
            }
            catch (Exception e) {
                Log.d("inservice",e.toString());
            }

            Log.i("incoming", res);

            if(res.equals("wifi:connected"))
            {
                Log.i("incoming", "inwifi");
            }

            //if(res.equals(""))

            else if(res.equals("alarmrang"))
            {
                Log.i("incoming", "inalarm");
                handler.sendEmptyMessage(0);
            }


            else if(res.startsWith("speeddial:")) {
                String a=res.replace("speeddial:","");
                Log.i("incoming", "inspeed");
                speed(a);
            }



            else if(res.equals("call:rejected"))
            {
                Log.i("incoming", "inrejected");
                endcall();
            }

            else if(res.equals("call:received"))
            {
                Log.i("incoming", "inrecieved");
                call_rec();

            }

            else if(res.equals("call:abruptEnd"))
            {
                Log.i("incoming","inabrupt");
                call_end();

            }


            else if(res.equals("call:missedCall"))
            {
                Log.i("incoming","missedCall");
                call_end();

            }


            else if(res.equals("call:disconnected"))
            {
                Log.i("incoming", "indisconnected");
                endcall();
            }

            else if(res.startsWith("call:"))
            {
                Log.i("incoming", "incall");
                res=res.replace("call:","");
                incoming(res);
            }
        }
    }

    public boolean isForeground(String myPackage)
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        Log.i("foreground", componentInfo.getPackageName().toString());
        return componentInfo.getPackageName().equals(myPackage);
    }

}
