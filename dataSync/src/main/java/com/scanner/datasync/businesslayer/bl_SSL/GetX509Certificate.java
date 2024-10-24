package com.scanner.datasync.businesslayer.bl_SSL;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.scanner.datasync.R;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class GetX509Certificate {

    Long version;


    @Singleton
    @Provides
    @NotNull
    public X509Certificate gettrustAllCerts  (@ApplicationContext Context hitcontext){
        // TODO: 18.10.2024
        X509Certificate cert = null;
        try{

            PackageInfo pInfo = null;
            try {
                pInfo = hitcontext.getPackageManager().getPackageInfo(hitcontext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
            version = pInfo.getLongVersionCode();


            Log.i(this.getClass().getName(),  " OkHttpClient"+
                    Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " время " +new Date().toLocaleString() );

            KeyStore keyStore = KeyStore.getInstance("BKS");
            InputStream instream = hitcontext.getResources().openRawResource(R.raw.bksbasedsu1ru1712024);
            keyStore.load(instream, "mypassword".toCharArray());
            cert = (X509Certificate) keyStore.getCertificate("base.dsu1.ru");

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n"+
                    "   cert[0] " +  cert.getPublicKey());

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
            new SubClassErrors(hitcontext).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + "\n");


        return cert;
    }

}