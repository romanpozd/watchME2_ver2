package com.watchme.roman.watchme_ver2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.moviesgreendao.model.MovieDao;
import com.watchme.roman.moviesgreendao.model.Movie;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 20/09/2015.
 */
public class FavoritesRecyclerAdapter extends RecyclerView.Adapter<FavoritesRecyclerAdapter.CustomViewHolder> {

    // OnClickListener
    OnItemClickListener mItemClickListener;
    OnItemLongClickListener mItemLongClickListener;

    // Image loader to get image from url
    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // List of movie objects
    private List<Movie> moviesList;

    // Constructor
    public FavoritesRecyclerAdapter(List<Movie> movies) {
        this.moviesList = movies;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected NetworkImageView movieImg;
        protected TextView movieTitle, movieYear;

        public CustomViewHolder(View v) {
            super(v);
            this.movieImg = (NetworkImageView) v.findViewById(R.id.niv_grid_poster);
            this.movieTitle = (TextView) v.findViewById(R.id.tv_grid_movie_title);
            this.movieYear = (TextView) v.findViewById(R.id.tv_grid_movie_year);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mItemLongClickListener != null)
                mItemLongClickListener.onItemLongClick(v, getPosition());
            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public FavoritesRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid_poster, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.movieImg.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + movie.getPosterURL(), imageLoader);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText("Release: " + String.valueOf(movie.getRelease()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
