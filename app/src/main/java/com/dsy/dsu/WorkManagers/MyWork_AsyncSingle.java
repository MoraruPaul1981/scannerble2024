package com.dsy.dsu.WorkManagers;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dsy.dsu.BusinessLogicAll.GetConnectivityManagerAndroid;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.dsy.dsu.Services.Service_For_Remote_Async_Binary;
import com.dsy.dsu.WorkManagers.BL_WorkMangers.ListenableFutures;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SuppressLint("RestrictedApi")
public class MyWork_AsyncSingle extends Worker {
    protected String ИмяСлужбыWorkManger ="WorkManager Synchronizasiy_Data";
    protected  String ИмяСлужбыSingleWorkManger ="WorkManager Synchronizasiy_Data Disposable";

    protected  ServiceConnection serviceConnectionAsyns;

    protected Service_For_Remote_Async_Binary.LocalBinderAsync localBinderAsyncSingleWorkManager;

    protected String  uri;
    protected ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;//TODO новаЯ

    protected ServiceConnection   connectionОбновлениеПО;

    protected BLForWorkManagerSingle blForWorkManagerSingle;



    // TODO: 28.09.2022
    public MyWork_AsyncSingle(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        try{
            // TODO: 02.04.2024 Bl
            blForWorkManagerSingle=new BLForWorkManagerSingle();
            // TODO: 01.04.2024
            blForWorkManagerSingle.  МетодПодключениекСлубе();
            // TODO: 01.04.2024
            blForWorkManagerSingle.   МетодБиндингаОбновлениеПО();
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new   Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
        Log.e(context.getClass().getName(), " ОШИБКА В WORK MANAGER  MyWork_AsyncSingle из FaceApp в  MyWork_AsyncSingle Exception  ошибка в классе  MyWork_AsyncSingle" + e.toString());
    }
    }




    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    @NonNull
    @Override
    public Result doWork() {
        // TODO: 24.09.2024
        Data data=null;
        try {
              data = getInputData();
// TODO: 24.09.2024
        Boolean allowtheWorker=    data.getBoolean("IallowtheWorkertostart",false);
            // TODO: 24.09.2024
            if (allowtheWorker==true) {
                uri =     getInputData().getString("uri");
                // TODO: 02.04.2024  запуск и анализ вор манежера если не запущен другой work manager publuc
                Long ФинальныйРезультатAsyncBackgroudSingle= blForWorkManagerSingle.  staringAndAlalyzWorkMangerSingleObserver();

                // TODO: 07.04.2024 cancel

                // TODO: 02.04.2024  ответ от single worlmager
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.putIfAbsent("dataSingleWork", ФинальныйРезультатAsyncBackgroudSingle);

                data= new Data.Builder()
                        .putLong("ReturnSingleAsyncWork", ФинальныйРезультатAsyncBackgroudSingle)
                        .putString("uri", uri)
                        .putAll(objectMap)
                        .build();
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " uri " +uri + " allowtheWorker " +allowtheWorker);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 07.04.2024 cancel
            WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(ИмяСлужбыSingleWorkManger);
            // TODO: 01.04.2024  
            Result.failure(data);
        }


        return Result.success(data);
        
    }







    // TODO: 02.04.2024  бизнес логика for Singlw work manager





    public class BLForWorkManagerSingle   {


        // TODO: 02.04.2024


        public void МетодПодключениекСлубе() {
            try{
                Intent intentОбноразоваяСинхронизациия = new Intent(getApplicationContext(), Service_For_Remote_Async_Binary.class);
                serviceConnectionAsyns =         new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        try{
                            if (service.isBinderAlive()) {
                                localBinderAsyncSingleWorkManager = (Service_For_Remote_Async_Binary.LocalBinderAsync) service;
                                //TODO СИНХРОНИАЗЦИЯ ГЛАВНАЯ
                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                        + "МетодПодключениекСлубе service.isBinderAlive() "+service.isBinderAlive());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }
                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.d(getApplicationContext().getClass().getName().toString(), "\n"
                                + "onServiceConnected  одноразовая  messengerActivity  " );
                    }
                };
                // TODO: 01.04.2024


                getApplicationContext().  bindService(intentОбноразоваяСинхронизациия, serviceConnectionAsyns,Context.BIND_AUTO_CREATE);


                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());

            }
        }
        // TODO: 03.10.2023  метод когда не биндинга
        public void МетодБиндингаОбновлениеПО() {
            try {
                Boolean asBoolenОбновлениеПО = null;
                if (localBinderОбновлениеПО==null) {
                    connectionОбновлениеПО = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            try {
                                if (service.isBinderAlive()) {
                                    // TODO: 28.07.2023  Update
                                    localBinderОбновлениеПО = (ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО) service;
                                }

                                Log.d(getApplicationContext().getClass().getName(), "\n"
                                        + " время: " + new Date() + "\n+" +
                                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n"
                                        + "localBinderОбновлениеПО " + localBinderОбновлениеПО);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                        this.getClass().getName(),
                                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                        }


                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            try {
                                localBinderОбновлениеПО = null;
                                Log.i(getApplicationContext().getClass().getName(), "    onServiceDisconnected  binder.isBinderAlive()");
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                        this.getClass().getName(),
                                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                        }
                    };
                    Intent intentЗапускСлужбыОбновлениеПО = new Intent(getApplicationContext(), ServiceUpdatePoОбновлениеПО.class);
                    intentЗапускСлужбыОбновлениеПО.setAction("com.ServiceUpdatePoОбновлениеПО");
                    asBoolenОбновлениеПО =getApplicationContext(). bindService(intentЗапускСлужбыОбновлениеПО, connectionОбновлениеПО, Context.BIND_AUTO_CREATE);
                }

                // TODO: 28.04.2023
                Log.d(this.getClass().getName(), "\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName()
                        + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " asBoolenОбновлениеПО " + asBoolenОбновлениеПО);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
                Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
            }

        }

        // TODO: 24.09.2024  запуск синхорнизации из  Workmanager
        private Long staringAndAlalyzWorkMangerSingleObserver(  ) {
            // TODO: 24.09.2024  
            AtomicReference<Long> ФинальныйРезультатAsyncBackgroudSingle=new AtomicReference<>(0l);
            try{
                // TODO: 01.04.2024  запускаем Listertable
                WorkInfo.State statePublic = new ListenableFutures(getApplicationContext()).listenableFutureWorkManager(ИмяСлужбыWorkManger);
                // TODO: 26.12.2021
                if ( statePublic!= WorkInfo.State.RUNNING   ) {

                    // TODO: 01.04.2024  запускаем сихпонизацию общую
                    ФинальныйРезультатAsyncBackgroudSingle.set(МетодЗапускаОднаразовая());

                }

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " ФинальныйРезультатAsyncBackgroudSingle " + ФинальныйРезультатAsyncBackgroudSingle.get()+
                            " uri " +uri);
                // TODO: 07.10.2023  Claer Bound Service
                blForWorkManagerSingle.   getunbindService( );
                // TODO: 01.04.2024  выходим

                
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                        Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.04.2024

            }
            return  ФинальныйРезультатAsyncBackgroudSingle.get();
        }









        private void getunbindService( ) {
            try{
                if (localBinderAsyncSingleWorkManager!=null) {
                    getApplicationContext().unbindService(serviceConnectionAsyns);
                }
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                        Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.04.2024

            }


        }


        @SuppressLint("SuspiciousIndentation")
        private Long МетодЗапускаОднаразовая() {
            Long ФинальныйРезультатAsyncBackgroud=0l;
            try{
                // TODO: 22.12.2022  сама запуска синхронищации из workmanager ОБЩЕГО
                boolean ВыбранныйРежимСети =
                        new GetConnectivityManagerAndroid(getApplicationContext()).сonnectivityManageruserselection();

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " lВыбранныйРежимСети" + ВыбранныйРежимСети);

                if (ВыбранныйРежимСети == true && localBinderAsyncSingleWorkManager !=null ) {

                    ФинальныйРезультатAsyncBackgroud = localBinderAsyncSingleWorkManager.getService().metodStartingSync( getApplicationContext());
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " ФинальныйРезультатAsyncBackgroud " + " service_for_remote_async " + localBinderAsyncSingleWorkManager);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            return ФинальныйРезультатAsyncBackgroud;

        }

        // TODO: 02.04.2024 \ end inner class sinlgw ework mager
    }





    // TODO: 02.04.2024  end main work namager

}





























