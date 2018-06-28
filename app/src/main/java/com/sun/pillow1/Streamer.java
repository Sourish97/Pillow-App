package com.sun.pillow1;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by root on 28/6/17.
 */
public class Streamer {


    public static Streamer mHelper;
    public InputStream in;
    public OutputStream out;

    private Streamer(){

    }


    public static Streamer getInstance() {
        if (mHelper != null) {
            Log.i("Assigned10", "yes");
            return mHelper;

        }
        if(mHelper==null){
            Log.i("Assigned10","NO");
        }
        mHelper=new Streamer();
        return mHelper;

    }


    public void setInputStreamer(InputStream is){
        in= is;
    }

    public InputStream getInputStreamer(){
        return in;
    }


    public void setOutputStreamer(OutputStream os){
        out= os;
    }

    public OutputStream getOutputStreamer(){
        return out;
    }
}





