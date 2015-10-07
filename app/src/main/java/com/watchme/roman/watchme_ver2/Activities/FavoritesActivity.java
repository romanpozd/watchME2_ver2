package com.watchme.roman.watchme_ver2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.watchme.roman.watchme_ver2.Adapters.FavoriteTabAdapter;
import com.watchme.roman.watchme_ver2.R;

/**
 * Created by roman on 19/09/2015.
 */
public class FavoritesActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuConfiguration();
        disableCollapsingToolbar();
        // Set toolbar title
        getSupportActionBar().setTitle("Favorites");

        // Create needed tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.movies_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tv_series_tab));

        final FavoriteTabAdapter adapter = new FavoriteTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v == itemTVSeries) {
            Intent intent = new Intent(this, TvSeriesActivity.class);
            startActivity(intent);
        } else if (v == itemMovies) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == itemFavorites)
            resideMenu.closeMenu();
        else if (v == itemSearch) {
            resideMenu.closeMenu();
            searchView.setIconified(false);
        }
    }

}
