package com.dsy.dsu.BootAndAsync.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;


import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusAppAfterSyncing;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusNetworkStatuses;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusPrograssBar;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.BootAndAsync.Model.ModuleSingleWorkManager.ModuleSingleWorkManager;
import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentActivityBootService;


import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentPrograssbar;
import com.dsy.dsu.BusinessLogicAll.Permissions.GrandPermissions;
import com.dsy.dsu.CoreApp.Model.BunessLogicCoreApp;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.dsy.dsu.Hilt.getSSLSocketFactory2.QualifiergetsslSocketFactory2;
import com.dsy.dsu.R;
import com.google.android.material.navigation.NavigationView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivityBootAndAsync extends AppCompatActivity {

    protected ProgressBar progressbarbootandasync;
    protected Activity activity;

    protected DrawerLayout drawerLayoutAsync;
    protected NavigationView navigationViewAsyncApp;

    protected LifecycleOwner getlifecycleOwner  ;

    @Inject
    @QualifiergetsslSocketFactory2
    public SSLSocketFactory getsslSocketFactory2;

    protected GetComponentActivityBootService blInnerMainActivityBootAndAsync;

    private   final int ALL_PERSSION_CODE=1;


    private ImageView imageView_faceapp_settings;


   private FragmentManager fragmentManager;


   @Inject
    ModuleSingleWorkManager moduleSingleWorkManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_bootandasync_prograssbar);
            getSupportActionBar().hide(); ///скрывать тул бар


            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            fragmentManager = getSupportFragmentManager();

            progressbarbootandasync = (ProgressBar) findViewById(R.id.progressbarbootandasync); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА/
            drawerLayoutAsync = (DrawerLayout) findViewById(R.id.drawerLayout_async_prograsser); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            drawerLayoutAsync.setBackgroundColor(Color.WHITE);         //TODO устанвливает цвета
            drawerLayoutAsync.setDrawingCacheBackgroundColor(Color.RED);//todo
            navigationViewAsyncApp    = (NavigationView) findViewById(R.id.navigator_asyncapp); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА

            imageView_faceapp_settings = (ImageView) findViewById(R.id.imageView_faceapp_settings); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА

            activity = this;
            getlifecycleOwner=this;

            // TODO: 04.10.2023 разрешения для всего
            // TODO: 04.10.2023 разрешения для всего
            GrandPermissions grandPermissions=   new GrandPermissions(this );
            grandPermissions.checkPermissions();




            // TODO   запускам бизнес логику CoreApp
            new BunessLogicCoreApp(getApplicationContext()).getBunessLogicCoreApp();




            registeEventBusFirst();


            blInnerMainActivityBootAndAsync=new GetComponentActivityBootService(getsslSocketFactory2,
                    progressbarbootandasync, activity, drawerLayoutAsync,
                    navigationViewAsyncApp,getApplicationContext(),  getlifecycleOwner,imageView_faceapp_settings);


            // TODO: 19.01.2024  запускаем бизнес логики автивити boot and async
            blInnerMainActivityBootAndAsync .  МетодБоковаяПанельОткрытьЗАкрыть();
            blInnerMainActivityBootAndAsync .listerNavigationViewAsyncApp();
            blInnerMainActivityBootAndAsync .  workerNavigationViewAsyncApp(fragmentManager);
            blInnerMainActivityBootAndAsync .  workerImageViewsettings();




            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            moduleSingleWorkManager.startingSingleWorkManger();;

            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");
    }

    // TODO: 26.10.2022 сохраняет данные
    protected void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            // TODO: 23.08.203 статуст поворта экрана
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    // TODO: 26.10.2022 воставливает данные
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    Log.d(this.getClass().getName(), " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+
                            " Класс  :" + Thread.currentThread().getStackTrace()[2].getClassName());
                } else {
                    // no permissions granted.
                    Log.d(this.getClass().getName(), " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber()+
                            " Класс  :" + Thread.currentThread().getStackTrace()[2].getClassName());
                }
                return;
            }
        }
    }













    // TODO: 17.08.2023  запуск обновленея ПО и синхрониазции






    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            unregisterEventBusFirst();
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

















    // TODO: 23.01.2024 EventBus for Prograssbar
    @Subscribe (threadMode = ThreadMode.MAIN_ORDERED)
    public void EventMessageEvensBusPrograssBar(MessageEvensBusPrograssBar messageEvensBusPrograssBar){
        try{
           GetComponentPrograssbar get_componentPrograssbar =new GetComponentPrograssbar(progressbarbootandasync,getApplicationContext());
            get_componentPrograssbar.getEventBusPrograssBar(messageEvensBusPrograssBar);
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + "   starting... onRestart" + " starting... onRestart");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    // TODO: 23.01.2024 EventBus for Update PO
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void EventMessageEvensBusUpdatePO(MessageEvensBusUpdatePO messageEvensBusUpdatePO){
        try{


            blInnerMainActivityBootAndAsync   .getEventBusUpdatePo(messageEvensBusUpdatePO);

            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + "   starting... onRestart" + " starting... onRestart");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    // TODO: 23.01.2024 EventBus for Status
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void EventMessageEvensBusAyns(MessageEvensBusNetworkStatuses messageEvensBusNetworkStatuses){
        try{

            blInnerMainActivityBootAndAsync .getEventBusNetworkStatuses(messageEvensBusNetworkStatuses);

            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + "   starting... onRestart" + " starting... onRestart");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    // TODO: 23.01.2024 EventBus for AppAfterSyncing
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void EventMessageEvensAppAfterSyncing(MessageEvensBusAppAfterSyncing messageEvensBusAppAfterSyncing){
        try{



            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + "   starting... onRestart" + " starting... onRestart");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }






    private void registeEventBusFirst() {

        if (  !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                + "   starting... onRestart" + " starting... onRestart");
    }

    private void unregisterEventBusFirst() {
        if (  EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        Log.d(getApplicationContext().getClass().getName(), "\n"
                + " время: " + new Date() + "\n+" +
                " Класс в процессе... " + this.getClass().getName() + "\n" +
                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                + "   starting... onRestart" + " starting... onRestart");
    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }























    // TODO: 22.01.2024 END
}

