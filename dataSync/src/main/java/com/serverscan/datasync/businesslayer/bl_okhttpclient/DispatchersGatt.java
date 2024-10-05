package com.serverscan.datasync.businesslayer.bl_okhttpclient;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

public class DispatchersGatt extends  Dispatchers {
    @Override
    public OkHttpClient.Builder setPoolDispatcher(Context context, OkHttpClient.Builder getOkhhtpBuilder) {
        Dispatcher dispatcher = new Dispatcher(Executors.newCachedThreadPool());
        getOkhhtpBuilder.getDispatcher$okhttp().cancelAll();
        getOkhhtpBuilder.dispatcher(dispatcher);
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                + " dispatcher.executorService().isShutdown() " + dispatcher.executorService().isShutdown());
        return getOkhhtpBuilder;
    }
}