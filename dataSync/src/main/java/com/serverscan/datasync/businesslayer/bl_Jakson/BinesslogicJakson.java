package com.serverscan.datasync.businesslayer.bl_Jakson;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.ByteSource;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URIBuilder;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicJakson {

    Context context;

    public @Inject BinesslogicJakson(@ApplicationContext Context hiltcontext) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context = hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }

    public StringBuffer sendOkhhtpServiceForJboss(@NonNull Context context,
            @NonNull long version, @NonNull OkHttpClient.Builder getOkhhtpBuilder,
                                                 @NonNull LinkedHashMap<String, String> getJbossAdress,
                                                 @NonNull Cursor cursorlocal,
                                                  byte[] ByteJakson)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        final String[] ANDROID_ID = {new String()};
        AtomicReference<StringBuffer> inputStreamJaksonSend = new AtomicReference();
        // TODO: 28.08.2024
        Completable.fromAction(()->{

                    // TODO: 23.08.2024
                    ANDROID_ID[0] = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    // TODO: 26.08.2024
                    // TODO: 27.08.2024 получаем данные и вставляем их в  URL для отправки
                    URL Adress = getUrlndParametrs(cursorlocal,getJbossAdress,version);

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +" Adress " +Adress);
                    OkHttpClient okHttpClientGattServer = getOkhhtpBuilder.addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    // TODO: 21.08.2024
                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                                    Request originalRequest = chain.request();
                                    Request.Builder builder = originalRequest.newBuilder()
                                            .header("Content-Type", "application/gzip" + " ;charset=UTF-8")
                                            .header("Accept-Encoding", "gzip,deflate,sdch")
                                            .header("Connection", "Keep-Alive")
                                            .header("Accept-Language", "ru-RU")
                                            .header("identifier", ANDROID_ID[0])
                                            .header("p_identifier", ANDROID_ID[0])
                                            .header("id_device_androis", ANDROID_ID[0]);
                                    Request newRequest = builder.build();
                                    return chain.proceed(newRequest);
                                }
                            }).connectTimeout(3, TimeUnit.SECONDS)
                            .writeTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(1, TimeUnit.MINUTES)
                            .build();
                    ///  MediaType JSON = MediaType.parse("application/json; charset=utf-16");
                    Request requestGET = new Request.Builder().get().url(Adress).build();
                    Dispatcher dispatcherДанныеОтСервера = okHttpClientGattServer.dispatcher();
                    // TODO: 23.08.2024
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                    okHttpClientGattServer.newCall(requestGET).enqueue(new Callback() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Call call, @androidx.annotation.NonNull IOException e) {
                            // TODO: 31.05.2022
                            dispatcherДанныеОтСервера.executorService().shutdown();
                            // TODO: 23.08.2024
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                            //TODO закрываем п отоки
                        }

                        @Override
                        public void onResponse(@androidx.annotation.NonNull Call call, @androidx.annotation.NonNull Response response) throws IOException {
                            // TODO: 04.09.2024  
                            if (response.isSuccessful()) {
                                String ПришедшегоПотока = response.header("stream_size");
                                ПришедшегоПотока = Optional.ofNullable(ПришедшегоПотока).map(String::valueOf).orElse("0");
                                Long РазмерПришедшегоПотока = Long.parseLong(ПришедшегоПотока);
                                // TODO: 29.09.2023
                                Integer КакаяКодировка = Integer.parseInt(Optional.ofNullable(response.header("getcharsets")).map(String::new).orElse("0"));
                                Boolean ФлагgZIPOutputStream = Boolean.parseBoolean(Optional.ofNullable(response.header("GZIPOutputStream")).map(String::new).orElse("false"));
                                if (РазмерПришедшегоПотока > 0l) {
                                    // TODO: 07.10.2023  gzip
                                    byte[] asByteBuffer = response.body().source().readByteArray();
                                    // TODO: 04.09.2024  
                                    InputStream callOtServerJboss=            new GZIPInputStream(ByteSource.wrap(asByteBuffer).openBufferedStream(), 2048);//4096
                                    // TODO: 30.08.2024
                                    // TODO: 23.08.2024
                                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " callOtServerJboss.available() " +callOtServerJboss.available());

                                }
                                // TODO: 31.05.2022
                                dispatcherДанныеОтСервера.executorService().shutdown();
                                // TODO: 23.08.2024
                                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                            }

                        }
                    });
                    //TODO
                    try {
                        dispatcherДанныеОтСервера.executorService().awaitTermination(1, TimeUnit.DAYS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    dispatcherДанныеОтСервера.cancelAll();

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

                }).doOnComplete(()->{

                    // TODO: 31.07.2024
                    if (cursorlocal!=null) {
                        cursorlocal.close();
                    }

                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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








    @SuppressLint("Range")
    private URL getUrlndParametrs(@NonNull Cursor cursorlocal ,
                                  @NonNull LinkedHashMap<String,
            String> getJbossAdress,@NonNull Long version) {
        // TODO: 27.08.2024
        AtomicReference <URL> Adress = new AtomicReference<>();

        Completable.fromAction(()->{

                    // TODO: 29.08.2024 время
                    String  bremylocal=prossecingBremy(cursorlocal);
                    // TODO: 29.08.2024 версия
                    Long  versionlocal=   prossecingVersion(cursorlocal);


                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " bremylocal " + bremylocal
                            + " versionlocal " + versionlocal+ " bremylocal " + bremylocal);

                    // TODO: 02.04.2024  Адресс и Порт Сервера Jboss
                    String getPortServer = getJbossAdress.values().stream().findFirst().orElseGet(()->"");
                    String getNameServer = getJbossAdress.keySet().stream().findFirst().orElseGet(()->"");
                    String СтрокаСвязиСсервером = "http://" + getNameServer + ":" + getPortServer;


                    try {
                        HttpGet someHttpPost = new HttpGet(СтрокаСвязиСсервером);
                        URIBuilder builder = new URIBuilder(someHttpPost.getURI());
                        builder.setParameter("NameTable", "scannerserversuccess")
                                .setParameter("JobForServer", "sendgattserver")
                                .setParameter("bremylocal", bremylocal)
                                .setParameter("versionlocal", versionlocal.toString());
                        URI adresssuri  = builder.build();
                        Adress.set(adresssuri.toURL());
                        // TODO: 31.07.2024
                        Log.d(this.getClass().getName(), "\n" + " class " +
                                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "Adress.get() " +Adress.get());
                    } catch (URISyntaxException | MalformedURLException e) {
                        throw new RuntimeException(e);
                    }


                }).doOnError(e->{
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
                .doOnComplete(()->{
                    Log.d(context.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                })
                .blockingSubscribe( );
        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "Adress " + Adress.get());

        return Adress.get();
    }



    @SuppressLint("Range")
    private  String prossecingBremy(@NonNull Cursor cursorlocal){
        String bremylocal=new String();
        if (cursorlocal.getCount() >0) {
            DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ru", "RU"));
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


    @SuppressLint("Range")
    private  Long prossecingVersion(@NonNull Cursor cursorlocal){
        Long versionlocal=0l;
        if (cursorlocal.getCount() >0) {
            // TODO: 27.08.2024 version data
            versionlocal = cursorlocal.getLong(cursorlocal.getColumnIndex("current_table"));
        }
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "versionlocal " + versionlocal);
        return versionlocal;
    }


    // TODO: 04.09.2024 end Class
}
