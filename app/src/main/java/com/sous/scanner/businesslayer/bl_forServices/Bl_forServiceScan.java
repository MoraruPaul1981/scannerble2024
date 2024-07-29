package com.sous.scanner.businesslayer.bl_forServices;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.sous.scanner.businesslayer.Broadcastreceiver.bl_BloadcastReceierGatt;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class Bl_forServiceScan {

    // TODO: 24.07.2024  varibles Service Scan
    private String getWorkerStateClient="Простое сканирование";

    private Message handlerScan;
    private LocationManager locationManager;
    private BluetoothManager bluetoothManagerServer;
    private  BluetoothAdapter bluetoothAdapterPhoneClient;
    private Long version;
    private     @NonNull Context context;
    private MutableLiveData<ConcurrentHashMap<String,String>> mediatorLiveDataScan=new MutableLiveData<>();
    private UUID getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();
    private      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Bl_forServiceScan(@NonNull Message handlerScan,
                             @NonNull LocationManager locationManager,
                             @NonNull BluetoothManager bluetoothManagerServer,
                             @NonNull BluetoothAdapter bluetoothAdapterPhoneClient,
                             @NonNull Long version,
                             @NonNull Context context ) {
        this.handlerScan = handlerScan;
        this.locationManager = locationManager;
        this.bluetoothManagerServer = bluetoothManagerServer;
        this.bluetoothAdapterPhoneClient = bluetoothAdapterPhoneClient;
        this.version = version;
        this.context = context;

    }




// TODO: 24.07.2024 end



    @SuppressLint({"MissingPermission"})
    public Integer МетодЗапускаСканированиеКлиентСкан() {
        try {

            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
            concurrentHashMap.put("GATTCLIENTProccessing", "1");
            mediatorLiveDataScan.setValue(concurrentHashMap);

            ConcurrentSkipListSet<String> concurrentSkipListSetMunualListServerDeviceScan = new ConcurrentSkipListSet();
            concurrentSkipListSetMunualListServerDeviceScan.add("98:2F:F8:19:BC:F7");
            concurrentSkipListSetMunualListServerDeviceScan.forEach(new Consumer<String>() {
                @Override
                public void accept(String remoteManualServerGatt) {
                    ///TODO:Довавляем Зарание созданные Адреса Сервера Gatt
                    BluetoothDevice bluetoothDeviceScan = bluetoothAdapterPhoneClient.getRemoteDevice(remoteManualServerGatt);//TODO: HUAWEI MatePad SE
                    // TODO: 29.07.2024 удаление возможное GATT
                    bl_BloadcastReceierGatt  blBloadcastReceierGatt = new bl_BloadcastReceierGatt(context, version);
                blBloadcastReceierGatt.unpairDevice(bluetoothDeviceScan);
                    bluetoothDeviceScan.fetchUuidsWithSdp();


                    // TODO: 12.02.2023  init CallBack Gatt Client for Scan
                    BluetoothGattCallback             bluetoothGattCallbackScan =     МетодРаботыСТекущийСерверомGATTДляScan( );

                    // TODO: 26.01.2023 staring  GATT
              МетодЗапускаGATTКлиентаScan(bluetoothDeviceScan,              bluetoothGattCallbackScan );


                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n" +
                            " bluetoothDeviceScan " + bluetoothDeviceScan +  " getPublicUUIDScan "+ getPublicUUIDScan);
                }
            });
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n");

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
        return  0;
    }


    @SuppressLint("MissingPermission")
    private BluetoothGattCallback МетодРаботыСТекущийСерверомGATTДляScan(   ) {
        // TODO: 25.01.2023 ПЕРВЫЙ ВАРИАНТ СЕРВЕР gatt
        BluetoothGattCallback             bluetoothGattCallbackScan = null;
        try{
            bluetoothGattCallbackScan     = new BluetoothGattCallback() {
                @Override
                public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                    super.onPhyUpdate(gatt, txPhy, rxPhy, status);
                }

                @Override
                public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                    super.onPhyRead(gatt, txPhy, rxPhy, status);
                }

                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                    int newState) {
                    try{
                        // TODO: 26.07.2024
                        switch (newState){

                            case BluetoothProfile.STATE_CONNECTED :
                                Log.i(this.getClass().getName(), "Connected to GATT client. BluetoothProfile.STATE_CONNECTED ###1 onConnectionStateChange  " +
                                        ""+new Date().toLocaleString());
                                handlerScan.getTarget().post(()->{
                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put("BluetoothProfile.STATE_CONNECTED","2");
                                    mediatorLiveDataScan.setValue(concurrentHashMap);
                                });
                                Boolean ДанныеОТGATTССевромGATT=         gatt.discoverServices();
                                Log.d(this.getClass().getName(), "Trying to ДанныеОТGATTССевромGATT " + ДанныеОТGATTССевромGATT + " newState " +newState);
                                break;

                            case BluetoothProfile.STATE_DISCONNECTED :
                                       /* handler.post(()->{
                                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                            concurrentHashMap  .put("BluetoothProfile.STATE_DISCONNECTED","3");
                                            mediatorLiveDataGATT.setValue(concurrentHashMap);
                                        });*/
                              //  new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +"    Flowable.fromAction(new Action() { "
                                        +   new Date().toLocaleString()+ " newState " +newState);
                                // TODO: 16.07.2024 когда ошивка разрываем сообщение
                                break;


                            case BluetoothGatt.GATT_FAILURE:
                                handlerScan.getTarget().post(()->{
                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put("BluetoothGatt.GATT_FAILURE","4");
                                    mediatorLiveDataScan.setValue(concurrentHashMap);
                                });
                              //  new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                                break;


                            case BluetoothGatt.GATT_CONNECTION_CONGESTED :
                                handlerScan.getTarget().post(()->{
                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put("BluetoothGatt.GATT_CONNECTION_CONGESTED","5");
                                    mediatorLiveDataScan.setValue(concurrentHashMap);
                                });
                              //  new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
                                Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                                break;
                            default:{
                                Log.d(this.getClass().getName(), "Trying to ДанныеОТGATTССевромGATT "  + " newState " +newState);
                            }
                        }
                        
                        
                        // TODO: 29.07.2024  closeing client gatt
                        switch (status){
                            case 133 :
                                // TODO: 29.07.2024 close Gatt Client  
                                closeClientGatt(gatt);
                                Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                            break;
                            
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }
                // TODO: 26.01.2023
                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    super.onServicesDiscovered(gatt, status);
                    try{
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            BluetoothGattService services = gatt.getService(getPublicUUIDScan);


                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "services "+services);

                            if (services!=null) {
                                Boolean КоннектССевромGATT = gatt.connect();

                                gatt.beginReliableWrite();

                                Log.d(this.getClass().getName(), "Trying КоннектССевромGATT " + КоннектССевромGATT);
                                BluetoothGattCharacteristic characteristics = services.getCharacteristic(getPublicUUIDScan);
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



                                        mediatorLiveDataScan.setValue(concurrentHashMap);
                                        gatt.executeReliableWrite();
                                    }else {
                                        ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                        concurrentHashMap  .put("6","ErrorWorkerGattClientWithServer");
                                        mediatorLiveDataScan.setValue(concurrentHashMap);
                                        gatt.abortReliableWrite();

                                    }

                                    Log.i(this.getClass().getName(), "characteristics" + new Date().toLocaleString()+  " characteristics "
                                            +characteristics+ " successОтправка " +successОтправка+
                                            " ДействиеДляСервераGATTОТКлиента "+ getWorkerStateClient);
                                }


                                // TODO: 25.07.2024  СОСТЫКОВКА С СЕРВРОМ НЕТ
                            }else {

                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "services "+services);


                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("7","ErrorWorkerGattClientWithServer");
                                mediatorLiveDataScan.setValue(concurrentHashMap);
                                Log.i(this.getClass().getName(), "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());





                            }

                            // TODO: 25.07.2024   Успех ТОже выключаем Сервр
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                        }else{

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                            concurrentHashMap  .put("BluetoothDevice.BOND_NONE","8");
                            mediatorLiveDataScan.setValue(concurrentHashMap);
                            Log.i(this.getClass().getName(), "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());

                        }

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                // TODO: 20.02.2023 Метод Вытаскиеваем ДАнные Симки пользователя
                @NonNull
                private ConcurrentSkipListSet<String> МетодЗаполенияДаннымиКлиентаДЛяGAtt() {
                    ConcurrentSkipListSet<String> linkedHashMapДанныеКлиентаДляGATT = new ConcurrentSkipListSet<>();
                    try {
                        //TODO :  отправлдяем данные
                        linkedHashMapДанныеКлиентаДляGATT.add(   getWorkerStateClient +"\n");
                        String getIMEI = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                    return linkedHashMapДанныеКлиентаДляGATT;
                }

                @Override
                public void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value, int status) {
                    super.onCharacteristicRead(gatt, characteristic, value, status);
                    byte[] newValueПришлиДАнныеОтКлиента= characteristic.getValue();
                    Log.i(this.getClass().getName(), "Connected to GATT server  newValueПришлиДАнныеОтКлиента."+new String(newValueПришлиДАнныеОтКлиента));
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
                                handlerScan.getTarget().post(()->{

                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put(ОтветОтСервераОбратно,ОтветОтСервераОбратно);
                                    mediatorLiveDataScan.setValue(concurrentHashMap);

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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                    super.onReliableWriteCompleted(gatt, status);
                }

                @Override
                public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                    super.onReadRemoteRssi(gatt, rssi, status);
                }

                @Override
                public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                    super.onMtuChanged(gatt, mtu, status);
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  bluetoothGattCallbackScan;
    }

    @SuppressLint("MissingPermission")
    private   void closeClientGatt(BluetoothGatt gatt ) {
        if (gatt!=null) {
            gatt.disconnect();
            gatt.close();
        }
        //  new Bl_froSetviceBLE(version,context). disaibleGattServer(gatt);
        Log.d(context.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " gatt closeting  ");
    }

    // TODO: 24.07.2024
    @SuppressLint("MissingPermission")
    private void МетодЗапускаGATTКлиентаScan(@NonNull BluetoothDevice bluetoothDevice,@NonNull BluetoothGattCallback             bluetoothGattCallbackScan ) {
        try{
                 BluetoothGatt gattScan         =      bluetoothDevice.connectGatt(context, false,
                    bluetoothGattCallbackScan,
                    BluetoothDevice.TRANSPORT_AUTO,
                    BluetoothDevice.PHY_OPTION_S8,handlerScan.getTarget());
            gattScan.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
            int bondstate = gattScan.getDevice().getBondState();
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   bluetoothDevice.getAddress()" + bluetoothDevice.getAddress() + " bondstate " + bondstate );
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }





    // TODO: 24.07.2024  end class

}
