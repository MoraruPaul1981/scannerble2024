package com.scanner.datasync.businesslayer.Errors;

import android.content.ContentValues;

import androidx.annotation.NonNull;

public interface ErrosInterface {


        Integer getVersionforErrorNew(@androidx.annotation.NonNull String СамЗапрос);

        void МетодЗаписиОшибок(@NonNull ContentValues contentValuesДляЗаписиОшибки);

}