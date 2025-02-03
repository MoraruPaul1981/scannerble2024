package com.sous.backasync.versions;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.util.Log;

import com.sous.backasync.dates.Class_GenerationBack_Data;

import org.jetbrains.annotations.NotNull;

public class SubClassVersionDATABack {
    SQLiteDatabase sqLiteDatabase ;

    public SubClassVersionDATABack( ) {

       // sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
    }

    // TODO: 19.11.2022  МЕТОДД ЗАПИСЫВАЕМ ВЕРСИЮ ДАННЫХ В СИСТЕМНЦЮ ТАБЛИЦ ИЗ ТЕКУЩЕЙ ТАБЛИЦЫ
    public Integer upVersionMODIFITATION_ClientRemote(@NotNull String Таблица, @NotNull Context context ) {
        Integer Результат_ПовышенаяВерсия = 0;
        try {
            String ТаблицаСистемная = "MODIFITATION_Client";
            SQLiteQueryBuilder SQLBuilderВерсияДанныхСистемнаяТАблицы = new SQLiteQueryBuilder();
            Long ВерсияДанныхПослеСинхрониазацииДляЗаписи = МетодАнализаВерсииCurrentTable(Таблица, context );
            // TODO: 22.11.2021  ПОСЛЕ УСПЕШНОЙ ОПЕРАЦИИ ПОДТВЕРЖДАЕМ ТРАНЗАУЙИЮ
            Log.d(this.getClass().getName(), "  ВерсияДанныхПослеСинхрониазацииДляЗаписи   " + ВерсияДанныхПослеСинхрониазацииДляЗаписи + " Таблица " + Таблица);

            String СгенерированованныйДата = new Class_GenerationBack_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
            ContentValues contentValuesДляПоднятияВерсии = new ContentValues();


            if (ВерсияДанныхПослеСинхрониазацииДляЗаписи>0) {
                // TODO: 01.07.2023  после выравниванию ДЛЯ СЕРВЕРА
                contentValuesДляПоднятияВерсии.put("versionserveraandroid", СгенерированованныйДата);
                contentValuesДляПоднятияВерсии.put("versionserveraandroid_version", ВерсияДанныхПослеСинхрониазацииДляЗаписи);

                // TODO: 01.07.2023  после выравниванию ДЛЯ ЛОКАЛЬНАЯ
                //  contentValuesДляПоднятияВерсии.put("localversionandroid_version", ВерсияДанныхПослеСинхрониазацииДляЗаписи);

                //  contentValuesДляПоднятияВерсии.put("localversionandroid", СгенерированованныйДата);

                SQLBuilderВерсияДанныхСистемнаяТАблицы.setTables(ТаблицаСистемная);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Результат_ПовышенаяВерсия = SQLBuilderВерсияДанныхСистемнаяТАблицы.
                            update(sqLiteDatabase, contentValuesДляПоднятияВерсии, "name=?", new String[]{Таблица.toLowerCase()});
                } else {
                    Результат_ПовышенаяВерсия = sqLiteDatabase.update(ТаблицаСистемная, contentValuesДляПоднятияВерсии,
                            "name=?", new String[]{Таблица.toLowerCase()});
                }
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  Результат_ПовышенаяВерсия   " + Результат_ПовышенаяВерсия);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
          /*  new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());*/
        }

        return Результат_ПовышенаяВерсия;

    }

    // TODO: 19.11.2022  МЕТОД ЗАПИСЫВАЕМ ВЕРСИЮ ДАННЫХ В СИСТЕМНУЮ ТАБЛИЦУ ВЕРСИЕ КОТОРАЯ ПРИШЛА ОТ СЕРВЕРА
    public Integer МетодVesrionFromSqlServerUPMODIFITATION_Client(@NotNull String Таблица,@NotNull Context context
            ,@NotNull Long ВерсияДанныхПослеСинхронизацииОтСервера) {
        Integer Результат_ПовышенаяВерсия = 0;
        try {
            String ТаблицаСистемная = "MODIFITATION_Client";
            SQLiteQueryBuilder      SQLBuilderВерсияДанныхСистемнаяТАблицы = new SQLiteQueryBuilder();
            // TODO: 22.11.2021  ПОСЛЕ УСПЕШНОЙ ОПЕРАЦИИ ПОДТВЕРЖДАЕМ ТРАНЗАУЙИЮ
            Log.d(this.getClass().getName(), "  ВерсияДанныхПослеСинхронизацииОтСервера   " + ВерсияДанныхПослеСинхронизацииОтСервера + " Таблица " + Таблица);
            String СгенерированованныйДата = new Class_GenerationBack_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанных();
            ContentValues contentValuesДляПоднятияВерсии = new ContentValues();


            if (ВерсияДанныхПослеСинхронизацииОтСервера>0) {
                // TODO: 01.07.2023  после выравниванию ДЛЯ СЕРВЕРА
                contentValuesДляПоднятияВерсии.put("versionserveraandroid", СгенерированованныйДата);
                contentValuesДляПоднятияВерсии.put("versionserveraandroid_version", ВерсияДанныхПослеСинхронизацииОтСервера);

                // TODO: 01.07.2023  после выравниванию ДЛЯ ЛОКАЛЬНАЯ
                contentValuesДляПоднятияВерсии.put("localversionandroid_version", ВерсияДанныхПослеСинхронизацииОтСервера);

                contentValuesДляПоднятияВерсии.put("localversionandroid", СгенерированованныйДата);

                SQLBuilderВерсияДанныхСистемнаяТАблицы.setTables(ТаблицаСистемная);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Результат_ПовышенаяВерсия = SQLBuilderВерсияДанныхСистемнаяТАблицы.
                            update(sqLiteDatabase, contentValuesДляПоднятияВерсии, "name=?", new String[]{Таблица.toLowerCase()});
                } else {
                    Результат_ПовышенаяВерсия = sqLiteDatabase.update(ТаблицаСистемная, contentValuesДляПоднятияВерсии,
                            "name=?", new String[]{Таблица.toLowerCase()});
                }
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                        "  Результат_ПовышенаяВерсия   " + Результат_ПовышенаяВерсия);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
/*            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());*/
        }

        return Результат_ПовышенаяВерсия;

    }


    // TODO:MAX cURRENT table
    public Long МетодАнализаВерсииCurrentTable( @NotNull String Текущаятаблицы,
                                                @NotNull Context context) {
        Long  АнализВерсииMAXCurrentTable=0l;
        try   ( SQLiteCursor КурсоАнализVersionCurrentTable=
                        (SQLiteCursor) sqLiteDatabase.rawQuery(" SELECT MAX ( current_table  ) " +
                                "AS MAX_R  FROM " +  Текущаятаблицы.trim()+"" , null);) {
    /*         Курсор_КоторыйПолучаетМаксимальюнуВерсиюДанных= (SQLiteCursor)
                            getБазаДанныхДЛяОперацийВнутри.rawQuery(" SELECT MAX ( " +ТекущаяяКолонкаТаблицы.trim() + "  ) AS MAX_MODIFITATION_Client  FROM " + уже запомнитовано провренно
                                    "  MODIFITATION_Client  WHERE  name = '" + Текущаятаблицы.trim().toLowerCase() +"'  ;", null);*/
            if(КурсоАнализVersionCurrentTable.getCount()>0){
                КурсоАнализVersionCurrentTable.moveToFirst();
                Integer  ИндексГдеСтолбикМах=КурсоАнализVersionCurrentTable.getColumnIndex("MAX_R");
                АнализВерсииMAXCurrentTable=КурсоАнализVersionCurrentTable.getLong(ИндексГдеСтолбикМах);
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " АнализВерсииMAXCurrentTable  " + АнализВерсииMAXCurrentTable + "  Текущаятаблицы" + Текущаятаблицы);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
           /* new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());*/
        }

        return  АнализВерсииMAXCurrentTable;
    }



    // TODO:MAX cURRENT table
    @SuppressLint("SuspiciousIndentation")
    public Long upVersionCurentTable(@NotNull String Текущаятаблицы,
                                     @NotNull Context context) {

        Long  ПовышенняВерсия=0l;
        try  ( Cursor Курсор_АнализMODIFITATION_Client= ( Cursor) sqLiteDatabase.rawQuery(" SELECT *  FROM " +
                "  MODIFITATION_Client  WHERE  name = '"+Текущаятаблицы+"' ", null);)    {

            if(Курсор_АнализMODIFITATION_Client.getCount()>0 && Курсор_АнализMODIFITATION_Client!=null){
                Курсор_АнализMODIFITATION_Client.moveToFirst();
                String  ПовышенняВерсияMAXCurrddentTable=Курсор_АнализMODIFITATION_Client.getString(0);
                Long ПовышенняВерсияMODIFITATION_Client=(Long) Курсор_АнализMODIFITATION_Client.getLong(3);

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " ПовышенняВерсияMAXCurrddentTable  " + ПовышенняВерсияMAXCurrddentTable);

                // TODO: 16.11.2023 еще обна проверка
                Long ПовышенняВерсияMAXCurrentTable=   МетодАнализаВерсииCurrentTable(Текущаятаблицы,context);

                if(ПовышенняВерсияMODIFITATION_Client>ПовышенняВерсияMAXCurrentTable){

                    ПовышенняВерсияMODIFITATION_Client++;
                    ПовышенняВерсия=ПовышенняВерсияMODIFITATION_Client;
                }else {

                    ПовышенняВерсияMAXCurrentTable++;
                    ПовышенняВерсия=ПовышенняВерсияMAXCurrentTable;
                }

            }else {
                ПовышенняВерсия=1l;
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " ПовышенняВерсия  " + ПовышенняВерсия + "  Текущаятаблицы" + Текущаятаблицы);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
           /* new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());*/
        }
        return  ПовышенняВерсия;
    }

}
