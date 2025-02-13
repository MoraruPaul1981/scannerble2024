package com.dsy.dsu.SynsProccessor.PrograsBarAsync;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dsy.dsu.BootAndAsync.BlBootAsync.SendMainActivity;
import com.dsy.dsu.BootAndAsync.EventsBus.MessageEvensBusPrograssBar;
import com.dsy.dsu.BusinessLogicAll.Class_Visible_Processing_Async;
import com.dsy.dsu.CnangeServers.PUBLIC_CONTENT;
import com.dsy.dsu.Errors.controller.RecordNewErros;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.CopyOnWriteArrayList;

public class GetPrograssbarChangeIndicator implements  InPrograssBars {
Context context;

    public GetPrograssbarChangeIndicator(Context context) {
        this.context = context;
    }

    @Override
    public void setAsyncrograssbarMap(@NonNull String имяТаблицаAsync) {
        try {

            //TODO Таблицы ОТ  Андройда
            CopyOnWriteArrayList<String>      getMainTabelAllAndroid = new PUBLIC_CONTENT(context).getWorkerTablesALl(context);

            int Проценты;
            Integer ПозицияТекущейТаблицы=      getMainTabelAllAndroid.indexOf(имяТаблицаAsync)+1;
            Проценты = new Class_Visible_Processing_Async(context).
                    ГенерируемПРОЦЕНТЫДляAsync(ПозицияТекущейТаблицы, getMainTabelAllAndroid.size());


            // TODO: 22.01.2024 текущее отобраение процентов
            методCallBackPrograssBars(  Проценты,имяТаблицаAsync, ПозицияТекущейТаблицы,getMainTabelAllAndroid.size()  );

            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()+
                    " Проценты " +Проценты+" имяТаблицаAsync " +имяТаблицаAsync);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    public void setAsyncrograssbarList( @NonNull CopyOnWriteArrayList<String>   NameTableAsync,@NonNull String имяТаблицаAsync,
                                        @NonNull Integer SuccessInsertOrUpdates) {
        try {
            int Проценты;

            Integer ПозицияТекущейТаблицы=      NameTableAsync.indexOf(имяТаблицаAsync)+1;
            Проценты = new Class_Visible_Processing_Async(context).
                    ГенерируемПРОЦЕНТЫДляAsync(ПозицияТекущейТаблицы, NameTableAsync.size());

            // TODO: 22.01.2024 текущее отобраение процентов
            методCallBackPrograssBars(  Проценты,имяТаблицаAsync, ПозицияТекущейТаблицы,NameTableAsync.size()  );

            Log.d(this.getClass().getName(),"\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber()+
                    " Проценты " +Проценты+" NameTableAsync " +NameTableAsync);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }

    @Override
    public void методCallBackPrograssBars(@NonNull int Проценны,
                                          @NonNull String имяТаблицаAsync,
                                          @NonNull Integer ПозицияТекущейТаблицы,
                                          @NonNull int  maxAllCountRow) {


        try {
            class SendUserДанныеДляPrograssbar extends SendMainActivity {

                public SendUserДанныеДляPrograssbar(Context context) {
                    super(context);
                }

                @Override
                public void startSendBroadSesiver() {
                    //  super.startSendBroadSesiver();
                    intentComunications.setAction("Broad_messageAsyncPrograssBar");
                    bundleComunications.putString("Статус" ,"AsyncPrograssBar");
                    bundleComunications.putInt("Проценны" ,Проценны);
                    bundleComunications.putString("имятаблицы" ,имяТаблицаAsync);
                    bundleComunications.putInt("maxtables" ,  maxAllCountRow );
                    bundleComunications.putInt("currentposition" ,ПозицияТекущейТаблицы);
                    intentComunications.putExtras(bundleComunications);

                    // TODO: 25.09.2024 call back AN Screnn User Boot Activity
                     EventBus.getDefault().post(new MessageEvensBusPrograssBar(intentComunications));
                   // MessageEvensBusPrograssBar messageEvensBusPrograssBar  =     new MessageEvensBusPrograssBar(intentComunications);
                    // TODO: 25.09.2024  запускаем слушателя обратного отоброжкнк Prograsbar
                    //new PrograssBarManagerBloadcastReciever().setBroadcastManagerPrograssBar(context,intentComunications);

                }
            }
            // TODO: 22.01.2024 когда режим офлайн
            new SendUserДанныеДляPrograssbar(context).startSendBroadSesiver();


            Log.d(this.getClass().getName(), "\n" + " class " +
                    Thread.currentThread().getStackTrace()[2].getClassName()
                    + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " Проценны " +Проценны);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }


    }




}
