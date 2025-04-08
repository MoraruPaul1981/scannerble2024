package com.dsy.dsu.BusinessLogicAll.DownloadsJBOSS.BunessLogicDownloadByte;

import android.content.Context;
import android.util.Log;

import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;

import org.jetbrains.annotations.NotNull;

import java.io.File;

final public class DownloadByte {



  public synchronized  byte[]  downloadByte  (@NotNull Context context,@NotNull GetBinessLogicDwonloadByteInterface getBinessLogicDwonloadByteInterface,
                                           @NotNull   byte[] getbytejboss,@NotNull String ИмяФайлаЗагрузки){
        // TODO: 07.04.2025
      byte[]  getDownloadByte=  null;

        try{
            getBinessLogicDwonloadByteInterface =new GetBinessLogicDownloadByte();
            // TODO: 07.04.2025 start
            getDownloadByte=    getBinessLogicDwonloadByteInterface.getBinessLogicDwonloadByte(context, getbytejboss, ИмяФайлаЗагрузки);


            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " getDownloadByte " +getDownloadByte);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    return  getDownloadByte;
    }

}
