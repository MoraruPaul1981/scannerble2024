package com.dsy.dsu.CnangeServers;
import android.content.Context;
import android.util.Log;


import com.dsy.dsu.BusinessLogicAll.WorkerTables.SubClassCreatingMainAllTables;

import java.util.Date;

public  class BinessLogicPublicContent extends SubClassCreatingMainAllTables {
    private  Context context;
    public BinessLogicPublicContent(Context context) {
        this.context=context;
        Log.d(context.getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()  );
        // TODO: 06.10.2024
    }

    private   String  СсылкаНаРежимСервера;
    // TODO: 19.02.2024
    private   String  СсылкаСервераТип="jboss-1.0-SNAPSHOT";
   /// private   String  СсылкаСервераТип="jbossbackend-1.0-SNAPSHOT";//todo ТИР backend  Gradle

    public String getСсылкаНаРежимСервераТабель() {
    //   СсылкаНаРежимСервера="dsu1.glassfish.atomic";//TODO РЕЛИЗ
        //СсылкаНаРежимСервера="jboss-1.0-SNAPSHOT/dsu1.glassfish.atomic";//TODO РЕЛИЗ
        СсылкаНаРежимСервера=СсылкаСервераТип+"/sous.jboss.tabel";//TODO РЕЛИЗ
        return СсылкаНаРежимСервера.trim();
    }
    private   String  СсылкаНаРежимСервераОбновлениеПО;
    public String getСсылкаНаРежимСервераОбновлениеПО() {
        СсылкаНаРежимСервераОбновлениеПО= СсылкаСервераТип+"/sous.jboss.download";//TODO РЕЛИЗ
        return СсылкаНаРежимСервераОбновлениеПО.trim();
    }

    private   String  СсылкаНаРежимСервераRuntime;
    public String getСсылкаНаРежимСервераRuntime() {
        //   СсылкаНаРежимСервера="dsu1.glassfish.atomic";//TODO РЕЛИЗ
        //СсылкаНаРежимСервера="jboss-1.0-SNAPSHOT/dsu1.glassfish.atomic";//TODO РЕЛИЗ
        СсылкаНаРежимСервераRuntime=СсылкаСервераТип+"/sous.jboss.runtimejboss";//TODO РЕЛИЗ
        return СсылкаНаРежимСервераRuntime.trim();
    }

}//TODO END CLASS





