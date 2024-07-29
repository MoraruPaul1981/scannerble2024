package com.sous.wifidirect;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Date;

public class WWIFI

{


    public WWIFI(Context context) {
        System.out.printf(" WWIFI onViewCreated  Fragment1_One_Tasks view "+context);
    }
      public String metodss(){
        System.out.printf(" WWIFI WWIFI onViewCreated  Fragment1_One_Tasks view ");
        return new String("ssssssssssssss"+new Date().toLocaleString()) ;
    }
}
