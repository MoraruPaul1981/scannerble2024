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
    public LinkedHashMap<Integer, String> getJbossAdressDebug(@ApplicationContext Context context) {
        LinkedHashMap<Integer, String> МассивПортовСервераDebugandRelize = new LinkedHashMap();
        // TODO: 18.03.2023 московский сервер ЧЕРЕЗ DNS
        // TODO: 18.03.2023 московский сервер
        МассивПортовСервераDebugandRelize.putIfAbsent(8080, "192.168.3.4");// TODO: 10.11.2022 ДЕбаг

        return МассивПортовСервераDebugandRelize;

    }


    @Provides
    @Singleton
    @QualifierJbossServer2
    public LinkedHashMap<Integer, String> getHiltJbossReliz(@ApplicationContext Context context) {
        LinkedHashMap<Integer, String> МассивПортовСервераReliz = new LinkedHashMap();
        // TODO: 18.03.2023 московский сервер ЧЕРЕЗ DNS
/*
       МассивПортовСервера.putIfAbsent(8888,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервера.putIfAbsent(8889,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервера.putIfAbsent(8890,"base.dsu1.ru");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
*/
        // TODO: 18.03.2023 московский сервер
        МассивПортовСервераReliz.putIfAbsent(8888, "80.70.108.165");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервераReliz.putIfAbsent(8890, "80.70.108.165");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        МассивПортовСервераReliz.putIfAbsent(8889, "80.70.108.165");// TODO: 10.11.2022 РЕЛИЗ  Москвовский
        return МассивПортовСервераReliz;
    }
}