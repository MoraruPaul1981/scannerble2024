package com.dsy.dsu.Hilt.Adress1cPays;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterface1cCommintgPays {


   String getHiltCommintgPays( );
}


