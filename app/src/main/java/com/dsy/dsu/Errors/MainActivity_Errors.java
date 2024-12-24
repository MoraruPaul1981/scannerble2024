package com.dsy.dsu.Errors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.BootAndAsync.Window.MainActivityBootAndAsync;
import com.dsy.dsu.BusinessLogicAll.Class_Sendiing_Errors;
import com.dsy.dsu.BusinessLogicAll.Permissions.ClassPermissions;
import com.dsy.dsu.Dashboard.View.MainActivity_Dashboard;
import com.dsy.dsu.R;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import dagger.hilt.android.AndroidEntryPoint;


//вывод данных на Автивити

@AndroidEntryPoint
public class MainActivity_Errors extends AppCompatActivity  {
    private SQLiteDatabase sqLiteDatabase ;
    private  TextView textViewAllError;
    private  TextView textViewHeaderErrors;
    private  MaterialButton materialButtonОтправка;
    private SharedPreferences preferences;
    private String fileName = "Sous-Avtodor-ERROR.txt";
    private   String patchFileName="SousAvtoFile";
    private MaterialButton imageViewBack;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static final int CAMERA_PERSSION_CODE=1;
    public static final int ALL_PERSSION_CODE=1;
    private  BiccessLogicActivityError biccessLogicActivityError;

  private   File fileNewPhotoFromCameraX;

  private Activity activity;

  private  String СтатусЗадачи;
    private  ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
                super.onCreate(savedInstanceState);
            // TODO: 12.12.2023
            activity=this;
            setContentView(R.layout.activitymain_errors); ///activitymain_viewlogin  /// fragment_dashboard
            getSupportActionBar().hide(); ///скрывать тул бар
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            textViewAllError = (TextView) findViewById(R.id.textViewAllError);
            textViewHeaderErrors = (TextView) findViewById(R.id.textViewHeaderErrors);
            sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
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
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
             // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
        // TODO: 04.10.2023 разрешения для всего
        new ClassPermissions(this,ALL_PERSSION_CODE,CAMERA_PERSSION_CODE);

            getDataForMainErrors();

            // TODO: 12.12.2023  staring biscce logic
            biccessLogicActivityError=new BiccessLogicActivityError();

            // TODO: 22.09.2023  exit error fragment
            biccessLogicActivityError.   методBackInError();

            biccessLogicActivityError.    metodProssecingErrorsAll();
            // TODO: 12.12.2023  Данные ОШибки

        // TODO: 17.04.2023
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }


    }

    private void getDataForMainErrors() {
        СтатусЗадачи=getIntent().getAction();

        localBinderОбновлениеПО =
                (ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО) getIntent().getExtras().getBinder("callbackbinderdashbord");
    }


    @Override
    public void setTheme(@Nullable Resources.Theme theme) {
        super.setTheme(theme);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
        if(requestCode == 11){
            Toast.makeText(this, "Отправляем...", Toast.LENGTH_LONG).show();
            // TODO: 22.09.2023  после оптавление ОШИБОК  
           // TODO: 28.06.2023 очищаем таблиц
            biccessLogicActivityError.   МетодУдаланиеОшибок(sqLiteDatabase );
            // TODO: 22.09.2023  exit error fragment
            biccessLogicActivityError.    metodCallBackkFragemtSettings();
        }
        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


    // TODO: 12.12.2023  Бизнес ологика активти с ошибками

    class  BiccessLogicActivityError{

        // TODO: 28.06.2023 Запись Ошибков
        private void МетодУдаланиеОшибок(
                @NonNull SQLiteDatabase create_database_error) throws ExecutionException, InterruptedException {
            try {
                CompletableFuture.supplyAsync(new Supplier<Object>() {
                            @Override
                            public Object get() {
                                if (!create_database_error.inTransaction()) {
                                    create_database_error.beginTransaction();
                                }
                                create_database_error.execSQL("DELETE FROM errordsu1 ");

                                // TODO: 22.09.2023


// TODO: 17.04.2023
                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );

                                create_database_error.setTransactionSuccessful();

                                if (create_database_error.inTransaction()) {
                                    create_database_error.endTransaction();
                                }
                                return create_database_error;
                            }
                        }).thenRun(new Runnable() {
                            @Override
                            public void run() {
                                // TODO: 22.09.2023 удалаляем данные
                                методЧистимФайлсОшибкамиErrors();
                                // TODO: 17.04.2023
                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                            }
                        })
                        .exceptionally(throwable -> {
                            throwable.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(throwable.toString(),
                                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                            return  null;
                        })
                        .complete(null);

            } catch (Exception e) {
                e.printStackTrace();
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }

        private void методЧистимФайлсОшибкамиErrors() {
            try    {
                fileNewPhotoFromCameraX = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        File.separator+patchFileName +File.separator+ fileName);

                if (fileNewPhotoFromCameraX.exists()) {
                    BufferedWriter bf = Files.newBufferedWriter(Paths.get(fileNewPhotoFromCameraX.getPath()),
                            StandardOpenOption.TRUNCATE_EXISTING);

                    bf.flush();
                    bf.close();
                }

                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );


            } catch (IOException e) {
                e.printStackTrace();
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
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
                        new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            });

        }

        private void metodProssecingErrorsAll() {
            try{

                Message message=Message.obtain(new Handler() , new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 17.04.2023
                        Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                    }
                });



                CompletableFuture
                        .supplyAsync(()->biccessLogicActivityError.   metodGetDataFotmFileErrorTxt())
                        .exceptionally(throwable ->biccessLogicActivityError.  ErrorHadler(throwable))
                        .thenAcceptAsync(new Consumer<StringBuffer>() {
                            @Override
                            public void accept(StringBuffer stringBufferEror) {

                                message.getTarget().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (stringBufferEror.length()>0) {

                                            biccessLogicActivityError. metodSendErrorsToMail(stringBufferEror);

                                            biccessLogicActivityError. metodInfoPhone(stringBufferEror);

                                            biccessLogicActivityError.    metodScreenErrorForUsers(stringBufferEror);

                                            biccessLogicActivityError.    metodButtonEnables();
                                        } else {
                                            biccessLogicActivityError.  metodInfoPhone(stringBufferEror);
                                            biccessLogicActivityError.    metodScreenDontErrorForUsers(stringBufferEror);
                                        }

                                        biccessLogicActivityError.    metodReebotNameErros(stringBufferEror);
                                    }
                                });

                            }
                        }).exceptionally(throwable -> {
                            biccessLogicActivityError.  ErrorHadler(throwable);
                            return null;
                        });



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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
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

         /*       // TODO Запусукаем Фргамент НАстройки  dashbord
                // TODO Запусукаем Фргамент НАстройки  dashbord
                DashboardFragmentSettings dashboardFragmentSettings = DashboardFragmentSettings.newInstance();
                Bundle data=new Bundle();
                dashboardFragmentSettings.setArguments(data);
                fragmentTransaction.remove(dashboardFragmentSettings);
                String fragmentNewImageNameaddToBackStack=   dashboardFragmentSettings.getClass().getName();
                Fragment FragmentУжеЕСтьИлиНЕт=     fragmentManager.findFragmentByTag(fragmentNewImageNameaddToBackStack);
                fragmentTransaction.addToBackStack(null);
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.commit();
                if (FragmentУжеЕСтьИлиНЕт==null) {
                    dashboardFragmentSettings.show(fragmentManager, "DashboardFragmentSettings");
                    // TODO: 01.08.2023
                }*/
               // TODO: 27.03.2024







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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }



        protected StringBuffer metodGetDataFotmFileErrorTxt()   {
            StringBuffer БуерДляОшибок =new StringBuffer();
            try{

                // TODO: 11.12.2023  для android 11++
                String NameNewPhotosCamerax="Sous-Avtodor-ERROR"+".txt";
                fileNewPhotoFromCameraX = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        +File.separator+patchFileName + File.separator+NameNewPhotosCamerax);

                File fileNewPhotoDirectory= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        +File.separator+patchFileName  );


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    // TODO: 27.09.2023  BUFFER
                    if (fileNewPhotoDirectory.isDirectory() && fileNewPhotoFromCameraX.exists()) {
                        Uri address = FileProvider.getUriForFile(getApplicationContext(), "com.dsy.dsu.provider", fileNewPhotoFromCameraX);
                        final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(address);

                        Log.d(this.getClass().getName(),  " date " +new Date().toGMTString().toString()   );

                        BufferedReader r = new BufferedReader(new InputStreamReader(imageStream, StandardCharsets.UTF_16));

                        String    lineErrorsAll=null;
                        while ((lineErrorsAll = r.readLine()) != null) {
                            БуерДляОшибок.append(lineErrorsAll);
                            БуерДляОшибок.append('\n');
                            Log.d(this.getClass().getName(), "line " +lineErrorsAll  );
                        }

                    }
                    Log.d(this.getClass().getName(),  " date " +new Date().toGMTString().toString()   );




                    // TODO: 11.12.2023  для android 10
                }else {
                    // TODO: 11.12.2023  для android 10
                    BufferedReader newBufferedReader = Files.newBufferedReader(Paths.get(fileNewPhotoFromCameraX.getPath()), StandardCharsets.UTF_16);
                    String lineErrorsAll = null;
                    while ((lineErrorsAll = newBufferedReader.readLine()) != null) {
                        БуерДляОшибок.append(lineErrorsAll);
                        БуерДляОшибок.append('\n');
                        Log.d(this.getClass().getName(), "line " + lineErrorsAll);
                    }

                }
                Log.d(this.getClass().getName(),  " date " +new Date().toGMTString().toString() + " БуерДляОшибок "  +БуерДляОшибок);
                Log.d(this.getClass().getName(),  " date " +new Date().toGMTString().toString() + " preferences " +preferences.getAll());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                        + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.09.2021 метод вызова
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

            return  БуерДляОшибок;
        }






        @SuppressLint("SuspiciousIndentation")
        private String metodInfoPhone(@NotNull StringBuffer stringBufferEror) {
            String ИнфоТелефон=null;
            try{
              ИнфоТелефон = Build.MANUFACTURER
                        + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                        + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
                stringBufferEror.append(ИнфоТелефон);

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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
        private void metodButtonEnables( ) {
            try{
                materialButtonОтправка.setClickable(true);
                materialButtonОтправка.setFocusable(true);
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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }





        private void metodScreenDontErrorForUsers(@NotNull StringBuffer stringBufferError) {
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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
        private void metodReebotNameErros(@NotNull StringBuffer stringBufferError) {
            try{
                int blockCount = stringBufferError.toString().split("Line").length-1 ;
                textViewHeaderErrors.setText(null);
                textViewHeaderErrors.setText("Ошибки "+"("+blockCount+")");

                textViewHeaderErrors.requestLayout();
                textViewHeaderErrors.refreshDrawableState();
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
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }


        StringBuffer ErrorHadler(Throwable throwable){
            Exception e=new Exception(throwable);
            Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());

        return new StringBuffer();
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
                        new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
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
                        .МетодПослываемОшибкиАдминистаторуПо(БуерДляОшибок,activity,ПубличноеID,   sqLiteDatabase );

                Log.d(this.getClass().getName(), " Ошибок Нет. время :   " +new Date().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                        + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.09.2021 метод вызова
                new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }

        }


    }


}// TODO конец public class MainActivity_Recyclerview extends AppCompatActivity {
