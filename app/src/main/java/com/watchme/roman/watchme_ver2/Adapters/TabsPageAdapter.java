package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.watchme.roman.watchme_ver2.Fragments.MostRatedFragment;
import com.watchme.roman.watchme_ver2.Fragments.NewestFragment;
import com.watchme.roman.watchme_ver2.Fragments.PopularFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roman on 03/09/2015.
 */
public class TabsPageAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public TabsPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewestFragment tab0 = new NewestFragment();
                return tab0;
            case 1:
                PopularFragment tab1 = new PopularFragment();
                return tab1;
            case 2:
                MostRatedFragment tab2 = new MostRatedFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
