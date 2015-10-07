package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.watchme.roman.watchme_ver2.Fragments.ActorDetailsFragment;
import com.watchme.roman.watchme_ver2.Fragments.ActorsFragment;
import com.watchme.roman.watchme_ver2.Fragments.FilmographyFragment;
import com.watchme.roman.watchme_ver2.Fragments.MovieInfoFragment;
import com.watchme.roman.watchme_ver2.Fragments.ReviewsFragment;

/**
 * Created by roman on 07/09/2015.
 */
public class ActorDetailsPageAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    public ActorDetailsPageAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ActorDetailsFragment tab0 = new ActorDetailsFragment();
                return tab0;
            case 1:
                FilmographyFragment tab1 = new FilmographyFragment();
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
