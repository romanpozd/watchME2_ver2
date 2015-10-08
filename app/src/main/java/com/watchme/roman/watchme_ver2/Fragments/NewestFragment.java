package com.watchme.roman.watchme_ver2.Fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vlonjatg.progressactivity.ProgressActivity;
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;
import com.watchme.roman.watchme_ver2.Activities.MainActivity;
import com.watchme.roman.watchme_ver2.Adapters.GridRecyclerAdapter;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Utils.ParseJSON;
import com.watchme.roman.watchme_ver2.Utils.Utility;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 04/09/2015.
 */
public class NewestFragment extends Fragment {

    // Uri of requested data
    protected Uri uri;
    // ProgressActivity
    protected ProgressActivity progressActivity;

    int page = 1;
    // Adapters
    protected RecyclerView recyclerView;
    protected GridRecyclerAdapter gridRecyclerAdapter;

    // Movies List
    protected List<Movie> myMovieList = new ArrayList<>();

    // Variables for onScroll
    private int visibleItems, totalItemCount, firstVisible;
    private boolean loading = true;

    // GridLayoutManager
    private GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);
        progressActivity = (ProgressActivity) view.findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        // Initialize recyclerVIew
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gridRecyclerAdapter = new GridRecyclerAdapter(myMovieList);
        recyclerView.setAdapter(gridRecyclerAdapter);

        // Disable nestedscrolling of CollapsingToolbar
        recyclerView.setNestedScrollingEnabled(false);

        // Adapter onClick
        gridRecyclerAdapter.SetOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Start DetailsActivity with movie details
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(Constants.ID_TAG, myMovieList.get(position).getMovieId());
                intent.putExtra(Constants.TITLE_TAG, myMovieList.get(position).getTitle());
                intent.putExtra(Constants.IS_TVSERIES_TAG, false);
                intent.putExtra(Constants.BACKDROP_TAG, myMovieList.get(position).getBackdrop());
                startActivity(intent);

            }
        });
        // Set the recycler to show 3 images in portrait mode, 4 in landscape and 5 in tablet landscape
        if (getActivity().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        else if (getResources().getBoolean(R.bool.isTablet))
            gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        else
            gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Handle reached bottom of recycledview to download more movies
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItems = recyclerView.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                firstVisible = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItems + firstVisible) >= totalItemCount) {
                        loading = false;

                        try {
                            UpdateMovies(++page);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if ((totalItemCount - visibleItems) <= firstVisible)
                    loading = true;

            }
        });

        try {
            UpdateMovies(page);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    protected void UpdateMovies(int pageNum) throws UnsupportedEncodingException, JSONException {
        uri = Utility.buildUriCurrentTheaters(getActivity(), pageNum, Constants.MOVIE_PATH, Constants.NEWEST_PARAM);
        ParseJSON parseJSON = new ParseJSON(uri, gridRecyclerAdapter, myMovieList);
        parseJSON.movieJSONparse(new ParseJSON.VolleyEmptyCallBack() {
            @Override
            public void onSuccess() {
                progressActivity.showContent();
            }

            @Override
            public void onError() {
                showErrorMsg();
            }
        });
    }

    protected View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VolleyController.getmInstance().getmRequestQueue().getCache().clear();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        VolleyController.getmInstance().getmRequestQueue().getCache().clear();
    }

    protected void showErrorMsg() {
        int size = myMovieList.size();
        myMovieList.clear();
        gridRecyclerAdapter.notifyItemRangeRemoved(0, size);
        if (progressActivity.isError())
            progressActivity.showContent();
        progressActivity.showError(ContextCompat.getDrawable(getContext(), R.drawable.no_signal), "No connection",
                "No internet connection, please try again when connected.",
                "Try again", errorClickListener);
    }
}
