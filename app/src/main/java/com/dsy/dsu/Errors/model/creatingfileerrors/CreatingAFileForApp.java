package com.dsy.dsu.Errors.model.creatingfileerrors;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.dsy.dsu.Errors.model.interfaces.GetWorkerErrosInterface;

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

public  class CreatingAFileForApp implements GetWorkerErrosInterface {

    private    Context context;

    private String fileNameFull = "Sous-Avtodor-ERROR";

    private   String patchFileName="SousAvtoFile";

    public CreatingAFileForApp(Context context) {
        this.context = context;
    }


    @Override
    public void launchCreatingAFileForApp() {
        try {


            /*File patchFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File.separator + patchFileName );*/

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File.separator + patchFileName +File.separator+fileNameFull );
            if ( ! file.exists()) {

            ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileNameFull); //"menuCategory"      //file name
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");        //file extension, will automatically add to file
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + patchFileName +File.separator+fileNameFull );

                ContentResolver contentResolverCreateFileError=   context.getContentResolver();

            Uri uri =contentResolverCreateFileError.insert(MediaStore.Files.getContentUri("external"), values);      //important!

            OutputStream outputStream = contentResolverCreateFileError.openOutputStream(uri);

            outputStream.write("This is menu category data.".getBytes());

            outputStream.close();


                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +   " file.isFile() "+file.isFile());
            }

      /*      if (!patchFile.isDirectory()) {
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
            }*/


                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +   " file.isFile() "+file.isFile());

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