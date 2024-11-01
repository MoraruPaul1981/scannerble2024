package com.sous.scanner.businesslayer.bl_BroadcastReciver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.sous.scanner.R;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_BroadcastReciver.interfaces.BusinesslogicWhetherMacBluetoothisAllowedInterface;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.zip.Inflater;

public class BusinesslogicWhetherMacBluetoothisAllowed implements BusinesslogicWhetherMacBluetoothisAllowedInterface {

    private  Context context;
    private  Long version;

    public BusinesslogicWhetherMacBluetoothisAllowed(Context context, Long version) {
        this.context = context;

        this.version = version;
    }


    /**
     * @param externalBluetoothDevice
     * @param preferences
     * @return
     */
    @Override
    public Boolean weAreLookingforwhethermacbluetoothisallowed(@NonNull String externalBluetoothDevice, @NonNull SharedPreferences preferences) {
        // TODO: 30.10.2024
        Boolean getweAreLookingforwhethermacbluetoothisallowed=false;
        try {


            LayoutInflater inflaterBluettohCleint=LayoutInflater.from(context);
         View view= inflaterBluettohCleint.inflate(R.layout.fragment1_gatt_clientfor_scaning,null,false);

            MaterialCardView materialcardview_gattclientonly_bottom = (MaterialCardView) view.findViewById(R.id.id_materialcardview_gattclientonly_bottom);

          MaterialTextView  searchview_maclistdeviceserver = (MaterialTextView) materialcardview_gattclientonly_bottom.findViewById(R.id.id_materialtextview_last_state);

            // TODO: 07.08.2024  Успешное Событие в нутри BroadCasr Recuver
            String  geMAc=     preferences.getString("geMAc" ,"");
            if (geMAc.length()==0) {
                geMAc=     preferences.getString("getAddress" ,"");
            }
            // TODO: 30.10.2024
            getweAreLookingforwhethermacbluetoothisallowed=    geMAc.contains(externalBluetoothDevice);


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "getweAreLookingforwhethermacbluetoothisallowed"+getweAreLookingforwhethermacbluetoothisallowed);

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
        return  getweAreLookingforwhethermacbluetoothisallowed;
    }
}


