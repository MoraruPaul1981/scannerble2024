package com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient;


import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;


import com.serverscan.datasync.R;
import com.serverscan.datasync.datasync_businesslayer.Errors.SubClassErrors;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces.OkhhtpInterface;
import com.serverscan.datasync.datasync_businesslayer.bl_okhttpclient.interfaces.QualifierOkhhtp;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
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
public class GetOkhhtpBuilder   implements OkhhtpInterface {
    Long version;

    @QualifierOkhhtp
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
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.i(this.getClass().getName(),  " OkHttpClient"+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.i(this.getClass().getName(),  " OkHttpClient"+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
// TODO: 18.10.2024
                            X509Certificate cert;
                            Log.i(this.getClass().getName(),  " OkHttpClient"+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );

                            KeyStore keyStore = null;
                            try {
                                keyStore = KeyStore.getInstance("BKS");
                            } catch (KeyStoreException e) {
                                throw new RuntimeException(e);
                            }
                            InputStream instream = hiltcontext.getResources().openRawResource(R.raw.androidsous);
                            try {
                                keyStore.load(instream, "mypassword".toCharArray());
                            } catch (CertificateException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                  cert = (X509Certificate) keyStore.getCertificate("base.dsu1.ru");
                            } catch (KeyStoreException e) {
                                throw new RuntimeException(e);
                            }

                            //return new java.security.cert.X509Certificate[]{};
                            return new java.security.cert.X509Certificate[]{cert};
                        }
                    }
            };
// Install the all-trusting trust manager
            final SSLContext sslContext3 = SSLContext.getInstance("TLSv1.3");//TLSv1.3
            sslContext3.init(null, trustAllCerts, new SecureRandom());
            final SSLSocketFactory sslSocketFactory2 = sslContext3.getSocketFactory();
            // TODO: 09.10.2024
            ConnectionSpec spec = new ConnectionSpec.Builder( ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            builder.connectionSpecs(  ( Arrays.asList(spec,ConnectionSpec.CLEARTEXT)));
            builder.retryOnConnectionFailure(false);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {


                    session.putValue("aaaaa","fdfdfdfdfddf");
                    return true;
                }

            });

            builder.sslSocketFactory(sslSocketFactory2, (X509TrustManager)trustAllCerts[0]);




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



