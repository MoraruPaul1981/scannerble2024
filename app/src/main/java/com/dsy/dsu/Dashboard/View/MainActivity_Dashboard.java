package com.dsy.dsu.Dashboard.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusAyns;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.BroadcastRecievers.Bl.RegisterBroadcastForWorkManager;
import com.dsy.dsu.BusinessLogicAll.Permissions.ClassPermissions;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentSettings;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.EventBus.EventBuss;
import com.dsy.dsu.FirebaseAndOneSignal.OneSignal.StartigOneSignal.GetStartingRegistraziyOneSIgnalAndFireBase;
import com.dsy.dsu.Hilt.JbossAdrress.qualifiers.QualifierJbossServer3;
import com.dsy.dsu.Hilt.getSSLSocketFactory2.QualifiergetsslSocketFactory2;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;
import com.dsy.dsu.R;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity_Dashboard extends AppCompatActivity {

    @Inject
    SQLiteDatabase sqlite;

    @Inject
    RegisterBroadcastForWorkManager registerBroadcastForWorkManager;

    @Inject
    GetStartingRegistraziyOneSIgnalAndFireBase getStartingRegistraziyOneSIgnalAndFireBase;

    private   Activity activity;
    private ScrollView scrollview_dashboard;
    private  BuniccessLogicaActivityDashboard buniccessLogicaActivityDashboard;
    private  Handler handlerAsync;
    public static final int ALL_PERSSION_CODE=1;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SharedPreferences preferences;

    @Inject
    @QualifierJbossServer3
    public LinkedHashMap<Integer,String> getHiltPortJboss;


    EventBuss eventBuss;


    @Inject
    @QualifiergetsslSocketFactory2
    SSLSocketFactory getsslSocketFactory2;


    // TODO: 03.11.2022 FaceApp
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_dashboard);
            scrollview_dashboard = (ScrollView) findViewById(R.id.scrollview_dashboard); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            activity = this;
            getSupportActionBar().hide();
            fragmentManager =  getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            preferences=   getApplicationContext() .getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            // TODO: 04.10.2023 разрешения для всего
            new ClassPermissions(this,ALL_PERSSION_CODE);
            // TODO: 15.08.2023 Начинается Пользовательский КОд
            buniccessLogicaActivityDashboard=new BuniccessLogicaActivityDashboard();
            eventBuss=new EventBuss(activity,getApplicationContext() ,getHiltPortJboss,   getsslSocketFactory2);
            buniccessLogicaActivityDashboard.     МетодИнициализацияHandler();
            // TODO: 29.09.2023 Статус Повтороной Синхрониазции
            buniccessLogicaActivityDashboard.  методЗаписываемПовторныйЭтапСинхрогниазции( );
            // TODO: 28.09.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (  !EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }



            // TODO: 27.03.2024 в зависомсти кто вызвает
       Bundle bundleMainActivityDashcBoard=    getIntent().getExtras();
            if (bundleMainActivityDashcBoard.getBoolean("CallBackMainActivityBootAndAsync")) {
                buniccessLogicaActivityDashboard.     методStartingDashboardFragment();
            }
            if (bundleMainActivityDashcBoard.getBoolean("CallBackFromMainActivity_Errors")) {
                buniccessLogicaActivityDashboard.     методStartingDashboardFragmentSettings();
            }
            buniccessLogicaActivityDashboard.  strartigWorkManger();
            buniccessLogicaActivityDashboard.  strartigOneSignal();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        try{
            if (  EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }
    }






    // TODO: 15.11.2023 test metod


    // TODO: 23.01.2024 EventBus for Async
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void EventMessageEvensBusAyns(MessageEvensBusAyns messageEvensBusAyns){
        try{
            eventBuss  .getEventBusManagerAsync(messageEvensBusAyns);
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
    @Subscribe (threadMode = ThreadMode.MAIN_ORDERED)
    public void EventMessageEvensBusUpdatePO(MessageEvensBusUpdatePO messageEvensBusUpdatePO){
        try{
            eventBuss. getEventBusUpdatePo(messageEvensBusUpdatePO);
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





















    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            int count = getSupportFragmentManager().getBackStackEntryCount();
   /*         int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getSupportFragmentManager().popBackStack();
            }
*/

            finishAndRemoveTask();
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
            Log.d(this.getClass().getName(), "  Полусаем Ошибку e.toString() " + e.toString());
        }
    }

























    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard
    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard
    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard
    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard
    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard
    // TODO: 15.08.2023 НачинаетсяБизнеЛОгика Активтив Dashboard

    public class BuniccessLogicaActivityDashboard {






        void методStartingDashboardFragment() {
            try {
                // TODO Запусукаем Фргамент DdshBoard
                DashboardFragmentMaterialDesign dashboardFragmentHarmonyOS = DashboardFragmentMaterialDesign.newInstance();
                Bundle data = new Bundle();
                dashboardFragmentHarmonyOS.setArguments(data);
                fragmentTransaction.remove(dashboardFragmentHarmonyOS);
                String fragmentNewImageNameaddToBackStack = dashboardFragmentHarmonyOS.getClass().getName();
                fragmentTransaction.addToBackStack(fragmentNewImageNameaddToBackStack)
                        .setPrimaryNavigationFragment(dashboardFragmentHarmonyOS)
                        .setReorderingAllowed(true);
                Fragment FragmentУжеЕСтьИлиНЕт = fragmentManager.findFragmentByTag(fragmentNewImageNameaddToBackStack);
                if (FragmentУжеЕСтьИлиНЕт == null) {
                    dashboardFragmentHarmonyOS.show(fragmentManager, "dashboardFragmentHarmonyOS");
                    // TODO: 01.08.2023
                }
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                        + " FragmentУжеЕСтьИлиНЕт " + FragmentУжеЕСтьИлиНЕт);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getApplicationContext().getClass().getName(),
                        "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                        this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

        }
        void методStartingDashboardFragmentSettings() {
            try {
                // TODO Запусукаем Фргамент DdshBoard
                DashboardFragmentSettings dashboardFragmentSettings = DashboardFragmentSettings.newInstance();
                Bundle data = new Bundle();
                dashboardFragmentSettings.setArguments(data);
                fragmentManager.popBackStack();
                // TODO: 10.03.2025
                fragmentTransaction.remove(dashboardFragmentSettings).commit() ;
                fragmentTransaction.setPrimaryNavigationFragment(dashboardFragmentSettings)
                        .setReorderingAllowed(true);
                // TODO: 10.03.2025
                    dashboardFragmentSettings.show(fragmentManager, "dashboardFragmentHarmonyOS");
                    // TODO: 01.08.2023
                Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getApplicationContext().getClass().getName(),
                        "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                        this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

        }




        private void МетодИнициализацияHandler() {
try{
            handlerAsync = new Handler(Looper.getMainLooper()) {


                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                }

                @Override
                public void dispatchMessage(@NonNull Message msg) {
                    super.dispatchMessage(msg);

                    Bundle bundleCallsBackAsynsService = msg.getData();


                }
            };

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        }




        private void strartigWorkManger() {
            try{

                registerBroadcastForWorkManager.statingPublicWorkMAnager(getApplicationContext());

                // TODO: 25.03.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
        private void strartigOneSignal() {
            try{

                getStartingRegistraziyOneSIgnalAndFireBase.getStartingRegistraziyOneSignalFirebase();

                // TODO: 25.03.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
        private void методЗаписываемПовторныйЭтапСинхрогниазции( ) {
            try {
                // TODO: 02.08.2023 БИЗНЕС КОД
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("РежимЗапускаСинхронизации", "ПовторныйЗапускСинхронизации");
                editor.apply();
                Log.d(getApplicationContext().getClass().getName(), "\n"
                        + " время: " + new Date()+"\n+" +
                        " Класс в процессе... " +  this.getClass().getName()+"\n"+
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.09.2021 метод вызова
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(),
                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }



        private void МетодСитемныйНастройкиЭкран() {
            try{
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                //////todo настрока экрана
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeAsUpIndicator(null);

                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                ((Activity) activity) .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                ((Activity) activity).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
                ((Activity) activity) .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                // TODO: 25.03.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }



//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic
    }//TODO END BUNIVEESS Logic //TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic//TODO END BUNIVEESS Logic




}


