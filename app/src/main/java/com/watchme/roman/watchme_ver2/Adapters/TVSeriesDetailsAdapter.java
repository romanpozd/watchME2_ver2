package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.watchme.roman.watchme_ver2.Fragments.ActorsFragment;
import com.watchme.roman.watchme_ver2.Fragments.MovieInfoFragment;
import com.watchme.roman.watchme_ver2.Fragments.ReviewsFragment;
import com.watchme.roman.watchme_ver2.Fragments.TVSeasonsFragment;
import com.watchme.roman.watchme_ver2.Fragments.TVSeriesInfoFragment;

/**
 * Created by roman on 15/09/2015.
 */
public class TVSeriesDetailsAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TVSeriesDetailsAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TVSeriesInfoFragment tab0 = new TVSeriesInfoFragment();
                return tab0;
            case 1:
                ActorsFragment tab1 = new ActorsFragment();
                return tab1;
            case 2:
                TVSeasonsFragment tab2 = new TVSeasonsFragment();
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
