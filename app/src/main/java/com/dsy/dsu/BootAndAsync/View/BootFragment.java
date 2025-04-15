package com.dsy.dsu.BootAndAsync.View;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusAppAfterSyncing;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusNetworkStatuses;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusPrograssBar;
import com.dsy.dsu.BootAndAsync.Model.EventsBus.MessageEvensBusUpdatePO;
import com.dsy.dsu.BootAndAsync.Model.ModuleSingleWorkManager.ModuleSingleWorkManager;
import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentActivityBootService;
import com.dsy.dsu.BootAndAsync.View.ComponetsUI.GetComponentPrograssbar;
import com.dsy.dsu.Dashboard.Model.bl_viewpager2.BunesslogicViewPager2;
import com.dsy.dsu.Dashboard.Model.endingasynsdashboard.LaunchMainAppAfterSyncing;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;
import com.dsy.dsu.Errors.WriteErrorForAll.RecordNewErros;
import com.dsy.dsu.Hilt.getSSLSocketFactory2.QualifiergetsslSocketFactory2;
import com.dsy.dsu.R;
import com.dsy.dsu.databinding.FragmentBootBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class BootFragment extends DialogFragment {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Animation animation5;
    protected ProgressBar progressbarbootandasync;
    protected Activity activity;
    protected DrawerLayout drawerLayoutAsync;
    protected NavigationView navigationViewAsyncApp;
    protected LifecycleOwner getlifecycleOwner  ;
    protected GetComponentActivityBootService blInnerMainActivityBootAndAsync;
    private ImageView imageView_faceapp_settings;


    @Inject
    ModuleSingleWorkManager moduleSingleWorkManager;

    @Inject
    @QualifiergetsslSocketFactory2
    public SSLSocketFactory getsslSocketFactory2;


    // TODO: Rename and change types and number of parameters
    // TODO: Rename and change types and number of parameters
    public static BootFragment newInstance( ) {
        BootFragment fragment = new BootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            // TODO: 15.04.2025
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
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
            // setStyle(   DialogFragment.STYLE_NORMAL ,android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen );
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
        View view=null;
        try{
            view= inflater.inflate(R.layout.fragment_boot, container, false);

            fragmentManager = getActivity(). getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            // TODO: 22.08.2023  анимауия
            animation5 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_row8);


            // TODO: 15.04.2025 запускам бизнес логику фрагмента boot
            blInnerMainActivityBootAndAsync=new GetComponentActivityBootService(getsslSocketFactory2,
                    progressbarbootandasync, activity, drawerLayoutAsync,
                    navigationViewAsyncApp,getContext(),  getlifecycleOwner,imageView_faceapp_settings);


            // TODO: 19.01.2024  запускаем бизнес логики автивити boot and async
            blInnerMainActivityBootAndAsync .  МетодБоковаяПанельОткрытьЗАкрыть();
            blInnerMainActivityBootAndAsync .listerNavigationViewAsyncApp();
            blInnerMainActivityBootAndAsync .  workerNavigationViewAsyncApp(fragmentManager);
            blInnerMainActivityBootAndAsync .  workerImageViewsettings();

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
            // TODO: 03.04.2025
          //  tableLayout_dashboard         = (TableLayout) view.findViewById(R.id.tableLayout_dashboard); /////КНОПКА ТАБЕЛЬНОГО УЧЕТА

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
            blInnerMainActivityBootAndAsync .  registeEventBusFirst(this);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void onStop() {
        super.onStop();
        try{
            blInnerMainActivityBootAndAsync .  unregisterEventBusFirst(this);
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















    // TODO: 15.04.2025  EvenBus   TODO: 15.04.2025  EvenBus   TODO: 15.04.2025  EvenBus   TODO: 15.04.2025  EvenBus   TODO: 15.04.2025  EvenBus  TODO: 15.04.2025  EvenBus  TODO: 15.04.2025  EvenBus
    // TODO: 23.01.2024 EventBus for Prograssbar
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
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

            Bundle    getbundleLaunchMainAppAfterSyncing= messageEvensBusAppAfterSyncing.mess.getExtras();

            Boolean getUserAuthenticated=   getbundleLaunchMainAppAfterSyncing.getBoolean("launchMainAppAfterSyncing",false);///"В процесс"

            LaunchMainAppAfterSyncing launchMainAppaftersyncing =new LaunchMainAppAfterSyncing(this);
            if (getUserAuthenticated){
                // TODO: 01.04.2024 Все в порядке ЗАпускам Саму Программу DashBord
                launchMainAppaftersyncing.appAfterSyncingDashboard();
            }else {
                // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                // TODO: 28.04.2023 НЕт Анутифтикации Пароль
                launchMainAppaftersyncing.appAfterSyncingPassword(  );
            }
            Log.d(getApplicationContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+"\n"+
                    "getUserAuthenticated " +getUserAuthenticated);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(), this.getClass().getName(), Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }



// TODO: 15.04.2025 end class
}