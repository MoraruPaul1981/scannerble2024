package com.dsy.dsu.Errors.model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.dsy.dsu.Errors.model.interfaces.GettingExistingErrorsInterface;
import com.sous.backasync.launch.ModuleQuety;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GettingExistingErrorFromFile  implements GettingExistingErrorsInterface {
    String fileName = "Sous-Avtodor-ERROR.txt";
    String patchFileName="SousAvtoFile";

    /**
     * @return
     */
    @SuppressLint("SuspiciousIndentation")
    @Override
    public StringBuffer gettingExistingErrors(@NonNull Context context,ModuleQuety moduleQuety,SQLiteDatabase sqLiteDatabase_error) {
        // TODO: 17.04.2023
        Single<StringBuffer>stringBufferSingle=null;
        try {
            // TODO: 07.04.2025  
         stringBufferSingle=Single.fromCallable(()->{
                // TODO: 07.04.2025  get erro from file
             StringBuffer stringBuffergetFileError=new StringBuffer();

                File getPatchNewFileError= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        +File.separator+patchFileName+File.separator+fileName  );
                BufferedReader newBufferedReader = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (  getPatchNewFileError.exists()) {
                        Uri address = FileProvider.getUriForFile(context, "com.dsy.dsu.provider", getPatchNewFileError);
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
                    newBufferedReader = Files.newBufferedReader(Paths.get(getPatchNewFileError.getPath()), StandardCharsets.UTF_16);

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "newBufferedReader " +newBufferedReader.markSupported() );

                }

                if (newBufferedReader!=null) {
                    String    lineErrorsAll=null;
                    while ((lineErrorsAll =newBufferedReader.readLine()) != null) {
                        stringBuffergetFileError.append(lineErrorsAll);
                        stringBuffergetFileError.append('\n');
                        Log.d(this.getClass().getName(), "line " +lineErrorsAll  );
                    }
                }
             // TODO: 07.04.2025
             newBufferedReader.close();
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "stringBuffergetFileError " +stringBuffergetFileError );

                return  stringBuffergetFileError;
            }).doOnError(e->{
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                        + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.09.2021 метод вызова
                new RecordNewErros(context).recordnewerror(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());


            }).subscribeOn(Schedulers.single());
            // TODO: 07.04.2025
            Log.d(this.getClass().getName(), "\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return      stringBufferSingle.blockingGet();

    }

    // TODO: 24.03.2025 end class
}

