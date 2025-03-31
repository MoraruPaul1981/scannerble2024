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

public class LaunchActivityDashboard {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Context context;

    public LaunchActivityDashboard(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager, Context context) {
        this.fragmentTransaction = fragmentTransaction;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }


  public   void launchADashboardFragment() {
        try {
            // TODO Запусукаем Фргамент DdshBoard
            DashboardFragmentMaterialDesign dashboardFragmentHarmonyOS = DashboardFragmentMaterialDesign.newInstance();
            Bundle data = new Bundle();
            dashboardFragmentHarmonyOS.setArguments(data);
            fragmentTransaction.remove(dashboardFragmentHarmonyOS);
            String fragmentNewImageNameaddToBackStack = dashboardFragmentHarmonyOS.getClass().getName();
            fragmentTransaction.addToBackStack(fragmentNewImageNameaddToBackStack)
                    .setPrimaryNavigationFragment(dashboardFragmentHarmonyOS)
                    .setReorderingAllowed(true);
            Fragment FragmentУжеЕСтьИлиНЕт = fragmentManager.findFragmentByTag(fragmentNewImageNameaddToBackStack);
            if (FragmentУжеЕСтьИлиНЕт == null) {
                dashboardFragmentHarmonyOS.show(fragmentManager, "dashboardFragmentHarmonyOS");
                // TODO: 01.08.2023
            }
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n"
                    + " FragmentУжеЕСтьИлиНЕт " + FragmentУжеЕСтьИлиНЕт);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getApplicationContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }
    public   void launchStartingDashboardFragmentSettings() {
        try {
            // TODO Запусукаем Фргамент DdshBoard
            DashboardFragmentSettings dashboardFragmentSettings = DashboardFragmentSettings.newInstance();
            Bundle data = new Bundle();
            dashboardFragmentSettings.setArguments(data);
            fragmentManager.popBackStack();
            // TODO: 10.03.2025
            fragmentTransaction.remove(dashboardFragmentSettings).commit() ;
            fragmentTransaction.setPrimaryNavigationFragment(dashboardFragmentSettings)
                    .setReorderingAllowed(true);
            // TODO: 10.03.2025
            dashboardFragmentSettings.show(fragmentManager, "dashboardFragmentHarmonyOS");
            // TODO: 01.08.2023
            Log.d(this.getClass().getName(), "\n" + " class " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n" +
                    " metod " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" +
                    " line " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(getApplicationContext().getClass().getName(),
                    "Ошибка " + e + " Метод :" + Thread.currentThread().getStackTrace()[2].getMethodName() +
                            " Линия  :" + Thread.currentThread().getStackTrace()[2].getLineNumber());
            new RecordNewErros(getApplicationContext()).recordnewerror(e.toString(),
                    this.getClass().getName().toString(), Thread.currentThread().getStackTrace()[2].getMethodName().toString(),
                    Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

    }





}
