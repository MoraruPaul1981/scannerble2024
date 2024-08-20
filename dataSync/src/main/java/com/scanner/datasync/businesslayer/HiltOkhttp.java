package com.scanner.datasync.businesslayer;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltOkhttp {
    OkHttpClient.Builder getOkhhtpBuilder( );
}
