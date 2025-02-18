package com.dsy.dsu.BootAndAsync.BlBootAsync.Hilts;


import com.dsy.dsu.Hilt.PublicId.QualifierPublicId;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltInterfaceEventAsyncOrUpdatePOUsers {

    @QualifierPublicId
    Integer getHiltInterfaceEventAsyncOrUpdatePOUsers( );
}


