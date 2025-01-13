package com.sous.backasync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
@Named("getModuleBufferGrud")
public class GetModuleBufferGrud {
    SQLiteDatabase sqlite;
    Context context;


    public  @Inject GetModuleBufferGrud(@ApplicationContext Context context ) {
        this.context = context;
    }





    public   void  getModuleBufferGrud(){

        System.out.printf(" Hello getModuleBackAsync !");
    }


}
