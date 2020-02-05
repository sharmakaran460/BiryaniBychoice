package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fragmentlist =new ArrayList<>();
    private final ArrayList<String> fragmenttitle =new ArrayList<>();

    public ViewPagerAdapter (FragmentManager fm){
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmenttitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmenttitle.get(position);
    }

    public void addfragment(Fragment fragment , String title){
        fragmentlist.add(fragment);
        fragmenttitle.add(title);
    }
}
