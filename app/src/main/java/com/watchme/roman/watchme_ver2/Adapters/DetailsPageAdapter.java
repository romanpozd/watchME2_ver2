package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.watchme.roman.watchme_ver2.Fragments.ActorsFragment;
import com.watchme.roman.watchme_ver2.Fragments.MovieInfoFragment;
import com.watchme.roman.watchme_ver2.Fragments.ReviewsFragment;


/**
 * Created by roman on 04/09/2015.
 */
public class DetailsPageAdapter extends FragmentStatePagerAdapter{

    int numOfTabs;
    public DetailsPageAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                MovieInfoFragment tab0 = new MovieInfoFragment();
                return tab0;
            case 1:
                ReviewsFragment tab1 = new ReviewsFragment();
                return tab1;
            case 2:
                ActorsFragment tab2 = new ActorsFragment();
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
