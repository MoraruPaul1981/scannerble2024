package com.scanner.datasync.businesslayer.bl_SSL;

import java.security.cert.X509Certificate;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface X509GetInt {

    X509Certificate gettrustAllCerts();
}
