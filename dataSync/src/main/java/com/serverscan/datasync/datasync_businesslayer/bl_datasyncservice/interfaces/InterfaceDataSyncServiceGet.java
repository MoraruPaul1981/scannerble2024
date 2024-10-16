package com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import com.serverscan.datasync.datasync_businesslayer.Services.DataSyncService;

public interface InterfaceDataSyncServiceGet {


      void proseccingDataSyncGet(@NonNull Context context , @NonNull Long version  , @NonNull DataSyncService dataSyncService);


}
