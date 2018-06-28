package com.sun.pillow1;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by root on 26/6/17.
 */

public class Friend {
    private String mName;
    private String mStatus;
    private int mFav;
    private static boolean array[]={false,false,false,false};

    public Friend(String name, String status,int fav) {
        mName = name;
        mStatus = status;
        if(fav!=0)
        {
            array[fav]=true;
        }
        mFav=fav;

    }

    public String getName() {
        return mName;
    }

    public String getisOnline() {
        return mStatus;
    }

    public int getFav()
    {
        return mFav;
    }

    public void setFav(int fav,int curr)
    {
        mFav=fav;
        array[fav]=true;
        array[curr]=false;
    }

    public int getFavCount()
    {
        for(int i=1;i<=3;i++) {
            if (array[i] == false)
            {
                return i;
            }
        }
        return 0;
    }

}