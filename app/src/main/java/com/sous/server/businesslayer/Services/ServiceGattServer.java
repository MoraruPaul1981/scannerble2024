package com.sous.server.businesslayer.Services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
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
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;


import com.sous.server.R;
import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;
import com.sous.server.businesslayer.Locations.GattLocationListener;


import org.greenrobot.eventbus.EventBus;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServiceGattServer extends Service {
    protected SQLiteDatabase sqLiteDatabase;
    public LocalBinderСерверBLE binderGatt = new LocalBinderСерверBLE();


    protected BluetoothGattServer getBluetoothGattServer;
    protected BluetoothManager bluetoothManagerServer;
    protected BluetoothAdapter bluetoothAdapter;
    protected  BluetoothAdapter bluetoothAdapterGATT;

    protected Long version = 0l;



    protected List<Address> addressesgetGPS;
    protected UUID getPublicUUIDGatt;


    //TODO: Local
    protected FusedLocationProviderClient fusedLocationClientGoogle;

    protected LocationManager locationManager;

    protected   ContentProviderServer contentProviderServer;

    protected      SharedPreferences sharedPreferencesGatt;


    protected ConcurrentHashMap<String,ContentValues> contentValuesConcurrentHashMap;



    private    AtomicReference<byte[]> atomicReferenceValue = new AtomicReference<>();



    private    Cursor successfuldevices;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());


            //For creating the Foreground Service
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? getNotificationChannel(notificationManager) : "";
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    // .setPriority(PRIORITY_MIN)
                    .setCategory(NotificationCompat.CATEGORY_SERVICE)
                    .build();

            startForeground(112, notification);



            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
          sharedPreferencesGatt = getSharedPreferences("gatt", Context.MODE_PRIVATE);

            contentValuesConcurrentHashMap=new ConcurrentHashMap<>();

          //TODO методы параменторв Службы Gaat
            launchmanagerBLE();//TODO: запускаем Новый Манаджер BTE


            getContentProvider();


            langingGPSforGATTServer(  sharedPreferencesGatt);



            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {

            //TODO:получаем Статус Адаптера Bluetooth true, false  и оптравляем статус в активти
            Boolean getStatusEnableBlueadapter = enableBluetoothAdapter();

            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " getStatusEnableBlueadapter " +getStatusEnableBlueadapter);


            // TODO: 26.07.2024 starting Fragment GATT
            callBackFromServiceToRecyreViewFragment(getStatusEnableBlueadapter);



            //TODO :  главный метод службы запускаем Gatt Server

            mainstartingServerGatt();

            //TODO:  для запущеного сервера Gatt ,дополвнительые параметры натсройки Charact and UUID
            settingGattServerBluetoothGattService();


            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");




            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  getStatusEnableBlueadapter " + getStatusEnableBlueadapter);


// TODO: 30.06.2022 сама не постредствено запуск метода
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }


        return START_STICKY;
      //  return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        try {
            Log.d(getApplicationContext().getClass().getName(), "\n"
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }



    @SuppressLint({"MissingPermission", "NewApi"})
    private void langingGPSforGATTServer(@NonNull SharedPreferences sharedPreferencesGatt) {
        try{

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                    2000,
                    10, new GattLocationListener(getApplicationContext(), sharedPreferencesGatt,  contentProviderServer));
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    }


















    private void getContentProvider() throws InterruptedException {
        try{
          contentProviderServer=new ContentProviderServer();
        contentProviderServer.attachInfo(getApplicationContext(),new ProviderInfo());
        contentProviderServer.onCreate();

            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    }

    public class LocalBinderСерверBLE extends Binder {
        public ServiceGattServer getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceGattServer.this;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        //   return super.onBind(intent);
        return binderGatt;

    }







    @SuppressLint("MissingPermission")
    private void launchmanagerBLE() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            bluetoothManagerServer = (BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = (BluetoothAdapter) bluetoothManagerServer.getAdapter();


            Log.d(getApplicationContext().getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }




//TODO: отвечам оюратно на фрагмент что включен Адаптер Blutooth
    private void callBackFromServiceToRecyreViewFragment(@NonNull    Boolean getStatusEnableBlueadapter) {
        try{

            ParamentsScannerServer sendFragmentparamentsScannerServer=new ParamentsScannerServer();
            sendFragmentparamentsScannerServer.setФлагЗапускаФрагментRecyreView(getStatusEnableBlueadapter);

            if (getStatusEnableBlueadapter) {
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterEnableGatt");
            } else {
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterDisabledGatt");
            }
            //TODO: послымаем Из Службы Значение на Фрагмент
            MessageScannerServer sendmessageScannerStartRecyreViewFragment= new MessageScannerServer( sendFragmentparamentsScannerServer);
            //TODO: ответ на экран работает ообрубование или нет
                EventBus.getDefault().post(sendmessageScannerStartRecyreViewFragment);

            Log.d(getApplicationContext().getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }



























//TODO: Включаем адаптер is Enabled BLE
    @SuppressLint("MissingPermission")
    private Boolean enableBluetoothAdapter() {
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
        return  getStatusEnableBlueadapter ;
    }







    // TODO: Запускаем Сервер GATT
    @SuppressLint("MissingPermission")
    public  void mainstartingServerGatt() {
        try {

            Log.d(this.getClass().getName(), "1МетодЗапускаСканиваронияДляАндройд: Запускаем.... Метод Сканирования Для Android binder.isBinderAlive()  " + "\n+" +
                    "" + binderGatt.isBinderAlive() + " date " + new Date().toString().toString() + "" +
                    "\n" + " POOL " + Thread.currentThread().getName() +
                    "\n" + " ALL POOLS  " + Thread.getAllStackTraces().entrySet().size());
            // TODO: 26.01.2023 Сервер КОД
            getBluetoothGattServer = bluetoothManagerServer.openGattServer(getApplicationContext(), new BluetoothGattServerCallback() {

                @Override
                public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                    // TODO: 22.07.2024
                    super.onConnectionStateChange(device, status, newState);
                    try {


                        МетодКоннектаДеконнектасКлиентамиGatt(device, status, newState);

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );


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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
                    }
                }

                @Override
                public void onServiceAdded(int status, BluetoothGattService service) {
                    // TODO: 22.07.2024
                    super.onServiceAdded(status, service);

                    Vibrator v2 = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    v2.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));

                    ///TODO: SuccessAddDevice
                    Bundle    bundleAddDeviceSuccess = new Bundle();
                    bundleAddDeviceSuccess.putString("Статус", "SERVERGATTRUNNIGSTARTING");



                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


                }

                @SuppressLint("NewApi")
                @Override
                public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic,
                                                         boolean preparedWrite,
                                                         boolean responseNeeded, int offset, byte[] value) {
                    // TODO: 22.07.2024
                    super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
                    try {
                        МетодОтвечаемКлиентуGatt(device, requestId, characteristic, offset, value);



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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
                    }
                }








                @Override
                public void onNotificationSent(BluetoothDevice device, int status) {
                    // TODO: 22.07.2024
                    super.onNotificationSent(device, status);
                    try {
                    /*    TODo*/
                        МетодПодтвержедиеЧтоОперацияУведомленияБыла(device, status);


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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
                    }
                }

                @Override
                public void onMtuChanged(BluetoothDevice device, int mtu) {
                    super.onMtuChanged(device, mtu);
                    Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
                }

            });


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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }


                //TODO:  метод после Запуска Сервер добавдяем к ниму  BluetoothGattService
            @SuppressLint("MissingPermission")
        void settingGattServerBluetoothGattService()  {
        try{
            ///TODO  служебный xiaomi "BC:61:93:E6:F2:EB", МОЙ XIAOMI FC:19:99:79:D6:D4  //////      "BC:61:93:E6:E2:63","FF:19:99:79:D6:D4"
            // TODO: 12.02.2023 Адреса серверов для Клиентна
            getPublicUUIDGatt = ParcelUuid.fromString("10000000-0000-1000-8000-00805f9b34fb").getUuid();
            BluetoothGattService service = new BluetoothGattService(getPublicUUIDGatt, BluetoothGattService.SERVICE_TYPE_PRIMARY);
            // TODO: 12.02.2023 первый сервер
            BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(getPublicUUIDGatt,
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
            characteristic.addDescriptor(new BluetoothGattDescriptor(getPublicUUIDGatt,
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        }









    /////TODO: код Вытаскиваем из общего метоада

    @NonNull
    private Long МетодГенерацииUUID() {
        Long getUUID = 0l;
        try{

        UUID uuid = UUID.randomUUID();

        //uuid   .toString().replaceAll("-", "").replaceAll("[a-zA-Z]", "").substring(0, 20);
        ///uuid = uuid.replaceAll("[a-zA-Z]", "");
        //uuid= CharMatcher.any().replaceFrom("[A-Za-z0-9]", "");
        Long fff2=  uuid.getLeastSignificantBits();
        Long fff3=  uuid.getMostSignificantBits();
        BigInteger bigInteger=BigInteger.valueOf(fff3).abs();
        getUUID= bigInteger.longValue();
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                " uuid " +uuid );

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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
        return getUUID;
    }


    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private ContentValues addToContevaluesNewSucceesDeviceOtGattServer(@NonNull BluetoothDevice device,
                                                      @NonNull List<String> listПришлиДанныеОтКлиентаЗапрос) {
        ContentValues   contentValuesВставкаДанных =   new ContentValues();;
        try {
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время "
                    + new Date().toLocaleString() + " listПришлиДанныеОтКлиентаЗапрос " + listПришлиДанныеОтКлиентаЗапрос);


         List<String> getStream= Stream.of(listПришлиДанныеОтКлиентаЗапрос.get(0).replaceAll("^\\[|\\]$", "").split(",")).collect(Collectors.toList());



            // TODO: 08.02.2023 Добавляем Данные для Записи в базу через Адаптер ContentValues
            contentValuesВставкаДанных.put("macdevice", device.getAddress().toString());
            contentValuesВставкаДанных.put("namedevice", device.getName().toString());

            contentValuesВставкаДанных.put("iemi", getStream.get(0).toString());
            contentValuesВставкаДанных.put("date_update", getStream.get(1).toString());
            contentValuesВставкаДанных.put("completedwork", getStream.get(2).toString());
            contentValuesВставкаДанных.put("operations", getStream.get(2).toString());



            contentValuesВставкаДанных.put("city",    sharedPreferencesGatt.getString("getLocality",""));
            contentValuesВставкаДанных.put("gps1", sharedPreferencesGatt.getString("getLongitude",""));
            contentValuesВставкаДанных.put("gps2", sharedPreferencesGatt.getString("getLatitude",""));
            contentValuesВставкаДанных.put("adress",  sharedPreferencesGatt.getString("getCountryName","")+" "+
                    sharedPreferencesGatt.getString("getLocality","")+" "+
                    sharedPreferencesGatt.getString("getSubAdminArea","")+" "+
                    sharedPreferencesGatt.getString("getLatitude","")+" "+
                    sharedPreferencesGatt.getString("getLongitude","")+" "+
                    sharedPreferencesGatt.getString("getLocale","")+" "+
                    sharedPreferencesGatt.getString("getThoroughfare","")+" "+
                    sharedPreferencesGatt.getString("getSubThoroughfare","")+" " );




            // TODO: 10.02.2023 версия данных
            // TODO: 10.02.2023 версия данных
            Integer current_table = МетодПоискДАнныхПоБазе("SELECT MAX ( current_table  ) AS MAX_R  FROM scannerserversuccess");
            contentValuesВставкаДанных.put("current_table", current_table);



            Integer version = МетодПоискДАнныхПоБазе("SELECT MAX ( version  ) AS MAX_R  FROM scannerserversuccess");
            contentValuesВставкаДанных.put("version", version);


            Long getuuid = МетодГенерацииUUID();
            contentValuesВставкаДанных.put("uuid", getuuid.toString());



                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " contentValuesВставкаДанных " +contentValuesВставкаДанных );


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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return contentValuesВставкаДанных;
    }

    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private Integer wtireNewSucceesDeviceOtGattServer(@NonNull   ContentValues   contentValuesВставкаДанныхGattServer) {
        Uri    resultAddDeviceToGattaDtabse = null;
        try {
                Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
                resultAddDeviceToGattaDtabse=   contentProviderServer.insert(uri, contentValuesВставкаДанныхGattServer);

                Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " resultAddDeviceToGattaDtabse " +resultAddDeviceToGattaDtabse);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " resultAddDeviceToGattaDtabse " +resultAddDeviceToGattaDtabse+ " contentValuesВставкаДанныхGattServer " +contentValuesВставкаДанныхGattServer );


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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return Integer.parseInt(resultAddDeviceToGattaDtabse.toString() );
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
            if (value.length > 0) {
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
                Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время "
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }

    @SuppressLint("MissingPermission")
    private void forwardUIAfterSuccessAddDiveceDatBAseGatt(@NonNull ConcurrentHashMap<String,Cursor> concurrentHashMapCursor ,
                                                           @NonNull   ContentValues    contentValuesВставкаДанныхGaTT) {
        try{

            Vibrator v2 = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            v2.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));


            //TODO:Event Send To Fragment Boot After Success DataBase and Divece
            sendStatusSucessEventBusDevece(contentValuesВставкаДанныхGaTT, concurrentHashMapCursor);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " contentValuesВставкаДанныхGaTT " + contentValuesВставкаДанныхGaTT);

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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    }


    private   void sendStatusSucessEventBusDevece(@NonNull   ContentValues    contentValuesВставкаДанныхGaTT,
                                                  @NonNull  ConcurrentHashMap<String,Cursor> concurrentHashMapCursor) {
        try{
        ParamentsScannerServer sendFragmentparamentsScannerServer=new ParamentsScannerServer();
            // TODO: 18.07.2024 sending status
        sendFragmentparamentsScannerServer.setCurrentTask("SuccessDeviceBluetoothAnServerGatt");


        // TODO: 18.07.2024 sending HashMap
        contentValuesConcurrentHashMap.compute(contentValuesВставкаДанныхGaTT.getAsString("macdevice").toString(),(x,y)->contentValuesВставкаДанныхGaTT);
        sendFragmentparamentsScannerServer.setContentValuesConcurrentHashMap(contentValuesConcurrentHashMap);

            // TODO: 18.07.2024 sending Cursor
        sendFragmentparamentsScannerServer.setConcurrentHashMapCursor( concurrentHashMapCursor);

        //TODO: послымаем Из Службы Значение на Фрагмент
        MessageScannerServer sendmessageScannerStartRecyreViewFragment= new MessageScannerServer( sendFragmentparamentsScannerServer);

        //TODO: ответ на экран работает ообрубование или нет
        EventBus.getDefault().post(sendmessageScannerStartRecyreViewFragment);

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                 " contentValuesConcurrentHashMap " +contentValuesConcurrentHashMap +
                " concurrentHashMapCursor " +concurrentHashMapCursor+" contentValuesВставкаДанныхGaTT.getAsString(\"macdevice\") ");

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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }

    }


    // TODO: 21.02.2023 Ответ Клиенту GATT send
    private synchronized void МетодОтвечаемКлиентуGatt(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, int offset, byte[] value) {
        try {
            BluetoothGattService services = characteristic.getService();
            if (services != null) {
                BluetoothGattCharacteristic characteristicsДляСерверОтКлиента = services.getCharacteristic(getPublicUUIDGatt);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);

    }
    }

    // TODO: 21.02.2023 метод превоночального коннекта с устройством
    @SuppressLint("MissingPermission")
    private synchronized void МетодКоннектаДеконнектасКлиентамиGatt(BluetoothDevice device, int status, int newState) {
        try{
        Vibrator v2 = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            // TODO: 27.02.2023 Переопреляем Адамтер Bluetooth

        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                    getBluetoothGattServer.connect(device,true);
                ///TODO: SucceessAddDevice
                Bundle    bundleAddDeviceSuccess = new Bundle();
                bundleAddDeviceSuccess.putString("Статус","SERVERGATTConnectiong");
                bundleAddDeviceSuccess.putString("Дивайс", device.getName());
                bundleAddDeviceSuccess.putString("ОтветКлиентуВсатвкаВБАзу", "Пинг прошел ," + "\n" + "Без записи в базу !!!");

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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    }










    // TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public  Integer МетодПоискДАнныхПоБазе(@NonNull String СамЗапрос) {
        Integer   ВерсияДАнных = 0;
        try{
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );



            Cursor cursorПолучаемДЛяСевреа = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);


            cursorПолучаемДЛяСевреа.moveToFirst();
             if (cursorПолучаемДЛяСевреа.getCount()>0){
                 ВерсияДАнных=      cursorПолучаемДЛяСевреа.getInt(0);
                 Log.i(this.getClass().getName(), "ВерсияДАнных"+ ВерсияДАнных) ;
                 ВерсияДАнных++;
             }
            Log.w(getApplicationContext().getClass().getName(), " РЕЗУЛЬТАТ insertData  cursorПолучаемДЛяСевреа  " +  cursorПолучаемДЛяСевреа.toString() );
            cursorПолучаемДЛяСевреа.close();
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ВерсияДАнных;
    }



    public   Boolean      getDateStoreOperationsDeviceFronDatabase(@NonNull String СамЗапрос) {
        Boolean   ТакоеВремяУжеЕсть  = false;
        try{
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
            Cursor cursorПолучаемДЛяСевреа = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);
            if (cursorПолучаемДЛяСевреа.getCount()>0){
                cursorПолучаемДЛяСевреа.moveToFirst();
               String ВремяДАнных=      cursorПолучаемДЛяСевреа.getString(0);
                Log.i(this.getClass().getName(), "ВремяДАнных"+ ВремяДАнных) ;
                ТакоеВремяУжеЕсть=true;
            }
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " cursorПолучаемДЛяСевреа.getCount() " +cursorПолучаемДЛяСевреа.getCount());
            // TODO: 19.07.2024 closing
            cursorПолучаемДЛяСевреа.close();

            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " ТакоеВремяУжеЕсть " +ТакоеВремяУжеЕсть);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ТакоеВремяУжеЕсть;
    }

// TODO: 15.03.2023 синхрониазция КЛАсс
// TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
public   ConcurrentHashMap<String,Cursor>  getallthedataofsuccessfuldevices(@NonNull String СамЗапрос) {
 //TODO
    ConcurrentHashMap<String,Cursor> cursorConcurrentHashMapGatt=new ConcurrentHashMap<>();
    try{

        Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
        successfuldevices = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);
        if (successfuldevices.getCount()>0){
            successfuldevices.move(successfuldevices.getCount());
            // TODO: 19.07.2024  Запаопление данными Курсора
            cursorConcurrentHashMapGatt.compute("Cursor",(x,y)->successfuldevices);
        }
        // TODO: 19.07.2024 closing
        Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " successfuldevices  " +successfuldevices+
                 " cursorConcurrentHashMapGatt " +cursorConcurrentHashMapGatt);
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    return  cursorConcurrentHashMapGatt;
}

    private void getcloseCursorAndHashMap() {
        try {
            if (successfuldevices != null) {
                if (successfuldevices.isClosed() == false) {
                    successfuldevices.close();
                }
            }
            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " successfuldevices  " + successfuldevices +
                    " successfuldevices " + successfuldevices);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private String getNotificationChannel(NotificationManager notificationManager){
        String channelId = "channelid";
        String channelName = getResources().getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }


    // TODO: 26.07.2024  end CLASS
}