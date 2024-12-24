package com.dsy.dsu.BusinessLogicAll.CreateFolderBinatySave;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.dsy.dsu.Errors.Class_Generation_Errors;

import java.io.File;

import io.reactivex.rxjava3.annotations.NonNull;

public class ClassCreateFolderBinatyMatrilal  extends  ClassCreateFolders{


    public ClassCreateFolderBinatyMatrilal(Context context) {
        super(context);
    }

    @Override
    public void МетодCreateFoldersBinaty() {
        patchFileName="SousAvtoFile/AppMaterial/Photos";
        // TODO: 25.03.2023
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        super.МетодCreateFoldersBinaty();
    }
}





