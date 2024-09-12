package com.serverscan.datasync.businesslayer.bl_datasyncservice;

import android.content.Context;

import androidx.annotation.NonNull;

import com.serverscan.datasync.businesslayer.Services.DataSyncService;

public interface InterfaceDataSyncService {


      void onTransact(@NonNull Context context , @NonNull Long version  , @NonNull DataSyncService dataSyncService);


}
