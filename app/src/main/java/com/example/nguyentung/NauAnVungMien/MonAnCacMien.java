package com.example.nguyentung.NauAnVungMien;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hungn on 15-Oct-16.
 */

public class MonAnCacMien extends FragmentPagerAdapter {
    private Fragment[] screens;
    public MonAnCacMien(FragmentManager fm, Fragment[] _screens) {
        super(fm);
        this.screens = _screens;
    }

    @Override
    public Fragment getItem(int position) {
        return screens[position];
    }

    @Override
    public int getCount() {
        return screens.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Miền Bắc";
                break;
            case 1:
                title = "Miền Trung";
                break;
            case 2:
                title = "Miền Nam";
                break;
            case 3:
                title = "Miền Tây";
                break;
        }
        return title;
    }
}
