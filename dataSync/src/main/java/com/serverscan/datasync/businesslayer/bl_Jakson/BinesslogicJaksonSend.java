package com.serverscan.datasync.businesslayer.bl_Jakson;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URIBuilder;
import com.serverscan.datasync.businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.businesslayer.bl_versionsgatt.BinesslogicVersions;

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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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


public class BinesslogicJaksonSend {
    private SharedPreferences preferences;

    private Context context;





    public BinesslogicJaksonSend(@NonNull  Context hiltcontext) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context = hiltcontext;
        // TODO: 06.09.2024


        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    @SuppressLint("NewApi")
    public  void sendOkhhtpServiceForSendJboss(@NonNull Context context, @NonNull long version,
                                               @NonNull LinkedHashMap<String, String> getJbossAdress, @NonNull Cursor cursorlocal,
                                               @NonNull  byte[] ByteJakson,
                                               @NonNull OkHttpClient.Builder getOkhhtpBuilder)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        AtomicReference<Long> callBacksqlserverdataVersion= new AtomicReference(0l);
        ExecutorService executorServiceDispacher=null;
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

              executorServiceDispacher=  getOkhhtpBuilder.getDispatcher$okhttp().executorService();
            executorServiceDispacher=Executors.newSingleThreadExecutor();
                    OkHttpClient okHttpClientGattServerSending =getOkhhtpBuilder.addInterceptor(new Interceptor() {
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
                            }).connectTimeout(5, TimeUnit.SECONDS)
                            .writeTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(1, TimeUnit.MINUTES)
                            .build();
                    // TODO: 06.09.2024 POST



            Dispatcher  dispatcherPost=     setPoolDispatcher(context, okHttpClientGattServerSending,version);


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
                    // TODO: 23.08.2024
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            ExecutorService finalExecutorServiceDispacher = executorServiceDispacher;
            okHttpClientGattServerSending.newCall(requestPost).enqueue(new Callback() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Call call, @androidx.annotation.NonNull IOException e) {
                            // TODO: 23.08.2024
                            // TODO: 31.07.2024 close database
                            clostingdatabase(cursorlocal);
                            // TODO: 10.09.2024  cancel
                            call.cancel();
                            // TODO: 31.05.2022
                            finalExecutorServiceDispacher.shutdown();
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
                                    callBacksqlserverdataVersion.set(versionOtGattServerCallback(inputStreamOtgattserver,КакаяКодировка,version));

                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " callBacksqlserverdataVersion.get() " +callBacksqlserverdataVersion.get() );

                                    // TODO: 02.10.2024 код заыершающие после успешной отпарвки даных
                                    endingProcessesaftersendingdatatothejbossserver(cursorlocal,version,callBacksqlserverdataVersion.get());

                                    // TODO: 31.05.2022
                                    finalExecutorServiceDispacher.shutdown();

                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " callBacksqlserverdataVersion " +callBacksqlserverdataVersion );
                                }
                                // TODO: 10.09.2024  cancel
                                call.cancel();
                                response.close();
                                // TODO: 23.08.2024
                                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                            }
                        }
                    });
            // TODO: 27.09.2024
            // TODO: 31.05.2022
            executorServiceDispacher.awaitTermination(5,TimeUnit.MINUTES);
            // TODO: 27.09.2024
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


    }

    private   void endingProcessesaftersendingdatatothejbossserver(@NonNull Cursor cursorlocal,@NonNull Long version, @NonNull Long буферОтветотJbossfinal) {
      try{
        if (буферОтветотJbossfinal>0  ) {
            // TODO: 10.09.2024 дополнительное увеличение версии данных уже в рабочей текуще версии чтобы большене вставлять дополнительно
            new BinesslogicVersions(context).recordingAfterNewVersionwealign(context,version);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" буферОтветотJbossfinal " +буферОтветотJbossfinal);
        }

        // TODO: 31.07.2024 close database
        clostingdatabase(cursorlocal);

        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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









    private Dispatcher setPoolDispatcher(@NonNull Context context, @NonNull OkHttpClient okHttpClientGattServer, @NonNull Long version) throws InterruptedException {
        // TODO: 01.10.2024
        Dispatcher  dispatcherPost=null;
        try{
            okHttpClientGattServer.connectionPool().evictAll();
                dispatcherPost= okHttpClientGattServer.dispatcher();
            dispatcherPost.cancelAll();
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                +"  okHttpClientGattServer.connectionPool() " + okHttpClientGattServer.connectionPool().connectionCount());
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
        return        dispatcherPost;
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

    private   void clostingdatabase(Cursor cursorSingle) {
        if (cursorSingle !=null) {
            cursorSingle.close();
        }
        Log.d(context.getClass().getName(), "\n" + " class " +
                Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }

    // TODO: 04.09.2024 end Class
}
