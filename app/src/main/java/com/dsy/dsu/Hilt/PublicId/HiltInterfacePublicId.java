package com.dsy.dsu.Hilt.PublicId;


import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfacePublicId {


    Integer getHiltInterfacePublicId( );
}


