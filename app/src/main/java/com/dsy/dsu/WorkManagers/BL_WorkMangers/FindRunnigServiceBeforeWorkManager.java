package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class FindRunnigServiceBeforeWorkManager {


    Context context;

    public FindRunnigServiceBeforeWorkManager(Context context) {
        this.context = context;
    }

    public boolean isMyServiceRunning(String serviceClassFindService) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().equalsIgnoreCase(serviceClassFindService.trim())) {
                // TODO: 10.10.2024

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " service.service.getClassName() " + service.service.getClassName()+" service.activeSince " +service.activeSince + " service.foreground " +service.foreground );

                return true;
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + "service.service.getClassName()" + service.service.getClassName());
        }
        return false;
    }
    @SuppressLint("SuspiciousIndentation")
    public boolean isMyActivityRunning(String serviceClassFindService) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {

            if (context.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                // TODO: 10.10.2024
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + "serviceClassFindService" + serviceClassFindService);

                return true;
        }
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                + "serviceClassFindService" + serviceClassFindService);
        return false;
    }

    @SuppressLint("SuspiciousIndentation")
    public boolean isSharedPreferencesRunning( SharedPreferences preferencesJboss) {
        // TODO: 10.10.2024  
        Boolean synsGrandiSrunnig = preferencesJboss.getBoolean("synsgrandisrunnig",true );
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                + "synsGrandiSrunnig" + synsGrandiSrunnig);
        return synsGrandiSrunnig;
    }
}
