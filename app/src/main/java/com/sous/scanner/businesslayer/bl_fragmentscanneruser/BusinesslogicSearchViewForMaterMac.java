package com.sous.scanner.businesslayer.bl_fragmentscanneruser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.Date;

public class BusinesslogicSearchViewForMaterMac {

    // TODO: 16.08.2024
    private AlertDialog alertDialog;
    private    ListView ListViewForSearchView;
    private   androidx.appcompat.widget.SearchView searchView;
    private  Cursor cursor;
    private  MaterialTextView materialTextViewListMacAdressMater;

    private Context context;
    private  long version;

    public BusinesslogicSearchViewForMaterMac(Cursor cursor, MaterialTextView materialTextViewListMacAdressMater, Context context, long version) {
        this.cursor = cursor;
        this.materialTextViewListMacAdressMater = materialTextViewListMacAdressMater;
        this.context = context;
        this.version = version;
    }


    @SuppressLint("Range")
    void landingsearchformacAddressesofmasters(@NonNull Cursor cursor,
                                         @NonNull String Столбик,
                                         @NonNull String ТаблицаТекущая,
                                         @NonNull String Спровочник,
                                         @NonNull MaterialTextView materialTextViewListMacAdressMater){

        alertDialog = new MaterialAlertDialogBuilder(context){
            private     MaterialButton ButtonFilterЗакрытьДиалог =null;
            @NonNull
            @Override
            public MaterialAlertDialogBuilder setView(View view) {
                try{
                    ButtonFilterЗакрытьДиалог =    (MaterialButton) view.findViewById(R.id.bottom_newscanner1);
                    ListViewForSearchView =    (ListView) view.findViewById(R.id.ListViewForNewOrderTransport);
                    searchView =    (androidx.appcompat.widget.SearchView) view.findViewById(R.id.searchview_newordertransport);
                    searchView.setQueryHint("Поиск "+Спровочник);
                    ListViewForSearchView.setTextFilterEnabled(true);
                    searchView.setDrawingCacheBackgroundColor(Color.GRAY);
                    searchView.setDrawingCacheEnabled(true);
                    searchView.setSubmitButtonEnabled(true);




                    TextView textViewСтрокаПосика = searchView.findViewById(com.google.android.material.R.id.search_src_text);




                    textViewСтрокаПосика.setTextColor(Color.rgb(0,102,102));
                    textViewСтрокаПосика.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    searchView.refreshDrawableState();

                    ///TODO ГЛАВНЫЙ АДАПТЕР чата
                    SimpleCursorAdapter simpleCursorForSearchView =
                            new SimpleCursorAdapter(getContext(),
                                    R.layout.simple_newspinner_dwonload_newfiltersearch, cursor, new String[]{ Столбик,"_id"},
                                    new int[]{android.R.id.text1,android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                    SimpleCursorAdapter.ViewBinder БиндингДляПоиск = new SimpleCursorAdapter.ViewBinder(){

                        @Override
                        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                            switch (view.getId()) {
                                case android.R.id.text1:
                                    Log.d(this.getClass().getName()," position");
                                    if (cursor.getCount()>0) {
                                        try{
                                            MaterialTextView materialTextViewЭлементСписка=(MaterialTextView) view;
                                            // TODO: 13.12.2022  производим состыковку
                                            Integer   getId = cursor.getInt(cursor.getColumnIndex("_id"));
                                            if (getId>0) {
                                                Long UUIDGetFilter = cursor.getLong(cursor.getColumnIndex("uuid"));
                                                String  getName = cursor.getString(cursor.getColumnIndex(Столбик)).trim();
                                                Bundle bundle=new Bundle();
                                                bundle.putInt("getId",getId);
                                                bundle.putString("getName",getName);
                                                bundle.putLong("getUUID",UUIDGetFilter);
                                                // TODO: 16.05.2023 Элемент Заполяем данными  TAG
                                                materialTextViewЭлементСписка.setTag(bundle);
                                                // TODO: 20.01.2022
                                                Log.d(this.getClass().getName()," getName "+getName + " getId " +getId  + " UUIDGetFilter " +UUIDGetFilter+ " bundle "+bundle);
                                                boolean ДлинаСтрокивСпиноре = getName.length() >40;
                                                if (ДлинаСтрокивСпиноре==true) {
                                                    StringBuffer sb = new StringBuffer(getName);
                                                    sb.insert(40, System.lineSeparator());
                                                    getName = sb.toString();
                                                    Log.d(getContext().getClass().getName(), " getName " + "--" + getName);/////
                                                }
                                                // TODO: 16.05.2023 Элемент Заполяем данными
                                                materialTextViewЭлементСписка.setText(getName);
                                                materialTextViewЭлементСписка.startAnimation(animation);
                                            }
                                            Log.d(getContext().getClass().getName(), "\n"
                                                    + " время: " + new Date() + "\n+" +
                                                    " Класс в процессе... " + this.getClass().getName() + "\n" +
                                                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                                    "  ((MaterialTextView)view) " +  materialTextViewЭлементСписка);
                                            // TODO: 13.12.2022 филь
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                            new   Class_Generation_Errors( getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                                    this.getClass().getName(),
                                                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
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
                    simpleCursorForSearchView.notifyDataSetChanged();
                    ListViewForSearchView.setAdapter(simpleCursorForSearchView);
                    ListViewForSearchView.setSelection(0);
                    ListViewForSearchView.startAnimation(animation);
                    ListViewForSearchView.refreshDrawableState();
                    ListViewForSearchView.requestLayout();

                    // TODO: 13.12.2022  Поиск и его слушель
                    МетодПоискаФильтр(  ТаблицаТекущая,             simpleCursorForSearchView );

                    // TODO: 15.05.2023 Слушатель Действия Кнопки Сохранить
                    // TODO: 16.05.2023  КЛИК СЛУШАТЕЛЬ ПО ЕЛЕМЕНТУ
                    методКликПоЗаказуOrder(  );

                    методКликДейсвиеКнопкиСохранить(ButtonFilterЗакрытьДиалог);

                    Log.d(this.getContext().getClass().getName(), "\n"
                            + " время: " + new Date()+"\n+" +
                            " Класс в процессе... " +
                            this.getContext().getClass().getName()+"\n"+
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new   Class_Generation_Errors(this.getContext())
                            .МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                    this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                }

                return super.setView(view);
                // TODO: 20.12.2022  тут конец выбеленого
            }
            // TODO: 16.05.2023  КЛИК ПО ЕЛЕМЕНТУ
            private void методКликПоЗаказуOrder( ) {
                try{
                    ListViewForSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try{
                                MaterialTextView materialTextViewGetElement=(MaterialTextView)       parent.getAdapter().getView(position, view,parent );
                                if(materialTextViewGetElement!=null){
                                    Bundle bundlePepoles= (Bundle) materialTextViewGetElement.getTag();
                                    materialTextViewGetElement.startAnimation(animationvibr1);
                                    // TODO: 16.05.2023 Из Выбраного Элемента Получаеним ДАнные
                                    Integer getId=      bundlePepoles.getInt("getId",0);
                                    String getName=   bundlePepoles.getString("getName","").trim();
                                    Long getUUID =   bundlePepoles.getLong("getUUID",0l);
                                    materialTextViewParent.setTag(bundlePepoles);
                                    materialTextViewParent.setText(getName);
                                    // TODO: 15.05.2023 ЗАПОЛЕНИЕ ДАННЫМИ КЛИК
                                    materialTextViewParent.startAnimation(animation);
                                    // TODO: 15.05.2023  ЗАКРЫВАЕТ
                                    alertDialogNewOrderTranport.cancel();
                                    alertDialogNewOrderTranport.dismiss();
                                    Log.d(getContext().getClass().getName(), "\n"
                                            + " время: " + new Date() + "\n+" +
                                            " Класс в процессе... " + this.getClass().getName() + "\n" +
                                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() +

                                            Log.d(getContext().getClass().getName(), "\n"
                                                    + " время: " + new Date()+"\n+" +
                                                    " Класс в процессе... " +
                                                    getContext().getClass().getName()+"\n"+
                                                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                                        Thread.currentThread().getStackTrace()[2].getMethodName() +
                                        " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                                new   Class_Generation_Errors( getContext())
                                        .МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                                this.getClass().getName(),
                                                Thread.currentThread().getStackTrace()[2].getMethodName(),
                                                Thread.currentThread().getStackTrace()[2].getLineNumber());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                    new   Class_Generation_Errors( getContext())
                            .МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                    this.getClass().getName(),
                                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                                    Thread.currentThread().getStackTrace()[2].getLineNumber());
                }
            }


        }
                .setTitle(Спровочник)
                .setCancelable(false)
                .setIcon( R.drawable.icon_newscannertwo)
                .setView(getLayoutInflater().inflate( R.layout.simple_for_new_spinner_searchview_newordertransport2, null )).show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(    alertDialogNewOrderTranport.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height =WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        alertDialogNewOrderTranport.getWindow().setAttributes(layoutParams);
        // TODO: 13.12.2022 ВТОРОЙ СЛУШАТЕЛЬ НА КНОПКУ
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

    }






    private void МетодПоискаФильтр(@NonNull String ТаблицаДляФильтра,@NonNull     SimpleCursorAdapter          simpleCursorForSearchView ) {
        try{
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    try{
                        Log.d(this.getClass().getName()," position");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new   Class_Generation_Errors( getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try{
                        Log.d(this.getClass().getName()," position");
                        if (newText.length() > 0) {
                            FilterQueryProvider filter = simpleCursorForSearchView.getFilterQueryProvider();
                            filter.runQuery(newText);
                            return true;
                        }else {
                            // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР
                            методПерезаполенияПоискаИзФилтра(cursor,             simpleCursorForSearchView );
                            Log.d(getContext() .getClass().getName(), "\n"
                                    + " время: " + new Date()+"\n+" +
                                    " Класс в процессе... " +   getContext().getClass().getName()+"\n"+
                                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " cursor "+ cursor);
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new   Class_Generation_Errors(getContext() ).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                    return false;
                }
            });
            simpleCursorForSearchView.setFilterQueryProvider(new FilterQueryProvider() {
                @Override
                public Cursor runQuery(CharSequence constraint) {
                    final Cursor[] cursorFilter = {null};
                    try{
                        message.getTarget().post(()->{
                            ///   cursorFilter[0] =    simpleCursorForSearchView.getCursor();
                            cursorFilter[0] =          subClassNewOrderTransport
                                    .методGetAllLike(ТаблицаТекущая,Столбик,constraint.toString());

                            // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР   // TODO: 15.05.2023 ПЕРЕПОЛУЧАЕМ НОВЫЕ ДАННЫЕ КУРСОР
                            методПерезаполенияПоискаИзФилтра(cursorFilter[0],          simpleCursorForSearchView );
                            // TODO: 16.05.2023
                            Log.d(getContext() .getClass().getName(), "\n"
                                    + " время: " + new Date()+"\n+" +
                                    " Класс в процессе... " +   getContext().getClass().getName()+"\n"+
                                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " cursorFilter "+ cursorFilter[0]);

                        });
                        Log.d(getContext() .getClass().getName(), "\n"
                                + " время: " + new Date()+"\n+" +
                                " Класс в процессе... " +   getContext().getClass().getName()+"\n"+
                                " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " cursorFilter "+ cursorFilter[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                                " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                        new   Class_Generation_Errors( getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                                this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
                    }
                    return cursorFilter[0];
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors( getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
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
                ListViewForSearchView.setSelection(0);
                View filter=       ListViewForSearchView.getChildAt(0);
                if(filter!=null){
                    ((MaterialTextView)filter).startAnimation(animationvibr1);
                }
            }else{
                searchView.setBackgroundColor(Color.RED);
                message.getTarget().postDelayed(() -> {
                    searchView.setBackgroundColor(Color.parseColor("#F2F5F5"));
                }, 500);
            }
            // TODO: 16.05.2023
            ListViewForSearchView.refreshDrawableState();
            ListViewForSearchView.forceLayout();
            Log.d(getContext() .getClass().getName(), "\n"
                    + " время: " + new Date()+"\n+" +
                    " Класс в процессе... " +   getContext().getClass().getName()+"\n"+
                    " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName() + " cursorFilter "+ cursorFilter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors( getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    private void методКликДейсвиеКнопкиСохранить( @NonNull MaterialButton materialButtonFilterЗакрытьДиалог) {
        try{
            materialButtonFilterЗакрытьДиалог.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogNewOrderTranport.dismiss();
                    alertDialogNewOrderTranport.cancel();

                    Log.d(materialButtonFilterЗакрытьДиалог.getContext().getClass().getName(), "\n"
                            + " время: " + new Date()+"\n+" +
                            " Класс в процессе... " +  materialButtonFilterЗакрытьДиалог.getContext().getClass().getName()+"\n"+
                            " метод в процессе... " + Thread.currentThread().getStackTrace()[2].getMethodName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new   Class_Generation_Errors(materialButtonFilterЗакрытьДиалог.getContext()).МетодЗаписиВЖурналНовойОшибки(e.toString(),
                    this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
    // TODO: 15.05.2023 КОНЕЦ НОВОГО ПОСИКА
}

}
