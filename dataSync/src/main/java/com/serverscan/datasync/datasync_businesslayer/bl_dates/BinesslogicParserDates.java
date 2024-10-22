package com.serverscan.datasync.datasync_businesslayer.bl_dates;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.rxjava3.annotations.NonNull;

public class BinesslogicParserDates {
    private  DateFormat dateFormat;

    private Context context;
    private  Long version;


    public BinesslogicParserDates(Context context, Long version) {
        this.context = context;
        this.version = version;
        // TODO: 06.09.2024
        dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", new Locale("ru", "RU"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
    }


    public   Date dateCreation(){
        Date date=null;
        try{
            date = Calendar.getInstance().getTime();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " date " +date );
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
        return date;
    }

    public  String datesasaString(@NotNull Date date){
        String datesting=null;
        try{
            datesting=  dateFormat.format(date);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " date " +date );
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
        return datesting;
    }


    public  Date datesasDates( String datesting){
        Date date=null;
        try{
            date = dateFormat.parse(datesting);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " date " +date );
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
        return date;
    }

    @SuppressLint("Range")
    public String prossecingBremy(@NonNull Cursor cursorlocal){
        String bremylocal=new String();
        if (cursorlocal.getCount() >0) {
            // TODO: 21.10.2024
            cursorlocal.moveToFirst();
            DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", new Locale("ru", "RU"));
            // TODO: 27.08.2024 bremy
            try {
                Date datelocal = dateFormat.parse(cursorlocal.getString(cursorlocal.getColumnIndex("date_update")));
                bremylocal = dateFormat.format(datelocal);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else {
            DateFormat	dateFormat =   new SimpleDateFormat("yyyy-MM-dd",new Locale("ru", "RU"));
            try {
                Date datelocal  = dateFormat.parse("2010-01-01");
                bremylocal = dateFormat.format(datelocal);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "bremylocal " + bremylocal);
        return  bremylocal;
    }

    public String prossecingDateToString(@NonNull Date dateForServerJboss){
        String bremylocal=new String();
        try{
        if (dateForServerJboss!=null) {
            // TODO: 21.10.2024
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", new Locale("ru", "RU"));
            // TODO: 27.08.2024 bremy
            bremylocal = dateFormat.format(dateForServerJboss);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "bremylocal " + bremylocal);
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
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "bremylocal " + bremylocal);
        return  bremylocal;
    }


}
