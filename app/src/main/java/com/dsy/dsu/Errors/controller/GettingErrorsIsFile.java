package com.dsy.dsu.Errors.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.dsy.dsu.Errors.controller.interfaces.GettingErrorsIsFileInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class GettingErrorsIsFile  implements GettingErrorsIsFileInterface {


    Context context;

    public GettingErrorsIsFile(Context context) {
        this.context = context;
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
