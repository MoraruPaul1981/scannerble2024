package com.dsy.dsu.BusinessLogicAll.Permissions;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.dsy.dsu.Errors.controller.RecordNewErros;

public class ClassPermissions {
    public ClassPermissions(@NonNull Activity activity, @NonNull int ALL_PERSSION_CODE ) {

        методДаемВсеCameraPermissions(activity,ALL_PERSSION_CODE);
    }

    public void методДаемВсеCameraPermissions(@NonNull Activity activity,@NonNull int ALL_PERSSION_CODE) {
        try{
        // Permission is not granted
        Log.d("checkCameraPermissions", "No Camera Permissions");
        //////////////////////TODO SERVICE
        String[] permissions = new String[]{
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.REQUEST_INSTALL_PACKAGES,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INSTALL_PACKAGES,
                android.Manifest.permission.WRITE_SETTINGS,
                android.Manifest.permission.WRITE_SECURE_SETTINGS,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.REQUEST_INSTALL_PACKAGES,
                android.Manifest.permission.INSTALL_PACKAGES,
                android.Manifest.permission.WRITE_SETTINGS,
                android.Manifest.permission.WRITE_SECURE_SETTINGS,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.VIBRATE,
                android.Manifest.permission.REQUEST_INSTALL_PACKAGES,
                android.Manifest.permission.INSTALL_PACKAGES,
                android.Manifest.permission.WRITE_SETTINGS,
                android.Manifest.permission.WRITE_SECURE_SETTINGS
        };
        ActivityCompat.requestPermissions(activity, permissions, ALL_PERSSION_CODE);


    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(activity).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

}
