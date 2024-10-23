package com.serverscan.datasync.datasync_businesslayer.bl_versionsgatt;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;


import com.serverscan.datasync.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_dates.BinesslogicParserDates;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;



@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicVersions {

Context context;

    public @Inject BinesslogicVersions(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    // TODO: 09.09.2024  запиываем новую версию данных с сервера  



    // TODO: 09.09.2024  получаем ранее записанную версию данных gatt server
  public   Long gettingVersionLocal(@NotNull Context context , @NotNull Long version  ){
    Long versionlocal=0l;
    try{
        ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null,
                    "  SELECT MAX ( versionlocal )   FROM gattserverdataversion  WHERE versionlocal  IS NOT  NULL  ", null,null,null);
        // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
                versionlocal=      cursorNewVesionGattServer.getLong(0);

            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " versionlocal " +versionlocal);
            // TODO: 19.07.2024 closing
        cursorNewVesionGattServer.close();
        
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e +
                " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
     return  versionlocal;
    }


    // TODO: 05.10.2024  


    public   Long gettingVersionRemote(@NotNull Context context , @NotNull Long version  ){
        Long versionremote=0l;
        try{
            ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null,
                    "  SELECT MAX ( versionremote )   FROM gattserverdataversion  WHERE versionremote  IS NOT  NULL  ", null,null,null);
            // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
                versionremote=      cursorNewVesionGattServer.getLong(0);

            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " versionremote " +versionremote);
            // TODO: 19.07.2024 closing
            cursorNewVesionGattServer.close();
            
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        return  versionremote;
    }























    // TODO: 05.10.2024  Методы Записи версии в сисменую таблицу
    @SuppressLint("NewApi")
    public void recordingVersionRemote(@NotNull Context context , @NotNull Long version,@NotNull Long versionPostDataJbossGattOtServer){
        try{
            ContentResolver contentProviderNewVersion=context.getContentResolver();
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            // TODO: 09.09.2024
            ContentValues contentValuesdvensedScannerserversuccessRemote =new ContentValues();
            contentValuesdvensedScannerserversuccessRemote.put("versionremote",versionPostDataJbossGattOtServer);

            // TODO: 25.07.2024  Создаем Новую Даты
            BinesslogicParserDates binesslogicParserDates =new BinesslogicParserDates(context,version);
            Date date_update = binesslogicParserDates.dateCreation();
            String date_updatefinal=  binesslogicParserDates.datesasaString(date_update);
            contentValuesdvensedScannerserversuccessRemote.put("date_update",date_updatefinal);
            contentValuesdvensedScannerserversuccessRemote.put("id",1);


         Bundle bUpdate=  new Bundle();
            String  SQlOperUpdate=  " UPDATE  gattserverdataversion  SET     versionremote=?  ,date_update=?  WHERE id =?    ;";
        //    String  SQlOperInsert=  " REPLACE INTO gattserverdataversion VALUES(?,?,?,? );";
            bUpdate.putString("sql",SQlOperUpdate );
            // TODO: 09.09.2024 new Date

            // TODO: 09.09.2024 сама операция
         int urlNewVersionGattServer=   contentProviderNewVersion.update(uri, contentValuesdvensedScannerserversuccessRemote,bUpdate);
            Integer resultNewVersionGattServer= Optional.ofNullable(urlNewVersionGattServer).map(Integer::new).orElse(0);


            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " resultNewVersionGattServer " +resultNewVersionGattServer+
                    " versionPostDataJbossGattOtServer " +versionPostDataJbossGattOtServer);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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


    }



    @SuppressLint("NewApi")
    public void  recordingVersionLocal(@NotNull Context context , @NotNull Long version,@NotNull Long versionGETDataJbossGattOtServer  ){
        try{
            // TODO: 05.10.2024
            ContentResolver contentProviderNewVersion=context.getContentResolver();
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            // TODO: 09.09.2024
            ContentValues contentValuesNewGattVersionLocal=new ContentValues();
            contentValuesNewGattVersionLocal.put("versionlocal",versionGETDataJbossGattOtServer);

            // TODO: 25.07.2024  Создаем Новую Даты
            BinesslogicParserDates binesslogicParserDates =new BinesslogicParserDates(context,version);
            Date date_update = binesslogicParserDates.dateCreation();
            String date_updatefinal=  binesslogicParserDates.datesasaString(date_update);
            contentValuesNewGattVersionLocal.put("date_update",date_updatefinal);
            contentValuesNewGattVersionLocal.put("id",1);


            Bundle bUpdate=  new Bundle();
            String  SQlOperUpdate=  " UPDATE  gattserverdataversion  SET     versionremote=?  ,date_update=?  WHERE id =?    ;";
            //    String  SQlOperInsert=  " REPLACE INTO gattserverdataversion VALUES(?,?,?,? );";
            bUpdate.putString("sql",SQlOperUpdate );
            // TODO: 09.09.2024 new Date

            // TODO: 09.09.2024 сама операция
            int urlNewVersionGattServer=   contentProviderNewVersion.update(uri, contentValuesNewGattVersionLocal,bUpdate);
            Integer resultNewVersionGattServer= Optional.ofNullable(urlNewVersionGattServer).map(Integer::new).orElse(0);


            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " resultNewVersionGattServer " +resultNewVersionGattServer
                    +" versionGETDataJbossGattOtServer " +versionGETDataJbossGattOtServer);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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


    }


// TODO: 09.09.2024 end class
}
