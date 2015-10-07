package com.watchme.roman.watchme_ver2.Activities;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.watchme.roman.watchme_ver2.Fragments.SearchListFragment;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;

/**
 * Created by roman on 21/09/2015.
 */
public class SearchActivity extends BaseActivity {

    protected String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        menuConfiguration();
        disableCollapsingToolbar();
        showFrameContainer();

        // Set toolbar title
        getSupportActionBar().setTitle(R.string.search);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            Bundle bundle = new Bundle();
            bundle.putString(Constants.SEARCH_QUERY, query);

            SearchListFragment fragment = new SearchListFragment();
            fragment.setArguments(bundle);

            // Replace framelayout with search fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();

        }
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
