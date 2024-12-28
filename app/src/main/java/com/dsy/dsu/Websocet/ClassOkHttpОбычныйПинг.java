package com.dsy.dsu.Websocet;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class  ClassOkHttpОбычныйПинг {
    OkHttpClient  okHttpClient;
    public  void методОбычногоПодключениявOkHttp(@NonNull Context context, @NotNull SSLSocketFactory getsslSocketFactory ) {
        try {
            StringBuffer stringBuffer = new StringBuffer();


       OkHttpClient.Builder builder=     new OkHttpClient().newBuilder();
            builder.socketFactory(getsslSocketFactory);

            builder.connectionPool(new ConnectionPool(100,5, TimeUnit.SECONDS));
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            Request.Builder builder = originalRequest.newBuilder();
                            Request newRequest = builder.build();
                            return chain.proceed(newRequest);
                        }
                    }).connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .build();
            Request requestGET = new Request.Builder().get().url("http://192.168.254.40:8080/websocet/gEt").build();////"http://192.168.254.40:8080/websocet/gEt"
            Log.d(this.getClass().getName(), "  request  " + requestGET);

            okHttpClient.newCall(requestGET).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(this.getClass().getName(), "  ERROR call  " + call + "  e" + e.toString());
                    okHttpClient.dispatcher().executorService().shutdown();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try{
                        if (response.isSuccessful()) {
                            String  ПришедшегоПотока =    response.header("stream_size");
                            ПришедшегоПотока =     Optional.ofNullable(ПришедшегоПотока).map(String::valueOf).orElse("0");
                            Long РазмерПришедшегоПотока = Long.parseLong(ПришедшегоПотока  );
                            Integer КакаяКодировка = Integer.parseInt(   Optional.ofNullable(response.header("getcharsets")).map(String::new).orElse("0"));
                            Boolean ФлагgZIPOutputStream =Boolean.parseBoolean (  Optional.ofNullable(response.header("GZIPOutputStream")).map(String::new).orElse("false"));

                            stringBuffer.append(response.body().string());
                            Log.d(this.getClass().getName(), "  stringBuffer  " + stringBuffer.toString() + "  responseGet.code()" + response.code());
                        }
                        // TODO: 28.12.2024 closeting
                        call.cancel();
                        response.close();
                        //TODO закрываем п отоки
                        okHttpClient.dispatcher().executorService().shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            });

            okHttpClient.dispatcher().executorService().awaitTermination(1, TimeUnit.DAYS);
            okHttpClient.dispatcher().cancelAll();
            Log.i(this.getClass().getName(),  " Атоманически установкаОбновление ПО "+
                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " время " +new Date().toLocaleString() );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
}