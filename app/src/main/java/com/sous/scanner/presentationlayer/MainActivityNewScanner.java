package com.sous.scanner.presentationlayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.R;
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


    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @RequiresPermission(anyOf = {

            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_ADMIN})
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

            // TODO: 19.02.2023 разрешает обновлени BLE
            МетодРАзрешенияBlurtooTКлиент();

            МетодБиндингаСканирование();

            МетодHandles();

            startingServiceSimpleScan();


            handlerScannerGatt.getTarget().postDelayed(()->{

                finish();

            },5000);

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
    private void МетодРАзрешенияBlurtooTКлиент() {
        try{
            String[] PERMISSIONS_STORAGE = {

                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_PRIVILEGED,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_ADMIN
            };
            String[] PERMISSIONS_LOCATION = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_PRIVILEGED,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_ADMIN
            };
            int permission1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED);
            int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN);
            if (permission1 != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        1
                );
            } else if (permission2 != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_LOCATION,
                        1
                );
            }
            // TODO: 19.02.2023 Безконечное Посик Дивайсов РАзрешение
            Intent discoverableIntent = new Intent();
            discoverableIntent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);//BluetoothAdapter.ACTION_DISCOVERY_FINISHED
            discoverableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(discoverableIntent);

            Log.i(this.getClass().getName(),  "НАстройки BLE " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
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


   
}