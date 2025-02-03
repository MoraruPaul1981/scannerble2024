package com.dsy.dsu.Errors.controller;





import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.sous.backasync.launch.ModuleQuety;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


public class  BiccessLogicActivityError{
    private ModuleQuety moduleQuety;
    private Context context;

    private SQLiteDatabase sqLiteDatabase_error;

    private String fileName = "Sous-Avtodor-ERROR.txt";
    private   String patchFileName="SousAvtoFile";



    public BiccessLogicActivityError(
                                     Context context,
                                     SQLiteDatabase sqLiteDatabase_error) {
        this.moduleQuety = moduleQuety;
        this.context = context;
        this.sqLiteDatabase_error = sqLiteDatabase_error;
    }


    // TODO: 28.06.2023 Запись Ошибков
    public void МетодУдаланиеОшибок()   {
        try {



            CompletableFuture.supplyAsync(new Supplier<Object>() {
                        @Override
                        public Object get() {
                            if (!sqLiteDatabase_error.inTransaction()) {
                                sqLiteDatabase_error.beginTransaction();
                            }
                            sqLiteDatabase_error.execSQL("DELETE FROM errordsu1 ");

                            // TODO: 22.09.2023


// TODO: 17.04.2023
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

                            sqLiteDatabase_error.setTransactionSuccessful();

                            if (sqLiteDatabase_error.inTransaction()) {
                                sqLiteDatabase_error.endTransaction();
                            }
                            return sqLiteDatabase_error;
                        }
                    }).thenRun(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: 22.09.2023 удалаляем данные
                            методЧистимФайлсОшибкамиErrors();
                            // TODO: 17.04.2023
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                        }
                    })
                    .exceptionally(throwable -> {
                        throwable.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new RecordNewErros(context).recordnewerror(throwable.toString(),
                                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                        return  null;
                    })
                    .complete(null);

        } catch (Exception e) {
            e.printStackTrace();
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void методЧистимФайлсОшибкамиErrors() {
        try    {
            File getFileAllErrors = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File.separator+patchFileName +File.separator+ fileName);

            if (getFileAllErrors.exists()) {
                BufferedWriter bf = Files.newBufferedWriter(Paths.get(getFileAllErrors.getPath()),
                        StandardOpenOption.TRUNCATE_EXISTING);

                bf.flush();
                bf.close();
            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


        } catch (IOException e) {
            e.printStackTrace();
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


}

