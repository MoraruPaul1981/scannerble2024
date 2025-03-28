package com.dsy.dsu.BootAndAsync.Service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ServiceCompat;

import com.dsy.dsu.BootAndAsync.BlBootAsync.CompleteRemoteSyncService;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.JbossAdrress.getHiltPortJbossInterface;
import com.dsy.dsu.Hilt.PublicId.QualifierPublicId;
import com.dsy.dsu.Hilt.getSSLSocketFactory2.QualifiergetsslSocketFactory2;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.EntryPoints;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 *
 *
 */

@AndroidEntryPoint
public class IntentServiceBoot extends IntentService {


    // TODO: 03.03.2025
    public  LocalBinderBootSerice getlocalBinderBootSerice = new  LocalBinderBootSerice();


    @Inject
    CompleteRemoteSyncService completeRemoteSyncService;

    @Inject
    @QualifiergetsslSocketFactory2
    SSLSocketFactory getsslSocketFactory2;




    @Inject
    @QualifierPublicId
    Integer getHiltPublicId;




    Notification notification;


    public IntentServiceBoot() {

        super("IntentServiceBoot");
    }


    @Override
    public ContentResolver getContentResolver() {
        return super.getContentResolver();
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 24.09.2024
        try{
            startingNotificationService();
            Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

}




    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            // TODO: 25.12.2024
            desibleServiceForeground();
            // TODO: 10.10.2024 записываем статус службы ка в менякем статус как отработал
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        try{
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {



            startingServiceBoot(intent,"BootService");





            desibleServiceForeground();

            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " intent.getAction() " +intent.getAction());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    private void desibleServiceForeground() {
        try{
            stopForeground(false);
        stopSelf();
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());

    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    private void startingNotificationService() {
        try{
            String CHANNEL_ID = this.getClass().getName();
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_NONE);

            ((NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Обмен данными...")
                    .setContentText("Обмен данными...").build();

            ServiceCompat.startForeground(this,17,notification,ServiceCompat.STOP_FOREGROUND_REMOVE);
            //ServiceCompat.startForeground(this,17,notification,ServiceCompat.STOP_FOREGROUND_REMOVE);
            // startForeground(17,notification,ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




    public class LocalBinderBootSerice extends Binder {
        public IntentServiceBoot getService() {
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            return IntentServiceBoot.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        //   return super.onBind(intent);
        return getlocalBinderBootSerice;
    }



public     void startingServiceBoot(@NotNull Intent intent,@NonNull String getWhoLaunched){
        try{
            LinkedHashMap<Integer,String> getHiltPortJboss=   EntryPoints.get(getApplicationContext(), getHiltPortJbossInterface.class).getHiltPortJboss();
            // TODO: 26.12.2024 выди запуска
            switch (intent.getAction().trim()){

                // TODO: 26.12.2024 Синхрониазция
              case "IntentServiceBootAsync.com" :

                    // TODO: 19.01.2024  запуск класса бизнес логики службы Синхроиазции и Обновление ПО
                    completeRemoteSyncService.startServiceOnlyAsync(getsslSocketFactory2, "IntentServiceBootAsync.com", getHiltPortJboss,
                            "BootService",getApplicationContext());

                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " intent.getAction() " +intent.getAction());

                    break;

                // TODO: 26.12.2024 Только Обновление ПО
                case "IntentServiceBootUpdatePo.com":

                    // TODO: 19.01.2024  запуск класса бизнес логики службы Синхроиазции и Обновление ПО
                    completeRemoteSyncService.startServiceOnlyUpdatePO(getsslSocketFactory2, "IntentServiceBootUpdatePo.com", getHiltPortJboss,
                            "BootService",getApplicationContext());

                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " intent.getAction() " +intent.getAction());
                    break;

                // TODO: 26.12.2024 И Обновление и Синхронизация
                case "IntentServiceBootUpdatePo.comAndIntentServiceBootAsync.com" :

                    // TODO: 19.01.2024  запуск класса бизнес логики службы Синхроиазции и Обновление ПО
                    completeRemoteSyncService.startServiceUpdatePOAndAsync( getsslSocketFactory2, "IntentServiceBootUpdatePo.comAndIntentServiceBootAsync.com",
                            getHiltPortJboss,"BootService",getApplicationContext());

                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " intent.getAction() " +intent.getAction());

                    break;


                // TODO: 26.12.2024 EXIT
                case     "ExitBootService" :
                    // TODO: 19.01.2024  запуск класса бизнес логики службы Синхроиазции и Обновление ПО
                    completeRemoteSyncService.startServiceUpdatePOAndAsync(getsslSocketFactory2, "IntentServiceBootUpdatePo.comAndIntentServiceBootAsync.com",
                            getHiltPortJboss,"BootService",getApplicationContext());


                    completeRemoteSyncService.    getDontNetwork(getApplicationContext());


                    Log.d(getApplicationContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " intent.getAction() " +intent.getAction());

                    break;





            }
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " getHiltPortJboss " +getHiltPortJboss);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    // TODO: 10.10.2024 end class
}
