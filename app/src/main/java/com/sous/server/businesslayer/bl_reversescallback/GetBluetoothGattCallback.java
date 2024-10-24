package com.sous.server.businesslayer.bl_reversescallback;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.ContentValues;
import android.content.Context;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class GetBluetoothGattCallback {


    Context context;

    Long version;

    Message message;
    UUID getPublicUUIDScan;

    public GetBluetoothGattCallback(@NotNull Context context,
                                    @NotNull Long version,
                                    @NotNull Message message,
                                    @NotNull  UUID getPublicUUIDScan) {
        this.context = context;
        this.version = version;
        this.message = message;
        this.getPublicUUIDScan = getPublicUUIDScan;
    }


    @SuppressLint("MissingPermission")
    public BluetoothGattCallback getBluetoothGattCallback(   ) {

        // TODO: 24.10.2024 end class
    // TODO: 25.01.2023 ПЕРВЫЙ ВАРИАНТ СЕРВЕР gatt
    BluetoothGattCallback bluetoothGattCallbackScan = null;
        try{
        bluetoothGattCallbackScan = new BluetoothGattCallback() {
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
                            Log.d(this.getClass().getName(), "Connected to GATT client. BluetoothProfile.STATE_CONNECTED ###1 onConnectionStateChange  " +
                                    ""+new Date().toLocaleString());
                            message.getTarget().post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothProfile.STATE_CONNECTED","2");

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
                            //  new Businesslogic_GattClinetClose(version,context). disaibleGattServer(gatt);
                            // TODO: 16.07.2024 когда ошивка разрываем сообщение
                            Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                            break;


                        case BluetoothProfile.GATT_SERVER:
                            message.getTarget().post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothGatt.GATT_FAILURE","4");
                            });
                            //  new Businesslogic_GattClinetClose(version,context). disaibleGattServer(gatt);
                            Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                            break;


                        case BluetoothProfile.GATT :
                            message.getTarget().post(()->{
                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put("BluetoothGatt.GATT_CONNECTION_CONGESTED","5");

                            });
                            //  new Businesslogic_GattClinetClose(version,context). disaibleGattServer(gatt);
                            Log.d(this.getClass().getName(), "Trying to \"SERVERВDontEndConnect\" "  + " newState " +newState);
                            break;


                        default:{

                            Log.d(this.getClass().getName(), "Trying to ДанныеОТGATTССевромGATT "  + " newState " +newState);
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
                                characteristics.setValue("действие:");
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




                                    gatt.executeReliableWrite();
                                }else {
                                    ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                    concurrentHashMap  .put("6","ErrorWorkerGattClientWithServer");

                                    gatt.abortReliableWrite();

                                }

                                Log.d(this.getClass().getName(), "characteristics" + new Date().toLocaleString()+  " characteristics "
                                        +characteristics+ " successОтправка " +successОтправка+
                                        " ДействиеДляСервераGATTОТКлиента ");
                            }


                            // TODO: 25.07.2024  СОСТЫКОВКА С СЕРВРОМ НЕТ
                        }else {

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "services "+services);


                            ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                            concurrentHashMap  .put("7","ErrorWorkerGattClientWithServer");

                            Log.d(this.getClass().getName(), "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());





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

                        Log.d(this.getClass().getName(), "GATT CLIENT Proccessing from GATT server.GATTCLIENTProccessing " + new Date().toLocaleString());

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
                    linkedHashMapДанныеКлиентаДляGATT.add(new String());
                    String getIMEI = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    linkedHashMapДанныеКлиентаДляGATT.add(  getIMEI+"\n");
                    linkedHashMapДанныеКлиентаДляGATT.add(  new Date().toLocaleString()+"\n");
                    Log.d(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()+
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
                Log.d(this.getClass().getName(), "Connected to GATT server  newValueПришлиДАнныеОтКлиента."+new String(newValueПришлиДАнныеОтКлиента));
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                try{
                    if (characteristic!=null) {
                        byte[] newValueОтветОтСервера = characteristic.getValue();
                        if (newValueОтветОтСервера!=null) {
                            String ОтветОтСервераОбратно=new String(newValueОтветОтСервера);
                            Log.d(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()
                                    + " время " +new Date().toLocaleString()+ " ОтветОтСервераОбратно "+ОтветОтСервераОбратно );
                            // TODO: 30.01.2023  ПОСЫЛАЕМ ОТВЕТ ОТ СЕРВЕРА СТАТУСА
                            message.getTarget().post(()->{

                                ConcurrentHashMap<String,String> concurrentHashMap=      new ConcurrentHashMap<String,String>();
                                concurrentHashMap  .put(ОтветОтСервераОбратно,ОтветОтСервераОбратно);


                            });
                            // TODO: 20.02.2023 закрыаем сесию ссервром

                        }
                        Log.d(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()
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
            public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
                super.onCharacteristicChanged(gatt, characteristic, value);
            }

            @Override
            public void onDescriptorRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattDescriptor descriptor, int status, @NonNull byte[] value) {
                super.onDescriptorRead(gatt, descriptor, status, value);
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
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
                Log.d(this.getClass().getName(),  "  " +Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " время " +new Date().toLocaleString() + " gatt " +gatt);
            }
        };

// TODO: 02.08.2024 Добавляем Данные CallBack
        // TODO: 02.08.2024
        Log.d(this.getClass().getName(),  " " +Thread.currentThread().getStackTrace()[2].getMethodName()
                + " время " +new Date().toLocaleString()  + " bluetoothGattCallbackScan  " +bluetoothGattCallbackScan);

        // TODO: 02.08.2024
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

}

