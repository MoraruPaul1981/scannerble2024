package com.dsy.dsu.Errors.controller;

import android.content.ContentValues;
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
import com.dsy.dsu.Errors.controller.interfaces.RecordNewErrorsInterface;
import com.sous.backasync.start.ModuleInserting;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


@Module
@InstallIn(SingletonComponent.class)
public class RecordNewErros  implements RecordNewErrorsInterface {

    private Context context;
    /*private String fileName = "Sous-Avtodor-ERROR.txt";

    private   String patchFileName="SousAvtoFile";*/


      ModuleInserting moduleInserting;


    public @Inject RecordNewErros(@ApplicationContext Context context) {

        this.context=context;

        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }


    @Override
    public void recordnewerror(@NonNull String ТекстОшибки,
                                              @NonNull String КлассГнерацииОшибки,
                                              @NonNull String МетодаОшибки,
                                              @NonNull Integer ЛинияОшибки) {

        try {
            if (context != null) {
                ///TODO Записываем ошибки только определного сорта
                if (!ТекстОшибки.trim().matches("(.*)UnknownHostException(.*)")
                        && !ТекстОшибки.trim().matches("(.*)SocketTimeoutException(.*)")
                        && !ТекстОшибки.trim().matches("(.*)ConnectException(.*)")) {


                    // TODO: 30.01.2025
                    getWriteNewError(  ТекстОшибки, КлассГнерацииОшибки,МетодаОшибки, ЛинияОшибки );

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n" +" ТекстОшибки " +ТекстОшибки);;

                    // TODO: 20.12.2022  дополнительный механизм записи ошибкок
                    getWriteNewErrorNotePad(ТекстОшибки, КлассГнерацииОшибки, МетодаОшибки, ЛинияОшибки);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "\n" +" ТекстОшибки " +ТекстОшибки);;
                    // TODO: 21.12.2022  главная  файл ErrorDSU1 в ТАблицу




                }
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "\n" +" ТекстОшибки " +ТекстОшибки);

            } else {
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "\n" +" ТекстОшибки " +ТекстОшибки);
            }
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ "\n" +" ТекстОшибки " +ТекстОшибки);
            // TODO: 09.07.2023 clear
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewBackErros");
            Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                    + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }



    @Override
      public void getWriteNewErrorNotePad(@NonNull String ТекстОшибки, @NonNull String КлассГнерацииОшибки, @NonNull String МетодаОшибки, @NonNull Integer ЛинияОшибки) {
        try{
        ArrayList<String> arrayListОшибкиДляЗаписивФайл = new ArrayList();
        arrayListОшибкиДляЗаписивФайл.add(ТекстОшибки);
        arrayListОшибкиДляЗаписивФайл.add(КлассГнерацииОшибки);
        arrayListОшибкиДляЗаписивФайл.add(МетодаОшибки);
        arrayListОшибкиДляЗаписивФайл.add(String.valueOf(ЛинияОшибки));

        // TODO: 09.07.2023 запись ошибки в файл  .txt

         writeDownAnewErrorFile(arrayListОшибкиДляЗаписивФайл);

            Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " arrayListОшибкиДляЗаписивФайл " + arrayListОшибкиДляЗаписивФайл);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewBackErros");
        Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }




    @Override
     public Integer getWriteNewError(@NonNull String ТекстОшибки,
                                  @NonNull String КлассГнерацииОшибки,
                                  @NonNull String МетодаОшибки,
                                  @NonNull Integer ЛинияОшибки ) {
        // TODO: 30.01.2025
        Integer InsertingNewErorr = null;
        try{

           moduleInserting=new ModuleInserting(context);
            // TODO: 30.01.2025
            ContentValues contentValuesNewError=new ContentValues();

            Long getVersionForError  = new SubClassUpVersionDATA().upVersionCurentTable("errordsu1"
                    , context );
            Long UUIDForError = (Long)
                    new Class_Generation_UUID(context).МетодГенерацииUUID();
            Integer getPublicIdForError = new Class_Generations_PUBLIC_CURRENT_ID().
                    getPublicIDAllApp(context);
            String getNewDateForError = new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();

            final Object VersionPC = BuildConfig.VERSION_CODE;
            Integer getVersionPCForError = Integer.parseInt(VersionPC.toString());
            // TODO: 30.01.2025 ВСТАВКА  новой ошибки

            contentValuesNewError.put("Error", ТекстОшибки.toLowerCase());
            contentValuesNewError.put("Klass", КлассГнерацииОшибки.toUpperCase());
            contentValuesNewError.put("Metod", МетодаОшибки.toUpperCase());
            contentValuesNewError.put("LineError", ЛинияОшибки);
            contentValuesNewError.put("user_update", getPublicIdForError.toString());
            contentValuesNewError.put("UUID", UUIDForError.toString());
            contentValuesNewError.put("current_table", getVersionForError);
            contentValuesNewError.put("whose_error", getVersionPCForError);
            contentValuesNewError.put("date_update", getNewDateForError);

              InsertingNewErorr =    moduleInserting.getModuleInsert("errordsu1",contentValuesNewError);

/*     Long   pезультатВставкиНовойОшибки = (Long) classGrudSqlOperationsОшибки.
                new InsertData(context).insertdata(classGrudSqlOperationsОшибки.concurrentHashMapНабор,
                classGrudSqlOperationsОшибки.contentValuesДляSQLBuilder_Для_GRUD_Операций,
                new PUBLIC_CONTENT(context).МенеджерПотоков,
                sqLiteDatabase);*/
            Log.d(this.getClass().getName(),"\n" + " class CoreApp    " + Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" InsertingNewErorr " +InsertingNewErorr);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("  Ошибка в самом классе записи ошибок нет КОНТЕКСТА RecordNewBackErros");
        Log.e(context.getClass().getName(), "Ошибка в самом классе создание ОШИБКИ (записи новой ошибки) ERROR  inse ERROR" + e
                + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return  InsertingNewErorr;
    }





    // TODO: 20.12.2022  дополнительный клас Заппси ОШИБКИВ ФАЙЛ

    @Override
       public void writeDownAnewErrorFile(@NonNull ArrayList<String> linkedBlockingQueueВскеОшибкиДляЗаписи) {
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



    @Override
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


