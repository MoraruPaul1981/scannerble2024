package com.dsy.dsu.Hilt.JbossAdrress.intarfaces;


import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfaceJbossServer {


   String getHiltJbossServer( );
}


