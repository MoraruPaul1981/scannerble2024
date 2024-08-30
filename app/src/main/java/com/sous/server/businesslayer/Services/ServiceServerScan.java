package com.sous.server.businesslayer.Services;

import static android.app.job.JobInfo.PRIORITY_MAX;

import android.Manifest;
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
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;


import com.sous.server.R;
import com.sous.server.businesslayer.BI_Services.BucceslogincStartServiceGattServer;
import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;
import com.sous.server.businesslayer.Locations.GattLocationListener;
import com.sous.server.businesslayer.bl_Tests.GetBleAdvertising;


import org.greenrobot.eventbus.EventBus;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
@AndroidEntryPoint
public class ServiceServerScan extends Service {
    // TODO: 30.07.2024
    protected SQLiteDatabase sqLiteDatabase;
    public LocalBinderСерверBLE binderScan = new LocalBinderСерверBLE();
    private ContentProviderServer contentProviderServer;
    protected BluetoothGattServer getBluetoothGattServer;
    protected BluetoothManager bluetoothManagerServer;
    protected BluetoothAdapter bluetoothAdapter;
    protected  BluetoothAdapter bluetoothAdapterGATT;
    protected Long version = 0l;
    protected UUID  getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();
    protected LocationManager locationManager;
    protected      SharedPreferences sharedPreferencesGatt;
    protected    AtomicReference<byte[]> atomicReferenceValue = new AtomicReference<>();
    protected     Cursor successfuldevices;
    protected  NotificationCompat.Builder notificationBuilderServer;
    protected    Boolean getStatusEnableBlueadapter;

    protected      final  Handler handler=new Handler();


    @Inject
    GetBleAdvertising getBleAdvertising;

    @Inject
    BucceslogincStartServiceGattServer bucceslogincStartServiceGattServer;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());

            // TODO: 24.07.2024 устанвливаем разрешения
            //For creating the Foreground Service
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? getNotificationChannel(notificationManager) : "";
            notificationBuilderServer = new NotificationCompat.Builder(this, channelId);
            Notification notification = notificationBuilderServer.setOngoing(true)
                    .setSmallIcon(R.drawable.icon_bluetooth_start)
                    .setContentText("запуск:"+LocalDateTime.now().toString() )
                    .setContentTitle("Сервре Bluetooth")
                   .setPriority(PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                    .build();

            startForeground(111, notification);//


            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            sharedPreferencesGatt =              PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


            //TODO методы параменторв Службы Gaat
            launchmanagerBLE();//TODO: запускаем Новый Манаджер BTE

            //TODO:получаем Статус Адаптера Bluetooth true, false  и оптравляем статус в активти
             getStatusEnableBlueadapter =bucceslogincStartServiceGattServer. enableBluetoothAdapter(bluetoothAdapter,version,contentProviderServer);



            // TODO: 25.08.2024
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }







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
            Log.d(getApplicationContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " getStatusEnableBlueadapter " +getStatusEnableBlueadapter);


            if (getStatusEnableBlueadapter==true) {

                getContentProvider();

                langingGPSforGATTServer(  sharedPreferencesGatt);


                // TODO: 25.08.2024 TEST
                getBleAdvertising.staringBleAdvertising(bluetoothAdapter);


// TODO: 28.07.2024 LIster
                getListerBluetoothDevice();

                // TODO: 26.07.2024 starting Fragment Scan

                callBackFromServiceToRecyreViewFragment(getStatusEnableBlueadapter);


                //TODO :  главный метод службы запускаем Gatt Server


                mainstartingServerScan();

                //TODO:  для запущеного сервера Gatt ,дополвнительые параметры натсройки Charact and UUID
                settingGattServerBluetoothGattService();


                Log.d(getApplicationContext().getClass().getName(), "\n" + " class " +
                        Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }


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
  try{
      //TODO:получаем Статус Адаптера Bluetooth true, false  и оптравляем статус в активти
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

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                        3600,
                        10, new GattLocationListener(getApplicationContext(), sharedPreferencesGatt,  contentProviderServer));

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER  " +
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER +"\n"+
                                " locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER "+
                                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)));
            }else {

                Toast toast = Toast.makeText(getApplicationContext(), "Нет сети и локации !!! ", Toast.LENGTH_LONG);
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
        public ServiceServerScan getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceServerScan.this;
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
        return binderScan;

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
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterEnableScan");
            } else {
                sendFragmentparamentsScannerServer.setCurrentTask("bluetootAdapterDisabledScan");
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
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.A2DP);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.GATT);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener, BluetoothProfile.GATT_SERVER);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.HEADSET);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.HEALTH);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.SAP);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.HEARING_AID);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.HID_DEVICE);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.LE_AUDIO);
        bluetoothAdapter.getProfileProxy(getApplicationContext(),listener,BluetoothProfile.HAP_CLIENT);
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
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }
    }












    // TODO: Запускаем Сервер GATT
    @SuppressLint("MissingPermission")
    public  void mainstartingServerScan() {
        try {

            Log.d(this.getClass().getName(), "1МетодЗапускаСканиваронияДляАндройд: Запускаем.... Метод Сканирования Для Android binder.isBinderAlive()  " + "\n+" +
                    "" + binderScan.isBinderAlive() + " date " + new Date().toString().toString() + "" +
                    "\n" + " POOL " + Thread.currentThread().getName() +
                    "\n" + " ALL POOLS  " + Thread.getAllStackTraces().entrySet().size());
            // TODO: 26.01.2023 Сервер КОД зарпуска сервера
            // TODO: 31.07.2024
            getBluetoothGattServer = bluetoothManagerServer.openGattServer(getApplicationContext(), new BluetoothGattServerCallback() {

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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
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
                        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
    private  void МетодКоннектаДеконнектасКлиентамиGattScan(BluetoothDevice device, int status, int newState) {
        try{
            Vibrator v2 = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
















// TODO: 26.07.2024  END class

}