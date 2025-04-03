package com.dsy.dsu.Dashboard.Model.bl_launchFragmentSettingsandDashbord;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;
import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentSettings;
import com.dsy.dsu.Errors.controller.RecordNewErros;
import com.dsy.dsu.R;
import com.dsy.dsu.Settings.View.BlankFragmentError;
import com.dsy.dsu.Tabels.Tabel.Single.FragmentSingleTabelOneSwipe;

public class LaunchActivityDashboard {

    FragmentManager fragmentManager;
    Context context;

    public LaunchActivityDashboard(  FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;
    }


  public   void launchADashboardFragment() {
        try {
            // TODO Запусукаем Фргамент DdshBoard
           // Fragment      dashboardFragmentMaterialDesign = new DashboardFragmentMaterialDesign();
            Fragment      dashboardFragmentMaterialDesign = new BlankFragmentError();
            Bundle data = new Bundle();
            FragmentTransaction   fragmentTransaction = fragmentManager.beginTransaction();
            dashboardFragmentMaterialDesign.setArguments(data);
             fragmentTransaction.setPrimaryNavigationFragment(dashboardFragmentMaterialDesign);
            dashboardFragmentMaterialDesign.setEnterTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
             fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.add(R.id.linearLayout_root_activity_dashboard, dashboardFragmentMaterialDesign);
            fragmentTransaction.commit();
            fragmentTransaction.show(dashboardFragmentMaterialDesign);
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(context.getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(context).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }






}
