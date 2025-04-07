package com.dsy.dsu.Errors.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsy.dsu.Errors.controller.BinessLogicFragmentError;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.R;
import com.google.android.material.button.MaterialButton;
import com.sous.backasync.launch.ModuleQuety;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
public class FragmentError extends DialogFragment {


    private TextView textViewAllError;
    private  TextView textViewHeaderErrors;
    private MaterialButton materialButtonОтправка;
    private SharedPreferences preferences;
    private MaterialButton imageViewBack;
    private BinessLogicFragmentError logicFragmentError;

    @Inject
    ModuleQuety moduleQuety;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Inject
   SQLiteDatabase getSqlLiteCoreApp;

    // TODO: 14.10.2022 настйрока хранилища
    private  SharedPreferences sharedPreferencesХранилище;
    private     StringBuffer BufferGetError;

    // TODO: Rename and change types and number of parameters
    public static FragmentError newInstance( ) {
        FragmentError fragment = new FragmentError();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
        //  setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Material_Dialog_Alert);//Theme_Dialog
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
        // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog
        //setCancelable(false);
        // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog  Без Часов
        //    setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);//Theme_Dialog
        // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_DarkActionBar);//Theme_Dialog
        //  setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);//Theme_Dialog   с часами сверху
        // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);//Theme_Dialog  Без Часов
          /*    setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);//Theme_Dialog   с часами сверху

            setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Black_NoTitleBar );*/
        setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Material_Light_NoActionBar );
        /*          setCancelable(false);*/
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=null;
        try{
         view= inflater.inflate(R.layout.fragment_error_list, container, false);
            ConstraintLayout constraintLayoutgetError =(ConstraintLayout) view;
            // TODO: 04.04.2025
            textViewAllError = (TextView) constraintLayoutgetError.findViewById(R.id.textViewAllError);
            textViewHeaderErrors = (TextView)constraintLayoutgetError. findViewById(R.id.textViewHeaderErrors);
            materialButtonОтправка = (MaterialButton) constraintLayoutgetError.findViewById(R.id.materialButtonОтправка);
            imageViewBack = (MaterialButton) constraintLayoutgetError.findViewById(R.id.imageViewBack);
            preferences=   getContext().getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);
            // TODO: 04.04.2025
            fragmentManager = getActivity(). getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            sharedPreferencesХранилище=  getContext().getSharedPreferences("sharedPreferencesХранилище", Context.MODE_MULTI_PROCESS);



            // TODO: 12.12.2023  staring biscce logic
            logicFragmentError =new BinessLogicFragmentError( getContext(),getSqlLiteCoreApp,moduleQuety);

            // TODO: 07.04.2025 exit fromFragment ERROR 
            logicFragmentError.BackFragmentSettings(imageViewBack,fragmentManager);

            // TODO: 17.01.2025  Получаем Ошибку двумя разными способами из файла и из курсора
              BufferGetError =     logicFragmentError.gettingErrorsIsCursor();

            Log.d(this.getClass().getName(),"\n" + " class "
                + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   +  "getSqlLiteCoreApp " +getSqlLiteCoreApp +
                    " BufferGetError " +BufferGetError);
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(getContext().getClass().getName(),
                "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getContext()).recordnewerror(e.toString(),
                this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        try{


            // TODO: 12.12.2023  Данные ОШибки*/
            if (BufferGetError.length()>0) {
                logicFragmentError.   metodButtonEnables(materialButtonОтправка);
                // TODO: 17.01.2025  полученные ошибку отправляем на экран ПОльзователю
                logicFragmentError.  metodScreenErrorForUsers(textViewAllError,BufferGetError);
                logicFragmentError.  metodSendErrorsToMail(materialButtonОтправка,BufferGetError,getActivity(),sharedPreferencesХранилище);
                // TODO: 07.04.2025 Когда нет данных
            } else {
                logicFragmentError.metodButtonDisable(materialButtonОтправка);
                logicFragmentError.  metodScreenDontErrorForUsers(  textViewAllError);
               
            }

            // TODO: 17.04.2023
            Log.d(this.getClass().getName(),"\n" + " class FaceAPp " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName()
                    + " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            // TODO: 01.09.2021 метод вызова
            new RecordNewErros(getContext()).recordnewerror(e.toString(),
                    this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }

    // TODO: 04.04.2025  END CLASS
}