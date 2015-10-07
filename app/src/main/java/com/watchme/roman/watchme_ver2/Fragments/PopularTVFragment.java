package com.watchme.roman.watchme_ver2.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vlonjatg.progressactivity.ProgressActivity;
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;
import com.watchme.roman.watchme_ver2.Activities.TvSeriesActivity;
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
 * Created by roman on 15/09/2015.
 */
public class PopularTVFragment extends Fragment {


    // ProgressActivity
    protected ProgressActivity progressActivity;
    // Shown pages count
    int page = 1;

    // Adapters
    protected RecyclerView recyclerView;
    protected GridRecyclerAdapter gridRecyclerAdapter;

    // Movies List
    protected List<Movie> myTVList = new ArrayList<>();

    // Variables for onScroll
    private int visibleItems, totalItemCount, firstVisible;
    private boolean loading = true;

    // GridLayoutManager
    private GridLayoutManager gridLayoutManager;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);

        progressActivity = (ProgressActivity) view.findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        // Initialize recyclerVIew
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gridRecyclerAdapter = new GridRecyclerAdapter(myTVList);
        recyclerView.setAdapter(gridRecyclerAdapter);

        // Adapter OnClick
        gridRecyclerAdapter.SetOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Start DetailsActivity with movie details
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(Constants.ID_TAG, myTVList.get(position).getMovieId());
                intent.putExtra(Constants.TITLE_TAG, myTVList.get(position).getTitle());
                intent.putExtra(Constants.BACKDROP_TAG, myTVList.get(position).getBackdrop());
                intent.putExtra(Constants.IS_TVSERIES_TAG, true);
                startActivity(intent);
            }
        });
        // Set the recycler to show two images in single row
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        // Handle reach bottom of recycledview and load more movies
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            UpdateTVSeries(++page);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if ((totalItemCount - visibleItems) <= firstVisible)
                    loading = true;

            }
        });
        try {
            UpdateTVSeries(page);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }


    protected void UpdateTVSeries(int page) throws UnsupportedEncodingException, JSONException {
        Uri uri = Utility.buildUriCurrentTheaters(getActivity(), page, Constants.TV_PATH, Constants.POPULAR_PATH);
        ParseJSON parseJSON = new ParseJSON(uri, gridRecyclerAdapter, myTVList);
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

    protected void showErrorMsg() {
        int size = myTVList.size();
        myTVList.clear();
        gridRecyclerAdapter.notifyItemRangeRemoved(0, size);
        if (progressActivity.isError())
            progressActivity.showContent();
        progressActivity.showError(ContextCompat.getDrawable(getContext(), R.drawable.no_signal), "No connection",
                "No internet connection, please try again when connected.",
                "Try again", errorClickListener);
    }

    protected View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VolleyController.getmInstance().getmRequestQueue().getCache().clear();
            Intent intent = new Intent(getActivity(), TvSeriesActivity.class);
            startActivity(intent);
        }
    };
}
