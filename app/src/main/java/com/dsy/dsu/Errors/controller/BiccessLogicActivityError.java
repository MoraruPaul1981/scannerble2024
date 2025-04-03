package com.dsy.dsu.Errors.controller;





import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dsy.dsu.BootAndAsync.ViewModelBoot.View.MainActivityBootAndAsync;
import com.dsy.dsu.BusinessLogicAll.AnalysisUserAuthenticated.GetAnalysisUserAuthenticated;
import com.dsy.dsu.Dashboard.Model.LaunchActivityDiaologSettings;
import com.dsy.dsu.Dashboard.Model.bl_launchFragmentSettingsandDashbord.LaunchActivityDashboard;
import com.dsy.dsu.Dashboard.Model.endingasynsdashboard.LauntchActivityAfterUpdatePOAndAsync;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentSettings;
import com.dsy.dsu.Dashboard.View.MainActivity_Dashboard;
import com.dsy.dsu.R;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.sous.backasync.launch.ModuleQuety;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


public class  BiccessLogicActivityError{
    private ModuleQuety moduleQuety;
    private Context context;

    private SQLiteDatabase sqLiteDatabase_error;

    private String fileName = "Sous-Avtodor-ERROR.txt";
    private   String patchFileName="SousAvtoFile";



    public BiccessLogicActivityError(
                                     Context context,
                                     SQLiteDatabase sqLiteDatabase_error) {
        this.moduleQuety = moduleQuety;
        this.context = context;
        this.sqLiteDatabase_error = sqLiteDatabase_error;
    }


    // TODO: 28.06.2023 Запись Ошибков
    public void МетодУдаланиеОшибок()   {
        try {

            if (!sqLiteDatabase_error.inTransaction()) {
                sqLiteDatabase_error.beginTransaction();
            }
            sqLiteDatabase_error.execSQL("DELETE FROM errordsu1 ");

            // TODO: 22.09.2023
// TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

            sqLiteDatabase_error.setTransactionSuccessful();

            if (sqLiteDatabase_error.inTransaction()) {
                sqLiteDatabase_error.endTransaction();
            }



            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void clearingTableError() {
        try    {
            File fileDelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+
                    File.separator+patchFileName +File.separator+ fileName);
            if ( fileDelete.exists()) {
                Uri address = FileProvider.getUriForFile(context, "com.dsy.dsu.provider", fileDelete);
               try( final InputStream getfileStream = context.getContentResolver().openInputStream(address);){

                   FileOutputStream outputStream = new FileOutputStream(fileDelete);
                   IOUtils.copy(getfileStream, outputStream);
                   fileDelete.deleteOnExit();
                   fileDelete.delete();
                   outputStream.close();
               }

            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


        } catch (IOException e) {
            e.printStackTrace();
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    public void metodCallBackkFragemtSettings( FragmentManager fragmentManager, Context context ) {
        try{
            // TODO Запусукаем Фргамент НАстройки  dashbord
            Boolean UserAuthenticated=       new GetAnalysisUserAuthenticated(context).analysisUserAuthenticated(240);

            if (UserAuthenticated) {
                // TODO: 01.04.2024 Все в порядке ЗАпускам Саму Программу DashBord
                // TODO Запусукаем Фргамент НАстройки  dashbord
                new LaunchActivityDiaologSettings(fragmentManager,context).launchADashboardSettings();
                // TODO: 01.08.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                    // TODO: 01.08.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

            }

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    // TODO: 24.03.2025 end class
}

