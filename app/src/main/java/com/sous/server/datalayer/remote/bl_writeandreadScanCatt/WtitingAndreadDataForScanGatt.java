package com.sous.server.datalayer.remote.bl_writeandreadScanCatt;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.sous.server.businesslayer.ContentProvoders.ContentProviderServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;

import org.greenrobot.eventbus.EventBus;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class WtitingAndreadDataForScanGatt {
    // TODO: 25.07.2024
  private Context context;
    private Long version;
    private ContentProviderServer contentProviderServer;
    private  SharedPreferences sharedPreferencesGatt;
    private  Cursor successfuldevices;
    protected ConcurrentHashMap<String,ContentValues>       contentValuesConcurrentHashMap=new ConcurrentHashMap<>();

    public WtitingAndreadDataForScanGatt(Context context, Long version,
                                         ContentProviderServer contentProviderServer,
                                         SharedPreferences sharedPreferencesGatt,
                                         Cursor successfuldevices) {
        this.context = context;
        this.version = version;
        this.contentProviderServer = contentProviderServer;
        this.sharedPreferencesGatt = sharedPreferencesGatt;
        this.successfuldevices = successfuldevices;
    }


    // TODO: 25.07.2024 метод Записи  в базу
    public void writeDatabaseScanGatt(@NonNull BluetoothDevice device, @NonNull Cursor successfuldevices,@NonNull Integer newState){
        Completable.complete().blockingSubscribe(new CompletableObserver() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSubscribe(@NonNull Disposable d) {

                // TODO: 25.07.2024 Код Записи в базу данных ScanGatt
                //TODO:ЗАписываем Новый Успешный Девайс в Базу от Gatt server
                ContentValues contentValuesВставкаДанных = addToContevaluesNewSucceesDeviceOtGattServer(device,newState );

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  contentValuesВставкаДанных " +contentValuesВставкаДанных);


                // TODO: 25.07.2024

                String   getcurrentDatefromthedatabase =  getDateStoreOperationsDeviceFronDatabase("SELECT    MAX ( date_update )   FROM scannerserversuccess" +
                        " WHERE   macdevice = '"+contentValuesВставкаДанных.getAsString("macdevice").trim() +"'");

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  getcurrentDatefromthedatabase " +getcurrentDatefromthedatabase);


// TODO: 25.07.2024  результат дата старше полу часа или нет
          long getMinute=      findoutthedateDifference(getcurrentDatefromthedatabase,contentValuesВставкаДанных.getAsString("date_update").trim());




                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  getcurrentDatefromthedatabase " +getcurrentDatefromthedatabase+"\n" + "getMinute  " +getMinute);


                Integer   resultAddDeviceToGattaDtabse= 0;


                if (getMinute>1 || getMinute==0) {
                    // TODO: 09.02.2023  запись в базу дивайса Отметка сотрдунка
                    resultAddDeviceToGattaDtabse = wtireNewSucceesDeviceOtGattServer(contentValuesВставкаДанных);
                }


                // TODO: 18.07.2024 ЕСЛИ Успещно прошла Операция передаем данные на Фрагмент Scanner
                if (resultAddDeviceToGattaDtabse >0) {

                    // TODO: 25.07.2024 closeing Before
                    getcloseCursorAndHashMap();
                    //todo ДОполнительный механизм данные упокаываем в Канкаренте СЕТ с Курсором


                    ConcurrentHashMap<String, Cursor> concurrentHashMapCursor = getallthedataofsuccessfuldevices("SELECT *    FROM scannerserversuccess");

                    Log.i(this.getClass().getName(), " resultAddDeviceToGattaDtabse " + resultAddDeviceToGattaDtabse +
                            " contentValuesВставкаДанных " + contentValuesВставкаДанных + " device.getAddress().toString() " +device.getAddress().toString()+
                            "  evice.getName().toString()  "+device.getName().toString()+ " concurrentHashMapCursor " +concurrentHashMapCursor+
                            " SUCCESS SUCCESS SUCCESS !!!! resultAddDeviceToGattaDtabse " +resultAddDeviceToGattaDtabse);

                    // TODO: 19.07.2024 Посылаем Пользователю сообщение что данные изменились
                    forwardUIAfterSuccessAddDiveceDatBAseScan(concurrentHashMapCursor, contentValuesВставкаДанных);


                }

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");








                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()); 
            }

            @Override
            public void onComplete() {
                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                
                Log.d(context.getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                
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
                new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);


        }
        });
    }


    // TODO: 25.07.2024

    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private ContentValues addToContevaluesNewSucceesDeviceOtGattServer(@NonNull BluetoothDevice device,@NonNull Integer newState) {
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
                LocalDateTime futureDate = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                  String date_update=   dtf.format(futureDate);

            // TODO: 25.07.2024 set date
            contentValuesВставкаДанных.put("date_update", date_update.toString());

            contentValuesВставкаДанных.put("completedwork", "простое сканирование...");
            contentValuesВставкаДанных.put("operations", newState.toString());



            contentValuesВставкаДанных.put("city",    sharedPreferencesGatt.getString("getLocality",""));
            contentValuesВставкаДанных.put("gps1", sharedPreferencesGatt.getString("getLongitude",""));
            contentValuesВставкаДанных.put("gps2", sharedPreferencesGatt.getString("getLatitude",""));
            contentValuesВставкаДанных.put("adress",  sharedPreferencesGatt.getString("getCountryName","")+" "+
                    sharedPreferencesGatt.getString("getLocality","")+" "+
                    sharedPreferencesGatt.getString("getSubAdminArea","")+" "+
                    sharedPreferencesGatt.getString("getLatitude","")+" "+
                    sharedPreferencesGatt.getString("getLongitude","")+" "+
                    sharedPreferencesGatt.getString("getLocale","")+" "+
                    sharedPreferencesGatt.getString("getThoroughfare","")+" "+
                    sharedPreferencesGatt.getString("getSubThoroughfare","")+" " );




            // TODO: 10.02.2023 версия данных
            // TODO: 10.02.2023 версия данных
            Integer current_table = МетодПоискДАнныхПоБазе("SELECT MAX ( current_table  ) AS MAX_R  FROM scannerserversuccess");
            contentValuesВставкаДанных.put("current_table", current_table);



            Integer version = МетодПоискДАнныхПоБазе("SELECT MAX ( version  ) AS MAX_R  FROM scannerserversuccess");
            contentValuesВставкаДанных.put("version", version);


            Long getuuid = МетодГенерацииUUID();
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return contentValuesВставкаДанных;
    }


    // TODO: 25.07.2024


    public   String      getDateStoreOperationsDeviceFronDatabase(@androidx.annotation.NonNull String СамЗапрос) {
        String ВремяДАнных=new String();
        try{
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ВремяДАнных;
    }








    // TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public  Integer МетодПоискДАнныхПоБазе(@androidx.annotation.NonNull String СамЗапрос) {
        Integer   ВерсияДАнных = 0;
        try{
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );



            Cursor cursorПолучаемДЛяСевреа = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);


            cursorПолучаемДЛяСевреа.moveToFirst();
            if (cursorПолучаемДЛяСевреа.getCount()>0){
                ВерсияДАнных=      cursorПолучаемДЛяСевреа.getInt(0);
                Log.i(this.getClass().getName(), "ВерсияДАнных"+ ВерсияДАнных) ;
                ВерсияДАнных++;
            }
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return  ВерсияДАнных;
    }


// TODO: 25.07.2024


    // TODO: 14.02.2023 Второй Метод БЕз GPS
    @SuppressLint("MissingPermission")
    private Integer wtireNewSucceesDeviceOtGattServer(@androidx.annotation.NonNull ContentValues   contentValuesВставкаДанныхGattServer) {
        Uri    resultAddDeviceToGattaDtabse = null;
        try {
            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return Integer.parseInt(resultAddDeviceToGattaDtabse.toString() );
    }

// TODO: 25.07.2024
    /////TODO: код Вытаскиваем из общего метоада

    @androidx.annotation.NonNull
    private Long МетодГенерацииUUID() {
        Long getUUID = 0l;
        try{

            UUID uuid = UUID.randomUUID();

            //uuid   .toString().replaceAll("-", "").replaceAll("[a-zA-Z]", "").substring(0, 20);
            ///uuid = uuid.replaceAll("[a-zA-Z]", "");
            //uuid= CharMatcher.any().replaceFrom("[A-Za-z0-9]", "");
            Long fff2=  uuid.getLeastSignificantBits();
            Long fff3=  uuid.getMostSignificantBits();
            BigInteger bigInteger=BigInteger.valueOf(fff3).abs();
            getUUID= bigInteger.longValue();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " uuid " +uuid );

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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }
        return getUUID;
    }

// TODO: 25.07.2024




    // TODO: 15.03.2023 синхрониазция КЛАсс
// TODO: 10.02.2023 МЕТОД ВЫБОР ДАННЫХ
    public   ConcurrentHashMap<String,Cursor>  getallthedataofsuccessfuldevices(@androidx.annotation.NonNull String СамЗапрос) {
        //TODO
        ConcurrentHashMap<String,Cursor> cursorConcurrentHashMapGatt=new ConcurrentHashMap<>();
        try{

            Uri uri = Uri.parse("content://com.sous.server.providerserver/scannerserversuccess" );
            successfuldevices = contentProviderServer.query(uri, null, СамЗапрос, null,null,null);
            if (successfuldevices.getCount()>0){
                successfuldevices.move(successfuldevices.getCount());
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
        }


    }





    @SuppressLint("MissingPermission")
    private void forwardUIAfterSuccessAddDiveceDatBAseScan(@androidx.annotation.NonNull ConcurrentHashMap<String,Cursor> concurrentHashMapCursor ,
                                                           @androidx.annotation.NonNull ContentValues    contentValuesВставкаДанныхScan) {
        try{

            Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            v2.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));


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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
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
            new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки,contentProviderServer);
        }

    }


    long findoutthedateDifference(@NonNull String getcurrentDatefromthedatabase,@NonNull String getLiveDatefromthedatabase) {
        long getMinute=0;
       try {
           Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                   " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                   " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " getcurrentDatefromthedatabase " +getcurrentDatefromthedatabase
                   + "\n"+ " getLiveDatefromthedatabase " +getLiveDatefromthedatabase);

           // TODO: 25.07.2024 обрабоатываем даты
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

           LocalDateTime   LiveDate = null;
           try {
               LiveDate = LocalDateTime.parse(getLiveDatefromthedatabase, formatter);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
           LocalDateTime   databaseDate = null;
           try {
               databaseDate = LocalDateTime.parse(getcurrentDatefromthedatabase, formatter);
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
           Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                   " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                   " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " LiveDate " +LiveDate+"\n" + "databaseDate " +databaseDate);

           getMinute = Math.abs(LiveDate.getMinute() - databaseDate.getMinute());
          // getMinute = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

           Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                   " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                   " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " getMinute " +getMinute);

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
           new SubClassErrors(context).МетодЗаписиОшибокИзServerGatt(valuesЗаписываемОшибки, contentProviderServer);
       }
       return getMinute;

   }

// TODO: 25.07.2024 end class 

}
