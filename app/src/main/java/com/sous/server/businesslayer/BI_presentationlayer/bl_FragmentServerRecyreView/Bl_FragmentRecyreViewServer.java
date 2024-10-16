package com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentServerRecyreView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Message;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.sous.server.R;
import com.sous.server.businesslayer.BI_presentationlayer.bl_FragmentBootScannerServer.Bi_FragmentBootScannerServer;
import com.sous.server.businesslayer.Errors.SubClassErrors;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Bl_FragmentRecyreViewServer  {

    private FragmentManager fragmentManager;
    private  MyRecycleViewAdapterServer myRecycleViewAdapterServer;
    private   MyViewHolder myViewHolder;
    private RecyclerView recyclerViewServer;
    // TODO: 17.07.2024

    private  Long version;

    private MaterialCardView maincardView_server_ble_fragment;
    private RelativeLayout relativeLayout_server_ble;
    private TabLayout tabLayout_server_ble;
    private MaterialCardView     card_server_ble_inner;
    private RecyclerView     recyclerview_server_ble;
    private ProgressBar progressbar_server_ble;
    private Animation animation;
    private Context context;

    private Activity activity;
    private  BlgeneralServer blgeneralServer;
    private Message messageGattServer;
    private  BottomNavigationView bottomNavigationViewGatt;

    public Bl_FragmentRecyreViewServer(FragmentManager fragmentManager,
                                       RecyclerView recyclerViewServer,
                                       Long version,
                                       MaterialCardView maincardView_server_ble_fragment,
                                       RelativeLayout relativeLayout_server_ble,
                                       TabLayout tabLayout_server_ble,
                                       MaterialCardView card_server_ble_inner,
                                       RecyclerView recyclerview_server_ble,
                                       ProgressBar progressbar_server_ble,
                                       Animation animation,
                                       Context context,
                                       Activity activity,
                                       Message messageGattServer,
                                       BottomNavigationView bottomNavigationViewGatt) {
        // TODO: 17.07.2024
        this.fragmentManager = fragmentManager;
        this.recyclerViewServer = recyclerViewServer;
        this.version = version;
        this.maincardView_server_ble_fragment = maincardView_server_ble_fragment;
        this.relativeLayout_server_ble = relativeLayout_server_ble;
        this.tabLayout_server_ble = tabLayout_server_ble;
        this.card_server_ble_inner = card_server_ble_inner;
        this.recyclerview_server_ble = recyclerview_server_ble;
        this.progressbar_server_ble = progressbar_server_ble;
        this.animation = animation;
        this.context = context;
        this.activity = activity;
        this.messageGattServer = messageGattServer;
        this.bottomNavigationViewGatt = bottomNavigationViewGatt;
        // TODO: 18.07.2024
        blgeneralServer=new BlgeneralServer(context,version);
    }


















    @SuppressLint("MissingPermission")
    public void getDISCOVERABLE_DURATIONs() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
       activity. startActivity(discoverableIntent);
        // TODO: 17.07.2024
        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    }
    public void getObserverRecyreView() {  // TODO: 04.03.2022  класс в котором находяться слушатели
        try {
            myRecycleViewAdapterServer.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    try {
                        Log.d(this.getClass().getName(), "onChanged ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    // TODO: 05.03.2022  СТАТУС ЗНАЧКА С ДОПОЛНИТЕЛЬНЫЙ СТАТУСОМ
                    try {
                        Log.d(this.getClass().getName(), "onItemRangeChanged ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                    super.onItemRangeChanged(positionStart, itemCount, payload);
                    try {
                        Log.d(this.getClass().getName(), "onItemRangeChanged ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    try {
                        Log.d(this.getClass().getName(), "onItemRangeInserted ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    try {
                        Log.d(this.getClass().getName(), "onItemRangeRemoved ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    try {
                        Log.d(this.getClass().getName(), "     onItemRangeMoved ");
                        //TODO
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
                        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
                    }
                }
            });
            //TODO
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    // TODO: 04.03.2022 прозвомжность Заполения RecycleView
    public void addAdapterServerforRecyreview(@NonNull Cursor concurrentHashMapCursor) {
        try {
            myRecycleViewAdapterServer = new MyRecycleViewAdapterServer(concurrentHashMapCursor);
            recyclerViewServer.setAdapter(myRecycleViewAdapterServer);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                     " concurrentHashMapCursor " +concurrentHashMapCursor);
            //TODO
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }




    // TODO: 04.03.2022 прозвомжность инициализации RecycleView
    public void setManagerfromRecyclerView() {
        try {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewServer.setLayoutManager(linearLayoutManager);

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
            //TODO
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }
    // TODO: 28.02.2022 начало  MyViewHolderДляЧата
    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView  card_server_success_data_oncreateviewholder;

        private TabLayout  tabLayout_server_ble;
        private TabLayout  tabLayout_server_ble_parent;
        // TODO: 18.07.2024 TextInputEditText все компонеты
        private    TextInputEditText   textinputtext_id_currentrow;
        private    TextInputEditText textinputtext_namedevice;

        private    TextInputEditText textinputtext_macdevice;
        private    TextInputEditText  textinputtext_completedwork;
        private    TextInputEditText  textinputtext_iemi;
        private    TextInputEditText textinputtext_date_update;
        private    TextInputEditText textinputtext_city;

        private TextInputEditText textinputtext_adress ;

        private TextInputEditText textinputtext_gps1 ;

        private TextInputEditText textinputtext_gps2 ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                //todo init componets
                        initingComponentsforRecyreView(  itemView);

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        "  itemView " +itemView
                        + " myRecycleViewAdapterServer.getMapReceivedFromBootFragmentGatta "
                        + myRecycleViewAdapterServer.getconcurrentHashMapCursor );

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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
        }

        private void initingComponentsforRecyreView(@NonNull View itemView) {
            try {

                // TODO: 18.07.2024 Сами компаненты при неличии
                    //card_server_dont_data_oncreateviewholder = itemView.findViewById(R.id.id_card_server_mini_data_oncreateviewholder);
                    card_server_success_data_oncreateviewholder = itemView.findViewById(R.id.id_card_server_success_data_oncreateviewholder);
                    //TODO Если инициализация проша тогда Инициализируем Компоненты
                if (card_server_success_data_oncreateviewholder!=null) {

                     tabLayout_server_ble = itemView.findViewById(R.id.id_tabLayout_server_ble);

                   tabLayout_server_ble_parent = itemView.findViewById(R.id.id_tabLayout_server_ble_parent);

                    // TODO: 18.07.2024 TextInputEditText все компонеты
                    textinputtext_id_currentrow = itemView.findViewById(R.id.id_textinputtext_id_currentrow);

                    textinputtext_namedevice = itemView.findViewById(R.id.id_textinputtext_namedevice);

                    textinputtext_macdevice = itemView.findViewById(R.id.id_textinputtext_macdevice);

                    textinputtext_completedwork = itemView.findViewById(R.id.id_textinputtext_completedwork);

                    textinputtext_iemi = itemView.findViewById(R.id.id_textinputtext_iemi);

                    textinputtext_date_update = itemView.findViewById(R.id.id_textinputtext_date_update);

                    textinputtext_city = itemView.findViewById(R.id.id_textinputtext_city);


                     textinputtext_adress = itemView.findViewById(R.id.id_textinputtext_adress);

                     textinputtext_gps1 = itemView.findViewById(R.id.id_textinputtext_gps1);

                     textinputtext_gps2 = itemView.findViewById(R.id.id_textinputtext_gps2);





                }


                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " itemView  " +itemView);

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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
        }
    }

    // TODO: 17.07.2024


    class MyRecycleViewAdapterServer extends RecyclerView.Adapter<MyViewHolder> {
        public  Cursor  getconcurrentHashMapCursor;

        public MyRecycleViewAdapterServer(@NotNull  Cursor  mapconcurrentHashMapCursor) {
            this.getconcurrentHashMapCursor = mapconcurrentHashMapCursor;
            //TODO
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " mapconcurrentHashMapCursor " +mapconcurrentHashMapCursor);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
            try {
             // TODO: 19.07.2024
                if (getconcurrentHashMapCursor!=null) {
                    if (getconcurrentHashMapCursor.getCount()>0) {
                        getconcurrentHashMapCursor.moveToPrevious();

                    }

                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " getconcurrentHashMapCursor " + getconcurrentHashMapCursor.getPosition());

                }
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                        " position " + position);

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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
        }

        @Override
        public void onViewRecycled(@NonNull MyViewHolder holder) {
            super.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull MyViewHolder holder) {
            return super.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

            recyclerView.removeAllViews();

            recyclerView.getRecycledViewPool().clear();
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public int getItemViewType(int position) {
            try {
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+"   getItemViewType  position" + position);
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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
            return super.getItemViewType(position);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            try {
                // TODO: 18.07.2024 первый запуск без данных
                if(getconcurrentHashMapCursor==null    ){
                    // TODO: 18.07.2024 Когда Данные НЕТ !!!
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_dont_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1
                    // TODO: 18.07.2024 второй  запуск   данных  есть

                }else {

                    if(getconcurrentHashMapCursor.getCount()>0){
                        // TODO: 18.07.2024 Когда Данные есть
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_success_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1
                      //  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_success_mini_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1*/
                    }else {
                        // TODO: 18.07.2024 Когда Данные НЕТ !!!
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_dont_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1
                        // TODO: 18.07.2024 второй  запуск   данных  есть


                    }

                }
                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " myViewHolder " +myViewHolder+
                        " getconcurrentHashMapCursor " +getconcurrentHashMapCursor);

                 // view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_dont_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1
                  //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_success_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1
                    //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_server_success_mini_data_oncreateviewholder, parent, false);//todo old simple_for_takst_cardview1*/


                myViewHolder = new MyViewHolder(view);

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+ " myViewHolder " +myViewHolder+
                        " getconcurrentHashMapCursor " +getconcurrentHashMapCursor);

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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
            return myViewHolder;
        }






        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            try {
                if(getconcurrentHashMapCursor!=null    ) {
                    if (getconcurrentHashMapCursor.getCount()>0) {

                        //todo ЗАполеняем Данными пришедешими с севрера

                        tabLayoutClick(holder);

                        // TODO: 18.07.2024  Главные Методы ЗАполения

                        gettextinputtext_id_currentrow(holder,getconcurrentHashMapCursor);

                       gettextinputtext_namedevice(holder,getconcurrentHashMapCursor);


                        gettextinputtext_macdevice(holder,getconcurrentHashMapCursor);

                        gettextinputtext_date_update(holder,getconcurrentHashMapCursor);

                        gettextinputtext_completedwork(holder,getconcurrentHashMapCursor);

                        gettextinputtext_iemi(holder,getconcurrentHashMapCursor);

                        gettextinputtext_city(holder,getconcurrentHashMapCursor);

                       gettextinputtext_adress(holder,getconcurrentHashMapCursor);

                        gettextinputtext_gps1(holder,getconcurrentHashMapCursor);

                        gettextinputtext_gps2(holder,getconcurrentHashMapCursor);


                        getAnimationtabLayout_server_ble(holder);

                        getAnimationtabLayout_server_bleparent(holder);


                        Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                                + "\n" + " getMapReceivedFromBootFragmentGatta " +getconcurrentHashMapCursor
                                + " holder " +holder+ " getContentValuesCurrentDivece " +getconcurrentHashMapCursor);

                    }
                }

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + "\n" + " getMapReceivedFromBootFragmentGatta " +getconcurrentHashMapCursor);

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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
        }




        @Override
        public long getItemId(int position) {
            // TODO: 04.03.2022
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+"     getItemId holder.position " + position);
            return super.getItemId(position);

        }

        @Override
        public int getItemCount() {
            Integer getFragmentScannerGatt=0;
            try {
                if (getconcurrentHashMapCursor==null) {
                    getFragmentScannerGatt=1;
                } else {
                    getFragmentScannerGatt=getconcurrentHashMapCursor.getCount()-1;
                }

                Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                        " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                        " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+"     getItemId getconcurrentHashMapCursor "
                        + getconcurrentHashMapCursor + " getFragmentScannerGatt " +getFragmentScannerGatt);
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
                new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
            }
            return getFragmentScannerGatt;
        }
    }

    private void getAnimationtabLayout_server_ble(@NonNull MyViewHolder holder) {
        try{
        Animation animationscroll  = AnimationUtils.loadAnimation(context, R.anim.fadein);
        holder.tabLayout_server_ble.startAnimation(animationscroll);

        messageGattServer.getTarget().postDelayed(()->{
            holder.tabLayout_server_ble.setBackgroundColor(Color.WHITE);


        },1500);
            holder.tabLayout_server_ble.setBackgroundColor(Color.GRAY);

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
        new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
    }
    }
    private void getAnimationtabLayout_server_bleparent(@NonNull MyViewHolder holder) {
        try{
            Animation animationscroll  = AnimationUtils.loadAnimation(context, R.anim.fadein_server);
            holder.textinputtext_id_currentrow.startAnimation(animationscroll);
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }
    @SuppressLint("Range")
    private void gettextinputtext_gps2(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_gps2.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("gps2")));///gps2
            holder. textinputtext_gps2.setClickable(false);
            holder.  textinputtext_gps2.setFocusable(false);
            holder.  textinputtext_gps2.refreshDrawableState();
            holder.  textinputtext_gps2.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"gps2\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("gps2")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @SuppressLint("Range")
    private void gettextinputtext_gps1(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_gps1.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("gps1")));//gps1
            holder. textinputtext_gps1.setClickable(false);
            holder.  textinputtext_gps1.setFocusable(false);
            holder.  textinputtext_gps1.refreshDrawableState();
            holder.  textinputtext_gps1.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"  getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"gps1\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("gps1")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @SuppressLint("Range")
    private void gettextinputtext_adress(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_adress.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("adress")));//adress
            holder. textinputtext_adress.setClickable(false);
            holder.  textinputtext_adress.setFocusable(false);
            holder.  textinputtext_adress.refreshDrawableState();
            holder.  textinputtext_adress.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"  getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"adress\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("adress")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @SuppressLint("Range")
    private void gettextinputtext_city(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_city.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("city")));//city
            holder. textinputtext_city.setClickable(false);
            holder.  textinputtext_city.setFocusable(false);
            holder.  textinputtext_city.refreshDrawableState();
            holder.  textinputtext_city.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"city\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("city")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @SuppressLint("Range")
    private void gettextinputtext_iemi(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_iemi.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("iemi")));//iemi
            holder. textinputtext_iemi.setClickable(false);
            holder.  textinputtext_iemi.setFocusable(false);
            holder.  textinputtext_iemi.refreshDrawableState();
            holder.  textinputtext_iemi.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"iemi\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("iemi")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    @SuppressLint("Range")
    private void gettextinputtext_completedwork(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_completedwork.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("completedwork")));//completedwork
            holder. textinputtext_completedwork.setClickable(false);
            holder.  textinputtext_completedwork.setFocusable(false);
            holder.  textinputtext_completedwork.refreshDrawableState();
            holder.  textinputtext_completedwork.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"  getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"completedwork\"))"
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("completedwork")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }


    }

    @SuppressLint("Range")
    private void gettextinputtext_date_update(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder.textinputtext_date_update.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("date_update")));//date_update
            holder. textinputtext_date_update.setClickable(false);
            holder.  textinputtext_date_update.setFocusable(false);
            holder.  textinputtext_date_update.refreshDrawableState();
            holder.  textinputtext_date_update.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"date_update\"))"
                    +getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("date_update")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }

    @SuppressLint("Range")
    private void gettextinputtext_macdevice(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {

        try{
            holder. textinputtext_macdevice.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("macdevice")));//"macdevice"
            holder. textinputtext_macdevice.setClickable(false);
            holder.  textinputtext_macdevice.setFocusable(false);
            holder.  textinputtext_macdevice.refreshDrawableState();
            holder.  textinputtext_macdevice.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex(\"macdevice\")) "
                    + getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("macdevice")));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }


    // TODO: 18.07.2024 Методы главные заполения данные GATT client START
    // TODO: 18.07.2024 Методы главные заполения данные GATT client START
    // TODO: 18.07.2024 Методы главные заполения данные GATT client START
    // TODO: 18.07.2024 Методы главные заполения данные GATT client START

    @SuppressLint("Range")
    private void gettextinputtext_id_currentrow(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder. textinputtext_id_currentrow.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("current_table")));//todo "namedevice"
            holder. textinputtext_id_currentrow.setClickable(false);
            holder.  textinputtext_id_currentrow.setFocusable(false);
            holder.  textinputtext_id_currentrow.refreshDrawableState();
            holder.  textinputtext_id_currentrow.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getColumnIndex(\"namedevice\")" + getconcurrentHashMapCursor.getColumnIndex("namedevice"));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }
    @SuppressLint("Range")
    private void gettextinputtext_namedevice(@NonNull MyViewHolder holder, @NonNull Cursor getconcurrentHashMapCursor) {
        try{
            holder. textinputtext_namedevice.setText(getconcurrentHashMapCursor.getString(getconcurrentHashMapCursor.getColumnIndex("namedevice")));//todo "namedevice"
            holder. textinputtext_namedevice.setClickable(false);
            holder.  textinputtext_namedevice.setFocusable(false);
            holder.  textinputtext_namedevice.refreshDrawableState();
            holder.  textinputtext_namedevice.requestLayout();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    +"   getconcurrentHashMapCursor.getColumnIndex(\"namedevice\")" + getconcurrentHashMapCursor.getColumnIndex("namedevice"));
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    // TODO: 18.07.2024 Методы главные заполения данные GATT client END
    // TODO: 18.07.2024 Методы главные заполения данные GATT client END
    // TODO: 18.07.2024 Методы главные заполения данные GATT client END
    // TODO: 18.07.2024 Методы главные заполения данные GATT client END




















    //TODO метод делает callback с ответом на экран
    public void reBootrecyclerView() {
        try {
            recyclerViewServer.getAdapter().notifyDataSetChanged();
            recyclerViewServer.setClickable(false);
            recyclerViewServer.setFocusable(false);
            recyclerViewServer.requestLayout();
            recyclerViewServer.forceLayout();
            recyclerViewServer.refreshDrawableState();

            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }

    }



    public void rebootRecyreViewApdater(@NonNull ConcurrentHashMap<String, ContentValues> mapReceivedFromBootFragmentGatta,
                                        @NonNull   ConcurrentHashMap<String,Cursor>  concurrentHashMapCursor) {
        try{
         Cursor curcorServerGatt=   concurrentHashMapCursor  .values().stream().findAny().get();

            if (curcorServerGatt.getCount()>0) {
                recyclerViewServer.removeAllViewsInLayout();
                myRecycleViewAdapterServer.getconcurrentHashMapCursor=curcorServerGatt;

            myRecycleViewAdapterServer.notifyDataSetChanged();
            RecyclerView.Adapter recyclerViewadapter=         recyclerViewServer.getAdapter();
            recyclerViewadapter.notifyDataSetChanged();
            recyclerViewServer.swapAdapter(recyclerViewadapter,true);

            }
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " mapReceivedFromBootFragmentGatta " +mapReceivedFromBootFragmentGatta +" curcorServerGatt " +curcorServerGatt);
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }

    public void settingAnimatios(@NonNull  RecyclerView recyclerview_server_ble) {
        try {
            Animation animationscroll  = AnimationUtils.loadAnimation(context, R.anim.fadein_server);
            recyclerview_server_ble.startAnimation(animationscroll);
            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " recyclerview_server_ble  "+ recyclerview_server_ble);
            //TODO
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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }


    ///todo первый метод #1

    private void tabLayoutClick(@NonNull MyViewHolder holder) {
        try {
            holder.tabLayout_server_ble.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " itemView  " +holder.itemView);

                    tab.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                    " itemView  " +holder.itemView);
                        }
                    });
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " itemView  " +holder.itemView);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                            " itemView  " +holder.itemView);

                    tab.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                                    " itemView  " +holder.itemView);
                        }
                    });

                }
            });




            Log.d(context.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" holder " +holder.itemView);

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
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
    }






}
