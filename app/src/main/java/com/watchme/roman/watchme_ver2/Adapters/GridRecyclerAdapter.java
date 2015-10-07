package com.watchme.roman.watchme_ver2.Adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.watchme.roman.watchme_ver2.Model.Movie;
import com.watchme.roman.watchme_ver2.R;
import com.watchme.roman.watchme_ver2.Utils.Constants;
import com.watchme.roman.watchme_ver2.Volley.VolleyController;

import java.util.List;

/**
 * Created by roman on 10/09/2015.
 */
public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.CustomViewHolder> {

    // OnClickListener
    OnItemClickListener mItemClickListener;

    // Image loader to get image from url
    ImageLoader imageLoader = VolleyController.getmInstance().getmImageLoader();

    // List of movie objects
    private List<Movie> moviesList;

    // Constructor
    public GridRecyclerAdapter(List<Movie> movies){
        this.moviesList = movies;

    }
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected NetworkImageView movieImg;
        protected TextView movieTitle,movieYear;

        public CustomViewHolder(View v) {
            super(v);
            this.movieImg = (NetworkImageView)v.findViewById(R.id.niv_grid_poster);
            this.movieTitle = (TextView)v.findViewById(R.id.tv_grid_movie_title);
            this.movieYear = (TextView)v.findViewById(R.id.tv_grid_movie_year);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getPosition());
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public GridRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid_poster, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.movieImg.setImageUrl(Constants.BASE_IMG_URL + Constants.SMALL_POSTER + movie.getPosterURL(), imageLoader);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText("Release: " + String.valueOf(movie.getYear()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
