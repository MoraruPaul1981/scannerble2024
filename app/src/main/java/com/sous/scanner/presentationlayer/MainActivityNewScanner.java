package com.sous.scanner.presentationlayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.sous.scanner.businesslayer.Broadcastreceiver.BroadcastReceiverACL;
import com.sous.scanner.businesslayer.Broadcastreceiver.BroadcastReceiverNAME_CHANGED;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.R;
import com.sous.scanner.businesslayer.Permissions.SetPermissions;
import com.sous.scanner.businesslayer.bl_forActivityScan.BussenslogicOneSignal;

import java.util.Date;


public class MainActivityNewScanner extends AppCompatActivity  {
   public Message handlerScannerGattClient;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_gatt_clent);
            getSupportActionBar().hide(); ///скрывать тул бар
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // TODO: 08.12.2022
            fragmentManagerScanner = getSupportFragmentManager();
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            Log.d(this.getClass().getName(),  "onResume " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );


// TODO: 17.07.2024 Коды запуски БИзнес ЛОгики Активти Сканера
            МетодHandles();

            // TODO: 24.07.2024 устанвливаем разрешения
            new SetPermissions(version).additionalpermissionsBle(this,getApplicationContext());



            startinпBroadcastReceiverCleint();

            BussenslogicOneSignal bussenslogicOneSignal=
                    new BussenslogicOneSignal(getApplicationContext(),version);
            bussenslogicOneSignal .initOneSignal();


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
            startingFragmentBootScanner(new FragmentBootScanner());

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







    public void МетодHandles() {
       handlerScannerGattClient =new Handler(Looper.getMainLooper(), new Handler.Callback() {
           @Override
           public boolean handleMessage(@NonNull Message msg) {
               return true;
           }
       }).obtainMessage();
        Log.d(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
    }






    @SuppressLint("MissingPermission")
    public void startinпBroadcastReceiverCleint(){
        try{

            IntentFilter filterScanClientAlcConn = new IntentFilter();
            filterScanClientAlcConn.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filterScanClientAlcConn.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            filterScanClientAlcConn.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filterScanClientAlcConn.setPriority(10);
            registerReceiver(new BroadcastReceiverACL(), filterScanClientAlcConn,null,handlerScannerGattClient.getTarget());

            // TODO: 02.08.2024

            IntentFilter filterScanClientOthets= new IntentFilter();
            filterScanClientOthets.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
            filterScanClientOthets.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
            filterScanClientOthets.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
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



    // TODO: 29.11.2022 служба сканирования



    @SuppressLint("SuspiciousIndentation")
    protected void startingFragmentBootScanner(@NonNull Fragment getfragmentBootScanner) {
        try {
            fragmentTransaction = fragmentManagerScanner.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.add(R.id.id_gattclientFrameLayout, getfragmentBootScanner)
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



    // TODO: 05.08.2024 end CLASSS

}