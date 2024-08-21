package com.sous.scanner.businesslayer.bl_fragmentscanneruser;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.sous.scanner.R;
import com.sous.scanner.businesslayer.Errors.SubClassErrors;
import com.sous.scanner.datalayer.bl_DataBase.BusinesslogicDatabase;

import java.util.Date;

public class BusinesslogicSelectMacAdressGattServer {

    // TODO: 16.08.2024
    private    ListView ListViewForSearchViewGattMacList;
    private   SearchView searchViewMacAdress;
    private Context context;
    private  long version;
    private Message messageClient;
    private Animation animation,animationvibr1;
    private AlertDialog   alertDialogMacAdress;
    private LayoutInflater layoutInflater;
    private  MaterialTextView searchview_maclistdeviceserver;
    private  Cursor cursor;
    private  String MacTable="listMacMastersSous";

    private    BusinesslogicDatabase  businesslogicDatabase;

    public BusinesslogicSelectMacAdressGattServer( @NonNull  Context context,
                                                   @NonNull  long version,
                                                   @NonNull   Message messageClient,
                                                   @NonNull   LayoutInflater layoutInflater,
                                                   @NonNull MaterialTextView searchview_maclistdeviceserver) {
        this.context = context;
        this.version = version;
        this.messageClient = messageClient;
        this.layoutInflater = layoutInflater;
        this.searchview_maclistdeviceserver = searchview_maclistdeviceserver;
        // TODO: 19.08.2024
        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row_newscanner1);
        animationvibr1 = AnimationUtils.loadAnimation(context,R.anim.slide_singletable2);//
    }


    @SuppressLint("Range")
  public   void selectiongMacAdressGattServer( ){

        // TODO: 19.08.2024
        try {
        alertDialogMacAdress = new MaterialAlertDialogBuilder(context){
            private     MaterialButton ButtonFilterЗакрытьДиалог =null;
            @NonNull
            @Override
            public MaterialAlertDialogBuilder setView(View view) {
                try{
                    ButtonFilterЗакрытьДиалог =    (MaterialButton) view.findViewById(R.id.bottom_newscanner1);
                    ListViewForSearchViewGattMacList =    (ListView) view.findViewById(R.id.id_listview_macgatt);
                    searchViewMacAdress =    (SearchView) view.findViewById(R.id.searchview_newordertransport);
                    searchViewMacAdress.setQueryHint("Поиск..");
                    ListViewForSearchViewGattMacList.setTextFilterEnabled(true);

                    Drawable drawable = context.getResources().getDrawable(R.drawable.style_for_newstypespinners_ietm6);

                    searchViewMacAdress.setDividerDrawable(drawable);
                    searchViewMacAdress.setDrawingCacheEnabled(true);
                    searchViewMacAdress.setSubmitButtonEnabled(true);


                    Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");


                    TextView textViewСтрокаПосика = searchViewMacAdress.findViewById(com.google.android.material.R.id.search_src_text);


                    // TODO: 19.08.2024
                    textViewСтрокаПосика.setTextColor(Color.rgb(0,102,102));
                    textViewСтрокаПосика.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    searchViewMacAdress.refreshDrawableState();
                    // TODO: 19.08.2024

                    // TODO: 19.08.2024  поулчение жданны
                       businesslogicDatabase=new BusinesslogicDatabase(context,version);
                     cursor=   businesslogicDatabase.getingCursor("SELECT * FROM listMacMastersSous ");

                    Log.d(getContext().getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+" textViewСтрокаПосика " +textViewСтрокаПосика +" cursor " +cursor );


                    // TODO: 20.08.2024  создание адапрета, Если Есть ДАнные


                    currentpopulationofListViewMacdata();


                    Log.d(this.getContext().getClass().getName(), "\n"
                            + " время: " + new Date()+"\n+" +
                            " Класс в процессе... " +
                            this.getContext().getClass().getName()+"\n"+
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());

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

                return super.setView(view);
                // TODO: 20.12.2022  тут конец выбеленого
            }

            private void currentpopulationofListViewMacdata() {
                try{
                if (cursor.getCount()>0) {
                    // TODO: 21.08.2024
                    SimpleCursorAdapter    simpleCursorForSearchView = fillingwhenDataisavailable();

                    // TODO: 13.12.2022  Поиск и его слушель
                    МетодПоискаФильтр(             simpleCursorForSearchView );

                    // TODO: 15.05.2023 Слушатель Действия Кнопки Сохранить
                    // TODO: 16.05.2023  КЛИК СЛУШАТЕЛЬ ПО ЕЛЕМЕНТУ
                    clickGattMacList(  );

                    методКликДейсвиеКнопкиСохранить(ButtonFilterЗакрытьДиалог);


                } else {

                    fillingwhenthereisNodata();

                }


                finaloperationforListView();

                Log.d(this.getContext().getClass().getName(), "\n"
                        + " время: " + new Date()+"\n+" +
                        " Класс в процессе... " +
                        this.getContext().getClass().getName()+"\n"+
                        " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());

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









            private void finaloperationforListView() {
                ListViewForSearchViewGattMacList.setForegroundGravity(Gravity.CENTER);
                ListViewForSearchViewGattMacList.setSelection(0);
                ListViewForSearchViewGattMacList.startAnimation(animation);
                ListViewForSearchViewGattMacList.refreshDrawableState();
                ListViewForSearchViewGattMacList.requestLayout();
            }

            private void fillingwhenthereisNodata() {
                View footerView = layoutInflater.inflate(R.layout.fragment_server_dont_data_oncreateviewholder,
                        ListViewForSearchViewGattMacList, false);

                MaterialCardView mSearchMoreBtn = (MaterialCardView) footerView
                        .findViewById(R.id.id_card_server_dont_data_oncreateviewholder);
                // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,100,0,0);
                mSearchMoreBtn.setChecked(true);
                mSearchMoreBtn.setLayoutParams(params);
                mSearchMoreBtn.setLayoutParams(params);
                mSearchMoreBtn.setForegroundGravity(Gravity.CENTER);
                footerView.requestLayout();
                footerView.refreshDrawableState();
                ListViewForSearchViewGattMacList.addFooterView(footerView);
            }


        }
                .setTitle("Выберете Mac-адрес")
                .setCancelable(false)
                .setIcon( R.drawable.icon_newscannertwo)
                .setView(layoutInflater.inflate( R.layout.simple_for_mac_adress_gatt_searchview, null )).show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(    alertDialogMacAdress.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height =WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        alertDialogMacAdress.getWindow().setAttributes(layoutParams);
        // TODO: 13.12.2022 ВТОРОЙ СЛУШАТЕЛЬ НА КНОПКУ
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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

    // TODO: 16.05.2023  КЛИК ПО ЕЛЕМЕНТУ
    private void clickGattMacList( ) {
            ListViewForSearchViewGattMacList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        MaterialCardView cardViewMAcs=(MaterialCardView)       parent.getAdapter().getView(position, view,parent );
                        // TODO: 20.08.2024
                        if(cardViewMAcs!=null){
                            // TODO: 20.08.2024
                            MaterialTextView materialTextVieMac=(MaterialTextView) cardViewMAcs.findViewById( R.id.id_mac);
                            String getName= null;
                            String geMAc= null;
                            if (materialTextVieMac!=null) {
                                materialTextVieMac.startAnimation(animation);
                                Bundle bundlePepoles= (Bundle) materialTextVieMac.getTag();
                                materialTextVieMac.startAnimation(animationvibr1);
                                // TODO: 16.05.2023 Из Выбраного Элемента Получаеним ДАнные
                                Integer getId=      bundlePepoles.getInt("getId",0);
                                getName = bundlePepoles.getString("getName","").trim();
                                geMAc = bundlePepoles.getString("geMAc","").trim();
                                Long getUUID =   bundlePepoles.getLong("getUUID",0l);
                                // TODO: 19.08.2024
                                searchview_maclistdeviceserver.setTag(bundlePepoles);
                                searchview_maclistdeviceserver.setText(getName);
                                // TODO: 15.05.2023 ЗАПОЛЕНИЕ ДАННЫМИ КЛИК
                                searchview_maclistdeviceserver.startAnimation(animationvibr1);
                                searchview_maclistdeviceserver.refreshDrawableState();
                                searchview_maclistdeviceserver.requestLayout();
                                // TODO: 15.05.2023  ЗАКРЫВАЕТ
                                alertDialogMacAdress.cancel();
                                alertDialogMacAdress.dismiss();
                            }


                            Log.d(context.getClass().getName(), "\n"
                                            + " время: " + new Date()+"\n+" +
                                            " Класс в процессе... " +
                                           context.getClass().getName()+"\n"+
                                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()+
                                            " geMAc " +geMAc+ " getName " +getName);
                        }
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
    }



    @SuppressLint("Range")
    @NonNull
    private SimpleCursorAdapter fillingwhenDataisavailable() {
        ///TODO ГЛАВНЫЙ АДАПТЕР чата
        SimpleCursorAdapter simpleCursorForSearchView=null;
        try{
            simpleCursorForSearchView =
                    new SimpleCursorAdapter(context,
                            R.layout.simple_newspinner_dwonload_newfiltersearch, cursor, new String[]{  "name","macadress"},
                            new int[]{android.R.id.text1,android.R.id.text2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            SimpleCursorAdapter.ViewBinder БиндингДляПоиск = new SimpleCursorAdapter.ViewBinder(){

                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    switch (view.getId()) {
                        case android.R.id.text1:
                            Log.d(this.getClass().getName()," position");
                            if (cursor.getCount()>0) {
                                try{
                                    MaterialCardView cardViewMAcs=(MaterialCardView) view.findViewById(android.R.id.text1);
                                    MaterialTextView materialTextVieMac=(MaterialTextView) cardViewMAcs.findViewById( R.id.id_mac);
                                    MaterialTextView materialTextVieMacsub=(MaterialTextView) cardViewMAcs.findViewById(R.id.id_macsub);
                                    // TODO: 13.12.2022  производим состыковку
                                   Integer   getId = cursor.getInt(cursor.getColumnIndex("_id"));
                                    if (getId>0) {
                                        Long UUIDGetFilter = cursor.getLong(cursor.getColumnIndex("uuid"));
                                        String  getName = cursor.getString(cursor.getColumnIndex("name")).trim();
                                        String  geMAc = cursor.getString(cursor.getColumnIndex("macadress")).trim();
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("getId",getId);
                                        bundle.putString("getName",getName);
                                        bundle.putString("geMAc",geMAc);
                                        bundle.putLong("getUUID",UUIDGetFilter);
                                        // TODO: 16.05.2023 Элемент Заполяем данными  TAG
                                        materialTextVieMac.setTag(bundle);
                                        // TODO: 20.01.2022
                                        Log.d(this.getClass().getName()," getName "+getName + " getId " +getId  + " UUIDGetFilter " +UUIDGetFilter+ " bundle "+bundle);
                                        boolean ДлинаСтрокивСпиноре = getName.length() >40;
                                        if (ДлинаСтрокивСпиноре==true) {
                                            StringBuffer sb = new StringBuffer(getName);
                                            sb.insert(40, System.lineSeparator());
                                            getName = sb.toString();
                                            Log.d(context.getClass().getName(), " getName " + "--" + getName);/////
                                        }
                                        // TODO: 16.05.2023 Элемент Заполяем данными
                                        materialTextVieMac.setText(getName);
                                        materialTextVieMacsub.setText(geMAc);
                                        materialTextVieMac.startAnimation(animationvibr1);
                                        materialTextVieMac.requestLayout();


                                        Log.d(context.getClass().getName(), "\n"
                                                + " время: " + new Date() + "\n+" +
                                                " Класс в процессе... " + this.getClass().getName() + "\n" +
                                                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                                "getName " +getName +  " geMAc " +geMAc );
                                    }
                                    Log.d(context.getClass().getName(), "\n"
                                            + " время: " + new Date() + "\n+" +
                                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() );
                                    // TODO: 13.12.2022 филь
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
                                return true;
                            } else {
                                Log.d(this.getClass().getName()," position");
                                return false;
                            }
                    }
                    return false;
                }
            };
             simpleCursorForSearchView.setViewBinder(БиндингДляПоиск);
            simpleCursorForSearchView.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            simpleCursorForSearchView.notifyDataSetChanged();
            ListViewForSearchViewGattMacList.setAdapter(simpleCursorForSearchView);

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

        return simpleCursorForSearchView;
    }

















    private void МетодПоискаФильтр( @NonNull     SimpleCursorAdapter          simpleCursorForSearchViewGattMacList ) {
        try{
            searchViewMacAdress.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    try{
                        // TODO: 13.12.2022 ВТОРОЙ СЛУШАТЕЛЬ НА КНОПКУ
                        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
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
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try{
                        Log.d(this.getClass().getName()," position");
                        if (newText.length() > 0) {
                            FilterQueryProvider filter = simpleCursorForSearchViewGattMacList.getFilterQueryProvider();
                            filter.runQuery(newText);
                            return true;
                        }else {
                            // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР
                            методПерезаполенияПоискаИзФилтра(cursor,             simpleCursorForSearchViewGattMacList );

                            Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

                            return true;
                        }
                        // TODO: 13.12.2022 ВТОРОЙ СЛУШАТЕЛЬ НА КНОПКУ
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
                    return false;
                }
            });
            simpleCursorForSearchViewGattMacList.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    // TODO: 20.08.2024
                    Cursor   cursorFilter=null;
                    try{
                            // TODO: 19.08.2024  поулчение жданны
                            businesslogicDatabase=new BusinesslogicDatabase(context,version);
                        cursorFilter=   businesslogicDatabase.getingCursor("  SELECT * FROM listMacMastersSous  WHERE name  LIKE  '%"+constraint+"%'");
                            // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР
                            методПерезаполенияПоискаИзФилтра(cursorFilter,          simpleCursorForSearchViewGattMacList );
                            // TODO: 16.05.2023
                            Log.d(context .getClass().getName(), "\n"
                                    + " время: " + new Date()+"\n+" +
                                    " Класс в процессе... " +   context.getClass().getName()+"\n"+
                                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()
                                    + " cursorFilter "+ cursorFilter+" constraint " +constraint);

                        // TODO: 13.12.2022 ВТОРОЙ СЛУШАТЕЛЬ НА КНОПКУ
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
                    return cursorFilter;
                }
            });
            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"+
                    " cursor  "+cursor );
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


    // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР
    private void методПерезаполенияПоискаИзФилтра(Cursor cursorFilter ,@NonNull     SimpleCursorAdapter          simpleCursorForSearchView ) {
        try {
            simpleCursorForSearchView.swapCursor(cursorFilter);
            // TODO: 16.05.2023 reboot disain
            simpleCursorForSearchView.notifyDataSetChanged();
            // TODO: 16.05.2023 Если данные Естьв Фильтре
            if(cursorFilter !=null && cursorFilter.getCount()>0) {
                ListViewForSearchViewGattMacList.setSelection(0);
            }else{
                searchViewMacAdress.setBackgroundColor(Color.RED);
                messageClient.getTarget().postDelayed(() -> {
                    // TODO: 19.08.2024
                    searchViewMacAdress.setBackgroundColor(Color.parseColor("#F2F5F5"));
                }, 500);
            }
            // TODO: 16.05.2023
            ListViewForSearchViewGattMacList.refreshDrawableState();
            ListViewForSearchViewGattMacList.forceLayout();
            ListViewForSearchViewGattMacList.requestLayout();
            // TODO: 19.08.2024
            Log.d(context .getClass().getName(), "\n"
                    + " время: " + new Date()+"\n+" +
                    " Класс в процессе... " +   context.getClass().getName()+"\n"+
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " cursorFilter "+ cursorFilter);
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

    private void методКликДейсвиеКнопкиСохранить( @NonNull MaterialButton materialButtonFilterЗакрытьДиалог) {
        try{
            materialButtonFilterЗакрытьДиалог.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogMacAdress.dismiss();
                    alertDialogMacAdress.cancel();
                    Log.d(materialButtonFilterЗакрытьДиалог.getContext().getClass().getName(), "\n"
                            + " время: " + new Date()+"\n+" +
                            " Класс в процессе... " +  materialButtonFilterЗакрытьДиалог.getContext().getClass().getName()+"\n"+
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }
            });
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
    // TODO: 15.05.2023 КОНЕЦ НОВОГО ПОСИКА
}


