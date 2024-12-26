package com.dsy.dsu.Hilt.OkhhtpBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.Hilt.getSSLSocketFactory2.QualifiergetsslSocketFactory2;
import com.dsy.dsu.R;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class GetOkhhtpBuilderSSL implements  InGetOkhhtpBuilder {
    private  Context context;
    private SSLSocketFactory getsslSocketFactory2;
    public GetOkhhtpBuilderSSL(@NotNull Context context,@NotNull SSLSocketFactory getsslSocketFactory2) {
        this.context = context;
        this.getsslSocketFactory2 = getsslSocketFactory2;
    }

    @Override
    public OkHttpClient.Builder getOkhhtpBuilder() {
        OkHttpClient.Builder builder=null;
        try{
            // TODO: 06.10.2024 3 вариат
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            // TODO: 25.12.2024
                            Log.i(this.getClass().getName(),  " chain  "+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() + " chain "  +chain);
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            // TODO: 25.12.2024
                            Log.i(this.getClass().getName(),  " chain  "+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString()+ " chain "  +chain );
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            // TODO: 25.12.2024
                            Log.i(this.getClass().getName(),  " java.security.cert.X509Certificate  "+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            Dispatcher dispatcher= new Dispatcher(Executors.newCachedThreadPool());
            builder=     new OkHttpClient().newBuilder().dispatcher(dispatcher);
            builder.connectionPool(new ConnectionPool(20, 30, TimeUnit.SECONDS));
            // TODO: 09.10.2024
            ConnectionSpec spec = new ConnectionSpec.Builder( ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            builder.connectionSpecs(  ( Arrays.asList(spec,ConnectionSpec.CLEARTEXT)));
            builder.retryOnConnectionFailure(false);
            builder.sslSocketFactory(getsslSocketFactory2, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO: 25.12.2024
                    Log.i(this.getClass().getName(),  " hostname  "+
                            Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " время " +new Date().toLocaleString()  + " hostname " +hostname +" session " +session);
                    return true;
                }

            });

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
        return builder;
    }
    }

