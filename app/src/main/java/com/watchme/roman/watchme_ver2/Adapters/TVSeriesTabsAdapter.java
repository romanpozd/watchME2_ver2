package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.watchme.roman.watchme_ver2.Fragments.MostRatedTVFragment;
import com.watchme.roman.watchme_ver2.Fragments.PopularTVFragment;

/**
 * Created by roman on 15/09/2015.
 */
public class TVSeriesTabsAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TVSeriesTabsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PopularTVFragment tab0 = new PopularTVFragment();
                return tab0;
            case 1:
                MostRatedTVFragment tab1 = new MostRatedTVFragment();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
