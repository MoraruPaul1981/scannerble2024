package com.sous.scanner.businesslayer.bl_fragmentbootscanner;

import android.content.ContentValues;
import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.google.common.util.concurrent.AtomicDouble;
import com.scanner.datasync.businesslayer.bl_RemoteMessaging.RemoteMessaging;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_forServices.Businesslogic_JOBServive;

import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;


@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicFragBootScanner {

    private Context context;
    private  long version;

    @Inject
    RemoteMessaging remoteMessaging;
    @Inject
    Businesslogic_JOBServive businesslogicJobServive;

    private  LocationManager locationManager;
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
        // TODO: 22.08.2024
        Completable.complete().blockingSubscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

                // TODO: 22.08.2024 Парименимае Решение Запускаем Сихронизацию  
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
;
                
                if (Optional.ofNullable(locationManager) .isPresent() 
                        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                    // TODO: 22.08.2024  Запускаем слуджу Синжрониазции
                    remoteMessaging.startingServicedataSync(context,version);
                    
                }else {
                    // TODO: 22.08.2024  Сразу переходим на запуск Службы Сканирование Bluetooth Client  

                    businesslogicJobServive.startingServiceSimpleScan("fistlauntfrombackground");
                }
                
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                        "  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) " + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)+
                        " Optional.ofNullable(locationManager) .isPresent()  " +Optional.ofNullable(locationManager) .isPresent() );
            }

            @Override
            public void onComplete() {
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
            }

            @Override
            public void onError(@NonNull Throwable e) {
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
        });


    }
}
