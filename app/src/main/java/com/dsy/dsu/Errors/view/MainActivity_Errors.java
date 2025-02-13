package com.dsy.dsu.Errors.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.dsy.dsu.BootAndAsync.Window.MainActivityBootAndAsync;
import com.dsy.dsu.BusinessLogicAll.Class_Sendiing_Errors;
import com.dsy.dsu.BusinessLogicAll.Permissions.ClassPermissions;
import com.dsy.dsu.Dashboard.View.MainActivity_Dashboard;
import com.dsy.dsu.Errors.controller.BiccessLogicActivityError;

import com.dsy.dsu.Errors.controller.GettingErrorsIsFileOrIsCursor;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.Sqlitehilt.HiltInterfacesqlite;
import com.dsy.dsu.R;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.google.android.material.button.MaterialButton;
import com.sous.backasync.launch.ModuleQuety;


import org.jetbrains.annotations.NotNull;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.EntryPoints;
import dagger.hilt.android.AndroidEntryPoint;


//вывод данных на Автивити

@AndroidEntryPoint
public class MainActivity_Errors extends AppCompatActivity  {
    private  TextView textViewAllError;
    private  TextView textViewHeaderErrors;
    private  MaterialButton materialButtonОтправка;
    private SharedPreferences preferences;

    private MaterialButton imageViewBack;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static final int CAMERA_PERSSION_CODE=1;
    public static final int ALL_PERSSION_CODE=1;
    private  BiccessLogicActivityError biccessLogicActivityError;

  private Activity activity;

  private  String СтатусЗадачи;
    private  ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;
    SQLiteDatabase sqLiteDatabase_error;

    @Inject
    ModuleQuety moduleQuety;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
                super.onCreate(savedInstanceState);

            // TODO: 12.12.2023
            activity=this;
            setContentView(R.layout.activitymain_errors); ///activitymain_viewlogin  /// fragment_dashboard
            getSupportActionBar().hide(); ///скрывать тул бар

            // TODO: 04.10.2023 разрешения для всего
            new ClassPermissions(this,ALL_PERSSION_CODE);

            cameBoundServiceUpdatePO();

// Storage Permissions
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE };
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            textViewAllError = (TextView) findViewById(R.id.textViewAllError);
            textViewHeaderErrors = (TextView) findViewById(R.id.textViewHeaderErrors);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            materialButtonОтправка = (MaterialButton) findViewById(R.id.materialButtonОтправка);
            preferences=   getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            imageViewBack = (MaterialButton) findViewById(R.id.imageViewBack);
            materialButtonОтправка.setClickable(false);
            materialButtonОтправка.setFocusable(false);



            // TODO: 17.01.2025
              sqLiteDatabase_error = EntryPoints.get(getApplicationContext(), HiltInterfacesqlite.class).getHiltSqlite();

            // TODO: 22.09.2023  exit error fragment
            методBackInError();

            // TODO: 12.12.2023  staring biscce logic
            biccessLogicActivityError=new BiccessLogicActivityError( getApplicationContext(),sqLiteDatabase_error);


            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
             // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{

           // TODO: 17.01.2025  Получаем Ошибку двумя разными способами из файла и из курсора
           //StringBuffer БуерДляОшибокИзФайла =     new GettingBackErrorsIsFileOrIsCursor(getApplicationContext()).gettingErrorsIsFile();
           StringBuffer БуерДляОшибокИзКурсора =     new GettingErrorsIsFileOrIsCursor(getApplicationContext(), moduleQuety).gettingErrorsIsCursor();

            // TODO: 17.01.2025  полученные ошибку отправляем на экран ПОльзователю
              metodProssecingErrorsAll(БуерДляОшибокИзКурсора);
            // TODO: 12.12.2023  Данные ОШибки*/


        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }


    }

    private void cameBoundServiceUpdatePO() {

        try{
        СтатусЗадачи=getIntent().getAction();

        if (getIntent().getExtras()!=null) {
            localBinderОбновлениеПО =
                    (ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО) getIntent().getExtras().getBinder("callbackbinderdashbord");
        }

        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "localBinderОбновлениеПО "+localBinderОбновлениеПО);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
        if(requestCode == 11){
            Toast.makeText(this, "Отправляем...", Toast.LENGTH_LONG).show();
            // TODO: 22.09.2023  после оптавление ОШИБОК  
           // TODO: 28.06.2023 очищаем таблиц
            biccessLogicActivityError.   МетодУдаланиеОшибок(  );
            // TODO: 22.09.2023  exit error fragment
             metodCallBackkFragemtSettings();
        }

        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }

    private void metodProssecingErrorsAll(@NonNull   StringBuffer БуерДляОшибок) {
        try{

            if (БуерДляОшибок.length()>0) {

                  metodSendErrorsToMail(БуерДляОшибок);
                  metodScreenErrorForUsers(БуерДляОшибок);
                   metodButtonEnables();



            } else {

               metodInfoPhone();

            metodScreenDontErrorForUsers();

                metodButtonINVISIBLEs();
            }

            metodInfoPhone( );


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString()  + "БуерДляОшибок " +БуерДляОшибок);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }
    private void metodCallBackkFragemtSettings() {
        try{
            // TODO Запусукаем Фргамент НАстройки  dashbord


            Intent Интент_ЗапускаетDashboard = new Intent();
            Интент_ЗапускаетDashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Интент_ЗапускаетDashboard.setAction("MainActivity_Dashboard.class");
            Интент_ЗапускаетDashboard.setClass(getApplicationContext(), MainActivity_Dashboard.class);

            Bundle bundleBinderUpdate=new Bundle();
            bundleBinderUpdate.putBoolean("CallBackFromMainActivity_Errors", true);
            bundleBinderUpdate.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
            Интент_ЗапускаетDashboard.putExtras(bundleBinderUpdate);

            Интент_ЗапускаетDashboard.putExtras(bundleBinderUpdate);
            activity.  startActivity(Интент_ЗапускаетDashboard);//tso*/

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    private void metodCallBackkFragemtBoot() {
        try{
            // TODO Запусукаем Фргамент НАстройки  dashbord
            Intent IntentStartFaceApp = new Intent();
            IntentStartFaceApp.setClass(getApplication(), MainActivityBootAndAsync.class);
            IntentStartFaceApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);/// FLAG_ACTIVITY_SINGLE_TOP
            startActivity(IntentStartFaceApp);


            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private String metodInfoPhone() {
        String ИнфоТелефон=null;
        try{
            ИнфоТелефон = Build.MANUFACTURER
                    + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                    + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " ИнфоТелефон   " +ИнфоТелефон);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return ИнфоТелефон;
    }


    private void metodScreenErrorForUsers(@NotNull StringBuffer stringBufferError) {
        try{
            textViewAllError.setText(stringBufferError.toString());

            textViewAllError.requestLayout();
            textViewAllError.refreshDrawableState();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    private void metodButtonEnables( ) {
        try{
            materialButtonОтправка.setVisibility(View.VISIBLE);
            materialButtonОтправка.requestLayout();
            materialButtonОтправка.refreshDrawableState();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void metodButtonINVISIBLEs( ) {
        try{
            materialButtonОтправка.setVisibility(View.INVISIBLE);
            materialButtonОтправка.requestLayout();
            materialButtonОтправка.refreshDrawableState();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    private void metodScreenDontErrorForUsers( ) {
        try{
            textViewAllError.setText("Нет ошибок !!! ");

            textViewAllError.requestLayout();
            textViewAllError.refreshDrawableState();
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }




    // TODO: 12.12.2023 метод Посылаешь данные на Почту
    protected void metodSendErrorsToMail(@NonNull  StringBuffer БуерДляОшибок ) {
        materialButtonОтправка.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //TODO полывоаем ошибки на почту
                    Vibrator v2 = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v2.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v2.vibrate(50);
                    }
                    // TODO: 06.07.2023  оправлем ощибку на почту
                    МетодПосылаемОшибкиНапочту(БуерДляОшибок);

                    Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                            this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
///////
                }
            }
        });



    }

    protected void МетодПосылаемОшибкиНапочту(@NonNull StringBuffer БуерДляОшибок) {
        try{
            Integer   ПубличноеID  = preferences.getInt("ПубличноеID",0);
            БуерДляОшибок.append("\n")
                    .append(" текущий пользователь : ").append("\n")
                    .append(ПубличноеID).append("\n")
                    .append(" время отправки: ").append("\n")
                    .append(new Date())
                    .append("\n");
            // TODO: 06.07.2023  оправлем ощибки на ПОЧТУ
            // TODO: 06.07.2023  оправлем ощибки на ПРЧТУ
            new Class_Sendiing_Errors(getApplicationContext())
                    .МетодПослываемОшибкиАдминистаторуПо(БуерДляОшибок,activity,ПубличноеID );

            Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }
    private void методBackInError() {
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (    СтатусЗадачи.equalsIgnoreCase("com.CallBackSettingsFragment")) {
                        metodCallBackkFragemtSettings();

                    } else     if (    СтатусЗадачи.equalsIgnoreCase("com.CallBackBootAndAsync")) {

                        metodCallBackkFragemtBoot();
                    }

                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                            + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    // TODO: 01.09.2021 метод вызова
                    new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                            this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }
        });

    }

}// TODO конец public class MainActivity_Recyclerview extends AppCompatActivity {
