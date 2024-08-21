package com.scanner.datasync.businesslayer.bl_Okhhtp;

import android.content.Context;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;


public interface OkhhtpInterface {

    OkHttpClient.Builder getOkhhtpBuilder(@ApplicationContext Context hiltcontext );
}
