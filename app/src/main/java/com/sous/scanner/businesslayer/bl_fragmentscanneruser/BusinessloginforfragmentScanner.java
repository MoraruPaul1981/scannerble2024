package com.sous.scanner.businesslayer.bl_fragmentscanneruser;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.common.util.concurrent.AtomicDouble;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_EvenBus.EventLocalBroadcastManager;

import java.time.LocalDateTime;

import kotlinx.coroutines.internal.AtomicOp;

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
            editor.putString("getMatetilaButtonControl",  "контроль доступа");
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
                                     @NonNull Animation animation ,
                                     @NonNull MaterialButton materialcardview_gattclientonly_bottom,
                                     @NonNull Message message) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String getAddress =preferences.getString("getAddress","");
            String getName =preferences.getString("getName","");
            String getFirstNameButton =preferences.getString("getMatetilaButtonControl","");
            // TODO: 07.08.2024
            message.getTarget().postDelayed(()->{
                materialcardview_gattclientonly_bottom.setText(getFirstNameButton);
                materialtextview_last_state.setError(null);
                materialcardview_gattclientonly_bottom.setTextColor(Color.BLACK);
            },1500);

            if (getAddress.length()>0 && getAction.length()>0) {
                String completeResultContol;
                if (getName.length()>0) {
                    completeResultContol =
                            "Девайс: "+getName +"\n"+
                                    "Сервер: "+getAddress+"\n"
                                    +"Время: " +getBremy;
                } else {
                    completeResultContol = "Сервер: "+getAddress
                            +"\n"+"Время: " +getBremy;
                }

                // TODO: 07.08.2024 save Last Secces Status
                setingSharedPreferencesEditor(preferences, completeResultContol);

                // TODO: 07.08.2024
                materialtextview_last_state.setText(completeResultContol);
                materialtextview_last_state.startAnimation(animation);
                // TODO: 07.08.2024
                materialcardview_gattclientonly_bottom.setText("Успешно");

                // TODO: 30.08.2024


                // TODO: 29.08.2024  сохраняем preferences
                afterSuccessfulscanningsavepreferences(materialtextview_last_state, preferences, getName);


            }else {
                materialtextview_last_state.setError("ошибка контроля");
                // TODO: 07.08.2024
                materialcardview_gattclientonly_bottom.setText("нет Успешно");
                materialcardview_gattclientonly_bottom.setTextColor(Color.RED);
            }
            materialtextview_last_state.refreshDrawableState();
            materialtextview_last_state.requestLayout();
            // TODO: 07.08.2024
            materialcardview_gattclientonly_bottom.refreshDrawableState();
            materialcardview_gattclientonly_bottom.requestLayout();







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

    private void afterSuccessfulscanningsavepreferences(@NonNull MaterialTextView materialtextview_last_state,
                                                        @NonNull SharedPreferences preferences,
                                                        String getName) {
        // TODO: 30.08.2024
        try{
        BusinessloginforfragmentScanner businessloginforfragmentScanner=
                new BusinessloginforfragmentScanner(context,version, preferences);
        Bundle bundlegetMac= (Bundle) materialtextview_last_state.getTag();
        String geMAc=     bundlegetMac.getString("geMAc","");
        if (geMAc.length()>0) {
            businessloginforfragmentScanner.preferencesSaveEvent("","",geMAc, getName);
        }

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


    private  void setingSharedPreferencesEditor(@NonNull SharedPreferences preferences, String completeResultContol) {
        try{
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("completeResultContol", completeResultContol);
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


    // TODO: 29.08.2024

    public void updateUIFragmentMacSelecting(@NonNull  MaterialTextView materialtextview_last_state ,
                                     @NonNull SharedPreferences preferences,@NonNull Animation animation) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String getAddress =preferences.getString("getAddress","");
            String getName =preferences.getString("getName","");
            String getFirstNameButton =preferences.getString("getMatetilaButtonControl","");
            // TODO: 07.08.2024
            materialtextview_last_state.setText(getAddress);
            materialtextview_last_state.startAnimation(animation);
            materialtextview_last_state.requestLayout();
            materialtextview_last_state.refreshDrawableState();

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


    public Bundle updateUIClickBottonControl(@NonNull  MaterialTextView materialtextview_last_state,SharedPreferences preferences){
        Bundle bundle = (Bundle) materialtextview_last_state.getTag();
        try{
            // TODO: 30.08.2024
            if (bundle==null) {
                bundle=new Bundle();
            }
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String getAddress =preferences.getString("getAddress","");
            String getName =preferences.getString("getName","");
            String getFirstNameButton =preferences.getString("getMatetilaButtonControl","");
            // TODO: 07.08.2024
            bundle.putString("getBremy",getBremy);
            bundle.putString("getAction",getAction);
            bundle.putString("getAddress",getAddress);
            bundle.putString("getName",getName);
            bundle.putString("getMatetilaButtonControl",getFirstNameButton);
            materialtextview_last_state.setTag(bundle);

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
        return  bundle;
    }

    public String updateUIClickBottonControlgetMac(@NonNull  MaterialTextView materialtextview_last_state){
     String getMacForClick=new String();
        try{
            // TODO: 30.08.2024
            getMacForClick=  materialtextview_last_state.getText().toString();
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " getMacForClick " +getMacForClick);

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
        return  getMacForClick;
    }


}
