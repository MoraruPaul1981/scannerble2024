package com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces;

import android.content.Context;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.OkHttpClient;


public interface OkhhtpInterface {

    OkHttpClient.Builder getOkhhtpBuilder(@ApplicationContext Context hiltcontext );
}
