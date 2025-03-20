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
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusAyns;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusPrograssBar;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.BroadcastRecievers.Bl.RegisterBroadcastForWorkManager;
import com.dsy.dsu.BusinessLogicAll.Class_Connections_Server;
import com.dsy.dsu.BusinessLogicAll.CreateFolderBinatySave.ClassCreateFolderBinatyMatrilal;
import com.dsy.dsu.BusinessLogicAll.CreateFolderBinatySave.ClassCreateFolderCommitPays1C;
import com.dsy.dsu.BusinessLogicAll.Errors.ClassCreateFileForError;
import com.dsy.dsu.BusinessLogicAll.GetConnectivityManagerAndroid;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)

@SuppressLint("Range")
public class CompleteRemoteSyncService {

    private Service_For_Remote_Async_Binary.LocalBinderAsync localBinderAsync;//TODO нова\
    private ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;//TODO нова

    private ServiceConnection connectionОбновлениеПО;
    private ServiceConnection connectionAsync;
    private SharedPreferences preferences;
    private String РежимЗапускаСинхронизации = new String();
    private String success_users;
    private String success_login;
    private String date_update;
    private  Boolean СтатусРаботыСервера;
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

    public void startingBindingAsyncJboss(  @NonNull  String getWhoLaunched,@NonNull Context context) {
        try {
            
            // TODO: 14.08.2023 вызов кода ПОльзовательский
            preferences =context. getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);

            РежимЗапускаСинхронизации = preferences.getString("РежимЗапускаСинхронизации","СамыйПервыйЗапускСинхронизации");

            // TODO: 22.01.2024

            Long    ФинальныйРезультатAsyncBackgroud = localBinderAsync.getService().metodStartingSync(context,getWhoLaunched);

            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" + " ФинальныйРезультатAsyncBackgroud " +ФинальныйРезультатAsyncBackgroud);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    public void startServiceUpdatePO(@NonNull SSLSocketFactory getsslSocketFactory2,
                                     @NonNull String landingMode ,
                                  @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                                        @NonNull  String getWhoLaunched,
                                     @NonNull Context context) {
        try {
            // TODO: 14.08.2023 вызов кода ПОльзовательский
            preferences =context. getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            РежимЗапускаСинхронизации = preferences.getString("РежимЗапускаСинхронизации","СамыйПервыйЗапускСинхронизации");
            // TODO: 22.01.2024
            // TODO: 23.01.2024 stating .... Main Code
            WorkerUpdatePOAndAsync(   getHiltPortJboss,  getsslSocketFactory2,landingMode,getWhoLaunched);


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



    public void startServiceUpdatePOAndAsync(@NonNull SSLSocketFactory getsslSocketFactory2,
                                     @NonNull String  landingMode  ,
                                     @NonNull LinkedHashMap<Integer,String> getHiltPortJboss
                                     ,@NonNull  String getWhoLaunched,
                                       @NonNull Context context) {
        try {
            // TODO: 14.08.2023 вызов кода ПОльзовательский
            preferences =context. getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            РежимЗапускаСинхронизации = preferences.getString("РежимЗапускаСинхронизации","СамыйПервыйЗапускСинхронизации");
            // TODO: 22.01.2024

            // TODO: 23.01.2024 stating .... Main Code
            WorkerUpdatePOAndAsync(   getHiltPortJboss,  getsslSocketFactory2,landingMode,getWhoLaunched);




            getCloseingBindingUpdate();

            getCloseingBindingAsync();

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






    private void WorkerUpdatePOAndAsync( @NonNull LinkedHashMap<Integer,
            String> getHiltPortJboss,@NonNull      SSLSocketFactory getsslSocketFactory2,
                                         @NonNull String landingMode,
                                         @NonNull  String getWhoLaunched) {

        try{
            // TODO: 22.01.2024  создание журнала ошщибок
            методCreateJornalErrorApp();

            // TODO: 14.08.2023 создаем папку для BinaryFile Save
            new ClassCreateFolderBinatyMatrilal(context).МетодCreateFoldersBinaty();

            // TODO: 14.08.2023 создаем папку для BinaryFile CommitPay1C Соласования
            new ClassCreateFolderCommitPays1C(context).МетодCreateFoldersBinaty();

            // TODO: 14.08.2023 создаем папку для Обновления ПО
            new ClassCreateFolderUpdatePO (context).МетодCreateFoldersBinaty();



            // TODO: 14.08.2023  Запускаем Код До Сиинхрониазщции
            Integer     ФиналПолучаемРазницуМеждуДатами=   МетодОпределениеКогдаПоследнийРазЗаходилПользователь();

            СтатусРаботыСервера=  МетодПингаКСереруЗапущенЛиСерерИлиНет(   getHiltPortJboss,getsslSocketFactory2);

            // TODO: 22.01.2024  если false значит сервер выключен

            // TODO: 26.12.2022  конец основгого кода
            Log.d(context.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                    + "\n"+ " СтатусРаботыСервера " +СтатусРаботыСервера);

            // TODO: 22.01.2024 сеть включена
            metodDontNetwork();
            // TODO: 22.01.2024 сеть выключена
            metodSucceessNetwork();


            if (СтатусРаботыСервера) {
                // TODO: 01.04.2024  запускаем саму синхрониазцию через запуск Work Manamger
                metodВыполняетсяГлавнаяWork(ФиналПолучаемРазницуМеждуДатами,    getHiltPortJboss ,  landingMode,getWhoLaunched);//todo Main Code Sercice
            }else {

                getSendDOntWorkDashBornElseSucceessAyntifica(ФиналПолучаемРазницуМеждуДатами);

            }

            // TODO: 26.12.2022  конец основгого кода
            Log.d(context.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " РежимЗапускаСинхронизации " +РежимЗапускаСинхронизации+
                    "  ФиналПолучаемРазницуМеждуДатами " +ФиналПолучаемРазницуМеждуДатами);


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

        }

    }







    private void getSendDOntWorkDashBornElseSucceessAyntifica(@NonNull   Integer     ФиналПолучаемРазницуМеждуДатами) {
        try {
            // TODO: 23.01.2024
            if (      date_update != null && success_users != null && success_login != null
                    && ФиналПолучаемРазницуМеждуДатами < permissibledaysofwork  ) {

                // TODO: 01.04.2024
                new GetEndingAsyn().   metoEndingAsynsDashboard(context,localBinderОбновлениеПО);
            }else {

                // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                startFirstApp();

                Log.d(this.getClass().getName(), "  ФиналПолучаемРазницуМеждуДатами  " + ФиналПолучаемРазницуМеждуДатами
                        + " date_update " + date_update + " СтатусРаботыСервера " + СтатусРаботыСервера +"\n"+
                         " permissibledaysofwork " +permissibledaysofwork);

            }

            // TODO: 26.12.2022  конец основгого кода
            Log.d(context.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());

        }


    }




















    private void методCreateJornalErrorApp() {
        try{

            // TODO: 07.10.2023  create file for ERROR
            ClassCreateFileForError classCreateFileForError=new ClassCreateFileForError();

            classCreateFileForError.metodCreateFileForError(context);


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


    private void metodВыполняетсяГлавнаяWork(@NonNull Integer ФиналПолучаемРазницуМеждуДатами,
                                             @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                                             @NonNull String landingMode,@NonNull  String getWhoLaunched) {
        try{
        if (      date_update != null && success_users != null && success_login != null
                && ФиналПолучаемРазницуМеждуДатами < 40 ) {

            // TODO: 22.01.2024  запускаеми службу обновление ПО
            new SuccessAsynsStartingUpdatrPO().startingAsyncForUpSoft(   getHiltPortJboss,landingMode,getWhoLaunched);


            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " localBinderОбновлениеПО.isBinderAlive() " + localBinderОбновлениеПО.isBinderAlive()+
                    " ФиналПолучаемРазницуМеждуДатами " +ФиналПолучаемРазницуМеждуДатами);

        }else {

            // TODO: 28.04.2023 НЕт Анутифтикации Пароль
            // TODO: 28.04.2023 НЕт Анутифтикации Пароль
            startFirstApp();

            Log.d(this.getClass().getName(), "  ФиналПолучаемРазницуМеждуДатами  " + ФиналПолучаемРазницуМеждуДатами
                    + " date_update " + date_update + " СтатусРаботыСервера " + СтатусРаботыСервера);

        }

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }




    private void startFirstApp() {
        class SendUserFirstApp extends      SendMainActivity{

            public SendUserFirstApp(Context context) {
                super(context);
            }

            @Override
            public void startSendBroadSesiver() {
                //super.startSendBroadSesiver();
                intentComunications.setAction("Broad_messageAsyncOrUpdateAsync");
                bundleComunications.putString("Статус",  "Первый запуск  !!!!");///"В процесс"
                bundleComunications.putString("Действие",   "Первый запуск  !!!!");///"В процесс"
                intentComunications.putExtras(bundleComunications);

                // TODO: 23.01.2024 SEND event bus
                EventBus.getDefault().post(new MessageEvensBusAyns(intentComunications));

            }
        }
        // TODO: 22.01.2024 когда режим офлайн
        new SendUserFirstApp(context).startSendBroadSesiver();
    }
    
    
    
    
    
    private void metodDontNetwork() {
        class SendUserСерверВыключен extends      SendMainActivity{

            public SendUserСерверВыключен(Context context) {
                super(context);
            }

            @Override
            public void startSendBroadSesiver() {
                // super.startSendBroadSesiver();
                if (СтатусРаботыСервера==false) {
                    intentComunications.setAction("Broad_messageAsyncOrUpdateAsync");
                    bundleComunications.putString("Статус",  "Сервер выкл.!!!");///"В процесс"
                    bundleComunications.putString("Действие",  "Сервер выкл.!!!");///"В процесс"
                    intentComunications.putExtras(bundleComunications);

                    EventBus.getDefault().post(new MessageEvensBusAyns(intentComunications));

                    // TODO: 22.01.2024 просто сеть рабоатет  переделаем програсс бару
                }
                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            }
        }
        // TODO: 22.01.2024 когда режим офлайн
        new SendUserСерверВыключен(context).startSendBroadSesiver();
    }





    private void metodSucceessNetwork() {
        class SendUserСерверВыключен extends      SendMainActivity{

            public SendUserСерверВыключен(Context context) {
                super(context);
            }

            @Override
            public void startSendBroadSesiver() {
                // super.startSendBroadSesiver();
          
                    if (СтатусРаботыСервера==true) {
                        intentComunications.setAction("Broad_messageAsyncPrograssBar");
                        bundleComunications.putString("Статус",  "PrograssBarVisible");///"В процесс"
                        bundleComunications.putString("Действие",  "PrograssBarVisible");///"В процесс"
                        intentComunications.putExtras(bundleComunications);


                        // TODO: 25.09.2024 call back AN Screnn User Boot Activity
                       EventBus.getDefault().post(new MessageEvensBusPrograssBar(intentComunications));;
                        //EventBus.getDefault().postSticky(new MessageEvensBusPrograssBar(intentComunications));
                      //  MessageEvensBusPrograssBar messageEvensBusPrograssBar  =     new MessageEvensBusPrograssBar(intentComunications);
                        // TODO: 25.09.2024  запускаем слушателя обратного отоброжкнк Prograsbar
                        //new PrograssBarManagerBloadcastReciever().setBroadcastManagerPrograssBar(context,intentComunications);


                    }
                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            }
        }
        // TODO: 22.01.2024 когда режим офлайн
        new SendUserСерверВыключен(context).startSendBroadSesiver();
    }






















    //TODO succeess
    class SuccessAsynsStartingUpdatrPO{
        void  startingAsyncForUpSoft(   @NonNull LinkedHashMap<Integer,String> getHiltPortJboss ,@NonNull String landingMode,@NonNull  String getWhoLaunched ){
            try{
                // TODO: 22.01.2024 true запускаем Анализ По
                    Integer    СервернаяВерсия=        metodAsyncUpdatePO(getHiltPortJboss);
                    
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

                    case    "IntentServiceBootUpdatePo.comAndIntentServiceBootAsync.com" :

                        // TODO: 22.01.2024  запускаем Синхронизацию
                        launchUpdatePOandSync(СервернаяВерсия, ЛокальнаяВерсияПО, getWhoLaunched);

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " landingMode " +landingMode);
                        break;



                    case "IntentServiceBootUpdatePo.com" :
                        // TODO: 22.01.2024  запускаем обновдение ПО
                        launchOnlyUpdatePO(СервернаяВерсия, ЛокальнаяВерсияПО);

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

        private void launchOnlyUpdatePO(Integer СервернаяВерсия, Integer ЛокальнаяВерсияПО) {
            // TODO: 22.01.2024  запускаем обновдение ПО
            try{
            if (СервернаяВерсия > ЛокальнаяВерсияПО) {

                // TODO: 22.01.2024 запускаю обновление ПО
                youStartingUpdatePOComplete(СервернаяВерсия);
                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                // TODO: 22.01.2024  запускаем обновдение ПО
            }else {

                // TODO: 24.09.2024 Запускаем  НЕ Обновленеи ПО  версии одинаковые
                youhaveLatestVersionofUpdatePOComplete(СервернаяВерсия);
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






        private void launchUpdatePOandSync(Integer СервернаяВерсия, Integer ЛокальнаяВерсияПО,@NonNull  String getWhoLaunched) {
            // TODO: 22.01.2024  запускаем Синхронизацию
            try{
            if (СервернаяВерсия > ЛокальнаяВерсияПО) {

                // TODO: 24.09.2024 Запускаем Обновленеи ПО
                // TODO: 22.01.2024 запускаю обновление ПО
                youStartingUpdatePOComplete(СервернаяВерсия);
                // TODO: 03.10.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            }else {

                // TODO: 24.09.2024 запускаем Синхронизацию
                startingJbossAsyncComplete(  getWhoLaunched ,co );

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


        void startingJbossAsyncComplete(@NonNull  String getWhoLaunched ){
            try{
                // TODO: 03.10.2023
                // TODO: 22.12.2022  сама запуска синхронищации из workmanager ОБЩЕГО
                boolean ВыбранныйРежимСети =
                        new GetConnectivityManagerAndroid(context).сonnectivityManageruserselection();

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " lВыбранныйРежимСети" + ВыбранныйРежимСети);


                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " localBinderAsync "+"  ВыбранныйРежимСети "
                        + ВыбранныйРежимСети);


                if (ВыбранныйРежимСети == true  ) {

                    startingBindingAsyncJboss(getWhoLaunched);


                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            " ВыбранныйРежимСети "+" ВыбранныйРежимСети " + "getWhoLaunched " +getWhoLaunched) ;
                }


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






        private void youStartingUpdatePOComplete(@NonNull Integer СервернаяВерсия) {
            class SendUserProssecingUpdatePo extends      SendMainActivity{

                public SendUserProssecingUpdatePo(Context context) {
                    super(context);
                }

                public void startSendBroadSesiver( ) {
                    try{
                    //  super.startSendBroadSesiver();
                    intentComunications.setAction("Broad_messageAsyncOrUpdatePO");
                    bundleComunications.putString("Статус",  "Запускаем Обновление ПО !!!!");///"В процесс"
                    bundleComunications.putInt("СервернаяВерсия",  СервернаяВерсия);///"В процесс"
                    intentComunications.putExtras(bundleComunications);

                    EventBus.getDefault().post(new MessageEvensBusUpdatePO(intentComunications));

                    Log.d(context.getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());



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
            // TODO: 22.01.2024 когда режим офлайн
            new SendUserProssecingUpdatePo(context).startSendBroadSesiver();
        }
        private void youhaveLatestVersionofUpdatePOComplete(@NonNull Integer СервернаяВерсия) {
            class SendUserAfterVersionPO extends      SendMainActivity{

                public SendUserAfterVersionPO(Context context) {
                    super(context);
                }

                public void startSendBroadSesiver( ) {
                    try{

                    PackageInfo pInfo = context. getPackageManager().getPackageInfo(context. getPackageName(), 0);
                    String version = pInfo.versionName;//Version Name
                    Integer ЛокальнаяВерсияПО = pInfo.versionCode;//Version Code

                        if(СервернаяВерсия==0){
                            bundleComunications.putInt("СервернаяВерсия",  ЛокальнаяВерсияПО);///"В процесс"
                        }else {
                            bundleComunications.putInt("СервернаяВерсия",  СервернаяВерсия);///"В процесс"
                        }

                    intentComunications.setAction("Broad_messageAsyncOrUpdatePO");
                    bundleComunications.putString("Статус",   "У вас последная версия ПО !!!");///"В процесс"
                    bundleComunications.putString("Действие",   "У вас последная версия ПО !!!");///"В процесс"

                    intentComunications.putExtras(bundleComunications);

                    EventBus.getDefault().post(new MessageEvensBusUpdatePO(intentComunications));

                        Log.d(context.getClass().getName(), "\n"
                                + " время: " + new Date() + "\n+" +
                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());



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
            // TODO: 22.01.2024 когда режим офлайн
            new SendUserAfterVersionPO(context).startSendBroadSesiver();
        }






        private void metodПользовательЗаблокирован() {
            class SendUserПользовательЗаблокирован extends      SendMainActivity{

                public SendUserПользовательЗаблокирован(Context context) {
                    super(context);
                }

                @Override
                public void startSendBroadSesiver() {
                  //  super.startSendBroadSesiver();
                    intentComunications.setAction("Broad_messageAsyncOrUpdateAsync");
                    bundleComunications.putString("Статус",  "Вы заблокирован   !!!!");///"В процесс"
                    bundleComunications.putString("Действие",  "Вы заблокирован   !!!!");///"В процесс"
                    intentComunications.putExtras(bundleComunications);

                    EventBus.getDefault().post(new MessageEvensBusAyns(intentComunications));


                }
            }
            // TODO: 22.01.2024 когда режим офлайн
            new SendUserПользовательЗаблокирован(context).startSendBroadSesiver();
        }


    }//TODO  end SuccessAsynsStartingUpdatrPO














    // TODO: 19.01.2024   /////// МЕТОД КОГДА ЗАХОДИЛ ПОСЛЬДНИЙ РАЗ ПОЛЬЗОВАТЛЬ


    public Integer МетодОпределениеКогдаПоследнийРазЗаходилПользователь() {
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
    Integer metodAsyncUpdatePO(@NonNull LinkedHashMap<Integer,String> getHiltPortJboss) {
        Integer СервернаяВерсия=0;
        try {
            СервернаяВерсия = localBinderОбновлениеПО.getService().МетодГлавныйОбновленияПОДоAsync(true,
                    context,getHiltPortJboss );

            Log.i(this.getClass().getName(), " Атоманически установкаОбновление ПО " +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
            Log.i(this.getClass().getName(), "R.id.item_async_updatepo  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString() +
                    "СервернаяВерсия " + СервернаяВерсия);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return  СервернаяВерсия;
    }




    @SuppressLint("SuspiciousIndentation")
    public Boolean МетодПингаКСереруЗапущенЛиСерерИлиНет(    @NonNull LinkedHashMap<Integer,String> getHiltPortJboss
            ,@NonNull SSLSocketFactory getsslSocketFactory2 ) {
        try {
            // TODO: 16.12.2021 НЕПОСРЕДСТВЕННЫЙ ПИНГ СИСТЕНМ ИНТРЕНАТ НА НАЛИЧЕНИ СВАЗИ С БАЗОЙ SQL SERVER
            СтатусРаботыСервера =
                    new Class_Connections_Server(). pingServerJbossSuccessfulOrNot(context,getsslSocketFactory2);

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
    public void getCloseingBindingUpdate( ) {
        try {

            if(localBinderОбновлениеПО!=null){
                if (localBinderОбновлениеПО.isBinderAlive()) {
                    connectionОбновлениеПО.onNullBinding(null);
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
    public void getCloseingBindingAsync( ) {
        try {
            if(localBinderAsync!=null){
                if (localBinderAsync.isBinderAlive()) {
                    connectionAsync.onNullBinding(null);;
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