package com.scanner.datasync.businesslayer.bl_DataSyncService;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.ByteSource;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URIBuilder;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Module
@InstallIn(SingletonComponent.class)
public class BinesslogicDataSync {

    Context context;


    public @Inject BinesslogicDataSync(@ApplicationContext Context hiltcontext) {
        // TODO: 22.08.2024
        // TODO: 21.08.2024
        context = hiltcontext;
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    public void callBackBroadcastManagerDataSyncService(@NonNull long version) {
        try {
            Intent intent = new Intent("DataSyncService");
            // You can also include some extra data.
            intent.putExtra("message", "DataSyncServiceEnding");
            LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);

            // TODO: 31.07.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                    + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");

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


    @SuppressLint("Range")
    public    InputStream    callOkhhtpDataSyncService(@NonNull long version, @NonNull OkHttpClient.Builder getOkhhtpBuilder,
                                          @NonNull LinkedHashMap<String, String> getJbossAdress,
                                          @NonNull Cursor cursorlocal)
            throws ExecutionException, InterruptedException {
        // TODO: 22.08.2024  Коненпт провайдер для зааписив базу данных
        final String[] ANDROID_ID = {new String()};
        final InputStream[] inputStreamJaksonByte = new InputStream[1];
        Completable.fromAction(()->{

            // TODO: 23.08.2024
            ANDROID_ID[0] = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            // TODO: 26.08.2024
            // TODO: 27.08.2024 получаем данные и вставляем их в  URL для отправки
            URL Adress = getUrlndParametrs(cursorlocal,getJbossAdress,version);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +" Adress " +Adress);
            OkHttpClient okHttpClientClientScanner = getOkhhtpBuilder.addInterceptor(new Interceptor() {
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
            Dispatcher dispatcherДанныеОтСервера = okHttpClientClientScanner.dispatcher();
            // TODO: 23.08.2024
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            okHttpClientClientScanner.newCall(requestGET).enqueue(new Callback() {
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
                            inputStreamJaksonByte[0] = new GZIPInputStream(ByteSource.wrap(asByteBuffer).openBufferedStream(), 2048);//4096
                            Log.d(this.getClass().getName(), "inputStreamJaksonByte[0] " + inputStreamJaksonByte[0].available() +
                                    " РазмерПришедшегоПотока " + РазмерПришедшегоПотока + " inputStreamJaksonByte[0] " +inputStreamJaksonByte[0]);

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

        return inputStreamJaksonByte[0];

    }










    @SuppressLint("Range")
    private URL getUrlndParametrs(@NonNull Cursor cursorlocal , @NonNull LinkedHashMap<String, String> getJbossAdress,@NonNull Long version) {
        // TODO: 27.08.2024
        final URL[] Adress = new URL[1];

        Completable.complete().blockingSubscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Long  versionlocal=0l;
                String  bremylocal=new String();
                if (cursorlocal.getCount() > 0) {
                    // TODO: 27.08.2024
                   versionlocal = cursorlocal.getLong(cursorlocal.getColumnIndex("current_table"));
                    // TODO: 27.08.2024 bremy
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", new Locale("ru", "RU"));
                    LocalDateTime futureDate = LocalDateTime.parse(cursorlocal.getString(cursorlocal.getColumnIndex("date_update")), dtf);
                    bremylocal = dtf.format(futureDate);
                }

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " bremylocal " + bremylocal
                        + " versionlocal " + versionlocal);

                // TODO: 02.04.2024  Адресс и Порт Сервера Jboss
                String getPortServer = getJbossAdress.values().stream().findFirst().orElseGet(()->"");
                String getNameServer = getJbossAdress.keySet().stream().findFirst().orElseGet(()->"");
                String СтрокаСвязиСсервером = "http://" + getNameServer + ":" + getPortServer;


                try {
                    HttpGet someHttpGet = new HttpGet(СтрокаСвязиСсервером);
                    URIBuilder    builder = new URIBuilder(someHttpGet.getURI());
                    builder.setParameter("NameTable", "listMacMastersSous")
                            .setParameter("JobForServer", "getscanner")
                            .setParameter("bremylocal", bremylocal)
                            .setParameter("versionlocal", versionlocal.toString());
                    URI   adresssuri  = builder.build();
                    Adress[0] =  adresssuri.toURL();
                    // TODO: 31.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                            + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "Adress " + Adress[0]);
                } catch (URISyntaxException | MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onComplete() {
                // TODO: 31.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                        + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "Adress " + Adress[0]);
            }

            @Override
            public void onError(@NonNull Throwable e) {
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
        });



        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n" + "Adress " + Adress[0]);

        return Adress[0];
    }





    public void callJaksonDataSyncService(@NonNull long version, @NonNull ObjectMapper getHiltJaksonObjectMapper) {
        Completable.complete().blockingSubscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // TODO: 31.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                        + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
            }

            @Override
            public void onComplete() {
                // TODO: 31.07.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                        + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
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
        });

        // TODO: 31.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n"
                + " LocalDateTime.now() " + LocalDateTime.now().toString().toUpperCase() + "\n");
    }

}
// TODO: 23.08.2024 END  


