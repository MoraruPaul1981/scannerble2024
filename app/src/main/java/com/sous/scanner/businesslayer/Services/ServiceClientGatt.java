package com.sous.scanner.businesslayer.Services;

import static java.util.stream.Collectors.groupingBy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.onesignal.OneSignal;
import com.sous.scanner.businesslayer.Broadcastreceiver.bl_BloadcastReceierGatt;
import com.sous.scanner.businesslayer.Firebase.MyFirebaseMessagingServiceScanner;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_forServices.Bl_froSetviceBLE;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ServiceClientGatt extends IntentService {


    public LocalBinderСканнер binder = new LocalBinderСканнер();
    private Context context;
    private Activity activity;
    private String TAG;
    private Handler handler;

    private BluetoothManager bluetoothManagerServer;
    private BluetoothAdapter bluetoothAdapterPhoneClient;
    private BluetoothAdapter bluetoothAdapterGATT;
    protected LocationManager locationManager;


    private MutableLiveData<ConcurrentHashMap<String,String>> mediatorLiveDataGATT;
    private Long version = 0l;
    private String getWorkerStateClient;
    private UUID getPublicUUID;
    private BluetoothGatt gatt;




    public ServiceClientGatt() {
        super("ServiceClientGatt");
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 07.02.2023 клиент сервер
        try {

            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            bluetoothManagerServer = (BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapterPhoneClient = (BluetoothAdapter) bluetoothManagerServer.getAdapter();


            setingEnableApapterBLE();
            getListDeviceForBluApdapter();

            TAG = getClass().getName().toString();
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();


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

    public class LocalBinderСканнер extends Binder {
        public ServiceClientGatt getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceClientGatt.this;
        }

        public void linkToDeath(DeathRecipient deathRecipient) {
            Log.i(this.getClass().getName(), "deathRecipient " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время " + new Date().toLocaleString());
            deathRecipient.binderDied();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(context.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        //   return super.onBind(intent);
        return binder;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        try {
            Log.d(context.getClass().getName(), "\n"
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(context.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " УДАЛЕНИЕ СТАТУСА Удаленная !!!!!" + "\n" +
                    " УДАЛЕНИЕ СТАТУСА Удаленная !!!!! " + "\n" +
                    " УДАЛЕНИЕ СТАТУСА Удаленная !!!!!   Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            this.context = getApplicationContext();
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d(newBase.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + newBase.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        this.context = newBase;
        super.attachBaseContext(newBase);
    }

    // TODO: 30.11.2022 сервер СКАНИРОВАНИЯ
    public void МетодКлиентЗапускСканера(@NonNull Handler handler, @NonNull Activity activity,
                                         @NonNull MutableLiveData<ConcurrentHashMap<String,String>> mediatorLiveDatagatt,
                                         @NonNull String ДействиеДляСервераGATTОТКлиента) {
        this.context = activity;
        this.activity = activity;
        this.handler = handler;
        this.mediatorLiveDataGATT = mediatorLiveDatagatt;
        this.getWorkerStateClient = ДействиеДляСервераGATTОТКлиента;
        // TODO: 08.12.2022 уснатавливаем настройки Bluetooth
        try {

            // TODO: 27.02.2023 Переплучние Bluettoth
            МетодЗапускаСканированиеКлиент();

            Log.w(this.getClass().getName(), "   bluetoothManager  " + bluetoothManagerServer + " bluetoothAdapter "
                    + bluetoothAdapterPhoneClient + "mediatorLiveDataGATT " + mediatorLiveDataGATT);

            Log.w(this.getClass().getName(), "   bluetoothManager  " + bluetoothManagerServer + " bluetoothAdapter "
                    + bluetoothAdapterPhoneClient + "mediatorLiveDataGATT " + mediatorLiveDataGATT);
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
    // TODO: 08.12.2022 Метод Сервер


    @SuppressLint({"MissingPermission"})
    private void МетодЗапускаСканированиеКлиент() {
        try {

            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
            concurrentHashMap  .put("GATTCLIENTProccessing","1");
            mediatorLiveDataGATT.setValue(concurrentHashMap);

            // TODO: 12.02.2023 адреса разыне колиентов
            getPublicUUID = ParcelUuid.fromString("10000000-0000-1000-8000-00805f9b34fb").getUuid();

                //TODO:
                Set<BluetoothDevice> deviceClientGattEnable = bluetoothAdapterGATT.getBondedDevices();

                if (deviceClientGattEnable.size() > 0) {
                    ///TODO: Когда есть реальные Девайсы BLE
                    loopAllDiveceFotConnecting(getPublicUUID, deviceClientGattEnable);

                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            " deviceClientGattEnable " + deviceClientGattEnable);
                } else {


                    ConcurrentSkipListSet<String> concurrentSkipListSetMunualListServerDevice = new ConcurrentSkipListSet();
                    concurrentSkipListSetMunualListServerDevice.add("98:2F:F8:19:BC:F7");
                    concurrentSkipListSetMunualListServerDevice.forEach(new java.util.function.Consumer<String>() {
                        @Override
                        public void accept(String remoteManualServerGatt) {
                            ///TODO:Довавляем Зарание созданные Адреса Сервера Gatt
                            BluetoothDevice bluetoothDevice = bluetoothAdapterPhoneClient.getRemoteDevice(remoteManualServerGatt);//TODO: HUAWEI MatePad SE
                            // TODO: 26.07.2024
                            // TODO: 26.07.2024
                            int connectionState = bluetoothManagerServer.getConnectionState(bluetoothDevice, BluetoothProfile.GATT);

                            ///TODO: когда ессть сами устройста Manual
                            manualDiveceFotConnecting(getPublicUUID, bluetoothDevice);

                            Log.d(this.getClass().getName(), "\n" + " class " +
                                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n" +
                                    " deviceClientGattEnable " + deviceClientGattEnable + " connectionState " +connectionState);
                        }
                    });
                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n" +
                            " deviceClientGattEnable " + deviceClientGattEnable);

                }


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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    //TODO запускаем Когда Адапетр BLE РЕАЛЬНЫЕ
    @SuppressLint("MissingPermission")
    private void loopAllDiveceFotConnecting(UUID stringUUIDEntry, Set<BluetoothDevice> deviceClientGatt) {
        ///TODO: оБработка реальный адресов BLE
        try {
            deviceClientGatt.forEach(new java.util.function.Consumer<BluetoothDevice>() {

                @Override
                public void accept(BluetoothDevice bluetoothDevice) {

                    Log.d(this.getClass().getName(), " bluetoothDevice " + bluetoothDevice);


                    Log.d("BT", "bluetoothDevice.getName(): " + bluetoothDevice.getName());
                    Log.d("BT", "bluetoothDevice.getAddress(): " + bluetoothDevice.getAddress());

                    // TODO: 12.02.2023  запускаем задачу в потоке
                    BluetoothGattCallback bluetoothGattCallback =
                            МетодРаботыСТекущийСерверомGATT(bluetoothDevice, stringUUIDEntry );

                    // TODO: 26.01.2023  конец сервера GATT
                    МетодЗапускаGATTКлиента(bluetoothDevice, bluetoothGattCallback);

                    Log.d(TAG, "  МетодЗапускаЦиклаСерверовGATT().... "
                            + "uuidКлючСервераGATTЧтениеЗапись " + getPublicUUID + " bluetoothGattCallback " + bluetoothGattCallback);
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
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }








    //TODO запускаем Когда Адапетр BLE ИСКУСТВЕННЫЕ АДРЕС сервера
    @SuppressLint("MissingPermission")
    private void manualDiveceFotConnecting( UUID  getPublicUUID, BluetoothDevice deviceClientGatt) {
        ///TODO: оБработка реальный адресов BLE
        try{


                Log.d(this.getClass().getName()," bluetoothDevice " +deviceClientGatt  );

                Log.d("BT", "bluetoothDevice.getName(): " + deviceClientGatt.getName());
                Log.d("BT", "bluetoothDevice.getAddress(): " + deviceClientGatt.getAddress());




                // TODO: 12.02.2023  запускаем задачу в потоке
                BluetoothGattCallback bluetoothGattCallback=
                        МетодРаботыСТекущийСерверомGATT(deviceClientGatt, getPublicUUID);




                // TODO: 26.01.2023  конец сервера GATT
                МетодЗапускаGATTКлиента(deviceClientGatt, bluetoothGattCallback);

                Log.d(TAG, "  МетодЗапускаЦиклаСерверовGATT().... "
                        +"uuidКлючСервераGATTЧтениеЗапись " + getPublicUUID + " bluetoothGattCallback " +bluetoothGattCallback);
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
    private void setingEnableApapterBLE() {
        try{
                if (bluetoothAdapterPhoneClient !=null) {
                    if (bluetoothAdapterPhoneClient.isEnabled()==false){
                        bluetoothAdapterPhoneClient.enable();
                    }





                    Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString()+
                             " bluetoothAdapter  "+ bluetoothAdapterPhoneClient);
                }

            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString()+
                     " bluetoothAdapter " + bluetoothAdapterPhoneClient);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        ContentValues valuesЗаписываемОшибки=new ContentValues();
        valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
        valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
        valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
        valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
        final Object ТекущаяВерсияПрограммы = version;
        Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
        valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
        new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }


    @SuppressLint("MissingPermission")
    private void getListDeviceForBluApdapter() {
        try{
            bluetoothAdapterGATT=  bluetoothManagerServer.getAdapter() ;
            if (bluetoothAdapterGATT!=null) {
                bluetoothAdapterGATT.getBondedDevices().forEach(new java.util.function.Consumer<BluetoothDevice>() {
                    @Override
                    public void accept(BluetoothDevice bluetoothDevice) {
                        Log.d("BT", "bluetoothDevice.getName(): " + bluetoothDevice.getName());
                        Log.d("BT", "bluetoothDevice.getAddress(): " + bluetoothDevice.getAddress());

                        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    }
                });
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString()+
                    " bluetoothAdapter " + bluetoothAdapterPhoneClient);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }




    @SuppressLint("MissingPermission")
                private BluetoothGattCallback МетодРаботыСТекущийСерверомGATT(@NonNull  BluetoothDevice bluetoothDevice,@NonNull  UUID UuidГлавныйКлючСерверGATT) {
                    // TODO: 25.01.2023 ПЕРВЫЙ ВАРИАНТ СЕРВЕР gatt
           BluetoothGattCallback       bluetoothGattCallback = null;
        try{

                                bluetoothGattCallback = new BluetoothGattCallback() {
                        @Override
                        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                            int newState) {
                            try{
                                switch (newState){

                                    case BluetoothProfile.STATE_CONNECTED :
                                        Log.i(TAG, "Connected to GATT client. BluetoothProfile.STATE_CONNECTED ###1 onConnectionStateChange  " +
                                                ""+new Date().toLocaleString());
                                        handler.post(()->{
                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put("BluetoothProfile.STATE_CONNECTED","2");
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        });
                                         Boolean ДанныеОТGATTССевромGATT=         gatt.discoverServices();
                                        Log.d(TAG, "Trying to ДанныеОТGATTССевромGATT " + ДанныеОТGATTССевромGATT + " newState " +newState);
                                        break;

                                    case BluetoothProfile.STATE_DISCONNECTED :
                                       /* handler.post(()->{
                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put("BluetoothProfile.STATE_DISCONNECTED","3");
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        });*/
                                       new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                        Log.d(TAG, "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                                        break;


                                    case BluetoothGatt.GATT_FAILURE:
                                        handler.post(()->{
                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put("BluetoothGatt.GATT_FAILURE","4");
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        });
                                        new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                        Log.d(TAG, "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                                        break;


                                    case BluetoothGatt.GATT_CONNECTION_CONGESTED :
                                        handler.post(()->{
                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put("BluetoothGatt.GATT_CONNECTION_CONGESTED","5");
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        });
                                        new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                        Log.d(TAG, "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                                        break;


                                    case 133 :
                                        // TODO: 16.07.2024 когда ошивка разрываем сообщение  
                                        new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                        Log.d(TAG, "Trying to ДанныеОТGATTССевромGATT "  + " newState " +newState);
                                        break;

                                    default:{
                                        Log.d(TAG, "Trying to ДанныеОТGATTССевромGATT "  + " newState " +newState);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                ContentValues valuesЗаписываемОшибки=new ContentValues();
                                valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                                valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                                valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                                valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                                final Object ТекущаяВерсияПрограммы = version;
                                Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                                valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                                new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                            }
                        }
                        // TODO: 26.01.2023
                        @Override
                        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                            super.onServicesDiscovered(gatt, status);
                            try{
                                if (status == BluetoothGatt.GATT_SUCCESS) {
                                    BluetoothGattService services = gatt.getService(UuidГлавныйКлючСерверGATT);
                                    if (services!=null) {
                                        Boolean КоннектССевромGATT = gatt.connect();

                                       gatt.beginReliableWrite();

                                        Log.d(TAG, "Trying КоннектССевромGATT " + КоннектССевромGATT);
                                        BluetoothGattCharacteristic characteristics = services.getCharacteristic(getPublicUUID);
                                        gatt.setCharacteristicNotification(characteristics, true);
                                        characteristics.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                                        if (characteristics != null) {
                                            characteristics.setValue("действие:" + getWorkerStateClient);
                                            // TODO: 20.02.2023  заполняем данными  клиента
                                            ConcurrentSkipListSet<String> linkedHashMapДанныеКлиентаДляGATT = МетодЗаполенияДаннымиКлиентаДЛяGAtt();
                                            //TODO add Data For GATT Client
                                            characteristics.setValue(linkedHashMapДанныеКлиентаДляGATT.toString());
                                            // TODO: 20.02.2023  послылвем Сервреу Данные
                                            Boolean successОтправка = gatt.writeCharacteristic(characteristics);

                                            if (successОтправка) {
                                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                                concurrentHashMap  .put("5","SuccessWorkerGattClientWithServer");
                                                concurrentHashMap  .put("callBackSeceesDataOtServer",linkedHashMapДанныеКлиентаДляGATT.toString());



                                                mediatorLiveDataGATT.setValue(concurrentHashMap);
                                                gatt.executeReliableWrite();
                                            }else {
                                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                                concurrentHashMap  .put("6","ErrorWorkerGattClientWithServer");
                                                mediatorLiveDataGATT.setValue(concurrentHashMap);
                                                gatt.abortReliableWrite();

                                            }

                                            Log.i(TAG, "characteristics" + new Date().toLocaleString()+  " characteristics "
                                                    +characteristics+ " successОтправка " +successОтправка+
                                                    " ДействиеДляСервераGATTОТКлиента "+ getWorkerStateClient);
                                        }
                                    }else {
                                        ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                        concurrentHashMap  .put("7","ErrorWorkerGattClientWithServer");
                                        mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        Log.i(TAG, "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());

                                    }
                                }else{
                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put("BluetoothDevice.BOND_NONE","8");
                                    mediatorLiveDataGATT.setValue(concurrentHashMap);
                                    Log.i(TAG, "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                ContentValues valuesЗаписываемОшибки=new ContentValues();
                                valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                                valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                                valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                                valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                                final Object ТекущаяВерсияПрограммы = version;
                                Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                                valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                                new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                            }
                        }

                        // TODO: 20.02.2023 Метод Вытаскиеваем ДАнные Симки пользователя  
                        @NonNull
                        private ConcurrentSkipListSet<String> МетодЗаполенияДаннымиКлиентаДЛяGAtt() {
                            ConcurrentSkipListSet<String> linkedHashMapДанныеКлиентаДляGATT = new ConcurrentSkipListSet<>();
                            try {
                                //TODO :  отправлдяем данные
                               linkedHashMapДанныеКлиентаДляGATT.add(   getWorkerStateClient +"\n");
                                String getIMEI = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                                linkedHashMapДанныеКлиентаДляGATT.add(  getIMEI+"\n");
                                linkedHashMapДанныеКлиентаДляGATT.add(  new Date().toLocaleString()+"\n");
                                Log.i(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()+
                                        " время " +new Date().toLocaleString() +
                                        getIMEI + " getIMEI "+ " linkedHashMapДанныеКлиентаДляGATT " +linkedHashMapДанныеКлиентаДляGATT);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            ContentValues valuesЗаписываемОшибки=new ContentValues();
                            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                            final Object ТекущаяВерсияПрограммы = version;
                            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                        }
                            return linkedHashMapДанныеКлиентаДляGATT;
                        }

                        @Override
                        public void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value, int status) {
                            super.onCharacteristicRead(gatt, characteristic, value, status);
                            byte[] newValueПришлиДАнныеОтКлиента= characteristic.getValue();
                            Log.i(TAG, "Connected to GATT server  newValueПришлиДАнныеОтКлиента."+new String(newValueПришлиДАнныеОтКлиента));
                        }

                        @Override
                        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                            super.onCharacteristicChanged(gatt, characteristic);
                            try{
                                if (characteristic!=null) {
                                    byte[] newValueОтветОтСервера = characteristic.getValue();
                                    if (newValueОтветОтСервера!=null) {
                                        String ОтветОтСервераОбратно=new String(newValueОтветОтСервера);
                                        Log.i(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                                + " время " +new Date().toLocaleString()+ " ОтветОтСервераОбратно "+ОтветОтСервераОбратно );
                                        // TODO: 30.01.2023  ПОСЫЛАЕМ ОТВЕТ ОТ СЕРВЕРА СТАТУСА
                                        handler.post(()->{

                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put(ОтветОтСервераОбратно,ОтветОтСервераОбратно);
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);

                                        });
                                        // TODO: 20.02.2023 закрыаем сесию ссервром

                                    }
                                    Log.i(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                            + " время " +new Date().toLocaleString()+ " characteristic "+characteristic );
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                ContentValues valuesЗаписываемОшибки=new ContentValues();
                                valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                                valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                                valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                                valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                                final Object ТекущаяВерсияПрограммы = version;
                                Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                                valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                                new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                            }
                        }

                        @Override
                        public void onServiceChanged(@NonNull BluetoothGatt gatt) {
                            super.onServiceChanged(gatt);
                            Log.i(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+ " время " +new Date().toLocaleString() + " gatt " +gatt);
                        }
                    };
                    } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    ContentValues valuesЗаписываемОшибки=new ContentValues();
                    valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                    valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                    valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                    valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                    final Object ТекущаяВерсияПрограммы = version;
                    Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                    valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                    new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                }
                    return  bluetoothGattCallback;
                }












    @SuppressLint("MissingPermission")
                private void МетодЗапускаGATTКлиента(@NonNull BluetoothDevice bluetoothDevice,
                                                     BluetoothGattCallback bluetoothGattCallback) {
                    try{
                    gatt =      bluetoothDevice.connectGatt(context, false,
                            bluetoothGattCallback,
                            BluetoothDevice.TRANSPORT_AUTO,
                            BluetoothDevice.PHY_OPTION_S8,handler);
                    gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
                    //gatt.setPreferredPhy(BluetoothDevice.PHY_LE_2M_MASK,BluetoothDevice.PHY_LE_2M_MASK,BluetoothDevice.PHY_OPTION_S2);
                    int bondstate = bluetoothDevice.getBondState();

                    Log.d(TAG, "Trying to write characteristic..., first bondstate " + bondstate);
                        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );

                    switch (bondstate) {
                        
                        case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                            // TODO: 19.07.2024
                            handler.post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothDevice.DEVICE_TYPE_UNKNOWN","9");
                                mediatorLiveDataGATT.setValue(concurrentHashMap);
                            });
                    /*        bl_BloadcastReceierGatt blBloadcastReceierGatt = new bl_BloadcastReceierGatt(context, version);
                            blBloadcastReceierGatt.unpairDevice(bluetoothDevice);*/

                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );
                            break;

                        case BluetoothDevice.BOND_NONE:
                            
                            handler.post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothDevice.BOND_NONE","10");
                                mediatorLiveDataGATT.setValue(concurrentHashMap);

                            });
                            // TODO: 22.07.2024  Принудительный Запуск Сопрежения
                       /*         blBloadcastReceierGatt = new bl_BloadcastReceierGatt(context, version);
                            blBloadcastReceierGatt.unpairDevice(bluetoothDevice);*/
                            // TODO: 22.07.2024  Принудительный Запуск Сопрежения

                          
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );
                            break;


                        case BluetoothDevice.BOND_BONDING:
                            handler.post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothDevice.BOND_BONDING","12");
                                mediatorLiveDataGATT.setValue(concurrentHashMap);

                            });
                            
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );
                            break;

                        case BluetoothDevice.BOND_BONDED:
                            handler.post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothDevice.BOND_BONDING","13");
                                mediatorLiveDataGATT.setValue(concurrentHashMap);

                            });
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );
                            break;



                        default:{


                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );

                        }


                    }








                        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    ContentValues valuesЗаписываемОшибки=new ContentValues();
                    valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
                    valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
                    valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
                    valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
                    final Object ТекущаяВерсияПрограммы = version;
                    Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                    valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
                    new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                }
                }

                @SuppressLint("MissingPermission")





    // TODO: 01.02.2023  класс Запуск OneSignala




    private String МетодПолучениеКлючаОтСлужбыONESIGNALAndFirebase(@NonNull String КлючДляFirebaseNotification) {
        String ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal = null;
        try{
            //TODO srating......  oneSignal
            Log.d(this.getClass().getName(), "  КЛЮЧ ДЛЯ Scanner  OneSignal........ "+ КлючДляFirebaseNotification +"\n");
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            // todo OneSignal Initialization
            OneSignal.initWithContext(context);
            ///////todo srating Google Notifications wits PUblic Key
            OneSignal.setAppId(КлючДляFirebaseNotification);
            OneSignal.disablePush(false);
            //TODO srating.......... firebase cloud --ПРИШЛО СООБЩЕНИЕ
            FirebaseMessagingService firebaseMessagingService =new MyFirebaseMessagingServiceScanner();
            //TODO srating......  oneSignal
            Log.d(this.getClass().getName(), "  FirebaseMessagingService"  );
            // TODO: 07.12.2021
            firebaseMessagingService.onNewToken("Сообщения от Firebase Cloud Google ");
            Log.d(this.getClass().getName(), "  КЛЮЧ ДЛЯ  КОНЕЦ  OneSignal........  56bbe169-ea09-43de-a28c-9623058e43a2 " );
            // TODO: 15.12.2021 настройки onesigmnal
            Map<String, String> params = new HashMap<String, String>();
            OneSignal.sendTag("Authorization", "Basic 56bbe169-ea09-43de-a28c-9623058e43a2");
            OneSignal.sendTag("Content-type", "application/json");
            OneSignal.sendTag("grp_msg", "scanner");
            OneSignal.sendTag("android_background_data", "true");
            OneSignal.sendTag("content_available", "true");
            //TODO srating......  oneSignal
          String  PushОнеСигнала = OneSignal.getDeviceState().getPushToken();

            ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal = OneSignal.getDeviceState().getUserId();
            // TODO: 15.12.2021
            Log.d(this.getClass().getName(), "  ПОСЛЕ КЛЮЧ ДЛЯ  OneSignal........  56bbe169-ea09-43de-a28c-9623058e43a2 "+"\n"+
                    "   OneSignal.getTriggerValueForKey(\"GT_PLAYER_ID\"); " + OneSignal.getTriggerValueForKey("GT_PLAYER_ID")+
                    "     OneSignal.getTriggers() " +   OneSignal.getTriggers()+"\n"+
                    "    ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal ОТ СЕРВЕРА ::: " + ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal);
            // TODO: 13.12.2021
            // TODO: 05.01.2022  ДЕЛАЕМ ПОДПИСКУ НА ОСУЩЕСТВЛЛЕНУЮ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getApplicationContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal;
    }


    public void getCloserClientGattFroFragment(){

        new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
        Log.d(this.getClass().getName(), "  ПОСЛЕ КЛЮЧ ДЛЯ  OneSignal........  56bbe169-ea09-43de-a28c-9623058e43a2 "+"\n"+
                "   OneSignal.getTriggerValueForKey(\"GT_PLAYER_ID\"); " + OneSignal.getTriggerValueForKey("GT_PLAYER_ID")+
                "     OneSignal.getTriggers() " +   OneSignal.getTriggers()+"\n"+
                "    gatt " + gatt);

    }








}
