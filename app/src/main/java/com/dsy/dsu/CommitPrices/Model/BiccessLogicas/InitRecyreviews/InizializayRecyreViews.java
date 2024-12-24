package com.dsy.dsu.CommitPrices.Model.BiccessLogicas.InitRecyreviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsy.dsu.CommitPrices.Model.BiccessLogicas.DizaynRecyreView.LeftDividerItemDecorator;
import com.dsy.dsu.CommitPrices.View.ComponentsPayPrices.CommintPriseItemDecorator;
import com.dsy.dsu.Errors.Class_Generation_Errors;
import com.dsy.dsu.R;

public class InizializayRecyreViews {

    private RecyclerView recycleview_comminingpprices;
    private Context context;
    private Animation animationДляСогласования;

    public InizializayRecyreViews(RecyclerView recycleview_comminingpprices, Context context ) {
        this.recycleview_comminingpprices = recycleview_comminingpprices;
        this.context = context;
    }

    public void startInitRecyreview() {
        try {
            recycleview_comminingpprices.setHasFixedSize(true);
            CommintPriseItemDecorator commintPriseItemDecorator=       new CommintPriseItemDecorator(context);
            recycleview_comminingpprices.addItemDecoration(commintPriseItemDecorator);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 1,
                    GridLayoutManager.VERTICAL,false);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.DefaultSpanSizeLookup());
            recycleview_comminingpprices.setLayoutManager(layoutManager);
            recycleview_comminingpprices.requestLayout();
            recycleview_comminingpprices.refreshDrawableState();
            // TODO: 28.02.2022
            Log.d(this.getClass().getName(), "\n" + " class "
                    + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                    + Thread.currentThread().getStackTrace()[2].getMethodName() +
                    " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new Class_Generation_Errors(context).МетодЗаписиВЖурналНовойОшибки(e.toString(), this.getClass().getName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(), Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }







}
