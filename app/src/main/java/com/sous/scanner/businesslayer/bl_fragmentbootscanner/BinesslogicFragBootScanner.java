package com.sous.scanner.businesslayer.bl_fragmentbootscanner;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.scanner.datasync.businesslayer.bl_RemoteMessaging.RemoteMessaging;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_EvenBus.EventB_Clent;
import com.sous.scanner.businesslayer.bl_forServices.BusinesslogicStartingService;
import com.sous.scanner.presentationlayer.FragmentScannerUser;

import org.greenrobot.eventbus.EventBus;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicFragBootScanner {

    private Context context;
    private  long version;


    @Inject
    BusinesslogicStartingService businesslogicJobServive;

  private ConnectivityManager connectivityManager ;
    public @Inject BinesslogicFragBootScanner(@ApplicationContext Context hiltcontext ) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context=hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }




    public void startingServicedataSync (long versionhilt) {
        this.version = versionhilt;
        try{
        // TODO: 22.08.2024


    Completable.complete()
            .subscribeOn(Schedulers.single())
            .observeOn(Schedulers.single())
            .delay(2,TimeUnit.SECONDS)
            .subscribe(new CompletableObserver() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
        // TODO: 07.11.2024
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                    "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " );

        }

        @Override
        public void onComplete() {
            // TODO: 07.11.2024

            connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            // TODO: 22.08.2024 Парименимае Решение Запускаем Сихронизацию

            if ( Optional.ofNullable(activeNetworkInfo).isPresent()  ) {
                // TODO: 22.08.2024  Запускаем слуджу Синжрониазции
                if (activeNetworkInfo.isConnected()) {
                    // TODO: 22.08.2024

                    ///   businesslogicJobServive.startingServicedataSync(context,version);

                    businesslogicJobServive.bindingServicedataSync(context,version);

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                            "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " +
                            " activeNetworkInfo  " +activeNetworkInfo );
                }else {
                    // TODO: 03.09.2024 После сихрониазции запускаем Фрашмент самого Сканера
                    new BusinesslogicStartingService(context). startingFragmentAflerAsyncData();

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                            "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " +
                            " activeNetworkInfo  " +activeNetworkInfo );
                }

            }else {

                // TODO: 03.09.2024 После сихрониазции запускаем Фрашмент самого Сканера
                new BusinesslogicStartingService(context). startingFragmentAflerAsyncData();

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                        "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " +
                        " activeNetworkInfo  " +activeNetworkInfo );



            }
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                    "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " +
                    " activeNetworkInfo  " +activeNetworkInfo );






            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                    "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " );
        }

        @Override
        public void onError(@NonNull Throwable e) {
            // TODO: 07.11.2024
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                    "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " );
        }
    });



        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки = new ContentValues();
        valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы = version;
        Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
        new com.scanner.datasync.businesslayer.Errors.SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }

    }//TODO END




    public void statyingCallBAckFragmentScaner() {
        try{
            EventB_Clent eventBClentCallBACKfRAGMENTsCANNER= new EventB_Clent( new FragmentScannerUser());
            //TODO: ответ на экран работает ообрубование или нет
            EventBus.getDefault().post(eventBClentCallBACKfRAGMENTsCANNER);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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




}
