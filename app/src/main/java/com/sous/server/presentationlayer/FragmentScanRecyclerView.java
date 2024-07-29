package com.sous.server.presentationlayer;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.sous.server.R;
import com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerbleRecyclerViewSimpleScan.Bl_FragmentRecyreViewServerSimpleScan;
import com.sous.server.businesslayer.BI_presentationlayer.bl_navigationView.GetNavigationViews;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Eventbus.MessageScannerServer;
import com.sous.server.businesslayer.Eventbus.ParamentsScannerServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class FragmentScanRecyclerView extends Fragment {

    private FragmentManager fragmentManager;

    private  Long version;

    private  MaterialCardView  maincardView_server_ble_fragment;
    private  RelativeLayout  relativeLayout_server_ble;
    private TabLayout   tabLayout_server_ble;
    private MaterialCardView     card_server_ble_inner;
    private RecyclerView     recyclerview_server_ble;
    private ProgressBar     progressbar_server_ble;
    private  Animation animation;
    private Bl_FragmentRecyreViewServerSimpleScan getblFragmentRecyreViewServerScan;

    private Message messageGattServer;
    private BottomNavigationView bottomnavigationview_server_scan ;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fragmentManager = getActivity().getSupportFragmentManager();
            animation  = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_row_vibrator2);

            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version = pInfo.getLongVersionCode();
            messageGattServer = (Message) ((ActivityServerScanner) getActivity()).messageGattServer;


            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }


    @SuppressLint({"RestrictedApi", "MissingPermission"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            maincardView_server_ble_fragment = (MaterialCardView) view.findViewById(R.id.id_maincardView_server_ble_fragment);
            relativeLayout_server_ble    = (RelativeLayout) maincardView_server_ble_fragment.findViewById(R.id.id_relativeLayout_server_ble);
            tabLayout_server_ble = (TabLayout) relativeLayout_server_ble.findViewById(R.id.id_tabLayout_server_ble);
              card_server_ble_inner = (MaterialCardView) relativeLayout_server_ble.findViewById(R.id.id_card_server_ble_inner);
             recyclerview_server_ble = (RecyclerView) relativeLayout_server_ble.findViewById(R.id.id_recyclerview_server_ble);
             progressbar_server_ble = (ProgressBar) relativeLayout_server_ble.findViewById(R.id.id_progressbar_server_ble);
            bottomnavigationview_server_scan = (BottomNavigationView) relativeLayout_server_ble.findViewById(R.id.id_bottomnavigationview_server_scan);


            
            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);

        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewimpleScan = null;
        try {
            viewimpleScan = inflater.inflate(R.layout.fragment_main_server_scan, container, false);

            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return viewimpleScan;
    }






    @Override
    public void onStart() {
        super.onStart();
        try {
            /*    //TODO:создаем подписку MessageScannerServer */
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }

            //todo Есди Данные Пришли ТО мы Их получаем от службыть
            selectingwhichdatatodownload();


            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"   );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            /*    //TODO:создаем подписку MessageScannerServer */
            EventBus.getDefault().unregister(this);
            super.onStop();

            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @Override
    public void onDestroy() {
        try {
        /*    //TODO:создаем подписку MessageScannerServer */
        EventBus.getDefault().unregister(this);
        super.onDestroy();

            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }









    // TODO: 18.07.2024 выбор какие даные загружать

    private void selectingwhichdatatodownload( ) {
        try{

                // TODO: 17.07.2024 запуск БИзнес логики Fragment Scanner, Когда Есть Данные НЕТ НЕТ !!!
            getblFragmentRecyreViewServerScan=new Bl_FragmentRecyreViewServerSimpleScan( fragmentManager,recyclerview_server_ble,
                        version,maincardView_server_ble_fragment,relativeLayout_server_ble,tabLayout_server_ble,card_server_ble_inner,recyclerview_server_ble,
                        progressbar_server_ble,animation,getContext(),getActivity(),messageGattServer,bottomnavigationview_server_scan);


            getblFragmentRecyreViewServerScan.     getDISCOVERABLE_DURATIONs();
            getblFragmentRecyreViewServerScan.     setManagerfromRecyclerView();
            getblFragmentRecyreViewServerScan.     addAdapterServerforRecyreview(null);
            getblFragmentRecyreViewServerScan.     getObserverRecyreView();
            getblFragmentRecyreViewServerScan. settingAnimatios(recyclerview_server_ble);

            //todo  NavigationViewGatt с тремя кнопками Выход .Скан .Контроль
            GetNavigationViews getNavigationViews=new GetNavigationViews(getContext(),
                    messageGattServer,bottomnavigationview_server_scan,
                    version,getActivity(),fragmentManager);
            getNavigationViews.startingbottomNavigationVeiw();
            // TODO: 26.07.2024 reboot reCyreView
            getblFragmentRecyreViewServerScan.     reBootrecyclerView();
           


            Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" + "    "   );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }






















    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageScannerServer event) {
        // Do something
        try {
            // Do something
            ParamentsScannerServer paramentsScannerServer=   event.paramentsScannerServer;
            Boolean getFladEnableApadaterBTEOtService= paramentsScannerServer.getФлагЗапускаФрагментRecyreView();
            String CurrentTask= paramentsScannerServer.getCurrentTask().trim();

            if ( CurrentTask.equalsIgnoreCase("SuccessDeviceBluetoothAnServerScan")) {
                ConcurrentHashMap<String, ContentValues> getMapReceivedFromBootFragmentGatta=  paramentsScannerServer.getContentValuesConcurrentHashMap() ;
                ConcurrentHashMap<String,Cursor> concurrentHashMapCursor=  paramentsScannerServer.getConcurrentHashMapCursor() ;
                // TODO: 18.07.2024
                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService+
                        " CurrentTask  "  +CurrentTask+
                         "getMapReceivedFromBootFragmentGatta " +getMapReceivedFromBootFragmentGatta+ "concurrentHashMapCursor " +concurrentHashMapCursor );


                getblFragmentRecyreViewServerScan.     rebootRecyreViewApdater(  getMapReceivedFromBootFragmentGatta,     concurrentHashMapCursor );
                getblFragmentRecyreViewServerScan.     getObserverRecyreView();
                getblFragmentRecyreViewServerScan.     reBootrecyclerView();



                Log.d(getContext().getClass().getName(), "\n"
                        + " время: " + new Date() + "\n+" +
                        " Класс в процессе... " + this.getClass().getName() + "\n" +
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                        " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService+
                        " CurrentTask  "  +CurrentTask);
            }else {
                // TODO: 18.07.2024  пришел статус что серверне рабоатет 
                if (CurrentTask.contentEquals("bluetootAdapterDisabledScan")) {
                    progressbar_server_ble.setIndeterminate(false);
                    Toast toast = Toast.makeText(getContext(),"Сервер или Bluetooth остановлен  !!! ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
                    toast.show();
                    // TODO: 18.07.2024
                    Log.d(getContext().getClass().getName(), "\n"
                            + " время: " + new Date() + "\n+" +
                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                            " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService+
                            " CurrentTask  "  +CurrentTask);
                }

            }

            Log.d(getContext().getClass().getName(), "\n"
                    + " время: " + new Date() + "\n+" +
                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                    " getFladEnableApadaterBTEOtService " +getFladEnableApadaterBTEOtService+
                    " CurrentTask  "  +CurrentTask);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки = new ContentValues();
            valuesЗаписываемОшибки.put("Error", e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass", this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod", Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError", Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error", ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(getContext()).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }








    // TODO: 17.07.2024
































}





























