package com.dsy.dsu.Tabels;

import static java.util.Locale.setDefault;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.dsy.dsu.AllDatabases.SQLTE.GetSQLiteDatabase;
import com.dsy.dsu.BusinessLogicAll.Class_GRUD_SQL_Operations;
import com.dsy.dsu.BusinessLogicAll.DATE.Class_Generation_Data;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.BusinessLogicAll.Class_Generation_UUID;
import com.dsy.dsu.BusinessLogicAll.Class_Generation_Weekend_For_Tabels;
import com.dsy.dsu.BusinessLogicAll.Class_MODEL_synchronized;
import com.dsy.dsu.BusinessLogicAll.DATE.SubClassCursorLoader;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.BusinessLogicAll.SubClassGetPublicId;
import com.dsy.dsu.BusinessLogicAll.SubClassUpVersionDATA;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentSettings;
import com.dsy.dsu.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;
@AndroidEntryPoint
public class MainActivity_New_Templates extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Spinner СпинерВыборДату;/////спинеры для создание табеляСпинерТабельДепратамент
    private String ПолученноеЗначениеИзСпинераДата; ///результат полученный из спенров
    private   String КакойКонтекст;
    private ScrollView ScrollНаАктивтиСозданныхТабелей;
    private LinearLayout LinearLayoutСозданныхТабелей;
    private LinearLayout LinearLayoutДляЛинии;
    private  boolean РежимыПросмотраДанныхЭкрана;
    private EditText ПрослойкаМеждуТабелей;
    private Configuration config;
    private Integer ГодТабелей ;
    private Integer МЕсяцТабелей  ;

    private  String ПолученноеЗначениеИзТолькоСпинераДата = "";
    private  Long MainParentUUID;

    private  Integer Position;
    private String FullNameCFO;
    private String ИмесяцвИГодСразу;
    private  String ПубличноеИмяКнопкиТабеля;
    private  View.OnLongClickListener СлушательУдаланиеСамогоТабеля;
    private  Button ШАблонвВидеКнопок = null;
    private  String ПолученныйГодДляНовогоТабеля = "";
    private  String ФинальнаяМЕсяцДляНовогоТабеля = "";

    private  int DigitalNameCFO;

    private Long CurrenrsСhildUUID;


    private  LinkedList<String> МассивДляВыбораВСпинерДата = new LinkedList<>(); //////АКАРЛИСТ ДЛЯ ПОЛУЧЕНЫЙ НОВЫХ ДАТ
    private SQLiteDatabase sqLiteDatabase ;
    private  Context КонтекстШаблоны;
    private String МесяцТабеляФиналИзВсехСотрудниковВТАбеле;
    private String ГодТабеляФиналИзВсехСотрудниковВТАбел;
    private  Button КнопкаНазадВсеТабеля;
    private LinkedList СодержимоеКурсораUUIDТабеляПриУдалениеиТАбеляилиВместеССотрудником = new LinkedList();
    private Integer ПубличноеIDПолученныйИзСервлетаДляUUID = 0;
    private  Cursor Курсор_ЗагружаетАрайдистЗначенийНовогоШАБЛОНА;
    private  PUBLIC_CONTENT Class_Engine_SQLГдеНаходитьсяМенеджерПотоков = null;
    private  TextView TExtvieeСловоТабельВсегоШАблонов;
    private ProgressDialog progressDialog ;
    private  FloatingActionButton КруглаяКнопкаСозданиеНовогоТабеля;
    private  Activity activity;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main__templates_tabely);
        КонтекстШаблоны = this;
        activity=this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Class_Engine_SQLГдеНаходитьсяМенеджерПотоков = new PUBLIC_CONTENT(getApplicationContext());
        getSupportActionBar().hide(); ///скрывать тул бар
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            fragmentManager =   getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

        /////
        ScrollНаАктивтиСозданныхТабелей = (ScrollView) findViewById(R.id.ScrollViewListTabels); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
        LinearLayoutСозданныхТабелей = (LinearLayout) findViewById(R.id.ГлавныйКонтейнерТабель); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
        КнопкаНазадВсеТабеля = findViewById(R.id.КонопкаНазадСтрелкаВсеТабеля);
        TExtvieeСловоТабельВсегоШАблонов = (TextView) findViewById(R.id.textView3СловоТабель);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        ///////TODO
            sqLiteDatabase=    GetSQLiteDatabase.SqliteDatabase();
        // Locale locale = Locale.ROOT;
        Locale locale = new Locale("rus");
        setDefault(locale);
        config =
                getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        createConfigurationContext(config);
        ///////// todo Круглая Кнопка
       КруглаяКнопкаСозданиеНовогоТабеля = findViewById(R.id.КруглаяКнопкаСамТабель);//////КНОПКА СОЗДАНИЕ НОВГО ТАБЕЛЯ ИЗ ИСТОРИИ ВТОРОЙ ШАГ СОЗДАНИЯ ТАБЕЛЯ СНАЧАЛА ИСТРОИЯ ПОТОМ НА БАЗЕ ЕГО СОЗЗДАНИЕ

            методActivityNewTemples();

            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   );
        } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        // TODO: 01.09.2021 метод вызова
        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }}

    private void МетодДляКруглойКнопки() {
        КруглаяКнопкаСозданиеНовогоТабеля.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Log.d(this.getClass().getName(), "МетодСозданияНовогоШАблона()   КруглаяКнопкаСозданиеНовогоТабеля  СОЗДАНИЕ НОВГО ШАБЛОНА" );
                МетодСозданияНовогоШАблона();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                // TODO: 01.09.2021 метод вызова
                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                        this.getClass().getName(),
                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
            }
        });
    }

    private void МетодВозвратаBACKАктивти() {
        try{
            КнопкаНазадВсеТабеля.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(this.getClass().getName(), " кликнем для созданни новго сотрдника при нажатии  ");
                    методBackActivityListPeoples();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    void методActivityNewTemples() {
        try {
            ////////
            Intent ИнтентctivityNewTemples = getIntent();
            Bundle bundleAtivityNewTemples =      ИнтентctivityNewTemples.getExtras();
            if (bundleAtivityNewTemples!=null) {
                MainParentUUID=    bundleAtivityNewTemples.getLong("MainParentUUID", 0);
                Position=    bundleAtivityNewTemples.getInt("Position", 0);
                ГодТабелей=  bundleAtivityNewTemples.getInt("ГодТабелей", 0);
                МЕсяцТабелей=  bundleAtivityNewTemples.getInt("МЕсяцТабелей",0);
                DigitalNameCFO=   bundleAtivityNewTemples.getInt("DigitalNameCFO", 0);
                FullNameCFO=  bundleAtivityNewTemples.getString("FullNameCFO", "");
                ИмесяцвИГодСразу= bundleAtivityNewTemples.getString("ИмесяцвИГодСразу", "");
                CurrenrsСhildUUID= bundleAtivityNewTemples.getLong("CurrenrsСhildUUID", 0l);
            }
// TODO: 17.04.2023  //////////20.15
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //////TODO  данный код срабатывает когда произошда ошивка в базе


       /* if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            if (ns!=null) {
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(0);
            }
        }*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            МетодДляКруглойКнопки();
            МетодДляУдалениеШаблонаЕслиВнемНетСотрудников();
            МетодЗаполненияАлайЛИстаНовымМЕсцевНовогоТабеля();////метод вызаваем все созжданные ТАБЕДЯ ИЗ БАПЗЫ И ДАЛЕЕ ИХ ЗАПИСЫВАЕМ В ОБМЕН
            МетодСозданиеСпинераДляДатыНаАктивитиСозданиеИВыборТабеля();
            МетодВозвратаBACKАктивти();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    //todo метод удаления табля из проекта если внем нет сотрудников
    private void МетодДляУдалениеШаблонаЕслиВнемНетСотрудников() {
        СлушательУдаланиеСамогоТабеля = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    TextView UUIDУдаляемогоТабеля = (TextView) v;
                    Log.d(this.getClass().getName(), " UUIDУдаляемогоТабеля " + UUIDУдаляемогоТабеля.getTag());
                    String UUIDУдаляемогоТабеляКАкТекст = (String) UUIDУдаляемогоТабеля.getTag();
                    String НазваниеУдаляемогоТАбеля = (String) UUIDУдаляемогоТабеля.getText();
                    Integer НазваниеУдаляемогоТАбеляВЦифровомФормате = (Integer) UUIDУдаляемогоТабеля.getId();
                    Log.d(this.getClass().getName(), " НазваниеУдаляемогоТАбеля " + НазваниеУдаляемогоТАбеля +
                            "НазваниеУдаляемогоТАбеляВЦифровомФормате " + НазваниеУдаляемогоТАбеляВЦифровомФормате);
                    /////todo сообщением передохим вв удаления табеля
                    Long СамUUIDТабеляКакLONG = Long.valueOf(UUIDУдаляемогоТабеляКАкТекст);
                    СообщениеВыборУдлалянияТабеляИзБазы("Удаление Шаблона", "удалить  ? : " + НазваниеУдаляемогоТАбеля,
                            "Templates", СамUUIDТабеляКакLONG, НазваниеУдаляемогоТАбеля, НазваниеУдаляемогоТАбеляВЦифровомФормате);
                    v.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.textlines_tabel_row_color_green_mini_longclick));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                            + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
                return false;
            }
        };
    }
    @UiThread
    void СообщениеПредпреждаетОВыбореУдалениеСамогоТабеля(String ШабкаДиалога, String СообщениеДиалога,
                                                          String СамИндификаторUUID, String UUIDУдаляемогоТабеляКАкТекст,
                                                          String НазваниеУдаляемогоТАбеля, Integer НазваниеУдаляемогоТАбеляВЦифровомФормате) {


        Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника ");
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
//////сам вид
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(ШабкаДиалога)
                .setMessage(СообщениеДиалога)
                .setPositiveButton("ОК", null)
                .setIcon(R.drawable.icon_dsu1_tabel_info)
                .show();
/////////кнопка
        final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
            ///MessageBoxUpdate метод CLICK для DIALOBOX
            @Override
            public void onClick(View v) {
                //удаляем с экрана Диалог
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника ");
                /////todo сообщением передохим вв удаления табеля
                Long СамUUIDТабеляКакLONG = Long.valueOf(UUIDУдаляемогоТабеляКАкТекст);
                ///todo
                СообщениеВыборУдлалянияТабеляИзБазы("Удаление Шаблона", "Удалить шаблон ?. " + НазваниеУдаляемогоТАбеля,
                        "Templates", СамUUIDТабеляКакLONG, НазваниеУдаляемогоТАбеля, НазваниеУдаляемогоТАбеляВЦифровомФормате);


            }
        });
    }
//todo  конеч сообщение предупреждения удлаения табеля


    ///todo сообщение
    @UiThread
    protected void СообщениеВыборУдлалянияТабеляИзБазы(String ШабкаДиалога, String СообщениеДиалога, String ИндификаторUUID,
                                                       Long СамоЗначениеUUID, String НазваниеУдаляемогоТАбеля, Integer НазваниеУдаляемогоТАбеляВЦифровомФормате) {
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
        try {
//////сам вид
            final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle(ШабкаДиалога)
                    .setMessage(СообщениеДиалога)
                    .setPositiveButton("Да", null)
                    .setNegativeButton("Нет", null)
                    .setIcon(R.drawable.icon_dsu1_delete_customer)
                    .show();
/////////кнопка
            final Button MessageBoxУдалениеСотрудникаИзТабеля = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            MessageBoxУдалениеСотрудникаИзТабеля.setOnClickListener(new View.OnClickListener() {
                ///MessageBoxUpdate метод CLICK для DIALOBOX
                @Override
                public void onClick(View v) {
                    //удаляем с экрана Диалог
                    alertDialog.dismiss();
                    Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника " + "ИндификаторUUID " + ИндификаторUUID +
                            " СамоЗначениеUUID " + СамоЗначениеUUID + "  " + ПолученноеЗначениеИзСпинераДата);
//////todo
                    МетодУдалениеВыбраногоШаблона(СамоЗначениеUUID,ИндификаторUUID); //// TODO передаеюм UUID для Удалание
                    Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника " + "ИндификаторUUID " + ИндификаторUUID +
                            " СамоЗначениеUUID " + СамоЗначениеUUID + "  " + ПолученноеЗначениеИзСпинераДата);
                }
            });

            /////////кнопка
            final Button MessageBoxУдалениеСотрудникаИзТабеляОтмена = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            MessageBoxУдалениеСотрудникаИзТабеляОтмена.setOnClickListener(new View.OnClickListener() {
                ///MessageBoxUpdate метод CLICK для DIALOBOX
                @Override
                public void onClick(View v) {
                    //удаляем с экрана Диалог
                    alertDialog.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    // TODO: 15.02.2023 Удаление Выбраного ШАблона
    //todo ВТОРОЙ МЕТОД УДАЛЕНИЕ ДЛЯ ВЕРХНЕНЙ ТАБЛИЦЫ ТАБЕЛЬ
    private void МетодУдалениеВыбраногоШаблона(@NotNull Long ДляУдалениеUUID,
                                                         @NonNull  String ИзКакойТаблицыУдалять) {
        ArrayList<Integer> УдалениеintegerArrayList=new ArrayList<>();
        ProgressDialog progressDialogДляУдаления;
        try{
            progressDialogДляУдаления = new ProgressDialog(activity);
            progressDialogДляУдаления.setTitle("Удаление Табеля");
            progressDialogДляУдаления.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialogДляУдаления.setProgress(0);
            progressDialogДляУдаления.setCanceledOnTouchOutside(false);
            progressDialogДляУдаления.setMessage("Удалание...");
            progressDialogДляУдаления.show();
            Log.d(this.getClass().getName()," ДляУдалениеUUID " +ДляУдалениеUUID);
            Observable.range(0,1)
                    .subscribeOn(Schedulers.single())
                    .zipWith(Observable.interval(300, TimeUnit.MILLISECONDS), new BiFunction<Object, Long, Object>() {
                        @Override
                        public Object apply(Object o, Long aLong) throws Throwable {
                            Log.d(this.getClass().getName(), " o " + o+ " aLong " +aLong);
                            return aLong;
                        }
                    }).doOnNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Throwable {
                            // TODO: 22.11.2022  первая часть
                            Integer     Удаление = new Class_MODEL_synchronized(getApplicationContext()).УдалениеТолькоПустогоТабеляЧерезКонтейнерУниверсальная(ИзКакойТаблицыУдалять,
                                    "uuid", ДляУдалениеUUID);
                            Log.d(this.getClass().getName(), " ДляУдалениеUUID " + ДляУдалениеUUID);
                            if (Удаление>0) {
                                УдалениеintegerArrayList.add(Удаление);
                            }
                        }
                    })
                    .doAfterNext(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Throwable {
                            getApplicationContext().getMainExecutor().execute(()->{
                                progressDialogДляУдаления.setMessage("Удалание..."+УдалениеintegerArrayList.size()+"("+УдалениеintegerArrayList.size()+")");
                            });

                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Throwable {
                            Log.d(this.getClass().getName(), " УдалениеintegerArrayList.size() " +УдалениеintegerArrayList.size());
                            getApplicationContext().getMainExecutor().execute(()->{
                                // TODO: 15.02.2023
                                progressDialogДляУдаления.dismiss();
                                progressDialogДляУдаления.cancel();;
                            });
                        }
                    })
                    .doOnTerminate(new Action() {
                        @Override
                        public void run() throws Throwable {
                            getApplicationContext().getMainExecutor().execute(()-> {
                                onStart();
                            });
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            Log.d(this.getClass().getName(), " doOnError  МетодУдалениеСамогоТабеля  throwable " +throwable.getMessage());
                            ///метод запись ошибок в таблицу
                            Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(throwable.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    })
                    .onErrorComplete(new Predicate<Throwable>() {
                        @Override
                        public boolean test(Throwable throwable) throws Throwable {
                            Log.d(this.getClass().getName(), " onErrorComplete  МетодУдалениеСамогоТабеля  throwable " +throwable.getMessage());
                            ///метод запись ошибок в таблицу
                            Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(throwable.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            return false;
                        }
                    }).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        };
    }


    //todo метод удаление сотрудника из табеля



    Integer МетодУдалениеТабеляПриУсловииЧтоНетСотрудниковВнем(String ДляУдалениеUUID, Long СамоЗначениеUUID, String НазваниеУдаляемогоТАбеля,
                                                               StringBuffer присутстуетСотрудникВУдаляемомоТабеле,
                                                               Cursor[] курсор_КоторыйПроверяетПУстойЛиТабеля, Integer НазваниеУдаляемогоТАбеляВЦифровомФормате
            ,String НазваниеТаблицыГдеУдалитьШАБЛОН)
            throws ExecutionException, InterruptedException, TimeoutException {
        Integer РезультатУдалениеСамогоТАбеля = 0;
        try {
            Class_MODEL_synchronized classModel_synchronizedСсылкаДляУдаленияШаблона =new Class_MODEL_synchronized(getApplicationContext());
            String СодержимоеКурсора = null;
            String СодержимоеКурсораНазваниеТабеля = null;
            РезультатУдалениеСамогоТАбеля = 0;
            /////TODO КОД ПРИ ОБНОВЛЯЕМ ПРИ ТАБЕЛЯ (ВАРИАНТ ВАРИАНТ УДЯЛЯЕМ А НИЖЕ ПРОСТО ОБНОВЛЯЕМ КОЛОКУ И ВПИСЫВАЕМ уДАЛЕННЫЕ)
            РезультатУдалениеСамогоТАбеля = classModel_synchronizedСсылкаДляУдаленияШаблона.
                    УдалениеТолькоШАблонЧерезКонтейнерУниверсальная(НазваниеТаблицыГдеУдалитьШАБЛОН,
                            "uuid",
                            String.valueOf(СамоЗначениеUUID));
            Log.d(this.getClass().getName(), " РезультатУдалениеСотрудникаИзТаблея " + РезультатУдалениеСамогоТАбеля + " СамоЗначениеUUID " + СамоЗначениеUUID);
            // TODO: 28.01.2022 удаление  ВО ВТОРОЙ ТАБЛИЦЕ НИЖНЕЙ
            Integer РезультатУдалениеСамогоФИО_Шаблон = 0;
            Log.d(this.getClass().getName(), " РезультатУдалениеСотрудникаИзТаблея " + РезультатУдалениеСамогоТАбеля + " СамоЗначениеUUID " + СамоЗначениеUUID);
            if (РезультатУдалениеСамогоТАбеля > 0) {
                Log.d(this.getClass().getName(), " УСпешное удаление ШАБЛОНА ШАБЛОНА Templates  РезультатУдалениеСамогоФИО_Шаблон " + РезультатУдалениеСамогоФИО_Шаблон + " СамоЗначениеUUID " + СамоЗначениеUUID);
                /////TODO КОД ПРИ ОБНОВЛЯЕМ ПРИ ТАБЕЛЯ (ВАРИАНТ ВАРИАНТ УДЯЛЯЕМ А НИЖЕ ПРОСТО ОБНОВЛЯЕМ КОЛОКУ И ВПИСЫВАЕМ уДАЛЕННЫЕ)
                РезультатУдалениеСамогоФИО_Шаблон = classModel_synchronizedСсылкаДляУдаленияШаблона.УдалениеТолькоШАблонЧерезКонтейнерУниверсальная("fio_template",
                        "fio_template",
                        String.valueOf(СамоЗначениеUUID));
            }
            Log.d(this.getClass().getName(), " РезультатУдалениеСамогоФИО_Шаблон " + РезультатУдалениеСамогоФИО_Шаблон + " СамоЗначениеUUID " + СамоЗначениеUUID);
            ///TODO СООБЩЕНИЕ О РЕЗУЛЬТАТОВ
            if (РезультатУдалениеСамогоТАбеля > 0) {
                ///todo сообещение о успешном удалении
                Long ВстакаРезультата = Long.parseLong(String.valueOf(РезультатУдалениеСамогоТАбеля));
                Log.d(this.getClass().getName(), " УСпешное удаление ШАБЛОНА ШАБЛОНА fio_template  РезультатУдалениеСамогоФИО_Шаблон "
                        + РезультатУдалениеСамогоФИО_Шаблон + " СамоЗначениеUUID " + СамоЗначениеUUID);
            } else {
                СообщениеПослеУдаленияСамогоТАбеля("Шаблоны", "Операция удалание Шаблона не прошла ", false,
                        НазваниеУдаляемогоТАбеляВЦифровомФормате);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return РезультатУдалениеСамогоТАбеля;
    }


    ///todo  конец метода удаления третий обработчки нажатия
    ///////МЕТОД СОЗДАННИЕ СПИНЕРА

    ///todo сообщение
    @UiThread
    protected void СообщениеПослеУдаленияСотрудникаИзТабеля(String ШабкаДиалога, String СообщениеДиалога, boolean Статус, Long СамоЗначениеUUID, String ДляУдалениеUUID,
                                                            Integer НазваниеУдаляемогоТАбеляВЦифровомФормате) {
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
//////сам вид
        int Значек;
        if (Статус) {
            Значек = R.drawable.icon_dsu1_tabel_info;
        } else {
            Значек = R.drawable.icon_dsu1_delete_customer;///
        }
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(ШабкаДиалога)
                .setMessage(СообщениеДиалога)
                .setPositiveButton("Удалить", null)
                .setNegativeButton("Нет", null)
                .setIcon(Значек)
                .show();
/////////кнопка
        final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), "  ФИНАЛ после удалание сотрудуника ");

            }
        });
        final Button MessageBoxUpdateСоздатьТабельВсеравноУдлаитьВместеССотрудникамиТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        MessageBoxUpdateСоздатьТабельВсеравноУдлаитьВместеССотрудникамиТабель.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), "  ФИНАЛ после удалание сотрудуника ");
                try {
                    МетодУдалениеВсехСотрудниковВТАбеле(СамоЗначениеUUID, ДляУдалениеUUID, НазваниеУдаляемогоТАбеляВЦифровомФормате);
                } catch (Exception e) {
                    e.printStackTrace();
                    ///метод запись ошибок в таблицу
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                            Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
                try {
                    ///todo метод для удаления табеля
                    МетодДляУдалениеШаблонаЕслиВнемНетСотрудников();
                    МетодЗаполненияАлайЛИстаНовымМЕсцевНовогоТабеля();////метод вызаваем все созжданные ТАБЕДЯ ИЗ БАПЗЫ И ДАЛЕЕ ИХ ЗАПИСЫВАЕМ В ОБМЕН
                    МетодСозданиеСпинераДляДатыНаАктивитиСозданиеИВыборТабеля();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                            this.getClass().getName(),
                            Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void МетодУдалениеВсехСотрудниковВТАбеле(Long СамоЗначениеUUID, String ДляУдалениеUUID,
                                                     Integer НазваниеУдаляемогоТАбеляВЦифровомФормате) throws ExecutionException, InterruptedException, TimeoutException {
        final long[] РезультатУдалениеВсехСотрудниковСамогоТАбеля = {0};
        try {
            String СамоЗначениеUUIDДляУдаланиевсехСотрудников = null;
            Iterator<String> итераторДляУдалениеВсегоТабеля = СодержимоеКурсораUUIDТабеляПриУдалениеиТАбеляилиВместеССотрудником.iterator();
            СамоЗначениеUUIDДляУдаланиевсехСотрудников = итераторДляУдалениеВсегоТабеля.next();
            System.out.println(СамоЗначениеUUIDДляУдаланиевсехСотрудников);
            РезультатУдалениеВсехСотрудниковСамогоТАбеля[0] = new Class_MODEL_synchronized(getApplication()).
                    УдалениеТолькоПустогоТабеляЧерезКонтейнерУниверсальная("tabels", "cfo",
                            Long.parseLong(String.valueOf(НазваниеУдаляемогоТАбеляВЦифровомФормате)));
            System.out.println("РезультатУдалениеВсехСотрудниковСамогоТАбеля " + РезультатУдалениеВсехСотрудниковСамогоТАбеля[0]);
            System.out.println(РезультатУдалениеВсехСотрудниковСамогоТАбеля[0]);
            СообщениеПослеУдаленияСамогоТАбеля("Оповещение Табеля", "Успешное удалание Табеля"
                    + "\n" + " (с сотрудниками): "
                    + СодержимоеКурсораUUIDТабеляПриУдалениеиТАбеляилиВместеССотрудником.size(), true, НазваниеУдаляемогоТАбеляВЦифровомФормате);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
////todo конец фильаного сообщения о удалени самого табеля


    ///todo сообщение
    @UiThread
    protected void СообщениеПослеУдаленияСамогоТАбеля(String ШабкаДиалога, String СообщениеДиалога, boolean Статус, Integer НазваниеУдаляемогоТАбеляВЦифровомФормате) {
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
//////сам вид
        int Значек;
        if (Статус) {
            Значек = R.drawable.icon_dsu1_tabel_info;
        } else {
            Значек = R.drawable.icon_dsu1_delete_customer;
        }
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(ШабкаДиалога)
                .setMessage(СообщениеДиалога)
                .setPositiveButton("ОК", null)
                .setIcon(Значек)
                .show();
/////////кнопка
        final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
            ///MessageBoxUpdate метод CLICK для DIALOBOX

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //удаляем с экрана Диалог
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), "  ФИНАЛ после удалание сотрудуника ");
                //////// todo если успешно удаление табеля то запускаем сообщение
                if (Статус) {
                    try {
                        ///todo метод для удаления табеля
                        МетодДляУдалениеШаблонаЕслиВнемНетСотрудников();
                        МетодЗаполненияАлайЛИстаНовымМЕсцевНовогоТабеля();////метод вызаваем все созжданные ТАБЕДЯ ИЗ БАПЗЫ И ДАЛЕЕ ИХ ЗАПИСЫВАЕМ В ОБМЕН
                        МетодСозданиеСпинераДляДатыНаАктивитиСозданиеИВыборТабеля();
                        ScrollНаАктивтиСозданныхТабелей.forceLayout();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        // TODO: 01.09.2021 метод вызова
                        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                }
            }
        });
    }
////todo конец фильаного сообщения о удалени самого табеля
    private void МетодСозданиеСпинераДляДатыНаАктивитиСозданиеИВыборТабеля() {
        try {
            Log.i(this.getClass().getName(), " НАЧИНАЕМ   МетодаСозданиеТабеляИзБазы()  .....  ");
            МетодаСозданиеТабеляИзБазы(); /////МЕТОД ЗАГРУЗКИ СОЗДАННЫХ ТАБЕЛЕЙ ИЗ БАЗЫ
            Log.i(this.getClass().getName(), "  ОТРАБОТАЛ  МетодаСозданиеТабеляИзБазы() ");
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }


    ////TODO Метод Преоразует цифру в  названия месяца
    private String МетодДляПреоразванияЦифрыВНазванияМесяца(Cursor Курсор_ВЫводимМаксимальнуюДатуДляСпинера) {
        String МаксимальнаяМесяцДляСпинера;
        String МаксимальнаяГодДляСпинера;
        String МаксимальнаяНазваниеДляСпинера;
        String ПолученыеМесяцНеОбработанный = null;
        try {
            МаксимальнаяМесяцДляСпинера = Курсор_ВЫводимМаксимальнуюДатуДляСпинера.getString(0);
            Log.i(this.getClass().getName(), "МаксимальнаяМесяцДляСпинера[0] " + МаксимальнаяМесяцДляСпинера);
            МаксимальнаяГодДляСпинера = Курсор_ВЫводимМаксимальнуюДатуДляСпинера.getString(1);
            Log.i(this.getClass().getName(), "МаксимальнаяГодДляСпинера[0] " + МаксимальнаяГодДляСпинера);
            МаксимальнаяНазваниеДляСпинера = Курсор_ВЫводимМаксимальнуюДатуДляСпинера.getString(2);
            Log.i(this.getClass().getName(), " МаксимальнаяНазваниеДляСпинера[0] " + МаксимальнаяНазваниеДляСпинера);
            DateFormat df = new SimpleDateFormat("MM/yyyy");
            Date date = df.parse(МаксимальнаяМесяцДляСпинера + "/" + МаксимальнаяГодДляСпинера);
            System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
            SimpleDateFormat ПреобразованиеЦифраВНАзваниемесяца = new SimpleDateFormat("LLLL  yyyy", new Locale("ru"));
            ПолученыеМесяцНеОбработанный = ПреобразованиеЦифраВНАзваниемесяца.format(date);
            System.out.println(ПолученыеМесяцНеОбработанный);
            ПолученыеМесяцНеОбработанный = (ПолученыеМесяцНеОбработанный.substring(0, 1).toUpperCase() +
                    ПолученыеМесяцНеОбработанный.substring(1).toLowerCase());
            Log.i(this.getClass().getName(), " ПолученыеМесяцНеОбработанный " + ПолученыеМесяцНеОбработанный);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return ПолученыеМесяцНеОбработанный;
    }
    protected void МетодаСозданиеТабеляИзБазы() throws InterruptedException, ExecutionException, TimeoutException, ParseException {
        SQLiteCursor Курсор_КоторыйЗагружаетГотовыеШаблоны = null;
        SQLiteCursor Курсор_КоторыйЗагружаетГотовыеШаблоныВнутриМаксимальнаяДата = null;
        String МесяцМаскимальнаяДатавТабеляхПоМесецям = null;
        int ПолученнаяUUIDНазванияОрганизации = 0;
        try {
            try {
                LinearLayoutСозданныхТабелей.removeAllViews();
            } catch (Exception e) {
                 /*   e.printStackTrace();
                    ///метод запись ошибок в таблицу
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());*/
            }
            Log.d(this.getClass().getName(), " код загружает все созданные табеля из базы " + КакойКонтекст);
            ПолученнаяUUIDНазванияОрганизации = МетодПолучениеОрганизацииНепосрдственодляДанногоСОтрудника();
            Log.d(this.getClass().getName(), "ПолученнаяUUIDНазванияОрганизации " + ПолученнаяUUIDНазванияОрганизации);
            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ
            Class_GRUD_SQL_Operations class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ = new Class_GRUD_SQL_Operations(getApplicationContext());
            ///
            class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.concurrentHashMapНабор.put("СамFreeSQLКОд",
                    " SELECT id  FROM successlogin  ORDER BY date_update DESC ;");
            // TODO: 12.10.2021  Ссылка Менеджер Потоков
            PUBLIC_CONTENT Class_Engine_SQLГдеНаходитьсяМенеджерПотоков = new PUBLIC_CONTENT(getApplicationContext());
            ///////
            SQLiteCursor Курсор_ПолучаемПубличныйID = (SQLiteCursor) class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.
                    new GetаFreeData(getApplicationContext()).getfreedata(class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);
            if (Курсор_ПолучаемПубличныйID.getCount() > 0) {
                Курсор_ПолучаемПубличныйID.moveToFirst();
                ПубличноеIDПолученныйИзСервлетаДляUUID = Курсор_ПолучаемПубличныйID.getInt(0);
                Log.d(this.getClass().getName(), " ID  " + ПубличноеIDПолученныйИзСервлетаДляUUID);

            }
            Log.d(this.getClass().getName(), "ПолученнаяUUIDНазванияОрганизации " + ПолученнаяUUIDНазванияОрганизации
                    + " ID " + ПубличноеIDПолученныйИзСервлетаДляUUID);


            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ
            Class_GRUD_SQL_Operations class_grud_sql_operationsСозданиеТабеляИзБазы = new Class_GRUD_SQL_Operations(getApplicationContext());

            ///
            class_grud_sql_operationsСозданиеТабеляИзБазы = new Class_GRUD_SQL_Operations(getApplicationContext());

            ///
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "Templates");
            ///////
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("СтолбцыОбработки", "*");
            //
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("ФорматПосика", " status_send !=? AND user_update=? ");
            ///"_id > ?   AND _id< ?"
            //////
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("УсловиеПоиска1", "Удаленная");
            ///
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("УсловиеПоиска2", ПубличноеIDПолученныйИзСервлетаДляUUID);
            ///
                  /*  class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска3",МЕсяцДляКурсораТабелей);
                    //
                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска4",ГодДляКурсораТабелей);////УсловиеПоискаv4,........УсловиеПоискаv5 .......
*/
            ////TODO другие поля

            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("ПоляГрупировки", "name_templates");
            ////
            //class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеГрупировки",null);
            ////
            class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
            ////
            /// class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеЛимита","1");
            ////
            Курсор_КоторыйЗагружаетГотовыеШаблоны = (SQLiteCursor) class_grud_sql_operationsСозданиеТабеляИзБазы.
                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsСозданиеТабеляИзБазы.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);
            Log.d(this.getClass().getName(), "GetData " + Курсор_КоторыйЗагружаетГотовыеШаблоны);
            //////todo работающий NULL в query
            Log.d(this.getClass().getName(), " Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount() " + Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount());
            /////TODO курсор для нахождения даты максимальной
            SQLiteCursor Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата = null;
            Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата = МетодКоторыйПоказываетМаксимальнуюДатуИзменения(ПолученнаяUUIDНазванияОрганизации);
            if (Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата.getCount() > 0) {
                ////////
                Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата.moveToFirst();
                String МаскимальнаяДатавТабеляхПоМесецям = Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата.getString(0);

                МесяцМаскимальнаяДатавТабеляхПоМесецям = Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата.getString(1);
                Log.d(this.getClass().getName(), " МаскимальнаяДатавТабеляхПоМесецям  " + МаскимальнаяДатавТабеляхПоМесецям
                        + " МесяцМаскимальнаяДатавТабеляхПоМесецям  " + МесяцМаскимальнаяДатавТабеляхПоМесецям);

            } else {
                МесяцМаскимальнаяДатавТабеляхПоМесецям = "";
            }
            //TODO закрываем курсор с максимальной датой
            Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата.close();

            if (ПолученнаяUUIDНазванияОрганизации == 0) {
                МетодКогдаДанныхСамихТабелйНет();
            }
            String[] НазваниеТабеля = {""};
            String[] ДатаТабеляИзБАзы = {""};
            ////todo ЗАГРУЖАЕМ КУРСОР ПОЛУЧЕННЫЙ С ГОТОВЫМИ ТАБЕЛЯ ЗА КОНКЕРТНЫЙ МЕСЯЦ
            if (Курсор_КоторыйЗагружаетГотовыеШаблоны != null) {/////ЕСЛИ ЕСТЬХОТЯБЫ ОДИН ТАБЕЛЬ
                Log.d(this.getClass().getName(), " Курсор_КоторыйЗагружаетГотовыеШаблоны " + Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount());
            }
            if (Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount() > 0) {/////ЕСЛИ ЕСТЬХОТЯБЫ ОДИН ТАБЕЛЬ
                Log.d(this.getClass().getName(), " Курсор_КоторыйЗагружаетГотовыеШаблоны " + Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount());
                ////TODO СТАВИМ КУРСОР НА НУЖНУЮ ПОЗИЦИЮ
                Курсор_КоторыйЗагружаетГотовыеШаблоны.moveToFirst();
                Log.d(this.getClass().getName(), " Курсор_КоторыйЗагружаетГотовыеШаблоны " + Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount());
                final int[] ИндексДляСозданныхОбьектовНаАктивитиТАбель = {0};
                try {
                    LinearLayoutСозданныхТабелей.removeAllViews();/////удалем данные с актиывти
                    LinearLayoutСозданныхТабелей.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                    ///метод запись ошибок в таблицу
               /* Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());*/
                }
                String СамСтатусАтбеля;
                    TExtvieeСловоТабельВсегоШАблонов.setText(TExtvieeСловоТабельВсегоШАблонов.getText());
                ////// todo заргужаем название табелеей ЦИКЛ загружаем на активти  УЖЕ СОЗДАННЫЕ ТАБЕЛЯ И ВИД ИХ ДЕЛАЕМ КАК КНОПКА
                do {
                    Log.d(this.getClass().getName(), " Количество Строчек В табеле " + Курсор_КоторыйЗагружаетГотовыеШаблоны.getCount() + " Количество столбиков в табеле " +
                            Курсор_КоторыйЗагружаетГотовыеШаблоны.getColumnCount());
                    /////TODO  ИЗВЛЕКАМ ДАННЫЕ ИЗ КУРСОРА ТАБЕЛЬ И ЕГО НАЗВАНИЕ UUID  ИТД
                    int ИндексНазваниеТабеля = Курсор_КоторыйЗагружаетГотовыеШаблоны.getColumnIndex("name_templates");
                    НазваниеТабеля[0] = Курсор_КоторыйЗагружаетГотовыеШаблоны.getString(ИндексНазваниеТабеля);
                    int ИндексДата = Курсор_КоторыйЗагружаетГотовыеШаблоны.getColumnIndex("date_update");
                    ДатаТабеляИзБАзы[0] = Курсор_КоторыйЗагружаетГотовыеШаблоны.getString(ИндексДата);
                    //todo перерводим в дату для СРАВНЕНИЯ
                    int ИндексID = Курсор_КоторыйЗагружаетГотовыеШаблоны.getColumnIndex("id");
                    Long ЦифровоеНазваниеТабеля = Курсор_КоторыйЗагружаетГотовыеШаблоны.getLong(ИндексID);
                    Log.d(this.getClass().getName(), " НазваниеТабеля " + ЦифровоеНазваниеТабеля);
                    Log.d(this.getClass().getName(), " НазваниеТабеля " + НазваниеТабеля[0] + " ДатаТабеляИзБАзы " + ДатаТабеляИзБАзы[0]);
                    ///////// TODO добаляем кнопки
                    ШАблонвВидеКнопок = new Button(КонтекстШаблоны);////СОЗДАЕМ НОВЫЕ КНОПКИ НА АКТИВТИ
                    //////
                    ШАблонвВидеКнопок.setId(ИндексДляСозданныхОбьектовНаАктивитиТАбель[0]);
                    int ИндексГдеНаходитьсяUUIDВТАБЕЛЕ = Курсор_КоторыйЗагружаетГотовыеШаблоны.getColumnIndex("uuid");
                    ////
                    String НепостредственоеЗначениеUUIDСозданогоТАбеля = "";
                    ///////
                    if (ИндексГдеНаходитьсяUUIDВТАБЕЛЕ >= 0) {
                        // TODO: 31.01.2022
                        НепостредственоеЗначениеUUIDСозданогоТАбеля = Курсор_КоторыйЗагружаетГотовыеШаблоны.getString(ИндексГдеНаходитьсяUUIDВТАБЕЛЕ).trim();
                    }
                    ////todo когда данные в табелй есть  САМИ ДАННЫЕ ТАБЕЛЕЙ ЗАГРУЖАЮТЬСЯ
                    ШАблонвВидеКнопок.setTag(НепостредственоеЗначениеUUIDСозданогоТАбеля);
                    // TODO: 31.01.2022
                    ШАблонвВидеКнопок.setId(Integer.parseInt(String.valueOf(ЦифровоеНазваниеТабеля)));
                    // TODO: 31.01.2022
                    ШАблонвВидеКнопок.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    // TODO: 31.01.2022
                    ШАблонвВидеКнопок.setMinLines(8);
                    // TODO: 31.01.2022
                    ШАблонвВидеКнопок.setTextSize(13);
                    // TODO: 31.01.2022
                    ШАблонвВидеКнопок.setHintTextColor(Color.RED);
                    Log.w(this.getClass().getName(), " НазваниеТабеля " + НазваниеТабеля[0] + " ДатаТабеляИзБАзы " + ДатаТабеляИзБАзы[0]);
                    if (НазваниеТабеля[0] != null) {
                        ШАблонвВидеКнопок.setText(НазваниеТабеля[0]);
                    }
                    ШАблонвВидеКнопок.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    ШАблонвВидеКнопок.setTextColor(Color.BLACK);
                    ШАблонвВидеКнопок.setHintTextColor(Color.RED);
                    Drawable icon = getResources().getDrawable(R.drawable.icon_newforshablon1);
                    icon.setBounds(10, 0, 80, 60);
                    ШАблонвВидеКнопок.setCompoundDrawables(icon, null, null, null);
                    ШАблонвВидеКнопок.setBackground(getApplication().getResources().getDrawable(R.drawable.textlines_tabel_row_color_green_mini));
                    ШАблонвВидеКнопок.setWidth(100);
                    ШАблонвВидеКнопок.setOnLongClickListener(СлушательУдаланиеСамогоТабеля);
                    ШАблонвВидеКнопок.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ///
                            try {
                                Button КнопкаНАжалиНАВыбраныйШаблон = (Button) v;
                                    SQLiteCursor Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней =
                                            МетодЗаполненияТабеляИзЗаранееСозданогоШабона();
                                    Log.d(this.getClass().getName(), " Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней "
                                            + Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней);
/////TODO одинатрный клик для загрузки в этот табель всех сотрудников
                                    МетодЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель((Button) v,
                                            Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней);

                                    КнопкаНАжалиНАВыбраныйШаблон.setTextColor(Color.BLACK);
                                    КнопкаНАжалиНАВыбраныйШаблон.setHintTextColor(Color.RED);

                                    Drawable icon = getResources().getDrawable(R.mipmap.icon_dsu1_tabels_sam_tamples);
                                    icon.setBounds(10, 0, 80, 60);
                                    КнопкаНАжалиНАВыбраныйШаблон.setCompoundDrawables(icon, null, null, null);
                                    КнопкаНАжалиНАВыбраныйШаблон.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.textlines_tabel_row_color_green_mini_longclick));

                            } catch (Exception e) {
                                e.printStackTrace();
                                ///метод запись ошибок в таблицу
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                            ///  ((TextView) v).setBackgroundColor(Color.parseColor("#F5FFFA"));

                        }


                        ////////////////////////TODO event


                        ////////


                        /////TODO метод запуска кода при однократорм нажатии просто загузка сотрудников табель
                        protected void МетодЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель(
                                Button v,
                                SQLiteCursor Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней) {

                            ///
                            Class_GRUD_SQL_Operations class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель;
                            // TODO: 07.09.2021

                            SQLiteCursor Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм = null;
                            try {


                                // TODO: 12.03.2021 первое действие получаем данные данного шаблона
                                // ((TextView) v).setBackgroundColor(Color.RED);


                                Log.d(this.getClass().getName(), " Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней  "
                                        + Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней);

                                // String ПередаемСозданнуюДатуНовогоТабеля = (String) ((TextView) СпинерВыборДату.getChildAt(0)).getText();///дата нового табеля
                                ///////todo ВЫТАСКИЕВАЕМ НАЗВАНИЕ ТАБЕЛЯ
                                Button ИзКнопкиПолучаемНазваниеТабеля = v;

                                String ПередаемСозданнуюНазваниеТабеля = ИзКнопкиПолучаемНазваниеТабеля.getText().toString();

                                Log.d(this.getClass().getName(), " ПередаемСозданнуюНазваниеТабеля  " + ПередаемСозданнуюНазваниеТабеля);

                                ///////todo ВЫТАСКИЕВАЕМ НАЗВАНИЕ ТАБЕЛЯ

                                Button ИзКнопкиПолучаемUUIDТабеля = v;

                                Object ПередаваемыйИзКнопкиПолучаемUUIDТабеля = ИзКнопкиПолучаемНазваниеТабеля.getTag();

                                Log.d(this.getClass().getName(), " ПередаваемыйИзКнопкиПолучаемUUIDТабеля  " + ПередаваемыйИзКнопкиПолучаемUUIDТабеля.toString());


                                /////////TODO действие второе
                                // TODO: 12.03.2021 выше полученных курсор с данными   Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм


                                Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм = null;

                                // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ

                                ///
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель = new Class_GRUD_SQL_Operations(getApplicationContext());

                                ///
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "Fio_Template");
                                ///////
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        concurrentHashMapНабор.put("СтолбцыОбработки", "*");///fio_uuid
                                //
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        concurrentHashMapНабор.put("ФорматПосика", "fio_template=? ");
                                ///"_id > ?   AND _id< ?"

                                Long РодительскийUUIDДляПоскаДанных = Long.parseLong(ПередаваемыйИзКнопкиПолучаемUUIDТабеля.toString().trim());
                                //////
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        concurrentHashMapНабор.put("УсловиеПоиска1", РодительскийUUIDДляПоскаДанных);
                                ///
                           /*         class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска2","Удаленная");
                                    ///
                                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска3",МЕсяцДляКурсораТабелей);
                                    //
                                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска4",ГодДляКурсораТабелей);////УсловиеПоискаv4,........УсловиеПоискаv5 .......
*/
                                ////TODO другие поля

                                ///classGrudSqlOperations. concurrentHashMapНабор.put("ПоляГрупировки",null);
                                ////
                                //class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеГрупировки",null);
                                ////
                                class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
                                ////
                                /// class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеЛимита","1");
                                ////

                                // TODO: 27.08.2021  ПОЛУЧЕНИЕ ДАННЫХ ОТ КЛАССА GRUD-ОПЕРАЦИИ

                                Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм = (SQLiteCursor) class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                        new GetData(getApplicationContext()).getdata(class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                concurrentHashMapНабор,
                                        Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);
                                //////


                                Log.d(this.getClass().getName(), "GetData " + Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм);





                         /*           // TODO: 07.09.2021    _old
                                                Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм =
                                                        new Class_MODEL_synchronized(getApplicationContext()).КурсорУниверсальныйДляБазыДанных("Fio_Template",
                                                                new String[]{"fio_uuid"},//     new String[]{"name,id,uuid,BirthDate,snils},
                                                                "fio_template=? ",//AND status_send IS NULL//"Удаленная" //AND status_send!=?" /AND status_send IS NULL AND  name IS NOT NULL AND fio IS NOT NULL
                                                                new String[]{ПередаваемыйИзКнопкиПолучаемUUIDТабеля.toString()},
                                                                null, null, "date_update DESC", null);
*/





                                StringBuffer stringBuffer = new StringBuffer();

                                int lines = 0;

                                ////todo результат

                                if (Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getCount() > 0) {

                                    Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.moveToFirst();

                                    Log.d(this.getClass().getName(), "Курсор_ПолчеамДанныеУжеСозданнымШаблоном.getCount() "
                                            + Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getCount());


                                    Log.d(this.getClass().getName(), "Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getCount() "
                                            + Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getCount());


                                    // TODO: 14.03.2021
                                    //
                                    Cursor ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм = null;

                                    // TODO: 26.09.2021  КОПИРУЕМ ОДИН КУРСОР ИЗ ДРУГОВА КУРСОР АДЛЯ ВИЧЧИСЛЕНИЯ ФИО ПО UUID ИЩ РОДИТЕЛЬСКОЙ ТАБЛИЦЫ
                                    ////
                                    ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм = Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм;




                                    ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.moveToFirst();


                                    // TODO: 17.03.2021 запускам даные из курсора

                                    do {


                                        //ССылкаНаСозданнуюБазу.execSQL(" DELETE FROM tabels WHERE fio= -9223372036854775808; ");

                                        //    ССылкаНаСозданнуюБазу.execSQL(" DELETE FROM Fio_Template WHERE fio_uuid= -9223372036854775808; ");


                                        //     int g=                     ССылкаНаСозданнуюБазу.delete("Fio_Template","fio_uuid=?",new String[]{"-9223372036854775808"});


                                        Integer ИндексГдеНАходитьсяUUIDФИО = ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getColumnIndex("fio_uuid");

                                        /////
                                        Long ПолученныйUUIDДЛЯПолучениеДанных = ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getLong(ИндексГдеНАходитьсяUUIDФИО);

                                        Log.d(this.getClass().getName(), "ПолученныйUUIDДЛЯПолучениеДанных "
                                                + ПолученныйUUIDДЛЯПолучениеДанных);


                                        // TODO: 07.09.2021  резуьтат
                                        if (ПолученныйUUIDДЛЯПолучениеДанных > 0) {

                                            Cursor Курсор_ТаблицаФИО = null;
                                            ///


                                            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ

                                            ///
                                            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ

                                            ///
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель = new Class_GRUD_SQL_Operations(getApplicationContext());


                                            ///
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "fio");
                                            ///////
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    concurrentHashMapНабор.put("СтолбцыОбработки", "name,uuid");
                                            //
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    concurrentHashMapНабор.put("ФорматПосика", "uuid=? ");
                                            ///"_id > ?   AND _id< ?"
                                            //////
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    concurrentHashMapНабор.put("УсловиеПоиска1", ПолученныйUUIDДЛЯПолучениеДанных);
                                            ///
                                       /*     class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска2","Удаленная");
                                            ///
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска3",МЕсяцДляКурсораТабелей);
                                            //
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска4",ГодДляКурсораТабелей);////УсловиеПоискаv4,........УсловиеПоискаv5 .......

                                            ////TODO другие поля*//*     class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска2","Удаленная");
                                            ///
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска3",МЕсяцДляКурсораТабелей);
                                            //
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель. concurrentHashMapНабор.put("УсловиеПоиска4",ГодДляКурсораТабелей);////УсловиеПоискаv4,........УсловиеПоискаv5 .......

                                            ////TODO другие поля*/

                                            ///classGrudSqlOperations. concurrentHashMapНабор.put("ПоляГрупировки",null);
                                            ////
                                            //class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеГрупировки",null);
                                            ////
                                            class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
                                            ////
                                            /// class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеЛимита","1");
                                            ////

                                            // TODO: 27.08.2021  ПОЛУЧЕНИЕ ДАННЫХ ОТ КЛАССА GRUD-ОПЕРАЦИИ

                                            Курсор_ТаблицаФИО = (SQLiteCursor) class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsЗапускаетПереходНаЗаполенияСозданнымШАБЛОНОМВТабель.
                                                            concurrentHashMapНабор,
                                                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);


                                            //////

                                            Log.d(this.getClass().getName(), "GetData " + Курсор_ТаблицаФИО);




                                   /*         // TODO: 07.09.2021    ___old
                                           Курсор_ТаблицаФИО = new Class_MODEL_synchronized(getApplicationContext()).КурсорУниверсальныйДляБазыДанных("fio",
                                        new String[]{"name,uuid"},//     new String[]{"name,id,uuid,BirthDate,snils},
                                        "uuid=?",//AND status_send IS NULL//"Удаленная" //AND status_send!=?" /AND status_send IS NULL AND  name IS NOT NULL AND fio IS NOT NULL
                                        new String[]{String.valueOf(ПолученныйUUIDДЛЯПолучениеДанных)},
                                        null, null, "date_update DESC", null);
*/


                                            // TODO: 07.09.2021   resultat

                                            if (Курсор_ТаблицаФИО.getCount() > 0) {
                                                /////
                                                Курсор_ТаблицаФИО.moveToFirst();


                                                String ИмяПолученоИзФИо = Курсор_ТаблицаФИО.getString(0);


                                                stringBuffer.append(ИмяПолученоИзФИо).append("\n");

                                            }

                                        }

                                    } while (ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.moveToNext());
                                    /////


                                  /*  ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.close();

                                    Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.close();*/


                                    //ДляСообщениеКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.close();

                                    String yourInput = "...";

                                    Matcher m = Pattern.compile("\r\n|\r|\n").matcher(stringBuffer.toString());



                                    while (m.find()) {
                                        lines++;
                                    }
                                    lines = lines ;


                                } else {

                                    Snackbar.make(v, "В шаблоне нет сотрудников.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                    //Toast.makeText(getApplicationContext(), "в шаблоне нет сотрудников.", Toast.LENGTH_SHORT).show();
                                }









                                ((TextView) v).setBackgroundColor(Color.parseColor("#F5FFFA"));

                                // TODO: 08.10.2021 ПЕРЕХОДИМ НА СОЗДАНЕИ СОТРУДНИКОВ ИЗ ШАБЛОНА


                                // TODO: 14.03.2021 метод записываем сотрудников в табель на базе ранее созданого шаблона
                                СообщениеЗаполнениеСотрудниковИзШаблона("Шаблоны", "Заполнить Табель из Шаблона ? :"
                                                + "\n" + "\n" + stringBuffer.toString() + ":" + lines + " кол.", true,
                                        Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней,
                                        Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм, ПередаемСозданнуюНазваниеТабеля,
                                        ПередаваемыйИзКнопкиПолучаемUUIDТабеля,lines,v);


                                /////
                                ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
                            } catch (Exception e) {
                                e.printStackTrace();
                                ///метод запись ошибок в таблицу
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                                        Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                        }


                    });


                    ///todo клик по табелби преход на сотрудников
                    /////
/////


                    //////

                    if (МесяцМаскимальнаяДатавТабеляхПоМесецям.trim().equals(НазваниеТабеля[0].trim())) {


                        LinearLayoutСозданныхТабелей.addView(ШАблонвВидеКнопок, 0); /////СОЗДАЕМ НАКШИ КНОПКИ ВНУРИ СКРОЛБАР


                    } else {
                        //TODO  ОЧИЩАЕМ ПАМТЬ


                        ////////унопки распологаем внутири скролбара
                        LinearLayoutСозданныхТабелей.addView(ШАблонвВидеКнопок, ИндексДляСозданныхОбьектовНаАктивитиТАбель[0]); /////СОЗДАЕМ НАКШИ КНОПКИ ВНУРИ СКРОЛБАР


                    }
                    ИндексДляСозданныхОбьектовНаАктивитиТАбель[0]++;///увеличиваем


                    ////ТУТ НАПИСАН  КОД КОТОРЫЙ ЗАПУСКАЕТ САМ ТАБЕЛЬ ПРИ НАЖАТИИ НА СОЗДАННЫЕ КНОПКА-ТАБЕЛЬ


                    /////todo ЦИКЛ ЗАГРУЗКИ ТОЛЬКО НАЗВАНИЙ ТАБЕЛЯ
                } while (Курсор_КоторыйЗагружаетГотовыеШаблоны.moveToNext());
                /////toto ПОСЛЕ ВСТАВКИ ДАННЫХ ПЕРЕОПРЕДЕЛЯЕМ ВНЕГНИЙ ВИДЖ


///todo  когда в табеле нет сотрудников ПУСТОЙ ТАБЕЛЬ
            } else {
                МетодКогдаДанныхСамихТабелйНет();
            }

            ////TODO ПОСЛЕ ЗАПОЛЕНЕИЯ ТАБЕЛЯ В АКТИВИТИ
            LinearLayoutСозданныхТабелей.invalidate();

            LinearLayoutСозданныхТабелей.requestLayout();

            ScrollНаАктивтиСозданныхТабелей.invalidate();

            // TODO: 30.01.2022

            ScrollНаАктивтиСозданныхТабелей.requestLayout();


            ScrollНаАктивтиСозданныхТабелей.fullScroll(View.FOCUS_UP);


            ///TODO удалем из памяти курсор
            /////    Курсор_КоторыйЗагружаетГотовыеШаблоны.close();

            /////
            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    // TODO: 12.03.2021  метод записываем В КОНКРЕНТЫЙ ТАБЕЛЬ СОТРУДНИКОВ ИЗ ЗАРАНЕЕ СОЗДАНОГО ШАБЛОНА

    protected SQLiteCursor МетодЗаполненияТабеляИзЗаранееСозданогоШабона() {
        SQLiteCursor Курсор_НаОсновеПолученогоЭлектроногоНазваниеОрганицииПонемуПолучаемВсеИнформацииОТабеле = null;
        Class_GRUD_SQL_Operations class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона = new Class_GRUD_SQL_Operations(getApplicationContext());
        try {
            Log.d(this.getClass().getName(), "МетодЗаполненияТабеляИзЗаранееСозданогоШабона ");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "tabel");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("СтолбцыОбработки", "*");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("ФорматПосика", "status_send!=?  AND cfo=? ");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("УсловиеПоиска1", "Удаленная");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("УсловиеПоиска2", DigitalNameCFO);
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
            class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор.put("УсловиеЛимита", "1");
            Курсор_НаОсновеПолученогоЭлектроногоНазваниеОрганицииПонемуПолучаемВсеИнформацииОТабеле = null;
            // TODO: 27.08.2021  ПОЛУЧЕНИЕ ДАННЫХ ОТ КЛАССА GRUD-ОПЕРАЦИИ
            Курсор_НаОсновеПолученогоЭлектроногоНазваниеОрганицииПонемуПолучаемВсеИнформацииОТабеле = (SQLiteCursor) class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.
                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsЗаполненияТабеляИзЗаранееСозданогоШабона.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков, sqLiteDatabase);
            Log.d(this.getClass().getName(), "GetData " + Курсор_НаОсновеПолученогоЭлектроногоНазваниеОрганицииПонемуПолучаемВсеИнформацииОТабеле);
// TODO: 12.03.2021  мы получили даные на основании ЦИФРОВОГО ИИЕНИ ДАЛЕЕ БУДЕМ ЗАПОЛНЯТЬ ЕГО ДАННЫМИ
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
//
        return Курсор_НаОсновеПолученогоЭлектроногоНазваниеОрганицииПонемуПолучаемВсеИнформацииОТабеле;
    }


    //////TODO вычисляем максимальную дату для LISTVIEW

    SQLiteCursor МетодКоторыйПоказываетМаксимальнуюДатуИзменения(int полученнаяUUIDНазванияОрганизации)
            throws ExecutionException, InterruptedException {
        SQLiteCursor Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата = null;
        Class_GRUD_SQL_Operations class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения = new Class_GRUD_SQL_Operations(getApplicationContext());
        try {
            class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "Templates");
            class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор.put("СтолбцыОбработки", "date_update , name_templates");
            class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор.put("ФорматПосика", "   date_update = (SELECT MAX(date_update) FROM Templates)  ");
            class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор.put("ПоляГрупировки", "name_templates");
            class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор.put("УсловиеЛимита", "1");
            Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата = (SQLiteCursor) class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.
                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsторыйПоказываетМаксимальнуюДатуИзменения.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);
            Log.d(this.getClass().getName(), "GetData " + Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
//

        return Курсор_КоторыйЗагружаетГотовыеШаблоныМаксимальнаяДата;
    }

//////


    //////TODO вычисляем максимальную дату для СПИНЕРА ДЛЯ ВАДАПТЕРА AAARYADAPTER

    Cursor МетодКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера()
            throws ExecutionException, InterruptedException {
        SQLiteCursor Курсор_КоторыйЗагружаетГотовыеШАблоныМаксимальнаяДатаДляСпинера = null;
        Class_GRUD_SQL_Operations class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера = new Class_GRUD_SQL_Operations(getApplicationContext());
        try {
            class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "Templates");
            class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор.put("СтолбцыОбработки", "name_templates");
            class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор.put("ФорматПосика", " date_update = (SELECT MAX(date_update) FROM Templates) ");
            class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
            class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор.put("УсловиеЛимита", "1");
            Курсор_КоторыйЗагружаетГотовыеШАблоныМаксимальнаяДатаДляСпинера = (SQLiteCursor) class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.
                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsКоторыйПоказываетМаксимальнуюДатуИзмененияДляСпинера.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);
            Log.d(this.getClass().getName(), "GetData " + Курсор_КоторыйЗагружаетГотовыеШАблоныМаксимальнаяДатаДляСпинера);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
///////

        return Курсор_КоторыйЗагружаетГотовыеШАблоныМаксимальнаяДатаДляСпинера;
    }


    private void МетодКогдаДанныхСамихТабелйНет() {
        try {
            ШАблонвВидеКнопок = new Button(this);////СОЗДАЕМ НОВЫЕ КНОПКИ НА АКТИВТИ

            ШАблонвВидеКнопок.setTag(" * В данном месяце нет табеля * ");
            ШАблонвВидеКнопок.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            ШАблонвВидеКнопок.setMinLines(10);

            ШАблонвВидеКнопок.setTextSize(14);

            ШАблонвВидеКнопок.setText("* нет Шаблона  " + "(создайте) *");

            ШАблонвВидеКнопок.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

            ШАблонвВидеКнопок.setTextColor(Color.RED);

            ШАблонвВидеКнопок.setHintTextColor(Color.RED);
            /////

            Drawable icon = getResources().getDrawable(R.mipmap.icon_dsu1_tabels_sam_tamples);
            icon.setBounds(0, 1, 60, 60);
            //  ШАблонвВидеКнопок.setPadding (0,0, 0, 150);
            ШАблонвВидеКнопок.setCompoundDrawables(icon, null, null, null);

            ////
            ШАблонвВидеКнопок.setBackground(this.getResources().getDrawable(R.drawable.textlines_tabel_row));
            //////ДОБАЯЛЕМ СТРОЧКУ
////////унопки распологаем внутири скролбар
            ((Activity) КонтекстШаблоны).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ////////унопки распологаем внутири скролбара
                    LinearLayoutСозданныхТабелей.addView(ШАблонвВидеКнопок); /////СОЗДАЕМ НАКШИ КНОПКИ ВНУРИ СКРОЛБАРА
                }
            });

            ///////
            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    /////МЕТОД СОЗДАНИЕ ДАТЫ И КАЛЕНДАРЯ
    /////МЕТОД СОЗДАНИЕ ДАТЫ И КАЛЕНДАРЯ
    /////МЕТОД СОЗДАНИЕ ДАТЫ И КАЛЕНДАРЯ
    /////МЕТОД СОЗДАНИЕ ДАТЫ И КАЛЕНДАРЯ

    private void МетодСозданиеДиалогаКалендаряДаты() {///////метод создание календяря даты
/////TODO тут визуализикуеться КАЛЕНДАРЬ
        try {
            DatePickerDialog ДатаДляКалендаря = new DatePickerDialog(this, this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            // ДатаДляКалендаря.setTitle("Календарь");

            ДатаДляКалендаря.show();
            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    //////////
//TODO метод который и создает календарь после нажатие на кнопку ОК

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        try {
            String МесяцИзКолендаря = String.valueOf(month + 1);////ТЕКУЩИЙ МЕСЯЦ ИЗ КАЛЕНДАРЯ
            if (МесяцИзКолендаря.length() == 2) {
                ПолученноеЗначениеИзСпинераДата = dayOfMonth + "-" + МесяцИзКолендаря + "-" + year;////ДАННОЕ ЗНАЧЕНИЕ ПЕРЕДАЕМ НА ВСЕ ПРОГРАММУ В ДАЛЬНЕЙШЕМ
                Log.d(this.getClass().getName(), " ПолученноеЗначениеИзСпинераДата" + ПолученноеЗначениеИзСпинераДата);
            } else {
                ПолученноеЗначениеИзСпинераДата = dayOfMonth + "-" + "0" + МесяцИзКолендаря + "-" + year;////ДАННОЕ ЗНАЧЕНИЕ ПЕРЕДАЕМ НА ВСЕ ПРОГРАММУ В ДАЛЬНЕЙШЕМ
                Log.d(this.getClass().getName(), " ПолученноеЗначениеИзСпинераДата" + ПолученноеЗначениеИзСпинераДата);
            }

            Date ПрасингДаты = new Date();
            try {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    ПрасингДаты = new SimpleDateFormat("dd-MM-yyyy", new Locale("ru")).parse(ПолученноеЗначениеИзСпинераДата);

                } else {

                    ПрасингДаты = new java.text.SimpleDateFormat("dd-MM-yyyy", new Locale("ru")).parse(ПолученноеЗначениеИзСпинераДата);


                }

                Log.e(this.getClass().getName(), " ПрасингДаты " + ПрасингДаты.toString());


            } catch (ParseException e) {
                e.printStackTrace();
            }
            ///////получаем значение месца на руском через метод дата
            ПолученноеЗначениеИзТолькоСпинераДата = МетодПереводаНазваниеМесяцаСАнглискогоНаРУсский(ПрасингДаты);
            Log.e(this.getClass().getName(), " ПолученноеЗначениеИзТолькоСпинераДата " + ПолученноеЗначениеИзТолькоСпинераДата);
            /////вАЖНО ЗАПИСЫВАЕМ ОБРАТНО В СПИНЕР НА РАБОЧИЙ СТОЛ АКТИВТИ НАПРИМЕР НОВЫЙ МЕСЯЦ  ОКТЯБРЬ 2020 ГОДА НАПРИМЕР
            Log.e(this.getClass().getName(), "  ПолученноеЗначениеИзСпинераДата" + ПолученноеЗначениеИзСпинераДата);


/////////////ТУТ КРУТИМ ВЕСЬ КУРСОР  И ПЫТАЕМСЯ НАЙТИ ЗНАЧЕНИЕ ВНЕМ  И ПО РЕЗУЛЬТАТ ЗАПОЛЯЕМ ЕГО В STRINGBUGGER
            ////TODO ТУТ МЫ КРУТИМ ВЕСЬ СПИНЕР В КОТРЫЙ ИЗ БАЗЫ ЗАГРУЗИЛОСЬ ВСЕ СОЗДАННЫЕ МЕСЯЦА ИМЫ ПРОВЕРЕМ ЕЛСИ ТАКОМ МЕСЯЦ ЕЩН ИЛИ НЕТ
            StringBuffer ИщемУжеСозданныйМЕсяц = new StringBuffer();
            for (int ИндексСуществуюЩимМесяц = 0; ИндексСуществуюЩимМесяц < СпинерВыборДату.getCount(); ИндексСуществуюЩимМесяц++) {
                ////todo ДА ПРОСТО ЗАПОЛЯНЕМ БУФЕР УЖЕ СОЗДАННЫМИ МЕСЯЦАМИ В СПИНЕРЕ
                ИщемУжеСозданныйМЕсяц.append(СпинерВыборДату.getItemAtPosition(ИндексСуществуюЩимМесяц).toString()).append("\n");
                Log.d(this.getClass().getName(), " ИщемУжеСозданныйМЕсяц " + ИщемУжеСозданныйМЕсяц.toString() + "\n");

            }


            ///// todo ТУТ ВСТАВЛЯЕМ ММЕСЯЦА УКТОРГНО НЕТ ЕШЕ
            try {
                МетодВставкиНовогоМесяцавТабельКоторогоНет(ИщемУжеСозданныйМЕсяц);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    ////TODO СОЗДАНИЯ КАЛЕНДАРЯ С ПОЛУЧЕННЫМИ УЖЕ ДАННЫМИ
    private void МетодВставкиНовогоМесяцавТабельКоторогоНет(StringBuffer ищемУжеСозданныйМЕсяц) throws ParseException {
        try {
            Log.d(this.getClass().getName(), " ПолученноеЗначениеИзТолькоСпинераДата " + ПолученноеЗначениеИзТолькоСпинераДата);
            StringBuffer МЕсяцСЗакглавнойБуквы = new StringBuffer(ПолученноеЗначениеИзТолькоСпинераДата.toLowerCase());
            ПолученноеЗначениеИзТолькоСпинераДата = МЕсяцСЗакглавнойБуквы.substring(0, 1).toUpperCase() + МЕсяцСЗакглавнойБуквы.substring(1).toLowerCase();
            Log.d(this.getClass().getName(), " МЕсяцСЗакглавнойБуквы " + ПолученноеЗначениеИзТолькоСпинераДата);
            ContentValues АдаптерВставкаНовогоМЕсяцаИзКалендаря = new ContentValues();////контрейнер для нового табеля
            int ДляВставкиНовогоМесяцаНазвание = МетодПолучениниеНовогоМесяцДляЗАписивОднуКолонку(ФинальнаяМЕсяцДляНовогоТабеля);
            int ДляВставкиНовогоГодНазвание = МетодПолучениниеНовыйГодДляЗАписивОднуКолонку(ПолученныйГодДляНовогоТабеля);
            Intent Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория = new Intent();
            Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория.setClass(getApplication(), MainActivity_New_Tabely.class);
            Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория.putExtra("ПолученноеЗначениеИзСпинераДата", ПолученноеЗначениеИзТолькоСпинераДата);
            Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория.putExtra("ПолученныйГодДляНовогоТабеля", String.valueOf(ДляВставкиНовогоГодНазвание));
            Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория.putExtra("ФинальнаяМЕсяцДляНовогоТабеля", String.valueOf(ДляВставкиНовогоМесяцаНазвание));
            АдаптерВставкаНовогоМЕсяцаИзКалендаря.clear();//
            ПолученныйГодДляНовогоТабеля = "";
            ФинальнаяМЕсяцДляНовогоТабеля = "";
            startActivity(Интент_ЗапускСозданиеНовогоТабельногоУчетавТаблицуИстория);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }


    //TODO метод получени месяа для записи в одну колонку

    private int МетодПолучениниеМесяцДляЗАписивОднуКолонку(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        int month = 0;
        try {
            System.out.println(" " + ДатаКоторуюНадоПеревестиИзТекставЦифру + " " + ДатаКоторуюНадоПеревестиИзТекставЦифру);
            SimpleDateFormat formatмесяц = new SimpleDateFormat("LLLL  yyyy");
            Date date = formatмесяц.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру);
            Calendar calendar = Calendar.getInstance(new Locale("ru"));
            calendar.setTime(date);
            Calendar calendar2 = new GregorianCalendar();
            calendar.setTime(date);
            month = calendar.get(Calendar.MONTH) + 1;


            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return month;
    }

    //TODO метод получени месяа для записи в одну колонку

    private int МетодПолучениниеГОдДляЗАписивОднуКолонку(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        int year = 0;
        try {
            System.out.println("ДатаКоторуюНадоПеревестиИзТекставЦифру " + ДатаКоторуюНадоПеревестиИзТекставЦифру);
            SimpleDateFormat formatгод = new SimpleDateFormat("LLLL  yyyy");
            Date date = formatгод.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру);
            Calendar calendar = Calendar.getInstance(new Locale("ru"));
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);

            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return year;
    }

////TODO МЕТОД ТОЛЬКО ДЛЯ ВСТВКИ НОВОГО МЕСЯЦА и ГОД НОВЫЙ


    private int МетодПолучениниеНовогоМесяцДляЗАписивОднуКолонку(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        System.out.println(" " + ДатаКоторуюНадоПеревестиИзТекставЦифру + " " + ДатаКоторуюНадоПеревестиИзТекставЦифру);
        SimpleDateFormat formatмесяц = new SimpleDateFormat("LLLL");
        Date date = formatмесяц.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру);
        Calendar calendar = Calendar.getInstance(new Locale("ru"));
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    private int МетодПолучениниеНовыйГодДляЗАписивОднуКолонку(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        int year = 0;
        try {
            System.out.println("ДатаКоторуюНадоПеревестиИзТекставЦифру " + ДатаКоторуюНадоПеревестиИзТекставЦифру);
            SimpleDateFormat formatгод = new SimpleDateFormat("yyyy");
            Date date = formatгод.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру);
            Calendar calendar = Calendar.getInstance(new Locale("ru"));
            calendar.setTime(date);
            Calendar calendar2 = new GregorianCalendar();
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);

            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return year;
    }


    //TODO метод получени года для записи в одну колонку

    private String МетодПолучениниеГодаДляЗАписивОднуКолонку(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        String Год = null;
        try {
            int ИщемЕслиПробелВДате = ДатаКоторуюНадоПеревестиИзТекставЦифру.indexOf(" ");
            StringBuffer ИщемГод = new StringBuffer(ДатаКоторуюНадоПеревестиИзТекставЦифру);

            Год = ИщемГод.substring(ИщемЕслиПробелВДате, ИщемГод.length());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", new Locale("ru"));
            Date date = formatter.parse(Год);
            System.out.println(date);
            System.out.println(formatter.format(date));
            ///КОНЕЦ ЗАПОЛЕНИЯ ТАБЕЛЯ ИЗ ДАННЫХ
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return Год;
    }


    //TODO метод получени года для записи в одну колонку


    //////TODO МЕТОД ПЕРВЫЙ ПРИ ПРОСМОТРЕ КАКЕИ ТАБЕЛЯ ВООБЩЕ ЕСТЬ
    private void МетодЗаполненияАлайЛИстаНовымМЕсцевНовогоТабеля() throws InterruptedException, ExecutionException, TimeoutException, ParseException {
        ///////
        Курсор_ЗагружаетАрайдистЗначенийНовогоШАБЛОНА = null;
        try {
            //TODO вытаскиваем текущее название организации для ДАННОГО КОНТРЕТНОГО ПОЛЬЗОВАТЕЛЯ


            int ПолученнаяUUIDОрганизациидДляКурсораСпинераДаты = 0;

            // ID


            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ
            Class_GRUD_SQL_Operations class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ = new Class_GRUD_SQL_Operations(getApplicationContext());
            ///
            class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.concurrentHashMapНабор.put("СамFreeSQLКОд",
                    " SELECT id  FROM successlogin  ORDER BY date_update DESC ;");


            // TODO: 12.10.2021  Ссылка Менеджер Потоков

            PUBLIC_CONTENT Class_Engine_SQLГдеНаходитьсяМенеджерПотоков = new PUBLIC_CONTENT(getApplicationContext());


            ///////
            SQLiteCursor Курсор_ПолучаемПубличныйID = (SQLiteCursor) class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.
                    new GetаFreeData(getApplicationContext()).getfreedata(class_grud_sql_operationsПолучаемНаБазуUUIDфиоПолучаемИзТаблицыФИОИМЯ.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);

            if (Курсор_ПолучаемПубличныйID.getCount() > 0) {
////
                Курсор_ПолучаемПубличныйID.moveToFirst();

                /////
                ПубличноеIDПолученныйИзСервлетаДляUUID = Курсор_ПолучаемПубличныйID.getInt(0);
///


                Log.d(this.getClass().getName(), " ID  " + ПубличноеIDПолученныйИзСервлетаДляUUID);


            }


            ///
            Log.d(this.getClass().getName(), " ПолученнаяUUIDОрганизациидДляКурсораСпинераДаты " + ПолученнаяUUIDОрганизациидДляКурсораСпинераДаты);

// TODO вытасиваем даные из базы чтобы ЗАполнить спирер готовыми табелями Датами НАПРИМЕР ОКТЯРЬ 2020  ДЕКАБРЬ 2019


//////////TODO
            Курсор_ЗагружаетАрайдистЗначенийНовогоШАБЛОНА = new Class_MODEL_synchronized(getApplicationContext()).
                    МетодЗагружаетЗначенияШаблонов(ПолученнаяUUIDОрганизациидДляКурсораСпинераДаты, getApplicationContext());

//

///////////////todo закрываем курсор для заполения данными АКайлиста
            //  Курсор_ЗагружаетАрайдистЗначенийНовогоШАБЛОНА[0].close();

            ///
        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    ////СООБЩЕНИЕ ИЗ ИТОРИИИ ТАБЛЕЙ ОТПРАВЛЯЕМ СООБЩЕНЕИ И ЗНАЧЕНИЕ В ДУГОЕ АКТИВТИ О СОЗДАНИЮ НОВОГО ТАБЕЛЯ
    public String МетодПереводаНазваниеМесяцаСАнглискогоНаРУсский(Date ПрасингДаты) {
        SimpleDateFormat sdfmt = null;
        SimpleDateFormat sdfmtГод = null;
        String ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев;
        String ФинальнаяДатаДЛяОпределенияНовыйЭтоМесяцИлиНЕт;
        sdfmt = new SimpleDateFormat("LLLL", new Locale("ru"));
        ФинальнаяМЕсяцДляНовогоТабеля = sdfmt.format(ПрасингДаты);
        Log.d(this.getClass().getName(), "  ФинальнаяМЕсяцДляНовогоТабеля  " + ФинальнаяМЕсяцДляНовогоТабеля);

        ///////МЕНЯЕМ АГЛИСКИЕ НА РУСКОЕ НАЗВАНИЕМЕСЯЦА
        ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев = sdfmt.format(ПрасингДаты);////ПЕРВОНОЧАЛЬНОЕ ВИД МЕСЯЦ НА АНГЛИСКОМ
        ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев = ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев;
        Log.d(this.getClass().getName(), "  ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев  " + ДатаДляСпинераИМеняемАнглискиеНаРУскиеНазваниеМесяцев);
        ///////Добалявем год
        sdfmtГод = new SimpleDateFormat("yyyy", new Locale("ru"));
        ПолученныйГодДляНовогоТабеля = sdfmtГод.format(ПрасингДаты);
        System.out.println("  операции время :  " + sdfmtГод.format(ПрасингДаты));
        ФинальнаяДатаДЛяОпределенияНовыйЭтоМесяцИлиНЕт = ФинальнаяМЕсяцДляНовогоТабеля + "  " + ПолученныйГодДляНовогоТабеля;
        System.out.println("  ФинальнаяДатаДЛяОпределенияНовыйЭтоМесяцИлиНЕт  " + ФинальнаяДатаДЛяОпределенияНовыйЭтоМесяцИлиНЕт);
////////свич пробегаеться по названиям месяцев и перерделываем их с аглиского на русский
        return ФинальнаяДатаДЛяОпределенияНовыйЭтоМесяцИлиНЕт;
    }


    //TODO метод получени месяа для записи в одну колонку ОБРАБОТКА ДАТЫ ДЛЯ КУРСОРА НЕ НОВЫЕ ДАННЫЕ А УЖЕ СУЩЕТСВУЮЩИЕ--МЕСЯЦ

    private int МетодПолучениниеКурсораМЕсяцДата(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        String[] ДелимМЕсяцИгод = ДатаКоторуюНадоПеревестиИзТекставЦифру.split(" ");
        System.out.println(" " + ДелимМЕсяцИгод[0]);
        SimpleDateFormat formatмесяц = new SimpleDateFormat("LLLL  yyyy", new Locale("ru"));
        Date date = formatмесяц.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру.trim());
        Calendar calendar = Calendar.getInstance(new Locale("ru"));
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(new SimpleDateFormat("MMMM").format(calendar.getTime()));
        return calendar.get(Calendar.MONTH) + 1;
    }

    //TODO метод получени месяа для записи в одну колонку ОБРАБОТКА ДАТЫ ДЛЯ КУРСОРА НЕ НОВЫЕ ДАННЫЕ А УЖЕ СУЩЕТСВУЮЩИЕ--ГОД
    private int МетодПолучениниеКурсораГОДДата(String ДатаКоторуюНадоПеревестиИзТекставЦифру) throws ParseException {
        String[] ДелимМЕсяцИгод = ДатаКоторуюНадоПеревестиИзТекставЦифру.split(" ");
        System.out.println(" " + ДелимМЕсяцИгод[1]);
        SimpleDateFormat formatгод = new SimpleDateFormat("LLLL  yyyy");
        Date date = formatгод.parse(ДатаКоторуюНадоПеревестиИзТекставЦифру.trim());
        Calendar calendar = Calendar.getInstance(new Locale("ru"));
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(new SimpleDateFormat("yyyy").format(calendar.getTime()));
        return calendar.get(Calendar.YEAR);
    }
    //TODO  конец метод получени месяа для записи в одну колонку ОБРАБОТКА ДАТЫ ДЛЯ КУРСОРА НЕ НОВЫЕ ДАННЫЕ А УЖЕ СУЩЕТСВУЮЩИЕ--МЕСЯЦ


    ///todo сообщение на активти создание новго сотрудника спрашиваем нужно ли создать
    @UiThread
    protected void СообщениеСпрашиваемПользователяЧтоОнТОчноХочетьСоздатьНовыйШаблон(String ШабкаДиалога, final String СообщениеДиалога, boolean статус) {
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
        try {
//////сам вид
            final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                    .setTitle(ШабкаДиалога)
                    .setMessage(СообщениеДиалога)
                    .setPositiveButton("Да", null)
                    .setNegativeButton("Нет", null)
                    .setIcon(R.drawable.icon_dsu1_tabels_for_new_tamples)
                    .show();
/////////кнопка
            final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
                ///MessageBoxUpdate метод CLICK для DIALOBOX
                @Override
                public void onClick(View v) {
                    //удаляем с экрана Диалог
                    alertDialog.dismiss();
                    Log.d(this.getClass().getName(), " создание нового сотрудника ");
                    ///TODO создание нового ТАБЕЛЯ
                    Log.d(this.getClass().getName(), " Переход на  Шаблоны");
                    МетодСозданияНовогоШАблона();
                }
            });
/////////кнопка
            final Button MessageBoxUpdateЗАкрытьСозданиеТабеля = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            MessageBoxUpdateЗАкрытьСозданиеТабеля.setOnClickListener(new View.OnClickListener() {
                ///MessageBoxUpdate метод CLICK для DIALOBOX
                @Override
                public void onClick(View v) {
                    //удаляем с экрана Диалог
                    alertDialog.dismiss();
///запуск метода обновления через DIALOGBOX
                }
            });
            /////
            //TODO шаблоны


        } catch (Exception e) {
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    private void МетодСозданияНовогоШАблона() {
        Intent Интент_Find_Templates = new Intent();
        try {
            Интент_Find_Templates .setClass(getApplicationContext(), MainActivity_Find_Templates.class); // Т
            Bundle bundleFind_Templates=new Bundle();
            bundleFind_Templates.putLong("MainParentUUID", MainParentUUID);
            bundleFind_Templates.putInt("Position",    Position);
            bundleFind_Templates.putInt("ГодТабелей",     ГодТабелей);
            bundleFind_Templates.putInt("МЕсяцТабелей", МЕсяцТабелей);
            bundleFind_Templates.putInt("DigitalNameCFO",  DigitalNameCFO);
            bundleFind_Templates.putString("FullNameCFO", FullNameCFO);
            bundleFind_Templates.putString("ИмесяцвИГодСразу",    ИмесяцвИГодСразу);
            bundleFind_Templates.putLong("CurrenrsСhildUUID",  CurrenrsСhildUUID);
            Интент_Find_Templates .putExtras(bundleFind_Templates);
            startActivity( Интент_Find_Templates );
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " bundleFind_Templates "+bundleFind_Templates);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    ////todo метод полчение огранизации при запуске программы
    protected int МетодПолучениеОрганизацииНепосрдственодляДанногоСОтрудника()
            throws InterruptedException, ExecutionException, TimeoutException {

        /////
        int названиеорганизациидлясотркдника = 0;
        ///
        SQLiteCursor Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего = null;
        ///
        Class_GRUD_SQL_Operations class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника;
        try {
////TODO КУРСОР ПРОВЕЯЕТ ПЕРВЫЙ ЭТО ЗАПУСК ИЛИ НЕТ


            // TODO: 26.08.2021 НОВЫЙ ВЫЗОВ НОВОГО КЛАСС GRUD - ОПЕРАЦИИ
            ///
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника = new Class_GRUD_SQL_Operations(getApplicationContext());

            ///
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор.put("НазваниеОбрабоатываемойТаблицы", "SuccessLogin");
            ///////
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор.put("СтолбцыОбработки", "id");
            //
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор.put("ФорматПосика", "id IS NOT NULL");
            ///"_id > ?   AND _id< ?"
          /*          //////
                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска1",finalПолученныйUUID);
                    ///
                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска2","Удаленная");
                    ///
                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска3",МЕсяцДляКурсораТабелей);
                    //
                    class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеПоиска4",ГодДляКурсораТабелей);////УсловиеПоискаv4,........УсловиеПоискаv5 .......
*/
            ////TODO другие поля

            ///classGrudSqlOperations. concurrentHashMapНабор.put("ПоляГрупировки",null);
            ////
            //class_grud_sql_operations. concurrentHashMapНабор.put("УсловиеГрупировки",null);
            ////
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор.put("УсловиеСортировки", "date_update DESC");
            ////
            class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор.put("УсловиеЛимита", "1");
            ////

            // TODO: 27.08.2021  ПОЛУЧЕНИЕ ДАННЫХ ОТ КЛАССА GRUD-ОПЕРАЦИИ

            Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего = null;
            ////

            Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего = (SQLiteCursor) new Class_GRUD_SQL_Operations(getApplicationContext()).
                    new GetData(getApplicationContext()).getdata(class_grud_sql_operationsПолучениеОрганизацииНепосрдственодляДанногоСОтрудника.concurrentHashMapНабор,
                    Class_Engine_SQLГдеНаходитьсяМенеджерПотоков.МенеджерПотоков,  sqLiteDatabase);

            Log.d(this.getClass().getName(), "GetData " + Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего);



/*

            // TODO: 07.09.2021  _old

                        Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего =
                                new Class_MODEL_synchronized(getApplicationContext()).КурсорУниверсальныйДляБазыДанных("SuccessLogin",
                                        new String[]{"id"}, "id IS NOT NULL", null,
                                        null, null, "date_update DESC", "1");//"settings_tabels", "date_update","id=","1",null,null,null,null


*/


            // TODO: 07.09.2021  полченный результат

            if (Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего.getCount() > 0) { //TODO ЕСЛИ ДАННЫЙ UUID НЕ ПУСТОЙ ЭТО ЗНАЧИТ ЧТО ЭТОТ ТАБЕЛЬ УЖЕ СУЩЕТСВЕТ И НАМ НАДО ОБНОВИТЬ

                ////TODO ТАБЕЛЬ УЖЕ ЕСТЬ И МЫ ЕГО ОБНОЫЛЕНИЯ ПубличноеИмяНовогоТабеля
                Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего.moveToFirst();


                //////todo полученео навзаение
                названиеорганизациидлясотркдника = Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего.getInt(0);
                //

                Log.d(this.getClass().getName(), " названиеорганизациидлясотркдника " +
                        названиеорганизациидлясотркдника);

            }


            ///todo вырубаем курсор
            Курсор_КоторыйВЫгружемНазваниеОрганизацииДляЭтогоСотркдникаТекущего.close();
            //////
        } catch (Exception e) {
            //  Block of code to handle errors
            e.printStackTrace();
            ///метод запись ошибок в таблицу
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return названиеорганизациидлясотркдника;
    }


    // TODO: 14.03.2021 метод который генерирует датц для встаки


    ///todo сообщение на активти создание новго сотрудника спрашиваем нужно ли создать
    @UiThread
    protected void СообщениеЗаполнениеСотрудниковИзШаблона(String ШабкаДиалога,
                                                           final String СообщениеДиалога,
                                                           boolean статус,
                                                           SQLiteCursor Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней,
                                                           SQLiteCursor Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм,
                                                           String ПередаемСозданнуюНазваниеТабеля,
                                                           Object ПередаваемыйИзКнопкиПолучаемUUIDТабеля
            , int lines,
                                                           @NonNull TextView view) {
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ
//////сам вид
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(ШабкаДиалога)
                .setMessage(СообщениеДиалога)
                .setPositiveButton("Да", null)
                .setNegativeButton("Нет", null)
                .setNeutralButton("Ещё", null)
                .setIcon(R.drawable.icon_dsu1_tabels_for_new_tamples)
                .show();
        if (MainParentUUID==0) {
             ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        }
        final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
            ///MessageBoxUpdate метод CLICK для DIALOBOX
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), " создание нового сотрудникаКурсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм " + Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм.getCount());
                Log.d(this.getClass().getName(), " Переход на  Шаблоны");

                // TODO: 17.03.2021 запусить добадение сотрудниковвиз шабона
                try {
                    МетодСамойЗаписиСотрудниковИзРанееСозданногШаблона(Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней,
                            Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм);
                    Log.d(this.getClass().getName(), " Переход на  Шаблоны"
                            + " Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней "
                            + Курсор_КотрыйПолученИзТаблицыТабельТолькоДляПолученияНаОсновеСФОляВставкиВыходныхДней +
                            "  Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм " + Курсор_СДаннымиИзШаблонаДАнныхСозданныйПользовательм+
                            " CurrenrsСhildUUID "+CurrenrsСhildUUID);
                    // TODO: 14.02.2023
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    // TODO: 01.09.2021 метод вызова
                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                            this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }

        });

/////////кнопка
        final Button MessageBoxUpdateЗАкрытьСозданиеТабеля = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        MessageBoxUpdateЗАкрытьСозданиеТабеля.setOnClickListener(new View.OnClickListener() {
            ///MessageBoxUpdate метод CLICK для DIALOBOX
            @Override
            public void onClick(View v) {
                //удаляем с экрана Диалог
                alertDialog.dismiss();
            }
        });
        final Button MessageBoxUpdateЕщеДобавитьКШАблонуСорудников = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        MessageBoxUpdateЕщеДобавитьКШАблонуСорудников.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                  Intent  Интент_Find_Templates=new Intent(getApplicationContext(),MainActivity_Find_Templates.class);
                    Интент_Find_Templates.setClass(getApplicationContext(), MainActivity_Find_Templates.class); // Т
                    Bundle bundleFind_Templates=new Bundle();
                    bundleFind_Templates.putLong("MainParentUUID", MainParentUUID);
                    bundleFind_Templates.putInt("Position",    Position);
                    bundleFind_Templates.putInt("ГодТабелей",     ГодТабелей);
                    bundleFind_Templates.putInt("МЕсяцТабелей", МЕсяцТабелей);
                    bundleFind_Templates.putInt("DigitalNameCFO",  DigitalNameCFO);
                    bundleFind_Templates.putString("FullNameCFO", FullNameCFO);
                    bundleFind_Templates.putString("ИмесяцвИГодСразу",    ИмесяцвИГодСразу);
                    bundleFind_Templates.putString("NameAddSelfTemplate",    view.getText().toString());


                    String УжеСозданныейШаблон=view.getText().toString().trim();
                    if(УжеСозданныейШаблон!=null && УжеСозданныейШаблон.length()>0){
                        Bundle bundleПосикШаблона=new Bundle();
                        bundleПосикШаблона.putString("СамЗапрос","  SELECT  uuid   FROM  templates WHERE name_templates  =?"+";");
                        bundleПосикШаблона.putStringArray("УсловияВыборки" ,new String[]{String.valueOf(УжеСозданныейШаблон)});
                        bundleПосикШаблона.putString("Таблица","templates");
                        Cursor КурсорПосикШаблона=      (Cursor)    new SubClassCursorLoader(). CursorLoaders(getApplicationContext(), bundleПосикШаблона);
                        if(КурсорПосикШаблона.getCount()>0){
                          Long  CurrenrsYesCreateTemplates=   КурсорПосикШаблона.getLong(0);
                            bundleFind_Templates.putLong("CurrenrsYesCreateTemplates",  CurrenrsYesCreateTemplates);
                        }
                    }
                    bundleFind_Templates.putLong("CurrenrsСhildUUID",  CurrenrsСhildUUID);
                    Интент_Find_Templates .putExtras(bundleFind_Templates);
                    startActivity( Интент_Find_Templates );
                    // TODO: 17.04.2023
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                            + " bundleFind_Templates "+bundleFind_Templates  + " view " +view);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    // TODO: 01.09.2021 метод вызова
                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                            this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                            Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }
        });
    }


    void МетодСамойЗаписиСотрудниковИзРанееСозданногШаблона(@NonNull SQLiteCursor Курсор_ВыходныеДниДанные,
                                                            @NonNull  SQLiteCursor Курсор_СамиДАнные) throws InterruptedException {

     
        try {
            // TODO: 29.06.2022 Данные Пришли ДЛля Вставки Данных В шаблон Из ШАБЛОНА ГТовго

            Курсор_СамиДАнные.moveToFirst();
            Курсор_ВыходныеДниДанные.moveToFirst();
            Integer КоличествоДанных = Курсор_СамиДАнные.getCount();
            final Long[] UUIDgeneratorOpertion = {0l};
            final ContentValues[] АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель = new ContentValues[1];
            progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(" Из Шаблона");
            progressDialog.setMessage("Добавление...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(КоличествоДанных);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setProgress(0);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            Log.d(this.getClass().getName(), " onSubscribe  МетодСамойЗаписиСотрудниковИзРанееСозданногШаблона  " +Thread.currentThread().getName());

                    Observable.range(0, КоличествоДанных)
                            .subscribeOn(Schedulers.single())
                            .concatMap(i -> Observable.just(i).delay(300, TimeUnit.MILLISECONDS))
                            .onErrorComplete(new Predicate<Throwable>() {
                                @Override
                                public boolean test(Throwable throwable) throws Throwable {
                                    Log.d(this.getClass().getName(), " onErrorComplete  МетодСамойЗаписиСотрудниковИзРанееСозданногШаблона  throwable " +throwable.getMessage());
                                    ///метод запись ошибок в таблицу
                                    Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(throwable.toString(), this.getClass().getName(),
                                            Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                                    return false;
                                }
                            })
                            .doOnError(new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Throwable {
                                    Log.e(this.getClass().getName(), "Ошибка " + throwable + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                    new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(throwable.toString(), this.getClass().getName(),
                                            Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Throwable {
                                    АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0] = new ContentValues();
                                    UUIDgeneratorOpertion[0] = (Long) new Class_Generation_UUID(getApplicationContext()).МетодГенерацииUUID();
                                    ///todo из заполянем адапрет из курсора
                                    АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0] =
                                            МетодЗаполенияДаннымиДляЗаполенияТабеляИзГотовогоШаблона(UUIDgeneratorOpertion[0],Курсор_СамиДАнные);
                                    // TODO: 26.03.2021 ДОПОЛНИТЕЛЬНО ОБНУЛЯЕМ ВСЕ ТАБЕЛЯ С NULL В ФИО ЧТО БЫ ОБМЕН НЕ РУГАЛЬСЯ
                                    Log.w(this.getClass().getName(), "  АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0]  "+
                                            АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0] + " UUIDgeneratorOpertion[0]  "+ UUIDgeneratorOpertion[0] );

                                }
                            })

                            .subscribeOn(Schedulers.single())
                            .doAfterNext(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Throwable {
                                    Long   ВставкиСотрудниковИзШаблона=0l;
                                    String ТаблицаОбработкиДорбалвенИзШаблона = "data_tabels";
                                    if (АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0].size() > 0) {
                                    ВставкиСотрудниковИзШаблона = new Class_MODEL_synchronized(getApplicationContext()).
                                                ВставкаДанныхЧерезКонтейнерТолькоПриСозданииНовогоСотрудникаУниверсальная(ТаблицаОбработкиДорбалвенИзШаблона,
                                                        АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель[0]);
                                        //////TODO когда true -это значет применяеться только не вобмене  и говорит что плюс записываем изменению версии джанных
                                    }
                                    Log.d(this.getClass().getName(), "  ВставкиСотрудниковИзШаблона " + ВставкиСотрудниковИзШаблона);
// TODO: 24.05.2021 вставка если пользователь разреил атоматическую вставку выходных дней
                                    // TODO: 24.05.2021  месяц
                                    if (ВставкиСотрудниковИзШаблона > 0) {
                                        int ИндексМесяц = Курсор_ВыходныеДниДанные.getColumnIndex("month_tabels");
                                        int Месяц = Курсор_ВыходныеДниДанные.getInt(ИндексМесяц);
                                        // TODO: 24.05.2021  год
                                        int ИндексГод = Курсор_ВыходныеДниДанные.getColumnIndex("year_tabels");
                                        int Год = Курсор_ВыходныеДниДанные.getInt(ИндексГод);
                                        Integer РезультатВставкаВыходныхДНей =
                                                new Class_Generation_Weekend_For_Tabels(getApplicationContext())
                                                        .МетодТретийАвтоматическаяВставкаВыходныхДней(UUIDgeneratorOpertion[0], Год, Месяц);
                                        Log.d(this.getClass().getName(), "   РезультатВставкаВыходныхДНей  " + РезультатВставкаВыходныхДНей);
                                        // TODO: 28.01.2022 ПОВЫШАЕМ ВЕРСИЮ  В ТАБЛИЦЕ МОДИФИКАЦИИ КЛИЕНТ
                                        ((Activity) КонтекстШаблоны).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.setIndeterminate(false);
                                                progressDialog.setProgress( integer);
                                                progressDialog.setMessage("Добавление сотрудника/ов..." + integer + " (" + КоличествоДанных + ")");
                                                Log.d(this.getClass().getName(), " integer " +integer);
                                            }
                                        });
                                    }else{
                                        ((Activity) КонтекстШаблоны).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.setIndeterminate(false);
                                                progressDialog.setMessage("Cотрудник уже есть в  табеле !!!"+"\n"
                                                        +integer+ " (" + КоличествоДанных + ")");
                                                Log.d(this.getClass().getName(), " finalВставкиСотрудниковИзШаблонаinteger  "+integer);
                                            }
                                        });
                                    }
                                    // TODO: 24.04.2023 Увеличиваем Курсор
                                    Курсор_СамиДАнные.moveToNext();
                                    Log.d(this.getClass().getName(), " Курсор_СамиДАнные  "+Курсор_СамиДАнные.getCount() +
                                             "  Курсор_СамиДАнные.getPosition() " +Курсор_СамиДАнные.getPosition());
                                }

                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Throwable {
                                    // TODO: 29.06.2022 close cursor
                                    Курсор_СамиДАнные.close();
                                    Курсор_ВыходныеДниДанные.close();
                                    progressDialog.setIndeterminate(true);
                                    progressDialog.dismiss();
                                    progressDialog.cancel();
                                    ///////todo ПОСЛЕ САТВКИ УСПЕШНОЙ ЕПРЕХОДИМ НА ДРУГУЮ АКТИВТИ
                                    методBackActivityListPeoples();
                                    // TODO: 26.03.2021 ДОПОЛНИТЕЛЬНО ОБНУЛЯЕМ ВСЕ ТАБЕЛЯ С NULL В ФИО ЧТО БЫ ОБМЕН НЕ РУГАЛЬСЯ
                                    Log.w(this.getClass().getName(), " Не был вставлен СОТРУДНИК ИЗ ШАБЛОНА КАК ТАК ОН УЖЕ ЕСТЬ В ЭТОМ ТАБЕЛЕ o  ");
                                }
                            })
                            .subscribe();
            Log.d(this.getClass().getName(), "   observableВставкаИзШаблонаВТабкель  " );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }


    // TODO: 29.06.2022 ПЕРЕНОСТИМ СЮДА МЕТОД ЗАПОЕНИЯ кОНТЕЙНЕРА С ДАННЫМИ ИХЗ aSYTASK



    // TODO: 26.09.2021  ЗАПОЛНЯЕМ КОНТЕЙНЕР ДЛЯ ВСТАВКИ ИЗ ГОТОВГО ШАБЛОНА  В ТАБЕЛЬ
    private ContentValues МетодЗаполенияДаннымиДляЗаполенияТабеляИзГотовогоШаблона(@NonNull Long UUIDgeneratorOpertion,@NonNull Cursor Курсор_СамиДАнные) {
        ContentValues АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель = new ContentValues();////контрейнер для нового табеля
        try {

            // TODO: 15.02.2023 заопления даными для шаблона
            МетодЗаполениеяДаннымиСотрудникаДляШаблонаЕслиОниЕсть(АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель,  UUIDgeneratorOpertion, Курсор_СамиДАнные);
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель;
    }

    private ContentValues МетодЗаполениеяДаннымиСотрудникаДляШаблонаЕслиОниЕсть(ContentValues АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель,
                                                                                @NonNull Long UUIDgeneratorOpertion,
                                                                                @NonNull Cursor Курсор_СамиДАнные)
            throws ExecutionException, InterruptedException {
        try{
            АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("status_send", " ");
            String СгенерированованныйДатаДляВставки = new Class_Generation_Data(getApplicationContext()).ГлавнаяДатаИВремяОперацийСБазойДанных();
            АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("date_update", СгенерированованныйДатаДляВставки);
            АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("uuid_tabel", MainParentUUID);
            // TODO: 08.10.2021 повышаем версию
            Class_GRUD_SQL_Operations class_grud_sql_operationsПовышаемВерсиюДанныхПриСозданеииИзШаблонаСотрудника
                    = new Class_GRUD_SQL_Operations(getApplicationContext());
            // TODO: 18.03.2023  получаем ВЕСИЮ ДАННЫХ
            Long РезультатУвеличинаяВерсияДАныхЧата =
                    new SubClassUpVersionDATA().upVersionCurentTable(    "data_tabels",getApplicationContext());
            Log.d(this.getClass().getName(), " РезультатУвеличинаяВерсияДАныхЧата  " + РезультатУвеличинаяВерсияДАныхЧата);

            АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("current_table", РезультатУвеличинаяВерсияДАныхЧата);

            // TODO: 27.08.2021  ПОЛУЧЕНИЕ ДАННЫХ ОТ КЛАССА GRUD-ОПЕРАЦИИ
                Integer ПубличноеID=    new SubClassGetPublicId().ПубличныйID(getApplicationContext());
                АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("user_update", ПубличноеID);
                АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("uuid", UUIDgeneratorOpertion);
                Long ФИОИзАТблицы=  Курсор_СамиДАнные.getLong(Курсор_СамиДАнные.getColumnIndex("fio_uuid"));
                АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель.put("fio", ФИОИзАТблицы);
            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +  "АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель "
                    +АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return  АдаптерДляВставкиИзГотоваШаблонаВТаблицуТабель;
    }


    @UiThread
    void СообщениеСооьщаетПользовательЧТоСоздалитьНовыеСотрудниккиУспешноИлиНет(String ШабкаДиалога, String СообщениеДиалога, boolean Статус) {

        Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника ");
        ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ///////СОЗДАЕМ ДИАЛОГ ДА ИЛИ НЕТ

        int Значек;

        if (Статус == true) {
            Значек = R.drawable.icon_dsu1_tabel_info;

        } else {
            Значек = R.drawable.icon_dsu1_create_coums_rom_temples;
        }


//////сам вид
        final AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(ШабкаДиалога)
                .setMessage(СообщениеДиалога)
                .setPositiveButton("ОК", null)
                .setIcon(Значек)
                .show();
/////////кнопка
        final Button MessageBoxUpdateСоздатьТабель = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        MessageBoxUpdateСоздатьТабель.setOnClickListener(new View.OnClickListener() {
            ///MessageBoxUpdate метод CLICK для DIALOBOX
            @Override
            public void onClick(View v) {
                //удаляем с экрана Диалог
                alertDialog.dismiss();
                Log.d(this.getClass().getName(), "  ФИНАЛ создание нового сотрудника ");


                // TODO: 12.03.2021 отпрравляем данные в предыдущее активти
                ///////
                методBackActivityListPeoples();


            }


        });
    }


    private void методBackActivityListPeoples() {
        try {
            Intent ИнтентBackactivityListPeoples = null;
            if(getIntent().getAction().equalsIgnoreCase( "FromFragmentSettings.class")){


                forfardSetting();

// TODO: 17.04.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            }else{

                if (MainParentUUID>0) {
                    ИнтентBackactivityListPeoples = new Intent(getApplicationContext(), MainActivity_List_Peoples.class);
                    Bundle bundleBackactivityListPeoples=new Bundle();
                    bundleBackactivityListPeoples.putLong("MainParentUUID", MainParentUUID);
                    bundleBackactivityListPeoples.putInt("Position",    Position);
                    bundleBackactivityListPeoples.putInt("ГодТабелей",     ГодТабелей);
                    bundleBackactivityListPeoples.putInt("МЕсяцТабелей", МЕсяцТабелей);
                    bundleBackactivityListPeoples.putInt("DigitalNameCFO",  DigitalNameCFO);
                    bundleBackactivityListPeoples.putString("FullNameCFO", FullNameCFO);
                    bundleBackactivityListPeoples.putString("ИмесяцвИГодСразу",    ИмесяцвИГодСразу);
                    bundleBackactivityListPeoples.putLong("CurrenrsСhildUUID",  CurrenrsСhildUUID);
                    ИнтентBackactivityListPeoples.putExtras(bundleBackactivityListPeoples);

                    startActivity( ИнтентBackactivityListPeoples);

                } else {
                    forfardSetting();

// TODO: 17.04.2023
                    Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
                }


            }


// TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void forfardSetting() {
        try{
        // TODO Запусукаем Фргамент НАстройки  dashbord
        DashboardFragmentSettings dashboardFragmentSettings = DashboardFragmentSettings.newInstance();
        Bundle data=new Bundle();
        dashboardFragmentSettings.setArguments(data);
        fragmentTransaction.remove(dashboardFragmentSettings);
        String fragmentNewImageNameaddToBackStack=   dashboardFragmentSettings.getClass().getName();
        fragmentTransaction.addToBackStack(fragmentNewImageNameaddToBackStack);
        Fragment FragmentУжеЕСтьИлиНЕт=     fragmentManager.findFragmentByTag(fragmentNewImageNameaddToBackStack);
        if (FragmentУжеЕСтьИлиНЕт==null) {
            dashboardFragmentSettings.show(fragmentManager, "DashboardFragmentSettings");
            // TODO: 01.08.2023

        }

            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new Class_Generation_Errors(getApplicationContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


}