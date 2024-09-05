package com.sous.server.businesslayer.BI_Services;

import static android.content.Context.POWER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GnssStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.sous.server.R;
import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;
import com.sous.server.businesslayer.Locations.GattLocationListener;
import com.sous.server.businesslayer.Services.ServiceServerScan;
import com.sous.server.businesslayer.bl_Tests.GetBleAdvertising;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class BuccesloginForServiceServerScan {

private Context context;
private  Long version;



    private SQLiteDatabase sqLiteDatabase;
    private BluetoothGattServer getBluetoothGattServer;
    private BluetoothManager bluetoothManagerServer;
    private BluetoothAdapter bluetoothAdapter;


    private UUID getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();
    private LocationManager locationManager;
    private      SharedPreferences sharedPreferencesGatt;
    private AtomicReference<byte[]> atomicReferenceValue = new AtomicReference<>();
    private Cursor successfuldevices;

    private    Boolean getStatusEnableBlueadapter;

    private      final Handler handler=new Handler();

    private ContentProviderServer contentProviderServer;


    @Inject
    GetBleAdvertising getBleAdvertising;


    public @Inject BuccesloginForServiceServerScan(@ApplicationContext Context hitcontext) {
        this.context = hitcontext;
        final PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        version = pInfo.getLongVersionCode();
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }



    public void launchBuccesloginForServiceServerScan(@NotNull  ServiceServerScan getserviceServerScan ) {
        try {
            // TODO: 23.07.2024 starting  core motods BLE Gatt Server
            initAdapterBluetoothManager();
            // TODO: 03.09.2024 запоминим данные в глобальн я память 
            getPreferenceManager();
            //TODO:получаем Статус Адаптера Bluetooth true, false  и оптравляем статус в активти
            getStatusEnableBlueadapter = enableBluetoothAdapter(bluetoothAdapter,version,contentProviderServer);

            if (getStatusEnableBlueadapter==true) {
                // TODO: 03.09.2024  
                getContentProvider();
                // TODO: 03.09.2024
                langingGPSLocations( );
                // TODO: 25.08.2024 TEST
                getBleAdvertising.staringAdvertisingSet(bluetoothAdapter);
// TODO: 28.07.2024 LIster
                getListerBluetoothDevice();
                // TODO: 26.07.2024 starting Fragment Scan
                callBackFromServiceToRecyreViewFragment(getStatusEnableBlueadapter);
                //TODO :  главный метод службы запускаем Gatt Server
                mainstartingServerScan();
                //TODO:  для запущеного сервера Gatt ,дополвнительые параметры натсройки Charact and UUID
                settingGattServerBluetoothGattService();

                Log.d(context.getClass().getName(), "\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }
            

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

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

    private void getPreferenceManager() {
        try{
        sharedPreferencesGatt =              PreferenceManager.getDefaultSharedPreferences(context);
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


    public void startingServiceGattServer( ) {
        try {
                        // TODO: 23.07.2024 starting
                        Intent ServiceGattServerScan = new Intent(context, ServiceServerScan.class);
                        // TODO: 15.08.2024
                        ServiceGattServerScan=  startPowerManager(ServiceGattServerScan);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        ServiceGattServerScan.addFlags(Intent.FLAG_FROM_BACKGROUND);
                        ServiceGattServerScan.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        // TODO: 08.08.2024
                        ContextCompat.startForegroundService(context,ServiceGattServerScan);

                        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

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

    public void stopingServiceGattServer( ) {
        try {
            // TODO: 23.07.2024 starting
            Intent ServiceGattServerScan = new Intent(context, ServiceServerScan.class);
            // TODO: 15.08.2024
            ServiceGattServerScan=  startPowerManager(ServiceGattServerScan);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            ServiceGattServerScan.addFlags(Intent.FLAG_FROM_BACKGROUND);
            ServiceGattServerScan.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            // TODO: 08.08.2024
            context.stopService(ServiceGattServerScan);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

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




    public Intent  startPowerManager(@NotNull  Intent intent){
        try{
// TODO: 02.08.2024
            String packageName =context. getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
            }
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            // new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

        return  intent;

    }


    @SuppressLint("MissingPermission")
    public Boolean enableBluetoothAdapter(@NotNull BluetoothAdapter bluetoothAdapter,
                                          @NotNull long version,
                                          @NotNull ContentProviderServer contentProviderServer) {
        Boolean getStatusEnableBlueadapter=false;
        try{

            if (bluetoothAdapter!=null) {
                if (bluetoothAdapter.isEnabled() ==false) {
                    bluetoothAdapter.enable();
                }
                getStatusEnableBlueadapter=bluetoothAdapter.isEnabled();
            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " getStatusEnableBlueadapter  " +getStatusEnableBlueadapter);


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " getStatusEnableBlueadapter  " +getStatusEnableBlueadapter);
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
        return  getStatusEnableBlueadapter ;
    }


    @SuppressLint("MissingPermission")
    public void   enadleBroadcastManager(@NotNull long version, @NotNull Intent intent) {
        try{
                    switch (intent.getAction()) {
                        case BluetoothAdapter.ACTION_STATE_CHANGED:
                  int  state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                 switch (state)  {
                     case BluetoothAdapter.STATE_OFF:
                         Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                         break;
                     case BluetoothAdapter.STATE_ON:
                         // TODO: 26.08.2024
                          startingServiceGattServer();

                         Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                         break;

                     case BluetoothAdapter.STATE_CONNECTED:
                         Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                         break;

                     case  BluetoothAdapter.STATE_TURNING_ON:
                         Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                         break;

                     case  BluetoothAdapter.STATE_TURNING_OFF:
                         Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                 " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                 " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                         break;

                 }
                    }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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

    @SuppressLint("MissingPermission")
    private void initAdapterBluetoothManager() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            bluetoothManagerServer = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = (BluetoothAdapter) bluetoothManagerServer.getAdapter();


            Log.d(context.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }



    //TODO: отвечам оюратно на фрагмент что включен Адаптер Blutooth
    private void callBackFromServiceToRecyreViewFragment(@NonNull Boolean getStatusEnableBlueadapter) {
        try{

            ParamentsScannerServer sendFragmentparamentsScannerServer=new ParamentsScannerServer();
            sendFragmentparamentsScannerServer.setФлагЗапускаФрагментRecyreView(getStatusEnableBlueadapter);

            if (getStatusEnableBlueadapter) {
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterEnableScan");
            } else {
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterDisabledScan");
            }
            //TODO: послымаем Из Службы Значение на Фрагмент
            MessageScannerServer sendmessageScannerStartRecyreViewFragment= new MessageScannerServer( sendFragmentparamentsScannerServer);
            //TODO: ответ на экран работает ообрубование или нет
            EventBus.getDefault().post(sendmessageScannerStartRecyreViewFragment);

            Log.d(context.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getStatusEnableBlueadapter "+getStatusEnableBlueadapter);

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


    //TODO: Включаем адаптер is Enabled BLE


    void getListerBluetoothDevice(){

        try{

            int[] states = new int[]{BluetoothProfile.STATE_CONNECTED,
                    BluetoothProfile.STATE_CONNECTING,
                    BluetoothProfile.STATE_DISCONNECTED,
                    BluetoothProfile.STATE_DISCONNECTING};

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            final BluetoothProfile.ServiceListener listener = new BluetoothProfile.ServiceListener() {
                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {

                    List<BluetoothDevice> list=       proxy.getDevicesMatchingConnectionStates(states);
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " profile  " +profile+
                            "  BluetoothProfile.ServiceListener  LIST !!  " +list);

                }

                @Override
                public void onServiceDisconnected(int profile) {
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " profile  " +profile);
                }
            };
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.A2DP);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.GATT);
            bluetoothAdapter.getProfileProxy(context,listener, BluetoothProfile.GATT_SERVER);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.HEADSET);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.HEALTH);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.SAP);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.HEARING_AID);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.HID_DEVICE);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.LE_AUDIO);
            bluetoothAdapter.getProfileProxy(context,listener,BluetoothProfile.HAP_CLIENT);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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



    // TODO: Запускаем Сервер GATT
    @SuppressLint("MissingPermission")
    public  void mainstartingServerScan() {
        try {
            Log.d(this.getClass().getName(), "1МетодЗапускаСканиваронияДляАндройд: Запускаем.... Метод Сканирования Для Android binder.isBinderAlive()  " + "\n+" +
                    ""  + " date " + new Date().toString().toString() + "" +
                    "\n" + " POOL " + Thread.currentThread().getName() +
                    "\n" + " ALL POOLS  " + Thread.getAllStackTraces().entrySet().size());
            // TODO: 26.01.2023 Сервер КОД зарпуска сервера
            // TODO: 31.07.2024
            getBluetoothGattServer = bluetoothManagerServer.openGattServer(context, new BluetoothGattServerCallback() {

                @Override
                public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                    // TODO: 22.07.2024
                    super.onConnectionStateChange(device, status, newState);
                    try {
// TODO: 25.07.2024 реакция на события Конеата и Деконтакта Устройств
                        МетодКоннектаДеконнектасКлиентамиGattScan(device, status, newState);

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " onConnectionStateChange " + new Date().toLocaleString());

                        // TODO: 31.07.2024 закрываепм сервер соединения
                        getBluetoothGattServer.cancelConnection(device);
                        // TODO: 29.07.2024

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                " onConnectionStateChange " + new Date().toLocaleString());
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

                @Override
                public void onServiceAdded(int status, BluetoothGattService service) {
                    // TODO: 22.07.2024
                    super.onServiceAdded(status, service);


                    ///TODO: SuccessAddDevice
                    Bundle bundleAddDeviceSuccess = new Bundle();
                    bundleAddDeviceSuccess.putString("Статус", "SERVERGATTRUNNIGSTARTING");



                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                }

                @SuppressLint("NewApi")
                @Override
                public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic,
                                                         boolean preparedWrite,
                                                         boolean responseNeeded, int offset, byte[] value) {
                    // TODO: 22.07.2024
                    super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
                    try {

                        // TODO: 29.07.2024
                        МетодОтвечаемКлиентуGatt(device, requestId, characteristic, offset, value);



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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }


                @Override
                public void onNotificationSent(BluetoothDevice device, int status) {
                    super.onNotificationSent(device, status);
                    try {
                        // TODO: 22.07.2024

                        /*    TODo*/
                        МетодПодтвержедиеЧтоОперацияУведомленияБыла(device, status);


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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onMtuChanged(BluetoothDevice device, int mtu) {
                    super.onMtuChanged(device, mtu);
                    Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());

                }

            });
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
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
            //TODO:
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }




    @SuppressLint({"MissingPermission", "NewApi"})
    private void langingGPSLocations( ) {
        try{

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            && locationManager.isLocationEnabled()){
                // TODO: 03.09.2024  получаем место нахожение
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                        36000,
                        10, Executors.newCachedThreadPool(),
                    new GattLocationListener(context, sharedPreferencesGatt,handler,version,  locationManager));

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER  " +
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER +"\n"+
                                " locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER "+
                                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
            }else {

                Toast toast = Toast.makeText(context, "Нет сети и локации !!! ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                toast.show();

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n" +
                        "locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER  " +
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER +"\n"+
                                " locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER "+
                                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));

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






    //TODO:  метод после Запуска Сервер добавдяем к ниму  BluetoothGattService
    @SuppressLint("MissingPermission")
    void settingGattServerBluetoothGattService()  {
        try{
            ///TODO  служебный xiaomi "BC:61:93:E6:F2:EB", МОЙ XIAOMI FC:19:99:79:D6:D4  //////      "BC:61:93:E6:E2:63","FF:19:99:79:D6:D4"
            // TODO: 07.08.2024
            if (getBluetoothGattServer!=null) {
                getBluetoothGattServer.clearServices();
            }
            // TODO: 12.02.2023 Адреса серверов для Клиентна
            BluetoothGattService service = new BluetoothGattService(getPublicUUIDScan, BluetoothGattService.SERVICE_TYPE_PRIMARY);
            // TODO: 12.02.2023 первый сервер
            BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(getPublicUUIDScan,
                    BluetoothGattCharacteristic.PROPERTY_READ |
                            BluetoothGattCharacteristic.PROPERTY_WRITE |
                            BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS |
                            BluetoothGattCharacteristic.PROPERTY_INDICATE |
                            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT |
                            BluetoothGattCharacteristic.PROPERTY_NOTIFY |
                            BluetoothGattCharacteristic.PROPERTY_BROADCAST,
                    BluetoothGattCharacteristic.PERMISSION_READ |
                            BluetoothGattCharacteristic.PERMISSION_WRITE |
                            BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED |
                            BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED);

            //TODO: Desctpior
            characteristic.addDescriptor(new BluetoothGattDescriptor(getPublicUUIDScan,
                    BluetoothGattCharacteristic.PERMISSION_READ |
                            BluetoothGattCharacteristic.PERMISSION_WRITE |
                            BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED |
                            BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED));
            service.addCharacteristic(characteristic);
            // TODO: 12.02.2023 добавлев в сервер
            getBluetoothGattServer.addService(service);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " getBluetoothGattServer " +getBluetoothGattServer );

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
            //TODO:
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }







    @SuppressLint({"NewApi", "SuspiciousIndentation", "MissingPermission"})
    private void callBackOtPhoneForServerGATT(@NonNull BluetoothDevice device,
                                              @NonNull int requestId,
                                              @NonNull int offset,
                                              @NonNull byte[] value,
                                              @NonNull BluetoothGattCharacteristic characteristicsServerОтКлиента) {
        String ПришлиДанныеОтКлиентаЗапрос = new String();
        String ДанныеСодранныеОтКлиента = new String();
        try {
            // TODO: 20.02.2023  Пришли ДАнные От Клиента
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString() + " value " + value);
            if (value.length > 0) {/*
                ПришлиДанныеОтКлиентаЗапрос = new String(value);

                Log.i(this.getClass().getName(), " " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString()
                        + " value " + value);


                Stream<String> streamgetDatOnAndroidGatt=Stream.of(ПришлиДанныеОтКлиентаЗапрос);
                List<String> listПришлиДанныеОтКлиентаЗапрос=    streamgetDatOnAndroidGatt.collect(Collectors.toList());

                //  List<String> listПришлиДанныеОтКлиентаЗапрос = Arrays.asList(ПришлиДанныеОтКлиентаЗапрос);

                Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " время " + new Date().toLocaleString() + " value " + value);

                // TODO: 07.02.2023  Записываем ВБАзу Данные{
                // TODO: 13.02.2023
                if (addressesgetGPS != null) {
                    ДанныеСодранныеОтКлиента = "Девайс отмечен..." + "\n" + device.getName().toString() +
                            "\n" + device.getAddress().toString() +
                            "\n" + new Date().toLocaleString()
                            + "\n" + ПришлиДанныеОтКлиентаЗапрос
                            + "\n" + "GPS"
                            + "\n" + "город: " + addressesgetGPS.get(0).getLocality()
                            + "\n" + "адрес: " + addressesgetGPS.get(0).getAddressLine(0)
                            + "\n" + "(корд1) " + addressesgetGPS.get(0).getLatitude()
                            + "\n" + "(корд2) " + addressesgetGPS.get(0).getLongitude();
                    characteristicsServerОтКлиента.setValue("SERVER#SousAvtoSuccess");

                    Log.i(this.getClass().getName(), "SERVER#SousAvtoSuccess GPS" + " " + new Date().toLocaleString());
                } else {
                    ДанныеСодранныеОтКлиента = "Девайс отмечен..." + "\n" + device.getName().toString() +
                            "\n" + device.getAddress().toString() +
                            "\n" + new Date().toLocaleString()
                            + "\n" + ПришлиДанныеОтКлиентаЗапрос;
                    characteristicsServerОтКлиента.setValue("SERVER#SousAvtoSuccess");

                    Log.i(this.getClass().getName(), "SERVER#SousAvtoSuccess ---" + " " + new Date().toLocaleString());
                }


                // TODO: 12.02.2023  ОТВЕТ !!!
                // TODO: 12.02.2023  ОТВЕТ !!!
                getBluetoothGattServer.notifyCharacteristicChanged(device, characteristicsServerОтКлиента, true);
                getBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristicsServerОтКлиента.toString().getBytes(StandardCharsets.UTF_8));
                // TODO: 13.02.2023  Метод Записи Девайса в базу





//TODO:ЗАписываем Новый Успешный Девайс в Базу от Gatt server
                ContentValues    contentValuesВставкаДанных = addToContevaluesNewSucceesDeviceOtGattServer(device, listПришлиДанныеОтКлиентаЗапрос);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  contentValuesВставкаДанных " +contentValuesВставкаДанных);



                Boolean   ТакоеВремяУжеЕсть =  getDateStoreOperationsDeviceFronDatabase("SELECT date_update    FROM scannerserversuccess" +
                        " WHERE  date_update = '"+contentValuesВставкаДанных.getAsString("date_update").trim()
                        +"' AND macdevice = '"+contentValuesВставкаДанных.getAsString("macdevice").trim() +"'");


                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  contentValuesВставкаДанных " +ТакоеВремяУжеЕсть);


                Integer   resultAddDeviceToGattaDtabse= 0;


                if (ТакоеВремяУжеЕсть==false) {
                    // TODO: 09.02.2023  запись в базу дивайса Отметка сотрдунка
                    resultAddDeviceToGattaDtabse = wtireNewSucceesDeviceOtGattServer(contentValuesВставкаДанных);
                }


                // TODO: 18.07.2024 ЕСЛИ Успещно прошла Операция передаем данные на Фрагмент Scanner
                if (resultAddDeviceToGattaDtabse >0) {

                    getcloseCursorAndHashMap();
                    //todo ДОполнительный механизм данные упокаываем в Канкаренте СЕТ с Курсором


                    ConcurrentHashMap<String,Cursor>        concurrentHashMapCursor = getallthedataofsuccessfuldevices("SELECT *    FROM scannerserversuccess");

                    Log.i(this.getClass().getName(), " resultAddDeviceToGattaDtabse " + resultAddDeviceToGattaDtabse +
                            " contentValuesВставкаДанных " + contentValuesВставкаДанных + " device.getAddress().toString() " +device.getAddress().toString()+
                            "  evice.getName().toString()  "+device.getName().toString()+ " concurrentHashMapCursor " +concurrentHashMapCursor);

                    // TODO: 19.07.2024 Посылаем Пользователю сообщение что данные изменились
                    forwardUIAfterSuccessAddDiveceDatBAseGatt(concurrentHashMapCursor, contentValuesВставкаДанных);


                }

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  ДанныеСодранныеОтКлиента " +ДанныеСодранныеОтКлиента);



            } else {
            */    Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время "
                    + new Date().toLocaleString() + " value " + value);
            }
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время "
                    + new Date().toLocaleString() + " value " + value);
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





    // TODO: 21.02.2023 Ответ Клиенту GATT send
    private synchronized void МетодОтвечаемКлиентуGatt(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, int offset, byte[] value) {
        try {
            BluetoothGattService services = characteristic.getService();
            if (services != null) {
                BluetoothGattCharacteristic characteristicsДляСерверОтКлиента = services.getCharacteristic(getPublicUUIDScan);
                if (characteristicsДляСерверОтКлиента != null && value != null) {
                    // TODO: 20.02.2023
                    atomicReferenceValue.set(value);
                    // TODO: 12.02.2023 ОТВЕТ КЛИЕНТУ
                    callBackOtPhoneForServerGATT(device, requestId, offset, atomicReferenceValue.get(), characteristicsДляСерверОтКлиента);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() +
                            "\n"+"characteristicsДляСерверОтКлиента" + characteristicsДляСерверОтКлиента +
                            " characteristicsДляСерверОтКлиента.getUuid() " + characteristicsДляСерверОтКлиента.getUuid() +
                            " atomicReferenceValue " +atomicReferenceValue.toString());

                }
            }
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
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







    @SuppressLint("MissingPermission")
    private synchronized void МетодПодтвержедиеЧтоОперацияУведомленияБыла(BluetoothDevice device, int status) {
        try{
            getBluetoothGattServer.sendResponse(device, status, BluetoothGatt.GATT_SUCCESS, status, "YOUR_RESPONSEonNotificationSent".getBytes(StandardCharsets.UTF_8));
            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() );
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

    // TODO: 21.02.2023 метод превоночального коннекта с устройством
    @SuppressLint("MissingPermission")
    private  void МетодКоннектаДеконнектасКлиентамиGattScan(BluetoothDevice device, int status, int newState) {
        try{
            Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v2.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            // TODO: 27.02.2023 Переопреляем Адамтер Bluetooth

            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    getBluetoothGattServer.connect(device,true);
                    ///TODO: SucceessAddDevice
                    Bundle    bundleAddDeviceSuccess = new Bundle();
                    bundleAddDeviceSuccess.putString("Статус","SERVERGATTConnectiong");
                    bundleAddDeviceSuccess.putString("Дивайс", device.getName());
                    bundleAddDeviceSuccess.putString("ОтветКлиентуВсатвкаВБАзу", "Пинг прошел ," + "\n" + "Без записи в базу !!!");

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "  device.getAddress() " +device.getAddress()+ " device.getName() "+device.getName());


                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;
                case BluetoothGatt.GATT_CONNECTION_CONGESTED:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;

                case BluetoothGatt.GATT_SERVER:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;

                case BluetoothGatt.GATT:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;
                case BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;
                case BluetoothGatt.GATT_FAILURE:
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);
                    break;

                // TODO: 25.07.2024
                default:{
                    Log.i(this.getClass().getName(), " onConnectionStateChange BluetoothProfile.STATE_DISCONNECTED "+  device.getAddress()+
                            "\n"+ "newState " + newState +  "status "+ status);

                }

            }
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    public String getNotificationChannel(NotificationManager notificationManager){
        String channelId = "channelid";
        String channelName =context.getResources().getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        // TODO: 03.09.2024
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        return channelId;
    }

    private void getContentProvider() throws InterruptedException {
        try{
            contentProviderServer=new ContentProviderServer();
            contentProviderServer.attachInfo(context,new ProviderInfo());
            contentProviderServer.onCreate();
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

    // TODO: 15.08.2024 end

}