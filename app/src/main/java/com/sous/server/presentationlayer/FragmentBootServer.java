package com.sous.server.presentationlayer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.AsyncTaskLoader;

import com.sous.server.R;
import com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentBootScannerServer.Bi_FragmentBootScannerServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class FragmentBootServer extends Fragment {
    private Long version;
    private FragmentManager fragmentManager;
    private Message messageGattServer  ;
    private AsyncTaskLoader asyncTaskLoaderGatt;
    private Bi_FragmentBootScannerServer biFragmentBootScannerServer;


    private ImageView imageviewbootscanner;
    private ConstraintLayout id_fragment_boot_scannerserver;

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Log.d(this.getClass().getName(), "  onViewCreated  Fragment1_One_Tasks view   " + view);

            imageviewbootscanner = (ImageView) view.findViewById(R.id.id_imageviewbootscanner);
            id_fragment_boot_scannerserver = (ConstraintLayout) view.findViewById(R.id.id_fragment_boot_scannerserver);

            /////todo Пришли переменные
            fragmentManager = (FragmentManager) ((ActivityServerScanner) getActivity()).fragmentManager;
            version = (Long) ((ActivityServerScanner) getActivity()).version;
            messageGattServer = (Message) ((ActivityServerScanner) getActivity()).messageGattServer;
            asyncTaskLoaderGatt = (AsyncTaskLoader) ((ActivityServerScanner) getActivity()).asyncTaskLoaderGatt;

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
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
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.fragment_boot_scannerserver, container, false);
            Log.d(getContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return view;
    }




    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onStart() {
        super.onStart();
        try{
        /*    //TODO:создаем подписку MessageScannerServer */
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }

            Log.d(getContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());



// TODO: 25.07.2024 запускам службы двух серверных
            startingServicesOnlyScan() ;


            Log.d(getContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }

    private void startingServicesOnlyScan() {
        try{
            /*    //TODO:создаем класс для бизнес логики */
            biFragmentBootScannerServer = new Bi_FragmentBootScannerServer(getContext(), fragmentManager, getActivity(),version);

        messageGattServer.getTarget().post(()->{
            // TODO: 19.07.2024 Запуск Службы

            biFragmentBootScannerServer.startingServiceScaning() ;

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

        });

        Log.d(getContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }






    @Override
    public void onStop() {
        try{
            EventBus.getDefault().unregister(this);

            super.onStop();
        Log.d(getContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }

    @Override
    public void onDestroy() {
  try{
        /*    //TODO:создаем подписку MessageScannerServer */
        EventBus.getDefault().unregister(this);

        super.onDestroy();
        Log.d(getContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
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
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageScannerServer event) {
        // Do something
        try {
        // Do something
        ParamentsScannerServer paramentsScannerServer=   event.paramentsScannerServer;
        Boolean getFladEnableApadaterBTEOtService= paramentsScannerServer.getФлагЗапускаФрагментRecyreView();
        String CurrentTask= paramentsScannerServer.getCurrentTask().trim();


// TODO: 25.07.2024 вибираем какой фрагмент загружать
        switch (CurrentTask.trim()) {

            case "bluetootAdapterEnableGatt" :
            case "bluetootAdapterDisabledGatt"  :

// TODO: 24.07.2024 GATT реакция на событие от Службы GATT
                reactionFromEventBusFromServiceGatt(CurrentTask, getFladEnableApadaterBTEOtService);

                
                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService);
                break;

            case "bluetootAdapterEnableScan"  :
            case "bluetootAdapterDisabledScan":

                // TODO: 24.07.2024 GATT реакция на событие от Службы Scan
                reactionFromEventBusFromServiceScan(CurrentTask, getFladEnableApadaterBTEOtService);


                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService);
                break;
        }
        
            Log.d(getContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService);

        

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
        new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }

    private void reactionFromEventBusFromServiceGatt(String CurrentTask, Boolean getFladEnableApadaterBTEOtService) {

        if (CurrentTask.contentEquals("bluetootAdapterEnableGatt")) {
////TODO: В зависимтсто какой результат прищели из службы то сообщаем пользоватю об этом , лии сразу переходим на новой  фрагмент RecyreView
            forwardOtServiceGattEventBus(getFladEnableApadaterBTEOtService);
            
            messageGattServer.getTarget().postDelayed(()->{
                // TODO: 17.07.2024  переходим после успещглй коннекта Обмена между Клиентмо и Сервером BLE  данными

                //TODO: Запускаем Фрагмент Server Fragment
                biFragmentBootScannerServer.МетодЗапускаФрагментаServer(  new FragmentGattRecyclerView())   ; /// Scan

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );



                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " CurrentTask " + CurrentTask);
            },1500);
         
            Log.d(getContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " CurrentTask " + CurrentTask);

        } else {

            if (CurrentTask.contentEquals("bluetootAdapterDisabledGatt")) {
                /*   Snackbar.make(id_fragment_boot_scannerserver , "Сервер или Bluetooth не работает !!! ",Snackbar.ANIMATION_MODE_SLIDE)
                    .setAction("Action",null).show();*/
                Toast toast = Toast.makeText(getContext(),"Сервер или Bluetooth не работает !!! ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                toast.show();

                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " CurrentTask " + CurrentTask);
            }

        }
    }

    private void reactionFromEventBusFromServiceScan(String CurrentTask, Boolean getFladEnableApadaterBTEOtService) {
        if (CurrentTask.contentEquals("bluetootAdapterEnableScan")) {
////TODO: В зависимтсто какой результат прищели из службы то сообщаем пользоватю об этом , лии сразу переходим на новой  фрагмент RecyreView
            forwardOtServiceGattEventBus(getFladEnableApadaterBTEOtService);

            messageGattServer.getTarget().postDelayed(()->{
                // TODO: 17.07.2024  переходим после успещглй коннекта Обмена между Клиентмо и Сервером BLE  данными
                //TODO: Запускаем Фрагмент Server Fragment
                biFragmentBootScannerServer.МетодЗапускаФрагментаServer(  new FragmentScanRecyclerView())   ; /// Scan

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " CurrentTask " + CurrentTask);
            },1500);

            Log.d(getContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " CurrentTask " + CurrentTask);

        } else {

            if (CurrentTask.contentEquals("bluetootAdapterDisabledScan")) {
                /*   Snackbar.make(id_fragment_boot_scannerserver , "Сервер или Bluetooth не работает !!! ",Snackbar.ANIMATION_MODE_SLIDE)
                    .setAction("Action",null).show();*/
                Toast toast = Toast.makeText(getContext(),"Сервер или Bluetooth не работает !!! ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                toast.show();

                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " CurrentTask " + CurrentTask);
            }

        }
    }







    @SuppressLint("ResourceType")
    private void forwardOtServiceGattEventBus(@NonNull Boolean getReslutOTServiceGatt ) {
        try{
            //TODO: Запускаем Фрагмент



             //   DrawableCompat.setTint(imageviewbootscanner.getDrawable(), ContextCompat.getColor(getContext(), com.google.android.material.R.color.design_default_color_on_primary));
                //imageviewbootscanner.setColorFilter(Color.argb(255, 50, 150, 140));
                imageviewbootscanner.setColorFilter(Color.argb(255, 0, 0, 0));


                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  getReslutOTServiceGatt " +getReslutOTServiceGatt);



            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  getReslutOTServiceGatt " +getReslutOTServiceGatt);

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
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    
    



    @SuppressLint("ResourceType")
    private ConcurrentHashMap forwardSuccessDiveceUIEventAnd(@NonNull  ContentValues     getListSuccessDerviceOtServerGatt) {
        ConcurrentHashMap<String,ContentValues> concurrentHashMapSucceesDataOtClient=new ConcurrentHashMap<>();
        try{
            //TODO: Запускаем Фрагмент
           String   getListOtAndroidGattClernt =   getListSuccessDerviceOtServerGatt.getAsString("namedevice").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("macdevice").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("iemi").trim().trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("completedwork").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("date_update").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("city").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("gps1").trim()+"\n"+
                   getListSuccessDerviceOtServerGatt.getAsString("gps2").trim()+"\n";

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  getListOtAndroidGattClernt " +getListOtAndroidGattClernt);
            // TODO: 17.07.2024 add

            concurrentHashMapSucceesDataOtClient.put(getListOtAndroidGattClernt,getListSuccessDerviceOtServerGatt);

            // Inflate the custom view from XML
            Toast toast = Toast.makeText(getContext(),getListOtAndroidGattClernt.trim(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);

            TextView textView = new TextView(getContext());
            textView.setBackgroundColor(Color.DKGRAY);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            Typeface typeface = Typeface.create("serif", Typeface.BOLD);
            textView.setTypeface(typeface);
            textView.setPadding(10, 10, 10, 10);
            textView.setText(getListOtAndroidGattClernt.trim());

            toast.setView(textView);
            toast.show();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  getListSuccessDerviceOtServerGatt " +getListSuccessDerviceOtServerGatt);

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
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  concurrentHashMapSucceesDataOtClient;
    }























}




























