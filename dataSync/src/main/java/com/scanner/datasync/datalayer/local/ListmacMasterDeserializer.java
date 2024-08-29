package com.scanner.datasync.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Consumer;

import io.reactivex.rxjava3.core.Completable;

public class ListmacMasterDeserializer  extends JsonDeserializer<listMacMastersSousListmacmasterssouslistMacMastersSous>
{
  private   listMacMastersSousListmacmasterssouslistMacMastersSous list;
    @Override
    public listMacMastersSousListmacmasterssouslistMacMastersSous deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        // TODO: 29.08.2024
        Completable.fromAction(()->{
                    // TODO: 29.08.2024 создаем новы экзепляр класс
                    list= new listMacMastersSousListmacmasterssouslistMacMastersSous();
                    // TODO: 29.08.2024
                    JsonNode node = p.getCodec().readTree(p);
                    JsonNode jsonNode=   node.deepCopy();

                    // TODO: 29.08.2024  id
                      Integer id= jsonNode.findValue("id").asInt();
                    list.setId(id);
                    // TODO: 29.08.2024  name
                    String name=   jsonNode.findValue("name").asText().trim();
                    list.setName(name);
                    // TODO: 29.08.2024  mac adress
                    String macadress=    jsonNode.findValue("macadress").asText().trim();
                    list.setMacadress(macadress);
                    // TODO: 29.08.2024  plot
                    Long plot=   jsonNode.findValue("plot").asLong();
                    list.setPlot(plot);

                    // TODO: 29.08.2024  date_update
                    String  date_update=  jsonNode.findValue("date_update").asText().trim();
                    DateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",new Locale("ru","RU"));
                    Date dateupdate= dateFormat.parse(date_update);
                    list.setDateUpdate(dateupdate);

                    // TODO: 29.08.2024 user_update
                    Long user_update=   jsonNode.findValue("user_update").asLong();
                    list.setUserUpdate(user_update);
                    // TODO: 29.08.2024 current_table
                    Long current_table=   jsonNode.findValue("current_table").asLong();
                    list.setCurrentTable(BigDecimal.valueOf(current_table));
                    // TODO: 29.08.2024 current_table
                    Long uuid=  jsonNode.findValue("uuid").asLong();
                    list.setUuid(BigDecimal.valueOf(uuid));


                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        }).doOnError(e->{
                    // TODO: 29.08.2024
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :" +
                            Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
        })
                .doOnComplete(()->{
                    // TODO: 26.07.2024
                    Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                            " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                            " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
                }).blockingSubscribe();
        // TODO: 26.07.2024
        Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n" );
        return  list ;
    }
}


