package com.dsy.dsu.Dashboard.Model.bl_viewpager2;

import android.content.Context;
import android.util.Log;

import androidx.viewpager2.widget.ViewPager2;

import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;
import com.dsy.dsu.Errors.controller.RecordNewErros;

public class BunesslogicViewPager2 {
   private ViewPager2 pagerdachbord;
    private Context getcontext;
    private  DemoCollectionAdapter demoCollectionAdapter;
    private  DashboardFragmentMaterialDesign dashboardFragmentMaterialDesign;
    public BunesslogicViewPager2(ViewPager2 pagerdachbord, Context getcontext, DashboardFragmentMaterialDesign dashboardFragmentMaterialDesign) {
        this.pagerdachbord = pagerdachbord;
        this.getcontext = getcontext;
        this.dashboardFragmentMaterialDesign = dashboardFragmentMaterialDesign;
    }

   public void getBunesslogicViewPager2(){
        try{
            demoCollectionAdapter = new DemoCollectionAdapter(dashboardFragmentMaterialDesign);

            dashboardFragmentMaterialDesign.pagerdachbord.setAdapter(demoCollectionAdapter);
        // TODO: 21.06.2023
        Log.d(this.getClass().getName(),"\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
    } catch (Exception e) {
        e.printStackTrace();
        Log.e(this.getClass().getName(), "Ошибка " + e + " Метод :"
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " Линия  :"
                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        new RecordNewErros(getcontext).recordnewerror(e.toString(), this.getClass().getName(),
                Thread.currentThread().getStackTrace()[2].getMethodName(),
                Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    }


}
