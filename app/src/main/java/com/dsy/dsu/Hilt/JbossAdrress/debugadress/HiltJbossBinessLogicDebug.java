package com.dsy.dsu.Hilt.JbossAdrress.debugadress;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.Hilt.JbossAdrress.intarfaces.HiltJbossBinessLogicIntarface;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class HiltJbossBinessLogicDebug implements HiltJbossBinessLogicIntarface {


    /**
     *
     */
    @Override
    public LinkedHashMap<Integer,String>   selectenableforSslrequests(@NotNull SharedPreferences preferencesJboss, @NotNull Context context) {
        LinkedHashMap<Integer,String> getJbossPort= new LinkedHashMap();
        try{
            SharedPreferences.Editor editor = preferencesJboss.edit();
            getJbossPort.putIfAbsent(8080,"192.168.3.4");// TODO: 10.11.2022 Debug
            editor.putString("enablesll","http");


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() );

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  getJbossPort;
    }
}
