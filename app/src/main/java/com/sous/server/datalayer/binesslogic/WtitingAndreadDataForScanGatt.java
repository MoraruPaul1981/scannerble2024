package com.sous.server.datalayer.binesslogic;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner.BunesslogicisRunnigActivity;
import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;
import com.sous.server.businesslayer.bl_UUID.GeneratorUUIDs;
import com.sous.server.businesslayer.bl_dates.WorkerDates;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.annotations.NonNull;

public class WtitingAndreadDataForScanGatt {
    // TODO: 25.07.2024
  private Context context;
    private Long version;
    private ContentProviderServer contentProviderServer;
    private  SharedPreferences sharedPreferencesGatt;
    private  Cursor successfuldevices;
    protected ConcurrentHashMap<String,ContentValues>       contentValuesConcurrentHashMap=new ConcurrentHashMap<>();
    private  Integer limitForInsertNewDevice =1;

    public WtitingAndreadDataForScanGatt(Context context, Long version,
                                         ContentProviderServer contentProviderServer,
                                         SharedPreferences sharedPreferencesGatt) {
        this.context = context;
        this.version = version;
        this.contentProviderServer = contentProviderServer;
        this.sharedPreferencesGatt = sharedPreferencesGatt;
    }


    // TODO: 25.07.2024 метод Записи  в базу
    public ConcurrentHashMap<Integer,ContentValues> writeDatabaseScanGatt(@NonNull BluetoothDevice device,@NonNull String  getAction){
        // TODO: 30.07.2024
        ConcurrentHashMap<Integer,ContentValues> writeDatabaseScanGatt=new ConcurrentHashMap<>();
        try{

            // TODO: 25.07.2024 Код Записи в базу данных ScanGatt
                        //TODO:ЗАписываем Новый Успешный Девайс в Базу от Gatt server
            ContentValues     contentValuesВставкаДанных= addToContevaluesNewSucceesDeviceOtGattServer(device,getAction );

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "  contentValuesВставкаДанных " + contentValuesВставкаДанных);


                        // TODO: 25.07.2024

                        String   dateLocaleBase =  getDateStoreOperationsDeviceFronDatabase("SELECT  " +
                                "  MAX ( date_update )   FROM scannerserversuccess " +
                                " WHERE   macdevice = '"+ contentValuesВставкаДанных.getAsString("macdevice").trim() +"'");

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "  dateLocaleBase " +dateLocaleBase);


                      // TODO: 25.07.2024  GET DATE
                       Bundle getDateForNewInsertDivice = getDateForNewInsertDivice(dateLocaleBase, contentValuesВставкаДанных.getAsString("date_update").trim());

                        // TODO: 25.07.2024  Search РАзница
                        Integer getserchDateDifference=   differencebetweendates(getDateForNewInsertDivice);

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                "getDateForNewInsertDivice  " +getDateForNewInsertDivice);


// TODO: 30.07.2024 САМА ЗАПИСЬ В БАЗУ
                        if (getserchDateDifference>= limitForInsertNewDevice) {
                            // TODO: 30.07.2024 сама вставка в базу нового устройства
                            Integer resultAddDeviceToGattaDtabse = entryitselfintothedatabase( contentValuesВставкаДанных);

                            // TODO: 31.07.2024 main add data for before send
                            writeDatabaseScanGatt.putIfAbsent(resultAddDeviceToGattaDtabse,contentValuesВставкаДанных);

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                                    "  dateLimitAnrecord " + limitForInsertNewDevice +"\n" );


                        }


            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " writeDatabaseScanGatt " +writeDatabaseScanGatt);


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
        return  writeDatabaseScanGatt;

    }


    // TODO: 24.10.2024 difference between dates
    Integer differencebetweendates(@NonNull Bundle getDateForNewInsertDivice){
        Integer getserchDateDifference=0;
        try{
        // TODO: 24.10.2024

        LocalDateTime    databaseDate= (LocalDateTime) getDateForNewInsertDivice.getSerializable("storedate");
        LocalDateTime     LiveDate= (LocalDateTime)    getDateForNewInsertDivice.getSerializable("livedate");
           getserchDateDifference= serchDateDifference(  databaseDate,  LiveDate ) ;
        Log.d(context.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " getserchDateDifference " +getserchDateDifference);
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
        return  getserchDateDifference;
    }






    // TODO: 31.07.2024 DONT DEVICE


    public   ConcurrentHashMap<String, Cursor>  writeDatabaseScanGattDontDevice( ){
        // TODO: 30.07.2024
        ConcurrentHashMap<String, Cursor> writeDatabaseScanGattDontDevice=new ConcurrentHashMap<>();
        try{
// TODO: 25.07.2024  результат первый Запска КОГДА нет пока ПИНГА
            writeDatabaseScanGattDontDevice = getallthedataofsuccessfuldevices("SELECT *    FROM scannerserversuccess  GROUP by date_update  ORDER BY id DESC");

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    "  writeDatabaseScanGattDontDevice " +writeDatabaseScanGattDontDevice+"\n" );

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
        return  writeDatabaseScanGattDontDevice;

    }















    // TODO: 31.07.2024 send data
    public void afteruccessfuldataformationweSend(@NonNull ConcurrentHashMap<Integer, ContentValues> writeDatabaseScanGatt) {
try{
    // TODO: 31.07.2024
    // TODO: 18.07.2024 ЕСЛИ Успещно прошла Операция передаем данные на Фрагмент Scanner


        // TODO: 25.07.2024 closeing Before
        getcloseCursorAndHashMap();
        //todo ДОполнительный механизм данные упокаываем в Канкаренте СЕТ с Курсором

        ConcurrentHashMap<String, Cursor> concurrentHashMapCursor = getallthedataofsuccessfuldevices("SELECT *    FROM scannerserversuccess  GROUP by date_update  ORDER BY id DESC");

        // TODO: 19.07.2024 Посылаем Пользователю сообщение что данные изменились
        forwardUIAfterSuccessAddDiveceDatBAseScan(concurrentHashMapCursor, writeDatabaseScanGatt.values().stream().findAny().get());


    Log.d(context.getClass().getName(), "\n"
            + " время: " + new Date() + "\n+" +
            " Класс в процессе... " + this.getClass().getName() + "\n" +
            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
            " writeDatabaseScanGatt.keySet().stream().findAny().get() " +writeDatabaseScanGatt.keySet().stream().findAny().get());
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




    // TODO: 30.07.2024 САМА ЗАПИСЬ В БАЗУ
    private Integer entryitselfintothedatabase(@NonNull  ContentValues contentValuesВставкаДанных) {
        Integer       resultAddDeviceToGattaDtabse=0;
// TODO: 30.07.2024
        try{

            // TODO: 09.02.2023  запись в базу дивайса Отметка сотрдунка
            resultAddDeviceToGattaDtabse = wtireNewSucceesDeviceOtGattServer(contentValuesВставкаДанных);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"\n" + "resultAddDeviceToGattaDtabse  " + resultAddDeviceToGattaDtabse);

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

        return resultAddDeviceToGattaDtabse;
    }





    // TODO: 25.07.2024

    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private ContentValues addToContevaluesNewSucceesDeviceOtGattServer(@NonNull BluetoothDevice device,@NonNull String getAction) {
        ContentValues   contentValuesВставкаДанных =   new ContentValues();;
        try {
            Log.i(this.getClass().getName(), "  " + Thread.currentThread().getStackTrace()[2].getMethodName() + " время "
                    + new Date().toLocaleString() + " device " +device );


            // TODO: 08.02.2023 Добавляем Данные для Записи в базу через Адаптер ContentValues
            if (device.getAddress()!=null) {
                contentValuesВставкаДанных.put("macdevice", device.getAddress().toString());
            }
            if (device.getName()!=null) {
                contentValuesВставкаДанных.put("namedevice", device.getName().toString());
            }


            // TODO: 25.07.2024  Создаем Новую Даты
            WorkerDates workerDates=new WorkerDates(context,version);
            Date date_update = workerDates.dateCreation();
            String date_updatefinal=  workerDates.datesasaString(date_update);
            //date_update = workerDates.datesasDates(date_updatestring);


            // TODO: 25.07.2024 set date
            contentValuesВставкаДанных.put("date_update", date_updatefinal);

            contentValuesВставкаДанных.put("completedwork", "Успешный контроль");
            contentValuesВставкаДанных.put("operations", getAction);




            contentValuesВставкаДанных.put("city",    sharedPreferencesGatt.getString("getLocality","нет данных"));
            contentValuesВставкаДанных.put("gps1", sharedPreferencesGatt.getString("getLongitude","нет данных"));
            contentValuesВставкаДанных.put("gps2", sharedPreferencesGatt.getString("getLatitude","нет данных"));

            if ( !sharedPreferencesGatt.getString("getLocality","нет данных").isEmpty()) {

                contentValuesВставкаДанных.put("adress",  sharedPreferencesGatt.getString("getCountryName","нет данных")+" "+
                        sharedPreferencesGatt.getString("getLocality","нет данных")+" "+
                        sharedPreferencesGatt.getString("getSubAdminArea","нет данных")+" "+
                        sharedPreferencesGatt.getString("getLatitude","нет данных")+" "+
                        sharedPreferencesGatt.getString("getLongitude","нет данных")+" "+
                        sharedPreferencesGatt.getString("getLocale","нет данных")+" "+
                        sharedPreferencesGatt.getString("getThoroughfare","нет данных")+" "+
                        sharedPreferencesGatt.getString("getSubThoroughfare","нет данных")+" " );
            }else {
                contentValuesВставкаДанных.put("adress",  "нет данных" );

            }


            // TODO: 10.02.2023 версия данных
            // TODO: 10.02.2023 версия данных
           // Long current_table = upVesrionScannerGatt("SELECT MAX ( versionremote  ) AS MAX_R  FROM gattserverdataversion","gattserverdataversion");
            Long current_table = upVesrionScannerGatt("SELECT MAX ( current_table  ) AS MAX_R  FROM scannerserversuccess","scannerserversuccess");
            contentValuesВставкаДанных.put("current_table", current_table.toString() );


          //  Long version = upVesrionScannerGatt("SELECT MAX ( versionlocal  ) AS MAX_R  FROM gattserverdataversion","gattserverdataversion");
            Long version = upVesrionScannerGatt("SELECT MAX ( version  ) AS MAX_R  FROM scannerserversuccess","scannerserversuccess");
            contentValuesВставкаДанных.put("version", version.toString());


            Long getuuid =new GeneratorUUIDs(). МетодГенерацииUUID();
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
           new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return contentValuesВставкаДанных;
    }


    // TODO: 25.07.2024


    public   String      getDateStoreOperationsDeviceFronDatabase(@androidx.annotation.NonNull String СамЗапрос) {
        String ВремяДАнных=new String();
        try{
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/scannerserversuccess" );
            Cursor cursorПолучаемДЛяСевреа = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);
            if (cursorПолучаемДЛяСевреа.getCount()>0){
                cursorПолучаемДЛяСевреа.moveToFirst();
                  ВремяДАнных=      cursorПолучаемДЛяСевреа.getString(0);
                Log.i(this.getClass().getName(), "ВремяДАнных"+ ВремяДАнных) ;
            }
            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " cursorПолучаемДЛяСевреа.getCount() " +cursorПолучаемДЛяСевреа.getCount());
            // TODO: 19.07.2024 closing
            cursorПолучаемДЛяСевреа.close();

            Log.d(context.getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " ВремяДАнных " +ВремяДАнных);
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
        return  ВремяДАнных;
    }








    // TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public  Long upVesrionScannerGatt(@NonNull String СамЗапрос, @NonNull String nametable) {
        Long   ВерсияДАнных = 0l;
        try{
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/"+nametable+"" );

            Cursor cursorПолучаемДЛяСевреа = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);

            if (cursorПолучаемДЛяСевреа.getCount()>0) {
                cursorПолучаемДЛяСевреа.moveToFirst();
                ВерсияДАнных = cursorПолучаемДЛяСевреа.getLong(0);
                Log.i(this.getClass().getName(), "ВерсияДАнных" + ВерсияДАнных);
                // TODO: 10.09.2024  уеличиваем на 1 ++
            }
            // TODO: 10.09.2024  уеличиваем на 1 ++
            ВерсияДАнных++;
            Log.w(context.getClass().getName(), " РЕЗУЛЬТАТ insertData  cursorПолучаемДЛяСевреа  " +  cursorПолучаемДЛяСевреа.toString() );
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
           new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  ВерсияДАнных;
    }


// TODO: 25.07.2024


    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private Integer wtireNewSucceesDeviceOtGattServer(@androidx.annotation.NonNull ContentValues   contentValuesВставкаДанныхGattServer) {
        Uri    resultAddDeviceToGattaDtabse = null;
        try {
            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/scannerserversuccess" );
            // TODO: 04.09.2024  вставка данных на сервере 
            resultAddDeviceToGattaDtabse=   contentProviderServer.insert(uri, contentValuesВставкаДанныхGattServer);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
           new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return Integer.parseInt(resultAddDeviceToGattaDtabse.toString() );
    }

// TODO: 25.07.2024
    /////TODO: код Вытаскиваем из общего метоада

// TODO: 25.07.2024




    // TODO: 15.03.2023 синхрониазция КЛАсс
// TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public   ConcurrentHashMap<String,Cursor>  getallthedataofsuccessfuldevices(@androidx.annotation.NonNull String СамЗапрос) {
        //TODO
        ConcurrentHashMap<String,Cursor> cursorConcurrentHashMapGatt=new ConcurrentHashMap<>();
        try{

            Uri uri = Uri.parse("content://com.sous.servergatt.prodider/scannerserversuccess" );
            successfuldevices = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);
            if (successfuldevices.getCount()>0){
                // TODO: 19.07.2024  Запаопление данными Курсора
                cursorConcurrentHashMapGatt.compute("Cursor",(x,y)->successfuldevices);
            }
            // TODO: 19.07.2024 closing
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
           new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return  cursorConcurrentHashMapGatt;
    }






    private void getcloseCursorAndHashMap() {
        try {
            if (successfuldevices != null) {
                if (successfuldevices.isClosed() == false) {
                    successfuldevices.close();
                }
            }
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);

        }


    }





    @SuppressLint("MissingPermission")
    private void forwardUIAfterSuccessAddDiveceDatBAseScan(@androidx.annotation.NonNull ConcurrentHashMap<String,Cursor> concurrentHashMapCursor ,
                                                           @androidx.annotation.NonNull ContentValues    contentValuesВставкаДанныхScan) {
        try{




            //TODO:Event Send To Fragment Boot After Success DataBase and Divece
            sendStatusSucessEventBusDeviceScan(contentValuesВставкаДанныхScan, concurrentHashMapCursor);


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " contentValuesВставкаДанныхScan " + contentValuesВставкаДанныхScan);

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






    private   void sendStatusSucessEventBusDeviceScan(@androidx.annotation.NonNull ContentValues    contentValuesВставкаДанныхGaTT,
                                                      @androidx.annotation.NonNull ConcurrentHashMap<String,Cursor> concurrentHashMapCursor) {
        try{
            ParamentsScannerServer sendFragmentparamentsScannerServer=new ParamentsScannerServer();
            // TODO: 18.07.2024 sending status
            sendFragmentparamentsScannerServer.setCurrentTask("SuccessDeviceBluetoothAnServerScan");


            // TODO: 18.07.2024 sending HashMap
            contentValuesConcurrentHashMap.compute(contentValuesВставкаДанныхGaTT.getAsString("macdevice").toString(),(x,y)->contentValuesВставкаДанныхGaTT);
            sendFragmentparamentsScannerServer.setContentValuesConcurrentHashMap(contentValuesConcurrentHashMap);

            // TODO: 18.07.2024 sending Cursor
            sendFragmentparamentsScannerServer.setConcurrentHashMapCursor( concurrentHashMapCursor);


// TODO: 23.10.2024  проверяем если запцщено актвити
            BunesslogicisRunnigActivity bunesslogicisRunnigActivity=new BunesslogicisRunnigActivity(context,version);
         Boolean isRunningActivity=   bunesslogicisRunnigActivity.isRunning();


            if (isRunningActivity) {
                //TODO: послымаем Из Службы Значение на Фрагмент
                MessageScannerServer sendmessageScannerStartRecyreViewFragment= new MessageScannerServer( sendFragmentparamentsScannerServer);

                //TODO: ответ на экран работает ообрубование или нет
                EventBus.getDefault().post(sendmessageScannerStartRecyreViewFragment);
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " contentValuesConcurrentHashMap " +contentValuesConcurrentHashMap +
                    " concurrentHashMapCursor " +concurrentHashMapCursor+"isRunningActivity"+isRunningActivity);

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




    public void sendStatusSucessEventBusDONTDevice(@androidx.annotation.NonNull ConcurrentHashMap<String, Cursor> concurrentHashMapCursor) {
        try{
            ParamentsScannerServer sendFragmentparamentsScannerServer=new ParamentsScannerServer();
            // TODO: 18.07.2024 sending status
            sendFragmentparamentsScannerServer.setCurrentTask("SuccessDeviceBluetoothAnServerScan");


            // TODO: 18.07.2024 sending HashMap
            sendFragmentparamentsScannerServer.setContentValuesConcurrentHashMap(contentValuesConcurrentHashMap);
            // TODO: 18.07.2024 sending Cursor
            sendFragmentparamentsScannerServer.setConcurrentHashMapCursor( concurrentHashMapCursor);
            //TODO: послымаем Из Службы Значение на Фрагмент
            MessageScannerServer sendmessageScannerStartRecyreViewFragment= new MessageScannerServer( sendFragmentparamentsScannerServer);
            //TODO: ответ на экран работает ообрубование или нет
            EventBus.getDefault().post(sendmessageScannerStartRecyreViewFragment);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " contentValuesConcurrentHashMap " +contentValuesConcurrentHashMap +
                    " concurrentHashMapCursor " +concurrentHashMapCursor+" contentValuesВставкаДанныхGaTT.getAsString(\"macdevice\") ");

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



    Bundle getDateForNewInsertDivice(@NonNull String dateLocaleBase, @NonNull String dateLocaleNew) {
       // TODO: 30.07.2024 анализ
        Bundle getDateForNewInsertDivice=new Bundle();
       try {
           Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                   " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                   " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " dateLocaleNew " +dateLocaleNew
                   + "\n"+ " dateLocaleBase " +dateLocaleBase);

           // TODO: 25.07.2024 обрабоатываем даты
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

           LocalDateTime   getLiveDate = null;
           if (dateLocaleNew!=null) {
               try {
                   getLiveDate = LocalDateTime.parse(dateLocaleNew, formatter);
               } catch (Exception e) {
                   //throw new RuntimeException(e);
               }
           }
           LocalDateTime   getDatabaseDate = null;
           if (dateLocaleBase!=null) {
               try {
                   getDatabaseDate = LocalDateTime.parse(dateLocaleBase, formatter);
               } catch (Exception e) {
                 //  throw new RuntimeException(e);
               }
           }else {
               try {
                   getDatabaseDate = LocalDateTime.parse("2010-10-24 07:20:17.523", formatter);
               } catch (Exception e) {
                   //  throw new RuntimeException(e);
               }

           }
           // TODO: 23.10.2024
           getDateForNewInsertDivice.putSerializable("storedate",getDatabaseDate);
           getDateForNewInsertDivice.putSerializable("livedate",getLiveDate);


           Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                   " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                   " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                   + "\n"+ " getDatabaseDate " +getDatabaseDate+"\n" + "getLiveDate " +getLiveDate);

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
       return getDateForNewInsertDivice;

   }





    // TODO: 23.10.2024

    Integer serchDateDifference(@NonNull  LocalDateTime   databaseDate,@NonNull  LocalDateTime   LiveDate ) {
        // TODO: 30.07.2024 анализ
        Integer    getindoutthedateDifference=0;
        try {

            if (databaseDate !=null  && LiveDate!=null ) {
                getindoutthedateDifference = Math.abs(LiveDate.getMinute() - databaseDate.getMinute());
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
        return getindoutthedateDifference;

    }

// TODO: 25.07.2024 end class 

}
