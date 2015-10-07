package com.watchme.roman.watchme_ver2.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
 * Created by roman on 19/09/2015.
 */
public class FavoriteMoviesFragment extends Fragment {

    private static int itemPosition;
    // Movies list
    private List<Movie> moviesList = new ArrayList<>();
    // Adapters
    protected RecyclerView recyclerView;
    protected FavoritesRecyclerAdapter favoritesRecyclerAdapter;

    // GridLayoutManager
    private GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.recyclerlist_fragment, container, false);


        // Initialize recyclerVIew
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        // Set the recycler to show two images in single row
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        DataBaseTask myTask = new DataBaseTask();
        myTask.execute();

        return view;
    }

    protected class DataBaseTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List doInBackground(Void... params) {
            DaoSession daoSession = ((WatchMeApplication) getActivity().getApplicationContext()).getDaoSession();
            MovieDao movieDao = daoSession.getMovieDao();
            List<Movie> movies = movieDao.queryBuilder().where(MovieDao.Properties.Tv.eq(false)).list();
            return movies;
        }

        @Override
        protected void onPostExecute(final List<Movie> movies) {
            super.onPostExecute(movies);
            moviesList = movies;
            favoritesRecyclerAdapter = new FavoritesRecyclerAdapter(moviesList);
            recyclerView.setAdapter(favoritesRecyclerAdapter);
            favoritesRecyclerAdapter.SetOnItemClickListener(new FavoritesRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    itemPosition = position;
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra(Constants.ID_TAG, moviesList.get(position).getMovie_id());
                    intent.putExtra(Constants.TITLE_TAG, moviesList.get(position).getTitle());
                    startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK) {
                moviesList.remove(itemPosition);
                favoritesRecyclerAdapter.notifyItemRemoved(itemPosition);
            }
        }
    }
}
