package com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerRecyreView;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.sous.server.businesslayer.Errors.SubClassErrors;

public class BlgeneralServer  implements  InterfaceServerRecyreView {

private  Context context;
private  Long version;
    BlgeneralServer(@NonNull Context context,@NonNull Long version){
        this.context=context;
        this.version=version;
    }

    @Override
    public void changethevisibilityoftheProgressbar(@NonNull ProgressBar progressbar_server_ble) {
        try{

            if (!progressbar_server_ble.isIndeterminate()) {
                progressbar_server_ble.setIndeterminate(true);
            }

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }


    }
}
