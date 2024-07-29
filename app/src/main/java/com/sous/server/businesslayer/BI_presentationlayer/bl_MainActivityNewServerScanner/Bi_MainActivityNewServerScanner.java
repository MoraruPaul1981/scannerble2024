package com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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

    public void МетодРАзрешенияBlurtooTКлиент() {
        try {
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    /*  Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,*/
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_PRIVILEGED,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_BASIC_PHONE_STATE,
                    Manifest.permission.READ_PRECISE_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.MODIFY_PHONE_STATE,
            };
            String[] PERMISSIONS_LOCATION = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    /*    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,*/
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_PRIVILEGED,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.READ_PRECISE_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_BASIC_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.WRITE_APN_SETTINGS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.MODIFY_PHONE_STATE,
                    Manifest.permission.SEND_SMS
            };
            int permission1 = ActivityCompat.checkSelfPermission(getactivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permission2 = ActivityCompat.checkSelfPermission(getactivity, Manifest.permission.BLUETOOTH_SCAN);
            if (permission1 != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        getactivity,
                        PERMISSIONS_STORAGE,
                        1
                );
            } else if (permission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getactivity,
                        PERMISSIONS_LOCATION,
                        1
                );
            }
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

    public SQLiteDatabase МетодInitDataBase() {
        SQLiteDatabase Create_Database_СамаБАзаSQLite=null;
        try {

           Create_Database_СамаБАзаSQLite=new CREATE_DATABASEServerScanner(context).getССылкаНаСозданнуюБазу();

        // CREATE_DATABASEServerScanner createDatabaseServerScanner= new CREATE_DATABASEServerScanner(context);
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

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
        return  Create_Database_СамаБАзаSQLite;
    }







}
