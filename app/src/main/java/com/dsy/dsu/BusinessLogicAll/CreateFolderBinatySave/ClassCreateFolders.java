package com.dsy.dsu.BusinessLogicAll.CreateFolderBinatySave;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import java.io.File;

import io.reactivex.rxjava3.annotations.NonNull;

 public class ClassCreateFolders {

  protected   Context context;
    protected String patchFileName;
    public ClassCreateFolders(@NonNull Context context) {
        this.context=context;
    }

    public void МетодCreateFoldersBinaty() {
        try{
            File patchFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), File.separator + patchFileName);

            if (!patchFile.exists()) {
                patchFile.mkdirs();
                patchFile.setReadable(true);
                patchFile.setWritable(true);
            }
            // TODO: 25.03.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




}
