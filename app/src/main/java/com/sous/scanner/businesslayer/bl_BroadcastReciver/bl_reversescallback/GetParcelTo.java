package com.sous.scanner.businesslayer.bl_BroadcastReciver.bl_reversescallback;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.bl_reversescallback.interfeces.GetParcelToInterface;

import org.jetbrains.annotations.NotNull;

public class GetParcelTo implements GetParcelToInterface {
    Context context;
    Long version;

    public GetParcelTo(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    @Override
    public void getParcelTo(@NotNull BluetoothDevice bluetoothDevicerevers) {
        try{
            final Parcel p2 = Parcel.obtain();
            bluetoothDevicerevers.writeToParcel(p2,0);
            // Unmarshall a Foo from that byte array
            p2.setDataPosition(0);
            final String result =      p2.readString( );

            p2.unmarshall();

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
}
