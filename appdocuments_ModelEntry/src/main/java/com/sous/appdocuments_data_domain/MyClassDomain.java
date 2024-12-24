package com.sous.appdocuments_data_domain;



import java.util.Date;

import javax.naming.Context;


public class MyClassDomain {



    public  String getDomain(){
        Context context = null;
        System.out.printf("context"+context);
        return  new Date().toLocaleString();
    }

}