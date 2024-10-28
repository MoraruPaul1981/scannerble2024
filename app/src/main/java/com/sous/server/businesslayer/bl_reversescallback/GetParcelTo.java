package com.sous.server.businesslayer.bl_reversescallback;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.bl_reversescallback.interfaces.GetParcelToInterface;

import org.jetbrains.annotations.NotNull;

public class GetParcelTo  implements GetParcelToInterface {
    Context context;
    Long version;

    public GetParcelTo(Context context, Long version) {
        this.context = context;
        this.version = version;
    }

    /**
     *
     */
    @Override
    public void getParcelTo(@NotNull BluetoothDevice bluetoothDevicerevers) {
        try{

            // Wrote orig to a parcel and then byte array
            final Parcel p1 = Parcel.obtain();
            p1.writeString(bluetoothDevicerevers.getAddress());
            p1.writeString("ble server");
            p1.setDataPosition(0);
            bluetoothDevicerevers.writeToParcel(p1,0);
            p1.recycle();

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
