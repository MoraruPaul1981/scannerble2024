package com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import com.serverscan.datasync.Services.DataSyncService;


public interface InterfaceDataSyncServicePost {


      void proseccingDataSyncPost(@NonNull Context context , @NonNull Long version  , @NonNull DataSyncService dataSyncService);


}
