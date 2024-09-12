package com.serverscan.datasync.businesslayer.bl_Jakson;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.ByteSource;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URIBuilder;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_okhttpclient.GetOkhhtpBuilder;
import com.serverscan.datasync.businesslayer.bl_okhttpclient.QualifierOkhhtp;
import com.serverscan.datasync.businesslayer.bl_preferences.BussenloginSaredPreferense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
import java.util.zip.GZIPOutputStream;

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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


public class BinesslogicJakson {
    private SharedPreferences preferences;

    private Context context;





    public  BinesslogicJakson(@NonNull  Context hiltcontext) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context = hiltcontext;
        // TODO: 06.09.2024


        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    @SuppressLint("NewApi")
    public  Long   sendOkhhtpServiceForJboss(@NonNull Context context, @NonNull long version,
                                             @NonNull LinkedHashMap<String, String> getJbossAdress, @NonNull Cursor cursorlocal,
                                             @NonNull  byte[] ByteJakson,
                                             @NonNull OkHttpClient.Builder getOkhhtpBuilder)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        AtomicReference<Long> буферОтветотJbossfinal= new AtomicReference(0l);
        try {
        // TODO: 28.08.2024
                    // TODO: 23.08.2024
        String ANDROID_ID= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    // TODO: 26.08.2024
                    // TODO: 27.08.2024 получаем данные и вставляем их в  URL для отправки
                    URL Adress = getUrlndParametrs(cursorlocal,getJbossAdress,version);

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +" Adress " +Adress);
                    OkHttpClient okHttpClientGattServer =getOkhhtpBuilder.addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    // TODO: 21.08.2024
                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                                    // TODO: 06.09.2024
                                    Request originalRequest = chain.request();
                                    Request.Builder builder = originalRequest.newBuilder()
                                            .header("Content-Type", "application/gzip" + " ;charset=UTF-8")
                                            .header("Accept-Encoding", "gzip,deflate,sdch")
                                            .header("Connection", "Keep-Alive")
                                            .header("Accept-Language", "ru-RU")
                                            .header("identifier", ANDROID_ID)
                                            .header("p_identifier", ANDROID_ID)
                                            .header("id_device_androis", ANDROID_ID);
                                    Request newRequest = builder.build();
                                    return chain.proceed(newRequest);
                                }
                            }).connectTimeout(3, TimeUnit.SECONDS)
                            .writeTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(1, TimeUnit.MINUTES)
                            .build();
                    // TODO: 06.09.2024 POST
                    MediaType JSON = MediaType.parse("application/octet-stream; charset=utf-8");
                    RequestBody requestBody = new RequestBody() {
                        @Override
                        public MediaType contentType() {

                                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                            return JSON;
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            // TODO: 21.09.2023 SEND BITY FROM SERVEER
                            try (GZIPOutputStream gzipOutputStream =       new GZIPOutputStream(sink.outputStream(),2048,true );){///4096
                                // TODO: 07.10.2023  wreting to server..
                                gzipOutputStream.write(ByteJakson);
                                gzipOutputStream.finish();
                                gzipOutputStream.close();
                                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
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
                        }
                    };


                    ///  MediaType JSON = MediaType.parse("application/json; charset=utf-16");
                    Request requestPost = new Request.Builder().post(requestBody).url(Adress).build();
                    Dispatcher dispatcherДанныеОтСервера = okHttpClientGattServer.dispatcher();
                    // TODO: 23.08.2024
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                    okHttpClientGattServer.newCall(requestPost).enqueue(new Callback() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Call call, @androidx.annotation.NonNull IOException e) {
                            // TODO: 31.05.2022SdispatcherДанныеОтСервера.executorService().shutdown();
                            // TODO: 23.08.2024
                            // TODO: 10.09.2024  cancel
                            call.cancel();
                            // TODO: 31.05.2022
                            dispatcherДанныеОтСервера.executorService().shutdownNow();
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
                                if (РазмерПришедшегоПотока>0l) {
                                    // TODO: 07.10.2023

                                    // TODO: 07.10.2023  Пришел ПОТОК
                                    InputStream inputStreamOtgattserver = new GZIPInputStream(response.body().source().inputStream(),2048);//4096

                                         // TODO: 07.10.2023  Обрабаотываем версию от сервера
                                    буферОтветотJbossfinal.set(versionOtGattServerCallback(inputStreamOtgattserver,КакаяКодировка,version));



                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " буферОтветотJbossfinal " +буферОтветотJbossfinal );



                                }

                                // TODO: 10.09.2024  cancel
                                call.cancel();
                                response.close();

                                // TODO: 31.05.2022
                                dispatcherДанныеОтСервера.executorService().shutdownNow();
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
                        // TODO: 09.09.2024
                        dispatcherДанныеОтСервера.cancelAll();
                        okHttpClientGattServer.connectionPool().evictAll();
                        // TODO: 10.09.2024
                        // TODO: 09.09.2024
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");


        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
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
        return буферОтветотJbossfinal.get();

    }



    @SuppressLint("NewApi")
    Long versionOtGattServerCallback(@NonNull  InputStream inputStreamOtgattserver, @NonNull Integer КакаяКодировка,@NonNull Long version){
        // TODO: 09.09.2024
        Long буферОтветотJbossfinal=0l;
        try{

        BufferedReader РидерОтСервераМетодаGET;//
        if (КакаяКодировка==8) {
            РидерОтСервераМетодаGET = new BufferedReader(new InputStreamReader(inputStreamOtgattserver, StandardCharsets.UTF_8));
        } else {
            РидерОтСервераМетодаGET = new BufferedReader(new InputStreamReader(inputStreamOtgattserver, StandardCharsets.UTF_16));
        }
        StringBuffer stringBufferGattSVersion=РидерОтСервераМетодаGET.lines().collect(StringBuffer::new, (sb, i) -> sb.append(i), StringBuffer::append);

        // TODO: 06.09.2024
         String буферОтветотJboss=Optional.ofNullable(stringBufferGattSVersion).stream().map(String::new).findAny().orElseGet(()->"");
        // TODO: 06.09.2024
          буферОтветотJbossfinal=Optional.ofNullable(буферОтветотJboss).stream().mapToLong(Long::new).findAny().orElseGet(()->0l);

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " буферОтветотJbossfinal " +буферОтветотJbossfinal );
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :"
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
    return  буферОтветотJbossfinal;

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
