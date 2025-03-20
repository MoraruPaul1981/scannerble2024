package com.dsy.dsu.EventBus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.DowloadUpdatePO.DownLoadPO;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusAyns;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.JbossAdrress.getHiltPortJbossInterface;
import com.dsy.dsu.Hilt.JbossAdrress.qualifiers.QualifierJbossServer3;
import com.dsy.dsu.Passwords.MainActivityPasswords;

import java.util.LinkedHashMap;
import java.util.function.ToDoubleBiFunction;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.EntryPoints;

public class EventBuss   {

Activity  activity;

    Context context;


    SSLSocketFactory getsslSocketFactory2;

      LinkedHashMap<Integer,String> getHiltPortJboss;


    public   EventBuss(@NonNull Activity activity, @NonNull Context context, @NonNull SSLSocketFactory getsslSocketFactory2) {
        this.activity = activity;
        this.context = context;
        this.getsslSocketFactory2 = getsslSocketFactory2;
        getHiltPortJboss=   EntryPoints.get(context, getHiltPortJbossInterface.class).getHiltPortJboss();
    }


    public void getEventBusUpdatePo(@NonNull MessageEvensBusUpdatePO messageEvensBusUpdatePO){

        try{
            Bundle bundleGetOtServiceUpdatePO =(Bundle)         messageEvensBusUpdatePO.mess.getExtras();
            String Статус=   bundleGetOtServiceUpdatePO.getString("Статус");
            Integer СервернаяВерсия=   bundleGetOtServiceUpdatePO.getInt("СервернаяВерсия");
            // TODO: 20.03.2025
            if (Статус.equalsIgnoreCase("EventBusUpdatePO")){
                // TODO: 20.03.2025




            }


            if(Статус.contains( "У вас последная версия ПО !!!")){


                Toast.makeText(context,
                        "Уже установлена !!! "+ СервернаяВерсия    , Toast.LENGTH_LONG).show();








            }else {
                if(Статус.contains( "Запускаем Обновление ПО !!!!")) {



                    Vibrator v2 = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    v2.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));

                    // TODO: 22.01.2024
                    DownLoadPO downLoadPO=new DownLoadPO(activity,context,СервернаяВерсия,getsslSocketFactory2, getHiltPortJboss);

                    downLoadPO.МетодСообщениеАнализПО( );
                    // TODO: 26.12.2022  конец основгого кода
                    Log.d(context.getClass().getName(), "\n" + " class "
                            + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                }
            }



            // TODO: 26.12.2022  конец основгого кода
            Log.d(context.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




    public void getEventBusManagerAsync(@NonNull MessageEvensBusAyns messageEvensBusAyns){

        try{

            Bundle bundleGetOtServicePrograssBar =(Bundle)         messageEvensBusAyns.mess.getExtras();
            String Статус=   bundleGetOtServicePrograssBar.getString("Статус");


            if(Статус.contains("Логин и/или пароль неправильный !!!!")){
                методПереходНаActivityPassword();
                Toast.makeText(context, "Логин и/или пароль неправильный !!!!"    , Toast.LENGTH_LONG).show();
            }




            if(Статус.contains("Вы заблокирован   !!!!")){
                // TODO: 22.01.2024  заблокирован
                методПереходНаActivityPassword();
                Toast.makeText(context, "Вы заблокирован   !!!! "    , Toast.LENGTH_LONG).show();

            }


            if(Статус.contains(    "Сервер выкл.!!!")){
                Toast.makeText(context,     "Сервер выкл.!!!"    , Toast.LENGTH_LONG).show();

            }







            // TODO: 26.12.2022  конец основгого кода
            Log.d(context.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + " Статус " +Статус);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }










    private void методПереходНаActivityPassword() {
        try {
            Intent Интент_ЗапускаетFaceApp=new Intent();
            Интент_ЗапускаетFaceApp.setClass(context, MainActivityPasswords.class);
            Интент_ЗапускаетFaceApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Интент_ЗапускаетFaceApp.setAction("MainActivityPasswords.class");
            activity.startActivity(Интент_ЗапускаетFaceApp);//tso
            activity. finishAfterTransition();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }



}
