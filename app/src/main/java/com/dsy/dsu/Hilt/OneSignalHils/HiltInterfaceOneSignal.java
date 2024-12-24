package com.dsy.dsu.Hilt.OneSignalHils;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfaceOneSignal {
    String getHiltInterfaceOneSignal( );
}


