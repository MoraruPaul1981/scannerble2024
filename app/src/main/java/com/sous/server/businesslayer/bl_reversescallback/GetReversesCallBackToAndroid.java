package com.sous.server.businesslayer.bl_reversescallback;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_reversescallback.interfaces.GetReversesCallBackINt;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GetReversesCallBackToAndroid implements GetReversesCallBackINt {

    private Context context;
    private Long version;
    private Message message;

    final private UUID getPublicUUIDScan = ParcelUuid.fromString("70000007-0000-1000-8000-00805f9b34fb").getUuid();

    public GetReversesCallBackToAndroid(Context context, Long version) {
        this.context = context;
        this.version = version;
        // TODO: 24.10.2024
        message = new GetMessage(context, version).getMessage();
    }


    @Override
    public void getReversesCallBackToAndroid(@NotNull BluetoothDevice bluetoothDeviceAndroidReverses) {
        try {
            // TODO: 30.07.2024
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            Parcel parcel=Parcel.obtain();
            parcel.writeString("89158111806");
            parcel.writeString(new Date().toGMTString().toString());
            parcel.writeDouble(89158111806d);

            bluetoothDeviceAndroidReverses.writeToParcel(parcel,0);

            BluetoothGattCallback getbluetoothGattCallback = new GetBluetoothGattCallback(context, version, message,getPublicUUIDScan).getBluetoothGattCallback();


            BluetoothGatt gattScan = bluetoothDeviceAndroidReverses.connectGatt(context, true,
                    getbluetoothGattCallback, BluetoothDevice.TRANSPORT_AUTO, BluetoothDevice.PHY_OPTION_NO_PREFERRED, message.getTarget());
            gattScan.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
            int bondstate = bluetoothDeviceAndroidReverses.getBondState();

            // TODO: 12.08.2024
            bluetoothDeviceAndroidReverses.fetchUuidsWithSdp();
            gattScan.connect();
            gattScan.executeReliableWrite();

            Log.d(this.getClass().getName(), "Trying to write characteristic..., first bondstate " + bondstate);
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "   bluetoothDevice.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);

            switch (bondstate) {

                case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                    // TODO: 19.07.2024
                    message.getTarget().post(() -> {
                        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
                        concurrentHashMap.put("BluetoothDevice.DEVICE_TYPE_UNKNOWN", "9");

                    });

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);
                    break;

                case BluetoothDevice.BOND_NONE:
                    // TODO: 29.07.2024
                    message.getTarget().post(() -> {
                        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
                        concurrentHashMap.put("BluetoothDevice.BOND_NONE", "10");

                    });
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);
                    break;


                case BluetoothDevice.BOND_BONDING:
                    // TODO: 29.07.2024
                    message.getTarget().post(() -> {
                        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
                        concurrentHashMap.put("BluetoothDevice.BOND_BONDING", "12");


                    });

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);
                    break;

                case BluetoothDevice.BOND_BONDED:
                    message.getTarget().post(() -> {
                        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
                        concurrentHashMap.put("BluetoothDevice.BOND_BONDING", "13");


                    });
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);
                    break;


                default: {


                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress() + " bondstate " + bondstate);

                }


            }


            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "   bluetoothDeviceAndroidReverses.getAddress()" + bluetoothDeviceAndroidReverses.getAddress());

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




