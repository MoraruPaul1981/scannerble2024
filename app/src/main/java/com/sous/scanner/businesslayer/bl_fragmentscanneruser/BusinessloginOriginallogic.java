package com.sous.scanner.businesslayer.bl_fragmentscanneruser;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;

import com.google.android.material.textview.MaterialTextView;
import com.jakewharton.rxbinding4.view.RxView;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import kotlin.Unit;


@Module
@InstallIn(SingletonComponent.class)
public class BusinessloginOriginallogic {
    Context context;
     Long version;

    public @Inject BusinessloginOriginallogic(@ApplicationContext Context hitcontext) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        PackageInfo pInfo = null;
        try {
            pInfo = hitcontext.getPackageManager().getPackageInfo(hitcontext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        version = pInfo.getLongVersionCode();
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }


    public void materialtextViewGetLastState(@NonNull SharedPreferences preferences,
                                             @NonNull MaterialTextView materialtextview_last_state,
                                             @NonNull Message messageClient) {
        try{
            String completeResultContol=    preferences.getString("completeResultContol","");
            if (completeResultContol.length()>0) {

                materialtextview_last_state.animate().rotationX(+20l);
                messageClient.getTarget() .postDelayed(()-> {
                    materialtextview_last_state.animate().rotationX(0);

                    materialtextview_last_state.setText(completeResultContol);
                    materialtextview_last_state.requestLayout();
                    materialtextview_last_state.refreshDrawableState();
                },200);

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }
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


    public void selectMacaddressviaclick(@NonNull MaterialTextView searchview_maclistdeviceserver,
                                         @NonNull LayoutInflater layoutInflater,
                                         @NonNull Message messageClient,
                                         @NonNull Animation animation,
                                         @NonNull SharedPreferences preferences,
                                         @NonNull Activity activity) {
        try{
            // TODO: 22.02.2023 для второй кнопки
            RxView.clicks(searchview_maclistdeviceserver)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Unit>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                            Log.d(this.getClass().getName(),  "  RxView.clicks " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                    + " время " +new Date().toLocaleString() );
                        }
                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull Unit unit) {
                            // TODO: 05.08.2024

                            // TODO: 02.08.2024
                            Log.d(this.getClass().getName(), "\n" + " class " +
                                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

                            // TODO: 19.08.2024
                           BusinesslogicSelectMacAdressGattServer businesslogicSelectMacAdressGattServer =
                                    new BusinesslogicSelectMacAdressGattServer(context,
                                            version,messageClient,layoutInflater
                                            ,searchview_maclistdeviceserver,preferences, activity);

                            searchview_maclistdeviceserver.startAnimation(animation);

                            businesslogicSelectMacAdressGattServer.AlertDialogSelectionMacAdress( );


                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName()
                                    + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
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

                        @Override
                        public void onComplete() {
                            // TODO: 09.08.2024 у вс не вслючен Bluetooth


                            Log.d(this.getClass().getName(),  "  RxView.clicks " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                    + " время " +new Date().toLocaleString() );

                            Log.d(this.getClass().getName(),  "  RxView.clicks " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                    + " время " +new Date().toLocaleString() );
                        }
                    });
            // TODO: 19.08.2024
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




    public void materialtextViewGetLastMac(@NonNull SharedPreferences preferences,
                                           @NonNull MaterialTextView searchview_maclistdeviceserver,
                                           @NonNull Animation animation) {
        try{
            // TODO: 29.08.2024  сохраняем preferences
            BusinessloginforfragmentScanner businessloginforfragmentScanner=
                    new BusinessloginforfragmentScanner(context,version,preferences );

            businessloginforfragmentScanner.updateUIFragmentMacSelecting(searchview_maclistdeviceserver,preferences,animation);

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








    // TODO: 29.08.2024  End CLass

}
