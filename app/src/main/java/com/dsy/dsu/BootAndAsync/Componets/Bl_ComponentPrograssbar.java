package com.dsy.dsu.BootAndAsync.Componets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusPrograssBar;
import com.dsy.dsu.Errors.controller.RecordNewErros;

import java.util.Optional;

public class Bl_ComponentPrograssbar {
    // TODO: 25.09.2024
  private   ProgressBar progressbarbootandasync;

    private   Context context;

    public Bl_ComponentPrograssbar(ProgressBar progressbarbootandasync, Context context) {
        this.progressbarbootandasync = progressbarbootandasync;
        this.context = context;
    }


    public void getEventBusPrograssBar(@NonNull MessageEvensBusPrograssBar messageEvensBusPrograssBar){
        try{
            if (progressbarbootandasync.isAttachedToWindow()) {
                Bundle bundleGetOtServicePrograssBar = (Bundle) messageEvensBusPrograssBar.mess.getExtras();
                String Статус = bundleGetOtServicePrograssBar.getString("Статус");
                Handler handlerProfgarsBar = progressbarbootandasync.getHandler();
                if (Статус.contains("PrograssBarVisible")) {
                    МетодВизуацииБесконечногоПрогресБара();
                    // TODO: 26.12.2022  конец основгого кода
                    Log.d(context.getClass().getName(), "\n" + " class "
                            + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                }
                if (Статус.contains("AsyncPrograssBar")) {
                    МетодВизуализацииСинхронизации(bundleGetOtServicePrograssBar, handlerProfgarsBar);
                    // TODO: 26.12.2022  конец основгого кода
                    Log.d(context.getClass().getName(), "\n" + " class "
                            + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                }
            }
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void МетодВизуацииБесконечногоПрогресБара() {
            if (!progressbarbootandasync.isIndeterminate()) {
                // TODO: 25.09.2024
                progressbarbootandasync.setIndeterminate(true);
                progressbarbootandasync.requestLayout();
                progressbarbootandasync.refreshDrawableState();
            }
    }


    @SuppressLint("NewApi")
    @MainThread
    private void МетодВизуализацииСинхронизации(@NonNull Bundle bundleCallsBackAsynsService,@NonNull  Handler handlerProfgarsBar) {
        try {
            // TODO: 25.09.2024
            handlerProfgarsBar.post(()->{
                // TODO: 10.01.2025
                try{
                // TODO: 25.09.2024

                Drawable progressDrawable = progressbarbootandasync.getProgressDrawable().mutate();
                progressDrawable.setColorFilter(Color.parseColor("#000000"), android.graphics.PorterDuff.Mode.SRC_IN);
                progressbarbootandasync.setProgressDrawable(progressDrawable);
                //      progressbarbootandasync.setProgressDrawableTiled(progressDrawable);



                    // TODO: 25.09.2024
                     Integer MaxКоличествоСТрочеек = bundleCallsBackAsynsService.getInt("maxtables", 0);
                    String currentEventTable = bundleCallsBackAsynsService.getString("имятаблицы", "");
                   // Integer SuccessInsertOrUpdates = bundleCallsBackAsynsService.getInt("currentEventTable" , 0);
                    // TODO: 25.09.2024
                    if (!currentEventTable.isEmpty()  ) {
                 CharSequence currentEventTableAnSync=    Optional.ofNullable(progressbarbootandasync.getTooltipText()).orElse("");
                        if (!currentEventTable.equalsIgnoreCase(currentEventTableAnSync.toString())) {
                            Integer  Progress = progressbarbootandasync.getProgress() + 1;
                            Integer SecondaryProgress = Progress + 2;
                            progressbarbootandasync.setMax(MaxКоличествоСТрочеек);
                            progressbarbootandasync.setIndeterminate(false);
                            // TODO: 25.09.2024  
                            if (Progress>progressbarbootandasync.getProgress()) {
                                progressbarbootandasync.setProgress(Progress, true);
                                // TODO: 25.09.2024
                                progressbarbootandasync.setSecondaryProgress(SecondaryProgress);
                                progressbarbootandasync.setSecondaryProgressTintList(ColorStateList.valueOf(Color.GRAY));
                                // TODO: 10.01.2025
                                progressbarbootandasync.setTooltipText(currentEventTableAnSync);
                            }
                            // TODO: 30.09.2024
                            progressbarbootandasync.setTooltipText(currentEventTable);
                        } else {
                            progressbarbootandasync.setIndeterminate(true);
                        }
                    }else {
                        progressbarbootandasync.setIndeterminate(true);
                    }
                    progressbarbootandasync.requestLayout();
                    progressbarbootandasync.refreshDrawableState();
                    
                    Log.d(this.getClass().getName(), "\n" + " class " +
                            Thread.currentThread().getStackTrace()[2].getClassName()
                            + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " progressbarbootandasync" + progressbarbootandasync.getProgress());
                // TODO: 10.01.2025
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(context).recordnewerror(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            });

            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " progressbarbootandasync" + progressbarbootandasync.getProgress());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }





    // TODO: 25.09.2024  END CLASS
}
