package com.scanner.datasync.businesslayer.bl_Jakson;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scanner.datasync.businesslayer.Errors.SubClassErrors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ModuleJakson  implements  ModuleJaksonInterface {

    Long version;
    @Singleton
    @Provides
    @Override
    public ObjectMapper getHiltJaksonObjectMapper(@ApplicationContext Context context) {
        ObjectMapper mapperJackson = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();


            JsonFactory factory = new JsonFactory();
            //CBORFactory factory = new CBORFactory();
            mapperJackson = new ObjectMapper(factory);
            mapperJackson.writerWithDefaultPrettyPrinter();
            mapperJackson.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            mapperJackson.setLocale(new Locale("ru"));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", new Locale("ru"));
            mapperJackson.setDateFormat(df);
            mapperJackson.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            mapperJackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapperJackson.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            mapperJackson.enable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
            mapperJackson.enable(SerializationFeature.INDENT_OUTPUT);
            mapperJackson.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " mapperJackson " + mapperJackson);
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
        return mapperJackson;


    }
}

