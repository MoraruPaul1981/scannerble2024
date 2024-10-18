package com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.binesslogic;

import android.content.Context;

import okhttp3.OkHttpClient;




public abstract class Dispatchers {
    public  Context context;
    public Long version;
    public abstract OkHttpClient.Builder  setPoolDispatcher(Context context, OkHttpClient.Builder getOkhhtpBuilder);


    // TODO: 04.10.2024 end class
}















