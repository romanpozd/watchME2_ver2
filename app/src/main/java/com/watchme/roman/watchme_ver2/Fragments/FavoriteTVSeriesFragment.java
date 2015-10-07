package com.watchme.roman.watchme_ver2.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.watchme.roman.moviesgreendao.model.DaoSession;
import com.watchme.roman.moviesgreendao.model.Movie;
import com.watchme.roman.moviesgreendao.model.MovieDao;
import com.watchme.roman.watchme_ver2.Activities.DetailsActivity;
import com.watchme.roman.watchme_ver2.Adapters.FavoritesRecyclerAdapter;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.WatchMeApplication;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 21/09/2015.
 */
public class FavoriteTVSeriesFragment extends Fragment {

    private static int itemPosition;
    // Movies list
    private List<Movie> tvSeriesList = new ArrayList<>();
    // Adapters
    protected RecyclerView recyclerView;
    protected FavoritesRecyclerAdapter favoriteTVSeriesRecyclerAdapter;

    // GridLayoutManager
    private GridLayoutManager gridLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseTask myTask = new DataBaseTask();
        myTask.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlist_fragment,container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        // Set the recycler to show two images in single row
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }

    private class DataBaseTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List doInBackground(Void... params) {
            DaoSession daoSession = ((WatchMeApplication) getActivity().getApplicationContext()).getDaoSession();
            MovieDao tvSeriesDao = daoSession.getMovieDao();
            List<Movie> tvSeries = tvSeriesDao.queryBuilder().where(MovieDao.Properties.Tv.eq(true)).list();
            return tvSeries;
        }

        @Override
        protected void onPostExecute(final List<Movie> movies) {
            super.onPostExecute(movies);
            tvSeriesList = movies;
            favoriteTVSeriesRecyclerAdapter = new FavoritesRecyclerAdapter(tvSeriesList);
            recyclerView.setAdapter(favoriteTVSeriesRecyclerAdapter);
            favoriteTVSeriesRecyclerAdapter.SetOnItemClickListener(
                    new FavoritesRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    itemPosition = position;
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(Constants.ID_TAG, tvSeriesList.get(position).getMovie_id());
                    intent.putExtra(Constants.TITLE_TAG, tvSeriesList.get(position).getTitle());
                    intent.putExtra(Constants.IS_TVSERIES_TAG, true);
                    startActivityForResult(intent,2);
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == getActivity().RESULT_OK) {
                tvSeriesList.remove(itemPosition);
                favoriteTVSeriesRecyclerAdapter.notifyItemRemoved(itemPosition);
            }
        }
    }
}
