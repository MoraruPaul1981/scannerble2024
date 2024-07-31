package com.sous.server.businesslayer.BI_Services;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_BloadcastReceiver.Bl_BloadcastGatt_pairDevice;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bl_forServiceGattServerScan {

    private ConcurrentHashMap<String,ContentValues> contentValuesConcurrentHashMap;
    private SharedPreferences sharedPreferencesGatt;
    private  Context context;
    private  Long version;
    private ContentProviderServer contentProviderServer;
    private    Cursor successfuldevices;
    public static final boolean PAIRING_VARIANT_PASSKEY_CONFIRMATION =true;
    public static final byte[] PAIRING_VARIANT_PIN = new byte[0];


    //TODO: Local
    private LocationManager locationManager;
    private      SharedPreferences sharedPreferencesScan;
    private BluetoothManager bluetoothManagerServer;
    private BluetoothAdapter bluetoothAdapterScan;

    private BluetoothLeScanner scannerSimple;

    public Bl_forServiceGattServerScan(LocationManager locationManager,
                                       SharedPreferences sharedPreferencesScan,
                                       BluetoothManager bluetoothManagerServer,
                                       BluetoothAdapter bluetoothAdapterScan,
                                       BluetoothLeScanner scannerSimple,
                                       Context context) {
        this.locationManager = locationManager;
        this.sharedPreferencesScan = sharedPreferencesScan;
        this.bluetoothManagerServer = bluetoothManagerServer;
        this.bluetoothAdapterScan = bluetoothAdapterScan;
        this.scannerSimple = scannerSimple;
        this.context = context;
    }


    //TODO :  главный метод службы запускаем Scan

   @SuppressLint("MissingPermission")
   public void  startingScanBLE(){
  try{

      ScanSettings scanSettings = new ScanSettings.Builder()
              .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
              .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
              .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
              .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
              .setReportDelay(10000l)
              .build();


      scannerSimple.startScan(null,scanSettings,new ScanCallback() {
          @Override
          public void onScanResult(int callbackType, ScanResult result) {
              super.onScanResult(callbackType, result);
              Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                      " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                      " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " result " +result );
          }

          @Override
          public void onBatchScanResults(List<ScanResult> results) {
              super.onBatchScanResults(results);
              Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                      " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                      " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " results " +results );

              results.forEach(new Consumer<ScanResult>() {
                  @Override
                  public void accept(ScanResult scanResult) {
                      // TODO: 23.07.2024

                 BluetoothDevice scanResultDevice= scanResult.getDevice();

                      Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                              " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                              " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                              + "\n" + " scanResult.getDevice() .getAddress() " +scanResult.getDevice() .getAddress()
                              + "\n" + " scanResult.getDevice().getName() " +scanResult.getDevice().getName());


                      if (scanResult.getDevice().getName()!=null) {
                          Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                  " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                  " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                  + "\n" + " scanResult.getDevice()  " +scanResult.getDevice()+"\n");
                      }else {
                          Bl_BloadcastGatt_pairDevice blBloadcastReceierGatt = new Bl_BloadcastGatt_pairDevice(context, version);
                          blBloadcastReceierGatt.unpairDevice(scanResultDevice);
                          blBloadcastReceierGatt.pairDevice(scanResultDevice);
                      }


                  }
              });

          }

          @Override
          public void onScanFailed(int errorCode) {
              super.onScanFailed(errorCode);
              Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                      " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                      " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "errorCode "+errorCode );
          }
      });




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
        new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
    }

    }


    // TODO: 24.07.2024 ДОполнительный Простой Скан


    @SuppressLint("MissingPermission")
    public void  startingSimpleScan(){
        try{

   /*         BluetoothAdapter.getDefaultAdapter().startLeScan(new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi,
                                     byte[] scanRecord) {
                    String remoteDeviceName = device.getName();
                    Log.d("Scanning", "scan device " + remoteDeviceName);
                }
            });*/

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


            Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapterScan.getBondedDevices();

            for (BluetoothDevice bluetoothDevice : bluetoothDeviceSet) {
                bluetoothDevice.connectGatt(context, true, new BluetoothGattCallback() {
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        super.onConnectionStateChange(gatt, status, newState);
                    }
                    @Override
                    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                        if(status == BluetoothGatt.GATT_SUCCESS)
                            Log.d("BluetoothRssi", String.format("BluetoothGat ReadRssi[%d]", rssi));
                    }
                });
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }

    @SuppressLint("MissingPermission")
    public void  startingRemoteScan(){
        try{

       //BluetoothDevice bluetoothDeviceremote=     bluetoothAdapterScan.getRemoteDevice("FC:19:99:79:D6:D4");
       BluetoothDevice bluetoothDeviceremote=     bluetoothAdapterScan.getRemoteDevice("48:59:A4:5B:C1:F5");
            bluetoothDeviceremote.fetchUuidsWithSdp();


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " bluetoothDeviceremote.getBondState() " +bluetoothDeviceremote.getBondState());

            //bluetoothDeviceremote.setPairingConfirmation(PAIRING_VARIANT_PASSKEY_CONFIRMATION);
            bluetoothDeviceremote.setPin(PAIRING_VARIANT_PIN);
            bluetoothDeviceremote.createBond();


            switch (bluetoothDeviceremote.getBondState()) {
                case BluetoothDevice.BOND_BONDING:
                    Log.d("BlueToothTestActivity", "it is pairing");
                    break;
                case BluetoothDevice.BOND_BONDED:
                    Log.d("BlueToothTestActivity", "finish");

                    break;
                case BluetoothDevice.BOND_NONE:
                    Log.d("BlueToothTestActivity", "cancel");
                    break;
                case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                    Log.d("BlueToothTestActivity", "cancel");
                    break;
                default:
                    Log.d("BlueToothTestActivity", "cancel");
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }





    @SuppressLint("MissingPermission")
    public void  startinggetrssi(){
        try{

            BluetoothDevice bluetoothDeviceremote=     bluetoothAdapterScan.getRemoteDevice("FC:19:99:79:D6:D4"); //REDMI
            bluetoothDeviceremote.fetchUuidsWithSdp();

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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }












    @SuppressLint("MissingPermission")
    public void  startingBluetoothSocket(){
        try{
            BluetoothDevice bluetoothBluetoothSocket=     bluetoothAdapterScan.getRemoteDevice("FC:19:99:79:D6:D4"); //REDMI 9С
            final BluetoothSocket[] socket = {null};

            class beet extends Thread{

                void ggg(@NonNull  BluetoothDevice bluetoothBluetoothSocket){
                    try {

           /* Method m = bluetoothBluetoothSocket.getClass().getMethod("createRfcommSocket",new Class[] { int.class });
            BluetoothSocket  socket = (BluetoothSocket)m.invoke(bluetoothBluetoothSocket, Integer.valueOf(1));*/


                        try {
                            socket[0] = bluetoothBluetoothSocket.createRfcommSocketToServiceRecord(UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
                        //    BluetoothSocket   socket2 = bluetoothBluetoothSocket.createL2capChannel(1000);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        start();
              /*      socket.connect();
                    socket2.connect();*/
                    Log.d("EF-BTBee", ">>Client connectted");



         /*           Log.d("EF-BTBee", ">>Client connectted");

                    InputStream inputStream = socket[0].getInputStream();
                    OutputStream outputStream = socket[0].getOutputStream();
                    outputStream.write(new byte[] { (byte) 0xa0, 0, 7, 16, 0, 4, 0 });
*/

     /*               new Thread() {
                        public void run() {
                            while(true)
                            {
                                try {
                                    Log.d("EF-BTBee", ">>Send data thread!");
                                    OutputStream outputStream = socket.getOutputStream();
                                    outputStream.write(new byte[] { (byte) 0xa2, 0, 7, 16, 0, 4, 0 });
                                } catch (IOException e) {
                                    Log.e("EF-BTBee", "", e);
                                }
                            }
                        };
                    }.start();*/




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
                    new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
                }

                };

                @Override
                public void run() {
                    super.run();

                    try {
                        bluetoothAdapterScan.cancelDiscovery();
                        socket[0].connect();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }


            }

// TODO: 24.07.2024 end
            new beet().ggg(bluetoothBluetoothSocket);


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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }











































    // TODO: 23.07.2024 СКОПИРОВАНЫЕ КОД ИЗ СЛУЖБЫ СКАНИрОВАНИЯ  , В будущем он весь будет использован

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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return contentValuesВставкаДанных;
    }


    // TODO: 23.07.2024

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
            Log.d(this.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " cursorПолучаемДЛяСевреа.getCount() " +cursorПолучаемДЛяСевреа.getCount());
            // TODO: 19.07.2024 closing
            cursorПолучаемДЛяСевреа.close();

            Log.d(this.getClass().getName(), "\n"
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ТакоеВремяУжеЕсть;
    }

    // TODO: 23.07.2024



    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private Integer wtireNewSucceesDeviceOtGattServer(@NonNull   ContentValues   contentValuesВставкаДанныхGattServer) {
        Uri    resultAddDeviceToGattaDtabse = null;
        try {
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
            resultAddDeviceToGattaDtabse=   contentProviderServer.insert(uri, contentValuesВставкаДанныхGattServer);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return Integer.parseInt(resultAddDeviceToGattaDtabse.toString() );
    }


    // TODO: 23.07.2024

    // TODO: 15.03.2023 синхрониазция КЛАсс
// TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public ConcurrentHashMap<String,Cursor> getallthedataofsuccessfuldevices(@NonNull String СамЗапрос) {
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
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  cursorConcurrentHashMapGatt;
    }


    // TODO: 23.07.2024


    @SuppressLint("MissingPermission")
    private void forwardUIAfterSuccessAddDiveceDatBAseGatt(@NonNull ConcurrentHashMap<String,Cursor> concurrentHashMapCursor ,
                                                           @NonNull   ContentValues    contentValuesВставкаДанныхGaTT) {
        try{

            Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v2.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));


            //TODO:Event Send To Fragment Boot After Success DataBase and Divece
           // sendStatusSucessEventBusDeviceScan(contentValuesВставкаДанныхGaTT, concurrentHashMapCursor);

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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
    }


    // TODO: 23.07.2024


// TODO: 23.07.2024



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
            Log.w(this.getClass().getName(), " РЕЗУЛЬТАТ insertData  cursorПолучаемДЛяСевреа  " +  cursorПолучаемДЛяСевреа.toString() );
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ВерсияДАнных;
    }

    // TODO: 23.07.2024



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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return getUUID;
    }


    // TODO: 23.07.2024


    private void getcloseCursorAndHashMap() {
        try {
            if (successfuldevices != null) {
                if (successfuldevices.isClosed() == false) {
                    successfuldevices.close();
                }
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
        }


    }

}
