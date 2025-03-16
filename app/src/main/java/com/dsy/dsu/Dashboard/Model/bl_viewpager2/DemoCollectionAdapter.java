package com.dsy.dsu.Dashboard.Model.bl_viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    public DemoCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = new DashboardFragmentMaterialDesign();
        Bundle args = new Bundle();
        // The object is just an integer.
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 100;
    }
}
