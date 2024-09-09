package com.serverscan.datasync.businesslayer.bl_versionsgatt;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_dates.WorkerDates;

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
  public   Long  getanewVersionofgatt  (@NotNull Context context , @NotNull Long version  ){
    Long newVersionGatt=0l;
    try{
        ContentResolver contentProviderNewVersion=context.getContentResolver();

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/gattserverdataversion" );
            Cursor cursorNewVesionGattServer = contentProviderNewVersion.query(uri, null, "SELECT  " +
                    "  MAX ( versionremote )   FROM scannerserversuccess  WHERE NOT IS NULL ", null,null,null);
        // TODO: 09.09.2024
            if (cursorNewVesionGattServer.getCount()>0){
                cursorNewVesionGattServer.moveToFirst();
                newVersionGatt=      cursorNewVesionGattServer.getLong(0);

            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " newVersionGatt " +newVersionGatt);
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
     return  newVersionGatt;
    }







// TODO: 09.09.2024 end class
}
