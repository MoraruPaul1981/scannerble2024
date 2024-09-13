package com.serverscan.datasync.businesslayer.bl_datasyncservice;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.Services.DataSyncService;
import com.serverscan.datasync.businesslayer.bl_Jakson.BinesslogicJakson;
import com.serverscan.datasync.businesslayer.bl_versionsgatt.BinesslogicVersions;
import com.serverscan.datasync.datalayer.model.ScannerserversuccessEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Completable;


@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicDataSyncServiceGET implements  InterfaceDataSyncService{
    // TODO: 25.08.2024
    Context context;
    long version;

    public @Inject BinesslogicDataSyncServiceGET(@ApplicationContext Context hitcontext) {
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
    public void onTransact(@NonNull Context context , @NonNull Long version
        ,@NonNull DataSyncService dataSyncService)    {
        // TODO: 03.09.2024
        // TODO: 04.09.2024 POST
        Completable.fromAction(()->{
                    // TODO: 12.09.2024
                    // TODO: 03.09.2024 get DATA
                    Long versionoflastsentdata=    new BinesslogicVersions(context).getanewVersionofgatt(context,version);
                    // TODO: 09.09.2024 получаем данные которые надотправить на сервер  GATT SEVER
                    Cursor cursorSingle=   dataSyncService.businesslogicDatabase.getingCursor("SELECT" +
                            " * FROM scannerserversuccess  WHERE current_table >='"+versionoflastsentdata.toString()+"' ORDER BY id   ",version);
                    // TODO: 03.09.2024
                    if (cursorSingle.getCount()>0) {

                        // TODO: 23.08.2024 Генерирум List базе курсора в Обьекты Листа ЧТобы ПОтом ПОлучить Jakson Json
                        List<ScannerserversuccessEntity> listForJakson=  dataSyncService. genetarorJaksonJSON.genetarorListFor(context,version,cursorSingle);
                        // TODO: 03.09.2024 get Stream based on Cursor
                        byte[] ByteJakson=    dataSyncService.genetarorJaksonJSON.genetarorJaksonJSON(context,version,     listForJakson  ,dataSyncService.getHiltJaksonObjectMapper     );

                        // TODO: 03.09.2024 sending  Stream to Server
                             new BinesslogicJakson(context).sendOkhhtpServiceForJboss(context,version,dataSyncService.getJbossAdressDebug,
                                cursorSingle ,ByteJakson,dataSyncService.getOkhhtpBuilder);

                        // TODO: 03.09.2024 get InputStream   for sending an server
                        Log.d(context.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );

                    }
                    // TODO: 03.09.2024 get InputStream   for sending an server
                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " cursorSingle.getCount() " +cursorSingle.getCount());

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
                .doOnComplete(()->{
                    // TODO: 04.09.2024
                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                }).subscribe();
        Log.d(context.getClass().getName(), "\n" + " class " +
                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }








}
