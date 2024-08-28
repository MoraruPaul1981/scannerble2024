package com.scanner.datasync.businesslayer.bl_RemoteMessaging;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;


public interface HiltOkhttp {
    OkHttpClient.Builder getOkhhtpBuilder( );
}