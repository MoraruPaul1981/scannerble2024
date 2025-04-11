package com.dsy.dsu.BootAndAsync.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;

import com.dsy.dsu.BootAndAsync.Model.ModuleSingleWorkManager.ModuleSingleWorkManager;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class ViewModel extends AndroidViewModel {

    private  Context context;
    private MutableLiveData mutableLiveData;


    public  @Inject ViewModel(@NonNull Application application) {
        super(application);
        // TODO: 03.03.2025
        context=getApplication().getApplicationContext();
        // TODO: 03.03.2025
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

    }




    public LiveData getWorkInfo() {
        try{
            mutableLiveData = new MutableLiveData<WorkInfo>();

            ModuleSingleWorkManager moduleSingleWorkManager =new ModuleSingleWorkManager(context);

            moduleSingleWorkManager.startingSingleWorkManger();

            mutableLiveData.postValue("startingSingleWorker");

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplication().getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return mutableLiveData;
    }












}
