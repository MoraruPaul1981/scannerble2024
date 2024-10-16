package com.serverscan.datasync.datasync_businesslayer.bl_Jakson;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.hilt.android.qualifiers.ApplicationContext;


public interface ModuleJaksonInterface {

    ObjectMapper getHiltJaksonObjectMapper(@ApplicationContext Context context);

}
