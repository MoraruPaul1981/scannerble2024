package com.sous.scanner.businesslayer.bl_BroadcastReciver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_forServices.Businesslogic_JOBServive;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Businesslogic_GattClinetSuccessNAMECHANGED {

    private Context context;
    private Long version;

    public Businesslogic_GattClinetSuccessNAMECHANGED(Context context, Long version) {
        this.context = context;
        this.version = version;
    }

    @SuppressLint("MissingPermission")
    public void successLocalBroadcastManagerNAMECHANGED(@NotNull Intent intent,
                                                        @NotNull BluetoothDevice bluetoothDevice,
                                                        @NotNull AtomicReference<BroadcastReceiver.PendingResult> pendingResultAtomicReferenceClient) {
        Completable.fromAction(new Action() {
                    @Override
                    public void run() throws Throwable {

                        // TODO: 31.07.2024
                        String getAction=    Optional.ofNullable( intent.getAction().toUpperCase()).map(m->m.toUpperCase()) .orElseGet(()->"");
                        String getAddress=     Optional.ofNullable(bluetoothDevice.getAddress().toUpperCase()) .orElseGet(()->"");
                        String getName= Optional.ofNullable(bluetoothDevice.getName()).map(m->m.toUpperCase()) .orElseGet(()->"");

                        LocalDateTime futureDate = LocalDateTime.now();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss.SSS");
                        String getBremy=   dtf.format(futureDate);
                        // TODO: 07.08.2024

                        final   String     bluetoothDeviceNAMe = intent.getParcelableExtra(BluetoothDevice.EXTRA_NAME);
                        int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

                        // TODO: 31.07.2024
                        Businesslogic_JOBServive businesslogicJobServive=new Businesslogic_JOBServive(context);

                        // TODO: 16.07.2024  startting Fragment Scannig
                        businesslogicJobServive.   startingServiceSimpleScan("robotlaunchingfrombackground");



                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "getAction "+getAction + "\n"
                                + " getAddress " +getAddress+ "\n" +" getName " +getName+"\n"+ " getBremy " +getBremy+"\n"
                                +" Thread " +Thread.currentThread().getName().toUpperCase()+"\n");

                        pendingResultAtomicReferenceClient.get().finish();


                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() );
                    }
                })
                .subscribeOn(Schedulers.computation())
                .blockingSubscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

            }

            @Override
            public void onComplete() {
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() );

            }

            @Override
            public void onError(@NonNull Throwable e) {
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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
        });

    }
}


