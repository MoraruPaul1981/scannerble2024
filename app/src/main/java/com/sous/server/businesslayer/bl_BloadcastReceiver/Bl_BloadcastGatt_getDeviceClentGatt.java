package com.sous.server.businesslayer.bl_BloadcastReceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ProviderInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.remote.bl_writeandreadScanCatt.WtitingAndreadDataForScanGatt;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Bl_BloadcastGatt_getDeviceClentGatt {

    private  Context context;
    private  Long version;

    private ContentProviderServer contentProviderServer;
    private SharedPreferences sharedPreferencesGatt;

    public Bl_BloadcastGatt_getDeviceClentGatt(Context context, Long version) {
        this.context = context;
        this.version = version;
    }

    // TODO: 30.07.2024 code for BroaadCastRecever GATT SERVER
    public void startingGetDeviceBLECkient(  @NonNull Intent intent,
                                             @NonNull AtomicReference<BroadcastReceiver.PendingResult>
                                                     pendingResultAtomicReference) {
        Completable.fromAction(new Action() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void run() throws Throwable {


                        sharedPreferencesGatt =context. getSharedPreferences("gatt", Context.MODE_PRIVATE);
                        // TODO: 29.07.2024
                        getContentProvider();
                        // TODO: 29.07.2024
                        BluetoothDevice bluetoothDevice = null;
                        // TODO: 22.07.2024  Код Брадкаста ресивера
                        // TODO: 24.07.2024
                        bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        // TODO: 29.07.2024
                        bluetoothDevice.fetchUuidsWithSdp();


                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "Bintent.getAction() "+intent.getAction() + " bluetoothDevice " +bluetoothDevice.getName()+"\n"+
                                " intent.getAction() " +intent.getAction());

                        // TODO: 25.07.2024  запускаем запись в базу
                        // TODO: 30.07.2024
                        Integer    resultAddDeviceToGattaDtabse=    new WtitingAndreadDataForScanGatt(context, version, contentProviderServer,
                                sharedPreferencesGatt).writeDatabaseScanGatt(bluetoothDevice, bluetoothDevice.getBondState());

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "Bintent.getAction() "+intent.getAction() + " bluetoothDevice " +bluetoothDevice);
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {
                        // TODO: 30.07.2024
                 BroadcastReceiver.PendingResult pendingResult= pendingResultAtomicReference.get();
                        pendingResult.finish();
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "Bintent.getAction() "+intent.getAction());
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        // TODO: 30.07.2024
                        throwable.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        ContentValues valuesЗаписываемОшибки = new ContentValues();
                        valuesЗаписываемОшибки.put("Error", throwable.toString().toLowerCase());
                        valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
                        valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
                        valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
                        final Object ТекущаяВерсияПрограммы = version;
                        Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                        valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
                        new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .subscribe();
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }

    private void getContentProvider() throws InterruptedException {
        try{
            contentProviderServer=new ContentProviderServer();
            contentProviderServer.attachInfo(context,new ProviderInfo());
            contentProviderServer.onCreate();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }

    // TODO: 30.07.2024 end

}
