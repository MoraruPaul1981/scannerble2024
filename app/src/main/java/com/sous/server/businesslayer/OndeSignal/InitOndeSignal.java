package com.sous.server.businesslayer.OndeSignal;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.onesignal.OneSignal;
import com.sous.server.businesslayer.Errors.SubClassErrors;
import com.sous.server.businesslayer.Firebases.MyFirebaseMessagingServiceServerScanners;

import java.util.HashMap;
import java.util.Map;

public class InitOndeSignal {

  protected Context context;

    protected Long version;

    public InitOndeSignal(Context context, Long version) {
        this.context = context;
        this.version = version;
    }


    public  String starttinggetkeyOndeSignal(@NonNull String КлючДляFirebaseNotification) {
        String ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal = null;
        try {
            // TODO: 23.12.2021 ЧЕТЫРЕ ПОПЫТКИ ПОДКЛЮЧЕНИЕ В СЕВРЕРУONESIGNAL
            //TODO srating......  oneSignal
            Log.d(this.getClass().getName(), "  КЛЮЧ ДЛЯ Scanner  OneSignal........ "+ КлючДляFirebaseNotification +"\n");
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            // todo OneSignal Initialization
            OneSignal.initWithContext(context);
            ///////todo srating Google Notifications wits PUblic Key
            OneSignal.setAppId(КлючДляFirebaseNotification);
            OneSignal.disablePush(false);
            //TODO srating.......... firebase cloud --ПРИШЛО СООБЩЕНИЕ
            FirebaseMessagingService firebaseMessagingService =new MyFirebaseMessagingServiceServerScanners();
            //TODO srating......  oneSignal
            Log.d(this.getClass().getName(), "  FirebaseMessagingService"  );
            // TODO: 07.12.2021
            firebaseMessagingService.onNewToken("Сообщения от Firebase Cloud Google ");
            Log.d(this.getClass().getName(), "  КЛЮЧ ДЛЯ SERVER BLE КОНЕЦ  OneSignal........  220d6edf-2b29-453e-97a8-d2aefe4a9eb0 " );
            // TODO: 15.12.2021 настройки onesigmnal
            Map<String, String> params = new HashMap<String, String>();
            OneSignal.sendTag("Authorization", "Basic 220d6edf-2b29-453e-97a8-d2aefe4a9eb0");
            OneSignal.sendTag("Content-type", "application/json");
            OneSignal.sendTag("grp_msg", "serverscanners");
            OneSignal.sendTag("android_background_data", "true");
            OneSignal.sendTag("content_available", "true");
            // TODO: 14.02.2023 получаем uuid для onesingal
            String tokenOneSignal=   OneSignal.getDeviceState().getPushToken();
            String   tokenSmsNumber=   OneSignal.getDeviceState().getSMSNumber();
            //TODO srating......  oneSignal
            ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal = OneSignal.getDeviceState().getUserId();
            // TODO: 15.12.2021
            Log.d(this.getClass().getName(), "  ПОСЛЕ КЛЮЧ ДЛЯ SERVER SCANNER  OneSignal........  220d6edf-2b29-453e-97a8-d2aefe4a9eb0  "+"\n"+
                    "   OneSignal.getTriggerValueForKey(\"GT_PLAYER_ID\"); " + OneSignal.getTriggerValueForKey("GT_PLAYER_ID")+
                    "     OneSignal.getTriggers() " +   OneSignal.getTriggers()+"\n"+
                    "    ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal ОТ СЕРВЕРА ::: " + ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal+ "tokenOneSignal" +tokenOneSignal+
                    " tokenSmsNumber " +tokenSmsNumber );
            // TODO: 13.12.2021
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            ContentValues valuesЗаписываемОшибки=new ContentValues();
            valuesЗаписываемОшибки.put("Error",e.toString().toLowerCase());
            valuesЗаписываемОшибки.put("Klass",this.getClass().getName());
            valuesЗаписываемОшибки.put("Metod",Thread.currentThread().getStackTrace()[2].getMethodName());
            valuesЗаписываемОшибки.put("LineError",   Thread.currentThread().getStackTrace()[2].getLineNumber());
            final Object ТекущаяВерсияПрограммы = version;
            Integer   ЛокальнаяВерсияПОСравнение = Integer.parseInt(ТекущаяВерсияПрограммы.toString());
            valuesЗаписываемОшибки.put("whose_error",ЛокальнаяВерсияПОСравнение);
            new SubClassErrors(context).МетодЗаписиОшибок(valuesЗаписываемОшибки);
        }
        return ПоулчаемДляТекущегоПользователяIDОтСЕРВРЕРАOneSignal;

    }

}
