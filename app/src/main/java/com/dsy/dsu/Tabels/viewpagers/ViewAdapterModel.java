package com.dsy.dsu.Tabels.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.concurrent.CopyOnWriteArrayList;

public class ViewAdapterModel extends FragmentPagerAdapter {//androidx.viewpager2.adapter.FragmentStateAdapter
    CopyOnWriteArrayList<Fragment> fragments=new CopyOnWriteArrayList<>();

    public void setFragments(CopyOnWriteArrayList<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public ViewAdapterModel(@NonNull FragmentManager fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
