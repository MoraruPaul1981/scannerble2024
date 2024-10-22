package com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.Services.DataSyncService;
import com.serverscan.datasync.datasync_businesslayer.bl_network.BinesslogicNetworkWorkerGet;
import com.serverscan.datasync.datasync_businesslayer.bl_Jakson.parsejsonfromserver.WtiringJaksonJSON;
import com.serverscan.datasync.datasync_businesslayer.bl_datasyncservice.interfaces.InterfaceDataSyncServiceGet;
import com.serverscan.datasync.datasync_businesslayer.bl_versionsgatt.BinesslogicVersions;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Single;


@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicDataSyncServiceGetGet implements InterfaceDataSyncServiceGet {
    // TODO: 25.08.2024
    Context context;
    long version;

    public @Inject BinesslogicDataSyncServiceGetGet(@ApplicationContext Context hitcontext) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        PackageInfo pInfo = null;
        try {
            pInfo = hitcontext.getPackageManager().getPackageInfo(hitcontext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        version = pInfo.getLongVersionCode();
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }



//TODO GET
@Override
    public void proseccingDataSyncGet(@NonNull Context context , @NonNull Long version
        , @NonNull DataSyncService dataSyncService)    {
    // TODO: 18.10.2024 Получаем данные от сервера gatt GET данные о всех mac adress
        Single.fromCallable(()->{
                    // TODO: 12.09.2024
                    // TODO: 09.09.2024 получаем данные которые надотправить на сервер  GATT SEVER
                    // TODO: 03.09.2024 get DATA
                    Long gettingVersionLocal=    new BinesslogicVersions(context).gettingVersionLocal(context,version);

                    // TODO: 03.09.2024 sending  Stream to Server
                    byte[] bytesGetOtJBoss =        new BinesslogicNetworkWorkerGet(context)
                            .callBackOtJbossGattServerGet(context, version, dataSyncService.getJbossAdressDebug,
                            gettingVersionLocal, dataSyncService.getOkhhtpBuilder);


                    // TODO: 03.09.2024 get InputStream   for sending an server
                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" bytesGetOtJBoss " +bytesGetOtJBoss);


               
            return  bytesGetOtJBoss;

        }).doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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

                })
                .doOnSuccess(bytesGetOtJBoss->{
                    // TODO: 04.09.2024 Полученые байты потов преобразуем в JAKSON JSON
                    WtiringJaksonJSON wtiringJaksonJSON=new WtiringJaksonJSON(context,version,dataSyncService.getHiltJaksonObjectMapper);
                    // TODO: 22.10.2024
                    JsonNode  jsonNodeAtomicReferenceGattGet     =    wtiringJaksonJSON.converttoJacksonObject(bytesGetOtJBoss);

                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " bytesGetOtJBoss " +bytesGetOtJBoss
                            +" jsonNodeAtomicReferenceGattGet " +jsonNodeAtomicReferenceGattGet);

                }).blockingSubscribe();

    //TODO GET

        Log.d(context.getClass().getName(), "\n" + " class " +
                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }








}
