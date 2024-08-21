package com.scanner.datasync.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scanner.datasync.businesslayer.bl_RemoteMessaging.RemoteMessaging;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DataSyncService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this

    @Inject
    RemoteMessaging remoteMessaging;

    @Inject
    ObjectMapper getHiltJaksonObjectMapper;

    @Inject
    OkHttpClient.Builder getOkhhtpBuilder;


    public DataSyncService() {
        super("DataSyncService");
    }

    // TODO: Customize helper method


    public DataSyncService(String name) {
        super(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

}