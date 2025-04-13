package com.dsy.dsu.Errors.model.bl_writer_errors_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.dsy.dsu.BusinessLogicAll.DATE.Class_Generation_Data;
import com.dsy.dsu.CallNavigarlaout.CallNavigarlaout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class WewriteallApplicationErrorstoaFile {

    private String fileNameFull = "Sous-Avtodor-ERROR";

    private   String patchFileName="SousAvtoFile";


    private Context context;

    public WewriteallApplicationErrorstoaFile(Context context) {
        this.context = context;
    }


   public void    launtchWewriteallApplicationErrorstoaFile(@NonNull ArrayList<String> linkedBlockingQueueВскеОшибкиДляЗаписи) {
        try {

            String GeneratorNewDate = new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
            String СамаОшибка = null;
            boolean ДлинаСтрокивСпиноре = linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString().length() > 40;
            if (ДлинаСтрокивСпиноре) {
                StringBuffer sb = new StringBuffer(linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString());
                sb.insert(40, System.lineSeparator());
                СамаОшибка = sb.toString();
            } else {
                СамаОшибка = linkedBlockingQueueВскеОшибкиДляЗаписи.get(0).toString();
            }

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    File.separator + patchFileName +File.separator+fileNameFull+".txt" );
            if (  file.exists()) {

                    Uri uriInsertNewError=     FileProvider.getUriForFile(context.getApplicationContext(), "com.dsy.dsu.provider" ,file );
                    ContentResolver contentResolver = context.getContentResolver();
                 
                try(OutputStream outputStreamInsertnewError = contentResolver.openOutputStream(uriInsertNewError);
                    BufferedWriter  bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStreamInsertnewError, StandardCharsets.UTF_16));
                    PrintWriter printWriterWriteError = new PrintWriter(bufferedWriter,true);) {
                    
              

                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("##### ОШИБКИ ##### "+GeneratorNewDate);
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("Ошибка");
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println(СамаОшибка);
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("Класс");
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println(linkedBlockingQueueВскеОшибкиДляЗаписи.get(1).toString());
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("Метод");
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println(linkedBlockingQueueВскеОшибкиДляЗаписи.get(2).toString());
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println("Линия");
                    printWriterWriteError.println("\n");
                    printWriterWriteError.println(linkedBlockingQueueВскеОшибкиДляЗаписи.get(3).toString());
                    // TODO: 13.04.2025
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()  );
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(context.getClass().getName(),
                            "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

            }

        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(context.getClass().getName(),
                "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


}
