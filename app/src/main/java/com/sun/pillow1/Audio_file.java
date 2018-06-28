package com.sun.pillow1;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;


public class Audio_file extends AppCompatActivity {

    byte[] byteArray;
    MediaPlayer mediaPlayer=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

/*
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        try {

            String url="http://13.126.45.185:666/voicemail/download";
            StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response){


                    try {

                       // File myFile = new File(getFilesDir().getPath().toString() + "/notif.wav");
                        //  File myFile = new File("notif.wav");
                        File myFile = new File("/storage/emulated/0/Android/media" + "/notification.wav");
                        myFile.createNewFile();
                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        Log.i("voice_messagebyte", response);

                        byte[] data = Base64.decode(response, Base64.DEFAULT);
                        String text = new String(data, "UTF-8");


                        myOutWriter.write(text);
                        myOutWriter.close();
                        fOut.close();
                        Log.i("voice_message", "success");
                        Log.i("voice_message", myFile.getPath()+""+myFile.length());
                    }

                    catch (Exception e)
                    {
                        Log.i("voice_message",e.toString());
                    }



                    //Uri myUri=Uri.fromFile(new File(getFilesDir().getPath().toString() + "/notif.wav"));
                    Uri myUri=Uri.fromFile(new File("/storage/emulated/0/Android/media" + "/notification.wav"));


                    Log.i("voice_mail",myUri.toString());

                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.i("voice_message 22 ",myUri.getPath());

                    try
                    {
                        mediaPlayer.setDataSource(Audio_file.this, myUri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }

                    catch (Exception e)
                    {
                        Log.i("voice_message 11 ",e.toString());
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("voice_message 33", error.toString());

                }
            });

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);

        }
        catch (Exception e)
        {

        }*/

        playSample();
       // MediaPlayer mediaplayer = MediaPlayer.create(this, Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"));
        //mediaplayer.start();
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        Log.i("voicemail","stopped");
        super.onBackPressed();
    }

    public void playSample() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    try {

                        Log.i("voicemail","set");
                        mediaPlayer.setDataSource("http://13.126.45.185:666/voicemail/download");
                        //mediaPlayer.setDataSource(Audio_file.this,Uri.parse("http://vprbbc.streamguys.net:80/vprbbc24.mp3"));
                        Log.i("voicemail","prepare");
                        mediaPlayer.prepare();
                        Log.i("voicemail","setVol");
                        mediaPlayer.setVolume(10000, 100000);

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Log.i("voicemail", "end");
                                mp.release();
                            }
                        });
//                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
//
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//
//                            }
//                        });
                        Log.i("voicemail","start");
                        mediaPlayer.start();
//                        Log.i("voicemail", "end");
                    }

                    catch (IOException e)
                    {
                        Log.e("AudioFileError", "Could not open file for playback.", e);
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

            }
        };
        task.execute((Void[]) null);
    }



        /*DownloadFile d=new DownloadFile();
        d.execute("");
    }
    class DownloadFile extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... params) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            try {
                String url="http://13.126.45.185:666/voicemail/download";
                StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){

                       byteArray= response.getBytes();
                        Log.i("Voicebytearray",byteArray.length+" "+byteArray.toString().length());




                        try {

                            File myFile = new File(getFilesDir().getPath().toString() + "/notif.wav");
                            //  File myFile = new File("notif.wav");
                            myFile.createNewFile();
                            FileOutputStream fOut = new FileOutputStream(myFile);
                            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                            myOutWriter.append(byteArray.toString());
                            myOutWriter.close();
                            fOut.close();
                            Log.i("voice_message", "success");
                            Log.i("voice_message", myFile.getPath()+""+myFile.length());


                        }

                        catch (Exception e)
                        {
                            Log.i("voice_message",e.toString());
                        }





                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("voice_message 33", error.toString());

                    }
                });

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(stringRequest);

            }
            catch (Exception e)
            {

            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            Uri myUri=Uri.fromFile(new File(getFilesDir().getPath().toString() + "/notif.wav"));
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            Log.i("voice_message 22 ",myUri.getPath());
            try
            {
                mediaPlayer.setDataSource(Audio_file.this, myUri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

            catch (Exception e)
            {
                   Log.i("voice_message 11 ",e.toString());
            }
        }
    }*/
}
