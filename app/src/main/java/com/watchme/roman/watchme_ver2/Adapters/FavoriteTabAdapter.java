package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.watchme.roman.watchme_ver2.Fragments.FavoriteMoviesFragment;
import com.watchme.roman.watchme_ver2.Fragments.FavoriteTVSeriesFragment;


/**
 * Created by roman on 19/09/2015.
 */
public class FavoriteTabAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public FavoriteTabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FavoriteMoviesFragment tab0 = new FavoriteMoviesFragment();
                return tab0;
            case 1:
                FavoriteTVSeriesFragment tab1 = new FavoriteTVSeriesFragment();
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
