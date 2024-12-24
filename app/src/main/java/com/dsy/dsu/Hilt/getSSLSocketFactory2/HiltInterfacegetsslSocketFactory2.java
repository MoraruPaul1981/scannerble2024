package com.dsy.dsu.Hilt.getSSLSocketFactory2;


import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfacegetsslSocketFactory2 {


    SSLSocketFactory getsslSocketFactory2( );
}


