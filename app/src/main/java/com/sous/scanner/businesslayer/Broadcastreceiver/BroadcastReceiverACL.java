package com.sous.scanner.businesslayer.Broadcastreceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.Businesslogic_GattClinetRemoteBord;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.Businesslogic_GattClinetSuccessfullycompletedClientControl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class BroadcastReceiverACL extends BroadcastReceiver {

    private Long version;
    private AtomicReference<PendingResult> pendingResultAtomicReferenceClient=new AtomicReference<>();



    @SuppressLint({"MissingPermission", "NewApi"})
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        try{
            pendingResultAtomicReferenceClient.set(goAsync());

            // TODO: 31.07.2024 Получаем сам девайс
            final   BluetoothDevice     bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            final    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            // TODO: 31.07.2024 рабочий код

            switch (intent.getAction()){
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_CONNECTED :
                    // TODO: 02.08.2024
                    final   String     bluetoothDeviceNAMe = intent.getParcelableExtra(BluetoothDevice.EXTRA_NAME);
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);


                    // TODO: 31.07.2024
              String getAction=    Optional.ofNullable( intent.getAction().toUpperCase()).map(m->m.toUpperCase()) .orElseGet(()->"");
              String getAddress=     Optional.ofNullable(bluetoothDevice.getAddress().toUpperCase()) .orElseGet(()->"");
              String getName= Optional.ofNullable(bluetoothDevice.getName()).map(m->m.toUpperCase()) .orElseGet(()->"");

                    LocalDateTime futureDate = LocalDateTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss.SSS");
                    String getBremy=   dtf.format(futureDate);

// TODO: 05.08.2024 CAll BAck От сервера запись уСпешноый КОТРОЛЬ

                    new Businesslogic_GattClinetSuccessfullycompletedClientControl(context,version).Successfullycompleted(context,getAction,getAddress,getName);

                    // TODO: 31.07.2024
                    Log.i(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " BroadcastReceiver LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n" +
                            "  getAction " + getAction+"\n"+
                            "  getAddress " + getAddress+"\n" +
                            "  getName " + getName+"\n"+"\n"
                            + " rssi " +rssi+"\n"+
                            " getBremy" +getBremy);




                PendingResult pendingResult=    pendingResultAtomicReferenceClient.get();
                pendingResult.finish();
                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n" );
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case   BluetoothDevice.ACTION_ACL_DISCONNECTED :
                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                // TODO: 31.07.2024
                // TODO: 31.07.2024
                case  BluetoothDevice.ACTION_BOND_STATE_CHANGED :
                    // TODO: 31.07.2024
                    new Businesslogic_GattClinetRemoteBord(context,version).unpairDevice(bluetoothDevice);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                // TODO: 31.07.2024

                default:{
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            "intent.getAction() "+intent.getAction() + " intent.getAction() " +intent.getAction());
                    break;
                }
            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "intent.getAction() "+intent.getAction() + " version " +version);

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
}