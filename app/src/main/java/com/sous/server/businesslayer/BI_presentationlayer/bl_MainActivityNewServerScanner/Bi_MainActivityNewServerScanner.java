package com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sous.server.R;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.datalayer.data.CREATE_DATABASEServerScanner;

import java.util.Date;

public class Bi_MainActivityNewServerScanner {

    protected  Context context;
    protected  Long version;
    protected FragmentManager fragmentManager;
    protected Activity getactivity;


    public Bi_MainActivityNewServerScanner(Context context , FragmentManager fragmentManager, Activity getactivity ) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.getactivity = getactivity;
    }

    public Long getversionCurrentPC() throws PackageManager.NameNotFoundException {
        Long version=0l;
        try{
            PackageInfo pInfo =context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            this.version=version;

            Log.i(this.getClass().getName(), "  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString() + " version " +version);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
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
        return  version;

    }



    // TODO: 05.12.2022  запуск клиента
    @SuppressLint("SuspiciousIndentation")
    public void МетодЗапускBootФрагмента(@NonNull Fragment fragmentBoot) {
        try {

            FragmentTransaction    fragmentTransactionBoot = fragmentManager.beginTransaction();
            fragmentTransactionBoot.addToBackStack(null);
            fragmentTransactionBoot.add(R.id.id_frameLayoutmain_boot, fragmentBoot);//.layout.activity_for_fragemtb_history_tasks
            fragmentTransactionBoot.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();//FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
            fragmentTransactionBoot.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransactionBoot.show(fragmentBoot);

            Log.i(this.getClass().getName(), "  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString());

            Log.i(this.getClass().getName(), "  "
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " время " + new Date().toLocaleString());
// TODO: 07.01.2022 GREAT OPERATIONS подпииска на данны
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
