package com.watchme.roman.watchme_ver2.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Adapters.ActorDetailsPageAdapter;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

/*******************************************************************/
/* Actors details class that gets actor image from intent and sets */
/* the thumb of collapsing toolbar                                 */

/*******************************************************************/
public class ActorDetailsActivity extends BaseActivity {

    // Animation
    private Animation posterAnim;

    private String actorImgURL;
    private String actorName;
    private NetworkImageView actorIMG;

    // ImageLoader
    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuConfiguration();

        // Get actor image and name from intent
        Intent intent = getIntent();
        if (intent != null) {
            actorImgURL = intent.getStringExtra(Constants.ACTOR_IMG_TAG);
            actorName = intent.getStringExtra(Constants.ACTOR_NAME_TAG);
        }
        // Set title as actor name
        getSupportActionBar().setTitle(actorName);

        actorIMG = (NetworkImageView) findViewById(R.id.iv_collapsing_thumb);
        actorIMG.setImageUrl(Constants.BASE_IMG_URL + Constants.BIG_POSTER + actorImgURL, imageLoader);
         actorIMG.getLayoutParams().height = 1300;


        // Customize AppBarLayout to show correctly actors image
        if (getResources().getBoolean(R.bool.isTablet)) {
            collapsingToolbarLayout.getLayoutParams().height = 900;
            posterAnim = new TranslateAnimation(0, 0, -400, -100);
        }
        else {
            collapsingToolbarLayout.getLayoutParams().height = 600;
            posterAnim = new TranslateAnimation(0, 0, -600, -350);

        }

        //  Actor collapsing image animation
        posterAnim.setDuration(5000);
        posterAnim.setFillAfter(true);
        actorIMG.setAnimation(posterAnim);

        // Create needed tabs
        tabLayout.addTab(tabLayout.newTab().setText("Actor"));
        tabLayout.addTab(tabLayout.newTab().setText("Filmography"));

        // Set the viewpager and the pages adapter
        final ActorDetailsPageAdapter adapter = new ActorDetailsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

    }

    /**********************************/
    /* OnClick of Residemenu          */

    /**********************************/
    @Override
    public void onClick(View v) {

        if (v == itemMovies) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v == itemTVSeries) {
            Intent intent = new Intent(this, TvSeriesActivity.class);
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
