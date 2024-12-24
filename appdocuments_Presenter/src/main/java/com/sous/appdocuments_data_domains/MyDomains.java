package com.sous.appdocuments_data_domains;

import android.content.Context;


import com.sous.appdocuments_data.MyData_Class;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class MyDomains {

    MyData_Class myDataClass=new MyData_Class();



    public String getDomains(@NotNull Context context){
        System.out.printf("context"+context);
        myDataClass.getData(context);
        return  new Date().toLocaleString();
    }



}
