package com.dsy.dsu.Errors.model.bl_writer_errors_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.dsy.dsu.BusinessLogicAll.DATE.Class_Generation_Data;
import com.dsy.dsu.CallNavigarlaout.CallNavigarlaout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

            String СгенерированованныйДатаВремениСейчаcДляУдаления = new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
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
                    OutputStream outputStreamInsertnewError = contentResolver.openOutputStream(uriInsertNewError);

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStreamInsertnewError, StandardCharsets.UTF_16));


                bufferedWriter.write("\n");
                bufferedWriter.write("##### ERROR #####");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
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

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ bufferedWriter.toString() );
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
