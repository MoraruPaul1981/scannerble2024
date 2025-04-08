package com.dsy.dsu.BusinessLogicAll.DownloadsJBOSS.BunessLogicDownloadBufferReader;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.dsy.dsu.BusinessLogicAll.DownloadsJBOSS.BunessLogicDownloadBufferReader.GetBinessLogicDwonloadReaderInterface;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class GetBinessLogicDownloadReader implements GetBinessLogicDwonloadReaderInterface {
    /**
     * @param context
     * @param
     * @return
     */
    @Override
    public BufferedReader getBinessLogicDwonloadReader(@NotNull Context context, @NotNull byte[] getbytejboss ) {
        // TODO: 07.04.2025
        BufferedReader   getNewReader =null;
  try{
          try (ByteArrayInputStream bin = new ByteArrayInputStream(getbytejboss);
               GZIPInputStream gzipper = new GZIPInputStream(bin))
          {
              // Not sure where to go here

              byte[] buffer = new byte[2048];
              ByteArrayOutputStream out = new ByteArrayOutputStream();
              int len;
              while ((len = gzipper.read(buffer)) > 0) {
                  out.write(buffer, 0, len);
              }

              gzipper.close();
              out.flush();
              out.close();
              //getNewReader=out.toByteArray();

              Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                      " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                      " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " getNewReader " +getNewReader);
          }

      Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
              " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
              " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " getNewReader " +getNewReader);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(context).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

        return getNewReader;
    }
}
