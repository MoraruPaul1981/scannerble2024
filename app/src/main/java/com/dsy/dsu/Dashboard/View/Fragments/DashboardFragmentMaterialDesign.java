package com.dsy.dsu.Dashboard.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.dsy.dsu.AdmissionMaterials.Window.MainActivity_AdmissionMaterials;
import com.dsy.dsu.CommitPrices.View.Window.MainActivityCommitingPrices;

import com.dsy.dsu.Dashboard.Model.changeaccessrights.DashboardGRANTorREVOKE;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.Hilt.PublicId.QualifierPublicId;
import com.dsy.dsu.OrdersTransports.Window.MainActivityOrdersTransports;
import com.dsy.dsu.PaysCommit.View.Window.MainActivity_CommitPay;
import com.dsy.dsu.R;
import com.dsy.dsu.Services.ServiceUpdatePoОбновлениеПО;
import com.dsy.dsu.Tabels.MainActivity_List_Tabels;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardFragmentMaterialDesign extends  DialogFragment  {
    // TODO: Rename parameter arguments, choose names that match

    private  BuniccessLogicFra4gmentDashboard buniccessLogicFra4gmentDashboard;
    private MaterialCardView materialcardview_dashboard=null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private          View ViewDashboart=null;

    private   RelativeLayout relativelayout_dashboard;

    private Animation animation1,animation2,animation3,animation4,animation5;

    private MaterialButton КнопкаТабель, КнопкаСогласование ,
            КнопкаПоступлениеМатериалов,КнопкаЗаявкаНаТранспорт,
            КнопкаСогласЦен;

    private  TextView TextViewLogo;
    private LifecycleOwner lifecycleOwner;

    private ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО localBinderОбновлениеПО;//TODO новаЯ

    private AppCompatImageButton imageview_to_settings ;


    @Inject
        DashboardGRANTorREVOKE dashboardGRANTorREVOKE;

    @Inject
    @QualifierPublicId
        Integer getHiltPublicId;

    private androidx.appcompat.widget.Toolbar toolbarcamera_dashbord;
    private  androidx.core.widget.NestedScrollView nestedScrollViewApp;

    // TODO: Rename and change types and number of parameters
    public static DashboardFragmentMaterialDesign newInstance( ) {
        DashboardFragmentMaterialDesign fragment = new DashboardFragmentMaterialDesign();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            fragmentManager = getActivity(). getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            buniccessLogicFra4gmentDashboard=new BuniccessLogicFra4gmentDashboard();
            // TODO: 17.08.2023 inizial message
            lifecycleOwner=getActivity();
            buniccessLogicFra4gmentDashboard. методСлушательФрагментовBinder( );

            buniccessLogicFra4gmentDashboard.методGetBinder(getArguments());



            // TODO: 22.08.2023  анимауия
            animation5 = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_row8);

            // TODO: 21.08.2023 насйтироки Разые дизайна Фрагмента
            /*setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Material_Dialog_Alert);//Theme_Dialog*/
            // setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog
            //  setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog
            //  setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Dialog_Alert);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_Dialog
            //setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Light_Panel);//Theme_Dialog
            //  setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_InputMethod);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Light_Panel);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Panel);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Settings);//Theme_Dialog
            //setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);//Theme_Dialog
            // setStyle(DialogFragment.STYLE_NO_FRAME | DialogFragment.STYLE_NO_INPUT,android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);//Theme_Dialog
            //  setStyle(  DialogFragment.STYLE_NO_FRAME | DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);//Theme_Dialog
            //    setStyle(   DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar);//Theme_Dialog
            //  setStyle(   DialogFragment.STYLE_NO_INPUT ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NO_FRAME ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NO_FRAME ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Overscan);//Theme_Dialog
            //setCancelable(false);

            //setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme_Material_Dialog);
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Material_Dialog_MinWidth);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NO_TITLE ,android.R.style.Theme_Material_Light);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NO_TITLE ,android.R.style.Theme_Material_Light_NoActionBar_Overscan);//Theme_Dialog
            //setStyle(   DialogFragment.STYLE_NORMAL,android.R.style.Theme_Material_Light_NoActionBar_Overscan);//Theme_Dialog

            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog  Без Часов
            //    setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_DarkActionBar);//Theme_Dialog
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);//Theme_Dialog   с часами сверху
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog  Без Часов
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Black_NoTitleBar );
            //  setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Material_Light_Dialog_Presentation);
            setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Material_Light_NoActionBar );

            setShowsDialog(true);
            // TODO: 15.08.2023
            Log.d(this.getClass().getName(),"\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        try{

            Log.d(this.getClass().getName(),"\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    // TODO: 07.10.2023  метод первой запуска Work Manager Public и его перввая регистарция






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try{
            fragmentManager.setFragmentResult(     "CallBackDashborndFragment",new Bundle());
            Log.d(this.getClass().getName(),"\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;
        try{
       /*     ViewDashboart= inflater.inflate(R.layout.simple_dashbord_fragment_grey, container, false);
            ViewDashboart= inflater.inflate(R.layout.simple_dashbord_fragment_grey, container, false);
            ViewDashboart= inflater.inflate(R.layout.simple_dashbord_fragment_grey, container, false);*/
            // ViewDashboart= inflater.inflate(R.layout.simple_dashbord_fragment_grey, container, false);
            // ViewDashboart= inflater.inflate(R.layout.simple_dashbord_fragment_blue, container, false);
            // view= inflater.inflate(R.layout.simple_dashbord_fragment_grey_materialdisign_3, container, false);
            view= inflater.inflate(R.layout.simple_dashbord_fragment_scroll, container, false);
            // view= inflater.inflate(R.layout.simple_dashbord_fragment_green, container, false);
            // TODO: 21.06.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        return  view; //TODO inflater.inflate(R.layout.activity_main__tabel_four_colums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        try{
            super.onViewCreated(view, savedInstanceState);



            ViewDashboart=view;
            relativelayout_dashboard         = (RelativeLayout) view.findViewById(R.id.relativelayout_dashboard); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА



            КнопкаЗаявкаНаТранспорт   = (MaterialButton) view.findViewById(R.id.КнопкаЗаявкаНаТранспорт); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            КнопкаСогласование         = (MaterialButton) view.findViewById(R.id.КнопкаСогласование); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            КнопкаСогласЦен         = (MaterialButton) view.findViewById(R.id.КнопкаСогласЦен); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            КнопкаПоступлениеМатериалов         = (MaterialButton) view.findViewById(R.id.КнопкаПоступлениеМатериалов); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            КнопкаТабель          = (MaterialButton) view.findViewById(R.id.КнопкаТабель); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА



            TextViewLogo      = (TextView) view.findViewById(R.id.TextViewLogo); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА

            imageview_to_settings      = (AppCompatImageButton) view.findViewById(R.id.imageview_to_settings); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА


            toolbarcamera_dashbord = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.toolbarcamera_dashbord);
            /////КНОПКА ТАБЕЛЬНОГО УЧЕТА
            nestedScrollViewApp = (androidx.core.widget.NestedScrollView) view.findViewById(R.id.nestedScrollViewApp); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА




            // TODO: 11.01.2024  ПОЛУЧАЕМ Права

            Integer getGrantRemote=    dashboardGRANTorREVOKE.getGrantRemote(getContext() ,getHiltPublicId);
            //TODO
            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " getGrantRemote " +getGrantRemote);

            dashboardGRANTorREVOKE.setGrantRemote(getContext(),
                    КнопкаЗаявкаНаТранспорт,
                    КнопкаСогласование,КнопкаСогласЦен
                    ,КнопкаПоступлениеМатериалов,
                    КнопкаТабель,getGrantRemote);







            // TODO: 11.01.2024 бизнес код настройки внешнего вида
            buniccessLogicFra4gmentDashboard.методНастройкиВнешнегоВида();




            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }





    @Override
    public void onStart() {
        super.onStart();
        try{

            buniccessLogicFra4gmentDashboard.new ClassButtonsApp().методStartingAllButtonApp();


            buniccessLogicFra4gmentDashboard.new ClassAnimatilBackButton().методToSettingsFragment();



            // TODO: 20.07.2023
            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }








    // TODO: 15.08.2023  Бизнес ЛОгика Bunecees Logic Dashboard Fragment

    class BuniccessLogicFra4gmentDashboard{


        private void методНастройкиВнешнегоВида() {
            try{
                // TODO: 21.08.2023  ЛОГОТИП
                TextViewLogo.startAnimation(animation5);
                TextViewLogo.refreshDrawableState();
                // TODO: 21.08.2023
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getContext().getClass().getName(),
                        "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getContext()).recordnewerror(e.toString(),
                        this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }


        // TODO: 17.08.2023  Класс Для Компонента Табель








        private void методСлушательФрагментовBinder( ) {
            try{
                fragmentManager.setFragmentResultListener("callbackbinderdashbord", lifecycleOwner, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (requestKey.equalsIgnoreCase("callbackbinderdashbord")) {
                            try{
                                localBinderОбновлениеПО=(ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО)       result.getBinder("callbackbinderdashbord");
                                // TODO: 21.08.2023

                                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  +
                                        "  localBinderОбновлениеПО " +localBinderОбновлениеПО);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(getContext().getClass().getName(),
                                        "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new RecordNewErros(getContext()).recordnewerror(e.toString(),
                                        this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                                        Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }

                        }
                    }
                });
                Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getContext().getClass().getName(),
                        "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getContext()).recordnewerror(e.toString(),
                        this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }


        // TODO: 21.08.2023 Класс с бизнес логикой Кнопки

        class ClassButtonsApp {
            void методStartingAllButtonApp() {


                // TODO: 14.04.2023 Запускаем Заявка НА Транспорт
                КнопкаЗаявкаНаТранспорт.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.d(this.getClass().getName(), "Запускает Согласния   ");
                            Intent ИнтентЗаявкаНаТранспорт = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            ИнтентЗаявкаНаТранспорт.putExtras(data);
                            ИнтентЗаявкаНаТранспорт.setClass(getContext(), MainActivityOrdersTransports.class);//рабочий
                            ИнтентЗаявкаНаТранспорт.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ИнтентЗаявкаНаТранспорт);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }


                    }
                });
// TODO: 21.08.2023 tretiy button
                КнопкаСогласование.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.d(this.getClass().getName(), "Запускает Согласния   ");
                            Intent intentЗапускСогласования1C = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            intentЗапускСогласования1C.putExtras(data);
                            intentЗапускСогласования1C.setClass(getContext(), MainActivity_CommitPay.class);//рабочий
                            intentЗапускСогласования1C.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentЗапускСогласования1C);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }


                });
                // TODO: 21.08.2023 hetbertay button

                // TODO: 14.04.2023 Запускаем Заявка НА Транспорт
                КнопкаПоступлениеМатериалов.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Log.d(this.getClass().getName(), "Запускает Согласния   ");
                            Intent ИнтентЗаявкаНаТранспорт = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            ИнтентЗаявкаНаТранспорт.putExtras(data);
                            ИнтентЗаявкаНаТранспорт.setClass(getContext(), MainActivity_AdmissionMaterials.class);//рабочий
                            ИнтентЗаявкаНаТранспорт.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ИнтентЗаявкаНаТранспорт);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }


                    }
                });

                // TODO: 21.08.2023 pyty abutton
                КнопкаТабель.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent Интент_ЗапускТабельногоУчётаПервыйШаг = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            Интент_ЗапускТабельногоУчётаПервыйШаг.putExtras(data);
                            Интент_ЗапускТабельногоУчётаПервыйШаг.setClass(getContext(), MainActivity_List_Tabels.class); //  ТЕСТ КОД КОТОРЫЙ ЗАПУСКАЕТ ACTIVITY VIEWDATA  ПРОВЕРИТЬ ОБМЕН
                            Интент_ЗапускТабельногоУчётаПервыйШаг.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Log.d(this.getClass().getName(), "" + "    КнопкаТабельныйУчёт.setOnClickListener(new View.OnClickListener() {");
                            startActivity(Интент_ЗапускТабельногоУчётаПервыйШаг);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }
                });

// TODO: 20.12.2023  новая кнопка согласование цен
                КнопкаСогласование.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intentЗапускСогласования = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            intentЗапускСогласования.putExtras(data);
                            intentЗапускСогласования.setClass(getContext(), MainActivity_CommitPay.class);//рабочий
                            intentЗапускСогласования.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentЗапускСогласования);
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }


                });
// TODO: 20.12.2023  новая кнопка согласование цен
                КнопкаСогласЦен.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intentЗапускСогласованиеЦены1С = new Intent();
                            Bundle data = new Bundle();
                            data.putBinder("callbackbinderdashbord", localBinderОбновлениеПО);
                            intentЗапускСогласованиеЦены1С.putExtras(data);
                            intentЗапускСогласованиеЦены1С.setClass(getContext(), MainActivityCommitingPrices.class);//рабочий
                            intentЗапускСогласованиеЦены1С.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentЗапускСогласованиеЦены1С);
                            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(), this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }


                });

                // TODO: 21.08.2023  teo button




            }
        }



        // TODO: 22.08.2023  классс который отвечает за Переход на Фрагмент НАстройки
        class ClassAnimatilBackButton{
            void методToSettingsFragment(){
                imageview_to_settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            // TODO Запусукаем Фргамент НАстройки  dashbord
                            DashboardFragmentSettings    dashboardFragmentSettings = DashboardFragmentSettings.newInstance();
                            Bundle data=new Bundle();
                            data.putBinder("callbackbinderdashbord",localBinderОбновлениеПО);
                            dashboardFragmentSettings.setArguments(data);
                            fragmentTransaction.remove(dashboardFragmentSettings);
                            fragmentTransaction.disallowAddToBackStack();
                            fragmentManager.popBackStackImmediate();
                            // TODO: 03.10.2023


                            String fragmentNewImageNameaddToBackStack=   dashboardFragmentSettings.getClass().getName();
                            //fragmentTransaction.addToBackStack(fragmentNewImageNameaddToBackStack);*/
                            Fragment FragmentУжеЕСтьИлиНЕт=     fragmentManager.findFragmentByTag(fragmentNewImageNameaddToBackStack);
                            if (FragmentУжеЕСтьИлиНЕт==null) {
                                dashboardFragmentSettings.show(fragmentManager, "DashboardFragmentSettings");
                                // TODO: 01.08.2023

                            }
                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                                    + " FragmentУжеЕСтьИлиНЕт " +FragmentУжеЕСтьИлиНЕт );




                            Log.i(getContext().getClass().getName(),  " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  );
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
                            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                        }
                    }
                });

            }
        }



        // TODO: 22.08.2023 МЕТОД ПОЛУЧЕНИЕ ДАННЫХ binder
        void методGetBinder (@NonNull Bundle bundleGetSettings){
            try{

                if (localBinderОбновлениеПО==null) {
                    localBinderОбновлениеПО =(ServiceUpdatePoОбновлениеПО.localBinderОбновлениеПО)  bundleGetSettings.getBinder("callbackbinderdashbord");

                }

                Log.i(getContext().getClass().getName(),  " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"  + " bundleGetSettings  " +bundleGetSettings +
                        " localBinderОбновлениеПО " +localBinderОбновлениеПО);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getContext()).recordnewerror(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }




        void   getnestedScrollViewAppClick(@NonNull   Handler handler) {
            try{
                ViewPropertyAnimator animation = nestedScrollViewApp.animate();
                animation.translationYBy(150).setDuration(500).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animation.translationYBy(-150);
                    }
                },1000);

                Log.i(getContext().getClass().getName(),  " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +
                        " localBinderОбновлениеПО " +localBinderОбновлениеПО);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                        + Thread.currentThread().getStackTrace()[2].getLineNumber());
                new RecordNewErros(getContext()).recordnewerror(e.toString(),
                        this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                        Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
        //TODO end Buniceess Lofic for Activity//TODO end Buniceess Lofic for Activity//TODO end Buniceess Lofic for Activity//TODO end Buniceess Lofic for Activity//TODO end Buniceess Lofic for Activity

    }//TODO end Buniceess Lofic for Activity



}