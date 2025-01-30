package com.dsy.dsu.Errors.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.dsy.dsu.Errors.controller.interfaces.GettingErrorsIsFileInterface;
import com.sous.backasync.start.ModuleQuety;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class GettingErrorsIsFileOrIsCursor implements GettingErrorsIsFileInterface {

    Context context;

    @Inject
    ModuleQuety moduleQuety;

    public GettingErrorsIsFileOrIsCursor(Context context, ModuleQuety moduleQuety) {
        this.context = context;
        this.moduleQuety = moduleQuety;
    }


    public StringBuffer gettingErrorsIsFile()   {
        StringBuffer БуерДляОшибок =new StringBuffer();
        // TODO: 14.01.2025
        java.io.File getFileAllErrors ;
        try{
            // TODO: 11.12.2023  для android 11++
            File getFileError = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    +File.separator+patchFileName +File.separator+fileName);

            getFileError.setWritable(true);
            getFileError.setExecutable(true);
            getFileError.setReadable(true);

            File getPatchNewFileError= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    +File.separator+patchFileName  );
            BufferedReader newBufferedReader = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                if (getPatchNewFileError.isDirectory() && getFileError.exists()) {
                    Uri address = FileProvider.getUriForFile(context, "com.dsy.dsu.provider", getFileError);
                    final InputStream imageStream = context.getContentResolver().openInputStream(address);
                    // TODO: 15.01.2025
                    newBufferedReader = new BufferedReader(new InputStreamReader(imageStream, StandardCharsets.UTF_16));


                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "newBufferedReader " +newBufferedReader.markSupported() );
                }
                Log.d(this.getClass().getName(),  " date " +new Date().toGMTString().toString()   );
            } else {
                // TODO: 15.01.2025
                newBufferedReader = Files.newBufferedReader(Paths.get(getFileError.getPath()), StandardCharsets.UTF_16);

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "newBufferedReader " +newBufferedReader.markSupported() );

            }

            if (newBufferedReader!=null) {
                String    lineErrorsAll=null;
                while ((lineErrorsAll =newBufferedReader.readLine()) != null) {
                    БуерДляОшибок.append(lineErrorsAll);
                    БуерДляОшибок.append('\n');
                    Log.d(this.getClass().getName(), "line " +lineErrorsAll  );
                }
            }


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "БуерДляОшибок " +БуерДляОшибок );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return  БуерДляОшибок;
    }

    @Override
    public StringBuffer gettingErrorsIsCursor() {
        StringBuffer БуерДляОшибок =new StringBuffer();
        // TODO: 14.01.2025
        try{

           Cursor getbackasyncQueryandWhere=   moduleQuety.getModuleQuery("errordsu1",
                    " SELECT  *   FROM errordsu1 AS er  WHERE er.ERROR IS  NOT NULL  ORDER BY er.id DESC  " ,
                    null);

           БуерДляОшибок=    rowAppendBufferErrors(getbackasyncQueryandWhere);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "БуерДляОшибок " +БуерДляОшибок );

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return  БуерДляОшибок;
    }


    @SuppressLint("Range")
    private StringBuffer rowAppendBufferErrors(Cursor getbackasyncQueryandWhere) {
        // TODO: 17.01.2025
        StringBuffer БуерДляОшибок=new StringBuffer();
        try {
        Flowable.range(0, getbackasyncQueryandWhere.getCount())
                .filter(kol-> getbackasyncQueryandWhere.getCount()>0)
                .filter(kol-> getbackasyncQueryandWhere!=null)
                        .onBackpressureBuffer().blockingIterable().forEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer step) {// TODO: 17.01.2025
                                try{

                        String date_update=     getbackasyncQueryandWhere.getString(getbackasyncQueryandWhere.getColumnIndex("date_update"));
                                    // TODO: 17.01.2025
                                    БуерДляОшибок
                                            .append("\n")
                                            .append("\n")
                                            .append("************ Ошибка ************")
                                            .append("\n")
                                            .append("\n").append(date_update);
                        String getErrorRow=     getbackasyncQueryandWhere.getString(getbackasyncQueryandWhere.getColumnIndex("Error"));
                                    // TODO: 17.01.2025
                                    БуерДляОшибок
                                            .append("\n")
                                            .append("\n")
                                            .append("\n").append(getErrorRow);
                        String Klass=     getbackasyncQueryandWhere.getString(getbackasyncQueryandWhere.getColumnIndex("Klass"));
                                    // TODO: 17.01.2025
                                    БуерДляОшибок
                                            .append("\n")
                                            .append("\n")
                                            .append("\n").append(Klass);
                        String Metod=     getbackasyncQueryandWhere.getString(getbackasyncQueryandWhere.getColumnIndex("Metod"));
                                    // TODO: 17.01.2025
                                    БуерДляОшибок
                                            .append("\n")
                                            .append("\n")
                                            .append("\n").append(Metod);
                        String LineError=     getbackasyncQueryandWhere.getString(getbackasyncQueryandWhere.getColumnIndex("LineError"));
                                    // TODO: 17.01.2025
                                    БуерДляОшибок.append("\n").append(LineError);

                        // TODO: 17.01.2025   СТЕП
                        getbackasyncQueryandWhere.move(step);
                        // TODO: 17.01.2025

                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "БуерДляОшибок " + БуерДляОшибок);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                                + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        // TODO: 01.09.2021 метод вызова
                        new RecordNewErros(context).recordnewerror(e.toString(),
                                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                    }
                });
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "БуерДляОшибок " +БуерДляОшибок );

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new RecordNewErros(context).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return  БуерДляОшибок;
    }


}
