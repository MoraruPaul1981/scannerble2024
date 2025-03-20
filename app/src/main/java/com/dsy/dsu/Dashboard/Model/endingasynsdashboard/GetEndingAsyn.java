package com.dsy.dsu.Dashboard.Model.endingasynsdashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusNetworkStatuses;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusEndAync;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Passwords.MainActivityPasswords;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

public class GetEndingAsyn {



    public void metoEndingAsynsDashboard(@NonNull Context context,
                                         @NonNull ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО){
        try {

            Intent intentAnsycEnding=new Intent();
            intentAnsycEnding.setAction("EventBusAnsyc");
            Bundle bundle=new Bundle();

            bundle.putBinder("callbackbinderdashbord",localBinderОбновлениеПО);
            bundle.putString("Статус",   "AnsycEnd");///"В процесс"
            intentAnsycEnding.putExtras(bundle);

            EventBus.getDefault().post(new MessageEvensBusEndAync(intentAnsycEnding));

            Log.d(this.getClass().getName(),"\n"
                    + " bremy: " + new Date()+"\n+"
                    + "  class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    public void metoEndingAsynsOtService(@NonNull Context context){
        try {

            Intent intentAnsycEnding=new Intent();
            intentAnsycEnding.setAction("EventBusAnsyc");
            Bundle bundle=new Bundle();

            bundle.putString("Статус",  "AnsycEnd");///"В процесс"
            intentAnsycEnding.putExtras(bundle);

            EventBus.getDefault().post(new MessageEvensBusEndAync(intentAnsycEnding));

            Log.d(this.getClass().getName(),"\n"
                    + " bremy: " + new Date()+"\n+"
                    + "  class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    public void veryfirstlaunchActivityPassword(@NonNull Context context) {
        try{
            Intent Интент_ЗапускаетFaceApp=new Intent();
            Интент_ЗапускаетFaceApp.setClass(context, MainActivityPasswords.class);
            Интент_ЗапускаетFaceApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Интент_ЗапускаетFaceApp.setAction("MainActivityPasswords.class");
            context.startActivity(Интент_ЗапускаетFaceApp);//tso
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" +
                    " localBinderAsync "+ "\n" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    // TODO: 25.09.2024  end class
}
