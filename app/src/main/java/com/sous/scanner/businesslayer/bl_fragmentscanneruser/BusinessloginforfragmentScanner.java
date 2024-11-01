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
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.businesslayer.bl_EvenBus.EventLocalBroadcastManager;
import com.sous.scanner.businesslayer.bl_LocalBroadcastManagers.BussenloginSaredPreferense;

import java.time.LocalDateTime;

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


    public SharedPreferences preferencesSaveEvent(   String getBremy, String getAction,  String getAddress, String getName) {
        try{

          SharedPreferences.Editor editor = preferences.edit();
            editor.putString("getBremy", getBremy);
            editor.putString("getAction",  getAction);
            editor.putString("geMAc", getAddress);
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
        return  preferences;
    }


   public SharedPreferences eventprocessingOtEventBus(@NonNull  EventLocalBroadcastManager event){
       SharedPreferences getsharedPreferences = null;
        try{
            String getBremy =event.getBremy;
            String getAction =event.getAction;
            String getAddress =event.getAddress;
            String getName =event.getName;
            // TODO: 07.08.2024 записываем в Публичное Хранилище

              getsharedPreferences=    preferencesSaveEvent(   getBremy,  getAction,    getAddress,   getName);

            // TODO: 31.07.2024
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase()+"\n"+  " event " +event.toString() + " getsharedPreferences " +getsharedPreferences);

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
        return  getsharedPreferences;
    }

    public void updateUIFragmentScangeMAc(@NonNull  MaterialTextView materialtextview_last_stateName ,
                                          @NonNull SharedPreferences preferences,
                                          @NonNull Animation animation ,
                                          @NonNull MaterialButton materialButtonEventSameOffice,
                                          @NonNull Message message,
                                          @NonNull String toProccessError,
                                          @NonNull String toProccessSuccess ) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String geMAc =preferences.getString("geMAc","");
            String  getName  =preferences.getString("getName","");
            // TODO: 07.08.2024
            message.getTarget().postDelayed(()->{
                materialButtonEventSameOffice.setText(toProccessSuccess);
                materialtextview_last_stateName.setError(null);
                materialButtonEventSameOffice.setTextColor(Color.BLACK);
                materialButtonEventSameOffice.requestLayout();
            },1500);

            if (geMAc.length()>0) {
                // TODO: 07.08.2024
                materialtextview_last_stateName.setText(geMAc);
                materialtextview_last_stateName.startAnimation(animation);
                materialtextview_last_stateName.requestLayout();

                // TODO: 07.08.2024 Отбражем на Конпке
                materialButtonEventSameOffice.setText(toProccessSuccess);
                materialButtonEventSameOffice.requestLayout();
                // TODO: 29.08.2024  сохраняем preferences
                 afterSuccessfulscanningsavepreferences(      getBremy, getAction,   geMAc,   getName);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " getBremy " +getBremy + " getName " +getName);

            }else {
                materialtextview_last_stateName.setError(null);
                // TODO: 07.08.2024
                materialButtonEventSameOffice.setText(toProccessError);
                materialButtonEventSameOffice.setTextColor(Color.RED);
            }
            materialtextview_last_stateName.refreshDrawableState();
            materialtextview_last_stateName.requestLayout();
            // TODO: 07.08.2024
            materialButtonEventSameOffice.refreshDrawableState();
            materialButtonEventSameOffice.requestLayout();



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

    // TODO: 31.10.2024 BREMY

    public void updateUIFragmentScanGetBremy(@NonNull  MaterialTextView materialtextview_last_bremy ,
                                            @NonNull SharedPreferences preferences,
                                            @NonNull Animation animation ,
                                            @NonNull MaterialButton materialButtonEventSameOffice,
                                            @NonNull Message message,
                                            @NonNull String toProccessError,
                                            @NonNull String toProccessSuccess ) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            String getBremy =preferences.getString("getBremy","");
            String getAction =preferences.getString("getAction","");
            String getName =preferences.getString("getName","");
            String  geMAc =preferences.getString("geMAc","");
            // TODO: 07.08.2024
            message.getTarget().postDelayed(()->{
                materialButtonEventSameOffice.setText(toProccessSuccess);
                materialtextview_last_bremy.setError(null);
                materialButtonEventSameOffice.setTextColor(Color.BLACK);
                materialButtonEventSameOffice.requestLayout();
            },1500);

            if (getBremy.length()>0) {
                // TODO: 07.08.2024
                materialtextview_last_bremy.setText(getBremy);
                materialtextview_last_bremy.startAnimation(animation);
                materialtextview_last_bremy.requestLayout();

                // TODO: 07.08.2024 Отбражем на Конпке
                materialButtonEventSameOffice.setText(toProccessSuccess);
                materialButtonEventSameOffice.requestLayout();
                // TODO: 29.08.2024  сохраняем preferences
                afterSuccessfulscanningsavepreferences(      getBremy, getAction,   geMAc,   getName);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        " getBremy " +getBremy + " getName " +getBremy);

            }else {
                materialtextview_last_bremy.setError(null);
                // TODO: 07.08.2024
                materialButtonEventSameOffice.setText(toProccessError);
                materialButtonEventSameOffice.setTextColor(Color.RED);
            }
            materialtextview_last_bremy.refreshDrawableState();
            materialtextview_last_bremy.requestLayout();
            // TODO: 07.08.2024
            materialButtonEventSameOffice.refreshDrawableState();
            materialButtonEventSameOffice.requestLayout();



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





    // TODO: 31.10.2024 SearchView

    public void updateUIFragmentScanGetSearchView(@NonNull  MaterialTextView searchview_maclistdeviceserver ,
                                             @NonNull SharedPreferences preferences,
                                             @NonNull Animation animation ,
                                             @NonNull MaterialButton materialButtonEventSameOffice,
                                             @NonNull Message message,
                                             @NonNull String toProccessError,
                                             @NonNull String toProccessSuccess ) {
        try{
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус

            String   getNameFromMAc=  searchview_maclistdeviceserver.getText().toString();

            if (getNameFromMAc.length()>0) {
              //TODO

                new BussenloginSaredPreferense(preferences,context,version).workerSharedPreferenSerchView(getNameFromMAc);

                String getBremy =preferences.getString("getBremy","");
                String getAction =preferences.getString("getAction","");
                String getName  =preferences.getString("getName","");
                String geMAc =preferences.getString("geMAc","");

                // TODO: 07.08.2024
                message.getTarget().postDelayed(()->{
                    materialButtonEventSameOffice.setText(toProccessSuccess);
                    searchview_maclistdeviceserver.setError(null);
                    materialButtonEventSameOffice.setTextColor(Color.BLACK);
                    materialButtonEventSameOffice.requestLayout();
                },1500);


                    // TODO: 07.08.2024
                    searchview_maclistdeviceserver.setText(getNameFromMAc);
                    searchview_maclistdeviceserver.startAnimation(animation);
                     searchview_maclistdeviceserver.requestLayout();

                    // TODO: 07.08.2024 Отбражем на Конпке
                    materialButtonEventSameOffice.setText(toProccessSuccess);
                    materialButtonEventSameOffice.requestLayout();
                    // TODO: 29.08.2024  сохраняем preferences
                    afterSuccessfulscanningsavepreferences(      getBremy, getAction,   geMAc,   getName);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                            " getBremy " +getBremy + " getName " +getBremy);

                }else {
                    searchview_maclistdeviceserver.setError(null);
                    // TODO: 07.08.2024
                    materialButtonEventSameOffice.setText(toProccessError);
                    materialButtonEventSameOffice.setTextColor(Color.RED);
                }
                searchview_maclistdeviceserver.refreshDrawableState();
                searchview_maclistdeviceserver.requestLayout();
                // TODO: 07.08.2024
                materialButtonEventSameOffice.refreshDrawableState();
                materialButtonEventSameOffice.requestLayout();



            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " getNameFromMAc " +getNameFromMAc);

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




    private void afterSuccessfulscanningsavepreferences(@NonNull String getBremy, @NonNull String getAction,  String geMAc,   String getName) {
        // TODO: 30.08.2024
        try{

                BusinessloginforfragmentScanner businessloginforfragmentScanner=
                        new BusinessloginforfragmentScanner(context,version, preferences);
                businessloginforfragmentScanner.preferencesSaveEvent(getBremy,getAction,geMAc, getName);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                " geMAc " +geMAc);

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
            String getAddress =preferences.getString("geMAc","");
            String getName =preferences.getString("getName","");
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
            String getAddress =preferences.getString("geMAc","");
            String getName =preferences.getString("getName","");
            // TODO: 07.08.2024
            bundle.putString("getBremy",getBremy);
            bundle.putString("getAction",getAction);
            bundle.putString(" geMAc",getAddress);
            bundle.putString("getName",getName);
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

    public String updateUIClickBottonControlgetMac( @NonNull  MaterialTextView searchview_maclistdeviceserver ,@NonNull MaterialTextView materialtextview_last_state){
     String getMacForClick=new String();
        try{
            if(searchview_maclistdeviceserver.getText().toString().length()>3){
                // TODO: 31.10.2024
             Bundle bundlesearchview=  (Bundle) searchview_maclistdeviceserver.getTag();
                // TODO: 16.05.2023 Из Выбраного Элемента Получаеним ДАнные
                if (bundlesearchview!=null) {
                    getMacForClick  =  bundlesearchview.getString("geMAc");
                }else{

                    getMacForClick  =    materialtextview_last_state.getText().toString();
                }

            }
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
// TODO: 01.11.2024

    public Boolean analysisOftomacBluetoothforSimilarity( @NonNull  MaterialTextView searchview_maclistdeviceserver ,@NonNull MaterialTextView materialtextview_last_state){
        Boolean analysisoftwomac=false;
        try{
            String   getMacOtCleintBluetooh=new String();
            Bundle bundlesearchview=  (Bundle) searchview_maclistdeviceserver.getTag();
            if (bundlesearchview!=null) {
                getMacOtCleintBluetooh  =  bundlesearchview.getString("geMAc");
            }
            if (getMacOtCleintBluetooh.length()==0) {
                getMacOtCleintBluetooh  = materialtextview_last_state.getText().toString();
            }
            String  getMacGattServer  =  preferences.getString("geMAc","");
            if( getMacGattServer.equalsIgnoreCase(getMacOtCleintBluetooh)) {
                // TODO: 01.11.2024
                analysisoftwomac=true;
            }
            // TODO: 07.08.2024  перезагружаем внешний вид экрана или точнее компонта Последний Статус
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " analysisoftwomac " +analysisoftwomac);

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
        return  analysisoftwomac;
    }






    // TODO: 01.11.2024  end class
}
