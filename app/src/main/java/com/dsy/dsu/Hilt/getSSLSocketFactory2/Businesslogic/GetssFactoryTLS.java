package com.dsy.dsu.Hilt.getSSLSocketFactory2.Businesslogic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.R;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class GetssFactoryTLS extends  BissennsLogica {
    @Override
    public SSLSocketFactory getSocketFactorySSL(@NonNull Context context) {
        SSLSocketFactory sslSocketFactory3 = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            caFileInputStream =  context.getResources().openRawResource(R.raw.keystore32cer);
            Certificate ca = cf.generateCertificate(caFileInputStream);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("jboss", ca);
            TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
          //  final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory3 = sslContext.getSocketFactory();
            // TODO: 25.12.2023  clear
            caFileInputStream.close();

            Log.i(this.getClass().getName(),  " Атоманически установкаОбновление ПО "+
                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " время " +new Date().toLocaleString()+ " sslSocketFactory3 " +sslSocketFactory3 +  "    ca.getPublicKey() " +   ca.getPublicKey());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return sslSocketFactory3;
    }


}
