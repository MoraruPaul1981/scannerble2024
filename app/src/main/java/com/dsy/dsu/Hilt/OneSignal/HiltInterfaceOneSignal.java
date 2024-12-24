package com.dsy.dsu.Hilt.OneSignal;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfaceOneSignal {
    String getHiltInterfaceOneSignal( );
}


