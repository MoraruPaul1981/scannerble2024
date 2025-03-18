package com.dsy.dsu.WorkManagers.BL_WorkMangers;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.sous.backasync.launch.ModuleQuety;

import java.util.List;
import java.util.function.Predicate;

public class FindRunnigServiceBeforeWorkManager {


    Context context;

    public FindRunnigServiceBeforeWorkManager(Context context) {
        this.context = context;
    }

    public boolean isMyServiceRunningWithNameActivity( ) {
        // TODO: 14.01.2025
        Boolean isMyActivityRunning=false;
        try{
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {



            if (service.service.getClassName().equalsIgnoreCase("MainActivityBootAndAsync")) {
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
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return isMyActivityRunning;

    }
    @SuppressLint({"SuspiciousIndentation", "NewApi"})
    public boolean isGetMyActivityRunning() {
        // TODO: 14.01.2025
         Boolean  isMyActivityRunning = false;
        try{
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = null;
            if (activityManager!=null) {
                tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
                // TODO: 14.01.2025
                List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
                isMyActivityRunning=    tasks.stream().anyMatch(new Predicate<ActivityManager.RunningTaskInfo>() {
                    @Override
                    public boolean test(ActivityManager.RunningTaskInfo runningTaskInfo) {
                        // TODO: 14.01.2025
                        Boolean  isMyActivityRunning = false;
                        ComponentName currentPackageName =   runningTaskInfo.topActivity;

                        if(currentPackageName.getClassName().length()>20){
                            if (runningTaskInfo.isRunning) {
                                isMyActivityRunning =true;
                            }
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" isMyActivityRunning "+ isMyActivityRunning);
                        }

                        return isMyActivityRunning;
                    }
                });
            }
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return isMyActivityRunning;
    }





    public Integer  getPublicIDWorkManager() {
        // TODO: 14.01.2025
        Integer  getPublicIDWorkManager = 0;
        try{
            //todo гененируем если есть публичный id
            ModuleQuety   moduleQuety=new ModuleQuety(context);
            Cursor getbackasyncQueryPulicID   =moduleQuety.getModuleQuery("successlogin",
                    " SELECT  sus.publicid  FROM successlogin  as sus   ORDER BY sus.id DESC  " ,
                    null);

            if(getbackasyncQueryPulicID.getCount()>0){
                getbackasyncQueryPulicID.moveToFirst();
                getPublicIDWorkManager =         getbackasyncQueryPulicID.getInt(0);
                Log.d(this.getClass().getName(), " ID  " + getPublicIDWorkManager);
            }

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return getPublicIDWorkManager;
    }






}
