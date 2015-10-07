package com.watchme.roman.watchme_ver2.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Adapters.DetailsPageAdapter;
import com.watchme.roman.watchme_ver2.Adapters.TVSeriesDetailsAdapter;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

public class DetailsActivity extends BaseActivity {

    // Volley ImageLoader
    private ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // Title and backdrop from intent
    private String title;
    private String backdropUrl;
    private boolean is_tv;

    // Movie backdrop
    private NetworkImageView movieBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        menuConfiguration();

        // Get movie thumb, title and check if it's tv or movie
        Intent intent = getIntent();
        if (intent != null) {
            backdropUrl = intent.getStringExtra(Constants.BACKDROP_TAG);
            title = intent.getStringExtra(Constants.TITLE_TAG);
            is_tv = intent.getBooleanExtra(Constants.IS_TVSERIES_TAG,false);
        }

        // Set movie backdrop
        movieBackdrop = (NetworkImageView)findViewById(R.id.iv_collapsing_thumb);
        movieBackdrop.setImageUrl(backdropUrl, imageLoader);


        getSupportActionBar().setTitle(title);


        // Check if there is a movie or tv series and create needed tabs
        if (!is_tv) {
            // If there is a movie
            tabLayout.addTab(tabLayout.newTab().setText("Movie"));
            tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
            tabLayout.addTab(tabLayout.newTab().setText("Actors"));
        }else{
            // If there is a TV series
            tabLayout.addTab(tabLayout.newTab().setText("TV Series"));
            tabLayout.addTab(tabLayout.newTab().setText("Actors"));
            tabLayout.addTab(tabLayout.newTab().setText("Seasons"));
        }

        final DetailsPageAdapter adapter;
        final TVSeriesDetailsAdapter tv_adapter;
        if (!is_tv) {
            adapter = new DetailsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
        }
        else {
            tv_adapter = new TVSeriesDetailsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(tv_adapter);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == itemMovies){
            resideMenu.closeMenu();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if (v == itemTVSeries){
            Intent intent = new Intent(this, TvSeriesActivity.class);
            startActivity(intent);
        }else if (v == itemFavorites){
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        }else if (v == itemSearch){
            resideMenu.closeMenu();
            searchView.setIconified(false);
        }
    }

}
