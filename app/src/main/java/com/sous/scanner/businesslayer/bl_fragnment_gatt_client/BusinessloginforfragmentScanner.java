package com.sous.scanner.businesslayer.bl_fragnment_gatt_client;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_EvenBus.EventLocalBroadcastManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusinessloginforfragmentScanner {

    // TODO: 07.08.2024
    private Context context;
    private  long version;
    private  SharedPreferences preferences;

    public BusinessloginforfragmentScanner(Context context, long version, SharedPreferences preferences) {
        this.context = context;
        this.version = version;
        this.preferences = preferences;
    }


    public void preferencesSaveEvent(   String getBremy, String getAction,  String getAddress, String getName) {
        try{

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("getBremy", getBremy);
            editor.putString("getAction",  getAction);
            editor.putString("getAddress", getAddress);
            editor.putString("getName",  getName);
            editor.apply();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " preferences " +preferences);

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


   public void eventprocessingOtEventBus(@NonNull  EventLocalBroadcastManager event){
        try{
            String getBremy =event.getBremy;
            String getAction =event.getAction;
            String getAddress =event.getAddress;
            String getName =event.getName;
            // TODO: 07.08.2024 записываем в Публичное Хранилище

            preferencesSaveEvent(   getBremy,  getAction,    getAddress,   getName);

            // TODO: 31.07.2024
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n"+  " event " +event.toString());

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

    public void updateUIFragmentScan(@NonNull  MaterialTextView materialtextview_last_state ,
                                     @NonNull SharedPreferences preferences,
                                     @NonNull Animation animation ) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String getAddress =preferences.getString("getAddress","");
            String getName =preferences.getString("getName","");
            if (getAddress.length()>0) {
                String completeResultContol="Сервер: "+getAddress+"время:" +getBremy;
                // TODO: 07.08.2024
                materialtextview_last_state.setText(completeResultContol);
                materialtextview_last_state.startAnimation(animation);

            }else {
                materialtextview_last_state.setError("ошибка контроля!!!");
            }
            materialtextview_last_state.refreshDrawableState();
            materialtextview_last_state.requestLayout();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " preferences " +preferences);

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
