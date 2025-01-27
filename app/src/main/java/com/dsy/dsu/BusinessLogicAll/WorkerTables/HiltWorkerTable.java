package com.dsy.dsu.BusinessLogicAll.WorkerTables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Qualifier;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@EntryPoint
@InstallIn(SingletonComponent.class)
public interface HiltWorkerTable {


    CopyOnWriteArrayList<String> getWorkerTablesALl( );
}
