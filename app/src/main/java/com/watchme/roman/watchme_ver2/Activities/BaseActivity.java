package com.watchme.roman.watchme_ver2.Activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.ResideMenu.ResideMenu;
import com.watchme.roman.watchme_ver2.ResideMenu.ResideMenuItem;
import com.watchme.roman.watchme_ver2.Utils.AnalyticsApplication;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

/****************************************************
 * Created by roman on 24/09/2015.
 * Base Activity holds all ResideMenu objects.
 * All classes inherited from this base class
 ****************************************************/
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    // Screen dimensions
    private int screenWidth;
    private int screenHeight;
    // Menu
    protected Menu menu;
    protected SearchView searchView;
    // ResideMenu items
    protected ResideMenu resideMenu;
    protected ResideMenuItem itemMovies;
    protected ResideMenuItem itemTVSeries;
    protected ResideMenuItem itemFavorites;
    protected ResideMenuItem itemSearch;

    // Collapsingtoolbar
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    // Toolbar
    Toolbar toolbar;

    // TabLayout
    TabLayout tabLayout;

    // Viewpager
    ViewPager viewPager;

    // GoogleAnalytics tracker
    protected Tracker tracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_activity);


        ((AnalyticsApplication) getApplication()).startTracker();
        // Start GoogleAnalytics tracking
        tracker = ((AnalyticsApplication)getApplication()).getmTracker();
        // Set screen name
        tracker.setScreenName(getLocalClassName());

        // set activity tracker
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable navigator = getResources().getDrawable(R.mipmap.ic_action_list);
        getSupportActionBar().setHomeAsUpIndicator(navigator);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);


        if (getResources().getBoolean(R.bool.isTablet)){
            // Set the collapsingtoolbar to be bigger than in smartphone
            collapsingToolbarLayout.getLayoutParams().height = 600;
        }
        // Set new tabLayout and create needed tabs
        tabLayout = (TabLayout) findViewById(R.id.collapsing_tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Initialize the viewpager
        viewPager = (ViewPager) findViewById(R.id.collapsing_pager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                ViewPager ignored_view = (ViewPager) findViewById(R.id.collapsing_pager);
                viewPager.setCurrentItem(tab.getPosition());

                // If user on first tab, enable left swipe menu, else disable to swipe correctly viewpager
                if (tab.getPosition() == 1 || tab.getPosition() == 2) {
                    resideMenu.addIgnoredView(ignored_view);
                } else if (tab.getPosition() == 0)
                    resideMenu.clearIgnoredViewList();

                tracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Tabs")
                                .setAction("Selected tab")
                                .setLabel(String.valueOf(tab.getText()))
                                .build());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    protected void menuConfiguration() {

        Rect visible = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(visible);
        // Method that gets device real size
        getRealScreenDimensions();

        // Check if navigationbar at right or bottom
        boolean navAtRight = screenHeight == visible.bottom;
        boolean navAtBottom = screenWidth == visible.right;

        int navigationSize;
        if (navAtRight)
            navigationSize = screenWidth - visible.right;
        else
            navigationSize = screenHeight - visible.bottom;

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.background);
        resideMenu.attachToActivity(this,navAtRight,navAtBottom,navigationSize);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);


        // Menu items
        itemMovies = new ResideMenuItem(this, R.drawable.movies, "Movies");
        itemTVSeries = new ResideMenuItem(this, R.drawable.series, "TV Series");
        itemFavorites = new ResideMenuItem(this, R.drawable.favorites, "Favorites");
        itemSearch = new ResideMenuItem(this, R.drawable.search_button, "Search");

        // OnClick
        itemMovies.setOnClickListener(this);
        itemTVSeries.setOnClickListener(this);
        itemFavorites.setOnClickListener(this);
        itemSearch.setOnClickListener(this);

        // Add items to ResideMenu
        resideMenu.addMenuItem(itemMovies, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTVSeries, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemFavorites, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSearch, ResideMenu.DIRECTION_LEFT);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Open ResideMenu when navigator clicked on toolbar
        if (menuItem.getItemId() == android.R.id.home) {
            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class)));


        return true;
    }

    @Override
    protected void onPause() {
        // Clear all volley cache when activity is onPause
        super.onPause();
        VolleyController.getmInstance().getmRequestQueue().getCache().clear();
        if (resideMenu.isOpened())
            resideMenu.closeMenu();
    }

    protected void disableCollapsingToolbar() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.movie_details_appBar);
        appBarLayout.setExpanded(false);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    protected void showFrameContainer() {
        viewPager.setVisibility(View.GONE);
        FrameLayout container = (FrameLayout) findViewById(R.id.frame_container);
        container.setVisibility(View.VISIBLE);
    }

    private void getRealScreenDimensions(){
        final int version = android.os.Build.VERSION.SDK_INT;
        Display display = getWindowManager().getDefaultDisplay();

        if (version >= 15)
        {
            Point size = new Point();
            display.getRealSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        }
        else
        {
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}





