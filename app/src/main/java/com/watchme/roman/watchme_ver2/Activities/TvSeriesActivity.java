package com.watchme.roman.watchme_ver2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.watchme.roman.watchme_ver2.Adapters.TVSeriesTabsAdapter;
import com.watchme.roman.watchme_ver2.R;


/**
 * Created by roman on 15/09/2015.
 */
public class TvSeriesActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuConfiguration();
        disableCollapsingToolbar();

        // Set toolbar title
        getSupportActionBar().setTitle("TV Series");


        // Create needed tabs
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.popular_movies)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.most_rated_moves)));

        final TVSeriesTabsAdapter adapter = new TVSeriesTabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v == itemMovies) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == itemTVSeries)
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
