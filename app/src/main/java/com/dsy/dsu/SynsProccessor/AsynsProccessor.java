package com.dsy.dsu.SynsProccessor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.BusinessLogicAll.Class_Generations_PUBLIC_CURRENT_ID;
import com.dsy.dsu.BusinessLogicAll.Class_MODEL_synchronized;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.net.ssl.SSLSocketFactory;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AsynsProccessor extends Class_MODEL_synchronized {
    // TODO: 28.07.2022  переменые
    public Context context;
    public CopyOnWriteArrayList<String> ГлавныеТаблицыСинхронизации =new CopyOnWriteArrayList();


    CopyOnWriteArrayList<String> NameTableAsync = new CopyOnWriteArrayList();
    ArrayList<String> ИменаПроектовОтСервера = new ArrayList<String>();
    public LinkedHashMap<String, Date> DatesTableAsync =  new LinkedHashMap<>();
    LinkedHashMap<String, Long> VesionTableAsync =  new LinkedHashMap<String, Long>();
    public SQLiteDatabase sqLiteDatabase ;
    public SharedPreferences preferences;
    public Integer  ПубличныйIDДляФрагмента=0;
    public     String НазваниеСервернойТаблицы=new String();
    public  String КлючДляFirebaseNotification = "2a1819db-60c8-4ca3-a752-1b6cd9cadfa1";

    public   ObjectMapper jsonGenerator;

    public SSLSocketFactory getsslSocketFactory2;

    public LinkedHashMap<Integer,String> getHiltPortJboss;


    public Integer getHiltPublicId;

    // TODO: 28.07.2022
    public AsynsProccessor(@NotNull Context context,@NonNull  ObjectMapper jsonGenerator,
                           @NotNull  SSLSocketFactory getsslSocketFactory2,
                           @NonNull LinkedHashMap<Integer,String> getHiltPortJboss,
                           @NonNull   Integer getHiltPublicId) {
        super(context);
        this.context=context;
        this.   sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
        this.jsonGenerator=    jsonGenerator;
        this.getsslSocketFactory2=    getsslSocketFactory2;
        this.getHiltPortJboss=    getHiltPortJboss;
        this.getHiltPublicId=    getHiltPublicId;
        // TODO: 12.04.2024  
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                + " getHiltPublicId " + getHiltPublicId+"sqLiteDatabase" + sqLiteDatabase);


    }




// TODO: 19.08.2021  ДАННЫЙ МЕТОД ЗАПУСКАЕТ СИНХРОНИЗЦИ ДЛЯ ЧАТА

// TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!
// TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!
// TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!
    // TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!
// TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!
    // TODO: 08.09.2021 НИЖЕ НАЧИНАЮТЬСЯ МЕТОДЫ САМОЙ ФОНОВОЙ СИНХРОНИАЗЦИИ ВНИМАНИЕ !!!!!!!










































//////ПЕРВЫЙ МЕТОД ОБМЕНА ДАННЫМИ С СЕРВЕРОМ МЕТОД GET

    public Long МетодНачалоСихронизациивФоне(@NotNull Context context)   {
        Long результатСинхрониазции=0l;
        try {
            ////САМАЯ ПЕРВАЯ КОМАНДА НАЧАЛА ОБМНЕНА ДАННЫМИ///// TODO ГЛАВНЫЙ МЕТОД ОБМЕНА ДАНЫМИ  НА АКТИВИТИ FACE_APP
            Integer getPublicID = new Class_Generations_PUBLIC_CURRENT_ID().getPublicIDAllApp(context);
            //TODO
            if (getPublicID > 0) {

                результатСинхрониазции = МетодПолучениеСпискаТаблицДляОбменаДанными(getPublicID);
            }


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " результатСинхрониазции " + результатСинхрониазции+ " getPublicID "+getPublicID);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(this.context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return    результатСинхрониазции;
    }








































    ////////////МЕТОД ПОЛУЧЕНИЕ  ВЕРСИИ ДАННЫХ
    Long МетодПолучениеСпискаТаблицДляОбменаДанными(@NonNull Integer   getPublicID)
            throws JSONException, InterruptedException, ExecutionException, TimeoutException {///второй метод получаем версию данных на СЕРВЕР ЧТОБЫ СОПОЧТАВИТЬ ДАТЫ

        Long РезультатСинхронизации=0l;
        try {
            Log.d(this.getClass().getName(), "   ID  getPublicID" +   getPublicID);
            String ДанныеПришлаСпискаТаблицДляОбмена = new String();
            StringBuffer BufferGetVersionData = new StringBuffer();

            preferences=  context .getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);


// TODO: 02.04.2024  Адресс и Порт Сервера Jboss 
            String   ИмяСерверИзХранилица = getHiltPortJboss.values().stream().map(m->String.valueOf(m)).findFirst().get();
            Integer    ПортСерверИзХранилица = getHiltPortJboss.keySet().stream().mapToInt(m->m).findFirst().getAsInt();


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ИмяСерверИзХранилица " + ИмяСерверИзХранилица+
                    " ПортСерверИзХранилица " +ПортСерверИзХранилица );


            // TODO: 10.11.2022 Получение Список Таблиц
            BufferGetVersionData = МетодУниверсальныйСервернаяВерсияДанныхДанныесСервера(
                    "view_alltablesversion",
                    "application/gzip",
                    "Хотим Получить Версию Данных Сервера",///"Хотим Получить Версию Данных Сервера" б //TODO "Хотим Получить Статус Блокировки Пользователя по ID"
                    0l,
                    getPublicID,
                    ИмяСерверИзХранилица ,
                    ПортСерверИзХранилица,getsslSocketFactory2);
            Log.d(this.getClass().getName(), " BufferGetVersionData.toString().toCharArray().length "
                    + BufferGetVersionData.toString().toCharArray().length);
            // TODO: 03.09.2021
            if (BufferGetVersionData != null) {
                if (BufferGetVersionData.toString().toCharArray().length > 3
                        && ! BufferGetVersionData.toString().trim().matches("(.*)Server Running...... Don't Login(.*)")) {
                    Log.d(this.getClass().getName(), "  getHiltPublicId  " + getHiltPublicId +
                            " BufferGetVersionData " + BufferGetVersionData.toString());


                    //TODO БУфер JSON от Сервера
                    CopyOnWriteArrayList<Map<String, String>> БуферJsonОтСервераmodification_server =
                            jsonGenerator.readValue(BufferGetVersionData.toString(),
                            new TypeReference<CopyOnWriteArrayList<Map<String, String>>>() {
                            });

                    ГлавныеТаблицыСинхронизации = new PUBLIC_CONTENT(context).методCreatingMainTabels(context);
                    ///упаковываем в j
                    Log.d(this.getClass().getName(), "  БуферJsonОтСервераmodification_server  " + БуферJsonОтСервераmodification_server);
                    VesionTableAsync = new LinkedHashMap<String, Long>();
                     NameTableAsync=new  CopyOnWriteArrayList<String>();

                    // TODO: 15.09.2023
                    // TODO: 09.08.2023  бежим по данным версии сервера
                    Flowable.fromIterable(БуферJsonОтСервераmodification_server)
                            .onBackpressureBuffer()
                            .blockingIterable()
                            .forEach(new Consumer<Map<String, String>>() {
                                @Override
                                public void accept(Map<String, String> stringStringMap) {
                                    stringStringMap.forEach(new BiConsumer<String, String>() {
                                        @Override
                                        public void accept(String НазваниеТаблицыСервера, String ВерсияДанныхСервернойТаблицы) {

                                            // TODO: 25.09.2024  анализ табоиц  серер  и таблиц которые локально находятся сдесь
                                    String currentOtServefJbossNameTable=        stringStringMap.entrySet().stream().filter(fi->fi.getKey().equalsIgnoreCase("name")).findAny().get().getValue().trim();
                                   /// String getLinkNameOtServerJboss=        stringStringMap.keySet().stream().filter(k->k.equalsIgnoreCase("name")).findAny().orElseGet(()->"");

                                        Boolean AnalysEmptyCurrentTable=    ГлавныеТаблицыСинхронизации.contains(currentOtServefJbossNameTable);
                                           // TODO: 25.09.2024
                                            if (AnalysEmptyCurrentTable) {
                                                // TODO: 25.09.2024  
                                                if (НазваниеТаблицыСервера.trim().equalsIgnoreCase("name")) {
                                                   NameTableAsync.add(ВерсияДанныхСервернойТаблицы.trim());
                                                    НазваниеСервернойТаблицы =ВерсияДанныхСервернойТаблицы.trim();
    
                                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                                            + " ВерсияДанныхСервернойТаблицы " + ВерсияДанныхСервернойТаблицы );
                                                }
                                                if (НазваниеТаблицыСервера.trim().equalsIgnoreCase("versionserverversion")) {
                                                  VesionTableAsync.putIfAbsent(НазваниеСервернойТаблицы.trim(),
                                                            Long.valueOf(ВерсияДанныхСервернойТаблицы));
    
                                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                                            + " ВерсияДанныхСервернойТаблицы " + ВерсияДанныхСервернойТаблицы );
    
                                                }
                                                if (НазваниеТаблицыСервера.trim().equalsIgnoreCase("versionserver")) {
    
                                                    // TODO: 09.08.2023  даты заполяем таблиц с серверар
                                                    Date ДатаВерсииДанныхSQLServer=    new FormattingVersionDastaSqlserver(context).formattingDateOnVersionSqlServer(ВерсияДанныхСервернойТаблицы);
                                                    DatesTableAsync.putIfAbsent(НазваниеСервернойТаблицы.trim(), ДатаВерсииДанныхSQLServer );
    
    
                                                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                                            + " ДатаВерсииДанныхSQLServer " + ДатаВерсииДанныхSQLServer );
                                                }
                                            }


                                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                                    + " НазваниеТаблицыСервера " + НазваниеТаблицыСервера + " ВерсияДанныхСервернойТаблицы " + ВерсияДанныхСервернойТаблицы+
                                                    " НазваниеСервернойТаблицы[0] " +НазваниеСервернойТаблицы+
                                                     " AnalysEmptyCurrentTable " +AnalysEmptyCurrentTable);
                                        }
                                    });
                                }
                            });
                    // TODO: 27.12.2024
                    Log.d(this.getClass().getName(),"\n" + " class "
                            + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " VesionTableAsync"
                            +VesionTableAsync+"\n"
                            + " NameTableAsync " +NameTableAsync);


// TODO: 25.09.2024  После Получение таблиц и заполение запускаем ихрониазцтию 
                    РезультатСинхронизации   = МетодГлавныхЦиклТаблицДляСинхронизации(getPublicID);


                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " РезультатСинхронизации " + РезультатСинхронизации);

                    Log.i(this.getClass().getName(), " ИменаТаблицыОтАндройда "
                            +  NameTableAsync.toString() +
                            "  ДанныеПришлаСпискаТаблицДляОбмена " + ДанныеПришлаСпискаТаблицДляОбмена);
                }
            }else {
                Log.i(this.getClass().getName(), " НЕт данных с сервера  BufferGetVersionData " + BufferGetVersionData );

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return РезультатСинхронизации;
    }



// TODO: 10.09.2021  запускаем метод обработки по таблицам

































    
    
    
    
    
    
    


















































    // TODO: 04.11.2021  метод ПОСЫЛАЕМ ТОЛЬКО NULL В ПОЛЕ ID  НА СЕРВЕР


























    @SuppressLint("SuspiciousIndentation")
    Long МетодГлавныхЦиклТаблицДляСинхронизации(@NonNull Integer PublicID)
            throws ExecutionException, InterruptedException {//КонтекстСинхроДляКонтроллера
        // TODO: 07.04.2024
       AtomicReference<Long> ResultatSync =new AtomicReference<>(0l);
        try {
            Log.i(this.getClass().getName(), " ИменаТаблицыОтАндройда "
                    +  NameTableAsync.toString()
                    + " ВерсииВсехСерверныхТаблиц "
                    +  VesionTableAsync.toString()
                    + " ВерсииДатыСерверныхТаблиц "
                    + DatesTableAsync.toString());


            /*
//TODO :  Реальная Работа
 */

// TODO: 21.08.2023 ГЛАВНЫЙ ЦИКЛ СИХРОНИАЗЦИИ
            // TODO: 21.08.2023  только  Параллено
            ResultatSync.set(       new ProccesorparallelSynch( context,
                    jsonGenerator,
                    getsslSocketFactory2,
                     getHiltPortJboss,
                    NameTableAsync,
                    VesionTableAsync,
                    DatesTableAsync,
                    PublicID).startingAsyncParallels());

            // TODO: 08.04.2024
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                    + "\n" + "parallel"+" ResultatSync.get() " + ResultatSync.get());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber()  );
        }
        return  ResultatSync.get();
    }




}