package com.sous.server.businesslayer.Eventbus;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ParamentsScannerServer {


    private String currentTask;
    private String s2;
    private String s3;


    private ConcurrentHashMap<String, ContentValues> contentValuesConcurrentHashMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Cursor> concurrentHashMapCursor = new ConcurrentHashMap<>();
    private String s5;

    private Boolean флагЗапускаФрагментRecyreView = false;


    public String getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(String currentTask) {
        this.currentTask = currentTask;
    }

    private String getS2() {
        return s2;
    }

    private void setS2(String s2) {
        this.s2 = s2;
    }

    private String getS3() {
        return s3;
    }

    private void setS3(String s3) {
        this.s3 = s3;
    }


    private String getS5() {
        return s5;
    }

    private void setS5(String s5) {
        this.s5 = s5;
    }


    public Boolean getФлагЗапускаФрагментRecyreView() {
        return флагЗапускаФрагментRecyreView;
    }

    public void setФлагЗапускаФрагментRecyreView(Boolean флагЗапускаФрагментRecyreView) {
        this.флагЗапускаФрагментRecyreView = флагЗапускаФрагментRecyreView;


    }

    public ConcurrentHashMap<String, ContentValues> getContentValuesConcurrentHashMap() {
        return contentValuesConcurrentHashMap;
    }

    public void setContentValuesConcurrentHashMap(ConcurrentHashMap<String, ContentValues> contentValuesConcurrentHashMap) {
        this.contentValuesConcurrentHashMap = contentValuesConcurrentHashMap;
    }

    public ConcurrentHashMap<String, Cursor> getConcurrentHashMapCursor() {
        return concurrentHashMapCursor;
    }

    public void setConcurrentHashMapCursor(ConcurrentHashMap<String, Cursor> concurrentHashMapCursor) {
        this.concurrentHashMapCursor = concurrentHashMapCursor;
    }


}
