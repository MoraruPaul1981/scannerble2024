package com.dsy.dsu.SynsProccessor;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.BusinessLogicAll.Class_MODEL_synchronized;
import com.dsy.dsu.BusinessLogicAll.Jakson.GeneratorBinarySONSerializer;
import com.dsy.dsu.BusinessLogicAll.Jakson.GeneratorJSONSerializer;
import com.dsy.dsu.BusinessLogicAll.SubClassUpVersionDATA;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.SynsProccessor.PrograsBarAsync.GetPrograssbarChangeIndicator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.json.JSONException;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.net.ssl.SSLSocketFactory;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProccesorparallelSynch   {



   protected Context context;
    protected  ObjectMapper jsonGenerator;
    protected   SSLSocketFactory getsslSocketFactory2;


    protected Integer PublicID;

    @NonNull LinkedHashMap<Integer,String> getHiltPortJboss;

    private SharedPreferences preferences;

    private  String  РежимЗапускаСинхронизации;

    private  CopyOnWriteArrayList<Map<String, String>> getBufferFromJbossServerAllTables;

    public ProccesorparallelSynch(@NonNull Context context,
                                  @NonNull ObjectMapper jsonGenerator,
                                  @NonNull SSLSocketFactory getsslSocketFactory2,
                                  @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                                  @NonNull    CopyOnWriteArrayList<Map<String, String>> getBufferFromJbossServerAllTables,
                                  @NonNull Integer PublicID) {



        this. context=context;
        this.   jsonGenerator=jsonGenerator;
        this.   getsslSocketFactory2=getsslSocketFactory2;
        this.    getHiltPortJboss=getHiltPortJboss;
        this.  PublicID=PublicID;
        this.  getBufferFromJbossServerAllTables=getBufferFromJbossServerAllTables;

    }

    public Long startingAsyncParallels() {
        CopyOnWriteArrayList<Long> coutSucceessItemAsycnTablesComplete=new CopyOnWriteArrayList();
        try{
            // TODO: 30.09.2024
            preferences =context. getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
             РежимЗапускаСинхронизации = preferences.getString("РежимЗапускаСинхронизации","СамыйПервыйЗапускСинхронизации");
             ExecutorService executorServiceAsync= null;

            // TODO: 07.04.2024  Г
             switch (РежимЗапускаСинхронизации) {
                 // TODO: 07.10.2024
                 case "СамыйПервыйЗапускСинхронизации":
                 // TODO: 27.12.2024
                     executorServiceAsync= Executors.newFixedThreadPool(1);
                 Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                         " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                         " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "\n"+ " РежимЗапускаСинхронизации " +РежимЗапускаСинхронизации);
                 break;
                 // TODO: 07.10.2024
                 case "ПовторныйЗапускСинхронизации":
                     // TODO: 27.12.2024
                     executorServiceAsync= Executors.newSingleThreadExecutor();
                     Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                             " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                             " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "\n"+ " РежимЗапускаСинхронизации " +РежимЗапускаСинхронизации);
                     break;

                 // TODO: 07.10.2024  chsnage
             }

// TODO: 20.01.2025 сама синхрониаиця
            Flowable.fromIterable(getBufferFromJbossServerAllTables)
                    .parallel()
                    .runOn(Schedulers.from(executorServiceAsync))
                    .doOnNext(new io.reactivex.rxjava3.functions.Consumer<Map<String, String>>() {
                        @Override
                        public void accept(Map<String, String> stringStringMapMultiPotoks) throws Throwable {
                            // TODO: 28.12.2024
                            // TODO: 27.12.2024
                            String getNameTableSingle= stringStringMapMultiPotoks.entrySet().stream().filter(e->e.getKey().equalsIgnoreCase("name")).map(Map.Entry::getValue).findFirst().get();

                            // TODO: 06.12.2023  запуск синхризуции по таблице конктерной
                            coutSucceessItemAsycnTablesComplete.add(getLooTablesPOSTANDGET(stringStringMapMultiPotoks))      ;
                            // TODO: 30.09.2024
                            // TODO: 15.09.2023
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    + " getBufferFromJbossServerAllTables.size() " + getBufferFromJbossServerAllTables.size()
                                    +"\n"+" getNameTableSingle " +getNameTableSingle);
                        }
                    })
                    .doOnError(new io.reactivex.rxjava3.functions.Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            throwable.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " +throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(context).recordnewerror(throwable.toString(),
                                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    })
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Throwable {

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " РежимЗапускаСинхронизации " +РежимЗапускаСинхронизации);
                        }
                    }).sequential().blockingSubscribe();




            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber()  );
        }
        return coutSucceessItemAsycnTablesComplete.stream().mapToLong(l->l)  .reduce(0, Long::sum);
    }

// TODO: 07.04.2024




    public Long getLooTablesPOSTANDGET(@NonNull Map<String, String> stringStringMapRowSingle) {
        Long   РезультатТаблицыОбмена=0l;
        try{
            // TODO: 21.08.2023 Запуск Синхронизации после получение Версии
            String getId= stringStringMapRowSingle.entrySet().stream().filter(e->e.getKey().equalsIgnoreCase("id")).map(Map.Entry::getValue).findFirst().get();
            String getNameTable= stringStringMapRowSingle.entrySet().stream().filter(e->e.getKey().equalsIgnoreCase("name")).map(Map.Entry::getValue).findFirst().get();
            String getVersionserver= stringStringMapRowSingle.entrySet().stream().filter(e->e.getKey().equalsIgnoreCase("versionserver")).map(Map.Entry::getValue).findFirst().get();
            Long getVersionserverversion= Long.valueOf(stringStringMapRowSingle.entrySet().stream().filter(e->e.getKey().equalsIgnoreCase("versionserverversion")).map(Map.Entry::getValue).findFirst().get());

         // TODO: 27.12.2024 get Date parser
            DateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",new Locale("ru"));
           Date  getParserVersionserver= dateFormat.parse(getVersionserver);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getVersionserver " +getVersionserver+ " getNameTable "
                    + getNameTable);
            /////////////TODO ИДЕМ ПО ШАГАМ К ЗАПУСКИ СИНХРОГНИАЗЦИИ
            РезультатТаблицыОбмена=
                    startSendingDataTotheServerOrReceivingDataFromTheJbossServer(getNameTable,
                            getVersionserverversion, PublicID,getParserVersionserver);
            // TODO: 12.07.2023

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getNameTable " +getNameTable+ " getVersionserver "
                    + getVersionserver +" getVersionserverversion " +getVersionserverversion+
                    "   РезультатТаблицыОбмена " + РезультатТаблицыОбмена);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  РезультатТаблицыОбмена;
    }

// TODO: 07.04.2024



    @SuppressLint("Range")
    Long startSendingDataTotheServerOrReceivingDataFromTheJbossServer(@NonNull String ИмяТаблицы,
                                                                      @NonNull  Long ВерсияДанныхсSqlServer,
                                                                      @NonNull  Integer PublicID,
                                                                      @NonNull Date     ВремяОтSqlServer) {

        ConcurrentSkipListSet<Long> completedPostAndGetInsertorUpdateOperations=new ConcurrentSkipListSet<>();
        try  {
                Log.d(this.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"
                        +" ИмяТаблицы " +ИмяТаблицы
                        + "\n"
                        +" ВерсияДанныхсSqlServer " +ВерсияДанныхсSqlServer
                        + "\n"
                        +" PublicID " +PublicID
                        + "\n"
                        +" ВремяОтSqlServer " +ВремяОтSqlServer);

// TODO: 24.09.2024 Запускаем Отправление и или ПОлучение данных  сервера JBoss


            // TODO: 08.04.2024 SEND SERVERR JBOSS POST
            completedPostAndGetInsertorUpdateOperations.add(startSendingDatatoTheServerOnjboss(ИмяТаблицы,
                    ВерсияДанныхсSqlServer,
                    PublicID,
                    ВремяОтSqlServer, "POST"));

// TODO: 24.09.2024
            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    "  +  completedPostAndGetInsertorUpdateOperations.size() " + completedPostAndGetInsertorUpdateOperations.size());




            // TODO: 08.04.2024 через Retry Obsever множественое ображение  к серверу GET
            completedPostAndGetInsertorUpdateOperations.add(completedInsertorUpdateOperations(ИмяТаблицы,
                    ВерсияДанныхсSqlServer,
                    PublicID,
                    ВремяОтSqlServer, "GET"));

            // TODO: 24.09.2024
            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    "  +  completedPostAndGetInsertorUpdateOperations.size() " + completedPostAndGetInsertorUpdateOperations.size());

            ///   TODO: 08.04.2024 Показываем пользовалю ПРоценты
             if (completedPostAndGetInsertorUpdateOperations.size() > 0) {
                //TODO Таблицы ОТ  Андройда
                CopyOnWriteArrayList<String>      getMainTabelAllAndroid = new PUBLIC_CONTENT(context).методCreatingMainTabels(context);
                // TODO: 24.12.2024
                new GetPrograssbarChangeIndicator(context).setAsyncrograssbarMap(getMainTabelAllAndroid, ИмяТаблицы, completedPostAndGetInsertorUpdateOperations.size());
            }




            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    "  +  completedPostAndGetInsertorUpdateOperations.get() " + completedPostAndGetInsertorUpdateOperations.size());


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return        completedPostAndGetInsertorUpdateOperations.stream().mapToLong(i->i).reduce(0,Long::sum);
    }









    // TODO: 07.04.2024

    @SuppressLint("SuspiciousIndentation")
    private   Long completedInsertorUpdateOperations(@NonNull String ИмяТаблицы,
                                                     @NonNull Long ВерсияДанныхсSqlServer,
                                                     @NonNull  Integer  PublicID,
                                                     @NonNull Date   ВремяОтSqlServer,
                                                     @NonNull String CooserGetandPost) {


        ConcurrentSkipListSet<Long> completedGetInsertorUpdateOperations=new ConcurrentSkipListSet<>();
        try{
            Observable observableGet=      Observable.range(1,Integer.MAX_VALUE)
                    .take(10,TimeUnit.MINUTES);
            // TODO: 02.11.2023  ПРИНИМАЕМ ДАННЫЕ ОТ СЕРВЕРА ПО ЧАСТЯМ
            observableGet .forEachWhile(new Predicate<Integer>() {
                        @Override
                        public boolean test(Integer integer) throws Throwable {
                            Long getAsyncCurerentTable =0l;
                            try {
                                // TODO: 08.04.2024 выполения операции  GET ()
                                  getAsyncCurerentTable = getCursorWithVersion(ИмяТаблицы, ВерсияДанныхсSqlServer, PublicID, ВремяОтSqlServer, CooserGetandPost);
                                // TODO: 30.09.2024
                                if (getAsyncCurerentTable>0) {
                                    completedGetInsertorUpdateOperations.add(getAsyncCurerentTable);
                                }

                                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                        "completedGetInsertorUpdateOperations " + completedGetInsertorUpdateOperations
                                        + "\n" +
                                        "getAsyncCurerentTable " + getAsyncCurerentTable);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                            if (getAsyncCurerentTable > 0) {
                                return true;
                            } else {
                                return false;
                            }
                        }

                    });
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "completedGetInsertorUpdateOperations.get() "
                    + completedGetInsertorUpdateOperations  );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


        return completedGetInsertorUpdateOperations.stream().mapToLong(i->i).reduce(0,Long::sum);
    }





//todo  оправляем  даннеы на СЕРВЕР POST
    private   Long startSendingDatatoTheServerOnjboss(@NonNull String ИмяТаблицы,
                                                           @NonNull Long ВерсияДанныхсSqlServer,
                                                           @NonNull  Integer  PublicID,
                                                           @NonNull Date   ВремяОтSqlServer,
                                                           @NonNull String CooserGetandPost) {


        ConcurrentSkipListSet<Long> completedPostInsertorUpdateOperations=new ConcurrentSkipListSet<>();
        try{

                        // TODO: 08.04.2024 выполения операции  POST ()
            completedPostInsertorUpdateOperations.add(getCursorWithVersion( ИмяТаблицы, ВерсияДанныхсSqlServer, PublicID, ВремяОтSqlServer,  CooserGetandPost));


                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                "completedPostInsertorUpdateOperations.get()  " + completedPostInsertorUpdateOperations.size());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return    completedPostInsertorUpdateOperations.stream().mapToLong(i->i).reduce(0,Long::sum);
    }


































    @SuppressLint("Range")
    private Long getCursorWithVersion(@NonNull String ИмяТаблицы,
                                      @NonNull Long ВерсияДанныхсSqlServer,
                                      @NonNull  Integer  PublicID,
                                      @NonNull Date   ВремяОтSqlServer,
                                      @NonNull String CooserGetandPost) {
        // TODO: 08.04.2024 get and post
        Long ResultatAndGETANDPOST=0l;
     try (        Cursor КурсорДляАнализаВерсииДанныхАндройда = getCurcorForAllVersionDataAndroid(ИмяТаблицы); ){
        // TODO: 07.04.2024  получаем данные локалные лдля сравенния

        // TODO: 05.04.2024  получаем верисю данных андройд версия всехданныхс
        if (КурсорДляАнализаВерсииДанныхАндройда.getCount() > 0) {
            КурсорДляАнализаВерсииДанныхАндройда.moveToFirst();


            Long ВерсииНаАндройдеЛокальная =
                    КурсорДляАнализаВерсииДанныхАндройда.getLong(КурсорДляАнализаВерсииДанныхАндройда
                            .getColumnIndex("localversionandroid_version"));

            Long ВерсииНаАндройдеСерверная = КурсорДляАнализаВерсииДанныхАндройда.getLong(
                    КурсорДляАнализаВерсииДанныхАндройда.getColumnIndex("versionserveraandroid_version"));

            String ВремяДанныхSQliteНаАндройде = КурсорДляАнализаВерсииДанныхАндройда.getString(
                    КурсорДляАнализаВерсииДанныхАндройда.getColumnIndex("versionserveraandroid"));

            // TODO: 09.08.2023  даты заполяем таблиц с серверар
           Date ВремяДанныхНаАндройде = new FormattingVersionDastaSqlserver(context).formattingDateOnVersionSqlServerMirror(ВремяДанныхSQliteНаАндройде);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "ВерсииНаАндройдеЛокальная  " + ВерсииНаАндройдеЛокальная
                    + "\n" +
                    "ВерсииНаАндройдеСерверная  " + ВерсииНаАндройдеСерверная
                    + "\n" +
                    "ВремяДанныхSQliteНаАндройде  " + ВремяДанныхSQliteНаАндройде
                    + "\n" +
                    "ВремяДанныхНаАндройде  " + ВремяДанныхНаАндройде);


            //TODO СЛЕДУЮЩИЙ ЭТАМ РАБОТЫ ОПРЕДЕЛЯЕМ ЧТО МЫ ДЕЛАЕМ ПОЛУЧАЕМ ДАННЫЕ С СЕВРЕРА ИЛИ НА ОБОРОТ  ОТПРАВЛЯЕМ ДАННЫЕ НА СЕРВЕР
            ResultatAndGETANDPOST = AceccssAndCoohceGetDatatingAndPostDating(
                    ИмяТаблицы,
                    ВерсияДанныхсSqlServer,
                    PublicID,
                    ВерсииНаАндройдеЛокальная,
                    ВерсииНаАндройдеСерверная,
                    ВремяДанныхНаАндройде,
                    ВремяОтSqlServer,
                      CooserGetandPost);
            // TODO: 07.04.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    "ResultatAndGETANDPOST " + ResultatAndGETANDPOST);


        }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
       return  ResultatAndGETANDPOST;
    }
// TODO: 07.04.2024



    // TODO: 05.04.2024 курсор получчаем весрию всех жанных на андройде для дальншего сопоствалвения
    private Cursor getCurcorForAllVersionDataAndroid(@NonNull String ИмяТаблицыОтАндройда_Локальноая) throws ExecutionException, InterruptedException {
        Cursor AllVersionAndroidLocalSQlite = null;
        try{
            Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabasecurrentoperations/" + "MODIFITATION_Client" + "");
            ContentResolver contentResolver=context. getContentResolver();
            // TODO: 05.04.2024 get Curcour all  version local Android Sqlite
            AllVersionAndroidLocalSQlite =      contentResolver.query(uri,new String[]{},
                    new String(" SELECT *  FROM    MODIFITATION_Client   where name = ?    "),
                    new String[]{String.valueOf(ИмяТаблицыОтАндройда_Локальноая)},null);///   "  //// SELECT * FROM  viewtabel WHERE year_tabels=?  AND month_tabels=?  AND cfo=?  AND status_send!=?
            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"+
                    " AllVersionAndroidLocalSQlite " +AllVersionAndroidLocalSQlite);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return AllVersionAndroidLocalSQlite;
    }
// TODO: 07.04.2024



    //TODO СЛЕДУЮЩИЙ ЭТАМ РАБОТЫ ОПРЕДЕЛЯЕМ   ОБОРОТ  ОТПРАВЛЯЕМ ДАННЫЕ НА СЕРВЕР




    Long AceccssAndCoohceGetDatatingAndPostDating(@NonNull String ИмяТаблицы,
                                                  @NonNull Long ВерсияДанныхсSqlServer,
                                                  @NonNull  Integer  PublicID,
                                                  @NonNull Long ВерсииНаАндройдеЛокальная,
                                                  @NonNull Long  ВерсииНаАндройдеСерверная,
                                                  @NonNull Date  ВремяДанныхНаАндройде,
                                                  @NonNull Date   ВремяОтSqlServer,
                                                  @NonNull String CooserGetandPost) {
        //TODO
       ConcurrentSkipListSet<Long> concurrentSkipListSetResultatOtServerIsertOrUpdate=new ConcurrentSkipListSet<>();
        try {

            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"
                    +" ИмяТаблицы " +ИмяТаблицы
                    + "\n"
                    +" ВерсияДанныхсSqlServer " +ВерсияДанныхсSqlServer
                    + "\n"
                    +" PublicID " +PublicID
                    + "\n"
                    +" ВерсииНаАндройдеЛокальная " +ВерсииНаАндройдеЛокальная+ "\n"
                    +" ВерсииНаАндройдеСерверная " +ВерсииНаАндройдеСерверная
                    + "\n"
                    +" ВремяДанныхНаАндройде " +ВремяДанныхНаАндройде
                    + "\n"
                    +" ВремяОтSqlServer " +ВремяОтSqlServer+"\n");



            // TODO: 08.04.2024 start  post
            if (CooserGetandPost.equalsIgnoreCase("POST")) {
                // TODO: 05.04.2024 post() sending
                // TODO: 05.10.2021  POST()-->
                if (ВерсииНаАндройдеЛокальная > ВерсииНаАндройдеСерверная) {
                    // TODO: 05.04.2024  отправлем только определенные таблицы
                    if (! ИмяТаблицы.equalsIgnoreCase("view_onesignal") &&
                            ! ИмяТаблицы.equalsIgnoreCase("chat_users") &&
                            ! ИмяТаблицы.equalsIgnoreCase("view_onesignal") &&
                            ! ИмяТаблицы.equalsIgnoreCase("track") &&
                            ! ИмяТаблицы.equalsIgnoreCase("cfo") ) {

                        Log.d(this.getClass().getName(), "\n"
                                + " время: " + new Date() + "\n+" +
                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"
                                +" ИмяТаблицы " +ИмяТаблицы
                                + "\n"
                                +" ВерсияДанныхсSqlServer " +ВерсияДанныхсSqlServer
                                + "\n"
                                +" PublicID " +PublicID
                                + "\n"
                                +" ВерсииНаАндройдеЛокальная " +ВерсииНаАндройдеЛокальная+ "\n"
                                +" ВерсииНаАндройдеСерверная " +ВерсииНаАндройдеСерверная
                                + "\n"
                                +" ВремяДанныхНаАндройде " +ВремяДанныхНаАндройде
                                + "\n"
                                +" ВремяОтSqlServer " +ВремяОтSqlServer+"\n");

                        ////// todo МЕТОД POST() в фоне    ////// todo МЕТОД POST
                        concurrentSkipListSetResultatOtServerIsertOrUpdate.add(   МетодПосылаемДанныеНаСервервФоне(ИмяТаблицы, ВерсииНаАндройдеСерверная));


                    final Long ResultSendDataANSqlServerJboss=   concurrentSkipListSetResultatOtServerIsertOrUpdate.stream().reduce(0l, (a, b) -> a + b);
                        if (ResultSendDataANSqlServerJboss>0) {
                            // TODO: 01.07.2023 После Успешно Посылании Данных На Сервер Повышаем Верисю Данных
                            методПослеУспешногоПолученияПовышаемВерсию(ИмяТаблицы );

                            // TODO: 09.10.2024

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                    " ResultSendDataANSqlServerJboss "+ResultSendDataANSqlServerJboss);
                        }


                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                " concurrentSkipListSetResultatOtServerIsertOrUpdate.size()  "+concurrentSkipListSetResultatOtServerIsertOrUpdate.size());
                    }

                    // TODO: 05.04.2024  метод GET()   ПОЛУЧАЕМ ДАННЫЕ !!!!!
                    // TODO: 05.04.2024  метод GET()   ПОЛУЧАЕМ ДАННЫЕ !!!!!
                }
            }


            // TODO: 08.04.2024 start get
            if (CooserGetandPost.equalsIgnoreCase("GET")) {

                // TODO: 19.10.2021   GET()->
                if (ВерсияДанныхсSqlServer > ВерсииНаАндройдеСерверная ) {
                    // TODO: 05.04.2024
                    if ( ВремяОтSqlServer.compareTo(ВремяДанныхНаАндройде)!=0) {
                        // TODO: 05.04.2024
                        if (!ИмяТаблицы.trim().equalsIgnoreCase("errordsu1")
                                && !ИмяТаблицы.trim().equalsIgnoreCase("settings_tabels")) {

                            ////// todo МЕТОД GET() в фоне    ////// todo МЕТОД GET
                            concurrentSkipListSetResultatOtServerIsertOrUpdate.add( МетодДанныеПолучаемНаСервервФоне(ИмяТаблицы, ВерсииНаАндройдеСерверная, PublicID));

                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " concurrentSkipListSetResultatOtServerIsertOrUpdate.size()  "
                                    + concurrentSkipListSetResultatOtServerIsertOrUpdate.size()+
                                    "\n"+ " ВремяДанныхНаАндройде " +ВремяДанныхНаАндройде+" ВремяДанныхНаАндройде " +ВремяДанныхНаАндройде);
                        }
                    }
                }
            }
            // TODO: 08.04.2024 start get


            // TODO: 05.04.2024  после обработки обоих методов post and get
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return concurrentSkipListSetResultatOtServerIsertOrUpdate.stream().mapToLong(l->l)  .reduce(0, Long::sum);
    }

// TODO: 07.04.2024

    ///----------- ТУТ КОД УЖЕ ПОСЫЛАНИЕ ДАННЫХ НА СЕРВЕР МЕТОДУ POST (данные андройда посылаються на сервер)


    /////todo POST МЕТОД КОГДА НА АНДРОЙДЕ ВЕРСИЯ ДАННЫХ ВЫШЕ ЧЕМ НА СЕРВРЕР И МЫ  JSON ФАЙЛ ТУДА МЕТОД POST
    Long МетодПосылаемДанныеНаСервервФоне(@NonNull String ИмяТаблицы,
                                          @NonNull Long ВерсииНаАндройдеСерверная) {

        Long РезультатСинхронизации=0l;
        try {
            // TODO: 15.02.2022  ДАННЫЕ ДЛЯ ОТПРАВКИ НА СЕРВЕР
            Cursor cursorForSendServer= методГлавныйGetDataForAsync(ИмяТаблицы ,ВерсииНаАндройдеСерверная ,PublicID);
            /////TODO результаты   количество отправляемой информации на сервера
            if (cursorForSendServer!=null && cursorForSendServer.getCount() > 0) {
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " cursorForSendServer "+cursorForSendServer.getCount() );

                //////// todo упаковываем в  json ПЕРЕХОДИМ НА СЛЕДУЩИМ МЕТОД для отрправки на сервер метод POST() POST() POST() POST() POST() POST()POST()
                РезультатСинхронизации = МетодГенерацииJSON(cursorForSendServer, ИмяТаблицы );
                // TODO: 04.08.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " РезультатСинхронизации "+РезультатСинхронизации );

            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " РезультатСинхронизации "+РезультатСинхронизации );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  РезультатСинхронизации;
    }


// TODO: 07.04.2024

    //TODO СЛЕДУЮЩИЙ ЭТАМ РАБОТЫ ОПРЕДЕЛЯЕМ ЧТО МЫ ДЕЛАЕМ ПОЛУЧАЕМ ДАННЫЕ С СЕВРЕРА
    Long МетодДанныеПолучаемНаСервервФоне(@NonNull String ИмяТаблицы,
                                          @NonNull Long  ВерсииНаАндройдеСерверная,
                                          @NonNull Integer PublicID) {
        // TODO: 05.04.2024 get ()
        Long         ДанныесСервера = 0l;
        try {
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" ИмяТаблицы  "+ИмяТаблицы
                    + "\n"+" ВерсииНаАндройдеСерверная  "+ВерсииНаАндройдеСерверная
                    + "\n"+" PublicID  "+PublicID+"\n");


            // TODO: 05.04.2024   GET()-> получаем данные с сервера
            ДанныесСервера = МетодОбменаЗаданиеСервера_сервераПолучаем_Сервер(ВерсииНаАндройдеСерверная, ИмяТаблицы, PublicID);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" ДанныесСервера  "+ДанныесСервера+ "\n"+" ИмяТаблицы  "+ИмяТаблицы
                    +"\n"+" ВерсииНаАндройдеСерверная " +ВерсииНаАндройдеСерверная);




            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" ИмяТаблицы  "+ИмяТаблицы);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  ДанныесСервера;
    }
// TODO: 07.04.2024


    // TODO: 07.09.2023 После Успешного ПОлучение и Успешной Отправки Выравниваем Версию  Данных AFTER

    private void методПослеУспешногоПолученияПовышаемВерсию(@NonNull String ИмяТаблицыОтАндройда_Локальноая) {
        try{
            // TODO: 19.11.2022 ПОДНИМАЕМ ВЕРИСЮ ДАННЫХ
            Integer РезультатПовышенииВерсииДанных =
                    new SubClassUpVersionDATA().upVersionMODIFITATION_ClientRemote(ИмяТаблицыОтАндройда_Локальноая,context);


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " ВерсияДанныхсСамогоSqlServer  "+
                    " РезультатПовышенииВерсииДанных  " + РезультатПовышенииВерсииДанных);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
// TODO: 07.04.2024

    @NonNull
    private Long МетодОбменаЗаданиеСервера_сервераПолучаем_Сервер(@NonNull  Long ВерсияДанных,
                                                                  @NonNull String ИмяТаблицы,
                                                                  @NonNull  Integer ID) {
        Long  РезультатДанныесСервера=0l;
        try{
            Log.d(this.getClass().getName(), " ВерсияДанных" + ВерсияДанных+" ID "   + ID + "ИмяТаблицы"  + ИмяТаблицы);
            //////////TODO МЕТОД get
            РезультатДанныесСервера =
                    МетодПолучаемДаннныесСервера(ИмяТаблицы,
                            ID,
                            ВерсияДанных );

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" РезультатДанныесСервера  "+РезультатДанныесСервера);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return РезультатДанныесСервера;
    }
// TODO: 07.04.2024






    // TODO: 19.08.2021   КОнец Класс ВЫЧИСЛЯЕТ ЕЩЕ НЕ ОТРРВЛЕННЫЕ СООБЩЕНИЯ НА СЕРВЕР ИЗ ЧАТА
    /////МЕТОД КОГДА НА СЕРВЕРЕ ВЕРСИЯ ДАННЫХ ВЫШЕ И МЫ ПОЛУЧАЕМ ДАННЫЕ С СЕРВРА
    @SuppressLint("SuspiciousIndentation")
    Long МетодПолучаемДаннныесСервера( @NonNull String ИмяТаблицы,
                                       @NonNull Integer ID
            , @NonNull Long  ВерсияДанных) {

        Long РезультатФоновнойСинхронизации=0l;
        try {
            // TODO: 02.04.2024  Адресс и Порт Сервера Jboss
            // TODO: 02.04.2024  Адресс и Порт Сервера Jboss
            String   ИмяСерверИзХранилица = getHiltPortJboss.values().stream().map(m->String.valueOf(m)).findFirst().get();
            Integer    ПортСерверИзХранилица = getHiltPortJboss.keySet().stream().mapToInt(m->m).findFirst().getAsInt();


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ИмяСерверИзХранилица " + ИмяСерверИзХранилица+
                    " ПортСерверИзХранилица " +ПортСерверИзХранилица );
            // TODO: 10.11.2022  Получение JSON-потока
            InputStream BufferGetData =new Class_MODEL_synchronized(context). методGetByteFromServerAsync(
                    ИмяТаблицы,
                    "application/gzip",
                    "Хотим Получить  JSON"
                    ,ВерсияДанных,
                    ID,
                    ИмяСерверИзХранилица
                    ,ПортСерверИзХранилица,getsslSocketFactory2);
            // TODO: 01.12.2023

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " BufferGetData " +BufferGetData );

            if (BufferGetData!=null) {
                //////TODO запускаем метод распарстивая JSON
                РезультатФоновнойСинхронизации=        МетодПарсингJSONФайлаОтСервреравФоне(BufferGetData, ИмяТаблицы);
            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" РезультатФоновнойСинхронизации "+РезультатФоновнойСинхронизации+
                    " BufferGetData " +BufferGetData);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
            ////// начало запись в файл
        }
        return РезультатФоновнойСинхронизации;
    }

// TODO: 07.04.2024

    /////// TODO МЕТОД ПАСРИНГА ПРИШЕДШЕГО  С СЕРВЕРА ВНУТРИ ASYNSTASK В ФОНЕ
    @SuppressLint("SuspiciousIndentation")
    Long МетодПарсингJSONФайлаОтСервреравФоне(@NonNull  InputStream БуферGetByteJson,
                                              @NonNull  String имяТаблицаAsync) throws InterruptedException, JSONException {
        // TODO: 05.07.2023 result suync
        Long  РезультСинхрониазции=0l;
        try {
            Log.d(this.getClass().getName(), " имяТаблицаAsync " + имяТаблицаAsync + " БуферПолученныйJSON " +БуферGetByteJson.available()  );
            //TODO БУфер JSON от Сервера
            //  ObjectMapper jsonGenerator = new PUBLIC_CONTENT(context).getGeneratorJackson();

            final JsonParser jsonParser= jsonGenerator.createParser(БуферGetByteJson);
            JsonNode jsonNodeParentMAP= jsonParser.readValueAsTree();
            if (jsonNodeParentMAP!=null && jsonNodeParentMAP.size()>0) {
                Log.d(this.getClass().getName(),"\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName()
                        + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " jsonNodeParentMAP.size() " +jsonNodeParentMAP.size() );

                // TODO: 03.10.2023 все кроме байт
              РезультСинхрониазции=   методRowJsonRow(jsonNodeParentMAP,имяТаблицаAsync);

                Log.d(this.getClass().getName(),"\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName()
                        + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " БуферGetByteJson " +БуферGetByteJson );


            }
// TODO: 14.09.2023 exit
            БуферGetByteJson.close();

            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " РезультСинхрониазции " + РезультСинхрониазции);
            // TODO: 01.05.2023 clear
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return   РезультСинхрониазции ;
    }

// TODO: 07.04.2024


    // TODO: 13.09.2023   ROW
    Long методRowJsonRow(@NonNull  JsonNode jsonNodeParentMAP,
                         @NonNull String имяТаблицаAsync){
        Long РезультСинхрониазции=0l;
        try{
    
            int Проценты = 0;
            if (jsonNodeParentMAP.size()>0) {
                // TODO: 11.10.2022 callback
                Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabasemirrorbinary/" + имяТаблицаAsync + "");
                ContentResolver resolver = context.getContentResolver();
                Bundle bundle=new Bundle();
                bundle.putSerializable("jsonNodeParentMAP", (Serializable) jsonNodeParentMAP);
                bundle.putString("nametable",имяТаблицаAsync);

                // TODO: 08.04.2024  оправляем на выполения  полученый от сервера json

                Bundle bundleРезультатОбновлениеМассовой =resolver.call(uri,имяТаблицаAsync,new StringBuffer(имяТаблицаAsync).toString(),bundle);
                РезультСинхрониазции=bundleРезультатОбновлениеМассовой.getLong("completeasync",0l)   ;
            }
            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  РезультСинхрониазции;
    }




    // TODO: 07.04.2024



    ////////TODO     МЕТОД ГЕНЕРИРОУЕМ JSON ПОЛЯ НА ОСНОВАНИЕ НАШИХ ДАННЫХ ДЛЯ ПОСЛЕДЖУЮЩЕ ОТПРАВКИ  POST()->
    Long МетодГенерацииJSON(@NonNull  Cursor КурсорДляОтправкиДанныхНаСерверОтАндройда,
                            @NonNull String Таблицы) {
        Long ResultatSendingJsonJboss = 0l;
        try {
            if (КурсорДляОтправкиДанныхНаСерверОтАндройда!=null) {
                if (КурсорДляОтправкиДанныхНаСерверОтАндройда.getCount()>0) {
                    КурсорДляОтправкиДанныхНаСерверОтАндройда.moveToFirst();
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " КурсорДляОтправкиДанныхНаСерверОтАндройда "+КурсорДляОтправкиДанныхНаСерверОтАндройда.getCount() );

                    StringWriter stringWriterJSONAndroid=    new StringWriter();
                    //   ObjectMapper jsonGenerator = new PUBLIC_CONTENT(context).getGeneratorJackson();
                    SimpleModule module = new SimpleModule();
                    // TODO: 11.09.2023  какая текущапя таблица
                    if (Таблицы.equalsIgnoreCase("materials_databinary")
                            || Таблицы.equalsIgnoreCase("data_chat") ) {
                        module.addSerializer(Cursor.class, new GeneratorBinarySONSerializer(context));
                    } else {
                        module.addSerializer(Cursor.class, new GeneratorJSONSerializer(context));
                    }
                    jsonGenerator.registerModule(module);
                    jsonGenerator.getFactory().createGenerator( stringWriterJSONAndroid ).useDefaultPrettyPrinter();
                    byte[] BufferJsonForSendServer=  jsonGenerator.writeValueAsBytes(КурсорДляОтправкиДанныхНаСерверОтАндройда);
                    // TODO: 23.03.2023 ID ПРОФЕСИИ
                    КурсорДляОтправкиДанныхНаСерверОтАндройда.close();
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " BufferJsonForSendServer"+BufferJsonForSendServer );



                    // TODO: 14.03.2023 ПОСЫЛАЕМ ДАННЫЕ СГЕНЕРИРОНГО JSON НА СЕРВЕР ---->SERVER
                    ResultatSendingJsonJboss = new SendJsonCompliteToJboss().sendingJsonCompliteToJboss(context,BufferJsonForSendServer,
                            Таблицы,getHiltPortJboss,PublicID,getsslSocketFactory2 );

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " РезультатОтветаОтСервреУспешнаяВставкаИлиОбновления " +ResultatSendingJsonJboss );
                }else{
                    Log.d(this.getClass().getName(), " НЕ т данных  "+"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " РезультатОтветаОтСервреУспешнаяВставкаИлиОбновления " +ResultatSendingJsonJboss +
                            " КурсорДляОтправкиДанныхНаСерверОтАндройда " +КурсорДляОтправкиДанныхНаСерверОтАндройда.getCount());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  ResultatSendingJsonJboss;
    }
// TODO: 07.04.2024






    // TODO: 15.02.2022 синхрогниазции таблиц
    @NonNull
    private Cursor методГлавныйGetDataForAsync( @NonNull  String Таблица,
                                                @NonNull Long ВерсияДанныхДляСравения,
                                                @NonNull Integer PublicId) {
        Cursor  cursor=null;
        try{
          //  ПубличныйIDДляФрагмента = new Class_Generations_PUBLIC_CURRENT_ID().getPublicIDAllApp(context);

            Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabaseonlyasync/" + Таблица.trim() + "");
            ContentResolver resolver = context.getContentResolver();
            Bundle data=null;

            switch (Таблица.trim()) {
                // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ____ID    // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ____ID    // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ____ID
                case "settings_tabels":
                    data=new Bundle();
                    data.putString("query","" +
                            "  SELECT DISTINCT  * FROM settings_tabels   as gett  " +
                            " WHERE   gett.current_table >   "+ВерсияДанныхДляСравения+" " +
                            " AND gett.user_update = "+PublicId+" "+";" );

                    Log.d(this.getClass().getName(), " Таблица Все остальные  _id " + Таблица);
                    break;
                case "data_notification":
                    data=new Bundle();
                    data.putString("query"," SELECT DISTINCT  * FROM " +Таблица+" as gett" +
                            " WHERE   gett.current_table >  "+ВерсияДанныхДляСравения+""+";"  );
                    Log.d(this.getClass().getName(), " Таблица Все остальные  _id " + Таблица);
                    break;
                // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID   // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID
                // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID // TODO: 23.03.2023 ТАБЛИЦЫ С ПОЛЕМ ID
                default:
                    data=new Bundle();
                    data.putString("query"," SELECT DISTINCT  * FROM " +Таблица+" as gett" +
                            " WHERE   gett.current_table >  "+ВерсияДанныхДляСравения+
                            " AND gett.user_update = "+PublicId + ""+";" );
                    break;
            }
            // TODO: 08.08.2023 ГЛАВНОЕ ПОЛУЧЕНИЕ ДАННЫХ  ДЛя ОТПРАВКИ НА СЕРВЕР
            // TODO: 16.05.2023
            if (data.size()>0) {
                cursor = resolver.query(uri,new String[]{"*"},data,null);// TODO: 13.10.2022 ,"Удаленная"
            }

            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    "cursor   " + cursor  + "  Таблица " +Таблица
                            + " data.size() " +data.size());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return cursor;
    }

// TODO: 07.04.2024





}
