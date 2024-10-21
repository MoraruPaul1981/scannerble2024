package com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.serverscan.datasync.R;
import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces.OkhhtpInterface;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces.QualifierOkhhtp;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces.QualifierOkhhtpTls;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;


@Module
@InstallIn(SingletonComponent.class)
public class GetOkhhtpBuilderTLS implements OkhhtpInterface {
    Long version;

    @QualifierOkhhtpTls
    @Singleton
    @Provides
    @Override
    public  OkHttpClient.Builder getOkhhtpBuilder(@ApplicationContext Context hiltcontext ) {
        OkHttpClient.Builder builder=null;
        try{
            PackageInfo pInfo = hiltcontext.getPackageManager().getPackageInfo(hiltcontext.getPackageName(), 0);
            version = pInfo.getLongVersionCode();

          Dispatcher dispatcher= new Dispatcher(Executors.newCachedThreadPool());
            builder=     new OkHttpClient().newBuilder().dispatcher(dispatcher);
            builder.connectionPool(new ConnectionPool(20, 30, TimeUnit.SECONDS));

            // TODO: 06.10.2024 3 вариат
            KeyStore keyStore = KeyStore.getInstance("BKS");
            InputStream instream = hiltcontext.getResources().openRawResource(R.raw.bksbasedsu1ru1712024);
            keyStore.load(instream, "mypassword".toCharArray());


            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("SSL");//TLSv1.3

            // TODO: 09.10.2024
            ConnectionSpec spec = new ConnectionSpec.Builder( ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            builder.connectionSpecs(  ( Arrays.asList(spec)));
            builder.retryOnConnectionFailure(false);


            sslContext.init(null, tmf.getTrustManagers(),  new SecureRandom());

            builder.setSocketFactory$okhttp(sslContext.getSocketFactory());

            Log.i(this.getClass().getName(),  " OkHttpClient"+
                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " время " +new Date().toLocaleString() );
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
            new SubClassErrors(hiltcontext).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return builder;
    }
}



