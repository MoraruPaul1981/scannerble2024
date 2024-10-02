package com.serverscan.datasync.businesslayer.bl_versionsgatt;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_dates.WorkerDates;
import com.serverscan.datasync.datalayer.findingtodata.FindingDataForGatServer;

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
 public void  recordinganewVersionofgatt  (@NotNull Context context , @NotNull Long version , @NonNull Long буферОтветотJbossfinal){
      try{
          ContentResolver contentProviderNewVersion=context.getContentResolver();
          Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
          // TODO: 09.09.2024
          ContentValues contentValuesNewGattVersion=new ContentValues();
          contentValuesNewGattVersion.put("versionremote",буферОтветотJbossfinal);
          // TODO: 09.09.2024 new Date
          // TODO: 25.07.2024  Создаем Новую Даты
          WorkerDates workerDates=new WorkerDates(context,version);
          Date date_update = workerDates.dateCreation();
          String date_updatefinal=  workerDates.datesasaString(date_update);
          contentValuesNewGattVersion.put("date_update",date_updatefinal);

          // TODO: 09.09.2024 сама операция
           Uri urlNewVersionGattServer=   contentProviderNewVersion.insert(uri, contentValuesNewGattVersion);
          Integer resultNewVersionGattServer= Optional.ofNullable(urlNewVersionGattServer.toString().replaceAll("content://","")).map(Integer::new).orElse(0);


          Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                  " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                  " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " resultNewVersionGattServer " +resultNewVersionGattServer);

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


    // TODO: 09.09.2024  получаем ранее записанную версию данных gatt server
  public   Long getVesionDataGattServerRemote(@NotNull Context context , @NotNull Long version  ){
    Long localVersionServerRemote=0l;
    try{
        ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null,
                    "  SELECT MAX ( versionremote  )   FROM gattserverdataversion  WHERE versionremote   IS NOT  NULL  ", null,null,null);// versionremote   //versionlocal
        // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
                localVersionServerRemote=      cursorNewVesionGattServer.getLong(0);

            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " localVersionServerRemote " +localVersionServerRemote);
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
     return  localVersionServerRemote;
    }


    // TODO: 02.10.2024

    public   Long getVesionDataGattServerLocal(@NotNull Context context , @NotNull Long version  ){
        Long localVersionServerLocal=0l;
        try{
            ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null,
                    "  SELECT MAX ( versionlocal   )   FROM gattserverdataversion  WHERE versionlocal    IS NOT  NULL  ", null,null,null);// versionremote   //versionlocal
            // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
                localVersionServerLocal=      cursorNewVesionGattServer.getLong(0);

            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " localVersionServerLocal " +localVersionServerLocal);
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
        return  localVersionServerLocal;
    }









    @SuppressLint("NewApi")
    public void recordingAfterNewVersionwealign(@NotNull Context context , @NotNull Long version){
        try{

            FindingDataForGatServer findingDataForGatServer=new FindingDataForGatServer(context,version);
            // Long current_table = findVersonGattServer("SELECT MAX ( versionremote  ) AS MAX_R  FROM gattserverdataversion","gattserverdataversion");
            Long буферОтветотJbossfinal =findingDataForGatServer.findVersonGattServer("SELECT MAX ( current_table  ) AS MAX_R  FROM scannerserversuccess","scannerserversuccess");

            ContentResolver contentProviderNewVersion=context.getContentResolver();
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            // TODO: 09.09.2024
            ContentValues contentValuesdvensedScannerserversuccess =new ContentValues();
            contentValuesdvensedScannerserversuccess.put("versionremote",буферОтветотJbossfinal);

            // TODO: 25.07.2024  Создаем Новую Даты
            WorkerDates workerDates=new WorkerDates(context,version);
            Date date_update = workerDates.dateCreation();
            String date_updatefinal=  workerDates.datesasaString(date_update);
            contentValuesdvensedScannerserversuccess.put("date_update",date_updatefinal);
            contentValuesdvensedScannerserversuccess.put("id",1);


         Bundle bUpdate=  new Bundle();
            String  SQlOperUpdate=  " UPDATE  gattserverdataversion  SET     versionremote=?  ,date_update=?  WHERE id =?    ;";
        //    String  SQlOperInsert=  " REPLACE INTO gattserverdataversion VALUES(?,?,?,? );";
            bUpdate.putString("sql",SQlOperUpdate );
            // TODO: 09.09.2024 new Date

            // TODO: 09.09.2024 сама операция
          int urlNewVersionGattServer=   contentProviderNewVersion.update(uri, contentValuesdvensedScannerserversuccess,bUpdate);
            Integer resultNewVersionGattServer= Optional.ofNullable(urlNewVersionGattServer).map(Integer::new).orElse(0);


            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " resultNewVersionGattServer " +resultNewVersionGattServer);

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
