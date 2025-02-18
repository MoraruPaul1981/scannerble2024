package com.dsy.dsu.BusinessLogicAll.DeviceName;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.dsy.dsu.BusinessLogicAll.DeviceName.hilt.QualifiergetDeviceName;
import com.dsy.dsu.Errors.controller.RecordNewErros;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;




@Module
@InstallIn(SingletonComponent.class)
public class ModulegetDeviceName {

    // TODO: 18.02.2025


    @Provides
    @QualifiergetDeviceName
   public  String getDeviceName(@ApplicationContext Context context ){
        // TODO: 18.02.2025
        String getDeviceName=new String();
        try{
            getDeviceName = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            Log.d(this.getClass().getName(), " getDeviceName   " +getDeviceName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  getDeviceName;

    }


}
