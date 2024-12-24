package com.sous.appdocuments_data;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class MyData_Class {


    public String getData(@NotNull Context context){
        System.out.printf("context"+context);
        return  new Date().toLocaleString();
    }



}
