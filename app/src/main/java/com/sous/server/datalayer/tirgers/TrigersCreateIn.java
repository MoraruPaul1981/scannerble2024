package com.sous.server.datalayer.tirgers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public interface TrigersCreateIn {

    Context context = null;
    AtomicReference<SQLiteDatabase> atomicstoredEntities = new AtomicReference<>();

    void triggergeneration (@NotNull Context context,@NotNull  Long  version);

}
