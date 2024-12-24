package com.dsy.dsu.SynsProccessor;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormattingVersionDastaSqlserver {

Context context;

    public FormattingVersionDastaSqlserver(@NonNull  Context context) {
        this.context = context;
    }

    public Date formattingDateOnVersionSqlServer( @NonNull  String ВерсияДанныхСервернойТаблицы) {
        Date ДатаВерсииДанныхSQLServer = null;
        try {
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS" );

            DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try{
                ДатаВерсииДанныхSQLServer = dateFormat.parse(ВерсияДанныхСервернойТаблицы);
            }catch(ParseException parseEx){
                //parseEx.printStackTrace();
                dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ДатаВерсииДанныхSQLServer = dateFormat.parse(ВерсияДанныхСервернойТаблицы);
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " ДатаВерсииДанныхSQLServer " + ДатаВерсииДанныхSQLServer);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return ДатаВерсииДанныхSQLServer;

    }


}
