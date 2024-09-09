package com.serverscan.datasync.businesslayer.bl_versionsgatt;


import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import org.jetbrains.annotations.NotNull;
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


  }


    // TODO: 09.09.2024  получаем ранее записанную версию данных gatt server
  public   Long  getanewVersionofgatt  (@NotNull Context context , @NotNull Long version  ){
    Long newVersionGatt=0l;
    try{




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
