package com.scanner.datasync.businesslayer.bl_JbossAdress;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.util.LinkedHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
@SuppressLint("SuspiciousIndentation")
public class HiltJboss {


    @Provides
    @Singleton
    @QualifierJbossServer3
    public LinkedHashMap<String, String> getJbossAdressDebug(@ApplicationContext Context context) {
        LinkedHashMap<String, String> МассивПортовСервераDebugandRelize = new LinkedHashMap();
        // TODO: SSL 10.11.2022 ДЕбаг*/
       /// МассивПортовСервераDebugandRelize.putIfAbsent("192.168.3.4" ,"8443/jbossgatt-2.0-SNAPSHOT/sous.jboss.scanner");// TODO: SSL 10.11.2022 ДЕбаг*/

        // TODO: 18.03.2023 московский сервер ЧЕРЕЗ DNS

        МассивПортовСервераDebugandRelize.putIfAbsent("base.dsu1.ru","8889/jbossgatt-2.0-SNAPSHOT/sous.jboss.scanner");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
/*
       МассивПортовСервера.putIfAbsent(8888,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервера.putIfAbsent(8889,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервера.putIfAbsent(8890,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
*/
        // TODO: 18.03.2023 московский сервер
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " МассивПортовСервераDebugandRelize "+МассивПортовСервераDebugandRelize );

        return МассивПортовСервераDebugandRelize;

    }



}