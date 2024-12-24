package com.dsy.dsu.BusinessLogicAll.CELLUPDATE;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.BusinessLogicAll.Class_Generations_PUBLIC_CURRENT_ID;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.BusinessLogicAll.DATE.Class_Generation_Data;
import com.dsy.dsu.BusinessLogicAll.SubClassUpVersionDATA;
import com.google.android.material.textview.MaterialTextView;

import java.util.Date;
import java.util.function.LongToIntFunction;
import java.util.stream.Stream;

//TODO класс обновление Ячеек
public class SubClassUpdatesCELL {
    Context context;
    private LongToIntFunction longToIntFunction;
    private SQLiteDatabase sqLiteDatabase ;
    public SubClassUpdatesCELL(Context context) {

        this.context = context;
        sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
    }


    public Integer МетодВалидацияЯчеекSaveCell(@NonNull EditText editTextRowКликПоДАнными,@NonNull String  НовоеЗначениеЯчейки ) {
        Integer ОбновлениеЯчейки=0;
        try{


    if(НовоеЗначениеЯчейки.length()>0){
        // TODO: 10.08.2023  ЦИФРА
        Integer НовоеЗначениеЯчейкиФинал= Stream.of(НовоеЗначениеЯчейки)
                .filter(f->f.length()<3)
                .mapToInt(mint->Integer.parseInt(mint))
                .findFirst().orElse(0);
        if ( НовоеЗначениеЯчейкиФинал>0 && НовоеЗначениеЯчейкиФинал<=24) {
            // TODO: 11.04.2023 Обновление Ячейки через ПРовайдер
            ОбновлениеЯчейки=    МетодСохранениеЯчейкиCellТабель(editTextRowКликПоДАнными,НовоеЗначениеЯчейкиФинал,context);
            if (ОбновлениеЯчейки>0) {
                Bundle bundleперезаписьЯчейки=(Bundle) editTextRowКликПоДАнными.getTag();
                bundleперезаписьЯчейки.putString("ПослеЗначниеДня"  , String.valueOf(НовоеЗначениеЯчейкиФинал));
            }
        }
    }else{
        // TODO: 11.04.2023 Обновление Ячейки через ПРовайдер
        ОбновлениеЯчейки=    МетодСохранениеЯчейкиCellТабель(editTextRowКликПоДАнными,0,context);
        if (ОбновлениеЯчейки>0) {
            Bundle bundleперезаписьЯчейки=(Bundle) editTextRowКликПоДАнными.getTag();
            bundleперезаписьЯчейки.putString("ПослеЗначниеДня"  , String.valueOf(НовоеЗначениеЯчейки));
        }
    }




            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +
                    "НовоеЗначениеЯчейки" +  НовоеЗначениеЯчейки + " ОбновлениеЯчейки " +ОбновлениеЯчейки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.e(context.getClass().getName(), "С ОШИБКОЙ  Стоп СЛУЖБА СЛУЖБАService_Notifications  ДЛЯ ЧАТА   ДЛЯ ЧАТА onDestroy() время "+new Date());

        }
        return ОбновлениеЯчейки;
    }

    public Integer МетодВалидацияЯчеекSaveCellМеткиТАбеля(@NonNull MaterialTextView materialTextView,@NonNull String  НовоеЗначениеЯчейки ) {
        Integer ОбновлениеЯчейки=0;
        try{
            if(НовоеЗначениеЯчейки.length()>0){
                // TODO: 10.08.2023  МЕТКА ТАБЕЛЯ
                    // TODO: 11.04.2023 Обновление Ячейки через ПРовайдер
                    ОбновлениеЯчейки=    МетодСохранениеЯчейкиCellМеток(materialTextView,НовоеЗначениеЯчейки,context);
                    if (ОбновлениеЯчейки>0) {
                        Bundle bundleперезаписьЯчейки=(Bundle) materialTextView.getTag();
                        bundleперезаписьЯчейки.putString("ПослеЗначниеДня"  , String.valueOf(НовоеЗначениеЯчейки));
                }
            }




            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +
                    "НовоеЗначениеЯчейки" +  НовоеЗначениеЯчейки + " ОбновлениеЯчейки " +ОбновлениеЯчейки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.e(context.getClass().getName(), "С ОШИБКОЙ  Стоп СЛУЖБА СЛУЖБАService_Notifications  ДЛЯ ЧАТА   ДЛЯ ЧАТА onDestroy() время "+new Date());

        }
        return ОбновлениеЯчейки;
    }





    Integer МетодСохранениеЯчейкиCellТабель(@NonNull EditText editTextCellSingleTabel,
                                            @NonNull Integer ЗначениеИзЯчейки ,
                                            @NonNull Context context){ //TODO метод записи СМЕНЫ ПРОФЕСИИ
        Integer ОбновлениеЯчейки=0;
        try{
            String ТаблицаОбработки="data_tabels";
            Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabasecurrentoperations/" +ТаблицаОбработки + "");
            Bundle bundleОбновлениеЯчейки= (Bundle)  editTextCellSingleTabel.getTag();
            ContentValues contentValuesОбноленияЯчейкиSingleTanel=new ContentValues();

            String День=bundleОбновлениеЯчейки.getString("День","");
            Long  uuid=bundleОбновлениеЯчейки.getLong("uuid",0l);
            if (ЗначениеИзЯчейки>0) {
                contentValuesОбноленияЯчейкиSingleTanel.put(День,ЗначениеИзЯчейки);
            } else {
                contentValuesОбноленияЯчейкиSingleTanel.putNull(День );
            }

            String Дата =     new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанныхДОП();
            contentValuesОбноленияЯчейкиSingleTanel.put("date_update", Дата);

            Long Версия = new SubClassUpVersionDATA().upVersionCurentTable(    ТаблицаОбработки,context);
            contentValuesОбноленияЯчейкиSingleTanel.put("current_table", Версия);



            Long getPublicID=  new Class_Generations_PUBLIC_CURRENT_ID().gettingSettingTableVersion(context," SELECT publicid FROM successlogin "  ,"successlogin");
            contentValuesОбноленияЯчейкиSingleTanel.put("user_update", getPublicID);



            // TODO: 12.04.2023 отправялем в провайдеор
            ContentResolver contentResolver=context.getContentResolver();
            ОбновлениеЯчейки=  contentResolver.update(uri, contentValuesОбноленияЯчейкиSingleTanel,"uuid=?",new String[]{String.valueOf(uuid)});
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" РЕЗУЛЬТАТ ОбновлениеЯчейки  " +  ОбновлениеЯчейки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  ОбновлениеЯчейки;
    }


// TODO: 25.08.2023  для  Меток Табеля

    Integer МетодСохранениеЯчейкиCellМеток(@NonNull MaterialTextView materialTextViewМетка,
                                            @NonNull String НоваяМетка ,
                                            @NonNull Context context){ //TODO метод записи СМЕНЫ ПРОФЕСИИ
        Integer ОбновлениеЯчейки=0;
        try{
            String ТаблицаОбработки="data_tabels";
            Uri uri = Uri.parse("content://com.dsy.dsu.providerdatabasecurrentoperations/" +ТаблицаОбработки + "");
            Bundle bundleОбновлениеЯчейки= (Bundle)  materialTextViewМетка.getTag();
            ContentValues contentValuesОбноленияЯчейкиSingleTanel=new ContentValues();

            String День=bundleОбновлениеЯчейки.getString("День","");
            Long  uuid=bundleОбновлениеЯчейки.getLong("uuid",0l);
            if (!НоваяМетка.isEmpty()) {
                contentValuesОбноленияЯчейкиSingleTanel.put(День,НоваяМетка);
            }

            String Дата =     new Class_Generation_Data(context).ГлавнаяДатаИВремяОперацийСБазойДанныхДОП();
            contentValuesОбноленияЯчейкиSingleTanel.put("date_update", Дата);

            Long Версия = new SubClassUpVersionDATA().upVersionCurentTable(    ТаблицаОбработки,context);
            contentValuesОбноленияЯчейкиSingleTanel.put("current_table", Версия);

            // TODO: 12.04.2023 отправялем в провайдеор
            ContentResolver contentResolver=context.getContentResolver();
            ОбновлениеЯчейки=  contentResolver.update(uri, contentValuesОбноленияЯчейкиSingleTanel,"uuid=?",new String[]{String.valueOf(uuid)});
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" РЕЗУЛЬТАТ ОбновлениеЯчейки  " +  ОбновлениеЯчейки);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  ОбновлениеЯчейки;
    }









// TODO: 25.08.2023  end bunisslogoc
}
