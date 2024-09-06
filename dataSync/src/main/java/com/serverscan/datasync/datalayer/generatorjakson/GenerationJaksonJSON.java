package com.serverscan.datasync.datalayer.generatorjakson;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.AtomicDouble;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_dates.WorkerDates;
import com.serverscan.datasync.datalayer.model.ScannerserversuccessEntity;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Module
@InstallIn(SingletonComponent.class)
public class GenerationJaksonJSON {

 private  Context context;

    public @Inject GenerationJaksonJSON(@ApplicationContext Context hitcontext ) {
        // TODO: 25.08.2024
        context = hitcontext;
        // TODO: 25.08.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }

    // TODO: 04.09.2024


    public byte[]  genetarorJaksonJSON(@NonNull Context  context,
                                       @NonNull long version,
                                       @NonNull   List<ScannerserversuccessEntity>  listForJakson,
                                       @NonNull ObjectMapper getHiltJaksonObjectMapper)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        AtomicReference<byte[]> inputStreamJaksonSend = new AtomicReference();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 05.09.2024
                    ObjectWriter writer = getHiltJaksonObjectMapper.writerWithDefaultPrettyPrinter();
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(2048);

                    SequenceWriter sequenceWriter = writer .writeValues(byteArrayOutputStream).init(true) .writeAll(listForJakson);
                    // TODO: 09.02.2024 fulch
                    sequenceWriter.flush();
                    sequenceWriter.close();
                    inputStreamJaksonSend.set(byteArrayOutputStream.toByteArray());

                    // TODO: 09.02.2024 close
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();


                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n"  +
                             " listForJakson " +listForJakson + "inputStreamJaksonSend " +inputStreamJaksonSend);

                }).doOnComplete(()->{


                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                })
                .doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                            + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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

                }).blockingSubscribe();
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

        return inputStreamJaksonSend.get();

    }












// TODO: 05.09.2024
@SuppressLint("Range")
public List<ScannerserversuccessEntity> genetarorListFor(@NonNull Context  context, @NonNull long version, @NonNull Cursor cursorlocal) {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
    // TODO: 06.09.2024
    CopyOnWriteArrayList  copyOnWriteArrayListSendJboss=new CopyOnWriteArrayList();
        // TODO: 28.08.2024
        Completable.fromAction(()->{
                    // TODO: 06.09.2024
                   do {
                        // TODO: 06.09.2024  заполяем данными на базе Курсора
                        if (cursorlocal.getPosition()<cursorlocal.getCount()) {
                            ScannerserversuccessEntity    scannerserversuccessEntity = processtheCursorandfillinmodel(cursorlocal ,version);
                            // TODO: 06.09.2024 далее заполяем Лист
                            copyOnWriteArrayListSendJboss.add(scannerserversuccessEntity);
                        }
                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
                    } while (  cursorlocal.moveToNext());
                    // TODO: 06.09.2024 ЗАполяем данными Класс Для Отправки НА сервер
                    // TODO: 06.09.2024 end
                }).doOnComplete(()->{
                    // TODO: 06.09.2024
                    if (cursorlocal!=null) {
                        cursorlocal.moveToFirst();
                    }
                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                })
                .doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                            + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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

                })
                .subscribeOn(Schedulers.single())
                .blockingSubscribe();
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

        return copyOnWriteArrayListSendJboss;

    }








    @NonNull
    @SuppressLint("Range")
    private ScannerserversuccessEntity processtheCursorandfillinmodel(@NonNull Cursor cursorlocal,@NonNull Long version) {
        // TODO: 06.09.2024  получаем данные из курсора
        ScannerserversuccessEntity scannerserversuccessEntity = null;
        try {
            // TODO: 06.09.2024  Создаем Обьект из Класса для вставки  
              scannerserversuccessEntity= new ScannerserversuccessEntity();

            // TODO: 06.09.2024 Получаем данные курсора 
            Integer id= Optional.ofNullable( cursorlocal.getInt(cursorlocal.getColumnIndex("id"))).orElse(0);
            String operations=    Optional.ofNullable( cursorlocal.getString(cursorlocal.getColumnIndex("operations"))).orElse("");
            String completedwork=      Optional.ofNullable( cursorlocal.getString(cursorlocal.getColumnIndex("completedwork"))).orElse("");
            String namedevice=    Optional.ofNullable(  cursorlocal.getString(cursorlocal.getColumnIndex("namedevice"))).orElse("");
            String macdevice=   Optional.ofNullable(   cursorlocal.getString(cursorlocal.getColumnIndex("macdevice"))).orElse("");
            String gps1=   Optional.ofNullable(    cursorlocal.getString(cursorlocal.getColumnIndex("gps1"))).orElse("");
            String gps2=  Optional.ofNullable(     cursorlocal.getString(cursorlocal.getColumnIndex("gps2"))).orElse("");
            Integer getstatusrow=  Optional.ofNullable(    cursorlocal.getInt(cursorlocal.getColumnIndex("getstatusrow"))).orElse(0);
            String adress=    Optional.ofNullable(     cursorlocal.getString(cursorlocal.getColumnIndex("adress"))).orElse("");
            String city=    Optional.ofNullable( cursorlocal.getString(cursorlocal.getColumnIndex("city"))).orElse("");
            String date_update=  Optional.ofNullable(    cursorlocal.getString(cursorlocal.getColumnIndex("date_update"))).orElse("");
            Long uuid=  Optional.ofNullable(    cursorlocal.getLong(cursorlocal.getColumnIndex("uuid"))).orElse(0l);
            Long Version=     Optional.ofNullable(  cursorlocal.getLong(cursorlocal.getColumnIndex("version"))).orElse(0l);
            Integer sim=    Optional.ofNullable(  cursorlocal.getInt(cursorlocal.getColumnIndex("sim"))).orElse(0);
            String iemi=    Optional.ofNullable(  cursorlocal.getString(cursorlocal.getColumnIndex("iemi"))).orElse("");
            Long current_table=    Optional.ofNullable(  cursorlocal.getLong(cursorlocal.getColumnIndex("current_table"))).orElse(0l);

            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" +" current_table " +current_table);


            // TODO: 06.09.2024 Заполняем данными
            scannerserversuccessEntity.setId(id);
            scannerserversuccessEntity.setOperations(operations);
            scannerserversuccessEntity.setCompletedwork(completedwork);
            scannerserversuccessEntity.setNamedevice(namedevice);
            scannerserversuccessEntity.setMacdevice(macdevice);
            scannerserversuccessEntity.setGps1(gps1);
            scannerserversuccessEntity.setGps2(gps2);
            scannerserversuccessEntity.setGetstatusrow(getstatusrow);
            scannerserversuccessEntity.setAdress(adress);
            scannerserversuccessEntity.setCity(city);


            // TODO: 06.09.2024 get DATETIME
            WorkerDates workerDates=new WorkerDates(context,version);
            String datestring=cursorlocal.getString(cursorlocal.getColumnIndex("date_update"));
            Date   Date_update = workerDates.datesasDates(datestring);
            scannerserversuccessEntity.setDateUpdate(Date_update);

            // TODO: 06.09.2024 UUID
            BigDecimal bigDecimaluuid=BigDecimal.valueOf(uuid);
            scannerserversuccessEntity.setUuid(bigDecimaluuid);


            scannerserversuccessEntity.setVersion(Version);
            scannerserversuccessEntity.setSim(sim);
            scannerserversuccessEntity.setIemi(iemi);


            // TODO: 06.09.2024  get Vrrsion date
            BigDecimal bigDecimalcurrent_table=BigDecimal.valueOf(current_table);
            scannerserversuccessEntity.setCurrentTable(bigDecimalcurrent_table);


            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" +" scannerserversuccessEntity " +scannerserversuccessEntity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        return scannerserversuccessEntity;
    }


















}
