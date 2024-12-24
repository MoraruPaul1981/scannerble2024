package com.dsy.dsu.BusinessLogicAll;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.Errors.Class_Generation_Errors;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.internal.observers.BlockingBaseObserver;
import io.reactivex.rxjava3.internal.observers.ForEachWhileObserver;


public class Class_Connections_Server  extends  Class_GRUD_SQL_Operations {
    private Context context1;
    private  Class_GRUD_SQL_Operations class_grud_sql_operations=null;
    private SharedPreferences preferences;
    private AsyncTaskLoader<Boolean> asyncTaskLoader;
    public Class_Connections_Server(Context context) {
        super(context);
        context1 =context;
        class_grud_sql_operations=new Class_GRUD_SQL_Operations(context1);
        //preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences =context.getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
    }
    ///////// TODO ПРОВЕРЯЕТ ЕСЛИ ПОДКЛЧБЕНИ В ИНТРЕНТУ
    public Boolean МетодПингаСервераРаботаетИлиНет(         @NotNull Context КонтекстКоторыйДляСинхронизации,
                                                   @NotNull SSLSocketFactory getsslSocketFactory2,
                                                            @NonNull LinkedHashMap<Integer,String> getHiltPortJboss) {
        Boolean РезультатПингакСервераРаботаетЛиОНРеально=false;
                            try{
                                РезультатПингакСервераРаботаетЛиОНРеально=
                                        МетодПингаСервераРаботаетИлиНетВнутри(КонтекстКоторыйДляСинхронизации,getsslSocketFactory2,
                                                getHiltPortJboss);

                                Log.d(this.getClass().getName(), "\n"
                                        + " время: " + new Date() + "\n+" +
                                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        "  РезультатПингакСервераРаботаетЛиОНРеально " +РезультатПингакСервераРаботаетЛиОНРеально);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (! e.toString().equalsIgnoreCase("java.util.concurrent.TimeoutException: The source did not signal an event for 5 seconds and has been terminated.")) {
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new Class_Generation_Errors(КонтекстКоторыйДляСинхронизации).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                        Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                        }
                            return РезультатПингакСервераРаботаетЛиОНРеально;
                        }









    ///////// TODO ПРОВЕРЯЕТ ЕСЛИ ПОДКЛЧБЕНИ В ИНТРЕНТУ
    private Boolean МетодПингаСервераРаботаетИлиНетВнутри(@NotNull Context КонтекстКоторыйДляСинхронизации,
                                                          @NotNull SSLSocketFactory getsslSocketFactory2,
                                                          @NonNull LinkedHashMap<Integer,String> getHiltPortJboss )
            throws ExecutionException, InterruptedException,
            TimeoutException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
         Boolean результатПрозвонаСокетом = false;
        try {
            // TODO: 02.04.2024  цикл пинг  
            for (Map.Entry<Integer,String> entry : getHiltPortJboss.entrySet()) {
                Integer   ИмяПорта =    entry.getKey();
                String     ИмяСервера=     entry.getValue();
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " ИмяСервера"+ ИмяСервера+" ИмяПорта "+ИмяПорта);


// TODO: 12.01.2024  производим пинг через 3 попытки



              Long  БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer=    pingS3Popitkami(КонтекстКоторыйДляСинхронизации, getsslSocketFactory2, ИмяПорта, ИмяСервера);



                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + " БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer[0] " +БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer );

                // TODO: 16.12.2021  положитльеный результат пинга
                if ( БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer>0) {
                    результатПрозвонаСокетом = true;



                    Log.d(this.getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " ИмяСервера" + ИмяСервера+ "ИмяПорта " +ИмяПорта);




                    // TODO: 15.12.2023 ВЫХОД ИЗ ЦИКЛА ВЫХОД ИЗ ЦИКЛА
                    break;
                }else{
                    результатПрозвонаСокетом = false;
                    Log.e(Class_MODEL_synchronized.class.getName(), " ОШИБКА НЕТ СВЯЗИ С СЕВРЕРОМ  результатПрозвонаСокетом[0] " + результатПрозвонаСокетом);
                }

                //todo old code

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + " БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer[0] " +БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer );
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(КонтекстКоторыйДляСинхронизации).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return результатПрозвонаСокетом;
    }


    // TODO: 12.01.2024 метод пинга с тремя попытками
    private Long pingS3Popitkami(@androidx.annotation.NonNull Context КонтекстКоторыйДляСинхронизации,
                                 @androidx.annotation.NonNull SSLSocketFactory getsslSocketFactory2,
                                 Integer ИмяПорта, String ИмяСервера) {
        // TODO: 12.01.2024
      Long  БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer =0l ;
        try{
        // TODO: 12.01.2024
            // TODO: 10.11.2022  пинг к сервера
            БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer =
                    new Class_MODEL_synchronized(КонтекстКоторыйДляСинхронизации).
                            МетодУниверсальногоПинга(new String(), "application/gzip",
                                    "Хотим Получить Статус Реальной Работы SQL SERVER"
                                    ,0l,
                                    0
                                    , ИмяСервера, ИмяПорта, getsslSocketFactory2);//application/gzip

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " ИмяСервера"+ ИмяСервера +" ИмяПорта "+ИмяПорта+
                    " БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer " + БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(КонтекстКоторыйДляСинхронизации).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return   БуферПолучениеДанныхРЕальныйСтатусРАботыSQLServer;
    }


}
