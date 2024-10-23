package com.serverscan.datasync.Errors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.serverscan.datasync.GeneratorUUIDs.GeneratorUUIDs;

import java.util.Date;
import java.util.Optional;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SubClassErrors {
    Context context;
    public SubClassErrors(@NonNull Context context) {
        this.context =context;
    }
    public  void МетодЗаписиОшибок(@NonNull ContentValues contentValuesДляЗаписиОшибки) {
        try {
            Log.i( context.getClass().getName(), "contentValuesДляЗаписиОшибки  " + contentValuesДляЗаписиОшибки);

            // TODO: 22.08.2024 Server gatt wtire Errror
            Completable.fromRunnable(()->{


                Integer getVersionforErrorNew=        getVersionforErrorNew("SELECT MAX ( current_table  ) AS MAX_R  FROM errordsu1");
                contentValuesДляЗаписиОшибки.put("current_table",getVersionforErrorNew);

                Long getuuid =new GeneratorUUIDs(). МетодГенерацииUUID();
                contentValuesДляЗаписиОшибки.put("uuid",getuuid);


                Uri uri = Uri.parse("content://com.sous.servergatt.prodider/errordsu1" );
                ContentResolver resolver = context. getContentResolver();
                Uri    insertData=   resolver.insert(uri, contentValuesДляЗаписиОшибки);
                Integer РезультатВставки= Optional.ofNullable(insertData.toString()).map(Integer::new).orElse(0);



                        // TODO: 08.08.2024
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR GATT SERVER  !!!!!!"
                                +"\n"+ " РезультатВставки " +РезультатВставки);




                        // TODO: 08.08.2024
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +"ERROR ERROR ERROR CLETNT SERVER  !!!!!!"+"\n"+ " РезультатВставки " +РезультатВставки+
                                " contentValuesДляЗаписиОшибки " +contentValuesДляЗаписиОшибки);


                    }).blockingSubscribe();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e( context.getClass().getName(), "SubClassErrors ДЛЯ GATT SERVER error " + e +
                    " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+ " date " +new Date().toGMTString());
        }

    }


    private   Integer getVersionforErrorNew(@NonNull String СамЗапрос) {
        Integer   ВерсияДАнных = 0;
        try{
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/errordsu1" );
            ContentResolver resolver = context. getContentResolver();

            Cursor cursorПолучаемДЛяСевреа = resolver.query(uri, null, СамЗапрос, null,null,null);

            if (cursorПолучаемДЛяСевреа.getCount()>0){
                cursorПолучаемДЛяСевреа.moveToFirst();
                ВерсияДАнных=      cursorПолучаемДЛяСевреа.getInt(0);
                Log.i(this.getClass().getName(), "ВерсияДАнных"+ ВерсияДАнных) ;
                ВерсияДАнных++;
            }else {
                ВерсияДАнных=1;
            }
            Log.w(context.getClass().getName(), " РЕЗУЛЬТАТ insertData  cursorПолучаемДЛяСевреа  " +  cursorПолучаемДЛяСевреа.toString() );
            cursorПолучаемДЛяСевреа.close();
            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  ВерсияДАнных;
    }



}
