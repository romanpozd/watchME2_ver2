package com.watchme.roman.watchme_ver2.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.watchme.roman.watchme_ver2.Adapters.TabsPageAdapter;
import com.watchme.roman.watchme_ver2.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        menuConfiguration();
        disableCollapsingToolbar();

        // Create needed tabs
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.newest_movies)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.popular_movies)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.most_rated_moves)));
        final TabsPageAdapter adapter = new TabsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        if (v == itemTVSeries) {
            Intent intent = new Intent(this, TvSeriesActivity.class);
            startActivity(intent);
        } else if (v == itemMovies)
            resideMenu.closeMenu();
        else if (v == itemFavorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        } else if (v == itemSearch) {
            resideMenu.closeMenu();
            searchView.setIconified(false);
        }
    }


}
