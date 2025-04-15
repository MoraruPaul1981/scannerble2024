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
import com.dsy.dsu.BootAndAsync.Model.LaunchActivityFragmentBoot;
import com.dsy.dsu.BootAndAsync.Model.ModuleSingleWorkManager.ModuleSingleWorkManager;
import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentActivityBootService;


import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentPrograssbar;
import com.dsy.dsu.BusinessLogicAll.Permissions.GrandPermissions;
import com.dsy.dsu.CoreApp.Model.BunessLogicCoreApp;
import com.dsy.dsu.Dashboard.Model.bl_launchFragmentSettingsandDashbord.LaunchActivityDashboard;
import com.dsy.dsu.Dashboard.Model.endingasynsdashboard.LaunchMainAppAfterSyncing;
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
    // TODO: 15.04.2025
    
    private  FragmentManager fragmentManagerBoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_bootandasync_prograssbar);
            getSupportActionBar().hide(); ///скрывать тул бар
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            // TODO: 15.04.2025 use code s

            fragmentManagerBoot = getSupportFragmentManager();
            // TODO: 04.10.2023 разрешения для всего
            GrandPermissions grandPermissions=   new GrandPermissions(this );
            grandPermissions.checkPermissions();


            // TODO   запускам бизнес логику CoreApp
            new BunessLogicCoreApp(getApplicationContext()).getBunessLogicCoreApp();

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
            LaunchActivityFragmentBoot launchActivityDashboard=new LaunchActivityFragmentBoot( fragmentManagerBoot,getApplicationContext());
            // TODO: 27.03.2024 в зависомсти кто вызвает
            launchActivityDashboard.     launchBootFragment();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // TODO: 22.01.2024 END
}

