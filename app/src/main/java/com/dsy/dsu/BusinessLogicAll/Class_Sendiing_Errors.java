package com.dsy.dsu.BusinessLogicAll;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import java.io.File;

public class Class_Sendiing_Errors {

    Context context;
    ////

    private String fileName = "Sous-Avtodor-ERROR.txt";

    private   String patchFileName="SousAvtoFile";
    private File file;
    public Class_Sendiing_Errors(Context context) {
        //
        this.context =context;

    }
    //функция получающая время операции ДАННАЯ ФУНКЦИЯ ВРЕМЯ ПРИМЕНЯЕТЬСЯ ВО ВСЕЙ ПРОГРАММЕ


    public void МетодПослываемОшибкиАдминистаторуПо(StringBuffer ЗаписьОшибковВстврочку,
                                                    Activity activity,Integer
                                                            ПубличноеIDПолученныйИзСервлетаДляUUID ,
                                                    @NonNull SQLiteDatabase sqLiteDatabase) {
        try {
            Intent sendErrorsMail = new Intent(Intent.ACTION_SEND);
            sendErrorsMail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            sendErrorsMail.setType("message/rfc822");
            sendErrorsMail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendErrorsMail.putExtra(Intent.EXTRA_EMAIL  , new String[]{"errorsousabto@gmail.com"});
            sendErrorsMail.putExtra(Intent.EXTRA_SUBJECT, "errorsousabto@gmail.com"+" ID пользователя чьи ошибки: " +ПубличноеIDПолученныйИзСервлетаДляUUID);
            sendErrorsMail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendErrorsMail.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            sendErrorsMail.putExtra(Intent.EXTRA_TEXT   , ЗаписьОшибковВстврочку.toString());
                /////TODO ВТОРОЙ ШАГ СИНХРОНИЗАЦИИ ПОЛУЧАЕМ СПИСОК ТАБЛИЦ КОТОРЫЕ НУЖНО  СИНХРОНИЗИРОВАТЬ 100% процентов , И ПРОВЕРМЯЕМ ЕСЛИ СВЯЗЬ С ИНТЕНТОМ
                    // TODO: 02.06.2022 код удаление ошибков после успешной отпавки ошибок на почту
               // activity. startActivity(Intent.createChooser(i, "Отправка ошибок..."));
            activity. startActivityForResult(Intent.createChooser(sendErrorsMail, "Отправка..."), 11);

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


    }


}


