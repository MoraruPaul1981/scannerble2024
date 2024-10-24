package com.scanner.datasync.businesslayer.bl_Okhhtp;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.scanner.datasync.businesslayer.Errors.SubClassErrors;
import com.scanner.datasync.businesslayer.bl_Okhhtp.interfaces.OkhhtpInterface;
import com.scanner.datasync.businesslayer.bl_Okhhtp.interfaces.QualifierOkhhtp;
import com.scanner.datasync.businesslayer.bl_Okhhtp.interfaces.QualifierOkhhtpTLS;
import com.scanner.datasync.businesslayer.bl_SSL.X509GetInt;

import java.security.PublicKey;
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
import dagger.hilt.EntryPoints;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;


@Module
@InstallIn(SingletonComponent.class)
public class GetOkhhtpBuilderTls implements OkhhtpInterface {
    Long version;

    @Singleton
    @QualifierOkhhtpTLS
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
                            X509Certificate x509CertificateCA=(X509Certificate)     chain[0];
                            X509Certificate x509Certificateuser=(X509Certificate)     chain[1];
                            PublicKey publicKeyCa=  x509CertificateCA.getPublicKey();
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n");
                            Log.i(this.getClass().getName(),  " OkHttpClient"+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            try {
                                X509Certificate x509CertificateCA=(X509Certificate)     chain[0];
                                X509Certificate x509Certificateuser=(X509Certificate)     chain[1];
                                PublicKey publicKeyCa=  x509CertificateCA.getPublicKey();
                                PublicKey publicKeyuser=  x509Certificateuser.getPublicKey();
                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n");

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
                            Log.i(this.getClass().getName(),  " OkHttpClient"+
                                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                                    " время " +new Date().toLocaleString() );
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            // TODO: 18.10.2024
                            X509Certificate x509Certificate=null;
                            try{
                                x509Certificate=      EntryPoints.get(hiltcontext, X509GetInt.class).gettrustAllCerts();
                                Log.i(this.getClass().getName(),  " OkHttpClient"+
                                        Thread.currentThread().getStackTrace()[2].getMethodName()+
                                        " время " +new Date().toLocaleString()+ " x509Certificate " +x509Certificate );

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
                            return new java.security.cert.X509Certificate[]{x509Certificate};
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext3 = SSLContext.getInstance("TLSv1.3");//TLSv1.3
            sslContext3.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory2 = sslContext3.getSocketFactory();
            // TODO: 09.10.2024
            ConnectionSpec spec = new ConnectionSpec.Builder( ConnectionSpec.MODERN_TLS)
                    .allEnabledTlsVersions()
                    .allEnabledCipherSuites()
                    .build();
            builder.connectionSpecs(  ( Arrays.asList(spec, ConnectionSpec.COMPATIBLE_TLS)));
            builder.retryOnConnectionFailure(false);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // TODO: 21.10.2024
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n");
                    return true;
                }

            });

            builder.sslSocketFactory(sslSocketFactory2, (X509TrustManager)trustAllCerts[0]);



            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n");

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



