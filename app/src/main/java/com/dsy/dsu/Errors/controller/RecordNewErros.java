package com.dsy.dsu.Errors.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.BuildConfig;


import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.BusinessLogicAll.Class_GRUD_SQL_Operations;
import com.dsy.dsu.BusinessLogicAll.Class_Generation_UUID;
import com.dsy.dsu.BusinessLogicAll.Class_Generations_PUBLIC_CURRENT_ID;
import com.dsy.dsu.BusinessLogicAll.DATE.Class_Generation_Data;
import com.dsy.dsu.BusinessLogicAll.SubClassUpVersionDATA;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class RecordNewErros {

    private Context context;
    private Class_GRUD_SQL_Operations classGrudSqlOperationsОшибки;

    private String fileName = "Sous-Avtodor-ERROR.txt";

    private   String patchFileName="SousAvtoFile";
    private SQLiteDatabase sqLiteDatabase ;
    public RecordNewErros(@NonNull Context context) {

        this.context = context;
        sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
    }

    public void recordnewerror(@NonNull String ТекстОшибки,
                                              @NonNull String КлассГнерацииОшибки,
                                              @NonNull String МетодаОшибки,
                                              @NonNull Integer ЛинияОшибки) {

        Long PезультатВставкиНовойОшибки = 0l;
        try {
            if (context != null) {
                Long ВерсияДанных = new SubClassUpVersionDATA().upVersionCurentTable("errordsu1"
                        , context );
                Long UUID = (Long)
                        new Class_Generation_UUID(context).МетодГенерацииUUID();
                Integer ПубличныйIDДляАсих = new Class_Generations_PUBLIC_CURRENT_ID().
                        getPublicIDAllApp(context);
                classGrudSqlOperationsОшибки = new Class_GRUD_SQL_Operations(context);
                classGrudSqlOperationsОшибки.concurrentHashMapНабор.clear();
                classGrudSqlOperationsОшибки = new Class_GRUD_SQL_Operations(context);
                classGrudSqlOperationsОшибки.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "ErrorDSU1");
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("Error", ТекстОшибки.toLowerCase());
                Log.d(this.getClass().getName(), " ТекстОшибки.toLowerCase()  " + ТекстОшибки.toLowerCase());
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("Klass", КлассГнерацииОшибки.toUpperCase());
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("Metod", МетодаОшибки.toUpperCase());
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("LineError", ЛинияОшибки);
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("user_update", ПубличныйIDДляАсих);
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("UUID", UUID);
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("current_table", ВерсияДанных);
                final Object ТекущаяВерсияПрограммы = BuildConfig.VERSION_CODE;
                Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("whose_error", ЛокальнаяВерсияПОСравнение);
                String СгенерированованныйДатаДляВставки = new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций.put("date_update", СгенерированованныйДатаДляВставки);

                ///TODO Записываем ошибки только определного сорта
                if (!ТекстОшибки.trim().matches("(.*)UnknownHostException(.*)")
                        && !ТекстОшибки.trim().matches("(.*)SocketTimeoutException(.*)")
                        && !ТекстОшибки.trim().matches("(.*)ConnectException(.*)")) {

                    // TODO: 21.12.2022  главная  файл ErrorDSU1
                   getWriteNewError( );

                    // TODO: 20.12.2022  дополнительный механизм записи ошибкок
                    getWriteNewErrorNotePad(ТекстОшибки, КлассГнерацииОшибки, МетодаОшибки, ЛинияОшибки);


                }
                Log.d(this.getClass().getName(), "PезультатВставкиНовойОшибки " + PезультатВставкиНовойОшибки);

            } else {
                System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewErros");
            }
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            // TODO: 09.07.2023 clear
            classGrudSqlOperationsОшибки.concurrentHashMapНабор.clear();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewErros");
            Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                    + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    private void getWriteNewErrorNotePad(@NonNull String ТекстОшибки, @NonNull String КлассГнерацииОшибки, @NonNull String МетодаОшибки, @NonNull Integer ЛинияОшибки) {
        try{
        ArrayList<String> arrayListОшибкиДляЗаписивФайл = new ArrayList();
        arrayListОшибкиДляЗаписивФайл.add(ТекстОшибки);
        arrayListОшибкиДляЗаписивФайл.add(КлассГнерацииОшибки);
        arrayListОшибкиДляЗаписивФайл.add(МетодаОшибки);
        arrayListОшибкиДляЗаписивФайл.add(String.valueOf(ЛинияОшибки));

        // TODO: 09.07.2023 запись ошибки в файл  .txt

        new SubClassWriteErrorFile(context, arrayListОшибкиДляЗаписивФайл).writeDownAnewErrorFile();

            Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " arrayListОшибкиДляЗаписивФайл " + arrayListОшибкиДляЗаписивФайл);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewErros");
        Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    private void getWriteNewError( ) {
        try{
     Long   pезультатВставкиНовойОшибки = (Long) classGrudSqlOperationsОшибки.
                new InsertData(context).insertdata(classGrudSqlOperationsОшибки.concurrentHashMapНабор,
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций,
                new PUBLIC_CONTENT(context).МенеджерПотоков,
                sqLiteDatabase);
            Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " pезультатВставкиНовойОшибки " + pезультатВставкиНовойОшибки);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewErros");
        Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


    // TODO: 20.12.2022  дополнительный клас Заппси ОШИБКИВ ФАЙЛ
    private class SubClassWriteErrorFile {
        // TODO: 14.12.2022 тест метод
        Context context;
        ArrayList<String> linkedBlockingQueueВскеОшибкиДляЗаписи;

        public SubClassWriteErrorFile(@NonNull Context context, @NonNull ArrayList linkedBlockingQueueВскеОшибкиДляЗаписи) {
            this.context = context;
            this.linkedBlockingQueueВскеОшибкиДляЗаписи = linkedBlockingQueueВскеОшибкиДляЗаписи;
        }

        void writeDownAnewErrorFile() {
            try {
                writeDownAnewErrorNotePad(linkedBlockingQueueВскеОшибкиДляЗаписи);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("  Ошибка в    дополнительном модуле записи ошиьки в файл SubClassWriteErrorFile ");
                Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }

    }


    public void writeDownAnewErrorNotePad(@NonNull  ArrayList<String> linkedBlockingQueueВскеОшибкиДляЗаписи) {
        try {
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), File.separator + fileName);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), File.separator+patchFileName +File.separator+ fileName);
            if (file.isFile()) {
                BufferedWriter bufferedWriter =  Files.newBufferedWriter(Paths.get(file.getPath()), StandardCharsets.UTF_16,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);



                String    СамаОшибка=null;
                boolean ДлинаСтрокивСпиноре = linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString().length() > 40;
                if (ДлинаСтрокивСпиноре) {
                    StringBuffer sb = new StringBuffer(linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString());
                    sb.insert(40, System.lineSeparator());
                 СамаОшибка = sb.toString();
                }else {
                    СамаОшибка =linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString();
                }






                bufferedWriter.write("\n");
                bufferedWriter.write("##### ERROR #####");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                String СгенерированованныйДатаВремениСейчаcДляУдаления=     new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
                bufferedWriter.write(СгенерированованныйДатаВремениСейчаcДляУдаления);
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("error");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write(СамаОшибка);
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("Class");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write(linkedBlockingQueueВскеОшибкиДляЗаписи.get(1).toString());
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("Metod");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write(linkedBlockingQueueВскеОшибкиДляЗаписи.get(2).toString());
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("Line");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write(linkedBlockingQueueВскеОшибкиДляЗаписи.get(3).toString());
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");


                bufferedWriter.flush();
                bufferedWriter.close();
            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
}

// TODO: 07.10.2023  class Create FILE for Error


