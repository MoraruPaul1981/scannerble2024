package com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.sous.server.businesslayer.BI_presentationlayer.bl_MainActivityNewServerScanner.interfaces.BunesslogicisRunnigActivityInterface;

import java.util.List;

public class BunesslogicisRunnigActivity  implements BunesslogicisRunnigActivityInterface {

    Context context;

    Long version;


    public BunesslogicisRunnigActivity(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    public boolean isRunning( ) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (context.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))

                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " task.baseActivity.getPackageName() " +task.baseActivity.getPackageName());
                return true;
        }
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                " tasks " +tasks.size());
        return false;
    }
}
