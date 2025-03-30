package com.dsy.dsu.BootAndAsync.BlBootAsync;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusNetworkStatuses;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.BroadcastRecievers.Bl.RegisterBroadcastForWorkManager;
import com.dsy.dsu.BusinessLogicAll.Class_Connections_Server;
import com.dsy.dsu.Dashboard.Model.endingasynsdashboard.GetEndingAsyn;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.dsy.dsu.Services.Service_For_Remote_Async_Binary;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;

@Module
@InstallIn(SingletonComponent.class)
@SuppressLint("Range")
public class CompleteRemoteSyncService {

    public   Service_For_Remote_Async_Binary.LocalBinderAsync localBinderAsync;//TODO нова\
    public    ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;//TODO нова

    private ServiceConnection connectionОбновлениеПО;
    private ServiceConnection connectionAsync;
    private SharedPreferences preferences;
    private String РежимЗапускаСинхронизации = new String();
    private String success_users;
    private String success_login;
    private String date_update;

    @Inject
    RegisterBroadcastForWorkManager registerBroadcastForWorkManager;
    private  Integer permissibledaysofwork=240;

    public  @Inject CompleteRemoteSyncService(@ApplicationContext Context contextBounding) {
        //TODO сомо имя json
        try{
        // TODO: 14.08.2023 методЗапукска Синхрониазйиии
           МетодБиндингаОбновлениеПО(  contextBounding);

            // TODO: 14.08.2023 методЗапукска Синхрониазйиии
            МетодБиндингаRemoteAsync(contextBounding);

        Log.d(contextBounding.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(contextBounding).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }





    public void lanchUpdatePO(@NonNull SSLSocketFactory getsslSocketFactory2,
                              @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                              @NonNull Context context) {
        try {
            // TODO: 14.08.2023 вызов кода ПОльзовательский




            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void lanchAsync(@NonNull SSLSocketFactory getsslSocketFactory2,
                           @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                           @NonNull Context context) {
        try {
            // TODO: 14.08.2023 вызов кода ПОльзовательский
            preferences =context. getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            РежимЗапускаСинхронизации = preferences.getString("РежимЗапускаСинхронизации","СамыйПервыйЗапускСинхронизации");
            // TODO: 22.01.2024
            // TODO: 23.01.2024 stating .... Main Code
            WorkerUpdatePOAndAsync(   getHiltPortJboss,  getsslSocketFactory2,landingMode,getWhoLaunched,  context);


            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void lanchUpdatePOAndAsync(@NonNull SSLSocketFactory getsslSocketFactory2,
                                      @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                                      @NonNull Context context) {
        try {
            // TODO: 14.08.2023 Проверяем версси ПО с серврной и локальной
           Maybe.fromCallable(()->{

               Log.d(context.getClass().getName(), "\n"
                       + " время: " + new Date() + "\n+" +
                       " Класс в процессе... " + this.getClass().getName() + "\n" +
                       " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
                return null;
            }).doOnSuccess(succes->{
               // TODO: 30.03.2025 Запускаем ПО
               Log.d(context.getClass().getName(), "\n"
                       + " время: " + new Date() + "\n+" +
                       " Класс в процессе... " + this.getClass().getName() + "\n" +
                       " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
            }).doOnComplete(()->{
               // TODO: 30.03.2025 Запускаем СИНХРОНИЗАЦИЮ
               Log.d(context.getClass().getName(), "\n"
                       + " время: " + new Date() + "\n+" +
                       " Класс в процессе... " + this.getClass().getName() + "\n" +
                       " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");

            }).onErrorResumeWith(Maybe.empty()).blockingSubscribe();


            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }











    private Boolean userHasReceivedAccesstoDashBordorPasswordisNeeded(  @NonNull Context context) {
        Boolean userHasReceivedAccesstoDashBordorPasswordisNeeded=false;
        try {

            // TODO: 14.08.2023  Запускаем Код До Сиинхрониазщции
            Integer     ФиналПолучаемРазницуМеждуДатами=   МетодОпределениеКогдаПоследнийРазЗаходилПользователь( context);
            // TODO: 23.01.2024
            if (      date_update != null && success_users != null && success_login != null
                    && ФиналПолучаемРазницуМеждуДатами < permissibledaysofwork  ) {
                // TODO: 28.03.2025
                userHasReceivedAccesstoDashBordorPasswordisNeeded=true;

            }


        // TODO: 28.04.2023 НЕт Анутифтикации Пароль
        Log.d(this.getClass().getName(), "  ФиналПолучаемРазницуМеждуДатами  " + ФиналПолучаемРазницуМеждуДатами
                + " date_update " + date_update +
                " permissibledaysofwork " +permissibledaysofwork + " ФиналПолучаемРазницуМеждуДатами " +ФиналПолучаемРазницуМеждуДатами+
                " userHasReceivedAccesstoDashBordorPasswordisNeeded " +userHasReceivedAccesstoDashBordorPasswordisNeeded);

    } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());

        }
        return  userHasReceivedAccesstoDashBordorPasswordisNeeded;


    }




    private void weswitchtothedesiredactivityaftersynchronization(  @NonNull Context context
            ,@NonNull  Boolean userHasReceivedAccesstoDashBordorPasswordisNeeded,
                                                                    @NonNull Integer ЛокальнаяВерсияПО,@NonNull Integer СервернаяВерсия) {

        try {

            if (ЛокальнаяВерсияПО>=СервернаяВерсия) {
                if (userHasReceivedAccesstoDashBordorPasswordisNeeded){
                    // TODO: 01.04.2024 Все в порядке ЗАпускам Саму Программу DashBord
                    new GetEndingAsyn().forvardDashboard(context,localBinderОбновлениеПО);

                }else {

                    // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                    // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                    new GetEndingAsyn().forvardActivityPassword(  context);

                }
            }
            // TODO: 28.04.2023
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " userHasReceivedAccesstoDashBordorPasswordisNeeded " +userHasReceivedAccesstoDashBordorPasswordisNeeded+
                     "ЛокальнаяВерсияПО " +ЛокальнаяВерсияПО  + " СервернаяВерсия " +СервернаяВерсия );
    } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());

        }

    }


















    private void metodВыполняетсяГлавнаяWork(@NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                                             @NonNull String landingMode,
                                             @NonNull  String getWhoLaunched,
                                             @NonNull Context context) {
        try{

            // TODO: 22.01.2024  запускаеми службу обновление ПО
            new SuccessAsynsStartingUpdatrPO().startingAsyncForUpSoft(   getHiltPortJboss,landingMode,getWhoLaunched, context);

            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " localBinderОбновлениеПО.isBinderAlive() " + localBinderОбновлениеПО.isBinderAlive() +"\n" + " permissibledaysofwork " +permissibledaysofwork);



    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }





    
    
    
    
    
    public void getSucceessNetwork(@NonNull Context context) {
        try{
            Intent intentComunicationsBusAyns=new Intent();
            Bundle bundleComunications=new Bundle();


                    intentComunicationsBusAyns.setAction("EventBusAnsyc");
                    bundleComunications.putString("Статус",  "ServerJbosOn");///"В процесс"
                    intentComunicationsBusAyns.putExtras(bundleComunications);
                    // TODO: 25.09.2024 call back AN Screnn User Boot Activity
                    EventBus.getDefault().post(new MessageEvensBusNetworkStatuses(intentComunicationsBusAyns));


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


    public void getDontNetwork(@NonNull Context context) {
        try{
            Intent intentComunicationsBusAyns=new Intent();
            Bundle bundleComunications=new Bundle();


                intentComunicationsBusAyns.setAction("EventBusAnsyc");
                bundleComunications.putString("Статус",  "ServerJbosOff");///"В процесс"
                intentComunicationsBusAyns.putExtras(bundleComunications);

                EventBus.getDefault().post(new MessageEvensBusNetworkStatuses(intentComunicationsBusAyns));
                // TODO: 22.01.2024 просто сеть рабоатет  переделаем програсс бару

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




    public void getLastVersionUpdatePO(@NonNull Context context) {
        try{
            Intent intentComunicationsBusAyns=new Intent();
            Bundle bundleComunications=new Bundle();


            intentComunicationsBusAyns.setAction("EventBusAnsyc");
            bundleComunications.putString("Статус",  "LastVersionUpdatePO");///"В процесс"
            intentComunicationsBusAyns.putExtras(bundleComunications);

            EventBus.getDefault().post(new MessageEvensBusNetworkStatuses(intentComunicationsBusAyns));
            // TODO: 22.01.2024 просто сеть рабоатет  переделаем програсс бару

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    public void getUpdateProcessorPO(@NonNull Context context) {
        try{
            Intent intentComunicationsBusAyns=new Intent();
            Bundle bundleComunications=new Bundle();


            intentComunicationsBusAyns.setAction("EventBusAnsyc");
            bundleComunications.putString("Статус",  "UpdateProcessorPO");///"В процесс"
            intentComunicationsBusAyns.putExtras(bundleComunications);

            EventBus.getDefault().post(new MessageEvensBusNetworkStatuses(intentComunicationsBusAyns));
            // TODO: 22.01.2024 просто сеть рабоатет  переделаем програсс бару

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }





















    //TODO succeess
    class SuccessAsynsStartingUpdatrPO{
        void  startingAsyncForUpSoft(   @NonNull LinkedHashMap<Integer,String> getHiltPortJboss ,@NonNull String landingMode,
                                        @NonNull  String getWhoLaunched ,@NonNull Context context){
            try{
                // TODO: 22.01.2024 true запускаем Анализ По
                    Integer    СервернаяВерсия=        completeUpdatePO(getHiltPortJboss,context,getWhoLaunched);
                    
                // TODO: 24.09.2024   Локальная Версия Программернр Обеспечения табель
                PackageInfo    pInfo = context. getPackageManager().getPackageInfo(context. getPackageName(), 0);
                String version = pInfo.versionName;//Version Name
                Integer ЛокальнаяВерсияПО = pInfo.versionCode;
                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " СервернаяВерсия "+СервернаяВерсия +  "landingMode " +landingMode);


                // TODO: 26.12.2024 режим Обновление ПО или ВМЕСТЕ
                switch (landingMode.trim()){

                    case    "lanchUpdatePOAndAsync" :
                        // TODO: 22.01.2024  запускаем Синхронизацию
                        launchUpdatePOandSync(СервернаяВерсия, ЛокальнаяВерсияПО, getWhoLaunched,context,landingMode);

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " landingMode " +landingMode);
                        break;



                    case "lanchUpdatePO" :
                        // TODO: 22.01.2024  запускаем обновдение ПО
                        launchOnlyUpdatePO(СервернаяВерсия, ЛокальнаяВерсияПО,  context,getWhoLaunched);

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " landingMode " +landingMode);

                        break;


                    // TODO: 26.12.2024 Синхрониазция
                    case "lanchAsync" :

                        // TODO: 24.09.2024 запускаем Синхронизацию
                        completeAsync(  getWhoLaunched ,context);

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " landingMode " +landingMode);

                        break;


                }

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " localBinderAsync "+ "\n" +
                        " landingMode " +landingMode);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                        + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(context).recordnewerror(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }

        private void launchOnlyUpdatePO(Integer СервернаяВерсия, Integer ЛокальнаяВерсияПО,@NonNull Context context,@NonNull String getWhoLaunched) {
            // TODO: 22.01.2024  запускаем обновдение ПО
            try{


                // TODO: 22.01.2024   запускаем  Обновление ПО

            if (СервернаяВерсия > ЛокальнаяВерсияПО) {

                // TODO: 22.01.2024 запускаю обновление ПО
                StartingUpdatePOComplete(СервернаяВерсия,context,getWhoLaunched);



                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );






                // TODO: 22.01.2024 версии равны Update PO ничего не запускаем
            }else {
                // TODO: 24.09.2024 Запускаем  НЕ Обновленеи ПО  версии одинаковые
                DontUpdatePOComplete(СервернаяВерсия,context,getWhoLaunched);
                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " СервернаяВерсия "+ "\n"+" СервернаяВерсия " +"ЛокальнаяВерсияПО "+ЛокальнаяВерсияПО );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        }






        private void launchUpdatePOandSync(Integer СервернаяВерсия, Integer ЛокальнаяВерсияПО,
                                           @NonNull  String getWhoLaunched,@NonNull Context context,@NonNull String landingMode) {
            // TODO: 22.01.2024  запускаем Синхронизацию
            try{
            if (СервернаяВерсия > ЛокальнаяВерсияПО) {
                // TODO: 24.09.2024 Запускаем Обновленеи ПО
                // TODO: 22.01.2024 запускаю обновление ПО
                StartingUpdatePOComplete(СервернаяВерсия,context,getWhoLaunched);
                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            }else {

                // TODO: 24.09.2024 запускаем Синхронизацию
                completeAsync(  getWhoLaunched ,context );

                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        }









        private void StartingUpdatePOComplete(@NonNull Integer СервернаяВерсия, @NonNull Context context,@NonNull String getWhoLaunched) {
                     try{
                         Intent intentComunicationsUpdatePO=new Intent();
                         Bundle bundleComunications=new Bundle();

                         intentComunicationsUpdatePO.setAction("EventBusUpdatePO");
                         bundleComunications.putString("Статус",  "UpdateProcessorPO");///"В процесс"
                         bundleComunications.putInt("СервернаяВерсия",  СервернаяВерсия);///"В процесс"
                         bundleComunications.putBinder("callbackbinderdashbord",localBinderОбновлениеПО);
                         intentComunicationsUpdatePO.putExtras(bundleComunications);

                         EventBus.getDefault().post(new MessageEvensBusUpdatePO(intentComunicationsUpdatePO));

                         Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                 " localBinderAsync "+ "\n" );
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new RecordNewErros(context).recordnewerror(e.toString(),
                            this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
        }
        }




        // TODO: 20.03.2025  слушатель после завершения синхронизации и обновление по 
        private void DontUpdatePOComplete(@NonNull Integer СервернаяВерсия, @NonNull Context context,@NonNull String getWhoLaunched) {
            try{
                Intent intentComunicationsUpdatePO=new Intent();
                intentComunicationsUpdatePO.setAction("EventBusUpdatePO");
                // TODO: 20.03.2025
                Bundle bundleComunications=new Bundle();

                PackageInfo pInfo = context. getPackageManager().getPackageInfo(context. getPackageName(), 0);
                Integer ЛокальнаяВерсияПО = pInfo.versionCode;//Version Code

                if(СервернаяВерсия==0){
                    bundleComunications.putInt("СервернаяВерсия",  ЛокальнаяВерсияПО);///"В процесс"
                }else {
                    bundleComunications.putInt("СервернаяВерсия",  СервернаяВерсия);///"В процесс"
                }
                bundleComunications.putString("Статус",    "LastVersionUpdatePO");///"В процесс"
                intentComunicationsUpdatePO.putExtras(bundleComunications);

                // TODO: 20.03.2025
           new CallBackBusUpdatePO(context).callbackEvensBusUpdatePO(intentComunicationsUpdatePO);

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        }



    // TODO: 19.01.2024   /////// МЕТОД КОГДА ЗАХОДИЛ ПОСЛЬДНИЙ РАЗ ПОЛЬЗОВАТЛЬ


    public Integer МетодОпределениеКогдаПоследнийРазЗаходилПользователь(@NonNull Context context) {
        Cursor Курсор_7ДнейЗаходаПользователя = null;
        Integer  ФиналПолучаемРазницуМеждуДатами=0;
        try {
            Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabasecurrentoperations/" + "successlogin" + "");
            ContentResolver contentResolver=context. getContentResolver();
            Курсор_7ДнейЗаходаПользователя =      contentResolver.query(uri,new String[]{},
                    new String(" SELECT *  FROM    successlogin   ORDER BY id  LIMIT   1  "),
                    new String[]{},null);///   "  //// SELECT * FROM  viewtabel WHERE year_tabels=?  AND month_tabels=?  AND cfo=?  AND status_send!=?
            Log.d(this.getClass().getName(), "  Курсор_7ДнейЗаходаПользователя " +  Курсор_7ДнейЗаходаПользователя);


            if (Курсор_7ДнейЗаходаПользователя.getCount() > 0) {/////ПРОВЕРЯЕМ ЕСЛИ ПО ДАННОМУ ID UUID ЗАПОЛНЕ ЛИ ОН
                Курсор_7ДнейЗаходаПользователя.moveToFirst();
                success_users =
                        Курсор_7ДнейЗаходаПользователя.getString(Курсор_7ДнейЗаходаПользователя.getColumnIndex("success_users")).trim();
                success_login =
                        Курсор_7ДнейЗаходаПользователя.getString(Курсор_7ДнейЗаходаПользователя.getColumnIndex("success_login")).trim();
                date_update =
                        Курсор_7ДнейЗаходаПользователя.getString(Курсор_7ДнейЗаходаПользователя.getColumnIndex("date_update")).trim();

                Integer   ПолученныйПубличныйID= Курсор_7ДнейЗаходаПользователя.getInt(Курсор_7ДнейЗаходаПользователя.getColumnIndex("id"));

                Log.d(this.getClass().getName(), "  success_users  " + success_users + "  " +
                        "    success_login  " + success_login + " date_update " + date_update);

                // TODO: 13.08.2023 дата из табции
                Date ДатаSucceslogin =
                        new android.icu.text.SimpleDateFormat("yyyy-MM-dd",
                                new Locale("ru")).parse(date_update);//TODO "2023-08-01 19:00:59.781"

                Log.d(this.getClass().getName(), "  ДатаSucceslogin  " + ДатаSucceslogin);


                // TODO: 13.08.2023 Дата NOW !!!!!
                Date ДатаNOW = Calendar.getInstance().getTime();
                DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));//"yyyy-MM-dd'T'HH:mm:ss'Z'
                String ДатСегодняДатаNOW = dateFormat.format(ДатаNOW);
                ДатаNOW = dateFormat.parse(ДатСегодняДатаNOW);
                Log.d(this.getClass().getName(), "  ДатаNOW  " + ДатаNOW);


                ////TODO само сравнивание дат на 7 дней назад
                long РазницаМеждуДатамиNowИДатыИзБазы =
                        ДатаNOW.getTime()
                                - ДатаSucceslogin.getTime(); //локальное сравнение дата из базы андройда и дат сегодня
                ///////////
                ФиналПолучаемРазницуМеждуДатами = Integer.parseInt("" + (TimeUnit.DAYS.convert(РазницаМеждуДатамиNowИДатыИзБазы, TimeUnit.MILLISECONDS)));

                Log.d(this.getClass().getName(), "  ФиналПолучаемРазницуМеждуДатами  " + ФиналПолучаемРазницуМеждуДатами);

            }
            // TODO: 13.08.2023
            if (Курсор_7ДнейЗаходаПользователя != null) {
                Курсор_7ДнейЗаходаПользователя.close();///
            }
            Log.d(this.getClass().getName(), "  ФиналПолучаемРазницуМеждуДатами  " + ФиналПолучаемРазницуМеждуДатами);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    return  ФиналПолучаемРазницуМеждуДатами;

    }

    ///////todo ФИНАЛЬНЫЙ МЕТОД КТО ВХОДИЛ ДО 7 ДНЕЙ ИЛИ ПОСЫЛАЕМ НА АУНТИФИКАЦИЮ
    Integer completeUpdatePO(@NonNull LinkedHashMap<Integer,String> getHiltPortJboss, @NonNull Context context,@NonNull String getWhoLaunched) {
        Integer СервернаяВерсия=0;
        try {
            СервернаяВерсия = localBinderОбновлениеПО.getService().МетодГлавныйОбновленияПОДоAsync(true,
                    context,getHiltPortJboss );

            Log.i(this.getClass().getName(), " Атоманически установкаОбновление ПО " +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
            Log.i(this.getClass().getName(), "R.id.item_async_updatepo  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString() +
                    "СервернаяВерсия " + СервернаяВерсия+ " getWhoLaunched " +getWhoLaunched);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return  СервернаяВерсия;
    }





    Long completeAsync(@NonNull  String getWhoLaunched , @NonNull Context context){
        // TODO: 28.03.2025
        Long completeAsync=0l;
        try{
            // TODO: 03.10.2023

            completeAsync=  localBinderAsync.getService().metodStartingSync(context,getWhoLaunched);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " completeAsync " +completeAsync + " getWhoLaunched " +getWhoLaunched);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  completeAsync;
    }




























    @SuppressLint("SuspiciousIndentation")
    public Boolean МетодПингаКСереруЗапущенЛиСерерИлиНет( @NonNull SSLSocketFactory getsslSocketFactory2, @NonNull Context context) {
        Boolean СтатусРаботыСервера =false;
        try {
            // TODO: 16.12.2021 НЕПОСРЕДСТВЕННЫЙ ПИНГ СИСТЕНМ ИНТРЕНАТ НА НАЛИЧЕНИ СВАЗИ С БАЗОЙ SQL SERVER
              СтатусРаботыСервера = new Class_Connections_Server(). pingServerJbossSuccessfulOrNot(context,getsslSocketFactory2);

   Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +  "  СтатусРаботыСервера " +СтатусРаботыСервера);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return СтатусРаботыСервера;
    }


























































    @SuppressLint("NewApi")
    public void МетодБиндингаRemoteAsync(  @NonNull  Context contextBounding  ) {
        try {
            // TODO: 28.04.2023  запускаем Гланвную Синхрониазцию

                //  Intent intentОбноразоваяСинхронизациия = new Intent(context, Service_For_Remote_Async.class);
                Intent intentAsync = new Intent(contextBounding, Service_For_Remote_Async_Binary.class);
                intentAsync.setAction("com.StartingAsyncMainBackgroud");
                connectionAsync = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        try {
                            if (service.isBinderAlive()) {
                                // TODO: 29.09.2023
                                 localBinderAsync = (Service_For_Remote_Async_Binary.LocalBinderAsync) service;

                                // TODO: 25.03.2023
                                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                        + " localBinderAsync " +localBinderAsync);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(contextBounding).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }


                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        localBinderAsync = null;
                        Log.d(contextBounding.getClass().getName().toString(), "\n"
                                + "onServiceConnected  одноразовая  messengerActivity  ");
                    }

                    @Override
                    public void onBindingDied(ComponentName name) {
                        ServiceConnection.super.onBindingDied(name);
                    }

                    @Override
                    public void onNullBinding(ComponentName name) {
                        ServiceConnection.super.onNullBinding(name);
                    }
                };

            contextBounding. bindService(intentAsync ,connectionAsync ,Context.BIND_AUTO_CREATE);


            // TODO: 28.04.2023
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(contextBounding).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }

    }





    @SuppressLint("NewApi")
    public void МетодБиндингаОбновлениеПО( @NonNull  Context contextBounding ) {
        try {
            connectionОбновлениеПО = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    try {
                        if (service.isBinderAlive()) {

                            // TODO: 28.07.2023  Update
                            localBinderОбновлениеПО = (ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО) service;

                            // TODO: 25.03.2023
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                        }

                        Log.d(contextBounding.getClass().getName(), "\n"
                                + " время: " + new Date() + "\n+" +
                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"
                                + "localBinderОбновлениеПО " + localBinderОбновлениеПО);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new RecordNewErros(contextBounding).recordnewerror(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }


                @Override
                public void onServiceDisconnected(ComponentName name) {
                    try {
                        localBinderОбновлениеПО = null;
                        Log.i(contextBounding.getClass().getName(), "    onServiceDisconnected  binder.isBinderAlive()");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new RecordNewErros(contextBounding).recordnewerror(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            };
            Intent intentЗапускСлужбыОбновлениеПО = new Intent(contextBounding, ServiceUpdatePoОбновлениеПО.class);
            intentЗапускСлужбыОбновлениеПО.setAction("com.ServiceUpdatePoОбновлениеПО");

            contextBounding. bindService(intentЗапускСлужбыОбновлениеПО,connectionОбновлениеПО,Context.BIND_AUTO_CREATE  );
            // TODO: 28.04.2023
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(contextBounding).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }

    }





    // TODO: 29.09.2023  метод зарцска синхронизации ВИЗУАЛЬНОЙ




    @SuppressLint("NewApi")
    public void getCloseingBindingUpdate( @NonNull Context context) {
        try {

            if(localBinderОбновлениеПО!=null){
                if (localBinderОбновлениеПО.isBinderAlive()) {
                    localBinderОбновлениеПО.getService().onDestroy();
                }
            }

            // TODO: 28.04.2023
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }

    }

    @SuppressLint("NewApi")
    public void getCloseingBindingAsync( @NonNull Context context) {
        try {
            if(localBinderAsync!=null){
                if (localBinderAsync.isBinderAlive()) {
                    localBinderAsync.getService().onDestroy();
                }
            }
            // TODO: 28.04.2023
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }

    }







    // TODO: 19.01.2024 END CLASS
}