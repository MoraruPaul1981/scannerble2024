package com.dsy.dsu.Dashboard.Model.bl_viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dsy.dsu.Dashboard.View.Fragments.DashboardFragmentMaterialDesign;
import com.dsy.dsu.Dashboard.View.Fragments.LogoFragment;
import com.dsy.dsu.Dashboard.View.Fragments.LogoFragmentTwo;

public class DemoCollectionAdapter extends FragmentStateAdapter {
    public DemoCollectionAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = null;
        Bundle args;
        switch (position){
            case 0:
                  fragment = new LogoFragment();
                  args = new Bundle();
                // The object is just an integer.
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new LogoFragmentTwo();
                  args = new Bundle();
                // The object is just an integer.
                fragment.setArguments(args);
                break;
            
            
        }


        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
