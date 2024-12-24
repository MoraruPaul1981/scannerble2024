package com.dsy.dsu.BusinessLogicAll.AllboundServices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.Services.Service_Async_1C;
import com.dsy.dsu.Services.Service_Notificatios_Для_Согласования;

public class AllBindingService {
Context context;
    Message message;
    public AllBindingService(@NonNull  Context context,@NonNull Message message) {
        this.context = context;
        this.message = message;
    }
    // TODO: 27.03.2023 BINDING#1
  public   void ВиндингСлужбы1С() {
      final Service_Async_1C.LocalBinderGET1С[] service_Async_СинхронизацияОБЩАЯ1С =
              new Service_Async_1C.LocalBinderGET1С[1];
        try {
            ServiceConnection connectionОБЩЕЙ1СGet = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    try {
                           service_Async_СинхронизацияОБЩАЯ1С[0] = (Service_Async_1C.LocalBinderGET1С) service;
                        if (service.isBinderAlive() == true) {
                            Log.i(context.getClass().getName(), "    onServiceConnected  " +
                                    "service_Async_СинхронизацияОБЩАЯ1С.getService()" + service.isBinderAlive());
                            Bundle bundle=new Bundle();
                            bundle.putBinder("allbinders",  service_Async_СинхронизацияОБЩАЯ1С[0]);
                            message.setData(bundle);
                            message.getTarget().dispatchMessage(message);
                           // message.sendToTarget();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                                + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    try {
                        Log.i(context.getClass().getName(), "    onServiceDisconnected  " );
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                                + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            };
            Intent intentЗапускСлужбыОБЩЕЙ1СGet = new Intent(context, Service_Async_1C.class);
            intentЗапускСлужбыОБЩЕЙ1СGet.setAction("com.ServiceUpdatePoОбновлениеПО");
          context.  bindService(intentЗапускСлужбыОБЩЕЙ1СGet ,connectionОБЩЕЙ1СGet,Context.BIND_AUTO_CREATE );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    // TODO: 27.03.2023 BINDING#3










}
