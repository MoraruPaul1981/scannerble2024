package com.serverscan.datasync.businesslayer.bl_okhttpclient;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;




public abstract class Dispatchers {
    public  Context context;
    public Long version;
    public abstract OkHttpClient.Builder  setPoolDispatcher(Context context, OkHttpClient.Builder getOkhhtpBuilder);


    // TODO: 04.10.2024 end class
}















