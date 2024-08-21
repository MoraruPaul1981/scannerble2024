package com.scanner.datasync.businesslayer.bl_Jakson;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


public interface ModuleJaksonInterface {

    ObjectMapper getHiltJaksonObjectMapper(@ApplicationContext Context context);

}
