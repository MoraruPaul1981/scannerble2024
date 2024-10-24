package com.sous.server.businesslayer.bl_reversescallback;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Date;

public class GetMessage {

    Context context;

    Long version;


    public GetMessage(Context context, Long version) {
        this.context = context;
        this.version = version;
    }



    public Message getMessage() {
        Message   message =new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Log.d(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );

                return true;
            }
        }).obtainMessage();
        Log.d(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
        return message;
    }


    // TODO: 24.10.2024  end class
}
