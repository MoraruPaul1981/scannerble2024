package com.sous.scanner.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.onesignal.OneSignal;
import com.sous.scanner.businesslayer.Broadcastreceiver.BroadcastReceiverACL;
import com.sous.scanner.businesslayer.Broadcastreceiver.BroadcastReceiverNAME_CHANGED;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.R;
import com.sous.scanner.businesslayer.Permissions.SetPermissions;
import com.sous.scanner.businesslayer.Services.ServiceClientsScan;
import com.sous.scanner.businesslayer.Services.ServiceClientGatt;

import java.util.Date;
import java.util.concurrent.Executors;


public class MainActivityNewScanner extends AppCompatActivity  {
   public Message handlerScannerGatt;
    private NavigationBarView bottomNavigationView;
    @SuppressLint("RestrictedApi")
    private BottomNavigationItemView bottomNavigationItemViewВыход;
    @SuppressLint("RestrictedApi")
    private BottomNavigationItemView bottomNavigationItemViewИстория;
    private FragmentManager fragmentManagerScanner;
    private FragmentTransaction fragmentTransaction;

    private com.google.android.material.card.MaterialCardView cardVievscanner;

    protected TabLayout  tabLayout ;

    private     Long version=0l;
    protected ServiceClientGatt.LocalBinderСканнер binderСканнер;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_newscanner_empty);
            getSupportActionBar().hide(); ///скрывать тул бар
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // TODO: 08.12.2022
        /*    cardVievscanner = (MaterialCardView) findViewById(R.id.id_cardViewblescanner);
            RelativeLayout relativecayoutscanner = (RelativeLayout) cardVievscanner. findViewById(R.id.id_relativecayoutscanner);

            bottomNavigationView = (NavigationBarView)  relativecayoutscanner.findViewById(R.id.BottomNavigationViewScanner)  ;
            tabLayout = (TabLayout)  relativecayoutscanner.findViewById(R.id.tabLayout)  ;


            bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);
            // TODO: 05.12.2022 строчлочки
            bottomNavigationItemViewВыход = bottomNavigationView.findViewById(R.id.id_lback);
            bottomNavigationItemViewИстория = bottomNavigationView.findViewById(R.id.id_scanner_history);
            bottomNavigationItemViewВыход.setItemRippleColor(ColorStateList.valueOf(Color.RED));
            bottomNavigationItemViewИстория.setItemRippleColor(ColorStateList.valueOf(Color.RED));*/

            // TODO: 16.07.2024
            fragmentManagerScanner = getSupportFragmentManager();
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            Log.i(this.getClass().getName(),  "onResume " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );


// TODO: 17.07.2024 Коды запуски БИзнес ЛОгики Активти Сканера

            МетодHandles();


            // TODO: 24.07.2024 устанвливаем разрешения

            new SetPermissions(version).additionalpermissionsBle(this,getApplicationContext());

            startinggeregisterReceiver();

            final String ONEKEY="d94341b5-cc58-4531-aa39-1186de5f9948";
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONEKEY);

            OneSignal.promptForPushNotifications();




            
            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{

            // TODO: 30.07.2024
            handlerScannerGatt.getTarget().post(()->{

               startingServiceSimpleScan();
               
                МетодБиндингаСканирование();
                

            });
            
            
// TODO: 30.07.2024  
        handlerScannerGatt.getTarget().postDelayed(()->{

            finishAndRemoveTask();

        },3000);

        // TODO: 24.01.2023  переходят после получение binder
        ///      МетодЗапускBootФрагмента(new FragmentBootScanner());//todo Запускам клиента или сервер фрагмент


        Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }

    private void sendthereceivedServicelinktotheFragment(@NonNull ServiceClientGatt.LocalBinderСканнер binderСканнер) {
        try{
        Bundle result = new Bundle();
        result.putBinder("bundleKey", binderСканнер);
        fragmentManagerScanner.setFragmentResult("requestKeyScannerBindindService", result);

            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " binderСканнер " +binderСканнер );


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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }






    public void МетодHandles() {
       handlerScannerGatt =new Handler(Looper.getMainLooper(), new Handler.Callback() {
           @Override
           public boolean handleMessage(@NonNull Message msg) {
               return true;
           }
       }).obtainMessage();
        Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
    }












    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // TODO: 05.12.2022  запуск клиента
    @SuppressLint("SuspiciousIndentation")
    protected void МетодЗапускBootФрагмента(@NonNull Fragment getfragmentBootScanner) {
        try {
            fragmentTransaction = fragmentManagerScanner.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.add(R.id.framelauoutbleprimary, getfragmentBootScanner)
                    .setPrimaryNavigationFragment(getfragmentBootScanner);//.layout.activity_for_fragemtb_history_tasks
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.show(getfragmentBootScanner);
            //TODO:
            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " fragmentTransaction " + fragmentManagerScanner);



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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    // TODO: 29.11.2022 служба сканирования
    private void МетодБиндингаСканирование() {
        try {
            ServiceConnection connectionСканирование = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    try {
                        binderСканнер = (ServiceClientGatt.LocalBinderСканнер) service;
                        if (binderСканнер.isBinderAlive()) {
                            Log.i(getApplicationContext().getClass().getName(), "    onServiceConnected  binderСогласованияbinderМатериалы.isBinderAlive()"
                                    + binderСканнер.isBinderAlive());


                            // TODO: 17.07.2024 Полученную Службу отправляем в Фрагмент
                            sendthereceivedServicelinktotheFragment(binderСканнер);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        ContentValues valuesЗаписываемОшибки = new ContentValues();
                        valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
                        valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
                        valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
                        valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
                        valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
                        final Object ТекущаяВерсияПрограммы = version;
                        Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                        valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    try {
                        Log.i(getApplicationContext().getClass().getName(), "    onServiceDisconnected  binderСканнер.isBinderAlive()" + binderСканнер.isBinderAlive());
                        binderСканнер = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                                + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            };
            Intent intentБиндингсСлужбойСканирования = new Intent(getApplicationContext(), ServiceClientGatt.class);
            intentБиндингсСлужбойСканирования.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentБиндингсСлужбойСканирования.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentБиндингсСлужбойСканирования.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intentБиндингсСлужбойСканирования.setAction("com.scannerforble");
          bindService(intentБиндингсСлужбойСканирования, Context.BIND_AUTO_CREATE, Executors.newSingleThreadExecutor(), connectionСканирование);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    // TODO: 29.11.2022 служба сканирования
    private void startingServiceSimpleScan() {
        try {
            handlerScannerGatt.getTarget().post(()->{

            Intent intentClientServiceSimpleScan = new Intent(getApplicationContext(), ServiceClientsScan.class);
            intentClientServiceSimpleScan.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intentClientServiceSimpleScan.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intentClientServiceSimpleScan.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            // TODO: 24.07.2024
                ContextCompat.startForegroundService(this,intentClientServiceSimpleScan);
                // TODO: 26.07.2024

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

            });
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }


    @SuppressLint("MissingPermission")
    public void  startinggeregisterReceiver(){
        try{

            IntentFilter filterScanClientAlcConn = new IntentFilter();
            filterScanClientAlcConn.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            registerReceiver(new BroadcastReceiverACL(), filterScanClientAlcConn);

            // TODO: 02.08.2024

            IntentFilter filterScanClientOthets= new IntentFilter();
            filterScanClientOthets.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
            filterScanClientOthets.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
            registerReceiver(new BroadcastReceiverNAME_CHANGED(), filterScanClientOthets);





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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            // new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }


}