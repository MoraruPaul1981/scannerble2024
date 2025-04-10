package com.dsy.dsu.BusinessLogicAll.Errors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public  class GetWorkerErrosMediaStore implements  GetWorkerErrosInterface {

    private    Context context;
    private String fileName = "Sous-Avtodor-ERROR.txt";

    private   String patchFileName="SousAvtoFile";

    public GetWorkerErrosMediaStore(Context context) {
        this.context = context;
    }


    @Override
    public void launchWorkerErros() {
        try {


            File patchFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File.separator + patchFileName );

            if (!patchFile.isDirectory()) {
                patchFile.setReadable(true);
                patchFile.setWritable(true);
                patchFile.setExecutable(true);
                patchFile.mkdirs();
                // TODO: 10.04.2025
                patchFile.createNewFile();
            }

            File file = new File(String.valueOf(patchFile.getAbsoluteFile())+File.separator+fileName);
            if ( ! file.isFile()) {
                file.setReadable(true);
                file.setWritable(true);
                file.setExecutable(true);
                file.createNewFile();



                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +   " file.isFile() "+file.isFile());
            }else {

                ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "menuCategory");       //file name
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");        //file extension, will automatically add to file
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + patchFileName + File.separator + patchFileName);     //end "/" is not mandatory

                Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!



                /*String s=null;
                s.length();*/
// Check for the freshest data.
                ContentResolver getcontentResolverFile = context.getContentResolver();
                OutputStream outputStream = getcontentResolverFile.openOutputStream(uri);
                InputStream inputStream = getcontentResolverFile.openInputStream(uri);



                // TODO: 15.01.2025
                BufferedReader     BufferedReaderError = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_16));

                BufferedWriter bufferedWriterError = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_16));


                BufferedWriter bufferedWriter =  Files.newBufferedWriter(Paths.get(file.getPath()), StandardCharsets.UTF_16,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);


                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +   " file.isFile() "+file.isFile());



                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
}