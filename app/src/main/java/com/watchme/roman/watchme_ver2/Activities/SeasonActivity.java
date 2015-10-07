package com.watchme.roman.watchme_ver2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Fragments.EpisodeFragment;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

/**
 * Created by roman on 17/09/2015.
 */
public class SeasonActivity extends BaseActivity {

    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();


    // Title
    private String title;

    // Thumb
    private String serieThumb;
    private NetworkImageView thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuConfiguration();
        showFrameContainer();

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra(Constants.TITLE_TAG);
            serieThumb = intent.getStringExtra(Constants.BACKDROP_TAG);
        }

        thumb = (NetworkImageView) findViewById(R.id.iv_collapsing_thumb);
        thumb.setImageUrl(Constants.BASE_IMG_URL + Constants.BIG_POSTER + serieThumb, imageLoader);

        // Set toolbar title
        getSupportActionBar().setTitle(title);


        // Commit episodes fragment below collapsing toolbar
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new EpisodeFragment()).commit();

    }


    @Override
    public void onClick(View v) {
        if (v == itemTVSeries) {
            Intent intent = new Intent(this, TvSeriesActivity.class);
            startActivity(intent);
        } else if (v == itemMovies) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == itemFavorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        } else if (v == itemSearch) {
            resideMenu.closeMenu();
            searchView.setIconified(false);
        }
    }


}
