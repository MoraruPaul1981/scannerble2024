package com.sous.server.presentationlayer;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.AsyncTaskLoader;

import com.onesignal.OneSignal;
import com.sous.server.R;
import com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner.Bi_MainActivityNewServerScanner;
import com.sous.server.businesslayer.BroadcastreceiverServer.BroadcastReceiverGattServerAlcConn;
import com.sous.server.businesslayer.BroadcastreceiverServer.BroadcastReceiverGattServerName_Changed;
import com.sous.server.businesslayer.BroadcastreceiverServer.BroadcastReceiverGattServerOthers;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Permissions.SetPermissions;
import com.sous.server.businesslayer.bl_OneSingal.BussenslogicOneSignal;

import java.util.Date;


public class ActivityServerScanner extends AppCompatActivity {
    protected FragmentManager fragmentManager;

    protected Long version;
    protected Message messageGattServer;
    protected AsyncTaskLoader asyncTaskLoaderGatt  ;


    protected  Bi_MainActivityNewServerScanner biMainActivityNewServerScanner;




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_boot_scannerserver);

            getSupportActionBar().hide(); ///скрывать тул бар

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

              /////TODO создание Мэнеджера Фрагмент
            fragmentManager = getSupportFragmentManager();



            PackageInfo   pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();


            // TODO: 24.07.2024 устанвливаем разрешения

       new SetPermissions(version).additionalpermissionsBle(this,getApplicationContext());


            getmessageGattServer();

            startinggeregisterReceiver();


              biMainActivityNewServerScanner=new Bi_MainActivityNewServerScanner(getApplicationContext(), fragmentManager,this);

            version=    biMainActivityNewServerScanner.  getversionCurrentPC();


            /////TODO создание Мэнеджера Фрагмент
            Log.i(this.getClass().getName(), "  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
          /*  //TODO: ссылка на класс бизнес логики Сервер Сканирование
          *      */



        biMainActivityNewServerScanner.   МетодЗапускBootФрагмента(new FragmentBootServer());//todo Запускам клиента или сервер фрагмент



            Log.i(this.getClass().getName(), "  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString());

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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


    public void getmessageGattServer() {

        try {

            messageGattServer =new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " msg " +msg );
                    return false;
                }
            }).obtainMessage();
            messageGattServer.setAsynchronous(true);

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
        }

    }







    @SuppressLint("MissingPermission")
    public void  startinggeregisterReceiver(){
        try{
// TODO: 02.08.2024
            IntentFilter filterScanServerAlcConn = new IntentFilter();
            filterScanServerAlcConn.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filterScanServerAlcConn.addAction(BluetoothDevice.ACTION_FOUND);
            filterScanServerAlcConn.setPriority(SYSTEM_HIGH_PRIORITY);
          //  registerReceiver(new BroadcastReceiverGattServerAlcConn(), filterScanServerAlcConn);
            registerReceiver(new BroadcastReceiverGattServerAlcConn(), filterScanServerAlcConn,null,messageGattServer.getTarget());

            // TODO: 02.08.2024
            IntentFilter filterScanServerName_Changed = new IntentFilter();
            filterScanServerName_Changed.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
            filterScanServerName_Changed.addAction(BluetoothDevice.ACTION_CLASS_CHANGED);
            filterScanServerName_Changed.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            filterScanServerName_Changed.setPriority(SYSTEM_HIGH_PRIORITY);
            registerReceiver(new BroadcastReceiverGattServerName_Changed(), filterScanServerName_Changed,null,messageGattServer.getTarget());



            // TODO: 02.08.2024
            IntentFilter filterScanServerOthers = new IntentFilter();
            filterScanServerOthers.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            filterScanServerOthers.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filterScanServerOthers.setPriority(SYSTEM_HIGH_PRIORITY);
            registerReceiver(new BroadcastReceiverGattServerOthers(), filterScanServerOthers,null,messageGattServer.getTarget());

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


















    // TODO: 24.07.2024
}



